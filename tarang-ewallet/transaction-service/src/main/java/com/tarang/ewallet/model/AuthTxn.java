package com.tarang.ewallet.model;

import java.io.Serializable;

public class AuthTxn implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String txnType;
	private String paymentMode;
	private String checkSum;
	private String pgTxnId;
	private String responseDecision;
	private String authorizationCode;
	private String traceNo;
	private String respnseText;
	private String cvvResponseCode;
	private String avsResponseCode;
	private Long authHistoryId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType
	 *            the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode
	 *            the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the checkSum
	 */
	public String getCheckSum() {
		return checkSum;
	}

	/**
	 * @param checkSum
	 *            the checkSum to set
	 */
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	/**
	 * @return the pgTxnId
	 */
	public String getPgTxnId() {
		return pgTxnId;
	}

	/**
	 * @param pgTxnId
	 *            the pgTxnId to set
	 */
	public void setPgTxnId(String pgTxnId) {
		this.pgTxnId = pgTxnId;
	}

	/**
	 * @return the responseDecision
	 */
	public String getResponseDecision() {
		return responseDecision;
	}

	/**
	 * @param responseDecision
	 *            the responseDecision to set
	 */
	public void setResponseDecision(String responseDecision) {
		this.responseDecision = responseDecision;
	}

	/**
	 * @return the authorizationCode
	 */
	public String getAuthorizationCode() {
		return authorizationCode;
	}

	/**
	 * @param authorizationCode
	 *            the authorizationCode to set
	 */
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	/**
	 * @return the traceNo
	 */
	public String getTraceNo() {
		return traceNo;
	}

	/**
	 * @param traceNo
	 *            the traceNo to set
	 */
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	/**
	 * @return the respnseText
	 */
	public String getRespnseText() {
		return respnseText;
	}

	/**
	 * @param respnseText
	 *            the respnseText to set
	 */
	public void setRespnseText(String respnseText) {
		this.respnseText = respnseText;
	}

	/**
	 * @return the cvvResponseCode
	 */
	public String getCvvResponseCode() {
		return cvvResponseCode;
	}

	/**
	 * @param cvvResponseCode
	 *            the cvvResponseCode to set
	 */
	public void setCvvResponseCode(String cvvResponseCode) {
		this.cvvResponseCode = cvvResponseCode;
	}

	/**
	 * @return the aVSResponseCode
	 */
	public String getAvsResponseCode() {
		return avsResponseCode;
	}

	/**
	 * @param avsResponseCode
	 *            the avsResponseCode to set
	 */
	public void setAvsResponseCode(String avsResponseCode) {
		this.avsResponseCode = avsResponseCode;
	}

	/**
	 * @return the historyId
	 */
	public Long getAuthHistoryId() {
		return authHistoryId;
	}

	/**
	 * @param historyId
	 *            the historyId to set
	 */
	public void setAuthHistoryId(Long historyId) {
		this.authHistoryId = historyId;
	}

}