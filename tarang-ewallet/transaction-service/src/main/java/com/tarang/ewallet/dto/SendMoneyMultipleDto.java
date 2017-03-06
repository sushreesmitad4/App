package com.tarang.ewallet.dto;

import java.io.Serializable;

public class SendMoneyMultipleDto implements Serializable{


	private static final long serialVersionUID = 1L;

	private String emailId;
	private String amount;
	private Long currency;
	private String message;
	private Long destinationType;
	//other than form fields
    private Long receiverAuthId;
  	private Long senderAuthId;
    private Long senderCurrencyId;
	private Long receiverCurrencyId;
	
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the destinationType
	 */
	public Long getDestinationType() {
		return destinationType;
	}
	/**
	 * @param destinationType the destinationType to set
	 */
	public void setDestinationType(Long destinationType) {
		this.destinationType = destinationType;
	}
	/**
	 * @return the receiverAuthId
	 */
	public Long getReceiverAuthId() {
		return receiverAuthId;
	}
	/**
	 * @param receiverAuthId the receiverAuthId to set
	 */
	public void setReceiverAuthId(Long receiverAuthId) {
		this.receiverAuthId = receiverAuthId;
	}
	/**
	 * @return the senderAuthId
	 */
	public Long getSenderAuthId() {
		return senderAuthId;
	}
	/**
	 * @param senderAuthId the senderAuthId to set
	 */
	public void setSenderAuthId(Long senderAuthId) {
		this.senderAuthId = senderAuthId;
	}
	/**
	 * @return the senderCurrencyId
	 */
	public Long getSenderCurrencyId() {
		return senderCurrencyId;
	}
	/**
	 * @param senderCurrencyId the senderCurrencyId to set
	 */
	public void setSenderCurrencyId(Long senderCurrencyId) {
		this.senderCurrencyId = senderCurrencyId;
	}
	/**
	 * @return the receiverCurrencyId
	 */
	public Long getReceiverCurrencyId() {
		return receiverCurrencyId;
	}
	/**
	 * @param receiverCurrencyId the receiverCurrencyId to set
	 */
	public void setReceiverCurrencyId(Long receiverCurrencyId) {
		this.receiverCurrencyId = receiverCurrencyId;
	}
	
	
}
