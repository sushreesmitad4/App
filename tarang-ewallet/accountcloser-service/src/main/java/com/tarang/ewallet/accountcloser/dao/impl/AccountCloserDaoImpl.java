package com.tarang.ewallet.accountcloser.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.accountcloser.dao.AccountCloserDao;
import com.tarang.ewallet.accountcloser.util.AccountCloserQueries;
import com.tarang.ewallet.accountcloser.util.AccountCloserStatus;
import com.tarang.ewallet.accountcloser.util.AccountCloserUtil;
import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AccountCloser;
import com.tarang.ewallet.model.AccountCloserMessage;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class AccountCloserDaoImpl implements AccountCloserDao , AccountCloserQueries{
	
	private HibernateOperations hibernateOperations;

	public AccountCloserDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}
	
	@Override
	public AccountCloser createAccountCloser(AccountCloser accountCloser) throws WalletException {
		try {
			hibernateOperations.save(accountCloser);
			hibernateOperations.flush();
		}catch(Exception ex){
			throw new WalletException(ex.getMessage(), ex);
		}
		return accountCloser;
	}
	
	@Override
	public AccountCloserMessage addMessage(AccountCloserMessage message) throws WalletException {
		try {
			hibernateOperations.save(message);
			hibernateOperations.update(message.getAccountCloser());
			hibernateOperations.flush();
		}catch(Exception ex){
			throw new WalletException(ex.getMessage(), ex);
		}
		return message;
	}
	
	@Override
	public AccountCloser getAccountCloserByUser(Long authId) throws WalletException {
		List<AccountCloser> list = (List<AccountCloser>) hibernateOperations.findByNamedQuery("getAccountCloserByAuthId", authId);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public AccountCloser getAccountCloserById(Long id) throws WalletException {
		List<AccountCloser> list = (List<AccountCloser>) hibernateOperations.findByNamedQuery("getAccountCloserById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<AccountCloserDto> getAccountCloserList(final Integer noOfRecords,
			final Long languageId, final Date fromDate, final Date toDate, final String userType) throws WalletException {
		List<AccountCloserDto> list = (List<AccountCloserDto>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(ACCOUNT_CLOSER_LIST);
				  q.setParameter("_limit", noOfRecords);
				  q.setParameter("_languageId", languageId);
				  q.setParameter("_fromDate", fromDate);
				  q.setParameter("_toDate", toDate);
				  q.setParameter("_userType", userType);
				  return AccountCloserUtil.getGridDataForAccountCloserList(q.list());
			}
		});
		return list;
	}

	@Override
	public AccountCloserDto getAccountCloserForView(final Long accountCloserId, final Long languageId) throws WalletException {
		List<AccountCloserDto> list = (List<AccountCloserDto>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(ACCOUNT_CLOSER_VIEW);
				  q.setParameter("_accountcloserid", accountCloserId);
				  q.setParameter("_languageId", languageId);
				  return AccountCloserUtil.getGridDataForgetAccountCloserForView(q.list());
			}
		});
		return list != null && list.size() > 0 ? list.get(0):null;
	}

	@Override
	public Boolean getAccountCloserStatusById(Long authId) throws WalletException {
		Boolean accountCloserStatus = Boolean.FALSE;
		AccountCloser accountCloser = getAccountCloserByUser(authId);
		if(accountCloser != null && accountCloser.getStatus().equals(AccountCloserStatus.CLOSED)){
			accountCloserStatus = Boolean.TRUE;
		}
		return accountCloserStatus;
	}

	@Override
	public List<Long> getApprovalAccountClosers(Long status, Date date) throws WalletException {
		List<Object> listOfObjects = (List<Object>) hibernateOperations.findByNamedQuery("getApprovalAccountClosers", status, date);
		List<Long> list =new ArrayList<Long>();
		for (int i = 0; i < listOfObjects.size(); i++) {
			list.add((Long)listOfObjects.get(i));
		}
		return list;
	}

	/*@Override
	public AccountCloser updateAccountCloser(AccountCloser accountCloser)
			throws WalletException {
		try {
			hibernateOperations.save(message);
			hibernateOperations.update(message.getAccountCloser());
			hibernateOperations.flush();
		}
		catch(Exception ex){
			throw new WalletException(ex.getMessage(), ex);
		}
		return accountCloser;
	}*/
}
