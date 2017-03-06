/**
 * 
 */
package com.tarang.ewallet.walletui.form;

/**
 * @author  : prasadj
 * @date    : Jan 17, 2013
 * @time    : 1:16:20 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class ReportsForm {

	private Long reportType;
	
	private String emailId;
	
	private String fromDate;
	
	private String toDate;
	
	private String userType;
	
	private String disputeType;
	
	private String cEmailId;

	private String mEmailId;
	
	public ReportsForm(){
	}

	public String getcEmailId() {
		return cEmailId;
	}

	public void setcEmailId(String cEmailId) {
		this.cEmailId = cEmailId;
	}
	public String getmEmailId() {
		return mEmailId;
	}

	public void setmEmailId(String mEmailId) {
		this.mEmailId = mEmailId;
	}
	
	public String getDisputeType() {
		return disputeType;
	}

	public void setDisputeType(String disputeType) {
		this.disputeType = disputeType;
	}

	public Long getReportType() {
		return reportType;
	}

	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
