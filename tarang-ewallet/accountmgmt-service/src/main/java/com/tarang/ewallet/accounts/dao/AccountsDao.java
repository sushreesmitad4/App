/**
 * 
 */
package com.tarang.ewallet.accounts.dao;

import java.util.List;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Account;
import com.tarang.ewallet.model.Bank;
import com.tarang.ewallet.model.Card;


/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:01:53 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface AccountsDao {

	Account createAccount(Account account) throws WalletException;

	Account getAccount(Long id) throws WalletException;
	
	Integer getAccountsCount(Long authId) throws WalletException;
	
	Account updateAccount(Account account) throws WalletException;
	
	Boolean permanentDelete(Account account) throws WalletException;
	
	Boolean softDelete(Account account) throws WalletException;

	List<Account> getAccounts(Long authId) throws WalletException;
	
	List<Account> getAllAccounts(Long authId) throws WalletException;
	
	List<Account> getCardAccounts(Long authId) throws WalletException;

	Account getDefaultAccount(Long authId) throws WalletException;
	
	Card getCardDeletedStatus(Long authId, String cardNumber, Long deletedStatus) throws WalletException;

	Bank getBankDeletedStatus(Long authId, String accountNumber, String sortCode, Long deletedStatus) throws WalletException;
	
	Boolean isExistBankAccount(String accountNumber, String sortCode, String userType) throws WalletException;
	
	Boolean bankAccountExistWithSameUser(String accountNumber, String sortCode, Long authId, Long deletedStatus) throws WalletException;
	
	Boolean cardAccountExistWithSameUser(String cardNumber, Long authId, Long deletedStatus) throws WalletException;
	
	Integer getTotalBankAccouts(String accountNumber, String sortCode, String userType, Long deletedStatus) throws WalletException;
	
	Integer getTotalCardAccounts(String cardNumber, String userType, Long deletedStatus ) throws WalletException;

	
}
