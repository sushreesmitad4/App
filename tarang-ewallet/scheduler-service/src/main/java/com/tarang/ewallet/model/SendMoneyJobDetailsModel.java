/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Jun 3, 2013
 * @time    : 5:11:02 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class SendMoneyJobDetailsModel extends SendMoneyJobSummaryModel {

	private static final long serialVersionUID = 1L;
	
	private String payeeEmailId;
	
	private Double amount;
	
	private String currency;
	
	private String message;
	
	private Long frequency;
	
	private Long totalOccurences;
	
	private Long completedOccurrences;
	
	private Long successfulOccurrences;
	
	private Long failureOccurrences;
	
	private Long remainingOccurrences;
	
	private String failureMessage;
	
	public SendMoneyJobDetailsModel(){
	}
	
	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public String getPayeeEmailId() {
		return payeeEmailId;
	}

	public void setPayeeEmailId(String payeeEmailId) {
		this.payeeEmailId = payeeEmailId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}

	public Long getTotalOccurences() {
		return totalOccurences;
	}

	public void setTotalOccurences(Long totalOccurences) {
		this.totalOccurences = totalOccurences;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getCompletedOccurrences() {
		return completedOccurrences;
	}

	public void setCompletedOccurrences(Long completedOccurrences) {
		this.completedOccurrences = completedOccurrences;
	}

	public Long getSuccessfulOccurrences() {
		return successfulOccurrences;
	}

	public void setSuccessfulOccurrences(Long successfulOccurrences) {
		this.successfulOccurrences = successfulOccurrences;
	}

	public Long getFailureOccurrences() {
		return failureOccurrences;
	}

	public void setFailureOccurrences(Long failureOccurrences) {
		this.failureOccurrences = failureOccurrences;
	}

	public Long getRemainingOccurrences() {
		return remainingOccurrences;
	}

	public void setRemainingOccurrences(Long remainingOccurrences) {
		this.remainingOccurrences = remainingOccurrences;
	}
	
}
