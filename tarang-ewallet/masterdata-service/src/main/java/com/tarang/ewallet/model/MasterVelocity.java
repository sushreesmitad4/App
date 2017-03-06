package com.tarang.ewallet.model;

public class MasterVelocity {
	
	private Long id;
	
	private Long userTypeId;
	
	private Long countryId;
	
	private Long currencyId;
	
	private Long transactionTypeId;
	
	private Long typeOfFrequenciesId;
	
	private Long totalNoOfTransaction;
	
	private Double totalVolumeOfAmount;
	
	public MasterVelocity(){
		
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

}
