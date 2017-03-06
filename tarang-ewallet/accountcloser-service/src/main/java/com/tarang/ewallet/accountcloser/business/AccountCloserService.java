package com.tarang.ewallet.accountcloser.business;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.exception.WalletException;


public interface AccountCloserService {
	
	AccountCloserDto createAccountCloser(AccountCloserDto accountCloserDto) throws WalletException;
	
	AccountCloserDto addMessage(Long accountCloserId, String message, Long creator)throws WalletException;
	
	AccountCloserDto addMessageByAdmin(Long accountCloserId, String message, Long creator, Long status) throws WalletException;
	
	AccountCloserDto getAccountCloserByUser(Long authId) throws WalletException;

	AccountCloserDto getAccountCloserById(Long id) throws WalletException ;
	
	List<AccountCloserDto> getAccountCloserList(Integer noOfRecords, Long languageId, Date fromDate, Date toDate, String userType) throws WalletException;
	
	AccountCloserDto getAccountCloserForView(Long accountCloserId, Long languageId) throws WalletException ;
	
	Boolean getAccountCloserStatusById(Long authId) throws WalletException;
	
	AccountCloserDto addMessageByScheduler(Long accountCloserId, String message, Long creator, Long status) throws WalletException;

	List<Long> getApprovalAccountClosers(Long status, Date date) throws WalletException;

}
