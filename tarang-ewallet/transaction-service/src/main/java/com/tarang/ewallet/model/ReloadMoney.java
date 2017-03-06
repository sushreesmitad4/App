package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

public class ReloadMoney implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long authId;
	private Long trxId;
	private Long accountId;
	private Double reloadAmount;
	private Date currentDate;
	private Long historyId;
	private Long typeOfRequest;
	
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
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Double getReloadAmount() {
		return reloadAmount;
	}
	public void setReloadAmount(Double reloadAmount) {
		this.reloadAmount = reloadAmount;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public Long getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
	public Long getTypeOfRequest() {
		return typeOfRequest;
	}
	public void setTypeOfRequest(Long typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}
	
	
}
