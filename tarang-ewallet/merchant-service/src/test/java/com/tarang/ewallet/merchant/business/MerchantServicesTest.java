package com.tarang.ewallet.merchant.business;

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
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.merchant.util.MerchantUserCriteriaUtil;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Merchant;
import com.tarang.ewallet.model.MerchantPayInfo;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;


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
public class MerchantServicesTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	private static final Logger LOG = Logger.getLogger(MerchantServicesTest.class);

	public static final String BUSINESS_INFORMATION = "BusinessInformation";
	public static final String BUSINESS_OWNER_INFORMATION = "BusinessOwnerInformation";
	public static final String CUSTOMER_SERVICE_INFORMATION = "CustomerServiceInformation";

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private CommonService commonService;

	@Test
	public void registrationTest() throws WalletException {

		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
	}

	@Test
	public void getCriteriaQueryTest1() {

		String searchString = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"legalName\",\"op\":\"cn\",\"data\":\"abc\"},"
				+ "{\"field\":\"emailId\",\"op\":\"eq\",\"data\":\"abc@c.com\"},{\"field\":\"deleted\",\"op\":\"eq\",\"data\":\"false\"},"
				+ "{\"field\":\"active\",\"op\":\"eq\",\"data\":\"true\"}]}";
		String exptQString = "select new com.tarang.ewallet.dto.UserDto(mr.id, bi.legalName, au.emailId, au.active, "
				+ "au.status, au.blocked, au.creationDate)  "
				+ "from Merchant mr, Authentication au, BusinessInformation bi "
				+ "where (mr.authenticationId=au.id and mr.businessInformation=bi.id and au.emailVarification=true)  "
				+ "and ( upper(bi.legalName) like upper('%abc%')  and au.emailId = 'abc@c.com'  )  order by bi.legalName desc";

		String exptCString = "select count(*)  from Merchant mr, Authentication au, BusinessInformation bi "
				+ "where (mr.authenticationId=au.id and mr.businessInformation=bi.id and au.emailVarification=true)  "
				+ "and ( upper(bi.legalName) like upper('%abc%')  and au.emailId = 'abc@c.com'  ) ";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("legalName");
		qf.setSord("desc");

		String queryString = MerchantUserCriteriaUtil.getMerchantSearchQuery(
				qf, null, null, null, null, null);

		String countString = MerchantUserCriteriaUtil
				.getMerchantSearchCountQuery(qf, null, null, null, null, null);

		LOG.debug("expected1:\n" + exptQString + "|");
		LOG.debug("output1:\n" + queryString + "|");

		Assert.assertEquals(exptQString, queryString);
		Assert.assertEquals(exptCString, countString);
	}

	@Test
	public void getCriteriaQueryTest2() {

		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";
		String exptQString = "select new com.tarang.ewallet.dto.UserDto(mr.id, bi.legalName, au.emailId, au.active, "
				+ "au.status, au.blocked, au.creationDate)  "
				+ "from Merchant mr, Authentication au, BusinessInformation bi "
				+ "where (mr.authenticationId=au.id and mr.businessInformation=bi.id and au.emailVarification=true)  order by bi.legalName desc";
		String exptCString = "select count(*)  from Merchant mr, Authentication au, BusinessInformation bi "
				+ "where (mr.authenticationId=au.id and mr.businessInformation=bi.id and au.emailVarification=true) ";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("legalName");
		qf.setSord("desc");

		String queryString = MerchantUserCriteriaUtil.getMerchantSearchQuery(
				qf, null, null, null, null, null);

		String countString = MerchantUserCriteriaUtil
				.getMerchantSearchCountQuery(qf, null, null, null, null,  null);

		LOG.debug("expected1:\n" + exptCString + "|");
		LOG.debug("output1:\n" + countString + "|");

		Assert.assertEquals(exptQString, queryString);
		Assert.assertEquals(exptCString, countString);
	}

	@Test
	public void getMerchantListTest1() throws WalletException {

		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
		Assert.assertNotNull(merchantDto.getAuthenticationId());
		mailVarification(merchantDto.getAuthenticationId());
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(1);
		qf.setSidx("legalName");
		qf.setSord("desc");

		List<UserDto> userDtos = merchantService.getMerchantsList(qf, null,
				null, null, null, 4L);
		Assert.assertNotNull(userDtos);
		Assert.assertEquals(1, userDtos.size());

	}

	@Test
	public void getMerchantListTest2() throws WalletException {

		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
		Assert.assertNotNull(merchantDto.getAuthenticationId());
		mailVarification(merchantDto.getAuthenticationId());

		MerchantDto merchantDto1 = DataPreparation.getMerchantDto();
		merchantDto1.setEmailId("sivaramar@tarangtech.com");
		merchantDto1.setPhoneCountryCode1("3333");
		merchantDto1.setCode("4444");

		merchantDto1 = merchantService.newregistration(merchantDto1);
		Assert.assertNotNull(merchantDto1.getId());
		Assert.assertNotNull(merchantDto1.getAuthenticationId());
		mailVarification(merchantDto1.getAuthenticationId());

		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(2);
		qf.setSidx("emailId");
		qf.setSord("desc");

		List<UserDto> userDtos = merchantService.getMerchantsList(qf, null,
				null, null, null, 4L);
		Assert.assertNotNull(userDtos);
		Assert.assertEquals(2, userDtos.size());
	}

	private void mailVarification(Long id) throws WalletException {
		LOG.debug(" emailStatusUpdate ");
		final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
		Authentication authentication = commonService.getAuthentication(id);
		Date creationDate = authentication.getCreationDate();
		Date todayDate = new Date();
		Integer diffInDays = (int) ((creationDate.getTime() - todayDate
				.getTime()) / DAY_IN_MILLIS);
		if (diffInDays == 0) {
			authentication.setEmailVarification(true);
			authentication.setActive(true);
			commonService.updateAuthentication(authentication);
		} else {
			LOG.debug("email verification failed");
		}
	}

	@Test
	public void getMerchantDetailsByIdTest1() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
		Assert.assertNotNull(merchantDto.getAuthenticationId());
		Long merchantId = null;
		merchantId = merchantService.getMerchantId(merchantDto.getEmailId(),
				GlobalLitterals.MERCHANT_USER_TYPE);
		MerchantDto merchantDto1 = merchantService
				.getMerchantDetailsById(merchantId);
		Assert.assertNotNull(merchantDto1.getAddress1BO());

	}

	@Test
	public void getMerchantDetailsByIdTest2() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto.setEmailId("sivaramar@tarangtech.com");
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
		Assert.assertNotNull(merchantDto.getAuthenticationId());
		Long merchantId = null;
		merchantId = merchantService.getMerchantId(merchantDto.getEmailId(),
				GlobalLitterals.MERCHANT_USER_TYPE);
		MerchantDto merchantDto1 = merchantService
				.getMerchantDetailsById(merchantId);
		Assert.assertNotNull(merchantDto1.getAddress1BO());

	}

	@Test
	public void updateMerchantDetailsTest1() throws WalletException {
		boolean acflag = true;
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
		Assert.assertNotNull(merchantDto.getAuthenticationId());

		merchantDto.setCityOrTownBO("bangalore");
		boolean expflag = merchantService.updateMerchantDetails(merchantDto,
				"admin");
		Assert.assertEquals(expflag, acflag);
	}

	@Test
	public void updateMerchantDetailsTest2() throws WalletException {
		boolean acflag = true;
		String cityOrTownBeforeUpdate = "";
		String cityOrTownAfterUpdate = "";
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
		Assert.assertNotNull(merchantDto.getAuthenticationId());
		cityOrTownBeforeUpdate = merchantDto.getCityOrTownBO();
		merchantDto.setCityOrTownBO("bangalore");
		boolean expflag = merchantService.updateMerchantDetails(merchantDto,
				"merchant");
		cityOrTownAfterUpdate = merchantDto.getCityOrTownBO();
		Assert.assertEquals(expflag, acflag);
		Assert.assertNotSame(cityOrTownBeforeUpdate, cityOrTownAfterUpdate);
	}

	@Test
	public void getMerchantIdTest1() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
		Assert.assertNotNull(merchantDto.getAuthenticationId());

		Long merchantId = merchantService.getMerchantId(
				"kedarnathd@tarangtech.com", GlobalLitterals.MERCHANT_USER_TYPE);
		Assert.assertNotNull(merchantId);

	}

	@Test
	public void getMerchantIdTest2() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto.setEmailId("sivaramar@tarangtech.com");
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
		Assert.assertNotNull(merchantDto.getAuthenticationId());

		Long merchantId = merchantService.getMerchantId(
				"sivaramar@tarangtech.com", GlobalLitterals.MERCHANT_USER_TYPE);
		Assert.assertNotNull(merchantId);

	}
	
	@Test
	public void getMerchantAddress()throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Long merchantId = merchantService.getMerchantId("kedarnathd@tarangtech.com", GlobalLitterals.MERCHANT_USER_TYPE);
		Address address = merchantService.getMerchantAddress(merchantId);
		Assert.assertNotNull(address);
		Assert.assertEquals(merchantDto.getAddress1BI(), address.getAddressOne());
	}
	
	@Test
	public void getMerchantLegalName()throws WalletException {
		String legalName = "Wallet Inc.";
		String emailId = "wallet@abc.com";
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto.setBusinessLegalname(legalName);
		merchantDto.setEmailId(emailId);
		merchantDto = merchantService.newregistration(merchantDto);
		String legalNameFromDB = merchantService.getLegalName(emailId);
		Assert.assertEquals(legalName, legalNameFromDB);
	}

