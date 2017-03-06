/**
 * 
 */
package com.tarang.ewallet.transaction.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.dto.ReloadMoneyDto;
import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.dto.SelfTransferDto;
import com.tarang.ewallet.dto.VelocityAndThresholdDto;
import com.tarang.ewallet.dto.WalletThresholdDto;
import com.tarang.ewallet.model.AuthTxn;
import com.tarang.ewallet.model.History;
import com.tarang.ewallet.model.MasterAmountWallet;
import com.tarang.ewallet.model.MasterFeeWallet;
import com.tarang.ewallet.model.MasterTaxWallet;
import com.tarang.ewallet.model.NonRegisterWallet;
import com.tarang.ewallet.model.WalletFee;
import com.tarang.ewallet.model.WalletTax;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.model.CancelTxn;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.model.Payment;
import com.tarang.ewallet.model.PgResponse;
import com.tarang.ewallet.model.RefundTxn;
import com.tarang.ewallet.model.SettlementTxn;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;


/**
 * @author vasanthar
 *
 */
public class DataPreparation {
	
	public static PgResponse getPgResponse(){
		PgResponse pgResponse = new PgResponse();
		pgResponse.setAccountId(1L);
		pgResponse.setAmount("10000");
		pgResponse.setAuthorizationCode("ANYPV3466A");
		pgResponse.setAvsResponseCode("VSResponsecode");
		pgResponse.setCardNumber("5193241027421599");
		pgResponse.setCheckSum("checkSum");
		pgResponse.setCode("code");
		pgResponse.setCurrency("rupee");
		pgResponse.setCvvResponseCode("cVV2Responsecode");
		pgResponse.setDateandTime("10/10/1000");
		pgResponse.setDateAndTimewithTimestamp(new Timestamp(1L));
		pgResponse.setIsSuccess(true);
		pgResponse.setMsg("msg");
		pgResponse.setNumberOfAttempts(4L);
		pgResponse.setOrderID("123456789");
		pgResponse.setPaymentMode("check");
		pgResponse.setPgTxnID("174");
		pgResponse.setResponseCode("responseCode");
		pgResponse.setResponsedecision("responsedecision");
		pgResponse.setResponseMsg("responseMsg");
		pgResponse.setResponseText("responseText");
		pgResponse.setTraceNo("100886");
		pgResponse.setTxnType("txnType");
		return pgResponse;			
	}
	
	public static PgResponse getPgResponse2(){
		PgResponse pgResponse = new PgResponse();
		pgResponse.setAccountId(1L);
		pgResponse.setAmount("50000");
		pgResponse.setAuthorizationCode("ANYPV3466A");
		pgResponse.setAvsResponseCode("VSResponsecode");
		pgResponse.setCardNumber("0123456789");
		pgResponse.setCheckSum("checkSum");
		pgResponse.setCode("code");
		pgResponse.setCurrency("rupee");
		pgResponse.setCvvResponseCode("cVV2Responsecode");
		pgResponse.setDateandTime("10/10/1000");
		pgResponse.setDateAndTimewithTimestamp(new Timestamp(1L));
		pgResponse.setIsSuccess(true);
		pgResponse.setMsg("msg");
		pgResponse.setNumberOfAttempts(4L);
		pgResponse.setOrderID("VSP21");
		pgResponse.setPaymentMode("check");
		pgResponse.setPgTxnID("0790110051");
		pgResponse.setResponseCode("responseCode");
		pgResponse.setResponsedecision("responsedecision");
		pgResponse.setResponseMsg("responseMsg");
		pgResponse.setResponseText("responseText");
		pgResponse.setTraceNo("100886");
		pgResponse.setTxnType("txnType");
		return pgResponse;			
	}
	
	public static SettlementTxn getSettlementTxn(){
		SettlementTxn settlementTxn = new SettlementTxn();
		settlementTxn.setMerchantID("merchantID");
		settlementTxn.setOrderId("VSP21");
		settlementTxn.setPgTxnId("0790110051");
		settlementTxn.setSettlementTxnId("079011005111141");
		settlementTxn.setTxnAmount(Double.parseDouble("50000"));
		settlementTxn.setSettledAmount(Double.parseDouble("50000"));
		settlementTxn.setTxnDate("10/10/1000");
		settlementTxn.setSettlementOrderNo("settlementOrderNo");
		settlementTxn.setTxnCurrency("rupee");
		settlementTxn.setMerchantOrderNo("merchantOrderNo");
		settlementTxn.setResponseCode("responseCode");
		settlementTxn.setResponseMessage("responseMessage");
		settlementTxn.setResponseDecision("responseDecision");
		return settlementTxn;			
	}
	
