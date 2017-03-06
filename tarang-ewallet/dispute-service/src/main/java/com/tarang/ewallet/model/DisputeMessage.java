/**
 * 
 */
package com.tarang.ewallet.model;

import java.util.Date;

/**
 * @author vasanthar
 *
 */
public class DisputeMessage {
	
	private Long id;
	
	private String message;
	
	private Date creationDate;
	
	private Long creator;
	
	private Dispute dispute;
	
	public DisputeMessage(){
	}
		
	public DisputeMessage(String message, Date creationDate, Long creator) {
		this.message = message;
		this.creationDate = creationDate;
		this.creator = creator;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Dispute getDispute() {
		return dispute;
	}

	public void setDispute(Dispute dispute) {
		this.dispute = dispute;
	}
	
}
