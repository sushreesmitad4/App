package com.tarang.ewallet.transaction.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.AddMoneyDto;
import com.tarang.ewallet.dto.ReloadMoneyDto;
import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.dto.SelfTransferDto;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.util.HttpServiceConstants;
import com.tarang.ewallet.model.AddMoney;
import com.tarang.ewallet.model.AuthTxn;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.model.History;
import com.tarang.ewallet.model.NonRegisterWallet;
import com.tarang.ewallet.model.PgRequest;
import com.tarang.ewallet.model.PgResponse;
import com.tarang.ewallet.model.ReloadMoney;
import com.tarang.ewallet.model.RequestMoney;
import com.tarang.ewallet.model.SendMoney;
import com.tarang.ewallet.model.SendMoneyRecurring;
import com.tarang.ewallet.model.SendMoneyTxn;
import com.tarang.ewallet.model.SettlementResponse;
import com.tarang.ewallet.model.SettlementTxn;
import com.tarang.ewallet.model.Tax;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;

public class WalletTransactionUtil implements GlobalLitterals {
	
	public static final String REQUEST_MONEY_LIST_QUERY = "select new com.tarang.ewallet.dto.RequestMoneyDto(rm.id, rm.requesterId, " +
			"au.emailId, rm.requestDate, rm.currencyId, rm.amount, rm.transactionId, rm.attempts, rm.status ) " +
			"from RequestMoney rm, Authentication au, PersonName pn, Customer cu " +
			"where (rm.responserId=au.id and au.id=cu.authenticationId and cu.nameId=pn.id and rm.requesterId=?) " +
			"or (rm.requesterId=au.id and au.id=cu.authenticationId and cu.nameId=pn.id and rm.responserId=?)";
	
	public static final String SEND_MONEY_LIST_FOR_REQUESTER_QUERY = "select new com.tarang.ewallet.dto.SendMoneyDto(sm.id, sm.currency, sm.requestDate, sm.amount, " +
			"sm.message, au.emailId, au.userType) from SendMoney sm, Authentication au where (au.id=sm.senderAuthId and sm.receiverAuthId=?) " +
			"order by sm.requestDate desc"; 
	
	public static final String IS_JOB_NAME_EXIST_QUERY = "select count(*) from SendMoney sm, SendMoneyRecurring smr where sm.id=smr.sendMoneyId and sm.senderAuthId=? and smr.userJobName=?";
	
	public static WalletTransaction getTransactionFromDto(ReloadMoneyDto dto){
		WalletTransaction transaction = new WalletTransaction();
		/*In case of reload money payer auth id will be null*/
		/*Receiver auth id*/
		transaction.setIpAddress(dto.getClientIpAddress());
		transaction.setPayee(dto.getAuthId());
		transaction.setPayerAmount(dto.getReloadAmount());
		transaction.setPayeeAmount(dto.getReloadAmount());
		transaction.setPayeeCurrency(dto.getCurrencyId());
		transaction.setPayerCurrency(dto.getCurrencyId());
		transaction.setTypeOfTransaction(dto.getTypeOfTransaction());
		transaction.setTypeOfRequest(dto.getTypeOfRequest());
		return transaction;
	}
	
	public static WalletTransaction getTransactionFromDto(AddMoneyDto dto){
		WalletTransaction transaction = new WalletTransaction();
		/*In case of add money payer auth id will be null*/
		/*Receiver auth id*/
		transaction.setIpAddress(dto.getClientIpAddress());
		transaction.setPayee(dto.getAuthId());
		transaction.setPayerAmount(dto.getAddMoneyAmount());
		transaction.setPayeeAmount(dto.getAddMoneyAmount());
		transaction.setPayeeCurrency(dto.getCurrencyId());
		transaction.setPayerCurrency(dto.getCurrencyId());
		transaction.setTypeOfTransaction(dto.getTypeOfTransaction());
		transaction.setTypeOfRequest(dto.getTypeOfRequest());
		return transaction;
	}
	