	public static RefundTxn getRefundTxn(){
		RefundTxn refundTxn = new RefundTxn();
		refundTxn.setMerchantID("merchantID");
		refundTxn.setPgTxnId("0790110051");
		refundTxn.setTxnAmount(Double.parseDouble("50000"));
		refundTxn.setRefundAmount(Double.parseDouble("50000"));
		refundTxn.setResponseCode("responseCode");
		refundTxn.setResponseMessage("responseMessage");
		refundTxn.setResponseDecision("responseDecision");
		return refundTxn;			
	}
	
	public static CancelTxn getCancelTxn(){
		CancelTxn cancelTxn = new CancelTxn();
		cancelTxn.setMerchantID("merchantID");
		cancelTxn.setPgTxnId("0790110051");
		cancelTxn.setTxnAmount(Double.parseDouble("50000"));
		cancelTxn.setResponseCode("responseCode");
		cancelTxn.setResponseMessage("responseMessage");
		cancelTxn.setResponseDecision("responseDecision");
		return cancelTxn;			
	}
	
	
	public static void getReceiveMoney(RequestMoneyDto receiveMoneyDto){
		receiveMoneyDto.setRequesterEmail("apitest744@gmail.com");
		receiveMoneyDto.setRequesterName("First name");
		receiveMoneyDto.setAmount(20000.00);
		receiveMoneyDto.setCurrencyId(1L);
		receiveMoneyDto.setRequesterMsg("Request Msg");
		receiveMoneyDto.setRequestDate(new Date());
		receiveMoneyDto.setResponserEmail("apitest744@gmail.com");
		receiveMoneyDto.setResponserName("First name");
		receiveMoneyDto.setResponserMsg("Response Msg");
		receiveMoneyDto.setResponseDate(new Date());
		receiveMoneyDto.setStatus(1L);
		receiveMoneyDto.setCountryId(1L);
		receiveMoneyDto.setTypeOfRequest(2L);
	}
	
	public static WalletTransaction getTransaction() {
		WalletTransaction tx = new WalletTransaction();
		//tx.setPayee(1L);
		//tx.setPayer(1L);
		tx.setCreationDate( new Timestamp(System.currentTimeMillis()));
		tx.setPayerAmount(10.0);
		tx.setPayeeCurrency(1L);
		tx.setPayerCurrency(1L);
		tx.setStatus(4L);
		tx.setTypeOfTransaction(1L);
		tx.setPayeeFee(1.0);
		tx.setPayeeTax(0.1);
		tx.setPayerFee(1.0);
		tx.setPayerTax(0.1);
		tx.setPayeeBalance(0.0);
		tx.setPayerBalance(0.0);
		tx.setConvertionRate(1.0);
		tx.setTypeOfRequest(2L);
		tx.setId(1L);
		return tx;
	}

	public static ReloadMoneyDto getReloadMoney() {
		ReloadMoneyDto reloadMoney = new ReloadMoneyDto();
		reloadMoney.setAccountId(1L);
		reloadMoney.setReloadAmount(10000.00);
		reloadMoney.setTrxId(1L);
		reloadMoney.setCurrentDate(new Timestamp(1L));
		reloadMoney.setCardExpairyDate("01/2031");
		reloadMoney.setCardNumber("4012888888881881");
		reloadMoney.setClientIpAddress("172.30.66.73");
		reloadMoney.setCurrencyCode("USD");
		reloadMoney.setCvv("123");
		reloadMoney.setUserAgent("userAgent");
		reloadMoney.setTypeOfTransaction(13L);
		reloadMoney.setCurrencyId(1L);
		reloadMoney.setCountryId(1L);
		reloadMoney.setPayeeName("Kedar nath");
		reloadMoney.setPayeeEmail("kedarnathd@tarangtech.com");
		reloadMoney.setLanguageId(1L);
		reloadMoney.setAccountOrCardHolderName("kedarnath das");
		return reloadMoney;
	}
	
