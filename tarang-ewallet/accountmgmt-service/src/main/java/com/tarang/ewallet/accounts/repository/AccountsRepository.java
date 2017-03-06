/**
 * 
 */
package com.tarang.ewallet.accounts.repository;

import java.util.List;

import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Bank;
import com.tarang.ewallet.model.Card;


/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:02:16 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface AccountsRepository {

	AccountDto createAccount(AccountDto account) throws WalletException;

	AccountDto getAccount(Long id) throws WalletException;
	
	Integer getAccountsCount(Long authId) throws WalletException;
	
	AccountDto updateAccount(AccountDto dto) throws WalletException;
	
	AccountDto updateStatus(Long id, Long status, Long typeOfRequest) throws WalletException;

	AccountDto updateExpiryDate(Long id, String expDate) throws WalletException;
	
	Boolean delete(Long id, Long typeOfRequest) throws WalletException;
	
	List<AccountDto> getAllAccounts(Long authId) throws WalletException;

	List<AccountDto> getAccounts(Long authId) throws WalletException;
	
	List<AccountDto> getCardAccounts(Long authId) throws WalletException;
	
	Boolean setAsDefaultAccount(Long authId, Long id) throws WalletException;
	
	AccountDto getDefaultAccount(Long authId) throws WalletException;

	Card getCardDeletedStatus(Long authId, String cardNumber, Long deletedStatus) throws WalletException;

	Bank getBankDeletedStatus(Long authId, String accountNumber, 
			String sortCode, Long deletedStatus) throws WalletException;

	AccountDto varifyCard(AccountDto dto) throws WalletException;
	
	Boolean isExistBankAccount(String accountNumber, String sortCode, String userType) throws WalletException;
	
	Boolean bankAccountExistWithSameUser(String accountNumber, String sortCode, Long authId, Long deletedStatus) throws WalletException;
	
	Boolean cardAccountExistWithSameUser(String cardNumber, Long authId, Long deletedStatus) throws WalletException;
	
	Integer getTotalBankAccouts(String accountNumber, String sortCode, String userType, Long deletedStatus) throws WalletException;
	
	Integer getTotalCardAccounts(String cardNumber, String userType, Long deletedStatus) throws WalletException;
}
