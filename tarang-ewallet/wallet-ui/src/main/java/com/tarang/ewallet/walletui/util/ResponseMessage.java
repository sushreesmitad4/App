package com.tarang.ewallet.walletui.util;

public class ResponseMessage {
	
	private String emailIdErrorMessage;
	
	private String passwordErrorMessage;
	
	private String successMessage;

	public String getEmailIdErrorMessage() {
		return emailIdErrorMessage;
	}

	public void setEmailIdErrorMessage(String emailIdErrorMessage) {
		this.emailIdErrorMessage = emailIdErrorMessage;
	}

	public String getPasswordErrorMessage() {
		return passwordErrorMessage;
	}

	public void setPasswordErrorMessage(String passwordErrorMessage) {
		this.passwordErrorMessage = passwordErrorMessage;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
}