	public static ReloadMoney getReloadMoney(ReloadMoneyDto reloadMoneyDto) {
		ReloadMoney reloadMoney = new ReloadMoney();
		reloadMoney.setAuthId(reloadMoneyDto.getAuthId());
		reloadMoney.setAccountId(reloadMoneyDto.getAccountId());
		reloadMoney.setReloadAmount(reloadMoneyDto.getReloadAmount());
		reloadMoney.setCurrentDate(new Date());
		reloadMoney.setTypeOfRequest(reloadMoneyDto.getTypeOfRequest());
		
		return reloadMoney;
	}
	
	public static AddMoney getAddMoney(AddMoneyDto addMoneyDto) {
		AddMoney addMoney = new AddMoney();
		addMoney.setAuthId(addMoneyDto.getAuthId());
		//addMoney.setAccountId(reloadMoneyDto.getAccountId());
		addMoney.setOrderId(addMoneyDto.getOrderId());
		addMoney.setAddMoneyAmount(addMoneyDto.getAddMoneyAmount());
		addMoney.setCurrentDate(new Date());
		addMoney.setTypeOfRequest(addMoneyDto.getTypeOfRequest());
		
		return addMoney;
	}
	
	/**
	 * Prepare request object for settlementAPIRequest
	 * @param history
	 * @param settlementAPIRequest
	 */
	public static void getPgRequestFromHistory(History history, PgRequest settlementAPIRequest){
		AuthTxn info = history.getAuthTxn();
		settlementAPIRequest.setTxnType(HttpServiceConstants.SETTLEMENT_TXN_TYPE);
		settlementAPIRequest.setPaymentMode(info.getPaymentMode());
		settlementAPIRequest.setDateAndTime(DateConvertion.getPgDateAndTime(new Date()));
		settlementAPIRequest.setOrderID(history.getOrderId());
		settlementAPIRequest.setReqType(history.getReqType());
		settlementAPIRequest.setAmount(CommonUtil.convertToString((history.getAmount())));
		settlementAPIRequest.setCurrency(history.getCurrency());
		settlementAPIRequest.setPgTxnId(info.getPgTxnId());
	}
	
	/**
	 * Save PG Auth response details in history
	 * @param pgRequest
	 * @param pgResponse
	 * @param accountId
	 * @throws WalletException
	 */
	public static History savePGAuthTransactionInHistory(PgRequest pgRequest, PgResponse pgResponse, Long accountId, CryptService cryptService ) throws WalletException{
		History history = new History();
		
		history.setTxnType(pgRequest.getTxnType());
		history.setReqType(pgRequest.getReqType());
		history.setAuthMode(pgRequest.getAuthMode());
		history.setTranceNumber(pgRequest.getTraceNo());
		history.setDateAndTime(new Date());
		history.setOrderId(pgRequest.getOrderID());
		history.setAccountId(accountId);
		history.setCardNumber(cryptService.encryptData(pgRequest.getCardNumber()));
		history.setAmount(CommonUtil.convertToDouble(pgRequest.getAmount()));
		history.setCurrency(pgRequest.getCurrency());
		history.setNumberOfAttempts(0L);
		history.setIsSuccess(pgResponse.getIsSuccess());
		history.setUserAgent(pgRequest.getUserAgent());
		history.setSourceIp(pgRequest.getSourceIp());
		history.setNameOnCard(pgRequest.getNameOnCard());
		if(pgResponse.getIsSuccess()){
			AuthTxn info = new AuthTxn();
			
			info.setTxnType(pgResponse.getTxnType());
			info.setPaymentMode(pgResponse.getPaymentMode());
			info.setCheckSum(pgResponse.getCheckSum());
			info.setPgTxnId(pgResponse.getPgTxnID());
			info.setResponseDecision(pgResponse.getResponsedecision());
			info.setAuthorizationCode(pgResponse.getAuthorizationCode());
			info.setTraceNo(pgResponse.getTraceNo());
			info.setRespnseText(pgResponse.getResponseText());
			info.setCvvResponseCode(pgResponse.getcvvResponseCode());
			info.setAvsResponseCode(pgResponse.getAvsResponseCode());
			
			history.setCode(pgResponse.getResponseCode());
			history.setMsg(pgResponse.getResponseMsg());
			
			history.setAuthTxn(info);
		}else{
			history.setErrorCode(pgResponse.getResponseCode());
			history.setErrorMsg(pgResponse.getResponseMsg());
		}
		return history;
	}
	
