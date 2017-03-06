package com.tarang.ewallet.dto;

import java.io.Serializable;

public class ForgotPasswordDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String emailId;
	private Long question1;
	/* answer for question1 */
	private String hint1;
	private String userType;
	private Long languageId;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Long getQuestion1() {
		return question1;
	}
	public void setQuestion1(Long question1) {
		this.question1 = question1;
	}
	public String getHint1() {
		return hint1;
	}
	public void setHint1(String hint1) {
		this.hint1 = hint1;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Long getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	
}
