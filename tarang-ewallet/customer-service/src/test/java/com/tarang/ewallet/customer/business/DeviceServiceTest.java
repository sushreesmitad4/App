package com.tarang.ewallet.customer.business;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.business.LoginService;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.util.GlobalLitterals;


@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class DeviceServiceTest extends AbstractTransactionalJUnit4SpringContextTests{

private static final Logger LOG = Logger.getLogger(DeviceServiceTest.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private LoginService loginService;
	
	Long customerId = 0L;
	
	 @Test
	  public void mobileRegistrationTest() throws WalletException{
		LOG.info(" mobileRegistrationTest ");

		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		
		//do email verified and make him active and update it
		mailVarification(dto.getAuthenticationId());
		
		String simNumber = "123456789"; 
		String imeiNumber = "987654321";
		
		//do mobile registration and update msisdnNumber, simNumber, imeiNumber number  
		Boolean mobileRegFlag = commonService.mobileRegistration(dto.getEmailId(), 
				GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPassword(), dto.getPhoneNo(), simNumber, imeiNumber);
		LOG.info("Mobile Registration :: " + mobileRegFlag);
		Assert.assertEquals(mobileRegFlag, Boolean.TRUE);
	}

	@Test
	  public void mPinGenerationTest() throws WalletException{
		LOG.info(" mPinGenerationTest ");

		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		
		//do email verified and make him active and update it
		mailVarification(dto.getAuthenticationId());
		
		String simNumber = "123456789"; 
		String imeiNumber = "987654321";
		
		//do mobile registration and update msisdnNumber, simNumber, imeiNumber number  
		Boolean mobileRegFlag = commonService.mobileRegistration(dto.getEmailId(), 
				GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPassword(), dto.getPhoneNo(), simNumber, imeiNumber);
		LOG.info("Mobile Registration :: " + mobileRegFlag);
		Assert.assertEquals(mobileRegFlag, Boolean.TRUE);
		
		String mPin = "1234";
		String personName = dto.getFirstName() + " " + dto.getLastName();
		String userTypeName  = "customer";
		 
		Boolean mPinGenFlag = commonService.mPinGeneration(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPhoneNo(),simNumber,imeiNumber, mPin, personName, userTypeName);
		LOG.info("mPin Generation :: " + mPinGenFlag);
		Assert.assertEquals(mPinGenFlag, Boolean.TRUE);
		
	 }
	 
	 @Test
	 public void getDeviceLoginAuthenticationTest() throws WalletException{
		LOG.info(" getDeviceLoginAuthenticationTest ");

		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		
		//do email verified and make him active and update it
		mailVarification(dto.getAuthenticationId());
		
		String simNumber = "123456789"; 
		String imeiNumber = "987654321";
		
		//do mobile registration and update msisdnNumber, simNumber, imeiNumber number  
		Boolean mobileRegFlag = commonService.mobileRegistration(dto.getEmailId(), 
				GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPassword(), dto.getPhoneNo(), simNumber, imeiNumber);
		LOG.info("Mobile Registration :: " + mobileRegFlag);
		Assert.assertEquals(mobileRegFlag, Boolean.TRUE);
		
		String mPin = "1234";
		String personName = dto.getFirstName() + " " + dto.getLastName();
		String userTypeName  = "customer";
		 
		Boolean mPinGenFlag = commonService.mPinGeneration(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPhoneNo(),simNumber,imeiNumber, mPin, personName, userTypeName);
		LOG.info("mPin Generation :: " + mPinGenFlag);
		Assert.assertEquals(mPinGenFlag, Boolean.TRUE);
		
		Long typeOfRequest = 2L;
	 
		Authentication authResponse = commonService.getDeviceLoginAuthentication(dto.getEmailId(), mPin, GlobalLitterals.CUSTOMER_USER_TYPE, typeOfRequest);
		System.out.println("Device Login Authentication Object ::" + authResponse);
		 
		Assert.assertNotNull(authResponse.getId());
		Assert.assertEquals(authResponse.isLoginStatus(), Boolean.TRUE);
		LOG.info("End Of LoginTest " + authResponse);
	 }
	 
	 @Test
	 public void validateMpinTest() throws WalletException{ 
		LOG.info(" validateMpinTest ");

		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		
		//do email verified and make him active and update it
		mailVarification(dto.getAuthenticationId());
		
		String simNumber = "123456789"; 
		String imeiNumber = "987654321";
		
		//do mobile registration and update msisdnNumber, simNumber, imeiNumber number  
		Boolean mobileRegFlag = commonService.mobileRegistration(dto.getEmailId(), 
				GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPassword(), dto.getPhoneNo(), simNumber, imeiNumber);
		LOG.info("Mobile Registration :: " + mobileRegFlag);
		Assert.assertEquals(mobileRegFlag, Boolean.TRUE);
		
		String mPin = "1234";
		String personName = dto.getFirstName() + " " + dto.getLastName();
		String userTypeName  = "customer";
		 
		Boolean mPinGenFlag = commonService.mPinGeneration(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPhoneNo(),simNumber,imeiNumber, mPin, personName, userTypeName);
		LOG.info("mPin Generation :: " + mPinGenFlag);
		Assert.assertEquals(mPinGenFlag, Boolean.TRUE);
		
		Long typeOfRequest = 2L;
	 
		Authentication authResponse = commonService.getDeviceLoginAuthentication(dto.getEmailId(), mPin, GlobalLitterals.CUSTOMER_USER_TYPE, typeOfRequest);
		System.out.println("Device Login Authentication Object ::" + authResponse);
		Assert.assertNotNull(authResponse.getId());
		Assert.assertEquals(authResponse.isLoginStatus(), Boolean.TRUE);
			 
		 
		Boolean validMpinFlag = commonService.validateMpin(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, mPin);
		LOG.info("Is Valid Mpin Flag " + validMpinFlag);
		Assert.assertEquals(validMpinFlag, Boolean.TRUE);
	 }
	
	 @Test
	 public void deActivateDeviceTest() throws WalletException{
		LOG.info(" deActivateDeviceTest ");

		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		
		//do email verified and make him active and update it
		mailVarification(dto.getAuthenticationId());
		
		String simNumber = "123456789"; 
		String imeiNumber = "987654321";
		
		//do mobile registration and update msisdnNumber, simNumber, imeiNumber number  
		Boolean mobileRegFlag = commonService.mobileRegistration(dto.getEmailId(), 
				GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPassword(), dto.getPhoneNo(), simNumber, imeiNumber);
		LOG.info("Mobile Registration :: " + mobileRegFlag);
		Assert.assertEquals(mobileRegFlag, Boolean.TRUE);
		
		String mPin = "1234";
		String personName = dto.getFirstName() + " " + dto.getLastName();
		String userTypeName  = "customer";
		 
		Boolean mPinGenFlag = commonService.mPinGeneration(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPhoneNo(),simNumber,imeiNumber, mPin, personName, userTypeName);
		LOG.info("mPin Generation :: " + mPinGenFlag);
		Assert.assertEquals(mPinGenFlag, Boolean.TRUE);
		
		Long typeOfRequest = 2L;
	 
		Authentication authResponse = commonService.getDeviceLoginAuthentication(dto.getEmailId(), mPin, GlobalLitterals.CUSTOMER_USER_TYPE, typeOfRequest);
		System.out.println("Device Login Authentication Object ::" + authResponse);
		Assert.assertNotNull(authResponse.getId());
		Assert.assertEquals(authResponse.isLoginStatus(), Boolean.TRUE);
			 
		 
		Boolean validMpinFlag = commonService.validateMpin(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, mPin);
		LOG.info("Is Valid Mpin Flag " + validMpinFlag);
		Assert.assertEquals(validMpinFlag, Boolean.TRUE);	
		 
		Boolean deactivateDevice = commonService.deActivateDevice(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, personName, userTypeName);
		Assert.assertEquals(deactivateDevice, Boolean.TRUE);
		LOG.info("Deactivate Device Test End " + deactivateDevice);
	 }
	 
	 @Test
		public void forgotMpinTest() throws WalletException{
		 CustomerDto dto = DataPreparation.getCustomerDto();
		 dto = customerService.newregistration(dto);
		 Authentication authentication = commonService.getAuthentication(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
		
		 authentication.setActive(true);
		 authentication.setEmailVarification(true);
		 authentication.setBlocked(false);
		 authentication.setStatus(1L);
		 
		 String imeiNumber = "3333333333";
		 String simNumber = "2222222222";
		 String mPin = "1234";
		 String personName = dto.getFirstName() + " " + dto.getLastName();
		 String userTypeName  = "customer";
		 
		 Boolean activateDevice = commonService.mobileRegistration(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPassword(), dto.getPhoneNo(), simNumber, imeiNumber);
		 LOG.info("Mobile Registration Done " + activateDevice);
		 Assert.assertEquals(activateDevice, Boolean.TRUE);
		 
		 
		 Boolean generateMpin = commonService.mPinGeneration(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPhoneNo(), simNumber, imeiNumber, mPin, personName, userTypeName);
		 LOG.info("MPin Generation Done " + generateMpin);
		 Assert.assertEquals(generateMpin, Boolean.TRUE);
		 
		  Long question = 1L;
		  String answer = "answer1";
		  	  
		  Boolean forgotMpinFlag = loginService.forgotMpin(dto.getEmailId(),GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPhoneNo(), simNumber, imeiNumber, question, answer, personName, userTypeName);
		  Assert.assertEquals(forgotMpinFlag, Boolean.TRUE);
		  LOG.info("Forgot MPin Test End; " + forgotMpinFlag);
		  
		  mailVarification(dto.getAuthenticationId());
			
}
	 
	 private void mailVarification(Long id) throws WalletException{
			LOG.debug( " emailStatusUpdate " );
			final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
			Authentication authentication = commonService.getAuthentication(id);
			Date creationDate=authentication.getCreationDate();
			Date todayDate=new Date();
			Integer diffInDays = (int) ((creationDate.getTime() - todayDate.getTime())/ DAY_IN_MILLIS );
			if(diffInDays==0){
				authentication.setEmailVarification(true);
				authentication.setActive(true);
				commonService.updateAuthentication(authentication);
			}
			else{
				LOG.debug("email verification failed");
			}
	}

	
}
