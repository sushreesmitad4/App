package com.tarang.ewallet.dto;

import java.io.Serializable;



public class WalletThresholdDto implements Serializable {
 
private static final long serialVersionUID = 1L;
 
	
    private Long id;
	
	private Long currency;
	
	private Long country;
	
	private Double maximumamount;
	
    private String currencyName;
	
  	private String countryName;
  	
  	private String strMaximumAmount;
	
	public WalletThresholdDto() {		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public Double getMaximumamount() {
		return maximumamount;
	}

	public void setMaximumamount(Double maximumamount) {
		this.maximumamount = maximumamount;
	}
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStrMaximumAmount() {
		return strMaximumAmount;
	}

	public void setStrMaximumAmount(String strMaximumAmount) {
		this.strMaximumAmount = strMaximumAmount;
	}
}
