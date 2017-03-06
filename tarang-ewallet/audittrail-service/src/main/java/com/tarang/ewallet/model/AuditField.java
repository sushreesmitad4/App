package com.tarang.ewallet.model;

/**
 * @author vasanthar
 * 
 */
public class AuditField {

	private Long id;
	private Audit audits;
	private String fieldName;
	private String oldValue;
	private String newValue;

	public AuditField() {
	}

	public AuditField(String fieldName, String oldValue, String newValue) {
		this.fieldName = fieldName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Audit getAudits() {
		return audits;
	}

	public void setAudits(Audit audits) {
		this.audits = audits;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

}
