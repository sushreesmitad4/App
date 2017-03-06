package com.tarang.ewallet.webservice.validation;

import com.tarang.ewallet.walletui.validator.CardValidator;
import com.tarang.ewallet.walletui.validator.common.CardNumberValidator;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;
import com.tarang.mwallet.rest.services.model.RestRequest;

public class RestCustomValidation extends CommonValidator {

	public static String checkFieldInput(RestRequest restRequest, int validationType) {
		String response = null;
		switch (validationType) {
			case LOGIN_SERVICE_REQ:
				response = checkLoginParameterInput(restRequest);
				break;
			case LOGOUT_SERVICE:
				response = checkLogOutParameterInput();
				break;
			case CUSTOMER_REGISTRATION_SERVICE:
				response = checkCustomerRegistrationParameterInput(restRequest);
				break;
		}
		return response;
	}

	@SuppressWarnings("unused")
	private static String checkLoginParameterInput(RestRequest restRequest) {
		String emailId = restRequest.getEmail();
		String password = restRequest.getPassword();
		String userType = restRequest.getUserType();
		String msisdnNumber = restRequest.getMsisdnNumber();
		String simNumber = restRequest.getSimNumber();	
		String imeiNumber = restRequest.getImeiNumber();	
		//apply these field validation
		return null;
	}

	private static String checkLogOutParameterInput() {
		return null;
	}
	
	private static String checkCustomerRegistrationParameterInput(RestRequest restRequest){
		String phoneCode = restRequest.getPhoneCode();
		String phoneNumber = restRequest.getPhoneNo();
		String emailId = restRequest.getEmail();
		String userType = restRequest.getUserType();
		Long hintQ = restRequest.getQuestion();
		String ans = restRequest.getAnswer();
		String msisdnNumber = restRequest.getMsisdnNumber();
		String simNumber = restRequest.getSimNumber();	
		String imeiNumber = restRequest.getImeiNumber();
		Long defaultCurrency = restRequest.getDefaultCurrency();
		if(null == phoneCode || null == phoneNumber){
			return CUSTOMER_MOBILE_NOT_EXIST;
		}
		
		if(null == emailId || null == userType || null == ans 
				|| null == hintQ || null == msisdnNumber || null == simNumber || null == imeiNumber || null == defaultCurrency){
			return PLEASE_CHECK_YOUR_INPUTS;
		}
		return null;
	}

	public static Boolean numberValidation(String value, Long cardType) {
		if (null == value || EMPTY_STRING.equals(value)) {
			return Boolean.TRUE;
		} else {
			return cvvValidator(value, cardType);
		}
	}

	public static Boolean cvvValidator(String value, Long cardType) {
		Boolean flag = Boolean.FALSE;
		if (!expressionPattern(NUMBER_EXPRESSION, value)) {
			flag = Boolean.TRUE;
			return flag;
		}
		if (cardType.equals(CardNumberValidator.AMERICAN_EXPRESS.longValue())) {
			if (value.length() != CardValidator.CVV_NUMBER_AMERICANEXP_CARD_LENGTH) {
				flag = Boolean.TRUE;
				return flag;
			}
		} else {
			if (value.length() != CardValidator.CVV_NUMBER_DEFAULT_CARD_LENGTH) {
				flag = Boolean.TRUE;
				return flag;
			}
		}
		return flag;

	}
	
	public static Boolean  numberValidator(String value, Integer minLength, Integer maxLength ){
		if(value == null || EMPTY_STRING.equals(value)){
			return true;
		}
		if(minLength != null && minLength != 0 && value.length() < minLength){
			return true;
		}
		if(maxLength != null && maxLength != 0 && value.length() > maxLength){
			return true;
		}
		if(!expressionPattern(NUMBER_EXPRESSION, value )){
			return true;
		}
		return false;
	}

}
