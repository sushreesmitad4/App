package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

public class VelocitySetting implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long countryId;
	
	private Long userTypeId;
	
	private Long transactionTypeId;
	
	private Long currencyId;
	
	private Long typeOfFrequenciesId;
	
	private Long totalNoOfTransaction;
	
	private Double totalVolumeOfAmount;
	
	private Date creationDate;
	
	private Date modifyDate;
	
	//userid
	private String createdBy;
	
	//userid
	private String modifyBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Long getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(Long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public Long getTypeOfFrequenciesId() {
		return typeOfFrequenciesId;
	}

	public void setTypeOfFrequenciesId(Long typeOfFrequenciesId) {
		this.typeOfFrequenciesId = typeOfFrequenciesId;
	}

	public Long getTotalNoOfTransaction() {
		return totalNoOfTransaction;
	}

	public void setTotalNoOfTransaction(Long totalNoOfTransaction) {
		this.totalNoOfTransaction = totalNoOfTransaction;
	}

	public Double getTotalVolumeOfAmount() {
		return totalVolumeOfAmount;
	}

	public void setTotalVolumeOfAmount(Double totalVolumeOfAmount) {
		this.totalVolumeOfAmount = totalVolumeOfAmount;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	
}
