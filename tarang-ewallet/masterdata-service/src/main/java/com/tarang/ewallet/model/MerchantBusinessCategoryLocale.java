package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class MerchantBusinessCategoryLocale {

	private Long id;

	private Long mbcId;

	private Long languageId;

	private String category;

	public MerchantBusinessCategoryLocale() {
	}

	public MerchantBusinessCategoryLocale(Long id, String category) {
		this.id = id;
		this.category = category;
	}

	public MerchantBusinessCategoryLocale(Long id, Long languageId, Long mbcId, String category) {
		this.id = id;
		this.mbcId = mbcId;
		this.languageId = languageId;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMbcId() {
		return mbcId;
	}

	public void setMbcId(Long mbcId) {
		this.mbcId = mbcId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
