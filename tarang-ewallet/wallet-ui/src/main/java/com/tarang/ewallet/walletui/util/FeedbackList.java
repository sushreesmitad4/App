package com.tarang.ewallet.walletui.util;

import java.io.Serializable;
import java.util.Date;

import com.tarang.ewallet.util.DateConvertion;

public class FeedbackList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;	
	
	private Long querryType;
	
	private String subject;
	
	private String dateAndTime;	 
	
	private String userType;
	
	private String responseDate;	
	
	private String userMail;
	
	private String queryTypeName;
	
	private String userTypeName;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getQuerryType() {
		return querryType;
	}
	
	public void setQuerryType(Long querryType) {
		this.querryType = querryType;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getDateAndTime() {
		return dateAndTime;
	}
	
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = DateConvertion.dateToString(dateAndTime);
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getResponseDate() {
		return responseDate;
	}
	
	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}
	
	public String getUserMail() {
		return userMail;
	}
	
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getQueryTypeName() {
		return queryTypeName;
	}

	public void setQueryTypeName(String queryTypeName) {
		this.queryTypeName = queryTypeName;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
}
