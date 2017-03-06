package com.tarang.ewallet.walletui.validator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.walletui.form.PereferencesForm;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class PreferencesValidator implements Validator {
	
	public static final String CURRENCY_VAR = "currency";
	public static final String LANGUAGE_VAR = "language";
	
	public static final String CURRENCY_REQUIRED = "currency.required.errmsg";
	public static final String LANGUAGE_REQUIRED = "language.required.errmsg";

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

		PereferencesForm pereferencesForm = (PereferencesForm)target;
		
		/**
		 * Commented Currency In Preferences
		 */
		/*
		if(pereferencesForm.getCurrency() !=null && pereferencesForm.getCurrency() == 0L){
			CommonValidator.mandatoryValidator(errors, CURRENCY_VAR,CURRENCY_REQUIRED);
		}
		*/
		if(pereferencesForm.getLanguage() == 0L){
			CommonValidator.mandatoryValidator(errors, LANGUAGE_VAR,LANGUAGE_REQUIRED);
		}	
	}
}