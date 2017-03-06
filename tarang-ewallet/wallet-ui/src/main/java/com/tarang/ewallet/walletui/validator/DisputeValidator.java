package com.tarang.ewallet.walletui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tarang.ewallet.dispute.util.DisputeStatusConstants;
import com.tarang.ewallet.transaction.util.ReversalType;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.DisputeForm;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


public class DisputeValidator implements Validator, GlobalLitterals, Common{
	
	public static final String DISPUTE_STATUS_VAR = "formStatus";
	public static final String DISPUTE_STATUS_REQUIRED = "dispute.status.required";
	
	public static final String MESSAGE_VAR = "message";
	
	public static final String APPROVED_AMOUNT = "approvedAmount";
	public static final String APPROVED_AMOUNT_REQUIRED = "common.dispute.approvedamount.required";
	public static final String APPROVED_AMOUNT_EXPRESSION = "common.dispute.approvedamount.expression";
	public static final String APPROVED_REQUEST_AMOUNT_EXCESS = "common.dispute.approved.request.amount.excess";
	
	public static final String REQUESTED_AMOUNT = "requestAmount";
	public static final String REQUESTED_AMOUNT_REQUIRED = "common.dispute.requestedamonut.required";
	public static final String REQUESTED_AMOUNT_EXPRESSION = "common.dispute.requestedamonut.expression";
	public static final String TRANSACTION_REQUESTED_AMOUNT_EXCESS = "common.dispute.txn.request.amount.excess";
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return DisputeForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DisputeForm form = (DisputeForm) target;
		String message=form.getMessage().trim();
		if(!message.equals(EMPTY_STRING)){
			CommonValidator.messageValidator(errors, message, MESSAGE_VAR,
					MESSAGE_MIN_LENGTH, MESSAGE_MIN_LENGTH_RANGE,
					MESSAGE_MAX_LENGTH, MESSAGE_MAX_LENGTH_RANGE,
					MESSAGE_MATCHER);
		} else {
			CommonValidator.mandatoryValidator(errors, MESSAGE_VAR, MESSAGE_REQUIRED);
		}
		form.setMessage(message);
		
		if(form.getUserType()!=null && form.getUserType().equals(CUSTOMER_USER_TYPE_ID)){
			if(form.getDistype().equals(ReversalType.REFUND)){
					String requestedAmount = form.getRequestAmount();
				if(requestedAmount.equals(EMPTY_STRING)){
					CommonValidator.mandatoryValidator(errors, REQUESTED_AMOUNT, REQUESTED_AMOUNT_REQUIRED);
				} else {
					Boolean amountError = CommonValidator.currencyValidator(errors, form.getRequestAmount(), 
							REQUESTED_AMOUNT, REQUESTED_AMOUNT_EXPRESSION, null);
					if(!amountError && Double.parseDouble(form.getRequestAmount()) == 0){
						CommonValidator.mandatoryValidator(errors, REQUESTED_AMOUNT, REQUESTED_AMOUNT_REQUIRED);
					}
					if(!amountError && form.getRequestAmount() != null && 
							(Double.parseDouble(form.getRequestAmount()) > (Double.parseDouble(form.getTransactionAmount())))){
						CommonValidator.mandatoryValidator(errors, REQUESTED_AMOUNT, TRANSACTION_REQUESTED_AMOUNT_EXCESS);
					}
				}
			}
		} else {
			Long status = form.getStatus();
			Long type = form.getDistype();
			if(status != null && type != null && (getMerchantStatus(form) || getAdminStatus(form))){
				if(form.getFormStatus() == null){
					CommonValidator.mandatoryValidator(errors, DISPUTE_STATUS_VAR, DISPUTE_STATUS_REQUIRED);
				}
				if(type.equals(ReversalType.REFUND) && form.getApprovedAmount() != null){
					form.setApprovedAmount(form.getApprovedAmount().trim());
					if(form.getApprovedAmount().equals(EMPTY_STRING)){
						CommonValidator.mandatoryValidator(errors, APPROVED_AMOUNT, APPROVED_AMOUNT_REQUIRED);
					} else {
						Boolean amountError = CommonValidator.currencyValidator(errors, form.getApprovedAmount(), 
								APPROVED_AMOUNT, APPROVED_AMOUNT_EXPRESSION, null);
						if(!amountError && Double.parseDouble(form.getApprovedAmount()) == 0){
							CommonValidator.mandatoryValidator(errors, APPROVED_AMOUNT, APPROVED_AMOUNT_REQUIRED);
						}
						if(!amountError && form.getRequestAmount() != null && 
								(Double.parseDouble(form.getApprovedAmount()) > Double.parseDouble(form.getRequestAmount()))){
							CommonValidator.mandatoryValidator(errors, APPROVED_AMOUNT, APPROVED_REQUEST_AMOUNT_EXCESS);
						}
					}
				}
			}
		}
	}
	
	private Boolean getMerchantStatus(DisputeForm form){
		return form.getUserType().equals(GlobalLitterals.MERCHANT_USER_TYPE_ID)
		&& (form.getStatus().equals(DisputeStatusConstants.PENDING)
					|| form.getStatus().equals(DisputeStatusConstants.MERCHANT_TO_PAY));
	}
	
	private Boolean getAdminStatus(DisputeForm form){
		return form.getUserType().equals(GlobalLitterals.ADMIN_USER_TYPE_ID)
		&& (form.getStatus().equals(DisputeStatusConstants.PENDING)
					|| form.getStatus().equals(DisputeStatusConstants.MERCHANT_REJECTED));
	}

}
