package com.tarang.ewallet.walletui.controller.constants;
/**
 * @author  : kedarnathd
 * @date    : Feb 12, 2013
 * @time    : 7:54:03 PM
 * @version : 1.0.0
 * @comments: 
 *
 */

public interface Dispute {
	
	String DISPUTE_TYPE_LIST = "disputeTypeList";
	
	String DISPUTE_STATUS_MAP = "disputeStatusMap";
	
	String DEFAULT_VIEW = "merchantdispute";
	
	String DISPUTE_CUSTOMER_PATH = "customerdispute";
	
	String DISPUTE_MERCHANT_PATH = "merchantdispute";
	
	String DISPUTE_ADMIN_PATH = "admindispute";
	
	String CUSTOMER_DISPUTE_VIEW = "customer.dispute";
	
	String MERCHANT_DISPUTE_VIEW = "merchant.dispute";
	
	String ADMIN_DISPUTE_VIEW = "admin.dispute";
	
	String ADMIN_DISPUTE_UPDATE = "admin.dispute.update";
	
	String CUSTOMER_DISPUTE_TRANSACTION = "customer.dispute.transaction";
	
	String CUSTOMER_RAISE_DISPUTE = "customer.raise.dispute";
	
	String MERCHANT_DISPUTE_UPDATE = "merchant.dispute.update";
	
	String CUSTOMER_UPDATE_DISPUTE = "customer.dispute.update";
	
	Integer LIMIT = 100;
	
	String DISPUTE_UPDATE_SUCCESS = "suc.msg.update.dispute";
	
	String DISPUTE_UPDATE_FAILED = "error.msg.update.dispute";
	
	String MERCHANT_STATUS_REQUIRED = "merchantStatusRequired";
	
	String ADMIN_STATUS_REQUIRED = "adminStatusRequired";
	
	String MERCHANT_APPROVED_AMOUNT_REQUIRED = "merchantApprovedAmountRequired";
	
	String ADMIN_APPROVED_AMOUNT_REQUIRED = "adminApprovedAmountRequired";
	
	String DISPUTE_CREATE_SUCCESS = "suc.msg.create.dispute";
	
	String DISPUTE_CREATE_FAILED = "error.msg.create.dispute";

	String DISPUTE_EXIST = "error.msg.dispute.exist";
	
	String FDATE = "fdate";
	
	String TDATE = "tdate";

	String DISPUTETYPE = "disputetype";
	
	String CEMAIL = "cemail";

	String MEMAILID = "memailid";

	String URL_TXNS_LIST = "urlTxnsList";
	
	String IS_TXN_PAGE = "isTxnpage";
	
	String CUSTOMER_DISPUTE_LIST_URL = "customerdispute/list";
	
	String REFUND = "refund";
	
	String CHARGEBACK = "chargeback";
	
	String NOTALLOW_DISPUTE_ERR_MSG = "error.msg.notallow.toget.disputes";
	
	String ERROR_MSG_ACCOUNT_CLOSURE_DISPUTE_RAISE_UPDATE = "error.msg.account.closure.dispute.raise.or.update.not.allowed";
}
