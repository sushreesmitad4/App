package com.tarang.ewallet.walletui.validator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.AddMoneyForm;
import com.tarang.ewallet.walletui.form.ReloadMoneyForm;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;
public class AddMoneyValidator implements Validator, GlobalLitterals{
	
	
	
				
		public static final String ADD_MONEY_USERTYPE_FIELD = "usertype";	
		public static final String ADD_MONEY__AMOUNT_FIELD = "amount";
		
		public static final String AMOUNT_REQUIRED = "amount.required.errmsg";
		public static final String AMOUNT_EXPRESSION = "add.money.errmsg.expression";
		
		public static final String MONEY_EXCEEDS_THRESHOLD_MSG_ONE = "add.money.errmsgone.amount.exceeds";
		public static final String MONEY_EXCEEDS_THRESHOLD_MSG_TWO = "add.money.errmsgtwo.amount.exceeds";
	

		





@Override
public boolean supports(Class<?> arg0) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void validate(Object target, Errors errors) {
	// TODO Auto-generated method stub
AddMoneyForm addMoneyForm = (AddMoneyForm) target;
	
addMoneyForm.setUserType(addMoneyForm.getUserType().trim());
	if(addMoneyForm.getUserType().equals(EMPTY_STRING)){
		CommonValidator.mandatoryValidator(errors, ADD_MONEY_USERTYPE_FIELD, UserValidator.ADD_MONEY_USERTYPE_REQUIRED);
	} 	
	addMoneyForm.setAmount(addMoneyForm.getAmount().trim());
	if(addMoneyForm.getAmount().equals(EMPTY_STRING)){
		CommonValidator.mandatoryValidator(errors, ADD_MONEY__AMOUNT_FIELD, AMOUNT_REQUIRED);
	} else{
		Boolean amountError = CommonValidator.currencyValidator(errors, addMoneyForm.getAmount(), 
				ADD_MONEY__AMOUNT_FIELD, AMOUNT_EXPRESSION, null);
		if(!amountError && Double.parseDouble(addMoneyForm.getAmount()) == 0){
			CommonValidator.mandatoryValidator(errors, ADD_MONEY__AMOUNT_FIELD, AMOUNT_REQUIRED);
		}
	}
	
}


}
