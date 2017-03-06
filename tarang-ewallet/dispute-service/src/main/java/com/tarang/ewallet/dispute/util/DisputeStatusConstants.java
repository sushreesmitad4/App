package com.tarang.ewallet.dispute.util;
/**
 * @author  : kedarnathd
 * @date    : Feb 12, 2013
 * @time    : 5:54:03 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface DisputeStatusConstants {
	/**
	 * Dispute Statuses:
	 */
	Long PENDING = 1L;
	
	Long ADMIN_REJECTED = 2L;
	
	Long MERCHANT_TO_PAY = 3L;
	
	Long APPROVED = 4L;
	
	Long MERCHANT_REJECTED = 5L;
	
	Long FORCE_WITHDRAWAL = 6L;
	
}
