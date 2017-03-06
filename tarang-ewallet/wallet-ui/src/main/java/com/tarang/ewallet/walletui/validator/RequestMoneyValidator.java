/**
 * 
 */
package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.RequestMoneyForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;

/**
 * @author vasanthar
 *
 */
public class RequestMoneyValidator implements Validator, GlobalLitterals, Common {
	
	public static final String EMAIL_VAR = "emailId";
	public static final String PHONE_CODE_VAR = "phoneCode";
	public static final String PHONE_NUM_VAR = "phoneNumber";
	public static final String CURRENCY_VAR = "currency";
	public static final String AMOUNT_VAR = "amount";
	public static final String REQ_MESSAGE_VAR = "requesterMsg";
	public static final String RES_MESSAGE_VAR = "responserMsg";
	
	public static final String CURRENCY_REQUIRED = "currency.required.errmsg"; 
	public static final String MESSAGE_PATTERN_MATCHER = "receivemoney.message.errmsg.contains";
	public static final String MESSAGE_LENGTH_MSG = "receivemoney.message.errmsg.length";
	public static final String AMOUNT_EXPRESSION = "receivemoney.amount.errmsg.expression";
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RequestMoneyForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		RequestMoneyForm requestMoneyForm = (RequestMoneyForm)target;
		
		requestMoneyForm.setEmailId(requestMoneyForm.getEmailId().trim());
		CommonValidator.emailValidation(errors, requestMoneyForm.getEmailId(), EMAIL_VAR);
	
		if (requestMoneyForm.getCurrency() == 0) {
			CommonValidator.mandatoryValidator(errors, CURRENCY_VAR, CURRENCY_REQUIRED);
		}
		
		if ("".equals(requestMoneyForm.getAmount().trim())) {
			CommonValidator.mandatoryValidator(errors, AMOUNT_VAR, AMOUNT_REQUIRED);
		} else{
			CommonValidator.currencyValidator(errors, requestMoneyForm.getAmount().trim(), AMOUNT_VAR, AMOUNT_EXPRESSION, null);
		}
		
		if("submit".equals(requestMoneyForm.getMode())){
			if(!"".equals(requestMoneyForm.getPhoneNumber().trim()) || !"".equals(requestMoneyForm.getPhoneCode().trim())){
				requestMoneyForm.setPhoneCode(requestMoneyForm.getPhoneCode().trim());
				CommonValidator.phoneCodeValidation(errors, requestMoneyForm.getPhoneCode(), PHONE_CODE_VAR, Boolean.TRUE);
				
				requestMoneyForm.setPhoneNumber(requestMoneyForm.getPhoneNumber().trim());
				CommonValidator.phoneNumberValidator(errors, requestMoneyForm.getPhoneNumber(), PHONE_NUM_VAR, Boolean.TRUE);
			}
			
			if(!"".equals(requestMoneyForm.getRequesterMsg().trim())){
				requestMoneyForm.setRequesterMsg(requestMoneyForm.getRequesterMsg().trim());
				CommonValidator.messageValidator(errors, requestMoneyForm.getRequesterMsg(), 
						REQ_MESSAGE_VAR, MESSAGE_MIN_LENGTH, MESSAGE_MIN_LENGTH_RANGE,
						MESSAGE_MAX_LENGTH, MESSAGE_MAX_LENGTH_RANGE, MESSAGE_MATCHER);
			}	
		} else if("accept".equals(requestMoneyForm.getMode()) || "reject".equals(requestMoneyForm.getMode())){
			if("".equals(requestMoneyForm.getResponserMsg().trim())){
				CommonValidator.mandatoryValidator(errors, RES_MESSAGE_VAR, MESSAGE_REQUIRED);
			} else{
				CommonValidator.messageValidator(errors, requestMoneyForm.getResponserMsg().trim(), 
						RES_MESSAGE_VAR, Common.MESSAGE_MIN_LENGTH, MESSAGE_MIN_LENGTH_RANGE,
						Common.MESSAGE_MAX_LENGTH, MESSAGE_MAX_LENGTH_RANGE, MESSAGE_MATCHER);
			}	
		}		
	}
}
