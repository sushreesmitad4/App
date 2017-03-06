/**
 * 
 */
package com.tarang.ewallet.dispute.dao;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.dto.DisputeGridDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Dispute;
import com.tarang.ewallet.model.DisputeMessage;


/**
 * @author vasanthar
 *
 */
public interface DisputeDao {
	
	Dispute createDispute(Dispute dispute) throws WalletException;
	
	DisputeMessage addMessage(DisputeMessage message) throws WalletException;
	
	Dispute updateDispute(Dispute dispute) throws WalletException;
	
	Dispute getDisputeById(Long id) throws WalletException; 
	
	List<DisputeGridDto> getDisputesForCustomer(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId,  Long disputeType) throws WalletException;
	
	List<DisputeGridDto> getDisputesForMerchant(Integer limit, Long languageId, Date fromDate, Date toDate, Long payerId, Long payeeId, Long disputeType) throws WalletException;
	
	List<DisputeGridDto> getDisputesForAdmin(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType) throws WalletException;
	
	List<DisputeGridDto> getCustomerTxnsForRaisedispute(Integer limit, Date fromDate, Date toDate, Long payeeId, Long payerId, Long typeOfTransaction) throws WalletException;
	
	List<DisputeDto> getCustomerRaiseOrUpdateDispute(Long languageId, Long txnId) throws WalletException;
	
	List<DisputeDto> getAdmineOrMerchantUpdateDispute(Long languageId, Long txnId) throws WalletException;
	
	List<Long> getMerchantToPayStatusDisputeIds(Long status, Date date)throws WalletException;
	
	Boolean isDisputeExistForTxnId(Long txnid)throws WalletException;
	
	Integer getDisputeCountForAccClose(Long authenticationId);

	Integer getActiveDisputeForMerchant(Long authenticationId);

}
