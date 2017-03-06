/**
 * 
 */
package com.tarang.ewallet.walletui.form;

/**
 * @author  : prasadj
 * @date    : Feb 26, 2013
 * @time    : 10:35:47 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class DisputeSearchForm {

	private String merchantEmailId;
	
	private String fromDate;
	
	private String toDate;
	
	private String disputeType;
	
	private String customerEmailId;
	
	private String userType;
	
	public DisputeSearchForm(){
	}
	
	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getMerchantEmailId() {
		return merchantEmailId;
	}

	public void setMerchantEmailId(String merchantEmailId) {
		this.merchantEmailId = merchantEmailId;
	}
	public String getDisputeType() {
		return disputeType;
	}

	public void setDisputeType(String disputeType) {
		this.disputeType = disputeType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
