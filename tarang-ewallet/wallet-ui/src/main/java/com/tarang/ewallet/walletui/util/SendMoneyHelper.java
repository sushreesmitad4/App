/**
 * 
 */
package com.tarang.ewallet.walletui.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.SelfTransferDto;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.transaction.util.CurrencyConversion;
import com.tarang.ewallet.transaction.util.DestinationTypes;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.controller.constants.SendMoney;
import com.tarang.ewallet.walletui.form.SelfTransferForm;
import com.tarang.ewallet.walletui.form.SendMoneyForm;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;
import com.tarang.mwallet.rest.services.util.ServerUtility;


/**
 * @author  : prasadj
 * @date    : Jan 29, 2013
 * @time    : 2:34:06 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SendMoneyHelper implements SendMoney, AttributeConstants, GlobalLitterals, AttributeValueConstants {

	private static final Logger LOGGER = Logger.getLogger(SendMoneyHelper.class);

	public static void checkEnoughMoney(Map model, SendMoneyDto sendMoneyDto, CommonService commonService, CustomerService customerService, 
			FeeMgmtService feeMgmtService) throws WalletException {
		UserWallet userWallet = commonService.getUserWallet(sendMoneyDto.getSenderAuthId(), sendMoneyDto.getRequestedCurrency());
		Boolean negativeBalance = false;
		Double deductions = 0.0;
		Double reqAmount = sendMoneyDto.getRequestedAmount();
		if(userWallet != null){
			deductions = feeMgmtService.calcuateDeductions(reqAmount, sendMoneyDto.getCountryId(), 
					sendMoneyDto.getRequestedCurrency(), sendMoneyDto.getTransactionType(), Boolean.FALSE);
		}
		if(userWallet == null || userWallet.getAmount() < (reqAmount + deductions) ){
			negativeBalance = true;
		}
		if(negativeBalance){
			// has to update
			CustomerDto customerDto = customerService.getRegisteredMember(sendMoneyDto.getEmailId(), "ut");
			if(!customerDto.getDefaultCurrency().equals(sendMoneyDto.getRequestedCurrency())){
				Double convertionRate = CurrencyConversion.getInstance().getConvertionFactor(customerDto.getDefaultCurrency(), 
						sendMoneyDto.getRequestedCurrency());
				reqAmount = sendMoneyDto.getRequestedAmount()/convertionRate;
				deductions = feeMgmtService.calcuateDeductions(reqAmount, sendMoneyDto.getCountryId(), 
						customerDto.getDefaultCurrency(), sendMoneyDto.getTransactionType(), Boolean.TRUE);
				userWallet = commonService.getUserWallet(sendMoneyDto.getSenderAuthId(), customerDto.getDefaultCurrency());
				if(deductions + reqAmount > userWallet.getAmount()){
					negativeBalance = false;
				}
			}
		}
		if(negativeBalance){
			throw new WalletException(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE);
		}
	    model.put("totalAmountFeeTax", reqAmount + deductions);
	}
	
	public static void getRegisteredOrNotRegisteredOrMerchant(SendMoneyForm sendMoneyForm, BindingResult errors, CommonService commonService) {
		if(!sendMoneyForm.getEmailId().equals(EMPTY_STRING) && sendMoneyForm.getDestinationType()!=0L ){
			String receiverMailId = sendMoneyForm.getEmailId();
		   Long userType = sendMoneyForm.getDestinationType();
		   Authentication authentication = new Authentication();
		   if(DestinationTypes.REGISTERED_PERSON.equals(userType)){
			    try{
			    	authentication = (Authentication)commonService.getAuthentication(receiverMailId, GlobalLitterals.CUSTOMER_USER_TYPE);
			    	if(authentication == null){
			    		errors.rejectValue(EMAIL_ID, EMAIL_CUSTOMER_NOTREGISTERED_ERROR);
			    		LOGGER.error( EMAIL_CUSTOMER_NOTREGISTERED_ERROR);
			    	}
			    	/*else if(UserStatusConstants.DELETED.equals(authentication.getStatus())){
			    		errors.rejectValue(EMAIL_ID, CUSTOMER_DELETED_ACCOUNT);
			    	}
			    	else if(UserStatusConstants.LOCKED.equals(authentication.getStatus())){
			    		errors.rejectValue(EMAIL_ID, CUSTOMER_ACCOUNT_LOCKED);
			    	}
			    	else if(!UserStatusConstants.APPROVED.equals(authentication.getStatus())){
			    		errors.rejectValue(EMAIL_ID, CUSTOMER_NOT_APPROVED);
			    	}
			    	else if(!authentication.isActive()){
				    	errors.rejectValue(EMAIL_ID, CUSTOMER_NOT_ACTIVE);
					}*/
			    } catch(Exception e){
			       LOGGER.error(e.getMessage() , e );
				   errors.rejectValue(EMAIL_ID, EMAIL_CUSTOMER_NOTREGISTERED_ERROR); 
				   LOGGER.error( EMAIL_CUSTOMER_NOTREGISTERED_ERROR);
			    }
		   }
		   if(DestinationTypes.NON_REGISTERED_PERSON.equals(userType)){
			   try{
				   Authentication auth = commonService.getAuthentication(receiverMailId, GlobalLitterals.CUSTOMER_USER_TYPE);
				   if(auth != null ){
					   errors.rejectValue(EMAIL_ID, EMAIL_REGISTERED_ERROR);
					   LOGGER.error( EMAIL_CUSTOMER_REGISTERED_ERROR);
				   }
			   } catch(Exception e){
				   LOGGER.error(e.getMessage() , e);
			   }
		   }
		   if(DestinationTypes.MERCHANT.equals(userType)){
			   try{
				   authentication = (Authentication)commonService.getAuthentication(receiverMailId, GlobalLitterals.MERCHANT_USER_TYPE);
				   if(authentication == null){
					   errors.rejectValue(EMAIL_ID, EMAIL_MERCHANT_NOTREGISTERED_ERROR); 
					   LOGGER.error( EMAIL_MERCHANT_NOTREGISTERED_ERROR);
				   }
				  /* else if(UserStatusConstants.DELETED.equals(authentication.getStatus())){
			    		errors.rejectValue(EMAIL_ID, MERCHANT_DELETED_ACCOUNT);
				   }
				   else if(!UserStatusConstants.APPROVED.equals(authentication.getStatus())){
			    		errors.rejectValue(EMAIL_ID, MERCHANT_NOT_APPROVED);
				   }
				   else if(!authentication.isActive()){
			    		errors.rejectValue(EMAIL_ID, MERCHANT_NOT_ACTIVE);
				   }*/
			   } catch(Exception e){
				   LOGGER.error(e.getMessage() , e);
				   errors.rejectValue(EMAIL_ID, EMAIL_MERCHANT_NOTREGISTERED_ERROR); 
				   LOGGER.error(EMAIL_MERCHANT_NOTREGISTERED_ERROR);
			   }  
		   } 
		}
	}
	
	public static SelfTransferDto convertSelfTransferFormToDto(HttpServletRequest request, SelfTransferForm selfTransferForm,
			Long authId, String simNumber,String imeiNumber, TypeOfRequest typeOfRequest){
		
		Long typeOfReqWeb = MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
		        (Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue());
		
		Long typeOfReqMobile = MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
		        (Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.MOBILE.getValue());
		
		SelfTransferDto selfTransferDto = new SelfTransferDto();		
		if(TypeOfRequest.WEB.equals(typeOfRequest)){
			selfTransferDto.setIpAddress(UIUtil.getClientIpAddr(request));
			selfTransferDto.setTypeOfRequest(typeOfReqWeb);
		} else {
			selfTransferDto.setSimNumber(simNumber);
			selfTransferDto.setImeiNumber(imeiNumber);
			selfTransferDto.setTypeOfRequest(typeOfReqMobile);
		}
		
		selfTransferDto.setAuthId(authId);
		selfTransferDto.setRequestedAmount(selfTransferForm.getRequestedAmount());
		selfTransferDto.setActualAmount(selfTransferForm.getActualAmount());
		selfTransferDto.setRequestedCurrency(selfTransferForm.getTowallet());
		selfTransferDto.setActualCurrency(selfTransferForm.getFromWallet());
		selfTransferDto.setTransactionType(selfTransferForm.getTransactionType());
		return selfTransferDto;
	}
	
	public static String getAllWalletBalances(Map<Long, UserWallet> userWallets) {
		String allBalances = "";  
		Set qset = userWallets.entrySet();
		Iterator i = qset.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			UserWallet userWallet=(UserWallet)me.getValue();
			String id = me.getKey().toString();
			String value = UIUtil.getConvertedAmountInString(userWallet.getAmount());
			allBalances = allBalances + id + ":" + value + ",";
		}
		return allBalances;
	}
	
	/**
	 * This method will check the requested email is register user or not.
	 * @param sendMoneyForm
	 * @param commonService
	 * @return
	 */
	public static Response getRegisteredOrNotRegisteredOrMerchant(HttpServletRequest request,
			SendMoneyForm sendMoneyForm, CommonService commonService) {
		if (!sendMoneyForm.getEmailId().equals(EMPTY_STRING) && sendMoneyForm.getDestinationType() != 0L 
				&& sendMoneyForm.getDestinationType() < 5L && sendMoneyForm.getDestinationType() != 3L) {
			String receiverMailId = sendMoneyForm.getEmailId();
			Long destinationType = sendMoneyForm.getDestinationType();
			Authentication authentication = new Authentication();
			if (DestinationTypes.REGISTERED_PERSON.equals(destinationType)) {
				try {
					authentication = (Authentication) commonService.getAuthentication(receiverMailId, GlobalLitterals.CUSTOMER_USER_TYPE);
					if (authentication == null) {
						return ServerUtility.papulateErrorCode(request,
								ServerProcessorStatus.EMAIL_CUSTOMER_NOTREGISTERED_ERROR.getValue());
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage() , e);
					return ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.EMAIL_CUSTOMER_NOTREGISTERED_ERROR.getValue());
				}
			}
			if (DestinationTypes.NON_REGISTERED_PERSON.equals(destinationType)) {
				try {
					Authentication auth = commonService.getAuthentication(receiverMailId, GlobalLitterals.CUSTOMER_USER_TYPE);
					if (auth != null) {
						return ServerUtility.papulateErrorCode(request,
								ServerProcessorStatus.EMAIL_REGISTERED_ERROR.getValue());
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage() , e);
					return ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.EMAIL_REGISTERED_ERROR.getValue());
				}
			}
			if (DestinationTypes.MERCHANT.equals(destinationType)) {
				try {
					authentication = (Authentication) commonService.getAuthentication(receiverMailId, GlobalLitterals.MERCHANT_USER_TYPE);
					if (authentication == null) {
						return ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.EMAIL_MERCHANT_NOTREGISTERED_ERROR.getValue());
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage() , e );
					return ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.EMAIL_MERCHANT_NOTREGISTERED_ERROR.getValue());
				}
			 }
			} else {
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.NOT_A_VALID_DESTINATION_TYPE.getValue());
		}
		return null;
	}
	
}