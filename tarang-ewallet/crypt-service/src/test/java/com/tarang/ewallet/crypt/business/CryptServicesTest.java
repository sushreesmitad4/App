package com.tarang.ewallet.crypt.business;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.crypt.util.EncryptConstants;
import com.tarang.ewallet.crypt.util.Encryption;
import com.tarang.ewallet.crypt.util.EncryptionFactory;


/**
* @author  : prasadj
* @date    : Oct 17, 2012
* @time    : 8:43:37 AM
* @version : 1.0.0
* @comments: 
*
*/
@ContextConfiguration(locations = { "classpath*:/wallettest-applicationContext.xml", 
		"classpath*:/wallet-applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
public class CryptServicesTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private CryptService cryptService;

	@Test
	public void cryptTest() throws Exception{
		String data = "abc"; 
		String encData = cryptService.encryptData(data);
		String decData = cryptService.decryptData(encData);
		Assert.assertNotNull( encData );
		Assert.assertNotSame(data, encData);
		Assert.assertNotSame(encData, decData);
		Assert.assertEquals(data, decData);
	}

	@Test
	public void encriptTest() throws Exception {
		String key2 = "Tarang1234567890";
		String key1 = "xxxxxxxxxxxxxxxx";
		String data = "abc abc";
		Encryption encryption = EncryptionFactory.getEncryptionInstance(EncryptConstants.ALGTHM_TYPE_AES);
		
		String encKey1 = encryption.encrypt(key1, key2);
		String decKey1 = encryption.decrypt(encKey1, key2);

		Assert.assertNotSame(key1, encKey1);
		Assert.assertNotSame(encKey1, decKey1);
		Assert.assertEquals(key1, decKey1);

		String encdata = encryption.encrypt(data, key1);
		String decdata = encryption.decrypt(encdata, key1);
		
		Assert.assertNotSame(data, encdata);
		Assert.assertNotSame(encdata, decdata);
		Assert.assertEquals(data, decdata);

	}
	
	@Test
	public void decriptTest() throws Exception {
		String encdata = "EgaaRySc3ZX8gJ7hviHBdg=="; 
		String encKey1 = "F2nSo8xEOCOnPAG5MacXl83tSGj4uI8qV2Lawqo4zpo=";
		String key2 = "Tarang1234567890";
		Encryption encryption = EncryptionFactory.getEncryptionInstance(EncryptConstants.ALGTHM_TYPE_AES);
		
		String decKey1 = encryption.decrypt(encKey1, key2);
		
		String decdata = encryption.decrypt(encdata, decKey1);

		System.out.println("data: " + decdata);
		
	}
	
	@Test
	public void cryptTest1() throws Exception{
		String encData = cryptService.decryptData("6ZL9t8ivfTK3ATPIv5SLRg==");
		System.out.println(" ======>  "+encData);
	}
}
