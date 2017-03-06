package com.tarang.ewallet.walletui.form;

public class MerchantMgmtView {

	
	// Account information
	private String emailId;
	private String password;
	private String confirmPassword;
	private Long question1;
	//answer for question1
	private String hint1;
	private Long question2;
	//answer for question2
	private String hint2;
	//security code
	private String securityCode;

	// Business information
	private String ownerType;
	private String businessLegalname;
	
	private String address1BI;
	private String address2BI;
	private String cityOrTownBI;
	private String stateorRegionBI;
	private String countryBI;
	private String postalCodeBI;
	
	private String phoneCountryCode1;
	private String phoneNumber;
	
	private String businessCategory;
	private String subCategory;
	private String businessEstablishedMonth;
	private String businessEstablishedYear;
	private String website;
	private String currency;
	private String averageTransactionAmount;
	private String highestMonthlyVolume;
	private String percentageOfAnnualRevenueFromOnlineSales;
	
	//Business Owner Information
	private String firstName;
	private String middleName;
	private String lastName;
	
	// is same as business address
    private Boolean homeAddress; 
	
	private String address1BO;
	private String address2BO;
	private String cityOrTownBO;
	private String stateOrRegionBO;
	private String countryBO;
	private String postalCodeBO;
	
	//Customer service information
	private String emailCSI;
	private String code;
	private String phone;
	
	// common variables
	private String mode;
	
	//for authentication
	private Long id;
	private Boolean active;
	private Boolean deleted;
	private String  status;
	private Boolean blocked;
	private String creationDate;
	
