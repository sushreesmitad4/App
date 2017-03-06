/**
 * 
 */
package com.tarang.ewallet.model;

import java.io.Serializable;

/**
 * @author vasanthar
 *
 */
public class Tax implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long country;

	private Double percentage;
	
	private String percentageAmount;
	
	private String countryName;
	
	public Tax() {
	}

	public Tax(Long country, Double percentage) {
		this.country = country;
		this.percentage = percentage;
	}

	public Long getId() {
		return id;
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

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getPercentageAmount() {
		return percentageAmount;
	}

	public void setPercentageAmount(String percentageAmount) {
		this.percentageAmount = percentageAmount;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
