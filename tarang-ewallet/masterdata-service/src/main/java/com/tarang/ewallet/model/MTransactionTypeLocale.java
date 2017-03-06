package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class MTransactionTypeLocale {

	private Long id;

	private Long mtTypeId;

	private Long languageId;

	private String name;

	public MTransactionTypeLocale() {
	}

	public MTransactionTypeLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public MTransactionTypeLocale(Long id, Long languageId, Long mtTypeId, String name) {
		this.id = id;
		this.mtTypeId = mtTypeId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMtTypeId() {
		return mtTypeId;
	}

	public void setMtTypeId(Long mtTypeId) {
		this.mtTypeId = mtTypeId;
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
