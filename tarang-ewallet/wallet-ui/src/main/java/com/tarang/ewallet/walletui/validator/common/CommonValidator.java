/**
 * 
 */
package com.tarang.ewallet.walletui.validator.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.validator.DateDuration;



/**
 * @author  : prasadj
 * @date    : Dec 1, 2012
 * @time    : 11:38:19 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CommonValidator implements Common, GlobalLitterals {

	private static final Logger LOGGER = Logger.getLogger(CommonValidator.class);
	
	public static void  nameValidator(Errors errors, String value, String fieldName,
			Integer minLength, String minLengthMsg,
			Integer maxLength, String maxLengthMsg, 
			String expressionMsg){
		if(minLength != null && minLength != 0 && value.length() < minLength){
			errors.rejectValue(fieldName, minLengthMsg);
			return;
		}
		if(maxLength != null && maxLength != 0 && value.length() > maxLength){
			errors.rejectValue(fieldName, maxLengthMsg);
			return;
		}
		if(!expressionPattern(NAME_EXPRESSION, value )){
			errors.rejectValue(fieldName, expressionMsg);
			return;
		}
	}
	
	public static void  addressValidator(Errors errors, String value, String fieldName,
			Integer minLength, String minLengthMsg, Integer maxLength, String maxLengthMsg, 
			String expressionMsg){
		if(minLength != null && minLength != 0 && value.length() < minLength){
			errors.rejectValue(fieldName, minLengthMsg);
			return;
		}
		if(maxLength != null && maxLength != 0 && value.length() > maxLength){
			errors.rejectValue(fieldName, maxLengthMsg);
			return;
		}
		if(!expressionPattern(ADDRESS_EXPRESSION, value )){
			errors.rejectValue(fieldName, expressionMsg);
			return;
		}
	}
	
	public static void  messageValidator(Errors errors, String value, String fieldName,
			Integer minLength, String minLengthMsg, Integer maxLength, String maxLengthMsg, 
			String expressionMsg){
		if(minLength != null && minLength != 0 && value.length() < minLength){
			errors.rejectValue(fieldName, minLengthMsg);
			return;
		}
		if(maxLength != null && maxLength != 0 && value.length() > maxLength){
			errors.rejectValue(fieldName, maxLengthMsg);
			return;
		}
		if(!expressionPattern(MESSAGE_EXPRESSION, value )){
			errors.rejectValue(fieldName, expressionMsg);
			return;
		}
	}

	public static void  stringValidator(Errors errors, String value, String fieldName,
			Integer minLength, String minLengthMsg, Integer maxLength, String maxLengthMsg, 
			String expression, String expressionMsg){
		if(minLength != null && minLength != 0 && value.length() < minLength){
			errors.rejectValue(fieldName, minLengthMsg);
			return;
		}
		if(maxLength != null && maxLength != 0 && value.length() > maxLength){
			errors.rejectValue(fieldName, maxLengthMsg);
			return;
		}
		if(expression != null && !expression.equals(EMPTY_STRING) && !expressionPattern(expression,value )){
			errors.rejectValue(fieldName, expressionMsg);
			return;
		}
	}
	
	public static Boolean  numberValidator(Errors errors, String value, String fieldName,
			Integer minLength, String minLengthMsg, Integer maxLength, String maxLengthMsg, 
			String expressionMsg){
		if(minLength != null && minLength != 0 && value.length() < minLength){
			errors.rejectValue(fieldName, minLengthMsg);
			return false;
		}
		if(maxLength != null && maxLength != 0 && value.length() > maxLength){
			errors.rejectValue(fieldName, maxLengthMsg);
			return false;
		}
		if(!expressionPattern(NUMBER_EXPRESSION, value )){
			errors.rejectValue(fieldName, expressionMsg);
			return false;
		}
		return true;
	}

	public static Boolean groupPatternCheckValidator(String value, char[] pattern){
		for(int i = 0 ; i < pattern.length; i++){
			if(!groupPatternCheckValidator(value, pattern[i])){
				return true;
			}
		}
		return false;
	}
	
	public static Boolean  groupPatternCheckValidator(String value, char pattern){
		String expression = "[" + pattern +  "]*";
		if(expressionPattern(expression, value )){
			return false;
		} else {
			return true;
		}
	}

	public static void cardValidator(Errors errors, String value, Long type){
		if(!expressionPattern(NUMBER_EXPRESSION, value )){
			errors.rejectValue(Common.CARD_NUMBER_FIELD, Common.CARD_NUMBER_ERROR_MSG);
			return;
		}
		if(!(new CardNumberValidator()).validate(value, type)){
			errors.rejectValue(Common.CARD_NUMBER_FIELD, Common.CARD_NUMBER_ERROR_MSG);
			return;
		}
	}
	
	public static void mandatoryValidator(Errors errors, String fieldName, String errorMsg){
		errors.rejectValue(fieldName, errorMsg);
	}
	
	public static void nullValidator(Errors errors, String fieldName, String errorMsg){
		errors.rejectValue(fieldName, errorMsg);
	}
	
	public static void equalValidator(Errors errors, String fieldName, String errorMsg){
		errors.rejectValue(fieldName, errorMsg);
	}
	
	public static void mandatoryValidator(Errors errors, Long value, String fieldName, String errorMsg){
		if (value == 0L) {
			errors.rejectValue(fieldName, errorMsg);
		}
	}
	
	public static void  firstOrLastNameValidator(Errors errors, String value, boolean firstOrlastName){
		
		String fieldName = null;
		if(firstOrlastName)	{
			fieldName = "firstName";
		} else {
			fieldName = "lastName";
		}
		
		if(  value.equals(EMPTY_STRING) ){
			if(firstOrlastName)	{
				mandatoryValidator(errors, fieldName, FIRST_NAME_REQUIRED);
			} else {
				mandatoryValidator(errors, fieldName, LAST_NAME_REQUIRED);
			}
		} else {
			if(firstOrlastName)	{
				nameValidator(errors, value, fieldName, FIRST_NAME_MIN_LENGTH, FIRST_NAME_REQUIRED, 
						FIRST_NAME_MAX_LENGTH, FIRST_NAME_MAX_LENGTH_MSG, FIRST_NAME_MATCHER_MSG);
			} else {
				nameValidator(errors, value, fieldName, LAST_NAME_MIN_LENGTH, LAST_NAME_REQUIRED, 
						LAST_NAME_MAX_LENGTH, LAST_NAME_MAX_LENGTH_MSG, LAST_NAME_MATCHER_MSG);
			}
		}
	}
	
	public static void phoneCodeValidation(Errors errors, String value, String fieldName, Boolean mandatory){
		if(value.equals(EMPTY_STRING) ){
			if(mandatory){
				mandatoryValidator(errors, fieldName, PHONE_CODE_REQUIRED);
			}
		} else {
			Boolean flag = numberValidator(errors, value, fieldName, PHONE_CODE_MIN_LENGTH, PHONE_NUMBER_MIN_LENGTH_MSG, 
					PHONE_CODE_MAX_LENGTH, PHONE_CODE_LENGTH_MAX_MSG, PHONE_CODE_PATTERN_MATCHER);
			if(flag){
				if(groupPatternCheckValidator(value, PHONE_NUMBER_ZERO_CHECK)){
					errors.rejectValue(fieldName, PHONECODE_CONTAINS_VALID_ERRMSG);
				}
			}
		}
	}
	
	public static void  phoneNumberValidator(Errors errors, String value,String fieldName, Boolean mandatory){
		if(value.equals(EMPTY_STRING) ){
			if(mandatory){
				mandatoryValidator(errors, fieldName, PHONE_NUMBER_REQUIRED);
			}
		} else {
			Boolean flag = numberValidator(errors, value, fieldName, PHONE_NUMBER_MIN_LENGTH, PHONE_NUMBER_MIN_LENGTH_MSG, 
					PHONE_NUMBER_MAX_LENGTH, PHONE_NUMBER_MAX_LENGTH_MSG, PHONE_NUMBER_MATCHER_MSG);
			if(flag){
				if(groupPatternCheckValidator(value, PHONE_NUMBER_ZERO_CHECK)){
					errors.rejectValue(fieldName, PHONENUMBER_CONTAINS_VALID_ERRMSG);
				}
			}
		}
	}
	
	public static void  cityValidator(Errors errors, String value,String fieldName){
		if( value.equals(EMPTY_STRING) ){
			mandatoryValidator(errors, fieldName, CITY_REQUIRED);
		} else {
			nameValidator(errors, value, fieldName, CITY_MIN_LENGTH, CITY_MIN_LENGTH_MSG, 
					CITY_MAX_LENGTH, CITY_MAX_LENGTH_MSG, CITY_MATCHER_MSG);
		}
	}
	
	public static void  zipCodeValidator(Errors errors, String value, String zipCode, Long countryId){
		if(countryId.equals(1L)){
			stringValidator(errors, value, zipCode, null, null, 
					null, null, POSTAL_CODE_EXP_US, POSTAL_CODE_MATCHER_MSG_US);
		} else if(countryId.equals(2L)){
			stringValidator(errors, value, zipCode, null, null, 
					null, null, POSTAL_CODE_EXP_JP, Common.POSTAL_CODE_MATCHER_MSG_JP);
		} else {
			stringValidator(errors, value, zipCode, POSTAL_CODE_MIN_LENGTH, POSTAL_CODE_MIN_LENGTH_MSG, 
					POSTAL_CODE_MAX_LENGTH, POSTAL_CODE_MAX_LENGTH_MSG, POSTAL_CODE_EXP, POSTAL_CODE_MATCHER_MSG);
		}
	}
   
	public static void  passwordValidator(Errors errors, String value, String fieldName, String pw){
		if( value.equals(EMPTY_STRING) ){
			mandatoryValidator(errors, fieldName, PASSWORD_REQUIRED);
		} else {
			if(pw != null && pw.equals(value)){
				mandatoryValidator(errors, fieldName, EMAIL_AS_PASSWORD);
			}
			passwordMinMaxExpValiHelper(errors, value, fieldName, PASSWORD_MIN_LENGTH, PASSWORD_MIN_LENGTH_MSG, 
					PASSWORD_MAX_LENGTH, PASSWORD_MAX_LENGTH_MSG, PASSWORD_EXP, PASSWORD_MATCHER_MSG);
		}		
	}
	
	public static void  newPasswordSameAsOldPasswordValidator(Errors errors, String newPass, String oldPass, String fieldName){
		if( !newPass.equals(EMPTY_STRING) && newPass.equals(oldPass) ){
			mandatoryValidator(errors, fieldName, NEW_AS_OLD_PASSWORD);
		}
			
	}
   
   public static void  passwordMinMaxExpValiHelper(Errors errors, String value, String fieldName,
			Integer minLength, String minLengthMsg, Integer maxLength, String maxLengthMsg, 
			String expression, String expressionMsg){
		if(minLength != null && minLength != 0 && value.length() < minLength){
			errors.rejectValue(fieldName, minLengthMsg);
			return;
		}
		if(maxLength != null && maxLength != 0 && value.length() > maxLength){
			errors.rejectValue(fieldName, maxLengthMsg);
			return;
		}
		if(!passwordValiExpHelper(value)){
			errors.rejectValue(fieldName, expressionMsg);
			return;
		}
		
	}
   
	public static boolean passwordValiExpHelper(String param) {
		int i = 0;
		if(param.matches(PASSWORD_REGEXP_NUMBER)){
			i++;
		}
		if(param.matches(PASSWORD_REGEXP_LOWER_CASE)){
			i++;
		}
		if(param.matches(PASSWORD_REGEXP_UPPER_CASE)){
			i++;
		}
		if(param.matches(PASSWORD_REGEXP_SPECIAL_CAHR)){
			i++;
		}
		boolean flag = false;
		/* updated from 2, need to support all as per PG */
		if(i >= 4){
			flag = true;
		} else {
			flag = false;
		}
		return  flag;
	}

	public static void confirmPasswordValidation(Errors errors, String password, String confPassword, String fieldName){
		if(!password.equals(confPassword)){
		   CommonValidator.mandatoryValidator(errors, fieldName, CONF_PASSWORD_MISMATCH);
		}
	}
   
	/*Old password does not required password policy, because it has to allow random generated passwords*/
	public static void oldPasswordValidation(Errors errors, String oldPassword, String fieldName){ 
		if (oldPassword.equals(EMPTY_STRING)) {
			CommonValidator.mandatoryValidator(errors, fieldName, OLD_PASSWORD_REQUIRED);
		} else if (oldPassword.length() > PASSWORD_MAX_LENGTH ) {
			CommonValidator.mandatoryValidator(errors, fieldName, PASSWORD_LENGTH);
		}
	}
	
	public static void emailValidation(Errors errors, String value, String fieldName){
		if( value.equals(EMPTY_STRING) ){
			mandatoryValidator(errors, fieldName, EMAIL_REQUIRED);
		} else {
			stringValidator(errors, value, fieldName, null, null, 
					EMAIL_MAX_LENGTH, EMAIL_MAX_LENGTH_MSG, EMAIL_PATTERN, EMAIL_MATCHER_MSG);
		}
	}
   
	public static void confirmEmailValidation(Errors errors, String email, String confEmail, String fieldName){
		if(!email.equals(confEmail)){
		   CommonValidator.mandatoryValidator(errors, fieldName, CONF_EMAIL_MISMATCH);
		}
	}
   
	public static void addressValidator(Errors errors, String value, String fieldName, Boolean isAddressOne) {
		if (isAddressOne) {
			if(value.equals(EMPTY_STRING)){
				errors.rejectValue(fieldName, ADDR_ONE_REQUIRED);
				return;
			}
			if (value.length() < ADDRESS_ONE_MIN_LENGTH) {
				errors.rejectValue(fieldName, ADDR_ONE_MIN_LENGTH_MSG);
				return;
			}
			if (value.length() > ADDRESS_ONE_MAX_LENGTH) {
				errors.rejectValue(fieldName, ADDR_ONE_MAX_LENGTH_MSG);
				return;
			}
			if (!expressionPattern(ADDRESS_EXPRESSION, value)) {
				errors.rejectValue(fieldName, ADDR_ONE_EXPRESSION_MSG);
				return;
			}
		} else {
			if (value.length() > ADDRESS_TWO_MAX_LENGTH) {
				errors.rejectValue(fieldName, ADDR_TWO_MAX_LENGTH_MSG);
				return;
			}
			if (!expressionPattern(ADDRESS_EXPRESSION, value)) {
				errors.rejectValue(fieldName, ADDR_TWO_EXPRESSION_MSG);
				return;
			}
		}
	}
	
	public static void answerValidator(Errors errors, String value, String fieldName){
		if(value.equals(EMPTY_STRING)){
			errors.rejectValue(fieldName, ANSWER_REQUIRED);
			return;
		} else{
			if (value.length() < ANSWER_MIN_LENGTH){
				errors.rejectValue(fieldName, ANSWER_MIN_LENGTH_MSG);
				return;
			}
			if (value.length() > ANSWER_MAX_LENGTH){
				errors.rejectValue(fieldName, ANSWER_MAX_LENGTH_MSG);
				return;
			}
		}
	}
	
	public static void dateOfBirthValidation(Errors errors, String dateOfBirth, String fieldName){
		Long duration = 0L;
		if(dateValidation(AttributeConstants.DATE_FORMAT, dateOfBirth) && DateDuration.validateDOJ(dateOfBirth)){
			try {
				Date givendate = DateConvertion.stirngToDate(dateOfBirth);
				duration = DateDuration.dateDiff(givendate, new Date());
			} catch (Exception e) {
				LOGGER.error(e.getMessage() , e);
			}
			if(duration < AGE_MIN){
				errors.rejectValue(fieldName, DATE_REQUIRED_MIN_AGE);
			} else if(duration > AGE_MAX){
				errors.rejectValue(fieldName, DATE_REQUIRED_MAX_AGE);
			}
		} else { 
			errors.rejectValue(fieldName, DATE_PATTERN_MATCHER);	
		}
	
	}
	
	public static void websiteValidation(Errors errors, String website, String fieldName){
		CommonValidator.stringValidator(errors, website, fieldName, WEBSITE_MIN_LENGTH, WEBSITE_MIN_LENGTH_MSG, 
				WEBSITE_MAX_LENGTH, WEBSITE_MAX_LENGTH_MSG, WEBSITE_EXP, WEBSITE_EXPRESSION_MSG);
	}
   
	public static void legalNameAndFLNameValidator(Errors errors, String legalName, String flName, String fieldName){
		if(legalName.equals(flName)){
			CommonValidator.mandatoryValidator(errors, fieldName, LEGAL_NAME_AND_NAME_PATTERN_MATCHER);
		}
	}
	
    /**
     *  verifies the value is valid for given expression pattern.
     * @param expressionStr
     * @param value
     * @return
     */
    public static boolean expressionPattern(String expressionStr, String value){
        try{
            return Pattern.matches(expressionStr, value);
        } catch(PatternSyntaxException pse){
        	LOGGER.error(pse.getMessage() , pse );
            return true;
        }
    }

    /**
     * It validates the given date value format is it like description format.
     * if the description value is null/empty then it will validate with the "dd/MM/yyyy" format
     *
     * @param date
     * @param description
     * @return valid
     */
    public static boolean dateValidation(String expessionStr, String dateValue){
        boolean valid = false;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(expessionStr);
            sdf.setLenient(false);
            sdf.parse(dateValue);
            valid = true;
        } catch(Exception ex){
        	LOGGER.error(ex.getMessage() , ex );
            valid = false;
        }
        return valid;
    }
    
	public static boolean routingNumberValidation(String rnumber){
		int n = 0;
		final int three = 3;
		final int seven = 7;
		final int ten = 10;
		for (int i = 0; i < rnumber.length() - 1; i += three) {
			n += Character.getNumericValue(rnumber.charAt(i)) * three
					+ Character.getNumericValue(rnumber.charAt(i + 1)) * seven
					+ Character.getNumericValue(rnumber.charAt(i + 2)) * 1;
		}
		return (n != 0 && n % ten == 0);
	}
	
	public static Boolean currencyValidator(Errors errors, String value, String fieldName, 
			String expressionMsg, String rangeMsg){
		Boolean isError = false;
		try{
			if(!expressionPattern(CURRENCY_EXPRESSION, value)){
				errors.rejectValue(fieldName, expressionMsg);
				isError = true;
			} else {
				if(rangeMsg != null && Double.parseDouble(value) < CURRENCY_MINIMUM_LIMIT){
					errors.rejectValue(fieldName, rangeMsg);
					isError = true;
				}
				if(Double.parseDouble(value) > CURRENCY_MAX_LIMIT){
					errors.rejectValue(fieldName, expressionMsg);
					isError = true;
				}
				if( ZERO_DOUBLE.equals(Double.parseDouble(value)) ){
					errors.rejectValue(fieldName, expressionMsg);
					isError = true;
				}
			}
		} catch (Exception e) {
            LOGGER.error( e.getMessage(), e );
			isError = true;
		}
		return isError;
	}
	
	public static void minimumAndMaximumValidation(Errors errors, String minimum, String maximum,
			 String minimumFieldName, String maximumFieldName){
	
		if(minimum.equals(EMPTY_STRING)){
		    CommonValidator.mandatoryValidator(errors, minimumFieldName, MINIMUM_AMOUNT_REQUIRED);
		}
		if(maximum.equals(EMPTY_STRING)){
			CommonValidator.mandatoryValidator(errors, maximumFieldName, MAXIMUM_AMOUNT_REQUIRED);
		}
		if(!minimum.equals(EMPTY_STRING) && !maximum.equals(EMPTY_STRING)){
			Boolean minError = CommonValidator.currencyValidator(errors, minimum, minimumFieldName, MINIMUM_AMOUNT_RECFRACTION, null);
			Boolean maxError = CommonValidator.currencyValidator(errors, maximum, maximumFieldName, MAXIMUM_AMOUNT_RECFRACTION, null);
			if((!minError && !maxError) && Double.parseDouble(minimum) >= Double.parseDouble(maximum)){
				errors.rejectValue(minimumFieldName, MIN_MAX_COMPARE);
			}
		}	   		
	}
	
	public static void dateComparision(Errors errors, String fromdate, String todate , String fromFieldName, String toFieldName, 
			String fDateErr, String tDateErr){					
		
		if(!fromdate.equals(EMPTY_STRING) && !todate.equals(EMPTY_STRING)){
			Date fromDate = DateConvertion.stirngToDate(fromdate);
			Date toDate = DateConvertion.stirngToDate(todate);
			Date currentDate = new Date();
			if(fromDate.compareTo(currentDate) == 0 || fromDate.compareTo(currentDate) < 0){	
				errors.rejectValue(fromFieldName, fDateErr);
			} else if(toDate.compareTo(fromDate) < 0){
				errors.rejectValue(toFieldName, tDateErr);
			}
		}
		
	}
	
	public static void  cvvValidator(Errors errors, String value, Long cardType, String fieldName,
			Integer defaultCvvLength, String defaultCvvLengthMsg, Integer americanExpCvvLength, String americanExpCvvLengthMsg, 
			String expressionMsg){
		if(!expressionPattern(NUMBER_EXPRESSION, value )){
			errors.rejectValue(fieldName, expressionMsg);
			return;
		}
		if(cardType.equals(CardNumberValidator.AMERICAN_EXPRESS.longValue())){
			if(value.length() != americanExpCvvLength){
				errors.rejectValue(fieldName, americanExpCvvLengthMsg);
				return;
			}
		} else {
			if(value.length() != defaultCvvLength){
				errors.rejectValue(fieldName, defaultCvvLengthMsg);
				return;
			}
		}
	}

	public static void realNumberValidator(Errors errors , String value, String fieldName , Double minLength, 
			String minLengthMsg, Double maxLength, String maxLengthMsg, String expMsg){
		if(!CommonValidator.expressionPattern(REAL_NUM_EXP, value)){
		    errors.rejectValue(fieldName, expMsg);
		} else if(Double.parseDouble(value) > maxLength){
			errors.rejectValue(fieldName, maxLengthMsg);
		} else if(minLength != null && Double.parseDouble(value) < minLength){
			errors.rejectValue(fieldName, minLengthMsg);
		}
	}

	public static void realNumValForFeeIncludeZeroRestrict(Errors errors , String value, String fieldName , Double minLength, 
			String minLengthMsg, Double maxLength, String maxLengthMsg, String expMsg, String restrictZerosMsg){
		if(!CommonValidator.expressionPattern(REAL_NUM_EXP, value)){
			errors.rejectValue(fieldName, expMsg);
		} else if(GlobalLitterals.ZERO_DOUBLE.equals(Double.parseDouble(value))){
			errors.rejectValue(fieldName, restrictZerosMsg);
		} else if(Double.parseDouble(value) > maxLength){
			errors.rejectValue(fieldName, maxLengthMsg);
		} else if(minLength != null && Double.parseDouble(value) < minLength){
			errors.rejectValue(fieldName, minLengthMsg);
		}
	}
	
	public static Boolean validateEmail(Long recordStatus, Date creationDate, BindingResult result){
		Boolean isRequestForRegistrationAfter24hours = Boolean.FALSE;
		if(null == recordStatus || null == creationDate){
			return isRequestForRegistrationAfter24hours;
		}
		if(recordStatus.equals(CommonConstrain.EMAIL_REGISTER_ACC_DELETED)) {
			CommonValidator.mandatoryValidator(result, EMAIL_VAR, EMAIL_ID_DELETED_MSG);
		} else if(recordStatus.equals(CommonConstrain.EMAIL_ALLREADY_REGISTER)) {
			CommonValidator.mandatoryValidator(result, EMAIL_VAR, Common.EMAIL_DUPLICATE);
		} else if(recordStatus.equals(CommonConstrain.EMAIL_REGISTER_NOT_VARIFIED)){
			Date todayDate = new Date();
			Integer diffInDays = (int) ((creationDate.getTime() - todayDate.getTime())/ GlobalLitterals.DAY_IN_MILLIS );
			if(diffInDays == 0){
				CommonValidator.mandatoryValidator(result, EMAIL_VAR, Common.DUPLICATE_MAIL_REG_FAIL);
				CommonValidator.mandatoryValidator(result, EMAIL_NOTE_VAR, Common.EMAIL_FAILED_NOTE);
			} else {
				isRequestForRegistrationAfter24hours = Boolean.TRUE;
			}
		}
		return isRequestForRegistrationAfter24hours;
	}
	
	public static String cardValidator(String value, Long type){
		if(!expressionPattern(NUMBER_EXPRESSION, value )){
			return Common.CARD_NUMBER_ERROR_MSG;
		}
		if(!(new CardNumberValidator()).validate(value, type)){
			return Common.CARD_NUMBER_ERROR_MSG;
		}
		return null;
	}
	
	public static void  mpinValidator(Errors errors, String value,String fieldName, Integer minLength, Integer maxLength ){
		if(value == null || EMPTY_STRING.equals(value)){
			mandatoryValidator(errors, fieldName, MPIN_REQUIRED);
		}
		/*if(minLength != null && minLength != 0 && value.length() < minLength){
			mandatoryValidator(errors, fieldName, MPIN_MIN_LENGTH_MSG);
		}
		if(maxLength != null && maxLength != 0 && value.length() > maxLength){
			mandatoryValidator(errors, fieldName, MPIN_MAX_LENGTH_MSG);
		}*/
		if(value.length() > 4 && value.length() != 0){
			mandatoryValidator(errors, fieldName, MPIN_LENGTH_MSG);
		}
		if(value.length() < 4 && value.length() != 0){
			mandatoryValidator(errors, fieldName, MPIN_LENGTH_MSG);
		}
		if(!expressionPattern(NUMBER_EXPRESSION, value )){
			mandatoryValidator(errors, fieldName, MPIN_NOT_A_NUMBER_MSG);
		}
	}
}