	/**
	 * Save PG settlement response details in history
	 * @param pgRequest
	 * @param settlementResponse
	 * @param accountId
	 * @param isSuccess
	 * @return
	 * @throws WalletException
	 */
	public static History saveSettlementResponseInHistory(PgRequest pgRequest, SettlementResponse settlementResponse, 
			Long accountId, Boolean isSuccess) throws WalletException{
		History history = new History();
		
		history.setTxnType(pgRequest.getTxnType());
		history.setReqType(pgRequest.getReqType());
		history.setAuthMode(pgRequest.getAuthMode());
		history.setTranceNumber(pgRequest.getTraceNo());
		history.setDateAndTime(new Date());
		history.setOrderId(pgRequest.getOrderID());
		history.setAccountId(accountId);
		history.setAmount(CommonUtil.convertToDouble(pgRequest.getAmount()));
		history.setCurrency(pgRequest.getCurrency());
		history.setNumberOfAttempts(0L);
		if(isSuccess){
			SettlementTxn settlementTxn = new SettlementTxn();
			
			settlementTxn.setMerchantID(settlementResponse.getMerchantID());
			settlementTxn.setOrderId(settlementResponse.getOrderId());
			settlementTxn.setPgTxnId(settlementResponse.getPgTxnId());
			settlementTxn.setSettlementTxnId(settlementResponse.getSettlementTxnId());
			settlementTxn.setTxnAmount(CommonUtil.convertToDouble(settlementResponse.getTxnAmount()));
			settlementTxn.setSettledAmount(CommonUtil.convertToDouble(settlementResponse.getSettledAmount()));
			settlementTxn.setTxnDate(settlementResponse.getTxnDate());
			settlementTxn.setSettlementOrderNo(settlementResponse.getSettlementOrderNo());
			settlementTxn.setTxnCurrency(settlementResponse.getTxnCurrency());
			settlementTxn.setMerchantOrderNo(settlementResponse.getMerchantOrderNo());
			settlementTxn.setResponseCode(settlementResponse.getResponseCode());
			settlementTxn.setResponseMessage(settlementResponse.getResponseMessage());
			settlementTxn.setResponseDecision(settlementResponse.getResponseDecision());
			
			history.setCode(settlementResponse.getResponseCode());
			history.setMsg(settlementResponse.getResponseMessage());
			history.setIsSuccess(true);
			
			history.setSettlementTxn(settlementTxn);
		}else{
			history.setErrorCode(settlementResponse.getErrorCode());
			history.setErrorMsg(settlementResponse.getErrorMsg());
			history.setIsSuccess(false);
		}
		return history;
	}
	
	public static RequestMoney getRequestMoney(RequestMoneyDto requestMoneyDto, Long requesterId, Long responserId){	
		RequestMoney receiveMoney = new RequestMoney();
		if(requestMoneyDto != null){
				receiveMoney.setAmount(requestMoneyDto.getAmount());
				receiveMoney.setRequestDate(requestMoneyDto.getRequestDate());
				receiveMoney.setCurrencyId(requestMoneyDto.getCurrencyId());
				receiveMoney.setRequesterMsg(requestMoneyDto.getRequesterMsg());
				receiveMoney.setResponseDate(requestMoneyDto.getResponseDate());
				receiveMoney.setResponserMsg(requestMoneyDto.getResponserMsg());
				receiveMoney.setStatus(requestMoneyDto.getStatus());	
				receiveMoney.setRequesterId(requesterId);
				receiveMoney.setResponserId(responserId);
				receiveMoney.setAttempts(WalletTransactionConstants.RECEIVEMONEY_ATTEMPTS);
		}
		return receiveMoney;
	}
	
