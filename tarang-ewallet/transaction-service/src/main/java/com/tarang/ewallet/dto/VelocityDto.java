package com.tarang.ewallet.dto;

import com.tarang.ewallet.model.VelocitySetting;

/**
 * @Author : kedarnathd
 * @Date : Dec 30, 2016
 * @Time : 10:09:24 AM
 * @Version : 1.0
 * @Comments: DTO class to provide common parameters for velocity services
 */
public class VelocityDto extends VelocitySetting{
	
	private static final long serialVersionUID = 1L;
	
	private String currencyName;
	
  	private String countryName;
  	
  	private String transactiontypeName;
	
	private String typeOfFrequenciesName;
	
	private String userTypeName;

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

	public String getTransactiontypeName() {
		return transactiontypeName;
	}

	public void setTransactiontypeName(String transactiontypeName) {
		this.transactiontypeName = transactiontypeName;
	}

	public String getTypeOfFrequenciesName() {
		return typeOfFrequenciesName;
	}

	public void setTypeOfFrequenciesName(String typeOfFrequenciesName) {
		this.typeOfFrequenciesName = typeOfFrequenciesName;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	
}
