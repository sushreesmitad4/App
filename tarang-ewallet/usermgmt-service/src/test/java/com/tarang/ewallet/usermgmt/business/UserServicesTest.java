/**
 * 
 */
package com.tarang.ewallet.usermgmt.business;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.business.UserService;
import com.tarang.ewallet.util.AdminUserCriteriaUtil;
import com.tarang.ewallet.util.QueryFilter;


/**
 * @author  : prasadj
 * @date    : Oct 17, 2012
 * @time    : 6:34:19 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class UserServicesTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UserService userService;

	@Test
	public void getAUListTest1() throws WalletException{
		String roleName = "Super Admin";
		String roleDescription = "Super Admin Role access to all menus";
		String menuDetails = "1:2:3:4:5:6:7:8:9:10:11:12:13:14:15:16";
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails(menuDetails);
		
		Role r = userService.createRole(role);

		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		adminUserDto.setRoleId(r.getId());
		adminUserDto = userService.createAdminUser(adminUserDto);
		Assert.assertNotNull( adminUserDto );
		
		AdminUserDto adminUserDto2 = DataPreparation.prepareAdminUserDto();
		adminUserDto2.setPhoneCode("9999");
		adminUserDto2.setRoleId(r.getId());
		adminUserDto2.setEmailId("shivap@tarangtech.com");
		adminUserDto2 = userService.createAdminUser(adminUserDto2);
		Assert.assertNotNull( adminUserDto2 );
		
		AdminUserDto adminUserDto3 = DataPreparation.prepareAdminUserDto();
		adminUserDto3.setRoleId(r.getId());
		adminUserDto3.setEmailId("yasina@tarangtech.com");
		adminUserDto3.setPhoneCode("8888");
		adminUserDto3 = userService.createAdminUser(adminUserDto3);
		Assert.assertNotNull( adminUserDto3 );
		
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(2);
		qf.setRows(3);
		qf.setSidx("emailId");
		qf.setSord("desc");
	
		List<UserDto> userDtos = userService.getAdminUsersList(qf, null, null, null, null, true);
		Assert.assertNotNull( userDtos );
		Assert.assertEquals(3, userDtos.size());
	}
	
	@Test
	public void getCriteriaQueryTest1(){
		
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"firstName\",\"op\":\"cn\",\"data\":\"abc\"}," +
				"{\"field\":\"emailId\",\"op\":\"eq\",\"data\":\"abc@c.com\"},{\"field\":\"deleted\",\"op\":\"eq\",\"data\":\"false\"}," +
				"{\"field\":\"active\",\"op\":\"eq\",\"data\":\"true\"}]}";
		String exptQString = "select new com.tarang.ewallet.dto.UserDto(admin.id, pn.firstName, pn.lastName, rn.name,au.emailId, au.active, " +
				"au.status, au.blocked, au.creationDate)  " +
				"from AdminUser admin, Authentication au, PersonName pn,Role rn " +
				"where (admin.authenticationId=au.id and admin.nameId=pn.id and admin.roleId=rn.id)  " +
				"and ( upper(pn.firstName) like upper('%abc%')  and au.emailId = 'abc@c.com'  and au.active = true  )  order by pn.firstName desc";
		
		String exptCString = "select count(*)  from AdminUser admin, Authentication au, PersonName pn,Role rn " +
				"where (admin.authenticationId=au.id and admin.nameId=pn.id and admin.roleId=rn.id)  " +
				"and ( upper(pn.firstName) like upper('%abc%')  and au.emailId = 'abc@c.com'  and au.active = true  ) ";
	
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("firstName");
		qf.setSord("desc");
	
		String queryString = AdminUserCriteriaUtil.getAdminUserSearchQuery(qf, null, null, null, null, null);
		
		String countString = AdminUserCriteriaUtil.getAdminUserSearchCountQuery(qf, null, null, null, null, null);
		
		
		Assert.assertEquals(exptQString, queryString);
		Assert.assertEquals(exptCString, countString);
	}
	
	@Test
	public void getCriteriaQueryTest2(){
		
		String searchString = "{\"groupOp\":\"AND\",\"rules\":[]}";
		String exptQString = "select new com.tarang.ewallet.dto.UserDto(admin.id, pn.firstName, pn.lastName, rn.name,au.emailId, au.active, " +
				"au.status, au.blocked, au.creationDate)  " +
				"from AdminUser admin, Authentication au, PersonName pn,Role rn " +
				"where (admin.authenticationId=au.id and admin.nameId=pn.id and admin.roleId=rn.id)  order by pn.firstName desc";
		String exptCString = "select count(*)  from AdminUser admin, Authentication au, PersonName pn,Role rn where (admin.authenticationId=au.id and admin.nameId=pn.id and admin.roleId=rn.id) ";
	
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("firstName");
		qf.setSord("desc");
	
		String queryString = AdminUserCriteriaUtil.getAdminUserSearchQuery(qf, null, null, null, null, null);
		
		String countString = AdminUserCriteriaUtil.getAdminUserSearchCountQuery(qf, null, null, null, null, null);
		
		Assert.assertEquals(exptQString, queryString);
		Assert.assertEquals(exptCString, countString);
		
		Assert.assertEquals(exptQString, queryString);
		Assert.assertEquals(exptCString, countString);
	}
	
}
