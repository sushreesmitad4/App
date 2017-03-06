/**
 * 
 */
package com.tarang.ewallet.dto;

import java.io.Serializable;


/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:03:22 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class AccountDto implements Serializable,Cloneable{

	private static final long serialVersionUID = 1L;

	//common attributes
	private Long id;
	
	private Long authId;
	
	private String accountHolderName;
	
	private Long status;
	
	private Boolean defaultAccount;
	
	private Long deletedStatus;
	
	private String atype;
	
	//card attributes
	private Long cardType;
	
	private String cardNumber;
	
	private String cardExpairyDate;
	
	//bank attributes;
	private Long country;
	
	private Long bankAccountType;
	
	private String sortCode;
	
	private String bankName;
	
	private String accountNumber;
	
	private String bankAddress;
	
	private String cvv;
	
	private String addrOne;
	
	private String addrTwo;
	
	private String city;
	
	private Long state;
	
	private String postalCode;
	
	private Boolean isSameAsProfileAddress;
	
	private String userAgent;
	
	private String clientIpAddress;
	
	private String currencyCode;
	
	private String transactionAmount;
	
	private String orderId;
	
	private Long currency;
	
	private Long transactionType;
	
	private String pgResponseCode;

	private String cardBin;
	
	private Boolean jointAccount;
	
	private Boolean holdBank;
	
	private Long typeOfRequest;
	
	private String simNumber;
	
	private String msisdnNumber;
	
	private String imeiNumber;
	
	private String cardName;
	
	private String statusName;
	
	private String defaultValue;
	
	private String cardNumberDisplay;
	
	private Long historyId;
	
	public AccountDto(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getAddrOne() {
		return addrOne;
	}

	public void setAddrOne(String addrOne) {
		this.addrOne = addrOne;
	}

	public String getAddrTwo() {
		return addrTwo;
	}

	public void setAddrTwo(String addrTwo) {
		this.addrTwo = addrTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Boolean getIsSameAsProfileAddress() {
		return isSameAsProfileAddress;
	}

	public void setIsSameAsProfileAddress(Boolean isSameAsProfileAddress) {
		this.isSameAsProfileAddress = isSameAsProfileAddress;
	}
	
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public Object clone() throws CloneNotSupportedException{
		return super.clone();		
	  }

	/**
	 * @return the currency
	 */
	public Long getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	/**
	 * @return the transactionType
	 */
	public Long getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(Long transactionType) {
		this.transactionType = transactionType;
	}

	public String getPgResponseCode() {
		return pgResponseCode;
	}

	public void setPgResponseCode(String pgResponseCode) {
		this.pgResponseCode = pgResponseCode;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public Boolean getJointAccount() {
		return jointAccount;
	}

	public void setJointAccount(Boolean jointAccount) {
		this.jointAccount = jointAccount;
	}
	
	public Boolean getHoldBank() {
		if(holdBank == null){
			return false;
		}
		return holdBank;
	}

	public void setHoldBank(Boolean holdBank) {
		this.holdBank = holdBank;
	}
	
	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
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
	
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumberDisplay() {
		return cardNumberDisplay;
	}

	public void setCardNumberDisplay(String cardNumberDisplay) {
		this.cardNumberDisplay = cardNumberDisplay;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
	
	
}
