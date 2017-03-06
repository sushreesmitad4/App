package com.tarang.ewallet.transaction.business.impl;

import java.util.List;

import com.tarang.ewallet.dto.ReloadMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.transaction.business.ReloadMoneyService;
import com.tarang.ewallet.transaction.repository.ReloadMoneyRepository;


public class ReloadMoneyServiceImpl implements ReloadMoneyService{
	
	private ReloadMoneyRepository reloadMoneyRepository;
	
	public ReloadMoneyServiceImpl(ReloadMoneyRepository reloadMoneyRepository) {
		this.reloadMoneyRepository = reloadMoneyRepository;
		
	}
	@Override
	public ReloadMoneyDto createReloadMoney(ReloadMoneyDto reloadMoneyDto) throws WalletException {
		return reloadMoneyRepository.createReloadMoney(reloadMoneyDto);
	}
	@Override
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
		
	}
}
