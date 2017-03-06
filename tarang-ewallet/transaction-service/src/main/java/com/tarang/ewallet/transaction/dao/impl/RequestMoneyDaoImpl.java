package com.tarang.ewallet.transaction.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.RequestMoney;
import com.tarang.ewallet.transaction.dao.RequestMoneyDao;
import com.tarang.ewallet.transaction.util.WalletTransactionUtil;


@SuppressWarnings("unchecked")
public class RequestMoneyDaoImpl implements RequestMoneyDao {
	
	private static final Logger LOGGER = Logger.getLogger(RequestMoneyDaoImpl.class);
	
	private HibernateOperations hibernateOperations;
	
	RequestMoneyDaoImpl(HibernateOperations hibernateOperations){
		this.hibernateOperations = hibernateOperations;
	}
	
	@Override
	public RequestMoney createRequestMoney(RequestMoney receiveMoney) throws WalletException {
		hibernateOperations.save(receiveMoney);
		return receiveMoney;
	}

	@Override
	public RequestMoney getRequestMoney(Long id) throws WalletException {
		try{
			List<RequestMoney> list = hibernateOperations.findByNamedQuery("findReceiveMoneyById", id);
			if(list != null && list.size() == 1){
				return list.get(0);
			}else{			
				throw new WalletException("unique.requestmoney.exception");
			}
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
		
	}
	
	//@SuppressWarnings("rawtypes")
	public List<RequestMoneyDto> getRequestMoneyDtoList(final Long authId) throws WalletException {
		return hibernateOperations.find(WalletTransactionUtil.REQUEST_MONEY_LIST_QUERY, new Object[]{authId, authId});
	}

	@Override
	public RequestMoney updateRequestMoney(RequestMoney receiveMoney) throws WalletException {
		hibernateOperations.update(receiveMoney);
		return receiveMoney;
	}

}
