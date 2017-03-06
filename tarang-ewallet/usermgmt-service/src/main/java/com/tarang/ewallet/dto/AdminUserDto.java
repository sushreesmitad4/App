package com.tarang.ewallet.dto;

import com.tarang.ewallet.dto.UserDto;

/**
 * @author kedarnathd
 *
 */
@SuppressWarnings("serial")
public class AdminUserDto extends UserDto {
	
	private String userType;
	
	private String password;
	
	private Long countryId;
	
	private Long stateId;
	
	private String city;
	
	private String addressOne;
	
	private String addressTwo;
	
	private String zipcode;
	
	private Long roleId;
	
	private Boolean deleted;
	
	private String roleName;
	
	private String phoneCode;
	
	private String phoneNo;
	
	private String existphone;
	
	private Long languageId;
	
	private Long authenticationId;
	
	private String updateReason;
	
	public AdminUserDto(){
	}
	
	
	public Long getAuthenticationId() {
		return authenticationId;
	}


	public void setAuthenticationId(Long authenticationId) {
		this.authenticationId = authenticationId;
	}


	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getCountryId() {
		return countryId;
	}
	
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	public Long getStateId() {
		return stateId;
	}
	
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
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
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getExistphone() {
		return existphone;
	}

	public void setExistphone(String existphone) {
		this.existphone = existphone;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}


	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
}
