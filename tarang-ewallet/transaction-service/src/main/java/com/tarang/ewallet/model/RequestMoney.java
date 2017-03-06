package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vasanthar
 *
 */
public class RequestMoney implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long requesterId;

	private Long currencyId;
	
	private Double amount;
	
	private Date requestDate;
	
	private String requesterMsg;
	
	private Long status;
	
	private Long responserId;	
	
	private String responserMsg;
	
	private Date responseDate;

	private Long attempts;
	
	private Long transactionId;
	
	private Long countryId;
	
	public RequestMoney() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(Long requesterId) {
		this.requesterId = requesterId;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRequesterMsg() {
		return requesterMsg;
	}

	public void setRequesterMsg(String requesterMsg) {
		this.requesterMsg = requesterMsg;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getResponserId() {
		return responserId;
	}

	public void setResponserId(Long responserId) {
		this.responserId = responserId;
	}

	public String getResponserMsg() {
		return responserMsg;
	}

	public void setResponserMsg(String responserMsg) {
		this.responserMsg = responserMsg;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public Long getAttempts() {
		return attempts;
	}

	public void setAttempts(Long attempts) {
		this.attempts = attempts;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	
}
