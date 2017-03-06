package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.AccountCloserForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;

public class AccountCloserValidator implements Validator, GlobalLitterals, Common {
	
	public static final String ACCOUNTCLOSE_MESSAGE_VAR = "message";
	public static final String REPLY_MESSAGE_VAR = "responseMessage";

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		AccountCloserForm accountCloserForm = (AccountCloserForm)target;
	
	if(accountCloserForm.getMessage().equals(EMPTY_STRING)){
		CommonValidator.mandatoryValidator(errors, ACCOUNTCLOSE_MESSAGE_VAR, MESSAGE_REQUIRED);
	} else{
		CommonValidator.messageValidator(errors, accountCloserForm.getMessage(), ACCOUNTCLOSE_MESSAGE_VAR,
				MESSAGE_MIN_LENGTH, MESSAGE_MIN_LENGTH_RANGE,
				MESSAGE_MAX_LENGTH, MESSAGE_MAX_LENGTH_RANGE,
				MESSAGE_MATCHER);
		}
	}
}