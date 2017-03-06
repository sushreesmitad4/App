package com.tarang.ewallet.walletui.util;

public interface PaymentConstants {
	
	Integer PAYMENT_SUCCESS = 1;
	
	Integer PAYMENT_FAILED = 2;
	
	String PAYMENT_FAILURE_VIEW = "payment.failure.response";
	
	String PAYMENT_SUCCESS_VIEW = "payment.success.response";

	String PAYMENT_LOGIN_VIEW = "payment.login";
	
	String PAYMENT_UNEXPECTED_RES_ONLINEPAYMENT = "payment.unexpected.res.onlinepayment";
	
	String PAYMENT_AUTHENTICATION_FAIL_ERROR_MSG= "payment.authentication.fail";
	
	String AUTH_FAIL_ERR_MSG_LOGIN_STATUS = "auth.fail.login.status.errmsg";

	String REQ_ORDER_ID = "orderId";
	
	String REQ_AMOUNT = "amount";
	
	String REQ_CURRENCY = "currency";
	
	String REQ_MERCHANT_ID = "merchantId";
	
	String REQ_CUSTOMER_LOGIN_ID = "customerLoginId";
	
	String REQ_CUSTOMER_PASSWORD = "customerPassword";
	
	String REQ_MERCHANT_SUCCESS_URL = "merchantSuccessUrl";
	
	String REQ_MERCHANT_FAILURE_URL = "merchantFailureUrl";
	
	String REQ_JSESSIONID = "jsessionid";
	
}
