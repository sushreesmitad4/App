package com.tarang.ewallet.transaction.dao;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.exception.WalletException;


public interface FraudCheckDao {
	
	Boolean ipMultipleAccountsVelocity( String ipAddress, Date fromDate, Date toDate, Integer noOfAccounts)throws WalletException;
	
	List<String> getIpAddressByAuthId(Long authId)throws WalletException;
	
	Boolean isLoginFromDifferentIp(Long ipNumber1, Long ipNumber2)throws WalletException;
	
	Boolean emailPatternCheck(String emailFirstFiveChars, String domain,  Date fromDate, Date toDate, Integer noOfAccounts)throws WalletException;
	
	Boolean fundingSourcePatternCheck(String bankCode, Date fromDateBank, Date toDateBank, Integer noOfAccountsBank)throws WalletException;
	
	Boolean fundingSourcePatternCheckCard(String cardCode, Date fromDateCard, Date toDateCard, Integer noOfAccountsCard)throws WalletException;
	
	Boolean ipCardBinGeoCheck(Long ipNumber, String cardBin)throws WalletException;
	
	Boolean domainCheck(String email, String domain)throws WalletException;
	
	Boolean merchantThresholdCheck(Long merchantAuthId, Integer noOfdispute, Date fromDate, Date toDate)throws WalletException;
}
