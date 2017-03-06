package com.tarang.ewallet.transaction.dao;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AddMoney;


public interface AddMoneyDao {
	
	AddMoney createAddMoney(AddMoney addMoney) throws WalletException;

	/*List<Long> getNonSettlementHistoryIds() throws WalletException;*/

}
