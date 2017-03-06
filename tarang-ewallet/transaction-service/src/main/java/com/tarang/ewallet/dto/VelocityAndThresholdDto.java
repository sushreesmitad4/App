package com.tarang.ewallet.dto;

import com.tarang.ewallet.model.VelocityAndThreshold;

/**
 * @author kedarnathd
 *
 */
public class VelocityAndThresholdDto extends VelocityAndThreshold{

	private static final long serialVersionUID = 1L;

	private String currencyName;
	
  	private String countryName;
  	
  	private String transactiontypeName;
    
  	private String strMinimumAmount;
  	
  	private String strMaximumAmount;
  	
  	private String userTypeName;
    
    public VelocityAndThresholdDto() {		
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

	public String getTransactiontypeName() {
		return transactiontypeName;
	}

	public void setTransactiontypeName(String transactiontypeName) {
		this.transactiontypeName = transactiontypeName;
	}

	public String getStrMinimumAmount() {
		return strMinimumAmount;
	}

	public void setStrMinimumAmount(String strMinimumAmount) {
		this.strMinimumAmount = strMinimumAmount;
	}

	public String getStrMaximumAmount() {
		return strMaximumAmount;
	}

	public void setStrMaximumAmount(String strMaximumAmount) {
		this.strMaximumAmount = strMaximumAmount;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	
}
