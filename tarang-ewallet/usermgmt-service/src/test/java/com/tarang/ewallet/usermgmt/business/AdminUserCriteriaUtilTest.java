package com.tarang.ewallet.usermgmt.business;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.business.UserService;
import com.tarang.ewallet.util.AdminUserCriteriaUtil;
import com.tarang.ewallet.util.QueryFilter;


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
public class AdminUserCriteriaUtilTest extends AbstractTransactionalJUnit4SpringContextTests {

	private static final Logger LOG = Logger.getLogger(AdminUserCriteriaUtilTest.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	HibernateOperations hibernateOperations;
	
	@Before
	public void prepareData() {
		// prepare data
	}
	
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

		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		adminUserDto.setRoleId(r.getId());
		adminUserDto = userService.createAdminUser(adminUserDto);
		Assert.assertNotNull(adminUserDto);
		Assert.assertEquals("super", adminUserDto.getFirstName());
		Assert.assertNotNull(adminUserDto.getRoleId());
		
		AdminUserDto adminUserDto1 = DataPreparation.prepareAdminUserDto1();
		adminUserDto1.setRoleId(r.getId());
		adminUserDto1 = userService.createAdminUser(adminUserDto1);
		Assert.assertNotNull(adminUserDto1);
		Assert.assertEquals("super", adminUserDto1.getFirstName());
		Assert.assertNotNull(adminUserDto1.getId());
		LOG.debug(" Role Id: "+adminUserDto1.getId());
		QueryFilter qf = new QueryFilter();
		qf.setRows(1);
		qf.setPage(1);
		Integer total = getAdminUsersCount(qf, null, null, true);
		Assert.assertNotNull(total);
		Assert.assertEquals(new Integer(2), total);
		AdminUserCriteriaUtil.updateCount(total, qf);
		Assert.assertNotNull(qf.getRows());
		Assert.assertNotNull(qf.getPage());
		
		Assert.assertEquals(new Integer(2), qf.getTotal());
		Assert.assertEquals(new Integer(1), qf.getRows());
		Assert.assertEquals(new Integer(1), qf.getPage());
	}
	
	@Test
	public void getAdminUserSearchQueryTest(){
		
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"firstName\",\"op\":\"cn\",\"data\":\"abc\"}," +
				"{\"field\":\"emailId\",\"op\":\"eq\",\"data\":\"abc@c.com\"},{\"field\":\"deleted\",\"op\":\"eq\",\"data\":\"false\"}," +
				"{\"field\":\"active\",\"op\":\"eq\",\"data\":\"true\"}]}";
		String exptQString = "select new com.tarang.ewallet.dto.UserDto(admin.id, pn.firstName, pn.lastName, rn.name,au.emailId, au.active, " +
				"au.status, au.blocked, au.creationDate)  " +
				"from AdminUser admin, Authentication au, PersonName pn,Role rn " +
				"where (admin.authenticationId=au.id and admin.nameId=pn.id and admin.roleId=rn.id)  " +
				"and ( upper(pn.firstName) like upper('%abc%')  and au.emailId = 'abc@c.com'  and au.active = true  )  order by pn.firstName desc";
	
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("firstName");
		qf.setSord("desc");
	
		String queryString = AdminUserCriteriaUtil.getAdminUserSearchQuery(qf, null, null, null, null, null);
		Assert.assertEquals(exptQString, queryString);
	}
	
	@Test
	public void getAdminUserSearchCountQueryTest(){
		
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"firstName\",\"op\":\"cn\",\"data\":\"abc\"}," +
				"{\"field\":\"emailId\",\"op\":\"eq\",\"data\":\"abc@c.com\"},{\"field\":\"deleted\",\"op\":\"eq\",\"data\":\"false\"}," +
				"{\"field\":\"active\",\"op\":\"eq\",\"data\":\"true\"}]}";
		String exptCString = "select count(*)  from AdminUser admin, Authentication au, PersonName pn,Role rn " +
				"where (admin.authenticationId=au.id and admin.nameId=pn.id and admin.roleId=rn.id)  " +
				"and ( upper(pn.firstName) like upper('%abc%')  and au.emailId = 'abc@c.com'  and au.active = true  ) ";
	
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("firstName");
		qf.setSord("desc");
	
		String countString = AdminUserCriteriaUtil.getAdminUserSearchCountQuery(qf, null, null, null, null, null);
		Assert.assertEquals(exptCString, countString);
	}
	@Test
	public void getFieldTypeTest(){
		Integer type = AdminUserCriteriaUtil.getFieldType("firstName");
		Assert.assertNotNull(type);
		Assert.assertEquals(new Integer(0), type);
		
		Integer type1 = AdminUserCriteriaUtil.getFieldType("emailId");
		Assert.assertNotNull(type1);
		Assert.assertEquals(new Integer(0), type1);
		
		Integer type2 = AdminUserCriteriaUtil.getFieldType("status");
		Assert.assertNotNull(type2);
		Assert.assertEquals(new Integer(1), type2);
		
		Integer type3 = AdminUserCriteriaUtil.getFieldType("creationDate");
		Assert.assertNotNull(type3);
		Assert.assertEquals(new Integer(3), type3);
		
		/*Integer type4 = AdminUserCriteriaUtil.getFieldType("deleted");
		Assert.assertNotNull(type4);
		Assert.assertEquals(new Integer(2), type4); */
		
		Integer type5 = AdminUserCriteriaUtil.getFieldType("blocked");
		Assert.assertNotNull(type5);
		Assert.assertEquals(new Integer(2), type5);
		
		Integer type6 = AdminUserCriteriaUtil.getFieldType("active");
		Assert.assertNotNull(type6);
		Assert.assertEquals(new Integer(2), type6);
	}
	private Integer getAdminUsersCount(final QueryFilter qf, final String fromDate, 
			final String toDate, final Boolean active) {
		return new Integer(hibernateOperations
				.find(AdminUserCriteriaUtil.getAdminUserSearchCountQuery(qf, fromDate, toDate, null, null, active))
				.get(0).toString());
	}
}