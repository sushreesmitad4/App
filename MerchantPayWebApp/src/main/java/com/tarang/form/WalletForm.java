package com.tarang.form;

public class WalletForm {

	private String bookName;
	
	private Long quantity;
	
	private Long qtyList;
	
	private Long qtyList1;
	
	private Double unitPrice;
	
	private Double total;

	private String bookName1;
	
	private Long quantity1;
	
	private Double unitPrice1;
	
	private Double total1;
	
	private Double alltotal;
	
	private Long payment;
	
	private String dispPayment;
	
	//hidden variable
	
	private String orderId;
	
	private String amount;
	
	private String currency;
	
	private String merchantId;
	
	private String merchantSuccessUrl; 

	private String merchantFailureUrl; 
	
	private String merchantCode; 

	//displaying success based on response
	
	private String responseStatus;
	
	private String successMessage;
	
	private String errorCode;

	private String errorMessage;
	
	private String txnId;

	private String successCode;
	
	private Boolean expCheckOut;
	
	private String addressOne;
	
	private String addressTwo;
	
	private String city;
	
	private String region;
	
	private String country;
	
	private String zipcode;
	
	private String jsessionid;

	public String getMerchantSuccessUrl() {
		return merchantSuccessUrl;
	}

	public void setMerchantSuccessUrl(String merchantSuccessUrl) {
		this.merchantSuccessUrl = merchantSuccessUrl;
	}

	public String getMerchantFailureUrl() {
		return merchantFailureUrl;
	}

	public void setMerchantFailureUrl(String merchantFailureUrl) {
		this.merchantFailureUrl = merchantFailureUrl;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getBookName1() {
		return bookName1;
	}

	public void setBookName1(String bookName1) {
		this.bookName1 = bookName1;
	}

	public Long getQuantity1() {
		return quantity1;
	}

	public void setQuantity1(Long quantity1) {
		this.quantity1 = quantity1;
	}

	public Double getUnitPrice1() {
		return unitPrice1;
	}

	public void setUnitPrice1(Double unitPrice1) {
		this.unitPrice1 = unitPrice1;
	}

	public Double getTotal1() {
		return total1;
	}

	public void setTotal1(Double total1) {
		this.total1 = total1;
	}

	public Double getAlltotal() {
		return alltotal;
	}

	public void setAlltotal(Double alltotal) {
		this.alltotal = alltotal;
	}

	public Long getPayment() {
		return payment;
	}

	public void setPayment(Long payment) {
		this.payment = payment;
	}
	

	public Long getQtyList() {
		return qtyList;
	}

	public void setQtyList(Long qtyList) {
		this.qtyList = qtyList;
	}

	public Long getQtyList1() {
		return qtyList1;
	}

	public void setQtyList1(Long qtyList1) {
		this.qtyList1 = qtyList1;
	}
	
	public String getDispPayment() {
		return dispPayment;
	}

	public void setDispPayment(String dispPayment) {
		this.dispPayment = dispPayment;
	}


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	public Boolean getExpCheckOut() {
		return expCheckOut;
	}

	public void setExpCheckOut(Boolean expCheckOut) {
		this.expCheckOut = expCheckOut;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	public String getJsessionid() {
		return jsessionid;
	}

	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

}
