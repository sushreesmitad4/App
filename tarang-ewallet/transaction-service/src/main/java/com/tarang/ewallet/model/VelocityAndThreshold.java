package com.tarang.ewallet.model;

import java.io.Serializable;

/**
 * @author shivap
 *
 */
public class VelocityAndThreshold implements Serializable,Cloneable{

	private static final long serialVersionUID = 1L;
 
	
    private Long id;
	
	private Long currency;
	
	private Long country;
	
	private Long transactiontype;
	
    private Double minimumamount;
	
	private Double maximumamount;
	
	private Long userType;
	
    public VelocityAndThreshold() {
	}
	
	public Long getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(Long transactiontype) {
		this.transactiontype = transactiontype;

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

	public Double getMinimumamount() {
		return minimumamount;
	}

	public void setMinimumamount(Double minimumamount) {
		this.minimumamount = minimumamount;
	}

	public Double getMaximumamount() {
		return maximumamount;
	}

	public void setMaximumamount(Double maximumamount) {
		this.maximumamount = maximumamount;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
	