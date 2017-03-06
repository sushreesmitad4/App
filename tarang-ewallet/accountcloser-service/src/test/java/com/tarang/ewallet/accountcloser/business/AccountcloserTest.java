package com.tarang.ewallet.accountcloser.business;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.util.GlobalLitterals;

/**
 * @author : prasadj
 * @date : Oct 17, 2012
 * @time : 8:43:37 AM
 * @version : 1.0.0
 * @comments:
 * 
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml",
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class AccountcloserTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	public static final String BUSINESS_INFORMATION = "BusinessInformation";
	public static final String BUSINESS_OWNER_INFORMATION = "BusinessOwnerInformation";
	public static final String CUSTOMER_SERVICE_INFORMATION = "CustomerServiceInformation";

	@Autowired
	private AccountCloserService accountCloserService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CustomerService customerService;

	@Test
	public void createAccountCloserTest() throws WalletException {
		AccountCloserDto accountCloserDto = new AccountCloserDto();
		accountCloserDto.setAuthId(1L);
		accountCloserDto.setStatus(1L);
		accountCloserDto.setUserType("C");
		accountCloserDto.setCreator(1L); // customer
		accountCloserDto.setMessage("customer message1");
		AccountCloserDto  rst  = accountCloserService.createAccountCloser(accountCloserDto);
		Assert.assertNotNull(rst.getId());
		Assert.assertEquals("customer message1", rst.getMessageList().get(0).getMessage());
	}
	
	@Test
	public void getAccountCloserByIdTest() throws WalletException {

		AccountCloserDto accountCloserDto = new AccountCloserDto();
		accountCloserDto.setAuthId(1L);
		accountCloserDto.setStatus(1L);
		accountCloserDto.setUserType("C");
		accountCloserDto.setCreator(1L); // customer
		accountCloserDto.setMessage("customer message1");
		AccountCloserDto  rst  = accountCloserService.createAccountCloser(accountCloserDto);
		AccountCloserDto  rst1 = accountCloserService.getAccountCloserById(rst.getId());
		Assert.assertNotNull(rst1);
		Assert.assertEquals(rst.getAuthId(), rst1.getAuthId());
		Assert.assertEquals(rst.getRequestedDate(), rst1.getRequestedDate());
		Assert.assertEquals(rst.getStatus(), rst1.getStatus());
		Assert.assertEquals(rst.getMessageList().get(0).getMessage(), rst1.getMessageList().get(0).getMessage());
		Assert.assertEquals(rst.getMessageList().get(0).getCreator(), rst1.getMessageList().get(0).getCreator());
		Assert.assertEquals(rst.getMessageList().get(0).getMessageDate(), rst1.getMessageList().get(0).getMessageDate());
	}

	@Test
	public void getAccountCloserByUserTest() throws WalletException {

		AccountCloserDto accountCloserDto = new AccountCloserDto();
		accountCloserDto.setAuthId(2L);
		accountCloserDto.setStatus(1L);
		accountCloserDto.setUserType("C");
		accountCloserDto.setCreator(1L); // customer
		accountCloserDto.setMessage("customer message1");
		AccountCloserDto  rst  = accountCloserService.createAccountCloser(accountCloserDto);
		AccountCloserDto  rst1 = accountCloserService.getAccountCloserByUser(2L);
		Assert.assertNotNull(rst1);
		Assert.assertEquals(rst.getAuthId(), rst1.getAuthId());
		Assert.assertEquals(rst.getRequestedDate(), rst1.getRequestedDate());
		Assert.assertEquals(rst.getStatus(), rst1.getStatus());
		Assert.assertEquals(rst.getMessageList().get(0).getMessage(), rst1.getMessageList().get(0).getMessage());
		Assert.assertEquals(rst.getMessageList().get(0).getCreator(), rst1.getMessageList().get(0).getCreator());
		Assert.assertEquals(rst.getMessageList().get(0).getMessageDate(), rst1.getMessageList().get(0).getMessageDate());
	}

	@Test
	public void addMessageTest() throws WalletException {

		AccountCloserDto accountCloserDto = new AccountCloserDto();
		Long creator = 2L;
		accountCloserDto.setAuthId(1L);
		accountCloserDto.setStatus(1L);
		accountCloserDto.setUserType("C");
		accountCloserDto.setCreator(1L); // customer
		accountCloserDto.setMessage("customer message1");
		AccountCloserDto  rst  = accountCloserService.createAccountCloser(accountCloserDto);
		AccountCloserDto  rst1  = accountCloserService.addMessage(rst.getId(), "admin message1", creator);
		accountCloserService.addMessage(rst.getId(), "customer message 2", 1L);
		
		Assert.assertNotNull(rst1);
		Assert.assertEquals(rst.getAuthId(), rst1.getAuthId());
		Assert.assertEquals(rst.getRequestedDate(), rst1.getRequestedDate());
		Assert.assertEquals(rst.getStatus(), rst1.getStatus());
		Assert.assertEquals(rst.getMessageList().get(0).getMessage(), rst1.getMessageList().get(0).getMessage());
		Assert.assertEquals(rst.getMessageList().get(0).getCreator(), rst1.getMessageList().get(0).getCreator());
		Assert.assertEquals(rst.getMessageList().get(0).getMessageDate(), rst1.getMessageList().get(0).getMessageDate());

	}
	
	@Test
	public void getAccountCloserListTest() throws WalletException {

		for(int i = 1; i <= 10; i++){
			AccountCloserDto accountCloserDto = new AccountCloserDto();
			accountCloserDto.setAuthId(Long.valueOf("" + i));
			accountCloserDto.setStatus(1L);
			accountCloserDto.setCreator(1L); // customer
			accountCloserDto.setUserType("C");
			accountCloserDto.setMessage("customer message1");
			accountCloserService.createAccountCloser(accountCloserDto);
		}
		List<AccountCloserDto>  rst1  = accountCloserService.getAccountCloserList(20, 1L, new Date(), new Date(), "C");
		Assert.assertNotNull(rst1);

	}
	
	
	@Test
	 public void getAccountCloserStatusById() throws WalletException {
	  AccountCloserDto accountCloserDto = new AccountCloserDto();
	  accountCloserDto.setAuthId(2L);
	  accountCloserDto.setStatus(4L);
	  accountCloserDto.setUserType("C");
	  accountCloserDto.setCreator(1L); // customer
	  accountCloserDto.setMessage("customer message1");
	  AccountCloserDto  acDto  = accountCloserService.createAccountCloser(accountCloserDto);
	  Assert.assertNotNull(acDto);
	  Boolean accountCloserStatus = accountCloserService.getAccountCloserStatusById(acDto.getAuthId());
	  Assert.assertEquals(Boolean.TRUE, accountCloserStatus);
	  
	  accountCloserStatus = accountCloserService.getAccountCloserStatusById(acDto.getAuthId()+10L);
	  Assert.assertEquals(Boolean.FALSE, accountCloserStatus);
	 }
	 
	
	@Test
	public void addMessageByAdminTest() throws WalletException {
		AccountCloserDto accountCloserDto = new AccountCloserDto();
		Long creator = 2L;
		accountCloserDto.setAuthId(1L);
		accountCloserDto.setStatus(1L);
		accountCloserDto.setUserType("A");
		accountCloserDto.setCreator(1L); // customer
		accountCloserDto.setMessage("customer message1");
		AccountCloserDto  rst  = accountCloserService.createAccountCloser(accountCloserDto);

		AccountCloserDto  rst1  = accountCloserService.addMessageByAdmin(rst.getId(), "admin message1", creator, accountCloserDto.getStatus());
		
		accountCloserService.addMessageByAdmin(rst.getId(), "customer message 2", creator ,1L);
		Assert.assertNotNull(rst1);
		Assert.assertEquals(rst.getAuthId(), rst1.getAuthId());
		Assert.assertEquals(rst.getRequestedDate(), rst1.getRequestedDate());
		Assert.assertEquals(rst.getStatus(), rst1.getStatus());
		Assert.assertEquals(rst.getMessageList().get(0).getMessage(), rst1.getMessageList().get(0).getMessage());
		Assert.assertEquals(rst.getMessageList().get(0).getCreator(), rst1.getMessageList().get(0).getCreator());
		Assert.assertEquals(rst.getMessageList().get(0).getMessageDate(), rst1.getMessageList().get(0).getMessageDate());

	}

    @SuppressWarnings("deprecation")
	//TODO: Need to work on this
    //@Test
	public void getAccountCloserForViewTest() throws WalletException {
		
		CustomerDto dto = getCustomerDto();	
		dto = customerService.newregistration(dto);
		System.out.println("Account Closer test" +dto.getEmailId());
		System.out.println("Account Closer test 189" + dto.getAuthenticationId());
		Long authenticationId = dto.getAuthenticationId();
		
		Long languageId = 1L;
		AccountCloserDto accountCloserDto = new AccountCloserDto();
		accountCloserDto.setAuthId(authenticationId);
		accountCloserDto.setId(dto.getId());
		accountCloserDto.setStatus(1L);
		accountCloserDto.setUserType("C");
		accountCloserDto.setCreator(1L); // customer
		accountCloserDto.setMessage("Account Closer");
		accountCloserDto.setRegistrationDate(new Date("10/10/1980"));
		accountCloserDto.setRequestedDate(new Date());
		accountCloserDto.setUserStatus("Approved");
	
		AccountCloserDto  rst  = accountCloserService.createAccountCloser(accountCloserDto);
		Assert.assertNotNull(rst);
      
		accountCloserService.addMessage(rst.getId(), accountCloserDto.getMessage(), 1L);
	    AccountCloserDto rst1 = accountCloserService.getAccountCloserForView(rst.getId(),languageId);
		Assert.assertNotNull(rst1);
	}
	
	@Test
	public void addMessageBySchedulerTest()throws WalletException {
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
		
		AccountCloserDto accountCloserDto = new AccountCloserDto();
		Long creator = 1L;
		Long status = 1L;
		accountCloserDto.setAuthId(authentication.getId());
		accountCloserDto.setStatus(status);
		accountCloserDto.setUserType("C");
		accountCloserDto.setCreator(creator); // customer
		accountCloserDto.setMessage("customer message1");
		AccountCloserDto  rst  = accountCloserService.createAccountCloser(accountCloserDto);
		
		Assert.assertNotNull(rst.getId());
		Assert.assertEquals(accountCloserDto.getAuthId(), rst.getAuthId());
		Assert.assertEquals(accountCloserDto.getStatus(), rst.getStatus());
		Assert.assertEquals(accountCloserDto.getUserType(), rst.getUserType());
		
		Assert.assertNotNull(rst.getMessageList());
		Assert.assertNotSame(Boolean.TRUE, rst.getMessageList().isEmpty());
		Assert.assertEquals(1, rst.getMessageList().size());
		
		AccountCloserDto  rst1  = accountCloserService.addMessageByScheduler(rst.getId(), "Customer Message", creator, status);
		Assert.assertNotNull(rst1.getId());
		
		Authentication closeAuth = commonService.getAuthentication(rst1.getAuthId());
		Assert.assertNotNull(closeAuth.getId());
		Assert.assertEquals(Boolean.FALSE, closeAuth.isActive());
	}
	
	@Test
	public void getApprovalAccountClosersTest() throws WalletException {
		for(int i = 1; i <= 10; i++){
			AccountCloserDto accountCloserDto = new AccountCloserDto();
			accountCloserDto.setAuthId(Long.valueOf("" + i));
			accountCloserDto.setStatus(1L);
			accountCloserDto.setCreator(1L); // customer
			accountCloserDto.setUserType("C");
			accountCloserDto.setMessage("customer message1");
			accountCloserService.createAccountCloser(accountCloserDto);
		}
		List<Long>  rst1  = accountCloserService.getApprovalAccountClosers(1L, new Date());
		Assert.assertNotNull(rst1);
	}
	
	private Authentication getAuthenticationData(){
		Authentication authentication=new Authentication();
		authentication.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);
		authentication.setEmailId("anand@tarangtech.com");
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
	
    @SuppressWarnings("deprecation")
	private  CustomerDto getCustomerDto() {
		CustomerDto dto = new CustomerDto();
		
    	dto.setEmailId("vasantha@tarangtech.com");
    	dto.setPassword("aaaa1A@");
    	dto.setHintQuestion1(new Long(1));
    	dto.setAnswer1("answer1");
    	dto.setHintQuestion2(new Long(2));
    	dto.setAnswer2("answer2");
    	dto.setTitle(1L);
    	dto.setFirstName("First name");
    	dto.setLastName("Last name");
    	dto.setPhoneCode("91");
    	dto.setPhoneNo("801234245");
    	
    	dto.setAddrOne("address one");
    	dto.setAddrTwo("address two");
    	dto.setCity("Bangalore");
    	dto.setState(1L);
    	dto.setCountry(1L);
    	dto.setPostelCode("560048");
    	dto.setDateOfBirth(new Date("10/10/1980"));
    	dto.setActive(true);
    	dto.setBlocked(false);
    	dto.setStatus(1L);
    	dto.setKycRequired(false);
    	dto.setLanguageId(1L);
    	dto.setDefaultCurrency(1L);

		return dto;
	}
}
