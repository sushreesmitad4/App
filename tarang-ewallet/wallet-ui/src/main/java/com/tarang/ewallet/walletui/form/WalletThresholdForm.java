package com.tarang.ewallet.walletui.form;

import com.tarang.ewallet.dto.WalletThresholdDto;

public class WalletThresholdForm {

     private Long id;
     
     private Long country;
	
	private Long currency;
	
	private String maximumamount;
	
    private String countryName;
	
	private String currencyName;

	public Long getId() {
		return id;
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

	public void setId(Long id) {
		this.id = id;
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

	public String getMaximumamount() {
		return maximumamount;
	}

	public void setMaximumamount(String maximumamount) {
		this.maximumamount = maximumamount;
	}

	
	public WalletThresholdDto getWalletThreshold() {
		WalletThresholdDto thresholddto = new WalletThresholdDto();
		
		thresholddto.setId(id);
		thresholddto.setCountry(country);
		thresholddto.setCountryName(countryName);
		thresholddto.setCurrency(currency);
		thresholddto.setCurrencyName(currencyName);
		
		return thresholddto;
	}


}
