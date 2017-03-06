package com.tarang.ewallet.walletui.form;

import com.tarang.ewallet.dto.ForgotPasswordDto;

public class ForgotPasswordForm{
	private String emailId;
	private Long question1;
	//answer for question1
	private String hint1;
	private Long question2;
	//answer for question2
	private String hint2;
	private String userType;
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
	public Long getQuestion2() {
		return question2;
	}
	public void setQuestion2(Long question2) {
		this.question2 = question2;
	}
	public String getHint2() {
		return hint2;
	}
	public void setHint2(String hint2) {
		this.hint2 = hint2;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public ForgotPasswordDto getForgotPassword(){
		ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
		
		forgotPasswordDto.setEmailId(emailId);
		forgotPasswordDto.setQuestion1(question1);
		forgotPasswordDto.setHint1(hint1);
		forgotPasswordDto.setUserType(userType);
		return forgotPasswordDto;
	}
	
	public void setForgotPassword(ForgotPasswordDto forgotPasswordDto){
				
		emailId = forgotPasswordDto.getEmailId();
		question1 = forgotPasswordDto.getQuestion1();
		hint1 = forgotPasswordDto.getHint1();
		userType = forgotPasswordDto.getUserType();
		
	}
}
