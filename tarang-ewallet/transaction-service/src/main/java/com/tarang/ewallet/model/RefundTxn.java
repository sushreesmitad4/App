package com.tarang.ewallet.model;

import java.io.Serializable;

public class RefundTxn implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String merchantID;
	private String pgTxnId;
	private Double txnAmount;
	private Double refundAmount;
	private String responseCode;
	private String responseMessage;
	private String responseDecision;
	private Long refundHistoryId;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
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
	 * @return the refundAmount
	 */
	public Double getRefundAmount() {
		return refundAmount;
	}
	/**
	 * @param refundAmount the refundAmount to set
	 */
	public void setRefundAmount(Double refundAmount) {
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
	 * @return the refundHistoryId
	 */
	public Long getRefundHistoryId() {
		return refundHistoryId;
	}
	/**
	 * @param refundHistoryId the refundHistoryId to set
	 */
	public void setRefundHistoryId(Long refundHistoryId) {
		this.refundHistoryId = refundHistoryId;
	}
	

}