/**
 * 
 */
package com.tarang.ewallet.customer.business;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.customer.util.CustomerUserCriteriaUtil;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.util.QueryFilter;


/**
 * @author vasanthar
 *
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
	"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class CustomerUserCriteriaUtilTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Test
	public void getFieldTypeTest() throws WalletException{
		int i = CustomerUserCriteriaUtil.getFieldType("status");
		Assert.assertNotNull( i );
		Assert.assertEquals(0,i);		
	}
	
	
	@Test
	public void getCustomerSearchQueryTest() throws Exception{
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("emailId");
		qf.setSord("desc");
		String str = CustomerUserCriteriaUtil.getCustomerSearchQuery(qf, "11/11/2012", DataPreparation.dateToString(new Date()), null, null, 4L);
		Assert.assertNotNull(str);
	}
	
	@Test
	public void getCustomerSearchCountQueryTest() throws Exception{
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("emailId");
		qf.setSord("desc");
		String str = CustomerUserCriteriaUtil.getCustomerSearchCountQuery(qf, "11/11/2012", DataPreparation.dateToString(new Date()), null, null, 4L);
		Assert.assertNotNull(str);
	}
	
	@Test
	public void updateCountTest() throws Exception{
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("emailId");
		qf.setSord("desc");
		CustomerUserCriteriaUtil.updateCount(1,qf);				
	}
		
}
