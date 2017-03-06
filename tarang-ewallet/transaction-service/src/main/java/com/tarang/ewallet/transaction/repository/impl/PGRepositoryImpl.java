package com.tarang.ewallet.transaction.repository.impl;

import java.util.List;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.History;
import com.tarang.ewallet.transaction.dao.PGDao;
import com.tarang.ewallet.transaction.repository.PGRepository;


public class PGRepositoryImpl implements PGRepository{
	
	private PGDao pGDao;
	
	PGRepositoryImpl(PGDao pGDao){
		this.pGDao = pGDao;
	}
	
	@Override
	public History saveTransaction(History history) throws WalletException {
		return pGDao.saveTransaction(history);
	}
	
	@Override
	public History getHistory(String orderId, Long accountId, Double amount) throws WalletException{
		return pGDao.getHistory(orderId, accountId, amount);
	}

	@Override
	public History getHistory(Long historyId) throws WalletException{
		return pGDao.getHistory(historyId);
	}

	@Override
	public History updateHistory(History history) throws WalletException {
		return pGDao.updateHistory(history);
	}

	@Override
	public History getHistoryBypgTxnId(Long pgTxnId){
		List<History> list = pGDao.getHistoryBypgTxnId(pgTxnId);
		return list != null && list.size() > 0?list.get(0):null;
	}

	@Override
	public History getHistoryByAccountId(Long accountId) throws WalletException {
		return pGDao.getHistoryByAccountId(accountId);
	}

}
