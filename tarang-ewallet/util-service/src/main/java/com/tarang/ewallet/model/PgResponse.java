package com.tarang.ewallet.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class RefundVO.
 */
public class PgResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String txnType;
	private String paymentMode;
	private String checkSum;
	private String dateandTime;
	private String orderID;
	private String pgTxnID;
	private String amount;
	private String currency;
	private String responseDecision;
	private String authorizationCode;
	private String traceNo;
	private String responseText;
	private String cvvResponseCode;
	private String avsResponseCode;
	 
	private String responseCode;
	private String responseMsg;
	
	//history
	private Long id;
	private Boolean isSuccess;
	private Timestamp dateAndTimewithTimestamp;
	// card account id
	private Long accountId; 
	private String cardNumber; 
	
	// PG Response code
	private String code; 
	// PG Response Message
	private String msg; 
	private Long numberOfAttempts;
	
	     
	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}
	
	/**
	 * @param txnType the txnType to set
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
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	/**
	 * @return the dateandTime
	 */
	public String getDateandTime() {
		return dateandTime;
	}
	
	/**
	 * @param dateandTime the dateandTime to set
	 */
	public void setDateandTime(String dateandTime) {
		this.dateandTime = dateandTime;
	}

	/**
	 * @return the orderID
	 */
	public String getOrderID() {
		return orderID;
	}
	
	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	
	/**
	 * @return the pgTxnID
	 */
	public String getPgTxnID() {
		return pgTxnID;
	}
	
	/**
	 * @param pgTxnID the pgTxnID to set
	 */
	public void setPgTxnID(String pgTxnID) {
		this.pgTxnID = pgTxnID;
	}
	
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	/**
	 * @return the responsedecision
	 */
	public String getResponsedecision() {
		return responseDecision;
	}
	
	/**
	 * @param responsedecision the responsedecision to set
	 */
	public void setResponsedecision(String responsedecision) {
		this.responseDecision = responsedecision;
	}
	
	/**
	 * @return the authorizationCode
	 */
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	
	/**
	 * @param authorizationCode the authorizationCode to set
	 */
	public void setAuthorizationCode(String authorizationcode) {
		this.authorizationCode = authorizationcode;
	}
	
	/**
	 * @return the traceNo
	 */
	public String getTraceNo() {
		return traceNo;
	}
	
	/**
	 * @param traceNo the traceNo to set
	 */
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
	
	/**
	 * @return the responseText
	 */
	public String getResponseText() {
		return responseText;
	}
	
	/**
	 * @param responseText the responseText to set
	 */
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	
	/**
	 * @return the cvvResponseCode
	 */
	public String getcvvResponseCode() {
		return cvvResponseCode;
	}
	
	/**
	 * @param cvvResponseCode the cvvResponseCode to set
	 */
	public void setCvvResponseCode(String cvvResponseCode) {
		this.cvvResponseCode = cvvResponseCode;
	}
	
	/**
	 * @return the avsResponseCode
	 */
	public String getAvsResponseCode() {
		return avsResponseCode;
	}
	
	/**
	 * @param avsResponseCode the avsResponseCode to set
	 */
	public void setAvsResponseCode(String avsResponseCode) {
		this.avsResponseCode = avsResponseCode;
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
	 * @return the responseMsg
	 */
	public String getResponseMsg() {
		return responseMsg;
	}
	
	/**
	 * @param responseMsg the responseMsg to set
	 */
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

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
	 * @return the isSuccess
	 */
	public Boolean getIsSuccess() {
		return isSuccess;
	}

	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	

	/**
	 * @return the dateAndTimewithTimestamp
	 */
	public Timestamp getDateAndTimewithTimestamp() {
		return dateAndTimewithTimestamp;
	}

	/**
	 * @param dateAndTimewithTimestamp the dateAndTimewithTimestamp to set
	 */
	public void setDateAndTimewithTimestamp(Timestamp dateAndTimewithTimestamp) {
		this.dateAndTimewithTimestamp = dateAndTimewithTimestamp;
	}

	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the numberOfAttempts
	 */
	public Long getNumberOfAttempts() {
		return numberOfAttempts;
	}

	/**
	 * @param numberOfAttempts the numberOfAttempts to set
	 */
	public void setNumberOfAttempts(Long numberOfAttempts) {
		this.numberOfAttempts = numberOfAttempts;
	}

	/**
	 * @return the checkSum
	 */
	public String getCheckSum() {
		return checkSum;
	}

	/**
	 * @param checkSum the checkSum to set
	 */
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

}