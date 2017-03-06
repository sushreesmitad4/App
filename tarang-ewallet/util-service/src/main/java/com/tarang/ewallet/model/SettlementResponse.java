package com.tarang.ewallet.model;

import java.io.Serializable;

public class SettlementResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//success case
	private String merchantID;
	private String orderId;
	private String pgTxnId;
	private String txnAmount;
	private String settledAmount;
	private String txnDate;
	private String settlementOrderNo;
	private String txnCurrency;
	private String merchantOrderNo;
	private String responseCode;
	private String responseMessage;
	private String responseDecision;
	private String settlementTxnId;
	
	// failure case
	private String errorCode;
	private String errorMsg;
	/**
	 * @return the merchantID
	 */
	public String getMerchantID() {
		return merchantID;
	}
	/**
	 * @param merchantID the merchantID to set
	 */
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
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
	public String getTxnAmount() {
		return txnAmount;
	}
	/**
	 * @param txnAmount the txnAmount to set
	 */
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	/**
	 * @return the settledAmount
	 */
	public String getSettledAmount() {
		return settledAmount;
	}
	/**
	 * @param settledAmount the settledAmount to set
	 */
	public void setSettledAmount(String settledAmount) {
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
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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