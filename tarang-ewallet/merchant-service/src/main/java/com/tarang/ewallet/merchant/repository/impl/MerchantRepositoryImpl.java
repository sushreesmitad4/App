package com.tarang.ewallet.merchant.repository.impl;
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
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.masterdata.business.MasterDataService;
import com.tarang.ewallet.merchant.dao.MerchantDao;
import com.tarang.ewallet.merchant.repository.MerchantRepository;
import com.tarang.ewallet.merchant.util.MerchantUtil;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.BusinessInformation;
import com.tarang.ewallet.model.BusinessOwnerInformation;
import com.tarang.ewallet.model.Country;
import com.tarang.ewallet.model.Hints;
import com.tarang.ewallet.model.Merchant;
import com.tarang.ewallet.model.MerchantPayInfo;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;


public class MerchantRepositoryImpl implements MerchantRepository{

	private static final Logger LOGGER = Logger.getLogger(MerchantRepositoryImpl.class);
	
	private CryptService cryptService;
	
	private MerchantDao merchantDao;
	
	private CommonService commonService;
	
	private MasterDataService masterDataService;
	
	private HibernateTransactionManager transactionManager;
	
	public MerchantRepositoryImpl(MerchantDao merchantDao, CommonService commonService, 
			CryptService cryptService, MasterDataService masterDataService, HibernateTransactionManager transactionManager){
		this.merchantDao = merchantDao;
	    this.commonService = commonService;
		this.cryptService = cryptService;
		this.masterDataService = masterDataService;
		this.transactionManager = transactionManager;
	}
	
