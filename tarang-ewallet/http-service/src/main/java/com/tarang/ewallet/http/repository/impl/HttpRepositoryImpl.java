/**
 * 
 */
package com.tarang.ewallet.http.repository.impl;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.repository.HttpRepository;
import com.tarang.ewallet.http.repository.PGRequest;
import com.tarang.ewallet.http.util.ErrorCodeConstants;
import com.tarang.ewallet.http.util.HttpServiceConstants;
import com.tarang.ewallet.model.CancelResponse;
import com.tarang.ewallet.model.PgRequest;
import com.tarang.ewallet.model.PgResponse;
import com.tarang.ewallet.model.RefundResponse;
import com.tarang.ewallet.model.SettlementResponse;
import com.tarang.ewallet.util.GlobalLitterals;



/**
 * @author  : prasadj
 * @date    : Dec 27, 2012
 * @time    : 12:41:41 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class HttpRepositoryImpl implements HttpRepository, HttpServiceConstants, ErrorCodeConstants {
	
	private static final Logger LOGGER = Logger.getLogger(HttpRepositoryImpl.class);
	
	private PGRequest pGRequest;
	
	HttpRepositoryImpl(PGRequest pGRequest){
		this.pGRequest = pGRequest;
	}
	
	
	@Override
	public void initializeHttpService() throws WalletException {
		pGRequest.initialize();
	}


	@Override
	public PgResponse postAuthRequest(PgRequest  authAPIRequest) throws WalletException {
		
		final long startTime = System.currentTimeMillis();
		JSONObject jsonObject = constructJSONObject(authAPIRequest);
		LOGGER.info(" postAuthRequest jsonObject:: " + jsonObject);
		String responseJsonData = pGRequest.connectPostMethod(jsonObject);
		PgResponse pgResponse = getResponseData(responseJsonData);
		LOGGER.info(" postAuthRequest pgResponse:: " + pgResponse);
		final long endTime   = System.currentTimeMillis();
		final long totalTime = (endTime - startTime)/GlobalLitterals.MILLI;
		LOGGER.debug(" postAuthRequest callduration(seconds) " +totalTime);
		return pgResponse;
		
	}
	
	@Override
	public CancelResponse postCancelRequest(PgRequest cancelAPIRequest) throws WalletException {
		
		final long startTime = System.currentTimeMillis();
		JSONObject jsonObject = constructJSONObject(cancelAPIRequest);
		LOGGER.info(" postCancelRequest jsonObject :: " +  jsonObject);
		String responseJsonData = pGRequest.connectPostMethod(jsonObject);
		LOGGER.info(" postCancelRequest responseJsonData :: " +  responseJsonData);
		CancelResponse cancelResponse = getResponseDataForCancel(responseJsonData);
		final long endTime   = System.currentTimeMillis();
		final long totalTime = (endTime - startTime)/GlobalLitterals.MILLI;
		LOGGER.debug(" postCancelRequest callduration(seconds) " + totalTime);
		return cancelResponse;
		
	}
	
	@Override
	public SettlementResponse postSettlementRequest(PgRequest settlementAPIRequest)
			throws WalletException {
		
		final long startTime = System.currentTimeMillis();
		JSONObject jsonObject = constructJSONObject(settlementAPIRequest);
		LOGGER.info(" postSettlementRequest :: jsonObject " + jsonObject);
		String responseJsonData = pGRequest.connectPostMethod(jsonObject);
		LOGGER.info(" postSettlementRequest :: responseJsonData " + responseJsonData);
		SettlementResponse settlementResponse = getResponseDataForSettle(responseJsonData);
		final long endTime   = System.currentTimeMillis();
		final long totalTime = (endTime - startTime)/GlobalLitterals.MILLI;
		LOGGER.debug(" postSettlementRequest callduration(seconds) " +totalTime);
		return settlementResponse;
		
	}
	
	@Override
	public RefundResponse postRefundRequest(PgRequest refundAPIRequest) throws WalletException {
		
		final long startTime = System.currentTimeMillis();
		JSONObject jsonObject = constructJSONObject(refundAPIRequest);
		LOGGER.info(" postRefundRequest jsonObject :: " + jsonObject);
		String responseJsonData = pGRequest.connectPostMethod(jsonObject);
		LOGGER.info(" postRefundRequest responseJsonData :: " + responseJsonData);
		RefundResponse refundResponse = getResponseDataForRefund(responseJsonData);
		final long endTime   = System.currentTimeMillis();
		final long totalTime = (endTime - startTime)/GlobalLitterals.MILLI;
		LOGGER.debug(" postSettlementRequest callduration(seconds) " +totalTime);
		return refundResponse;
		
	}

	private PgResponse getResponseData(String jsonData) {
		
		String responseCode = null;
		String responseMsg = null;
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonData);
		//LOGGER.info("Got Auth Response :: "+jsonObject.toString());
		PgResponse authAPIResponse = new PgResponse();
		if (null != jsonObject && !jsonObject.isEmpty()) {
			if (jsonObject.has(RESPONSEDECISION)) {
				String responseDecision = jsonObject.getString(RESPONSEDECISION);
				if (responseDecision.equalsIgnoreCase(RESPONSE_DECISION_SUCCESS)) {
					responseCode = responseDecision;
					responseMsg = jsonObject.getString(RESPONSE_TEXT);
					constructAuthAPIResponse(jsonObject, authAPIResponse);
				}
				responseCode = jsonObject.getString(RESPONSEDECISION);
				responseMsg = jsonObject.getString(RESPONSE_TEXT_RESP);
			} else {
				responseCode = ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG;
				responseMsg = responseCode;
			}
		} else {
			responseCode = ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG;
			responseMsg = responseCode;
		}
		authAPIResponse.setResponseCode(responseCode);
		authAPIResponse.setResponseMsg(responseMsg);
		return authAPIResponse;
	
	}
	
	private CancelResponse getResponseDataForCancel(String jsonData){
		
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonData);
		//LOGGER.info("Got CancelResponse :: "+jsonObject.toString());
		CancelResponse cancelResponse = new CancelResponse();
		if (null != jsonObject && !jsonObject.isEmpty()) {
			if (jsonObject.has(RESPONSE_DECISION)) {
				String responseDecision = jsonObject.getString(RESPONSE_DECISION);
				if (responseDecision.equalsIgnoreCase(RESPONSE_DECISION_SUCCESS)) {
					constructCancelAPIResponse(jsonObject, cancelResponse);
				}
				cancelResponse.setResponseMessage(jsonObject.getString(RESPONSE_TEXT));
		    	cancelResponse.setResponseDecision(jsonObject.getString(RESPONSE_DECISION));
			} else {
				//failed
				cancelResponse.setResponseMessage(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
				cancelResponse.setResponseDecision(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
			}
		} else {
			cancelResponse.setResponseMessage(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
			cancelResponse.setResponseDecision(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
		}
		return cancelResponse;
	}
	
	private SettlementResponse getResponseDataForSettle(String jsonData){
		
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonData);
		SettlementResponse settlementResponse = new SettlementResponse();
		//LOGGER.info("Got SettlementResponse :: "+jsonObject.toString());
		if (null != jsonObject && !jsonObject.isEmpty()) {
			if (jsonObject.has(RESPONSE_DECISION)) {
				String responseDecision = jsonObject.getString(RESPONSE_DECISION);
				if (responseDecision.equalsIgnoreCase(RESPONSE_DECISION_SUCCESS)) {
					constructSettleAPIResponse(jsonObject, settlementResponse);
				}
				settlementResponse.setResponseMessage(jsonObject.getString(RESPONSE_TEXT));
			    settlementResponse.setResponseDecision(jsonObject.getString(RESPONSE_DECISION));
			} else {
				//failed
				settlementResponse.setResponseMessage(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
				settlementResponse.setResponseDecision(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
			}
		} else {
			settlementResponse.setResponseMessage(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
			settlementResponse.setResponseDecision(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
		}
		return settlementResponse;
	}
	
	private RefundResponse getResponseDataForRefund(String jsonData){
		
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonData);
		//LOGGER.info("Got RefundResponse :: "+jsonObject.toString());
		RefundResponse refundResponse = new RefundResponse();
		if (null != jsonObject && !jsonObject.isEmpty()) {
			if (jsonObject.has(RESPONSE_DECISION)) {
				String responseDecision = jsonObject.getString(RESPONSE_DECISION);
				if (responseDecision.equalsIgnoreCase(RESPONSE_DECISION_SUCCESS)) {
					constructRefundAPIResponse(jsonObject, refundResponse);
				}
				 refundResponse.setResponseMessage(jsonObject.getString(RESPONSE_TEXT));
				 refundResponse.setResponseDecision(jsonObject.getString(RESPONSEDECISION));
			} else {
				//failed
				refundResponse.setResponseMessage(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
				refundResponse.setResponseDecision(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
			}
		} else {
			refundResponse.setResponseMessage(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
			refundResponse.setResponseDecision(ErrorCodeConstants.UNKNOWN_ERROR_FROM_PG);
		}
		return refundResponse;
	}
	
	private static PgResponse constructAuthAPIResponse(JSONObject jsonObject, PgResponse authAPIResponse){
		authAPIResponse.setAmount(jsonObject.getString(AMOUNT_RESP));
		//authAPIResponse.setAuthorizationCode(jsonObject.getString(AUTHORIZATION_CODE_RESP));
		//authAPIResponse.setAvsResponseCode(jsonObject.getString(AVS_RESPONSE_CODE_RESP));
		authAPIResponse.setCurrency(jsonObject.getString(CURRENCY_RESP));
		//authAPIResponse.setCvvResponseCode(jsonObject.getString(CVV2_RESPONSE_CODE_RESP));
		authAPIResponse.setDateandTime(jsonObject.getString(DATE_AND_TIME_RESP));
		authAPIResponse.setOrderID(jsonObject.getString(ORDER_ID_RESP));
		authAPIResponse.setPaymentMode(jsonObject.getString(PAYMENT_MODE_RESP));
		authAPIResponse.setPgTxnID(jsonObject.getString(PG_TXN_ID_RESP));
		authAPIResponse.setResponsedecision(jsonObject.getString(RESPONSE_DECISION));
		authAPIResponse.setResponseText(jsonObject.getString(RESPONSE_TEXT_RESP));
		//authAPIResponse.setTraceNo(jsonObject.getString(TRACE_NO_RESP));
		authAPIResponse.setTxnType(jsonObject.getString(TXN_TYPE_RESP));
		return authAPIResponse;
		
	}
	
    private static CancelResponse constructCancelAPIResponse(JSONObject jsonObject, CancelResponse cancelResponse){
		
    	cancelResponse.setMerchantID(jsonObject.getString(MERCHANT_ID));
    	cancelResponse.setPgTxnId(jsonObject.getString(PG_TXNID_CANCEL_RESP));
    	cancelResponse.setTxnAmount(jsonObject.getString(TXN_AMOUNT_CANCEL_RESP));
    	//cancelResponse.setResponseCode(jsonObject.getString(RESPONSE_CODE));
    	cancelResponse.setResponseMessage(jsonObject.getString(RESPONSE_TEXT));
    	cancelResponse.setResponseDecision(jsonObject.getString(RESPONSE_DECISION));
		return cancelResponse;
		
	}
   
    private static SettlementResponse constructSettleAPIResponse(JSONObject jsonObject, SettlementResponse settlementResponse){
    	
    	settlementResponse.setTxnDate(jsonObject.getString(DATE_AND_TIME_RESP));
    	settlementResponse.setMerchantID(jsonObject.getString(MERCHANT_ID));
    	settlementResponse.setOrderId(jsonObject.getString(ORDER_ID_SETTLE_RESP));
    	settlementResponse.setPgTxnId(jsonObject.getString(PG_TXNID_SETTLE_RESP));
    	settlementResponse.setSettlementTxnId(jsonObject.getString(SETTLEMENT_TXN_ID_SETTLE_RESP));
    	settlementResponse.setTxnAmount(jsonObject.getString(TXN_AMOUNT_SETTLE_RESP));
    	settlementResponse.setSettledAmount(jsonObject.getString(SETTLED_AMOUNT_SETTLE_RESP));
    	
    	//settlementResponse.setSettlementOrderNo(jsonObject.getString(SETTLEMENT_ORDER_NO_SETTLE_RESP));
    	//settlementResponse.setTxnCurrency(jsonObject.getString(TXN_CURRENCY_SETTLE_RESP));
    	//settlementResponse.setMerchantOrderNo(jsonObject.getString(MERCHANT_ORDER_NO_SETTLE_RESP));
    	//settlementResponse.setResponseCode(jsonObject.getString(RESPONSE_CODE));
    	settlementResponse.setResponseMessage(jsonObject.getString(RESPONSE_TEXT));
    	settlementResponse.setResponseDecision(jsonObject.getString(RESPONSE_DECISION));
		return settlementResponse;
		
	}
	
  private static RefundResponse constructRefundAPIResponse(JSONObject jsonObject, RefundResponse refundResponse){
    	
	    refundResponse.setMerchantID(jsonObject.getString(MERCHANT_ID));
	    refundResponse.setPgTxnId(jsonObject.getString(PG_TXNID_REFUND_RESP));
	    refundResponse.setTxnAmount(jsonObject.getString(TXN_AMOUNT_REFUND_RESP));
	    refundResponse.setRefundAmount(jsonObject.getString(REFUND_AMOUNT_REFUND_RESP));
	    refundResponse.setResponseCode(jsonObject.getString(RESPONSE_CODE));
	    refundResponse.setResponseMessage(jsonObject.getString(RESPONSE_TEXT));
	    refundResponse.setResponseDecision(jsonObject.getString(RESPONSEDECISION));
	    return refundResponse;
		
	}
	private	JSONObject	constructJSONObject(PgRequest apiRequest) {
		
		JSONObject jsonObject = new JSONObject();
		// Cancel //Auth //Settle //Refund
		jsonObject.put(TXN_TYPE_REQ, apiRequest.getTxnType());
		jsonObject.put(PAYMENT_MODE_REQ, apiRequest.getPaymentMode());
		jsonObject.put(DATE_AND_TIME_REQ, apiRequest.getDateAndTime());
		jsonObject.put(STORE_ID_REQ, apiRequest.getStoreID());
		jsonObject.put(ORDER_ID_REQ, apiRequest.getOrderID());
		jsonObject.put(CUSTOMER_ID_REQ, apiRequest.getCustomerID());
		jsonObject.put(REQ_TYPE_REQ, apiRequest.getReqType());
		jsonObject.put(CUSTOMER_DOB_REQ, apiRequest.getCustomerDOB());
		jsonObject.put(SOCIAL_ID_REQ, apiRequest.getSocialID());
		jsonObject.put(DRIVING_LICENSE_REQ, apiRequest.getDrivingLicense());
		jsonObject.put(MOBILE_NUMBER_REQ, apiRequest.getMobileNumber());//
		jsonObject.put(EMAIL_ID_REQ, apiRequest.getEmailId());
		jsonObject.put(ADDRESS_ONE_REQ, apiRequest.getAddressOne());
		jsonObject.put(ADDRESS_TWO_REQ, apiRequest.getAddressTwo());
		jsonObject.put(CITY_REQ, apiRequest.getCity());
		jsonObject.put(STATE_REQ, apiRequest.getState());
		jsonObject.put(COUNTRY_REQ, apiRequest.getCountry());
		jsonObject.put(ZIP_CODE_REQ, apiRequest.getZipcode());
		jsonObject.put(AMOUNT_REQ, apiRequest.getAmount());//
		jsonObject.put(CURRENCY_REQ, apiRequest.getCurrency());
		jsonObject.put(AUTH_MODE_REQ, apiRequest.getAuthMode());
		jsonObject.put(ORDER_DETAILS_REQ, apiRequest.getOrderDetails());
		jsonObject.put(COMMENT_REQ, apiRequest.getComment());
		jsonObject.put(USER_AGENT_REQ, apiRequest.getUserAgent());
		jsonObject.put(SOURCE_IP_REQ, apiRequest.getSourceIp());
		jsonObject.put(IS_FRAUD_CHECK_REQ, apiRequest.getIsFraudCheck());
		jsonObject.put(PG_TXN_ID_REQ, apiRequest.getPgTxnId());
		
		jsonObject.put(NAME_ON_CARD_REQ, apiRequest.getNameOnCard());
		jsonObject.put(CARD_NUMBER_REQ, apiRequest.getCardNumber());
		
		if(apiRequest.getCardExpiryMonth() != null){
			String tempMonth = apiRequest.getCardExpiryMonth().length() == 2 ? apiRequest.getCardExpiryMonth(): "0" + apiRequest.getCardExpiryMonth();
			jsonObject.put(CARD_EXPIRY_MONTH_REQ, tempMonth);
		}
		
		jsonObject.put(CARD_EXPIRY_YEAR_REQ, apiRequest.getCardExpiryYear());
		jsonObject.put(CARD_CVV_REQ, apiRequest.getCardCVV());
		jsonObject.put(CARD_STORAGE_ID_REQ, apiRequest.getCardStorageId());
		
		jsonObject.put(STORE_CARD_FLAG_REQ, apiRequest.getStoreCardFlag());
		jsonObject.put(DELETE_CARD_FLAG_REQ, apiRequest.getDeleteCardFlag());
		jsonObject.put(TRACE_NO_REQ, apiRequest.getTraceNo());
		jsonObject.put(BILLING_ADDRESS_LINE_ONE_REQ, apiRequest.getBillingAddressLineOne());
		jsonObject.put(BILLING_ADDRESS_LINE_TWO_REQ, apiRequest.getBillingAddressLineTwo());
		jsonObject.put(BILLING_ADDRESS_CITY_REQ, apiRequest.getBillingAddressCity());
		jsonObject.put(BILLING_ADDRESS_STATE_REQ, apiRequest.getBillingAddressState());
		jsonObject.put(BILLING_ADDRESS_COUNTRY_REQ, apiRequest.getBillingAddressCountry());
		jsonObject.put(BILLING_ADDRESS_ZIP_REQ, apiRequest.getBillingAddressZIP());
		jsonObject.put(STATEMENT_NARRATIVE_REQ, apiRequest.getStatementNarrative());
		jsonObject.put(TERMINAL_ID_REQ, apiRequest.getTerminalID());
		jsonObject.put(THREE_D_SECURE_ENROLLED_REQ, apiRequest.getThreeDSecureEnrolled());
		jsonObject.put(CAVV_REQ, apiRequest.getcAVV());
		jsonObject.put(ECI_FLAG_REQ, apiRequest.geteCIFlag());
		jsonObject.put(XID_VALUE_REQ, apiRequest.getxIDValue());
		jsonObject.put(AUTHCAPTURE, DEFAULT_AUTHCAPTURE);
		return jsonObject;

	}
}
