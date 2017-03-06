package com.tarang.ewallet.audittrail.util;

/**
 * @author vasanthar
 *
 */
public interface AuditTrailConstrain {
	
	String MODULE_CUSTOMER_REGISTRATION = "Customer Registration";
	
	String MODULE_CUSTOMER_MPIN_GENERATION = "Customer MPIN Generation";
	
	String MODULE_CUSTOMER_EDIT = "Customer Edit";
	
	String MODULE_CUSTOMER_UPDATE = "Customer Update";
	
	String MODULE_CUSTOMER_REGISTRATION_AFTER_24HOURS = "Customer Registration After 24hours";
	
	String MODULE_MERCHANT_REGISTRATION = "Merchant Registration";
	
	String MODULE_MERCHANT_EDIT = "Merchant Edit";
	
	String MODULE_MERCHANT_UPDATE = "Merchant Update";
	
	String MODULE_ADMIN_REGISTRATION = "Admin Registration";
	
	String MODULE_ADMIN_EDIT = "Admin Edit";
	
	String MODULE_ADMIN_UPDATE = "Admin Update";
	
	String MODULE_ROLE_CREATION = "Role Creation";
	
	String MODULE_ROLE_EDIT = "Role Edit";
	
	String MODULE_ROLE_DELETED = "Role Delete";
	
	String MODULE_ACCOUNT_CLOSER_CREATION = "Account Closer Creation";
	
	String MODULE_ACCOUNT_CLOSER_UPDATE = "Account Closer Update";
	
	String MODULE_VELOCITY_AND_THRESHOLD_CREATION = "Velocity And Threshold Creation";
	
	String MODULE_VELOCITY_AND_THRESHOLD_EDIT = "Velocity And Threshold Edit";
	
	String MODULE_WALLET_THRESHOLD_CREATION = "Wallet Threshold Creation";
	
	String MODULE_WALLET_THRESHOLD_EDIT = "Wallet Threshold Edit";
	
	String MODULE_PREFEREANCES_UPDATE = "Prefereances Update";
	
	String MODULE_CUSTOMER_EMAILVERIFICATION_UPDATE = "Customer Emailverification Update";
	
	String MODULE_MERCHANT_EMAILVERIFICATION_UPDATE = "Merchant Emailverification Update";
	
	String MODULE_CUSTOMER_DISPUTE_CREATE = "Customer Dispute Create";
	
	String MODULE_DISPUTE_UPDATE = "Dispute Update";
	
	String MODULE_FEE_FINANCIAL_CREATE = "Admin Fee Create For Financial";
	
	String MODULE_FEE_NON_FINANCIAL_CREATE = "Admin Fee Create For Nonfinancial";
	
	String MODULE_FEE_FINANCIAL_VELOCITY_CREATE = "Admin Fee Create For Financial&Velocity";
	
	String MODULE_FEE_FINANCIAL_UPDATE = "Admin Fee Update For Financial";
	
	String MODULE_FEE_NON_FINANCIAL_UPDATE = "Admin Fee Update For Nonfinancial";
	
	String MODULE_FEE_FINANCIAL_VELOCITY_UPDATE = "Admin Fee Update For Financial&Velocity";
	
	String MODULE_USER_LOGIN_OTP_CREATE = "User Login OTP Create";
	
	String MODULE_USER_LOGIN_OTP_UPDATE = "User Login OTP Update";
	
	String MODULE_USER_CHANGE_PASSWORD = "User Change Password";
	
	String MODULE_USER_FORGOT_PASSWORD = "User Forgot Password";
	
	String MODULE_FORGOT_MPIN_REQUEST = "Forgot Mpin Request From Mobile";
	
	String MODULE_CHANGE_MPIN_REQUEST = "Change Mpin Request From Mobile";
	
	String MODULE_NEW_MPIN_REQUEST = "New Mpin Request From Mobile";
	
	String MODULE_TAX_CREATE = "Tax Create";
	
	String MODULE_TAX_EDIT = "Tax Edit";
	
	String MODULE_ADD_MONEY_CREATE = "Add Money Create";
	
	String MODULE_RELAOD_MONEY_CREATE = "Reload Money Create";
	
	String MODULE_REQUEST_MONEY_CREATE = "Request Money Create";
	
	String MODULE_REQUEST_MONEY_ACCEPT = "Reload Money Accept";
	
	String MODULE_REQUEST_MONEY_REJECT = "Request Money Reject";
	
	String MODULE_REQUEST_MONEY_CANCEL = "Request Money Cancel";
	
	String MODULE_QUERY_FEEDBACK_CREATE = "Query&Feedback Create";
	
	String MODULE_OTP_NOTIFICATION_CREATE = "OTP Notification Create";
	
	String MODULE_QUERY_FEEDBACK_REPLY = "Query&Feedback Reply";
	
	Long STATUS_REGISTRATION = 1L;
	
	Long STATUS_CREATE = 2L;
	 
	Long STATUS_EDIT = 3L;
	
	Long STATUS_UPDATE = 4L;
	
	Long STATUS_DELETE = 5L;
	
	String ACCOUNT_MODULE_BANK_CREATE = "Account Bank Create";
	
	String ACCOUNT_MODULE_BANK_EDIT = "Account Bank Edit";
	
	String ACCOUNT_MODULE_CARD_CREATE = "Account Card Create";
	
	String ACCOUNT_MODULE_CARD_EDIT = "Account Card Edit";
		
	Long AUTHINTICATION_ID = 1L;
	
	String AUDITTRAIL_CLONING_ERROR = "audittrail.error.cloneobjectcreat";

	
}
