package com.tarang.ewallet.dto;

import java.io.Serializable;

public class PreferencesDto implements Serializable, Cloneable {
	
private static final long serialVersionUID = 1L;
	
	private Long id;	
	private Long authentication;
	private Long language;
	private Long currency;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAuthentication() {
		return authentication;
	}
	public void setAuthentication(Long authentication) {
		this.authentication = authentication;
	}
	public Long getLanguage() {
		return language;
	}
	public void setLanguage(Long language) {
		this.language = language;
	}
	public Long getCurrency() {
		return currency;
	}
	public void setCurrency(Long currency) {
		this.currency = currency;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
