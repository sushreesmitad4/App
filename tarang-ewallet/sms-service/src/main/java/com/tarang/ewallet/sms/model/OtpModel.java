package com.tarang.ewallet.sms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kedarnathd
 *
 */
public class OtpModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long otpNumber;
	
	private String mobileCode;
	
	private String mobileNumber;
	
	private Long customerId;
	
	private String emailId;
	
	private Date otpDate;
	
	private Date otpExpDate;
	
	private Boolean isOtpExpired;
	
	private String message;
	
	private String otpModuleName;
	
	private Boolean isSmsSent;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOtpNumber() {
		return otpNumber;
	}

	public void setOtpNumber(Long otpNumber) {
		this.otpNumber = otpNumber;
	}
	
	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getOtpDate() {
		return otpDate;
	}

	public void setOtpDate(Date otpDate) {
		this.otpDate = otpDate;
	}

	public Date getOtpExpDate() {
		return otpExpDate;
	}

	public void setOtpExpDate(Date otpExpDate) {
		this.otpExpDate = otpExpDate;
	}

	public Boolean getIsOtpExpired() {
		return isOtpExpired;
	}

	public void setIsOtpExpired(Boolean isOtpExpired) {
		this.isOtpExpired = isOtpExpired;
	}

	public String getOtpModuleName() {
		return otpModuleName;
	}

	public void setOtpModuleName(String otpModuleName) {
		this.otpModuleName = otpModuleName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Boolean getIsSmsSent() {
		return isSmsSent;
	}

	public void setIsSmsSent(Boolean isSmsSent) {
		this.isSmsSent = isSmsSent;
	}

	@Override
	public String toString() {
		return "OtpModel [id=" + id + ", otpNumber=" + otpNumber + ", mobileCode=" + mobileCode + ", mobileNumber="
				+ mobileNumber + ", customerId=" + customerId + ", emailId=" + emailId + ", otpDate=" + otpDate
				+ ", otpExpDate=" + otpExpDate + ", isOtpExpired=" + isOtpExpired + ", message=" + message
				+ ", otpModuleName=" + otpModuleName + ", isSmsSent=" + isSmsSent + "]";
	}
	
	
	

	
}
