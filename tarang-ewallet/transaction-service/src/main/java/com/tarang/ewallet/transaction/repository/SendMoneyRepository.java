package com.tarang.ewallet.transaction.repository;

import java.util.List;

import com.tarang.ewallet.dto.SelfTransferDto;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.SendMoneyRecurring;
import com.tarang.ewallet.model.SendMoneyTxn;


public interface SendMoneyRepository {
	
	SendMoneyDto createSendMoney(SendMoneyDto sendMoneyDto)throws WalletException;
	
	List<SendMoneyDto> createSendMoneyToMultiple(List<SendMoneyDto> listofTrans)throws WalletException;
	
	Boolean validateThresholdLimit(SendMoneyDto sendMoneyDto) throws WalletException;
	
	Boolean validateThresholdLimit(Long countryId, Long currencyId, Long transType, Double amount, Long userType) throws WalletException;
	
	Boolean validateThresholdLimitForSelfTransfer(Long countryId, Long currencyId, Long transType, Double amount, Long userType) throws WalletException;
	
	//this is for accept send money transactions request
	SendMoneyDto acceptReceiveMoney(SendMoneyDto sendMoneyDto)throws WalletException;
	
	//this is for reject send money transactions request
	SendMoneyDto rejectReceiveMoney(SendMoneyDto sendMoneyDto)throws WalletException;

	List<SendMoneyDto> getReceiveMoneyList(Long receiverAuthId);
	
	void updateSendMoneyForNonRegisters(Long txnId, Long authId)throws WalletException;
	
	SelfTransferDto createSelfMoney(SelfTransferDto selfTransferDto)throws WalletException;
	
	SendMoneyTxn startSendMoneyRecurring(Long sendMoneyId, Integer occurences)throws WalletException;
	
	SendMoneyDto getSendMoneyById(Long id)throws WalletException;
	
	SendMoneyRecurring updateSendMoneyRecurring(SendMoneyRecurring sendMoneyRecurring)throws WalletException;
	
	Boolean isJobNameExist(Long senderAuthId, String jobName);
	
}