	public static void getRequestMoney(RequestMoney requestMoney, WalletTransaction transaction){	
		transaction.setPayerAmount(requestMoney.getAmount());
		transaction.setPayee(requestMoney.getRequesterId());
		transaction.setPayer(requestMoney.getResponserId());
		transaction.setPayeeCurrency(requestMoney.getCurrencyId());
		transaction.setPayerCurrency(requestMoney.getCurrencyId());
		transaction.setTypeOfTransaction(WalletTransactionTypes.P_TO_P);
	}
	
	public static void getTransactionFromRequestMoney(RequestMoneyDto requestMoneyDto, WalletTransaction transaction){	
		transaction.setIpAddress(requestMoneyDto.getIpAddress());
		transaction.setPayerAmount(requestMoneyDto.getAmount());
		transaction.setPayeeAmount(requestMoneyDto.getAmount());
		transaction.setPayee(requestMoneyDto.getRequesterId());
		transaction.setPayer(requestMoneyDto.getResponserId());
		transaction.setPayeeCurrency(requestMoneyDto.getCurrencyId());
		transaction.setPayerCurrency(requestMoneyDto.getCurrencyId());
		transaction.setTypeOfTransaction(WalletTransactionTypes.P_TO_P);
		transaction.setTypeOfRequest(requestMoneyDto.getTypeOfRequest());
	}
	
	/**
	 * Sender Fee and Tax
	 * @param feedto
	 * @param tax
	 * @param transaction
	 * @return
	 */
	public static WalletTransaction calculateFeeAndTaxForPayer(FeeSlab matchingFeeSlab, Tax tax, WalletTransaction transaction){
		Double payerFee = 0.0;
		Double payerTax = 0.0;
		
		if(matchingFeeSlab.getPercentageOfSender() != null){
			payerFee = (transaction.getPayerAmount() * matchingFeeSlab.getPercentageOfSender())/PERCENTAGE_FACTOR;
		}
		if(matchingFeeSlab.getFixedChargeSender() != null){
			payerFee += matchingFeeSlab.getFixedChargeSender();
		}
		if(tax != null && !tax.getPercentage().equals(ZERO_DOUBLE)){
			payerTax = (payerFee * tax.getPercentage())/PERCENTAGE_FACTOR;
		}
		
		/*Populate fee and tax amount*/
		transaction.setPayerFee(payerFee);
		transaction.setPayerTax(payerTax);
		return transaction;
	}
	
	/**
	 * Receiver Fee and Tax
	 * @param feedto
	 * @param tax
	 * @param transaction
	 * @return
	 */
	public static WalletTransaction calculateFeeAndTaxForPayee(FeeSlab matchingFeeSlab, Tax tax, WalletTransaction transaction){
		Double payeeFee = ZERO_DOUBLE;
		Double payeeTax = ZERO_DOUBLE;
		Double amount = ZERO_DOUBLE;
		if(transaction.getConvertionRate() != null){
			amount = transaction.getPayerAmount() * transaction.getConvertionRate();
		}else{
			amount = transaction.getPayerAmount();
		}
		
		if(matchingFeeSlab.getPercentageOfReceiver() != null){
			payeeFee = amount * matchingFeeSlab.getPercentageOfReceiver()/PERCENTAGE_FACTOR;
		}
		if(matchingFeeSlab.getFixedChargeReceiver() != null){
			payeeFee += matchingFeeSlab.getFixedChargeReceiver();
		}
		if(tax != null && !tax.getPercentage().equals(ZERO_DOUBLE)){
			payeeTax = (payeeFee * tax.getPercentage())/PERCENTAGE_FACTOR;
		}
		/*Populate fee and tax amount*/
		transaction.setPayeeFee(payeeFee);
		transaction.setPayeeTax(payeeTax);
		
		return transaction;
	}
	
