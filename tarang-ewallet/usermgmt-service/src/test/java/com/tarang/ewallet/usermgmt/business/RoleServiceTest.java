/**
 * 
 */
package com.tarang.ewallet.usermgmt.business;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.business.UserService;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.RoleCriteriaUtil;


/**
 * @author  : prasadj
 * @date    : Oct 16, 2012
 * @time    : 1:29:43 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class RoleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	private static final Logger log = Logger.getLogger(RoleServiceTest.class);
	
	@Autowired
	private UserService userService;
	
	@Test
	public void getCriteriaQueryTest1(){
		
		String searchString = "{\"groupOp\":\"OR\",\"rules\":[{\"field\":\"name\",\"op\":\"cn\",\"data\":\"User\"}, {\"field\":\"name\",\"op\":\"eq\",\"data\":\"User Management\"}]}";
		String exptQString = "select r  from Role r where  ( upper(name) like upper('%User%')  or name = 'User Management'  )  order by r.name desc";
		String exptCString = "select count(*)  from Role r where  ( upper(name) like upper('%User%')  or name = 'User Management'  ) ";
	
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("name");
		qf.setSord("desc");
	
		String queryString = RoleCriteriaUtil.getRoleSearchQuery(qf);
		
		String countString = RoleCriteriaUtil.getRoleSearchCountQuery(qf);
		log.debug(countString);
		
		Assert.assertEquals(exptQString, queryString);
		Assert.assertEquals(exptCString, countString);
	}
	
	@Test
	public void getCriteriaQueryTest2(){
		
		String searchString = "{\"groupOp\":\"OR\",\"rules\":[{\"field\":\"name\",\"op\":\"cn\",\"data\":\"sub\"}]}";
		String exptQString = "select r  from Role r where  ( upper(name) like upper('%sub%')  )  order by r.name desc";
		String exptCString = "select count(*)  from Role r where  ( upper(name) like upper('%sub%')  ) ";
	
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("name");
		qf.setSord("desc");
	
		String queryString = RoleCriteriaUtil.getRoleSearchQuery(qf);
		
		String countString = RoleCriteriaUtil.getRoleSearchCountQuery(qf);
		
		Assert.assertEquals(exptQString, queryString);
		Assert.assertEquals(exptCString, countString);	
	}

	@Test
	public void createSuperAdminRoleTest() throws WalletException{
		
		String roleName = "super admin";
		String roleDescription = "Super Admin Role access to all menus";
		String menuDetails = "1:2:3:4:5:6:7:8:9:10:11:12:13:14:15:16:17:18:19:20:21";
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails(menuDetails);
		
		Role output = userService.createRole(role);
		
		Assert.assertNotNull( output.getId() );
		Assert.assertEquals(roleName, output.getName());
		Assert.assertEquals(roleDescription, output.getDescription());
		Assert.assertEquals(menuDetails, output.getMenuDetails());
		
	}
	@Test
	public void createRoleTest() throws WalletException{
		
		String roleName = "subadmin1";
		String roleDescription = "user managment";
		String menuDetails = "1:13";
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails(menuDetails);
		
		Role output = userService.createRole(role);
		
		Assert.assertNotNull( output.getId() );
		Assert.assertEquals(roleName, output.getName());
		Assert.assertEquals(roleDescription, output.getDescription());
		Assert.assertEquals(menuDetails, output.getMenuDetails());
		
	}
	
	@Test
	public void getRolesListTest() throws WalletException{
		
		String roleName = "sub Admin";
		String roleDescription = "user managment";
		String menuDetails = "1:13";
		Role role = null;
		
		for(int i = 0; i < 30; i++){
			role = new Role();
			role.setName(roleName + i);
			role.setDescription(roleDescription + i);
			role.setMenuDetails(menuDetails);
			userService.createRole(role);
		}
		
		String searchString = "{\"groupOp\":\"OR\",\"rules\":[{\"field\":\"name\",\"op\":\"cn\",\"data\":\"sub\"}]}";
		
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("name");
		qf.setSord("desc");
	
		List<Role> roles = userService.rolesList(qf);
		
		Assert.assertNotNull( roles );
		Assert.assertEquals((int)qf.getRows(), roles.size());
		
	}
	
	@Test
	public void getRolesListTest2() throws WalletException{
		
		String roleName = "sub Admin";
		String roleDescription = "user managment";
		String menuDetails = "1:13";
		Role role = null;
		
		for(int i = 0; i < 30; i++){
			role = new Role();
			role.setName(roleName + i);
			role.setDescription(roleDescription + i);
			role.setMenuDetails(menuDetails);
	
			userService.createRole(role);
		}
		
		String searchString = "{\"groupOp\":\"OR\",\"rules\":[{\"field\":\"name\",\"op\":\"cn\",\"data\":\"sub\"}]}";
		
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(3);
		qf.setRows(25);
		qf.setSidx("name");
		qf.setSord("desc");
	
		List<Role> roles = userService.rolesList(qf);
		
		Assert.assertNotNull( roles );
		//Assert.assertEquals(5, roles.size());
		
	}
	
	@Test
	public void getRolesListTest3() throws WalletException{
		
		String roleName = "sub Admin";
		String roleDescription = "user managment";
		String menuDetails = "1:13";
		Role role = null;
		
		for(int i = 0; i < 30; i++){
			role = new Role();
			role.setName(roleName + i);
			role.setDescription(roleDescription + i);
			role.setMenuDetails(menuDetails);
			userService.createRole(role);
		}
		
		String searchString = "{\"groupOp\":\"OR\",\"rules\":[{\"field\":\"name\",\"op\":\"cn\",\"data\":\"1\"}]}";
		
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(searchString);
		qf.setPage(1);
		qf.setRows(25);
		qf.setSidx("name");
		qf.setSord("desc");
	
		List<Role> roles = userService.rolesList(qf);
		Assert.assertNotNull( roles );
		//Assert.assertEquals(12, roles.size());
		
	}
	
	@Test
	public void getAllRolesTest() throws WalletException{
		
		String roleName = "sub Admin";
		String roleDescription = "user managment";
		String menuDetails = "1:13";
		Role role = null;
		
		for(int i = 0; i < 30; i++){
			role = new Role();
			role.setName(roleName + i);
			role.setDescription(roleDescription + i);
			role.setMenuDetails(menuDetails);
			userService.createRole(role);
		}
		
	
		List<Role> roles = userService.getAllRoles();
		Assert.assertNotNull( roles );
		Assert.assertEquals(30, roles.size());
		
	}	
	
	@Test
	public void getRoleTest() throws WalletException{
		
		String roleName = "subadmin1";
		String roleDescription = "user managment";
		String menuDetails = "1:13";
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails(menuDetails);
		Role output = userService.createRole(role);
		
		output = userService.getRole(output.getId());
	
		Assert.assertNotNull( output.getId() );
		Assert.assertEquals(roleName, output.getName());
		Assert.assertEquals(roleDescription, output.getDescription());
		Assert.assertEquals(menuDetails, output.getMenuDetails());
	}
	
	@Test
	public void updateRoleTest() throws WalletException{
		
		String roleName = "subadmin1";
		String roleDescription = "user managment";
		String menuDetails = "1";
		
		String roleNewName = "Managment Role";
		String roleNewDescription = "user managment Role";
		String newMenuDetails = "1:13";
		
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails(menuDetails);
		Role output = userService.createRole(role);
		
		Assert.assertNotNull( output.getId() );
		Assert.assertEquals(roleName, output.getName());
		Assert.assertEquals(roleDescription, output.getDescription());
		Assert.assertEquals(menuDetails, output.getMenuDetails());
		
		output.setName(roleNewName);
		output.setDescription(roleNewDescription);
		output.setMenuDetails(newMenuDetails);
		
		output = userService.updateRole(output);
		
		Assert.assertNotNull( output.getId() );
		Assert.assertEquals(roleNewName, output.getName());
		Assert.assertEquals(roleNewDescription, output.getDescription());
		Assert.assertEquals(newMenuDetails, output.getMenuDetails());
	
	}
	
	@Test
	public void roleExistInAdminUserTest() throws WalletException {
		
		String roleName = "subadmin1";
		String roleDescription = "user managment";
		String menuDetails = "1:13";
		
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails(menuDetails);
		
		Role output = userService.createRole(role);
		Assert.assertNotNull( output.getId() );
		Assert.assertEquals(roleName, output.getName());
		Assert.assertEquals(roleDescription, output.getDescription());
		Assert.assertEquals(menuDetails, output.getMenuDetails());
		
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		adminUserDto.setRoleId(output.getId());
		adminUserDto = userService.createAdminUser(adminUserDto);
		Assert.assertNotNull(adminUserDto);
		Assert.assertEquals("super", adminUserDto.getFirstName());
		Assert.assertNotNull(adminUserDto.getRoleId());
		
		Boolean isRoleExists = userService.roleExistInAdminUser(output.getId());
		Assert.assertEquals(isRoleExists, Boolean.TRUE);
		
	} 
	
	@Test
	public void deleteRoleTest() throws WalletException {
		
		String roleName = "subadmin1";
		String roleDescription = "user managment";
		String menuDetails = "1:13";
		
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails(menuDetails);
		
		Role output = userService.createRole(role);
		
		Assert.assertNotNull( output.getId() );
		Assert.assertEquals(roleName, output.getName());
		Assert.assertEquals(roleDescription, output.getDescription());
		Assert.assertEquals(menuDetails, output.getMenuDetails());
		
		Boolean deleteRole = userService.deleteRole(output);
		Assert.assertEquals(deleteRole, Boolean.TRUE);
		
	}
	
}
