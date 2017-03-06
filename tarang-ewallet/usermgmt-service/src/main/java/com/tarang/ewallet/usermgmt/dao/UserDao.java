package com.tarang.ewallet.usermgmt.dao;

import java.util.List;

import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AdminUser;
import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.util.QueryFilter;


/**
 * @author : kedarnathd
 * @date : Oct 10, 2012
 * @time : 3:15:41 PM
 * @version : 1.0.0
 * @comments: dao interface for user management
 * 
 */
public interface UserDao {

	List<Role> getAllRoles() throws WalletException;

	List<Role> rolesList(QueryFilter qf) throws WalletException;

	Role getRole(Long roleId) throws WalletException;

	Role createRole(Role role) throws WalletException;

	Role updateRole(Role role) throws WalletException;

	Boolean deleteRole(Role role) throws WalletException;

	AdminUser createAdminUser(AdminUser adminUser) throws WalletException;

	AdminUser findAdminUserById(Long id) throws WalletException;
	
	AdminUser findAdminUserByAuthId(Long authId) throws WalletException;
	
	List<UserDto> getAdminUsersList(QueryFilter qf, String fromDate, 
			String toDate, String name,String  emailId, Boolean active) throws WalletException;

	AdminUser updateAdminUser(AdminUser adminUser) throws WalletException;
	
	List<Feedback> loadFeedbackDetails(Long id) throws WalletException;
	
	List<Feedback> loadFeedback(Long id) throws WalletException;
	
	Feedback createFeedbackReply(Feedback feedback) throws WalletException;
	
	Feedback updateFeedback(Feedback feedback) throws WalletException;
	
	Boolean roleExistInAdminUser(Long roleId) throws WalletException;
	
}
