/**
 * 
 */
package com.tarang.ewallet.accounts.business.impl;

import java.util.List;

import com.tarang.ewallet.accounts.business.AccountsService;
import com.tarang.ewallet.accounts.repository.AccountsRepository;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Bank;
import com.tarang.ewallet.model.Card;


/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:21:39 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class AccountsServiceImpl implements AccountsService {

	private AccountsRepository accountsRepository;

	public AccountsServiceImpl(AccountsRepository accountsRepository){
		this.accountsRepository = accountsRepository;	
	}
	
	@Override
	public AccountDto createAccount(AccountDto account) throws WalletException {
		return accountsRepository.createAccount(account);
	}

	@Override
	public Integer getAccountsCount(Long authId) throws WalletException {
		return accountsRepository.getAccountsCount(authId);
	}
	
	@Override
	public AccountDto getAccount(Long id) throws WalletException {
		return accountsRepository.getAccount(id);
	}

	@Override
	public AccountDto updateStatus(Long id, Long status, Long typeOfRequest) throws WalletException {
		return accountsRepository.updateStatus(id, status, typeOfRequest);
	}

	@Override
	public AccountDto updateExpiryDate(Long id, String expDate) throws WalletException {
		return accountsRepository.updateExpiryDate(id, expDate);
	}

	@Override
	public AccountDto updateAccount(AccountDto dto) throws WalletException {
		return accountsRepository.updateAccount(dto);
	}

	@Override
	public Boolean delete(Long id, Long typeOfrequest) throws WalletException {
		return accountsRepository.delete(id, typeOfrequest);
	}

	@Override
	public List<AccountDto> getAllAccounts(Long authId) throws WalletException {
		return accountsRepository.getAllAccounts(authId);
	}

	@Override
	public List<AccountDto> getAccounts(Long authId) throws WalletException {
		return accountsRepository.getAccounts(authId);
	}
	
	@Override
	public List<AccountDto> getCardAccounts(Long authId) throws WalletException {
		return accountsRepository.getCardAccounts(authId);
	}
	
	@Override
	public Boolean setAsDefaultAccount(Long authId, Long id) throws WalletException {
		return accountsRepository.setAsDefaultAccount(authId, id);
	}

	@Override
	public AccountDto getDefaultAccount(Long authId) throws WalletException {
		return accountsRepository.getDefaultAccount(authId);
	}
	@Override
	public Card getCardDeletedStatus(Long authId, String cardNumber, Long deletedStatus) throws WalletException{
		return accountsRepository.getCardDeletedStatus(authId, cardNumber, deletedStatus);
	}
	
	@Override
	public Bank getBankDeletedStatus(Long authId, String accountNumber, 
			String sortCode, Long deletedStatus) throws WalletException{
		return accountsRepository.getBankDeletedStatus(authId, accountNumber, sortCode, deletedStatus);
	}

	@Override
	public AccountDto varifyCard(AccountDto dto) throws WalletException {
		return accountsRepository.varifyCard(dto);
		
	}

	@Override
	public Boolean isExistBankAccount(String accountNumber, String sortCode, String userType)
			throws WalletException {
		return accountsRepository.isExistBankAccount(accountNumber, sortCode, userType);
	}

	@Override
	public Boolean bankAccountExistWithSameUser(String accountNumber, String sortCode, Long authId, Long deletedStatus) throws WalletException {
		return accountsRepository.bankAccountExistWithSameUser(accountNumber, sortCode, authId, deletedStatus);
	}

	@Override
	public Boolean cardAccountExistWithSameUser(String cardNumber, Long authId, Long deletedStatus) throws WalletException {
		return accountsRepository.cardAccountExistWithSameUser(cardNumber, authId, deletedStatus);
	}

	@Override
	public Integer getTotalBankAccouts(String accountNumber, String sortCode, String userType, Long deletedStatus) throws WalletException {
		return accountsRepository.getTotalBankAccouts(accountNumber, sortCode, userType, deletedStatus);
	}

	@Override
	public Integer getTotalCardAccounts(String cardNumber, String userType, Long deletedStatus) throws WalletException {
		return accountsRepository.getTotalCardAccounts(cardNumber, userType, deletedStatus);
	}
	
}
