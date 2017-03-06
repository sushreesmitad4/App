/**
 * 
 */
package com.tarang.ewallet.walletui.validator;

import java.math.BigInteger;

import org.springframework.validation.Errors;

import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.form.CustomerRegFormTwo;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


/**
 * @author  : prasadj
 * @date    : Oct 23, 2012
 * @time    : 12:17:57 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CRTwoValidator implements GlobalLitterals, Common {
	
	public static final String ADDRES_ONE_VAR = "addrOne";
	public static final String ADDRES_TWO_VAR = "addrTwo";
	
	public static final String CITY_VAR = "city";
	public static final String POSTAL_VAR = "postalCode";
	
	public static final String PHONE_CODE_VAR = "phoneCode";
	public static final String PHONE_NO_VAR = "phoneNo";
	
	public static final String TITLE_VAR = "ptitle";
	public static final String COUNTRY_VAR = "country";
	public static final String DATE_VAR = "dateOfBirth";
	//public static final String TERMS_VAR = "terms";

	public static final String COUNTRY_REQUIRED = "country.required.errmsg";
	public static final String CUSTOMER_UPDATE_SUCCESS_MSG = "cusotmer.update.success.msg";

	public CRTwoValidator() {
	}
	
	public boolean supports(Class<?> clazz) {
		return CustomerRegFormTwo.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		CustomerRegFormTwo customerForm = (CustomerRegFormTwo) target;
		
		if (!customerForm.getProfileComplet()){
		if (customerForm.getPtitle() == 0){
			CommonValidator.mandatoryValidator(errors, TITLE_VAR, Common.TITLE_REQUIRED);
		}
	
		customerForm.setFirstName(customerForm.getFirstName().trim());
		CommonValidator.firstOrLastNameValidator(errors, customerForm.getFirstName(), true);
		
		customerForm.setLastName(customerForm.getLastName().trim());
		CommonValidator.firstOrLastNameValidator(errors, customerForm.getLastName(), false);
		
		customerForm.setAddrOne(customerForm.getAddrOne().trim());
		CommonValidator.addressValidator(errors, customerForm.getAddrOne(), ADDRES_ONE_VAR,	true);
		
		if(!customerForm.getAddrTwo().equals(EMPTY_STRING)){
			customerForm.setAddrTwo(customerForm.getAddrTwo().trim());
			CommonValidator.addressValidator(errors, customerForm.getAddrTwo(), ADDRES_TWO_VAR, false);
		}
		
		customerForm.setCity(customerForm.getCity().trim());
		CommonValidator.cityValidator(errors, customerForm.getCity(), CITY_VAR);
	
		if (customerForm.getCountry() == 0) {
			CommonValidator.mandatoryValidator(errors, COUNTRY_VAR, COUNTRY_REQUIRED);
		}
		
		if(!customerForm.getPostalCode().equals(EMPTY_STRING)){
			customerForm.setPostalCode(customerForm.getPostalCode().trim());
			CommonValidator.zipCodeValidator(errors, customerForm.getPostalCode(), POSTAL_VAR,customerForm.getCountry());
		}
		String mode = customerForm.getMode();
		if (null != mode && AttributeConstants.UPDATE.equalsIgnoreCase(mode)) {
			customerForm.setPhoneCode(customerForm.getPhoneCode().trim());
			CommonValidator.phoneCodeValidation(errors, customerForm.getPhoneCode(), PHONE_CODE_VAR, Boolean.TRUE);
			
			customerForm.setPhoneNo(customerForm.getPhoneNo().trim());
			CommonValidator.phoneNumberValidator(errors, customerForm.getPhoneNo(), PHONE_NO_VAR, Boolean.TRUE);
		}
			
		customerForm.setDateOfBirth(customerForm.getDateOfBirth().trim());
		if (customerForm.getDateOfBirth().equals(EMPTY_STRING)) {
			CommonValidator.mandatoryValidator(errors, DATE_VAR, Common.DATE_REQUIRED);
		} else {
			CommonValidator.dateOfBirthValidation(errors, customerForm.getDateOfBirth(), DATE_VAR);
		}
		if(validateActive(customerForm)){
			validateUpdateReason(customerForm, errors);
		} else if(validateDelete(customerForm)){
			validateUpdateReason(customerForm, errors);
		} else if(validateStatus(customerForm)){
			validateUpdateReason(customerForm, errors);
		}
		/*if (!customerForm.getTerms()){
			errors.rejectValue(TERMS_VAR, Common.TERMS_REQUIRED);
		}*/
	}
	}
	public static Long getLongFormate(String str){
		BigInteger bb=new BigInteger(str);
		return bb.longValue();
	}
	
	private void validateUpdateReason(CustomerRegFormTwo form, Errors errors){
		if(form.getUpdateReason().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, UPDATE_REASON_VAR, UPDATE_REASON_REQUIRED);
		} else {
			CommonValidator.addressValidator(errors, form.getUpdateReason(), UPDATE_REASON_VAR,
					MESSAGE_MIN_LENGTH, UPDATE_REASON_MIN_LENGTH_RANGE,
					MESSAGE_MAX_LENGTH, UPDATE_REASON_MAX_LENGTH_RANGE,
					UPDATE_REASON_MATCHER);
		}
	}
	
	private Boolean validateActive(CustomerRegFormTwo form){
		return form.getActive() != null && !form.getOldActive().equals(form.getActive());
	}
	
	private Boolean validateDelete(CustomerRegFormTwo form){
		return form.getDeleted() != null && !form.getOldDeleted().equals(form.getDeleted());
	}
	
	private Boolean validateStatus(CustomerRegFormTwo form){
		return !skipStatus(form) && form.getStatus() != null && !form.getOldStatus().equals(form.getStatus());
	}
	
	private Boolean skipStatus(CustomerRegFormTwo form){
		return UserStatusConstants.PENDING.equals(form.getOldStatus()) && UserStatusConstants.APPROVED.equals(form.getStatus());
	}
	
}