/*	@Test
	public void getMerchantCode()throws WalletException {
		String merchantCode = "12345ABCDE";
		String emailId = "wallet@abc.com";
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto.setMerchantCode(merchantCode);
		merchantDto.setEmailId(emailId);
		merchantDto = merchantService.registration(merchantDto);
		String merchantCodeFromDB = merchantService.getMerchantCode(emailId);
		Assert.assertEquals(merchantCode, merchantCodeFromDB);
	}*/
	
	@Test
	public void getMerchantPayInfo()throws WalletException {
		String emailId = "wallet@abc.com";
		String merchantCode = "12345ABCDE";
		String merSuccessUrl = "http://172.30.66.132:8888/merchantpay/welcome";
		String merFailureUrl = "http://172.30.66.132:8888/merchantpay/welcome";
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto.setMerchantCode(merchantCode);
		merchantDto.setSuccessUrl(merSuccessUrl);
		merchantDto.setFailureUrl(merFailureUrl);
		merchantDto.setEmailId(emailId);
		merchantDto = merchantService.newregistration(merchantDto);
		MerchantPayInfo merchantPayInfo = merchantService.getMerchantPayInfo(emailId);
		Assert.assertNotNull(merchantPayInfo);
		Assert.assertEquals(merchantDto.getMerchantPayInfoId(), merchantPayInfo.getId());
	}

	@Test
	public void getPersonNameTest() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		String personName = merchantService.getPersonName("kedarnathd@tarangtech.com", GlobalLitterals.MERCHANT_USER_TYPE);
		
		Assert.assertNotNull(personName);
		Assert.assertEquals(personName , "firstName lastName");
	}
	
	@Test
	public void registrationAfter24hoursTest() throws WalletException {
		boolean acflag = true;
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
		Assert.assertNotNull(merchantDto.getAuthenticationId());

		merchantDto.setCityOrTownBO("bangalore");
		boolean expflag = merchantService.updateMerchantDetails(merchantDto,
				"admin");
		Assert.assertEquals(expflag, acflag);
		
	}
	
	@Test
	public void getRegisteredMemberTest() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		
		merchantDto = merchantService.getRegisteredMember(merchantDto.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE);

		Assert.assertNotNull(merchantDto);
		Assert.assertEquals(merchantDto.getEmailId(),"kedarnathd@tarangtech.com");		
	}
	
	@Test
	public void getMerchantByAuthIdTest() throws WalletException {
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Long authenticationId = merchantDto.getAuthenticationId();
		
		Merchant merchant = merchantService.getMerchantByAuthId(merchantDto.getAuthenticationId());
		
		Assert.assertNotNull(merchant);
		Assert.assertEquals(authenticationId, merchant.getAuthenticationId());
		
	}
}
