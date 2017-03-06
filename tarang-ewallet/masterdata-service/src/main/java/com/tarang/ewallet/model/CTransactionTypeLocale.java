package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class CTransactionTypeLocale {

	private Long id;

	private Long ctTypeId;

	private Long languageId;

	private String name;

	public CTransactionTypeLocale() {
	}

	public CTransactionTypeLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public CTransactionTypeLocale(Long id, Long languageId, Long ctTypeId, 
			String name) {
		this.id = id;
		this.ctTypeId = ctTypeId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCtTypeId() {
		return ctTypeId;
	}

	public void setCtTypeId(Long ctTypeId) {
		this.ctTypeId = ctTypeId;
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
