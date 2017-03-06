/**
 * 
 */
package com.tarang.ewallet.accounts.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.accounts.dao.AccountsDao;
import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Account;
import com.tarang.ewallet.model.Bank;
import com.tarang.ewallet.model.Card;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:19:09 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class AccountsDaoImpl implements AccountsDao {

	private static final Logger LOGGER = Logger.getLogger(AccountsDaoImpl.class);
	
	private HibernateOperations hibernateOperations;

	public AccountsDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}

	public Account createAccount(Account account) throws WalletException {
		try {
			hibernateOperations.save(account);
			hibernateOperations.flush();
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			throw new WalletException(GlobalLitterals.DB_DUPLICATE_ENTRY, ex);
		}
		return account;
	}

	@Override
	public Integer getAccountsCount(Long authId) throws WalletException {
		List list = hibernateOperations.findByNamedQuery("getAccountsCountByAuthenticationId", new Object[]{AccountsConstants.NOT_DELETE, authId});
		if(list != null && list.size() > 0){
			return ((Long)list.get(0)).intValue();
		}
		return 0;
	}

	@Override
	public Account getAccount(Long id) throws WalletException {
		List<Account> list = (List<Account>) hibernateOperations.findByNamedQuery("getAccountById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			throw new WalletException(AccountsConstants.ERROR_MSG_NON_EXISTS);
		}
	}

	@Override
	public Account updateAccount(Account account) throws WalletException {
		try {
			hibernateOperations.update(account);
			hibernateOperations.flush();
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			throw new WalletException(GlobalLitterals.DB_DUPLICATE_ENTRY, ex);
		}
		return account;
	}

	@Override
	public Boolean permanentDelete(Account account) throws WalletException {
		hibernateOperations.delete(account);
		return Boolean.TRUE;
	}

	@Override
	public Boolean softDelete(Account account) throws WalletException {
		updateAccount(account);
		return Boolean.TRUE;
	}

	@Override
	public List<Account> getAccounts(Long authId) throws WalletException {
		return (List<Account>) hibernateOperations.findByNamedQuery("getAccountsByAuthenticationId", new Object[]{AccountsConstants.NOT_DELETE, authId});
	}
	
	@Override
	public List<Account> getAllAccounts(Long authId) throws WalletException {
		return (List<Account>) hibernateOperations.findByNamedQuery("getAllAccountsByAuthenticationId", authId);
	}
	
	@Override
	public List<Account> getCardAccounts(Long authId) throws WalletException {
		return (List<Account>) hibernateOperations.findByNamedQuery("getAllCardAccountsByAuthId", 
				new Object[]{AccountsConstants.CARD_ACCOUNT, AccountsConstants.NOT_DELETE, authId});
	}

	@Override
	public Account getDefaultAccount(Long authId) throws WalletException {
		List<Account> list = (List<Account>) hibernateOperations.findByNamedQuery("getDefaultAccount", new Object[]{Boolean.TRUE, authId});
		if (list != null && list.size() == 1) {
			return list.get(0);
		} 
		return null;
	}

	@Override
	public Card getCardDeletedStatus(Long authId, String cardNumber, Long deletedStatus)
			throws WalletException {
		List<Card> list = (List<Card>) hibernateOperations.findByNamedQuery("getCardByCardNumber", new Object[]{deletedStatus, authId, cardNumber});
		if (list != null && list.size() == 1) {
			return list.get(0);
		} 
		return null;
	}
	
	@Override
	public Bank getBankDeletedStatus(Long authId, String accountNumber, 
			String sortCode, Long deletedStatus) throws WalletException {
		List<Bank> list = (List<Bank>) hibernateOperations.findByNamedQuery("getBankByAccountNumber", new Object[]{deletedStatus, authId, accountNumber, sortCode});
		if (list != null && list.size() == 1) {
			return list.get(0);
		} 
		return null;
	}
	
	
	@Override
	public Boolean isExistBankAccount(String accountNumber, String sortCode, String userType) throws WalletException {
		List<Bank> list = (List<Bank>) hibernateOperations.findByNamedQuery("isExistBankAccount", new Object[]{accountNumber, sortCode, userType});
		if (list != null && list.size() > 0) {
			return Boolean.TRUE;
		} 
		return Boolean.FALSE;
	}

	@Override
	public Boolean bankAccountExistWithSameUser(String accountNumber, String sortCode, Long authId, Long deletedStatus) throws WalletException {		
		List<Bank> list = (List<Bank>) hibernateOperations.findByNamedQuery("bankAccountExistWithSameUser", new Object[]{accountNumber, sortCode, authId, deletedStatus});
		if (list != null && list.size() > 0) {
			return Boolean.TRUE;
		} 
		return Boolean.FALSE;
	}

	@Override
	public Boolean cardAccountExistWithSameUser(String cardNumber, Long authId, Long deletedStatus) throws WalletException {		
		List<Card> list = (List<Card>) hibernateOperations.findByNamedQuery("cardAccountExistWithSameUser", new Object[]{cardNumber, authId, deletedStatus});
		if (list != null && list.size() > 0) {
			return Boolean.TRUE;
		} 
		return Boolean.FALSE;
	}

	@Override
	public Integer getTotalBankAccouts(String accountNumber, String sortCode, String userType, Long deletedStatus) throws WalletException {		
		List list = hibernateOperations.findByNamedQuery("getTotalBankAccouts", new Object[]{accountNumber, sortCode, userType, deletedStatus});
		if (list != null && list.size() > 0) { 
			return ((Long)list.get(0)).intValue();
		} 
		return GlobalLitterals.ZERO_INTEGER;
	}

	@Override
	public Integer getTotalCardAccounts(String cardNumber, String userType, Long deletedStatus) throws WalletException {		
		List list = hibernateOperations.findByNamedQuery("getTotalCardAccounts", new Object[]{deletedStatus, cardNumber, userType});
		if (list != null && list.size() > 0) {
			return ((Long)list.get(0)).intValue();
		} 
		return GlobalLitterals.ZERO_INTEGER;
	}
	
}
