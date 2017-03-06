package com.tarang.ewallet.dto;

import java.io.Serializable;

import com.tarang.ewallet.dto.UserDto;

public class MerchantDto extends UserDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	// Account information
	private String password;
	private Long question1;
	//answer for question1
	private String hint1;
	private Long question2;
	//answer for question2
	private String hint2;

	// Business information
	private Long ownerType;
	private String businessLegalname;
	
	private String address1BI;
	private String address2BI;
	private String cityOrTownBI;
	private Long stateorRegionBI;
	private Long countryBI;
	private String postalCodeBI;
	
	private String phoneCountryCode1;
	private String phoneNumber;
	
	private Long businessCategory;
	private Long subCategory;
	private String businessEstablishedMonth;
	private String businessEstablishedYear;
	private String website;
	private Long currency;
	private Long averageTransactionAmount;
	private Long highestMonthlyVolume;
	private Long percentageOfAnnualRevenueFromOnlineSales;
	private Boolean codeCheck;
	private String merchantCode;
	private String successUrl;
	private String failureUrl;
	private Long merchantPayInfoId;
	
	/* is same as business address */
	private Boolean homeAddress; 
	
	private String address1BO;
	private String address2BO;
	private String cityOrTownBO;
	private Long stateOrRegionBO;
	private Long countryBO;
	private String postalCodeBO;
	
	//Customer service information
	private String emailCSI;
	private String code;
	private String phone;
	//For unit test case
	private Long authenticationId;
	
	//For mail service
	private Long languageId;
	
	private Long defaultCurrency;
	
	private Boolean ipAddressCheck;	
	private Boolean emailPatternCheck;	
	private Boolean chargeBackCheck;
	
	private String updateReason;
	
	public MerchantDto(){
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
	
	/**
	 * @return the hint2
	 */
	public String getHint2() {
		return hint2;
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
	 * @param hint2 the hint2 to set
	 */
	public void setHint2(String hint2) {
		this.hint2 = hint2;
	}
	
	/**
	 * @return the ownerType
	 */

	/**
	 * @return the businessLegalname
	 */
	public String getBusinessLegalname() {
		return businessLegalname;
	}
	/**
	 * @return the ownerType
	 */
	public Long getOwnerType() {
		return ownerType;
	}
	/**
	 * @param ownerType the ownerType to set
	 */
	public void setOwnerType(Long ownerType) {
		this.ownerType = ownerType;
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
	 * @param address1BI the address1BI to set
	 */
	public void setAddress1BI(String address1BI) {
		this.address1BI = address1BI;
	}
	/**
	 * @return the address2BI
	 */
	public String getAddress2BI() {
		return address2BI;
	}
	/**
	 * @param address2BI the address2BI to set
	 */
	public void setAddress2BI(String address2BI) {
		this.address2BI = address2BI;
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
	public Long getStateorRegionBI() {
		return stateorRegionBI;
	}
	/**
	 * @param stateorRegionBI the stateorRegionBI to set
	 */
	public void setStateorRegionBI(Long stateorRegionBI) {
		this.stateorRegionBI = stateorRegionBI;
	}
	/**
	 * @return the countryForBusiness
	 */
	public Long getCountryBI() {
		return countryBI;
	}
	/**
	 * @param countryBI the countryBI to set
	 */
	public void setCountryBI(Long countryBI) {
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
	
	
	/**
	 * @return the businessEstablishedMonth
	 */
	public String getBusinessEstablishedMonth() {
		return businessEstablishedMonth;
	}
	/**
	 * @return the businessCategory
	 */
	public Long getBusinessCategory() {
		return businessCategory;
	}
	/**
	 * @param businessCategory the businessCategory to set
	 */
	public void setBusinessCategory(Long businessCategory) {
		this.businessCategory = businessCategory;
	}
	
	
	/**
	 * @return the subCategory
	 */
	public Long getSubCategory() {
		return subCategory;
	}
	/**
	 * @param subCategory the subCategory to set
	 */
	public void setSubCategory(Long subCategory) {
		this.subCategory = subCategory;
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
	 * @return the averageTransactionAmount
	 */
	public Long getAverageTransactionAmount() {
		return averageTransactionAmount;
	}
	/**
	 * @param averageTransactionAmount the averageTransactionAmount to set
	 */
	public void setAverageTransactionAmount(Long averageTransactionAmount) {
		this.averageTransactionAmount = averageTransactionAmount;
	}
	/**
	 * @return the highestMonthlyVolume
	 */
	public Long getHighestMonthlyVolume() {
		return highestMonthlyVolume;
	}
	/**
	 * @param highestMonthlyVolume the highestMonthlyVolume to set
	 */
	public void setHighestMonthlyVolume(Long highestMonthlyVolume) {
		this.highestMonthlyVolume = highestMonthlyVolume;
	}
	/**
	 * @return the percentageOfAnnualRevenueFromOnlineSales
	 */
	public Long getPercentageOfAnnualRevenueFromOnlineSales() {
		return percentageOfAnnualRevenueFromOnlineSales;
	}
	/**
	 * @param percentageOfAnnualRevenueFromOnlineSales the percentageOfAnnualRevenueFromOnlineSales to set
	 */
	public void setPercentageOfAnnualRevenueFromOnlineSales(
			Long percentageOfAnnualRevenueFromOnlineSales) {
		this.percentageOfAnnualRevenueFromOnlineSales = percentageOfAnnualRevenueFromOnlineSales;
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

	public Boolean getCodeCheck() {
		return codeCheck;
	}

	public void setCodeCheck(Boolean codeCheck) {
		this.codeCheck = codeCheck;
	}
	
	/**
	 * @return the address1BO
	 */
	public String getAddress1BO() {
		return address1BO;
	}
	/**
	 * @param address1BO the address1BO to set
	 */
	public void setAddress1BO(String address1BO) {
		this.address1BO = address1BO;
	}
	/**
	 * @return the address2BO
	 */
	public String getAddress2BO() {
		return address2BO;
	}
	/**
	 * @param address2BO the address2BO to set
	 */
	public void setAddress2BO(String address2BO) {
		this.address2BO = address2BO;
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
	public Long getStateOrRegionBO() {
		return stateOrRegionBO;
	}
	/**
	 * @param stateOrRegionBO the stateOrRegionBO to set
	 */
	public void setStateOrRegionBO(Long stateOrRegionBO) {
		this.stateOrRegionBO = stateOrRegionBO;
	}
	/**
	 * @return the countryBO
	 */
	public Long getCountryBO() {
		return countryBO;
	}
	/**
	 * @param countryBO the countryBO to set
	 */
	public void setCountryBO(Long countryBO) {
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
	public Long getAuthenticationId() {
		return authenticationId;
	}
	public void setAuthenticationId(Long authenticationId) {
		this.authenticationId = authenticationId;
	}
	public Long getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	public Long getCurrency() {
		return currency;
	}
	public void setCurrency(Long currency) {
		this.currency = currency;
	}
	public Long getDefaultCurrency() {
		return defaultCurrency;
	}
	public void setDefaultCurrency(Long defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public Boolean getIpAddressCheck() {
		return ipAddressCheck;
	}

	public void setIpAddressCheck(Boolean ipAddressCheck) {
		this.ipAddressCheck = ipAddressCheck;
	}

	public Boolean getEmailPatternCheck() {
		return emailPatternCheck;
	}

	public void setEmailPatternCheck(Boolean emailPatternCheck) {
		this.emailPatternCheck = emailPatternCheck;
	}

	public Boolean getChargeBackCheck() {
		return chargeBackCheck;
	}

	public void setChargeBackCheck(Boolean chargeBackCheck) {
		this.chargeBackCheck = chargeBackCheck;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public Long getMerchantPayInfoId() {
		return merchantPayInfoId;
	}

	public void setMerchantPayInfoId(Long merchantPayInfoId) {
		this.merchantPayInfoId = merchantPayInfoId;
	}

}
