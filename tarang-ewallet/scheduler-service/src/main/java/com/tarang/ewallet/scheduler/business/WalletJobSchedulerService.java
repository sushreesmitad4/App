/**
 * 
 */
package com.tarang.ewallet.scheduler.business;

import com.tarang.ewallet.exception.WalletException;

/**
 * @author vasanthar
 *
 */
public interface WalletJobSchedulerService {
	
	void disputeForceWithdrawalService() throws WalletException;
	
	void reconsilationService() throws WalletException;
	
	void pgSettlementService() throws WalletException;

	void cancelNonRegisterWalletTxnsService() throws WalletException;

	void accountCloserService() throws WalletException;
	
	void emailService() throws WalletException;

}
