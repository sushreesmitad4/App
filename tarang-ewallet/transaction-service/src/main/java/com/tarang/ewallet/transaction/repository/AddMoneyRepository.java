package com.tarang.ewallet.transaction.repository;

import com.tarang.ewallet.dto.AddMoneyDto;
import com.tarang.ewallet.exception.WalletException;


public interface AddMoneyRepository {

	AddMoneyDto createAddMoney(AddMoneyDto addMoneyDto) throws WalletException;

	/*void settlementReloadMoney(ReloadMoneyDto reloadMoneyDto) throws WalletException;
	
	List<Long> getNonSettlementHistoryIds() throws WalletException;
	
	void postNonSettlementTransactions() throws WalletException;*/

}
