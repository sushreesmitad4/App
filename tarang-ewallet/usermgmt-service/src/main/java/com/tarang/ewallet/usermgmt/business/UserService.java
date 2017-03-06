package com.tarang.ewallet.usermgmt.business;

import java.util.List;

import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AdminUser;
import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.util.QueryFilter;


/**
 * @Author : kedarnathd
 * @Date : Oct 10 2012
 * @Time : 6.30PM
 * @Version : 1.0
 * @Comments: Customized Exception Class to handle Exceptions
 */
public interface UserService {

	List<UserDto> getAdminUsersList(QueryFilter qf, String fromDate, 
			String toDate, String name, String emailId, Boolean active) throws WalletException;

	Role getRole(Long roleId) throws WalletException;

	List<Role> getAllRoles() throws WalletException;

	List<Role> rolesList(QueryFilter qf) throws WalletException;

	Role createRole(Role role) throws WalletException;

	Role updateRole(Role role) throws WalletException;

	Boolean deleteRole(Role role) throws WalletException;

	AdminUserDto createAdminUser(AdminUserDto adminUserDto) throws WalletException;

	AdminUserDto updateAdminUser(AdminUserDto adminUserDto) throws WalletException;

	AdminUserDto loadAdminUser(Long adminUserId) throws WalletException;
	
	AdminUser findAdminUserById(Long id) throws WalletException;
	
	Boolean deleteAdminUser(Long adminUserId)throws WalletException;

	AdminUser findAdminUserByAuthId(Long authId) throws WalletException;
	
	List<Feedback> loadFeedbackDetails(Long id) throws WalletException;
	
	List<Feedback> loadFeedback(Long id) throws WalletException;
	
	Feedback createFeedbackReply(Feedback feedback) throws WalletException;
	
	Feedback updateFeedback(Feedback feedback) throws WalletException;
	
	Boolean roleExistInAdminUser(Long roleId) throws WalletException;
	
}
