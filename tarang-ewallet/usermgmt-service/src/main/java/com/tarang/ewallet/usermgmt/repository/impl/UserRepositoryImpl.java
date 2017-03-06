package com.tarang.ewallet.usermgmt.repository.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.AdminUser;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.dao.UserDao;
import com.tarang.ewallet.usermgmt.repository.UserRepository;
import com.tarang.ewallet.util.AdminUtil;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;

/**
 * @Author : kedarnathd
 * @Date : Oct 10 2012
 * @Time : 10:09:24 PM
 * @Version : 1.0
 * @Comments: user repository service interface
 */
public class UserRepositoryImpl implements UserRepository {

	private static final Logger LOGGER = Logger
			.getLogger(UserRepositoryImpl.class);

	private CryptService cryptService;

	private UserDao userDao;

	private CommonService commonService;
	
	private HibernateTransactionManager transactionManager;
	
	private String adminUnknowException = "admin.unknow.save.exception" ;

	public UserRepositoryImpl(UserDao userDao, CommonService commonService, 
			CryptService cryptService, HibernateTransactionManager transactionManager) {
		LOGGER.debug( " UserRepositoryImpl constructor " );
		this.userDao = userDao;
		this.commonService = commonService;
		this.cryptService = cryptService;
		this.transactionManager = transactionManager;
	}
	
	@Override
	public List<Feedback> loadFeedbackDetails(Long id) throws WalletException {
        LOGGER.debug("loadFeedbackDetails");
		return userDao.loadFeedbackDetails(id);
	}

	@Override
	public List<Role> getAllRoles() throws WalletException {
		LOGGER.debug( " getAllRoles " );
		return userDao.getAllRoles();
	}

	@Override
	public List<Role> rolesList(QueryFilter qf) throws WalletException {
		LOGGER.debug( " rolesList " );
		return userDao.rolesList(qf);
	}

	@Override
	public Role getRole(Long roleId) throws WalletException {
		LOGGER.debug( " getRole " );
		return userDao.getRole(roleId);
	}

