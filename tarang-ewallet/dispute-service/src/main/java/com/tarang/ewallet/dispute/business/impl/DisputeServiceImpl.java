/**
 * 
 */
package com.tarang.ewallet.dispute.business.impl;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dispute.business.DisputeService;
import com.tarang.ewallet.dispute.repository.DisputeRepository;
import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.dto.DisputeGridDto;
import com.tarang.ewallet.exception.WalletException;


/**
 * @author vasanthar
 *
 */
public class DisputeServiceImpl implements DisputeService{

	private DisputeRepository disputeRepository;
	
	public DisputeServiceImpl(DisputeRepository disputeRepository) {
		this.disputeRepository = disputeRepository;
	}

	@Override
	public DisputeDto createDispute(DisputeDto disputeDto) throws WalletException {
		return disputeRepository.createDispute(disputeDto);
	}

	@Override
	public DisputeDto updateDispute(DisputeDto disputeDto) throws WalletException {
		return disputeRepository.updateDispute(disputeDto);
	}
	
	@Override
	public DisputeDto getDisputeById(Long id) throws WalletException {
		return disputeRepository.getDisputeById(id);
	}

	@Override
	public DisputeDto getDisputeById(Long disputeId, Long languageId) throws WalletException {
		return disputeRepository.getDisputeById(disputeId, languageId);
	}

	@Override
	public List<DisputeGridDto> getDisputesForCustomer(Integer limit, Long languageId, Date fromDate, Date toDate, 
			Long payeeId, Long payerId, Long disputeType)throws WalletException {
		return disputeRepository.getDisputesForCustomer(limit, languageId, fromDate, toDate, payeeId, payerId, disputeType);
	}

	@Override
	public List<DisputeGridDto> getDisputesForMerchant(Integer limit, Long languageId, Date fromDate, Date toDate, Long payerId, 
			Long payeeId, Long disputeType) throws WalletException {
		return disputeRepository.getDisputesForMerchant(limit, languageId, fromDate, toDate, payerId, payeeId, disputeType);
	}

	@Override
	public List<DisputeGridDto> getDisputesForAdmin(Integer limit, Long languageId, Date fromDate,
			Date toDate, Long payeeId, Long payerId, Long disputeType) throws WalletException {
		return disputeRepository.getDisputesForAdmin(limit, languageId, fromDate, toDate, payeeId, payerId, disputeType);
	}

	@Override
	public List<DisputeGridDto> getCustomerTxnsForRaisedispute(Integer limit,Date fromDate, Date toDate,
			Long payeeId, Long payerId, Long typeOfTransaction) throws WalletException {
		return disputeRepository.getCustomerTxnsForRaisedispute(limit, fromDate, toDate, payeeId, payerId, typeOfTransaction);
	}

	@Override
	public List<Long> getMerchantToPayStatusDisputeIds(Long status, Date date)
			throws WalletException {
		return disputeRepository.getMerchantToPayStatusDisputeIds(status, date);
	}
	@Override
	public Boolean isDisputeExistForTxnId(Long txnid)throws WalletException {
		return disputeRepository.isDisputeExistForTxnId(txnid);
	}
	
	/*
	@Override
	public List<DisputeDto> getCustomerRaiseOrUpdateDispute(Long languageId, Long txnId) throws WalletException {
		return disputeRepository.getCustomerRaiseOrUpdateDispute(languageId, txnId);
	}

	@Override
	public List<DisputeDto> getAdmineOrMerchantUpdateDispute(Long languageId, Long txnId) throws WalletException {
		return disputeRepository.getAdmineOrMerchantUpdateDispute(languageId, txnId);
	}	
	*/
	@Override
	public Integer getDisputeCountForAccClose(Long authenticationId)throws WalletException{
		return disputeRepository.getDisputeCountForAccClose(authenticationId);
	}

	@Override
	public Integer getActiveDisputeForMerchant(Long authenticationId)throws WalletException {
		return disputeRepository.getActiveDisputeForMerchant(authenticationId);
	}

}
