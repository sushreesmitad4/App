package com.tarang.ewallet.dto;

import java.io.Serializable;
import java.util.Date;

import com.tarang.ewallet.util.DateConvertion;


public class DisputeGridDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long transactionId;
	private String customerEmail;
	private String merchantEmail;
	private Double txnAmount;
	private String txnCurrencyName;
	private Date txnDate;
	private Double disputeAmount;
	private Long disputeStatusId;
	private String disputeStatusName;
	private Date disputeLogDate;
	private Long disputeTypeId;
	private String disputeTypeName;
	private Long disputeId;
	
	
	
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getMerchantEmail() {
		return merchantEmail;
	}
	public void setMerchantEmail(String merchantEmail) {
		this.merchantEmail = merchantEmail;
	}
	public Double getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getTxnCurrencyName() {
		return txnCurrencyName;
	}
	public void setTxnCurrencyName(String txnCurrencyName) {
		this.txnCurrencyName = txnCurrencyName;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnDateStr() {
		return DateConvertion.dateToString(txnDate);
	}
	public Double getDisputeAmount() {
		return disputeAmount;
	}
	public void setDisputeAmount(Double disputeAmount) {
		this.disputeAmount = disputeAmount;
	}
	public Long getDisputeStatusId() {
		return disputeStatusId;
	}
	public void setDisputeStatusId(Long disputeStatusId) {
		this.disputeStatusId = disputeStatusId;
	}
	public String getDisputeStatusName() {
		return disputeStatusName;
	}
	public void setDisputeStatusName(String disputeStatusName) {
		this.disputeStatusName = disputeStatusName;
	}
	
	public Date getDisputeLogDate() {
		return disputeLogDate;
	}
	public void setDisputeLogDate(Date disputeLogDate) {
		this.disputeLogDate = disputeLogDate;
	}
	public String getDisputeLogDateStr() {
		return DateConvertion.dateToString(disputeLogDate);
	}
	public Long getDisputeTypeId() {
		return disputeTypeId;
	}
	public void setDisputeTypeId(Long disputeTypeId) {
		this.disputeTypeId = disputeTypeId;
	}
	public String getDisputeTypeName() {
		return disputeTypeName;
	}
	public void setDisputeTypeName(String disputeTypeName) {
		this.disputeTypeName = disputeTypeName;
	}
	public Long getDisputeId() {
		return disputeId;
	}
	public void setDisputeId(Long disputeId) {
		this.disputeId = disputeId;
	}
	
	

}
