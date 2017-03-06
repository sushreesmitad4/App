package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.ReloadMoneyForm;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class ReloadMoneyValidator implements Validator, GlobalLitterals{
		
	public static final String RELOAD_MONEY_CVV_FIELD = "cvv";	
	public static final String RELOAD_MONEY_AMOUNT_FIELD = "amount";
	
	public static final String AMOUNT_REQUIRED = "amount.required.errmsg";
	public static final String AMOUNT_EXPRESSION = "reload.money.errmsg.expression";
	
	public static final String MONEY_EXCEEDS_THRESHOLD_MSG_ONE = "reload.money.errmsgone.amount.exceeds";
	public static final String MONEY_EXCEEDS_THRESHOLD_MSG_TWO = "reload.money.errmsgtwo.amount.exceeds";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ReloadMoneyForm reloadMoneyForm = (ReloadMoneyForm) target;
		
		reloadMoneyForm.setCvv(reloadMoneyForm.getCvv().trim());
		if(reloadMoneyForm.getCvv().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, RELOAD_MONEY_CVV_FIELD, CardValidator.CVV_NUMBER_REQUIRED);
		} else{
			CommonValidator.cvvValidator(errors, reloadMoneyForm.getCvv(), reloadMoneyForm.getCardType(),
					RELOAD_MONEY_CVV_FIELD, CardValidator.CVV_NUMBER_DEFAULT_CARD_LENGTH, CardValidator.CVV_NUMBER_DEFAULT_LENGTH_RANGE, 
					CardValidator.CVV_NUMBER_AMERICANEXP_CARD_LENGTH, CardValidator.CVV_NUMBER_AMERICANEXP_LENGTH_RANGE, CardValidator.CVV_NUMBER_EXPRESSION);	
		}
		
		reloadMoneyForm.setAmount(reloadMoneyForm.getAmount().trim());
		if(reloadMoneyForm.getAmount().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, RELOAD_MONEY_AMOUNT_FIELD, AMOUNT_REQUIRED);
		} else{
			Boolean amountError = CommonValidator.currencyValidator(errors, reloadMoneyForm.getAmount(), 
					RELOAD_MONEY_AMOUNT_FIELD, AMOUNT_EXPRESSION, null);
			if(!amountError && Double.parseDouble(reloadMoneyForm.getAmount()) == 0){
				CommonValidator.mandatoryValidator(errors, RELOAD_MONEY_AMOUNT_FIELD, AMOUNT_REQUIRED);
			}
		}
	}
}