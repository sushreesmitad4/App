package com.tarang.ewallet.walletui.validator;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.transaction.util.DestinationTypes;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.controller.constants.SendMoney;
import com.tarang.ewallet.walletui.form.MerchantForm;
import com.tarang.ewallet.walletui.form.SendMoneyMultipleForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class SendMoneyMultipleValidator implements Validator, GlobalLitterals, AttributeValueConstants, Common {
	
	private static final Logger LOGGER = Logger.getLogger(SendMoneyMultipleValidator.class);
	
	public static final String COMMON1= "(\\d{0,9}.\\d{1,2})|(\\d{0,2})";
	
	private static final String EMAIL_PREFIX = "emailId[";
	private static final String AMOUNT_PREFIX = "amount[";
	private static final String CURRENCY_PREFIX = "currency[";
	private static final String MSG_PREFIX = "message[";
	private static final String DEST_TYPE_PREFIX = "destinationType[";
	private static final String SUFIX = "]";
	
	private CommonService commonService = null;
	
	public SendMoneyMultipleValidator(CommonService commonService) {
		this.commonService = commonService;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MerchantForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		SendMoneyMultipleForm form = (SendMoneyMultipleForm) target;
		
		String emailId = null;
		String amount = null;
		Long currency = null;
		String message = null;
		Long destinationType = null;
		int slabs = form.getEmailId().length;
		for(int i = 0; i < slabs; i++){
			String[] emailArray = form.getEmailId();
			emailId = emailArray[i];
			CommonValidator.emailValidation(errors, emailId, EMAIL_PREFIX + i + SUFIX);
			
			String[] amountArray = form.getAmount();
			amount = amountArray[i];
			if(amount.equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, AMOUNT_PREFIX + i + SUFIX, Common.AMOUNT_REQUIRED);
			}else{
				CommonValidator.realNumberValidator(errors, amount, AMOUNT_PREFIX + i + SUFIX,
						SEND_MONEY_MIN_LIMIT, AMOUNT_ERROR_FORMAT, SEND_MONEY_MAX_LIMIT, 
						SEND_MONEY_MAX_ERR_MSG, AMOUNT_ERROR_FORMAT);
			}
			
			Long[] currencyArray = form.getCurrency();
			currency = currencyArray[i];
			CommonValidator.mandatoryValidator(errors, currency, CURRENCY_PREFIX + i + SUFIX, MerchantValidator.MERCHANT_CURRENCY_REQUIRED);
			
			String[] messageArray = form.getMessage();
			message = messageArray[i];
		
			if( !message.isEmpty() ){
				CommonValidator.messageValidator(errors,message, MSG_PREFIX + i + SUFIX,
					SendMoneySingleValidator.MESSAGE_MIN_LENGTH,SendMoneySingleValidator.MESSAGE_MIN_LENGTH_RANGE,
					SendMoneySingleValidator.MESSAGE_MAX_LENGTH,SendMoneySingleValidator.MESSAGE_MAX_LENGTH_RANGE,
					SendMoneySingleValidator.MESSAGE_MATCHER);
			}
			
			
			Long[] destinationTypeArray = form.getDestinationType();
			destinationType = destinationTypeArray[i];
			CommonValidator.mandatoryValidator(errors, destinationType, DEST_TYPE_PREFIX + i + SUFIX, Common.DESTINATION_TYPE_REQUIRED);
		
		if( !errors.hasErrors() && !emailId.equals(EMPTY_STRING) && destinationType != 0L){
			String receiverMailId = emailId;
			   Long userType = destinationType;
			   Authentication authentication = new Authentication();
			   if(DestinationTypes.REGISTERED_PERSON.equals(userType)){
				    try{
				    	authentication = (Authentication)commonService.getAuthentication(receiverMailId, GlobalLitterals.CUSTOMER_USER_TYPE);
				    	if(authentication == null){
				    		errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.EMAIL_CUSTOMER_NOTREGISTERED_ERROR);
				    		LOGGER.error( SendMoney.EMAIL_CUSTOMER_NOTREGISTERED_ERROR);
				    	} else if(UserStatusConstants.DELETED.equals(authentication.getStatus())){
				    		errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.CUSTOMER_DELETED_ACCOUNT);
				    	} else if(!UserStatusConstants.APPROVED.equals(authentication.getStatus())){
					    	errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.CUSTOMER_NOT_APPROVED);
						} else if(!authentication.isActive()){
					    	errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.CUSTOMER_NOT_ACTIVE);
						}
				    } catch(Exception e){
				       LOGGER.error(e.getMessage(), e);
					   errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.EMAIL_CUSTOMER_NOTREGISTERED_ERROR); 
					   LOGGER.error( SendMoney.EMAIL_CUSTOMER_NOTREGISTERED_ERROR);
				    }
			   }
			   if(DestinationTypes.NON_REGISTERED_PERSON.equals(userType)){
				   try{
					   Authentication auth = commonService.getAuthentication(receiverMailId, GlobalLitterals.CUSTOMER_USER_TYPE);
					   if(auth != null ){
						   errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.EMAIL_REGISTERED_ERROR);
						   LOGGER.error( SendMoney.EMAIL_CUSTOMER_REGISTERED_ERROR);
					   }
				   } catch(Exception e){
					   LOGGER.error(e.getMessage() , e );
					   errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.EMAIL_REGISTERED_ERROR);
				   }
			   }
			   if(DestinationTypes.MERCHANT.equals(userType)){
				   try{
					   authentication = (Authentication)commonService.getAuthentication(receiverMailId, GlobalLitterals.MERCHANT_USER_TYPE);
					   if(authentication == null){
						   errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.EMAIL_MERCHANT_NOTREGISTERED_ERROR); 
						   LOGGER.error( SendMoney.EMAIL_MERCHANT_NOTREGISTERED_ERROR);
					   } else if(UserStatusConstants.DELETED.equals(authentication.getStatus())){
				    		errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.MERCHANT_DELETED_ACCOUNT);
				    	} else if(!UserStatusConstants.APPROVED.equals(authentication.getStatus())){
				    		errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.MERCHANT_NOT_APPROVED);
				    	} else if(!authentication.isActive()){
					    	errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.MERCHANT_NOT_ACTIVE);
						}
				   } catch(Exception e){
					   LOGGER.error(e.getMessage() , e);
					   errors.rejectValue(EMAIL_PREFIX + i + SUFIX, SendMoney.EMAIL_MERCHANT_NOTREGISTERED_ERROR); 
					   LOGGER.error( SendMoney.EMAIL_MERCHANT_NOTREGISTERED_ERROR);
				   }  
			   }    
			}
		}
	}

}