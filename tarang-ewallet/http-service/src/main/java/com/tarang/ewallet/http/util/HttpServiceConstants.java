package com.tarang.ewallet.http.util;
/**
 * 
 */

/**
 * @author  : prasadj
 * @date    : Dec 27, 2012
 * @time    : 3:27:14 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface HttpServiceConstants {

	String IS_SSL = "isSSL";
	
	String HTTPS = "https";

	String HTTP = "http";
	
	String AMOUNT = "Amount";

	String PG_TXN_ID = "PgTxnID";

	String SERVER_HTTP_PORT = "serverHttpPort";

	String SERVER_HTTPS_PORT = "serverHttpsPort";
	
	String SSL_KEYSTORE_FILE_PATH = "sslKeyStoreFilePath";
	
	String SSL_PASSWORD = "sslPassword";
	
	String CONNECTION_TIME_OUT = "connectionTimeout";
	
	String READ_TIME_OUT = "readTimeout";
	
	String SERVICE_URL = "serviceURL";
	
	String EWALLET_MERCHANT = "ewalletMerchantId";
	
	String EWALLET_USER = "ewalletUserId";
	
	String JSN_ERR_CODE = "ErrorCode";
	
	String JSN_ERR_MSG = "ErrorMsg";

	String RESPONSE_CODE = "ResponseCode";
	
	String RESPONSE_MSG = "ResponseMessage";
    
	// jSon key
	String RESPONSEDECISION = "Response Decision";  
	
	//jSon key
	String RESPONSE_TEXT = "Response Text"; 
	
	//has to change
	String SUCCESS_MSG = "Successfully Auth the transaction"; 
	
	// has to change
	String FAILURE_MSG = "Failed Auth the transaction"; 
	
	
	//REQUEST VARIABLES
	String TXN_TYPE_REQ = "TxnType";  
	
	String PAYMENT_MODE_REQ = "PaymentMode";
	
	String DATE_AND_TIME_REQ = "DateAndTime";
	
	String STORE_ID_REQ = "StoreID";
	
	String MERCHANT_ID = "MerchantID";
	
	String USER_ID = "UserId";
	
	String ORDER_ID_REQ = "OrderID";
	
	String CUSTOMER_ID_REQ = "CustomerID";
	
	String REQ_TYPE_REQ = "ReqType";
	
	String CUSTOMER_DOB_REQ = "CustomerDOB";
	
	String SOCIAL_ID_REQ = "SocialID";
	
	String DRIVING_LICENSE_REQ = "DrivingLicense";
	
	String MOBILE_NUMBER_REQ = "MobileNumber";
	
	String EMAIL_ID_REQ = "EmailId";
	
	String ADDRESS_ONE_REQ = "AddressOne";
	
	String ADDRESS_TWO_REQ = "AddressTwo";
	
	String CITY_REQ = "City";
	
	String STATE_REQ = "State";
	
	String COUNTRY_REQ = "Country";
	
	String ZIP_CODE_REQ = "Zipcode";
	
	String 	AMOUNT_REQ = AMOUNT;
	
	String CURRENCY_REQ = "Currency";
	
	String AUTH_MODE_REQ = "AuthMode";
	
	String ORDER_DETAILS_REQ = "OrderDetails";
	
	String COMMENT_REQ = "Comment";
	
	String USER_AGENT_REQ = "UserAgent";
	
	String SOURCE_IP_REQ = "SOURCE_IP";
	
	String IS_FRAUD_CHECK_REQ = "IsFraudCheck";
	
	String PG_TXN_ID_REQ = PG_TXN_ID;
	
	String NAME_ON_CARD_REQ = "NameOnCard";
	
	String CARD_NUMBER_REQ = "CardNumber";
	
	String CARD_EXPIRY_MONTH_REQ = "CardExpiryMonth";
	
	String CARD_EXPIRY_YEAR_REQ = "CardExpiryYear";
	
	String CARD_STORAGE_ID_REQ = "CardStorageId";
	
	String CARD_CVV_REQ = "CardCVV";
	
	String STORE_CARD_FLAG_REQ = "StoreCardFlag";
	
	String DELETE_CARD_FLAG_REQ = "DeleteCardFlag";
	
	String TRACE_NO_REQ = "TraceNo";
	
	String BILLING_ADDRESS_LINE_ONE_REQ = "BillingAddressLineOne";
	
	String BILLING_ADDRESS_LINE_TWO_REQ = "BillingAddressLineTwo";
	
	String BILLING_ADDRESS_CITY_REQ = "BillingAddressCity";
	
	String BILLING_ADDRESS_STATE_REQ = "BillingAddressState";
	
	String BILLING_ADDRESS_COUNTRY_REQ = "BillingAddressCountry";
	
	String 	BILLING_ADDRESS_ZIP_REQ = "BillingAddressZIP";
	
	String STATEMENT_NARRATIVE_REQ = "StatementNarrative";
	
	String TERMINAL_ID_REQ = "TerminalID";
	
	String THREE_D_SECURE_ENROLLED_REQ = "ThreeDSecureEnrolled";
	
	String CAVV_REQ = "CAVV";
	
	String ECI_FLAG_REQ = "ECIFlag";
	
	String XID_VALUE_REQ = "XIDValue";  
	
	//JSON RESPONSE KEYS
	String	AMOUNT_RESP = AMOUNT ;
	
	String	AUTHORIZATION_CODE_RESP = "Authorization code" ;
	
	String	AVS_RESPONSE_CODE_RESP = "AVS Response Code" ;
	
	String	CURRENCY_RESP = "Currency" ;
	
	String	CVV2_RESPONSE_CODE_RESP = "CVV2 Response code" ;
	
	String	DATE_AND_TIME_RESP = "Date and Time" ;
	
	//String	MERCHANT_ID_RESP = "MerchantID" ;
	
	String	ORDER_ID_RESP = "OrderID" ;
	
	String	PAYMENT_MODE_RESP = "PaymentMode" ;
	
	String	PG_TXN_ID_RESP = PG_TXN_ID ;
	
	String	RESPONSE_DECISION_RESP = "Response decision" ;
	
	String	RESPONSE_TEXT_RESP = "Response Text" ;
	
	String	TRACE_NO_RESP = "Trace No" ;
	
	String	TXN_TYPE_RESP = "TxnType" ;

	//Transaction Types
	String AUTH_TXN_TYPE = "Auth";
	
	String CANCEL_TXN_TYPE = "Cancel";
	
	String SETTLEMENT_TXN_TYPE = "Settle";
	
	String REFUND_TXN_TYPE = "Refund";
	
	//JSON RESPONSE KEY VALUES
	String RESPONSE_DECISION_SUCCESS = "1000";
	
	String RESPONSE_TEXT_MSG = "12345";
	
	//STATIC MESSAGES
	String FILE_NOT_FOUND = "property file not found in the classpath";
	
	String USER_AGENT = "User-Agent";
	
	String HTTP_CLIENT_VERSION = "HTTP CLIENT/4.3";
	
	String ACCEPT = "ACCEPT";
	
	String TEXT_OR_HTML = "text/html, application/xml;q=0.9, application/xhtml+xml";
	
	String CONTENT_TYPE = "Content-Type";
	
	String JSON_DATA = "JsonData";
	
	String PAYMENT_MODE = "CC";
	
	String AUTH_MODE_3D = "3D";
	
	String REQ_TYPE_MOTO = "01";
	
	String REQ_TYPE_RECURRING = "02";
	
	String REQ_TYPE_ECOM = "07";
	
	String REQ_TYPE_3D_SECURE_AUTHENTICATED = "05";
	
	String REQ_TYPE_3D_SECURE_ATTEMPTED = "06";
	
	String TRACE_NO = "52364";
	
	//SETTLEMENT RESPONSE JSON KEYS
	//String MERCHANT_ID_SETTLE_RESP = "MerchantID";
	
	String ORDER_ID_SETTLE_RESP = "OrderID";
	
	String PG_TXNID_SETTLE_RESP = PG_TXN_ID;
	
	String SETTLEMENT_TXN_ID_SETTLE_RESP = "SettlementTxnID";
	
	String TXN_AMOUNT_SETTLE_RESP = AMOUNT;
	
	String SETTLED_AMOUNT_SETTLE_RESP = "SettledAmount";
	
	String TXN_DATE_SETTLE_RESP = "TxnDate";
	
	String SETTLEMENT_ORDER_NO_SETTLE_RESP = "SettlementOrderNo";
	
	String TXN_CURRENCY_SETTLE_RESP = "txnCurrency";
	
	String MERCHANT_ORDER_NO_SETTLE_RESP = "MerchantOrderNo";
	
	//String RESPONSE_CODE_SETTLE_RESP = "ResponseCode";
	
	//String RESPONSE_MESSAGE_SETTLE_RESP = "ResponseMessage";
	
	//String RESPONSE_DECISION = "ResponseDecision";
	
	String RESPONSE_DECISION = "Response Decision";
	
	
	//REFUND RESPONSE JSON KEYS
	//String MERCHANT_ID_REFUND_RESP = "MerchantID";
	
	String PG_TXNID_REFUND_RESP = PG_TXN_ID;
	
	String TXN_AMOUNT_REFUND_RESP = "TxnAmount";
	
	String REFUND_AMOUNT_REFUND_RESP = "RefundAmount";
	
	//String RESPONSE_CODE_REFUND_RESP = "ResponseCode";
	
	//String RESPONSE_MESSAGE_REFUND_RESP = "ResponseMessage";
	
	//String RESPONSE_DECISION_REFUND_RESP = "ResponseDecision";

	
	//Cancel RESPONSE JSON KEYS
	//String MERCHANT_ID_CANCEL_RESP = "MerchantID";
	
	String	PG_TXNID_CANCEL_RESP = PG_TXN_ID;
	
	String TXN_AMOUNT_CANCEL_RESP =	AMOUNT;
	
	//String RESPONSE_CODE_CANCEL_RESP = "ResponseCode";
	
	//String	RESPONSE_MESSAGE_CANCEL_RESP = "ResponseMessage";
	
	//String	RESPONSE_DECISION_CANCEL_RESP = "ResponseDecision";
	
	String PG_ERROR_PRIFIX = "pgresponse.";
	
	//Updated On 11:49 AM 12/11/2013 
	String AUTHCAPTURE = "AuthCapture";
	
	String DEFAULT_AUTHCAPTURE = "N";
}
