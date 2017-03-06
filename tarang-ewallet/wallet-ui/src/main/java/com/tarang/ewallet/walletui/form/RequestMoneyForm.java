package com.tarang.ewallet.walletui.form;

public class RequestMoneyForm {
	
	private Long id;
	
	private String emailId;
	
	private String phoneCode;
	
	private String phoneNumber;
	
	private Long currency;
	
	private String amount;
	
	private Long status;
	
	private String requesterMsg;
	
	private String responserMsg;
	
	private String requestDate;
	
	private String responseDate;
	
	private String mode;
	
	private Long countryId;
	
	private String userWalletBalance;
	
	private String txnDeductedBalance;
	
	private String currencyCodeName;
	
	private String statusName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}
	
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getRequesterMsg() {
		return requesterMsg;
	}

	public void setRequesterMsg(String requesterMsg) {
		this.requesterMsg = requesterMsg;
	}

	public String getResponserMsg() {
		return responserMsg;
	}

	public void setResponserMsg(String responserMsg) {
		this.responserMsg = responserMsg;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	
	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getUserWalletBalance() {
		return userWalletBalance;
	}

	public void setUserWalletBalance(String userWalletBalance) {
		this.userWalletBalance = userWalletBalance;
	}

	public String getTxnDeductedBalance() {
		return txnDeductedBalance;
	}

	public void setTxnDeductedBalance(String txnDeductedBalance) {
		this.txnDeductedBalance = txnDeductedBalance;
	}

	public String getCurrencyCodeName() {
		return currencyCodeName;
	}

	public void setCurrencyCodeName(String currencyCodeName) {
		this.currencyCodeName = currencyCodeName;
	}	
	
}
