package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.AccountCloserFormView;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;

public class AccountCloserMgmtValidator implements Validator, GlobalLitterals, Common{
	
	public static final String ACCOUNT_CLOSER_STATUS_VAR = "accCloserStatus";
	public static final String ACCOUNT_CLOSER_STATUS_REQUIRED = "account.closer.status.required";
	
	public static final String MESSAGE_VAR = "message";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return AccountCloserFormView.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AccountCloserFormView form = (AccountCloserFormView) target;
		String message=form.getMessage().trim();
		if(!message.equals(EMPTY_STRING)){
			CommonValidator.messageValidator(errors, message, MESSAGE_VAR,
					MESSAGE_MIN_LENGTH, MESSAGE_MIN_LENGTH_RANGE,
					MESSAGE_MAX_LENGTH, MESSAGE_MAX_LENGTH_RANGE,
					MESSAGE_MATCHER);
		} else{
			CommonValidator.mandatoryValidator(errors, MESSAGE_VAR, MESSAGE_REQUIRED);
		}
		form.setMessage(message);
		
		Long status = form.getAccCloserStatus();
		if(status == null){
		CommonValidator.mandatoryValidator(errors, ACCOUNT_CLOSER_STATUS_VAR, ACCOUNT_CLOSER_STATUS_REQUIRED);
		}
	}

}