package com.tarang.ewallet.transaction.repository.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.ReloadMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.business.HttpService;
import com.tarang.ewallet.http.util.HttpServiceConstants;
import com.tarang.ewallet.http.util.HttpServiceUtil;
import com.tarang.ewallet.model.History;
import com.tarang.ewallet.model.PgRequest;
import com.tarang.ewallet.model.PgResponse;
import com.tarang.ewallet.model.ReloadMoney;
import com.tarang.ewallet.model.SettlementResponse;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.transaction.business.PGService;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.transaction.dao.ReloadMoneyDao;
import com.tarang.ewallet.transaction.repository.ReloadMoneyRepository;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionUtil;


public class ReloadMoneyRepositoryImpl implements ReloadMoneyRepository{
	
	private static final Logger LOGGER = Logger.getLogger(ReloadMoneyRepositoryImpl.class);
	
	private HibernateTransactionManager transactionManager;
	
	private TransactionWalletService transactionWalletService;
	
	private ReloadMoneyDao reloadMoneyDao;
	
	private CommonService commonService;
	
	private HttpService httpService;
	
	private PGService pGService;
	
	private CryptService cryptService;
	
	private static final int EXP_YEAR_LENGTH3 = 3;
	
	private static final int EXP_YEAR_LENGTH4 = 4;
	
	public ReloadMoneyRepositoryImpl(HibernateTransactionManager transactionManager, 
			TransactionWalletService transactionWalletService, ReloadMoneyDao reloadMoneyDao, 
			CommonService commonService, HttpService httpService, 
			CryptService cryptService, PGService pGService ) {
		this.transactionManager = transactionManager;
		this.transactionWalletService = transactionWalletService;
		this.reloadMoneyDao =  reloadMoneyDao;
		this.commonService = commonService;
		this.httpService = httpService;
		this.cryptService = cryptService;
		this.pGService = pGService;
	}

	@Override
	public ReloadMoneyDto createReloadMoney(ReloadMoneyDto reloadMoneyDto) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			/*Velocity and threshold check taken care at controller*/
			ReloadMoney reloadMoney = WalletTransactionUtil.getReloadMoney(reloadMoneyDto);
			ReloadMoneyDto newDto = postHttpServiceAuthRequest(reloadMoneyDto);
			if(newDto.getIsReloadMoneySucc()){
				WalletTransaction transaction = WalletTransactionUtil.getTransactionFromDto(reloadMoneyDto);
				/*create with pending status*/
				transaction = transactionWalletService.initiateTransaction(transaction);
				transactionWalletService.settleTransaction(transaction.getId());
				/*Populate parent transaction id in reload money table*/
				reloadMoney.setTrxId(transaction.getId());
				
			}
			/*Populate history id in reload money table*/
			reloadMoney.setHistoryId(newDto.getHistoryId());
			reloadMoney = reloadMoneyDao.createReloadMoney(reloadMoney);
			newDto.setId(reloadMoney.getId());
			transactionManager.commit(txstatus);
			if(newDto.getIsReloadMoneySucc()){
				commonService.sendReloadMoneyEmail(reloadMoneyDto.getPayeeName(), reloadMoneyDto.getPayeeEmail(), 
						CommonUtil.getConvertedAmountInString(reloadMoneyDto.getReloadAmount()), reloadMoneyDto.getCurrencyCode(), reloadMoneyDto.getLanguageId());
			}
			return newDto;
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
	public void settlementReloadMoney(ReloadMoneyDto dto) throws WalletException {
			History history = null;
			TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
			TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
			try{												//changed getId() to getAccountId() 
				history =  pGService.getHistory(dto.getOrderId(), dto.getAccountId(), Double.valueOf(dto.getReloadAmount()));
				
				if( null == history){
						throw new WalletException(WalletTransactionConstants.ERROR_MSG_WRONG_INFO);
				}
				postHttpSettlementRequest(history, dto);
				transactionManager.commit(txstatus);
			}catch(Exception ex){
				try {
					transactionManager.rollback(txstatus);
				}catch (Exception e){
					LOGGER.error(e.getMessage(), e);
				}
				throw new WalletException(ex.getMessage(), ex);
			}
	}
	/**
	 * This method will call PG Auth Service
	 * @param dto
	 * @return
	 * @throws WalletException
	 */
	private ReloadMoneyDto postHttpServiceAuthRequest(ReloadMoneyDto dto) throws WalletException{
		PgResponse pgResponse = null;
		PgRequest authAPIRequest = new PgRequest();
		papulateAuthAPIRequestFromDto(authAPIRequest, dto);
		HttpServiceUtil.getAuthPgRequest(authAPIRequest);
		try{
			pgResponse = httpService.postAuthRequest(authAPIRequest);
			LOGGER.debug("Auth Txn Response Code :  " +pgResponse.getResponseCode());
			LOGGER.debug("Auth Txn Response Msg  :  " +pgResponse.getResponseMsg());
			
			if(pgResponse.getResponseCode().equals(HttpServiceConstants.RESPONSE_DECISION_SUCCESS)){
				pgResponse.setIsSuccess(Boolean.TRUE);
				dto.setIsReloadMoneySucc(Boolean.TRUE);
			}else{
				pgResponse.setIsSuccess(Boolean.FALSE);
				dto.setIsReloadMoneySucc(Boolean.FALSE);
			}
			
			/*save transaction details*/
			History history = WalletTransactionUtil.savePGAuthTransactionInHistory(authAPIRequest, pgResponse, 
					dto.getAccountId(), cryptService);
			history = pGService.saveTransaction(history);
			dto.setHistoryId(history.getId());
			dto.setPgResponseCode(pgResponse.getResponseCode());
			return dto;
		}catch (Exception ex){
			LOGGER.error(ex);
			throw new WalletException(ex.getMessage(), ex);
		}     
	}
	
