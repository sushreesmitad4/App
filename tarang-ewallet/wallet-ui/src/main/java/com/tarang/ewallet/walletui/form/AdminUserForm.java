package com.tarang.ewallet.walletui.form;

import com.tarang.ewallet.dto.AdminUserDto;

/**
 * @author kedarnathd
 * 
 */
public class AdminUserForm {

	private Long userId;
	private String firstName;
	private String lastName;
	private Long countryId;
	private Long stateId;
	private String city;
	private String addressOne;
	private String addressTwo;
	private String emailId;
	private Long status;
	private String mode;
	private String zipcode;
	private Long roleId;
	private String roleName;
	private String phoneCode;
	private String phoneNo;

	/* For edit user details */
	private Boolean active;
	private Boolean block;
	private Boolean deleted;
	private String existphone;
	private String updateReason;
	private Boolean oldActive;
	private Boolean oldDeleted;
	
	private String emailNote;

	public AdminUserForm() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getBlock() {
		return block;
	}

	public void setBlock(Boolean block) {
		this.block = block;
	}

	public Boolean getDeleted() {
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
	
	public String getEmailNote() {
		return emailNote;
	}

	public void setEmailNote(String emailNote) {
		this.emailNote = emailNote;
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

	public void setAdminUser(AdminUserDto adminUserDto) {
		if (adminUserDto != null) {
			active = adminUserDto.isActive();
			firstName = adminUserDto.getFirstName();
			lastName = adminUserDto.getLastName();
			countryId = adminUserDto.getCountryId();
			stateId = adminUserDto.getStateId();
			city = adminUserDto.getCity();
			addressOne = adminUserDto.getAddressOne();
			addressTwo = adminUserDto.getAddressTwo();
			emailId = adminUserDto.getEmailId();
			status = adminUserDto.getStatus();
			zipcode = adminUserDto.getZipcode();
			roleId = adminUserDto.getRoleId();
			userId = adminUserDto.getId();
			roleName = adminUserDto.getRoleName();
			phoneCode = adminUserDto.getPhoneCode();
			phoneNo = adminUserDto.getPhoneNo();
			existphone = adminUserDto.getExistphone();
			oldActive = adminUserDto.isActive();
			oldDeleted = adminUserDto.isDeleted();
		}
	}

	public AdminUserDto getAdminUser() {
		AdminUserDto adminUserDto = new AdminUserDto();
		if (mode != null & "update".equals(mode)) {
			adminUserDto.setId(userId);
		}
		adminUserDto.setFirstName(firstName);
		adminUserDto.setLastName(lastName);
		adminUserDto.setCountryId(countryId);
		adminUserDto.setStateId(stateId);
		adminUserDto.setCity(city);
		adminUserDto.setAddressOne(addressOne);
		adminUserDto.setAddressTwo(addressTwo);
		adminUserDto.setEmailId(emailId);
		adminUserDto.setStatus(status);
		adminUserDto.setZipcode(zipcode);
		adminUserDto.setRoleId(roleId);
		adminUserDto.setActive(active);
		adminUserDto.setPhoneCode(phoneCode);
		adminUserDto.setPhoneNo(phoneNo);
		adminUserDto.setExistphone(existphone);
		adminUserDto.setDeleted(deleted);
		adminUserDto.setUpdateReason(updateReason);
		return adminUserDto;
	}
}
