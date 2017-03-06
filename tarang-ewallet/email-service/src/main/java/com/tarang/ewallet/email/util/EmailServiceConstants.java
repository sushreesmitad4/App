/**
 * 
 */
package com.tarang.ewallet.email.util;

/**
 * @author  : prasadj
 * @date    : Nov 23, 2012
 * @time    : 9:54:03 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface EmailServiceConstants {

	String EMAIL_SEND_FAILED = "send.email.failure";
	
	String CUSTOMER_REGISTRATION = "customerregistration";
	
	String MERCHANT_REGISTRATION = "merchantregistration";
	
	String RESET_PASSWORD = "resetpassword";
	
	String ADMIN_REGISTRATION = "adminregistration";
	
	String FORGOT_PASSWORD_MAIL = "forgotpasswordmail";
	
	String RELOAD_MONEY_MAIL = "reloadmoneymail";
	
	String SEND_MONEY_MAIL = "sendmoneymail";
	
	String SEND_MONEY_MAIL_NON_REG_PERSON = "sendmoneymailnonregperson";
	
	String RECEIVE_MONEY_MAIL_ACCEPT = "receivemoneyacceptconfirmation";
	
	String RECEIVE_MONEY_MAIL_REJECT = "receivemoneyrejectnotification";
	
	String RECEIVE_MONEY_MAIL_CANCEL = "receivemoneycancelnotification";
	
	String RECEIVE_MONEY_MAIL_PENDING = "receivemoneypendingnotification";
	
	String EMAIL_TEMPLATE_EXCEPTION = "Verify the email template contains or path";
	
	String ONE_TIME_IP_CHECK_CODE = "onetimeipcheckcode";
	
	String USER_ACCOUNT_REJECTED_BY_ADMIN = "useraccountrejectedbyadmin";
	
	String USER_STATUS_UPDATED_BY_ADMIN = "userstatusupdatedbyadmin";
	
	String CREATE_DISPUTE_MESSAGE_FOR_MERCHANT = "create.dispute.message.for.merchant";
	
	String UPDATE_DISPUTE_MESSAGE_FOR_CUSTOMER = "update.dispute.message.for.customer";
	
	String UPDATE_DISPUTE_MESSAGE_FOR_MERCHANT = "update.dispute.message.for.merchant";
	
	String SYSTEM_ACTION_MESSAGE_FOR_CUSTOMER = "system.action.message.for.customer";
			
	String SYSTEM_ACTION_MESSAGE_FOR_MERCHANT = "system.action.message.for.merchant";
	
	String RECURRING_PAYMENT_FAILURE_SENDER = "recurring.payment.failure.sender";
	
	String RECURRING_PAYMENT_FAILURE_RECEIVER = "recurring.payment.failure.receiver";
	
	String RECURRING_PAYMENT_FAILURE_NON_REGISTERED_PERSON = "recurring.payment.failure.receiver.non.registered.person";
	
	String FEEDBACK_REPLY_BY_ADMIN_TO_CUSTOMER_OR_MERCHANT = "feedback.reply.by.admin.to.customer.or.merchant"; 
	
	String MOBILE_WALLET_MPIN_GENERATION_CONFIRMATION = "mobile.wallet.mpin.generation.confirmation";
	
	String MOBILE_WALLET_CHANGED_MPIN_CONFIRMATION = "mobile.wallet.changed.mpin.confirmation";
	
	String MOBILE_WALLET_FORGOT_MPIN_CONFIRMATION = "mobile.wallet.mpin.forgot.confirmation";
	
	String MOBILE_WALLET_DEACTIVATE_CONFIRMATION = "mobile.wallet.deactivate.confirmation";
	
	String MOBILE_WALLET_FORGOT_MPIN_NOTIFICATION = "mobile.wallet.mpin.forgot.notification";
	
	String MOBILE_WALLET_UNBLOCK_CONFIRMATION_ACTION_TAKENBY_SYSTEM = "mobile.wallet.unblock.confirmation.action.takenby.system";

	Boolean EMAIL_SENT_SUCCESS = Boolean.TRUE; 

	Boolean EMAIL_SENT_FAILED = Boolean.FALSE; 
	
}
