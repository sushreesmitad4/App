package com.tarang.ewallet.walletui.util;

import java.io.Serializable;

public class RequestMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Header header;
	
	private Long transactionId;
	
	private Long disputeType;
	
	private String requestAmount;
	
	private  Long requestCurrency;
	
	private String message;
	
	private String transactionAmount;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getDisputeType() {
		return disputeType;
	}

	public void setDisputeType(Long disputeType) {
		this.disputeType = disputeType;
	}

	public String getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}

	public Long getRequestCurrency() {
		return requestCurrency;
	}

	public void setRequestCurrency(Long requestCurrency) {
		this.requestCurrency = requestCurrency;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

}
