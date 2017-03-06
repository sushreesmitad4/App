package com.tarang.ewallet.transaction.repository.impl;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.transaction.dao.FraudCheckDao;
import com.tarang.ewallet.transaction.repository.FraudCheckRepository;
import com.tarang.ewallet.transaction.util.IpNumberGenerator;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;


public class FraudCheckRepositoryImpl implements FraudCheckRepository, GlobalLitterals{

	private FraudCheckDao fraudCheckDao;
	
	private UtilService utilService;
	
	public FraudCheckRepositoryImpl(FraudCheckDao fraudCheckDao, UtilService utilService) {
		this.fraudCheckDao = fraudCheckDao;
		this.utilService = utilService;
	}

	@Override
	public Boolean ipMultipleAccountsVelocity(String ipAddress,	Date fromDate,	Date toDate, Integer noOfAccounts) throws WalletException{
		return fraudCheckDao.ipMultipleAccountsVelocity(ipAddress, fromDate, toDate, noOfAccounts);
	}

	@Override
	public Boolean isLoginFromDifferentIp(String ipAddress, Long authId) throws WalletException{
		 List<String> ipAddressList = fraudCheckDao.getIpAddressByAuthId(authId);
		 if(ipAddressList.size() >= 1){
			 Long ipNumber1 = IpNumberGenerator.getIpNumber(ipAddressList.get(0));
			 Long ipNumber2 = IpNumberGenerator.getIpNumber(ipAddress);
			 return fraudCheckDao.isLoginFromDifferentIp(ipNumber1, ipNumber2); 
		 } else{
			 return false;
		 }
	}

	@Override
	public Boolean emailPatternCheck(String email,	Date fromDate, Date toDate,Integer noOfAccounts) throws WalletException{
		return fraudCheckDao.emailPatternCheck(email.substring(0, utilService.getEmailPatternLength()), 
				email.substring( email.indexOf(AT_THE_RATE) + 1), fromDate, toDate, noOfAccounts); 
	}

	@Override
	public Boolean fundingSourcePatternCheck(String bankCode, Date fromDateBank, Date toDateBank, Integer noOfAccountsBank) throws WalletException{
		return fraudCheckDao.fundingSourcePatternCheck(bankCode, fromDateBank, toDateBank, noOfAccountsBank);
	}

	@Override
	public Boolean fundingSourcePatternCheckCard(String cardCode, Date fromDateCard, Date toDateCard, Integer noOfAccountsCard) throws WalletException {
		return fraudCheckDao.fundingSourcePatternCheckCard(cardCode, fromDateCard, toDateCard, noOfAccountsCard);
	}
	
	@Override
	public Boolean ipCardBinGeoCheck(String ipAddress, String cardBin) throws WalletException{
		return fraudCheckDao.ipCardBinGeoCheck(IpNumberGenerator.getIpNumber(ipAddress), cardBin);
	}

	@Override
	public Boolean domainCheck(String email) throws WalletException{
		return fraudCheckDao.domainCheck(email, email.substring( email.indexOf(AT_THE_RATE) + 1));
	}

	@Override
	public Boolean merchantThresholdCheck(Long merchantAuthId, Integer noOfdispute, Date fromDate, Date toDate)throws WalletException {
		return fraudCheckDao.merchantThresholdCheck(merchantAuthId, noOfdispute, fromDate, toDate);
	}

}
