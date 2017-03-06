package com.tarang.ewallet.audittrail.business;

import java.util.Date;

import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.accounts.util.FundingAccountStatus;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author vasanthar
 *
 */
public class DataPreparation implements GlobalLitterals{

	@SuppressWarnings("deprecation")
	public static CustomerDto getOldCustomerDto(){
		
		CustomerDto dto = new CustomerDto();
		
    	dto.setPassword("aaaa1A@");
    	dto.setHintQuestion1(new Long(1));
    	dto.setAnswer1("answer1");
    	dto.setHintQuestion2(new Long(2));
    	dto.setAnswer2("answer2");
    	dto.setTitle(1L);
    	dto.setFirstName("First name");
    	dto.setLastName("Last name");
    	dto.setPhoneCode("91");
    	dto.setPhoneNo("801234");
    	
    	dto.setAddrOne("address one");
    	dto.setAddrTwo("address two");
    	dto.setCity("Bangalore");
    	dto.setState(1L);
    	dto.setCountry(1L);
    	dto.setPostelCode("560048");
    	dto.setDateOfBirth(new Date("10/10/1980"));
    	dto.setActive(false);
    	dto.setBlocked(false);
    	dto.setStatus(4L);
    	dto.setKycRequired(false);
    	dto.setLanguageId(1L);

		return dto;
	}
	
	@SuppressWarnings("deprecation")
	public static CustomerDto getNewCustomerDto(){
		
		CustomerDto dto = new CustomerDto();
		
    	dto.setPassword("bbbb1B@");
    	dto.setHintQuestion1(new Long(2));
    	dto.setAnswer1("answer1_new");
    	dto.setHintQuestion2(new Long(3));
    	dto.setAnswer2("answer2_new");
    	dto.setTitle(2L);
    	dto.setFirstName("First name_new");
    	dto.setLastName("Last name_new");
    	dto.setPhoneCode("92");
    	dto.setPhoneNo("801235");
    	
    	dto.setAddrOne("address one_new");
    	dto.setAddrTwo("address two_new");
    	dto.setCity("Bangalore_new");
    	dto.setState(2L);
    	dto.setCountry(2L);
    	dto.setPostelCode("560049");
    	dto.setDateOfBirth(new Date("10/10/1990"));
    	dto.setActive(true);
    	dto.setBlocked(false);
    	dto.setStatus(3L);
    	dto.setKycRequired(true);
    	dto.setLanguageId(1L);

		return dto;
	}

	public static MerchantDto getOldMerchantDto() {
		MerchantDto merchantDto = new MerchantDto();

		merchantDto.setPassword("Kedar@123");

		// merchantDto.setConfirmPassword("  ");
		merchantDto.setQuestion1(1L);
		merchantDto.setHint1("Ans1");// answer for question1
		merchantDto.setQuestion2(2L);
		merchantDto.setHint2("Ans2");// answer for question2
		// merchantDto.setSecurityCode("F!pUac");//security code

		// Business information
		merchantDto.setOwnerType(1L);
		merchantDto.setBusinessLegalname("businessLegalname");

		merchantDto.setAddress1BI("address1");
		merchantDto.setAddress2BI("address2");
		merchantDto.setCityOrTownBI("city");
		merchantDto.setStateorRegionBI(1L);
		merchantDto.setCountryBI(1L);
		merchantDto.setCountryBO(1L);
		
		merchantDto.setPostalCodeBI("12345");
		merchantDto.setPhoneCountryCode1("1");
		merchantDto.setPhoneNumber("phoneNumber");

		merchantDto.setBusinessCategory(1L);
		merchantDto.setSubCategory(1L);
		merchantDto.setBusinessEstablishedMonth("10");
		merchantDto.setBusinessEstablishedYear("2012");
		merchantDto.setWebsite("website");
		merchantDto.setCurrency(1L);
		merchantDto.setAverageTransactionAmount(10L);
		merchantDto.setHighestMonthlyVolume(1000L);
		merchantDto.setPercentageOfAnnualRevenueFromOnlineSales(1L);

		// Business Owner Information
		merchantDto.setFirstName("firstName");
		// merchantDto.setMiddleName("middleName");
		merchantDto.setLastName("lastName");

		merchantDto.setHomeAddress(true); // is same as business address

		merchantDto.setAddress1BO("address1");
		merchantDto.setAddress2BO("address2");
		merchantDto.setCityOrTownBO("city");
		merchantDto.setStateOrRegionBO(1L);
		merchantDto.setCountryBO(1L);
		merchantDto.setPostalCodeBO("12345");
		merchantDto.setLegalName("legalName");
		merchantDto.setRoleName("roleName");
		merchantDto.setStatusName("statusName");

		// Customer service information
		merchantDto.setEmailCSI("email");
		merchantDto.setCode("1");
		merchantDto.setPhone("phone");

		return merchantDto;
	}
	
