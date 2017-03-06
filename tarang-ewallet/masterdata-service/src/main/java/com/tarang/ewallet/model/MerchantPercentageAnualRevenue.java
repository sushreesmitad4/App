package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class MerchantPercentageAnualRevenue {

	private Long id;

	private String anualRevenue;

	public MerchantPercentageAnualRevenue() {
	}

	public MerchantPercentageAnualRevenue(Long id, String anualRevenue) {
		this.id = id;
		this.anualRevenue = anualRevenue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnualRevenue() {
		return anualRevenue;
	}

	public void setAnualRevenue(String anualRevenue) {
		this.anualRevenue = anualRevenue;
	}

}
