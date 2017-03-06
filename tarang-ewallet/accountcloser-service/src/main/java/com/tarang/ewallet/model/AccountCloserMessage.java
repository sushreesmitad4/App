package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

public class AccountCloserMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String message;
	
	private Date messageDate;
	
	private Long creator;
	
	private Long accountcloserId;
	
	private AccountCloser accountCloser;

	public AccountCloserMessage(){
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

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public AccountCloser getAccountCloser() {
		return accountCloser;
	}

	public void setAccountCloser(AccountCloser accountCloser) {
		this.accountCloser = accountCloser;
	}

	public Long getAccountcloserId() {
		return accountcloserId;
	}

	public void setAccountcloserId(Long accountcloserId) {
		this.accountcloserId = accountcloserId;
	}
	
}
