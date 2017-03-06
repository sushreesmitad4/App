/**
 * 
 */
package com.tarang.ewallet.walletui.form;

import com.tarang.ewallet.dto.VelocityAndThresholdDto;
import com.tarang.ewallet.model.VelocityAndThreshold;


/**
 * @author vasanthar
 *
 */
public class VelocityAndThresholdForm {
	
	private Long id;
	
	private Long currency;
	
	private Long country;
	
	private String countryName;
	
	private String currencyName;
	
	private String transactiontypeName;
	
	private Long transactiontype;
	
    private String minimumamount;
	
	private String maximumamount;
	
	private Long usertype;
	
	private String userTypeName;
	
	public Long getUsertype() {
		return usertype;
	}


	public void setUsertype(Long usertype) {
		this.usertype = usertype;
	}


	public Long getTransactiontype() {
		return transactiontype;
	}


	public void setTransactiontype(Long transactiontype) {
		this.transactiontype = transactiontype;
	}


	public String getMinimumamount() {
		return minimumamount;
	}


	public void setMinimumamount(String minimumamount) {
		this.minimumamount = minimumamount;
	}


	public String getMaximumamount() {
		return maximumamount;
	}


	public void setMaximumamount(String maximumamount) {
		this.maximumamount = maximumamount;
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
	
	
	public String getTransactiontypeName() {
		return transactiontypeName;
	}
	
	public void setTransactiontypeName(String transactiontypeName) {
		this.transactiontypeName = transactiontypeName;
	}
	
	
	public String getUserTypeName() {
		return userTypeName;
	}


	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}


	public VelocityAndThreshold getVelocityAndThreshold() {
		VelocityAndThresholdDto velocitydto = new VelocityAndThresholdDto();
		
		velocitydto.setId(id);
		velocitydto.setCountry(country);
		velocitydto.setCountryName(countryName);
		velocitydto.setCurrency(currency);
		velocitydto.setCurrencyName(currencyName);
		velocitydto.setTransactiontype(transactiontype);
		velocitydto.setTransactiontypeName(transactiontypeName);
		velocitydto.setUserType(usertype);
		return velocitydto;
	}

}

