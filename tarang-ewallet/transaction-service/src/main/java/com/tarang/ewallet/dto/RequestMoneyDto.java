/**
 * 
 */
package com.tarang.ewallet.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vasanthar
 *
 */
public class RequestMoneyDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Bellow fields are using for communicating with model(database) object. 
	 */
	private Long id;
	
	private String ipAddress;
	
	private Long requesterId;
	
	private String email;
	
	private Date requestDate;
	
	private String requesterName;
	
	private Long currencyId;
	
	private Double amount;
	
	private Long transactionId;
	
	private Long attempts;
	
	/**
	 * Bellow fields are using for communicating presentation to services. 
	 */	
	private String requesterEmail;
	
	private String requesterMsg;
	
	private Long status;
	
	private String responserName;
	
	private String responserEmail;
	
	private String responserMsg;
	
	private Date responseDate;
	
	private String phoneCode;
	
	private String phoneNumber;	
	
	/**
	 * Bellow fields are using fee and tax calculation for user wallet amount. 
	 */	
	private Double userWalletBalance;
	
	private Double txnDeductedBalance;
	
	private Long countryId;
	
	private Long responserId;
	
	/**
	 * Bellow fields are using for receiving money email notification
	 */	
	private Long languageId;
	
	private String payerName;
	
	private String payeeName;
	
	private String currencyCode;
	
	/**
	 * Bellow fields are using for grid(request money) display. 
	 */
	private String currencyName;

	private String statusName;
	
	private String isSelfRequest;
	
	private String dateToString;

	private String amountString;
	
	private Long typeOfRequest;
	
	public RequestMoneyDto(){
	}
	
	public RequestMoneyDto(Long id, Long requesterId, String email, Date requestDate, Long currencyId, Double amount, Long transactionId, Long attempts, Long status) {
		this.id = id;
		this.requesterId = requesterId;
		this.email = email;
		this.requestDate = requestDate;
		this.currencyId = currencyId;
		this.amount = amount;
		this.transactionId = transactionId;
		this.attempts = attempts;
		this.status = status;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getStatusName() {
		  return statusName;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateToString() {
		return dateToString;
	}

	public void setDateToString(String dateToString) {
		this.dateToString = dateToString;
	}

	public String getAmountString() {
		return amountString;
	}

	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}

	public void setStatusName(String statusName) {
	  this.statusName = statusName;
	 }
	public String getCurrencyName() {
	  return currencyName;
	 }

	public void setCurrencyName(String currencyName) {
	  this.currencyName = currencyName;
	 }
	
	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	public String getRequesterEmail() {
		return requesterEmail;
	}

	public void setRequesterEmail(String requesterEmail) {
		this.requesterEmail = requesterEmail;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRequesterMsg() {
		return requesterMsg;
	}

	public void setRequesterMsg(String requesterMsg) {
		this.requesterMsg = requesterMsg;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getResponserName() {
		return responserName;
	}

	public void setResponserName(String responserName) {
		this.responserName = responserName;
	}

	public String getResponserEmail() {
		return responserEmail;
	}

	public void setResponserEmail(String responserEmail) {
		this.responserEmail = responserEmail;
	}

	public String getResponserMsg() {
		return responserMsg;
	}

	public void setResponserMsg(String responserMsg) {
		this.responserMsg = responserMsg;
	}
	
	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getAttempts() {
		return attempts;
	}

	public void setAttempts(Long attempts) {
		this.attempts = attempts;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getIsSelfRequest() {
		return isSelfRequest;
	}

	public void setIsSelfRequest(String isSelfRequest) {
		this.isSelfRequest = isSelfRequest;
	}

	public Double getUserWalletBalance() {
		return userWalletBalance;
	}

	public void setUserWalletBalance(Double userWalletBalance) {
		this.userWalletBalance = userWalletBalance;
	}

	public Double getTxnDeductedBalance() {
		return txnDeductedBalance;
	}

	public void setTxnDeductedBalance(Double txnDeductedBalance) {
		this.txnDeductedBalance = txnDeductedBalance;
	}

	public Long getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(Long requesterId) {
		this.requesterId = requesterId;
	}

	public Long getResponserId() {
		return responserId;
	}

	public void setResponserId(Long responserId) {
		this.responserId = responserId;
	}
public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getTypeOfRequest() {
		return typeOfRequest;
	}

	public void setTypeOfRequest(Long typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}
	
}
