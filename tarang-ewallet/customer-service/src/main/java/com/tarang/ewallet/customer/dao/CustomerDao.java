/**
 * 
 */
package com.tarang.ewallet.customer.dao;

import java.util.List;

import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Customer;
import com.tarang.ewallet.util.QueryFilter;


/**
* @author  : prasadj
* @date    : Oct 19, 2012
* @time    : 1:38:06 PM
* @version : 1.0.0
* @comments: 
*
*/
public interface CustomerDao {
	
	Customer registration(Customer customer) throws WalletException;
	
	Boolean deleteCustomer(Long id) throws WalletException;
	
	Customer getCustomer(Long id) throws WalletException;

	List<UserDto> getCustomersList(QueryFilter qf, String fromDate, 
			String toDate, String name, String emailId, Long status) throws WalletException;
	
	Long getCustomerId(Long authenticationId, String userType) throws WalletException ;
	
	void updateCustomer(Customer customer) throws WalletException;

	Customer getCustomerByAuthId(Long authId) throws WalletException;
	
	Customer getCustomerByPhoneId(Long phoneId) throws WalletException;
	
}

