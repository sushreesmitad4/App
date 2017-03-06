/**
 * 
 */
package com.tarang.ewallet.util;

import java.util.Date;


import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.util.GlobalLitterals;

/**
 * @Author : kedarnathd
 * @Date : Oct 10 2012
 * @Time : 10:09:24 PM
 * @Version : 1.0
 * @Comments: Admin util for repository service
 */
public class AdminUtil {

	public static PersonName getPersonName(AdminUserDto adminUserDto) {
	    PersonName person = new PersonName();
	    person.setFirstName(adminUserDto.getFirstName());
		person.setLastName(adminUserDto.getLastName());
		return person;
	}

	public static Address getAddress(AdminUserDto adminUserDto) {
		Address address = new Address();
		address.setAddressOne(adminUserDto.getAddressOne());
		address.setAddressTwo(adminUserDto.getAddressTwo());
		address.setCity(adminUserDto.getCity());
		address.setRegion(adminUserDto.getStateId());
		address.setCountry(adminUserDto.getCountryId());
		address.setZipcode(adminUserDto.getZipcode());
		return address;
	}

	public static Authentication getAuthentication(AdminUserDto adminUserDto,
			Authentication authentication) throws WalletException {
		authentication.setUserType(GlobalLitterals.ADMIN_USER_TYPE);
		authentication.setEmailId(adminUserDto.getEmailId());
		authentication.setHints(CommonConstrain.DEFAULT_HINTS);
		authentication.setResetPassword(CommonConstrain.TRUE);
		authentication.setActive(CommonConstrain.TRUE);
		authentication.setBlocked(CommonConstrain.FALSE);
		authentication.setAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
		authentication.setStatus(CommonConstrain.DEFAULT_STATUS_ADMIN);
		authentication.setEmailVarification(CommonConstrain.TRUE);
		authentication.setLoginStatus(CommonConstrain.FALSE);
		authentication.setKycRequired(CommonConstrain.KYC_REQUIRED);
		authentication.setCreationDate(new Date());
		/*These values are need to set at the time of mobile wallet registration, 
		 * now set it to null*/
		authentication.setMsisdnNumber(null);
		authentication.setSimNumber(null);
		authentication.setImeiNumber(null);
		return authentication;
	}

	public static void papulatePersonName(AdminUserDto newAdminUserDto,
			PersonName personName) {
		newAdminUserDto.setFirstName(personName.getFirstName());
		newAdminUserDto.setLastName(personName.getLastName());
	}

	public static void papulateAddress(AdminUserDto newAdminUserDto,
			Address address) {
		newAdminUserDto.setCountryId(address.getCountry());
		newAdminUserDto.setStateId(address.getRegion());
		newAdminUserDto.setCity(address.getCity());
		newAdminUserDto.setAddressOne(address.getAddressOne());
		newAdminUserDto.setAddressTwo(address.getAddressTwo());
		newAdminUserDto.setZipcode(address.getZipcode());
	}

	public static void papulateAuthentication(AdminUserDto newAdminUserDto,
			Authentication authentication) {
		newAdminUserDto.setUserType(authentication.getUserType());
		newAdminUserDto.setEmailId(authentication.getEmailId());
		newAdminUserDto.setBlocked(authentication.isBlocked());
		newAdminUserDto.setStatus(authentication.getStatus());
		newAdminUserDto.setActive(authentication.isActive());
		if(UserStatusConstants.DELETED.equals(authentication.getStatus())){
			newAdminUserDto.setDeleted(Boolean.TRUE);
		} else {
			newAdminUserDto.setDeleted(Boolean.FALSE);
		}
	}

	public static void preparePersonNameForUpdate(PersonName personName,
			AdminUserDto newAdminUserDto) {
		personName.setFirstName(newAdminUserDto.getFirstName());
		personName.setLastName(newAdminUserDto.getLastName());
	}

	public static void prepareAddressForUpdate(Address address,
			AdminUserDto newAdminUserDto) {
		address.setCountry(newAdminUserDto.getCountryId());
		address.setRegion(newAdminUserDto.getStateId());
		address.setCity(newAdminUserDto.getCity());
		address.setAddressOne(newAdminUserDto.getAddressOne());
		address.setAddressTwo(newAdminUserDto.getAddressTwo());
		address.setZipcode(newAdminUserDto.getZipcode());
	}

	public static void prepareAuthenticationForUpdate(Authentication authentication, 
			AdminUserDto newAdminUserDto) {
		authentication.setUserType(GlobalLitterals.ADMIN_USER_TYPE);
		authentication.setHints(0L);
		authentication.setBlocked(Boolean.FALSE);
		authentication.setStatus(CommonConstrain.DEFAULT_STATUS_ADMIN);
		authentication.setActive(newAdminUserDto.isActive());
		if(newAdminUserDto.isDeleted() != null && newAdminUserDto.isDeleted()){
			authentication.setStatus(UserStatusConstants.DELETED);
			authentication.setActive(Boolean.FALSE);
		}
		if(newAdminUserDto.getUpdateReason() != null){
			authentication.setUpdateReason(newAdminUserDto.getUpdateReason());
		}
	}
}
