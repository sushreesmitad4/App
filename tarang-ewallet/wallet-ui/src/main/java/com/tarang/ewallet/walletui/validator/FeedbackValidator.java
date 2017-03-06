package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.FeedbackForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class FeedbackValidator implements Validator, GlobalLitterals, Common {

	public static final String FEEDBACK_QUERRY_VAR = "querryType";
	public static final String FEEDBACK_QUERRY_REQUIRED = "type.required.errmsg";

	public static final String FEEDBACK_SUBJECT_VAR = "subject";
	public static final Integer FEEDBACK_SUBJECT_MIN_LENGTH = 3;
	public static final Integer FEEDBACK_SUBJECT_MAX_LENGTH = 100;
	public static final String FEEDBACK_SUBJECT_REQUIRED = "subject.required.errmsg";
	public static final String FEEDBACK_SUBJECT_MATCHER = "subject.contains.errmsg";
	public static final String FEEDBACK_SUBJECT_MIN_LENGTH_RANGE = "subject.min.length.errmsg";
	public static final String FEEDBACK_SUBJECT_MAX_LENGTH_RANGE = "subject.max.length.errmsg";
	
	public static final String FEEDBACK_MESSAGE_VAR = "message";
	
	public static final String REPLY_MESSAGE_VAR = "responseMessage";
	

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		FeedbackForm feedbackForm = (FeedbackForm) target;
		if(feedbackForm.getMode() !=null ) {
			if(feedbackForm.getQuerryType() == null){
				CommonValidator.mandatoryValidator(errors, FEEDBACK_QUERRY_VAR, FEEDBACK_QUERRY_REQUIRED);
			}
			
			if(feedbackForm.getSubject().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, FEEDBACK_SUBJECT_VAR, FEEDBACK_SUBJECT_REQUIRED);
			} else{
				CommonValidator.nameValidator(errors,feedbackForm.getSubject(), FEEDBACK_SUBJECT_VAR,
						FEEDBACK_SUBJECT_MIN_LENGTH, FEEDBACK_SUBJECT_MIN_LENGTH_RANGE,
						FEEDBACK_SUBJECT_MAX_LENGTH, FEEDBACK_SUBJECT_MAX_LENGTH_RANGE,
						FEEDBACK_SUBJECT_MATCHER);
			}
	
			if(feedbackForm.getMessage().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, FEEDBACK_MESSAGE_VAR, MESSAGE_REQUIRED);
			} else{
				CommonValidator.messageValidator(errors, feedbackForm.getMessage(), FEEDBACK_MESSAGE_VAR,
						MESSAGE_MIN_LENGTH, MESSAGE_MIN_LENGTH_RANGE, MESSAGE_MAX_LENGTH,
						MESSAGE_MAX_LENGTH_RANGE, MESSAGE_MATCHER);
			}
		}
		
		if(null == feedbackForm.getMode() ) {
			if(feedbackForm.getResponseMessage().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, REPLY_MESSAGE_VAR, MESSAGE_REQUIRED);
			} else {
				CommonValidator.messageValidator(errors, feedbackForm.getResponseMessage(), REPLY_MESSAGE_VAR,
						MESSAGE_MIN_LENGTH, MESSAGE_MIN_LENGTH_RANGE, MESSAGE_MAX_LENGTH, 
						MESSAGE_MAX_LENGTH_RANGE, MESSAGE_MATCHER);
			}
		}
	}
       
}