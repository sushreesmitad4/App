/**
 * 
 */
package com.tarang.ewallet.model;

import java.util.Date;
import java.util.Set;

/**
 * @author vasanthar
 *
 */
public class Dispute {
	
	private Long id;
	
	private Long transactionId;

	private Long type;
	
	private Date creationDate;
	
	private Date updationDate;
	
	private Double requestAmount;
	
	private Long requestCurrency;
	
	private Double approvedAmount;
	
	private Long approvedCurrency;
	
	private Long status;
	
	private Set<DisputeMessage> messages;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdationDate() {
		return updationDate;
	}

	public void setUpdationDate(Date updationDate) {
		this.updationDate = updationDate;
	}

	public Double getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(Double requestAmount) {
		this.requestAmount = requestAmount;
	}

	public Long getRequestCurrency() {
		return requestCurrency;
	}

	public void setRequestCurrency(Long requestCurrency) {
		this.requestCurrency = requestCurrency;
	}

	public Double getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(Double approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public Long getApprovedCurrency() {
		return approvedCurrency;
	}

	public void setApprovedCurrency(Long approvedCurrency) {
		this.approvedCurrency = approvedCurrency;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Set<DisputeMessage> getMessages() {
		return messages;
	}

	public void setMessages(Set<DisputeMessage> messages) {
		this.messages = messages;
	}
	
}
