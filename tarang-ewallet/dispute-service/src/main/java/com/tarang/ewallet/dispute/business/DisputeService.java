/**
 * 
 */
package com.tarang.ewallet.dispute.business;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.dto.DisputeGridDto;
import com.tarang.ewallet.exception.WalletException;


/**
 * @author vasanthar
 *
 */
public interface DisputeService {
	
	DisputeDto createDispute(DisputeDto disputeDto) throws WalletException;
	
	DisputeDto updateDispute(DisputeDto disputeDto) throws WalletException;
	
	DisputeDto getDisputeById(Long id) throws WalletException; 
	
	DisputeDto getDisputeById(Long disputeId, Long languageId) throws WalletException; 
	
	List<DisputeGridDto> getDisputesForCustomer(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType) throws WalletException;
	
	List<DisputeGridDto> getDisputesForMerchant(Integer limit, Long languageId, Date fromDate, Date toDate, Long payerId, Long payeeId, Long disputeType) throws WalletException;
	
	List<DisputeGridDto> getDisputesForAdmin(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType) throws WalletException;
	
	List<DisputeGridDto> getCustomerTxnsForRaisedispute(Integer limit, Date fromDate, Date toDate, Long payeeId, Long payerId, Long typeOfTransaction) throws WalletException;
	
	List<Long> getMerchantToPayStatusDisputeIds(Long status, Date date)throws WalletException;

	Boolean isDisputeExistForTxnId(Long txnid)throws WalletException;
	
	Integer getDisputeCountForAccClose(Long authenticationId)throws WalletException;
	
	Integer getActiveDisputeForMerchant(Long authenticationId)throws WalletException;
	
	
	
	//List<DisputeDto> getCustomerRaiseOrUpdateDispute(Long languageId, Long txnId) throws WalletException;
	
	//List<DisputeDto> getAdmineOrMerchantUpdateDispute(Long languageId, Long txnId) throws WalletException;
	
	//DisputeDto getCustomerTxnForRaisedispute(Long txn) throws WalletException;

}
