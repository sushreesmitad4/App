package com.tarang.ewallet.transaction.dao;

import java.util.List;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.ReloadMoney;


public interface ReloadMoneyDao {
	
	ReloadMoney createReloadMoney(ReloadMoney reloadMoney) throws WalletException;

	List<Long> getNonSettlementHistoryIds() throws WalletException;

}