	@SuppressWarnings("deprecation")
	public static CustomerDto getCustomerDto() {
		
		CustomerDto dto = new CustomerDto();
		
    	dto.setEmailId("apitest744@gmail.com");
    	dto.setPassword("aaaa1A@");
    	dto.setHintQuestion1(new Long(1));
    	dto.setAnswer1("answer1");
    	dto.setHintQuestion2(new Long(2));
    	dto.setAnswer2("answer2");
    	dto.setTitle(1L);
    	dto.setFirstName("First name");
    	dto.setLastName("Last name");
    	dto.setPhoneCode("91");
    	dto.setPhoneNo("801234");
    	
    	dto.setAddrOne("address one");
    	dto.setAddrTwo("address two");
    	dto.setCity("Bangalore");
    	dto.setState(1L);
    	dto.setCountry(1L);
    	dto.setPostelCode("560048");
    	dto.setDateOfBirth(new Date("10/10/1980"));
    	dto.setActive(true);
    	dto.setBlocked(false);
    	dto.setKycRequired(false);
    	dto.setLanguageId(1L);
    	dto.setDefaultCurrency(1L);
    	dto.setDefaultCurrency(1L);
    	dto.setAuthenticationId(1L);
		return dto;
	}
	public static VelocityAndThresholdDto createVelocityAndThresholdDto() {
		
		VelocityAndThresholdDto velocity = new VelocityAndThresholdDto();
		velocity.setCountry(1L);
		velocity.setCurrency(1L);
		velocity.setTransactiontype(1L);
		velocity.setMinimumamount(100.0);
		velocity.setMaximumamount(1000.0);
		velocity.setUserType(1L);
		return velocity;		
	}
	
	public static void dataPrepCreateForFeeDto(FeeDto feeDto) {
		feeDto.setUserType(2L);
		feeDto.setServices(2L);
		feeDto.setOperationType(2L);
		feeDto.setCountry(1L);
		feeDto.setCurrency(1L);
		feeDto.setPayingentity(2L);
		feeDto.setFeeType(2L);
		feeDto.setFixCharSen(666.666);
		feeDto.setPercentageSen(666.666);
		feeDto.setFixCharRec(666.666);
		feeDto.setPercentageRec(666.666);
	
		List<FeeSlab> fsList = new ArrayList<FeeSlab>();
		fsList.add(new FeeSlab(0.0, 10.0, 50.0, 10.0, 50.0, 10.0));
		fsList.add(new FeeSlab(10.0, 10.0, 10.0, 20.0, 10.0, 20.0));
		feeDto.setFeeSlabs(fsList);
	}
	
	public static WalletThresholdDto createWalletThresholdDto() {
		
		WalletThresholdDto threshold = new WalletThresholdDto();
		threshold.setCountry(1L);
		threshold.setCurrency(1L);
		threshold.setMaximumamount(100.0);
		return threshold;		
	}
	
	public static Payment dataPrePayment() {
		
	    Payment payment = new Payment();
	    payment.setOrderId("1234");
	    payment.setAmount(2345.88);
	    payment.setCurrency("USD");
	    payment.setCustomerAuthId(1L);
	    payment.setMerchantAuthId(2L);
	    payment.setStatus(1);
	    payment.setIpAddress("192.68.0.0");
	    payment.setOrderDate(new Date());
	    payment.setTxnId(76L);
	    return payment;
	}
	
	public static NonRegisterWallet getNonRegisterWallet() {
		
		NonRegisterWallet nonRegisterWallet = new NonRegisterWallet();
		nonRegisterWallet.setAmount(1000.0);
		nonRegisterWallet.setCreationDate(new Date());
		nonRegisterWallet.setCurrency(1L);
		nonRegisterWallet.setEmail("abcd@tarangtech.com");
		nonRegisterWallet.setId(1L);
		nonRegisterWallet.setRegister(2L);
		nonRegisterWallet.setTxnId(25L);
		return nonRegisterWallet;
	}
	
	public static SelfTransferDto getselfTransferDto(CustomerDto dto) {
		
		SelfTransferDto selfTransferDto = new SelfTransferDto();
		selfTransferDto.setActualAmount("1000");
		selfTransferDto.setActualCurrency(1L);
		selfTransferDto.setAuthId(dto.getAuthenticationId());
		selfTransferDto.setImeiNumber("4444");
		selfTransferDto.setRequestedAmount("10");
		selfTransferDto.setRequestedCurrency(3L);
		selfTransferDto.setSimNumber("4444");
		selfTransferDto.setTotalDeductions("10");
		selfTransferDto.setTransactionType(WalletTransactionTypes.P_TO_S);
		selfTransferDto.setTypeOfRequest(2L);
		return selfTransferDto;
	}
	
