package com.tarang.ewallet.http.util;

import java.util.Date;

import com.tarang.ewallet.model.PgRequest;
import com.tarang.ewallet.util.DateConvertion;


public class HttpServiceUtil {
	
	/**
	 * This is called for reload money auth request 
	 * @param authAPIRequest
	 */
	public static void getAuthPgRequest(PgRequest authAPIRequest){
		
		//Auth //Cancel //Settle //Refund
		authAPIRequest.setTxnType(HttpServiceConstants.AUTH_TXN_TYPE);
		authAPIRequest.setPaymentMode(HttpServiceConstants.PAYMENT_MODE);
		authAPIRequest.setDateAndTime(DateConvertion.getPgDateAndTime(new Date()));
		authAPIRequest.setOrderID(DateConvertion.getPgOrderId(new Date()));
		authAPIRequest.setReqType(HttpServiceConstants.REQ_TYPE_MOTO);
		/* Amount, currency, card number, expairyDate, CVV setting out side */
	}
}
