package com.tarang.ewallet.accounts.repository.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.accounts.dao.AccountsDao;
import com.tarang.ewallet.accounts.repository.AccountsRepository;
import com.tarang.ewallet.accounts.util.AccountMgmtUtil;
import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.accounts.util.FundingAccountStatus;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.business.HttpService;
import com.tarang.ewallet.http.util.HttpServiceConstants;
import com.tarang.ewallet.masterdata.util.CurrencyIds;
import com.tarang.ewallet.model.Account;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Bank;
import com.tarang.ewallet.model.Card;
import com.tarang.ewallet.model.History;
import com.tarang.ewallet.model.PgRequest;
import com.tarang.ewallet.model.PgResponse;
import com.tarang.ewallet.model.SettlementResponse;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.transaction.business.PGService;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionUtil;
import com.tarang.ewallet.util.service.UtilService;


/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:15:27 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class AccountsRepositoryImpl implements AccountsRepository {

	private static final Logger LOGGER = Logger.getLogger(AccountsRepositoryImpl.class);
	
	private HttpService httpService;
	
	private PGService pGService;
	
	private CommonService commonService;
	
	private AccountsDao accountsDao;
	
	private UtilService utilService;
	
	private CryptService cryptService;
	
	private HibernateTransactionManager transactionManager;
	
	private TransactionWalletService transactionWalletService;
	
	public AccountsRepositoryImpl(AccountsDao accountsDao, CommonService commonService, HttpService httpService, 
			PGService pGService, HibernateTransactionManager transactionManager, UtilService utilService, 
			TransactionWalletService transactionWalletService, CryptService cryptService){
		this.accountsDao = accountsDao;
		this.commonService = commonService;
		this.httpService = httpService;
		this.pGService = pGService;
		this.transactionManager = transactionManager;
		this.utilService = utilService;
		this.transactionWalletService = transactionWalletService;
		this.cryptService = cryptService;
	}
	
	@Override
	public AccountDto createAccount(AccountDto dto) throws WalletException {
		LOGGER.debug("createAccount");
		Address address = new Address();
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Boolean isOldAccount = false;
			Account account = null;
			if(dto.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
				String encCardNumber = cryptService.encryptData(dto.getCardNumber());
				Card oldCard = accountsDao.getCardDeletedStatus(dto.getAuthId(), 
						encCardNumber, AccountsConstants.SOFT_DELETE);
		     	if(oldCard != null){
		     		Account acc = accountsDao.getAccount(oldCard.getId());
		     		acc.setDeletedStatus(AccountsConstants.NOT_DELETE);
		     		acc.setStatus(FundingAccountStatus.NOT_VERIFIED_STATUS);
		     		account = accountsDao.updateAccount(acc);
		     		transactionManager.commit(txstatus);
		     		isOldAccount = true;
		     	}
		     //for bank
			}else if(dto.getAtype().equals(AccountsConstants.BANK_ACCOUNT)){
				Bank oldBank = accountsDao.getBankDeletedStatus(dto.getAuthId(), 
						dto.getAccountNumber(), dto.getSortCode(), AccountsConstants.SOFT_DELETE);
		     	if(oldBank != null){
		     		Account acc = accountsDao.getAccount(oldBank.getId());
		     		acc.setDeletedStatus(AccountsConstants.NOT_DELETE);
		     		acc.setStatus(FundingAccountStatus.NOT_VERIFIED_STATUS);
		     		account = accountsDao.updateAccount(acc);
		     		transactionManager.commit(txstatus);
		     		isOldAccount = true;
		     	}
			}
			if(!isOldAccount){
				Long addressId = null;
				if(dto.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
					AccountMgmtUtil.getBillingAddress(dto, address);
					address = commonService.createAddress(address);
					addressId = address.getId();
				}
				account = accountsDao.createAccount(AccountMgmtUtil.getAcccount(dto, addressId, cryptService));
				dto.setId(account.getId());
				if(dto.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
					postHttpServiceAuthRequest(dto);
				}
		    	transactionManager.commit(txstatus);
			}else{
				dto.setPgResponseCode(AccountsConstants.OLD_ACCOUNT);
			}
			AccountDto ad = AccountMgmtUtil.getAcccountDto(account, address, cryptService);
			ad.setPgResponseCode(dto.getPgResponseCode());
			ad.setHistoryId(dto.getId());
			ad.setOrderId(dto.getOrderId()); 
			ad.setTransactionAmount(dto.getTransactionAmount()); 
			return ad;
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}     
	}

	@Override
	public Integer getAccountsCount(Long authId) throws WalletException {
		return accountsDao.getAccountsCount(authId);
	}

	@Override
	public AccountDto getAccount(Long id) throws WalletException {
		LOGGER.debug("getAccount");
		Account account = accountsDao.getAccount(id);
		Address address = null;
		if(account.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
			Card card = (Card)account;
			address = commonService.getAddress(card.getAddressId());
		}
		return AccountMgmtUtil.getAcccountDto(account, address, cryptService);
	}
	
	@Override
	public AccountDto updateAccount(AccountDto dto) throws WalletException {
		LOGGER.debug("updateAccount");
		Address address = null;
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Account account = accountsDao.getAccount(dto.getId());
			AccountMgmtUtil.updateEditAcccount(dto, account, cryptService);
			if(dto.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
				Card card = (Card)account;
				/*Card has modified, so called PG HTTP Service*/
				postHttpServiceAuthRequest(dto);
				address = commonService.getAddress(card.getAddressId());
				AccountMgmtUtil.getBillingAddress(dto, address);
				commonService.updateAddress(address);
			}
			account = accountsDao.updateAccount(account);
			transactionManager.commit(txstatus);
			AccountDto ad = AccountMgmtUtil.getAcccountDto(account, address, cryptService);
			ad.setPgResponseCode(dto.getPgResponseCode());
			return ad;
		}catch (Exception ex){
			LOGGER.error(ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}     
	}
	
	@Override
	public AccountDto updateStatus(Long id, Long status, Long typeOfRequest) throws WalletException {
		LOGGER.debug("updateStatus");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Account account = accountsDao.getAccount(id);
			account.setStatus(status);
			account.setTypeOfRequest(typeOfRequest);
			account = accountsDao.updateAccount(account);
			transactionManager.commit(txstatus);
			return AccountMgmtUtil.getAcccountDto(account, null, cryptService);
		}catch (Exception ex){
			LOGGER.error(ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}     
	}

	@Override
	public AccountDto updateExpiryDate(Long id, String expDate) throws WalletException {
		LOGGER.debug("updateExpiryDate");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Account account = accountsDao.getAccount(id);
			if(account instanceof Card){
				Card card = (Card)account;
				card.setCardExpairyDate(cryptService.encryptData(expDate));
				account = accountsDao.updateAccount(account);
				transactionManager.commit(txstatus);
				return AccountMgmtUtil.getAcccountDto(account, null, cryptService);
			}else {
				throw new WalletException(AccountsConstants.ERROR_MSG_UPDATE_EXPIRYDATE_BANK);
			}
		}catch (Exception ex){
			LOGGER.error(ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}     
	}

	@Override
	public Boolean delete(Long id, Long typeOfRequest) throws WalletException {
		LOGGER.debug("delete");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			boolean rst = false;
			Account account = accountsDao.getAccount(id);
			if( account.getStatus().equals(FundingAccountStatus.NOT_VERIFIED_STATUS) 
					|| account.getStatus().equals(FundingAccountStatus.REJECTED_STATUS) ){
				rst = accountsDao.permanentDelete(account);
			}else {
				account.setDeletedStatus(AccountsConstants.SOFT_DELETE);
				account.setTypeOfRequest(typeOfRequest);
				rst = accountsDao.softDelete(account);
			}
			transactionManager.commit(txstatus);
			return rst;
		}catch (Exception ex){
			LOGGER.error(ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}     
	}

	@Override
	public List<AccountDto> getAllAccounts(Long authId) throws WalletException {
		LOGGER.debug("getAllAccounts");
		return AccountMgmtUtil.getAccountDtos(accountsDao.getAllAccounts(authId), cryptService);
	}

	@Override
	public List<AccountDto> getAccounts(Long authId) throws WalletException {
		LOGGER.debug("getAccounts");
		return AccountMgmtUtil.getAccountDtos(accountsDao.getAccounts(authId), cryptService);
	}
	
	@Override
	public List<AccountDto> getCardAccounts(Long authId) throws WalletException {
		LOGGER.debug("getCardAccounts");
		return AccountMgmtUtil.getAccountDtos(accountsDao.getCardAccounts(authId), cryptService);
	}

	@Override
	public Boolean setAsDefaultAccount(Long authId, Long id) throws WalletException {
		LOGGER.debug("setAsDefaultAccount");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			List<Account> accounts = accountsDao.getAccounts(authId);
			Boolean res = false;
			if(accounts != null && accounts.size() > 0) {
				for(Account account: accounts){
					if(account.getDefaultAccount()){
						account.setDefaultAccount(false);
						accountsDao.updateAccount(account);
					}
					if(account.getId().equals(id) ){
						account.setDefaultAccount(true);
						accountsDao.updateAccount(account);
					}
				}
				res = true;
			}
			transactionManager.commit(txstatus);
			return res;
		}catch (Exception ex){
			LOGGER.error(ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}     
	}

	@Override
	public AccountDto getDefaultAccount(Long authId) throws WalletException {
		LOGGER.debug("getDefaultAccount");
		return AccountMgmtUtil.getAcccountDto(accountsDao.getDefaultAccount(authId), null, cryptService);
	}
	
	@Override
	public Card getCardDeletedStatus(Long authId, String cardNumber, Long deletedStatus) throws WalletException {
		LOGGER.debug("getCardDeletedStatus");
		String encCardNumber = cryptService.encryptData(cardNumber);
		return accountsDao.getCardDeletedStatus(authId, encCardNumber, deletedStatus);
	}
	
	@Override
	public Bank getBankDeletedStatus(Long authId, String accountNumber, String sortCode, Long deletedStatus) throws WalletException {
		LOGGER.debug("getBankDeletedStatus");
		return accountsDao.getBankDeletedStatus(authId, accountNumber, sortCode, deletedStatus);
	}
	@Override
	public AccountDto varifyCard(AccountDto dto) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		History history = null;
		/*amount for pending card*/
		String amount = dto.getTransactionAmount() != null ? dto.getTransactionAmount(): "0.00";
		try{
			if(dto.getStatus().equals(FundingAccountStatus.NOT_VERIFIED_STATUS)){
				postHttpServiceAuthRequest(dto);
			}else if(dto.getStatus().equals(FundingAccountStatus.PENDING_STATUS)){
				history =  pGService.getHistory(dto.getOrderId(), 
							dto.getId(), Double.valueOf(dto.getTransactionAmount()));
					
				if( null == history){
						throw new WalletException(WalletTransactionConstants.ERROR_MSG_WRONG_INFO);
				}
				postHttpSettlementRequest(history, dto, amount);
			}
			transactionManager.commit(txstatus);
		}catch (Exception ex){
			LOGGER.error(ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}    
		return dto;
	}
	
	
	
	@Override
	public Boolean isExistBankAccount(String accountNumber, String sortCode, String userType)
			throws WalletException {
		return accountsDao.isExistBankAccount(accountNumber, sortCode, userType);
	}

	@Override
	public Boolean bankAccountExistWithSameUser(String accountNumber, String sortCode, Long authId, Long deletedStatus) throws WalletException {
		return accountsDao.bankAccountExistWithSameUser(accountNumber, sortCode, authId, deletedStatus);
	}

	@Override
	public Boolean cardAccountExistWithSameUser(String cardNumber, Long authId, Long deletedStatus) throws WalletException {
		String cdNumber = cryptService.encryptData(cardNumber);
		return accountsDao.cardAccountExistWithSameUser(cdNumber, authId, deletedStatus);
	}

	@Override
	public Integer getTotalBankAccouts(String accountNumber, String sortCode, String userType, Long deletedStatus) throws WalletException {
		return accountsDao.getTotalBankAccouts(accountNumber, sortCode, userType, deletedStatus);
	}

	@Override
	public Integer getTotalCardAccounts(String cardNumber, String userType, Long deletedStatus) throws WalletException {
		String cdNumber = cryptService.encryptData(cardNumber);
		return accountsDao.getTotalCardAccounts(cdNumber, userType, deletedStatus);
	}
	
	/**
	 * Call PG Auth Request
	 * @param dto
	 * @return
	 * @throws WalletException
	 */
	private void postHttpServiceAuthRequest(AccountDto dto) throws WalletException{
		Long status = null;
		PgResponse pgResponse = null;
		PgRequest authAPIRequest = new PgRequest();
		AccountMgmtUtil.getPgRequest(dto, authAPIRequest, getPennyDropAmountAsPerCurrency(dto.getCurrency()));
		
		pgResponse = httpService.postAuthRequest(authAPIRequest);
		LOGGER.info("Auth Txn Response Code :  " + pgResponse.getResponseCode());
		LOGGER.info("Auth Txn Response Msg  :  " + pgResponse.getResponseMsg());		
		
		if(pgResponse.getResponseCode().equals(HttpServiceConstants.RESPONSE_DECISION_SUCCESS)){
			status = FundingAccountStatus.PENDING_STATUS;
			pgResponse.setIsSuccess(true);
		}else{
			status = FundingAccountStatus.NOT_VERIFIED_STATUS;
			pgResponse.setIsSuccess(false);
		}
		/*update card status*/
		updateStatus(dto.getId(), status, dto.getTypeOfRequest());
		/*save transaction details*/
		History history = WalletTransactionUtil.savePGAuthTransactionInHistory(authAPIRequest, pgResponse, 
				dto.getId(), cryptService);
		pGService.saveTransaction(history);
		dto.setStatus(status);
		dto.setPgResponseCode(pgResponse.getResponseCode());
		dto.setHistoryId(history.getId());
		dto.setOrderId(history.getOrderId()); 
		dto.setTransactionAmount(history.getAmount().toString()); 
	}
	
	/**
	 * Call PG Settlement Request
	 * @param history
	 * @param dto
	 * @throws WalletException
	 */
	private void postHttpSettlementRequest(History history, AccountDto dto, String amount) throws WalletException{
		Long status = dto.getStatus();
		PgRequest settlementAPIRequest = new PgRequest();
		SettlementResponse settlementResponse = null;
		Boolean isSuccess = false;
		
		AccountMgmtUtil.getPgRequestFromHistory(history, settlementAPIRequest);
		settlementResponse = httpService.postSettlementRequest(settlementAPIRequest);
		
		LOGGER.info("Settlement Txn Response Decision :  " +settlementResponse.getResponseDecision());
		LOGGER.info("Settlement Txn Response Code :  " +settlementResponse.getResponseCode());
		LOGGER.info("Settlement Txn Response Msg  :  " +settlementResponse.getResponseMessage());
		
		if(settlementResponse.getResponseDecision() == null){
			isSuccess = Boolean.FALSE;
		}else if(settlementResponse.getResponseDecision().equals(HttpServiceConstants.RESPONSE_DECISION_SUCCESS)){
			/* change card account status*/
			status = FundingAccountStatus.VERIFIED_STATUS;
			isSuccess = Boolean.TRUE;
			Account account = accountsDao.getAccount(dto.getId());
			/*set first default card*/
			Account defultAccount = accountsDao.getDefaultAccount(dto.getAuthId());
			if(null == defultAccount ){
				account.setDefaultAccount(Boolean.TRUE);
			}
			account.setTypeOfRequest(dto.getTypeOfRequest());
			account.setStatus(status);
			accountsDao.updateAccount(account);
			
			/*If settle is success then log transaction for penny drop feature*/
			WalletTransaction transaction = constructTransactionForPenny(dto, amount);
			transaction = transactionWalletService.initiateTransaction(transaction);
			transactionWalletService.settleTransaction(transaction.getId());
		}
			
		/*save transaction details*/
		History newhistory = WalletTransactionUtil.saveSettlementResponseInHistory(settlementAPIRequest, 
				settlementResponse, dto.getId(), isSuccess);
		dto.setPgResponseCode(settlementResponse.getResponseDecision());
		pGService.saveTransaction(newhistory);
	}
	
	private static WalletTransaction constructTransactionForPenny(AccountDto accountDto, String amount) {

		WalletTransaction transaction = new WalletTransaction();
		//payee auth id
		transaction.setPayer(null);
		transaction.setIpAddress(accountDto.getClientIpAddress());
		//payer auth id
		transaction.setPayee(accountDto.getAuthId());
		//payer amount
		transaction.setPayerAmount(Double.valueOf(amount));
		//payer amount
		transaction.setPayeeAmount(Double.valueOf(amount));
		//payee currency id
		transaction.setPayeeCurrency(accountDto.getCurrency());
		//payer currency id
		transaction.setPayerCurrency(accountDto.getCurrency());
		//P_TO_P,P_TO_NP...
		transaction.setTypeOfTransaction(accountDto.getTransactionType());
		//Mobile/Web
		transaction.setTypeOfRequest(accountDto.getTypeOfRequest());
		return transaction;
	}
	
	private Integer getPennyDropAmountAsPerCurrency(Long currencyId){
		if(CurrencyIds.USD.equals(currencyId)){
			return utilService.getPennyDropAmountUsd();
		}else if(CurrencyIds.JPY.equals(currencyId)){
			return utilService.getPennyDropAmountJpy();
		}else if(CurrencyIds.THB.equals(currencyId)){
			return utilService.getPennyDropAmountThb();
		}else{
			return null;
		}
	}
	
}
