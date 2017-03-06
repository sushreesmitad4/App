/**
 * 
 */
package com.tarang.ewallet.transaction.business;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.PaymentDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.model.Payment;


/**
 * @author  : prasadj
 * @date    : Jan 12, 2013
 * @time    : 10:39:59 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface TransactionWalletService {

	WalletTransaction initiateTransaction(WalletTransaction transaction) throws WalletException;

	WalletTransaction settleTransaction(Long transactionId) throws WalletException;

	WalletTransaction cancelTransaction(Long transactionId) throws WalletException;
	
	WalletTransaction rejectTransaction(Long transactionId) throws WalletException;

	WalletTransaction getTransaction(Long transactionId) throws WalletException;
	
	List<Object[]> getLastNdaysTransactionsCountDayWise(Date fromdate, Date todate, Long transactionType) throws WalletException;
	
	void updateTransactionForNonRegisters(Long txnId, Long authId)throws WalletException;
	
	Payment createPayment(Payment payment) throws WalletException;

	PaymentDto customerPaymentFromMerchant(PaymentDto paymentDto) throws WalletException;

	void reversalTransaction(Long transactionId, Long reversalType, Double approvedAmount) throws WalletException;
	
}
