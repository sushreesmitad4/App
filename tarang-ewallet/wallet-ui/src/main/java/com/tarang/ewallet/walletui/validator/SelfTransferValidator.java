package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.SelfTransferForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class SelfTransferValidator implements Validator, GlobalLitterals, Common {
	
	public static final String FROM_WALLET_VAR = "fromWallet";
	public static final String TO_WALLET_VAR = "towallet";
	public static final String FROM_WALLET_REQUIRED = "fromwallet.errmsg.required";
	public static final String TO_WALLET_REQUIRED = "towallet.errmsg.required";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		SelfTransferForm selfTransferForm = (SelfTransferForm)target;
		
		if( ZERO_LONG.equals(selfTransferForm.getFromWallet())){
			CommonValidator.mandatoryValidator(errors, FROM_WALLET_VAR, FROM_WALLET_REQUIRED );
		}
		
		if( ZERO_LONG.equals(selfTransferForm.getTowallet()) ){
			CommonValidator.mandatoryValidator(errors, TO_WALLET_VAR,TO_WALLET_REQUIRED );
		}
		
		selfTransferForm.setRequestedAmount(selfTransferForm.getRequestedAmount().trim());
		if(selfTransferForm.getRequestedAmount().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, AMOUNT_VAR, AMOUNT_REQUIRED );
		}else{
			CommonValidator.realNumberValidator(errors, selfTransferForm.getRequestedAmount(),
					AMOUNT_VAR, SEND_MONEY_MIN_LIMIT, AMOUNT_ERROR_FORMAT, SEND_MONEY_MAX_LIMIT,
					SEND_MONEY_MAX_ERR_MSG, AMOUNT_ERROR_FORMAT);
		}
	}		
}