	private Boolean codeCheck;
	private String merchantCode;
	private String successUrl;
	private String failureUrl;

	
	
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	/**
	 * @return the question1
	 */
	public Long getQuestion1() {
		return question1;
	}
	/**
	 * @param question1 the question1 to set
	 */
	public void setQuestion1(Long question1) {
		this.question1 = question1;
	}
	/**
	 * @return the hint1
	 */
	public String getHint1() {
		return hint1;
	}
	/**
	 * @param hint1 the hint1 to set
	 */
	public void setHint1(String hint1) {
		this.hint1 = hint1;
	}
	/**
	 * @return the question2
	 */
	public Long getQuestion2() {
		return question2;
	}
	/**
	 * @param question2 the question2 to set
	 */
	public void setQuestion2(Long question2) {
		this.question2 = question2;
	}
	/**
	 * @return the hint2
	 */
	public String getHint2() {
		return hint2;
	}
	/**
	 * @param hint2 the hint2 to set
	 */
	public void setHint2(String hint2) {
		this.hint2 = hint2;
	}
	/**
	 * @return the securityCode
	 */
	public String getSecurityCode() {
		return securityCode;
	}
	/**
	 * @param securityCode the securityCode to set
	 */
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	/**
	 * @return the ownerType
	 */
	public String getOwnerType() {
		return ownerType;
	}
	/**
	 * @param ownerType the ownerType to set
	 */
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	/**
	 * @return the businessLegalname
	 */
	public String getBusinessLegalname() {
		return businessLegalname;
	}
	/**
	 * @param businessLegalname the businessLegalname to set
	 */
	public void setBusinessLegalname(String businessLegalname) {
		this.businessLegalname = businessLegalname;
	}
	/**
	 * @return the address1BI
	 */
	public String getAddress1BI() {
		return address1BI;
	}
	/**
	 * @param address1bi the address1BI to set
	 */
	public void setAddress1BI(String address1bi) {
		address1BI = address1bi;
	}
	/**
	 * @return the address2BI
	 */
	public String getAddress2BI() {
		return address2BI;
	}
	/**
	 * @param address2bi the address2BI to set
	 */
	public void setAddress2BI(String address2bi) {
		address2BI = address2bi;
	}
	/**
	 * @return the cityOrTownBI
	 */
	public String getCityOrTownBI() {
		return cityOrTownBI;
	}
	/**
	 * @param cityOrTownBI the cityOrTownBI to set
	 */
	public void setCityOrTownBI(String cityOrTownBI) {
		this.cityOrTownBI = cityOrTownBI;
	}
	/**
	 * @return the stateorRegionBI
	 */
	public String getStateorRegionBI() {
		return stateorRegionBI;
	}
	/**
	 * @param stateorRegionBI the stateorRegionBI to set
	 */
	public void setStateorRegionBI(String stateorRegionBI) {
		this.stateorRegionBI = stateorRegionBI;
	}
	/**
	 * @return the countryBI
	 */
	public String getCountryBI() {
		return countryBI;
	}
	/**
	 * @param countryBI the countryBI to set
	 */
	public void setCountryBI(String countryBI) {
		this.countryBI = countryBI;
	}
	/**
	 * @return the postalCodeBI
	 */
	public String getPostalCodeBI() {
		return postalCodeBI;
	}
	/**
	 * @param postalCodeBI the postalCodeBI to set
	 */
	public void setPostalCodeBI(String postalCodeBI) {
		this.postalCodeBI = postalCodeBI;
	}
	/**
	 * @return the phoneCountryCode1
	 */
	public String getPhoneCountryCode1() {
		return phoneCountryCode1;
	}
	/**
	 * @param phoneCountryCode1 the phoneCountryCode1 to set
	 */
	public void setPhoneCountryCode1(String phoneCountryCode1) {
		this.phoneCountryCode1 = phoneCountryCode1;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the businessCategory
	 */
	public String getBusinessCategory() {
		return businessCategory;
	}
	/**
	 * @param businessCategory the businessCategory to set
	 */
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}
	/**
	 * @return the subcategory
	 */
	public String getSubCategory() {
		return subCategory;
	}
	/**
	 * @param subcategory the subcategory to set
	 */
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	/**
	 * @return the businessEstablishedMonth
	 */
	public String getBusinessEstablishedMonth() {
		return businessEstablishedMonth;
	}
	/**
	 * @param businessEstablishedMonth the businessEstablishedMonth to set
	 */
	public void setBusinessEstablishedMonth(String businessEstablishedMonth) {
		this.businessEstablishedMonth = businessEstablishedMonth;
	}
	/**
	 * @return the businessEstablishedYear
	 */
	public String getBusinessEstablishedYear() {
		return businessEstablishedYear;
	}
	/**
	 * @param businessEstablishedYear the businessEstablishedYear to set
	 */
	public void setBusinessEstablishedYear(String businessEstablishedYear) {
		this.businessEstablishedYear = businessEstablishedYear;
	}
	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
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
	 * @return the averageTransactionAmount
	 */
	public String getAverageTransactionAmount() {
		return averageTransactionAmount;
	}
	/**
	 * @param averageTransactionAmount the averageTransactionAmount to set
	 */
	public void setAverageTransactionAmount(String averageTransactionAmount) {
		this.averageTransactionAmount = averageTransactionAmount;
	}
	/**
	 * @return the highestMonthlyVolume
	 */
	public String getHighestMonthlyVolume() {
		return highestMonthlyVolume;
	}
	/**
	 * @param highestMonthlyVolume the highestMonthlyVolume to set
	 */
	public void setHighestMonthlyVolume(String highestMonthlyVolume) {
		this.highestMonthlyVolume = highestMonthlyVolume;
	}
	/**
	 * @return the percentageOfAnnualRevenueFromOnlineSales
	 */
	public String getPercentageOfAnnualRevenueFromOnlineSales() {
		return percentageOfAnnualRevenueFromOnlineSales;
	}
	/**
	 * @param percentageOfAnnualRevenueFromOnlineSales the percentageOfAnnualRevenueFromOnlineSales to set
	 */
	public void setPercentageOfAnnualRevenueFromOnlineSales(
			String percentageOfAnnualRevenueFromOnlineSales) {
		this.percentageOfAnnualRevenueFromOnlineSales = percentageOfAnnualRevenueFromOnlineSales;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the address1BO
	 */
	public String getAddress1BO() {
		return address1BO;
	}
	/**
	 * @param address1bo the address1BO to set
	 */
	public void setAddress1BO(String address1bo) {
		address1BO = address1bo;
	}
	/**
	 * @return the address2BO
	 */
	public String getAddress2BO() {
		return address2BO;
	}
	/**
	 * @param address2bo the address2BO to set
	 */
	public void setAddress2BO(String address2bo) {
		address2BO = address2bo;
	}
	/**
	 * @return the cityOrTownBO
	 */
	public String getCityOrTownBO() {
		return cityOrTownBO;
	}
	/**
	 * @param cityOrTownBO the cityOrTownBO to set
	 */
	public void setCityOrTownBO(String cityOrTownBO) {
		this.cityOrTownBO = cityOrTownBO;
	}
	/**
	 * @return the stateOrRegionBO
	 */
	public String getStateOrRegionBO() {
		return stateOrRegionBO;
	}
	/**
	 * @param stateOrRegionBO the stateOrRegionBO to set
	 */
	public void setStateOrRegionBO(String stateOrRegionBO) {
		this.stateOrRegionBO = stateOrRegionBO;
	}
	/**
	 * @return the countryBO
	 */
	public String getCountryBO() {
		return countryBO;
	}
	/**
	 * @param countryBO the countryBO to set
	 */
	public void setCountryBO(String countryBO) {
		this.countryBO = countryBO;
	}
	/**
	 * @return the postalCodeBO
	 */
	public String getPostalCodeBO() {
		return postalCodeBO;
	}
	/**
	 * @param postalCodeBO the postalCodeBO to set
	 */
	public void setPostalCodeBO(String postalCodeBO) {
		this.postalCodeBO = postalCodeBO;
	}
	/**
	 * @return the emailCSI
	 */
	public String getEmailCSI() {
		return emailCSI;
	}
	/**
	 * @param emailCSI the emailCSI to set
	 */
	public void setEmailCSI(String emailCSI) {
		this.emailCSI = emailCSI;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the blocked
	 */
	public Boolean getBlocked() {
		return blocked;
	}
	/**
	 * @param blocked the blocked to set
	 */
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	
	/**
	 * @return the creationDate
	 */
	public String getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the homeAddress
	 */
	public Boolean getHomeAddress() {
		return homeAddress;
	}
	/**
	 * @param homeAddress the homeAddress to set
	 */
	public void setHomeAddress(Boolean homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public Boolean getCodeCheck() {
		return codeCheck;
	}
	
	public void setCodeCheck(Boolean codeCheck) {
		this.codeCheck = codeCheck;
	}
	
	public String getMerchantCode() {
		return merchantCode;
	}
	
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	public String getSuccessUrl() {
		return successUrl;
	}
	
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	
	public String getFailureUrl() {
		return failureUrl;
	}
	
	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

}
