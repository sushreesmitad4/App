package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.RoleForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class RoleValidator implements Validator, GlobalLitterals, Common {
	
    /**
     * Please refer the hbm file for the max length
     */
	public static final int NAME_MAX_LENGTH = 25;
	public static final int DESCRIPTION_MAX_LENGTH = 200;
	
	public static final String NAME_VAR = "name";
	public static final String DESCRIPTION_VAR = "description";
	public static final String MENU_VAR = "menuDetails";

	// message string
	public static final String NAME_REQUIRED = "role.name.required.errmsg";
	public static final String NAME_LENGTH = "role.name.lengthrange.errmsg";
	public static final String NAME_PATTERN_MATCHER = "role.name.contains.errmsg";
	public static final String NAME_UNIQUE = "role.already.exist.errmsg";

	public static final String DESCRIPTION_REQUIRED = "role.description.required.errmsg";
	public static final String DESCRIPTION_LENGTH = "role.description.lengthrange.errmsg";
	public static final String DESCRIPTION_PATTERN_MATCHER = "role.description.contains.errmsg";

	public static final String FUNCTION_REQUIRED = "role.function.required.errmsg";

	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		RoleForm roleForm = (RoleForm) target;
		//for role name field
		roleForm.setName(roleForm.getName().trim());
		if(roleForm.getName().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, NAME_VAR, NAME_REQUIRED);
		} else {
			CommonValidator.stringValidator(errors,roleForm.getName(), 
					NAME_VAR, null, null, NAME_MAX_LENGTH, NAME_LENGTH, NAME_EXPRESSION, NAME_PATTERN_MATCHER);
		}
	
		// validation for description text field
		roleForm.setDescription(roleForm.getDescription().trim());
		if(roleForm.getDescription().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, DESCRIPTION_VAR, DESCRIPTION_REQUIRED);
		} else {
			CommonValidator.stringValidator(errors, roleForm.getDescription(), DESCRIPTION_VAR, null, null, 
					DESCRIPTION_MAX_LENGTH, DESCRIPTION_LENGTH, NAME_EXPRESSION, DESCRIPTION_PATTERN_MATCHER);	
		}

		// validation for function ListBox
		roleForm.setMenuDetails(roleForm.getMenuDetails().trim());
		if(roleForm.getMenuDetails().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors,MENU_VAR, FUNCTION_REQUIRED);
		}
	}
}