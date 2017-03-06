/**
 * 
 */
package com.tarang.ewallet.walletui.controller;

import com.tarang.ewallet.util.GlobalLitterals;

/**
 * @author  : prasadj
 * @date    : Nov 23, 2012
 * @time    : 9:54:03 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface AttributeConstants extends GlobalLitterals{
	
	String DB_ID = "dbId";
	
	/**
	 * attribute name for id
	 */
	String ID = "id";
	/**
	 * attribute name for userTypeId for fee management 
	 */
	String USER_TYPE_ID = "userTypeId";
	/**
	 * attribute name for header user agent
	 */
	String USER_AGENT = "User-Agent";
	
	/**
	 * attribute name for customer id
	 */
	String CUSTOMER_ID = "customerId";
	
	/**
	 * attribute name for merchant id
	 */
	String MERCHANT_ID = "merchantId";
	
	/**
	 * attribute name for error message
	 */
	String ERROR_MESSAGE = "errorMessage";
	
	/**
	 * attribute name for success message
	 */
	String SUCCESS_MESSAGE = "successMessage";
	
	/**
	 * attribute name for user id
	 */
	String USER_ID = "userId";
	
	/**
	 * attribute name for language id
	 */
	String LANGUAGE_ID = "languageId";
	
	/**
	 * attribute name for question
	 */
	String QUESTIONS = "questions";

	/**
	 * attribute name for authentication id
	 */
	String AUTHENTICATION_ID = "authenticationId";
	
	/**
	 * attribute name for user types
	 */
	String USER_TYPE = "userType";
	
	/**
	 * attribute name for SIM number
	 */
	String SIM_NUMBER = "simNumber";
	
	/**
	 * attribute name for IMEI number
	 */
	String IMEI_NUMBER = "imeiNumber";
	
	/**
	 * attribute name for email
	 */
	String USER_EMAIL = "email";
	
	/**
	 * attribute name for user status
	 */
	String USER_STATUS = "userStatus";
	
	/**
	 * attribute name for countries list
	 */
	String COUNTRY_LIST = "countryList";
	
	/**
	 * attribute name for countries list
	 */
	String COUNTRY_NAME = "countryName";
	

	/**
	 * attribute name for state name
	 */
	String STATE_NAME = "stateName";
	
	/**
	 *  attribute name for country states list
	 */
	String STATE_LIST = "stateList";
	
	/**
	 *  attribute name for user states list which is referring second time in a page
	 */
	String STATE_LIST2 = "stateList2";
	
	/**
	 *  user status list
	 */
	String STATUS_LIST = "statusList";
	

	/**
	 * attribute name for merchant business categories
	 */
	String BUSINESS_CATEGORIES = "businessCategories";
	
	/**
	 * attribute name for merchant business sub-categories
	 */
	String SUB_CATEGORIES = "subcategories";
	
	/**
	 * attribute name for merchant business owner types
	 */
	String OWNER_TYPES = "ownerTypes";
	
	/**
	 * attribute name for merchant average transaction amounts
	 */
	String MERCHANT_AVG_TXN_AMOUNT = "merchantAvgTxnAmount";
	
	/**
	 * attribute name for merchant highest monthly volumes
	 */
	String MERCHANT_HIGHEST_MONTHLY_VOLUMES = "merchantHeighestMonthlyVolmes";
	
	String CURRENCY_LIST = "currencyList";
	
	String DESTINATION_TYPE_LIST = "destinationTypeList";
	
	String DURATION_LIST = "durationList";
	
	String SEQUENCE = "sequence";
	
	String TRANSACTIONTYPE_LIST = "transactionTypeList";
	
	/**
	 * attribute name for merchant percentage anual revenues
	 */
	String MERCHANT_PERCENTAGE_ANUAL_REVENUES = "merchantPercenatgeAnualRevenues";
	
	/**
	 * attribute name for list of person titles
	 */
	String PERSON_TITLES = "titles"; 
	
	String CARD_TYPES_LIST ="cardTypes"	;		
	
	/**
	 * attribute to display customer name in header page
	 */
	String NAME = "name";
	
	String CUSTOMER_NAME = "customerName";
	
	String MERCHANT_NAME = "merchantName";
	
	String MERCHANT_FORM = "merchantForm";
	
	String ADMIN_NAME = "adminName";
	
	String ESTABLISH_MONTH = "establishMonth";
	
	String ESTABLISH_YEAR = "establishYear";
	
	String COUNTRY_SELECTED = "countrySelected";
	
	String IS_EDIT_BANK_PAGE = "isEditPage";
	
	String ACCOUNT_STATUS = "accountStatus";

	String HIDE_ACCOUNT_STATUS = "hideAccountStatus";

	String HIDE_DELETE_STATUS = "hideDeleteStatus";

	String CARD_TYPE = "cardType";
	
	String IS_EDIT_PAGE = "editPage";
	
	String IS_EDIT_CUSTOMER_PAGE = "isEdit";
	
	String CARD_ISSUE_DATE_LIST = "issueDateYears";
	
	String CARD_EXP_DATE_LIST = "expiryDateYears";

	String MONTHS_LIST = "monthsList";
	
	String IS_EDIT_MERCHANT_PAGE = "isMerchantEdit";
	
	/**
	 * attribute to display existing billing address
	 */
	String PROFILE_ADDRESS = "profileAddress";
	
	String COUNTRY_PHONE_CODES = "phoneCodes";
	
	String PHONE_NUMER = "phoneNumber";
	
	/**
	 * attribute name for status name
	 */
	String STATUS_NAME = "statusName";

	/**
	 * attribute name for status id
	 */
	String STATUS_ID = "statusid";
	/**
	 * attribute name for feetypelist
	 */	
	String FEE_TYPE_LIST = "feeTypeList";
	/**
	 * attribute name for payingentity
	 */
	String PEYING_ENTITY_LIST = "recipientList";
	/**
	 * attribute name for usertypelist
	 */
	String USER_TYPE_LIST = "userTypeList";
	/**
	 * attribute name for servlicelist
	 */
	String SERVICE_LIST = "servicesList";
	/**
	 * attribute name for operationtypelist
	 */
	String OPERATION_TYPE_LIST = "operationTypeList";
	
	String LASTDAY_CUSTOMERS_COUNT = "lastdayRegisteredCustomersCount";
	
	String LASTWEEK_CUSTOMERS_COUNT = "lastweekRegisteredCustomersCount";
	
	String TOTAL_CUSTOMERS_COUNT = "totalRegisteredCustomersCount";
	
	String LASTDAY_MERCHANTS_COUNT = "lastdayRegisteredMerchantsCount";
	
	String LASTWEEK_MERCHANTS_COUNT = "lastweekRegisteredMerchantsCount";
	
	String TOTAL_MERCHANTS_COUNT = "totalRegisterdMerchantsCount";
	
	String AMMOUNT_IN_ALL_WALLETS = "ammountInAllWallets";
	
	String AMMOUNT_IN_TEMPRARY_WALLET = "ammountInTempraryWallet";
	/**
	 * attribute display to successfully updated
	 */
	String FEE_CREATE_SUCCESS_MSG = "fee.create.success.msg";
	
	String FEE_TIME_FREEQUENCY_LIST = "timePeriodList";
	
	String DURATION_TIME = "durationList";

	String FEE_MGMT_SERVICE_TYPE = "servicetype";
	
	String EMAIL_ID = "emailId";
	
	String CHANGE_PASSWORD_SUCS_MSG = "changepasssugmsg";
	
	String REPORTS_DATA = "reportsData";
	
	String REPORTS_TYPES_LIST = "reportTypesList";
	
	String REPORT_PAGE_HEADER = "reportPageHeader";
	
	String SESSION_EXPIRED_MSG = "session.expired.errmsg";
	
	String PAYMENT_DETAILS = "paymentDetails";
	
	String REGISTRATION = "registration";
	
	String URL_DISPUTE_LIST = "urlDisputeList";
	
	String NULL = "null";
	
	String REQUESTER_EMAIL = "requesterEmail";
	
	String CUSTOMER_REG_FORM_TWO = "customerRegFormTwo";

	String URL_VELOCITY_LIST = "urlVelocityList";

	String URL_WALLET_THRESHOLD_LIST = "urlWalletThresholdList";
	
	String ERROR_MSG_INVALID_SESSION = "error.msg.invalid.session";
	
	String ERROR_MSG_INVALID_USER_ACCESS = "error.msg.invalid.user.access";

	String USER_SESSION_EXPIRED = "session.expired.login.again.errmsg";
	
	String MERCHANT_ALREADY_ADDED_ACCOUNT_NUMBER = "merchant.already.added.account.number";

	String BANK_FRAUD_CHECK_FAILED = "bank.fraud.check.failed";
	
	String IS_ENOUGH_CREDIT = "isEnoughCredit";
	
	int INVALID_SESSION = 1;
	
	int INVALID_USER_ACCESS = 2;
	
	int VALID_USER_ACCESS = 3;
	
	String DATAFOR = "dataFor";
	
	String MERCHANT_CODE = "merchantCode";
	
	String IMAGE_UPLOAD_FILE_FORMATE = "image.upload.file.formate";
	
	String IMAGE_UPLOAD_FILE_SIZE = "image.upload.file.size";
	
	String IMAGE_UPLOAD_SUCCESFULLY = "image.upload.succesfully";
	
	String IMAGE_UNABLE_TO_STORE_IMAGE = "image.unable.to.store.image";
	
	String ERROR_MSG_IMAGE_UNABLE_TO_UPLOAD_IS_ONLINE_PAYMENT_NOT_SET = "error.msg.image.unable.to.upload.is.online.payment.not.set";
	
	String IS_RESET_PASSWORD = "isResetPassword";
	
	String MENU_DETAILS = "Menu Details";
	
	String LOGO_REQUIRED = "logo.required";
	
	String DESTINATION_TYPE = "destinationType";
	
	String CANCEL = "cancel";
	
	String  RELOAD = "reload";
	
	String CREATE = "create" ;
	
	String SEARCH = "search";
	
	String BACK = "back";
	
	String UPDATE = "update";
	
    String F_DATE = "fdate";
	
	String T_DATE = "tdate";
	
	String STARTDATE_STR = "startDateStr";
	
	String ENDDATE_STR = "endDateStr";
	
	String NEWEND_DATE = "newEndDate";
	
	String SendMoney_Job_Details = "sendMoneyJobDetailsModel";
	
	String CREATIONDATE_STR = "creationDateStr";
	
	String FREQUENCY = "frequency" ;
	
	String NEXTOCCURENCE_DATE_STR = "nextOccurenceDateStr";
	
	String AMOUNT_STR = "amountStr";
	
	String NEWSTART_DATE = "newStartDate";
	
	String FREQUENCY_STR = "frequencyStr";
	
	String CURRENT_DATE = "currentDate";
	
	String SENDMONEY_ID = "sendMoneyId";
	
	String URL_RECCURING_LIST = "urlReccuringList";
	
	String RELOADMONEY_FORM = "reloadMoneyForm";
	
	String DISPUTETYPE_LIST = "disputeTypeList";
	
	String TYPE_VALUE = "typevalue";
	
	String MERCHANT_FAILURE_URL = "merchantFailureUrl";
	
	String IMAGE_ID = "imageId";
	
	String PAYMENT_DTO = "paymentDto";
	
	String URLACCOUNT_LIST = "urlAccountList";
	
	String FROMDATE = "fromdate";
	
	String TODATE = "todate";
	
	String URLUSER_LIST = "urlUserList";
	
	String  STATUS = "status";
	
	String EMAILID = "emailid";
	
	String CEMAIL_ID = "cemailid";

	String DISPUTE_TYPE = "disputeType";
	
	String FEEMGMT_FORM = "feeMgmtForm";
	
	String CUS_ID = "cusId";
	
	String DISPUTE_ID = "disputeid";
	
	String TXN_ID = "txnid";
	
	String CLOSE_ACCOUNT = "closeAccount";
	
	String CUSTOMER_REG_FORMTWO = "customerRegFormTwo";
	
	String ROLES_LIST = "rolesList";
	
	String ADMIN_USERID = "adminUserId";
	
	String ACCOUNTCLOSER_STATUS = "accountCloserStatus";
	
	String ACCOUNTCLOSER_FORMVIEW = "accountCloserFormView";
	
	String FEEDBACK_FORM = "feedbackForm";
	
	String DEFAULT_ACCOUNT = "Default Account";
	
	String DEFAULT_CURRENCY = "defaultcurrency";
	
	String EMPTY_VALUE = " ";
	
	String EMPTY_SPACE = "";
	
	
	
}


