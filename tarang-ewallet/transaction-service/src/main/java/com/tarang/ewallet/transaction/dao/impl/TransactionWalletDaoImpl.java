/**
 * 
 */
package com.tarang.ewallet.transaction.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Payment;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.transaction.dao.TransactionWalletDao;
import com.tarang.ewallet.util.QueryConstants;


/**
 * @author  : prasadj
 * @date    : Jan 12, 2013
 * @time    : 10:45:27 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes"})
public class TransactionWalletDaoImpl implements TransactionWalletDao, QueryConstants {

	private static final Logger LOGGER = Logger.getLogger(TransactionWalletDaoImpl.class);
	
	private HibernateOperations hibernateOperations;
	
	TransactionWalletDaoImpl(HibernateOperations hibernateOperations){
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public WalletTransaction createTransaction(WalletTransaction transaction) {
		hibernateOperations.save(transaction);
		return transaction;
	}

	@Override
	public WalletTransaction settleTransaction(Long txId, Long status, Double payeeBalance) {
		WalletTransaction transaction = getTransaction(txId);
		transaction.setStatus(status);
		transaction.setPayeeBalance(payeeBalance);
		transaction.setUpdatededDate(new Date());
		hibernateOperations.update(transaction);
		return transaction;
	}

	@Override
	public WalletTransaction cancelOrRejectTransaction(Long txId, Long status, Double payerBalance) {
		WalletTransaction transaction = getTransaction(txId);
		transaction.setStatus(status);
		transaction.setPayerBalance(payerBalance);
		transaction.setUpdatededDate(new Date());
		hibernateOperations.update(transaction);
		return transaction;
	}

	@Override
	public WalletTransaction getTransaction(Long transactionId) {
		try{
			List<WalletTransaction> list = hibernateOperations.findByNamedQuery("findTransactionById", transactionId);
			if(list != null && list.size() == 1){
				return list.get(0);
			}else{			
				return null;
			}
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<Object[]> getLastNdaysTransactionsCountDayWise(final Date fromdate, final Date todate, 
			final  Long transactionType) throws WalletException {
		
		List<Object[]> list = (List<Object[]>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				String hql = "select tx.creationDate, count(*) from WalletTransaction tx where tx.typeOfTransaction = :_transactionType "
						+ "and tx.creationDate between :_fromDate and :_toDate group by tx.creationDate order by tx.creationDate";
                Query q = session.createSQLQuery(hql);
                q.setParameter(Q_TRANSACTION_TYPE, transactionType);
                q.setParameter(Q_FROM_DATE, fromdate);
                q.setParameter(Q_TO_DATE, todate);
                return q.list();
			}
		});
		return list;
	}

	@Override
	public WalletTransaction updateTransaction(WalletTransaction transaction) {
		hibernateOperations.update(transaction);
		return transaction;
	}

	@Override
	public Payment createPayment(Payment payment) throws WalletException {
		try{
			hibernateOperations.save(payment);
			return payment;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

}