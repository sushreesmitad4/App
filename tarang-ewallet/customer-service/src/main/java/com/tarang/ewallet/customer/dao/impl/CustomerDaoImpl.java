/**
 * 
 */
package com.tarang.ewallet.customer.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.customer.dao.CustomerDao;
import com.tarang.ewallet.customer.util.CustomerUserCriteriaUtil;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Customer;
import com.tarang.ewallet.util.QueryFilter;



/**
* @author  : prasadj
* @date    : Oct 19, 2012
* @time    : 1:12:47 PM
* @version : 1.0.0
* @comments: 
*
*/

@SuppressWarnings({ "unchecked" })
public class CustomerDaoImpl implements CustomerDao {
	
	private static final Logger LOGGER = Logger.getLogger(CustomerDaoImpl.class);

	private HibernateOperations hibernateOperations;
	
	public static final String CUSTOMER_ID = "getCustomerIdById";
	
	public static final String CUSTOMER_PHONE_ID = "getCustomerIdByPhoneId";

	public CustomerDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public Customer registration(Customer customer) throws WalletException {
		hibernateOperations.save(customer);
		return customer;
	}

	@Override
	public Boolean deleteCustomer(final Long id) throws WalletException {
		return true;		
	}

	@Override
	public Customer getCustomer(Long id) throws WalletException {
		try{
			List<Customer> list = (List<Customer>) hibernateOperations
					.find("from Customer as customer where customer.id="+id);
			if (list != null && list.size() == 1) {
				return (Customer)list.get(0);
			} else {
				throw new WalletException(CommonConstrain.UNIQUE_CUSTOMER_EXCEPTION);
			}
			}catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
				return null;
			}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<UserDto> getCustomersList(final QueryFilter qf, final String fromDate, 
			final String toDate, final String name, final String emailId, final Long status){
		List list = hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException{
				Integer total = getCustomerCount(qf, fromDate, toDate, name, emailId,  status);
				CustomerUserCriteriaUtil.updateCount(total, qf);
				String hql = CustomerUserCriteriaUtil.getCustomerSearchQuery(qf, fromDate, toDate, name, emailId, status);
				Query q = session.createQuery(hql);
				q.setFirstResult((qf.getPage() - 1) * qf.getRows());
				q.setMaxResults(qf.getRows());
				return q.list();
			}
		});
	
		return list;
	}

	private Integer getCustomerCount(final QueryFilter qf, final String fromDate, 
			final String toDate, final String name,final String emailId, final Long status) {
		return Integer.valueOf(hibernateOperations
				.find(CustomerUserCriteriaUtil.getCustomerSearchCountQuery(qf, fromDate, toDate, name, emailId,  status)).get(0)
				.toString());
	}

	@Override
	public Long getCustomerId(Long authenticationId, String userType)
			throws WalletException {
		List<Customer> list = (List<Customer>) hibernateOperations.findByNamedQuery(CUSTOMER_ID, authenticationId);	
		for(Customer c:list){
			LOGGER.debug("CustomerId is :"+c.getId());
		}
		if (list != null && list.size() == 1) {
			return list.get(0).getId();
		} else {
			throw new WalletException(CommonConstrain.UNIQUE_CUSTOMER_EXCEPTION);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws WalletException {
		hibernateOperations.update(customer);		
	}

	@Override
	public Customer getCustomerByAuthId(Long authId) throws WalletException {
		List<Customer> list = (List<Customer>) hibernateOperations.findByNamedQuery(CUSTOMER_ID, authId);	
		for(Customer c:list){
			LOGGER.debug("CustomerId :"+c.getId());
		}
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else {
			throw new WalletException("unable to find customer");
		}
	}

	@Override
	public Customer getCustomerByPhoneId(Long phoneId) throws WalletException {
		List<Customer> list = (List<Customer>) hibernateOperations.findByNamedQuery(CUSTOMER_PHONE_ID, phoneId);	
		for(Customer c:list){
			LOGGER.debug("CustomerId is :"+c.getId());
		}
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else {
			throw new WalletException(CommonConstrain.UNIQUE_CUSTOMER_EXCEPTION);
		}
	}
}
