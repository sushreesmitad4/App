package com.tarang.ewallet.walletui.form;

public class AddMoneyForm {
	
	
	private String amount;
	private String name;
	private String userType;
	private String emailId;
	private String orderId;
	private String requestedCurrency;
	
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRequestedCurrency() {
		return requestedCurrency;
	}
	public void setRequestedCurrency(String requestedCurrency) {
		this.requestedCurrency = requestedCurrency;
	}
	
	
	@Override
	public String toString() {
		return "AddMoneyForm [amount=" + amount + ", name=" + name + ", userType=" + userType + ", emailId=" + emailId
				+ ", orderId=" + orderId + ", requestedCurrency=" + requestedCurrency + "]";
	}
	

}
