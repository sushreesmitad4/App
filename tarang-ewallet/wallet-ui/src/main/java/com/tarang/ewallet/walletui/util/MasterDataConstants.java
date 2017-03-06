/**
 * 
 */
package com.tarang.ewallet.walletui.util;

/**
 * @author  : prasadj
 * @date    : Oct 10, 2012
 * @time    : 6:57:45 PM
 * @version : 1.0.0
 * @comments: provides the constants for mapping the context attributes which are related to master data
 *
 */
public interface MasterDataConstants {

	Long MD_DEFAULT_PROPS_CODE = 0L;
	
	Long DEFAULT_LANGUAGE = 1L;
	
	String MD_LANGUAGES = "languages";

	String MD_COUNTRIES = "countries";

	String MD_CURRENCIES = "currencies";
	
	String MD_CURRENCY_CODES = "currencycodes";
	
	String MD_CARD_TYPES = "card.types";

	String MD_FREQUENCIES = "frequencies";

	String MD_USER_TYPES = "user.types";

	String MD_CUSTOMER_TRANSACTION_TYPES = "ctransaction.types";

	String MD_MERCHANT_TRANSACTION_TYPES = "mtransaction.types";

	String MD_ADMIN_TRANSACTION_TYPES = "atransaction.types";

	String MD_MERCHANT_OWNER_TYPES = "merchant.owner.types";

	String MD_MERCHANT_BUSINESS_CATEGORIES = "merchant.business.categories";

	String MD_MERCHANT_AVERAGE_TX_AMOUNTS = "merchant.avg.tx.amounts";

	String MD_MERCHANT_HIGHEST_MONTHLY_VOLUMES = "merchant.highest.monthly.volumes";

	String MD_MERCHANT_PERCENTAGE_ANUAL_REVENUES = "merchant.percentage.anual.revenues";

	String MD_USER_STATUSES = "user.statuses";
	
	String MD_MERCHANT_SUB_CATEGORIES = "merchant.sub.categories";
	
	String HINT_QUESTIONS = "hint.questions";
	
	String REGIONS = "regions";

	String TITLES = "titles";

	String MONEY_ACCOUNT_STATUSES = "money.account.statuses";

	String MONEY_ACCOUNT_DELETE_STATUSES = "money.account.delete.statuses";

	String BANK_ACCOUNT_TYPES = "bank.account.types";
	
	String FEE_TYPES = "fee.types";
	
	String FEE_OPERATION_TYPES = "fee.operation.types";
	
	String FEE_SERVICE_TYPES = "fee.service.types";

	String FEE_PAYING_ENTITIES = "fee.paying.entities";
	
	String FEE_TIME_FREQUENCY = "fee.time.frequency";
	
	String TRX_TIME_PERIOD = "trx.time.period";
	
	String DESTINATION_TYPE = "destination.type";

	String TRX_STATUS = "trx.status";
	
	String RECEIVE_MONEY_STATUS = "receive.money.status";

	String REPORT_TYPES = "report.types";
	
	String DISPUTE_TYPES = "dispute.types";

	String ACCOUNT_CLOSURE_STATUS = "account.closure.status";
	
	String DISPUTE_STATUS = "dispute.status";
	
	String PAYMENT_MESSAGES = "payment.messages";
	
	String QUERY_OR_FEEDBACK = 	"query.feedback";
	
	String MOBILE_WALLET_ERROR_CODE = 	"mobile.wallet.error.code";
	
	String TYPE_OF_REQUEST = "channel.types";
	
	String PG_ERROR_CODE = "pg.error.code";
}
