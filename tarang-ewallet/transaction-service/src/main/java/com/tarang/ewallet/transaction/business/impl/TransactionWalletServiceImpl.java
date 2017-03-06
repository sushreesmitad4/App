/**
 * 
 */
package com.tarang.ewallet.transaction.business.impl;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.PaymentDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.model.Payment;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.transaction.repository.TransactionWalletRepository;


/**
 * @author  : prasadj
 * @date    : Jan 12, 2013
 * @time    : 10:42:18 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class TransactionWalletServiceImpl implements TransactionWalletService {

	private TransactionWalletRepository transactionWalletRepository;

	public TransactionWalletServiceImpl(TransactionWalletRepository transactionWalletRepository) {
		this.transactionWalletRepository = transactionWalletRepository;
	}
	
	@Override
	public WalletTransaction initiateTransaction(WalletTransaction transaction) throws WalletException {
		return transactionWalletRepository.initiateTransaction(transaction);
	}
	
	@Override
	public WalletTransaction settleTransaction(Long transactionId) throws WalletException {
		return transactionWalletRepository.settleTransaction(transactionId);
	}

	@Override
	public WalletTransaction cancelTransaction(Long transactionId) throws WalletException {
		return transactionWalletRepository.cancelTransaction(transactionId);
	}

	@Override
	public WalletTransaction rejectTransaction(Long transactionId) throws WalletException {
		return transactionWalletRepository.rejectTransaction(transactionId);
	}

	@Override
	public WalletTransaction getTransaction(Long transactionId) throws WalletException {
		return transactionWalletRepository.getTransaction(transactionId);
	}
	
	@Override
	public List<Object[]> getLastNdaysTransactionsCountDayWise(
			Date fromdate, Date todate, Long transactionType)throws WalletException {
		return transactionWalletRepository.getLastNdaysTransactionsCountDayWise(fromdate, todate, transactionType);
	}

	@Override
	public void updateTransactionForNonRegisters(Long txnId, Long authId) throws WalletException {
		transactionWalletRepository.updateTransactionForNonRegisters(txnId, authId);
	}

	@Override
	public Payment createPayment(Payment payment) throws WalletException {
		return transactionWalletRepository.createPayment(payment);
	}

	@Override
	public PaymentDto customerPaymentFromMerchant(PaymentDto paymentDto) throws WalletException {
		return transactionWalletRepository.customerPaymentFromMerchant(paymentDto);
	}

	@Override
	public void reversalTransaction(Long transactionId, Long reversalType, Double approvedAmount) throws WalletException {
		transactionWalletRepository.reversalTransaction(transactionId, reversalType, approvedAmount);
	}

}