	public static WalletFee getAddFee() {
		
		WalletFee walletFee = new WalletFee();
		walletFee.setAmount(1000.0);
		walletFee.setCountry(1L);
		walletFee.setCurrency(1L);
		walletFee.setId(1L);
		walletFee.setParentId(1L);
		walletFee.setPayDate(new Date());
		walletFee.setReversal(Boolean.TRUE);
		walletFee.setTransactionId(1L);
		walletFee.setType(1L);
		return walletFee;
	}
	
	public static WalletTax getAddTax() {

		WalletTax walletTax = new WalletTax();
		walletTax.setAmount(1000.0);
		walletTax.setCountry(1L);
		walletTax.setCurrency(1L);
		walletTax.setId(1L);
		walletTax.setParentId(1L);
		walletTax.setPayDate(new Date());
		walletTax.setReversal(Boolean.TRUE);
		walletTax.setTransactionId(1L);
		walletTax.setType(1L);
		return walletTax;
	}
	
    public static AuthTxn getTransactionInfo(){
    	 PgResponse pgResponse = getPgResponse();
 	    
 	    AuthTxn transactionInfo = new AuthTxn();
 		transactionInfo.setAuthorizationCode(pgResponse.getAuthorizationCode());
 		transactionInfo.setAvsResponseCode(pgResponse.getAvsResponseCode());
 		transactionInfo.setCheckSum(pgResponse.getCheckSum());
 		transactionInfo.setCvvResponseCode(pgResponse.getcvvResponseCode());
 		transactionInfo.setPaymentMode(pgResponse.getPaymentMode());
 		transactionInfo.setPgTxnId(pgResponse.getPgTxnID());
 		transactionInfo.setRespnseText(pgResponse.getResponseText());
 		transactionInfo.setResponseDecision(pgResponse.getResponsedecision());
 		transactionInfo.setTraceNo(pgResponse.getTraceNo());
 		transactionInfo.setTxnType(pgResponse.getTxnType());
 		transactionInfo.setAuthHistoryId(2L);
 		return transactionInfo;	 
	}
    
	public static History getHistroyInfo(){
		PgResponse pgResponse = getPgResponse();
		
	    AuthTxn transactionInfo = getTransactionInfo();
	    History history = new History();
		history.setAccountId(pgResponse.getAccountId());
		history.setOrderId(pgResponse.getOrderID());
		history.setAmount(Double.parseDouble(pgResponse.getAmount()));
		history.setCardNumber(pgResponse.getCardNumber());
		history.setCode(pgResponse.getCode());
		history.setCurrency(pgResponse.getCurrency());
		history.setDateAndTime(pgResponse.getDateAndTimewithTimestamp());
		history.setIsSuccess(pgResponse.getIsSuccess());
		history.setMsg(pgResponse.getMsg());
		history.setNumberOfAttempts(pgResponse.getNumberOfAttempts());
		history.setTxnType(pgResponse.getTxnType());
		history.setAuthTxn(transactionInfo);
		history.setReqType("01");
		history.setAuthMode("3D");
		history.setTranceNumber("52364");
		history.setNameOnCard("kedarnath das");
		return history;	
	}
	
	public static List<Object> getMasterWalletsData(){
		List<Object> list = new ArrayList<Object>();
		
		// MasterAmountWallet (id, currency, amount)
		list.add(new MasterAmountWallet(1L, 1L, 0.0));
		list.add(new MasterAmountWallet(2L, 2L, 0.0));
		list.add(new MasterAmountWallet(3L, 3L, 0.0));

		// MasterFeeWallet( id, currency, fee)
		list.add(new MasterFeeWallet(1L, 1L, 0.0));
		list.add(new MasterFeeWallet(2L, 2L, 0.0));
		list.add(new MasterFeeWallet(3L, 3L, 0.0));

		// MasterTaxWallet( id, currency, tax)
		list.add(new MasterTaxWallet(1L, 1L, 0.0));
		list.add(new MasterTaxWallet(2L, 2L, 0.0));
		list.add(new MasterTaxWallet(3L, 3L, 0.0));

		return list;
	}
}
