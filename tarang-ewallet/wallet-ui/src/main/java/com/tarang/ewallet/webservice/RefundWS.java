package com.tarang.ewallet.webservice;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dispute.business.DisputeService;
import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.transaction.business.FraudCheckService;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.transaction.util.ReversalType;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.form.DisputeForm;
import com.tarang.ewallet.walletui.form.LoginUserForm;
import com.tarang.ewallet.walletui.util.DisputeResponseMessage;
import com.tarang.ewallet.walletui.util.RequestMessage;
import com.tarang.ewallet.walletui.validator.DisputeValidator;
import com.tarang.ewallet.walletui.validator.LoginValidator;


@WebService
public class RefundWS {
	
	private static final Logger LOGGER = Logger.getLogger(RefundWS.class);	
	
	@Autowired
	private DisputeService disputeService;
	
	@Autowired 
	private TransactionWalletService transactionWalletService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private FraudCheckService fraudCheckService;

	
	@WebMethod(operationName="getHelloWorld")
	public DisputeResponseMessage getHelloWorld(RequestMessage requestMessage) {
		
		LoginUserForm loginUserForm = new LoginUserForm();
		loginUserForm.setEmail(requestMessage.getHeader().getEmail());
		loginUserForm.setPassword(requestMessage.getHeader().getPassword());
		LoginValidator loginValidator = new LoginValidator();
		DataBinder dataBinder = new DataBinder(loginUserForm); 
		BindingResult result = dataBinder.getBindingResult(); 
		loginValidator.validate(loginUserForm, result);
		
		if(result.hasErrors()) {
			return	prepareErrorResponseMessageForAuthentication(result);
		} else {
			DisputeForm disputeForm = new DisputeForm();
			disputeForm.setTransactionId(requestMessage.getTransactionId());
			disputeForm.setDistype(requestMessage.getDisputeType());
			disputeForm.setRequestAmount(requestMessage.getRequestAmount());
			disputeForm.setRequestedCurrencyId(requestMessage.getRequestCurrency());
			disputeForm.setMessage(requestMessage.getMessage());
			disputeForm.setTransactionAmount(requestMessage.getTransactionAmount());
			disputeForm.setUserType(1L);
			DataBinder dataBinder1 = new DataBinder(disputeForm); 
			BindingResult result1 = dataBinder1.getBindingResult(); 
			new DisputeValidator().validate(disputeForm, result1);
			if(result1.hasErrors()) {
				return prepareErrorResponseMessageForRefund(result1);
			}
		}

		try {
			if(disputeService.isDisputeExistForTxnId(requestMessage.getTransactionId())) {
				DisputeResponseMessage responseMessage = new DisputeResponseMessage();
				responseMessage.setDisputeAlreadyRaisedErrorMessage("error.msg.dispute.exist");
		        return responseMessage;
			}
			DisputeDto  riaseDto = new DisputeDto();
			riaseDto.setTransactionId(requestMessage.getTransactionId());
			riaseDto.setType(requestMessage.getDisputeType());
			riaseDto.setRequestAmount(Double.parseDouble(requestMessage.getRequestAmount()));
			riaseDto.setRequestCurrency(requestMessage.getRequestCurrency());
			riaseDto.setMessage(requestMessage.getMessage());
			riaseDto.setCreator(new BigDecimal(GlobalLitterals.CUSTOMER_USER_TYPE_ID.toString()));
			
			/*For email template*/
			WalletTransaction walletTxn = transactionWalletService.getTransaction(requestMessage.getTransactionId());
			riaseDto.setPayeeemailid(commonService.getUserEmailById(walletTxn.getPayee()));
			riaseDto.setPayeremailid(commonService.getUserEmailById(walletTxn.getPayer()));
			String personName = customerService.getPersonName(riaseDto.getPayeremailid(), GlobalLitterals.CUSTOMER_USER_TYPE);
			riaseDto.setPayerName(personName);
			
			riaseDto.setCurrencyCode(riaseDto.getRequestcurrency());
			if(riaseDto.getType().equals(ReversalType.CHARGE_BACK)){
				riaseDto.setDisputetype(GlobalLitterals.CHARGE_BACK_LABEL);
			} else {
				riaseDto.setDisputetype(GlobalLitterals.REFUND_LABEL);
			}
			DisputeDto disputeDto = disputeService.createDispute(riaseDto);
			//************   FRAUD CHECK START - 14  ***************//
			if(requestMessage.getDisputeType().equals(ReversalType.CHARGE_BACK)){
				Long txnId= disputeDto.getTransactionId();
				WalletTransaction walletTransaction= transactionWalletService.getTransaction(txnId);
				Integer hours = utilService.getDisputeAllowedHours();
				Integer noOfdisputes = utilService.getNumberOfDisputes();
				Date fromDate = DateConvertion.getPastDateByHours(hours);
				Boolean flag = fraudCheckService.merchantThresholdCheck(walletTransaction.getPayee(), noOfdisputes, fromDate, new Date());
				commonService.updateChargeBackCheck(walletTransaction.getPayee(), flag);
			}
		} catch (WalletException e){
			LOGGER.error(e.getMessage(), e);
			DisputeResponseMessage responseMessage = new DisputeResponseMessage();
			responseMessage.setDisputeAlreadyRaisedErrorMessage("error.msg.create.dispute");
	        return responseMessage;  
		}
		return prepareDisputeSuccessMessage();
	}


	private DisputeResponseMessage prepareErrorResponseMessageForAuthentication(BindingResult result){
		DisputeResponseMessage responseMessage =  new DisputeResponseMessage();
		FieldError emailError = result.getFieldError("email");
		FieldError passwordError = result.getFieldError("password");			
		if(emailError != null){
			responseMessage.setEmailIdErrorMessage(emailError.getCode());
		}
		if(passwordError != null){
			responseMessage.setPasswordErrorMessage(passwordError.getCode());
		}	
		return responseMessage;
	}
	
	private DisputeResponseMessage prepareErrorResponseMessageForRefund(BindingResult result){
		DisputeResponseMessage responseMessage =  new DisputeResponseMessage();
		FieldError transactionIdError = result.getFieldError("transactionId");
		FieldError disputeTypeError = result.getFieldError("disputeType");
		FieldError requestAmountError = result.getFieldError("requestAmount");
		FieldError requestCurrencyError = result.getFieldError("requestCurrency");
		FieldError messageError = result.getFieldError("message");
		FieldError transactionAmountError = result.getFieldError("transactionAmount");
		if(transactionIdError != null){
	    	responseMessage.setTransactionIdErrorMessage(transactionIdError.getCode());
		}
	    if(disputeTypeError != null){
	    	responseMessage.setDisputeTypeErrorMessage(disputeTypeError.getCode());
		}
	    if(requestAmountError != null){
	    	responseMessage.setRequestAmountErrorMessage(requestAmountError.getCode());
	    }
	    if(requestCurrencyError != null){
	    	responseMessage.setRequestCurrencyErrorMessage(requestCurrencyError.getCode());
	    }
	    if(messageError != null){
	    	responseMessage.setMessageErrorMessage(messageError.getCode());
	    }
	    if(transactionAmountError != null){
	    	responseMessage.setTransactionAmountErrorMessage(transactionAmountError.getCode());
	    }
	    return responseMessage;
	}
	
	private DisputeResponseMessage prepareDisputeSuccessMessage(){
		DisputeResponseMessage responseMessage = new DisputeResponseMessage();
		responseMessage .setSuccessMessage("suc.msg.create.dispute");
		return responseMessage;
	}
}