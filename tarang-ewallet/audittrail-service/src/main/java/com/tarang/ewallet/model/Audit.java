package com.tarang.ewallet.model;

import java.util.Date;
import java.util.List;

/**
 * @author vasanthar
 *
 */
public class Audit {
	
	private Long id;
	private Long authId;
	private String moduleName;
	private Long status;
	private String emailId;
	private Date creationDate;
	private String userType;
	private List<AuditField> auditFields;
	
	public Audit() {
	}	

	public Audit(Long authId, String moduleName, Long status, String emailId,
			Date creationDate, String userType, List<AuditField> auditFields) {
		this.authId = authId;
		this.moduleName = moduleName;
		this.status = status;
		this.emailId = emailId;
		this.creationDate = creationDate;
		this.userType = userType;
		this.auditFields = auditFields;
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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<AuditField> getAuditFields() {
		return auditFields;
	}

	public void setAuditFields(List<AuditField> auditFields) {
		this.auditFields = auditFields;
	}	
	

}
