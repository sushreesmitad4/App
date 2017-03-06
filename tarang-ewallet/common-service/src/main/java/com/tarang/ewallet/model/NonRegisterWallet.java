package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;
/**
 * @author  : kedarnathd
 * @date    : Jan 19, 2013
 * @time    : 3:07:30 PM
 * @version : 1.0.0
 * @comments: NonRegisterWallet to hold non register person transaction details
 *
 */
public class NonRegisterWallet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String email;
	
	private Long currency;
	
	private Date creationDate;
	
	private Double amount;
	
	private Long txnId;
	
	private Long register;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public Long getRegister() {
		return register;
	}

	public void setRegister(Long register) {
		this.register = register;
	}
	
	
}
