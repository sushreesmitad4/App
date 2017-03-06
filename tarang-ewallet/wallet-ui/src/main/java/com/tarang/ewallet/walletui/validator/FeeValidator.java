package com.tarang.ewallet.walletui.validator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.form.FeeMgmtForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;



public class FeeValidator implements Validator, Common, GlobalLitterals {

	public static final String FEE_USERTYPE_VAR = "userType";
	
	public static final String FEE_SERVICE_VAR = "services";
	
	public static final String FEE_OPE_TYPE_VAR = "operationType";
	public static final String FEE_OPE_TYPE_REQUIRED = "operation.type.required.errmsg";

	public static final String FEE_COUNTRY_VAR = "country";
	public static final String FEE_COUNTRY_REQUIRED = "country.required.errmsg";

	public static final String FEE_CURRENCY_VAR = "currency";
	public static final String FEE_CURRENCY_REQUIRED = "currency.required.errmsg";
	
	public static final String FEE_PAYINGENTITY_VAR = "payingentity";
	public static final String FEE_PAYINGENTITY_REQUIRED = "paying.entity.required.errmsg";
	
	public static final String FEE_FEE_TYPE_VAR = "feeType";
	public static final String FEE_FEE_TYPE_REQUIRED = "fee.type.required.errmsg";
	
	public static final String FEE_TIME_FREEQUENCY_VAR = "timeFreequency";
	public static final String FEE_TIME_FREEQUENCY_REQUIRED = "time.frequency.required.errmsg";
	
	public static final String FEE_FIX_CHAR_SEN_VAR = "fixCharSen";
	public static final String FEE_FIX_CHAR_SEN_AND_REC_REQUIRED = "flat.fee.required.errmsg";
	public static final String FEE_FIX_CHAR_SEN_AND_REC_FRACTION = "flat.fee.contains.errmsg";
	public static final String FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS = "flat.fee.max.exceeds.errmsg";
	public static final String FLAT_FEE_ZERO_ERRMSG = "flat.fee.zero.errmsg";
	
	
	public static final String FEE_FIX_CHAR_SEN_SLB1_VAR = "fixCharSenSlb1";
	public static final String FEE_FIX_CHAR_SEN_SLB2_VAR = "fixCharSenSlb2";
	public static final String FEE_FIX_CHAR_SEN_SLB3_VAR = "fixCharSenSlb3";

	
	public static final String FEE_PER_SEN_VAR = "percentageSen";
	public static final String FEE_PER_SEN_AND_REC_FRACTION = "percentage.contains.errmsg";
	public static final String FEE_PER_SEN_AND_REC_EXCEEDS = "percentage.max.exceeds.errmsg";
	public static final String PERCENTAGE_ZERO_ERRMSG = "percentage.zero.errmsg";
	
	
	public static final String FEE_PER_SEN_SLB1_VAR = "percentageSenSlb1";
	public static final String FEE_PER_SEN_SLB2_VAR = "percentageSenSlb2";
	public static final String FEE_PER_SEN_SLB3_VAR = "percentageSenSlb3";


	public static final String FEE_FIX_CHAR_REC_VAR = "fixCharRec";

	public static final String FEE_FIX_CHAR_REC_SLB1_VAR = "fixCharRecSlb1";
	public static final String FEE_FIX_CHAR_REC_SLB2_VAR = "fixCharRecSlb2";
	public static final String FEE_FIX_CHAR_REC_SLB3_VAR = "fixCharRecSlb3";


	public static final String FEE_PER_REC_VAR = "percentageRec";

	public static final String FEE_PER_REC_SLB1_VAR = "percentageRecSlb1";
	public static final String FEE_PER_REC_SLB2_VAR = "percentageRecSlb2";
	public static final String FEE_PER_REC_SLB3_VAR = "percentageRecSlb3";

