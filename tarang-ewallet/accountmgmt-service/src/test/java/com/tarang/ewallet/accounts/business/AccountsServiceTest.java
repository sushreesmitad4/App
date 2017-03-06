package com.tarang.ewallet.accounts.business;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.accounts.business.AccountsService;
import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.accounts.util.FundingAccountStatus;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Bank;
import com.tarang.ewallet.model.Card;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;


/**
 * 
 */

/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:27:02 PM
 * @version : 1.0.0
 * @comments: 
 *
 */

@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class AccountsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private CryptService cryptService; 
	
	@Test
	public void createBankAccountTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);
		Assert.assertNotNull( accountDto );
		Assert.assertNotNull( accountDto.getId() );
		Assert.assertEquals(authId, accountDto.getAuthId());
		Assert.assertEquals(ahn, accountDto.getAccountHolderName());
		Assert.assertEquals(status, accountDto.getStatus());
		Assert.assertEquals(da, accountDto.getDefaultAccount());
		Assert.assertEquals(ds, accountDto.getDeletedStatus());
		Assert.assertEquals(AccountsConstants.BANK_ACCOUNT, accountDto.getAtype());
		Assert.assertEquals(country, accountDto.getCountry());
		Assert.assertEquals(bat, accountDto.getBankAccountType());
		Assert.assertEquals(sortCode, accountDto.getSortCode());
		Assert.assertEquals(bkn, accountDto.getBankName());
		Assert.assertEquals(acn, accountDto.getAccountNumber());
		Assert.assertEquals(bka, accountDto.getBankAddress());
		
	}
	
	
	@Test
	public void createCardAccountTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Abc xyz";
		Long status = FundingAccountStatus.PENDING_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		String cn = "4012888888881881";
		String expd = "10/2013";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		dto.setCvv("1234");
		
		dto.setIsSameAsProfileAddress(false);
		dto.setCountry(1L);
		dto.setAddrOne("abc def");
		dto.setCity("xyz");
		dto.setState(1L);
		dto.setPostalCode("123456");
		
		dto.setUserAgent("userAgent");
		dto.setClientIpAddress("172.30.66.73");
		dto.setTransactionAmount("112000");
		dto.setCurrencyCode("USD");
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);
		Assert.assertNotNull( accountDto );
		Assert.assertNotNull( accountDto.getId() );
		Assert.assertEquals(authId, accountDto.getAuthId());
		Assert.assertEquals(ahn, accountDto.getAccountHolderName());
		
		Assert.assertEquals(da, accountDto.getDefaultAccount());
		Assert.assertEquals(ds, accountDto.getDeletedStatus());
		Assert.assertEquals(AccountsConstants.CARD_ACCOUNT, accountDto.getAtype());
		Assert.assertEquals(cn, accountDto.getCardNumber());
		Assert.assertEquals(expd, accountDto.getCardExpairyDate());
		Assert.assertEquals(dto.getCardNumber().substring(0,6), accountDto.getCardBin());
		
	}
	
	@Test
	public void getBankAccountTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto temp = accountsService.createAccount(dto);
		
		AccountDto accountDto = accountsService.getAccount(temp.getId());
		Assert.assertNotNull( accountDto );
		Assert.assertNotNull( accountDto.getId() );
		Assert.assertEquals(authId, accountDto.getAuthId());
		Assert.assertEquals(ahn, accountDto.getAccountHolderName());
		Assert.assertEquals(status, accountDto.getStatus());
		Assert.assertEquals(da, accountDto.getDefaultAccount());
		Assert.assertEquals(ds, accountDto.getDeletedStatus());
		Assert.assertEquals(AccountsConstants.BANK_ACCOUNT, accountDto.getAtype());
		Assert.assertEquals(country, accountDto.getCountry());
		Assert.assertEquals(bat, accountDto.getBankAccountType());
		Assert.assertEquals(sortCode, accountDto.getSortCode());
		Assert.assertEquals(bkn, accountDto.getBankName());
		Assert.assertEquals(acn, accountDto.getAccountNumber());
		Assert.assertEquals(bka, accountDto.getBankAddress());
		
	}
	
	@Test
	public void getAccountsCountTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto temp = accountsService.createAccount(dto);
		
		AccountDto accountDto = accountsService.getAccount(temp.getId());
		Assert.assertNotNull( accountDto );
		Assert.assertNotNull( accountDto.getId() );
		Assert.assertEquals(authId, accountDto.getAuthId());
		Assert.assertEquals(ahn, accountDto.getAccountHolderName());
		Assert.assertEquals(status, accountDto.getStatus());
		Assert.assertEquals(da, accountDto.getDefaultAccount());
		Assert.assertEquals(ds, accountDto.getDeletedStatus());
		Assert.assertEquals(AccountsConstants.BANK_ACCOUNT, accountDto.getAtype());
		Assert.assertEquals(country, accountDto.getCountry());
		Assert.assertEquals(bat, accountDto.getBankAccountType());
		Assert.assertEquals(sortCode, accountDto.getSortCode());
		Assert.assertEquals(bkn, accountDto.getBankName());
		Assert.assertEquals(acn, accountDto.getAccountNumber());
		Assert.assertEquals(bka, accountDto.getBankAddress());
		
	}

	@Test
	public void getCardAccountTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Abc xyz";
		Long status = FundingAccountStatus.PENDING_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		String cn = "4012888888881881";
		String expd = "11/2020";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		dto.setCvv("1234");
		
		dto.setIsSameAsProfileAddress(false);
		dto.setCountry(1L);
		dto.setAddrOne("abc def");
		dto.setCity("xyz");
		dto.setState(1L);
		dto.setPostalCode("123456");
		
		dto.setUserAgent("userAgent");
		dto.setClientIpAddress("172.30.66.73");
		dto.setTransactionAmount("112000");
		dto.setCurrencyCode("USD");
		//Web
		dto.setTypeOfRequest(2L);
		
		accountsService.createAccount(dto);
		
		Integer count = accountsService.getAccountsCount(authId);
		Integer expCount = 1;
		Assert.assertEquals(expCount, count);
	}
	
	@Test
	public void getAccountsCountTest2() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Abc xyz";
		Long status = FundingAccountStatus.PENDING_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		String cn = "4012888888881881";
		String expd = "11/2020";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		dto.setCvv("1234");
		
		dto.setIsSameAsProfileAddress(false);
		dto.setCountry(1L);
		dto.setAddrOne("abc def");
		dto.setCity("xyz");
		dto.setState(1L);
		dto.setPostalCode("123456");
		
		dto.setUserAgent("userAgent");
		dto.setClientIpAddress("172.30.66.73");
		dto.setTransactionAmount("112000");
		dto.setCurrencyCode("USD");
		//Web
		dto.setTypeOfRequest(2L);
		
		accountsService.createAccount(dto);
		
		Integer count = accountsService.getAccountsCount(authId);
		Integer expCount = 1;
		Assert.assertEquals(expCount, count);
	}
	
	@Test
	public void getAllAccountsTest() throws WalletException {
		
		int total = 20;
		Long authId = 1L;
		for( int i = 0; i < total; i++ ){
			
			String ahn = "Abc xyz";
			Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
			Boolean da = true;
			Long ds = AccountsConstants.NOT_DELETE;
			String Atype = AccountsConstants.CARD_ACCOUNT;
			
			AccountDto dto = new AccountDto();
			dto.setAuthId(authId);
			dto.setAccountHolderName(ahn);
			dto.setStatus(status);
			dto.setDefaultAccount(da);
			dto.setDeletedStatus(ds);
			
			if(i/2*2 == i){
				Atype = AccountsConstants.BANK_ACCOUNT;
				
				Long country = 1L;
				Long bat = 10L;
				String sortCode = "000053" + i;
				String bkn = "ICICI" + i;
				String acn = "0000531234567890" + i;
				String bka = "Jayanagar 3rd Block, Bangalore" + i;
				
				dto.setCountry(country);
				dto.setBankAccountType(bat);
				dto.setSortCode(sortCode);
				dto.setBankName(bkn);
				dto.setAccountNumber(acn);
				dto.setBankAddress(bka);

			}
			else {
				String cn = "401288888888188" + i;
				String expd = "11/20" + i;
				
				dto.setCardNumber(cn);
				dto.setCardExpairyDate(expd);
				dto.setCvv("1234");
				
				dto.setIsSameAsProfileAddress(false);
				dto.setCountry(1L);
				dto.setAddrOne("abc def");
				dto.setCity("xyz");
				dto.setState(1L);
				dto.setPostalCode("123456");
				
				dto.setUserAgent("userAgent");
				dto.setClientIpAddress("172.30.66.73");
				dto.setTransactionAmount("112000");
				dto.setCurrencyCode("USD");
			}
			dto.setAtype(Atype);
			//Web
			dto.setTypeOfRequest(2L);
			
			accountsService.createAccount(dto);
			
		}

		List<AccountDto> dtos = accountsService.getAllAccounts(authId);
		Assert.assertNotNull( dtos );
		Assert.assertEquals( dtos.size(), total );
		
	}
	
	@Test
	public void getNonDeletedAccountsTest() throws WalletException {
		
		int deletedCount = 0;
		int total = 20;
		Long authId = 1L;
		for( int i = 0; i < total; i++ ){
			
			String ahn = "Abc xyz";
			Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
			Boolean da = true;
			Long ds = AccountsConstants.NOT_DELETE;
			String Atype = AccountsConstants.CARD_ACCOUNT;
			
			AccountDto dto = new AccountDto();
			dto.setAuthId(authId);
			dto.setAccountHolderName(ahn);
			dto.setStatus(status);
			dto.setDefaultAccount(da);
			dto.setDeletedStatus(ds);
			
			if(i/3*3 == i){
				dto.setDeletedStatus(AccountsConstants.SOFT_DELETE);
				deletedCount++;
			}
			
			if(i/2*2 == i){
				Atype = AccountsConstants.BANK_ACCOUNT;
				
				Long country = 1L;
				Long bat = 10L;
				String sortCode = "000053" + i;
				String bkn = "ICICI" + i;
				String acn = "0000531234567890" + i;
				String bka = "Jayanagar 3rd Block, Bangalore" + i;
				
				dto.setCountry(country);
				dto.setBankAccountType(bat);
				dto.setSortCode(sortCode);
				dto.setBankName(bkn);
				dto.setAccountNumber(acn);
				dto.setBankAddress(bka);

			}
			else {
				String cn = "401288888888188" + i;
				String expd = "11/20" + i;
				
				dto.setCardNumber(cn);
				dto.setCardExpairyDate(expd);
				dto.setCvv("1234");
				
				dto.setIsSameAsProfileAddress(false);
				dto.setCountry(1L);
				dto.setAddrOne("abc def");
				dto.setCity("xyz");
				dto.setState(1L);
				dto.setPostalCode("123456");
				
				dto.setUserAgent("userAgent");
				dto.setClientIpAddress("172.30.66.73");
				dto.setTransactionAmount("112000");
				dto.setCurrencyCode("USD");
			}
			dto.setAtype(Atype);
			//Web
			dto.setTypeOfRequest(2L);
			
			accountsService.createAccount(dto);
			
		}

		List<AccountDto> dtos = accountsService.getAccounts(authId);
		Assert.assertNotNull( dtos );
		Assert.assertEquals( dtos.size(), total - deletedCount );
	}
	
	@Test
	public void udpateBankAccountTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto temp = accountsService.createAccount(dto);
		
		AccountDto accountDto = accountsService.getAccount(temp.getId());
		
		String _ahn = "ABC xyz";
		accountDto.setAccountHolderName(_ahn);
		
		Long _country = 2L;
		Long _bat = 11L;
		String _sortCode = "000054";
		String _bkn = "HDFC";
		String _acn = "0002381234567890";
		String _bka = "Koramangala, Bangalore";

		accountDto.setCountry(_country);
		accountDto.setBankAccountType(_bat);
		accountDto.setSortCode(_sortCode);
		accountDto.setBankName(_bkn);
		accountDto.setAccountNumber(_acn);
		accountDto.setBankAddress(_bka);
		
		AccountDto _accountDto = accountsService.updateAccount(accountDto);
		
		Assert.assertNotNull( _accountDto );
		Assert.assertNotNull( _accountDto.getId() );
		Assert.assertEquals(authId, _accountDto.getAuthId());
		Assert.assertEquals(_ahn, _accountDto.getAccountHolderName());
		Assert.assertEquals(status, _accountDto.getStatus());
		Assert.assertEquals(da, _accountDto.getDefaultAccount());
		Assert.assertEquals(ds, _accountDto.getDeletedStatus());
		Assert.assertEquals(AccountsConstants.BANK_ACCOUNT, _accountDto.getAtype());
		Assert.assertEquals(_country, _accountDto.getCountry());
		Assert.assertEquals(_bat, _accountDto.getBankAccountType());
		Assert.assertEquals(_sortCode, _accountDto.getSortCode());
		Assert.assertEquals(_bkn, _accountDto.getBankName());
		Assert.assertEquals(_acn, _accountDto.getAccountNumber());
		Assert.assertEquals(_bka, _accountDto.getBankAddress());
		
	}
	
	@Test
	public void udpateCardAccountTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.PENDING_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		String cn = "4012888888881881";
		String expd = "11/2020";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		dto.setCvv("1234");
		
		dto.setIsSameAsProfileAddress(false);
		dto.setCountry(1L);
		dto.setAddrOne("abc def");
		dto.setCity("xyz");
		dto.setState(1L);
		dto.setPostalCode("123456");
		
		dto.setUserAgent("userAgent");
		dto.setClientIpAddress("172.30.66.73");
		dto.setTransactionAmount("112000");
		dto.setCurrencyCode("USD");
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto temp = accountsService.createAccount(dto);
		
		AccountDto accountDto = accountsService.getAccount(temp.getId());
		
		String _ahn = "ABC xyz";
		accountDto.setAccountHolderName(_ahn);
		
		String _cn = "2222531234567000";
		String _expd = "11/2004";

		accountDto.setCardNumber(_cn);
		accountDto.setCardExpairyDate(_expd);
		
		accountDto.setUserAgent("userAgent");
		accountDto.setClientIpAddress("172.30.66.73");
		accountDto.setTransactionAmount("112000");
		accountDto.setCurrencyCode("USD");
		accountDto.setCvv("122");
		
		AccountDto _accountDto = accountsService.updateAccount(accountDto);
		
		Assert.assertNotNull( _accountDto );
		Assert.assertNotNull( _accountDto.getId() );
		Assert.assertEquals(authId, accountDto.getAuthId());
		Assert.assertEquals(_ahn, accountDto.getAccountHolderName());
		
		Assert.assertEquals(da, accountDto.getDefaultAccount());
		Assert.assertEquals(ds, accountDto.getDeletedStatus());
		Assert.assertEquals(AccountsConstants.CARD_ACCOUNT, accountDto.getAtype());
		Assert.assertEquals(_cn, accountDto.getCardNumber());
		Assert.assertEquals(_expd, accountDto.getCardExpairyDate());
		
	}
	
	@Test
	public void updateBankAccountStatusTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);

		status = FundingAccountStatus.REJECTED_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 1L);
		Assert.assertEquals(status, dto.getStatus());

		status = FundingAccountStatus.PENDING_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 1L);
		Assert.assertEquals(status, dto.getStatus());

		status = FundingAccountStatus.VERIFIED_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 1L);
		Assert.assertEquals(status, dto.getStatus());

	}
	
	@Test
	public void updateCardAccountStatusTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Abc xyz";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long ct = 2L;
		String cn = "2222531234567890";
		String expd = "11/2020";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		
		dto.setCardType(ct);
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		dto.setCvv("1234");
		
		dto.setIsSameAsProfileAddress(false);
		dto.setCountry(1L);
		dto.setAddrOne("abc def");
		dto.setCity("xyz");
		dto.setState(1L);
		dto.setPostalCode("123456");
		
		dto.setUserAgent("userAgent");
		dto.setClientIpAddress("172.30.66.73");
		dto.setTransactionAmount("112000");
		dto.setCurrencyCode("USD");
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);
		
		status = FundingAccountStatus.REJECTED_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 1L);
		Assert.assertEquals(status, dto.getStatus());

		status = FundingAccountStatus.PENDING_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 1L);
		Assert.assertEquals(status, dto.getStatus());

		status = FundingAccountStatus.VERIFIED_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 1L);
		Assert.assertEquals(status, dto.getStatus());

	}
	
	@Test
	public void updateCardAccountExpiryDateTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Abc xyz";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long ct = 2L;
		String cn = "2222531234567890";
		String expd = "11/2020";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		
		dto.setCardType(ct);
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		dto.setCvv("1234");
		
		dto.setIsSameAsProfileAddress(false);
		dto.setCountry(1L);
		dto.setAddrOne("abc def");
		dto.setCity("xyz");
		dto.setState(1L);
		dto.setPostalCode("123456");
		
		dto.setUserAgent("userAgent");
		dto.setClientIpAddress("172.30.66.73");
		dto.setTransactionAmount("112000");
		dto.setCurrencyCode("USD");
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);
		
		status = FundingAccountStatus.VERIFIED_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 1L);
		Assert.assertEquals(status, dto.getStatus());
		expd = "11/20";
		dto = accountsService.updateExpiryDate(accountDto.getId(), expd);
		Assert.assertEquals(expd, dto.getCardExpairyDate());

	}
	
	@Test(expected=WalletException.class)
	public void updateBankAccountExpiryDateTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);
		
		status = FundingAccountStatus.VERIFIED_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 1L);
		Assert.assertEquals(status, dto.getStatus());
		String expd = "11/20";
		dto = accountsService.updateExpiryDate(accountDto.getId(), expd);
		Assert.assertEquals(expd, dto.getCardExpairyDate());

	}

	@Test
	public void deleteBankAccountTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);

		status = FundingAccountStatus.VERIFIED_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 1L);
		Assert.assertEquals(status, dto.getStatus());
		
		Boolean isdeleted = accountsService.delete(dto.getId(), 2L);
		Assert.assertEquals(isdeleted, true);
		
		dto = accountsService.getAccount(accountDto.getId());
		Assert.assertEquals(AccountsConstants.SOFT_DELETE, dto.getDeletedStatus());

	}
	
	@Test
	public void deleteBankAccount2Test() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);

		status = FundingAccountStatus.PENDING_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 2L);
		Assert.assertEquals(status, dto.getStatus());
		
		Boolean isdeleted = accountsService.delete(dto.getId(), 2L);
		Assert.assertEquals(isdeleted, true);
		
		dto = accountsService.getAccount(accountDto.getId());
		Assert.assertEquals(AccountsConstants.SOFT_DELETE, dto.getDeletedStatus());

	}
	
	@Test(expected=WalletException.class)
	public void deleteBankAccount3Test() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);

		status = FundingAccountStatus.REJECTED_STATUS;
		dto = accountsService.updateStatus(accountDto.getId(), status, 1L);
		Assert.assertEquals(status, dto.getStatus());
		
		Boolean isdeleted = accountsService.delete(dto.getId(), 2L);
		Assert.assertEquals(isdeleted, true);
		
		dto = accountsService.getAccount(accountDto.getId());
		Assert.assertEquals(null, dto);
		
	}
	
	@Test(expected=Exception.class)
	public void deleteBankAccount4Test() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);

		Boolean isdeleted = accountsService.delete(dto.getId(), 2L);
		Assert.assertEquals(isdeleted, true);
		
		dto = accountsService.getAccount(accountDto.getId());
		Assert.assertEquals(null, dto);
		
	}
	
	@Test
	public void getAccountsTest() throws WalletException {
		
		int total = 20;
		Long authId = 1L;
		for( int i = 0; i < total; i++ ){
			
			String ahn = "Abc xyz";
			Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
			Boolean da = true;
			Long ds = AccountsConstants.NOT_DELETE;
			String Atype = AccountsConstants.CARD_ACCOUNT;
			
			AccountDto dto = new AccountDto();
			dto.setAuthId(authId);
			dto.setAccountHolderName(ahn);
			dto.setStatus(status);
			dto.setDefaultAccount(da);
			dto.setDeletedStatus(ds);
			if(i/2*2 == i){
				Atype = AccountsConstants.BANK_ACCOUNT;
				
				Long country = 1L;
				Long bat = 10L;
				String sortCode = "000053" + i;
				String bkn = "ICICI" + i;
				String acn = "0000531234567890" + i;
				String bka = "Jayanagar 3rd Block, Bangalore" + i;
				
				dto.setCountry(country);
				dto.setBankAccountType(bat);
				dto.setSortCode(sortCode);
				dto.setBankName(bkn);
				dto.setAccountNumber(acn);
				dto.setBankAddress(bka);
			}
			else {
				Long ct = 2L;
				String cn = "2222531234567890" + i;
				String expd = "11/20" + i;
				
				dto.setCardType(ct);
				dto.setCardNumber(cn);
				dto.setCardExpairyDate(expd);
				dto.setCvv("1234");
				
				dto.setIsSameAsProfileAddress(false);
				dto.setCountry(1L);
				dto.setAddrOne("abc def");
				dto.setCity("xyz");
				dto.setState(1L);
				dto.setPostalCode("123456");
				
				dto.setUserAgent("userAgent");
				dto.setClientIpAddress("172.30.66.73");
				dto.setTransactionAmount("112000");
				dto.setCurrencyCode("USD");
			}
			dto.setAtype(Atype);
			//Web
			dto.setTypeOfRequest(2L);
			
			dto = accountsService.createAccount(dto);
			accountsService.updateStatus(dto.getId(), FundingAccountStatus.VERIFIED_STATUS, 1L);
			if(i/3*3 == i){
				ds = AccountsConstants.SOFT_DELETE;
				accountsService.delete(dto.getId(), 2L);
			}
		}
		List<AccountDto> dtos = accountsService.getAccounts(authId);
		Assert.assertEquals( 13, dtos.size() );
		
	}
	
	@Test
	public void getCardAccountsTest() throws WalletException {
		
		int total = 20;
		Long authId = 1L;
		for( int i = 0; i < total; i++ ){
			
			String ahn = "Abc xyz";
			Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
			Boolean da = true;
			Long ds = AccountsConstants.NOT_DELETE;
			String Atype = AccountsConstants.CARD_ACCOUNT;
			
			AccountDto dto = new AccountDto();
			dto.setAuthId(authId);
			dto.setAccountHolderName(ahn);
			dto.setStatus(status);
			dto.setDefaultAccount(da);
			dto.setDeletedStatus(ds);
			if(i/2*2 == i){
				Atype = AccountsConstants.BANK_ACCOUNT;
				
				Long country = 1L;
				Long bat = 10L;
				String sortCode = "000053" + i;
				String bkn = "ICICI" + i;
				String acn = "0000531234567890" + i;
				String bka = "Jayanagar 3rd Block, Bangalore" + i;
				
				dto.setCountry(country);
				dto.setBankAccountType(bat);
				dto.setSortCode(sortCode);
				dto.setBankName(bkn);
				dto.setAccountNumber(acn);
				dto.setBankAddress(bka);
			}
			else {
				Long ct = 2L;
				String cn = "2222531234567890" + i;
				String expd = "11/20" + i;
				
				dto.setCardType(ct);
				dto.setCardNumber(cn);
				dto.setCardExpairyDate(expd);
				dto.setCvv("1234");
				
				dto.setIsSameAsProfileAddress(false);
				dto.setCountry(1L);
				dto.setAddrOne("abc def");
				dto.setCity("xyz");
				dto.setState(1L);
				dto.setPostalCode("123456");
				
				dto.setUserAgent("userAgent");
				dto.setClientIpAddress("172.30.66.73");
				dto.setTransactionAmount("112000");
				dto.setCurrencyCode("USD");
			}
			dto.setAtype(Atype);
			//Web
			dto.setTypeOfRequest(2L);
			
			dto = accountsService.createAccount(dto);
			accountsService.updateStatus(dto.getId(), FundingAccountStatus.VERIFIED_STATUS, 1L);
			if(i/3*3 == i){
				ds = AccountsConstants.SOFT_DELETE;
				accountsService.delete(dto.getId(), 2L);
			}
		}
		List<AccountDto> dtos = accountsService.getCardAccounts(authId);
		Assert.assertEquals( 7, dtos.size() );
		
	}
	
	@Test
	public void getAllAccountsTestTest() throws WalletException {
		
		int total = 20;
		Long authId = 1L;
		for( int i = 0; i < total; i++ ){
			
			String ahn = "Abc xyz";
			Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
			Boolean da = true;
			Long ds = AccountsConstants.NOT_DELETE;
			String Atype = AccountsConstants.CARD_ACCOUNT;
			
			AccountDto dto = new AccountDto();
			dto.setAuthId(authId);
			dto.setAccountHolderName(ahn);
			dto.setStatus(status);
			dto.setDefaultAccount(da);
			dto.setDeletedStatus(ds);
			if(i/2*2 == i){
				Atype = AccountsConstants.BANK_ACCOUNT;
				
				Long country = 1L;
				Long bat = 10L;
				String sortCode = "000053" + i;
				String bkn = "ICICI" + i;
				String acn = "0000531234567890" + i;
				String bka = "Jayanagar 3rd Block, Bangalore" + i;
				
				dto.setCountry(country);
				dto.setBankAccountType(bat);
				dto.setSortCode(sortCode);
				dto.setBankName(bkn);
				dto.setAccountNumber(acn);
				dto.setBankAddress(bka);
			}
			else {
				Long ct = 2L;
				String cn = "2222531234567890" + i;
				String expd = "11/20" + i;
				
				dto.setCardType(ct);
				dto.setCardNumber(cn);
				dto.setCardExpairyDate(expd);
				dto.setCvv("1234");
				
				dto.setIsSameAsProfileAddress(false);
				dto.setCountry(1L);
				dto.setAddrOne("abc def");
				dto.setCity("xyz");
				dto.setState(1L);
				dto.setPostalCode("123456");
				
				dto.setUserAgent("userAgent");
				dto.setClientIpAddress("172.30.66.73");
				dto.setTransactionAmount("112000");
				dto.setCurrencyCode("GBP");
			}
			dto.setAtype(Atype);
			//Web
			dto.setTypeOfRequest(2L);
			
			dto = accountsService.createAccount(dto);
			accountsService.updateStatus(dto.getId(), FundingAccountStatus.VERIFIED_STATUS, 1L);
			if(i/3*3 == i){
				ds = AccountsConstants.SOFT_DELETE;
				accountsService.delete(dto.getId(), 2L);
			}
		}
		
		List<AccountDto> dtos = accountsService.getAllAccounts(authId);
		Assert.assertEquals( dtos.size(), 20 );
	}
	
	@Test
	public void setAsDefaultAccountTest() throws WalletException {
		
		int total = 20;
		Long authId = 1L;
		Long defaultId = 0L;
		for( int i = 0; i < total; i++ ){
			
			String ahn = "Abc xyz";
			Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
			Boolean da = false;
			Long ds = AccountsConstants.NOT_DELETE;
			String Atype = AccountsConstants.CARD_ACCOUNT;
			
			AccountDto dto = new AccountDto();
			dto.setAuthId(authId);
			dto.setAccountHolderName(ahn);
			dto.setStatus(status);
			dto.setDefaultAccount(da);
			dto.setDeletedStatus(ds);
			if(i/2*2 == i){
				Atype = AccountsConstants.BANK_ACCOUNT;
				
				Long country = 1L;
				Long bat = 10L;
				String sortCode = "000053" + i;
				String bkn = "ICICI" + i;
				String acn = "0000531234567890" + i;
				String bka = "Jayanagar 3rd Block, Bangalore" + i;
				
				dto.setCountry(country);
				dto.setBankAccountType(bat);
				dto.setSortCode(sortCode);
				dto.setBankName(bkn);
				dto.setAccountNumber(acn);
				dto.setBankAddress(bka);
			}
			else {
				Long ct = 2L;
				String cn = "2222531234567890" + i;
				String expd = "11/20" + i;
				
				dto.setCardType(ct);
				dto.setCardNumber(cn);
				dto.setCardExpairyDate(expd);
				dto.setCvv("1234");
				
				dto.setIsSameAsProfileAddress(false);
				dto.setCountry(1L);
				dto.setAddrOne("abc def");
				dto.setCity("xyz");
				dto.setState(1L);
				dto.setPostalCode("123456");
				
				dto.setUserAgent("userAgent");
				dto.setClientIpAddress("172.30.66.73");
				dto.setTransactionAmount("112000");
				dto.setCurrencyCode("USD");
			}
			dto.setAtype(Atype);
			//Web
			dto.setTypeOfRequest(2L);
			
			dto = accountsService.createAccount(dto);
			accountsService.updateStatus(dto.getId(), FundingAccountStatus.VERIFIED_STATUS, 2L);
			if(i/3*3 == i){
				ds = AccountsConstants.SOFT_DELETE;
				accountsService.delete(dto.getId(), 2L);
			}
			if(i == 14)
				defaultId = dto.getId();
		}
		
		Boolean res = accountsService.setAsDefaultAccount(authId, defaultId);
		Assert.assertNotNull( res );
		Assert.assertEquals( res, new Boolean (true) );
		
		AccountDto res2 = accountsService.getDefaultAccount(authId);
		Assert.assertNotNull( res2 );
		Assert.assertEquals( res2.getId(), defaultId );
		
		
	}	
	
	@Test
	public void createCardWithExitingCardNumberTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Abc xyz";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.SOFT_DELETE;
		
		Long ct = 2L;
		String cn = "2222531234567890";
		String expd = "11/2020";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		
		dto.setCardType(ct);
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		dto.setCvv("1234");
		
		dto.setIsSameAsProfileAddress(false);
		dto.setCountry(1L);
		dto.setAddrOne("abc def");
		dto.setCity("xyz");
		dto.setState(1L);
		dto.setPostalCode("123456");
		
		dto.setUserAgent("userAgent");
		dto.setClientIpAddress("172.30.66.73");
		dto.setTransactionAmount("112000");
		dto.setCurrencyCode("USD");
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto temp = accountsService.createAccount(dto);
		
		Card card = accountsService.getCardDeletedStatus(temp.getAuthId(), dto.getCardNumber(), AccountsConstants.SOFT_DELETE);
		String decCardNo = cryptService.decryptData(card.getCardNumber());
		String decExpd = cryptService.decryptData(card.getCardExpairyDate());
		
		Assert.assertEquals( cn, decCardNo );
		Assert.assertEquals( expd, decExpd );
		
		temp.setDeletedStatus(AccountsConstants.NOT_DELETE);
		temp.setId(null);
		//Web
		temp.setTypeOfRequest(2L);
		
		AccountDto temp2 = accountsService.createAccount(temp);
		Assert.assertNotNull(temp2);
	}
	
	@Test
	public void createBankAccountWithExistingAccountNumberTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Prasad Jorige";
		Long status = FundingAccountStatus.NOT_VERIFIED_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.SOFT_DELETE;
		
		Long country = 1L;
		Long bat = 10L;
		String sortCode = "000053";
		String bkn = "ICICI";
		String acn = "0000531234567890";
		String bka = "Jayanagar 3rd Block, Bangalore";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		
		dto.setCountry(country);
		dto.setBankAccountType(bat);
		dto.setSortCode(sortCode);
		dto.setBankName(bkn);
		dto.setAccountNumber(acn);
		dto.setBankAddress(bka);
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto accountDto = accountsService.createAccount(dto);
		Assert.assertNotNull( accountDto );
		Assert.assertNotNull( accountDto.getId() );
		Assert.assertEquals(authId, accountDto.getAuthId());
		Assert.assertEquals(ds, accountDto.getDeletedStatus());
		
		Bank bank = accountsService.getBankDeletedStatus(accountDto.getAuthId(), 
				accountDto.getAccountNumber(), accountDto.getSortCode(), AccountsConstants.SOFT_DELETE);
		Assert.assertNotNull(bank);
		
		accountDto.setDeletedStatus(AccountsConstants.NOT_DELETE);
		accountDto.setId(null);
		//Web
		accountDto.setTypeOfRequest(2L);
		
		AccountDto temp2 = accountsService.createAccount(accountDto);
		Assert.assertNotNull(temp2);
		
		
	}
	
	@Test
	public void varifyCardAccountTest() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Abc xyz";
		Long status = FundingAccountStatus.PENDING_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long ct = 1L; // VISA
		String cn = "4532251507162715";
		String expd = "11/2020";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		
		dto.setCardType(ct);
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		dto.setCvv("123");
		
		dto.setIsSameAsProfileAddress(false);
		dto.setCountry(1L);
		dto.setAddrOne("abc def");
		dto.setCity("xyz");
		dto.setState(1L);
		dto.setPostalCode("123456");
		
		dto.setTransactionAmount("10");
		dto.setCurrencyCode("USD");
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto temp = accountsService.createAccount(dto);
			
		temp.setTransactionAmount("10");
		temp.setCurrencyCode("USD");
		temp.setCvv("123");
		temp.setTransactionType(WalletTransactionTypes.PENNY_DROP_FROM_CARD_CUSTOMER);
		AccountDto temp2 = accountsService.varifyCard(temp);
		
		Assert.assertNotNull( temp2 );
		Assert.assertNotNull( temp2.getId() );
	}
	
	@Test
	public void varifyCardAccountTest2() throws WalletException {
		
		Long authId = 1L;
		String ahn = "Abc xyz";
		Long status = FundingAccountStatus.PENDING_STATUS;
		Boolean da = true;
		Long ds = AccountsConstants.NOT_DELETE;
		
		Long ct = 1L; // VISA
		String cn = "4532251507162715";
		String expd = "11/2020";
		
		AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(ahn);
		dto.setStatus(status);
		dto.setDefaultAccount(da);
		dto.setDeletedStatus(ds);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		
		dto.setCardType(ct);
		dto.setCardNumber(cn);
		dto.setCardExpairyDate(expd);
		dto.setCvv("123");
		
		dto.setIsSameAsProfileAddress(false);
		dto.setCountry(1L);
		dto.setAddrOne("abc def");
		dto.setCity("xyz");
		dto.setState(1L);
		dto.setPostalCode("123456");
		
		dto.setTransactionAmount("112");
		dto.setCurrencyCode("USD");
		//Web
		dto.setTypeOfRequest(2L);
		
		AccountDto temp = accountsService.createAccount(dto);
		
		temp.setTransactionAmount("112");
		temp.setCurrencyCode("USD");
		temp.setCvv("123");
		temp.setTransactionType(WalletTransactionTypes.PENNY_DROP_FROM_CARD_CUSTOMER);
		AccountDto temp3 = accountsService.varifyCard(temp);
		Assert.assertNotNull( temp3 );
		Assert.assertNotNull( temp3.getId() );
	}
	
}
