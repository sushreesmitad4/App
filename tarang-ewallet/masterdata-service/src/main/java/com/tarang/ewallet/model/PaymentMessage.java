package com.tarang.ewallet.model;

public class PaymentMessage {
	
	private Long id;
	private String errorCode;
	private String errorMessage;
	
	
	public PaymentMessage(){
	}
	
	public PaymentMessage(Long id, String errorcode, String errormessage){
		this.id = id;
		this.errorMessage = errormessage;
		this.errorCode = errorcode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
