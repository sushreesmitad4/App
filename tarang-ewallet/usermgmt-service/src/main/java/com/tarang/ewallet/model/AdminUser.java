package com.tarang.ewallet.model;

import java.io.Serializable;
/**
 * @author kedarnathd
 *
 */
@SuppressWarnings("serial")
public class AdminUser implements Serializable{
	
	@SuppressWarnings("unused")
	private static final Long serialVersionUID = 1L;
	
	private Long id;
	private Long addressId;
	private Long nameId;
	private Long authenticationId;
	private Long roleId;
	private Long phoneIdOne;
	
	public AdminUser() {
	}
	public AdminUser(Long nameId, Long addressId, Long authenticationId,Long roleId, Long phoneIdOne) {
		this.nameId = nameId;
		this.addressId = addressId;
		this.authenticationId = authenticationId;
		this .roleId = roleId;
		this.phoneIdOne = phoneIdOne;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public Long getNameId() {
		return nameId;
	}
	public void setNameId(Long nameId) {
		this.nameId = nameId;
	}
	public Long getAuthenticationId() {
		return authenticationId;
	}
	public void setAuthenticationId(Long authenticationId) {
		this.authenticationId = authenticationId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getPhoneIdOne() {
		return phoneIdOne;
	}
	public void setPhoneIdOne(Long phoneIdOne) {
		this.phoneIdOne = phoneIdOne;
	}
	
}
