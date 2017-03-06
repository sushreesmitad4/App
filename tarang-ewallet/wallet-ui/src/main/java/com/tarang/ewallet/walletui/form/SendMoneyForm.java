package com.tarang.ewallet.walletui.form;

import java.io.Serializable;

public class SendMoneyForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String emailId;
	private String requestedAmount;
	private Long requestedCurrency;
	private String message;
	private Long destinationType;
	private Boolean recurring;
	private String fromDate;
	private String toDate;
	private Long frequency;
	private Integer totalOccurences;
	private String mode;
	
	private String actualAmount;
	private Long actualCurrency;
	private String userJobName;
	
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
	 * @return the requestedAmount
	 */
	public String getRequestedAmount() {
		return requestedAmount;
	}
	/**
	 * @param requestedAmount the requestedAmount to set
	 */
	public void setRequestedAmount(String requestedAmount) {
		this.requestedAmount = requestedAmount;
	}
	/**
	 * @return the requestedCurrency
	 */
	public Long getRequestedCurrency() {
		return requestedCurrency;
	}
	/**
	 * @param requestedCurrency the requestedCurrency to set
	 */
	public void setRequestedCurrency(Long requestedCurrency) {
		this.requestedCurrency = requestedCurrency;
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
	 * @return the recurring
	 */
	public Boolean getRecurring() {
		return recurring;
	}
	/**
	 * @param recurring the recurring to set
	 */
	public void setRecurring(Boolean recurring) {
		this.recurring = recurring;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the frequency
	 */
	public Long getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the totalOccurences
	 */
	public Integer getTotalOccurences() {
		return totalOccurences;
	}
	/**
	 * @param totalOccurences the totalOccurences to set
	 */
	public void setTotalOccurences(Integer totalOccurences) {
		this.totalOccurences = totalOccurences;
	}
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return the actualAmount
	 */
	public String getActualAmount() {
		return actualAmount;
	}
	/**
	 * @param calculateAmount the calculateAmount to set
	 */
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}
	/**
	 * @return the actualCurrency
	 */
	public Long getActualCurrency() {
		return actualCurrency;
	}
	/**
	 * @param actualCurrency the actualCurrency to set
	 */
	public void setActualCurrency(Long actualCurrency) {
		this.actualCurrency = actualCurrency;
	}
	public String getUserJobName() {
		return userJobName;
	}
	public void setUserJobName(String userJobName) {
		this.userJobName = userJobName;
	}
	
}
