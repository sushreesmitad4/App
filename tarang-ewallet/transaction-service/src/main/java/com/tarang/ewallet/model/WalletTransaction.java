/**
 * 
 */
package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  : prasadj
 * @date    : Jan 11, 2013
 * @time    : 3:07:30 PM
 * @version : 1.0.0
 * @comments: 
 *
 */

public class WalletTransaction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String ipAddress;
	/**
	 * payee represents receiver auth id
	 */
	private Long payee;

	/**
	 * payer represents sender auth id
	 */
	private Long payer;

	private Date creationDate;
	
	private Double payerAmount;
	
	/**
	 * payeeCurrency represents receiver currency id
	 */
	private Long payeeCurrency;
	
	/**
	 * payerCurrency represents sender currency id
	 */
	private Long payerCurrency;

	/** 
	 * status represents Pending, Cancel, Reject and Success
	 */
	private Long status;
	
	/**
	 * type of transaction represents any one of the following type
	 * 
	 * 1.	P2P
	 * 2.	P2M
	 * 3.	P2NP (Non Register Person)
	 * 4.	M2P
	 * 5.	M2M
	 * 6.	M2NP
	 * 7.	Withdraw money to Card (Customer)
	 * 8.	Withdraw money to Bank (Customer)
	 * 9.	Withdraw money to Card (Merchant)
	 * 10.	Withdraw money to Bank (Merchant)
	 * 11.	Upload money from Card (Customer)
	 * 12.	Upload money from Bank (Customer)
	 * 13.	Upload money from Card (Merchant)
	 * 14.	Upload money from Bank (Merchant)
	 * 15.	Penny drop for Card (Customer)
	 * 16.	Penny drop for Bank (Customer)
	 * 17.	Penny drop for Card (Merchant)
	 * 18.	Penny drop for Bank (Merchant)
	 * 19.	Recover from Card (Customer)
	 * 20.	Recover from Bank (Customer)
	 * 21.	Recover from Card (Merchant)
	 * 22.	Recover from Bank (Merchant)
	 */
	private Long typeOfTransaction;
	
	private Long parentId;
	
	/**
	 * payeeFee represents receiver fee
	 */
	private Double payeeFee;
	
	/**
	 * payeeTax represents receiver tax
	 */
	private Double payeeTax;
	
	/**
	 * payerFee represents sender fee
	 */
	private Double payerFee;
	
	/**
	 * payerTax represents sender tax
	 */
	private Double payerTax;
	
	/**
	 * payeeBalance represents receiver gross balance
	 */
	private Double payeeBalance;
	
	/**
	 * payerBalance represents sender gross balance
	 */
	private Double payerBalance;
	
	/**
	 * payee amount after conversion
	 */
	private Double payeeAmount;
	
	/**
	 * status updated date
	 */
	private Date updatededDate;
	
	/**
	 * conversion rate for currency
	 */
	private Double convertionRate;

	private Long reversalType;
	
	private Long typeOfRequest;
	
	private String simNumber;
	
	private String imeiNumber;
	
	public WalletTransaction(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Long getPayee() {
		return payee;
	}

	public void setPayee(Long payee) {
		this.payee = payee;
	}

	public Long getPayer() {
		return payer;
	}

	public void setPayer(Long payer) {
		this.payer = payer;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Double getPayerAmount() {
		return payerAmount;
	}

	public void setPayerAmount(Double amount) {
		this.payerAmount = amount;
	}

	public Long getPayeeCurrency() {
		return payeeCurrency;
	}

	public void setPayeeCurrency(Long payeeCurrency) {
		this.payeeCurrency = payeeCurrency;
	}
	
	public Long getPayerCurrency() {
		return payerCurrency;
	}

	public void setPayerCurrency(Long payerCurrency) {
		this.payerCurrency = payerCurrency;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getTypeOfTransaction() {
		return typeOfTransaction;
	}

	public void setTypeOfTransaction(Long typeOfTransaction) {
		this.typeOfTransaction = typeOfTransaction;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Double getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Double payeeFee) {
		this.payeeFee = payeeFee;
	}

	public Double getPayeeTax() {
		return payeeTax;
	}

	public void setPayeeTax(Double payeeTax) {
		this.payeeTax = payeeTax;
	}

	public Double getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Double payerFee) {
		this.payerFee = payerFee;
	}

	public Double getPayerTax() {
		return payerTax;
	}

	public void setPayerTax(Double payerTax) {
		this.payerTax = payerTax;
	}

	public Double getPayeeBalance() {
		return payeeBalance;
	}

	public void setPayeeBalance(Double payeeBalance) {
		this.payeeBalance = payeeBalance;
	}

	public Double getPayerBalance() {
		return payerBalance;
	}

	public void setPayerBalance(Double payerBalance) {
		this.payerBalance = payerBalance;
	}

	public Double getConvertionRate() {
		return convertionRate;
	}

	public void setConvertionRate(Double convertionRate) {
		this.convertionRate = convertionRate;
	}
	
	public Double getPayeeAmount() {
		return payeeAmount;
	}

	public void setPayeeAmount(Double payeeAmount) {
		this.payeeAmount = payeeAmount;
	}

	public Date getUpdatededDate() {
		return updatededDate;
	}

	public void setUpdatededDate(Date updatededDate) {
		this.updatededDate = updatededDate;
	}

	public Long getReversalType() {
		return reversalType;
	}

	public void setReversalType(Long reversalType) {
		this.reversalType = reversalType;
	}

	/**
	 * @return the typeOfRequest
	 */
	public Long getTypeOfRequest() {
		return typeOfRequest;
	}

	/**
	 * @param typeOfRequest the channel to set
	 */
	public void setTypeOfRequest(Long typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}

	/**
	 * @return the simNumber
	 */
	public String getSimNumber() {
		return simNumber;
	}

	/**
	 * @param simNumber the simNumber to set
	 */
	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}
    
	public void populate(WalletTransaction temp){

		temp.payer = this.payer;
		temp.payee = this.payee;
		temp.creationDate = this.creationDate;
		temp.payerAmount = this.payerAmount;
		temp.payerCurrency = this.payerCurrency;
		temp.payeeCurrency = this.payeeCurrency;
		temp.status = this.status;
		temp.typeOfTransaction = this.typeOfTransaction;
		temp.convertionRate = this.convertionRate;
		temp.parentId = this.parentId;
		temp.payerFee = this.payerFee;
		temp.payerTax = this.payerTax;
		temp.payeeFee = this.payeeFee;
		temp.payeeTax = this.payeeTax;
		temp.payeeAmount = this.payeeAmount;
		temp.updatededDate = this.updatededDate;
		temp.payerBalance = this.payerBalance;
		temp.payeeBalance = this.payeeBalance;
		temp.reversalType = this.reversalType;
		temp.typeOfRequest = this.typeOfRequest;
		temp.simNumber = this.simNumber;
		temp.imeiNumber = this.imeiNumber;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	
}
