package com.tarang.ewallet.dto;

import java.util.Date;

import com.tarang.ewallet.dto.UserDto;


/**
* @author  : prasadj
* @date    : Oct 19, 2012
* @time    : 12:46:59 PM
* @version : 1.0.0
* @comments: 
*
*/
public class CustomerDto extends UserDto implements Cloneable{
	
	private static final long serialVersionUID = -3264123954427443831L;

	private Long title;

	private Long country;

	private String phoneCode;
	
	private String phoneNo;
	
	private Date dateOfBirth;

	private String addrOne;

	private String addrTwo;
	
	private String city;
	
	private Long state;
	
	private String postelCode;
	
	private Boolean kycRequired;
	
	private String password;

	private Long hintQuestion1;

	private Long hintQuestion2;
	
	private String answer1;
	
	private String answer2;
	//For unit test case
	private Long authenticationId;
	
	//For mail service
	private Long languageId;
	
	private Long defaultCurrency;
	
	private Boolean ipAddressCheck;
	
	private Boolean emailPatternCheck;
	
	private String updateReason;
	
	private String msisdnNumber;

	private String simNumber;

	private String imeiNumber;
	
	private String mPin;
	
	private Date lastLogin;
	
	private Long addressId;
	
	private Boolean profileComplet;
	
	public CustomerDto(){
	}

	public Long getTitle() {
		return title;
	}

	public void setTitle(Long title) {
		this.title = title;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getPostelCode() {
		return postelCode;
	}

	public void setPostelCode(String postelCode) {
		this.postelCode = postelCode;
	}

	public Boolean getKycRequired() {
		return kycRequired;
	}

	public void setKycRequired(Boolean kycRequired) {
		this.kycRequired = kycRequired;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getHintQuestion1() {
		return hintQuestion1;
	}

	public void setHintQuestion1(Long hintQuestion1) {
		this.hintQuestion1 = hintQuestion1;
	}

	public Long getHintQuestion2() {
		return hintQuestion2;
	}

	public void setHintQuestion2(Long hintQuestion2) {
		this.hintQuestion2 = hintQuestion2;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	/**
	 * @return the authenticationId
	 */
	public Long getAuthenticationId() {
		return authenticationId;
	}

	/**
	 * @param authenticationId the authenticationId to set
	 */
	public void setAuthenticationId(Long authenticationId) {
		this.authenticationId = authenticationId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	
	/**
	 * @return the defaultCurrency
	 */
	public Long getDefaultCurrency() {
		return defaultCurrency;
	}

	/**
	 * @param defaultCurrency the defaultCurrency to set
	 */
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

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	  }

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
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

	public String getmPin() {
		return mPin;
	}

	public void setmPin(String mPin) {
		this.mPin = mPin;
	}

	public Boolean getProfileComplet() {
		return profileComplet;
	}

	public void setProfileComplet(Boolean profileComplet) {
		this.profileComplet = profileComplet;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	
}