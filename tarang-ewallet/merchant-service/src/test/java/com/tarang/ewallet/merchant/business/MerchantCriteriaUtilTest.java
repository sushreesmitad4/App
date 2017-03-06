package com.tarang.ewallet.merchant.business;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.merchant.util.MerchantUserCriteriaUtil;
import com.tarang.ewallet.util.QueryFilter;


@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml",
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class MerchantCriteriaUtilTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private String STATUS = "status";
	private String CREATIONDATE = "creationDate";
	private static final Logger LOG = Logger
			.getLogger(MerchantServicesTest.class);

	@Test
	public void updateCountTest() {
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"legalName\",\"op\":\"cn\",\"data\":\"abc\"},"
				+ "{\"field\":\"emailId\",\"op\":\"eq\",\"data\":\"abc@c.com\"},{\"field\":\"deleted\",\"op\":\"eq\",\"data\":\"false\"},"
				+ "{\"field\":\"active\",\"op\":\"eq\",\"data\":\"true\"}]}";
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("legalName");
		qf.setSord("desc");
		MerchantUserCriteriaUtil.updateCount(12, qf);

	}

	@Test
	public void getMerchantSearchQueryTest1() {

		String searchString = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"legalName\",\"op\":\"cn\",\"data\":\"abc\"},"
				+ "{\"field\":\"emailId\",\"op\":\"eq\",\"data\":\"abc@c.com\"},{\"field\":\"deleted\",\"op\":\"eq\",\"data\":\"false\"},"
				+ "{\"field\":\"active\",\"op\":\"eq\",\"data\":\"true\"}]}";
		String exptQString = "select new com.tarang.ewallet.dto.UserDto(mr.id, bi.legalName, au.emailId, au.active, "
				+ "au.status, au.blocked, au.creationDate)  "
				+ "from Merchant mr, Authentication au, BusinessInformation bi "
				+ "where (mr.authenticationId=au.id and mr.businessInformation=bi.id and au.emailVarification=true)  "
				+ "and ( upper(bi.legalName) like upper('%abc%')  and au.emailId = 'abc@c.com'  )  order by bi.legalName desc";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("legalName");
		qf.setSord("desc");

		String queryString = MerchantUserCriteriaUtil.getMerchantSearchQuery(
				qf, null, null, null, null, null);

		LOG.debug("expected1:\n" + exptQString + "|");
		LOG.debug("output1:\n" + queryString + "|");

		Assert.assertEquals(exptQString, queryString);

	}

	@Test
	public void getMerchantSearchCountQueryTest1() {
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"legalName\",\"op\":\"cn\",\"data\":\"abc\"},"
				+ "{\"field\":\"emailId\",\"op\":\"eq\",\"data\":\"abc@c.com\"},{\"field\":\"deleted\",\"op\":\"eq\",\"data\":\"false\"},"
				+ "{\"field\":\"active\",\"op\":\"eq\",\"data\":\"true\"}]}";
		String exptCString = "select count(*)  from Merchant mr, Authentication au, BusinessInformation bi "
				+ "where (mr.authenticationId=au.id and mr.businessInformation=bi.id and au.emailVarification=true)  "
				+ "and ( upper(bi.legalName) like upper('%abc%')  and au.emailId = 'abc@c.com'  ) ";
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("legalName");
		qf.setSord("desc");
		String countString = MerchantUserCriteriaUtil
				.getMerchantSearchCountQuery(qf, null, null, null, null, null);
		LOG.debug("expected1:\n" + exptCString + "|");
		LOG.debug("output1:\n" + countString + "|");
		Assert.assertEquals(exptCString, countString);
	}

	@Test
	public void getFieldTypeTest1() {
		int type = MerchantUserCriteriaUtil.getFieldType(STATUS);
		Assert.assertNotNull(type);
		Assert.assertEquals(type, 1);
	}

	@Test
	public void getFieldTypeTest2() {
		int type = MerchantUserCriteriaUtil.getFieldType(CREATIONDATE);
		Assert.assertNotNull(type);
		Assert.assertEquals(type, 3);
	}

}
