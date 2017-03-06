/**
 * 
 */
package com.tarang.ewallet.customer.business;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.customer.util.CustomerUtil;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Hints;


/**
 * @author vasanthar
 *
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
	"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class CustomerUtilTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Test
	public void getHintsTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();		
		Hints hints = CustomerUtil.getHints(dto);
		Assert.assertNotNull( hints );
		Assert.assertEquals(dto.getAnswer1(),hints.getHintAnswer1());		
	}
	
	@Test
	public void getAddressTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();		
		Address addr = CustomerUtil.getAddress(dto);
		Assert.assertNotNull( addr );
		Assert.assertEquals(dto.getCity(),addr.getCity());		
	}
	
	@Test
	public void updateHintsTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();	
		Hints hints = new Hints();
		CustomerUtil.updateHints(dto, hints);
		Assert.assertNotNull( hints );
		Assert.assertEquals(dto.getAnswer1(),hints.getHintAnswer1());		
	}
	
	@Test
	public void updateAuthenticationTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();		
		Authentication auth = new Authentication();
		Authentication authentication = CustomerUtil.updateAuthentication(dto, auth);
		Assert.assertEquals(dto.getEmailId(),authentication.getEmailId());		
	}
	
	@Test
	public void updateAddressTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();	
		Address address = new Address();
		CustomerUtil.updateAddress(dto, address);
		Assert.assertNotNull( address );
		Assert.assertEquals(dto.getCity(),address.getCity());		
	}
}
