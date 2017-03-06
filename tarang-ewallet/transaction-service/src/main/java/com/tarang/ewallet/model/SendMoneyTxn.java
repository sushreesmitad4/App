/**
 * 
 */
package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kedarnathd
 *
 */
public class SendMoneyTxn implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long sendMoneyId;
	
	private Long transactionId;
	
	private Long transactionStatus;
	
	private String failureMessage;
	
	private Integer occurences;
	
	private Integer repeatJobs;
	
	private Date triggerDate;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSendMoneyId() {
		return sendMoneyId;
	}

	public void setSendMoneyId(Long sendMoneyId) {
		this.sendMoneyId = sendMoneyId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(Long transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public Integer getOccurences() {
		return occurences;
	}

	public void setOccurences(Integer occurences) {
		this.occurences = occurences;
	}

	public Integer getRepeatJobs() {
		return repeatJobs;
	}

	public void setRepeatJobs(Integer repeat) {
		this.repeatJobs = repeat;
	}

	public Date getTriggerDate() {
		return triggerDate;
	}

	public void setTriggerDate(Date triggerDate) {
		this.triggerDate = triggerDate;
	}
	
	public void incrementRepeat(){
		if(null == repeatJobs){
			repeatJobs = 1;
		}else {
			this.repeatJobs++;
		}
	}
}
