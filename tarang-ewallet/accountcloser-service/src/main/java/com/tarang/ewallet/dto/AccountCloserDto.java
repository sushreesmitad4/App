package com.tarang.ewallet.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tarang.ewallet.util.DateConvertion;


public class AccountCloserDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long authId;
	
	private String userType;
	
	private Long status;
	private String statusStr;
	
	private Date requestedDate;
	
	private List<AccountCloserMessageDto> messageList;
	
	private String message;
	
	private Long creator;
	
	// for grid
	
	private String personOrOrganisationName;
	
	private String userStatus;
	
	
	private String emailId;
	
	private Date registrationDate;
	
	private Date lastTransactionDate;

	private Map<Long,String> wallets;
	
	public AccountCloserDto(){
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

	public void setUserType(String usertype) {
		this.userType = usertype;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}
	
	public String getRequestedDateStr() {
		return DateConvertion.dateToString(requestedDate);
	}

	public List<AccountCloserMessageDto> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<AccountCloserMessageDto> messageList) {
		this.messageList = messageList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public String getRegistrationDateStr() {
		return DateConvertion.dateToString(registrationDate);
	}

	public Date getLastTransactionDate() {
		return lastTransactionDate;
	}

	public void setLastTransactionDate(Date lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}
	
	public String getLastTransactionDateStr() {
		return DateConvertion.dateToString(lastTransactionDate);
	}

	public Map<Long, String> getWallets() {
		return wallets;
	}

	public void setWallets(Map<Long, String> wallets) {
		this.wallets = wallets;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getPersonOrOrganisationName() {
		return personOrOrganisationName;
	}

	public void setPersonOrOrganisationName(String personOrOrganisationName) {
		this.personOrOrganisationName = personOrOrganisationName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	
}