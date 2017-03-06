package com.tarang.ewallet.walletui.form;

import java.io.Serializable;

public class ManageAccountForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long authId;

	private String accountHolderName;

	private Long status;

	private Boolean defaultAccount;

	private Long deletedStatus;

	private String atype;

	// card attributes
	private Long cardType;

	private String cardNumber;

	private String cardIssueDate;

	private String cardExpairyDate;

	// bank attributes
	private Long country;

	private Long bankAccountType;

	private String sortCode;

	private String bankName;

	private String accountNumber;

	private String bankAddress;

	private String mode;

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Boolean getDefaultAccount() {
		return defaultAccount;
	}

	public void setDefaultAccount(Boolean defaultAccount) {
		this.defaultAccount = defaultAccount;
	}

	public Long getDeletedStatus() {
		return deletedStatus;
	}

	public void setDeletedStatus(Long deletedStatus) {
		this.deletedStatus = deletedStatus;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public Long getCardType() {
		return cardType;
	}

	public void setCardType(Long cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardIssueDate() {
		return cardIssueDate;
	}

	public void setCardIssueDate(String cardIssueDate) {
		this.cardIssueDate = cardIssueDate;
	}

	public String getCardExpairyDate() {
		return cardExpairyDate;
	}

	public void setCardExpairyDate(String cardExpairyDate) {
		this.cardExpairyDate = cardExpairyDate;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public Long getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(Long bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
