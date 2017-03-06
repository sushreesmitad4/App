package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.masterdata.util.CountryIds;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.AddBankAccountForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;

public class BankValidator implements Validator, Common, GlobalLitterals {
	//for country
	public static final String COUNTRY_VAR = "country";
	
	//for account holder name
	public static final String ACCOUNT_HOLDER_VAR = "accountHolderName";
	public static final Integer ACCOUNT_HOLDER_NAME_MIN_LENGTH = 1;
	public static final Integer ACCOUNT_HOLDER_NAME_MAX_LENGTH = 40;
	public static final String ACCOUNT_HOLDER_NAME_REQUIRED = "account.holder.name.required.errmsg";
	public static final String ACCOUNT_HOLDER_NAME_MATCHER = "account.holder.name.valid.errmsg";
	public static final String ACCOUNT_HOLDER_NAME_MIN_LENGTH_MSG = "account.holder.name.min.length.errmsg";
	public static final String ACCOUNT_HOLDER_NAME_MAX_LENGTH_MSG = "account.holder.name.max.length.errmsg";
	
	//for bank name
	public static final Integer BANK_NAME_MIN_LENGTH = 1;
	public static final Integer BANK_NAME_MAX_LENGTH = 60;
	public static final String BANK_NAME_VAR = "bankName";
	public static final String BANK_NAME_REQUIRED = "bankname.required.errmsg";
	public static final String BANK_NAME_MATCHER = "bankname.contains.errmsg";
	public static final String BANK_NAME_MIN_LENGTH_RANGE = "bankname.min.length.errmsg";
	public static final String BANK_NAME_MAX_LENGTH_RANGE = "bankname.max.length.errmsg";
	
	//for account type
	public static final String ACCOUNT_TYPE_VAR = "accountType";
	public static final String ACCOUNT_TYPE_REQUIRED = "account.type.required.errmsg";
	
	/* for Account Number */
	//just check field name
	public static final String ACCOUNT_NUMBER_VAR = "accountNumber";
	public static final Integer ACCOUNT_NUMBER_MIN_LENGTH = 7;
	public static final Integer ACCOUNT_NUMBER_MAX_LENGTH = 18;
	public static final String ACCOUNT_NUMBER_REQUIRED = "account.number.required.errmsg";
	public static final String ACCOUNT_NUMBER_MATCHER = "account.number.contains.errmsg";
	public static final String ACCOUNT_NUMBER_MIN_LENGTH_RANGE = "account.number.min.length.errmsg";
	public static final String ACCOUNT_NUMBER_MAX_LENGTH_RANGE = "account.number.max.length.errmsg";

	//for reaccount number
	public static final String RE_ACCOUNT_NUMBER_VAR = "reEnterAccountNumber";
	public static final String ACCNUM_RE_ACC_NUM_EQUAL = "reaccountno.notmatching.errmsg";
	
	//for bank address
	public static final String BANK_ADD_VAR = "bankAddress";
	public static final Integer BANK_ADDRESS_MIN_LENGTH = 1;
	public static final Integer BANK_ADDRESS_MAX_LENGTH = 100;
	public static final String BANK_ADDRESS_REQUIRED = "bank.address.required.errmsg";
	public static final String BANK_ADDRESS_PATTERN_MATCHER = "bank.address.contains.errmsg";
	public static final String BANK_ADD_MIN_LENGTH_RANGE = "bank.address.min.length.errmsg";
	public static final String BANK_ADD_MAX_LENGTH_RANGE = "bank.address.max.length.errmsg";

	//for routing number
	public static final String ROUTING_NUMBER_VAR = "sortCode";
	public static final Integer ROUTING_NUMBER_MIN_LENGTH = 2;
	public static final Integer ROUTING_NUMBER_MAX_LENGTH = 40;
	public static final String ROUTING_NUMBER_REQUIRED = "routnum.required.errmsg";
	public static final String ROUTING_NUMBER_MATCHER = "routnum.contains.errmsg";
	public static final String ROUTING_NUMBER_MIN_LENGTH_MSG = "routnum.min.length.errmsg";
	public static final String ROUTING_NUMBER_MAX_LENGTH_MSG = "routnum.max.length.errmsg";
	
