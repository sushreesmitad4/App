/**
 * 
 */
package com.tarang.ewallet.dispute.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.dispute.dao.DisputeDao;
import com.tarang.ewallet.dispute.util.DisputeQueriesPostgres;
import com.tarang.ewallet.dispute.util.DisputeUtilPostgres;
import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.dto.DisputeGridDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Dispute;
import com.tarang.ewallet.model.DisputeMessage;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryConstants;


/**
 * @author vasanthar
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DisputeDaoImpl implements DisputeDao, DisputeQueriesPostgres, QueryConstants, GlobalLitterals{
	
	private HibernateOperations hibernateOperations;
		
	public DisputeDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public Dispute createDispute(Dispute dispute) throws WalletException {		
		try{
			hibernateOperations.save(dispute);
			hibernateOperations.flush();
			return dispute;
		}
		catch(Exception e){
			throw new WalletException(e.getMessage(), e);
		}		 
	}

	@Override
	public DisputeMessage addMessage(DisputeMessage message) throws WalletException {
		try {
			hibernateOperations.save(message);
			hibernateOperations.update(message.getDispute());
			hibernateOperations.flush();
		}
		catch(Exception ex){
			throw new WalletException(ex.getMessage(), ex);
		}
		return message;
	}

	@Override
	public Dispute updateDispute(Dispute dispute) throws WalletException {
		return null;
	}

	@Override
	public Dispute getDisputeById(Long id) throws WalletException {
		List<Dispute> list = (List<Dispute>) hibernateOperations.findByNamedQuery("getDisputeById", id);
		if (list != null && list.size() == 1) {
			list.get(0).getMessages().size();
			return list.get(0);
		} 
		else {
			return null;
		}
	}

	@Override
	public List<DisputeGridDto> getDisputesForCustomer(final Integer limit, final Long languageId, final Date fromDate, 
			final Date toDate, final Long payeeId, final Long payerId, final Long disputeType) throws WalletException {
		List<DisputeGridDto> list = (List<DisputeGridDto>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(DISPUTE_TRANSACTIONS_FOR_CUSTOMER);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_PAYEE_ID, payeeId);
                q.setParameter(Q_PAYER_ID, payerId);
                q.setParameter(Q_DISPUTE_TYPE, disputeType);
                return DisputeUtilPostgres.getDtoObjectsForCMAGrid(q.list(), CUSTOMER_USER_TYPE_ID);
			}
		});
		return list;
	}
	
	@Override
	public List<DisputeGridDto> getDisputesForMerchant(final Integer limit, final Long languageId, final Date fromDate, final Date toDate, 
			final Long payerId, final Long payeeId, final Long disputeType) throws WalletException {
		List<DisputeGridDto> list = (List<DisputeGridDto>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(DISPUTE_TRANSACTIONS_FOR_MERCHANT);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_PAYER_ID, payerId);
                q.setParameter(Q_PAYEE_ID, payeeId);
                q.setParameter(Q_DISPUTE_TYPE, disputeType);
                return DisputeUtilPostgres.getDtoObjectsForCMAGrid(q.list(), MERCHANT_USER_TYPE_ID);
			}
		});
		return list;
	}

	@Override
	public List<DisputeGridDto> getDisputesForAdmin(final Integer limit, final Long languageId, final Date fromDate,
			final Date toDate, final Long payeeId, final Long payerId, final Long disputeType) throws WalletException {
		List<DisputeGridDto> list = (List<DisputeGridDto>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(DISPUTE_TRANSACTIONS_FOR_ADMIN);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_PAYEE_ID, payeeId);
                q.setParameter(Q_PAYER_ID, payerId);             
                q.setParameter(Q_DISPUTE_TYPE, disputeType);
               return DisputeUtilPostgres.getDtoObjectsForCMAGrid(q.list(), ADMIN_USER_TYPE_ID);
			}
		});
		return list;
	}

	@Override
	public List<DisputeGridDto> getCustomerTxnsForRaisedispute(final Integer limit, final Date fromDate, final Date toDate,
		final Long payeeId, final Long payerId, final Long typeOfTransaction) throws WalletException {
		List<DisputeGridDto> list = (List<DisputeGridDto>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(CUSTOMER_ALL_TRANSACTIONS_FOR_RAISE_DISPUTE);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_PAYEE_ID, payeeId);
                q.setParameter(Q_PAYER_ID, payerId);
                q.setParameter("_typeOfTransaction", typeOfTransaction);
                return DisputeUtilPostgres.getDtoObjectsForTxnGrid(q.list());
			}
		});
		return list;
	}

	@Override
	public List<DisputeDto> getCustomerRaiseOrUpdateDispute(final Long languageId, final Long txnId) throws WalletException {
		List<DisputeDto> list = (List<DisputeDto>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(CUSTOMER_RAISE_OR_UPDATE_DISPUTE_PAGE);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_TXN_ID, txnId);             
                return DisputeUtilPostgres.getModelToDto(q.list());
			}
		});
		return list;
	}

	@Override
	public List<DisputeDto> getAdmineOrMerchantUpdateDispute(final Long languageId, final Long txnId) throws WalletException {
		List<DisputeDto> list = (List<DisputeDto>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(ADMIN_OR_MERCHANT_UPDATE_DISPUTE);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_TXN_ID, txnId);             
                return DisputeUtilPostgres.getModelToDtoForMerchantUpdate(q.list());
			}
		});
		return list;
	}

	@Override
	public List<Long> getMerchantToPayStatusDisputeIds(Long status, Date date)
			throws WalletException {
		List<Object> listOfObjects = (List<Object>) hibernateOperations.findByNamedQuery("getMerchantToPayStatusDisputeIds", status, date);
		List<Long> list =new ArrayList<Long>();
		for (int i = 0; i < listOfObjects.size(); i++) {
			list.add((Long)listOfObjects.get(i));
		}
		return list;
	}

	@Override
	public Boolean isDisputeExistForTxnId(Long txnid) throws WalletException {
		Boolean flag=false;
		List<Object> listOfObjects = (List<Object>) hibernateOperations.findByNamedQuery("isDisputeExistForTxnId", txnid);
		if(listOfObjects.size() > 0){
			flag= true;
		}
		return flag;
	}
	
	@Override
	public Integer getDisputeCountForAccClose(final Long auth) {
		
		
		List list = hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(DISPUTE_COUNT_QUERY);
				q.setParameter(0, auth);
				return q.list();
			}
		});
		
		if(list != null && list.size() > 0) {
			BigDecimal bi = new BigDecimal((BigInteger)list.get(0));
			return bi.intValue();
		}
		return 0;
	}

	@Override
	public Integer getActiveDisputeForMerchant(final Long authenticationId) {
		List list = hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(MERCHANT_ACTIVE_DISPUTE_QUERY);
				q.setParameter(0, authenticationId);
				return q.list();
			}
		});
		
		if(list != null && list.size() > 0) {
			BigDecimal bi = (BigDecimal)list.get(0);
			return bi.intValue();
		}
		return 0;
	}

}
