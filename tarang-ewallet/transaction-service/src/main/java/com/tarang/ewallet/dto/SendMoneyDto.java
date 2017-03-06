package com.tarang.ewallet.dto;

import java.io.Serializable;
import java.util.Date;


public class SendMoneyDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String ipAddress;
	
	private String simNumber;
	
	private Long typeOfRequest;
	
	/**
	 * emailId to hold payeeEmail
	 */
	private String emailId;
	/**
	 * actualAmount to hold payerAmount
	 */
	private Double actualAmount;
	/**
	 * defaultCurrency to hold payerCurrency
	 */
	private Long actualCurrency;
	/**
	 * requestedAmount to hold payeeAmount
	 */
	private Double requestedAmount;
	/**
	 * requestedCurrency to hold payeeCurrency
	 */
	private Long requestedCurrency;
	private String message;
	/**
	 * destinationType to hold register, non register and merchant type
	 */
	private Long destinationType;
	/**
	 * Below fields are for scheduler
	 */
	private Boolean recurring;
	private String userJobName;
	private Date fromDate;
	private Date toDate;
	private Long frequency;
	private Integer totalOccurences;
	private String failureMessage;
	
	//added for email by kedar
	/**
	 * Send money DB id
	 */
	private Long id;
	/**
	 * userType For Customer 1 and Merchant 2
	 */
	private Long languageId;
	private String payerName;
	private String payeeName;
	/**
	 * currencyCode to hold currency name 
	 */
	private String currencyCode;
	private Long transactionId;
	private Long transactionStatus;
	/**
	 * requestDate to hold current date 
	 */
	private Date requestDate;
	/**
	 * Below fields are for Send Money Grid display
	 */
	private String amountString;
	private String dateToString;
	private String transactionStatusName;
	private String authUserType;
	
	//added for transaction by rajasekhar
	/**
	 * receiverAuthId to hold PayeeAuth id
	 */
	private Long receiverAuthId;
	/**
	 * senderAuthId to hold PayerAuth id
	 */
	private Long senderAuthId;
	private Long transactionType;
	private Long countryId;
	
	//added for file validation
	private Boolean status;
	
	//added for usertype(only C=1L or M=2L)
	private Long senderUserType;
	
	private String imeiNumber;
	
	private Date transactionDate;
	
	public SendMoneyDto(){
	}
	
	/**
	 * @param id 
	 * @param currency 
	 * @param requestDate 
	 * @param amount 
	 * @param transactionId 
	 * @param emailId 
	 * @param message 
	 * @Kedarnathd
	 * This is for received money grid page
	 */
	public SendMoneyDto(Long id, Long currency, Date requestDate, Double amount, String message, String emailId, String authUserType){
		this.id = id;
		this.requestedCurrency = currency;
		this.requestDate = requestDate;
		this.requestedAmount = amount;
		this.message = message;
		this.emailId = emailId;
		this.authUserType = authUserType;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public Double getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}
	public Long getActualCurrency() {
		return actualCurrency;
	}
	public void setActualCurrency(Long actualCurrency) {
		this.actualCurrency = actualCurrency;
	}
	public Double getRequestedAmount() {
		return requestedAmount;
	}
	public void setRequestedAmount(Double amount) {
		this.requestedAmount = amount;
	}
	public Long getRequestedCurrency() {
		return requestedCurrency;
	}
	public void setRequestedCurrency(Long currency) {
		this.requestedCurrency = currency;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(Long destinationType) {
		this.destinationType = destinationType;
	}
	public Boolean getRecurring() {
		return recurring;
	}
	public void setRecurring(Boolean recurring) {
		this.recurring = recurring;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Long getFrequency() {
		return frequency;
	}
	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}
	public Integer getTotalOccurences() {
		return totalOccurences;
	}
	public void setTotalOccurences(Integer totalOccurences) {
		this.totalOccurences = totalOccurences;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	/**
	 * @return the staus
	 */
	public Boolean getStatus() {
		return status;
	}
	/**
	 * @param staus the staus to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public Long getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(Long transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @return the countryId
	 */
	public Long getCountryId() {
		return countryId;
	}
	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	/**
	 * @return the languageId
	 */
	public Long getLanguageId() {
		return languageId;
	}
	/**
	 * @param languageId the languageId to set
	 */
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	/**
	 * @return the payerName
	 */
	public String getPayerName() {
		return payerName;
	}
	/**
	 * @param payerName the payerName to set
	 */
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	/**
	 * @return the payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}
	/**
	 * @param payeeName the payeeName to set
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * @return the senderUserType
	 */
	/**
	 * @return the senderUserType
	 */
	public Long getSenderUserType() {
		return senderUserType;
	}
	/**
	 * @param senderUserType the senderUserType to set
	 */
	public void setSenderUserType(Long senderUserType) {
		this.senderUserType = senderUserType;
	}
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(Long transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getTransactionStatusName() {
		return transactionStatusName;
	}
	public void setTransactionStatusName(String transactionStatusName) {
		this.transactionStatusName = transactionStatusName;
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
	
	public String getAuthUserType() {
		return authUserType;
	}
	public void setAuthUserType(String authUserType) {
		this.authUserType = authUserType;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public String getUserJobName() {
		return userJobName;
	}

	public void setUserJobName(String userJobName) {
		this.userJobName = userJobName;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}
