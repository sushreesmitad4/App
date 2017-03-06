package com.tarang.ewallet.model;

public class MerchantPercentageAnualRevenueLocale {

	private Long id;

	private Long mparId;

	private Long languageId;

	private String anualRevenue;

	public MerchantPercentageAnualRevenueLocale() {
	}

	public MerchantPercentageAnualRevenueLocale(Long id, String anualRevenue) {
		this.id = id;
		this.anualRevenue = anualRevenue;
	}

	/**
	 * @param id
	 * @param mparId
	 * @param languageId
	 * @param anualRevenue
	 */
	public MerchantPercentageAnualRevenueLocale(Long id, Long languageId, Long mparId, String anualRevenue) {
		this.id = id;
		this.mparId = mparId;
		this.languageId = languageId;
		this.anualRevenue = anualRevenue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMparId() {
		return mparId;
	}

	public void setMparId(Long mparId) {
		this.mparId = mparId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getAnualRevenue() {
		return anualRevenue;
	}

	public void setAnualRevenue(String anualRevenue) {
		this.anualRevenue = anualRevenue;
	}

}
