package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class ATransactionTypeLocale {

	private Long id;

	private Long atTypeId;

	private Long languageId;

	private String name;

	public ATransactionTypeLocale() {
	}

	public ATransactionTypeLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public ATransactionTypeLocale(Long id, Long languageId, Long atTypeId, String name) {
		this.id = id;
		this.atTypeId = atTypeId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAtTypeId() {
		return atTypeId;
	}

	public void setAtTypeId(Long atTypeId) {
		this.atTypeId = atTypeId;
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
