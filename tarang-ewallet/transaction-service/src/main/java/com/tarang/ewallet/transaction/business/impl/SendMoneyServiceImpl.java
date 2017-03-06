package com.tarang.ewallet.transaction.business.impl;

import java.util.List;

import com.tarang.ewallet.dto.SelfTransferDto;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.SendMoneyRecurring;
import com.tarang.ewallet.model.SendMoneyTxn;
import com.tarang.ewallet.transaction.business.SendMoneyService;
import com.tarang.ewallet.transaction.repository.SendMoneyRepository;


public class SendMoneyServiceImpl implements SendMoneyService{

	private SendMoneyRepository sendMoneyRepository;
	public SendMoneyServiceImpl(SendMoneyRepository sendMoneyRepository){
		this.sendMoneyRepository = sendMoneyRepository;
	}

	@Override
	public SendMoneyDto createSendMoney(SendMoneyDto sendMoneyDto) throws WalletException {
		return sendMoneyRepository.createSendMoney(sendMoneyDto);
	}

	@Override
	public List<SendMoneyDto> createSendMoneyToMultiple(List<SendMoneyDto> listofTrans) throws WalletException {
		return sendMoneyRepository.createSendMoneyToMultiple(listofTrans);
	}

	@Override
	public Boolean validateThresholdLimit(SendMoneyDto sendMoneyDto) throws WalletException {
		return  sendMoneyRepository.validateThresholdLimit(sendMoneyDto);
	}

	@Override
	public Boolean validateThresholdLimit(Long countryId, Long currencyId,
			Long transType, Double amount, Long userType) throws WalletException {
		return sendMoneyRepository.validateThresholdLimit(countryId, currencyId, transType, amount, userType);
		
	}

	@Override
	public Boolean validateThresholdLimitForSelfTransfer(Long countryId,
			Long currencyId, Long transType, Double amount, Long userType) throws WalletException {
		return sendMoneyRepository.validateThresholdLimitForSelfTransfer(countryId, currencyId, transType, amount, userType);
	}
	
	@Override
	public SendMoneyDto acceptReceiveMoney(SendMoneyDto sendMoneyDto) throws WalletException {
		return sendMoneyRepository.acceptReceiveMoney(sendMoneyDto);
	}

	@Override
	public SendMoneyDto rejectReceiveMoney(SendMoneyDto sendMoneyDto) throws WalletException {
		return sendMoneyRepository.rejectReceiveMoney(sendMoneyDto);
	}

	@Override
	public List<SendMoneyDto> getReceiveMoneyList(Long receiverAuthId) {
		return sendMoneyRepository.getReceiveMoneyList(receiverAuthId);
	}

	@Override
	public void updateSendMoneyForNonRegisters(Long txnId, Long authId) throws WalletException {
		sendMoneyRepository.updateSendMoneyForNonRegisters(txnId, authId);
	}

	@Override
	public SelfTransferDto createSelfMoney(SelfTransferDto selfTransferDto) throws WalletException {
		return sendMoneyRepository.createSelfMoney(selfTransferDto);
	}

	@Override
	public SendMoneyTxn startSendMoneyRecurring(Long sendMoneyId, Integer occurences) throws WalletException {
		return sendMoneyRepository.startSendMoneyRecurring(sendMoneyId, occurences);
	}

	@Override
	public SendMoneyDto getSendMoneyById(Long id) throws WalletException {
		return sendMoneyRepository.getSendMoneyById(id);
	}

	@Override
	public SendMoneyRecurring updateSendMoneyRecurring(SendMoneyRecurring sendMoneyRecurring) throws WalletException {
		return sendMoneyRepository.updateSendMoneyRecurring(sendMoneyRecurring);
	}

	@Override
	public Boolean isJobNameExist(Long senderAuthId, String jobName) {
		return sendMoneyRepository.isJobNameExist(senderAuthId, jobName);
	}

}
