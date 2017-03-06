package com.tarang.ewallet.model;

import java.io.Serializable;
/**
 * @author rajasekhar
 * 
 */
public class BusinessOwnerInformation implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long addressId;
	private Long nameId;
	private Boolean homeAddress;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the addressId
	 */
	public Long getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	/**
	 * @return the nameId
	 */
	public Long getNameId() {
		return nameId;
	}
	/**
	 * @param nameId the nameId to set
	 */
	public void setNameId(Long nameId) {
		this.nameId = nameId;
	}
	/**
	 * @return the homeAddress
	 */
	public Boolean getHomeAddress() {
		return homeAddress;
	}
	/**
	 * @param homeAddress the homeAddress to set
	 */
	public void setHomeAddress(Boolean homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	
}
