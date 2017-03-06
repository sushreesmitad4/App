package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

public class Authentication implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String ipAddress;
	private String userType;
	private String emailId;
	private String password;
	private Long hints;
	private Boolean resetPassword;
	private Date lastLogin;
	private Boolean active;
	private Boolean blocked;
	private Integer attempts;
	private Long status;
	private Boolean emailVarification;
	private Boolean loginStatus;
	private Boolean kycRequired;
	private Date creationDate;
	private Boolean ipAddressCheck;
	private Boolean emailPatternCheck;
	private Boolean chargeBackCheck;
	private String updateReason;
	
	private Long typeOfRequest;
	private String msisdnNumber;
	private String imeiNumber;
	private String simNumber;
	private String mPin;
	private Date mWalletActicationDate;
	private Date mWalletDeacticationDate;
	private Boolean isMwalletActive;
	
	private Integer mPinAttempts;
	private Boolean mPinBlocked;
	private Date mPinBlockedDate;

	public Authentication() {
	}
	
	public Authentication(Long id, String userType, String emailId, String password) {
		this.id = id;
		this.userType = userType;
		this.emailId = emailId;
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getHints() {
		return hints;
	}
	
	public void setHints(Long hints) {
		this.hints = hints;
	}
	
	public Boolean isResetPassword() {
		return resetPassword;
	}
	
	public void setResetPassword(Boolean resetPassword) {
		this.resetPassword = resetPassword;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public Boolean isActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	public Boolean isBlocked() {
		return blocked;
	}
	
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	
	public Integer getAttempts() {
		return attempts;
	}
	
	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}
	public Long getStatus() {
		return status;
	}
	
	public void setStatus(Long status) {
		this.status = status;
	}
	
	public Boolean isEmailVarification() {
		return emailVarification;
	}
	
	public void setEmailVarification(Boolean emailVarification) {
		this.emailVarification = emailVarification;
	}
	
	public Boolean isLoginStatus() {
		return loginStatus;
	}
	
	public void setLoginStatus(Boolean loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	public Boolean isKycRequired() {
		return kycRequired;
	}
	
	public void setKycRequired(Boolean kycRequired) {
		this.kycRequired = kycRequired;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getIpAddressCheck() {
		return ipAddressCheck;
	}

	public void setIpAddressCheck(Boolean ipAddressCheck) {
		this.ipAddressCheck = ipAddressCheck;
	}

	public Boolean getEmailPatternCheck() {
		return emailPatternCheck;
	}

	public void setEmailPatternCheck(Boolean emailPatternCheck) {
		this.emailPatternCheck = emailPatternCheck;
	}

	public Boolean getChargeBackCheck() {
		return chargeBackCheck;
	}

	public void setChargeBackCheck(Boolean chargeBackCheck) {
		this.chargeBackCheck = chargeBackCheck;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public Long getTypeOfRequest() {
		return typeOfRequest;
	}

	public void setTypeOfRequest(Long typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}

	public String getMsisdnNumber() {
		return msisdnNumber;
	}

	public void setMsisdnNumber(String msisdnNumber) {
		this.msisdnNumber = msisdnNumber;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}
	
	public String getmPin() {
		return mPin;
	}

	public void setmPin(String mPin) {
		this.mPin = mPin;
	}

	public Date getmPinGenerateDate() {
		return mWalletActicationDate;
	}

	public void setmPinGenerateDate(Date mPinGenerateDate) {
		this.mWalletActicationDate = mPinGenerateDate;
	}

	public Date getmWalletActicationDate() {
		return mWalletActicationDate;
	}

	public void setmWalletActicationDate(Date mWalletActicationDate) {
		this.mWalletActicationDate = mWalletActicationDate;
	}

	public Date getmWalletDeacticationDate() {
		return mWalletDeacticationDate;
	}

	public void setmWalletDeacticationDate(Date mWalletDeacticationDate) {
		this.mWalletDeacticationDate = mWalletDeacticationDate;
	}

	public Boolean getIsMwalletActive() {
		return isMwalletActive;
	}

	public void setIsMwalletActive(Boolean isMwalletActive) {
		this.isMwalletActive = isMwalletActive;
	}

	public Integer getmPinAttempts() {
		return mPinAttempts;
	}

	public void setmPinAttempts(Integer mPinAttempts) {
		this.mPinAttempts = mPinAttempts;
	}

	public Boolean getmPinBlocked() {
		return mPinBlocked;
	}

	public void setmPinBlocked(Boolean mPinBlocked) {
		this.mPinBlocked = mPinBlocked;
	}

	public Date getmPinBlockedDate() {
		return mPinBlockedDate;
	}

	public void setmPinBlockedDate(Date mPinBlockedDate) {
		this.mPinBlockedDate = mPinBlockedDate;
	}
	
}
