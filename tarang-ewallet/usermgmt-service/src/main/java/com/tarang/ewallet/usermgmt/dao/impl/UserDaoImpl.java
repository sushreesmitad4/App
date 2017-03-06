package com.tarang.ewallet.usermgmt.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AdminUser;
import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.dao.UserDao;
import com.tarang.ewallet.util.AdminUserCriteriaUtil;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.RoleCriteriaUtil;

/**
 * @author : kedarnathd
 * @date : Oct 10, 2012
 * @time : 3:15:41 PM
 * @version : 1.0.0
 * @comments: dao interface for user management
 * 
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserDaoImpl implements UserDao {

	private HibernateOperations hibernateOperations;

	private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

	public UserDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}
 
	@Override
	public List<Feedback> loadFeedbackDetails(Long id) throws WalletException {
	     if( null == id ){
		return (List<Feedback>) hibernateOperations.find("from Feedback as feedback where feedback.parentId is null");
	    } else {
	      return (List<Feedback>) hibernateOperations.findByNamedQuery("getFeedbackList",id);
	    }
	}
	
	@Override
	public List<Role> getAllRoles() throws WalletException {
		return (List<Role>) hibernateOperations.find("from Role as role");
	}

	@Override
	public List<Role> rolesList(final QueryFilter qf) throws WalletException {
		List<Role> list = (List<Role>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				qf.setFilterString(null);
				Integer total = getRolesCount(qf);
				qf.setRows(total);
				RoleCriteriaUtil.updateCount(total, qf);
				String hql = RoleCriteriaUtil.getRoleSearchQuery(qf);
				Query q = session.createQuery(hql);
				q.setFirstResult((qf.getPage() - 1) * qf.getRows());
				q.setMaxResults(qf.getRows());
				return q.list();
			}
		});
		return list;
	}

	private Integer getRolesCount(final QueryFilter qf) {
		return Integer.valueOf(hibernateOperations
				.find(RoleCriteriaUtil.getRoleSearchCountQuery(qf)).get(0)
				.toString());
	}

	@Override
	public Role getRole(Long roleId) throws WalletException {
		List<Role> roles = (List<Role>) hibernateOperations.findByNamedQuery("getRoleOnId", roleId);
		if (roles.size() != 1) {
			throw new WalletException("ExpectedOneRole");
		}
		return roles.get(0);
	}

	@Override
	public Role createRole(Role role) throws WalletException {
		hibernateOperations.save(role);
		return role;
	}

	@Override
	public Role updateRole(Role role) throws WalletException {
		hibernateOperations.update(role);
		return role;
	}

	@Override
	public Boolean deleteRole(Role role) throws WalletException {
		hibernateOperations.delete(role);
		return true;
	}

	@Override
	public AdminUser createAdminUser(AdminUser adminUser) throws WalletException {
		Long id = (Long) hibernateOperations.save(adminUser);
		if ( null == id ) {
			throw new WalletException("Fails to insert admin user details");
		}
		adminUser.setId(id);
		return adminUser;
	}

	@Override
	public AdminUser findAdminUserById(Long id) throws WalletException {
		List<AdminUser> list = (List<AdminUser>) hibernateOperations.findByNamedQuery("getAdminUserById", id);
		
		LOGGER.debug("findAdminUserById :: list size " + list.size());
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else { 
			throw new WalletException("unique AdminUser exception");
		}
	}

	@Override
	public List<UserDto> getAdminUsersList(final QueryFilter qf, final String fromDate, 
			final String toDate, final String name, final String emailId, final Boolean active) {
		List<UserDto> list = (List<UserDto>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Integer total = getAdminUsersCount(qf, fromDate, toDate, name,emailId, active);
				AdminUserCriteriaUtil.updateCount(total, qf);
				String hql = AdminUserCriteriaUtil.getAdminUserSearchQuery(qf, fromDate, toDate, name, emailId, active);
				Query q = session.createQuery(hql);
				q.setFirstResult((qf.getPage() - 1) * qf.getRows() );
				LOGGER.debug("firstResult: " + ((qf.getPage() - 1) * qf.getRows()));
				q.setMaxResults(qf.getRows());
				LOGGER.debug("hql: " + hql);
				return q.list();
			}
		});
		return list;
	}

	private Integer getAdminUsersCount(final QueryFilter qf, final String fromDate, 
			final String toDate, final String name, final String emailId, final Boolean active) {
		return Integer.valueOf(hibernateOperations
				.find(AdminUserCriteriaUtil.getAdminUserSearchCountQuery(qf, fromDate, toDate, name, emailId, active))
				.get(0).toString());
	}

	@Override
	public AdminUser updateAdminUser(AdminUser adminUser) throws WalletException {
		hibernateOperations.update(adminUser);
		return adminUser;
	}
		
	@Override
	public AdminUser findAdminUserByAuthId(Long authId) throws WalletException {
		List<AdminUser> list = (List<AdminUser>) hibernateOperations.findByNamedQuery("getAdminUserByauthId", authId);
		LOGGER.debug("findAdminUserByAuthId :: list size " + list.size());
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else { 
			throw new WalletException("unique AdminUser exception");
		}
	}

	@Override
	public List<Feedback> loadFeedback(Long id) throws WalletException {
		List<Feedback> feedbackList = null;
		try{
		   feedbackList = hibernateOperations.findByNamedQuery("getFeedback",id);
		} catch(Exception e){
			throw new WalletException(CommonConstrain.FEEDBACK_RETRIVE_EXCEPTION, e);	
		}
		 return feedbackList;
	}

	@Override
	public Feedback createFeedbackReply(Feedback feedback) throws WalletException {
		try{
			hibernateOperations.save(feedback);
		} catch(Exception e){
			throw new WalletException(CommonConstrain.REPLY_SEND_EXCEPTION, e);	
		}
		return feedback;
	}
	
	@Override
	public Feedback updateFeedback(Feedback feedback) throws WalletException {
		hibernateOperations.update(feedback);
		return feedback;
	}

	@Override
	public Boolean roleExistInAdminUser(Long roleId) throws WalletException {
		List<Long> list = (List<Long>) hibernateOperations.findByNamedQuery("getAdminUserByRoleId", roleId);
		LOGGER.debug("roleExistInAdminUser :: list size " + list.size());
		Boolean flag = false;
		if (list != null) {
			Integer count = list.get(0).intValue();
			if (count > 0) {
				flag = true;
			}
		}
		return flag;
	}
}
