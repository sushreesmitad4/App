package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.List;

public class Fee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long userType;
	
	private Long services;
		
	private Long operationType;
	
	private Long country;
	
	private Long currency;
	
	private Long payingentity;
	
	private Long feeType;
	
	private Double fixCharSen;
	
	private Double percentageSen;
	
	private Double fixCharRec;
	
	private Double percentageRec;
	
	private List<FeeSlab> feeSlabs;
	
	/**
	 *for nonfinancial and nonfinancial periodic 
	 */
	private Long timefreequency;
	
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

	public List<FeeSlab> getFeeSlabs() {
		return feeSlabs;
	}

	public void setFeeSlabs(List<FeeSlab> feeSlabs) {
		this.feeSlabs = feeSlabs;
	}	
	
	/**
	 * for non financial and non financial grid 
	 */
	public Long getTimefreequency() {
		return timefreequency;
	}

	public void setTimefreequency(Long timefreequency) {
		this.timefreequency = timefreequency;
	}
	
}
	