	public static SendMoney getSendMoney(SendMoneyDto sendMoneyDto){
		
		SendMoney sendMoneyToSingle = null;
		if(sendMoneyDto != null){
			sendMoneyToSingle = new SendMoney();
			sendMoneyToSingle.setIpAddress(sendMoneyDto.getIpAddress());
			sendMoneyToSingle.setSimNumber(sendMoneyDto.getSimNumber());
			sendMoneyToSingle.setImeiNumber(sendMoneyDto.getImeiNumber());
			sendMoneyToSingle.setTypeOfRequest(sendMoneyDto.getTypeOfRequest());
			sendMoneyToSingle.setReceiverMail(sendMoneyDto.getEmailId());
			sendMoneyToSingle.setAmount(sendMoneyDto.getRequestedAmount());
			sendMoneyToSingle.setCurrency(sendMoneyDto.getRequestedCurrency());
			sendMoneyToSingle.setTransactionType(sendMoneyDto.getTransactionType());
			sendMoneyToSingle.setMessage(sendMoneyDto.getMessage());
			sendMoneyToSingle.setReceiverType(sendMoneyDto.getDestinationType());
			sendMoneyToSingle.setCountryId(sendMoneyDto.getCountryId());
			if (null == sendMoneyDto.getRecurring()) {
				sendMoneyDto.setRecurring(false);
			}
			sendMoneyToSingle.setRecurring(sendMoneyDto.getRecurring());
			sendMoneyToSingle.setSenderAuthId(sendMoneyDto.getSenderAuthId());
			sendMoneyToSingle.setReceiverAuthId(sendMoneyDto.getReceiverAuthId());
			sendMoneyToSingle.setSenderName(sendMoneyDto.getPayerName());
			sendMoneyToSingle.setReceiverName(sendMoneyDto.getPayeeName());
			sendMoneyToSingle.setLanguageId(sendMoneyDto.getLanguageId());
			sendMoneyToSingle.setCurrencyCode(sendMoneyDto.getCurrencyCode());
			sendMoneyToSingle.setRequestDate(new Date());
		}
		return sendMoneyToSingle;
	}
	
	public static SendMoneyTxn getSendMoneyTxn(SendMoneyDto sendMoneyDto, Long transactionId, Long transactionStatus){
		
		SendMoneyTxn sendMoneyTxn = null;
		if(sendMoneyDto != null){
			sendMoneyTxn = new SendMoneyTxn();
			sendMoneyTxn.setSendMoneyId(sendMoneyDto.getId());
			sendMoneyTxn.setTransactionId(transactionId);
			sendMoneyTxn.setTransactionStatus(transactionStatus);
			sendMoneyTxn.setOccurences(1);
			sendMoneyTxn.setTriggerDate(new Date());
		}
		return sendMoneyTxn;
	}
	
	public static SendMoneyTxn getSendMoneyTxn(Integer occurences, Long transactionId, Long transactionStatus){
		SendMoneyTxn sendMoneyTxn = null;
		sendMoneyTxn = new SendMoneyTxn();
		sendMoneyTxn.setOccurences(occurences);
		sendMoneyTxn.setTransactionId(transactionId);
		sendMoneyTxn.setTransactionStatus(transactionStatus);
		sendMoneyTxn.setTriggerDate(new Date());
		return sendMoneyTxn;
	}
	
	public static SendMoneyRecurring getSendMoneyRecurring(SendMoneyDto sendMoneyDto, Long sendMoneyId){
		SendMoneyRecurring sendMoneyJob = null;
		if(sendMoneyDto.getRecurring()){
			sendMoneyJob = new SendMoneyRecurring();
			sendMoneyJob.setUserJobName(sendMoneyDto.getUserJobName());
			sendMoneyJob.setFromDate(sendMoneyDto.getFromDate());
			sendMoneyJob.setToDate(sendMoneyDto.getToDate());
			sendMoneyJob.setFrequency(sendMoneyDto.getFrequency());
			sendMoneyJob.setTotalOccurences(sendMoneyDto.getTotalOccurences());
			sendMoneyJob.setSendMoneyId(sendMoneyId);
		}
		return sendMoneyJob;
	}
	