	public static final String FEE_LOW_LIMIT_SLB_ONE_VAR = "lowerLimSlb1";
	public static final String FEE_LOW_LIMIT_SLB_ONE_REQUIRED = "lower.limit.required.errmsg";
	public static final String FEE_LOW_LIMIT_SLB_ONE_RECFRACTION = "fee.lowlimslb1.errmsg.fraction";
	public static final String FEE_LOW_LIMIT_SLB_TWO_RANGE = "fee.lowlimslb2.errmsg.range";
	public static final String FEE_LOW_LIMIT_EXCEEDS_MSG = "lower.limit.exceeds.errmsg";
	
	
	public static final String FEE_UPP_LIMIT_SLB_ONE_VAR = "upperLimSlb1";
	public static final String FEE_UPP_LIMIT_RECFRACTION = "fee.upplim.errmsg.fraction";
	public static final String FEE_UPP_LIMIT_REQUIRED = "upper.limit.required.errmsg";
	public static final String FEE_UPP_LIMIT_EXCEEDS_MSG = "upper.limit.exceeds.errmsg";
	
	public static final String FEE_UPP_LIMIT_SLB_TWO_VAR = "upperLimSlb2";
	public static final String FEE_UPP_LIMIT_RANGE = "fee.upplim.errmsg.range";
	
	public static final String FEE_UPP_LIMIT_SLB_THREE_VAR = "upperLimSlb3";

	private Double maxFaltFee;
	private Double maxPercentageFee;
	private Double feeUpperLimit;
	
