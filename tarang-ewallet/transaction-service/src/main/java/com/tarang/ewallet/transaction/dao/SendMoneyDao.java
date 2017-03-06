package com.tarang.ewallet.transaction.dao;

import java.util.List;

import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.SendMoney;
import com.tarang.ewallet.model.SendMoneyRecurring;
import com.tarang.ewallet.model.SendMoneyTxn;


public interface SendMoneyDao {
	
	SendMoney createSendMoney(SendMoney sendMoney)throws WalletException;
	
	SendMoney getSendMoney(Long id)throws WalletException;
	
	SendMoney updateSendMoney(SendMoney sendMoney)throws WalletException;

	List<SendMoneyDto> getReceiveMoneyList(Long receiverAuthId);
	
	void updateSendMoney(Long id, Long txnId, Long txnStatus)throws WalletException;

	SendMoneyTxn createSendMoneyTxn(SendMoneyTxn sendMoneyTxn)throws WalletException;

	SendMoneyRecurring createSendMoneyRecurring(SendMoneyRecurring sendMoneyJob)throws WalletException;

	void updateSendMoneyTxn(Long id, Long transactionId, Long transactionStatus)throws WalletException;

	SendMoneyTxn getSendMoneyTxn(Long transactionId)throws WalletException;

	SendMoneyTxn updateSendMoneyTxn(SendMoneyTxn sendMoneyTxn)throws WalletException;
	
	SendMoneyRecurring getSendMoneyRecurringBySendMoneyId(Long sendMoneyId)throws WalletException;
	
	SendMoneyRecurring updateSendMoneyRecurring(SendMoneyRecurring sendMoneyRecurring)throws WalletException;
	
	Boolean isJobNameExist(Long senderAuthId, String jobName);
	
}
