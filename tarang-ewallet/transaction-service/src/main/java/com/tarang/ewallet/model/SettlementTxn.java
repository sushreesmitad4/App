package com.tarang.ewallet.model;

import java.io.Serializable;

public class SettlementTxn implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String merchantId;
	private String orderId;
	private String pgTxnId;
	private Double txnAmount;
	private Double settledAmount;
	private String txnDate;
	private String settlementOrderNo;
	private String txnCurrency;
	private String merchantOrderNo;
	private String responseCode;
	private String responseMessage;
	private String responseDecision;
	private Long settlementHistoryId;
	private String settlementTxnId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the historyId
	 */
	public Long getSettlementHistoryId() {
		return settlementHistoryId;
	}
	/**
	 * @param historyId the historyId to set
	 */
	public void setSettlementHistoryId(Long historyId) {
		this.settlementHistoryId = historyId;
	}
	/**
	 * @return the merchantID
	 */
	public String getMerchantID() {
		return merchantId;
	}
	/**
	 * @param merchantID the merchantID to set
	 */
	public void setMerchantID(String merchantID) {
		this.merchantId = merchantID;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the pgTxnId
	 */
	public String getPgTxnId() {
		return pgTxnId;
	}
	/**
	 * @param pgTxnId the pgTxnId to set
	 */
	public void setPgTxnId(String pgTxnId) {
		this.pgTxnId = pgTxnId;
	}
	/**
	 * @return the txnAmount
	 */
	public Double getTxnAmount() {
		return txnAmount;
	}
	/**
	 * @param txnAmount the txnAmount to set
	 */
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}
	/**
	 * @return the settledAmount
	 */
	public Double getSettledAmount() {
		return settledAmount;
	}
	/**
	 * @param settledAmount the settledAmount to set
	 */
	public void setSettledAmount(Double settledAmount) {
		this.settledAmount = settledAmount;
	}
	/**
	 * @return the txnDate
	 */
	public String getTxnDate() {
		return txnDate;
	}
	/**
	 * @param txnDate the txnDate to set
	 */
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	/**
	 * @return the settlementOrderNo
	 */
	public String getSettlementOrderNo() {
		return settlementOrderNo;
	}
	/**
	 * @param settlementOrderNo the settlementOrderNo to set
	 */
	public void setSettlementOrderNo(String settlementOrderNo) {
		this.settlementOrderNo = settlementOrderNo;
	}
	/**
	 * @return the txnCurrency
	 */
	public String getTxnCurrency() {
		return txnCurrency;
	}
	/**
	 * @param txnCurrency the txnCurrency to set
	 */
	public void setTxnCurrency(String txnCurrency) {
		this.txnCurrency = txnCurrency;
	}
	/**
	 * @return the merchantOrderNo
	 */
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	/**
	 * @param merchantOrderNo the merchantOrderNo to set
	 */
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		return responseMessage;
	}
	/**
	 * @param responseMessage the responseMessage to set
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	/**
	 * @return the responseDecision
	 */
	public String getResponseDecision() {
		return responseDecision;
	}
	/**
	 * @param responseDecision the responseDecision to set
	 */
	public void setResponseDecision(String responseDecision) {
		this.responseDecision = responseDecision;
	}
	/**
	 * @return the settlementTxnId
	 */
	public String getSettlementTxnId() {
		return settlementTxnId;
	}
	/**
	 * @param settlementTxnId the settlementTxnId to set
	 */
	public void setSettlementTxnId(String settlementTxnId) {
		this.settlementTxnId = settlementTxnId;
	}
	
}