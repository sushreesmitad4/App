package com.tarang.ewallet.transaction.repository;

import java.util.List;

import com.tarang.ewallet.dto.ReloadMoneyDto;
import com.tarang.ewallet.exception.WalletException;


public interface ReloadMoneyRepository {

	ReloadMoneyDto createReloadMoney(ReloadMoneyDto reloadMoneyDto) throws WalletException;

	void settlementReloadMoney(ReloadMoneyDto reloadMoneyDto) throws WalletException;
	
	List<Long> getNonSettlementHistoryIds() throws WalletException;
	
	void postNonSettlementTransactions() throws WalletException;

}
