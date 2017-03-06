package com.tarang.ewallet.usermgmt.business;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.util.AdminUtil;


/**
 * @author kedarnathd
 * @date    : Oct 17, 2012
 * @time    : 6:34:19 PM
 * @version : 1.0.0
 * @comments: 
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class AdminUtilTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private CryptService cryptService;

	@Test
	public void getPersonNameTest(){
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		PersonName person = AdminUtil.getPersonName(adminUserDto);
		Assert.assertNotNull(person);
		Assert.assertEquals(adminUserDto.getFirstName(),person.getFirstName());
		Assert.assertEquals(adminUserDto.getLastName(),person.getLastName());
	}
	
	@Test
	public void getAddressTest(){
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		Address address = AdminUtil.getAddress(adminUserDto);
		Assert.assertNotNull(address);
		Assert.assertEquals(adminUserDto.getAddressOne(),address.getAddressOne());
		Assert.assertEquals(adminUserDto.getAddressTwo(),address.getAddressTwo());
	}
	
	@Test
	public void getAuthentication() throws WalletException{
		String newPassword = CommonUtil.getRandomPassword();
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		Authentication authentication = new Authentication();
		authentication.setPassword(cryptService.encryptData(newPassword));
		authentication = AdminUtil.getAuthentication(adminUserDto, authentication);
		Assert.assertNotNull(authentication);
		Assert.assertEquals(adminUserDto.getEmailId(),authentication.getEmailId());
	}
	
	@Test
	public void papulatePersonNameTest(){
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		PersonName personName = AdminUtil.getPersonName(adminUserDto);
		Assert.assertNotNull(personName);
		Assert.assertEquals(adminUserDto.getFirstName(),personName.getFirstName());
		Assert.assertEquals(adminUserDto.getLastName(),personName.getLastName());
		
		AdminUserDto newAdminUserDto = new AdminUserDto();
		AdminUtil.papulatePersonName(newAdminUserDto, personName);
		Assert.assertNotNull(personName);
		Assert.assertEquals(newAdminUserDto.getFirstName(),personName.getFirstName());
		Assert.assertEquals(newAdminUserDto.getLastName(),personName.getLastName());
	}
	
	@Test
	public void papulateAddressTest(){
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		Address address = AdminUtil.getAddress(adminUserDto);
		Assert.assertNotNull(address);
		Assert.assertEquals(adminUserDto.getAddressOne(),address.getAddressOne());
		Assert.assertEquals(adminUserDto.getAddressTwo(),address.getAddressTwo());
		
		AdminUserDto newAdminUserDto = new AdminUserDto();
		AdminUtil.papulateAddress(newAdminUserDto, address);
		Assert.assertNotNull(address);
		Assert.assertEquals(newAdminUserDto.getAddressOne(),address.getAddressOne());
		Assert.assertEquals(newAdminUserDto.getAddressTwo(),address.getAddressTwo());
	}
	@Test
	public void papulateAuthenticationTest() throws WalletException{
		
		String newPassword = CommonUtil.getRandomPassword();
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		Authentication authentication = new Authentication();
		authentication.setPassword(cryptService.encryptData(newPassword));
		authentication = AdminUtil.getAuthentication(adminUserDto, authentication);
		Assert.assertNotNull(authentication);
		Assert.assertEquals(adminUserDto.getEmailId(),authentication.getEmailId());
		
		AdminUserDto newAdminUserDto = new AdminUserDto(); 
		AdminUtil.papulateAuthentication(newAdminUserDto, authentication);
		Assert.assertNotNull(authentication);
		Assert.assertEquals(newAdminUserDto.getEmailId(),authentication.getEmailId());
		
	}
	@Test
	public void preparePersonNameForUpdateTest(){
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		PersonName personName = AdminUtil.getPersonName(adminUserDto);
		Assert.assertNotNull(personName);
		Assert.assertEquals(adminUserDto.getFirstName(),personName.getFirstName());
		Assert.assertEquals(adminUserDto.getLastName(),personName.getLastName());
		
		AdminUserDto newAdminUserDto = new AdminUserDto();
		AdminUtil.preparePersonNameForUpdate(personName, newAdminUserDto);
		Assert.assertNotNull(personName);
		Assert.assertEquals(newAdminUserDto.getFirstName(),personName.getFirstName());
		Assert.assertEquals(newAdminUserDto.getLastName(),personName.getLastName());
	}
	@Test
	public void prepareAddressForUpdateTest(){
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		Address address = AdminUtil.getAddress(adminUserDto);
		Assert.assertNotNull(address);
		Assert.assertEquals(adminUserDto.getAddressOne(),address.getAddressOne());
		Assert.assertEquals(adminUserDto.getAddressTwo(),address.getAddressTwo());
		
		AdminUserDto newAdminUserDto = new AdminUserDto();
		AdminUtil.prepareAddressForUpdate(address, newAdminUserDto);
		Assert.assertNotNull(address);
		Assert.assertEquals(newAdminUserDto.getAddressOne(),address.getAddressOne());
		Assert.assertEquals(newAdminUserDto.getAddressTwo(),address.getAddressTwo());
	}
	@Test
	public void prepareAuthenticationForUpdateTest() throws WalletException{
		String newPassword = CommonUtil.getRandomPassword();
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		Authentication authentication = new Authentication();
		authentication.setPassword(cryptService.encryptData(newPassword));
		authentication = AdminUtil.getAuthentication(adminUserDto, authentication);
		Assert.assertNotNull(authentication);
		Assert.assertEquals(adminUserDto.getEmailId(),authentication.getEmailId());
		
		AdminUserDto newAdminUserDto = new AdminUserDto(); 
		AdminUtil.prepareAuthenticationForUpdate(authentication, newAdminUserDto);
		Assert.assertNotNull(authentication);
	}
}
