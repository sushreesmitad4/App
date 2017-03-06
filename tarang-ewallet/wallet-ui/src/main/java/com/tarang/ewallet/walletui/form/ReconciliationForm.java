package com.tarang.ewallet.walletui.form;

import java.io.Serializable;
import java.util.Date;

public class ReconciliationForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
private Long id;
	
	private Double actualAmount;
	
	private Long pgTxnId;  
	
	private String scheme;
	
	private String purchaseDate;
	
	private String purchaseTime;
	
	private Double txnAmount;
	
	private Double finalAmount; 
	
	private String txnCurrency;
	
	private Date creationDate;
	
	private Date updateDate;
	
	private Double selectedAmount;
	
	private Long status;
	
	/* extra properties*/
	
	private String fromDate;
	
	private String toDate;
	
	private String mode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Long getPgTxnId() {
		return pgTxnId;
	}

	public void setPgTxnId(Long pgTxnId) {
		this.pgTxnId = pgTxnId;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(String purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public Double getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public String getTxnCurrency() {
		return txnCurrency;
	}

	public void setTxnCurrency(String txnCurrency) {
		this.txnCurrency = txnCurrency;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Double getSelectedAmount() {
		return selectedAmount;
	}

	public void setSelectedAmount(Double selectedAmount) {
		this.selectedAmount = selectedAmount;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
