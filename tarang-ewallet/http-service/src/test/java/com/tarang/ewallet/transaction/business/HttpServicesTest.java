package com.tarang.ewallet.transaction.business;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.business.HttpService;
import com.tarang.ewallet.model.CancelResponse;
import com.tarang.ewallet.model.PgRequest;
import com.tarang.ewallet.model.PgResponse;
import com.tarang.ewallet.model.RefundResponse;
import com.tarang.ewallet.model.SettlementResponse;
import com.tarang.ewallet.util.DateConvertion;


/**
 * @author : rajasekhar
 * @date : Dec 21, 2012
 * @time : 4:18:37 PM
 * @version : 1.0.0
 * @comments:
 * 
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
	"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class HttpServicesTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	private static final Logger LOG = Logger.getLogger(HttpServicesTest.class);

	@Autowired
	private HttpService httpService;
	
	@Test
	public void postAuthRequestTest() throws WalletException {
		
		PgRequest authAPIRequest = new PgRequest();
		authAPIRequest.setTxnType("Auth");//Auth //Cancel //Settle //Refund 
		authAPIRequest.setPaymentMode("CC");
		authAPIRequest.setDateAndTime(DateConvertion.getPgDateAndTime(new Date()));
		authAPIRequest.setOrderID(DateConvertion.getPgOrderId(new Date()));
		authAPIRequest.setReqType("01");//MOTO
		authAPIRequest.setAmount("10000");
		authAPIRequest.setCurrency("USD");//JPY //USD 
		
		authAPIRequest.setNameOnCard("kedarnath Das");
		authAPIRequest.setCardNumber("4532939897463780");//visa
		//authAPIRequest.setCardNumber("5410584659361469");//Mastercard
		authAPIRequest.setCardExpiryMonth("02");
		authAPIRequest.setCardExpiryYear("22");
		authAPIRequest.setCardCVV("003");
		
		PgResponse authAPIResponse = httpService.postAuthRequest(authAPIRequest);
		LOG.info(authAPIResponse.getResponseCode());
		LOG.info("Auth Txn Response Code :  " +authAPIResponse.getResponseCode());
		LOG.info("Auth Txn Response Msg  :  " + authAPIResponse.getResponseMsg());
		LOG.info("Auth Txn Response PgTxnID :  " + authAPIResponse.getPgTxnID());
	}
	
	@Test
	public void postCancelRequestTest() throws WalletException {
		
		PgRequest authAPIRequest = new PgRequest();
		authAPIRequest.setTxnType("Cancel");//Auth //Cancel //Settle //Refund 
		authAPIRequest.setPaymentMode("CC");
		authAPIRequest.setDateAndTime(DateConvertion.getPgDateAndTime(new Date()));
		authAPIRequest.setOrderID(DateConvertion.getPgOrderId(new Date()));
		authAPIRequest.setReqType("01");
		authAPIRequest.setAmount("10000");
		authAPIRequest.setCurrency("USD");//EUR //USD //INR 
		authAPIRequest.setPgTxnId("2067203042013173703");//for cancel we need to pass pgTxnId
		authAPIRequest.setNameOnCard("kedarnath Das");
		authAPIRequest.setCardNumber("4532939897463780");
		authAPIRequest.setCardExpiryMonth("02");
		authAPIRequest.setCardExpiryYear("22");
		authAPIRequest.setCardCVV("589");
		
		CancelResponse authAPIResponse = httpService.postCancelRequest(authAPIRequest);
		LOG.info("Cancel Txn Response Decision :  " +authAPIResponse.getResponseDecision());
		LOG.info("Cancel Txn Response Msg  :  " +authAPIResponse.getResponseMessage());
	}
	
	@Test
	public void postSettlementRequestTest() throws WalletException {
		
		PgRequest authAPIRequest = new PgRequest();
		authAPIRequest.setTxnType("Settle");//Auth //Cancel //Settle //Refund 
		authAPIRequest.setPaymentMode("CC");
		authAPIRequest.setDateAndTime(DateConvertion.getPgDateAndTime(new Date()));
		authAPIRequest.setOrderID(DateConvertion.getPgOrderId(new Date()));
		authAPIRequest.setReqType("01");//MOTO
		authAPIRequest.setAmount("10000");
		authAPIRequest.setCurrency("USD");//JPY //USD 
		authAPIRequest.setPgTxnId("2083203042013182351");
		
		SettlementResponse settlementResponse = httpService.postSettlementRequest(authAPIRequest);
		LOG.info(settlementResponse);
		Assert.assertNotNull(settlementResponse);
		
		LOG.info("Settlement Txn Response Decision :  " +settlementResponse.getResponseDecision());
		LOG.info("Settlement Txn Response Msg  :  " +settlementResponse.getResponseMessage());
		//LOG.info("Settlement Txn Response Msg  :  " +settlementResponse.getS
	}
	
	
	@Test
	public void postRefundRequestTest() throws WalletException {
		
		PgRequest authAPIRequest = new PgRequest();
		authAPIRequest.setTxnType("Refund");//Auth //Cancel //Settle //Refund 
		authAPIRequest.setPaymentMode("CC");
		authAPIRequest.setDateAndTime(DateConvertion.getPgDateAndTime(new Date()));
		authAPIRequest.setOrderID(DateConvertion.getPgOrderId(new Date()));
		authAPIRequest.setReqType("01");//MOTO
		authAPIRequest.setAmount("10000");
		authAPIRequest.setCurrency("USD");//JPY //USD
		
		authAPIRequest.setPgTxnId("2041303042013171716");
		
		RefundResponse refundResponse = httpService.postRefundRequest(authAPIRequest);
		LOG.info(refundResponse);
		Assert.assertNotNull(refundResponse);
		LOG.info("Cancel Txn Response Decision :  " +refundResponse.getResponseDecision());
		LOG.info("Cancel Txn Response Msg  :  " +refundResponse.getResponseMessage());
	}
}