	public static MerchantDto getNewMerchantDto() {
		MerchantDto merchantDto = new MerchantDto();

		merchantDto.setPassword("vasanth@123");

		// merchantDto.setConfirmPassword("  ");
		merchantDto.setQuestion1(2L);
		merchantDto.setHint1("Ans2");// answer for question1
		merchantDto.setQuestion2(2L);
		merchantDto.setHint2("Ans1");// answer for question2
		// merchantDto.setSecurityCode("F!pUac");//security code

		// Business information
		merchantDto.setOwnerType(2L);
		merchantDto.setBusinessLegalname("businessLegalname_new");

		merchantDto.setAddress1BI("address1_new");
		merchantDto.setAddress2BI("address2_new");
		merchantDto.setCityOrTownBI("city_new");
		merchantDto.setStateorRegionBI(2L);
		merchantDto.setCountryBI(2L);
		merchantDto.setCountryBO(2L);
		merchantDto.setPostalCodeBI("12346");
		
		merchantDto.setPhoneCountryCode1("2");
		merchantDto.setPhoneNumber("123456789");

		merchantDto.setBusinessCategory(2L);
		merchantDto.setSubCategory(2L);
		merchantDto.setBusinessEstablishedMonth("11");
		merchantDto.setBusinessEstablishedYear("2013");
		merchantDto.setWebsite("website_new");
		merchantDto.setCurrency(2L);
		merchantDto.setAverageTransactionAmount(15L);
		merchantDto.setHighestMonthlyVolume(10000L);
		merchantDto.setPercentageOfAnnualRevenueFromOnlineSales(2L);

		// Business Owner Information
		merchantDto.setFirstName("firstName_new");
		// merchantDto.setMiddleName("middleName");
		merchantDto.setLastName("lastName_new");

		merchantDto.setHomeAddress(true); // is same as business address

		merchantDto.setAddress1BO("address1_new");
		merchantDto.setAddress2BO("address2_new");
		merchantDto.setCityOrTownBO("city_new");
		merchantDto.setStateOrRegionBO(2L);
		merchantDto.setCountryBO(2L);
		merchantDto.setPostalCodeBO("12346");

		// Customer service information
		merchantDto.setEmailCSI("email_new");
		merchantDto.setCode("12");
		merchantDto.setPhone("phone_new");
		merchantDto.setLegalName("legalName_new");
		merchantDto.setRoleName("roleName_new");
		merchantDto.setStatusName("statusName_new");
		return merchantDto;
	}

	public static AdminUserDto getOldAdminUserDto() {
		AdminUserDto adminUserDto = new AdminUserDto();
		adminUserDto.setFirstName("super");
		adminUserDto.setLastName("admin");
		adminUserDto.setUserType(ADMIN_USER_TYPE);
		adminUserDto.setEmailId("kedarnathd@tarangtech.com");
		adminUserDto.setPassword("bangalore");
		adminUserDto.setCountryId(1L);
		adminUserDto.setStateId(1L);
		adminUserDto.setCity("Bangalore");
		adminUserDto.setAddressOne("Bangalore");
		adminUserDto.setAddressTwo("Bangalore1");
		adminUserDto.setStatus(1L);
		adminUserDto.setZipcode("123132");
		adminUserDto.setPhoneCode("1234");
		adminUserDto.setPhoneNo("123456789");
		adminUserDto.setLegalName("legalName");
		adminUserDto.setRoleId(1L);
		adminUserDto.setRoleName("roleName");

		return adminUserDto;
	}
	public static AdminUserDto getNewAdminUserDto() {
		AdminUserDto adminUserDto = new AdminUserDto();
		adminUserDto.setFirstName("super_new");
		adminUserDto.setLastName("admin_new");
		adminUserDto.setUserType("B");
		adminUserDto.setEmailId("vasanthar@tarangtech.com");
		adminUserDto.setPassword("bangalore_new");
		adminUserDto.setCountryId(2L);
		adminUserDto.setStateId(2L);
		adminUserDto.setCity("Bangalore_new");
		adminUserDto.setAddressOne("Bangalore_new");
		adminUserDto.setAddressTwo("Bangalore1_new");
		adminUserDto.setZipcode("123133");
		adminUserDto.setRoleId(new Long(2));
		adminUserDto.setPhoneCode("1235");
		adminUserDto.setPhoneNo("1234567890");
		adminUserDto.setLegalName("legalName_new");
		adminUserDto.setRoleId(2L);
		adminUserDto.setRoleName("roleName_new");
		
		return adminUserDto;
	}
	
