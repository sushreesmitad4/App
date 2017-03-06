/**
 * 
 */
package com.tarang.ewallet.customer.service;


import java.util.List;

import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Customer;
import com.tarang.ewallet.util.QueryFilter;


/**
* @author  : prasadj
* @date    : Oct 19, 2012
* @time    : 1:38:40 PM
* @version : 1.0.0
* @comments: 
*
*/
public interface CustomerService {
	
	CustomerDto newregistration(CustomerDto customer) throws WalletException;
	
	CustomerDto registrationAfter24hours(CustomerDto customer) throws WalletException;
	
	Boolean deleteCustomer(Long id) throws WalletException;
	
	CustomerDto getCustomerDto(Long id) throws WalletException;

	List<UserDto> getCustomersList(QueryFilter qf, String fromDate, 
			String toDate,String name, String emailId, Long status) throws WalletException;
	
	Boolean updateCustomerDetails(CustomerDto customerDto)throws WalletException;
	 
	Long getCustomerId(String userMail, String userType) throws WalletException ;
	
	String getPersonName(String userMail, String userType) throws WalletException;
	
	Address getCustomerAddress(Long custId) throws WalletException;
	
	CustomerDto getRegisteredMember(String userMail, String userType) throws WalletException;
	
	Customer getCustomerByAuthId(Long authId)  throws WalletException;
	
	CustomerDto mobileNewRegistration(CustomerDto customer) throws WalletException;
	
	CustomerDto getCustomerByPhoneNo(String phoneCode, String phoneNo) throws WalletException;
	
	CustomerDto mobileUpdateProfile(CustomerDto customer) throws WalletException;

	CustomerDto getCustomerByPhoneNo(String phoneNo) throws WalletException;
}
