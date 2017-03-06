package com.tarang.ewallet.masterdata.util;

import java.io.Serializable;

public class MasterWalletGridDisplayDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private Long currency;
	
	private Double amount;
	
	private String currencyName;
	
	private String amountString;
	
	private Double fee;
	
	private Double tax;
	
	private Double total;
	
	private String feeString;
	
	private String taxString;
	
	private String totalString;

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAmountString() {
		return amountString;
	}

	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}

	public String getFeeString() {
		return feeString;
	}

	public void setFeeString(String feeString) {
		this.feeString = feeString;
	}

	public String getTaxString() {
		return taxString;
	}

	public void setTaxString(String taxString) {
		this.taxString = taxString;
	}

	
	public String getTotalString() {
		return totalString;
	}

	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	
	

}
