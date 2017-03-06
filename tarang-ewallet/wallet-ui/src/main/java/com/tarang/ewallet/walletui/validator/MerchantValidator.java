package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.form.MerchantForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class MerchantValidator implements Validator, GlobalLitterals, AttributeValueConstants, Common {

	// variable length
	public static final int BUSINESS_LEGAL_MIN_LENGTH = 1;
	public static final int BUSINESS_LEGAL_MAX_LENGTH = 50;
	public static final int AVG_TRAN_AMOUNT_MAX_LENGTH = 17;
	public static final int HIGHEST_MON_VOLUME_MAX_LENGTH = 17;
	public static final int MERCHANT_CODE_MIN_LENGTH = 8;
	public static final int MERCHANT_CODE_MAX_LENGTH = 20;
	public static final int MERCHANT_SUCCESS_URL_MIN_LENGTH = 8;
	public static final int MERCHANT_SUCCESS_URL_MAX_LENGTH = 500;
	public static final int MERCHANT_FAILURE_URL_MIN_LENGTH = 8;
	public static final int MERCHANT_FAILURE_URL_MAX_LENGTH = 500;
	
	public static final String MERCHANT_CODE_EXP = "[a-zA-Z0-9-]*";

	// variable names
	public static final String EMAIL_NOTE_VAR = "emailNote";
	public static final String EMAIL_VAR = "emailId";
	public static final String CONFIRM_EMAIL_VAR = "confirmEmailId";
	public static final String PASSWORD_VAR = "password";
	public static final String CONFIRM_PASSWORD_VAR = "confirmPassword";
	public static final String QUESTION_ONE_VAR = "question1";
	public static final String HINT_ONE_VAR = "hint1";
	public static final String QUESTION_TWO_VAR = "question2";
	public static final String HINT_TWO_VAR = "hint2";
	public static final String SECURITY_CODE_VAR = "securityCode";
	public static final String OWNER_TYPE_VAR = "ownerType";
	public static final String BUSINESS_LEGAL_VAR = "businessLegalname";
	public static final String ADDRESS_ONE_VAR = "address1BI";
	public static final String ADDRESS_TWO_VAR = "address2BI";
	public static final String COUNTRY_VAR = "countryBI";
	/* City just check */
	public static final String CITY_OR_TOWN_VAR = "cityOrTownBI";
	
	/* City just check */
	public static final String POSTAL_CODE_VAR = "postalCodeBI";
																
	/* Phone country just check */
	public static final String PHONE_COUNTRY_CODE_VAR = "phoneCountryCode1";
																		
	public static final String PHONE_NUBER_VAR = "phoneNumber";
	public static final String BUSINESS_CATEGORY_VAR = "businessCategory";
	public static final String SUBCATEGORY_VAR = "subCategory";
	public static final String BUSINESS_ESTABLISED_MONTH_VAR = "businessEstablishedMonth";
	public static final String BUSINESS_EST_YEAR_VAR = "businessEstablishedYear";
	public static final String WEBSITE_VAR = "website";
	public static final String AVG_TRAN_AMOUNT_VAR = "averageTransactionAmount";
	public static final String HIGHEST_MON_VOLUME_VAR = "highestMonthlyVolume";
	public static final String PER_OF_ANN_REV_FROM_ONLINE_SALES_VAR = "percentageOfAnnualRevenueFromOnlineSales";
	public static final String PHONE_VAR = "phone";
	public static final String CODE_VAR = "code";
	public static final String EMAILCSI_VAR = "emailCSI";
	public static final String POSTAL_CODE_BO_VAR = "postalCodeBO";
	public static final String CITY_OR_TOWN_BO_VAR = "cityOrTownBO";
	public static final String COUNTRY_BO_VAR = "countryBO";
	public static final String ADDRESS1_BO_VAR = "address1BO";
	public static final String ADDRESS2_BO_VAR = "address2BO";
	public static final String LAST_NAME_VAR = "lastName";
	public static final String MIDDLE_NAME_VAR = "middleName";
	public static final String FIRST_NAME_VAR = "firstName";
	public static final String CURRENCY_VAR = "currency";
	public static final String MERCHANT_CODE_VAR = "merchantCode";
	public static final String MERCHANT_SUCCESS_URL_VAR = "successUrl";
	public static final String MERCHANT_FAILURE_URL_VAR = "failureUrl";


	// messages
	public static final String QUESTION_ONE_REQUIRED = "question.required.errmsg";
	public static final String OWNER_TYPE_REQUIRED = "owner.type.required.errmsg";
	public static final String BUSINESS_LEGAL_REQUIRED = "business.legal.required.errmsg";
	public static final String BUSINESS_LEGAL_MIN_LENGTH_MSG = "business.legal.min.length.errmsg";
	public static final String BUSINESS_LEGAL_MAX_LENGTH_MSG = "business.legal.max.length.errmsg";
	public static final String BUSINESS_LEGAL_MATCHER = "business.legal.contains.errmsg";
	public static final String COUNTRY_REQUIRED = "country.required.errmsg";
	public static final String BUSINESS_CATEGORY_REQUIRED = "business.category.required.errmsg";
	public static final String SUBCATEGORY_REQUIRED = "sub.category.required.errmsg";
	public static final String BUSINESS_ESTABLISED_MONTH_MATCHER = "month.errmsg";
	public static final String BUSINESS_EST_YEAR_MATCHER = "year.errmsg";
	public static final String AVG_TRAN_AMOUNT_REQUIRED = "avg.txn.amount.required.errmsg";
	public static final String HIGHEST_MON_VOLUME_REQUIRED = "highest.monthly.valume.required.errmsg";
	public static final String PER_OF_ANN_REV_FROM_ONLINE_SALES_REQUIRED = "per.anual.revenue.required.errmsg";
	public static final String COUNTRY_BO_REQUIRED = "country.required.errmsg";

	public static final String AVG_TRAN_AMOUNT_MAX_LENGTH_MSG = "avg.txn.amount.max.length.errmsg";
	public static final String AVG_TRAN_AMOUNT_EXPRESSION_MSG = "avg.txn.amount.contains.errmsg";
	public static final String HIGHEST_MON_VOLUME_MAX_LENGTH_MSG = "highest.monthly.valume.length.errmsg";
	public static final String HIGHEST_MON_VOLUME_EXPRESSION_MSG = "highest.monthly.valume.contains.errmsg";
	public static final String MERCHANT_CURRENCY_REQUIRED = "currency.required.errmsg";
	public static final String SERVICE_PHONE_NUBER_EXIST = "duplicate.service.phoneno.errmsg";
	public static final String MERCHANT_CODE_MIN_LENGTH_MSG = "merchant.code.error.min.length.msg";
	public static final String MERCHANT_CODE_MAX_LENGTH_MSG = "merchant.code.error.max.length.msg";
	public static final String MERCHANT_CODE_EXPRESSION_MSG = "merchant.code.expression.errmsg";
	public static final String MERCHANT_CODE_REQUIRED = "merchant.code.required";
	public static final String MERCHANT_SUCCESS_URL_REQUIRED = "merchant.success.url.required";
	public static final String MERCHANT_SUCCESS_URL_MIN_LENGTH_MSG = "merchant.success.url.min.length.msg";
	public static final String MERCHANT_SUCCESS_URL_MAX_LENGTH_MSG = "merchant.success.url.max.length.msg";
	public static final String MERCHANT_SUCCESS_URL_EXPRESSION_MSG = "merchant.success.url.expression.msg";
	public static final String MERCHANT_FAILURE_URL_REQUIRED = "merchant.failure.url.required";
	public static final String MERCHANT_FAILURE_URL_MIN_LENGTH_MSG = "merchant.failure.url.min.length.msg";
	public static final String MERCHANT_FAILURE_URL_MAX_LENGTH_MSG = "merchant.failure.url.max.length.msg";
	public static final String MERCHANT_FAILURE_URL_EXPRESSION_MSG = "merchant.failure.url.expression.msg";


	
	private Boolean flag = Boolean.TRUE;
	private String regOrUpdate = EMPTY_STRING;
	private String adminOrmerchant = EMPTY_STRING;

	public MerchantValidator(Boolean flag, String adminOrmerchant, String regOrUpdate) {
		this.flag = flag;
		this.regOrUpdate = regOrUpdate;
		this.adminOrmerchant = adminOrmerchant;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return MerchantForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		MerchantForm form = (MerchantForm) target;
		if (flag) {

			// for email id
			String emailId = form.getEmailId().trim();
			//String confirmEmailId = form.getConfirmEmailId().trim();
			
			CommonValidator.emailValidation(errors, emailId, EMAIL_VAR);
			//CommonValidator.confirmEmailValidation(errors, emailId, confirmEmailId, CONFIRM_EMAIL_VAR);
			
			// for phone country code
			String phoneCountryCode = form.getPhoneCountryCode1().trim();
			CommonValidator.phoneCodeValidation(errors, phoneCountryCode, PHONE_COUNTRY_CODE_VAR, Boolean.TRUE);
			form.setPhoneCountryCode1(phoneCountryCode.trim());

			// for phone number
			String phoneNumber = form.getPhoneNumber().trim();
			CommonValidator.phoneNumberValidator(errors, phoneNumber, PHONE_NUBER_VAR, Boolean.TRUE);
			
			// for password
			String password = form.getPassword();
			CommonValidator.passwordValidator(errors, password, PASSWORD_VAR, emailId);

			// for confirmpassword
			String confirmPassword = form.getConfirmPassword();
			CommonValidator.confirmPasswordValidation(errors, password, confirmPassword, CONFIRM_PASSWORD_VAR);

			// for question one
			CommonValidator.mandatoryValidator(errors, form.getQuestion1(), QUESTION_ONE_VAR, QUESTION_ONE_REQUIRED);

			// for hint one
			String hintOne = form.getHint1().trim();
			CommonValidator.answerValidator(errors, hintOne, HINT_ONE_VAR);
			form.setHint1(hintOne.trim());
		} else {

			if ("update".equals(regOrUpdate) && adminOrmerchant.equals(MERCHANT_PATH)) {
				// for question one
				CommonValidator.mandatoryValidator(errors, form.getQuestion1(), QUESTION_ONE_VAR, QUESTION_ONE_REQUIRED);

				// for hint one
				String hintOne = form.getHint1().trim();
				CommonValidator.answerValidator(errors, hintOne, HINT_ONE_VAR);
				form.setHint1(hintOne.trim());
				
				
			}

			// for OwnerType
			CommonValidator.mandatoryValidator(errors, form.getOwnerType(), OWNER_TYPE_VAR, OWNER_TYPE_REQUIRED);

			// for business legal name
			String businessLegalName = form.getBusinessLegalname().trim();

			if(businessLegalName.equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, BUSINESS_LEGAL_VAR, BUSINESS_LEGAL_REQUIRED);
			} else{
				CommonValidator.nameValidator(errors, businessLegalName,
					BUSINESS_LEGAL_VAR, BUSINESS_LEGAL_MIN_LENGTH,
					BUSINESS_LEGAL_MIN_LENGTH_MSG, BUSINESS_LEGAL_MAX_LENGTH,
					BUSINESS_LEGAL_MAX_LENGTH_MSG, BUSINESS_LEGAL_MATCHER);
			}
			form.setBusinessLegalname(businessLegalName.trim());

			// for address one
			String addressOne = form.getAddress1BI().trim();
			CommonValidator.addressValidator(errors, addressOne, ADDRESS_ONE_VAR, true);
			form.setAddress1BI(addressOne.trim());

			// for address two
			String addressTwo = form.getAddress2BI().trim();
			CommonValidator.addressValidator(errors, addressTwo, ADDRESS_TWO_VAR, false);
			form.setAddress2BI(addressTwo.trim());

			// for country
			CommonValidator.mandatoryValidator(errors, form.getCountryBI(), COUNTRY_VAR, COUNTRY_REQUIRED);

			// for city or town
			String cityOrTown = form.getCityOrTownBI().trim();
			CommonValidator.cityValidator(errors, cityOrTown, CITY_OR_TOWN_VAR);

			// for postal code
			String postalCode = form.getPostalCodeBI().trim();
			if (!postalCode.equals(EMPTY_STRING)) {
				CommonValidator.zipCodeValidator(errors, postalCode, POSTAL_CODE_VAR, form.getCountryBI());
			}

			// for businesscategory
			if (form.getBusinessCategory() == 0L) {
				CommonValidator.mandatoryValidator(errors, form.getBusinessCategory(), BUSINESS_CATEGORY_VAR, BUSINESS_CATEGORY_REQUIRED);
			} else if (form.getSubCategory() == 0L) {
				// for subcategory
				CommonValidator.mandatoryValidator(errors, form.getSubCategory(), SUBCATEGORY_VAR, SUBCATEGORY_REQUIRED);
			}

			// for bussEstablishedMonth
			String bussEstablishedMonth = form.getBusinessEstablishedMonth().trim();
			CommonValidator.mandatoryValidator(errors,
					Long.parseLong(bussEstablishedMonth),
					BUSINESS_ESTABLISED_MONTH_VAR,
					BUSINESS_ESTABLISED_MONTH_MATCHER);
			form.setBusinessEstablishedMonth(bussEstablishedMonth.trim());

			// for bussEstablishedYear
			String bussEstablishedYear = form.getBusinessEstablishedYear().trim();
			CommonValidator.mandatoryValidator(errors, Long.parseLong(bussEstablishedYear), BUSINESS_EST_YEAR_VAR, BUSINESS_EST_YEAR_MATCHER);

			// for website
			form.setWebsite(form.getWebsite().trim());
			if (!form.getWebsite().equals(EMPTY_STRING)) {
				CommonValidator.websiteValidation(errors, form.getWebsite(), WEBSITE_VAR);
			}

			// for currency
			CommonValidator.mandatoryValidator(errors, form.getCurrency(), CURRENCY_VAR, MERCHANT_CURRENCY_REQUIRED);

			// for AverageTransactionAmount
			form.setAverageTransactionAmount(form.getAverageTransactionAmount().trim());
			if (form.getAverageTransactionAmount().equals(EMPTY_STRING)) {
				CommonValidator.mandatoryValidator(errors, AVG_TRAN_AMOUNT_VAR, AVG_TRAN_AMOUNT_REQUIRED);
			} else {
				CommonValidator.numberValidator(errors,
						form.getAverageTransactionAmount(),
						AVG_TRAN_AMOUNT_VAR, null, null,
						AVG_TRAN_AMOUNT_MAX_LENGTH,
						AVG_TRAN_AMOUNT_MAX_LENGTH_MSG,
						AVG_TRAN_AMOUNT_EXPRESSION_MSG);

			}

			// for HighestMonthlyVolume
			form.setHighestMonthlyVolume(form.getHighestMonthlyVolume().trim());
			if (form.getHighestMonthlyVolume().equals(EMPTY_STRING)) {
				CommonValidator.mandatoryValidator(errors, HIGHEST_MON_VOLUME_VAR, HIGHEST_MON_VOLUME_REQUIRED);
			}  else {
				CommonValidator.numberValidator(errors,
						form.getHighestMonthlyVolume(), HIGHEST_MON_VOLUME_VAR,
						null, null, HIGHEST_MON_VOLUME_MAX_LENGTH,
						HIGHEST_MON_VOLUME_MAX_LENGTH_MSG,
						HIGHEST_MON_VOLUME_EXPRESSION_MSG);
			}

			// validation for percentageOfAnnualRevenueFromOnlineSales
			CommonValidator.mandatoryValidator(errors,
					form.getPercentageOfAnnualRevenueFromOnlineSales(),
					PER_OF_ANN_REV_FROM_ONLINE_SALES_VAR,
					PER_OF_ANN_REV_FROM_ONLINE_SALES_REQUIRED);
			
			// validation for merchantCode
			if(form.getCodeCheck()){
				form.setMerchantCode(form.getMerchantCode().trim());
				form.setSuccessUrl(form.getSuccessUrl().trim());
				form.setFailureUrl(form.getFailureUrl().trim());
				if(form.getMerchantCode() != null && form.getMerchantCode().equals(EMPTY_STRING)){
					CommonValidator.mandatoryValidator(errors, MERCHANT_CODE_VAR, MERCHANT_CODE_REQUIRED);
				} else if(form.getMerchantCode().trim().length() < MERCHANT_CODE_MIN_LENGTH){
				    errors.rejectValue(MERCHANT_CODE_VAR, MERCHANT_CODE_MIN_LENGTH_MSG);	
				} else if(form.getMerchantCode().trim().length() > MERCHANT_CODE_MAX_LENGTH){
					errors.rejectValue(MERCHANT_CODE_VAR, MERCHANT_CODE_MAX_LENGTH_MSG);
				} else if(! CommonValidator.expressionPattern(MERCHANT_CODE_EXP, form.getMerchantCode().trim())){
					errors.rejectValue(MERCHANT_CODE_VAR, MERCHANT_CODE_EXPRESSION_MSG);
				}
			
				if(form.getSuccessUrl() != null && form.getSuccessUrl().equals(EMPTY_STRING)){
					CommonValidator.mandatoryValidator(errors, MERCHANT_SUCCESS_URL_VAR, MERCHANT_SUCCESS_URL_REQUIRED);
				} else if(form.getSuccessUrl().trim().length() < MERCHANT_SUCCESS_URL_MIN_LENGTH){
					errors.rejectValue(MERCHANT_SUCCESS_URL_VAR, MERCHANT_SUCCESS_URL_MIN_LENGTH_MSG);	
				} else if(form.getSuccessUrl().trim().length() > MERCHANT_SUCCESS_URL_MAX_LENGTH){
					errors.rejectValue(MERCHANT_SUCCESS_URL_VAR, MERCHANT_SUCCESS_URL_MAX_LENGTH_MSG);
				} else if( !CommonValidator.expressionPattern(URL_EXP, form.getSuccessUrl().trim())
						&& !CommonValidator.expressionPattern(WEBSITE_EXP, form.getSuccessUrl().trim())){
					errors.rejectValue(MERCHANT_SUCCESS_URL_VAR, MERCHANT_SUCCESS_URL_EXPRESSION_MSG);
				}

				if(form.getFailureUrl() != null && form.getFailureUrl().equals(EMPTY_STRING)){
					CommonValidator.mandatoryValidator(errors, MERCHANT_FAILURE_URL_VAR, MERCHANT_FAILURE_URL_REQUIRED);
				} else if(form.getFailureUrl().trim().length() < MERCHANT_FAILURE_URL_MIN_LENGTH){
					errors.rejectValue(MERCHANT_FAILURE_URL_VAR, MERCHANT_FAILURE_URL_MIN_LENGTH_MSG);	
				} else if(form.getFailureUrl().trim().length() > MERCHANT_FAILURE_URL_MAX_LENGTH){
					errors.rejectValue(MERCHANT_FAILURE_URL_VAR, MERCHANT_FAILURE_URL_MAX_LENGTH_MSG);
				} else if(!CommonValidator.expressionPattern(URL_EXP, form.getFailureUrl().trim()) 
						&& !CommonValidator.expressionPattern(WEBSITE_EXP, form.getFailureUrl().trim())){
					errors.rejectValue(MERCHANT_FAILURE_URL_VAR, MERCHANT_FAILURE_URL_EXPRESSION_MSG);
				}
			}

			// validation for code and phone text field
			String code = form.getCode().trim();
			String phone = form.getPhone().trim();
			
			if(phone != null && !phone.equals(EMPTY_STRING)){
				CommonValidator.phoneCodeValidation(errors, code, CODE_VAR, Boolean.TRUE);
			} else {
				CommonValidator.phoneCodeValidation(errors, code, CODE_VAR, Boolean.FALSE);
			}

			if(code != null && !code.equals(EMPTY_STRING)){
				CommonValidator.phoneNumberValidator(errors, phone, PHONE_VAR, Boolean.TRUE);
			} else { 
				CommonValidator.phoneNumberValidator(errors, phone, PHONE_VAR, Boolean.FALSE);
			}
							
			// validation for mail text field
			String emailcsi = form.getEmailCSI().trim();
			CommonValidator.emailValidation(errors, emailcsi, EMAILCSI_VAR);

			// validation for lastname text box
			String lastname = form.getLastName().trim();
			CommonValidator.firstOrLastNameValidator(errors, lastname, false);

			// validation for firstname text box
			String firstname = form.getFirstName().trim();
			CommonValidator.firstOrLastNameValidator(errors, firstname, true);

			if (!form.getHomeAddress()) {

				// validation for address1BO
				String address1bo = form.getAddress1BO();
				CommonValidator.addressValidator(errors, address1bo, ADDRESS1_BO_VAR, true);

				// validation for address2BO
				String address2bo = form.getAddress2BO().trim();
				CommonValidator.addressValidator(errors, address2bo, ADDRESS2_BO_VAR, false);

				// validation for cityOrTownBO text field
				String cityortownbo = form.getCityOrTownBO().trim();
				CommonValidator.cityValidator(errors, cityortownbo, CITY_OR_TOWN_BO_VAR);
				form.setCityOrTownBO(cityortownbo.trim());

				// validation for countryBo select box
				CommonValidator.mandatoryValidator(errors, form.getCountryBO(), COUNTRY_BO_VAR, COUNTRY_BO_REQUIRED);

				// validation for postalcode
				String postalCode2 = form.getPostalCodeBO().trim();
				if (!postalCode2.equals(EMPTY_STRING)) {
					CommonValidator.zipCodeValidator(errors, postalCode2, POSTAL_CODE_BO_VAR, form.getCountryBO());
				}
			}

			// validation for legal name should not same as name
			String legalname = form.getBusinessLegalname();
			String flName = form.getFirstName() + SPACE_STRING + form.getLastName();
			CommonValidator.legalNameAndFLNameValidator(errors, legalname, flName, LAST_NAME_VAR);

			// validation for terms
			if ( !form.getTerms() ) {
				CommonValidator.mandatoryValidator(errors, Common.TERMS_VAR, Common.TERMS_REQUIRED);
			}
			if(validateActive(form)){
				validateUpdateReason(form, errors);
			} else if(validateDelete(form)){
				validateUpdateReason(form, errors);
			} else if(validateStatus(form)){
				validateUpdateReason(form, errors);
			}
		}
	}
	
	private void validateUpdateReason(MerchantForm form, Errors errors){
		if(form.getUpdateReason().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, UPDATE_REASON_VAR, UPDATE_REASON_REQUIRED);
		} else{
			CommonValidator.addressValidator(errors, form.getUpdateReason(), UPDATE_REASON_VAR,
					MESSAGE_MIN_LENGTH, UPDATE_REASON_MIN_LENGTH_RANGE,
					MESSAGE_MAX_LENGTH, UPDATE_REASON_MAX_LENGTH_RANGE,
					UPDATE_REASON_MATCHER);
		}
	}
	
	private Boolean validateActive(MerchantForm form){
		return form.getActive() != null && !form.getOldActive().equals(form.getActive());
	}
	
	private Boolean validateDelete(MerchantForm form){
		return form.getDeleted() != null && !form.getOldDeleted().equals(form.getDeleted());
	}
	
	private Boolean validateStatus(MerchantForm form){
		return !skipStatus(form) && form.getStatus() != null && !form.getOldStatus().equals(form.getStatus());
	}
	
	private Boolean skipStatus(MerchantForm form){
		return UserStatusConstants.PENDING.equals(form.getOldStatus()) && UserStatusConstants.APPROVED.equals(form.getStatus());
	}
}
	