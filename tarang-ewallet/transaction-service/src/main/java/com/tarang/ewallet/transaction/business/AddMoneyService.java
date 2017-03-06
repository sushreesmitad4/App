package com.tarang.ewallet.transaction.business;

import com.tarang.ewallet.dto.AddMoneyDto;
import com.tarang.ewallet.exception.WalletException;


public interface AddMoneyService {
	
	AddMoneyDto createAddMoney(AddMoneyDto addMoneyDto) throws WalletException;
	
	//void settlementReloadMoney(ReloadMoneyDto reloadMoneyDto) throws WalletException;
	
	//List<Long> getNonSettlementHistoryIds() throws WalletException;
	
	//void postNonSettlementTransactions() throws WalletException;
}
