package com.tarang.ewallet.transaction.business;

import java.util.List;

import com.tarang.ewallet.dto.ReloadMoneyDto;
import com.tarang.ewallet.exception.WalletException;


public interface ReloadMoneyService {
	
	ReloadMoneyDto createReloadMoney(ReloadMoneyDto reloadMoneyDto) throws WalletException;
	
	void settlementReloadMoney(ReloadMoneyDto reloadMoneyDto) throws WalletException;
	
	List<Long> getNonSettlementHistoryIds() throws WalletException;
	
	void postNonSettlementTransactions() throws WalletException;
}
