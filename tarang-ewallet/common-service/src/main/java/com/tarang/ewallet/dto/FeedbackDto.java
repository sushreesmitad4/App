package com.tarang.ewallet.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class FeedbackDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long parentId;
	
	private Long querryType;
	
	private String subject;	
	
	private String message;	
	
	private Date dateAndTime;	
	
	private String userType;	
	
	private Long userId;	
	
	private Long responseSender;	
	
	private Timestamp responseDate;	
	
	private String responseMessage;	
	
	private String userMail;
	
	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the querryType
	 */
	public Long getQuerryType() {
		return querryType;
	}
	/**
	 * @param querryType the querryType to set
	 */
	public void setQuerryType(Long querryType) {
		this.querryType = querryType;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the dateAndTime
	 */
	public Date getDateAndTime() {
		return dateAndTime;
	}
	/**
	 * @param dateAndTime the dateAndTime to set
	 */
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the responseSender
	 */
	public Long getResponseSender() {
		return responseSender;
	}
	/**
	 * @param responseSender the responseSender to set
	 */
	public void setResponseSender(Long responseSender) {
		this.responseSender = responseSender;
	}
	/**
	 * @return the responseDate
	 */
	public Timestamp getResponseDate() {
		return responseDate;
	}
	/**
	 * @param responseDate the responseDate to set
	 */
	public void setResponseDate(Timestamp responseDate) {
		this.responseDate = responseDate;
	}
	/**
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		return responseMessage;
	}
	/**
	 * @param responseMessage the responseMessage to set
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	/**
	 * @return the userMail
	 */
	public String getUserMail() {
		return userMail;
	}
	/**
	 * @param userMail the userMail to set
	 */
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
}
