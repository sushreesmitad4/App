package com.tarang.ewallet.model;

public class AccountClosureStatusLocale {
	
	private Long id;

	private Long accountClosureStatusId;

	private Long languageId;

	private String name;
	
	public AccountClosureStatusLocale(){
		
	}
	
	public AccountClosureStatusLocale(Long id, Long languageId, Long accountClosureStatusId, String name){
		this.id = id;
		this.languageId = languageId;
		this.accountClosureStatusId = accountClosureStatusId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountClosureStatusId() {
		return accountClosureStatusId;
	}

	public void setAccountClosureStatusId(Long accountClosureStatusId) {
		this.accountClosureStatusId = accountClosureStatusId;
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
