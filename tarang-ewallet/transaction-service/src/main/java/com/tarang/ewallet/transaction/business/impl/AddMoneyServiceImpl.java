package com.tarang.ewallet.transaction.business.impl;

import com.tarang.ewallet.dto.AddMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.transaction.business.AddMoneyService;
import com.tarang.ewallet.transaction.repository.AddMoneyRepository;


public class AddMoneyServiceImpl implements AddMoneyService{
	
	private AddMoneyRepository addMoneyRepository;
	
	public AddMoneyServiceImpl(AddMoneyRepository addMoneyRepository) {
		this.addMoneyRepository = addMoneyRepository;
		
	}
	@Override
	public AddMoneyDto createAddMoney(AddMoneyDto addMoneyDto) throws WalletException {
		return addMoneyRepository.createAddMoney(addMoneyDto);
	}
	/*@Override
	public void settlementReloadMoney(ReloadMoneyDto reloadMoneyDto) throws WalletException {
		reloadMoneyRepository.settlementReloadMoney(reloadMoneyDto);
		
	}
	@Override
	public List<Long> getNonSettlementHistoryIds() throws WalletException {
		return reloadMoneyRepository.getNonSettlementHistoryIds();
	}
	@Override
	public void postNonSettlementTransactions() throws WalletException {
		reloadMoneyRepository.postNonSettlementTransactions();
		
	}*/
}
