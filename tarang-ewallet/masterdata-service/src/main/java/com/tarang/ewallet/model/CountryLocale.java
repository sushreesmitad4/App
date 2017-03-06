package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class CountryLocale {

	private Long id;

	private Long countryId;

	private Long languageId;

	private String code;

	private String name;

	public CountryLocale() {
	}

	public CountryLocale(Long id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public CountryLocale(Long id, Long languageId, Long countryId, String code, String name) {
		this.id = id;
		this.countryId = countryId;
		this.languageId = languageId;
		this.code = code;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
