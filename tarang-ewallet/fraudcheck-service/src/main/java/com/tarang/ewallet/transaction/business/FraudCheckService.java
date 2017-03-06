package com.tarang.ewallet.transaction.business;

import java.util.Date;

import com.tarang.ewallet.exception.WalletException;


public interface FraudCheckService {
	
	Boolean ipMultipleAccountsVelocity( String ipAddress, Date fromDate, Date toDate, Integer noOfAccounts) throws WalletException;
	
	Boolean isLoginFromDifferentIp(String ipAddress, Long authId)throws WalletException;
	
	Boolean emailPatternCheck(String email, Date fromDate, Date toDate, Integer noOfAccounts)throws WalletException;
	
	Boolean fundingSourcePatternCheck(String bankCode, Date fromDateBank, Date toDateBank, Integer noOfAccountsBank)throws WalletException;
	
	Boolean fundingSourcePatternCheckCard(String cardCode, Date fromDateCard, Date toDateCard, Integer noOfAccountsCard)throws WalletException;
	
	Boolean ipCardBinGeoCheck(String ipAddress, String cardBin)throws WalletException;

	Boolean domainCheck(String email)throws WalletException;
	
	Boolean merchantThresholdCheck(Long merchantAuthId, Integer noOfdispute, Date fromDate, Date toDate)throws WalletException;

}
