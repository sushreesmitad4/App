package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.AdminUserForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class UserValidator implements Validator, GlobalLitterals, Common{

	public static final String CITY_VAR = "city";
	public static final String ADDRESS_ONE_VAR = "addressOne";
	public static final String ADDRESS_TWO_VAR = "addressTwo";
	public static final String EMAIL_VAR = "emailId";
	public static final String ZIPCODE_VAR = "zipcode";
	public static final String COUNTRY_VAR = "countryId";
	public static final String STATUS_VAR = "status";
	public static final String ROLE_VAR = "roleId";
	public static final String PHONE_CODE_VAR = "phoneCode";
	public static final String PHONE_NO_VAR = "phoneNo";
	
	// message strings
	public static final String EMAIL_REQUIRED = "email.required.errmsg";
	public static final String EMAIL_VALIDATE = "email.contains.errmsg";

	public static final String COUNTRY_REQUIRED = "country.required.errmsg";

	public static final String ROLE_REQUIRED = "admin.role.select";
	
	public static final String NAME_DEFAULT_EXPRESSION = "admin.name.default.expression";

	public static final String UNKNOW_SAVE_ERROR = "admin.unknow.save.exception";
	public static final String CREATE_SUCCESS_MSG = "admin.create.successmsg";
	public static final String UPDATE_SUCCESS_MSG = "admin.update.successmsg";
	public static final String PATTERN_MATCHER = "admin.name.pattern";
	public static final String ADMIN_USER_DELETE_MSG = "admin.delete.successmsg";
	public static final String ADMIN_USER_DELETE_FAIL_MSG = "admin.delete.failmsg";
	public static final String NAME_NUMBER_MATCHER = "admin.name.number";
	public static final String FAIL_TO_LOAD_REGIONS = "fail.to.load.regions.errmsg";
	public static final String NO_RECORDS_FOUND = "no.records.found.errmsg";
	public static final String RESET_PASSWORD_FAIL = "reset.password.fail.errmsg";
	public static final String ADD_MONEY_USERTYPE_REQUIRED = "add.money.usertype.errmsg";
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return AdminUserForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		AdminUserForm s = (AdminUserForm) target;
		//firstname field
		s.setFirstName(s.getFirstName().trim());
		CommonValidator.firstOrLastNameValidator(errors, s.getFirstName(), true);
		
		//lastName filed
		s.setLastName(s.getLastName().trim());
		CommonValidator.firstOrLastNameValidator(errors, s.getLastName(), false);
		
		//couuntyId field
		if(s.getCountryId().equals(0L)){
			CommonValidator.mandatoryValidator(errors, COUNTRY_VAR, COUNTRY_REQUIRED);
		}
		
		//city field
		s.setCity(s.getCity().trim());
		CommonValidator.cityValidator(errors, s.getCity(), CITY_VAR);
		
		//addressOne field
		s.setAddressOne(s.getAddressOne().trim());
		CommonValidator.addressValidator(errors, s.getAddressOne(), ADDRESS_ONE_VAR, true);
		
		
		//addressTwo field
		s.setAddressTwo(s.getAddressTwo().trim());
		if(!s.getAddressTwo().equals(EMPTY_STRING)){
			CommonValidator.addressValidator(errors, s.getAddressTwo(), ADDRESS_TWO_VAR, false);
		}
		
		//zipcode field
		s.setZipcode(s.getZipcode().trim());
		if(!s.getZipcode().equals(EMPTY_STRING)){
			CommonValidator.zipCodeValidator(errors, s.getZipcode(), ZIPCODE_VAR , s.getCountryId());
		}
		
		//PhoneCode field
		s.setPhoneCode(s.getPhoneCode().trim());
		CommonValidator.phoneCodeValidation(errors, s.getPhoneCode(), PHONE_CODE_VAR, Boolean.TRUE);
		
		//phone number
		s.setPhoneNo(s.getPhoneNo().trim());
		CommonValidator.phoneNumberValidator(errors, s.getPhoneNo(), PHONE_NO_VAR, Boolean.TRUE);
		
		//Role field
		if(s.getRoleId() != null && s.getRoleId().equals(0L)){
			CommonValidator.mandatoryValidator(errors, ROLE_VAR, ROLE_REQUIRED);
		}
		
		//for edit page emailId field is ready only
		if(s.getEmailId() != null){
			s.setEmailId(s.getEmailId().trim());
			CommonValidator.emailValidation(errors, s.getEmailId(), EMAIL_VAR);
		}
		if(s.getActive() != null && s.getDeleted() != null){
			if(validateActive(s)){
				validateUpdateReason(s, errors);
			} else if(validateDelete(s)){
				validateUpdateReason(s, errors);
			}
		}
	}
	
	private void validateUpdateReason(AdminUserForm form, Errors errors){
		if(form.getUpdateReason().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, UPDATE_REASON_VAR, UPDATE_REASON_REQUIRED);
		} else{
			CommonValidator.addressValidator(errors, form.getUpdateReason(), UPDATE_REASON_VAR,
					MESSAGE_MIN_LENGTH, UPDATE_REASON_MIN_LENGTH_RANGE,
					MESSAGE_MAX_LENGTH, UPDATE_REASON_MAX_LENGTH_RANGE,
					UPDATE_REASON_MATCHER);
		}
	}
	
	private Boolean validateActive(AdminUserForm form){
		return form.getActive() != null && !form.getOldActive().equals(form.getActive());
	}
	
	private Boolean validateDelete(AdminUserForm form){
		return form.getDeleted() != null && !form.getOldDeleted().equals(form.getDeleted());
	}
}