package com.tarang.ewallet.walletui.form;

import java.util.List;

import com.tarang.ewallet.dto.DisputeMessageDto;



/**
 * @author  : kedarnathd
 * @date    : Feb 13, 2013
 * @time    : 1:16:20 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class DisputeForm {
	
	
	private String disputeType;
	
	private Long distype;
	
	//To hold dispute DB status value
	private Long status;
	
	//To hold dispute Form status value
	private Long formStatus;
	
	private String disputeStatus;
	
	private Long transactionId;
	
    private Long disputeId;

	private String transactionDate;
	
	private String transactionCurrency;
	
	private String transactionAmount;
	
	private String disputeLogDate;
	
	private String disputeUpdationDate;
	
	private String requestCurrency;
	
	private String requestAmount;
	
	private String approvedCurrency;
	
	private String approvedAmount;
	
	private String message;
	
	private Long requestedCurrencyId;
	
	private String dispRequestedCurrency;
	
	//For update page
	private String payerEmailId;
	
	private String payeeEmailId;
	
	private String payerAmount;
	
	private String payerCurrency;
	
	private Long userType;
	
	private List<DisputeMessageDto> dtoMessages;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDisputeLogDate() {
		return disputeLogDate;
	}

	public void setDisputeLogDate(String disputeLogDate) {
		this.disputeLogDate = disputeLogDate;
	}

	public String getDisputeUpdationDate() {
		return disputeUpdationDate;
	}

	public void setDisputeUpdationDate(String disputeUpdationDate) {
		this.disputeUpdationDate = disputeUpdationDate;
	}

	public String getRequestCurrency() {
		return requestCurrency;
	}

	public void setRequestCurrency(String requestCurrency) {
		this.requestCurrency = requestCurrency;
	}

	public String getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}

	public String getApprovedCurrency() {
		return approvedCurrency;
	}

	public void setApprovedCurrency(String approvedCurrency) {
		this.approvedCurrency = approvedCurrency;
	}

	public String getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(String approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public String getDisputeStatus() {
		return disputeStatus;
	}

	public void setDisputeStatus(String disputeStatus) {
		this.disputeStatus = disputeStatus;
	}

	public Long getDisputeId() {
		return disputeId;
	}

	public void setDisputeId(Long disputeId) {
		this.disputeId = disputeId;
	}

	public Long getDistype() {
		return distype;
	}

	public void setDistype(Long distype) {
		this.distype = distype;
	}

	public String getDisputeType() {
		return disputeType;
	}

	public void setDisputeType(String disputeType) {
		this.disputeType = disputeType;
	}

	public String getPayerEmailId() {
		return payerEmailId;
	}

	public void setPayerEmailId(String payerEmailId) {
		this.payerEmailId = payerEmailId;
	}

	public String getPayerAmount() {
		return payerAmount;
	}

	public void setPayerAmount(String payerAmount) {
		this.payerAmount = payerAmount;
	}

	public String getPayerCurrency() {
		return payerCurrency;
	}

	public void setPayerCurrency(String payerCurrency) {
		this.payerCurrency = payerCurrency;
	}

	public Long getRequestedCurrencyId() {
		return requestedCurrencyId;
	}

	public void setRequestedCurrencyId(Long requestedCurrencyId) {
		this.requestedCurrencyId = requestedCurrencyId;
	}
	
	public List<DisputeMessageDto> getDtoMessages() {
		return dtoMessages;
	}

	public void setDtoMessages(List<DisputeMessageDto> dtoMessages) {
		this.dtoMessages = dtoMessages;
	}

	public Long getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(Long formStatus) {
		this.formStatus = formStatus;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getPayeeEmailId() {
		return payeeEmailId;
	}

	public void setPayeeEmailId(String payeeEmailId) {
		this.payeeEmailId = payeeEmailId;
	}
	
	public String getDispRequestedCurrency() {
		return dispRequestedCurrency;
	}

	public void setDispRequestedCurrency(String dispRequestedCurrency) {
		this.dispRequestedCurrency = dispRequestedCurrency;
	}
}
