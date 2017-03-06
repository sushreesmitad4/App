package com.tarang.ewallet.model;

import java.io.Serializable;

public class PgRequest implements Serializable {
	
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	private String txnType;              
	private String paymentMode;        
	private String dateAndTime;         
	private String storeID;             
	private String orderID;           
	private String customerID;          
	private String reqType;          
	private String customerDOB;        
	private String socialID;         
	private String drivingLicense;      
	private String mobileNumber;          
	private String emailId;            
	private String addressOne;           
	private String addressTwo;            
	private String city;               
	private String state;               
	private String country;               
	private String zipcode;              
	private String amount;              
	private String currency;            
	private String authMode;             
	private String orderDetails;         
	private String comment;                
	private String userAgent;             
	private String sourceIp;             
	private String isFraudCheck;           
	private String pgTxnId;               
	private String nameOnCard;           
	private String cardNumber;         
	private String cardExpiryMonth;       
	private String cardExpiryYear;        
	private String cardStorageId;       
	private String cardCVV;          
	private String storeCardFlag;         
	private String deleteCardFlag;        
	private String traceNo;             
	private String billingAddressLineOne;
	private String billingAddressLineTwo;
	private String billingAddressCity;    
	private String billingAddressState;  
	private String billingAddressCountry;
	private String billingAddressZIP;    
	private String statementNarrative;    
	private String terminalID;           
	private String threeDSecureEnrolled;
	private String cAVV;              
	private String eCIFlag;            
	private String xIDValue;
	
	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}
	
	/**
	 * @param txnType the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	
	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}
	
	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	/**
	 * @return the dateAndTime
	 */
	public String getDateAndTime() {
		return dateAndTime;
	}
	
	/**
	 * @param dateAndTime the dateAndTime to set
	 */
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	
	/**
	 * @return the storeID
	 */
	public String getStoreID() {
		return storeID;
	}
	
	/**
	 * @param storeID the storeID to set
	 */
	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}
	
	/**
	 * @return the orderID
	 */
	public String getOrderID() {
		return orderID;
	}
	
	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	
	/**
	 * @return the customerID
	 */
	public String getCustomerID() {
		return customerID;
	}
	
	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	/**
	 * @return the reqType
	 */
	public String getReqType() {
		return reqType;
	}
	
	/**
	 * @param reqType the reqType to set
	 */
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	
	/**
	 * @return the customerDOB
	 */
	public String getCustomerDOB() {
		return customerDOB;
	}
	
	/**
	 * @param customerDOB the customerDOB to set
	 */
	public void setCustomerDOB(String customerDOB) {
		this.customerDOB = customerDOB;
	}
	
	/**
	 * @return the socialID
	 */
	public String getSocialID() {
		return socialID;
	}
	
	/**
	 * @param socialID the socialID to set
	 */
	public void setSocialID(String socialID) {
		this.socialID = socialID;
	}
	
	/**
	 * @return the drivingLicense
	 */
	public String getDrivingLicense() {
		return drivingLicense;
	}
	
	/**
	 * @param drivingLicense the drivingLicense to set
	 */
	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	
	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	/**
	 * @return the addressOne
	 */
	public String getAddressOne() {
		return addressOne;
	}
	
	/**
	 * @param addressOne the addressOne to set
	 */
	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}
	
	/**
	 * @return the addressTwo
	 */
	public String getAddressTwo() {
		return addressTwo;
	}
	
	/**
	 * @param addressTwo the addressTwo to set
	 */
	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}
	
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}
	
	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	/**
	 * @return the authMode
	 */
	public String getAuthMode() {
		return authMode;
	}
	
	/**
	 * @param authMode the authMode to set
	 */
	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}
	
	/**
	 * @return the orderDetails
	 */
	public String getOrderDetails() {
		return orderDetails;
	}
	
	/**
	 * @param orderDetails the orderDetails to set
	 */
	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}
	
	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	/**
	 * @return the sourceIp
	 */
	public String getSourceIp() {
		return sourceIp;
	}
	
	/**
	 * @param sourceIp the sourceIp to set
	 */
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	
	/**
	 * @return the isFraudCheck
	 */
	public String getIsFraudCheck() {
		return isFraudCheck;
	}
	
	/**
	 * @param isFraudCheck the isFraudCheck to set
	 */
	public void setIsFraudCheck(String isFraudCheck) {
		this.isFraudCheck = isFraudCheck;
	}
	
	/**
	 * @return the pgTxnId
	 */
	public String getPgTxnId() {
		return pgTxnId;
	}
	
	/**
	 * @param pgTxnId the pgTxnId to set
	 */
	public void setPgTxnId(String pgTxnId) {
		this.pgTxnId = pgTxnId;
	}
	
	/**
	 * @return the nameOnCard
	 */
	public String getNameOnCard() {
		return nameOnCard;
	}
	
	/**
	 * @param nameOnCard the nameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}
	
	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	/**
	 * @return the cardExpiryMonth
	 */
	public String getCardExpiryMonth() {
		return cardExpiryMonth;
	}
	
	/**
	 * @param cardExpiryMonth the cardExpiryMonth to set
	 */
	public void setCardExpiryMonth(String cardExpiryMonth) {
		this.cardExpiryMonth = cardExpiryMonth;
	}
	
	/**
	 * @return the cardExpiryYear
	 */
	public String getCardExpiryYear() {
		return cardExpiryYear;
	}
	
	/**
	 * @param cardExpiryYear the cardExpiryYear to set
	 */
	public void setCardExpiryYear(String cardExpiryYear) {
		this.cardExpiryYear = cardExpiryYear;
	}
	
	/**
	 * @return the cardStorageId
	 */
	public String getCardStorageId() {
		return cardStorageId;
	}
	
	/**
	 * @param cardStorageId the cardStorageId to set
	 */
	public void setCardStorageId(String cardStorageId) {
		this.cardStorageId = cardStorageId;
	}
	
	/**
	 * @return the cardCVV
	 */
	public String getCardCVV() {
		return cardCVV;
	}
	
	/**
	 * @param cardCVV the cardCVV to set
	 */
	public void setCardCVV(String cardCVV) {
		this.cardCVV = cardCVV;
	}
	
	/**
	 * @return the storeCardFlag
	 */
	public String getStoreCardFlag() {
		return storeCardFlag;
	}
	
	/**
	 * @param storeCardFlag the storeCardFlag to set
	 */
	public void setStoreCardFlag(String storeCardFlag) {
		this.storeCardFlag = storeCardFlag;
	}
	
	/**
	 * @return the deleteCardFlag
	 */
	public String getDeleteCardFlag() {
		return deleteCardFlag;
	}
	
	/**
	 * @param deleteCardFlag the deleteCardFlag to set
	 */
	public void setDeleteCardFlag(String deleteCardFlag) {
		this.deleteCardFlag = deleteCardFlag;
	}
	
	/**
	 * @return the traceNo
	 */
	public String getTraceNo() {
		return traceNo;
	}
	
	/**
	 * @param traceNo the traceNo to set
	 */
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
	
	/**
	 * @return the billingAddressLineOne
	 */
	public String getBillingAddressLineOne() {
		return billingAddressLineOne;
	}
	
	/**
	 * @param billingAddressLineOne the billingAddressLineOne to set
	 */
	public void setBillingAddressLineOne(String billingAddressLineOne) {
		this.billingAddressLineOne = billingAddressLineOne;
	}
	
	/**
	 * @return the billingAddressLineTwo
	 */
	public String getBillingAddressLineTwo() {
		return billingAddressLineTwo;
	}
	
	/**
	 * @param billingAddressLineTwo the billingAddressLineTwo to set
	 */
	public void setBillingAddressLineTwo(String billingAddressLineTwo) {
		this.billingAddressLineTwo = billingAddressLineTwo;
	}
	
	/**
	 * @return the billingAddressCity
	 */
	public String getBillingAddressCity() {
		return billingAddressCity;
	}
	
	/**
	 * @param billingAddressCity the billingAddressCity to set
	 */
	public void setBillingAddressCity(String billingAddressCity) {
		this.billingAddressCity = billingAddressCity;
	}
	
	/**
	 * @return the billingAddressState
	 */
	public String getBillingAddressState() {
		return billingAddressState;
	}
	
	/**
	 * @param billingAddressState the billingAddressState to set
	 */
	public void setBillingAddressState(String billingAddressState) {
		this.billingAddressState = billingAddressState;
	}
	
	/**
	 * @return the billingAddressCountry
	 */
	public String getBillingAddressCountry() {
		return billingAddressCountry;
	}
	
	/**
	 * @param billingAddressCountry the billingAddressCountry to set
	 */
	public void setBillingAddressCountry(String billingAddressCountry) {
		this.billingAddressCountry = billingAddressCountry;
	}
	
	/**
	 * @return the billingAddressZIP
	 */
	public String getBillingAddressZIP() {
		return billingAddressZIP;
	}
	
	/**
	 * @param billingAddressZIP the billingAddressZIP to set
	 */
	public void setBillingAddressZIP(String billingAddressZIP) {
		this.billingAddressZIP = billingAddressZIP;
	}
	
	/**
	 * @return the statementNarrative
	 */
	public String getStatementNarrative() {
		return statementNarrative;
	}
	
	/**
	 * @param statementNarrative the statementNarrative to set
	 */
	public void setStatementNarrative(String statementNarrative) {
		this.statementNarrative = statementNarrative;
	}
	
	/**
	 * @return the terminalID
	 */
	public String getTerminalID() {
		return terminalID;
	}
	
	/**
	 * @param terminalID the terminalID to set
	 */
	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}
	
	/**
	 * @return the threeDSecureEnrolled
	 */
	public String getThreeDSecureEnrolled() {
		return threeDSecureEnrolled;
	}
	
	/**
	 * @param threeDSecureEnrolled the threeDSecureEnrolled to set
	 */
	public void setThreeDSecureEnrolled(String threeDSecureEnrolled) {
		this.threeDSecureEnrolled = threeDSecureEnrolled;
	}
	
	/**
	 * @return the cAVV
	 */
	public String getcAVV() {
		return cAVV;
	}
	
	/**
	 * @param cAVV the cAVV to set
	 */
	public void setcAVV(String cAVV) {
		this.cAVV = cAVV;
	}
	
	/**
	 * @return the eCIFlag
	 */
	public String geteCIFlag() {
		return eCIFlag;
	}
	
	/**
	 * @param eCIFlag the eCIFlag to set
	 */
	public void seteCIFlag(String eCIFlag) {
		this.eCIFlag = eCIFlag;
	}
	
	/**
	 * @return the xIDValue
	 */
	public String getxIDValue() {
		return xIDValue;
	}
	
	/**
	 * @param xIDValue the xIDValue to set
	 */
	public void setxIDValue(String xIDValue) {
		this.xIDValue = xIDValue;
	}

}