package com.tarang.ewallet.accounts.business;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * 
 */

/**
 * @author  : kedarnathd
 * @date    : July 26, 2016
 * @time    : 4:27:02 PM
 * @version : 1.0.0
 * @comments: 
 *
 */

@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class PrepaidCardServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	
	
}