	/**
	 * This method will call PG Settlement Service
	 * @param history
	 * @param dto
	 * @throws WalletException
	 */
	private void postHttpSettlementRequest(History history, ReloadMoneyDto dto) throws WalletException{
		PgRequest settlementAPIRequest = new PgRequest();
		SettlementResponse settlementResponse = null;
		Boolean isSuccess = false;
		WalletTransactionUtil.getPgRequestFromHistory(history, settlementAPIRequest);
		try{
			settlementResponse = httpService.postSettlementRequest(settlementAPIRequest);
			LOGGER.debug("Settlement Txn Response Code :  " + settlementResponse.getResponseCode());
			LOGGER.debug("Settlement Txn Response Msg  :  " + settlementResponse.getResponseMessage());
			
			if(settlementResponse.getResponseDecision() == null){
				isSuccess = Boolean.FALSE;
			}else if(settlementResponse.getResponseDecision().equals(HttpServiceConstants.RESPONSE_DECISION_SUCCESS)){
				isSuccess = Boolean.TRUE;
			}
			
			/*save transaction details*/
			History newHistory = WalletTransactionUtil.saveSettlementResponseInHistory(settlementAPIRequest,
									//changed getId() to getAccountId()
					settlementResponse, dto.getAccountId(), isSuccess);
			pGService.saveTransaction(newHistory);
		}catch (Exception ex){
			LOGGER.error(ex);
			throw new WalletException(ex.getMessage(), ex);
		}     
	}
	
	/**
	 * Populate Auth request object from DTO object 
	 * @param authAPIRequest
	 * @param dto
	 */
	private void papulateAuthAPIRequestFromDto(PgRequest authAPIRequest, ReloadMoneyDto dto){
		authAPIRequest.setAmount(CommonUtil.convertToString(dto.getReloadAmount()));
		authAPIRequest.setCurrency(dto.getCurrencyCode());
		authAPIRequest.setCardNumber(dto.getCardNumber());
		if(dto.getCardExpairyDate() != null){
			String[] ced = dto.getCardExpairyDate().split("/");
			authAPIRequest.setCardExpiryMonth(ced[0]);
			//test case date is set 3
			if(ced[1].length() == EXP_YEAR_LENGTH3){
				authAPIRequest.setCardExpiryYear(ced[1].substring(1, EXP_YEAR_LENGTH3));
			}else{
				authAPIRequest.setCardExpiryYear(ced[1].substring(2, EXP_YEAR_LENGTH4));
			}
		}
		authAPIRequest.setCardCVV(dto.getCvv());
		authAPIRequest.setNameOnCard(dto.getAccountOrCardHolderName());
	}
	
    /**
	 * This method will get list of non settlement transactions history id's
	 * @throws WalletException
	 */
	@Override
	public List<Long> getNonSettlementHistoryIds() throws WalletException {
		return reloadMoneyDao.getNonSettlementHistoryIds();
	}

	/**
	 * This method will call PG Settlement Service by Scheduler
	 * @throws WalletException
	 */
	@Override
	public void postNonSettlementTransactions() throws WalletException {
		LOGGER.info("Settlement transaction processed at :  " + new java.util.Date());
		Boolean isSuccess = false;
		try{
			List<Long> list = getNonSettlementHistoryIds();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					LOGGER.info("Got list size for reloadmoney settle "+list.size());
					try{
						History history = pGService.getHistory(list.get(i));
						LOGGER.info("Processed reload money settle for history id "+history.getId());
						PgRequest settlementAPIRequest = new PgRequest();
						SettlementResponse settlementResponse = null;
						WalletTransactionUtil.getPgRequestFromHistory(history,	settlementAPIRequest);
						settlementResponse = httpService.postSettlementRequest(settlementAPIRequest);
						
						LOGGER.info("Settlement Txn Response Decision :  " + settlementResponse.getResponseDecision());
						LOGGER.info("Settlement Txn Response Code :  " + settlementResponse.getResponseCode());
						LOGGER.info("Settlement Txn Response Msg  :  " + settlementResponse.getResponseMessage());
						
						if(settlementResponse.getResponseDecision() == null){
							isSuccess = Boolean.FALSE;
						}else if(settlementResponse.getResponseDecision().equals(HttpServiceConstants.RESPONSE_DECISION_SUCCESS)){
							isSuccess = Boolean.TRUE;
						}
						
						/*save transaction details*/
						History newHistory = WalletTransactionUtil.saveSettlementResponseInHistory(settlementAPIRequest, 
								settlementResponse, history.getAccountId(), isSuccess);
						pGService.saveTransaction(newHistory);
					}catch (Exception ex){
						LOGGER.error(ex.getMessage(), ex);
					}
				}
			}
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}	
	}
	
}
