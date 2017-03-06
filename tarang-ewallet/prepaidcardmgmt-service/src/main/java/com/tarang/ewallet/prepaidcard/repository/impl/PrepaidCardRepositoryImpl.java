package com.tarang.ewallet.prepaidcard.repository.impl;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.PrepaidCardDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.business.HttpService;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.PrepaidCard;
import com.tarang.ewallet.model.PrepaidCardAccount;
import com.tarang.ewallet.prepaidcard.dao.PrepaidCardDao;
import com.tarang.ewallet.prepaidcard.repository.PrepaidCardRepository;
import com.tarang.ewallet.prepaidcard.util.AccountMgmtUtil;
import com.tarang.ewallet.prepaidcard.util.AccountsConstants;
import com.tarang.ewallet.transaction.business.PGService;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.util.service.UtilService;


/**
 * @author  : kedarnathd
 * @date    : July 26, 2016
 * @time    : 4:15:27 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class PrepaidCardRepositoryImpl implements PrepaidCardRepository {

	private static final Logger LOGGER = Logger.getLogger(PrepaidCardRepositoryImpl.class);
	
	private HttpService httpService;
	
	private PGService pGService;
	
	private CommonService commonService;
	
	private PrepaidCardDao prepaidCardDao;
	
	private UtilService utilService;
	
	private CryptService cryptService;
	
	private HibernateTransactionManager transactionManager;
	
	private TransactionWalletService transactionWalletService;
	
	public PrepaidCardRepositoryImpl(PrepaidCardDao prepaidCardDao, CommonService commonService, HttpService httpService, 
			PGService pGService, HibernateTransactionManager transactionManager, UtilService utilService, 
			TransactionWalletService transactionWalletService, CryptService cryptService){
		this.prepaidCardDao = prepaidCardDao;
		this.commonService = commonService;
		this.httpService = httpService;
		this.pGService = pGService;
		this.transactionManager = transactionManager;
		this.utilService = utilService;
		this.transactionWalletService = transactionWalletService;
		this.cryptService = cryptService;
	}
	
	@Override
	public PrepaidCardDto createPrepaidCard(PrepaidCardDto dto) throws WalletException {
		LOGGER.debug("createPrepaidCard");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		PrepaidCardAccount prepaidCardAccount = null;
		try{
			prepaidCardAccount = AccountMgmtUtil.getPrepaidCardAccount(dto, dto.getAddressId(), cryptService);
			prepaidCardAccount.setCustomerNumber(dto.getCustomerNumber());
			prepaidCardAccount = prepaidCardDao.createPrepaidCard(prepaidCardAccount);
			dto.setPrepaidCardId(prepaidCardAccount.getId());
			transactionManager.commit(txstatus);
			return dto;
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
	public PrepaidCardDto getPrepaidCard(Long customerId) throws WalletException {
		LOGGER.debug("getAccount");
		PrepaidCardAccount prepaidCardAccount = prepaidCardDao.getPrepaidCard(customerId);
		if(null == prepaidCardAccount){
			return null;
		}
		Address address = null;
		if(prepaidCardAccount.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
			PrepaidCard card = (PrepaidCard) prepaidCardAccount;
			address = commonService.getAddress(card.getAddressId());
		}
		return AccountMgmtUtil.getAcccountDto(prepaidCardAccount, address, cryptService);
	}
	
	
	
	
	
	
	
}
