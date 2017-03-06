package com.tarang.ewallet.model;

public class AccountClosureStatus {
	
	private Long id;

	private String name;

	public AccountClosureStatus() {
	}

	public AccountClosureStatus(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
