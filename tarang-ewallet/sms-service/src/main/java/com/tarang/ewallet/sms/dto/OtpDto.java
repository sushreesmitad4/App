package com.tarang.ewallet.sms.dto;

import java.io.Serializable;

public class OtpDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long otpNumber;
	
	private String mobileCode;
	
	private String mobileNumber;
	
	private Long customerId;
	
	private String emailId;
	
	private Boolean isOtpExpired;
	
	private String otpModuleName;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
	
}
