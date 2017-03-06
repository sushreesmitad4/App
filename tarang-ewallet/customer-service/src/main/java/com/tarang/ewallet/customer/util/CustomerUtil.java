/**
 * 
 */
package com.tarang.ewallet.customer.util;

import java.util.Date;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Hints;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author  : prasadj
 * @date    : Oct 19, 2012
 * @time    : 1:44:18 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CustomerUtil {

	
	public static void updateHints(CustomerDto customerDto, Hints hints){
		
		hints.setHintQuestion1(customerDto.getHintQuestion1());
		hints.setHintAnswer1(customerDto.getAnswer1());
		
	}
	
	public static Authentication updateAuthentication(CustomerDto customerDto, Authentication authentication) throws WalletException{
		
		authentication.setEmailId(customerDto.getEmailId());
		authentication.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);
		authentication.setCreationDate(new Date());
		authentication.setResetPassword(CommonConstrain.FALSE);
		authentication.setActive(CommonConstrain.FALSE);
		authentication.setBlocked(CommonConstrain.FALSE);
		authentication.setmPinBlocked(CommonConstrain.FALSE);
		authentication.setAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
		authentication.setmPinAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
		authentication.setStatus(CommonConstrain.DEFAULT_STATUS);
		authentication.setEmailVarification(CommonConstrain.FALSE);
		authentication.setLoginStatus(CommonConstrain.FALSE);
		authentication.setKycRequired(customerDto.getKycRequired());
		/*These values are need to set at the time of mobile wallet registration, 
		 * now set it to null*/
		authentication.setMsisdnNumber(null);
		authentication.setSimNumber(null);
		authentication.setImeiNumber(null);
		return authentication;

	}

	public static void updateAddress(CustomerDto customerDto, Address address){
		address.setAddressOne(customerDto.getAddrOne());
		address.setAddressTwo(customerDto.getAddrTwo());
		address.setCity(customerDto.getCity());
		address.setRegion(customerDto.getState());
		address.setZipcode(customerDto.getPostelCode());
		address.setCountry(customerDto.getCountry());
		
	}
	
	public static Hints getHints(CustomerDto dto){
		Hints hints = new Hints();
		hints.setHintQuestion1(dto.getHintQuestion1());
		hints.setHintAnswer1(dto.getAnswer1());
		return hints;		
	}

	public static Address getAddress(CustomerDto dto){
		Address address = new Address();
		address.setAddressOne(dto.getAddrOne());
		address.setAddressTwo(dto.getAddrTwo());
		address.setCity(dto.getCity());
		address.setCountry(dto.getCountry());
		address.setRegion(dto.getState());
		address.setZipcode(dto.getPostelCode());
		return address;		
	}
	
}
