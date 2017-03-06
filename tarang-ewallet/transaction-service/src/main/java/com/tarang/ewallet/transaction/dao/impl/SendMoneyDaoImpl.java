package com.tarang.ewallet.transaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.SendMoney;
import com.tarang.ewallet.model.SendMoneyRecurring;
import com.tarang.ewallet.model.SendMoneyTxn;
import com.tarang.ewallet.transaction.dao.SendMoneyDao;
import com.tarang.ewallet.transaction.util.WalletTransactionUtil;


@SuppressWarnings("unchecked")
public class SendMoneyDaoImpl implements SendMoneyDao {
	
	private static final Logger LOGGER = Logger.getLogger(SendMoneyDaoImpl.class);
	
	private HibernateOperations hibernateOperations;
	
	public SendMoneyDaoImpl(HibernateOperations hibernateOperations){
		this.hibernateOperations = hibernateOperations;
	}
	
	@Override
	public SendMoney createSendMoney(SendMoney sendMoney) throws WalletException {
		 hibernateOperations.save(sendMoney);
		return sendMoney;
	}
	
	@Override
	public SendMoney getSendMoney(Long id) throws WalletException {
		try{
			List<SendMoney> list = hibernateOperations.findByNamedQuery("findSendMoneyById", id);
			if(list != null && list.size() == 1){
				return list.get(0);
			}else{			
				throw new WalletException("unique.sendmoney.exception");
			}
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}
	
	@Override
	public SendMoney updateSendMoney(SendMoney sendMoney) throws WalletException {
		hibernateOperations.update(sendMoney);
		return sendMoney;
	}

	@Override
	public List<SendMoneyDto> getReceiveMoneyList(final Long receiverAuthId) {
		return hibernateOperations.find(WalletTransactionUtil.SEND_MONEY_LIST_FOR_REQUESTER_QUERY, receiverAuthId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateSendMoney(final Long id, final Long txnId, final Long txnStatus) throws WalletException {
		hibernateOperations.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query query = session.createQuery("update SendMoney set transactionId="+txnId+",transactionStatus= "+txnStatus+" where id ="+id);
				query.executeUpdate();
				return null;
			}
		});
		
	}

	@Override
	public SendMoneyTxn createSendMoneyTxn(SendMoneyTxn sendMoneyTxn) throws WalletException {
			hibernateOperations.save(sendMoneyTxn);
		return sendMoneyTxn;
	}

	@Override
	public SendMoneyRecurring createSendMoneyRecurring(SendMoneyRecurring sendMoneyRecurring) throws WalletException {
		hibernateOperations.save(sendMoneyRecurring);
		return sendMoneyRecurring;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void updateSendMoneyTxn(final Long id, final Long txnId, final Long txnStatus) throws WalletException {
		hibernateOperations.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query query = session.createQuery("update SendMoneyTxn set transactionId="+txnId+",transactionStatus= "+txnStatus+" where id ="+id);
				query.executeUpdate();
				return null;
			}
		});
		
	}

	@Override
	public SendMoneyTxn getSendMoneyTxn(Long transactionId) throws WalletException {
		try{
			List<SendMoneyTxn> list = hibernateOperations.findByNamedQuery("findSendMoneyTxnByTxnId", transactionId);
			if(list != null && list.size() == 1){
				return list.get(0);
			}else{			
				throw new WalletException("unique.sendmoneytxn.exception");
			}
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public SendMoneyTxn updateSendMoneyTxn(SendMoneyTxn sendMoneyTxn) throws WalletException {
			hibernateOperations.update(sendMoneyTxn);
		return sendMoneyTxn;
		
	}

	@Override
	public SendMoneyRecurring getSendMoneyRecurringBySendMoneyId(Long sendMoneyId) throws WalletException {
		try{
			List<SendMoneyRecurring> list = hibernateOperations.findByNamedQuery("findSendMoneyRecurringBySendMoneyId", sendMoneyId);
			if(list != null && list.size() == 1){
				return list.get(0);
			}else{			
				throw new WalletException("unique.sendmoneyrecurring.exception");
			}
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public SendMoneyRecurring updateSendMoneyRecurring(SendMoneyRecurring sendMoneyRecurring) throws WalletException {
		hibernateOperations.update(sendMoneyRecurring);
		return sendMoneyRecurring;
	}

	@Override
	public Boolean isJobNameExist(Long senderAuthId, String jobName) {
		List<Long> list = hibernateOperations.find(WalletTransactionUtil.IS_JOB_NAME_EXIST_QUERY, new Object[]{senderAuthId, jobName});
		if(list != null && list.size() > 0 && list.get(0) > 0){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
