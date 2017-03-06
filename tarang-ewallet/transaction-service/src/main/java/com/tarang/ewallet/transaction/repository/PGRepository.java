package com.tarang.ewallet.transaction.repository;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.History;


public interface PGRepository {
	
	History saveTransaction(History history) throws WalletException;
	
	History getHistory(String orderId, Long accountId, Double amount) throws WalletException;

	History getHistory(Long historyId) throws WalletException;
	
	History updateHistory(History history) throws WalletException;
	
	History getHistoryBypgTxnId(Long pgTxnId);

	History getHistoryByAccountId(Long accountId) throws WalletException;
	
}
