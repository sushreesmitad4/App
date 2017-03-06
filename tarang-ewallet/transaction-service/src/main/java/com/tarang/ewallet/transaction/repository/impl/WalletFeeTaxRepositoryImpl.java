/**
 * 
 */
package com.tarang.ewallet.transaction.repository.impl;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.WalletFee;
import com.tarang.ewallet.model.WalletTax;
import com.tarang.ewallet.transaction.dao.WalletFeeTaxDao;
import com.tarang.ewallet.transaction.repository.WalletFeeTaxRepository;


/**
 * @author  : prasadj
 * @date    : Jan 16, 2013
 * @time    : 12:44:30 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class WalletFeeTaxRepositoryImpl implements WalletFeeTaxRepository {

	private WalletFeeTaxDao walletFeeTaxDao;

	public WalletFeeTaxRepositoryImpl(WalletFeeTaxDao walletFeeTaxDao) {
		this.walletFeeTaxDao = walletFeeTaxDao;
	}

	@Override
	public WalletFee addFee(WalletFee walletFee) throws WalletException {
		return walletFeeTaxDao.addFee(walletFee);
	}

	@Override
	public WalletFee getFeeById(Long feeId) throws WalletException {
		return walletFeeTaxDao.getFeeById(feeId);
	}

	@Override
	public WalletFee getFeeByTransaction(Long transactionId) throws WalletException {
		return walletFeeTaxDao.getFeeByTransaction(transactionId);
	}

	@Override
	public WalletTax addTax(WalletTax walletTax) throws WalletException {
		return walletFeeTaxDao.addTax(walletTax);
	}

	@Override
	public WalletTax getTaxById(Long taxId) throws WalletException {
		return walletFeeTaxDao.getTaxById(taxId);
	}

	@Override
	public WalletTax getTaxByTransaction(Long transactionId) throws WalletException {
		return walletFeeTaxDao.getTaxByTransaction(transactionId);
	}
}
