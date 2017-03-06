package com.tarang.ewallet.accountcloser.business.impl;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.accountcloser.business.AccountCloserService;
import com.tarang.ewallet.accountcloser.repository.AccountCloserRepository;
import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.exception.WalletException;


public class AccountCloserServiceImpl implements AccountCloserService{
	private AccountCloserRepository accountCloserRepository;

	
	public AccountCloserServiceImpl(AccountCloserRepository accountCloserRepository) {
		this.accountCloserRepository = accountCloserRepository;
	}

	@Override
	public AccountCloserDto createAccountCloser(AccountCloserDto accountCloserDto) throws WalletException {
		return accountCloserRepository.createAccountCloser(accountCloserDto);
	}

	@Override
	public AccountCloserDto addMessage(Long accountCloserId, String message, Long creator) throws WalletException {
		return accountCloserRepository.addMessage(accountCloserId, message, creator);
	}
	
	@Override
	public AccountCloserDto addMessageByAdmin(Long accountCloserId, String message, Long creator, Long status) throws WalletException {
		return accountCloserRepository.addMessageByAdmin(accountCloserId, message, creator, status);
	}

	@Override
	public AccountCloserDto getAccountCloserByUser(Long authId) throws WalletException {
		return accountCloserRepository.getAccountCloserByUser(authId);
	}

	@Override
	public AccountCloserDto getAccountCloserById(Long id) throws WalletException {
		return accountCloserRepository.getAccountCloserById(id);
	}

	@Override
	public List<AccountCloserDto> getAccountCloserList(Integer noOfRecords,
			Long languageId, Date fromDate, Date toDate, String userType) throws WalletException {
		return accountCloserRepository.getAccountCloserList(noOfRecords, languageId, fromDate, toDate, userType);
	}

	@Override
	public AccountCloserDto getAccountCloserForView(Long accountCloserId, Long languageId) throws WalletException {
		return accountCloserRepository.getAccountCloserForView(accountCloserId, languageId);
	}

	@Override
	public Boolean getAccountCloserStatusById(Long authId) throws WalletException {
		return accountCloserRepository.getAccountCloserStatusById(authId);
	}

	@Override
	public AccountCloserDto addMessageByScheduler(Long accountCloserId, String message, Long creator, Long status) throws WalletException {
		return accountCloserRepository.addMessageByScheduler(accountCloserId, message, creator, status);
	}

	@Override
	public List<Long> getApprovalAccountClosers(Long status, Date date) throws WalletException {
		return accountCloserRepository.getApprovalAccountClosers(status, date);
	}
	
}
