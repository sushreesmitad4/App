package com.tarang.ewallet.walletui.form;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.AccountCloserMessageDto;


public class AccountCloserForm {
	
	private Long acountCloserId;

	private Date requestedDate;

	private String message;

	private Long status;
	
	private List<AccountCloserMessageDto> messageList;

	public Long getAcountCloserId() {
		return acountCloserId;
	}

	public void setAcountCloserId(Long acountCloserId) {
		this.acountCloserId = acountCloserId;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public List<AccountCloserMessageDto> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<AccountCloserMessageDto> messageList) {
		this.messageList = messageList;
	}
}
