package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

public class EmailHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String emailTo;

	private String message;

	private String subject;

	private Date creationDate;

	private Date emailSentDate;

	private Boolean emailStatus;
	/**
	 * for scheduler
	 */
	private Integer occurrence;
	
	/**
	 * email sending periority for future purpose
	 */
	private Integer periority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getEmailSentDate() {
		return emailSentDate;
	}

	public void setEmailSentDate(Date emailSentDate) {
		this.emailSentDate = emailSentDate;
	}

	public Boolean getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(Boolean emailStatus) {
		this.emailStatus = emailStatus;
	}

	public Integer getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(Integer occurrence) {
		this.occurrence = occurrence;
	}

	public Integer getPeriority() {
		return periority;
	}

	public void setPeriority(Integer periority) {
		this.periority = periority;
	}

	public void incrementOccurance() {
		if (null == occurrence) {
			occurrence = 1;
		} else {
			this.occurrence++;
		}
	}

}
