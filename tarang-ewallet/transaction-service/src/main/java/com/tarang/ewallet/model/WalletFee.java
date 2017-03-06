/**
 * 
 */
package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  : prasadj
 * @date    : Jan 11, 2013
 * @time    : 2:58:50 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class WalletFee implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long transactionId;
	
	private Double amount;
	
	private Long country;
	
	private Long currency;
	
	/**
	 * type represents sender or receiver
	 */
	private Long type;
	
	private Date payDate;
	
	private Long parentId;

	private Boolean reversal;
	
	public WalletFee(){
	}

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean getReversal() {
		return reversal;
	}

	public void setReversal(Boolean reversal) {
		this.reversal = reversal;
	}
	
	public void populate(WalletFee walletFee){
		walletFee.transactionId = this.transactionId;
		walletFee.amount = this.amount;
		walletFee.country = this.country;
		walletFee.currency = this.currency;
		walletFee.type = this.type;
		walletFee.payDate = this.payDate;
		walletFee.parentId = this.parentId;
		walletFee.reversal = this.reversal;
	}
}
