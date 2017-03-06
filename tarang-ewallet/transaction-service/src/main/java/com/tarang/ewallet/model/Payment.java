package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String orderId;
	
	private Double amount;
	
	private String currency;
	
	private Long customerAuthId;
	
	private Long merchantAuthId;
	
	private Integer status;
	
	private String ipAddress;
	
	private Date orderDate;
	
	private Long txnId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getCustomerAuthId() {
		return customerAuthId;
	}

	public void setCustomerAuthId(Long customerAuthId) {
		this.customerAuthId = customerAuthId;
	}

	public Long getMerchantAuthId() {
		return merchantAuthId;
	}

	public void setMerchantAuthId(Long merchantAuthId) {
		this.merchantAuthId = merchantAuthId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

}
