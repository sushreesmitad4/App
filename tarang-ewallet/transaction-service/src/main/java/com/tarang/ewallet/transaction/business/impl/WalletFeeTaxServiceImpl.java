/**
 * 
 */
package com.tarang.ewallet.transaction.business.impl;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.WalletFee;
import com.tarang.ewallet.model.WalletTax;
import com.tarang.ewallet.transaction.business.WalletFeeTaxService;
import com.tarang.ewallet.transaction.repository.WalletFeeTaxRepository;


/**
 * @author  : prasadj
 * @date    : Jan 16, 2013
 * @time    : 12:41:50 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class WalletFeeTaxServiceImpl implements WalletFeeTaxService {

	private WalletFeeTaxRepository walletFeeTaxRepository;

	public WalletFeeTaxServiceImpl(WalletFeeTaxRepository walletFeeTaxRepository) {
		this.walletFeeTaxRepository = walletFeeTaxRepository;
	}

	@Override
	public WalletFee addFee(WalletFee walletFee) throws WalletException {
		return walletFeeTaxRepository.addFee(walletFee);
	}

	@Override
	public WalletFee getFeeById(Long feeId) throws WalletException {
		return walletFeeTaxRepository.getFeeById(feeId);
	}

	@Override
	public WalletFee getFeeByTransaction(Long transactionId) throws WalletException {
		return walletFeeTaxRepository.getFeeByTransaction(transactionId);
	}

	@Override
	public WalletTax addTax(WalletTax walletTax) throws WalletException {
		return walletFeeTaxRepository.addTax(walletTax);
	}

	@Override
	public WalletTax getTaxById(Long taxId) throws WalletException {
		return walletFeeTaxRepository.getTaxById(taxId);
	}

	@Override
	public WalletTax getTaxByTransaction(Long transactionId) throws WalletException {
		return walletFeeTaxRepository.getTaxByTransaction(transactionId);
	}

}
