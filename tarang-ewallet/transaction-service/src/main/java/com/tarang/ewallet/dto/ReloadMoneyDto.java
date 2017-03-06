package com.tarang.ewallet.dto;

import com.tarang.ewallet.model.ReloadMoney;

/**
 * @author kedarnathd
 *
 */
public class ReloadMoneyDto extends ReloadMoney{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private String userAgent;
	private String clientIpAddress;
	private String currencyCode;
	private String cardNumber;
	private String cardExpairyDate;
	private String cvv;
	private String orderId;
	private Boolean isReloadMoneySucc;
	private Long currencyId;
	private Long typeOfTransaction;
	private Long countryId;
	private String payeeName;
	private String payeeEmail;
	private Long languageId;
	private Long userType;
	private String accountOrCardHolderName;
	private String pgResponseCode;
	private Long typeOfRequest;
	
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
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Boolean getIsReloadMoneySucc() {
		return isReloadMoneySucc;
	}
	public void setIsReloadMoneySucc(Boolean isReloadMoneySucc) {
		this.isReloadMoneySucc = isReloadMoneySucc;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public Long getTypeOfTransaction() {
		return typeOfTransaction;
	}
	public void setTypeOfTransaction(Long typeOfTransaction) {
		this.typeOfTransaction = typeOfTransaction;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayeeEmail() {
		return payeeEmail;
	}
	public void setPayeeEmail(String payeeEmail) {
		this.payeeEmail = payeeEmail;
	}
	public Long getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	public Long getUserType() {
		return userType;
	}
	public void setUserType(Long userType) {
		this.userType = userType;
	}
	
	public String getAccountOrCardHolderName() {
		return accountOrCardHolderName;
	}
	
	public void setAccountOrCardHolderName(String accountOrCardHolderName) {
		this.accountOrCardHolderName = accountOrCardHolderName;
	}
	
	public String getPgResponseCode() {
		return pgResponseCode;
	}
	
	public void setPgResponseCode(String pgResponseCode) {
		this.pgResponseCode = pgResponseCode;
	}
	public Long getTypeOfRequest() {
		return typeOfRequest;
	}
	public void setTypeOfRequest(Long typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}
	
}