	/*public static List<SendMoney> getSendMoneyToMultiple(List<SendMoneyDto> listofTrans){
		List<SendMoney> list = new ArrayList<SendMoney>();
		SendMoney sendMoney = null;
		for (int i = 0; i < listofTrans.size(); i++) {
			sendMoney = new SendMoney();
			SendMoneyDto sendMoneyMultipleDto = listofTrans.get(i);
			sendMoney = new SendMoney();
			sendMoney.setReceiverMail(sendMoneyMultipleDto.getEmailId());
			sendMoney.setAmount(sendMoneyMultipleDto.getAmount());
			sendMoney.setCurrency(sendMoneyMultipleDto.getCurrency());
			sendMoney.setMessage(sendMoneyMultipleDto.getMessage());
			sendMoney.setReceiverType(sendMoneyMultipleDto.getDestinationType());
			// sendMoneyToSingle.setRecurring(sendMoneyMultipleDto.getRecurring());
			// sendMoneyToSingle.setFromDate(sendMoneyMultipleDto.getFromDate());
			// sendMoneyToSingle.setToDate(sendMoneyMultipleDto.getToDate());
			// sendMoneyToSingle.setFrequency(sendMoneyMultipleDto.getFrequency());
			// sendMoneyToSingle.setTotalOccurences(sendMoneyMultipleDto.getTotalOccurences());
			sendMoney.setFromDate("2/3/13");
			sendMoney.setSenderAuthId(sendMoneyMultipleDto.getSenderAuthId());
			sendMoney.setReceiverAuthId(sendMoneyMultipleDto.getReceiverAuthId());
			list.add(sendMoney);
		}
		return list;
	}*/

	public static WalletTransaction constructTransaction(SendMoneyDto sendMoneyDto) {

		WalletTransaction transaction = new WalletTransaction();
		transaction.setIpAddress(sendMoneyDto.getIpAddress());
		//payer auth id
		transaction.setPayer(sendMoneyDto.getSenderAuthId());
		//payee amount
		transaction.setPayerAmount(sendMoneyDto.getActualAmount());
		//payer amount
		transaction.setPayeeAmount(sendMoneyDto.getRequestedAmount());
		//payee currency id
		transaction.setPayeeCurrency(sendMoneyDto.getRequestedCurrency());
		//payer currency id
		transaction.setPayerCurrency(sendMoneyDto.getActualCurrency());
		//P_TO_P,P_TO_NP...
		transaction.setTypeOfTransaction(sendMoneyDto.getTransactionType());
		transaction.setTypeOfRequest(sendMoneyDto.getTypeOfRequest());
		return transaction;
	}
	
	public static WalletTransaction constructTransaction(SendMoney sendMoney) {

		WalletTransaction transaction = new WalletTransaction();
		transaction.setIpAddress(sendMoney.getIpAddress());
		//payer auth id
		transaction.setPayer(sendMoney.getSenderAuthId());
		//payee amount
		transaction.setPayerAmount(sendMoney.getAmount());
		//payer amount
		transaction.setPayeeAmount(sendMoney.getAmount());
		//payee currency id
		transaction.setPayeeCurrency(sendMoney.getCurrency());
		//payer currency id
		transaction.setPayerCurrency(sendMoney.getCurrency());
		//P_TO_P,P_TO_NP...
		transaction.setTypeOfTransaction(sendMoney.getTransactionType());
		transaction.setTypeOfRequest(sendMoney.getTypeOfRequest());
		return transaction;
	}
	
	public static NonRegisterWallet prepareNonRegisterWallet(SendMoneyDto sendMoneyDto, Long txnId){
		
		NonRegisterWallet nonRegisterWallet=new NonRegisterWallet();
		nonRegisterWallet.setAmount(sendMoneyDto.getRequestedAmount());
		nonRegisterWallet.setCreationDate(new Date());
		nonRegisterWallet.setCurrency(sendMoneyDto.getRequestedCurrency());
		nonRegisterWallet.setEmail(sendMoneyDto.getEmailId());
		nonRegisterWallet.setTxnId(txnId);
		nonRegisterWallet.setRegister(WalletTransactionStatus.PENDING);
		return nonRegisterWallet;
	}
	
