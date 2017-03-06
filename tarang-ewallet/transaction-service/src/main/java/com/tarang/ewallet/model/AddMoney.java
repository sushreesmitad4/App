package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

public class AddMoney implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long authId;
	private Long trxId;
	private Double addMoneyAmount;
	private Date currentDate;
	/*private Long historyId;*/
	private Long typeOfRequest;
	private String orderId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAuthId() {
		return authId;
	}
	public void setAuthId(Long authId) {
		this.authId = authId;
	}
	public Long getTrxId() {
		return trxId;
	}
	public void setTrxId(Long trxId) {
		this.trxId = trxId;
	}
	public Double getAddMoneyAmount() {
		return addMoneyAmount;
	}
	public void setAddMoneyAmount(Double addMoneyAmount) {
		this.addMoneyAmount = addMoneyAmount;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	/*public Long getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}*/
	public Long getTypeOfRequest() {
		return typeOfRequest;
	}
	public void setTypeOfRequest(Long typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Override
	public String toString() {
		return "AddMoney [id=" + id + ", authId=" + authId + ", trxId=" + trxId + ", addMoneyAmount=" + addMoneyAmount
				+ ", currentDate=" + currentDate + ", typeOfRequest=" + typeOfRequest
				+ ", orderId=" + orderId + "]";
	}

}
