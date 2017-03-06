/**
 * 
 */
package com.tarang.ewallet.walletui.form;

import com.tarang.ewallet.dto.CustomerDto;

/**
 * @author  : prasadj
 * @date    : Oct 23, 2012
 * @time    : 12:18:45 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CustomerRegFormTwo {

	private Long ptitle;
	
	private String firstName;
	
	private String lastName;
	
	private String dateOfBirth;

	private Long country;

	private String addrOne;
	
	private String addrTwo;
	
	private String city;
	
	private Long state;
	
	private String postalCode;
	
	private String phoneCode;

	private String phoneNo;

	private String mobileCode;	
	
	private String mobileNo;
	
	private boolean terms;
	
	private Boolean profileComplet;
	
	//extra variable	
	private String creationDate;
	private Long id;
	private Boolean active;
	private Boolean deleted;
	private Boolean blocked;
	private Long status;
	private Boolean kycRequired;
	private Long hintQuestion2;
	private String answer2;
	private String existphone;
	private String existmobile;
	/*Keep track of old mail id*/
	private String oldEmailId;
	
	// registration form one relate variables
	private String emailId;
	private String cemailId;
	private String password;
	private String cpassword;
	private Long hintQuestion1;
	private String answer1;
	private String securityCode;
	private String mode;
	/*This used to display email note error message*/
	private String emailNote;
	
	private Boolean ipAddressCheck;
	private Boolean emailPatternCheck;
	
	private String updateReason;
	private Boolean oldActive;
	private Boolean oldDeleted;
	private Long oldStatus;
	
	private String countryName;
	private String stateName;
	private String titleName;
	
	public CustomerRegFormTwo(){
	}
	
	public String getCemailId() {
		return cemailId;
	}

	public void setCemailId(String cemailId) {
		this.cemailId = cemailId;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getTerms() {
		return terms;
	}

	public void setTerms(boolean terms) {
		this.terms = terms;
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

	public Long getPtitle() {
		return ptitle;
	}

	public void setPtitle(Long title) {
		this.ptitle = title;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getEmailId() {
		return emailId;
	}



	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getCreationDate() {
		return creationDate;
	}



	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	public Boolean getDeleted() {
		return deleted;
	}



	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}



	public Boolean getBlocked() {
		return blocked;
	}



	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}



	public Long getStatus() {
		return status;
	}



	public void setStatus(Long status) {
		this.status = status;
	}



	public Boolean getKycRequired() {
		return kycRequired;
	}



	public void setKycRequired(Boolean kycRequired) {
		this.kycRequired = kycRequired;
	}
	public Long getHintQuestion1() {
		return hintQuestion1;
	}

	public void setHintQuestion1(Long hintQuestion1) {
		this.hintQuestion1 = hintQuestion1;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public Long getHintQuestion2() {
		return hintQuestion2;
	}

	public void setHintQuestion2(Long hintQuestion2) {
		this.hintQuestion2 = hintQuestion2;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	
	public String getExistphone() {
		return existphone;
	}

	public void setExistphone(String existphone) {
		this.existphone = existphone;
	}

	public String getExistmobile() {
		return existmobile;
	}

	public void setExistmobile(String existmobile) {
		this.existmobile = existmobile;
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
	public String getEmailNote() {
		return emailNote;
	}

	public void setEmailNote(String emailNote) {
		this.emailNote = emailNote;
	}
	
	public String getOldEmailId() {
		return oldEmailId;
	}

	public void setOldEmailId(String oldEmailId) {
		this.oldEmailId = oldEmailId;
	}
    
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
    
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
    
	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	
	public Boolean getProfileComplet() {
		return profileComplet;
	}

	public void setProfileComplet(Boolean profileComplet) {
		this.profileComplet = profileComplet;
	}

	public void setCustomer(CustomerDto customerDto) {
		if (customerDto != null) {
		    ptitle=customerDto.getTitle();
			firstName = customerDto.getFirstName();
			lastName = customerDto.getLastName();
			dateOfBirth = customerDto.getDateOfBirth().toString();
			country = customerDto.getCountry();
			addrOne=customerDto.getAddrOne();
			addrTwo=customerDto.getAddrTwo();
			city=customerDto.getCity();
			state=customerDto.getState();
			postalCode =customerDto.getPostelCode();
			phoneCode=customerDto.getPhoneCode();
			phoneNo=customerDto.getPhoneNo();
			emailId=customerDto.getEmailId();
			creationDate=customerDto.getCreationDate().toString();
			active=customerDto.isActive();
			deleted=customerDto.isDeleted();
			blocked=customerDto.isBlocked();
			status=customerDto.getStatus();
			kycRequired=customerDto.getKycRequired();
			hintQuestion1=customerDto.getHintQuestion1();
			hintQuestion2=customerDto.getHintQuestion2();
			answer1=customerDto.getAnswer1();
			answer2=customerDto.getAnswer2();
			oldActive = customerDto.isActive();
			oldDeleted = customerDto.isDeleted();
			oldStatus = customerDto.getStatus();
		}
	}

}
