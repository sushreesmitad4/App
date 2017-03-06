package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.LoginUserForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;
import com.tarang.ewallet.webservice.validation.RestCustomValidation;


public class LoginValidator implements Validator, GlobalLitterals {

	// variable names
	public static final String EMAIL_VAR = "email";
	public static final String PASSWORD_VAR = "password";

	@Override
	public boolean supports(Class<?> form) {
		return LoginUserForm.class.equals(form);

	}

	@Override
	public void validate(Object target, Errors errors) {
		
		LoginUserForm form = (LoginUserForm) target;
		String user = getUserType(form.getUserType());
		form.setEmail(form.getEmail().trim());
		if(GlobalLitterals.CUSTOMER_USER_TYPE.equalsIgnoreCase(user)){
			CommonValidator.phoneNumberValidator(errors, form.getEmail(), EMAIL_VAR, Boolean.TRUE);
			form.setPassword(form.getPassword().trim());
			/*if (form.getPassword().equals(EMPTY_STRING)) {
				CommonValidator.mandatoryValidator(errors, PASSWORD_VAR, Common.PASSWORD_REQUIRED);
			}*/
			CommonValidator.mpinValidator(errors,form.getPassword(), PASSWORD_VAR ,null,Common.M_PIN_MAX_LENGHT);
		}else{
			CommonValidator.emailValidation(errors, form.getEmail(), EMAIL_VAR);
			
			/*Password does not required password policy, 
			 * because it has to allow random generated passwords*/
			form.setPassword(form.getPassword().trim());
			if (form.getPassword().equals(EMPTY_STRING)) {
				CommonValidator.mandatoryValidator(errors, PASSWORD_VAR, Common.PASSWORD_REQUIRED);
			}
			/*else if (form.getPassword().length() < Common.PASSWORD_MIN_LENGTH ) {
				CommonValidator.mandatoryValidator(errors, PASSWORD_VAR, Common.PASSWORD_MIN_LENGTH_MSG);
			}
			else if (form.getPassword().length() > Common.PASSWORD_MAX_LENGTH ) {
				CommonValidator.mandatoryValidator(errors, PASSWORD_VAR, Common.PASSWORD_MAX_LENGTH_MSG);
			}*/ 
		}
		
	}
	
	private String getUserType(String ut) {
		
		String usertyps = ut;
		if (usertyps != null && !usertyps.equals(EMPTY_STRING)) {
			usertyps = usertyps.trim();
		}
		return "Customer".equalsIgnoreCase(usertyps) ? GlobalLitterals.CUSTOMER_USER_TYPE : GlobalLitterals.MERCHANT_USER_TYPE;
	}

}