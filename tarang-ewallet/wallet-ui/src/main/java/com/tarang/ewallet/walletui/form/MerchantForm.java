package com.tarang.ewallet.walletui.form;
import java.util.Date;

import com.tarang.ewallet.dto.MerchantDto;


public class MerchantForm{
	
	// Account information
	private String emailId;
	private String confirmEmailId;
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
	private String averageTransactionAmount;
	private String highestMonthlyVolume;
	private Long percentageOfAnnualRevenueFromOnlineSales;
	private Boolean codeCheck;
	private String merchantCode;
	private String successUrl;
	private String failureUrl;
	
	//Business Owner Information
	private String firstName;
	private String middleName;
	private String lastName;
	
	// is same as business address
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
	
	// comman variables
	private String mode;
	/*This used to display email note error message*/
	private String emailNote;
	
	//for authentication
	private Long id;
	private Boolean active;
	private Boolean deleted;
	private Long status;
	private Boolean blocked;
	private Date creationDate;
	private Boolean terms;
	private String creationDateAsMMDDYYY;
	//for hidden field
	private Long status2;
	private Boolean ipAddressCheck;
	private Boolean emailPatternCheck;
	private Boolean chargeBackCheck;
	
	//Phone validations
	private String existPersonPhoneNo;
	private String existServicePhoneNo;
	
	private String updateReason;
	private Boolean oldActive;
	private Boolean oldDeleted;
	private Long oldStatus;
	/*Keep track of old mail id*/
	private String oldEmailId;
	
	private String countryBIName ;
	private String ownerTypeName;
	private String stateorRegionBIName;
	private String businessCategoryName;
	private String subCategoryName;
	private String currencyName;
	private String percentageOfAnnualRevenueFromOnlineSalesName;
	
	public void setMerchant(MerchantDto merchantDto) {
		if (merchantDto != null) {

			emailId = merchantDto.getEmailId();
			password = merchantDto.getPassword();
			question1 = merchantDto.getQuestion1();
			hint1 = merchantDto.getHint1();
			question2 = merchantDto.getQuestion2();
			hint2 = merchantDto.getHint2();
			ownerType = merchantDto.getOwnerType();
			businessLegalname = merchantDto.getBusinessLegalname();
			address1BI = merchantDto.getAddress1BI();
			address2BI = merchantDto.getAddress2BI();
			cityOrTownBI = merchantDto.getCityOrTownBI();
			stateorRegionBI = merchantDto.getStateorRegionBI();
			countryBI = merchantDto.getCountryBI();
			postalCodeBI = merchantDto.getPostalCodeBI();
			phoneCountryCode1 = merchantDto.getPhoneCountryCode1();
			phoneNumber = merchantDto.getPhoneNumber();
			businessCategory = merchantDto.getBusinessCategory();
			subCategory = merchantDto.getSubCategory();
			businessEstablishedMonth = merchantDto.getBusinessEstablishedMonth();
			businessEstablishedYear = merchantDto.getBusinessEstablishedYear();
			website = merchantDto.getWebsite();
			currency = merchantDto.getCurrency();
			averageTransactionAmount = merchantDto.getAverageTransactionAmount().toString();
			highestMonthlyVolume = merchantDto.getHighestMonthlyVolume().toString();
			percentageOfAnnualRevenueFromOnlineSales = merchantDto.getPercentageOfAnnualRevenueFromOnlineSales();
			firstName = merchantDto.getFirstName();
			lastName = merchantDto.getLastName();
			homeAddress = getHomeAddress();
			emailCSI = merchantDto.getEmailCSI();
			code = merchantDto.getCode();
			phone = merchantDto.getPhone();
		}
	}

	public MerchantDto getMerchantDto() {
		MerchantDto merchantDto = new MerchantDto();
		merchantDto.setEmailId(emailId);
		merchantDto.setPassword(password);
		merchantDto.setQuestion1(question1);
		merchantDto.setHint1(hint1);
		merchantDto.setQuestion2(question2);
		merchantDto.setHint2(hint2);
		merchantDto.setOwnerType(ownerType);
		merchantDto.setBusinessLegalname(businessLegalname);
		merchantDto.setAddress1BI(address1BI);
		merchantDto.setAddress2BI(address2BI);
		merchantDto.setCityOrTownBI(cityOrTownBI);
		merchantDto.setStateorRegionBI(stateorRegionBI);
		merchantDto.setCountryBI(countryBI);
		merchantDto.setPostalCodeBI(postalCodeBI);
		merchantDto.setPhoneCountryCode1(phoneCountryCode1);
		merchantDto.setPhoneNumber(phoneNumber);
		merchantDto.setBusinessCategory(businessCategory);
		merchantDto.setSubCategory(subCategory);
		merchantDto.setBusinessEstablishedMonth(businessEstablishedMonth);
		merchantDto.setBusinessEstablishedYear(businessEstablishedYear);
		merchantDto.setWebsite(website);
		merchantDto.setCurrency(currency);
		merchantDto.setAverageTransactionAmount(Long.parseLong(averageTransactionAmount));
		merchantDto.setHighestMonthlyVolume(Long.parseLong(highestMonthlyVolume));
		merchantDto
				.setPercentageOfAnnualRevenueFromOnlineSales(percentageOfAnnualRevenueFromOnlineSales);
		merchantDto.setFirstName(firstName);
		merchantDto.setLastName(lastName);
		merchantDto.setHomeAddress(homeAddress);
			if(homeAddress){
				merchantDto.setAddress1BO(address1BI);
				merchantDto.setAddress2BO(address2BI);
				merchantDto.setCityOrTownBO(cityOrTownBI);
				merchantDto.setStateOrRegionBO(stateorRegionBI);
				merchantDto.setCountryBO(countryBI);
				merchantDto.setPostalCodeBO(postalCodeBI);
			} else{
				merchantDto.setAddress1BO(address1BO);
				merchantDto.setAddress2BO(address2BO);
				merchantDto.setCityOrTownBO(cityOrTownBO);
				merchantDto.setStateOrRegionBO(stateOrRegionBO);
				merchantDto.setCountryBO(countryBO);
				merchantDto.setPostalCodeBO(postalCodeBO);	
			}
		merchantDto.setEmailCSI(emailCSI);
		merchantDto.setCode(code);
		merchantDto.setPhone(phone);
		if(codeCheck){
			merchantDto.setMerchantCode(merchantCode.trim());
			merchantDto.setSuccessUrl(successUrl.trim());
			merchantDto.setFailureUrl(failureUrl.trim());
		}
		merchantDto.setCodeCheck(codeCheck);
		return merchantDto;
	}
	
