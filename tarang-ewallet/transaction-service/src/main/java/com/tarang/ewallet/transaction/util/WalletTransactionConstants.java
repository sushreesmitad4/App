package com.tarang.ewallet.transaction.util;

public interface WalletTransactionConstants {
	
	String ERROR_MSG_WRONG_INFO = "common.error.card.wrong.info";
	
	Long UPLOAD_MONEY_FROM_CARD_CUSTOMER = 11L;
	
	Long UPLOAD_MONEY_FROM_CARD_MERCHANT = 13L;
	
	Long RECEIVEMONEY_ATTEMPTS = 0L;
	
	String ERROR_OVER_LIMIT_THRESHOLD_AMOUNT = "error.msg.over.limit.threshold.amount";
	
	String ERROR_LIMIT_THRESHOLD_ADD_AMOUNT = "error.msg.limit.threshold.add.amount";
	
	String ERROR_OVER_LIMIT_THRESHOLD_ADD_AMOUNT = "error.msg.limit.over.threshold.add.amount";
	
	String ERROR_USER_WALLET_NOT_ENOUGH_BALANCE = "userwallet.nothaving.enoughbalance";
	
	String TRANSACTION_SUCCESS="transaction.success";
	
	String TRANSACTION_FAILED="transaction.failed";
	
	String TRANSACTION_SUCCESS_FILE_UPLOAD = "transaction.success.records";
	
	String IN_SUFFICIENT_BALANCE = "in.sufficient.balance";
	
	String USER_LOCKED = "user.locked";
	
	String USER_INACTIVE = "user.inactive";
	
	String USER_NOT_APPROVED = "user.notapproved";
	
	String USER_DELETED = "user.deleted";
	
	String USER_REJECTED = "user.rejected";
	
	String ERR_MSG_FAILS_TO_INITIATE_TRANSACTION = "fails to initiate transaction";
	
	String ERR_CODE_FAILS_TO_INITIATE_TRANSACTION = "fails.to.initiate.transaction";
	
	String FAILS_TOCREATE_SENDMONEY_REQUEST = "fails.tocreate.sendmoney.request";
	
	String ERROR_ACCEPT_REQUEST_MONEY = "error.accept.request.money";
	
	String ERROR_REJECT_REQUEST_MONEY = "error.reject.request.money";
	
	String ERROR_CANCEL_REQUEST_MONEY = "error.cancel.request.money";

}
