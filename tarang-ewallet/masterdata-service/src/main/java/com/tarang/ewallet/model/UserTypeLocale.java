package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class UserTypeLocale {

	private Long id;

	private Long userTypeId;

	private Long languageId;

	private String name;

	public UserTypeLocale() {
	}

	public UserTypeLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public UserTypeLocale(Long id, Long languageId, Long userTypeId, String name) {
		this.id = id;
		this.userTypeId = userTypeId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
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
