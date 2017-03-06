package com.tarang.mwallet.rest.services.model;

import java.io.Serializable;

/**
 * @author kedarnathd
 *	This DTO class used to send response for self transfer transaction
 */
public class SelfTransferInputResponse implements Serializable {

	// Property key for serialVersionUID
	private static final long serialVersionUID = 1107657677269075418L;

	private String actualAmount;
	
	private String requestedAmount;
	
	private String actualAmountDisplay;
	
	private String requestedAmountDisplay;
	
	private String taxfee;
	
	private String totalDeductions;

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(String requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public String getActualAmountDisplay() {
		return actualAmountDisplay;
	}

	public void setActualAmountDisplay(String actualAmountDisplay) {
		this.actualAmountDisplay = actualAmountDisplay;
	}

	public String getRequestedAmountDisplay() {
		return requestedAmountDisplay;
	}

	public void setRequestedAmountDisplay(String requestedAmountDisplay) {
		this.requestedAmountDisplay = requestedAmountDisplay;
	}

	public String getTaxfee() {
		return taxfee;
	}

	public void setTaxfee(String taxfee) {
		this.taxfee = taxfee;
	}

	public String getTotalDeductions() {
		return totalDeductions;
	}

	public void setTotalDeductions(String totalDeductions) {
		this.totalDeductions = totalDeductions;
	}

}
