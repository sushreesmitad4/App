/**
 * 
 */
package com.tarang.ewallet.walletui.controller;


/**
 * @author  : prasadj
 * @date    : Nov 30, 2012
 * @time    : 5:28:28 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface AttributeValueConstants {

	String REDIRECT_PREFIX = "redirect:";
	
	String SLASH = "/";
	
	String URL_PATH_PREFIX = "tarang"; 
	
	String WALLET_PATH_PREFIX = SLASH + URL_PATH_PREFIX + SLASH;
	
	String ADMIN_LOGIN_PATH = "adminlogin";
	
	String CUSTOMER_REG_PATH = "customer/registration";
	
	String CUSTOMER_REG_SUCC_PATH = "customer/registrationsuccess";
	
	String HOME = "home";
	
	String ADMIN_LOGIN_CHANGE_PASS_PATH = "adminlogin/changepassword";
	
	String ADMIN_PATH = "admin";
	
	String ADMIN_USERMGMT_PATH = "adminusermgmt";
	
	String HOME_LOGIN_PATH = "home/login";
	
	String CUSTOMER_PATH = "customer";
	
	String CUSTOMER_MGMT_PATH = "customermgmt";
	
	String MERCHANT_PATH = "merchant";
	
	String HOME_CHANGE_PASS_PATH = "home/changepassword";
	
	String MERCHANT_REG_PATH = "merchant/registration";
	
	String MERCHANT_REG_SUCC_PATH = "merchant/registrationsuccess";
	
	String MERCHANT_MGMT_PATH = "merchantmgmt";
	
	String MANAGE_ACCOUNTS_PATH = "manageaccounts";
	
	String ACCOUNT_CLOSER_PATH = "accountclosermgmt";
	
	String TAXS_MGMT_PATH = "tax";
	
	String CUSTOMER_REQUESTMONEY_PATH = "customertransaction/requestmoneylist";
	
	String CONTENT_TYPE_PDF = "application/pdf";
	
	String UNDEFINED = "undefined";
	
	String XSL_FILE_EXT = ".xls";
	
	String PDF_FILE_EXT = ".pdf";

	String ADMIN_ROLE_NAME = "super admin";
	
	String LOGIN_CODE_PATH = "home/logincode";
	
	int DEFAULT_PAGE_SIZE = 10;
	
	String ADMIN_DISPUTE_LIST_PATH = "admindispute/list";
	
	String JSON_HEADER = "Accept=application/json";
	
	String JSON_PRODUCER = "application/json";
	
	String STRING_ZERO = "0";
	
	String AND_OPERATOR = "^";
	
	String PLEASE_SELECT = "Please select";
	
	String SEMICOLON = ";";
	
	Integer DAILY_FREQUENCY = 1;
	
	Integer WEEKLY_FREQUENCY = 2;
	
	Integer MONTHLY_FREQUENCY = 3;
	
	Long PHONE_CODE = 1L;
	
	Long PHONE_NO = 2L;
	
	Long FULL_NAME = 3L;
	
	Integer SETTIMEOUT = 15000;
	
	String ADMIN_ROLE_PATH = "adminroles";
	
	String TRANSACTION_THRESHOLD_PATH = "admin/velocityandthreshold";
	
	String WALLET_THRESHOLD_PATH = "admin/walletthreshold";
	
	String BALANCE_STATUS_CR = "cr.lbl";
	
	String BALANCE_STATUS_DR = "dr.lbl";
	
	String REFUND_LBL = "dispute.refund.lbl";
	
	String CHARGE_BACK_LBL = "dispute.chargeback.lbl";
	
	String SCHEDULER_LIST_VIEW = "scheduler.list";
	
	String SCHEDULER_EDIT_VIEW = "scheduler.edit";
	
	String SCHEDULER_VIEW = "scheduler.view";
	
	String SCHEDULER_PATH = "scheduler";
	
	String UNKNOWN_KEY = "unkown.key";
	
	String NOT_STARTED_KEY = "not.started.key";

	String SUCCESS_KEY = "success.key";
	
	String FAIL_KEY = "fail.key";
	
	String DISPUTE_UPDATED_BY_ADMIN = "dispute.updated.by.admin.lbl";
	
	String CUSTOMER_DISPUTE_PATH = "customerdispute";
	  
}
