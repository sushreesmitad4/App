/**
 * 
 */
package com.tarang.ewallet.walletui.util;

/**
 * @author vasanthar
 *
 */
public class DisputeResponseMessage extends ResponseMessage{
	
	private String transactionIdErrorMessage;
	
	private String disputeTypeErrorMessage;
	
	private String requestTypeErrorMessage;
	
	private String requestAmountErrorMessage;
	
	private String requestCurrencyErrorMessage;
	
	private String messageErrorMessage;
	
	private String transactionAmountErrorMessage;
	
	private String disputeAlreadyRaisedErrorMessage;
	
	private String exceptionErrorMessage;

	public String getTransactionIdErrorMessage() {
		return transactionIdErrorMessage;
	}

	public void setTransactionIdErrorMessage(String transactionIdErrorMessage) {
		this.transactionIdErrorMessage = transactionIdErrorMessage;
	}

	public String getDisputeTypeErrorMessage() {
		return disputeTypeErrorMessage;
	}

	public void setDisputeTypeErrorMessage(String disputeTypeErrorMessage) {
		this.disputeTypeErrorMessage = disputeTypeErrorMessage;
	}

	public String getRequestTypeErrorMessage() {
		return requestTypeErrorMessage;
	}

	public void setRequestTypeErrorMessage(String requestTypeErrorMessage) {
		this.requestTypeErrorMessage = requestTypeErrorMessage;
	}

	public String getRequestAmountErrorMessage() {
		return requestAmountErrorMessage;
	}

	public void setRequestAmountErrorMessage(String requestAmountErrorMessage) {
		this.requestAmountErrorMessage = requestAmountErrorMessage;
	}

	public String getRequestCurrencyErrorMessage() {
		return requestCurrencyErrorMessage;
	}

	public void setRequestCurrencyErrorMessage(String requestCurrencyErrorMessage) {
		this.requestCurrencyErrorMessage = requestCurrencyErrorMessage;
	}

	public String getMessageErrorMessage() {
		return messageErrorMessage;
	}

	public void setMessageErrorMessage(String messageErrorMessage) {
		this.messageErrorMessage = messageErrorMessage;
	}

	public String getTransactionAmountErrorMessage() {
		return transactionAmountErrorMessage;
	}

	public void setTransactionAmountErrorMessage(
			String transactionAmountErrorMessage) {
		this.transactionAmountErrorMessage = transactionAmountErrorMessage;
	}

	public String getDisputeAlreadyRaisedErrorMessage() {
		return disputeAlreadyRaisedErrorMessage;
	}

	public void setDisputeAlreadyRaisedErrorMessage(
			String disputeAlreadyRaisedErrorMessage) {
		this.disputeAlreadyRaisedErrorMessage = disputeAlreadyRaisedErrorMessage;
	}

	public String getExceptionErrorMessage() {
		return exceptionErrorMessage;
	}

	public void setExceptionErrorMessage(String exceptionErrorMessage) {
		this.exceptionErrorMessage = exceptionErrorMessage;
	}
	
}
