/**
 * 
 */

package com.tarang.ewallet.initialdatasetup;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.business.UserService;
import com.tarang.ewallet.util.GlobalLitterals;



/**
 * @author prasadj
 *
 */

@ContextConfiguration(locations={"classpath*:/wallet-applicationContext.xml",
		"classpath*:/wallettest-applicationContext.xml"})
@TransactionConfiguration(defaultRollback=false)
public class InitialDataSetupTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	private static final Logger LOGGER = Logger.getLogger(InitialDataSetupTest.class);
	
    @Autowired
    private HibernateTemplate hibernateTemplate;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@Test
	public void sampleTest(){
	}
	
	//@Test
	public void createEncriptedKey1(){
		//final String drop = "DROP TABLE walletkey";
		final String create = "CREATE TABLE walletkey(wallet varchar(500) NOT NULL)";
		final String update = "INSERT INTO walletkey VALUES('Tarang1234567890')";
		
		try {
			//jdbcTemplate.update(drop);
		}
		catch(Exception ex){
			LOGGER.info("Table 'walletkey' doesn't exists.");
		}

		jdbcTemplate.update(create);
		jdbcTemplate.update(update);
	}

    /*
     * this test case run only to update master data if required
     */
    //@Test 
    public void loadMasterData(){
    	List<Object> data = DataPreparation.getData();
    	Iterator<Object> iter = data.iterator();
    	while(iter.hasNext()){
    		hibernateTemplate.save(iter.next());
    	}

    }

	//@Test
	public void createAdminUserTest() throws WalletException {
		String roleName = "super admin";
		String roleDescription = "Super Admin Role access to all menus";
		String menuDetails = "2:5:5_2:5_1:6:6_2:6_1:7:7_2:7_1:8:8_2:8_1:19:19_2:19_1" +
				":20:9:9_2:9_1:17:17_2:17_1:18:18_2:18_1:14:14_2:14_1:22:22_2:22_1:24:24_2:24_1:" +
				"16:16_2:16_1:4:12:12_2:12_1:15:15_2:15_1:21:21_2:21_1:13:13_2:13_1";
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails(menuDetails);
		
		Role r = userService.createRole(role);

		AdminUserDto adminUserDto = prepareAdminUserDto();
		adminUserDto.setRoleId(r.getId());
		adminUserDto = userService.createAdminUser(adminUserDto);
		Assert.assertNotNull(adminUserDto);
		Assert.assertEquals("super", adminUserDto.getFirstName());
		Assert.assertNotNull(adminUserDto.getRoleId());
	}
	
	/**
	 * This Test Case For Creating The Scheduler Scripts Related To Oracle Database Table
	 */
	//@Test
	public void createSchedulerScriptsForOracle(){
		Set<String> scripSet = SchedulerScriptOracleDataBase.scriptsMap.keySet();
		Iterator<String> scripIterator = scripSet.iterator();
		try {
			while(scripIterator.hasNext()){
				String schedTableName = scripIterator.next();
				jdbcTemplate.update("DROP TABLE " + schedTableName);
				LOGGER.info("Table :: " + schedTableName + " Successfully Droped");
				jdbcTemplate.update(SchedulerScriptOracleDataBase.scriptsMap.get(schedTableName));
				LOGGER.info("Table :: " + SchedulerScriptOracleDataBase.scriptsMap.get(schedTableName) + "Successfully Created");
			}
			LOGGER.info("Total :: " + scripSet.size() + " Table Created Successfully" );
		}
		catch(Exception ex){
			LOGGER.info(ex);
		}
	}

	
	@Test
	public void createInitialDataTest() throws WalletException {
		List<Object> data = DataPreparation.getData();
    	Iterator<Object> iter = data.iterator();
    	while(iter.hasNext()){
    		hibernateTemplate.save(iter.next());
    	}
		String roleName = "super admin";
		String roleDescription = "Super Admin Role access to all menus";
		String menuDetails = "2:5:5_2:5_1:6:6_2:6_1:7:7_2:7_1:8:8_2:8_1:19:19_2:19_1" +
				":20:9:9_2:9_1:17:17_2:17_1:18:18_2:18_1:14:14_2:14_1:22:22_2:22_1:24:24_2:24_1:" +
				"16:16_2:16_1:4:12:12_2:12_1:15:15_2:15_1:21:21_2:21_1:13:13_2:13_1";
		
		Role role = new Role();
		role.setName(roleName);
		role.setDescription(roleDescription);
		role.setMenuDetails(menuDetails);
		
		Role r = userService.createRole(role);

		AdminUserDto adminUserDto = prepareAdminUserDto();
		adminUserDto.setRoleId(r.getId());
		adminUserDto = userService.createAdminUser(adminUserDto);
		PreferencesDto preferencesDto = new PreferencesDto();
		preferencesDto.setLanguage(CommonConstrain.DEFAULT_LANGUAGE);
		preferencesDto.setAuthentication(adminUserDto.getAuthenticationId());
		commonService.createPreferences(preferencesDto);
		Assert.assertNotNull(adminUserDto);
	}


	private static AdminUserDto prepareAdminUserDto() {
		AdminUserDto adminUserDto = new AdminUserDto();
		adminUserDto.setFirstName("super");
		adminUserDto.setLastName("admin");
		adminUserDto.setUserType(GlobalLitterals.ADMIN_USER_TYPE);
		adminUserDto.setEmailId("kedarnathd@tarangtech.com");
		adminUserDto.setPassword("bangalore");
		adminUserDto.setCountryId(4L);
		adminUserDto.setStateId(119L);
		adminUserDto.setCity("Bangalore");
		adminUserDto.setAddressOne("Bangalore");
		adminUserDto.setAddressTwo("Bangalore");
		adminUserDto.setStatus(1L);
		adminUserDto.setZipcode("560029");
		adminUserDto.setRoleId(new Long(1));
		adminUserDto.setPhoneCode("1234");
		adminUserDto.setPhoneNo("123456789");

		return adminUserDto;
	}
}
