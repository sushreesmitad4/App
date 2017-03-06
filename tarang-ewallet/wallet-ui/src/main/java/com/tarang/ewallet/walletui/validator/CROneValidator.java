/**
 * 
 */
package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.CustomerRegFormOne;
import com.tarang.ewallet.walletui.form.CustomerRegFormTwo;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


/**
 * @author  : prasadj
 * @date    : Oct 23, 2012
 * @time    : 12:03:54 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CROneValidator implements Validator {
	
	public static final String EMAIL_VAR = "emailId";
	//public static final String CONF_EMAIL_VAR = "cemailId";
	public static final String PASSWORD_VAR = "password";
	public static final String CONF_PASSWORD_VAR = "cpassword";
	public static final String ANS_ONE_VAR = "answer1";
	public static final String QUE_ONE_VAR = "hintQuestion1";
	public static final String PHONE_CODE_VAR = "phoneCode";
	public static final String PHONE_NO_VAR = "phoneNo";
	public static final String TERMS_VAR = "terms";
	
	
	public CROneValidator() {	
	}
	
	public boolean supports(Class<?> clazz) {
		return CustomerRegFormOne.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		CustomerRegFormTwo customerForm = (CustomerRegFormTwo) target;
		
		customerForm.setEmailId(customerForm.getEmailId().trim());
		CommonValidator.emailValidation(errors, customerForm.getEmailId(), EMAIL_VAR);
		
		/*customerForm.setCemailId(customerForm.getCemailId().trim());
		CommonValidator.confirmEmailValidation(errors, customerForm.getEmailId(), customerForm.getCemailId(), CONF_EMAIL_VAR);
		 */
		customerForm.setPhoneCode(customerForm.getPhoneCode().trim());
		CommonValidator.phoneCodeValidation(errors, customerForm.getPhoneCode(), PHONE_CODE_VAR, Boolean.TRUE);
		
		customerForm.setPhoneNo(customerForm.getPhoneNo().trim());
		CommonValidator.phoneNumberValidator(errors, customerForm.getPhoneNo(), PHONE_NO_VAR, Boolean.TRUE);
				
		customerForm.setPassword(customerForm.getPassword().trim());
		if(customerForm.getPassword().equals(GlobalLitterals.EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, PASSWORD_VAR, Common.PASSWORD_REQUIRED);
		} else{
			CommonValidator.passwordValidator(errors, customerForm.getPassword(), PASSWORD_VAR, customerForm.getEmailId());
		}
		
		customerForm.setCpassword(customerForm.getCpassword().trim());
		CommonValidator.confirmPasswordValidation(errors, customerForm.getPassword(), customerForm.getCpassword(), CONF_PASSWORD_VAR);
		
		if (customerForm.getHintQuestion1() == 0) {
			CommonValidator.mandatoryValidator(errors, QUE_ONE_VAR, Common.QUE_ONE_REQUIRED);
		}
		customerForm.setAnswer1(customerForm.getAnswer1().trim());
		CommonValidator.answerValidator(errors, customerForm.getAnswer1(), ANS_ONE_VAR);
		
		if (!customerForm.getTerms()){
			errors.rejectValue(TERMS_VAR, Common.TERMS_REQUIRED);
		}
	}

}
