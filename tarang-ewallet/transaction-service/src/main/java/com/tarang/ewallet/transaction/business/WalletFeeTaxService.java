/**
 * 
 */
package com.tarang.ewallet.transaction.business;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.WalletFee;
import com.tarang.ewallet.model.WalletTax;


/**
 * @author  : prasadj
 * @date    : Jan 16, 2013
 * @time    : 12:40:39 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface WalletFeeTaxService {

	WalletFee addFee(WalletFee walletFee) throws WalletException;
	
	WalletFee getFeeById(Long feeId) throws WalletException;
	
	WalletFee getFeeByTransaction(Long transactionId) throws WalletException;

	WalletTax addTax(WalletTax walletTax) throws WalletException;
	
	WalletTax getTaxById(Long taxId) throws WalletException;
	
	WalletTax getTaxByTransaction(Long transactionId) throws WalletException;

}
