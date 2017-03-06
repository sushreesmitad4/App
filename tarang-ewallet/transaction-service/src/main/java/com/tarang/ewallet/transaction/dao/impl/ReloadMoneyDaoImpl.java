package com.tarang.ewallet.transaction.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.ReloadMoney;
import com.tarang.ewallet.transaction.dao.ReloadMoneyDao;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReloadMoneyDaoImpl implements ReloadMoneyDao{
	
	private HibernateOperations hibernateOperations;
	public ReloadMoneyDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}
	
	@Override
	public ReloadMoney createReloadMoney(ReloadMoney reloadMoney) throws WalletException {
		hibernateOperations.save(reloadMoney);
		return reloadMoney;
	}
	
	@Override
	public List<Long> getNonSettlementHistoryIds() throws WalletException{
		final String querry = "select HIS.id from authtxn AUTH JOIN (select * from history where id in (select historyid from reloadmoney))HIS " 
				+ "ON HIS.authhistoryid = AUTH.id where AUTH.pgtxnid not in (select pgtxnid from settlementtxn) order by HIS.id desc";
		List<Object> listOfObjects = (List<Object>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(querry);
				return q.list();
			}
		});
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < listOfObjects.size(); i++) {
			BigDecimal bigintId	 = (BigDecimal)listOfObjects.get(i);
			Long historyId= bigintId.longValue();
			list.add(historyId);
		}
		return list;
	}
}
