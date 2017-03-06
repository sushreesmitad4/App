/**
 * 
 */
package com.tarang.ewallet.transaction.business;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;


import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.transaction.business.FraudCheckService;


/**
 * @author vasanthar
 *
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class FraudCheckServicesTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
    private FraudCheckService fraudCheckService;
    
	@Test
	public void ipMultipleAccountsVelocityTest() throws WalletException{
		Boolean  flag = fraudCheckService.ipMultipleAccountsVelocity("172.30.66.177", new Date(), new Date(), 1);
		Assert.assertNotNull(flag);
	}
	
	@Test
	public void emailPatternCheckTest() throws WalletException{
		Boolean  flag = fraudCheckService.emailPatternCheck("rajasekhar@tarangtech.com",new Date(), new Date(), 1);
		Assert.assertNotNull(flag);
	}
	
	@Test
	public void domainCheckTest() throws WalletException{
		Boolean  flag = fraudCheckService.domainCheck("rajasekhar@tarangtech.com");
		Assert.assertNotNull(flag);
	}
	
	@Test
	public void merchantThresholdCheckTest() throws WalletException{
		Boolean  flag = fraudCheckService.merchantThresholdCheck(1L, 1, new Date(), new Date());
		Assert.assertNotNull(flag);
	}
	
	@Test
	public void IpCardBinGeoCheckTest() throws WalletException{
		Boolean  flag = fraudCheckService.ipCardBinGeoCheck("172.30.66.177", "1111");
		Assert.assertNotNull(flag);
	}
	
	@Test
	public void fundingSourcePatternCheckTest() throws WalletException{
		Boolean  flag = fraudCheckService.fundingSourcePatternCheck("111", new Date(), new Date(), 0);
		Assert.assertNotNull(flag);
	}
	
	@Test
	public void isLoginFromDifferentIpTest() throws WalletException{
		Boolean  flag = fraudCheckService.isLoginFromDifferentIp("172.30.66.177", 1L);
		Assert.assertNotNull(flag);
	}
	
	@Test
	public void fundingSourcePatternCheckCardTest() throws WalletException{
		Boolean  flag = fraudCheckService.fundingSourcePatternCheckCard("542496", new Date(), new Date(), 0);
		Assert.assertNotNull(flag);
	}
}
