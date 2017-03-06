package com.tarang.ewallet.common.business;



import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.dto.FeedbackDto;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.model.Hints;
import com.tarang.ewallet.model.MasterAmountWallet;
import com.tarang.ewallet.model.MasterFeeWallet;
import com.tarang.ewallet.model.MasterTaxWallet;
import com.tarang.ewallet.model.NonRegisterWallet;
import com.tarang.ewallet.model.PasswordHistory;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.model.Preferences;
import com.tarang.ewallet.model.UserIP;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;


/**
* @author  : prasadj
* @date    : Oct 17, 2012
* @time    : 8:43:37 AM
* @version : 1.0.0
* @comments: 
*
*/
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class CommonServicesTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private CommonService commonService;

	@Autowired
	private HibernateTemplate hibernateTemplate;
	    
	@Test
	public void createAddressTest() throws WalletException{
		Address address = getAddressData();
		address = commonService.createAddress(address);
		Assert.assertNotNull( address.getId() );
	}
	
	@Test
	public void createPersonNameTest() throws WalletException{
		PersonName pname= getPersonNameData();
		pname = commonService.createPersonName(pname);		
		Assert.assertNotNull( pname.getId() );
	}
	
	@Test
	public void createHintsTest() throws WalletException{
		Hints hints = getHintsData();
		hints = commonService.createHints(hints);
		Assert.assertNotNull( hints.getId() );
	}
	
	
	@Test
	public void createPhoneNumberTest() throws WalletException{
		PhoneNumber pnum = getPhoneNumberData();
		pnum = commonService.createPhone(pnum);
		Assert.assertNotNull( pnum.getId() );
	}
	
	
	@Test
	public void getAddressTest() throws WalletException {
		Address address = getAddressData();
		address = commonService.createAddress(address);
		address = commonService.getAddress(address.getId());
		Assert.assertNotNull(address);
		Assert.assertEquals(new Long(1), address.getCountry());
	}
	
	@Test
	public void getPersonNameTest() throws WalletException {
		PersonName pname = getPersonNameData();
		pname = commonService.createPersonName(pname);		
		pname = commonService.getPersonName(pname.getId());
		Assert.assertNotNull(pname);
		Assert.assertEquals("Firstname", pname.getFirstName());
	}

	@Test
	public void getHintsTest() throws WalletException {
		Hints hints = getHintsData();
		hints = commonService.createHints(hints);
		hints = commonService.getHints(hints.getId());
		Assert.assertNotNull(hints);
		Assert.assertEquals("answerone", hints.getHintAnswer1());
	}

	@Test
	public void getPhoneNumberTest() throws WalletException {
		PhoneNumber pnum = getPhoneNumberData();
		pnum = commonService.createPhone(pnum);
		pnum = commonService.getPhone(pnum.getId());
		Assert.assertNotNull(pnum);
		Assert.assertEquals("9492768765", pnum.getPnumber());
	}
	
	@Test
	public void creteAuthenticationTest() throws WalletException {
		Authentication authentication = getAuthenticationData();
		Authentication auth = commonService.createAuthentication(authentication);
		Assert.assertNotNull( auth.getId() );
	}

	@Test
	public void deleteAuthenticationTest() throws WalletException {
		Authentication authentication = getAuthenticationData();
		Authentication auth = commonService.createAuthentication(authentication);
		Assert.assertNotNull( auth.getId() );
		Boolean status = commonService.deleteAuthentication(auth.getId());
		Assert.assertNotNull( status );
	}
	
	@Test
	public void updateAddressTest() throws WalletException {
		Address address = getAddressData();
		Address returnAdd = commonService.createAddress(address);
		Long addressId = returnAdd.getId();
		Address updateAdd = commonService.getAddress(addressId);
		updateAdd.setAddressOne("UpdateCheckAddressOne");
		updateAdd.setAddressTwo("UpdateCheckAddressTwo");
		updateAdd.setCity("LKO");
		updateAdd.setCountry(1l);
		updateAdd.setZipcode("208023");
		commonService.updateAddress(updateAdd);
		Assert.assertNotNull(updateAdd.getId());
	}
	

	@Test
	public void updatePersonNameTest()throws WalletException{
		PersonName personName = getPersonNameData();
		PersonName savedPersonName = commonService.createPersonName(personName);
		Long savedPerNameId=savedPersonName.getId();
		PersonName updatePersonName = commonService.getPersonName(savedPerNameId);
		updatePersonName.setFirstName("UpdatePersonFirstName");
		updatePersonName.setLastName("UpdatePersonLastName");
		commonService.updatePersonName(updatePersonName);
		Assert.assertNotNull(updatePersonName.getId());
	}
 
	@Test
	public void getAuthenticationTest()throws WalletException{
		Authentication authentication = getAuthenticationData();
		Authentication savedAuthen = commonService.createAuthentication(authentication);
		Long savedAuthenId = savedAuthen.getId();
		Assert.assertNotNull(savedAuthenId);
		Authentication getAuth = commonService.getAuthentication(savedAuthenId);
		Assert.assertNotNull(getAuth.getId());		
	}

	@Test
	public void updateAuthenticationTest()throws WalletException {
		Authentication authentication = getAuthenticationData();
		Authentication savedAuthen = commonService.createAuthentication(authentication);
		Authentication updateAuht = commonService.getAuthentication(savedAuthen.getId());
		updateAuht = getUpdateAuthData(updateAuht);
		commonService.updateAuthentication(updateAuht);
		Assert.assertNotNull(updateAuht.getId());
	}
	
	
	@Test
	public void updatePhoneTest() throws WalletException {
		PhoneNumber phoneNumber = getPhoneNumberData();
		phoneNumber = commonService.createPhone(phoneNumber);
		Assert.assertNotNull(phoneNumber.getId());
		PhoneNumber phno = commonService.getPhone(phoneNumber.getId());
		phno.setCode("3");
		phno.setPnumber("452136987");
		Assert.assertNotNull(phno.getId());
		commonService.updatePhone(phoneNumber);
	}
	
	@Test
	public void updateHintsTest() throws WalletException {
		Hints hints = getHintsData();
		hints = commonService.createHints(hints);
		Assert.assertNotNull( hints.getId());
		Hints updateHints = commonService.getHints(hints.getId());
		updateHints.setHintQuestion1(3l);
		updateHints.setHintAnswer1("UpdateAnswerOne..");
		Assert.assertNotNull(updateHints.getId());
	}
	
	@Test
	public void getAuthenticationByEmailIdTest() throws WalletException{
		Authentication authentication = getAuthenticationData();
		Authentication createAuth = commonService.createAuthentication(authentication);
		Assert.assertNotNull(createAuth.getId());
		Authentication sendAuth = commonService.getAuthentication(createAuth.getId());
		Authentication authEmailId = commonService.getAuthentication(createAuth.getEmailId(), createAuth.getUserType());
		Authentication emailFlag = commonService.getAuthentication(createAuth.getEmailId());
		Assert.assertNotNull(authEmailId);
		Assert.assertNotNull(emailFlag);
		Assert.assertEquals(createAuth.getUserType(), sendAuth.getUserType());
		Assert.assertEquals(createAuth.getEmailId(), sendAuth.getEmailId()); 
		
		Authentication authByEmailId = commonService.getAuthentication(createAuth.getEmailId());
		Assert.assertNotNull(authByEmailId);
		Assert.assertEquals(createAuth.getUserType(), sendAuth.getUserType());
		Assert.assertEquals(createAuth.getEmailId(), sendAuth.getEmailId());   
		  
	}

	@Test
	public void resetPasswordTest() throws WalletException{
		Authentication authentication = getAuthenticationData();
		Authentication savedAuthen = commonService.createAuthentication(authentication);
		Authentication resetAuthe = commonService.getAuthentication(savedAuthen.getId());
		Boolean resetPaas = commonService.resetPassword(resetAuthe.getEmailId(), resetAuthe.getUserType(), 1L, "firstName");
		Assert.assertTrue(resetPaas);
	}
	
	@Test
	public void emailIdExistTest() throws WalletException{
		Authentication authentication = getAuthenticationData();
		Authentication savedAuthen = commonService.createAuthentication(authentication);
		
		Assert.assertFalse(savedAuthen.isEmailVarification());
		
		Long flag = commonService.emailIdExist(savedAuthen.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
		System.out.println(flag);
		Assert.assertNotNull(flag);
		Assert.assertEquals(new Long(2), flag);
		
		boolean emailFlag = commonService.emailIdExist("abc@tarangtech.com");
		Assert.assertNotNull(emailFlag);
		Assert.assertEquals(emailFlag, Boolean.FALSE);
	}
	
	@Test
	public void phoneNOExistTest() throws WalletException {
		PhoneNumber pnum = new PhoneNumber();
		pnum.setCode("87");
		pnum.setPnumber("9492768765");
		pnum = commonService.createPhone(pnum);
		Assert.assertNotNull(pnum.getId());
		Boolean flag = commonService.phoneNOExist(pnum.getCode(), pnum.getPnumber());
		Assert.assertTrue(flag);
	}
	
		
	@Test
	public void sendForgotPasswordMailTest() throws WalletException{
		Authentication authentication = getAuthenticationData();
		Authentication savedAuthen = commonService.createAuthentication(authentication);
		Assert.assertNotNull(savedAuthen.getId());
		Authentication forAuthe = commonService.getAuthentication(savedAuthen.getId());
		commonService.sendForgotPasswordMail(forAuthe.getEmailId(),forAuthe.getPassword(),forAuthe.getUserType(), 1L,"ram");
	}

	@Test
	public void sendAdminRegistrationMailTest() throws WalletException{
		Authentication authentication = getAuthenticationData();
		Authentication savedAuthen = commonService.createAuthentication(authentication);
		Assert.assertNotNull(savedAuthen.getId());
		Authentication forAuthe = commonService.getAuthentication(savedAuthen.getId());
		commonService.sendAdminRegistrationMail("Admin", forAuthe.getEmailId(),forAuthe.getPassword(),forAuthe.getUserType(), 1L);
	}
	
	@Test
	public void validateUserSessionTest() throws WalletException{
		Authentication authentication = getAuthenticationData();
		Authentication savedAuthen = commonService.createAuthentication(authentication);
		Assert.assertNotNull(savedAuthen.getId());
		Authentication forAuthe = commonService.getAuthentication(savedAuthen.getId());
		commonService.validateUserSession(forAuthe.getId(),forAuthe.getUserType());
	}
	
	@Test
	public void createFeedbackTest() throws WalletException{
		FeedbackDto feedbackDto = new FeedbackDto();
		feedbackDto.setQuerryType(1L);
		feedbackDto.setSubject("subject");
		feedbackDto.setMessage("message");
		feedbackDto.setDateAndTime(new Date());
		feedbackDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);
		feedbackDto.setUserId(1l);
		feedbackDto.setUserMail("natarajac@tarangtech.com");
		Feedback  feedback = commonService.createFeedback(feedbackDto);
		System.out.println("feedback id : "+feedback.getId());
		Assert.assertNotNull( feedback.getId());
	}
	@Test
	public void createFeedbackTest2() throws WalletException{
		FeedbackDto feedbackDto = new FeedbackDto();
		feedbackDto.setQuerryType(1L);
		feedbackDto.setSubject("subject1");
		feedbackDto.setMessage("message2");
		feedbackDto.setDateAndTime(new Date());
		feedbackDto.setUserType(GlobalLitterals.MERCHANT_USER_TYPE);
		feedbackDto.setUserId(1l);
		feedbackDto.setUserMail("rajasekhar@tarangtech.com");
		Feedback  feedback = commonService.createFeedback(feedbackDto);
		Assert.assertEquals("subject1", feedback.getSubject());
	}
	@Test
	public void createFeedbackTest3() throws WalletException{
		FeedbackDto feedbackDto = new FeedbackDto();
		feedbackDto.setQuerryType(1L);
		feedbackDto.setSubject("subject3");
		feedbackDto.setMessage("message3");
		feedbackDto.setDateAndTime(new Date());
		feedbackDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);
		feedbackDto.setUserId(1l);
		feedbackDto.setUserMail("raj@tarangtech.com");
		Feedback  feedback = commonService.createFeedback(feedbackDto);
		System.out.println("feedback id : "+feedback.getId());
		Assert.assertNotNull(feedback.getId());
	}
	
	@Test
	public void createUserWalletTest() throws WalletException{
		UserWallet userWallet = null;
		userWallet = commonService.createUserWallet(1L, 1L);
		Assert.assertNotNull(userWallet.getId());
	}
	
	@Test
	public void updateUserWalletTest() throws WalletException{
		UserWallet userWallet = null;
		userWallet = commonService.createUserWallet(1L, 1L);
		Assert.assertNotNull(userWallet.getId());
		Assert.assertEquals(new Double(0.0), userWallet.getAmount());
		userWallet.setAmount(10000.00);
		userWallet = commonService.updateUserWallet(userWallet);
		Assert.assertNotNull(userWallet.getId());
		Assert.assertEquals(new Double(10000.00), userWallet.getAmount());
	}
	
	@Test
	public void getUserWalletTest() throws WalletException{
		UserWallet userWallet = null;
		userWallet = commonService.createUserWallet(1L, 1L);
		Assert.assertNotNull(userWallet.getId());
		
		userWallet = commonService.getUserWallet(1L, 1L);
		Assert.assertNotNull(userWallet.getId());
	}
	
	@Test
	public void createNonRegisterWalletTest() throws WalletException{
		NonRegisterWallet nonRegisterWallet = DataPreparation.getNonRegisterWallet();
		nonRegisterWallet = commonService.createNonRegisterWallet(nonRegisterWallet);
		Assert.assertNotNull(nonRegisterWallet.getId());
	}
	
	@Test
	public void getNonRegisterWalletTest() throws WalletException{
		NonRegisterWallet nonRegisterWallet = DataPreparation.getNonRegisterWallet();
		nonRegisterWallet = commonService.createNonRegisterWallet(nonRegisterWallet);
		Assert.assertNotNull(nonRegisterWallet.getId());
		
		List<NonRegisterWallet> newTempws = commonService.getMoneyFromTemporaryWallet(nonRegisterWallet.getEmail());
		Assert.assertNotNull(newTempws);
		Assert.assertNotNull(newTempws.get(0).getId());
		Assert.assertEquals(nonRegisterWallet.getEmail(), newTempws.get(0).getEmail());
	}
	
	@Test
	public void updateNonRegisterWalletTest() throws WalletException{
		NonRegisterWallet nonRegisterWallet = DataPreparation.getNonRegisterWallet();
		nonRegisterWallet = commonService.createNonRegisterWallet(nonRegisterWallet);
		Assert.assertNotNull(nonRegisterWallet.getId());
		
		List<NonRegisterWallet> newTempws = commonService.getMoneyFromTemporaryWallet(nonRegisterWallet.getEmail());
		Assert.assertNotNull(newTempws);
		Assert.assertNotNull(newTempws.get(0).getId());
		NonRegisterWallet nonRegister = newTempws.get(0);
		nonRegister.setRegister(2L);
		commonService.updateTemporaryWalletRecord(nonRegister);
	}
	
	@Test
	public void sendsendReloadMoneyEmailTest() throws WalletException{
		commonService.sendReloadMoneyEmail("kedar", "kedarnathd@tarangtech.com", "10000", "USD", 1L);
	}
	
	@Test
	public void getPreferencesTest1()throws WalletException{
		 PreferencesDto preferencesDto = new PreferencesDto();
	     preferencesDto.setId(73L);
	     preferencesDto.setAuthentication(87L);
	     preferencesDto.setCurrency(2L);
	     preferencesDto.setLanguage(3L);
		 commonService.createPreferences(preferencesDto);
		 
		 PreferencesDto preferencesDto1 = commonService.getPreferences(87L);
		 Assert.assertNotNull(preferencesDto1);
		
	}
	
	@Test
	public void getPreferencesTest2()throws WalletException{
		 PreferencesDto preferencesDto=new PreferencesDto();
	     preferencesDto.setId(73L);
	     preferencesDto.setAuthentication(87L);
	     preferencesDto.setCurrency(2L);
	     preferencesDto.setLanguage(3L);
		 commonService.createPreferences(preferencesDto);
		 
		 PreferencesDto preferencesDto1 = commonService.getPreferences(87L);
		 Assert.assertEquals(preferencesDto.getCurrency(),preferencesDto1.getCurrency());
	}
	
	@Test
	public void updatePreferencesTest() throws WalletException{
		PreferencesDto preferencesDto = new PreferencesDto();
	     preferencesDto.setId(73L);
	     preferencesDto.setAuthentication(87L);
	     preferencesDto.setCurrency(2L);
	     preferencesDto.setLanguage(3L);
		 commonService.createPreferences(preferencesDto);
		 
		 PreferencesDto preferencesDto1 = new PreferencesDto();
	     preferencesDto1.setId(73L);
	     preferencesDto1.setAuthentication(87L);
	     preferencesDto1.setCurrency(1L);
	     preferencesDto1.setLanguage(3L);
	     commonService.updatePreferences(preferencesDto1);	 
	}
	
	@Test
	public void saveUserIPAddressTest() throws WalletException{
		UserIP userIP = commonService.saveUserIPAddress(new UserIP(1L, "192.169.32.65", new Date(), null));
		Assert.assertNotNull(userIP.getId());
	}
	
	@Test
	public void existUserIPAddressTest() throws WalletException{
		UserIP userIP = commonService.saveUserIPAddress(new UserIP(1L, "192.169.32.65", new Date(), null));
		UserIP userIpTwo = commonService.getUserIPAddress(userIP.getAuthId(), userIP.getIpAddress());
		Assert.assertNotNull(userIpTwo);
		Assert.assertEquals(new Long(1), userIpTwo.getAuthId());
		
		userIpTwo = commonService.getUserIPAddress(userIP.getAuthId(), userIP.getIpAddress()+"100");
		Assert.assertEquals(null, userIpTwo);
	}
	
	@Test
	public void sendOTPToUserTest() throws WalletException{
		
		Authentication authentication = getAuthenticationData();
		Authentication createAuth = commonService.createAuthentication(authentication);
		Assert.assertNotNull(createAuth.getId());
		
		UserIP userIP = commonService.sendOTPToUser(new UserIP(createAuth.getId(), "192.169.32.129", new Date(), null), 1L, "Kedar nath");
		Assert.assertNotNull(userIP);
		Assert.assertNotNull(userIP.getAuthId());
		Assert.assertNotNull(userIP.getIpAddress());
		
		UserIP userIpTwo = commonService.getUserIPAddress(userIP.getAuthId(), userIP.getIpAddress());
		Assert.assertNotNull(userIpTwo);
		Assert.assertEquals(createAuth.getId(), userIpTwo.getAuthId());
	}
	
	@Test
	public void updateUserIPAddressTest() throws WalletException{
		
		Authentication authentication = getAuthenticationData();
		Authentication createAuth = commonService.createAuthentication(authentication);
		Assert.assertNotNull(createAuth.getId());
		
		UserIP userIP = commonService.saveUserIPAddress(new UserIP(1L, "192.169.32.65", new Date(), null));
		Assert.assertNotNull(userIP);
		Assert.assertNotNull(userIP.getAuthId());
		Assert.assertNotNull(userIP.getIpAddress());
		
		UserIP userIpTwo = commonService.getUserIPAddress(userIP.getAuthId(), userIP.getIpAddress());
		Assert.assertNotNull(userIpTwo);
		Assert.assertEquals(new Long(1), userIpTwo.getAuthId());
		
		userIpTwo.setCode(null);
		UserIP userIpThree = commonService.updateUserIPAddress(userIpTwo);
		Assert.assertNotNull(userIpThree);
		Assert.assertNull(userIpThree.getCode());
	}
	
	private Authentication getAuthenticationData(){
		Authentication authentication=new Authentication();
		authentication.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);
		authentication.setEmailId("kedarnathd@tarangtech.com");
		authentication.setPassword("aaaa1A@");
		authentication.setHints(666L);
		authentication.setResetPassword(new Boolean("True"));
		authentication.setLastLogin(new Date());
		authentication.setActive(new Boolean("true"));
		authentication.setBlocked(new Boolean("false"));
		authentication.setAttempts(2);
		authentication.setStatus(4L);
		authentication.setEmailVarification(new Boolean("false"));
		authentication.setLoginStatus(new Boolean("true"));
		authentication.setKycRequired(new Boolean("false"));
		authentication.setCreationDate(new Date());
		authentication.setTypeOfRequest(2L);
		authentication.setMsisdnNumber("963258741");
		return authentication;
	}
	
	private Authentication getUpdateAuthData(Authentication authentication){
		authentication.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);
		authentication.setEmailId("yasina@tarangtech.com");
		authentication.setPassword("tArAnG@321");
		authentication.setHints(777L);
		authentication.setResetPassword(new Boolean("false"));
		authentication.setLastLogin(new Date());
		authentication.setActive(new Boolean("false"));
		
		authentication.setBlocked(new Boolean("false"));
		authentication.setAttempts(2);
		authentication.setStatus(4L);
		authentication.setEmailVarification(new Boolean("false"));
		authentication.setLoginStatus(new Boolean("true"));
		authentication.setKycRequired(new Boolean("True"));
		authentication.setCreationDate(new Date());
		return authentication;
	}
	
	private Address getAddressData(){
		Address address = new Address();
		address.setAddressOne("Address one");
		address.setAddressTwo("Address two");
		address.setCity("Bangalore");
		address.setRegion(1L);
		address.setCountry(1L);
		address.setZipcode("xyz");
		return address;
	}
	
	private PersonName getPersonNameData(){
		PersonName pname = new PersonName();
		pname.setFirstName("Firstname");
		pname.setLastName("Lastname");		
		return pname;
	}
	
	private Hints getHintsData(){
		Hints hints = new Hints();
		hints.setHintQuestion1(1L);
		hints.setHintAnswer1("answerone");
		return hints;
	}
	
	private PhoneNumber getPhoneNumberData(){
		PhoneNumber pnum = new PhoneNumber();
		pnum.setCode("90");
		pnum.setPnumber("9492768765");
		return pnum;
	}
	//test cases for Preferences
		@Test
		public void createPreferencesTest()throws WalletException{
		     PreferencesDto preferencesDto = new PreferencesDto();
		     preferencesDto.setId(73L);
		     preferencesDto.setAuthentication(87L);
		     preferencesDto.setCurrency(2L);
		     preferencesDto.setLanguage(3L);
			Preferences preferences = commonService.createPreferences(preferencesDto);
			Assert.assertNotNull(preferences);
			
		}
		
		@Test
		public void updateIpAddressCheckTest() throws WalletException{
			Authentication authentication = getAuthenticationData();
			Authentication auth=commonService.createAuthentication(authentication);
			Integer result = commonService.updateIpAddressCheck(auth.getId(), true);
			System.out.println(result+ "  RECORD(S) UPDATED");
			Assert.assertNotNull(result);
		}
		
		@Test
		public void updateEmailPatternCheckTest() throws WalletException{
			Authentication authentication = getAuthenticationData();
			Authentication auth=commonService.createAuthentication(authentication);
			Integer result = commonService.updateEmailPatternCheck(auth.getId(), true);
			System.out.println(result+ "  RECORD(S) UPDATED");
			Assert.assertNotNull(result);
		}
		
		@Test
		public void updateChargeBackCheckTest() throws WalletException{
			Authentication authentication = getAuthenticationData();
			Authentication auth=commonService.createAuthentication(authentication);
			Integer result = commonService.updateChargeBackCheck(auth.getId(), true);
			System.out.println(result+ "  RECORD(S) UPDATED");
			Assert.assertNotNull(result);
		}
		
		@Test
		public void feedbackreplyTest() throws WalletException{
			Feedback feedback = DataPreparation.getFeedbackData();
			commonService.sendMailForFeedbackReply(feedback);
		}
		
		@Test
		public void testSendEmailNotificationToMobileUser() throws WalletException{
			Authentication authentication = getAuthenticationData();
			Authentication savedAuthen = commonService.createAuthentication(authentication);
			Assert.assertNotNull(savedAuthen.getId());
			Authentication forAuthe = commonService.getAuthentication(savedAuthen.getId());
			commonService.sendEmailNotificationToMobileUser("customerUser", "Customer", forAuthe.getId(), 1L, 
					EmailServiceConstants.MOBILE_WALLET_UNBLOCK_CONFIRMATION_ACTION_TAKENBY_SYSTEM);
		}    
		
		@Test
		public void authenticationTest() throws WalletException{
			Authentication authentication = getAuthenticationData();
			Assert.assertNotNull(authentication);
			Authentication auth = commonService.createAuthentication(authentication);
			Assert.assertNotNull(auth.getId());
			Long typeOfRequest = 2L;
			auth.setActive(Boolean.TRUE);
			auth.setBlocked(Boolean.FALSE);
			auth.setEmailVarification(Boolean.TRUE);
			auth.setLastLogin(new Date());
			auth.setLoginStatus(Boolean.FALSE);
			authentication.setPassword("vzkAm+7GR2FivSLHH1QMng==");
			String password="aaaa1A@";
			authentication = commonService.getAuthentication(auth.getEmailId(), 
					password, auth.getUserType(), typeOfRequest);
			Assert.assertNotNull(authentication);
			Assert.assertNotNull(auth.getId());
		}
		
    @Test
	public void deletePhoneNumberTest() throws WalletException {
		PhoneNumber pnum = getPhoneNumberData();
		pnum = commonService.createPhone(pnum);
		pnum = commonService.getPhone(pnum.getId());
		boolean deletePhone = commonService.deletePhone(pnum);
		Assert.assertNotNull(pnum);
		Assert.assertEquals("9492768765", pnum.getPnumber());
		Assert.assertEquals(deletePhone, Boolean.TRUE);
	}
	  
	@Test
	public void registrationEmailVarificationTest() throws WalletException{
		Authentication authentication = getAuthenticationData();
		authentication.setEmailVarification(true);
		Authentication savedAuthen = commonService.createAuthentication(authentication);
		Assert.assertNotNull(savedAuthen.getId());
		Authentication forAuthe = commonService.getAuthentication(savedAuthen.getId());
		commonService.registrationEmailVarification(forAuthe.getId());
	}
	  
  @Test
	public void getUserEmailByIdTest() throws WalletException { 
	  Authentication authentication = getAuthenticationData();
	  Authentication savedAuthen = commonService.createAuthentication(authentication);
	  String emailId = commonService.getUserEmailById(savedAuthen.getId());
	  Assert.assertEquals("kedarnathd@tarangtech.com", emailId);
	  Assert.assertNotNull(emailId);
   }
	  
   @Test
	public void getUserWalletsTest() throws WalletException{
	    UserWallet userWallet = null;
		userWallet = commonService.createUserWallet(1L, 1L);
		userWallet = commonService.createUserWallet(1L, 2L);
		userWallet = commonService.createUserWallet(1L, 3L);
		Assert.assertNotNull(userWallet);
		Assert.assertNotNull(userWallet.getId());
		List<UserWallet> wallets = null;
	    wallets = commonService.getUserWallets(1L);
	    Assert.assertNotNull(wallets);
	}
	   
   @Test
   public void sendRegistrationMailTest() throws WalletException{
		Authentication authentication = getAuthenticationData();
		Assert.assertNotNull(authentication);
		Authentication savedAuthen = commonService.createAuthentication(authentication);
		Assert.assertNotNull(savedAuthen.getId());
		Authentication forAuthe = commonService.getAuthentication(savedAuthen.getId());
		Assert.assertNotNull(forAuthe.getId());
		commonService.sendRegistrationMail("customer", forAuthe.getId(), forAuthe.getUserType(), 1L);
	}
		
   @Test
	public void getPasswordHistoryTest() throws WalletException{
	   Authentication authentication = getAuthenticationData();
	   Assert.assertNotNull(authentication);
	   Authentication savedAuthen = commonService.createAuthentication(authentication);
	   Assert.assertNotNull(savedAuthen.getId());
	   Authentication forAuthe = commonService.getAuthentication(savedAuthen.getId());
	   Assert.assertNotNull(forAuthe.getId());
	   List<PasswordHistory> passwordHistories = commonService.getPasswordHistory(forAuthe.getId());
	   Assert.assertNotNull(passwordHistories);
   }
   
   @Test
	public void getTotalNumberOfAccountsTest() throws WalletException{
	   Authentication authentication = getAuthenticationData();
	   Assert.assertNotNull(authentication);
	   Authentication savedAuthen = commonService.createAuthentication(authentication);
	   Assert.assertNotNull(savedAuthen.getId());
	   Authentication forAuthe = commonService.getAuthentication(savedAuthen.getId());
	   Assert.assertNotNull(forAuthe.getId());
	    
	   Long numberOfAccounts = commonService.getTotalNumberOfAccounts(GlobalLitterals.CUSTOMER_USER_TYPE);
	   Assert.assertNotNull(numberOfAccounts);
	        
	   Long getLastWeekCreatedAccounts = commonService.getLastWeekCreatedNumberOfAccounts(DateConvertion.getFromDate(GlobalLitterals.WEEK_DAYS), GlobalLitterals.CUSTOMER_USER_TYPE);
	   Assert.assertNotNull(getLastWeekCreatedAccounts);
	   
	   Long lastDayCreatedAccounts = commonService.getLastDayCreatedNumberOfAccounts(DateConvertion.getFromDate(0), DateConvertion.getToDate(0),GlobalLitterals.CUSTOMER_USER_TYPE);
	   Assert.assertNotNull(lastDayCreatedAccounts);
	   
	  }
	   
   @Test
 	public void getCustomersTotalAmountsByCountryTest() throws WalletException{
	   
	Authentication authentication = getAuthenticationData();
	Authentication auth = commonService.createAuthentication(authentication);
    Assert.assertNotNull( auth.getId() );
	   
	UserWallet userWallet = null;
	userWallet = commonService.createUserWallet(1L, 1L);
	Assert.assertNotNull( userWallet );   
	userWallet.setAmount(10000.0);
	List<UserWallet> wallets = null;
	wallets = commonService.getUserWallets(1L);
	Assert.assertNotNull(wallets);
	 
	List<Object[]> customerAmount = null;
	customerAmount = commonService. getCustomersTotalAmountsByCountry(auth.getUserType());
	Assert.assertNotNull(customerAmount);
    }
	   
   @Test
   public void getCustomerBalancesByUserIdTest() throws WalletException{
	   Authentication authentication = getAuthenticationData();
	   Authentication auth = commonService.createAuthentication(authentication);
	   Assert.assertNotNull( auth.getId() );
	   
	   UserWallet userWallet = null;
	   userWallet = commonService.createUserWallet(1L, 1L);
	   Assert.assertNotNull( userWallet );
	   
	   userWallet.setAmount(10000.0);
	   List<UserWallet> wallets = null;
	   wallets = commonService.getUserWallets(1L);
	   Assert.assertNotNull(wallets);
	    
	   List<Object[]> customerBalanceById = commonService.getCustomerBalancesByUserId(userWallet.getAuthId());
	   Assert.assertNotNull(customerBalanceById);
    }
   
   @Test
	public void sendMailForUserStatusUpdateTest() throws WalletException{
       commonService.sendMailForUserStatusUpdate("kedar", "kedarnathd@tarangtech.com", "Approved", true, "Testing", GlobalLitterals.CUSTOMER_USER_TYPE, 1L);
	}
	   
   @Test
	public void sendMailUserAccountRejectTest() throws WalletException{
	   commonService. sendMailUserAccountReject("kedarnathd", "kedarnathd@tarangtech.com", "Account Rejected", GlobalLitterals.CUSTOMER_USER_TYPE, 1L);
	}
	  
   @Test
	public void getTxnIdsForNonRegisterWalletsTest() throws WalletException{
	   
	  NonRegisterWallet nonRegisterWallet = DataPreparation.getNonRegisterWallet();
	  nonRegisterWallet = commonService.createNonRegisterWallet(nonRegisterWallet);
	  Assert.assertNotNull(nonRegisterWallet.getId());
	  List<Long> trxId = commonService.getTxnIdsForNonRegisterWallets(1L, new Date());
	  Assert.assertNotNull(trxId);
	}
	  
   @Test
	public void getMasterAmountWalletTest() throws WalletException{
	   
	   List<Object> data = getMasterWalletsData();
       Iterator<Object> iter = data.iterator();
       while(iter.hasNext()){
    		hibernateTemplate.save(iter.next());
    	}
       MasterAmountWallet masterAmountWallet = commonService.getMasterAmountWallet(1L);
       Assert.assertNotNull(masterAmountWallet);
       
       MasterAmountWallet updateMasterWallet = commonService.updateMasterAmountWallet(masterAmountWallet);
       Assert.assertNotNull(updateMasterWallet);
	}
	   
   @Test
   public void getMasterFeeWalletTest() throws WalletException{
	   
		List<Object> data = getMasterWalletsData();
	    Iterator<Object> iter = data.iterator();
	    while(iter.hasNext()){
	    hibernateTemplate.save(iter.next());
	    }   
	    MasterFeeWallet masterFeeWallet = commonService.getMasterFeeWallet(1L);
	    Assert.assertNotNull(masterFeeWallet);
	    
	    MasterFeeWallet updateFeeWallet = commonService.getMasterFeeWallet(2L);
	    Assert.assertNotNull(updateFeeWallet);
   
   }

   @Test
 	public void getMasterTaxWalletTest() throws WalletException{
	   
		 List<Object> data = getMasterWalletsData();
		 Iterator<Object> iter = data.iterator();
		 while(iter.hasNext()){
		 hibernateTemplate.save(iter.next());
		 }      
	 	MasterTaxWallet masterTaxWallet = commonService.getMasterTaxWallet(1L);
	 	Assert.assertNotNull(masterTaxWallet);
	 	
	 	MasterTaxWallet updateTaxWallet = commonService.updateMasterTaxWallet(masterTaxWallet);
	 	Assert.assertNotNull(updateTaxWallet);
 	}

   private static List<Object> getMasterWalletsData(){
	 List<Object> list = new ArrayList<Object>();
			
			// MasterAmountWallet (id, currency, amount)
	 list.add(new MasterAmountWallet(1L, 1L, 100.0));
	 list.add(new MasterAmountWallet(2L, 2L, 100.0));
	 list.add(new MasterAmountWallet(3L, 3L, 100.0));
	
		// MasterFeeWallet( id, currency, fee)
	 list.add(new MasterFeeWallet(1L, 1L, 100.0));
	 list.add(new MasterFeeWallet(2L, 2L, 100.0));
	 list.add(new MasterFeeWallet(3L, 3L, 100.0));
	
		// MasterTaxWallet( id, currency, tax)
	 list.add(new MasterTaxWallet(1L, 1L, 100.0));
	 list.add(new MasterTaxWallet(2L, 2L, 100.0));
	 list.add(new MasterTaxWallet(3L, 3L, 100.0));
	 
	 return list;
    }
	   
  }
