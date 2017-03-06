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
import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.ForgotPasswordDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.util.GlobalLitterals;



@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
	"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class LoginServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	
	private static final Logger LOG = Logger.getLogger(LoginServiceTest.class);
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CustomerService customerService;
	
	@Test(expected=WalletException.class)
	public void loginIfEmailNotVarifiedTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();
		
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		
		Authentication loginAuth=loginService.login(dto.getEmailId(), dto.getPassword(), GlobalLitterals.CUSTOMER_USER_TYPE, 2L);
		Assert.assertNull(loginAuth);
	}
	
	@Test
	public void loginTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		Assert.assertNotNull( dto.getAuthenticationId() );
		/*varify the register customer*/
		mailVarification(dto.getAuthenticationId());
		
		Authentication loginAuth=loginService.login(dto.getEmailId(), dto.getPassword(), GlobalLitterals.CUSTOMER_USER_TYPE, 2L);
		Assert.assertNotNull(loginAuth);
		Assert.assertNotNull( loginAuth.getId() );
		Assert.assertEquals(dto.getEmailId(), loginAuth.getEmailId());
		
	}
	@Test
	public void forgotPasswordTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		mailVarification(dto.getAuthenticationId());
		ForgotPasswordDto forgotPassDto = DataPreparation.prepareForgotPasswordDto();
		Boolean b = loginService.forgotPassword(forgotPassDto, "Ram");
		Assert.assertTrue(b);
	}
	
	@Test
	public void logoutTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		loginService.logout(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
	}
	
	@Test
	public void changePasswordTest()throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		Assert.assertEquals("aaaa1A@", dto.getPassword());
		
		ChangePasswordDto changePasswordDto = DataPreparation.prepareChangePasswordDto();
		Boolean flag=loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);
	}
	
	public void mailVarification(Long id) throws WalletException{
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

	@Test
	public void changePassword1Test() throws Exception {
		CustomerDto dto = DataPreparation.getCustomerDto();
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		Assert.assertEquals("aaaa1A@", dto.getPassword());
		
		ChangePasswordDto changePasswordDto = DataPreparation.prepareChangePasswordDto();

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(dto.getPassword());
		changePasswordDto.setNewPassword("Kedar@321");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		Boolean flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);
		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("xyz@321");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("abcef123");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("efeash545");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("admin@123");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

	}

	@Test (expected=WalletException.class)
	public void changePassword2Test() throws Exception {
		CustomerDto dto = DataPreparation.getCustomerDto();
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		Assert.assertEquals("aaaa1A@", dto.getPassword());
		
		ChangePasswordDto changePasswordDto = DataPreparation.prepareChangePasswordDto();

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(dto.getPassword());
		changePasswordDto.setNewPassword("Kedar@321");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		Boolean flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);
		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("xyz@321");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("aaaa1A@");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("efeash545");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("admin@123");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

	}

	@Test (expected=WalletException.class)
	public void changePassword3Test() throws Exception {
		CustomerDto dto = DataPreparation.getCustomerDto();
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		Assert.assertEquals("aaaa1A@", dto.getPassword());
		
		ChangePasswordDto changePasswordDto = DataPreparation.prepareChangePasswordDto();

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(dto.getPassword());
		changePasswordDto.setNewPassword("Kedar@321");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		Boolean flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);
		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("xyz@321");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("abcef123");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("efeash545");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);

		changePasswordDto.setEmailId(dto.getEmailId());
		changePasswordDto.setOldPassword(changePasswordDto.getNewPassword());
		changePasswordDto.setNewPassword("aaaa1A@");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);

		flag = loginService.changePassword(changePasswordDto);
		Assert.assertTrue(flag);
     }
	
	@Test
	public void loginWithDeviceTest() throws Exception {
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
		
		String mPin = "1234";
		String personName = dto.getFirstName() + " " + dto.getLastName();
		String userTypeName  = "customer"; 
		
		Boolean mPinGenFlag = commonService.mPinGeneration(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, dto.getPhoneNo(),simNumber,imeiNumber, mPin, personName, userTypeName);
		LOG.info("mPin Generation :: " + mPinGenFlag);
		Assert.assertEquals(mPinGenFlag, Boolean.TRUE);
		
		Authentication authentication = null;
		authentication = loginService.loginWithDevice(dto.getEmailId(), "1234", GlobalLitterals.CUSTOMER_USER_TYPE, 2L);
		Assert.assertEquals(authentication.getUserType(), GlobalLitterals.CUSTOMER_USER_TYPE);
		Assert.assertNotNull(authentication);
	}
	 @Test
	 public void getPhoneNumberByEmailTest() throws WalletException {
	  CustomerDto dto = DataPreparation.getCustomerDto();		
	  dto = customerService.newregistration(dto);
	  Assert.assertNotNull(dto);	
	  PhoneNumber phoneNumber = commonService.getPhoneNumber(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
	  Assert.assertNotNull(phoneNumber);
	  Assert.assertEquals(dto.getPhoneNo(), phoneNumber.getPnumber());
	}
}
