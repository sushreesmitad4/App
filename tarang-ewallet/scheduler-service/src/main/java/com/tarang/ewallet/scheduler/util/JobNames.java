/**
 * 
 */
package com.tarang.ewallet.scheduler.util;

/**
 * @author  : prasadj
 * @date    : Mar 7, 2013
 * @time    : 2:50:28 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface JobNames {

	String DISPUTE_JOB = "dispute.job";
	
	String RECONCILE_JOB = "reconcile.job";

	String SETTLEMENT_JOB = "settlement.job";

	String CANCEL_NON_REGISTER_WALLET_TXNS_JOB = "cancel.non.register.wallet.txns.job";

	String PENDING_ACCOUNT_CLOSERS_JOB  = "account.closing.job";
	
	String SEND_MONEY_JOB  = "sendmoney.job.";
	
	String UNBLOCKED_USER_JOB  = "unblocked.user.job.";
	
	String RESENT_EMAIL_JOB  = "resent.email.job";
}
