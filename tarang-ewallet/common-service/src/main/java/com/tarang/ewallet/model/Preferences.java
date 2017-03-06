package com.tarang.ewallet.model;

import java.io.Serializable;

public class Preferences implements Serializable {
	
private static final long serialVersionUID = 1L;
	
  /*query_type BigInt */
    private Long id;	
	private Long authentication;
	private Long language;
	private Long currency;
	
	
	public Long getCurrency() {
		return currency;
	}
	public void setCurrency(Long currency) {
		this.currency = currency;
	}
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
