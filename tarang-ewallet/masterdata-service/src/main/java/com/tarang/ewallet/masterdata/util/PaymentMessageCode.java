package com.tarang.ewallet.masterdata.util;


public interface PaymentMessageCode {
	
	String PAYMENT_SUCCESS = "S001";
	
	String AUTHENTICATION_FAILED = "E001";
	
	String INSUFFICIANT_BALANCE = "E002";
	
	String MERCHANT_BLOCKED = "E003";
	
	String CUSTOMER_BLOCKED = "E004";
	
	String FRAUDCHECK_FAILED = "E005";
	
	String TXN_FAILED = "E006";
	
	String UNKNOWN_ERROR = "E007";
	
	String CUSTOMER_NOT_FOUND = "E008";
	
	String MERCHANT_NOT_FOUND = "E009";
	
	String INVALID_INPUTS = "E010";
	
	String INVALID_INPUT_DATA = "E011"; 
	
	String CURRENCY_NOT_SUPPORT = "E012";

	String USER_CANCEL_ACTION = "E013";
	
	String CUSTOMER_LOCKED = "E014";
	
	String USER_LOCKED_EXCED_FAILURE_LIMIT = "E015";
	
	String MERCHANT_CODE_NOT_MATCHES = "E016";
	
	String MERCHANT_HAND_SHAKE_NOT_MATCHES = "E017";
	
	String MERCHANT_ACCOUNT_REJ_ADMINISTRATOR = "E018";
	
	String MERCHANT_LOCKED = "E019";
	
	String MERCHANT_ACCOUNT_DELETED = "E020";
	
	String CUSTOMER_ACCOUNT_PENDIND_STATE = "E021";
	
	String CUSTOMER_ACCOUNT_REJ_ADMINISTRATOR = "E022";
	
	String CUSTOMER_ACCOUNT_DELETED = "E023";
	
	String CUSTOMER_ACCOUNT_DEACTIVE = "E024";
	
	String INVALID_AMOUNT = "E025";
	
}