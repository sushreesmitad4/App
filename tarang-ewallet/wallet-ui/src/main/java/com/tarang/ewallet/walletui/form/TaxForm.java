/**
 * 
 */
package com.tarang.ewallet.walletui.form;

/**
 * @author vasanthar
 *
 */
public class TaxForm {
	
	private Long id;
	
	private Long country;

	private String percentage;	

	public TaxForm() {	
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

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

}
