package com.tarang.ewallet.walletui.validator;

import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.AddCardAccountForm;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class CardValidator implements Validator, GlobalLitterals{
		
		public static final Long CARD_STATUS_VARIFIED = 4L;
		public static final String CARD_HOLDER_VAR = "accountHolderName";
		public static final Integer CARD_HOLDER_NAME_MIN_LENGTH = 1;
		public static final Integer CARD_HOLDER_NAME_MAX_LENGTH = 25;
		public static final Integer CVV_NUMBER_DEFAULT_CARD_LENGTH = 3;
		public static final Integer  CVV_NUMBER_AMERICANEXP_CARD_LENGTH = 4;
		
		
		public static final String CARD_HOLDER_NAME_REQUIRED = "card.holder.name.required.errmsg";
		public static final String CARD_HOLDER_MATCHER = "card.holder.name.contains.errmsg";
		public static final String CARD_HOLDER_NAME_MIN_LENGTH_RANGE = "card.holder.name.min.length.errmsg";
		public static final String CARD_HOLDER_NAME_MAX_LENGTH_RANGE = "card.holder.name.max.length.errmsg";
		
		public static final String CARD_TYPE_VAR = "cardType";
		public static final String CARD_TYPE_REQUIRED = "cardtype.required.errmsg";

		public static final String CARD_NUMBER_VAR = "cardNumber";
		public static final String CARD_NUMBER_REQUIRED = "cardno.required.errmsg";

		/*public static final String ISSUE_DATE_VAR = "issueDateMonth";
		public static final String ISSUE_DATE_MONTH="common.issuedate.errmsg.month";*/

		public static final String EXP_DATE_VAR = "expireDateMonth";
		public static final String EXP_DATE_REQUIRED = "card.expdate.required.errmsg";
		public static final String EXPIRE_DATE_MONTH = "expire.date.required.errmsg";
		
		public static final String CVV_NUMBER_VAR = "cvv";
		public static final String CVV_NUMBER_REQUIRED = "cvv.required.errmsg";
		public static final String CVV_NUMBER_DEFAULT_LENGTH_RANGE = "cvv.default.length.errmsg";
		public static final String CVV_NUMBER_AMERICANEXP_LENGTH_RANGE = "cvv.american.exp.length.errmsg";
		public static final String CVV_NUMBER_EXPRESSION = "cvv.expression.errmsg";
		
		public static final String COUNTRY_VAR = "country";
		public static final String COUNTRY_REQUIRED = "country.required.errmsg";
		
		public static final String ADDRES_ONE_VAR = "addrOne";
		public static final String ADDRES_TWO_VAR = "addrTwo";
		public static final String CITY_VAR = "city";
		public static final String POSTAL_VAR = "postalCode";
		
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		AddCardAccountForm addCardAccountForm = (AddCardAccountForm) target;
		
		if(addCardAccountForm.getStatus() != null && !addCardAccountForm.getStatus().equals(CARD_STATUS_VARIFIED)){
			addCardAccountForm.setAccountHolderName(addCardAccountForm.getAccountHolderName().trim());
			if(addCardAccountForm.getAccountHolderName().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, CARD_HOLDER_VAR, CARD_HOLDER_NAME_REQUIRED);
			} else {
				CommonValidator.nameValidator(errors,addCardAccountForm.getAccountHolderName(),CARD_HOLDER_VAR,
					CARD_HOLDER_NAME_MIN_LENGTH, CARD_HOLDER_NAME_MIN_LENGTH_RANGE,
					CARD_HOLDER_NAME_MAX_LENGTH, CARD_HOLDER_NAME_MAX_LENGTH_RANGE,
					CARD_HOLDER_MATCHER);
			}
			
			if(addCardAccountForm.getCardType() == null || addCardAccountForm.getCardType().equals(0L)){
				CommonValidator.nullValidator(errors, CARD_TYPE_VAR, CARD_TYPE_REQUIRED);
			}
			// this condition is to validate card number
			if(addCardAccountForm.getCardType() == null || !addCardAccountForm.getCardType().equals(0L)){  
				// only  user select card type, other wise we ignore
				addCardAccountForm.setCardNumber(addCardAccountForm.getCardNumber().trim());               
				if(addCardAccountForm.getCardNumber().equals(EMPTY_STRING)){
					CommonValidator.mandatoryValidator(errors, CARD_NUMBER_VAR, CARD_NUMBER_REQUIRED);
				} else {
					CommonValidator.cardValidator(errors, addCardAccountForm.getCardNumber(), addCardAccountForm.getCardType());
				}
				
				/*if( (addCardAccountForm.getIssueDateMonth() != 0 || addCardAccountForm.getIssueDateYear() != 0)
						&& (addCardAccountForm.getIssueDateYear() == Calendar.getInstance().get(Calendar.YEAR)) ){
					Long month = addCardAccountForm.getIssueDateMonth();
					Calendar cal = Calendar.getInstance();
					int curMonth = cal.get(Calendar.MONTH);
					curMonth = curMonth + 1;
					if(month > curMonth){
						errors.rejectValue(ISSUE_DATE_VAR, ISSUE_DATE_MONTH);
					}
				}*/
			}
		}
		if(addCardAccountForm.getExpireDateMonth() == 0 || addCardAccountForm.getExpireDateYear() == 0){
			CommonValidator.mandatoryValidator(errors, EXP_DATE_VAR,EXP_DATE_REQUIRED);
		} else {
			if(addCardAccountForm.getExpireDateYear() == Calendar.getInstance().get(Calendar.YEAR)){
				Long month = addCardAccountForm.getExpireDateMonth();
				Calendar cal = Calendar.getInstance();
				int curMonth = cal.get(Calendar.MONTH);
				curMonth = curMonth + 1;
				if(month < curMonth){
					errors.rejectValue(EXP_DATE_VAR, EXPIRE_DATE_MONTH);
				}
			}	
		}
		/* this condition is to validate cvv number */
		if(addCardAccountForm.getCardType() == null || !addCardAccountForm.getCardType().equals(0L)){	
			/* only  user select card type, other wise we ignore  */
			addCardAccountForm.setCvv(addCardAccountForm.getCvv().trim());                           
			if(addCardAccountForm.getCvv().equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, CVV_NUMBER_VAR, CVV_NUMBER_REQUIRED);
			} else {
				CommonValidator.cvvValidator(errors, addCardAccountForm.getCvv(), addCardAccountForm.getCardType(),
						CVV_NUMBER_VAR, CVV_NUMBER_DEFAULT_CARD_LENGTH, CVV_NUMBER_DEFAULT_LENGTH_RANGE, 
						CVV_NUMBER_AMERICANEXP_CARD_LENGTH, CVV_NUMBER_AMERICANEXP_LENGTH_RANGE, CVV_NUMBER_EXPRESSION);	
			}
		}
		if(!addCardAccountForm.getIsSameAsProfileAddress()){
			if (addCardAccountForm.getCountry() == 0L) {
				CommonValidator.mandatoryValidator(errors, COUNTRY_VAR, COUNTRY_REQUIRED);
			}
			
			addCardAccountForm.setAddrOne(addCardAccountForm.getAddrOne().trim());
			CommonValidator.addressValidator(errors, addCardAccountForm.getAddrOne(), ADDRES_ONE_VAR, true);
			
			addCardAccountForm.setAddrTwo(addCardAccountForm.getAddrTwo().trim());
			CommonValidator.addressValidator(errors, addCardAccountForm.getAddrTwo(), ADDRES_TWO_VAR, false);
			
			addCardAccountForm.setCity(addCardAccountForm.getCity().trim());
			CommonValidator.cityValidator(errors, addCardAccountForm.getCity(), CITY_VAR);
			
			if(!addCardAccountForm.getPostalCode().equals(EMPTY_STRING)){
				addCardAccountForm.setPostalCode(addCardAccountForm.getPostalCode().trim());
				CommonValidator.zipCodeValidator(errors, addCardAccountForm.getPostalCode(), POSTAL_VAR, addCardAccountForm.getCountry());
			}
		}
	}
}