	public static Role getOldRole(){
		Role role = new Role();
		role.setName("role_old");
		role.setDescription("description_old");		
		role.setMenuDetails("1");
		return role;
	}
	public static Role getNewRole(){
		Role role = new Role();
		role.setName("role_new");
		role.setDescription("description_new");		
		role.setMenuDetails("2");
		return role;
	}
	
	public static AccountDto getOldAccountDto(){
		String ahn = "Vasanth";
		Boolean da = false;
		Long ds = FundingAccountStatus.REJECTED_STATUS;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		Long ct = 2L;
		String cn = "2222531234567890";
		String expd = "11/2020";
		
		AccountDto dto = new AccountDto();
		dto.setAccountHolderName(ahn);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		
		dto.setCardType(ct);
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		return dto;	
	}
	
	public static AccountDto getNewAccountDto(){
		String ahn = "Vasanth Kumar";
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 2L;
		Long bat = 9L;
		String sortCode = "000054";
		String bkn = "HDFC";
		String acn = "0000531234567899";
		String bka = "Jayanagar 4th Block, Bangalore";
		
		Long ct = 1L;
		String cn = "2222531234567891";
		String expd = "13/2020";
		
		AccountDto dto = new AccountDto();
		dto.setAccountHolderName(ahn);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		
		dto.setCardType(ct);
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		
		return dto;	
	}
	
	public static RequestMoneyDto getOldRequestMoneyStatus(){
		
		RequestMoneyDto requestMoneyOld = new RequestMoneyDto();
		
		requestMoneyOld.setRequesterEmail("vasanthar@tarangtech.com");
		requestMoneyOld.setRequesterName("First name");
		requestMoneyOld.setAmount(20000.00);
		requestMoneyOld.setCurrencyId(1L);
		requestMoneyOld.setRequesterMsg("Request Msg");
		requestMoneyOld.setRequestDate(new Date());
		requestMoneyOld.setResponserEmail("vasanthar@tarangtech.com");
		requestMoneyOld.setResponserName("First name");
		requestMoneyOld.setResponserMsg("Response Msg");
		requestMoneyOld.setResponseDate(new Date());
		requestMoneyOld.setStatus(1L);
		requestMoneyOld.setCountryId(1L);
		
		return requestMoneyOld;
	}
	
	public static RequestMoneyDto getNewRequestMoneyStatus(){
		
		RequestMoneyDto requestMoneyNew = new RequestMoneyDto();
		
		requestMoneyNew.setRequesterEmail("vasanthar@tarangtech.com");
		requestMoneyNew.setRequesterName("First name");
		requestMoneyNew.setAmount(20000.00);
		requestMoneyNew.setCurrencyId(1L);
		requestMoneyNew.setRequesterMsg("Request Msg");
		requestMoneyNew.setRequestDate(new Date());
		requestMoneyNew.setResponserEmail("vasanthar@tarangtech.com");
		requestMoneyNew.setResponserName("First name");
		requestMoneyNew.setResponserMsg("Response Msg");
		requestMoneyNew.setResponseDate(new Date());
		requestMoneyNew.setStatus(2L);
		requestMoneyNew.setCountryId(1L);
		
		return requestMoneyNew;
	}
	
	
}
