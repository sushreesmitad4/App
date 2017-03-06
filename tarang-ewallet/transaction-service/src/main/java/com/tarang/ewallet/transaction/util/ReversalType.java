/**
 * 
 */
package com.tarang.ewallet.transaction.util;

/**
 * @author  : prasadj
 * @date    : Mar 6, 2013
 * @time    : 10:49:56 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface ReversalType {

	Long REFUND = 1L;
	
	Long CHARGE_BACK = 2L;
}
