package com.tarang.ewallet.dto;

import java.io.Serializable;
import java.util.Date;

import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.util.GlobalLitterals;



/**
* @author  : prasadj
* @date    : Oct 19, 2012
* @time    : 12:47:26 PM
* @version : 1.0.0
* @comments: 
*
*/

public class UserDto implements Serializable {

	private static final long serialVersionUID = -3264123954427443831L;

	/* Following fields are used for display */ 
	private Long id;

	private String ipAddress;
	
	private String legalName;
	
	private String firstName;
	
	private String lastName;
	
	private String roleName;
	
	private String emailId;

	private Boolean active;

	private Boolean deleted;

	private Long status;

	private Boolean blocked;

	private Date creationDate;
	
	private String statusName;
	
	private String creDate;
	
	private String fullName;
	
	private String activeName;
	
	public UserDto() {
	}

	public UserDto(Long id, String firstName, String lastName, String emailId, Boolean active,
			Long status, Boolean blocked, Date creationDate) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.active = active;
		setStatus(status);
		this.blocked = blocked;
		this.creationDate = creationDate;
		this.creDate = CommonUtil.getDate(creationDate);
		this.fullName = getName();
	}
	
	public UserDto(Long id, String firstName, String lastName,String roleName, String emailId, Boolean active,
			Long status, Boolean blocked, Date creationDate) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roleName = roleName;
		this.emailId = emailId;
		this.active = active;
		setStatus(status);
		this.blocked = blocked;
		this.creationDate = creationDate;
		this.creDate = CommonUtil.getDate(creationDate);
		this.fullName = getName();
	}

	public UserDto(Long id, String legalName, String emailId, Boolean active,
			Long status, Boolean blocked, Date creationDate) {
		this.id = id;
		this.legalName = legalName;
		this.emailId = emailId;
		this.active = active;
		setStatus(status);
		this.blocked = blocked;
		this.creationDate = creationDate;
		this.creDate = CommonUtil.getDate(creationDate);
		this.fullName = getName();
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

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public Long getStatus() {
		return status;
	}

	public final void setStatus(Long status) {
		this.status = status;
		if(UserStatusConstants.DELETED.equals(status)){
			this.deleted = Boolean.TRUE; 
		}else{
			this.deleted = Boolean.FALSE;
		}
	}

	public Boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		this.fullName = getName();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		this.fullName = getName();
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreDate() {
		return creDate;
	}

	public void setCreDate(String creDate) {
		this.creDate = creDate;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	private String getName(){
		return this.firstName + GlobalLitterals.SPACE_STRING + this.lastName;
	}

	public String getFullName(){
		return fullName;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	
}
