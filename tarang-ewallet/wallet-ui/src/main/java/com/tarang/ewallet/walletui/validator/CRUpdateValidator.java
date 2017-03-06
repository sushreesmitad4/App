/**
 * 
 */
package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;

import com.tarang.ewallet.util.GlobalLitterals;
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
public class CRUpdateValidator implements GlobalLitterals {

	public static final String ADDRES_ONE_VAR = "addrOne";
	public static final String ADDRES_TWO_VAR = "addrTwo";
	public static final String CITY_VAR = "city";
	public static final String POSTAL_VAR = "postalCode";
	
	public static final String PHONE_CODE_VAR = "phoneCode";
	public static final String PHONE_NO_VAR = "phoneNo";
	public static final String TITLE_VAR = "ptitle";
	public static final String COUNTRY_VAR = "country";
	public static final String DATE_VAR = "dateOfBirth";
	public static final String ANS_ONE_VAR = "answer1";
	public static final String QUE_ONE_VAR = "hintQuestion1";
	
	public static final String COUNTRY_REQUIRED = "country.required.errmsg";

	public static final String PHONE_NUM_UNIQUE  = "customer.phoneno.unique";
	
	public static final String CUSTOMER_UPDATE_SUCCESS_MSG = "cusotmer.update.success.msg";

	public boolean supports(Class<?> clazz) {
		return CustomerRegFormTwo.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		CustomerRegFormTwo customerUpdateForm = (CustomerRegFormTwo) target;
		
		if (customerUpdateForm.getHintQuestion1() == 0){
			CommonValidator.mandatoryValidator(errors, QUE_ONE_VAR, Common.QUE_ONE_REQUIRED);
		}
		
		customerUpdateForm.setAnswer1(customerUpdateForm.getAnswer1().trim());
		CommonValidator.answerValidator(errors, customerUpdateForm.getAnswer1(), ANS_ONE_VAR);
		
		if (customerUpdateForm.getPtitle() == 0){
			CommonValidator.mandatoryValidator(errors, TITLE_VAR, Common.TITLE_REQUIRED);
		}
		
		customerUpdateForm.setFirstName(customerUpdateForm.getFirstName().trim());
		CommonValidator.firstOrLastNameValidator(errors, customerUpdateForm.getFirstName(), true);
		
		customerUpdateForm.setLastName(customerUpdateForm.getLastName().trim());
		CommonValidator.firstOrLastNameValidator(errors, customerUpdateForm.getLastName(), false);			
		
		if (customerUpdateForm.getCountry() == 0){
			CommonValidator.mandatoryValidator(errors, COUNTRY_VAR, COUNTRY_REQUIRED);
		}
		
		customerUpdateForm.setAddrOne(customerUpdateForm.getAddrOne().trim());
		CommonValidator.addressValidator(errors, customerUpdateForm.getAddrOne(), ADDRES_ONE_VAR,	true);
		
		
		if(!customerUpdateForm.getAddrTwo().equals(EMPTY_STRING)){
			customerUpdateForm.setAddrTwo(customerUpdateForm.getAddrTwo().trim());
			CommonValidator.addressValidator(errors, customerUpdateForm.getAddrTwo(), ADDRES_TWO_VAR, false);
		}
		
		customerUpdateForm.setCity(customerUpdateForm.getCity().trim());
		CommonValidator.cityValidator(errors, customerUpdateForm.getCity(), CITY_VAR);
		
		if(!customerUpdateForm.getPostalCode().equals(EMPTY_STRING)){
			customerUpdateForm.setPostalCode(customerUpdateForm.getPostalCode().trim());
			CommonValidator.zipCodeValidator(errors, customerUpdateForm.getPostalCode(), POSTAL_VAR, customerUpdateForm.getCountry());
		}
		
		customerUpdateForm.setPhoneCode(customerUpdateForm.getPhoneCode().trim());
		CommonValidator.phoneCodeValidation(errors, customerUpdateForm.getPhoneCode(), PHONE_CODE_VAR, Boolean.TRUE);
			
		customerUpdateForm.setPhoneNo(customerUpdateForm.getPhoneNo().trim());
		CommonValidator.phoneNumberValidator(errors, customerUpdateForm.getPhoneNo(), PHONE_NO_VAR, Boolean.TRUE);
		
		customerUpdateForm.setDateOfBirth(customerUpdateForm.getDateOfBirth().trim());
		if (customerUpdateForm.getDateOfBirth().equals(EMPTY_STRING)) {
			CommonValidator.mandatoryValidator(errors, DATE_VAR, Common.DATE_REQUIRED);
		} else {
			CommonValidator.dateOfBirthValidation(errors, customerUpdateForm.getDateOfBirth(), DATE_VAR);
		}	
	}
}
