package com.tarang.ewallet.merchant.business;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.merchant.util.MerchantUtil;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.BusinessInformation;
import com.tarang.ewallet.model.BusinessOwnerInformation;
import com.tarang.ewallet.model.Hints;
import com.tarang.ewallet.model.Merchant;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.model.PhoneNumber;


@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml",
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class MerchantUtilTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	public static final String BUSINESS_INFORMATION = "BusinessInformation";
	public static final String BUSINESS_OWNER_INFORMATION = "BusinessOwnerInformation";
	public static final String CUSTOMER_SERVICE_INFORMATION = "CustomerServiceInformation";

	@Autowired
	private MerchantService merchantService;

	@Test
	public void getHintsObjTest() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Hints hints = MerchantUtil.getHintsObj(merchantDto);
		Assert.assertNotNull(hints);
		Assert.assertEquals(hints.getHintAnswer1(), merchantDto.getHint1());
	}

	@Test
	public void getAuthenticationObjTest() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Authentication authentication = new Authentication();
		Authentication authentication1 = MerchantUtil.getAuthenticationObj(merchantDto, authentication);
		Assert.assertNotNull(authentication1);
		Assert.assertEquals(merchantDto.getEmailId(), authentication1.getEmailId());
	}

	@Test
	public void getPhoneNumberObjTest1() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		PhoneNumber phoneNumber = MerchantUtil.getPhoneNumberObj(merchantDto, BUSINESS_INFORMATION);
		Assert.assertNotNull(phoneNumber);
		Assert.assertEquals(phoneNumber.getCode(), merchantDto.getPhoneCountryCode1());
	}

	@Test
	public void getPhoneNumberObjTest2() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		PhoneNumber phoneNumber = MerchantUtil.getPhoneNumberObj(merchantDto, CUSTOMER_SERVICE_INFORMATION);
		Assert.assertNotNull(phoneNumber);
		Assert.assertEquals(phoneNumber.getCode(), merchantDto.getPhoneCountryCode1());
	}

	@Test
	public void getAddressObjTest1() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Address address = MerchantUtil.getAddressObj(merchantDto, BUSINESS_INFORMATION);
		Assert.assertNotNull(address);
		Assert.assertEquals(address.getAddressOne(), merchantDto.getAddress1BI());
	}

	@Test
	public void getAddressObjTest2() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Address address = MerchantUtil.getAddressObj(merchantDto, BUSINESS_OWNER_INFORMATION);
		Assert.assertNotNull(address);
		Assert.assertEquals(address.getAddressOne(), merchantDto.getAddress1BO());
	}

	@Test
	public void getPersonNameObjTest1() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		PersonName personName = MerchantUtil.getPersonNameObj(merchantDto);
		Assert.assertNotNull(personName);
	}

	@Test
	public void getBusinessInformationTest1() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Address address = MerchantUtil.getAddressObj(merchantDto, BUSINESS_INFORMATION);
		PhoneNumber phoneNumber = MerchantUtil.getPhoneNumberObj(merchantDto, BUSINESS_INFORMATION);
		BusinessInformation businessInformation = MerchantUtil.getBusinessInformationObj(merchantDto, address.getId(), phoneNumber.getId());
		Assert.assertNotNull(businessInformation);
		Assert.assertEquals(businessInformation.getEstablishedMonth(), merchantDto.getBusinessEstablishedMonth());
	}

	@Test
	public void getBusinessOwnerInformationTest1() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Address address = MerchantUtil.getAddressObj(merchantDto, BUSINESS_OWNER_INFORMATION);
		PersonName personName = MerchantUtil.getPersonNameObj(merchantDto);
		BusinessOwnerInformation businessOwnerInformation = MerchantUtil.getBusinessOwnerInformationObj(personName.getId(), address.getId(), true);
		Assert.assertNotNull(businessOwnerInformation);

	}

	@Test
	public void getMerchantObjTest1() throws WalletException {
		// to get authenticationId
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Authentication authentication = new Authentication();
		Authentication authentication1 = MerchantUtil.getAuthenticationObj(merchantDto, authentication);
		Long authenticationId = authentication1.getId();

		// to get businessOwnerInfoId
		Address address = MerchantUtil.getAddressObj(merchantDto,BUSINESS_OWNER_INFORMATION);
		PersonName personName = MerchantUtil.getPersonNameObj(merchantDto);
		BusinessOwnerInformation businessOwnerInformation = MerchantUtil.getBusinessOwnerInformationObj(personName.getId(), address.getId(), true);
		Long businessOwnerInfoId = businessOwnerInformation.getId();

		// to get businessInfoId
		Address address1 = MerchantUtil.getAddressObj(merchantDto, BUSINESS_INFORMATION);
		PhoneNumber phoneNumber = MerchantUtil.getPhoneNumberObj(merchantDto, BUSINESS_INFORMATION);
		BusinessInformation businessInformation = MerchantUtil.getBusinessInformationObj(merchantDto, address1.getId(), phoneNumber.getId());
		Long businessInfoId = businessInformation.getId();

		// to get emailId
		String email = merchantDto.getEmailId();

		// to get phoneId
		PhoneNumber phoneNumber1 = MerchantUtil.getPhoneNumberObj(merchantDto, BUSINESS_INFORMATION);
		Long phoneId = phoneNumber1.getId();

		Merchant merchant = MerchantUtil.getMerchantObj(authenticationId, businessInfoId, businessOwnerInfoId, email, phoneId, merchantDto.getMerchantPayInfoId());
		Assert.assertNotNull(merchant);
	}

	@Test
	public void constructMerchantDtoWithDefaultVal() throws WalletException {
		//to get authentication object
		MerchantDto merchantDto1 = DataPreparation.getMerchantDto();
		Authentication authentication = new Authentication();
		Authentication authentication1 = MerchantUtil.getAuthenticationObj(merchantDto1, authentication);
        
		//to get authenticationId
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Long authenticationId = authentication1.getId();

		//to get businessOwnerInfoId
		Address address = MerchantUtil.getAddressObj(merchantDto, BUSINESS_OWNER_INFORMATION);
		PersonName personName = MerchantUtil.getPersonNameObj(merchantDto);
		BusinessOwnerInformation businessOwnerInformation = MerchantUtil.getBusinessOwnerInformationObj(personName.getId(), address.getId(), true);
		Long businessOwnerInfoId = businessOwnerInformation.getId();
		
        //to get businessInfoId
		Address address1 = MerchantUtil.getAddressObj(merchantDto, BUSINESS_INFORMATION);
		PhoneNumber phoneNumber = MerchantUtil.getPhoneNumberObj(merchantDto, BUSINESS_INFORMATION);
		BusinessInformation businessInformation = MerchantUtil.getBusinessInformationObj(merchantDto, address1.getId(), phoneNumber.getId());
		Long businessInfoId = businessInformation.getId();

        //to get email		
		String email = merchantDto.getEmailId();
		
		//to get phoneId
		PhoneNumber phoneNumber1 = MerchantUtil.getPhoneNumberObj(merchantDto, BUSINESS_INFORMATION);
		Long phoneId = phoneNumber1.getId();
	
		Merchant merchant = MerchantUtil.getMerchantObj(authenticationId, businessInfoId, businessOwnerInfoId, email, phoneId, merchantDto.getMerchantPayInfoId());
		MerchantDto merchantDto2 = MerchantUtil.constructMerchantDtoWithDefaultVal(merchantDto1, authentication1, merchant);
		Assert.assertNotNull(merchantDto2);
		Assert.assertNotNull(merchantDto2.isActive());

	}

	@Test
	public void dateToYYYYMMddTest() {
		Date date = new Date();
		String stringDate = MerchantUtil.dateToYYYYMMdd(date);
		Assert.assertNotNull(stringDate);
	}
}
