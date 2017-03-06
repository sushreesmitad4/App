/**
 * 
 */
package com.tarang.ewallet.transaction.dao;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.model.Payment;


/**
 * @author  : prasadj
 * @date    : Jan 12, 2013
 * @time    : 10:44:47 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface TransactionWalletDao {
	
	WalletTransaction createTransaction(WalletTransaction transaction);
	
	WalletTransaction settleTransaction(Long txId, Long status, Double payeeBalance);

	WalletTransaction cancelOrRejectTransaction(Long txId, Long status, Double payerBalance);

	WalletTransaction getTransaction(Long transactionId);
	
	List<Object[]> getLastNdaysTransactionsCountDayWise(Date fromdate, Date todate, Long transactionType) throws WalletException;

	WalletTransaction updateTransaction(WalletTransaction transaction);
	
	Payment createPayment(Payment payment) throws WalletException;

}