	@Override
	public MerchantDto newregistration(MerchantDto merchantDto)
			throws WalletException {
		LOGGER.debug( " registration " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Hints hints = commonService.createHints(MerchantUtil.getHintsObj(merchantDto));
			Authentication authentication = new Authentication();
			authentication.setHints(hints.getId());
			authentication.setIpAddress(merchantDto.getIpAddress());
			authentication.setPassword(cryptService.encryptData(merchantDto.getPassword()));
			authentication.setHints(hints.getId());
						
			commonService.createAuthentication(MerchantUtil.getAuthenticationObj(merchantDto, authentication));
			// business information operation
			Address addressBI = commonService.createAddress(MerchantUtil.getAddressObj(merchantDto, MerchantUtil.BUSINESS_INFORMATION));
			PhoneNumber phonenumberBI = commonService.createPhone(MerchantUtil.getPhoneNumberObj(merchantDto, MerchantUtil.BUSINESS_INFORMATION));
			BusinessInformation businessInformation = MerchantUtil.getBusinessInformationObj(merchantDto, addressBI.getId(), phonenumberBI.getId());
	
			businessInformation = merchantDao.saveBusinessInformation(businessInformation);
	
			// business owner information operations
			Address addressBO = commonService.createAddress(MerchantUtil.getAddressObj(merchantDto, MerchantUtil.BUSINESS_OWNER_INFORMATION));
			PersonName personName = commonService.createPersonName(MerchantUtil.getPersonNameObj(merchantDto));
			BusinessOwnerInformation businessOwnerInformation = MerchantUtil.getBusinessOwnerInformationObj(
					personName.getId(), addressBO.getId(),merchantDto.getHomeAddress());
	
			businessOwnerInformation = merchantDao.saveBusinessOwnerInformation(businessOwnerInformation);
	
			// customer service information operations
			PhoneNumber phoneNumberCSI = null;
			if(merchantDto.getCode() != null && !"".equals(merchantDto.getCode().trim())){
				phoneNumberCSI = commonService.createPhone(MerchantUtil.getPhoneNumberObj(
						merchantDto, MerchantUtil.CUSTOMER_SERVICE_INFORMATION));
			}
			
			if(merchantDto.getCodeCheck() != null && merchantDto.getCodeCheck()){
				MerchantPayInfo merPayInfo = MerchantUtil.getMerchantPayInfo(merchantDto.getMerchantCode(), merchantDto.getSuccessUrl(), merchantDto.getFailureUrl());
				merchantDao.saveMerchantPayInfo(merPayInfo);
				merchantDto.setMerchantPayInfoId(merPayInfo.getId());
			}
			
			// Merchant Object save
			Merchant merchant = MerchantUtil.getMerchantObj(authentication.getId(),
					businessInformation.getId(), businessOwnerInformation.getId(),
					merchantDto.getEmailCSI(), phoneNumberCSI != null ? phoneNumberCSI.getId() : null, merchantDto.getMerchantPayInfoId());
			merchantDao.saveMerchant(merchant);
			/*Create User Wallet*/
			commonService.createUserWallet(authentication.getId(), merchantDto.getDefaultCurrency());
			transactionManager.commit(txstatus);
			commonService.sendRegistrationMail(personName.getFirstName()+ GlobalLitterals.SPACE_STRING + personName.getLastName(), 
					authentication.getId(), GlobalLitterals.MERCHANT_USER_TYPE, merchantDto.getLanguageId());
			MerchantUtil.constructMerchantDtoWithDefaultVal(merchantDto, authentication, merchant);
			
		} catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			if(ex.getMessage().contains(CommonConstrain.INSERT_PHONENUMBER)){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, ex);
			} else {
				throw ex;
			}
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
			}
			if(e.getMessage().contains(CommonConstrain.INSERT_PHONENUMBER)){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			} else {
				throw new WalletException("merchant.registration.failed", e);
			}
		}
		return merchantDto;
	}
	
	@Override
	public MerchantDto registrationAfter24hours(MerchantDto merchantDto) throws WalletException {
		LOGGER.debug( " registrationAfter24hours " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Boolean requestForRegistrationAfter24hours = Boolean.TRUE;
			updateMerchant(merchantDto, requestForRegistrationAfter24hours, "merchant");
			Merchant merchant = merchantDao.getMerchant(merchantDto.getId());
			Authentication authentication = commonService.getAuthentication(merchantDto.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE);
			merchantDto.setAuthenticationId(authentication.getId());
			commonService.sendRegistrationMail(merchantDto.getFirstName()+ GlobalLitterals.SPACE_STRING + merchantDto.getLastName(), 
					authentication.getId(), GlobalLitterals.MERCHANT_USER_TYPE, merchantDto.getLanguageId());
			MerchantUtil.constructMerchantDtoWithDefaultVal(merchantDto, authentication, merchant);
			transactionManager.commit(txstatus);
		} catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			if(ex.getMessage().contains(CommonConstrain.UPDATE_PHONENUMBER)){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, ex);
			} else {
				throw ex;
			}
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
			}
			throw new WalletException("merchant.registartion.after.24hours.failed", e);
		}
		return merchantDto;
	}
	
	@Override
	public List<UserDto> getMerchantsList(QueryFilter qf, String fromDate, String toDate, String name, String emailId, Long status) throws WalletException {
		LOGGER.debug( " getMerchantsList " );
		return merchantDao.getMerchantsList(qf, fromDate, toDate, name, emailId, status);
	}
	
	// merchant management methods
	public MerchantDto getMerchantDetailsById(Long merchantId)throws WalletException {
		LOGGER.debug( " getMerchantDetailsById " );
		Merchant merchant = merchantDao.getMerchant(merchantId);
		return	convertModelsToDto(merchant);
	}
	
	

	@Override
	public Boolean updateMerchantDetails(MerchantDto merchantDto, String adminOrmerchant) throws WalletException {
		LOGGER.debug( " updateMerchantDetails " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Boolean requestForRegistrationAfter24hours = Boolean.FALSE;
			Boolean isStatusUpdated = updateMerchant(merchantDto, requestForRegistrationAfter24hours, adminOrmerchant);
			Authentication authentication = commonService.getAuthentication(merchantDto.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE);
			merchantDto.setAuthenticationId(authentication.getId());
			transactionManager.commit(txstatus);
			//Send mail for status changed
			if(UserStatusConstants.REJECTED.equals(merchantDto.getStatus())){
				commonService.sendMailUserAccountReject(merchantDto.getFirstName()+ GlobalLitterals.SPACE_STRING + merchantDto.getLastName(), 
						merchantDto.getEmailId(), merchantDto.getUpdateReason(), GlobalLitterals.MERCHANT_USER_TYPE, merchantDto.getLanguageId());
			}
			/* Send mail for status changed to approved from rejected */
			Boolean isActive = merchantDto.isActive() != null ? merchantDto.isActive() : authentication.isActive();
			if(!UserStatusConstants.REJECTED.equals(merchantDto.getStatus()) && isStatusUpdated){
				commonService.sendMailForUserStatusUpdate(merchantDto.getFirstName()+ GlobalLitterals.SPACE_STRING + merchantDto.getLastName(), 
						merchantDto.getEmailId(), merchantDto.getStatusName(), isActive, merchantDto.getUpdateReason(), GlobalLitterals.MERCHANT_USER_TYPE, merchantDto.getLanguageId());
			}
		} catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			if(ex.getMessage().contains(CommonConstrain.UPDATE_PHONENUMBER)){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, ex);
			} else {
				throw ex;
			}
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
			}
			throw new WalletException("merchant.update.failed", e);
		}
		return true;
	}
	
	@Override
	public Long getMerchantId(String userMail, String userType) throws WalletException {
		Authentication au = commonService.getAuthentication(userMail, userType);
		return merchantDao.getMerchantId(au.getId(), userType); 
	}
	
	@Override
	public String getPersonName(String userMail, String userType)throws WalletException{
		Long id = getMerchantId(userMail, userType);
		MerchantDto merchantDto = getMerchantDetailsById(id);
		return merchantDto.getFirstName() + " " + merchantDto.getLastName();
	}
	
	@Override
	public Address getMerchantAddress(Long merchantId) throws WalletException {
		try{
			Merchant merchant = merchantDao.getMerchant(merchantId);
			BusinessInformation businessInformation = merchantDao.getBusinessInformation(merchant.getBusinessInformation());
			return commonService.getAddress(businessInformation.getAddress());
		} catch(Exception e){
			throw new WalletException("unable to retrive profile address details", e);
		}
	}
	 
	
	
	@Override
	public BusinessInformation getMerchantBusinessInfo(Long merchantId) throws WalletException {
		try{
			Merchant merchant = merchantDao.getMerchant(merchantId);
			BusinessInformation businessInformation = merchantDao.getBusinessInformation(merchant.getBusinessInformation());
			return businessInformation;
		} catch(Exception e){
			throw new WalletException("unable to retrive profile address details", e);
		}
	}
	
	/* 
	 * This method will called in transaction to find out the receiver is registered member or not.
	 */
	@Override
	public MerchantDto getRegisteredMember(String userMail, String userType) throws WalletException {
		return getMerchantDetailsById(getMerchantId(userMail, userType));
	}
	
	@Override
	public Merchant getMerchantByAuthId(Long authId) throws WalletException {
		return merchantDao.getMerchantByAuthId(authId);
	}
	
	private MerchantDto convertModelsToDto(Merchant merchant)throws WalletException{
		LOGGER.debug( " convertModelsToDto " );
		Authentication authentication = commonService.getAuthentication(merchant.getAuthenticationId());
		//getting account details information(hints)
		Hints hints = commonService.getHints(authentication.getHints());
		
		//getting  business information
		BusinessInformation businessInformation = merchantDao.getBusinessInformation(merchant.getBusinessInformation());
		Address addressBI = commonService.getAddress(businessInformation.getAddress());
		PhoneNumber phoneNumberBI = commonService.getPhone(businessInformation.getPhone());
		
		//getting  business owner information
		BusinessOwnerInformation businessOwnerInformation = merchantDao.getBusinessOwnerInformation(merchant.getBusinessOwnerInformation());
		Address  addressBO = commonService.getAddress(businessOwnerInformation.getAddressId());
		PersonName  personNameBO = commonService.getPersonName(businessOwnerInformation.getNameId());
		
		//getting Customer service information
		String customerEmail = merchant.getCustomerServiceEmail();
		PhoneNumber phoneNumberCSI = null;
		if(merchant.getCustomerServicePhone() != null){
			phoneNumberCSI = commonService.getPhone(merchant.getCustomerServicePhone());
		}
		
		MerchantDto merchantDto = new MerchantDto();
		if(merchant.getPageInfoId() != null){
			MerchantPayInfo merchantPayInfo = merchantDao.getMerchantPayInfo(merchant.getPageInfoId());
			merchantDto.setMerchantCode(merchantPayInfo.getMerchantCode());
			merchantDto.setSuccessUrl(merchantPayInfo.getSuccessUrl());
			merchantDto.setFailureUrl(merchantPayInfo.getFailureUrl());
		}
		/* Business information  setting in to MerchantDto for view addressBI */
		merchantDto.setAuthenticationId(merchant.getAuthenticationId());
		merchantDto.setAddress1BI(addressBI.getAddressOne());
		merchantDto.setAddress2BI(addressBI.getAddressTwo());
		merchantDto.setCityOrTownBI(addressBI.getCity());
		merchantDto.setStateorRegionBI(addressBI.getRegion());
		merchantDto.setCountryBI(addressBI.getCountry());
		merchantDto.setPostalCodeBI(addressBI.getZipcode());
		//phoneNumberBI
		merchantDto.setPhoneCountryCode1(phoneNumberBI.getCode());
		merchantDto.setPhoneNumber(phoneNumberBI.getPnumber());
		//Business Information
		merchantDto.setOwnerType(businessInformation.getOwnerType());
		merchantDto.setBusinessLegalname(businessInformation.getLegalName());
		//here SubCategory missed
		merchantDto.setBusinessCategory(businessInformation.getCategory());
		merchantDto.setBusinessEstablishedMonth(businessInformation.getEstablishedMonth());
		merchantDto.setBusinessEstablishedYear(businessInformation.getEstablishedYear());
		merchantDto.setWebsite(businessInformation.getWebsite());
		merchantDto.setCurrency(businessInformation.getCurrency());
		merchantDto.setAverageTransactionAmount(businessInformation.getAverageTxnAmount());
		merchantDto.setHighestMonthlyVolume(businessInformation.getHeighestMonthlyVolume());
		merchantDto.setPercentageOfAnnualRevenueFromOnlineSales(businessInformation.getPercentageOfAnnualRevenue());
		merchantDto.setSubCategory(businessInformation.getSubCategory());
		/* Business owner information information  setting in to MerchantDto for view personNameBO */
		merchantDto.setFirstName(personNameBO.getFirstName());
		merchantDto.setLastName(personNameBO.getLastName());
		// is same as business address
		merchantDto.setHomeAddress(businessOwnerInformation.getHomeAddress()); 
		//addressBO	
		merchantDto.setAddress1BO(addressBO.getAddressOne());
		merchantDto.setAddress2BO(addressBO.getAddressTwo());
		merchantDto.setCityOrTownBO(addressBO.getCity());
		merchantDto.setStateOrRegionBO(addressBO.getRegion());
		merchantDto.setCountryBO(addressBO.getCountry());
		merchantDto.setPostalCodeBO(addressBO.getZipcode());
		/* Customer service information setting in to MerchantDto for view */
		merchantDto.setEmailCSI(customerEmail) ;
		if(phoneNumberCSI != null){
			merchantDto.setCode(phoneNumberCSI.getCode());
			merchantDto.setPhone(phoneNumberCSI.getPnumber());
		}
		//authentication
		merchantDto.setEmailId(authentication.getEmailId());
		merchantDto.setCreationDate(authentication.getCreationDate());
		merchantDto.setActive(authentication.isActive());
		merchantDto.setBlocked(authentication.isBlocked());
		merchantDto.setStatus(authentication.getStatus());
		merchantDto.setIpAddressCheck(authentication.getIpAddressCheck());
		merchantDto.setEmailPatternCheck(authentication.getEmailPatternCheck());
		merchantDto.setChargeBackCheck(authentication.getChargeBackCheck());
		
		//newly added for edit
		merchantDto.setQuestion1(hints.getHintQuestion1());
		merchantDto.setHint1(hints.getHintAnswer1());
		
		return merchantDto;
	}

	@Override
	public String getLegalName(String email) {
		return merchantDao.getLegalName(email);
	}

	@Override
	public MerchantPayInfo getMerchantPayInfo(String email) throws WalletException {
		MerchantPayInfo merchantPayInfo = null;
		Authentication authentication = commonService.getAuthentication(email, GlobalLitterals.MERCHANT_USER_TYPE);
		Merchant merchant = merchantDao.getMerchantByAuthId(authentication.getId());
		if(merchant.getPageInfoId() != null){
			merchantPayInfo = merchantDao.getMerchantPayInfo(merchant.getPageInfoId());
		}
		return merchantPayInfo;
	}
	
	private Boolean updateMerchant(MerchantDto merchantDto, Boolean requestForRegistrationAfter24hours,
			String adminOrmerchant) throws WalletException{
		Boolean isStatusUpdated = Boolean.FALSE;
		Merchant merchant = merchantDao.getMerchant(merchantDto.getId());
		Authentication authentication = commonService.getAuthentication(merchant.getAuthenticationId());
		if(merchantDto.getStatus() != null && !merchantDto.getStatus().equals(authentication.getStatus())){
			isStatusUpdated = Boolean.TRUE;
		}
		if(merchantDto.isActive() != null && !merchantDto.isActive().equals(authentication.isActive())){
			isStatusUpdated = Boolean.TRUE;
		}
		//getting account details information(hints)
		Hints hints = commonService.getHints(authentication.getHints());
		
		//getting  business information
		BusinessInformation businessInformation = merchantDao.getBusinessInformation(merchant.getBusinessInformation());
		Address addressBI=commonService.getAddress(businessInformation.getAddress());
		PhoneNumber phoneNumberBI = commonService.getPhone(businessInformation.getPhone());
		
		//getting  business owner information
		BusinessOwnerInformation businessOwnerInformation = merchantDao.getBusinessOwnerInformation(merchant.getBusinessOwnerInformation());
		Address  addressBO = commonService.getAddress(businessOwnerInformation.getAddressId());
		PersonName  personNameBO = commonService.getPersonName(businessOwnerInformation.getNameId());
		
		/* from here updating each individual model */
		if("merchant".equals(adminOrmerchant)){
			if(requestForRegistrationAfter24hours){
				authentication.setPassword(cryptService.encryptData(merchantDto.getPassword()));
				authentication.setIpAddress(merchantDto.getIpAddress());
				authentication.setCreationDate(new Date());
				commonService.updateAuthentication(authentication);
			}
			//hints updating
			hints.setHintQuestion1(merchantDto.getQuestion1());
			hints.setHintAnswer1(merchantDto.getHint1());
			commonService.updateHints(hints);
		} else{
			//authentication updating
			if(merchantDto.isActive() != null){
				authentication.setActive(merchantDto.isActive());
			}
			authentication.setIpAddressCheck(merchantDto.getIpAddressCheck());
			authentication.setEmailPatternCheck(merchantDto.getEmailPatternCheck());
			authentication.setChargeBackCheck(merchantDto.getChargeBackCheck());
			if(merchantDto.getStatus() != null){
				if(UserStatusConstants.DELETED.equals(merchantDto.getStatus())){
					/*Account deleted so inactive the account*/
					authentication.setActive(Boolean.FALSE);
				}
				authentication.setStatus(merchantDto.getStatus());
			}
			authentication.setUpdateReason(merchantDto.getUpdateReason());
			commonService.updateAuthentication(authentication);
		}
		
		//addressBI updating
		addressBI.setAddressOne(merchantDto.getAddress1BI());
		addressBI.setAddressTwo(merchantDto.getAddress2BI());
		addressBI.setCountry(merchantDto.getCountryBI());
		addressBI.setRegion(merchantDto.getStateorRegionBI());
		addressBI.setCity(merchantDto.getCityOrTownBI());
		addressBI.setZipcode(merchantDto.getPostalCodeBI());
		commonService.updateAddress(addressBI);
		
		//phonenumberBI updating
		phoneNumberBI.setCode(merchantDto.getPhoneCountryCode1());
		phoneNumberBI.setPnumber(merchantDto.getPhoneNumber());
		commonService.updatePhone(phoneNumberBI);

		//updating Business Information
		businessInformation.setOwnerType(merchantDto.getOwnerType());
		businessInformation.setLegalName(merchantDto.getBusinessLegalname());
		businessInformation.setCategory(merchantDto.getBusinessCategory());
		businessInformation.setSubCategory(merchantDto.getSubCategory());
		businessInformation.setEstablishedMonth(merchantDto.getBusinessEstablishedMonth());
		businessInformation.setEstablishedYear(merchantDto.getBusinessEstablishedYear());
		businessInformation.setCurrency(merchantDto.getCurrency());
		businessInformation.setAverageTxnAmount(merchantDto.getAverageTransactionAmount());
		businessInformation.setHeighestMonthlyVolume(merchantDto.getHighestMonthlyVolume());
		businessInformation.setPercentageOfAnnualRevenue(merchantDto.getPercentageOfAnnualRevenueFromOnlineSales());
		businessInformation.setWebsite(merchantDto.getWebsite());
		merchantDao.updateBusinessInformation(businessInformation);
		
		//addressBO updating
		addressBO.setAddressOne(merchantDto.getAddress1BO());
		addressBO.setAddressTwo(merchantDto.getAddress2BO());
		addressBO.setCountry(merchantDto.getCountryBO());
		addressBO.setRegion(merchantDto.getStateOrRegionBO());
		addressBO.setCity(merchantDto.getCityOrTownBO());
		addressBO.setZipcode(merchantDto.getPostalCodeBO());
		commonService.updateAddress(addressBO);
		
		//personName updating
		personNameBO.setFirstName(merchantDto.getFirstName());
		personNameBO.setLastName(merchantDto.getLastName());
		commonService.updatePersonName(personNameBO);
		
		//BusinessOwnerInformation updating
		businessOwnerInformation.setHomeAddress(merchantDto.getHomeAddress());
		merchantDao.updateBusinessOwnerInformation(businessOwnerInformation);
		
		//phoneNumberCSI updating
		PhoneNumber phoneNumberCSI = null;
		if(merchantDto.getCode() != null && !"".equals(merchantDto.getCode().trim())){
			if(merchant.getCustomerServicePhone() != null){
				phoneNumberCSI = commonService.getPhone(merchant.getCustomerServicePhone());
				commonService.deletePhone(phoneNumberCSI);
			}
			PhoneNumber phoneNumber = new PhoneNumber();		
			phoneNumber.setCode(merchantDto.getCode());
			phoneNumber.setPnumber(merchantDto.getPhone());
			phoneNumber = commonService.createPhone(phoneNumber);
			merchant.setCustomerServicePhone(phoneNumber.getId());
		} else{
			if(merchant.getCustomerServicePhone() != null){
				phoneNumberCSI = commonService.getPhone(merchant.getCustomerServicePhone());
				commonService.deletePhone(phoneNumberCSI);
			}
			merchant.setCustomerServicePhone(null);
		}
		MerchantPayInfo merPayInfo = null;
		if(merchant.getPageInfoId() == null && merchantDto.getCodeCheck()){
			merPayInfo = MerchantUtil.getMerchantPayInfo(merchantDto.getMerchantCode(), merchantDto.getSuccessUrl(), merchantDto.getFailureUrl());
			merchantDao.saveMerchantPayInfo(merPayInfo);
			merchant.setPageInfoId(merPayInfo.getId());
		} else if(merchant.getPageInfoId() != null && merchantDto.getCodeCheck()){
			//user wants to update
			merPayInfo = merchantDao.getMerchantPayInfo(merchant.getPageInfoId());
			merPayInfo.setMerchantCode(merchantDto.getMerchantCode());
			merPayInfo.setSuccessUrl(merchantDto.getSuccessUrl());
			merPayInfo.setFailureUrl(merchantDto.getFailureUrl());
			merchantDao.updateMerchantPayInfo(merPayInfo);
		} else if(merchant.getPageInfoId() != null && !merchantDto.getCodeCheck()){
			//user wants to delete
			merPayInfo = merchantDao.getMerchantPayInfo(merchant.getPageInfoId());
			merchantDao.deleteMerchantPayInfo(merPayInfo);
			merchant.setPageInfoId(null);
		}

		//updating Merchant 
		merchant.setCustomerServiceEmail(merchantDto.getEmailCSI());
		merchantDao.updateMerchant(merchant);
		
		Long countryId = merchantDto.getCountryBI();
		Long authId = authentication.getId();
		Country country = masterDataService.getCountryById(countryId);
		if (null != country) {
			Long currency = country.getCurrency();
			UserWallet userWallet = commonService.getUserWallet(authId, currency);
			if (userWallet == null) {
				commonService.createUserWallet(authId, currency);
			}
		}
		return isStatusUpdated;
	}

	@Override
	public MerchantDto getMerchantByPhoneNumber(String code, String phoneNumber) throws WalletException {
		PhoneNumber phone = null;
		Merchant merchant = null;
		phone = commonService.getPhoneNumberByCode(code, phoneNumber);
		if(null == phoneNumber){
			throw new WalletException(CommonConstrain.PHONE_NUMBER_NOT_EXIST);
		}
		merchant = merchantDao.getMerchantByPhoneId(phone.getId());
		if(null == merchant){
				throw new WalletException(CommonConstrain.MERCHANT_NOT_EXIST);
		}
		return convertModelsToDto(merchant);
	}
	
}