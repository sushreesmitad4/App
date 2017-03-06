package com.tarang.ewallet.transaction.dao.impl;

import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AddMoney;
import com.tarang.ewallet.transaction.dao.AddMoneyDao;

public class AddMoneyDaoImpl implements AddMoneyDao{
	
	private HibernateOperations hibernateOperations;
	public AddMoneyDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}
	
	@Override
	public AddMoney createAddMoney(AddMoney addMoney) throws WalletException {
		hibernateOperations.save(addMoney);
		return addMoney;
	}
	
	/*@Override
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
	}*/
}