	public Boolean getCodeCheck() {
		return codeCheck;
	}

	public void setCodeCheck(Boolean codeCheck) {
		this.codeCheck = codeCheck;
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
	 * @return the hint1
	 */
	public String getHint1() {
		return hint1;
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
	 * @param hint1 the hint1 to set
	 */
	public void setHint1(String hint1) {
		this.hint1 = hint1;
	}

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
	 * @return the countryBI
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
	 * @param businessEstablishedMonth the businessEstablishedMonth to set
	 */
	public void setBusinessEstablishedMonth(String businessEstablishedMonth) {
		this.businessEstablishedMonth = businessEstablishedMonth;
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
	public Long getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Long currency) {
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
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
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
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the confirmEmailId
	 */
	public String getConfirmEmailId() {
		return confirmEmailId;
	}

	/**
	 * @param confirmEmailId the confirmEmailId to set
	 */
	public void setConfirmEmailId(String confirmEmailId) {
		this.confirmEmailId = confirmEmailId;
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

	/**
	 * @return the creationDateAsMMDDYYY
	 */
	public String getCreationDateAsMMDDYYY() {
		return creationDateAsMMDDYYY;
	}

	/**
	 * @param creationDateAsMMDDYYY the creationDateAsMMDDYYY to set
	 */
	public void setCreationDateAsMMDDYYY(String creationDateAsMMDDYYY) {
		this.creationDateAsMMDDYYY = creationDateAsMMDDYYY;
	}

	/**
	 * @return the terms
	 */
	public Boolean getTerms() {
		return terms;
	}

	/**
	 * @param terms the terms to set
	 */
	public void setTerms(Boolean terms) {
		this.terms = terms;
	}

	/**
	 * @return the status2
	 */
	public Long getStatus2() {
		return status2;
	}

	/**
	 * @param status2 the status2 to set
	 */
	public void setStatus2(Long status2) {
		this.status2 = status2;
	}

	public String getEmailNote() {
		return emailNote;
	}

	public void setEmailNote(String emailNote) {
		this.emailNote = emailNote;
	}

	public String getExistPersonPhoneNo() {
		return existPersonPhoneNo;
	}

	public void setExistPersonPhoneNo(String existPersonPhoneNo) {
		this.existPersonPhoneNo = existPersonPhoneNo;
	}

	public String getExistServicePhoneNo() {
		return existServicePhoneNo;
	}

	public void setExistServicePhoneNo(String existServicePhoneNo) {
		this.existServicePhoneNo = existServicePhoneNo;
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

	public Boolean getOldActive() {
		return oldActive;
	}

	public void setOldActive(Boolean oldActive) {
		this.oldActive = oldActive;
	}
	
	public Boolean getOldDeleted() {
		return oldDeleted;
	}

	public void setOldDeleted(Boolean oldDeleted) {
		this.oldDeleted = oldDeleted;
	}

	public Long getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Long oldStatus) {
		this.oldStatus = oldStatus;
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

	public String getOldEmailId() {
		return oldEmailId;
	}

	public void setOldEmailId(String oldEmailId) {
		this.oldEmailId = oldEmailId;
	}

	public String getCountryBIName() {
		return countryBIName;
	}

	public void setCountryBIName(String countryBIName) {
		this.countryBIName = countryBIName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getBusinessCategoryName() {
		return businessCategoryName;
	}

	public void setBusinessCategoryName(String businessCategoryName) {
		this.businessCategoryName = businessCategoryName;
	}

	public String getStateorRegionBIName() {
		return stateorRegionBIName;
	}

	public void setStateorRegionBIName(String stateorRegionBIName) {
		this.stateorRegionBIName = stateorRegionBIName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getPercentageOfAnnualRevenueFromOnlineSalesName() {
		return percentageOfAnnualRevenueFromOnlineSalesName;
	}

	public void setPercentageOfAnnualRevenueFromOnlineSalesName(
			String percentageOfAnnualRevenueFromOnlineSalesName) {
		this.percentageOfAnnualRevenueFromOnlineSalesName = percentageOfAnnualRevenueFromOnlineSalesName;
	}

	public String getOwnerTypeName() {
		return ownerTypeName;
	}

	public void setOwnerTypeName(String ownerTypeName) {
		this.ownerTypeName = ownerTypeName;
	}

}
