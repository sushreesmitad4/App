package com.tarang.ewallet.dto;

import java.io.Serializable;
import java.util.List;

import com.tarang.ewallet.model.FeeSlab;


public class FeeDto implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long userType;
	private String userTypeName;

	private Long services;
	private String serviceName;

	private Long operationType;
	private String operationTypeName;

	private Long country;
	private String countryName;

	private Long currency;
	private String currencyName;

	private Long payingentity;
	private String payingEntity;

	private Long feeType;
	private String feeTypeName;
	
	private List<FeeSlab> feeSlabs;
	
	private Double fixCharSen;
	
	private Double percentageSen;
	
	private Double fixCharRec;
	
	private Double percentageRec;
	
	/**
	 * for non financial and non financial periodic
	 */
	
	private Long timeFreequency;

	public FeeDto(){
		
	}
	
	public FeeDto(	Long id, Long userType, Long services, Long operationType, Long country, Long currency,
			Long payingentity, Long feeType, Double fixCharSen, Double percentageSen, Double fixCharRec, Double percentageRec){
		this.id = id;
		this.userType = userType;
		this.services = services;
		this.operationType = operationType;
		this.country = country;
		this.currency = currency;
		this.payingentity = payingentity;
		this.feeType = feeType;
		this.fixCharSen = fixCharSen;
		this.percentageSen = percentageSen;
		this.fixCharRec = fixCharRec;
		this.percentageRec = percentageRec;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public Long getServices() {
		return services;
	}

	public void setServices(Long services) {
		this.services = services;
	}

	public Long getOperationType() {
		return operationType;
	}

	public void setOperationType(Long operationType) {
		this.operationType = operationType;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public Long getPayingentity() {
		return payingentity;
	}

	public void setPayingentity(Long payingentity) {
		this.payingentity = payingentity;
	}

	public Long getFeeType() {
		return feeType;
	}

	public void setFeeType(Long feeType) {
		this.feeType = feeType;
	}

	public Double getFixCharSen() {
		return fixCharSen;
	}

	public void setFixCharSen(Double fixCharSen) {
		this.fixCharSen = fixCharSen;
	}

	public Double getPercentageSen() {
		return percentageSen;
	}

	public void setPercentageSen(Double percentageSen) {
		this.percentageSen = percentageSen;
	}

	public Double getFixCharRec() {
		return fixCharRec;
	}

	public void setFixCharRec(Double fixCharRec) {
		this.fixCharRec = fixCharRec;
	}

	public Double getPercentageRec() {
		return percentageRec;
	}

	public void setPercentageRec(Double percentageRec) {
		this.percentageRec = percentageRec;
	}
	
	
	
	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getOperationTypeName() {
		return operationTypeName;
	}

	public void setOperationTypeName(String operationTypeName) {
		this.operationTypeName = operationTypeName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getPayingEntity() {
		return payingEntity;
	}

	public void setPayingEntity(String payingEntity) {
		this.payingEntity = payingEntity;
	}

	public String getFeeTypeName() {
		return feeTypeName;
	}

	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}

	public List<FeeSlab> getFeeSlabs() {
		return feeSlabs;
	}

	public void setFeeSlabs(List<FeeSlab> feeSlabs) {
		this.feeSlabs = feeSlabs;
	}
	
	public Long getTimeFreequency() {
		return timeFreequency;
	}

	public void setTimeFreequency(Long timeFreequency) {
		this.timeFreequency = timeFreequency;
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();		
	}
	
}



