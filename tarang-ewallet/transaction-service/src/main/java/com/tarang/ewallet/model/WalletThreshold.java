package com.tarang.ewallet.model;

import java.io.Serializable;

public class WalletThreshold implements Serializable,Cloneable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
		
	private Long currency;
	
	private Long country;
	
	private Double maximumamount;

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
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
