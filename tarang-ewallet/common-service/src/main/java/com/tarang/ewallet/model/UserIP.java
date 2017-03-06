/**
 * 
 */
package com.tarang.ewallet.model;

import java.util.Date;

/**
 * @author vasanthar
 *	This is to hold information for oneTimeIPcheck service
 */
public class UserIP implements Cloneable{
	
	private Long id;
	
	private Long authId;
	
	private String ipAddress;
	
	private String code;
	
	private Date currentDate;

	public UserIP() {
		
	}
	
	public UserIP(Long authId, String ipAddress) {
		this.authId = authId;
		this.ipAddress = ipAddress;
	}
	
	public UserIP(Long authId, String ipAddress, Date currentDate, String code) {
		this.authId = authId;
		this.ipAddress = ipAddress;
		this.currentDate = currentDate;
		this.code = code;
	}	

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