	public static NonRegisterWallet prepareNonRegisterWallet(SendMoney sendMoney, Long txnId){
		
		NonRegisterWallet nonRegisterWallet=new NonRegisterWallet();
		nonRegisterWallet.setAmount(sendMoney.getAmount());
		nonRegisterWallet.setCreationDate(new Date());
		nonRegisterWallet.setCurrency(sendMoney.getCurrency());
		nonRegisterWallet.setEmail(sendMoney.getReceiverMail());
		nonRegisterWallet.setTxnId(txnId);
		nonRegisterWallet.setRegister(WalletTransactionStatus.PENDING);
		return nonRegisterWallet;
	}
	
	public static RequestMoneyDto getReceiveMoneyDtoFromModelObject(RequestMoney receiveMoney){	
		RequestMoneyDto dto = new RequestMoneyDto();
		if(receiveMoney != null){
			dto.setId(receiveMoney.getId());
			dto.setResponserId(receiveMoney.getResponserId());
			dto.setRequesterId(receiveMoney.getRequesterId());
			dto.setCurrencyId(receiveMoney.getCurrencyId());
			dto.setAmount(receiveMoney.getAmount());
			dto.setRequestDate(receiveMoney.getRequestDate());
			dto.setRequesterMsg(receiveMoney.getRequesterMsg());
			dto.setResponseDate(receiveMoney.getResponseDate());
			dto.setResponserMsg(receiveMoney.getResponserMsg());
			dto.setStatus(receiveMoney.getStatus());	
			dto.setAttempts(receiveMoney.getAttempts());
			dto.setTransactionId(receiveMoney.getTransactionId());
			dto.setCountryId(receiveMoney.getCountryId());
		}
		return dto;
	}
	
	public static WalletTransaction constructTransactionForSelf(SelfTransferDto selfTransferDto) {
		Long authId = selfTransferDto.getAuthId();
		WalletTransaction transaction=new WalletTransaction();
		transaction.setIpAddress(selfTransferDto.getIpAddress());
		transaction.setPayer(authId);
		transaction.setPayee(authId);
		transaction.setPayerAmount(Double.parseDouble(selfTransferDto.getActualAmount()));
		transaction.setPayeeAmount(Double.parseDouble(selfTransferDto.getRequestedAmount()));
		transaction.setPayeeCurrency(selfTransferDto.getRequestedCurrency());
		transaction.setPayerCurrency(selfTransferDto.getActualCurrency());
		transaction.setTypeOfTransaction(selfTransferDto.getTransactionType());
		transaction.setTypeOfRequest(selfTransferDto.getTypeOfRequest());
		transaction.setSimNumber(selfTransferDto.getSimNumber());
		transaction.setImeiNumber(selfTransferDto.getImeiNumber());
		return transaction;
	}
	
	public static List<History> getDtoObjectsForTxnGrid(List<Object[]> list){

		List<History> historyList = new ArrayList<History>();
		History history = null;
		for(Object[] objects: list){
			history = new History();
			history.setId(Long.parseLong(objects[0].toString()));
			history.setAmount(Double.parseDouble(objects[1].toString()));
			historyList.add(history);
		}
		return historyList;
	}
	
	
	public static String convertFromScientificNotation(double number) {
	    // Check if in scientific notation
	    if (String.valueOf(number).toLowerCase().contains("e")) {
	        System.out.println("The scientific notation number'"
	                + number
	                + "' detected, it will be converted to normal representation with 25 maximum fraction digits.");
	        NumberFormat formatter = new DecimalFormat();
	        formatter.setMaximumFractionDigits(2);
	        return formatter.format(number);
	    } else
	        return String.valueOf(number);
	}
}
