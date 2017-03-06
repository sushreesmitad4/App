package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class CurrencyLocale {

	private Long id;

	private Long currencyId;

	private Long languageId;

	private String name;

	public CurrencyLocale() {
	}

	public CurrencyLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public CurrencyLocale(Long id, Long languageId, Long currencyId, String name) {
		this.id = id;
		this.languageId = languageId;
		this.currencyId = currencyId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
