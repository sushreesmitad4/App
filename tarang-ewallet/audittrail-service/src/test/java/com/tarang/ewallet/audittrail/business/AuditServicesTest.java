
package com.tarang.ewallet.audittrail.business;


import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Audit;
import com.tarang.ewallet.model.AuditField;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author vasanthar
 *
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class AuditServicesTest extends AbstractTransactionalJUnit4SpringContextTests implements AuditTrailConstrain {
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Test
	public void createAuditTrailTest() throws WalletException{
		Audit audit = auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.MODULE_CUSTOMER_REGISTRATION, AuditTrailConstrain.STATUS_REGISTRATION, "AuditRegisterTest@gmail.com", GlobalLitterals.CUSTOMER_USER_TYPE);
		Assert.assertNotNull( audit );
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, audit.getAuthId());
		Assert.assertEquals(AuditTrailConstrain.MODULE_CUSTOMER_REGISTRATION, audit.getModuleName());
		Assert.assertEquals(AuditTrailConstrain.STATUS_REGISTRATION, audit.getStatus());
		Assert.assertEquals("AuditRegisterTest@gmail.com", audit.getEmailId());
		Assert.assertEquals(GlobalLitterals.CUSTOMER_USER_TYPE, audit.getUserType());
		Assert.assertNotNull( audit.getCreationDate() );
	}	
	
	@Test
	public void createAuditCustomerTest() throws WalletException{
		CustomerDto oldCustomerDto = DataPreparation.getOldCustomerDto();
		CustomerDto newCustomerDto = DataPreparation.getNewCustomerDto();
		Audit audit=null;
		audit = auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.MODULE_CUSTOMER_UPDATE, AuditTrailConstrain.STATUS_UPDATE, "AuditCustomerTest@gmail.com", GlobalLitterals.CUSTOMER_USER_TYPE, oldCustomerDto, newCustomerDto);
		Assert.assertNotNull( audit );
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, audit.getAuthId());
		Assert.assertEquals(AuditTrailConstrain.MODULE_CUSTOMER_UPDATE, audit.getModuleName());
		Assert.assertEquals(AuditTrailConstrain.STATUS_UPDATE, audit.getStatus());
		Assert.assertEquals("AuditCustomerTest@gmail.com", audit.getEmailId());
		Assert.assertEquals(GlobalLitterals.CUSTOMER_USER_TYPE, audit.getUserType());
		Assert.assertNotNull( audit.getCreationDate() );
		
		List<AuditField> list = audit.getAuditFields();
		Assert.assertEquals(oldCustomerDto.getFirstName(), list.get(0).getOldValue());
		Assert.assertEquals(oldCustomerDto.getLastName(), list.get(1).getOldValue());
		Assert.assertEquals(oldCustomerDto.getTitle(), Long.valueOf(list.get(2).getOldValue()));
		Assert.assertEquals(oldCustomerDto.getCountry(), Long.valueOf(list.get(3).getOldValue()));
		Assert.assertEquals(oldCustomerDto.getAddrOne(), list.get(4).getOldValue());
		Assert.assertEquals(oldCustomerDto.getAddrTwo(), list.get(5).getOldValue());
		Assert.assertEquals(oldCustomerDto.getState(), Long.valueOf(list.get(7).getOldValue()));
		Assert.assertEquals(oldCustomerDto.getCity(), list.get(8).getOldValue());
		Assert.assertEquals(oldCustomerDto.getPostelCode(), list.get(9).getOldValue());
		Assert.assertEquals(oldCustomerDto.getPhoneCode(), list.get(10).getOldValue());
		Assert.assertEquals(oldCustomerDto.getPhoneNo(), list.get(11).getOldValue());
		Assert.assertEquals(oldCustomerDto.getHintQuestion1(), Long.valueOf(list.get(12).getOldValue()));
		Assert.assertEquals(oldCustomerDto.getAnswer1(), list.get(13).getOldValue());
		Assert.assertEquals(oldCustomerDto.getPassword(), list.get(14).getOldValue());
		
		Assert.assertEquals(newCustomerDto.getFirstName(), list.get(0).getNewValue());
		Assert.assertEquals(newCustomerDto.getLastName(), list.get(1).getNewValue());
		Assert.assertEquals(newCustomerDto.getTitle(), Long.valueOf(list.get(2).getNewValue()));
		Assert.assertEquals(newCustomerDto.getCountry(), Long.valueOf(list.get(3).getNewValue()));
		Assert.assertEquals(newCustomerDto.getAddrOne(), list.get(4).getNewValue());
		Assert.assertEquals(newCustomerDto.getAddrTwo(), list.get(5).getNewValue());
		Assert.assertEquals(newCustomerDto.getState(), Long.valueOf(list.get(7).getNewValue()));;
		Assert.assertEquals(newCustomerDto.getCity(), list.get(8).getNewValue());
		Assert.assertEquals(newCustomerDto.getPostelCode(), list.get(9).getNewValue());
		Assert.assertEquals(newCustomerDto.getPhoneCode(), list.get(10).getNewValue());
		Assert.assertEquals(newCustomerDto.getPhoneNo(), list.get(11).getNewValue());
		Assert.assertEquals(newCustomerDto.getHintQuestion1(), Long.valueOf(list.get(12).getNewValue()));
		Assert.assertEquals(newCustomerDto.getAnswer1(), list.get(13).getNewValue());
		Assert.assertEquals(newCustomerDto.getPassword(), list.get(14).getNewValue());
		
		
		
	}
	
	@Test
	public void createAuditMerchantTest() throws WalletException{
		MerchantDto oldMerchantDto = DataPreparation.getOldMerchantDto();
		MerchantDto newMerchantDto = DataPreparation.getNewMerchantDto();
		Audit audit=null;
		audit = auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.MODULE_MERCHANT_UPDATE, AuditTrailConstrain.STATUS_UPDATE, "AuditMerchantTest@gmail.com", GlobalLitterals.MERCHANT_USER_TYPE, oldMerchantDto, newMerchantDto);
		Assert.assertNotNull( audit );
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, audit.getAuthId());
		Assert.assertEquals(AuditTrailConstrain.MODULE_MERCHANT_UPDATE, audit.getModuleName());
		Assert.assertEquals(AuditTrailConstrain.STATUS_UPDATE, audit.getStatus());
		Assert.assertEquals("AuditMerchantTest@gmail.com", audit.getEmailId());
		Assert.assertEquals(GlobalLitterals.MERCHANT_USER_TYPE, audit.getUserType());
		Assert.assertNotNull( audit.getCreationDate() );
		
		List<AuditField> list = audit.getAuditFields();
		Assert.assertEquals(oldMerchantDto.getFirstName(), list.get(0).getOldValue());
		Assert.assertEquals(oldMerchantDto.getLastName(), list.get(1).getOldValue());
		Assert.assertEquals(oldMerchantDto.getCountryBI(), Long.valueOf(list.get(2).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getCountryBO(), Long.valueOf(list.get(3).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getAddress1BI(), list.get(4).getOldValue());
		Assert.assertEquals(oldMerchantDto.getAddress1BO(), list.get(5).getOldValue());
		Assert.assertEquals(oldMerchantDto.getAddress2BI(), list.get(6).getOldValue());
		Assert.assertEquals(oldMerchantDto.getAddress2BO(), list.get(7).getOldValue());
		Assert.assertEquals(oldMerchantDto.getStateorRegionBI(), Long.valueOf(list.get(8).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getStateOrRegionBO(), Long.valueOf(list.get(9).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getCityOrTownBI(), list.get(10).getOldValue());
		Assert.assertEquals(oldMerchantDto.getCityOrTownBO(), list.get(11).getOldValue());
		Assert.assertEquals(oldMerchantDto.getPostalCodeBI(), list.get(12).getOldValue());
		Assert.assertEquals(oldMerchantDto.getPostalCodeBO(), list.get(13).getOldValue());
		Assert.assertEquals(oldMerchantDto.getPhoneCountryCode1(), list.get(14).getOldValue());
		Assert.assertEquals(oldMerchantDto.getPhone(), list.get(15).getOldValue());
		Assert.assertEquals(oldMerchantDto.getPhoneNumber(), list.get(16).getOldValue());
		Assert.assertEquals(oldMerchantDto.getQuestion1(), Long.valueOf(list.get(17).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getHint1(), list.get(18).getOldValue());
		Assert.assertEquals(oldMerchantDto.getBusinessCategory(), Long.valueOf(list.get(19).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getBusinessEstablishedMonth(), list.get(20).getOldValue());
		Assert.assertEquals(oldMerchantDto.getBusinessEstablishedYear(), list.get(21).getOldValue());
		Assert.assertEquals(oldMerchantDto.getBusinessLegalname(), list.get(22).getOldValue());
		Assert.assertEquals(oldMerchantDto.getCurrency(), Long.valueOf(list.get(23).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getHighestMonthlyVolume(), Long.valueOf(list.get(24).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getOwnerType(), Long.valueOf(list.get(25).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getPercentageOfAnnualRevenueFromOnlineSales(), Long.valueOf(list.get(26).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getSubCategory(), Long.valueOf(list.get(27).getOldValue()));
		Assert.assertEquals(oldMerchantDto.getWebsite(), list.get(28).getOldValue());
		//Assert.assertEquals(oldMerchantDto.getAverageTransactionAmount(), Long.valueOf(list.get(29).getOldValue()));
		
		Assert.assertEquals(newMerchantDto.getFirstName(), list.get(0).getNewValue());
		Assert.assertEquals(newMerchantDto.getLastName(), list.get(1).getNewValue());
		Assert.assertEquals(newMerchantDto.getCountryBI(), Long.valueOf(list.get(2).getNewValue()));
		Assert.assertEquals(newMerchantDto.getCountryBO(), Long.valueOf(list.get(3).getNewValue()));
		Assert.assertEquals(newMerchantDto.getAddress1BI(), list.get(4).getNewValue());
		Assert.assertEquals(newMerchantDto.getAddress1BO(), list.get(5).getNewValue());
		Assert.assertEquals(newMerchantDto.getAddress2BI(), list.get(6).getNewValue());
		Assert.assertEquals(newMerchantDto.getAddress2BO(), list.get(7).getNewValue());
		Assert.assertEquals(newMerchantDto.getStateorRegionBI(), Long.valueOf(list.get(8).getNewValue()));
		Assert.assertEquals(newMerchantDto.getStateOrRegionBO(), Long.valueOf(list.get(9).getNewValue()));
		Assert.assertEquals(newMerchantDto.getCityOrTownBI(), list.get(10).getNewValue());
		Assert.assertEquals(newMerchantDto.getCityOrTownBO(), list.get(11).getNewValue());
		Assert.assertEquals(newMerchantDto.getPostalCodeBI(), list.get(12).getNewValue());
		Assert.assertEquals(newMerchantDto.getPostalCodeBO(), list.get(13).getNewValue());
		Assert.assertEquals(newMerchantDto.getPhoneCountryCode1(), list.get(14).getNewValue());
		Assert.assertEquals(newMerchantDto.getPhone(), list.get(15).getNewValue());
		Assert.assertEquals(newMerchantDto.getPhoneNumber(), list.get(16).getNewValue());
		Assert.assertEquals(newMerchantDto.getQuestion1(), Long.valueOf(list.get(17).getNewValue()));
		Assert.assertEquals(newMerchantDto.getHint1(), list.get(18).getNewValue());
		Assert.assertEquals(newMerchantDto.getBusinessCategory(), Long.valueOf(list.get(19).getNewValue()));
		Assert.assertEquals(newMerchantDto.getBusinessEstablishedMonth(), list.get(20).getNewValue());
		Assert.assertEquals(newMerchantDto.getBusinessEstablishedYear(), list.get(21).getNewValue());
		Assert.assertEquals(newMerchantDto.getBusinessLegalname(), list.get(22).getNewValue());
		Assert.assertEquals(newMerchantDto.getCurrency(), Long.valueOf(list.get(23).getNewValue()));
		Assert.assertEquals(newMerchantDto.getHighestMonthlyVolume(), Long.valueOf(list.get(24).getNewValue()));
		Assert.assertEquals(newMerchantDto.getOwnerType(), Long.valueOf(list.get(25).getNewValue()));
		Assert.assertEquals(newMerchantDto.getPercentageOfAnnualRevenueFromOnlineSales(), Long.valueOf(list.get(26).getNewValue()));
		Assert.assertEquals(newMerchantDto.getSubCategory(), Long.valueOf(list.get(27).getNewValue()));
		Assert.assertEquals(newMerchantDto.getWebsite(), list.get(28).getNewValue());
		//Assert.assertEquals(newMerchantDto.getAverageTransactionAmount(), Long.valueOf(list.get(29).getNewValue()));
		
	}
	
	@Test
	public void createAuditAdminTest() throws WalletException{
		AdminUserDto oldAdminUserDto = DataPreparation.getOldAdminUserDto();
		AdminUserDto newAdminUserDto = DataPreparation.getNewAdminUserDto();
		Audit audit=null;
		audit = auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.MODULE_ADMIN_REGISTRATION, AuditTrailConstrain.STATUS_REGISTRATION, "AuditAdminTest@gmail.com", GlobalLitterals.ADMIN_USER_TYPE, oldAdminUserDto, newAdminUserDto);
		Assert.assertNotNull( audit );
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, audit.getAuthId());
		Assert.assertEquals(AuditTrailConstrain.MODULE_ADMIN_REGISTRATION, audit.getModuleName());
		Assert.assertEquals(AuditTrailConstrain.STATUS_REGISTRATION, audit.getStatus());
		Assert.assertEquals("AuditAdminTest@gmail.com", audit.getEmailId());
		Assert.assertEquals(GlobalLitterals.ADMIN_USER_TYPE, audit.getUserType());
		Assert.assertNotNull( audit.getCreationDate() );
		
		List<AuditField> list = audit.getAuditFields();
		Assert.assertEquals(oldAdminUserDto.getFirstName(), list.get(0).getOldValue());
		Assert.assertEquals(oldAdminUserDto.getLastName(), list.get(1).getOldValue());
		Assert.assertEquals(oldAdminUserDto.getCountryId(), Long.valueOf(list.get(2).getOldValue()));
		Assert.assertEquals(oldAdminUserDto.getAddressOne(), list.get(3).getOldValue());
		Assert.assertEquals(oldAdminUserDto.getAddressTwo(), list.get(4).getOldValue());
		Assert.assertEquals(oldAdminUserDto.getStateId(), Long.valueOf(list.get(5).getOldValue()));
		Assert.assertEquals(oldAdminUserDto.getCity(), list.get(6).getOldValue());
		Assert.assertEquals(oldAdminUserDto.getZipcode(), list.get(7).getOldValue());
		Assert.assertEquals(oldAdminUserDto.getPhoneCode(), list.get(8).getOldValue());
		Assert.assertEquals(oldAdminUserDto.getPhoneNo(), list.get(9).getOldValue());
		Assert.assertEquals(oldAdminUserDto.getRoleId(), Long.valueOf(list.get(10).getOldValue()));
		Assert.assertEquals(oldAdminUserDto.getRoleName(), list.get(11).getOldValue());
		
		Assert.assertEquals(newAdminUserDto.getFirstName(), list.get(0).getNewValue());
		Assert.assertEquals(newAdminUserDto.getLastName(), list.get(1).getNewValue());
		Assert.assertEquals(newAdminUserDto.getCountryId(), Long.valueOf(list.get(2).getNewValue()));
		Assert.assertEquals(newAdminUserDto.getAddressOne(), list.get(3).getNewValue());
		Assert.assertEquals(newAdminUserDto.getAddressTwo(), list.get(4).getNewValue());
		Assert.assertEquals(newAdminUserDto.getStateId(), Long.valueOf(list.get(5).getNewValue()));
		Assert.assertEquals(newAdminUserDto.getCity(), list.get(6).getNewValue());
		Assert.assertEquals(newAdminUserDto.getZipcode(), list.get(7).getNewValue());
		Assert.assertEquals(newAdminUserDto.getPhoneCode(), list.get(8).getNewValue());
		Assert.assertEquals(newAdminUserDto.getPhoneNo(), list.get(9).getNewValue());
		Assert.assertEquals(newAdminUserDto.getRoleId(), Long.valueOf(list.get(10).getNewValue()));
		Assert.assertEquals(newAdminUserDto.getRoleName(), list.get(11).getNewValue());
	}
	
	@Test
	public void createAuditRoleTest() throws WalletException{
		Role oldRole = DataPreparation.getOldRole();
		Role newRole = DataPreparation.getNewRole();		
		Audit audit=null;
		audit = auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.MODULE_ROLE_CREATION, AuditTrailConstrain.STATUS_CREATE, "AuditRoleTest@gmail.com", GlobalLitterals.ADMIN_USER_TYPE, oldRole, newRole);
		Assert.assertNotNull( audit );
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, audit.getAuthId());
		Assert.assertEquals(AuditTrailConstrain.MODULE_ROLE_CREATION, audit.getModuleName());
		Assert.assertEquals(AuditTrailConstrain.STATUS_CREATE, audit.getStatus());
		Assert.assertEquals("AuditRoleTest@gmail.com", audit.getEmailId());
		Assert.assertEquals(GlobalLitterals.ADMIN_USER_TYPE, audit.getUserType());
		Assert.assertNotNull( audit.getCreationDate() );

		List<AuditField> list = audit.getAuditFields();
		Assert.assertEquals(oldRole.getName(), list.get(0).getOldValue());
		Assert.assertEquals(oldRole.getDescription(), list.get(1).getOldValue());
		Assert.assertEquals(oldRole.getMenuDetails(), list.get(2).getOldValue());
		
		Assert.assertEquals(newRole.getName(), list.get(0).getNewValue());
		Assert.assertEquals(newRole.getDescription(), list.get(1).getNewValue());
		Assert.assertEquals(newRole.getMenuDetails(), list.get(2).getNewValue());
		
	}
	
	@Test
	public void createAuditAccountTest() throws WalletException{
		AccountDto oldAccountDto = DataPreparation.getOldAccountDto();
		AccountDto newAccountDto = DataPreparation.getNewAccountDto();
		Audit audit=null;
		audit = auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.ACCOUNT_MODULE_BANK_CREATE, AuditTrailConstrain.STATUS_CREATE, "AuditAccountTest@gmail.com", GlobalLitterals.ADMIN_USER_TYPE, oldAccountDto, newAccountDto);
		Assert.assertNotNull( audit );
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, audit.getAuthId());
		Assert.assertEquals(AuditTrailConstrain.ACCOUNT_MODULE_BANK_CREATE, audit.getModuleName());
		Assert.assertEquals(AuditTrailConstrain.STATUS_CREATE, audit.getStatus());
		Assert.assertEquals("AuditAccountTest@gmail.com", audit.getEmailId());
		Assert.assertEquals(GlobalLitterals.ADMIN_USER_TYPE, audit.getUserType());
		Assert.assertNotNull( audit.getCreationDate() );
		
		List<AuditField> list = audit.getAuditFields();
		Assert.assertEquals(oldAccountDto.getDefaultAccount(), Boolean.valueOf(list.get(0).getOldValue()));
		Assert.assertEquals(oldAccountDto.getAccountHolderName(), list.get(1).getOldValue());
		Assert.assertEquals(oldAccountDto.getAccountNumber(), list.get(2).getOldValue());
		Assert.assertEquals(oldAccountDto.getAtype(), list.get(3).getOldValue());
		Assert.assertEquals(oldAccountDto.getBankAccountType(), Long.valueOf(list.get(4).getOldValue()));
		Assert.assertEquals(oldAccountDto.getBankAddress(), list.get(5).getOldValue());
		Assert.assertEquals(oldAccountDto.getBankName(), list.get(6).getOldValue());
		Assert.assertEquals(oldAccountDto.getCardExpairyDate(), list.get(7).getOldValue());
		Assert.assertEquals(oldAccountDto.getCardType(), Long.valueOf(list.get(8).getOldValue()));
		Assert.assertEquals(oldAccountDto.getCardNumber(), list.get(9).getOldValue());
		Assert.assertEquals(oldAccountDto.getCountry(), Long.valueOf(list.get(10).getOldValue()));
		Assert.assertEquals(oldAccountDto.getDeletedStatus(), Long.valueOf(list.get(11).getOldValue()));
		Assert.assertEquals(oldAccountDto.getSortCode(), list.get(12).getOldValue());
		
		Assert.assertEquals(newAccountDto.getDefaultAccount(), Boolean.valueOf(list.get(0).getNewValue()));
		Assert.assertEquals(newAccountDto.getAccountHolderName(), list.get(1).getNewValue());
		Assert.assertEquals(newAccountDto.getAccountNumber(), list.get(2).getNewValue());
		Assert.assertEquals(newAccountDto.getAtype(), list.get(3).getNewValue());
		Assert.assertEquals(newAccountDto.getBankAccountType(), Long.valueOf(list.get(4).getNewValue()));
		Assert.assertEquals(newAccountDto.getBankAddress(), list.get(5).getNewValue());
		Assert.assertEquals(newAccountDto.getBankName(), list.get(6).getNewValue());
		Assert.assertEquals(newAccountDto.getCardExpairyDate(), list.get(7).getNewValue());
		Assert.assertEquals(newAccountDto.getCardType(), Long.valueOf(list.get(8).getNewValue()));
		Assert.assertEquals(newAccountDto.getCardNumber(), list.get(9).getNewValue());
		Assert.assertEquals(newAccountDto.getCountry(), Long.valueOf(list.get(10).getNewValue()));
		Assert.assertEquals(newAccountDto.getDeletedStatus(), Long.valueOf(list.get(11).getNewValue()));
		Assert.assertEquals(newAccountDto.getSortCode(), list.get(12).getNewValue());
	}
	
	@Test
	public void getAuditTrailTest() throws WalletException{
		
		CustomerDto oldCustomerDto = DataPreparation.getOldCustomerDto();
		CustomerDto newCustomerDto = DataPreparation.getNewCustomerDto();
		Audit audit = auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.MODULE_CUSTOMER_UPDATE, AuditTrailConstrain.STATUS_UPDATE, "getAuditTrailTest@gmail.com", GlobalLitterals.CUSTOMER_USER_TYPE, oldCustomerDto, newCustomerDto);
		
		Audit adt = auditTrailService.getAuditTrail(audit.getId());
		
		Assert.assertNotNull( audit );
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, adt.getAuthId());
		Assert.assertEquals(AuditTrailConstrain.MODULE_CUSTOMER_UPDATE, adt.getModuleName());
		Assert.assertEquals(AuditTrailConstrain.STATUS_UPDATE, adt.getStatus());
		Assert.assertEquals("getAuditTrailTest@gmail.com", adt.getEmailId());
		Assert.assertEquals(GlobalLitterals.CUSTOMER_USER_TYPE, adt.getUserType());
		Assert.assertNotNull( audit.getCreationDate() );
		
		List<AuditField> list = adt.getAuditFields();
		Assert.assertEquals(oldCustomerDto.getFirstName(), list.get(0).getOldValue());
		Assert.assertEquals(oldCustomerDto.getLastName(), list.get(1).getOldValue());
		Assert.assertEquals(oldCustomerDto.getTitle(), Long.valueOf(list.get(2).getOldValue()));
		Assert.assertEquals(oldCustomerDto.getCountry(), Long.valueOf(list.get(3).getOldValue()));
		Assert.assertEquals(oldCustomerDto.getAddrOne(), list.get(4).getOldValue());
		Assert.assertEquals(oldCustomerDto.getAddrTwo(), list.get(5).getOldValue());
		Assert.assertEquals(oldCustomerDto.getState(), Long.valueOf(list.get(7).getOldValue()));
		Assert.assertEquals(oldCustomerDto.getCity(), list.get(8).getOldValue());
		Assert.assertEquals(oldCustomerDto.getPostelCode(), list.get(9).getOldValue());
		Assert.assertEquals(oldCustomerDto.getPhoneCode(), list.get(10).getOldValue());
		Assert.assertEquals(oldCustomerDto.getPhoneNo(), list.get(11).getOldValue());
		Assert.assertEquals(oldCustomerDto.getHintQuestion1(), Long.valueOf(list.get(12).getOldValue()));
		Assert.assertEquals(oldCustomerDto.getAnswer1(), list.get(13).getOldValue());
		Assert.assertEquals(oldCustomerDto.getPassword(), list.get(14).getOldValue());
		
		Assert.assertEquals(newCustomerDto.getFirstName(), list.get(0).getNewValue());
		Assert.assertEquals(newCustomerDto.getLastName(), list.get(1).getNewValue());
		Assert.assertEquals(newCustomerDto.getTitle(), Long.valueOf(list.get(2).getNewValue()));
		Assert.assertEquals(newCustomerDto.getCountry(), Long.valueOf(list.get(3).getNewValue()));
		Assert.assertEquals(newCustomerDto.getAddrOne(), list.get(4).getNewValue());
		Assert.assertEquals(newCustomerDto.getAddrTwo(), list.get(5).getNewValue());
		Assert.assertEquals(newCustomerDto.getState(), Long.valueOf(list.get(7).getNewValue()));;
		Assert.assertEquals(newCustomerDto.getCity(), list.get(8).getNewValue());
		Assert.assertEquals(newCustomerDto.getPostelCode(), list.get(9).getNewValue());
		Assert.assertEquals(newCustomerDto.getPhoneCode(), list.get(10).getNewValue());
		Assert.assertEquals(newCustomerDto.getPhoneNo(), list.get(11).getNewValue());
		Assert.assertEquals(newCustomerDto.getHintQuestion1(), Long.valueOf(list.get(12).getNewValue()));
		Assert.assertEquals(newCustomerDto.getAnswer1(), list.get(13).getNewValue());
		Assert.assertEquals(newCustomerDto.getPassword(), list.get(14).getNewValue());
	}
	
	@Test
	public void getAllAuditObjectsTest() throws WalletException{
		
		CustomerDto oldCustomerDto = DataPreparation.getOldCustomerDto();
		CustomerDto newCustomerDto = DataPreparation.getNewCustomerDto();
		auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.MODULE_CUSTOMER_EDIT, AuditTrailConstrain.STATUS_EDIT, "getAllAuditObjectsTest1@gmail.com", GlobalLitterals.CUSTOMER_USER_TYPE, oldCustomerDto, newCustomerDto);
		
		AccountDto oldAccountDto = DataPreparation.getOldAccountDto();
		AccountDto newAccountDto = DataPreparation.getNewAccountDto();
		auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.ACCOUNT_MODULE_BANK_CREATE, AuditTrailConstrain.STATUS_CREATE, "getAllAuditObjectsTest2@gmail.com", GlobalLitterals.ADMIN_USER_TYPE, oldAccountDto, newAccountDto);
		
		List<Audit> adtList = auditTrailService.getAllAuditTrailObjects();
		Assert.assertNotNull( adtList );
		Assert.assertEquals(2, adtList.size());
	}
    
	@Test
	public void createAuditTrailForRoleTest()throws WalletException {
		Role oldRoleDto = DataPreparation.getOldRole();
		Role newRoleDto = DataPreparation.getNewRole();
		Audit audit = auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.MODULE_CUSTOMER_UPDATE, AuditTrailConstrain.STATUS_UPDATE, "getAuditTrailRoleTest@gmail.com", GlobalLitterals.CUSTOMER_USER_TYPE, oldRoleDto, newRoleDto);
		Audit adt = auditTrailService.createAuditTrailForRole(AuditTrailConstrain.AUTHINTICATION_ID ,AuditTrailConstrain.MODULE_ROLE_CREATION ,AuditTrailConstrain.STATUS_UPDATE , 
				"getAuditTrailRoleTest@gmail.com" ,GlobalLitterals.CUSTOMER_USER_TYPE , 2L );
	 
		Assert.assertNotNull( audit );
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, adt.getAuthId());
		Assert.assertEquals(AuditTrailConstrain.MODULE_ROLE_CREATION, adt.getModuleName());
		Assert.assertEquals(AuditTrailConstrain.STATUS_UPDATE, adt.getStatus());
		Assert.assertEquals("getAuditTrailRoleTest@gmail.com", adt.getEmailId());
		Assert.assertEquals(GlobalLitterals.CUSTOMER_USER_TYPE, adt.getUserType());	 
	}
	
	@Test 
	public void createAuditTrailForAccountCloserTest()throws WalletException {
		Long oldAccountCloserStatus = 1L ;
		Long newAccountCloserStatus = 2L;
		
		Audit audit = auditTrailService.createAuditTrailForAccountCloser(AuditTrailConstrain.AUTHINTICATION_ID, 
				AuditTrailConstrain.MODULE_ACCOUNT_CLOSER_UPDATE, AuditTrailConstrain.STATUS_UPDATE, "getAuditTrailTest@gmail.com", GlobalLitterals.CUSTOMER_USER_TYPE, 
				oldAccountCloserStatus , newAccountCloserStatus);
		Assert.assertNotNull(audit);
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, audit.getAuthId());
		Assert.assertEquals(AuditTrailConstrain.MODULE_ACCOUNT_CLOSER_UPDATE, audit.getModuleName());
	}
	
    
	@Test
	public void createAuditTrailForRequestMoneyTest()throws WalletException {
		RequestMoneyDto oldRequestMoneyDto = DataPreparation.getOldRequestMoneyStatus();
		RequestMoneyDto newRequestMoneyDto = DataPreparation.getNewRequestMoneyStatus();
		
		Audit audit = auditTrailService.createAuditTrail(AuditTrailConstrain.AUTHINTICATION_ID, AuditTrailConstrain.MODULE_CUSTOMER_UPDATE, AuditTrailConstrain.STATUS_UPDATE, "getAuditTrailRoleTest@gmail.com", 
				GlobalLitterals.CUSTOMER_USER_TYPE, oldRequestMoneyDto, newRequestMoneyDto);
		Audit adt = auditTrailService.createAuditTrailForRequestMoney(AuditTrailConstrain.AUTHINTICATION_ID ,
				AuditTrailConstrain.MODULE_REQUEST_MONEY_ACCEPT , AuditTrailConstrain.STATUS_UPDATE, "getAuditTrailRoleTest@gmail.com" , GlobalLitterals.CUSTOMER_USER_TYPE , oldRequestMoneyDto.getStatus(), newRequestMoneyDto.getStatus());
	 
		Assert.assertNotNull( audit );
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, adt.getAuthId());
		Assert.assertEquals(AuditTrailConstrain.MODULE_REQUEST_MONEY_ACCEPT, adt.getModuleName());
		Assert.assertEquals(AuditTrailConstrain.STATUS_UPDATE, adt.getStatus());
		Assert.assertEquals("getAuditTrailRoleTest@gmail.com", adt.getEmailId());
		Assert.assertEquals(GlobalLitterals.CUSTOMER_USER_TYPE, adt.getUserType());	 
	}
	
	@Test
	public void createAuditTrailForEmailVerificationTest()throws WalletException {
		Audit audit = auditTrailService.createAuditTrailForEmailVarification(AuditTrailConstrain.AUTHINTICATION_ID ,
				AuditTrailConstrain.MODULE_CUSTOMER_EMAILVERIFICATION_UPDATE, AuditTrailConstrain.STATUS_UPDATE, "getEmailVerification@gmail.com",GlobalLitterals.CUSTOMER_USER_TYPE);
	      
		Assert.assertNotNull( audit );
		Assert.assertEquals(AuditTrailConstrain.AUTHINTICATION_ID, audit.getAuthId());
		Assert.assertEquals(GlobalLitterals.CUSTOMER_USER_TYPE, audit.getUserType());	
	}
	
}
