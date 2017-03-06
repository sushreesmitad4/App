/**
 * 
 */
package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:05:27 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String ipAddress;
	
	private Long authId;
	
	private String accountHolderName;
	
	private Long status;
	
	private Boolean defaultAccount;
	
	private Long deletedStatus;
	
	private String atype;
	
	private Date creationDate;

	private Boolean jointAccount;
	
	private String simNumber;
	
	private Long typeOfRequest;
	
	private String imeiNumber;
	
	public Account(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Boolean getDefaultAccount() {
		return defaultAccount;
	}

	public void setDefaultAccount(Boolean defaultAccount) {
		this.defaultAccount = defaultAccount;
	}

	public Long getDeletedStatus() {
		return deletedStatus;
	}

	public void setDeletedStatus(Long deletedStatus) {
		this.deletedStatus = deletedStatus;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getJointAccount() {
		return jointAccount;
	}

	public void setJointAccount(Boolean jointAccount) {
		this.jointAccount = jointAccount;
	}

	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}

	public Long getTypeOfRequest() {
		return typeOfRequest;
	}

	public void setTypeOfRequest(Long typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	
}
