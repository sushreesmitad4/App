package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.walletui.form.ChangePasswordForm;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class ChangePasswordValidator implements Validator {
	 
	public static final String OLD_PASSWORD_VAR = "oldPassword";
	public static final String NEW_PASSWORD_VAR = "newPassword";
	public static final String CONFIRM_PASSWORD_VAR = "confirmNewPassword";
 
	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordForm.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		ChangePasswordForm form =(ChangePasswordForm)target;
               
		//for old password
		form.setOldPassword(form.getOldPassword().trim());
		CommonValidator.oldPasswordValidation(errors, form.getOldPassword(), OLD_PASSWORD_VAR);
		
		//for new password
		form.setNewPassword(form.getNewPassword().trim());
		CommonValidator.passwordValidator(errors, form.getNewPassword(), NEW_PASSWORD_VAR, form.getEmailId());
		
		CommonValidator.newPasswordSameAsOldPasswordValidator(errors, form.getNewPassword(), form.getOldPassword(), NEW_PASSWORD_VAR);
		
		//for confirm new password
		form.setConfirmNewPassword(form.getConfirmNewPassword().trim());
		CommonValidator.confirmPasswordValidation(errors, form.getNewPassword(), form.getConfirmNewPassword(), CONFIRM_PASSWORD_VAR);
	}
		
}