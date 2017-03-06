package com.tarang.ewallet.walletui.controller.constants;

public interface Accounts {

	String ADD_CARD_VIEW = "manageaccounts.addcard";
	
	String ADD_BANK_VIEW = "manageaccounts.addbank";
	
	String ADD_EDIT_VIEW = "manageaccounts.edit.viewpage";
	
	String ACC_MANAGEMENT_VIEW = "manageaccounts.accounts";
	
	String EDIT_BANK_VIEW = "manageaccounts.editbank";

	String EDIT_CARD_VIEW = "manageaccounts.editcard";

	String VIEW_RESOLVER_SESSION_EXPIRED = "sessionExpired";
	
	String CUSTOMER_VIEW = "customer.";

	String MERCHANT_VIEW = "merchant.";
	
	String ACCOUNTS_SIZE_EXCEEDS_ERROR_MESSAGE = "accounts.size.exceeds.errmsg";
	
	String ACCOUNTS_SIZE_UNKNOWN_ERROR_MESSAGE = "accounts.size.unknown.errmsg";
	
	String ACCOUNT_DELETE_ERROR_MESSAGE = "account.delete.errmsg";
	
	String ACCOUNT_SETDEFAULT_ERROR_MESSAGE = "set.default.account.errmsg";
	
	String ACCOUNT_VERIFY_ERROR_MESSAGE = "account.very.process.errmsg";
	
	String ACCOUNT_DELETE_SUCCESS_MESSAGE = "account.deleted.success.msg";
	
	String ACCOUNT_SETDEFAULT_SUCCESS_MESSAGE = "set.default.account.success.msg";
	
	String ACCOUNT_VERIFY_SUCCESS_MESSAGE = "account.verified.success.msg";
	
	String BANK_ACCOUNT = "BANK";
	
	String CARD_ACCOUNT = "CARD";
	
	Long CARD_STATUS_VARIFIED = 4L;
	
	String NON_VERIFIED_CARD_VIEW = "manageaccounts.not.verified.card";
	
	String PENDING_CARD_VIEW = "manageaccounts.pending.card";
	
	String CARD_VARIFICATION_FAILED = "card.varification.failed.errmsg";
	
	String ERROR_MSG_WRONG_CARD_INFO = "common.error.card.wrong.info";
	
	String ERROR_MSG_UNKNOWN_PG_RES = "common.error.unknown.pg.responce";
	
	String ERROR_MSG_FAILS_TO_GET_ACCOUNT = "get.account.details.errmsg";
	
	String ERROR_MSG_NOT_VERIFIED_STATUS_ACCOUNT = "account.not.in.not.verified.status.errmsg";
	
	String ERROR_MSG_NOT_PENDING_STATUS_ACCOUNT = "account.not.in.pending.status.errmsg";
	
	String ERROR_MSG_VERIFIED_ACCOUNT_REQUIRED = "error.msg.verified.account.required";
	
	String ERROR_MSG_ALREADY_DEFAULT_ACCOUNT = "error.msg.already.default.account";
	
	String ERROR_MSG_UNABLE_DELETE_DEFAULT_ACCOUNT = "error.msg.unable.delete.default.account";
	
	String ERROR_MSG_NOT_VERIFIED_AND_VERIFIED_ACCOUNT_REQUIRED = "error.msg.not.verified.and.verified.account.required";
	
	String ERROR_MSG_UNABLE_EDIT_DEFAULT_ACCOUNT = "error.msg.unable.edit.default.account";
	
	String ERROR_MSG_ACCOUNT_ALREADY_VERIFIED = "error.msg.account.already.verified";
	
	int CARD_BIN_LENGTH = 6;
	
}
