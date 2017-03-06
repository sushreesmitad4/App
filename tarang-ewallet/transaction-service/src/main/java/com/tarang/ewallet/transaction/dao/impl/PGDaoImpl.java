package com.tarang.ewallet.transaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.History;
import com.tarang.ewallet.transaction.dao.PGDao;
import com.tarang.ewallet.transaction.util.WalletTransactionUtil;


@SuppressWarnings("unchecked")
public class PGDaoImpl implements PGDao {
	
	private HibernateOperations hibernateOperations;
	
	public PGDaoImpl(HibernateOperations hibernateOperations){
		this.hibernateOperations = hibernateOperations;
	}
	
	@Override
	public History saveTransaction(History history) throws WalletException {
		hibernateOperations.save(history);
		return history;
	}

	@Override
	public History getHistory(String orderId, Long accountId, Double amount) throws WalletException{
		List<History> list = hibernateOperations.findByNamedQuery("findHistoryByOrderId_accountId_success", new Object[]{orderId, accountId, amount});
		if(list != null && list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public History getHistory(Long historyId) throws WalletException{
		List<History> list = hibernateOperations.findByNamedQuery("findHistoryById", historyId);
		if(list != null && list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public History updateHistory(History history) throws WalletException {
		hibernateOperations.update(history);
		return history;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<History> getHistoryBypgTxnId(final Long pgTxnId) {
		
		List<History> list = (List<History>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery("select id, amount from History as hi where hi.authHistoryId in (select au.id from authTxn as au where au.pgTxnId = :_pgTxnId)");
                q.setParameter("_pgTxnId", pgTxnId);
                return WalletTransactionUtil.getDtoObjectsForTxnGrid(q.list());
			}
		});
		return list;
	}

	@Override
	public History getHistoryByAccountId(Long accountId) throws WalletException {
		List<History> list = hibernateOperations.findByNamedQuery("findHistoryOrderId_Amount_By_AccountId", accountId);
		if(list != null && list.size() >= 0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
