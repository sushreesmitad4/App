package com.tarang.ewallet.walletui.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tarang.ewallet.dto.AccountCloserMessageDto;


public class AccountCloserFormView implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String emailid;

	private String userType;

	private Date registrationDate;

	private String userStatus;

	private Long authId;

	private Long accCloserStatus;
	
	private String accountCloserStatus;

	private Date requestedDate;

	private String message;

	private Date messagedate;

	private Long creator;

	private Date lastTransactionDate;

	private String personOrOrganisationName;

	private Boolean isRecurringPaymentExist;

	private List<AccountCloserMessageDto> messageList;

	private Map<Long, String> userWalletDetails;

	// for display purpose
	
	private String registrationDateAsString;
	
	private String requestedDateAsString;
	
	private String lastTransactionDateAsString;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public String getAccountCloserStatus() {
		return accountCloserStatus;
	}

	public void setAccountCloserStatus(String accountCloserStatus) {
		this.accountCloserStatus = accountCloserStatus;
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

	public Date getMessagedate() {
		return messagedate;
	}

	public void setMessagedate(Date messagedate) {
		this.messagedate = messagedate;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getLastTransactionDate() {
		return lastTransactionDate;
	}

	public void setLastTransactionDate(Date lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	public String getPersonOrOrganisationName() {
		return personOrOrganisationName;
	}

	public void setPersonOrOrganisationName(String personOrOrganisationName) {
		this.personOrOrganisationName = personOrOrganisationName;
	}

	public Boolean getIsRecurringPaymentExist() {
		return isRecurringPaymentExist;
	}

	public void setIsRecurringPaymentExist(Boolean isRecurringPaymentExist) {
		this.isRecurringPaymentExist = isRecurringPaymentExist;
	}

	public List<AccountCloserMessageDto> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<AccountCloserMessageDto> messageList) {
		this.messageList = messageList;
	}

	

	public Map<Long, String> getUserWalletDetails() {
		return userWalletDetails;
	}

	public void setUserWalletDetails(Map<Long, String> userWalletDetails) {
		this.userWalletDetails = userWalletDetails;
	}

	public Long getAccCloserStatus() {
		return accCloserStatus;
	}

	public void setAccCloserStatus(Long accCloserStatus) {
		this.accCloserStatus = accCloserStatus;
	}

	public String getRequestedDateAsString() {
		return requestedDateAsString;
	}

	public void setRequestedDateAsString(String requestedDateAsString) {
		this.requestedDateAsString = requestedDateAsString;
	}

	public String getLastTransactionDateAsString() {
		return lastTransactionDateAsString;
	}

	public void setLastTransactionDateAsString(String lastTransactionDateAsString) {
		this.lastTransactionDateAsString = lastTransactionDateAsString;
	}

	public String getRegistrationDateAsString() {
		return registrationDateAsString;
	}

	public void setRegistrationDateAsString(String registrationDateAsString) {
		this.registrationDateAsString = registrationDateAsString;
	}

	
}
