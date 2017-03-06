/**
 * 
 */
package com.tarang.ewallet.customer.business;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.model.Customer;


/**
* @author  : prasadj
* @date    : Oct 19, 2012
* @time    : 3:22:38 PM
* @version : 1.0.0
* @comments: 
*/

@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
	"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class CustomerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	private static final Logger LOG = Logger.getLogger(CustomerServiceTest.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CommonService commonService;
	
	Long customerId = 0L;
	
	
	public void registrationTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		customerId = dto.getId();
	}
	
	@Test
	public void mobileNewRegistrationTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.mobileNewRegistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		customerId = dto.getId();
	}

	
	public void getCustomerIdTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Long l = customerService.getCustomerId(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
		Assert.assertNotNull(l);
		Assert.assertEquals(dto.getId(),l);		
	}
	
	
	public void getCustomerDtoTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Long customerId = customerService.getCustomerId(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
		CustomerDto customerDto = customerService.getCustomerDto(customerId);
		Assert.assertNotNull( customerDto );
		Assert.assertEquals("First name", customerDto.getFirstName());		
	}
	
	
	public void deleteCustomerTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		boolean b = customerService.deleteCustomer(dto.getId());
		Assert.assertEquals(true, b);		
	}
	
	
	public void updateCustomerDetailsTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		dto.setStatusName("Pending");
		boolean  b = customerService.updateCustomerDetails(dto);
		Assert.assertNotNull( b );		
		Assert.assertEquals(true, b);		
	}
	
	
	/*
	public void getCriteriaQueryTest1(){
		
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"firstName\",\"op\":\"cn\",\"data\":\"abc\"}," +
				"{\"field\":\"emailId\",\"op\":\"eq\",\"data\":\"abc@c.com\"},{\"field\":\"deleted\",\"op\":\"eq\",\"data\":\"false\"}," +
				"{\"field\":\"active\",\"op\":\"eq\",\"data\":\"true\"}]}";
		String exptQString = "select new com.tarang.ewallet.dto.UserDto(cr.id, pn.firstName, pn.lastName, au.emailId, au.active, au.deleted, " +
				"au.status, au.blocked, au.creationDate)  " +
				"from Customer cr, Authentication au, PersonName pn " +
				"where (cr.authenticationId=au.id and cr.nameId=pn.id)  " +
				"and ( upper(pn.firstName) like upper('%abc%')  and au.emailId = 'abc@c.com'  and au.deleted = false  and au.active = true  )  order by pn.firstName desc";
		
		String exptCString = "select count(*)  from Customer cr, Authentication au, PersonName pn " +
				"where (cr.authenticationId=au.id and cr.nameId=pn.id)  " +
				"and ( upper(pn.firstName) like upper('%abc%')  and au.emailId = 'abc@c.com'  and au.deleted = false  and au.active = true  ) ";
	
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("firstName");
		qf.setSord("desc");
	
		String queryString = CustomerUserCriteriaUtil.getCustomerSearchQuery(qf, null, null, null);
		
		String countString = CustomerUserCriteriaUtil.getCustomerSearchCountQuery(qf, null, null, null);
		
		log.debug("expected1:\n" + exptQString + "|");
		log.debug("output1:\n" + queryString + "|");
		
		Assert.assertEquals(exptQString, queryString);
		Assert.assertEquals(exptCString, countString);
	}
	
	//
	public void getCriteriaQueryTest2(){
		
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";
		String exptQString = "select new com.tarang.ewallet.dto.UserDto(cr.id, pn.firstName, pn.lastName, au.emailId, au.active, au.deleted, " +
				"au.status, au.blocked, au.creationDate)  " +
				"from Customer cr, Authentication au, PersonName pn " +
				"where (cr.authenticationId=au.id and cr.nameId=pn.id)  order by pn.firstName desc";
		String exptCString = "select count(*)  from Customer cr, Authentication au, PersonName pn where (cr.authenticationId=au.id and cr.nameId=pn.id) ";
	
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("firstName");
		qf.setSord("desc");
	
		String queryString = CustomerUserCriteriaUtil.getCustomerSearchQuery(qf, null, null, null);
		
		String countString = CustomerUserCriteriaUtil.getCustomerSearchCountQuery(qf, null, null, null);
		
		Assert.assertEquals(exptQString, queryString);
		Assert.assertEquals(exptCString, countString);
		
		Assert.assertEquals(exptQString, queryString);
		Assert.assertEquals(exptCString, countString);
	}*/
	
	
	public void getCustomerListTest1() throws WalletException{
		
		/*List<CustomerDto> list = DataPreparation.getCustomerDtos(20);
		for(CustomerDto dto: list){
			customerService.registration(dto);
		}*/
		
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		customerId = dto.getId();
		/*verify the register customer*/
		mailVarification(dto.getAuthenticationId());
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(1);
		qf.setSidx("emailId");
		qf.setSord("desc");
	
		List<UserDto> userDtos = customerService.getCustomersList(qf, null, null, null, null, 4L);
		Assert.assertNotNull( userDtos );
		Assert.assertEquals(1, userDtos.size());
	}
/*
	//
	public void getCustomerListTest2() throws WalletException{
		
		List<CustomerDto> list = DataPreparation.getCustomerDtos(20);
		for(CustomerDto dto: list){
			customerService.registration(dto);
		}

		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(2);
		qf.setRows(15);
		qf.setSidx("emailId");
		qf.setSord("desc");
	
		List<UserDto> userDtos = customerService.getCustomersList(qf, null, null, 4L);
		Assert.assertNotNull( userDtos );
		Assert.assertEquals(5, userDtos.size());
	}*/
	
	
	public void getCustomerAddress() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Long custId = customerService.getCustomerId(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
		Address address = customerService.getCustomerAddress(custId);
		Assert.assertNotNull(address);
		Assert.assertEquals(dto.getAddrOne(), address.getAddressOne());
	}
	
	private void mailVarification(Long id) throws WalletException {
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
		} else {
			LOG.debug("email verification failed");
		}
	}
	
	
	public void getPhoneNumberTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		PhoneNumber phoneNumber = commonService.getPhoneNumber(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
		Assert.assertEquals(phoneNumber.getPnumber(),dto.getPhoneNo());
	}
	
	
	public void getRegisteredMemberTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		dto = customerService.getRegisteredMember(dto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getEmailId(),"vasanthar@tarangtech.com");
   }
	
	
	public void getCustomerByAuthIdTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		Long authenticationId = dto.getAuthenticationId();
		Customer customer = customerService.getCustomerByAuthId(dto.getAuthenticationId());
		Assert.assertEquals(authenticationId, customer.getAuthenticationId());
	}
	
	
    public void registrationAfter24hoursTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		dto.setStatusName("Pending");
		boolean  b = customerService.updateCustomerDetails(dto);
		Assert.assertNotNull( b );		
		Assert.assertEquals(true, b);	
    }
	
}
