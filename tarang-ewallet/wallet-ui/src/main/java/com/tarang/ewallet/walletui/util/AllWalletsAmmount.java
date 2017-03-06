package com.tarang.ewallet.walletui.util;

import java.io.Serializable;

public class AllWalletsAmmount implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long currency;
	
	private Double amount;
	
	private Double customerAmount;
	
	private Double merchantAmount;

	private String currencyName;
	
	private String amountString;
	
	private String customerAmountString;
	
	private String merchantAmountString;

	public String getAmountString() {
		return amountString;
	}

	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getCustomerAmount() {
		return customerAmount;
	}

	public void setCustomerAmount(Double customerAmount) {
		this.customerAmount = customerAmount;
	}

	public Double getMerchantAmount() {
		return merchantAmount;
	}

	public void setMerchantAmount(Double merchantAmount) {
		this.merchantAmount = merchantAmount;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCustomerAmountString() {
		return customerAmountString;
	}

	public void setCustomerAmountString(String customerAmountString) {
		this.customerAmountString = customerAmountString;
	}

	public String getMerchantAmountString() {
		return merchantAmountString;
	}

	public void setMerchantAmountString(String merchantAmountString) {
		this.merchantAmountString = merchantAmountString;
	}
	
}