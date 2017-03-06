package com.tarang.ewallet.model;

import java.io.Serializable;

public class RefundResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// success case
	private String merchantID;
	private String pgTxnId;
	private String txnAmount;
	private String refundAmount;
	private String responseCode;
	private String responseMessage;
	private String responseDecision;
	
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
	 * @return the refundAmount
	 */
	public String getRefundAmount() {
		return refundAmount;
	}
	/**
	 * @param refundAmount the refundAmount to set
	 */
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
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
	
}