	public FeeValidator(UtilService us){
		maxFaltFee = us.getFeeMaxFlat();
		maxPercentageFee = us.getFeeMaxPercentage();
		feeUpperLimit = us.getFeeUpperLimit();
	}
		
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	public static void mandatoryValidator(Errors errors, String fieldName, String errorMsg){
		errors.rejectValue(fieldName, errorMsg);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		FeeMgmtForm feeMgmtForm = (FeeMgmtForm) target;
		
		int validation = feeMgmtForm.getValidation();
		if(validation == FINANCIAL_ADD_FEE_VALIDATOR){
			feeMgmtForm.setValidation(feeMgmtForm.getValidation());
			userTypeValidation(feeMgmtForm, errors);
			operationTypeValidation(feeMgmtForm, errors);
			countryValidation(feeMgmtForm, errors);
			currencyValidation(feeMgmtForm, errors);
			payingEntityValidation(feeMgmtForm, errors);
			feeTypeValidation(feeMgmtForm, errors);
			/**
			 * validation for fixed charged and percentage 
			 */
			validationForFixed(errors, feeMgmtForm.getFixCharSen(),
					FEE_FIX_CHAR_SEN_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG );
			validationForPercenatage(errors, feeMgmtForm.getPercentageSen(), 
					FEE_PER_SEN_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
			validationForFixed(errors, feeMgmtForm.getFixCharRec(), 
					FEE_FIX_CHAR_REC_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
			validationForPercenatage(errors, feeMgmtForm.getPercentageRec(), 
					FEE_PER_REC_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
			/**
			 * validation for lower for slab1 only
			 */
			validationForLowerLimitOfSlab1(feeMgmtForm, errors);
			/**
			 * validation for upper for slab1 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab1(feeMgmtForm, errors);
			
			/**
			 * validation for upper for slab2 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab2(feeMgmtForm, errors);
			
			/**
			 *  validation for upper for slab3 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab3(feeMgmtForm, errors);	
			
		} else if(validation == NON_FINANCIAL_ADD_FEE_VALIDATOR){
			feeMgmtForm.setValidation(feeMgmtForm.getValidation());
			userTypeValidation(feeMgmtForm, errors);
			operationTypeValidation(feeMgmtForm, errors);
			countryValidation(feeMgmtForm, errors);
			validationForFixed(errors, feeMgmtForm.getFixCharSen(), 
					FEE_FIX_CHAR_SEN_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
			timeFreequencyValidation(feeMgmtForm, errors);
		} else if(validation == NON_FINANCIAL_VARY_ADD_FEE_VALIDATOR){
			feeMgmtForm.setValidation(feeMgmtForm.getValidation());
			userTypeValidation(feeMgmtForm, errors);
			operationTypeValidation(feeMgmtForm, errors);
			countryValidation(feeMgmtForm, errors);
			feeTypeValidation(feeMgmtForm, errors);
			validationForFixed(errors, feeMgmtForm.getFixCharRec(), 
					FEE_FIX_CHAR_REC_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
			validationForPercenatage(errors, feeMgmtForm.getPercentageRec(), 
					FEE_PER_REC_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
			timeFreequencyValidation(feeMgmtForm, errors);
			/**
			 * validation for lower for slab1 only
			 */
			validationForLowerLimitOfSlab1(feeMgmtForm, errors);
			/**
			 * validation for upper for slab1 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab1(feeMgmtForm, errors);
			
			/**
			 * validation for upper for slab2 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab2(feeMgmtForm, errors);
			
			/**
			 *  validation for upper for slab3 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab3(feeMgmtForm, errors);	
			 
			
		} else if(validation == FINANCIAL_EDIT_FEE_VALIDATOR){
			feeMgmtForm.setValidation(feeMgmtForm.getValidation());
			payingEntityValidation(feeMgmtForm, errors);
			feeTypeValidation(feeMgmtForm, errors);
			/**
			 * validation for fixed charged and percentage 
			 */
			validationForFixed(errors, feeMgmtForm.getFixCharSen(),
					FEE_FIX_CHAR_SEN_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
			validationForPercenatage(errors, feeMgmtForm.getPercentageSen(), 
					FEE_PER_SEN_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
			validationForFixed(errors, feeMgmtForm.getFixCharRec(), 
					FEE_FIX_CHAR_REC_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
			validationForPercenatage(errors, feeMgmtForm.getPercentageRec(), 
					FEE_PER_REC_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
			/**
			 * validation for lower for slab1 only
			 */
			validationForLowerLimitOfSlab1(feeMgmtForm, errors);
			/**
			 * validation for upper for slab1 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab1(feeMgmtForm, errors);
			
			/**
			 * validation for upper for slab2 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab2(feeMgmtForm, errors);
			
			/**
			 *  validation for upper for slab3 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab3(feeMgmtForm, errors);
		} else if(validation == NON_FINANCIAL_EDIT_FEE_VALIDATOR){
			feeMgmtForm.setValidation(feeMgmtForm.getValidation());											
			validationForFixed(errors, feeMgmtForm.getFixCharSen(),
					FEE_FIX_CHAR_SEN_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
			timeFreequencyValidation(feeMgmtForm, errors);
			
		} else if(validation == NON_FINANCIAL_VARY_EDIT_FEE_VALIDATOR){
			feeMgmtForm.setValidation(feeMgmtForm.getValidation());
			feeTypeValidation(feeMgmtForm, errors);
			timeFreequencyValidation(feeMgmtForm, errors);
			validationForFixed(errors, feeMgmtForm.getFixCharRec(), 
					FEE_FIX_CHAR_REC_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
			validationForPercenatage(errors, feeMgmtForm.getPercentageRec(), 
					FEE_PER_REC_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
			/**
			 * validation for lower for slab1 only
			 */
			validationForLowerLimitOfSlab1(feeMgmtForm, errors);
			/**
			 * validation for upper for slab1 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab1(feeMgmtForm, errors);
			
			/**
			 * validation for upper for slab2 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab2(feeMgmtForm, errors);
			
			/**
			 *  validation for upper for slab3 and corresponds enable fields
			 */
			validationForUpperLimitOfSlab3(feeMgmtForm, errors);
		}
		
	}

	private void userTypeValidation(FeeMgmtForm feeMgmtForm, Errors errors){
		if(feeMgmtForm.getUserType() == null || feeMgmtForm.getUserType().equals(0L)){
			CommonValidator.nullValidator(errors,FEE_USERTYPE_VAR, USER_TYPE_REQUIRED);
		}
	}
	
	private void operationTypeValidation(FeeMgmtForm feeMgmtForm, Errors errors){
		if(feeMgmtForm.getOperationType() == null || feeMgmtForm.getOperationType().equals(0L)){
			CommonValidator.nullValidator(errors, FEE_OPE_TYPE_VAR, FEE_OPE_TYPE_REQUIRED);
		}
	}
	
	private void countryValidation(FeeMgmtForm feeMgmtForm, Errors errors){
		if(feeMgmtForm.getCountry() == null || feeMgmtForm.getCountry().equals(0L)){
			CommonValidator.nullValidator(errors, FEE_COUNTRY_VAR, FEE_COUNTRY_REQUIRED);
		}
	}
	
	private void currencyValidation(FeeMgmtForm feeMgmtForm, Errors errors){
		if(feeMgmtForm.getCurrency() == null || feeMgmtForm.getCurrency().equals(0L)) {
			CommonValidator.nullValidator(errors, FEE_CURRENCY_VAR, FEE_CURRENCY_REQUIRED);
		}
	}
	
	private void payingEntityValidation(FeeMgmtForm feeMgmtForm, Errors errors){
		if(feeMgmtForm.getPayingentity() == null || feeMgmtForm.getPayingentity().equals(0L)){
			CommonValidator.nullValidator(errors, FEE_PAYINGENTITY_VAR,FEE_PAYINGENTITY_REQUIRED);
		}
	}
	
	private void feeTypeValidation(FeeMgmtForm feeMgmtForm, Errors errors){
		if(feeMgmtForm.getFeeType() == null || feeMgmtForm.getFeeType().equals(0L)){
			CommonValidator.nullValidator(errors, FEE_FEE_TYPE_VAR,FEE_FEE_TYPE_REQUIRED);
		}
	}
	private void timeFreequencyValidation(FeeMgmtForm feeMgmtForm, Errors errors){
		if(feeMgmtForm.getTimeFreequency() == null || feeMgmtForm.getTimeFreequency().equals(0L)){
			CommonValidator.nullValidator(errors, FEE_TIME_FREEQUENCY_VAR, FEE_TIME_FREEQUENCY_REQUIRED);
		}
	}
	
	private void validationForFixed(Errors errors, String value, String var, String reqMsg,
									String fraMsg, String exceedsMsg, String restrictZerosMsg){
		if(value != null){
			String v  = value.trim();
			if(v.equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, var, reqMsg);
			} else {
				CommonValidator.realNumValForFeeIncludeZeroRestrict(errors, v, var,	null, null, maxFaltFee, exceedsMsg, fraMsg, restrictZerosMsg);
			}
		}	
	}
	
	private void validationForPercenatage(Errors errors, String value, String var, 
									String reqMsg, String fraMsg, String exceedsMsg, String restrictZerosMsg){
		if(value != null){
			String v = value.trim();
			if(v.equals(EMPTY_STRING)){
				CommonValidator.mandatoryValidator(errors, var, reqMsg);
			} else {
				CommonValidator.realNumValForFeeIncludeZeroRestrict(errors, v, var,	null, null,	maxPercentageFee, exceedsMsg, fraMsg, restrictZerosMsg);
			}
		}	
	}
	
	private void validationForLowerLimitOfSlab1(FeeMgmtForm feeMgmtForm, Errors errors){
		feeMgmtForm.setLowerLimSlb1(feeMgmtForm.getLowerLimSlb1().trim());
		if(!feeMgmtForm.getLowerLimSlb1().equals(EMPTY_STRING)){
			if(!CommonValidator.expressionPattern(Common.REAL_NUM_EXP, feeMgmtForm.getLowerLimSlb1())){
				errors.rejectValue(FEE_LOW_LIMIT_SLB_ONE_VAR, FEE_LOW_LIMIT_SLB_ONE_RECFRACTION);
			} else if(Double.parseDouble(feeMgmtForm.getLowerLimSlb1()) > feeUpperLimit){
				errors.rejectValue(FEE_LOW_LIMIT_SLB_ONE_VAR, FEE_LOW_LIMIT_EXCEEDS_MSG);
			} else if(Double.parseDouble(feeMgmtForm.getLowerLimSlb1())== 0 ){
				errors.rejectValue(FEE_LOW_LIMIT_SLB_ONE_VAR, FEE_LOW_LIMIT_SLB_ONE_RECFRACTION);
			} else if(checkDoubleValue(feeMgmtForm.getUpperLimSlb1(), Common.REAL_NUM_EXP, feeUpperLimit) 
					&& Double.parseDouble(feeMgmtForm.getLowerLimSlb1())>=Double.parseDouble(feeMgmtForm.getUpperLimSlb1())){
				errors.rejectValue(FEE_LOW_LIMIT_SLB_ONE_VAR, FEE_LOW_LIMIT_SLB_TWO_RANGE);
			}
		}
	}
	
	private void validationForUpperLimitOfSlab1(FeeMgmtForm feeMgmtForm, Errors errors){
		feeMgmtForm.setUpperLimSlb1(feeMgmtForm.getUpperLimSlb1().trim());
		feeMgmtForm.setLowerLimSlb1(feeMgmtForm.getLowerLimSlb1().trim());
		if(feeMgmtForm.getUpperLimSlb1().equals(EMPTY_STRING)){
			feeMgmtForm.setLowerLimSlb2(feeMgmtForm.getUpperLimSlb1().trim());
			if((feeMgmtForm.getFixCharSenSlb1() != null && !feeMgmtForm.getFixCharSenSlb1().equals(EMPTY_STRING))
					||(feeMgmtForm.getPercentageSenSlb1() != null && !feeMgmtForm.getPercentageSenSlb1().equals(EMPTY_STRING))
					||(feeMgmtForm.getFixCharRecSlb1() != null && !feeMgmtForm.getFixCharRecSlb1().equals(EMPTY_STRING))
					||(feeMgmtForm.getPercentageRecSlb1() != null && !feeMgmtForm.getPercentageRecSlb1().equals(EMPTY_STRING))){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_ONE_VAR, FEE_UPP_LIMIT_REQUIRED);
				
				feeMgmtForm.setLowerLimSlb1(feeMgmtForm.getLowerLimSlb1().trim());
				if(feeMgmtForm.getLowerLimSlb1().equals(EMPTY_STRING)){
					errors.rejectValue(FEE_LOW_LIMIT_SLB_ONE_VAR, FEE_LOW_LIMIT_SLB_ONE_REQUIRED);
				}	
			}
		} else{
			if(!CommonValidator.expressionPattern(Common.REAL_NUM_EXP, feeMgmtForm.getUpperLimSlb1())){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_ONE_VAR, FEE_UPP_LIMIT_RECFRACTION);
			} else if(Double.parseDouble(feeMgmtForm.getUpperLimSlb1()) > feeUpperLimit){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_ONE_VAR, FEE_UPP_LIMIT_EXCEEDS_MSG);
			} else if(Double.parseDouble(feeMgmtForm.getUpperLimSlb1()) == 0){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_ONE_VAR, FEE_UPP_LIMIT_RECFRACTION);
			} else {
				if(feeMgmtForm.getLowerLimSlb1().equals(EMPTY_STRING)){
					
					errors.rejectValue(FEE_LOW_LIMIT_SLB_ONE_VAR, FEE_LOW_LIMIT_SLB_ONE_REQUIRED);
				}
				feeMgmtForm.setLowerLimSlb2(">" + feeMgmtForm.getUpperLimSlb1().trim());
				validationForFixed(errors, feeMgmtForm.getFixCharSenSlb1(), 
						FEE_FIX_CHAR_SEN_SLB1_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
				validationForPercenatage(errors, feeMgmtForm.getPercentageSenSlb1(), 
						FEE_PER_SEN_SLB1_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
				validationForFixed(errors, feeMgmtForm.getFixCharRecSlb1(), 
						FEE_FIX_CHAR_REC_SLB1_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
				validationForPercenatage(errors, feeMgmtForm.getPercentageRecSlb1(), 
						FEE_PER_REC_SLB1_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
			}	
		}
		
	}
	
	private void validationForUpperLimitOfSlab2(FeeMgmtForm feeMgmtForm, Errors errors){
		feeMgmtForm.setUpperLimSlb2(feeMgmtForm.getUpperLimSlb2().trim());
		if(feeMgmtForm.getUpperLimSlb2().equals(EMPTY_STRING)){
			feeMgmtForm.setLowerLimSlb3(feeMgmtForm.getUpperLimSlb2().trim());
			if((feeMgmtForm.getFixCharSenSlb2()!= null && !feeMgmtForm.getFixCharSenSlb2().equals(EMPTY_STRING))
					||(feeMgmtForm.getPercentageSenSlb2()!=null && !feeMgmtForm.getPercentageSenSlb2().equals(EMPTY_STRING))
					||(feeMgmtForm.getFixCharRecSlb2()!=null && !feeMgmtForm.getFixCharRecSlb2().equals(EMPTY_STRING))
					||(feeMgmtForm.getPercentageRecSlb2()!=null && !feeMgmtForm.getPercentageRecSlb2().equals(EMPTY_STRING))){
					errors.rejectValue(FEE_UPP_LIMIT_SLB_TWO_VAR, FEE_UPP_LIMIT_REQUIRED);
			}
		} else{
			if(!CommonValidator.expressionPattern(Common.REAL_NUM_EXP, feeMgmtForm.getUpperLimSlb2() )){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_TWO_VAR, FEE_UPP_LIMIT_RECFRACTION);
			} else if(Double.parseDouble(feeMgmtForm.getUpperLimSlb2()) > feeUpperLimit){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_TWO_VAR, "upper.limit.exceeds.errmsg");
			} else if(Double.parseDouble(feeMgmtForm.getUpperLimSlb2()) == 0){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_TWO_VAR, FEE_UPP_LIMIT_RECFRACTION);
			} else if(feeMgmtForm.getUpperLimSlb1().equals(EMPTY_STRING)){
				if(!((feeMgmtForm.getFixCharSenSlb1()!= null && !feeMgmtForm.getFixCharSenSlb1().equals(EMPTY_STRING))
						||(feeMgmtForm.getPercentageSenSlb1()!=null && !feeMgmtForm.getPercentageSenSlb1().equals(EMPTY_STRING))
						||(feeMgmtForm.getFixCharRecSlb1()!=null && !feeMgmtForm.getFixCharRecSlb1().equals(EMPTY_STRING))
						||(feeMgmtForm.getPercentageRecSlb1()!=null && !feeMgmtForm.getPercentageRecSlb1().equals(EMPTY_STRING)))){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_ONE_VAR, FEE_UPP_LIMIT_REQUIRED);
				}
			} else if(checkDoubleValue(feeMgmtForm.getUpperLimSlb1(), Common.REAL_NUM_EXP, feeUpperLimit) 
					&& Double.parseDouble(feeMgmtForm.getUpperLimSlb1())>=Double.parseDouble(feeMgmtForm.getUpperLimSlb2())){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_TWO_VAR, FEE_UPP_LIMIT_RANGE);
			} else {
				feeMgmtForm.setLowerLimSlb3(">" + feeMgmtForm.getUpperLimSlb2().trim());
				validationForFixed(errors, feeMgmtForm.getFixCharSenSlb2(), 
						FEE_FIX_CHAR_SEN_SLB2_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
				validationForPercenatage(errors, feeMgmtForm.getPercentageSenSlb2(), 
						FEE_PER_SEN_SLB2_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
				validationForFixed(errors, feeMgmtForm.getFixCharRecSlb2(), 
						FEE_FIX_CHAR_REC_SLB2_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
				validationForPercenatage(errors, feeMgmtForm.getPercentageRecSlb2(), 
						FEE_PER_REC_SLB2_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
			}
		}
	}
	
	private void validationForUpperLimitOfSlab3(FeeMgmtForm feeMgmtForm, Errors errors){
		feeMgmtForm.setUpperLimSlb3(feeMgmtForm.getUpperLimSlb3().trim());
		if(feeMgmtForm.getUpperLimSlb3().equals(EMPTY_STRING)){
			if((feeMgmtForm.getFixCharSenSlb3()!= null && !feeMgmtForm.getFixCharSenSlb3().equals(EMPTY_STRING))
					||(feeMgmtForm.getPercentageSenSlb3() != null && !feeMgmtForm.getPercentageSenSlb3().equals(EMPTY_STRING))
					||(feeMgmtForm.getFixCharRecSlb3() != null && !feeMgmtForm.getFixCharRecSlb3().equals(EMPTY_STRING))
					||(feeMgmtForm.getPercentageRecSlb3() != null && !feeMgmtForm.getPercentageRecSlb3().equals(EMPTY_STRING))){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_THREE_VAR, FEE_UPP_LIMIT_REQUIRED);
			}
		} else{
			if(!CommonValidator.expressionPattern(Common.REAL_NUM_EXP, feeMgmtForm.getUpperLimSlb3())){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_THREE_VAR, FEE_UPP_LIMIT_RECFRACTION);
			} else if(Double.parseDouble(feeMgmtForm.getUpperLimSlb3()) > feeUpperLimit){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_THREE_VAR, "upper.limit.exceeds.errmsg");
			} else if(Double.parseDouble(feeMgmtForm.getUpperLimSlb3()) == 0  ){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_THREE_VAR, FEE_UPP_LIMIT_RECFRACTION);
			} else if(feeMgmtForm.getUpperLimSlb2().equals(EMPTY_STRING)){
				if(!((feeMgmtForm.getFixCharSenSlb2() != null && !feeMgmtForm.getFixCharSenSlb2().equals(EMPTY_STRING))
						||(feeMgmtForm.getPercentageSenSlb2() != null && !feeMgmtForm.getPercentageSenSlb2().equals(EMPTY_STRING))
						||(feeMgmtForm.getFixCharRecSlb2() != null && !feeMgmtForm.getFixCharRecSlb2().equals(EMPTY_STRING))
						||(feeMgmtForm.getPercentageRecSlb2() != null && !feeMgmtForm.getPercentageRecSlb2().equals(EMPTY_STRING)))){
					errors.rejectValue(FEE_UPP_LIMIT_SLB_TWO_VAR, FEE_UPP_LIMIT_REQUIRED);
				}
			} else if( checkDoubleValue(feeMgmtForm.getUpperLimSlb2(), Common.REAL_NUM_EXP, feeUpperLimit) 
					&& Double.parseDouble(feeMgmtForm.getUpperLimSlb2()) >= Double.parseDouble(feeMgmtForm.getUpperLimSlb3()) ){
				errors.rejectValue(FEE_UPP_LIMIT_SLB_THREE_VAR, FEE_UPP_LIMIT_RANGE);
			} else {
				validationForFixed(errors, feeMgmtForm.getFixCharSenSlb3(), 
						FEE_FIX_CHAR_SEN_SLB3_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
				validationForPercenatage(errors, feeMgmtForm.getPercentageSenSlb3(), 
						FEE_PER_SEN_SLB3_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS, PERCENTAGE_ZERO_ERRMSG);
				validationForFixed(errors, feeMgmtForm.getFixCharRecSlb3(), 
						FEE_FIX_CHAR_REC_SLB3_VAR, FEE_FIX_CHAR_SEN_AND_REC_REQUIRED, FEE_FIX_CHAR_SEN_AND_REC_FRACTION, FEE_FIX_CHAR_SEN_AND_REC_EXCEEDS, FLAT_FEE_ZERO_ERRMSG);
				validationForPercenatage(errors, feeMgmtForm.getPercentageRecSlb3(), 
						FEE_PER_REC_SLB3_VAR, PERCENTAGE_REQUIRED, FEE_PER_SEN_AND_REC_FRACTION, FEE_PER_SEN_AND_REC_EXCEEDS , PERCENTAGE_ZERO_ERRMSG);
			}
		}
	}
	
	private static Boolean checkDoubleValue(String value, String exp, Double range){
		String v = value;
		if(v != null){
			v = value.trim();
			if( v.equals(EMPTY_STRING) || !CommonValidator.expressionPattern(exp, v) || Double.parseDouble(v) > range){
				return false;
			}
		}	
		return true;
	}
	
}
