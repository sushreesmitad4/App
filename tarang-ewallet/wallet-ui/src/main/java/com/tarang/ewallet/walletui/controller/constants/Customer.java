/**
 * 
 */
package com.tarang.ewallet.walletui.controller.constants;

/**
 * @author prasadj
 *
 */
public interface Customer {

	String DEFAULT_VIEW = "customer";

	String LOGIN_VIEW = "customer.login";

	String REG_VIEW_ONE = "customer.registration.one";

	String REG_VIEW_TWO = "customer.registration.two";

	String REG_VIEW_SUCCESS = "registration.success";

	String SUCCESS_VIEW = "customer.success";
	
	String CUSTOMER_DATA = "customer.information";
	
	String CUSTOMER_UPDATE_PROFILE = "admin.customer.update.profile";
	
	String FORGOT_PASSWORD_MAIL_SEND_FAIL = "login.forgot.password.mail.send.fail";
	
	String VIEW_RESOLVER_HOME_PAGE = "wallet.home";
	
	String CUSTOMER_VIEW_UNDERCONSTRUCTION = "customer.underconstruction";
	
	String CUSTOMER_SEND_MONEY = "customer.sendmoney";
	
	String CUSTOMER_REQUEST_MONEY_CREATE = "customer.requestmoney.create";
	
	String CUSTOMER_REQUEST_MONEY_SUCCESS ="customer.requestmoney.success";
	
	String CUSTOMER_REQUEST_MONEY_LIST = "customer.requestmoney.list";
	
	String CUSTOMER_REQUEST_MONEY_ACCEPT = "customer.requestmoney.accept";
	
	String CUSTOMER_REQUEST_MONEY_REJECT ="customer.requestmoney.reject";
	
	String CUSTOMER_REQUEST_MONEY_VIEW = "customer.requestmoney.view";
	
	String CUSTOMER_REQUESTMONEY_PATH = "requestmoney/list";
	
	String ERROR_MSG_NOT_AUTHORIZED_TO_ACCEPT_REQUESTED_MONEY = "error.msg.not.authorized.to.accept.requested.money";
	
	String ERROR_MSG_NOT_AUTHORIZED_TO_REJECT_REQUESTED_MONEY = "error.msg.not.authorized.to.reject.requested.money";
	
	String ERROR_MSG_NOT_AUTHORIZED_TO_CANCEL_REQUESTED_MONEY = "error.msg.not.authorized.to.cancel.requested.money";
	
	String ERROR_MSG_REQUESTMONEY_FAILED_CANCEL = "error.msg.requestmoney.failed.cancel";
	
	String ERROR_MSG_PENDING_RECORDS_REQUIRED = "error.msg.pending.records.required";
	
	String ERROR_MSG_REQUESTMONEY_FAILED_ACCEPT = "error.msg.requestmoney.failed.accept";
	
	String ERROR_MSG_REQUESTMONEY_FAILED_REJECT = "error.msg.requestmoney.failed.reject";
	
	int PAGE_SIZE = 10;
}
