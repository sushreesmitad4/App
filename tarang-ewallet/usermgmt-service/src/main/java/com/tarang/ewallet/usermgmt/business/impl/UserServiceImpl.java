/**
 * 
 */
package com.tarang.ewallet.usermgmt.business.impl;

import java.util.List;

import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AdminUser;
import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.business.UserService;
import com.tarang.ewallet.usermgmt.repository.UserRepository;
import com.tarang.ewallet.util.QueryFilter;


/**
 * @author : kedarnathd
 * @date : Oct 10, 2012
 * @time : 3:15:41 PM
 * @version : 1.0.0
 * @comments: Service Class to provide eWallet User Management Services and it
 *            implements UserService interface
 */
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<Feedback> loadFeedbackDetails(Long id) throws WalletException {
		return userRepository.loadFeedbackDetails(id);
	}
	@Override
	public List<UserDto> getAdminUsersList(QueryFilter qf, String fromDate, 
			String toDate, String name, String emailId, Boolean active) throws WalletException {
		return userRepository.getAdminUsersList(qf, fromDate, toDate, name, emailId, active);
	}

	@Override
	public List<Role> getAllRoles() throws WalletException {
		return userRepository.getAllRoles();
	}

	@Override
	public List<Role> rolesList(QueryFilter qf) throws WalletException {
		return userRepository.rolesList(qf);
	}

	@Override
	public Role getRole(Long roleId) throws WalletException {
		return userRepository.getRole(roleId);
	}

	@Override
	public Role createRole(Role role) throws WalletException {
		return userRepository.createRole(role);
	}

	@Override
	public Role updateRole(Role role) throws WalletException {
		return userRepository.updateRole(role);
	}

	@Override
	public Boolean deleteRole(Role role) throws WalletException {
		return userRepository.deleteRole(role);
	}

	@Override
	public AdminUserDto createAdminUser(AdminUserDto adminUserDto)
			throws WalletException {
		return userRepository.createAdminUser(adminUserDto);
	}

	@Override
	public AdminUserDto updateAdminUser(AdminUserDto adminUserDto)
			throws WalletException {
		return userRepository.updateAdminUser(adminUserDto);
	}

	@Override
	public AdminUserDto loadAdminUser(Long adminUserId) throws WalletException {
		return userRepository.loadAdminUser(adminUserId);
	}

	@Override
	public AdminUser findAdminUserById(Long id) throws WalletException {
		return userRepository.findAdminUserById(id);
	}

	@Override
	public Boolean deleteAdminUser(Long adminUserId) throws WalletException {
		return userRepository.deleteAdminUser(adminUserId);
	}
	
	@Override
	public AdminUser findAdminUserByAuthId(Long authId) throws WalletException {
		return userRepository.findAdminUserByAuthId(authId);
	}

	@Override
	public List<Feedback> loadFeedback(Long id) throws WalletException {
        return userRepository.loadFeedback(id);
	}

	@Override
	public Feedback createFeedbackReply(Feedback feedback) throws WalletException {
		return userRepository.createFeedbackReply(feedback);
	}
	
	@Override
	public Feedback updateFeedback(Feedback feedback) throws WalletException {
		return userRepository.updateFeedback(feedback);
	}
	
	@Override
	public Boolean roleExistInAdminUser(Long roleId) throws WalletException {
		return userRepository.roleExistInAdminUser(roleId);
	}
	
}
