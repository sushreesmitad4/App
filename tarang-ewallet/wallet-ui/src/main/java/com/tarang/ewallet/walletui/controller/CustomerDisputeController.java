package com.tarang.ewallet.walletui.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.accountcloser.business.AccountCloserService;
import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.dispute.business.DisputeService;
import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.dto.DisputeGridDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.transaction.business.FraudCheckService;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.transaction.util.ReversalType;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Dispute;
import com.tarang.ewallet.walletui.form.DisputeForm;
import com.tarang.ewallet.walletui.form.DisputeSearchForm;
import com.tarang.ewallet.walletui.util.DisputeSearchUtil;
import com.tarang.ewallet.walletui.util.DisputeUtil;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.DisputeValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;


/**
 * @author  : kedarnathd
 * @date    : Feb 12, 2013
 * @time    : 7:54:03 PM
 * @version : 1.0.0
 * @comments: 
 *
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/customerdispute")
public class CustomerDisputeController implements Dispute, AttributeConstants, AttributeValueConstants, GlobalLitterals {
	
	private static final Logger LOGGER = Logger.getLogger(CustomerDisputeController.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private DisputeService disputeService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired 
	private TransactionWalletService transactionWalletService;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private FraudCheckService fraudCheckService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private MerchantService merchantService;

	@Autowired
	private  AccountCloserService  accountCloserService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showDisputePage(@Valid DisputeSearchForm disputeSearchForm, Map model, HttpServletRequest request, 
			Locale locale) throws WalletException{
		LOGGER.debug( " Customer dispute default page" );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
        String sucMessage = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(sucMessage != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(sucMessage, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		
		String errorMessage = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorMessage != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
			
		AccountCloserDto dto = accountCloserService.getAccountCloserByUser((Long)session.getAttribute(AUTHENTICATION_ID));
		if(dto != null){
			model.put(CLOSE_ACCOUNT , true);
		}
		
		model.put(DISPUTE_TYPE_LIST, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.DISPUTE_TYPES));
		
		String merchantId = (String)request.getParameter(MEMAILID);
		String fromDate = (String)request.getParameter(FDATE);
		String toDate = (String)request.getParameter(TDATE);
		String disputeType = (String)request.getParameter(DISPUTETYPE);
		
		// code for validating merchantId, fromDate, toDate
		String url = null;
			model.put("disputeSearchForm", disputeSearchForm );
			disputeSearchForm.setFromDate((String)request.getParameter(FDATE));
			disputeSearchForm.setToDate((String)request.getParameter(TDATE));
			disputeSearchForm.setMerchantEmailId((String)request.getParameter(MEMAILID));
			disputeSearchForm.setDisputeType(request.getParameter(DISPUTETYPE));

			int disputeDays = utilService.getDisputeDays();
			if(merchantId != null && !DisputeSearchUtil.mailValidation(merchantId, commonService)) {
				url = WALLET_PATH_PREFIX + CUSTOMER_DISPUTE_LIST_URL;
				request.setAttribute(URL_DISPUTE_LIST, url);
				model.put(ERROR_MESSAGE, context.getMessage("error.msg.notexist.merchant.emailid", null, locale));
				return CUSTOMER_DISPUTE_VIEW;
			}
			
			if(fromDate != null){	
				if(!DisputeSearchUtil.checkDate(fromDate, disputeDays)){
					url = WALLET_PATH_PREFIX + CUSTOMER_DISPUTE_LIST_URL;
					request.setAttribute(URL_DISPUTE_LIST, url);
					model.put(ERROR_MESSAGE, context.getMessage(NOTALLOW_DISPUTE_ERR_MSG, null, locale));
					return CUSTOMER_DISPUTE_VIEW;
				}
			} else {
				fromDate = CommonUtil.getBeforeDates(disputeDays);
			}
			
			if(toDate != null){
				if(!DisputeSearchUtil.checkDate(toDate, disputeDays)){
					url = WALLET_PATH_PREFIX + CUSTOMER_DISPUTE_LIST_URL;
					request.setAttribute(URL_DISPUTE_LIST, url);
					model.put(ERROR_MESSAGE, context.getMessage(NOTALLOW_DISPUTE_ERR_MSG, null, locale));
					return CUSTOMER_DISPUTE_VIEW;
				}
			} else{
				toDate = CommonUtil.getBeforeDates(0);
			}
			url = WALLET_PATH_PREFIX + "customerdispute/list?search=disputerecords&memailid=" 
					+ merchantId + "&fdate=" + fromDate + "&tdate=" + toDate + "&disputetype=" + disputeType;
		request.setAttribute(URL_DISPUTE_LIST, url);
		return CUSTOMER_DISPUTE_VIEW;		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<DisputeGridDto> disputeRecords( @Valid DisputeSearchForm disputeSearchForm, Map model, 
			Locale locale, HttpServletRequest request) {
		List<DisputeGridDto> displayList = null;
		String search = request.getParameter("search");
		try {
			if(search != null){
				// validate fields 
				int disputeDays = utilService.getDisputeDays();
				String fromd = (String)request.getParameter(FDATE);
				String tod = (String)request.getParameter(TDATE);
				if( fromd == null || NULL.equals(fromd)) {
					fromd = CommonUtil.getBeforeDates(disputeDays);
				}
				if(tod == null || NULL.equals(tod)) {
					tod = CommonUtil.getBeforeDates(0);
				}
				Date fromDate =DateConvertion.getFromDate( DateConvertion.stirngToDate(fromd));
				Date toDate = DateConvertion.getToDate(DateConvertion.stirngToDate(tod));
				
				Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
				if(languageId == null){
					languageId = MasterDataConstants.DEFAULT_LANGUAGE;
				}
				Authentication merchant = commonService.getAuthentication((String)request.getParameter(MEMAILID), GlobalLitterals.MERCHANT_USER_TYPE);
				Long merchantId = null;
				if(merchant != null){
					merchantId = merchant.getId();
				} else{
					merchantId = 0L;
				}
				Long customerId = (Long) request.getSession().getAttribute(AUTHENTICATION_ID);
				Integer limit = utilService.getReportLimit();
				if("disputerecords".equals(search)){
					
					Long disputeType = null;
					if( (request.getParameter(DISPUTETYPE) != null) && !"null".equals(request.getParameter(DISPUTETYPE)) ){
						disputeType = Long.valueOf(request.getParameter(DISPUTETYPE));
					} else{
						disputeType = 0L;
					}  
					displayList = disputeService.getDisputesForCustomer(limit, languageId, fromDate, toDate, merchantId, customerId, disputeType);
				} else if("transactions".equals(search)){
					displayList = disputeService.getCustomerTxnsForRaisedispute(limit, fromDate, toDate, merchantId, customerId, WalletTransactionTypes.P_TO_M);
				}
			}
		}  catch (Exception e) {
			LOGGER.error(e.getMessage() , e);
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error("disputeRecords", e);
		}
		
		if(displayList == null) {
			displayList = new ArrayList<DisputeGridDto>();
		}
		JqgridResponse<DisputeGridDto> response = new JqgridResponse<DisputeGridDto>();
		response.setRows(displayList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = displayList.size()/ps;
		if( displayList.size()/ps*ps != displayList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);

		return response;
	}
	
	@RequestMapping(value = "/transactions", method=RequestMethod.GET)
	public String raiseDispute(@Valid DisputeSearchForm disputeSearchForm, Map model, HttpServletRequest request, Locale locale) throws WalletException{
		LOGGER.debug( " raiseDispute" );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		AccountCloserDto dto = accountCloserService.getAccountCloserByUser((Long)session.getAttribute(AUTHENTICATION_ID));
		if(dto != null){
			model.put(CLOSE_ACCOUNT , true);
		}
		if(dto != null){
			session.setAttribute(ERROR_MESSAGE, ERROR_MSG_ACCOUNT_CLOSURE_DISPUTE_RAISE_UPDATE);
			return UIUtil.redirectPath(CUSTOMER_DISPUTE_PATH);
		}
		String merchantId = (String)request.getParameter(MEMAILID);
		String fromDate = (String)request.getParameter(FDATE);
		String toDate = (String)request.getParameter(TDATE);
		
		String url = null;
		int disputeDays = utilService.getDisputeDays();
		
		disputeSearchForm.setFromDate((String)request.getParameter(FDATE));
		disputeSearchForm.setToDate((String)request.getParameter(TDATE));
		disputeSearchForm.setMerchantEmailId((String)request.getParameter(MEMAILID));
		disputeSearchForm.setDisputeType(request.getParameter(DISPUTETYPE));
		
		if(merchantId != null && !DisputeSearchUtil.mailValidation(merchantId, commonService)){
			url = WALLET_PATH_PREFIX + CUSTOMER_DISPUTE_LIST_URL;
			request.setAttribute(URL_TXNS_LIST, url);
			request.setAttribute(IS_TXN_PAGE, TRUE_STR);
			model.put(ERROR_MESSAGE, context.getMessage("error.msg.notexist.merchant.emailid", null, locale));
			return CUSTOMER_DISPUTE_TRANSACTION;
		}
		
		if(fromDate != null ){	
			if(!DisputeSearchUtil.checkDate(fromDate, disputeDays)){
				url = WALLET_PATH_PREFIX + CUSTOMER_DISPUTE_LIST_URL;
				request.setAttribute(URL_TXNS_LIST, url);
				request.setAttribute(IS_TXN_PAGE, TRUE_STR);
				model.put(ERROR_MESSAGE, context.getMessage(NOTALLOW_DISPUTE_ERR_MSG, null, locale));
				return CUSTOMER_DISPUTE_TRANSACTION;
			}
		} else {
			fromDate = CommonUtil.getBeforeDates(disputeDays);
		}
		
		if(toDate != null){
			if(!DisputeSearchUtil.checkDate(toDate, disputeDays)){
				url = WALLET_PATH_PREFIX + CUSTOMER_DISPUTE_LIST_URL;
				request.setAttribute(URL_TXNS_LIST, url);
				request.setAttribute(IS_TXN_PAGE, TRUE_STR);
				model.put(ERROR_MESSAGE, context.getMessage(NOTALLOW_DISPUTE_ERR_MSG, null, locale));
				return CUSTOMER_DISPUTE_TRANSACTION;
			}
		} else{
			toDate = CommonUtil.getBeforeDates(0);
		}
		url = WALLET_PATH_PREFIX + CUSTOMER_DISPUTE_LIST_URL + "?search=transactions&memailid=" 
				+ merchantId + "&fdate=" + fromDate + "&tdate=" + toDate;
		request.setAttribute(URL_TXNS_LIST, url);
		request.setAttribute(IS_TXN_PAGE, TRUE_STR);
		return CUSTOMER_DISPUTE_TRANSACTION;		
	}
	
	@RequestMapping(value = "/refundorchargeback", method=RequestMethod.GET)
	public String txnsForRaiseDispute(@Valid DisputeForm disputeForm, Map model, HttpServletRequest request) throws WalletException{
		LOGGER.debug( " txnsForRaiseDispute page" );
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		String disputeType = (String)request.getParameter("type");
		Long txnId =0L;
		Long disputeId = 0L;
		if(request.getParameter(TXN_ID) != null){
			txnId = Long.valueOf(request.getParameter(TXN_ID));
		}
		if(request.getParameter(DISPUTE_ID)!=null){
			disputeId = Long.valueOf(request.getParameter(DISPUTE_ID));
		}
		Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
		if(languageId == null){
			languageId=1L;
		}
		AccountCloserDto dto = accountCloserService.getAccountCloserByUser((Long)session.getAttribute(AUTHENTICATION_ID));
		if(dto != null){
			session.setAttribute(ERROR_MESSAGE, ERROR_MSG_ACCOUNT_CLOSURE_DISPUTE_RAISE_UPDATE);
			return UIUtil.redirectPath(CUSTOMER_DISPUTE_PATH);
		}
		if(disputeType.equals(REFUND)){		
			WalletTransaction walletTransaction = transactionWalletService.getTransaction(txnId);
			DisputeUtil.convertWalletTransactioToDisputeForm(walletTransaction, disputeForm, request);
			Authentication payeeAuth = null;
			try{
				payeeAuth = commonService.getAuthentication(walletTransaction.getPayee());
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			disputeForm.setPayeeEmailId(payeeAuth != null ? payeeAuth.getEmailId() : EMPTY_STRING);
			disputeForm.setDistype(ReversalType.REFUND);
			request.setAttribute(DISPUTETYPE, REFUND);
		} else if(disputeType.equals(CHARGEBACK)){
			WalletTransaction walletTransaction = transactionWalletService.getTransaction(txnId);
			DisputeUtil.convertWalletTransactioToDisputeForm(walletTransaction, disputeForm, request);
			Authentication payeeAuth = null;
			try{
				payeeAuth = commonService.getAuthentication(walletTransaction.getPayee());
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			disputeForm.setPayeeEmailId(payeeAuth != null ? payeeAuth.getEmailId() : EMPTY_STRING);
			disputeForm.setDistype(ReversalType.CHARGE_BACK);
			request.setAttribute(DISPUTETYPE, CHARGEBACK);
		} else if(UPDATE.equals(disputeType)){
			//replace with disputeId
			DisputeDto disputeDto = disputeService.getDisputeById(disputeId, languageId);
			DisputeUtil.convertdisputeDtoToForm(disputeDto, disputeForm);
			request.setAttribute(DISPUTETYPE, UPDATE);
			return CUSTOMER_UPDATE_DISPUTE;
		}	
		return CUSTOMER_RAISE_DISPUTE;		
	}
	
	@RequestMapping(value = "/raisedispute", method=RequestMethod.POST)
	public String forRaiseDispute(@Valid DisputeForm disputeForm, BindingResult result, Map model, HttpServletRequest request, 
			Locale locale) throws WalletException {
		LOGGER.debug( " forRaiseDispute page" );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String requestedAmount = null;
		Long txnid = disputeForm.getTransactionId();
		Long disputeType = disputeForm.getDistype();
		if(disputeType.equals(ReversalType.REFUND)){
			requestedAmount = disputeForm.getRequestAmount();
		} else if(disputeType.equals(ReversalType.CHARGE_BACK)){
			requestedAmount = disputeForm.getTransactionAmount().toString();
		}
		Long requestedCurrency = disputeForm.getRequestedCurrencyId();
		String message = disputeForm.getMessage();
		//validations
		disputeForm.setUserType(CUSTOMER_USER_TYPE_ID);
		new DisputeValidator().validate(disputeForm, result);
		if(result.hasErrors()){
			if(disputeType.equals(ReversalType.REFUND)){
				model.put(DISPUTETYPE, REFUND);
			} else if(disputeType.equals(ReversalType.CHARGE_BACK)){
				model.put(DISPUTETYPE, CHARGEBACK);
			}
			return CUSTOMER_RAISE_DISPUTE;
		}
		
		if(disputeService.isDisputeExistForTxnId(txnid)){
			model.put(ERROR_MESSAGE, context.getMessage(DISPUTE_EXIST, null, locale));
			if(disputeType.equals(ReversalType.REFUND)){
				model.put(DISPUTETYPE, REFUND);
			} else if(disputeType.equals(ReversalType.CHARGE_BACK)){
				model.put(DISPUTETYPE, CHARGEBACK);
			}
			return CUSTOMER_RAISE_DISPUTE;
		}
		
		try{
			DisputeDto  riaseDto = new DisputeDto();
			riaseDto.setTransactionId(txnid);
			riaseDto.setType(disputeType);
			riaseDto.setRequestAmount(Double.parseDouble(requestedAmount));
			riaseDto.setRequestCurrency(requestedCurrency);
			riaseDto.setMessage(message);
			riaseDto.setCreator(new BigDecimal(GlobalLitterals.CUSTOMER_USER_TYPE_ID.toString()));
			/*For email template*/
			riaseDto.setPayeeemailid(disputeForm.getPayeeEmailId());
			riaseDto.setPayerName((String)session.getAttribute(NAME));
			String payeeName = null;
			try{
				payeeName = merchantService.getLegalName(disputeForm.getPayeeEmailId());
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			riaseDto.setPayeeName(payeeName != null ? payeeName : EMPTY_STRING);
			riaseDto.setCurrencyCode(disputeForm.getDispRequestedCurrency());
			if(disputeForm.getDistype().equals(ReversalType.CHARGE_BACK)){
				riaseDto.setDisputetype(context.getMessage(CHARGE_BACK_LBL, null, locale));
			} else{
				riaseDto.setDisputetype(context.getMessage(REFUND_LBL, null, locale));
			}
			DisputeDto  disputeDto = disputeService.createDispute(riaseDto);
			
			/*DisputeDto  disputeDto = disputeService.createDispute(txnid, disputeType, Double.parseDouble(requestedAmount), 
			requestedCurrency, message, GlobalLitterals.CUSTOMER_USER_TYPE_ID);*/
			
			//Audit Trail service
			auditTrailService.createAuditTrail(Long.parseLong(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_CUSTOMER_DISPUTE_CREATE, 
					AuditTrailConstrain.STATUS_CREATE, (String)session.getAttribute(USER_ID), GlobalLitterals.CUSTOMER_USER_TYPE);
			
			//************   FRAUD CHECK START - 14  ***************//
			if(disputeType.equals(ReversalType.CHARGE_BACK)){
				Long txnId= disputeDto.getTransactionId();
				WalletTransaction walletTransaction= transactionWalletService.getTransaction(txnId);
				Integer hours = utilService.getDisputeAllowedHours();
				Integer noOfdisputes = utilService.getNumberOfDisputes();
				Date fromDate = DateConvertion.getPastDateByHours(hours);
				Boolean flag = fraudCheckService.merchantThresholdCheck(walletTransaction.getPayee(), noOfdisputes, fromDate, new Date());
				commonService.updateChargeBackCheck(walletTransaction.getPayee(), flag);
			}
			 //************   FRAUD CHECK END   ***************//
			
			session.setAttribute(SUCCESS_MESSAGE, DISPUTE_CREATE_SUCCESS);
			return UIUtil.redirectPath(DISPUTE_CUSTOMER_PATH);
		} catch(Exception e){
			LOGGER.error(e.getMessage() , e );
			if(disputeType.equals(ReversalType.REFUND)){
				model.put(DISPUTETYPE, REFUND);
			} else if(disputeType.equals(ReversalType.CHARGE_BACK)){
				model.put(DISPUTETYPE, CHARGEBACK);
			}
			model.put(ERROR_MESSAGE, context.getMessage(DISPUTE_CREATE_FAILED, null, locale));
			return CUSTOMER_RAISE_DISPUTE;
		}
		
	}
	@RequestMapping(value = "/updatedispute", method=RequestMethod.POST)
	public String forUpdateDispute(@Valid DisputeForm disputeForm,BindingResult result, Map model, 
			HttpServletRequest request,Locale locale) throws WalletException{
		LOGGER.debug( " forUpdateDispute submit page" );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
		if(languageId == null){
			languageId = 1L;
		}
		disputeForm.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE_ID);
		new DisputeValidator().validate(disputeForm, result);
		DisputeDto disputeDto = null;
		try{
			disputeDto = disputeService.getDisputeById(disputeForm.getDisputeId(), languageId);
			if(result.hasErrors()){
				disputeForm.setDtoMessages(disputeDto.getDtomessages());
				return CUSTOMER_UPDATE_DISPUTE;
			}
			
			DisputeDto  updateDto = new DisputeDto();
			updateDto.setId(disputeForm.getDisputeId());
			updateDto.setStatus(disputeForm.getStatus());
			updateDto.setMessage(disputeForm.getMessage());
			updateDto.setCreator(new BigDecimal(GlobalLitterals.CUSTOMER_USER_TYPE_ID.toString()));
			updateDto.setRequestamount(Double.parseDouble(disputeForm.getRequestAmount()));
			updateDto.setRequestcurrency(disputeForm.getRequestCurrency());
			updateDto.setApprovedAmount(Double.parseDouble(disputeForm.getApprovedAmount()));
			updateDto.setApprovedcurrency(disputeForm.getApprovedCurrency());
			/*For email template*/
			updateDto.setPayeeemailid(disputeForm.getPayeeEmailId());
			updateDto.setPayeremailid(disputeForm.getPayerEmailId());
			updateDto.setPayerName((String)session.getAttribute(NAME));
			/*Dispute is updated by customer*/
			updateDto.setUpdatedby((String)session.getAttribute(NAME));
			String payeeName = null;
			try{
				payeeName = merchantService.getLegalName(disputeForm.getPayeeEmailId());
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			
			updateDto.setPayeeName(payeeName != null ? payeeName : EMPTY_STRING);
			updateDto.setCurrencyCode(disputeForm.getApprovedCurrency());
			if(disputeForm.getDistype().equals(ReversalType.CHARGE_BACK)){
				updateDto.setDisputetype(context.getMessage(CHARGE_BACK_LBL, null, locale));
			} else{
				updateDto.setDisputetype(context.getMessage(REFUND_LBL, null, locale));
			}
			disputeService.updateDispute(updateDto);
						
			//Audit Trail service
			auditTrailService.createAuditTrailForDispute(Long.parseLong(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_DISPUTE_UPDATE, 
					AuditTrailConstrain.STATUS_UPDATE, disputeDto.getPayeremailid(), GlobalLitterals.CUSTOMER_USER_TYPE, disputeDto.getStatus(), disputeDto.getApprovedamount(), disputeForm.getStatus(), Double.valueOf(disputeForm.getApprovedAmount()));
			
			session.setAttribute(SUCCESS_MESSAGE, DISPUTE_UPDATE_SUCCESS);
			return UIUtil.redirectPath(DISPUTE_CUSTOMER_PATH);
		} catch (Exception e) {
			disputeDto = disputeService.getDisputeById(disputeForm.getDisputeId(), languageId);
			LOGGER.error(e.getMessage(), e);
			disputeForm.setDtoMessages(disputeDto.getDtomessages());
			model.put(ERROR_MESSAGE, context.getMessage(DISPUTE_UPDATE_FAILED, null, locale));
		    return CUSTOMER_UPDATE_DISPUTE;
		}
	}
}