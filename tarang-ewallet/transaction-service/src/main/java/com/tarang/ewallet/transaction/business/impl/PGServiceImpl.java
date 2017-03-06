package com.tarang.ewallet.transaction.business.impl;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.History;
import com.tarang.ewallet.transaction.business.PGService;
import com.tarang.ewallet.transaction.repository.PGRepository;


public class PGServiceImpl implements PGService {
	
	private PGRepository pGRepository;

	public PGServiceImpl(PGRepository pGRepository) {
		this.pGRepository = pGRepository;
	}
	
	@Override
	public History saveTransaction(History history) throws WalletException {
		return pGRepository.saveTransaction(history);
	}
	
	@Override
	public History getHistory(String orderId, Long accountId, Double amount) throws WalletException{
		return pGRepository.getHistory(orderId, accountId, amount);
	}

	@Override
	public History getHistory(Long historyId) throws WalletException{
		return pGRepository.getHistory(historyId);
	}

	@Override
	public History updateHistory(History history) throws WalletException {
		return pGRepository.updateHistory(history);
	}

	@Override
	public History getHistoryBypgTxnId(Long pgTxnId) {
		return pGRepository.getHistoryBypgTxnId(pgTxnId);
	}

	@Override
	public History getHistoryByAccountId(Long accountId) throws WalletException {
		return pGRepository.getHistoryByAccountId(accountId);
	}

}
