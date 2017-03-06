package com.tarang.ewallet.dto;


public class PaymentDto {
	
	private String orderId;
	private Double amount;
	private String currency;
	private String merchantLoginId;
	private Long merchantAuthId;
	private Long customerAuthId;
	private String merchantSuccessUrl;
	private String merchantFailureUrl;
	private String merchantCode;
	
	private Long currencyId;
	private Long txnId;
	private Integer status;
	private String ipAddress;
	
	private String successCode;
	private String successMessage;
	private String errorCode;
	private String errorMessage;
	
	private Boolean expCheckOut;

	private String addressOne;
	
	private String addressTwo;
	
	private String city;
	
	private String region;
	
	private String country;
	
	private String zipcode;
	
	private Long authenticationId;
	
	private String merchantCodeFromDB;
	
	private Long typeOfRequest;

	public PaymentDto() {
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
	
	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getMerchantLoginId() {
		return merchantLoginId;
	}

	public void setMerchantLoginId(String merchantLoginId) {
		this.merchantLoginId = merchantLoginId;
	}

	public Long getMerchantAuthId() {
		return merchantAuthId;
	}
	
	public void setMerchantAuthId(Long merchantAuthId) {
		this.merchantAuthId = merchantAuthId;
	}
	
	public Long getCustomerAuthId() {
		return customerAuthId;
	}
	
	public void setCustomerAuthId(Long customerAuthId) {
		this.customerAuthId = customerAuthId;
	}
	
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

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
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
	
	public Long getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(Long authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getMerchantCodeFromDB() {
		return merchantCodeFromDB;
	}

	public void setMerchantCodeFromDB(String merchantCodeFromDB) {
		this.merchantCodeFromDB = merchantCodeFromDB;
	}

	public Long getTypeOfRequest() {
		return typeOfRequest;
	}

	public void setTypeOfRequest(Long typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}
	
}