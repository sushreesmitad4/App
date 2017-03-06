/**
 * 
 */
package com.tarang.ewallet.prepaidcard.util;

/**
 * @author  : prasadj
 * @date    : Nov 27, 2012
 * @time    : 12:17:44 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface AccountsConstants {

	String BANK_ACCOUNT = "BANK";
	
	String CARD_ACCOUNT = "CARD";

	Boolean ALL_ACCOUNTS = true;
	
	Boolean DEFAULT_ACCOUNT_STATUS = false;
	
	String ERROR_MSG_INVALID_ACCOUNT = "invalid.account";
	
	String ERROR_MSG_WRONG_INFO = "common.error.card.wrong.info";

	String ERROR_MSG_NON_EXISTS = "non-exists.account";

	String ERROR_MSG_UPDATE_EXPIRYDATE_BANK = "update.expirydate.bank.account";
		
	Long NOT_DELETE = 1L;
	
	Long SOFT_DELETE = 2L; 
	
	String PG_RESPONSE_CODE = "";
	
	String JOINT_CARD_ACCOUNT_EXISTWITHAUTHID= "joint.card.account.existwithauthid";
	
	String JOINT_CARD_ACCOUNT_EXCEEDED = "joint.card.accounts.exceeded";
	
	String JOINT_BANK_ACCOUNT_EXISTWITHAUTHID= "joint.bank.account.existwithauthid";
	
	String JOINT_BANK_ACCOUNT_EXCEEDED = "joint.bank.accounts.exceeded";
	
	String OLD_ACCOUNT = "added.old.account.success";
	
}
