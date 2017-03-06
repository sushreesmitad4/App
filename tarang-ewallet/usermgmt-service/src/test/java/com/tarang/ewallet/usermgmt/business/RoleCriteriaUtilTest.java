package com.tarang.ewallet.usermgmt.business;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.business.UserService;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.RoleCriteriaUtil;


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
public class RoleCriteriaUtilTest extends
AbstractTransactionalJUnit4SpringContextTests{
	
	private static final Logger LOG = Logger.getLogger(RoleCriteriaUtilTest.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	HibernateOperations hibernateOperations;
	
	@Test
	public void updateCountTest() throws WalletException{
		
		String roleName = "Super Admin";
		String roleDescription = "Super Admin Role access to all menus";
		String menuDetails = "1:2:3:4:5:6:7:8:9:10:11:12:13:14:15:16:17:18:19:20:21:22";
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails(menuDetails);
		
		Role r = userService.createRole(role);
		LOG.debug("Role Id: "+r.getId());
		
		QueryFilter qf = new QueryFilter();
		qf.setRows(1);
		qf.setPage(1);
		
		Integer total = getRolesCount(qf);
		RoleCriteriaUtil.updateCount(total, qf);
		
		Assert.assertNotNull(total);
		Assert.assertEquals(new Integer(1), total);
		RoleCriteriaUtil.updateCount(total, qf);
		Assert.assertNotNull(qf.getRows());
		Assert.assertNotNull(qf.getPage());
		
		Assert.assertEquals(new Integer(1), qf.getTotal());
		Assert.assertEquals(new Integer(1), qf.getRows());
		Assert.assertEquals(new Integer(1), qf.getPage());
	}
	
	@Test
	public void getRoleSearchQueryTest(){
		String searchString = "{\"groupOp\":\"OR\",\"rules\":[{\"field\":\"name\",\"op\":\"cn\",\"data\":\"User\"}, {\"field\":\"name\",\"op\":\"eq\",\"data\":\"User Management\"}]}";
		String exptQString = "select r  from Role r where  ( upper(name) like upper('%User%')  or name = 'User Management'  )  order by r.name desc";
	
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(1);
		qf.setSidx("name");
		qf.setSord("desc");
	
		String queryString = RoleCriteriaUtil.getRoleSearchQuery(qf);
		Assert.assertEquals(exptQString, queryString);
		
	}
	
	@Test
	public void getRoleSearchCountQueryTest(){
		String searchString = "{\"groupOp\":\"OR\",\"rules\":[{\"field\":\"name\",\"op\":\"cn\",\"data\":\"User\"}, {\"field\":\"name\",\"op\":\"eq\",\"data\":\"User Management\"}]}";
		String exptCString = "select count(*)  from Role r where  ( upper(name) like upper('%User%')  or name = 'User Management'  ) ";
	
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(1);
		qf.setSidx("name");
		qf.setSord("desc");
	
		String countString = RoleCriteriaUtil.getRoleSearchCountQuery(qf);
		LOG.debug(countString);
		
		Assert.assertEquals(exptCString, countString);
	}
	private Integer getRolesCount(final QueryFilter qf) {
		return new Integer(hibernateOperations
				.find(RoleCriteriaUtil.getRoleSearchCountQuery(qf)).get(0)
				.toString());
	}
}
