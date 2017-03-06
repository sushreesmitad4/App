/**
 * 
 */
package com.tarang.ewallet.transaction.util;

/**
 * @author  : prasadj
 * @date    : Jan 16, 2013
 * @time    : 12:22:18 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface WalletTransactionStatus {

	Long NOT_STARTED = 0L;
	
	Long PENDING = 1L;
	
	Long CANCEL = 2L;
	
	Long REJECT = 3L;

	Long SUCCESS = 4L;
	
	Long SCHEDULE = 5L;

	Long FAILED = 6L;
	
	Long EXPIRED = 7L;
}
