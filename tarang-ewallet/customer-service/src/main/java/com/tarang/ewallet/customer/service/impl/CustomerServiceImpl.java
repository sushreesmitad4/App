/**
 * 
 */
package com.tarang.ewallet.customer.service.impl;

import java.util.List;

import com.tarang.ewallet.customer.repository.CustomerRepository;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Customer;
import com.tarang.ewallet.util.QueryFilter;


/**
* @author  : prasadj
* @date    : Oct 19, 2012
* @time    : 1:38:51 PM
* @version : 1.0.0
* @comments: 
*
*/
public class CustomerServiceImpl implements CustomerService {
	
	private CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public CustomerDto newregistration(CustomerDto customer) throws WalletException {
		return customerRepository.newregistration(customer);
	}
	
	@Override
	public CustomerDto registrationAfter24hours(CustomerDto customer) throws WalletException {
		return customerRepository.registrationAfter24hours(customer);
	}

	@Override
	public Boolean deleteCustomer(Long id) throws WalletException {
		return customerRepository.deleteCustomer(id);
	}

	@Override
	public CustomerDto getCustomerDto(Long id) throws WalletException {
		return customerRepository.getCustomerDto(id);
	}

	@Override
	public List<UserDto> getCustomersList(QueryFilter qf, String fromDate, 
			String toDate, String name, String emailId, Long status)
			throws WalletException {
		return customerRepository.getCustomersList(qf, fromDate, toDate, name, emailId, status);
	}
	
	@Override
	public Boolean	updateCustomerDetails(CustomerDto customerDto)throws WalletException {
		return	customerRepository.updateCustomerDetails(customerDto);
	}

	@Override
	public Long getCustomerId(String userMail, String userType)
			throws WalletException {
		return	customerRepository.getCustomerId(userMail, userType);
	}
	@Override
	public String getPersonName(String userMail, String userType) throws WalletException{
		return customerRepository.getPersonName(userMail,userType);
	}

	@Override
	public Address getCustomerAddress(Long custId) throws WalletException {
		return customerRepository.getCustomerAddress(custId);
	}

	@Override
	public CustomerDto getRegisteredMember(String userMail, String userType) throws WalletException {
		return customerRepository.getRegisteredMember(userMail, userType);
	}

	@Override
	public Customer getCustomerByAuthId(Long authId) throws WalletException {
		return customerRepository.getCustomerByAuthId(authId);
	}

	@Override
	public CustomerDto mobileNewRegistration(CustomerDto customer) throws WalletException {
		return customerRepository.mobileNewRegistration(customer);
	}
	
	@Override
	public CustomerDto mobileUpdateProfile(CustomerDto customer) throws WalletException {
		return customerRepository.mobileUpdateProfile(customer);
	}

	@Override
	public CustomerDto getCustomerByPhoneNo(String phoneCode, String phoneNo) throws WalletException {
		return customerRepository.getCustomerByPhoneNo(phoneCode, phoneNo);
	}
	
	@Override
	public CustomerDto getCustomerByPhoneNo(String phoneNo) throws WalletException {
		return customerRepository.getCustomerByPhoneNo( phoneNo);
	}
	
}
