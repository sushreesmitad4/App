package com.tarang.ewallet.transaction.dao;

import java.util.List;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.History;


public interface PGDao {
	 
	History saveTransaction(History history) throws WalletException;
	
	History getHistory(String orderId, Long accountId, Double amount) throws WalletException;

	History getHistory(Long historyId) throws WalletException;
	
	History updateHistory(History history) throws WalletException;

	List<History> getHistoryBypgTxnId(Long pgTxnId);

	History getHistoryByAccountId(Long accountId) throws WalletException;

}
