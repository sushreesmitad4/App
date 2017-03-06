package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long title;
	private Long nameId;
	private Long addressId;
	private Long authenticationId;
	private Long phoneIdOne;
	private Date dateOfBirth;
	private Boolean profileComplet;

	public Customer() {	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(Long title) {
		this.title = title;
	}
	
	public Long getTitle() {
		return title;
	}

	public Long getNameId() {
		return nameId;
	}

	public void setNameId(Long nameId) {
		this.nameId = nameId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(Long authenticationId) {
		this.authenticationId = authenticationId;
	}

	public Long getPhoneIdOne() {
		return phoneIdOne;
	}

	public void setPhoneIdOne(Long phoneIdOne) {
		this.phoneIdOne = phoneIdOne;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Boolean getProfileComplet() {
		return profileComplet;
	}

	public void setProfileComplet(Boolean profileComplet) {
		this.profileComplet = profileComplet;
	}
	
}