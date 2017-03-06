package com.tarang.ewallet.reconcile.util;
/**
 * @author  : kedarnathd
 * @date    : March 07, 2013
 * @time    : 9:58:50 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface ReconcileConstants {
	
	Long MISS_MATCHED_WITH_HISTORY = 1L;
	
	Long NOT_EXISTS_IN_HISTORY = 2L;
	
	int FIXED_COLUMN = 44;
	
	String CURRENCY_EXPRESSION = "\\d{0,9}(\\.\\d{1,2})?";
	
	Double CURRENCY_MAX_LIMIT = 1000000000.0;
	
	String NUMBER_EXPRESSION = "[0-9]*";
	
	String CREATE_RECONCILE_EXCEPTION = "error.msg.fails.tocreate.reconcile";
	
	String ERROR_PGTXNID_EXIST = "pgTxnId all ready exist in reconcile";
	
	String COLUMN_NAME_LINE_NUMBER = "Line Number";
	
}
