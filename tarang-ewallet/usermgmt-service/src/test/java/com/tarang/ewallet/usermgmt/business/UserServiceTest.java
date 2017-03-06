package com.tarang.ewallet.usermgmt.business;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.FeedbackDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AdminUser;
import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.business.UserService;


/**
 * @author kedarnathd
 * 
 */

@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class UserServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private static final Logger log = Logger.getLogger(UserServiceTest.class);
	
	@Autowired
	private UserService userService;
	
    @Autowired
	private CommonService commonService;

	RoleServiceTest roleServiceTest = new RoleServiceTest();
	
	@Before
	public void prepareData() {
		// prepare data
	}
	
	@Test
	public void createAdminUserTest() throws WalletException {
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
	}
	
	@Test(expected = WalletException.class) 
	public void createAdminUserWithNullObjectTest() throws WalletException {
		userService.createAdminUser(null);
	}
	
	@Test
	public void updateAdminUserTest() throws WalletException{
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
		
		AdminUserDto newAdminUserDto = userService.loadAdminUser(adminUserDto.getId());
		Assert.assertNotNull(adminUserDto);
		newAdminUserDto.setFirstName("xyz");
		newAdminUserDto.setId(adminUserDto.getId());
		Assert.assertNotNull(newAdminUserDto.getPhoneCode());
		
		newAdminUserDto.setExistphone(newAdminUserDto.getPhoneCode());
		newAdminUserDto.setPhoneNo("1234569");
		newAdminUserDto = userService.updateAdminUser(newAdminUserDto);
		
		Assert.assertNotNull(newAdminUserDto);
		Assert.assertNotNull(newAdminUserDto.getId());
	}
	@Test
	public void loadAdminUserWithRoleTest() throws WalletException{
		AdminUserDto adminUserDto = DataPreparation.prepareAdminUserDto();
		Long roleId = createRoleForAdminUser();
		adminUserDto.setRoleId(roleId);
		adminUserDto = userService.createAdminUser(adminUserDto);
		Assert.assertNotNull(adminUserDto);
		
		AdminUserDto adminUserDto2 = userService.loadAdminUser(adminUserDto.getId());
		Assert.assertNotNull(adminUserDto2);
		Assert.assertEquals("super", adminUserDto2.getFirstName());
	}
	
	@Test
	public void findAdminUserByIdTest() throws WalletException{
		String roleName = "Admin";
		String roleDescription = "Admin Role access to all menus";
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
		
		AdminUser adminUser = userService.findAdminUserById(adminUserDto.getId());
		Assert.assertNotNull(adminUser);
		Assert.assertNotNull(adminUser.getId());
		Assert.assertEquals(adminUserDto.getId(),adminUser.getId());
	}
	
	@Test
	public void deleteAdminUserTest() throws WalletException{
		String roleName = "Admin";
		String roleDescription = "Admin Role access to all menus";
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
		
		Boolean delete = userService.deleteAdminUser(adminUserDto.getId());
		Assert.assertNotNull(delete);
		Assert.assertEquals(true,delete);
	}
	
	@Test
	public void findAdminUserByAuthIdTest() throws WalletException{
		String roleName = "Admin";
		String roleDescription = "Admin Role access to all menus";
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
		
		AdminUser adminUser = userService.findAdminUserById(adminUserDto.getId());
		Assert.assertNotNull(adminUser);
		Assert.assertNotNull(adminUser.getAuthenticationId());
		
		AdminUser user = userService.findAdminUserByAuthId(adminUser.getAuthenticationId());
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
	}
	
	/*
	 * Test cases related to admin feedback reply
	 */
	@Test
	public void loadFeedbackDetailsTest() throws WalletException{
		FeedbackDto feedbackDto = DataPreparation.createFeedbackDto();
		commonService.createFeedback(feedbackDto);
		List<Feedback> list = userService.loadFeedbackDetails(null);
		Feedback feedback = list.get(0);
		Assert.assertNotNull(list);
		Assert.assertNotNull(feedback.getUserMail());
		
	}
	
	@Test
	public void loadFeedbackTest() throws WalletException{
		FeedbackDto feedbackDto = DataPreparation.createFeedbackDto();
		commonService.createFeedback(feedbackDto);
		List<Feedback> list = userService.loadFeedback(1L);
		Assert.assertNotNull(list);
	}
	
	@Test
	public void createFeedbackReplyTest() throws WalletException{
		FeedbackDto feedbackDto = DataPreparation.createFeedbackDto();
		Feedback feedback = commonService.createFeedback(feedbackDto);
		feedback.setResponseSender(4L);
		feedback.setResponseDate(new Date());
		feedback.setResponseMessage("ok thank u for your reply");
		Feedback feedback1 = userService.createFeedbackReply(feedback);
		Assert.assertNotNull(feedback1);
	}
	
	@Test
	public void updateFeedbackTest() throws WalletException{
		FeedbackDto feedbackDto = DataPreparation.createFeedbackDto();
		Feedback feedback = commonService.createFeedback(feedbackDto);
		 feedback.setResponseDate(new Date());
		 Feedback feedback1 = userService.updateFeedback(feedback);
		 Assert.assertNotNull(feedback1);
		 
	}
	
	private Long createRoleForAdminUser(){
		String roleName = "subadmin1";
		String roleDescription = "user managment";
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails("1:13");
		Role output = null;

		try {
			output = userService.createRole(role);
		} 
		catch (WalletException e) {
			log.error(e);
		}
		
		Assert.assertNotNull( output.getId() );
		Assert.assertEquals(roleName, output.getName());
		Assert.assertEquals(roleDescription, output.getDescription());
		return output.getId();
	}
	
}
