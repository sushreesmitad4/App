package com.tarang.mwallet.rest.services.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kedarnathd
 *
 */
public class RestRequest implements Serializable {

	// Property key for serialVersionUID
	private static final long serialVersionUID = 1107657677269075418L;

	private String msisdnNumber;

	private String simNumber;

	private String imeiNumber;
	
	private String mPin;
	private String newMPin;

	// Property key for email
	private String email;

	// Property key for password
	private String password;

	// Property key for userRole
	private String userType;

	/*Add card Rest Service*/
	private String accountHolderName;
	private Long cardType;
	private String cardNumber;
	private Long expireDateYear;
	private Long expireDateMonth;
	private String cvv;
	private Boolean isSameAsProfileAddress;
	private Boolean jointAccount;
	/*Add card Rest Service*/
	
	/*verify card*/
	private Long accountId;
	private String amount;
	private String code;
	private String countryName;
	
	/*Self Transfer Rest Service */
	private Long fromWallet;
	private String requestedAmount;
	private Long towallet;
	private String actualAmount;
	/*Self Transfer Rest Service */

    /* P2P Transfer */
	private String receiverEmail;
	private Long destinationType;
	private Long requestedCurrency;
	private String message;
	private String deductions;
	private String totalAmountFeeTax;
	private Date transactionDate;
	
	private Long transactionId;
	private String transactionStatusName;
	private String transactionTypeName;
	/* P2P Transfer */
	
	private Long question;
	/* answer for question */
 	private String answer;
 	
 	/* Feedback */
	private Long queryType;	
	private String subject;	
	
	private Long languageId;
	
	/*New Mobile Registration Page*/
	
	/*DD/MM/YYYY*/
	private String dateOfBirth;
	
	private String phoneCode;
	
	private String phoneNo;
	
	private String firstName;
	
	private String lastName;
	private String recieverPhoneNo;
	/*To get prepaid card */ 
	private Long customerNumber;
	
	private String OTP;
	
	private Boolean isNewMPIN;
	
	private String otpModuleName;
	
	private String orderId;
	
	private Long defaultCurrency;
	
	/*Profile*/
	private Long ptitle;
	
	private Long country;

	private String addrOne;
	
	private String addrTwo;
	
	private String city;
	
	private Long state;
	
	private String postalCode;
	
	private String requestFor;
	
	
	public RestRequest() {
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public Long getCardType() {
		return cardType;
	}

	public void setCardType(Long cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Long getExpireDateYear() {
		return expireDateYear;
	}

	public void setExpireDateYear(Long expireDateYear) {
		this.expireDateYear = expireDateYear;
	}

	public Long getExpireDateMonth() {
		return expireDateMonth;
	}

	public void setExpireDateMonth(Long expireDateMonth) {
		this.expireDateMonth = expireDateMonth;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Boolean getIsSameAsProfileAddress() {
		return isSameAsProfileAddress;
	}

	public void setIsSameAsProfileAddress(Boolean isSameAsProfileAddress) {
		this.isSameAsProfileAddress = isSameAsProfileAddress;
	}

	public Boolean getJointAccount() {
		return jointAccount;
	}

	public void setJointAccount(Boolean jointAccount) {
		this.jointAccount = jointAccount;
	}

	public String getMsisdnNumber() {
		return msisdnNumber;
	}

	public void setMsisdnNumber(String msisdnNumber) {
		this.msisdnNumber = msisdnNumber;
	}

	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Long getFromWallet() {
		return fromWallet;
	}

	public void setFromWallet(Long fromWallet) {
		this.fromWallet = fromWallet;
	}

	public String getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(String requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public Long getTowallet() {
		return towallet;
	}

	public void setTowallet(Long towallet) {
		this.towallet = towallet;
	}

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public Long getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(Long destinationType) {
		this.destinationType = destinationType;
	}

	public Long getRequestedCurrency() {
		return requestedCurrency;
	}

	public void setRequestedCurrency(Long requestedCurrency) {
		this.requestedCurrency = requestedCurrency;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDeductions() {
		return deductions;
	}

	public void setDeductions(String deductions) {
		this.deductions = deductions;
	}

	public String getTotalAmountFeeTax() {
		return totalAmountFeeTax;
	}

	public void setTotalAmountFeeTax(String totalAmountFeeTax) {
		this.totalAmountFeeTax = totalAmountFeeTax;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionStatusName() {
		return transactionStatusName;
	}

	public void setTransactionStatusName(String transactionStatusName) {
		this.transactionStatusName = transactionStatusName;
	}

	public String getTransactionTypeName() {
		return transactionTypeName;
	}

	public void setTransactionTypeName(String transactionTypeName) {
		this.transactionTypeName = transactionTypeName;
	}

	public String getmPin() {
		return mPin;
	}

	public void setmPin(String mPin) {
		this.mPin = mPin;
	}

	public String getNewMPin() {
		return newMPin;
	}

	public void setNewMPin(String newMPin) {
		this.newMPin = newMPin;
	}

	public Long getQuestion() {
		return question;
	}

	public void setQuestion(Long question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getQueryType() {
		return queryType;
	}

	public void setQueryType(Long queryType) {
		this.queryType = queryType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRecieverPhoneNo() {
		return recieverPhoneNo;
	}

	public void setRecieverPhoneNo(String recieverPhoneNo) {
		this.recieverPhoneNo = recieverPhoneNo;
	}

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public Boolean getIsNewMPIN() {
		return isNewMPIN;
	}

	public void setIsNewMPIN(Boolean isNewMPIN) {
		this.isNewMPIN = isNewMPIN;
	}

	public String getOtpModuleName() {
		return otpModuleName;
	}

	public void setOtpModuleName(String otpModuleName) {
		this.otpModuleName = otpModuleName;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(Long defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public Long getPtitle() {
		return ptitle;
	}

	public void setPtitle(Long ptitle) {
		this.ptitle = ptitle;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public String getAddrOne() {
		return addrOne;
	}

	public void setAddrOne(String addrOne) {
		this.addrOne = addrOne;
	}

	public String getAddrTwo() {
		return addrTwo;
	}

	public void setAddrTwo(String addrTwo) {
		this.addrTwo = addrTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getRequestFor() {
		return requestFor;
	}

	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}
	
	
    
}
