package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.VelocityAndThresholdForm;
import com.tarang.ewallet.walletui.form.WalletThresholdForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


/***
 * @author vasanthar
 *
 */
public class VelocityAndThresholdValidator implements Validator, GlobalLitterals, Common {

	public static final String USERTYPE_VAR = "usertype";
	public static final String COUNTRY_VAR = "country";
	public static final String CURRENCY_VAR = "currency";
	public static final String TRANSACTIONTYPE_VAR = "transactiontype";
	public static final String MINMUM_AMOUNT_VAR = "minimumamount";
	public static final String MAXIMUM_AMOUNT_VAR = "maximumamount";
	public static final String COUNTRY_REQUIRED = "country.required.errmsg";
	public static final String CURRENCY_REQUIRED = "currency.required.errmsg";	
	public static final String TRANSACTIONTYPE_REQUIRED = "transaction.type.required.errmsg";
	
	public static final String COMMON= "\\d{0,9}(\\.\\d{1,2})?";

	
	public boolean supports(Class<?> clazz) {
		return VelocityAndThresholdForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		VelocityAndThresholdForm velocityform = (VelocityAndThresholdForm)target;
		
		if (velocityform.getUsertype() == 0L) {
			CommonValidator.mandatoryValidator(errors, USERTYPE_VAR, Common.USER_TYPE_REQUIRED);
		}
		
		if (velocityform.getCountry() == 0L) {
			CommonValidator.mandatoryValidator(errors, COUNTRY_VAR, COUNTRY_REQUIRED);
		}
		
		if (velocityform.getCurrency() == 0L) {
			CommonValidator.mandatoryValidator(errors, CURRENCY_VAR, CURRENCY_REQUIRED);
		}
		
		
		if (velocityform.getTransactiontype() != null && velocityform.getTransactiontype() == 0L) {
			CommonValidator.mandatoryValidator(errors, TRANSACTIONTYPE_VAR, TRANSACTIONTYPE_REQUIRED);
		}
		
		velocityform.setMinimumamount(velocityform.getMinimumamount().trim());
		velocityform.setMaximumamount(velocityform.getMaximumamount().trim());
		CommonValidator.minimumAndMaximumValidation(errors, 
				velocityform.getMinimumamount(), velocityform.getMaximumamount(), MINMUM_AMOUNT_VAR, MAXIMUM_AMOUNT_VAR);
	}
	
	public void validates(Object target, Errors errors ) {
		WalletThresholdForm thresholdform = (WalletThresholdForm)target;
		
		
		if (thresholdform.getCountry() == 0L) {
			CommonValidator.mandatoryValidator(errors, COUNTRY_VAR, COUNTRY_REQUIRED);
		}
		
		if (thresholdform.getCurrency() == 0L) {
			CommonValidator.mandatoryValidator(errors, CURRENCY_VAR, CURRENCY_REQUIRED);
		}
		
		if(thresholdform.getMaximumamount().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, MAXIMUM_AMOUNT_VAR, MAXIMUM_AMOUNT_REQUIRED);
		}
		
		if(!thresholdform.getMaximumamount().equals(EMPTY_STRING)){
			validationForPercenatage( errors, thresholdform.getMaximumamount(), MAXIMUM_AMOUNT_VAR, MAXIMUM_AMOUNT_REQUIRED, AMOUNT_ERROR_FORMAT );			
		}
	}
	
	private void validationForPercenatage(Errors errors, String value, String var, String req, String fra){
		if(value != null){
			String v = value.trim();
			if(v.equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, var, req);
			} else if(!CommonValidator.expressionPattern(COMMON, v)){
				errors.rejectValue(var, fra);
			} else if(Double.parseDouble(v) > CURRENCY_MAX_LIMIT){
				errors.rejectValue(var, fra);
			} else if(Double.parseDouble(v) == 0){
				errors.rejectValue(var, fra);
			}
		}	
	}

}