	/* for ZINGING bank & branch code */
	//just check field name
	public static final String ZINGIN_BANK_AND_BARNCH_CODE_VAR = "sortCode";
	public static final Integer ZINGIN_BANK_AND_BARNCH_CODE_MIN_LENGTH = 2;
	public static final Integer ZINGIN_BANK_AND_BARNCH_CODE_MAX_LENGTH = 40;
	public static final String ZINGIN_BANK_AND_BARNCH_CODE_REQUIRED = "zingin.required.errmsg";
	public static final String ZINGIN_BANK_AND_BARNCH_CODE_MATCHER = "zingin.contains.errmsg";
	public static final String ZINGIN_BANK_AND_BARNCH_CODE_MIN_LENGTH_MSG = "zingin.min.length.errmsg";
	public static final String ZINGIN_BANK_AND_BARNCH_CODE_MAX_LENGTH_MSG = "zingin.max.length.errmsg";
	
	//just check field name
	public static final String BARNCH_CODE_VAR = "sortCode";
	public static final Integer BARNCH_CODE_MIN_LENGTH = 2;
	public static final Integer BARNCH_CODE_MAX_LENGTH = 40;
	public static final String BARNCH_CODE_REQUIRED = "branchcode.required.errmsg";
	public static final String BARNCH_CODE_MATCHER = "branchcode.contains.errmsg";
	public static final String BARNCH_CODE_MIN_LENGTH_MSG = "branchcode.min.length.errmsg";
	public static final String BARNCH_CODE_MAX_LENGTH_MSG = "branchcode.max.length.errmsg";

	public static final Integer US_BANK_SORT_CODE_LENGTH = 9;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		AddBankAccountForm f = (AddBankAccountForm)target;

