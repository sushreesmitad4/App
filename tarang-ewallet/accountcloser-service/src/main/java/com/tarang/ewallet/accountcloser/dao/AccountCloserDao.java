package com.tarang.ewallet.accountcloser.dao;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AccountCloser;
import com.tarang.ewallet.model.AccountCloserMessage;


public interface AccountCloserDao {

	AccountCloser createAccountCloser(AccountCloser accountCloser) throws WalletException;
	
	AccountCloserMessage addMessage(AccountCloserMessage message) throws WalletException;
	
	AccountCloser getAccountCloserByUser(Long authId) throws WalletException;

	AccountCloser getAccountCloserById(Long id) throws WalletException;
	
	List<AccountCloserDto> getAccountCloserList(Integer noOfRecords, Long languageId, Date fromDate, Date toDate, String userType) throws WalletException;
	
	AccountCloserDto getAccountCloserForView(Long accountCloserId, Long languageId) throws WalletException;

	/*AccountCloser updateAccountCloser(AccountCloser accountCloser)throws WalletException;*/
	
	Boolean getAccountCloserStatusById(Long authId) throws WalletException;
	
	List<Long> getApprovalAccountClosers(Long status, Date date)throws WalletException;
}
