package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class AccountCloser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private Long authId;

	private String userType;
	
	private Long status;
	
	private Date requestedDate;
	
	private Set<AccountCloserMessage> messages;

	public AccountCloser(){
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public Set<AccountCloserMessage> getMessages() {
		return messages;
	}

	public void setMessages(Set<AccountCloserMessage> messages) {
		this.messages = messages;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
