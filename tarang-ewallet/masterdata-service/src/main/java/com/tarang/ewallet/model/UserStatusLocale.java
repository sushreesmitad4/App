package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class UserStatusLocale {

	private Long id;

	private Long userStatusId;

	private Long languageId;

	private String name;

	public UserStatusLocale() {
	}

	public UserStatusLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public UserStatusLocale(Long id, Long languageId, Long userStatusId, String name) {
		this.id = id;
		this.userStatusId = userStatusId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserStatusId() {
		return userStatusId;
	}

	public void setUserStatusId(Long userStatusId) {
		this.userStatusId = userStatusId;
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