	@Override
	public Role createRole(Role role) throws WalletException {
		LOGGER.debug( " createRole " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			role.setName(role.getName().toLowerCase());
			Role ro = userDao.createRole(role);
			transactionManager.commit(txstatus);
			return ro;
		} catch(org.springframework.dao.DataIntegrityViolationException ex){
			LOGGER.error( ex.getMessage() , ex);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception e){
				LOGGER.error( e.getMessage() , e);
			}
			throw new WalletException(CommonConstrain.DUPLICATE_ENTRY, ex);
		} catch(WalletException e){
			LOGGER.error( e.getMessage() , e);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception ex){
				LOGGER.error( ex.getMessage() , ex);
			}
			throw e;
		} catch(Exception e){
			LOGGER.error( e.getMessage() , e);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception ex){
				LOGGER.error( ex.getMessage() , ex);
			}
			throw new WalletException(CommonConstrain.ROLE_CREATE_EXCEPTION, e);
		}
	}

	@Override
	public Role updateRole(Role role) throws WalletException {
		LOGGER.debug( " updateRole " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Role ro =  userDao.updateRole(role);
			transactionManager.commit(txstatus);
			return ro;
		} catch(WalletException ex){
			LOGGER.error( ex.getMessage() , ex);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception e){
				LOGGER.error( e.getMessage() , e);
			}
			throw ex;
		} catch(Exception ex){
			LOGGER.error( ex.getMessage() , ex);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception e){
				LOGGER.error( e.getMessage() , e);
			}
			throw new WalletException(CommonConstrain.ROLE_UPDATE_EXCEPTION, ex);
		}
	}

	@Override
	public Boolean deleteRole(Role role) throws WalletException {
		LOGGER.debug( " deleteRole " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Boolean deleteFlag = userDao.deleteRole(role);
			transactionManager.commit(txstatus);
			return deleteFlag;
		} catch (Exception ex){
			LOGGER.error( ex.getMessage() , ex);
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		} 
		
	}
	
	@Override
	public AdminUserDto createAdminUser(AdminUserDto adminUserDto) throws WalletException {
		LOGGER.debug( " createAdminUser " );
		if (adminUserDto == null) {
			throw new WalletException("AdminUserDto should not be null");
		}
		AdminUserDto newAdminUserDto = new AdminUserDto();
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		PersonName personName = null;
		Authentication authentication = null;
		String newPassword = null;
		try{
			LOGGER.debug( "In side try block :: CreateAdminUser " );
			adminUserDto.setExistphone(adminUserDto.getPhoneNo());	
			PhoneNumber phoneNumber = new PhoneNumber(adminUserDto.getPhoneCode(),
					adminUserDto.getPhoneNo());
			/*Create phone*/
			phoneNumber = commonService.createPhone(phoneNumber);
				
			newPassword = CommonUtil.getRandomPassword();
			personName = AdminUtil.getPersonName(adminUserDto);
			personName = commonService.createPersonName(personName);
			Address address = AdminUtil.getAddress(adminUserDto);
			address = commonService.createAddress(address);
			authentication = new Authentication();
			authentication.setIpAddress(adminUserDto.getIpAddress());
			authentication.setPassword(cryptService.encryptData(newPassword));
			AdminUtil.getAuthentication(adminUserDto, authentication);
			/*Create authentication*/
			authentication = commonService.createAuthentication(authentication);
	
			Long nameId = personName.getId();
			Long addressId = address.getId();
			Long authenticationId = authentication.getId();
	
			LOGGER.debug("createAdminUser " + "personNameId: "
					+ nameId + " addressId: " + addressId + " authenticationId: "
					+ authenticationId);
			
			AdminUser adminUser = new AdminUser(nameId, addressId,
					authenticationId, adminUserDto.getRoleId(), phoneNumber.getId());
			/*Create ADMIN user*/
			adminUser = userDao.createAdminUser(adminUser);
			
			LOGGER.debug("UserId: " + adminUser.getId());
	
			//commonService.sendAdminRegistrationMail(personName.getFirstName()+GlobalLitterals.SPACE_STRING+personName.getLastName(), authentication.getEmailId(), newPassword,
			//		GlobalLitterals.ADMIN_USER_TYPE, adminUserDto.getLanguageId());
			
			LOGGER.debug("UserRepositoryImpl :: saveAdminUser "
					+ "After save authenticationId: " + authentication.getId());
			
			AdminUtil.papulatePersonName(newAdminUserDto, personName);
			AdminUtil.papulateAddress(newAdminUserDto, address);
			AdminUtil.papulateAuthentication(newAdminUserDto, authentication);
	
			newAdminUserDto.setAuthenticationId(authentication.getId());
			newAdminUserDto.setId(adminUser.getId());
			newAdminUserDto.setRoleId(adminUser.getRoleId());
			transactionManager.commit(txstatus);
		} catch(WalletException e){
			LOGGER.error( e.getMessage() , e );
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception ex){
				LOGGER.error( ex.getMessage() , ex);
			}
			if(e.getMessage().contains(CommonConstrain.INSERT_PHONENUMBER)){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}
			throw e;
		} catch(Exception e){
			LOGGER.error( e.getMessage() , e);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception ex){
				LOGGER.error( ex.getMessage() , ex);
			}
			if(e.getMessage().contains(CommonConstrain.INSERT_PHONENUMBER)){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
			}
			throw new WalletException(adminUnknowException, e);
		}
		commonService.sendAdminRegistrationMail(personName.getFirstName()+GlobalLitterals.SPACE_STRING+personName.getLastName(), authentication.getEmailId(), newPassword,
				GlobalLitterals.ADMIN_USER_TYPE, adminUserDto.getLanguageId());
		return newAdminUserDto;
	}

	@Override
	public AdminUserDto updateAdminUser(AdminUserDto adminUserDto)
			throws WalletException {
		LOGGER.debug( " updateAdminUser " );
			AdminUserDto newAdminUserDto = new AdminUserDto();
		if (adminUserDto != null && adminUserDto.getId() != null) {
			TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
			TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
			try{
				AdminUser adminUser = userDao.findAdminUserById(adminUserDto.getId());
				
				preparePhoneNumberForUpdate(adminUserDto, adminUser.getPhoneIdOne());
				preparePersonNameForUpdate(adminUserDto, adminUser.getNameId());
				prepareAddressForUpdate(adminUserDto, adminUser.getAddressId());
				prepareAuthenticationForUpdate(adminUserDto, adminUser.getAuthenticationId());
				newAdminUserDto = adminUserDto;
				adminUser.setRoleId(adminUserDto.getRoleId());
				adminUser = userDao.updateAdminUser(adminUser);
				
				newAdminUserDto.setAuthenticationId(adminUser.getAuthenticationId());
				newAdminUserDto.setId(adminUser.getId());
				newAdminUserDto.setRoleId(adminUser.getRoleId());
				transactionManager.commit(txstatus);
				/* Send mail for status changed */
				if(adminUserDto.getUpdateReason() != null && !"".equals(adminUserDto.getUpdateReason())){
					commonService.sendMailForUserStatusUpdate(adminUserDto.getFirstName()+ GlobalLitterals.SPACE_STRING + adminUserDto.getLastName(), 
							adminUserDto.getEmailId(), adminUserDto.getStatusName(), adminUserDto.isActive(), adminUserDto.getUpdateReason(), GlobalLitterals.ADMIN_USER_TYPE, adminUserDto.getLanguageId());
				}
			} catch(WalletException e){
				LOGGER.error( e.getMessage() , e);
				try{
					transactionManager.rollback(txstatus);
				} catch(Exception ex){
					LOGGER.error( ex.getMessage() , ex);
				}
				if(e.getMessage().contains(CommonConstrain.UPDATE_PHONENUMBER)){
					throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
				}
				throw e;
			} catch(Exception e){
				LOGGER.error( e.getMessage() , e );
				try{
					transactionManager.rollback(txstatus);
				} catch(Exception ex){
					LOGGER.error( ex.getMessage() , ex);
				}
				if(e.getMessage().contains(CommonConstrain.UPDATE_PHONENUMBER)){
					throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
				}
				throw new WalletException(adminUnknowException, e);
			 }
		} else {
			LOGGER.error("ERROR ::UserRepositoryImpl::updateAdminUser::Admin User id is null");
			throw new WalletException("Admin user id should not be null");
		}
		return newAdminUserDto;
	}

	@Override
	public AdminUserDto loadAdminUser(Long adminUserId) throws WalletException {
		LOGGER.debug( " loadAdminUser " );
		AdminUser user = userDao.findAdminUserById(adminUserId);
		PhoneNumber phoneNumber = commonService.getPhone(user.getPhoneIdOne());
		
		PersonName personName = commonService.getPersonName(user.getNameId());
		Address address = commonService.getAddress(user.getAddressId());
		Authentication authentication = commonService.getAuthentication(user.getAuthenticationId());
		AdminUserDto newAdminUserDto = new AdminUserDto();
		
		newAdminUserDto.setExistphone(phoneNumber.getPnumber());
		newAdminUserDto.setPhoneCode(phoneNumber.getCode());
		newAdminUserDto.setPhoneNo(phoneNumber.getPnumber());
		
		AdminUtil.papulatePersonName(newAdminUserDto, personName);
		AdminUtil.papulateAddress(newAdminUserDto, address);
		AdminUtil.papulateAuthentication(newAdminUserDto, authentication);

		newAdminUserDto.setId(user.getId());
		newAdminUserDto.setRoleId(user.getRoleId());
		Role role = userDao.getRole(user.getRoleId());
		LOGGER.debug(" role name "+role != null?role.getName():"");
		newAdminUserDto.setRoleName(role.getName());
		return newAdminUserDto;
	}

	@Override
	public List<UserDto> getAdminUsersList(QueryFilter qf, String fromDate, String toDate, String name, String emailId, Boolean active) throws WalletException {
		LOGGER.debug( " getAdminUsersList " );
		return userDao.getAdminUsersList(qf, fromDate, toDate, name, emailId, active);
	}

	@Override
	public AdminUser findAdminUserById(Long id) throws WalletException {
		LOGGER.debug( " findAdminUserById " );
		return userDao.findAdminUserById(id);
	}
	
	@Override
	public AdminUser findAdminUserByAuthId(Long authId) throws WalletException {
		LOGGER.debug( " findAdminUserByAuthId " );
		return userDao.findAdminUserByAuthId(authId);
	}
	
	@Override
	public Boolean deleteAdminUser(Long adminUserId) throws WalletException {
		LOGGER.debug( " deleteAdminUser " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			AdminUser adminUser = userDao.findAdminUserById(adminUserId);
			Boolean deleteFlag = commonService.deleteAuthentication(adminUser.getAuthenticationId());
			transactionManager.commit(txstatus);
			return deleteFlag;
		} catch(WalletException e){
			LOGGER.error( e.getMessage() , e);
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception ex){
				LOGGER.error( ex.getMessage() , ex);
			}
			throw e;
		} catch(Exception e){
			LOGGER.error( e.getMessage() , e );
			try{
				transactionManager.rollback(txstatus);
			} catch(Exception ex){
				LOGGER.error( ex.getMessage() , ex);
			}
			throw new WalletException(adminUnknowException, e);
		}
	}
	
	@Override
	public List<Feedback> loadFeedback(Long id) throws WalletException {
		LOGGER.debug( " loadFeedback " );
		return userDao.loadFeedback(id);
	}
	
	@Override
	public Feedback createFeedbackReply(Feedback feedback) throws WalletException {
		LOGGER.debug( " createFeedbackReply " );
		Feedback feed = userDao.createFeedbackReply(feedback);
		/* Send ADMIN reply as mail notification */
		commonService.sendMailForFeedbackReply(feedback);
		return feed;
	}
	
	@Override
	public Feedback updateFeedback(Feedback feedback) throws WalletException {
		LOGGER.debug( " updateFeedback " );
		return userDao.updateFeedback(feedback);
	}
	
	@Override
	public Boolean roleExistInAdminUser(Long roleId) throws WalletException {
		return userDao.roleExistInAdminUser(roleId);
	}
	
	private void preparePhoneNumberForUpdate(AdminUserDto adminUserDto, Long phoneId) throws WalletException {
		LOGGER.debug( " preparePhoneNumberForUpdate " );
		PhoneNumber phoneNumber = commonService.getPhone(phoneId);
		phoneNumber.setCode(adminUserDto.getPhoneCode());
		phoneNumber.setPnumber(adminUserDto.getPhoneNo());
		commonService.updatePhone(phoneNumber);
	}
	
	private void preparePersonNameForUpdate(AdminUserDto adminUserDto, Long nameId) throws WalletException {
		LOGGER.debug( " preparePersonNameForUpdate " );
		PersonName personName = commonService.getPersonName(nameId);
		AdminUtil.preparePersonNameForUpdate(personName, adminUserDto);
		commonService.updatePersonName(personName);
	}

	private void prepareAddressForUpdate(AdminUserDto adminUserDto, Long addressId) throws WalletException {
		LOGGER.debug( " prepareAddressForUpdate " );
		Address address = commonService.getAddress(addressId);
		AdminUtil.prepareAddressForUpdate(address, adminUserDto);
		commonService.updateAddress(address);
	}

	private void prepareAuthenticationForUpdate(AdminUserDto adminUserDto, Long authenticationId) throws WalletException {
		LOGGER.debug( " prepareAuthenticationForUpdate " );
		Authentication authentication = commonService.getAuthentication(authenticationId);
		AdminUtil.prepareAuthenticationForUpdate(authentication, adminUserDto);
		commonService.updateAuthentication(authentication);
	}
	
}
