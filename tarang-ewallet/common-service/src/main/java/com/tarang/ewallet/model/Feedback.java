package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

public class Feedback implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/* id BigInt */	
	private Long id;	            
	
	private Long parentId;
	
	/* query_type BigInt */ 
	private Long querryType;	    
	
	/* subject character varying(100)*/
	private String subject;	        
	
	/* message character varying(1000) */ 
	private String message;	        
	
	/* dateAndTime TimeStamp */ 
	private Date dateAndTime;	    
	
	/* userType character varying(1) */
	private String userType;	    
	
	/* userId BigInt */
	private Long userId;	        
	
	/* responseSender BigInt */
	private Long responseSender;    
	
	/* responseDate TimeStamp */
	private Date responseDate;	
	
	/* responseMessage character varying(1000) */
	private String responseMessage;	
	
	/* userEmail character varying(40)) */
	private String userMail;        
	
	/* only for display purpose */
	private String responseSenderMailId;
	
	private String responseDateAsString;
	
	private String reciverName;
	
	private Long languageId;
	
	private String queryTypeName;
	
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
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
	public Date getResponseDate() {
		return responseDate;
	}
	
	/**
	 * @param responseDate the responseDate to set
	 */
	public void setResponseDate(Date responseDate) {
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
	
	public String getResponseSenderMailId() {
		return responseSenderMailId;
	}
	
	public void setResponseSenderMailId(String responseSenderMailId) {
		this.responseSenderMailId = responseSenderMailId;
	}

	public String getResponseDateAsString() {
		return responseDateAsString;
	}

	public void setResponseDateAsString(String responseDateAsString) {
		this.responseDateAsString = responseDateAsString;
	}

	public String getReciverName() {
		return reciverName;
	}

	public void setReciverName(String reciverName) {
		this.reciverName = reciverName;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getQueryTypeName() {
		return queryTypeName;
	}

	public void setQueryTypeName(String queryTypeName) {
		this.queryTypeName = queryTypeName;
	}
	
}
