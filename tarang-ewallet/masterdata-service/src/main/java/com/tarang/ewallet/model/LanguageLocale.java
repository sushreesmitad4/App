/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author prasadj
 * 
 */
public class LanguageLocale {

	private Long id;

	private Long langId;

	private Long languageId;

	private String code;

	private String name;

	public LanguageLocale() {
	}

	public LanguageLocale(Long id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public LanguageLocale(Long id, Long langId, Long languageId, String code, String name) {
		this.id = id;
		this.langId = langId;
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

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
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
