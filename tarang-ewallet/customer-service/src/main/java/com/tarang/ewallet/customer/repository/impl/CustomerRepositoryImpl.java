/**
 * 
 */
package com.tarang.ewallet.customer.repository.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.customer.dao.CustomerDao;
import com.tarang.ewallet.customer.repository.CustomerRepository;
import com.tarang.ewallet.customer.util.CustomerUtil;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.masterdata.business.MasterDataService;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Country;
import com.tarang.ewallet.model.Customer;
import com.tarang.ewallet.model.Hints;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;


/**
 * @author : prasadj
 * @date : Oct 19, 2012
 * @time : 1:12:13 PM
 * @version : 1.0.0
 * @comments:
 * 
 */
public class CustomerRepositoryImpl implements CustomerRepository {
	
	private static final Logger LOGGER = Logger.getLogger(CustomerRepositoryImpl.class);
	
	private CryptService cryptService;

	private CommonService commonService;

	private CustomerDao customerDao;
	
	private MasterDataService masterDataService;
	
	private HibernateTransactionManager transactionManager;

	public CustomerRepositoryImpl(CustomerDao customerDao, CommonService commonService, 
			CryptService cryptService, MasterDataService masterDataService, HibernateTransactionManager transactionManager) {
		this.customerDao = customerDao;
		this.commonService = commonService;
		this.cryptService = cryptService;
		this.masterDataService = masterDataService;
		this.transactionManager = transactionManager;
	}
	
