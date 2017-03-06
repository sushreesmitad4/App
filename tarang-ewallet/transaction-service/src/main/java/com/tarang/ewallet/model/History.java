package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class RefundVO.
 */
public class History implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String txnType;
	private String reqType;
	private String authMode;
	private String tranceNumber;
	private Boolean isSuccess;
	private Date dateAndTime;
	private String orderId;
	private Long accountId;
	private String cardNumber;
	private String code;
	private String msg;
	private Double amount;
	private String currency;
	private Long numberOfAttempts;
	private String userAgent;
	private String sourceIp;
	private String nameOnCard;
	
	private String errorCode;
	private String errorMsg;
	
	private AuthTxn authTxn;
	private SettlementTxn settlementTxn;
	private RefundTxn refundTxn;
	private CancelTxn cancelTxn;
	
	//Recancelation transaction
	private Date reconcileDate;
	private Boolean reconciledStatus;
	
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
	
	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getAuthMode() {
		return authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}

	public String getTranceNumber() {
		return tranceNumber;
	}

	public void setTranceNumber(String tranceNumber) {
		this.tranceNumber = tranceNumber;
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
	 * @return the dateAndTime
	 */
	public Date getDateAndTime() {
		return dateAndTime;
	}
	
	/**
	 * @param dateAndTime the dateAndTime to set
	 */
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
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
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
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
	
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	
	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public AuthTxn getAuthTxn() {
		return authTxn;
	}

	public void setAuthTxn(AuthTxn authTxn) {
		this.authTxn = authTxn;
	}

	public SettlementTxn getSettlementTxn() {
		return settlementTxn;
	}

	public void setSettlementTxn(SettlementTxn settlementTxn) {
		this.settlementTxn = settlementTxn;
	}

	/**
	 * @return the refundTxn
	 */
	public RefundTxn getRefundTxn() {
		return refundTxn;
	}

	/**
	 * @param refundTxn the refundTxn to set
	 */
	public void setRefundTxn(RefundTxn refundTxn) {
		this.refundTxn = refundTxn;
	}

	/**
	 * @return the cancelTxn
	 */
	public CancelTxn getCancelTxn() {
		return cancelTxn;
	}

	/**
	 * @param cancelTxn the cancelTxn to set
	 */
	public void setCancelTxn(CancelTxn cancelTxn) {
		this.cancelTxn = cancelTxn;
	}

	public Date getReconcileDate() {
		return reconcileDate;
	}

	public void setReconcileDate(Date reconcileDate) {
		this.reconcileDate = reconcileDate;
	}

	public Boolean getReconciledStatus() {
		return reconciledStatus;
	}

	public void setReconciledStatus(Boolean reconciledStatus) {
		this.reconciledStatus = reconciledStatus;
	}
}