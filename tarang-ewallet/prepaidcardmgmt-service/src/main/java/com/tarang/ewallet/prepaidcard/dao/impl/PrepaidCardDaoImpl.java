/**
 * 
 */
package com.tarang.ewallet.prepaidcard.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.PrepaidCardAccount;
import com.tarang.ewallet.prepaidcard.dao.PrepaidCardDao;
import com.tarang.ewallet.prepaidcard.util.AccountsConstants;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author  : kedarnathd
 * @date    : July 26, 2016
 * @time    : 4:19:09 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@SuppressWarnings({"unchecked"})
public class PrepaidCardDaoImpl implements PrepaidCardDao {

	private static final Logger LOGGER = Logger.getLogger(PrepaidCardDaoImpl.class);
	
	private HibernateOperations hibernateOperations;

	public PrepaidCardDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}

	public PrepaidCardAccount createPrepaidCard(PrepaidCardAccount prepaidCardAccount) throws WalletException {
		try {
			hibernateOperations.save(prepaidCardAccount);
			hibernateOperations.flush();
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			throw new WalletException(GlobalLitterals.DB_DUPLICATE_ENTRY, ex);
		}
		return prepaidCardAccount;
	}

	@Override
	public PrepaidCardAccount getPrepaidCard(Long customerId) throws WalletException {
		List<PrepaidCardAccount> list = (List<PrepaidCardAccount>) hibernateOperations.findByNamedQuery("getPrepaidCardByCustomer", customerId);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			return null;
		}
	}

	
	
}
