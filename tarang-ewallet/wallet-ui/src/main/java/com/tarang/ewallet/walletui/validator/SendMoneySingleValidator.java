package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.SendMoneyForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class SendMoneySingleValidator implements Validator, GlobalLitterals, Common {
	
	public static final String CURRENCY_VAR = "requestedCurrency";
	public static final String MESSAGE_VAR = "message";
	public static final String DESTINATION_TYPE_VAR = "destinationType";
	public static final String FROMDATE_VAR = "fromDate";
	public static final String TODATE_VAR = "toDate";
	public static final String FREQUENCY_VAR = "frequency";
	public static final String OCCURENCES_VAR = "totalOccurences";
	
	public static final String CURRENCY_REQUIRED = "currency.required.errmsg";
	
	public static final String FROMDATE_FORMAT_ERROR = "valid.date.errmsg";
	
	public static final String TODATE_FORMAT_ERROR = "valid.date.errmsg";
	public static final String FROMDATE_ERROR = "fromdate.greater.today.errmsg";
	public static final String TODATE_ERROR = "todate.lesser.fromdate.errmsg";
	
	public static final String FREQUENCY_REQUIRED = "frequency.required.errmsg";
	
	public static final String OCCURENCES_ERROR = "occurences.required.errmsg";
	
	public static final String  EMAIL_CUSTOMER_NOTREGISTERED_ERROR  = "sendmoney.emailid.notregistered.errmsg";
	public static final String EMAIL_CUSTOMER_REGISTERED_ERROR = "sendmoney.emailid.registered.errmsg";
	public static final String EMAIL_MERCHANT_REGISTERED_ERROR = "sendmoney.merchant.emailid.notregisterd.errmsg";
	
	public static final Integer USER_JOBNAME_MIN_LENGTH = 5;
	public static final Integer USER_JOBNAME_MAX_LENGTH = 30;
	public static final String USER_JOBNAME_REQUIRED = "user.jobname.required.errmsg";
	public static final String USER_JOBNAME_MIN_LENGTH_RANGE = "user.jobname.min.length.errmsg";
	public static final String USER_JOBNAME_MAX_LENGTH_RANGE = "user.jobname.max.length.errmsg";
	public static final String USER_JOBNAME_MATCHER = "user.jobname.contains.errmsg";
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		SendMoneyForm sendMoneyForm = (SendMoneyForm)target;
		sendMoneyForm.setEmailId(sendMoneyForm.getEmailId().trim());
		CommonValidator.emailValidation(errors, sendMoneyForm.getEmailId(), EMAIL_VAR );
		
		sendMoneyForm.setRequestedAmount(sendMoneyForm.getRequestedAmount().trim());
		if(sendMoneyForm.getRequestedAmount().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, AMOUNT_VAR, AMOUNT_REQUIRED );
		} else {
			CommonValidator.realNumberValidator(errors, sendMoneyForm.getRequestedAmount(), 
					AMOUNT_VAR, SEND_MONEY_MIN_LIMIT, AMOUNT_ERROR_FORMAT, SEND_MONEY_MAX_LIMIT, 
					SEND_MONEY_MAX_ERR_MSG, AMOUNT_ERROR_FORMAT);
		}	
		
		if(ZERO_LONG.equals( sendMoneyForm.getRequestedCurrency()) ){
			CommonValidator.mandatoryValidator(errors, CURRENCY_VAR, CURRENCY_REQUIRED );
		}
		
		if(!sendMoneyForm.getMessage().equals(EMPTY_STRING)){
			CommonValidator.messageValidator(errors, sendMoneyForm.getMessage(), MESSAGE_VAR,
					MESSAGE_MIN_LENGTH, MESSAGE_MIN_LENGTH_RANGE,
					MESSAGE_MAX_LENGTH, MESSAGE_MAX_LENGTH_RANGE,
					MESSAGE_MATCHER);
		}
		
		if( ZERO_LONG.equals(sendMoneyForm.getDestinationType()) ){
			CommonValidator.mandatoryValidator(errors, DESTINATION_TYPE_VAR, DESTINATION_TYPE_REQUIRED);
		}
		
		if(sendMoneyForm.getRecurring()){
			if(sendMoneyForm.getUserJobName().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, USER_JOBNAME_VAR, USER_JOBNAME_REQUIRED);
			} else{
				CommonValidator.stringValidator(errors, sendMoneyForm.getUserJobName(), USER_JOBNAME_VAR, USER_JOBNAME_MIN_LENGTH, USER_JOBNAME_MIN_LENGTH_RANGE,
						USER_JOBNAME_MAX_LENGTH, USER_JOBNAME_MAX_LENGTH_RANGE,
						NAME_EXPRESSION, USER_JOBNAME_MATCHER);
			}		
			if(sendMoneyForm.getFromDate().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors,FROMDATE_VAR, FROMDATE_REQUIRED);
			} else if(!CommonValidator.dateValidation(DATE_FORMAT, sendMoneyForm.getFromDate())){
				errors.rejectValue(FROMDATE_VAR,FROMDATE_FORMAT_ERROR);
			}
			
			if(sendMoneyForm.getToDate().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, TODATE_VAR, TODATE_REQUIRED);
			} else if(!CommonValidator.dateValidation(DATE_FORMAT, sendMoneyForm.getToDate())){
				errors.rejectValue(TODATE_VAR,TODATE_FORMAT_ERROR);
			}
		   
			CommonValidator.dateComparision(errors, sendMoneyForm.getFromDate(), sendMoneyForm.getToDate(), 
					FROMDATE_VAR, TODATE_VAR, FROMDATE_ERROR, TODATE_ERROR);
		   
			if( ZERO_LONG.equals(sendMoneyForm.getFrequency()) ){
				CommonValidator.mandatoryValidator(errors, FREQUENCY_VAR, FREQUENCY_REQUIRED);
			}
			if(sendMoneyForm.getTotalOccurences() != null && sendMoneyForm.getTotalOccurences() < 1){
				errors.rejectValue(OCCURENCES_VAR, OCCURENCES_ERROR);
		   } 
		}
	}
	
}