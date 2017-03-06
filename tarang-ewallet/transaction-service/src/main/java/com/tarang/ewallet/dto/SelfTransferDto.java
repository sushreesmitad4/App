package com.tarang.ewallet.dto;

import java.io.Serializable;

public class SelfTransferDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ipAddress;
	
	private String requestedAmount;
	
	private String actualAmount;
	
	private String taxandFee;
	
	private String totalDeductions;
	
	private Long requestedCurrency;
	
	private Long actualCurrency;
	
	private Long authId;
	
	private Long transactionType;
	
	private Long typeOfRequest;
	
	private String simNumber;
	
	private String imeiNumber;
	
	public SelfTransferDto(){
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
	 * @return the actualAmount
	 */
	public String getActualAmount() {
		return actualAmount;
	}

	/**
	 * @param actualAmount the actualAmount to set
	 */
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	/**
	 * @return the taxandFee
	 */
	public String getTaxandFee() {
		return taxandFee;
	}

	/**
	 * @param taxandFee the taxandFee to set
	 */
	public void setTaxandFee(String taxandFee) {
		this.taxandFee = taxandFee;
	}

	/**
	 * @return the totalDeductions
	 */
	public String getTotalDeductions() {
		return totalDeductions;
	}

	/**
	 * @param totalDeductions the totalDeductions to set
	 */
	public void setTotalDeductions(String totalDeductions) {
		this.totalDeductions = totalDeductions;
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

	/**
	 * @return the authId
	 */
	public Long getAuthId() {
		return authId;
	}

	/**
	 * @param authId the authId to set
	 */
	public void setAuthId(Long authId) {
		this.authId = authId;
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

	public Long getTypeOfRequest() {
		return typeOfRequest;
	}

	public void setTypeOfRequest(Long typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}

	/**
	 * @return the simNumber
	 */
	public String getSimNumber() {
		return simNumber;
	}

	/**
	 * @param simNumber the simNumber to set
	 */
	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	
}
