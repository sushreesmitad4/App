/**
 * 
 */
package com.tarang.ewallet.transaction.util;

/**
 * @author  : prasadj
 * @date    : Jan 16, 2013
 * @time    : 8:39:53 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface WalletTransactionTypes {

	// customer Financial transaction types
	Long P_TO_P = 1L;
	
	Long P_TO_M = 2L;
	
	Long P_TO_NP = 3L;

	Long WITHDRAW_MONEY_TO_CARD_CUSTOMER = 4L;

	Long WITHDRAW_MONEY_TO_BANK_CUSTOMER = 5L;

	Long RELOAD_MONEY_FROM_CARD_CUSTOMER = 6L;

	Long RELOAD_MONEY_FROM_BANK_CUSTOMER = 7L;
	
	Long PENNY_DROP_FROM_CARD_CUSTOMER = 8L;
	
	Long PENNY_DROP_FROM_BANK_CUSTOMER = 9L;
	
	Long RECOVER_FROM_CARD_CUSTOMER = 10L;

	Long RECOVER_FROM_BANK_CUSTOMER = 11L;
	
	// customer Non-Financial transaction types
	Long ANUAL_FEE_CUSTOMER = 12L;
	
	Long REGISTRTION_FEE_CUSTOMER = 13L;
	
	// customer Financial periodic transaction types
	Long QUARTARLY_FEE_CUSTOMER = 14L;
	
	Long MONTHLY_FEE_CUSTOMER = 15L;
	

	// merchant Financial transaction types
	Long M_TO_P = 16L;
	
	Long M_TO_M = 17L;
	
	Long M_TO_NP = 18L;

	Long WITHDRAW_MONEY_TO_CARD_MERCHANT = 19L;

	Long WITHDRAW_MONEY_TO_BANK_MERCHANT = 20L;

	Long RELOAD_MONEY_FROM_CARD_MERCHANT = 21L;

	Long RELOAD_MONEY_FROM_BANK_MERCHANT = 22L;
	
	Long PENNY_DROP_FROM_CARD_MERCHANT = 23L;
	
	Long PENNY_DROP_FROM_BANK_MERCHANT = 24L;
	
	Long RECOVER_FROM_CARD_MERCHANT = 25L;

	Long RECOVER_FROM_BANK_MERCHANT = 26L;
	
	// merchant Non-Financial transaction types
	Long ANUAL_FEE_MERCHANT = 27L;
	
	Long REGISTRTION_FEE_MERCHANT = 28L;
	
	Long STATEMENT_COPY_FEE_MERCHANT = 29L;
	
	// merchant Financial periodic transaction types
	Long QUARTARLY_FEE_MERCHANT = 30L;
	
	Long MONTHLY_FEE_MERCHANT = 31L;
	
	//financial P 2 S
	Long P_TO_S = 32L;
	
	//financial M 2 S
	Long M_TO_S = 33L;
	
	Long CHARGE_BACK = 34L;
	
	Long REFUND = 35L;
	
	Long ADD_MONEY_FROM_CARD_CUSTOMER = 36L;
	
	Long ADD_MONEY_FROM_CARD_MERCHANT = 37L;
	
}
