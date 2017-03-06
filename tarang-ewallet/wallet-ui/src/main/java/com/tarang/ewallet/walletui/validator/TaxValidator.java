/**
 * 
 */
package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.TaxForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


/**
 * @author vasanthar
 *
 */
public class TaxValidator implements Validator, GlobalLitterals {
	
	public static final String COUNTRY_VAR = "country";
	public static final String PERCENTAGE_VAR = "percentage";
	public static final String COUNTRY_REQUIRED = "country.required.errmsg";	
	
	public static final String TAX_NUMBER_EXPRESSION = "percentage.contains.errmsg";
	private static final String COMMON = "\\d{0,2}(\\.\\d{1,2})?";
	
	
	public boolean supports(Class<?> clazz) {
		return TaxForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		TaxForm taxform = (TaxForm)target;
		
		if (taxform.getCountry() == 0) {
			CommonValidator.mandatoryValidator(errors, COUNTRY_VAR, COUNTRY_REQUIRED);
		}
		taxform.setPercentage(taxform.getPercentage().trim());
		validationForPercenatage(errors, taxform.getPercentage(), PERCENTAGE_VAR, Common.PERCENTAGE_REQUIRED, TAX_NUMBER_EXPRESSION);
	}
	
	private void validationForPercenatage(Errors errors, String value , String var, String req, String fra){
		String v = value;
		if(v != null){
			v = v.trim();
			if(v.equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, var, req);
			} else if(!CommonValidator.expressionPattern(COMMON, v)){
				errors.rejectValue(var, fra);
			} else if(Double.parseDouble(v) > MAX_PERCENTAGE){
				errors.rejectValue(var, fra);
			}
		}	
	}

}