package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

public class SendMoney implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String ipAddress;
	
	private String simNumber;
	
	private Long countryId;
	
	private String receiverMail;
	
	private Double amount;
	
	private Long currency;
	
	private String currencyCode;
	
	private String message;
	
	private Long receiverType;
	
	private Boolean recurring;
	
	private Long receiverAuthId;
	
	private Long senderAuthId;
	
	private Long transactionType;
	
	private Date requestDate;
	
	private String receiverName;
	
	private String senderName;
	
	private Long languageId;
	
	private Long typeOfRequest;
	
	private String imeiNumber;
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getRecurring() {
		return recurring;
	}

	public void setRecurring(Boolean recurring) {
		this.recurring = recurring;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReceiverAuthId() {
		return receiverAuthId;
	}

	public void setReceiverAuthId(Long receiverAuthId) {
		this.receiverAuthId = receiverAuthId;
	}

	public Long getSenderAuthId() {
		return senderAuthId;
	}

	public void setSenderAuthId(Long senderAuthId) {
		this.senderAuthId = senderAuthId;
	}

	public String getReceiverMail() {
		return receiverMail;
	}

	public void setReceiverMail(String receiverMail) {
		this.receiverMail = receiverMail;
	}

	public Long getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(Long receiverType) {
		this.receiverType = receiverType;
	}

	public Long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Long transactionType) {
		this.transactionType = transactionType;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	
}
