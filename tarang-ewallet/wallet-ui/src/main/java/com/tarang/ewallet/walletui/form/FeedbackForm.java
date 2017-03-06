package com.tarang.ewallet.walletui.form;

import java.util.Date;

import com.tarang.ewallet.dto.FeedbackDto;

public class FeedbackForm {

	private Long querryType;
	
	private String subject;	
	
	private String message;	
	
	private String mode;

	private Long id;
	
	private Long parentId;
	
	private String userType;
	
	private String userMail;
	
	private Long responseSender;
	
	private String dateAndTime;
	
	private String responseMessage;
	
	private Date responseDate;
	
	private String queryTypeName;
	
	private String userTypeName;
	

	public void setFeedbackForm(FeedbackDto feedbackDto) {
		if (feedbackDto != null) {
			querryType = feedbackDto.getQuerryType();
			subject = feedbackDto.getSubject();
			message = feedbackDto.getMessage();
		}
	}

	public FeedbackDto getFeedbackDto() {
		FeedbackDto feedbackDto = new FeedbackDto();
		feedbackDto.setQuerryType(querryType);
		feedbackDto.setSubject(subject);
		feedbackDto.setMessage(message);
		return feedbackDto;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public Long getResponseSender() {
		return responseSender;
	}

	public void setResponseSender(Long responseSender) {
		this.responseSender = responseSender;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
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