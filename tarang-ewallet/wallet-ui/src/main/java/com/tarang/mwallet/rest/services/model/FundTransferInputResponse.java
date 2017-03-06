package com.tarang.mwallet.rest.services.model;

import java.io.Serializable;
/**
 * @author kedarnathd
 *	This DTO class used to send response for fund transfer transaction
 */
public class FundTransferInputResponse implements Serializable {

	// Property key for serialVersionUID
	private static final long serialVersionUID = 1107657677269075418L;
	
	private String emailId;
	
	private String requestedAmount;
	
	private String deductions;
	
	private String totalAmountFeeTax;
	
	private String requestedCurrencyCode;
	
	private Long requestedCurrency;
	
	private String destinationTypeName;
	
	private Long destinationType;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(String requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public String getDeductions() {
		return deductions;
	}

	public void setDeductions(String deductions) {
		this.deductions = deductions;
	}

	public String getTotalAmountFeeTax() {
		return totalAmountFeeTax;
	}

	public void setTotalAmountFeeTax(String totalAmountFeeTax) {
		this.totalAmountFeeTax = totalAmountFeeTax;
	}

	public String getRequestedCurrencyCode() {
		return requestedCurrencyCode;
	}

	public void setRequestedCurrencyCode(String requestedCurrencyCode) {
		this.requestedCurrencyCode = requestedCurrencyCode;
	}

	public Long getRequestedCurrency() {
		return requestedCurrency;
	}

	public void setRequestedCurrency(Long requestedCurrency) {
		this.requestedCurrency = requestedCurrency;
	}

	public String getDestinationTypeName() {
		return destinationTypeName;
	}

	public void setDestinationTypeName(String destinationTypeName) {
		this.destinationTypeName = destinationTypeName;
	}

	public Long getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(Long destinationType) {
		this.destinationType = destinationType;
	}
	
}
