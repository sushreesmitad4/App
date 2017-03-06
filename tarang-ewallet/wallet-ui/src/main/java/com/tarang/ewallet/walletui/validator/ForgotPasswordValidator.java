package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.dto.ForgotPasswordDto;
import com.tarang.ewallet.walletui.form.ForgotPasswordForm;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class ForgotPasswordValidator implements Validator {

	// variable names
	public static final String EMAIL_VAR = "emailId";
	public static final String QUESTION_ONE_VAR = "question1";
	public static final String HINT_ONE_VAR = "hint1";
	public static final String USER_TYPE_VAR = "userType";

	
	// messages
	public static final String QUESTION_ONE_REQUIRED = "question.required.errmsg";
	
	@Override
	public boolean supports(Class<?> c) {
		return ForgotPasswordForm.class.equals(c);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ForgotPasswordDto form = (ForgotPasswordDto) target;

		// for email id
		form.setEmailId(form.getEmailId().trim());
		CommonValidator.emailValidation(errors, form.getEmailId(), EMAIL_VAR);

		// for question
		if (form.getQuestion1() == 0L) {
			CommonValidator.mandatoryValidator(errors, QUESTION_ONE_VAR, QUESTION_ONE_REQUIRED);
		}

		// for answer
		form.setHint1(form.getHint1().trim());
		CommonValidator.answerValidator(errors, form.getHint1(), HINT_ONE_VAR);
	}

}