		//for account holder name
		f.setAccountHolderName(f.getAccountHolderName().trim());
		if(f.getAccountHolderName().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, ACCOUNT_HOLDER_VAR, ACCOUNT_HOLDER_NAME_REQUIRED);
		} else{
			CommonValidator.nameValidator(errors,f.getAccountHolderName(), ACCOUNT_HOLDER_VAR,
				ACCOUNT_HOLDER_NAME_MIN_LENGTH, ACCOUNT_HOLDER_NAME_MIN_LENGTH_MSG,
				ACCOUNT_HOLDER_NAME_MAX_LENGTH, ACCOUNT_HOLDER_NAME_MAX_LENGTH_MSG,
				ACCOUNT_HOLDER_NAME_MATCHER);
		}	
		
		f.setBankName(f.getBankName().trim());
		if(f.getBankName().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, BANK_NAME_VAR, BANK_NAME_REQUIRED);
		} else{
			CommonValidator.nameValidator(errors,f.getBankName(), BANK_NAME_VAR,
				BANK_NAME_MIN_LENGTH, BANK_NAME_MIN_LENGTH_RANGE,
				BANK_NAME_MAX_LENGTH, BANK_NAME_MAX_LENGTH_RANGE, BANK_NAME_MATCHER);
		}
		
		//for accounttype
		if(f.getAccountType() == null){
			CommonValidator.nullValidator(errors, ACCOUNT_TYPE_VAR, ACCOUNT_TYPE_REQUIRED);
		}
		f.setAccountNumber(f.getAccountNumber().trim());
		if(f.getAccountNumber().equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, ACCOUNT_NUMBER_VAR, ACCOUNT_NUMBER_REQUIRED);
		} else{
			CommonValidator.stringValidator(errors,f.getAccountNumber(), ACCOUNT_NUMBER_VAR,
				ACCOUNT_NUMBER_MIN_LENGTH, ACCOUNT_NUMBER_MIN_LENGTH_RANGE,
				ACCOUNT_NUMBER_MAX_LENGTH, ACCOUNT_NUMBER_MAX_LENGTH_RANGE,
				ACCOUNT_NUMBER_EXP, ACCOUNT_NUMBER_MATCHER);

		}
		//for re account number
		if(f.getReEnterAccountNumber() != null){
			f.setReEnterAccountNumber(f.getReEnterAccountNumber().trim());
			if(f.getAccountNumber() != null && !f.getAccountNumber().equals(f.getReEnterAccountNumber())){
				CommonValidator.equalValidator(errors, RE_ACCOUNT_NUMBER_VAR, ACCNUM_RE_ACC_NUM_EQUAL);
			}
		}
		//for bank address
		if(f.getBankAddress() != null){ 
			f.setBankAddress(f.getBankAddress().trim());
			if(f.getBankAddress().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, BANK_ADD_VAR, BANK_ADDRESS_REQUIRED);
			} else{
				CommonValidator.addressValidator(errors,f.getBankAddress(),BANK_ADD_VAR,
					BANK_ADDRESS_MIN_LENGTH, BANK_ADD_MIN_LENGTH_RANGE,
					BANK_ADDRESS_MAX_LENGTH, BANK_ADD_MAX_LENGTH_RANGE,
					BANK_ADDRESS_PATTERN_MATCHER);
			}
		}
		
		//for routing number or branch code
		if(CountryIds.USD_COUNTRY_ID.equals(f.getCountry())){
			f.setSortCode(f.getSortCode().trim());
			if(f.getSortCode().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, ROUTING_NUMBER_VAR, ROUTING_NUMBER_REQUIRED);
			} else {
				CommonValidator.stringValidator(errors,f.getSortCode(), ROUTING_NUMBER_VAR,
					ROUTING_NUMBER_MIN_LENGTH, ROUTING_NUMBER_MIN_LENGTH_MSG,
					ROUTING_NUMBER_MAX_LENGTH, ROUTING_NUMBER_MAX_LENGTH_MSG,
					ROUTING_NUMBER_EXP, ROUTING_NUMBER_MATCHER);
				
				if(f.getSortCode().length() == US_BANK_SORT_CODE_LENGTH){
					if(!CommonValidator.routingNumberValidation(f.getSortCode())){
						errors.rejectValue(ROUTING_NUMBER_VAR, ROUTING_NUMBER_MATCHER);
					}
				} else {
					errors.rejectValue(ROUTING_NUMBER_VAR, ROUTING_NUMBER_MATCHER);
				}
			}
		} else if(CountryIds.THAI_COUNTRY_ID.equals(f.getCountry())){
			f.setSortCode(f.getSortCode().trim());
			if(f.getSortCode().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, BARNCH_CODE_VAR, BARNCH_CODE_REQUIRED);
			} else{
				CommonValidator.stringValidator(errors,f.getSortCode(), BARNCH_CODE_VAR,
					BARNCH_CODE_MIN_LENGTH, BARNCH_CODE_MIN_LENGTH_MSG,
					BARNCH_CODE_MAX_LENGTH, BARNCH_CODE_MAX_LENGTH_MSG,
					BARNCH_CODE_EXP, BARNCH_CODE_MATCHER);
			}
		} else{
			if(f.getSortCode()!= null){ 
				f.setSortCode(f.getSortCode().trim());
				if(f.getSortCode().equals(EMPTY_STRING)){
					CommonValidator.mandatoryValidator(errors, 
						ZINGIN_BANK_AND_BARNCH_CODE_VAR, ZINGIN_BANK_AND_BARNCH_CODE_REQUIRED);
				} else{
					CommonValidator.stringValidator(errors,f.getSortCode(), ZINGIN_BANK_AND_BARNCH_CODE_VAR,
						ZINGIN_BANK_AND_BARNCH_CODE_MIN_LENGTH, ZINGIN_BANK_AND_BARNCH_CODE_MIN_LENGTH_MSG,
						ZINGIN_BANK_AND_BARNCH_CODE_MAX_LENGTH, ZINGIN_BANK_AND_BARNCH_CODE_MAX_LENGTH_MSG,
						ZINGIN_BANK_AND_BARNCH_CODE_EXP, ZINGIN_BANK_AND_BARNCH_CODE_MATCHER);
				}
			}
		}
	}
}