	@Override
	public Boolean updateCustomerDetails(CustomerDto customerDto) throws WalletException {
		LOGGER.debug( " updateCustomerDetails " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Boolean requestForRegistrationAfter24hours = Boolean.FALSE;
			Boolean isStatusUpdated = updateCustomer(customerDto, requestForRegistrationAfter24hours);
			Authentication authentication = commonService.getAuthentication(customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			transactionManager.commit(txstatus);
			//Send mail for status changed to rejected from pending
			if(UserStatusConstants.REJECTED.equals(customerDto.getStatus())){
				commonService.sendMailUserAccountReject(customerDto.getFirstName()+ GlobalLitterals.SPACE_STRING + customerDto.getLastName(), 
						authentication.getEmailId(), customerDto.getUpdateReason(), GlobalLitterals.CUSTOMER_USER_TYPE, customerDto.getLanguageId());
			}
			//Send mail for status changed
			Boolean isActive = customerDto.isActive() != null ? customerDto.isActive() : authentication.isActive();
			if(!UserStatusConstants.REJECTED.equals(customerDto.getStatus()) && isStatusUpdated){
				commonService.sendMailForUserStatusUpdate(customerDto.getFirstName()+ GlobalLitterals.SPACE_STRING + customerDto.getLastName(), 
						authentication.getEmailId(), customerDto.getStatusName(), isActive, customerDto.getUpdateReason(), GlobalLitterals.CUSTOMER_USER_TYPE, customerDto.getLanguageId());
			}
		}catch(Exception e){
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
			}
			if(e.getMessage().contains(CommonConstrain.UPDATE_PHONENUMBER)){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else if(e instanceof org.hibernate.exception.ConstraintViolationException){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else {
				throw new WalletException(CommonConstrain.CUSTOMER_UPDATE_FAILED, e);
			}
			
		}
		return true;
	}

	@Override
	public CustomerDto newregistration(CustomerDto customerDto) throws WalletException {
		LOGGER.debug( " newregistration " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			customerDto.setKycRequired(false);
			
			PhoneNumber landLine = new PhoneNumber(customerDto.getPhoneCode(), customerDto.getPhoneNo());
			/*Create phone*/
			landLine = commonService.createPhone(landLine);
			
			Hints hints = new Hints();
			CustomerUtil.updateHints(customerDto, hints);
			/*Create hints*/
			hints = commonService.createHints(hints);
	
			Authentication authentication = new Authentication();
			authentication.setIpAddress(customerDto.getIpAddress());
			authentication.setHints(hints.getId());
			authentication.setPassword(cryptService.encryptData(customerDto.getPassword()));
			authentication = CustomerUtil.updateAuthentication(customerDto, authentication);
			/*Create authentication*/
			authentication = commonService.createAuthentication(authentication);
			Customer customer = new Customer();
			PersonName personName = new PersonName();
			if(customerDto.getProfileComplet()){
				Address address = new Address();
				CustomerUtil.updateAddress(customerDto, address);
				/*Create Address*/
				address = commonService.createAddress(address);
				personName.setFirstName(customerDto.getFirstName());
				personName.setLastName(customerDto.getLastName());
				/*Create person*/
				personName = commonService.createPersonName(personName);
				
				customer.setAddressId(address.getId());
				customer.setNameId(personName.getId());
				customer.setProfileComplet(Boolean.TRUE);
			}else{
				customer.setAddressId(null);
				customer.setNameId(null);
				customer.setProfileComplet(Boolean.FALSE);
				personName.setFirstName("There");
				personName.setLastName("");
				personName.setLastName(customerDto.getLastName());
			}
			customer.setAuthenticationId(authentication.getId());
			customer.setDateOfBirth(customerDto.getDateOfBirth());
			customer.setTitle(customerDto.getTitle());
			customer.setPhoneIdOne(landLine.getId());
			
			/*Create customer*/
			Customer cid = customerDao.registration(customer);
			customerDto.setId(cid.getId());
			customerDto.setAuthenticationId(authentication.getId());
			/*Update non register wallet status*/
			/*List<NonRegisterWallet> nonRegWallets = commonService.getNonRegisterWallets(authentication.getEmailId());
			if(nonRegWallets != null){
				for(NonRegisterWallet nonRegWallet:nonRegWallets){
					nonRegWallet.setRegister(Boolean.TRUE);
				}
				commonService.updateNonRegisterWallet(nonRegWallets);
			}*/
			/*Create User Wallet*/
			commonService.createUserWallet(authentication.getId(), customerDto.getDefaultCurrency());
			transactionManager.commit(txstatus);
			commonService.sendRegistrationMail(personName.getFirstName()+ GlobalLitterals.SPACE_STRING + personName.getLastName(), 
					authentication.getId(), GlobalLitterals.CUSTOMER_USER_TYPE, customerDto.getLanguageId());
		}catch(Exception e){
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
			}
			if(e.getMessage().contains("insert into PhoneNumber")){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else if(e instanceof org.hibernate.exception.ConstraintViolationException){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else{
				throw new WalletException(CommonConstrain.CUSTOMER_REGISTRATION_FAILED, e);
			}
		}
		return customerDto;
	}
	
	/*	Note: User is requested for registration after 24hours from his/her first time registration, 
	 * 	Then the system will all allow to register with same email id and it will update the existing 
	 * 	record including IP address, password and creation date
	 * 	@auther: kedarnathd 
	 */
	@Override
	public CustomerDto registrationAfter24hours(CustomerDto customerdto) throws WalletException {
		LOGGER.debug( " registrationAfter24hours " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Boolean requestForRegistrationAfter24hours = Boolean.TRUE;
			updateCustomer(customerdto, requestForRegistrationAfter24hours);
			Customer customer = customerDao.getCustomer(getCustomerId(customerdto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE));
			customerdto.setId(customer.getId());
			customerdto.setAuthenticationId(customer.getAuthenticationId());
			transactionManager.commit(txstatus);
			commonService.sendRegistrationMail(customerdto.getFirstName()+ GlobalLitterals.SPACE_STRING + customerdto.getLastName(), 
					customerdto.getAuthenticationId(), GlobalLitterals.CUSTOMER_USER_TYPE, customerdto.getLanguageId());
		}catch(Exception e){
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
			}
			if(e.getMessage().contains(CommonConstrain.UPDATE_PHONENUMBER)){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else if(e instanceof org.hibernate.exception.ConstraintViolationException){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else {
				throw new WalletException(CommonConstrain.CUSTOMER_UPDATE_FAILED, e);
			}
		}
		return customerdto;
	}

	@Override
	public boolean deleteCustomer(Long id) throws WalletException {
		LOGGER.debug( " deleteCustomer " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Customer customer = customerDao.getCustomer(id);
			Boolean deleteFlag = commonService.deleteAuthentication(customer.getAuthenticationId());
			transactionManager.commit(txstatus);
			return deleteFlag;
		}catch (Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		} 
	}

	@Override
	public CustomerDto getCustomerDto(Long id) throws WalletException {
		LOGGER.debug( " getCustomerDto ");
		Customer customer = customerDao.getCustomer(id);
	     return	convertModelsToDto(customer);
	}

	@Override
	public List<UserDto> getCustomersList(QueryFilter qf, String fromDate, 
			String toDate, String name, String emailId, Long status) throws WalletException {
		LOGGER.debug(" getCustomersList " );
		return customerDao.getCustomersList(qf, fromDate, toDate, name, emailId, status);
	}
	
	@Override
	public Long getCustomerId(String userMail, String userType)
			throws WalletException {
		LOGGER.debug( " getCustomerId " );
		Authentication au = commonService.getAuthentication(userMail, userType);
		return customerDao.getCustomerId(au.getId(), userType); 
	}
	
	@Override
	public String getPersonName(String userMail, String userType) throws WalletException{
		CustomerDto customerDto = getCustomerDto(getCustomerId(userMail, userType));
		return customerDto.getFirstName() + " " + customerDto.getLastName();
	}
	
	@Override
	public Address getCustomerAddress(Long custId) throws WalletException {
		Customer customer = customerDao.getCustomer(custId);
		return commonService.getAddress(customer.getAddressId());
	}
	
	/* 
	 * This method will called in transaction to find out the receiver is registered member or not.
	 */
	@Override
	public CustomerDto getRegisteredMember(String userMail, String userType) throws WalletException {
		return getCustomerDto(getCustomerId(userMail, userType));
	}
	
	@Override
	public Customer getCustomerByAuthId(Long authId) throws WalletException {
		return customerDao.getCustomerByAuthId(authId);
	}
	
	private Boolean updateCustomer(CustomerDto customerDto, Boolean requestForRegistrationAfter24hours) throws WalletException{
		LOGGER.debug( " updateCustomer " );
		Boolean isStatusUpdated = Boolean.FALSE;
		Customer customer = customerDao.getCustomer(getCustomerId(customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE));
		Long authId = customer.getAuthenticationId();
		Long personId = customer.getNameId();
		Long addrId = customer.getAddressId();
		Long phoneId = customer.getPhoneIdOne();
		Authentication authentication = commonService.getAuthentication(authId);
		if(requestForRegistrationAfter24hours){
			authentication.setPassword(cryptService.encryptData(customerDto.getPassword()));
			authentication.setCreationDate(new Date());
			authentication.setIpAddress(customerDto.getIpAddress());
		}
		if(customerDto.getStatus() != null && !customerDto.getStatus().equals(authentication.getStatus())){
			isStatusUpdated = Boolean.TRUE;
		}
		if(customerDto.isActive() != null && !customerDto.isActive().equals(authentication.isActive())){
			isStatusUpdated = Boolean.TRUE;
		}
		if(customerDto.isActive() != null){
			authentication.setActive(customerDto.isActive());
		}
		if(customerDto.getStatus() != null){
			if(UserStatusConstants.DELETED.equals(customerDto.getStatus())){
				/*Account deleted so inactive the account*/
				authentication.setActive(Boolean.FALSE);
			}
			authentication.setStatus(customerDto.getStatus());
		}
		authentication.setKycRequired(customerDto.getKycRequired());
		authentication.setIpAddressCheck(customerDto.getIpAddressCheck());
		authentication.setEmailPatternCheck(customerDto.getEmailPatternCheck());
		authentication.setUpdateReason(customerDto.getUpdateReason());
		commonService.updateAuthentication(authentication);
			
		Long hintId = authentication.getHints();
		Hints hints = commonService.getHints(hintId);
		hints.setHintQuestion1(customerDto.getHintQuestion1());
		hints.setHintAnswer1(customerDto.getAnswer1());
		commonService.updateHints(hints);
		
		Address address = new Address();
		CustomerUtil.updateAddress(customerDto, address);
		
		PersonName personName = new PersonName();
		personName.setFirstName(customerDto.getFirstName());
		personName.setLastName(customerDto.getLastName());
		if(customer.getProfileComplet()){
			/*Update Address*/
			address.setId(addrId);
			commonService.updateAddress(address);
			/*Update Person*/
			personName.setId(personId);
			commonService.updatePersonName(personName);
		}else{
			/*Create Address*/
			address = commonService.createAddress(address);
			/*Create person*/
			personName = commonService.createPersonName(personName);
			customer.setAddressId(address.getId());
			customer.setNameId(personName.getId());
		}
		if(!customer.getProfileComplet()){
			customer.setProfileComplet(Boolean.TRUE);
		}	
		PhoneNumber phone = commonService.getPhone(phoneId);
		phone.setCode(customerDto.getPhoneCode());
		phone.setPnumber(customerDto.getPhoneNo());
		commonService.updatePhone(phone);
			
		customer.setTitle(customerDto.getTitle());
		customer.setDateOfBirth(customerDto.getDateOfBirth());
		customerDao.updateCustomer(customer);
			
		Long countryId = customerDto.getCountry();
		Country country = masterDataService.getCountryById(countryId);
		if(null != country){
			Long currency = country.getCurrency();
			UserWallet userWallet = commonService.getUserWallet(authId, currency);
			if(userWallet == null){
				commonService.createUserWallet(authId, currency);
			}
		}
		return isStatusUpdated;
	}
	
	private CustomerDto convertModelsToDto(Customer customer)throws WalletException{
		
		LOGGER.debug( " convertModelsToDto " );
		PersonName personName = null;
		if(null != customer.getNameId()){
			personName = commonService.getPersonName(customer.getNameId());	
		}
		PhoneNumber phoneNumber1=commonService.getPhone(customer.getPhoneIdOne());
		Address address = null;
		if(null != customer.getAddressId()){
			address = commonService.getAddress(customer.getAddressId());
		}
		
		Authentication authentication = commonService.getAuthentication(customer.getAuthenticationId());
		Hints hints = commonService.getHints(authentication.getHints());
	
		CustomerDto customerDto=new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setAuthenticationId(authentication.getId());
		if(null != personName){
			customerDto.setFirstName(personName.getFirstName());
			customerDto.setLastName(personName.getLastName());
		}
		customerDto.setEmailId(authentication.getEmailId());
		customerDto.setPassword(authentication.getPassword());
		customerDto.setCreationDate(authentication.getCreationDate());
		customerDto.setStatus(authentication.getStatus());
		LOGGER.debug("Authentication Staus is :  "+authentication.getStatus());
		customerDto.setActive(authentication.isActive());
		customerDto.setBlocked(authentication.isBlocked());
		customerDto.setIpAddressCheck(authentication.getIpAddressCheck());
		customerDto.setEmailPatternCheck(authentication.getEmailPatternCheck());
		customerDto.setHintQuestion1(hints.getHintQuestion1());
		customerDto.setAnswer1(hints.getHintAnswer1());
		customerDto.setTitle(customer.getTitle());
		customerDto.setPhoneCode(phoneNumber1.getCode());
		customerDto.setPhoneNo(phoneNumber1.getPnumber());
		customerDto.setDateOfBirth(customer.getDateOfBirth());
		if(null != address){
			customerDto.setCountry(address.getCountry());
			customerDto.setAddrOne(address.getAddressOne());
			customerDto.setAddrTwo(address.getAddressTwo());
			customerDto.setCity(address.getCity());
			customerDto.setState(address.getRegion());
			customerDto.setPostelCode(address.getZipcode());
			customerDto.setAddressId(address.getId());
		}
		customerDto.setKycRequired(authentication.isKycRequired());
		customerDto.setLastLogin(authentication.getLastLogin());
		customerDto.setProfileComplet(customer.getProfileComplet());
		return customerDto;
	}

	@Override
	public CustomerDto mobileNewRegistration(CustomerDto customerDto) throws WalletException {
		LOGGER.debug( " mobileNewRegistration " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			customerDto.setKycRequired(false);
			Boolean isPhoneExist = commonService.phoneNOExist(customerDto.getPhoneCode(), customerDto.getPhoneNo());
			PhoneNumber landLine = new PhoneNumber(customerDto.getPhoneCode(), customerDto.getPhoneNo());
			if(!isPhoneExist){
				/*Create phone*/
				landLine = commonService.createPhone(landLine);
			}else{
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION);
			}
			
			
			Hints hints = new Hints();
			CustomerUtil.updateHints(customerDto, hints);
			/*Create hints*/
			hints = commonService.createHints(hints);
			
			Authentication isExistAuthentication = commonService.getAuthentication(customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			if(null != isExistAuthentication){
				throw new WalletException(CommonConstrain.ALL_READY_REGISTER_USER);
			}
				
			Authentication authentication = new Authentication();
			authentication.setIpAddress(customerDto.getIpAddress());
			authentication.setHints(hints.getId());
			authentication.setPassword(cryptService.encryptData(customerDto.getPassword()));
			authentication = CustomerUtil.updateAuthentication(customerDto, authentication);
			
			/*For Mobile Registration*/
			authentication.setEmailVarification(CommonConstrain.TRUE);
			authentication.setActive(CommonConstrain.TRUE);
			authentication.setStatus(CommonConstrain.DEFAULT_STATUS_MOBILE);
			authentication.setMsisdnNumber(customerDto.getMsisdnNumber());
			authentication.setSimNumber(customerDto.getSimNumber());
			authentication.setImeiNumber(customerDto.getImeiNumber());
			
			/*MPIN generation */
			authentication.setmPin(cryptService.encryptData(customerDto.getmPin()));
   			authentication.setmWalletActicationDate(new Date());
   			authentication.setIsMwalletActive(Boolean.TRUE);
			
			/*Create authentication*/
			authentication = commonService.createMobileAuthentication(authentication);
			Customer customer = new Customer();
			customer.setAddressId(null);
			customer.setAuthenticationId(authentication.getId());
			customer.setDateOfBirth(null);
			customer.setTitle(customerDto.getTitle());
			customer.setNameId(null);
			customer.setPhoneIdOne(landLine.getId());
			customer.setProfileComplet(Boolean.FALSE);
			
			/*Create customer*/
			Customer cid = customerDao.registration(customer);
			customerDto.setId(cid.getId());
			customerDto.setAuthenticationId(authentication.getId());
			PersonName personName = new PersonName();
			personName.setFirstName("There");
			personName.setLastName("");
			/*Update non register wallet status*/
			/*List<NonRegisterWallet> nonRegWallets = commonService.getNonRegisterWallets(authentication.getEmailId());
			if(nonRegWallets != null){
				for(NonRegisterWallet nonRegWallet:nonRegWallets){
					nonRegWallet.setRegister(Boolean.TRUE);
				}
				commonService.updateNonRegisterWallet(nonRegWallets);
			}*/
			/*Create User Wallet*/
			commonService.createUserWallet(authentication.getId(), customerDto.getDefaultCurrency());
			transactionManager.commit(txstatus);
			
			commonService.sendRegistrationMail(personName.getFirstName()+ GlobalLitterals.SPACE_STRING + personName.getLastName(), 
						authentication.getId(), GlobalLitterals.CUSTOMER_USER_TYPE, customerDto.getLanguageId());
			commonService.sendEmailNotificationToMobileUser(/*personName.getFirstName()+*/ GlobalLitterals.SPACE_STRING /*+personName.getLastName()*/, 
						GlobalLitterals.CUSTOMER_USER_TYPE, authentication.getId(), customerDto.getLanguageId(), EmailServiceConstants.MOBILE_WALLET_MPIN_GENERATION_CONFIRMATION);
			
		}catch(Exception e){
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
			}
			if(e.getMessage().contains(CommonConstrain.INSERT_INTO_AUTHENTICATION)){
				throw new WalletException(CommonConstrain.ALL_READY_REGISTER_USER, e);
			}else if(e.getMessage().contains("insert into PhoneNumber")){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else if(e instanceof org.hibernate.exception.ConstraintViolationException){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else if(e.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else if(e.getMessage().contains(CommonConstrain.AUTHENTICATION_CREATE_EXCEPTION)){
				throw new WalletException(CommonConstrain.AUTHENTICATION_CREATE_EXCEPTION, e);
			}else{
				throw new WalletException(CommonConstrain.CUSTOMER_REGISTRATION_FAILED, e);
			}
		}
		return customerDto;
	}

	@Override
	public CustomerDto getCustomerByPhoneNo(String phoneCode, String phoneNo) throws WalletException  {
		
		PhoneNumber phoneNumber = null;
		Customer customer = null;
		phoneNumber = commonService.getPhoneNumberByCode(phoneCode, phoneNo);
		if(null == phoneNumber){
			throw new WalletException(CommonConstrain.PHONE_NUMBER_NOT_EXIST);
		}
		customer = customerDao.getCustomerByPhoneId(phoneNumber.getId());
		if(null == customer){
			throw new WalletException(CommonConstrain.UNIQUE_CUSTOMER_EXCEPTION);
		}
		return getCustomerDto(customer.getId());
	}
	
	
	
	@Override
	public CustomerDto mobileUpdateProfile(CustomerDto customerDto) throws WalletException {
		LOGGER.debug( " mobileNewRegistration " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Address address = new Address();
			CustomerUtil.updateAddress(customerDto, address);
			
			PersonName personName = new PersonName();
			personName.setFirstName(customerDto.getFirstName());
			personName.setLastName(customerDto.getLastName());
			
			Customer customer = customerDao.getCustomer(customerDto.getId());
			if(customer.getProfileComplet()){
				/*Update Address*/
				address.setId(customer.getAddressId());
				commonService.updateAddress(address);
				/*Update Person*/
				personName.setId(customer.getNameId());
				commonService.updatePersonName(personName);
			}else{
				/*Create Address*/
				address = commonService.createAddress(address);
				/*Create person*/
				personName = commonService.createPersonName(personName);
				customer.setAddressId(address.getId());
				customer.setNameId(personName.getId());
			}
			customer.setDateOfBirth(customerDto.getDateOfBirth());
			customer.setTitle(customerDto.getTitle());
			if(!customer.getProfileComplet()){
				customer.setProfileComplet(Boolean.TRUE);
			}
			/*Update customer*/
			customerDao.updateCustomer(customer);
			customerDto.setId(customer.getId());
			transactionManager.commit(txstatus);
		}catch(Exception e){
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
			}
			if(e.getMessage().contains(CommonConstrain.INSERT_INTO_AUTHENTICATION)){
				throw new WalletException(CommonConstrain.ALL_READY_REGISTER_USER, e);
			}else if(e.getMessage().contains("insert into PhoneNumber")){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else if(e instanceof org.hibernate.exception.ConstraintViolationException){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else if(e.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}else if(e.getMessage().contains(CommonConstrain.AUTHENTICATION_CREATE_EXCEPTION)){
				throw new WalletException(CommonConstrain.AUTHENTICATION_CREATE_EXCEPTION, e);
			}else{
				throw new WalletException(CommonConstrain.CUSTOMER_UPDATE_FAILED, e);
			}
		}
		return customerDto;
	}
	
	@Override
	public CustomerDto getCustomerByPhoneNo(String phoneNo) throws WalletException  {
		
		PhoneNumber phoneNumber = null;
		Customer customer = null;
		phoneNumber = commonService.getPhoneNumber( phoneNo);
		if(null == phoneNumber){
			throw new WalletException(CommonConstrain.PHONE_NUMBER_NOT_EXIST);
		}
		customer = customerDao.getCustomerByPhoneId(phoneNumber.getId());
		if(null == customer){
			throw new WalletException(CommonConstrain.UNIQUE_CUSTOMER_EXCEPTION);
		}
		return getCustomerDto(customer.getId());
	}
}
