package com.tarang.ewallet.transaction.business.impl;

import java.util.Date;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.transaction.business.FraudCheckService;
import com.tarang.ewallet.transaction.repository.FraudCheckRepository;


/**
 * All the fraud check service methods returns boolean values, if requested data exist(satisfies) in data base 
 * returning 'TRUE' else 'FALSE'. Here 'TRUE' means check failed and 'FALSE' means check passed.
 * 
 * @Date : 21 Mar 2013
 * @Time : 11:30:50 AM
 * @Version : 1.0
 * @author rajasekhar.s
 */

public class FraudCheckServiceImpl implements FraudCheckService{

	private FraudCheckRepository fraudCheckRepository;
	
	public FraudCheckServiceImpl(FraudCheckRepository fraudCheckRepository) {
		this.fraudCheckRepository = fraudCheckRepository;
	}

	@Override
	public Boolean ipMultipleAccountsVelocity(String ipAddress,	Date fromDate,	Date toDate, Integer noOfAccounts)throws WalletException {

		return fraudCheckRepository.ipMultipleAccountsVelocity(ipAddress, fromDate, toDate, noOfAccounts);
	}

	@Override
	public Boolean isLoginFromDifferentIp(String ipAddress, Long authId) throws WalletException{

		return fraudCheckRepository.isLoginFromDifferentIp(ipAddress, authId);
	}

	@Override
	public Boolean emailPatternCheck(String email, Date fromDate,	Date toDate, Integer noOfAccounts) throws WalletException{

		return fraudCheckRepository.emailPatternCheck(email, fromDate, toDate, noOfAccounts);
	}

	@Override
	public Boolean fundingSourcePatternCheck(String bankCode, Date fromDateBank, Date toDateBank, Integer noOfAccountsBank) throws WalletException{

		return fraudCheckRepository.fundingSourcePatternCheck(bankCode, fromDateBank, toDateBank, noOfAccountsBank);
	}
	
	@Override
	public Boolean fundingSourcePatternCheckCard(String cardCode, Date fromDateCard, Date toDateCard, Integer noOfAccountsCard)throws WalletException{
		
		return fraudCheckRepository.fundingSourcePatternCheckCard(cardCode, fromDateCard, toDateCard, noOfAccountsCard);
	}
	
	@Override
	public Boolean ipCardBinGeoCheck(String ipAddress, String cardBin) throws WalletException{

		return fraudCheckRepository.ipCardBinGeoCheck(ipAddress, cardBin);
	}

	@Override
	public Boolean domainCheck(String email) throws WalletException{
		
		return fraudCheckRepository.domainCheck(email);
	}

	@Override
	public Boolean merchantThresholdCheck(Long merchantAuthId, Integer noOfdispute, Date fromDate, Date toDate) throws WalletException{

		return fraudCheckRepository.merchantThresholdCheck(merchantAuthId, noOfdispute, fromDate, toDate);
	}

}
