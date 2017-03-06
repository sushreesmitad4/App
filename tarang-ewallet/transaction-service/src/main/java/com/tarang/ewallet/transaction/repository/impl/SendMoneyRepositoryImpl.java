package com.tarang.ewallet.transaction.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.dto.SelfTransferDto;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.email.service.EmailService;
import com.tarang.ewallet.email.service.EmailTemplateService;
import com.tarang.ewallet.email.util.EmailLitterals;
import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.NonRegisterWallet;
import com.tarang.ewallet.model.SendMoney;
import com.tarang.ewallet.model.SendMoneyRecurring;
import com.tarang.ewallet.model.SendMoneyTxn;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.transaction.dao.SendMoneyDao;
import com.tarang.ewallet.transaction.repository.SendMoneyRepository;
import com.tarang.ewallet.transaction.repository.TransactionWalletRepository;
import com.tarang.ewallet.transaction.repository.VelocityAndThresholdRepository;
import com.tarang.ewallet.transaction.util.DestinationTypes;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionStatus;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.transaction.util.WalletTransactionUtil;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;


public class SendMoneyRepositoryImpl implements SendMoneyRepository, GlobalLitterals {
	
	private static final Logger LOGGER = Logger.getLogger(SendMoneyRepositoryImpl.class);
	
	private SendMoneyDao sendMoneyDao;
	
	private TransactionWalletRepository transactionWalletRepository;
	
	private VelocityAndThresholdRepository velocityAndThresholdRepository;
	
	private CommonService commonService;
	
	private EmailService emailService;
	
	private EmailTemplateService emailTemplateService;
	
	private FeeMgmtService feeMgmtService;
	
	private HibernateTransactionManager transactionManager;
	
	public SendMoneyRepositoryImpl(SendMoneyDao sendMoneyDao, TransactionWalletRepository transactionWalletRepository,
			VelocityAndThresholdRepository velocityAndThresholdRepository, CommonService commonService, 
			EmailService emailService, EmailTemplateService emailTemplateService,
			FeeMgmtService feeMgmtService, HibernateTransactionManager transactionManager){
		this.sendMoneyDao = sendMoneyDao;
		this.transactionWalletRepository = transactionWalletRepository;
		this.velocityAndThresholdRepository = velocityAndThresholdRepository;
		this.commonService = commonService;
		this.emailService = emailService;
		this.emailTemplateService = emailTemplateService;
		this.feeMgmtService = feeMgmtService;
		this.transactionManager = transactionManager;
	}
	
	@Override
	public SendMoneyDto createSendMoney(SendMoneyDto sendMoneyDto) throws WalletException {
		
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);   
		try{
			Boolean recurring =	false;
			if( null != sendMoneyDto.getRecurring()) {
				recurring = sendMoneyDto.getRecurring();
			}
			
			Long transactionId = null;
			Long status = null;
			Authentication receiverAuth = null;
			
			SendMoney sendMoney = WalletTransactionUtil.getSendMoney(sendMoneyDto);
			sendMoney = sendMoneyDao.createSendMoney(sendMoney);
			sendMoneyDto.setId(sendMoney.getId());
			
			if(sendMoney.getReceiverAuthId() != null){
			receiverAuth = commonService.getAuthentication(sendMoney.getReceiverMail(), getUserType(sendMoney.getReceiverType()));
			}
			if(null == receiverAuth || !(UserStatusConstants.LOCKED.equals(receiverAuth.getStatus()) || UserStatusConstants.DELETED.equals(receiverAuth.getStatus()) || UserStatusConstants.REJECTED.equals(receiverAuth.getStatus()))){
				Authentication sender = commonService.getAuthentication(sendMoney.getSenderAuthId());
				
				if(sender.isActive() && UserStatusConstants.APPROVED.equals(sender.getStatus())){
					
					if(!recurring){
						WalletTransaction transaction = WalletTransactionUtil.constructTransaction(sendMoneyDto);
						status = WalletTransactionStatus.PENDING;
						if(DestinationTypes.NON_REGISTERED_PERSON.equals(sendMoneyDto.getDestinationType())){
							/*Here always null value for non register person*/
							transaction.setPayee(null);
						}else{
							transaction.setPayee(sendMoneyDto.getReceiverAuthId());
						}
						try{
							transaction = transactionWalletRepository.initiateTransaction(transaction);
							transactionId = transaction.getId();
										
							SendMoneyTxn sendMoneyTxn = WalletTransactionUtil.getSendMoneyTxn(sendMoneyDto, transactionId, status);
							
							sendMoneyTxn = sendMoneyDao.createSendMoneyTxn(sendMoneyTxn);
						
							NonRegisterWallet nonRegisterWallet = WalletTransactionUtil.prepareNonRegisterWallet(sendMoneyDto, transactionId);
							/*create non reg wallet account*/
							if(DestinationTypes.NON_REGISTERED_PERSON.equals(sendMoneyDto.getDestinationType())){
								commonService.createNonRegisterWallet(nonRegisterWallet);
							}else{
								/*Settlement transaction*/
								sendMoneyDto.setTransactionId(transactionId);
								sendMoneyDto.setTransactionDate(transaction.getCreationDate());
								acceptReceiveMoney(sendMoneyDto);
							}
						}catch (Exception ex){
							LOGGER.error(ex.getMessage(), ex);
							try {
								transactionManager.rollback(txstatus);
							}catch (Exception e){
								LOGGER.error(e.getMessage(), e);
							}
							status = WalletTransactionStatus.FAILED;
							SendMoneyTxn sendMoneyTxn = WalletTransactionUtil.getSendMoneyTxn(sendMoneyDto, transactionId, status);
							
							sendMoneyTxn.setFailureMessage(WalletTransactionConstants.ERR_MSG_FAILS_TO_INITIATE_TRANSACTION);
							sendMoneyTxn.incrementRepeat();
							sendMoneyTxn = sendMoneyDao.createSendMoneyTxn(sendMoneyTxn);
							
							throw new WalletException(WalletTransactionConstants.ERR_CODE_FAILS_TO_INITIATE_TRANSACTION, ex);
						}
						
					}else {
						SendMoneyRecurring sendMoneyRecurring = WalletTransactionUtil.getSendMoneyRecurring(sendMoneyDto, sendMoney.getId());
						sendMoneyRecurring = sendMoneyDao.createSendMoneyRecurring(sendMoneyRecurring);
					}
				}else if(!sender.isActive()){
					throw new WalletException(WalletTransactionConstants.USER_INACTIVE);
				}else {
					throw new WalletException(WalletTransactionConstants.USER_NOT_APPROVED);
				}
			}else if(UserStatusConstants.DELETED.equals(receiverAuth.getStatus())){
				throw new WalletException(WalletTransactionConstants.USER_DELETED);
			}else if(UserStatusConstants.LOCKED.equals(receiverAuth.getStatus())){
				throw new WalletException(WalletTransactionConstants.USER_LOCKED);
			}else {
				throw new WalletException(WalletTransactionConstants.USER_REJECTED);
			}
			transactionManager.commit(txstatus);
			if(!recurring){ 
				if(DestinationTypes.NON_REGISTERED_PERSON.equals(sendMoneyDto.getDestinationType())){
					sendEmailNotificationForNonRegPerson(sendMoneyDto, EmailServiceConstants.SEND_MONEY_MAIL_NON_REG_PERSON);
				}else{
					sendEmailNotificationForSendMoney(sendMoneyDto, EmailServiceConstants.SEND_MONEY_MAIL);
				}
			}
			return sendMoneyDto;
		}catch(WalletException e){
			LOGGER.error(e.getMessage(), e);
			throw new WalletException(e.getMessage(), e);
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			/*No roleback, track every record*/
			throw new WalletException(WalletTransactionConstants.FAILS_TOCREATE_SENDMONEY_REQUEST, ex);
		}
	}
	
	@Override
	public List<SendMoneyDto> createSendMoneyToMultiple(List<SendMoneyDto> listofTrans) throws WalletException {
		List<SendMoneyDto> failedDtos=new ArrayList<SendMoneyDto>();
		
		for (int i = 0; i < listofTrans.size(); i++) {
			try{
				createSendMoney(listofTrans.get(i));
			}catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				failedDtos.add(listofTrans.get(i));
			}
		}
		return failedDtos;
	}
	
	@Override
	public Boolean validateThresholdLimit(SendMoneyDto sendMoneyDto) throws WalletException{
		VelocityAndThreshold velocity = null;
		velocity = velocityAndThresholdRepository.getThreshold(sendMoneyDto.getCountryId(), 
				sendMoneyDto.getRequestedCurrency(), sendMoneyDto.getTransactionType(), 
				sendMoneyDto.getSenderUserType());
		if(null == velocity){
			return true;
		}else{
			return velocity.getMinimumamount() <= sendMoneyDto.getRequestedAmount() 
					&& velocity.getMaximumamount() >= sendMoneyDto.getRequestedAmount();
		}
		
	}
	
	@Override
	public Boolean validateThresholdLimit(Long countryId, Long currencyId, Long transType, Double transAmount, Long userType) throws WalletException{
		VelocityAndThreshold velocity = null;
		velocity = velocityAndThresholdRepository.getThreshold(countryId, 
				currencyId, transType, userType);
		if(null == velocity){
			return true;
		}else{
			return velocity.getMinimumamount() <= transAmount && 
					velocity.getMaximumamount() >= transAmount;
		}
	}
	
	@Override
	public Boolean validateThresholdLimitForSelfTransfer(Long countryId,
			Long currencyId, Long transType, Double transAmount, Long userType)
			throws WalletException {
		VelocityAndThreshold velocity = null;
		velocity = velocityAndThresholdRepository.getThreshold(countryId, 
				currencyId, transType, userType);
		if(null == velocity){
			return true;
		}else{
			return velocity.getMinimumamount() <= transAmount 
					&& velocity.getMaximumamount() >= transAmount;
		}
	}
	
	
	/* 
	 * @Kedar
	 * This method will called when receiver has accepted money for send money request
	 */
	@Override
	public SendMoneyDto acceptReceiveMoney(SendMoneyDto sendMoneyDto) throws WalletException {
		
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		
		try{
			WalletTransaction transaction = transactionWalletRepository.settleTransaction(sendMoneyDto.getTransactionId());
			LOGGER.info(transaction.getId());
			
			SendMoneyTxn sendMoneyTxn = sendMoneyDao.getSendMoneyTxn(sendMoneyDto.getTransactionId());
			sendMoneyDao.updateSendMoneyTxn(sendMoneyTxn.getId(), sendMoneyTxn.getTransactionId(), WalletTransactionStatus.SUCCESS);
			transactionManager.commit(txstatus);
		   /*There is no accept for send money, so no need to send acceptance mail
			sendEmailNotificationForReceiveMoney(sendMoneyDto, EmailServiceConstants.RECEIVE_MONEY_MAIL_ACCEPT);*/
			return sendMoneyDto;
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(CommonConstrain.ACCEPT_RECEIVE_MONEY_EXCEPTION, ex);
		}
	}
	
	/* 
	 * @Kedar
	 * This method will called when receiver has rejected money for send money request
	 */
	@Override
	public SendMoneyDto rejectReceiveMoney(SendMoneyDto sendMoneyDto) throws WalletException{
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			WalletTransaction transaction = transactionWalletRepository.rejectTransaction(sendMoneyDto.getTransactionId());
			LOGGER.info(transaction.getId());
			
			SendMoneyTxn sendMoneyTxn = sendMoneyDao.getSendMoneyTxn(sendMoneyDto.getTransactionId());
			sendMoneyTxn.setTransactionStatus(WalletTransactionStatus.REJECT);
			sendMoneyDao.updateSendMoneyTxn(sendMoneyTxn);
			transactionManager.commit(txstatus);
			sendEmailNotificationForSendMoney(sendMoneyDto, EmailServiceConstants.RECEIVE_MONEY_MAIL_REJECT);
		return sendMoneyDto;
		
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(CommonConstrain.REJECT_RECEIVE_MONEY_EXCEPTION, ex);
		}
	}
	
	/* 
	 * @Kedar
	 * This method will called to display receive money grid
	 */
	@Override
	public List<SendMoneyDto> getReceiveMoneyList(Long receiverAuthId) {
		return sendMoneyDao.getReceiveMoneyList(receiverAuthId);
	}
	
	
	/**
	 * This method will send confirmation mail for payment send to receiver i.e non reg person
	 * @param sendMoneyDto
	 * @throws WalletException 
	 */
	private void sendEmailNotificationForNonRegPerson(SendMoneyDto sendMoneyDto, String type) throws WalletException{
		Properties dvalues = new Properties();
		dvalues.put(EmailLitterals.PAYER_NAME, sendMoneyDto.getPayerName()!= null ? sendMoneyDto.getPayerName() : EMPTY_STRING);
		dvalues.put(EmailLitterals.PAYEE_AMOUNT, sendMoneyDto.getRequestedAmount() != null ? CommonUtil.getConvertedAmountInString(sendMoneyDto.getRequestedAmount()) : EMPTY_STRING );
		dvalues.put(EmailLitterals.PAYEE_CURRENCY, sendMoneyDto.getCurrencyCode() != null ? sendMoneyDto.getCurrencyCode() : EMPTY_STRING);
		dvalues.put(EmailLitterals.MESSAGE, sendMoneyDto.getMessage());
		sendEmailNotificationReceiveMoney(dvalues, sendMoneyDto.getLanguageId(), sendMoneyDto.getEmailId(), type);
	 }
	
	/**
	 * This method will send confirmation mail for payment send to receiver i.e customer or merchant
	 * @param sendMoneyDto
	 * @throws WalletException 
	 */
	private void sendEmailNotificationForSendMoney(SendMoneyDto sendMoneyDto, String type) throws WalletException{
		Properties dvalues = new Properties();
		dvalues.put(EmailLitterals.PAYER_NAME, sendMoneyDto.getPayerName()!= null ? sendMoneyDto.getPayerName() : EMPTY_STRING);
		dvalues.put(EmailLitterals.PAYEE_NAME, sendMoneyDto.getPayeeName()!= null ? sendMoneyDto.getPayeeName() : EMPTY_STRING);
		dvalues.put(EmailLitterals.PAYEE_AMOUNT, sendMoneyDto.getRequestedAmount()!= null? CommonUtil.getConvertedAmountInString(sendMoneyDto.getRequestedAmount()) : EMPTY_STRING );
		dvalues.put(EmailLitterals.PAYEE_CURRENCY, sendMoneyDto.getCurrencyCode() != null? sendMoneyDto.getCurrencyCode() : EMPTY_STRING);
		dvalues.put(EmailLitterals.MESSAGE, sendMoneyDto.getMessage());
		sendEmailNotificationReceiveMoney(dvalues, sendMoneyDto.getLanguageId(), sendMoneyDto.getEmailId(), type);
	 }
	
	/**
	 * This method will send payment failed notification to sender and receiver i.e customer or merchant
	 * @param sendMoneyDto
	 * @throws WalletException 
	 */
	private void sendEmailNotificationForSendMoneyFailed(SendMoney sendMoney, Authentication sender, Authentication receiver) throws WalletException{
		Long languageId = sendMoney.getLanguageId() != null ? sendMoney.getLanguageId(): CommonConstrain.DEFAULT_LANGUAGE;
		Properties dvalues = new Properties();
		dvalues.put(EmailLitterals.SENDER_NAME, sendMoney.getSenderName() != null ? sendMoney.getSenderName() : EMPTY_STRING);
		dvalues.put(EmailLitterals.RECEIVER_NAME, sendMoney.getReceiverName() != null ? sendMoney.getReceiverName() : EMPTY_STRING);
		dvalues.put(EmailLitterals.PAYEE_AMOUNT, sendMoney.getAmount() != null ? CommonUtil.getConvertedAmountInString(sendMoney.getAmount()) : EMPTY_STRING );
		dvalues.put(EmailLitterals.PAYEE_CURRENCY, sendMoney.getCurrencyCode() != null ? sendMoney.getCurrencyCode() : EMPTY_STRING);
		dvalues.put(EmailLitterals.PAY_DATE, sendMoney.getRequestDate() != null ? DateConvertion.dateTimeToString(sendMoney.getRequestDate()) : EMPTY_STRING);
		
		sendEmailNotificationReceiveMoney(dvalues, languageId, sender.getEmailId(), EmailServiceConstants.RECURRING_PAYMENT_FAILURE_SENDER);
		if(null == receiver){
			sendEmailNotificationForSendMoneyFailedToNonRegPerson(sendMoney); 
		}else{
			sendEmailNotificationReceiveMoney(dvalues, languageId, receiver.getEmailId(), EmailServiceConstants.RECURRING_PAYMENT_FAILURE_RECEIVER);
		}
	 }
	
	/**
	 * This method will send payment failed notification to receiver i.e non registar person 
	 * @param sendMoneyDto
	 * @throws WalletException 
	 */
	private void sendEmailNotificationForSendMoneyFailedToNonRegPerson(SendMoney sendMoney) throws WalletException{
		Long languageId = sendMoney.getLanguageId() != null ? sendMoney.getLanguageId(): CommonConstrain.DEFAULT_LANGUAGE;
		Properties dvalues = new Properties();
		dvalues.put(EmailLitterals.SENDER_NAME, sendMoney.getSenderName() != null ? sendMoney.getSenderName() : EMPTY_STRING);
		dvalues.put(EmailLitterals.PAYEE_AMOUNT, sendMoney.getAmount() != null ? CommonUtil.getConvertedAmountInString(sendMoney.getAmount()) : EMPTY_STRING );
		dvalues.put(EmailLitterals.PAYEE_CURRENCY, sendMoney.getCurrencyCode() != null ? sendMoney.getCurrencyCode() : EMPTY_STRING);
		dvalues.put(EmailLitterals.PAY_DATE, sendMoney.getRequestDate() != null ? DateConvertion.dateTimeToString(sendMoney.getRequestDate()) : EMPTY_STRING);
		sendEmailNotificationReceiveMoney(dvalues, languageId, sendMoney.getReceiverMail(), EmailServiceConstants.RECURRING_PAYMENT_FAILURE_NON_REGISTERED_PERSON);
	}
	/**
	 * Send mail notification
	 * @param dvalues
	 * @param languageId
	 * @param email
	 * @param type
	 * @throws WalletException
	 */
	private void sendEmailNotificationReceiveMoney(Properties dvalues, Long languageId, String email, String type) throws WalletException{
		try {
			Long langId = languageId != null ? languageId : 1L;
			String emailTo = email;
			String mailMsg = null;
			String mailSubject = null;
			mailMsg = emailTemplateService.getEmailBodyMessage(langId, type, dvalues);
			mailSubject = emailTemplateService.getEmailSubjectMessage(langId, type, dvalues);
			emailService.sendMessage(emailTo, mailMsg, mailSubject);
		}catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
		}catch(Exception e){
			LOGGER.error(EmailServiceConstants.EMAIL_SEND_FAILED, e);
		}
	 }
	
	@Override
	public void updateSendMoneyForNonRegisters(Long txnId, Long authId) throws WalletException {
		SendMoneyTxn sendMoneyTxn = sendMoneyDao.getSendMoneyTxn(txnId);
			if(sendMoneyTxn != null){
				sendMoneyTxn.setTransactionStatus(WalletTransactionStatus.SUCCESS);
				sendMoneyDao.updateSendMoneyTxn(sendMoneyTxn);
				
				SendMoney sendMoney = sendMoneyDao.getSendMoney(sendMoneyTxn.getSendMoneyId());
				if(sendMoney != null){
					sendMoney.setReceiverAuthId(authId);
					sendMoneyDao.updateSendMoney(sendMoney);
				}
			}
			transactionWalletRepository.updateTransactionForNonRegisters(txnId, authId);
	}

	@Override
	public SelfTransferDto createSelfMoney(SelfTransferDto selfTransferDto)
			throws WalletException {

		
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);   
		try{
				WalletTransaction transaction = WalletTransactionUtil.constructTransactionForSelf(selfTransferDto);
				transaction = transactionWalletRepository.initiateTransaction(transaction);
				transaction = transactionWalletRepository.settleTransaction(transaction.getId());
				//need  to call settle transaction
				transactionManager.commit(txstatus);
			return selfTransferDto;
		}catch (Exception ex){
			LOGGER.error(ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(WalletTransactionConstants.FAILS_TOCREATE_SENDMONEY_REQUEST, ex);
		}
	
	}

	@Override
	public SendMoneyTxn startSendMoneyRecurring(Long sendMoneyId, Integer occurences) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);   
		try{
			Long transactionId = null;
			Long status = null;
			String failureMsg = null;
			Authentication receiver = null;
			Authentication sender = null;
			
			SendMoney sendMoney = sendMoneyDao.getSendMoney(sendMoneyId);
			
			if(sendMoney.getReceiverAuthId() != null){
				receiver = commonService.getAuthentication(sendMoney.getReceiverMail(), getUserType(sendMoney.getReceiverType()));
			}
			
			if(null == receiver || !(UserStatusConstants.LOCKED.equals(receiver.getStatus()) || UserStatusConstants.DELETED.equals(receiver.getStatus()) || UserStatusConstants.REJECTED.equals(receiver.getStatus()))){
				sender = commonService.getAuthentication(sendMoney.getSenderAuthId());
				if(sender.isActive()){
					
					if(UserStatusConstants.APPROVED.equals(sender.getStatus())){
						
						if(!calcuateUserWalletAvailableBalance(sendMoney)){
							
							try{
								status = WalletTransactionStatus.PENDING;
								WalletTransaction transaction = WalletTransactionUtil.constructTransaction(sendMoney);
								if(DestinationTypes.NON_REGISTERED_PERSON.equals(sendMoney.getReceiverType())){
									if(receiver != null){
										transaction.setPayee(receiver.getId());
										if(sender.getUserType().equals(CUSTOMER_USER_TYPE)){
											transaction.setTypeOfTransaction(WalletTransactionTypes.P_TO_P);
										}else {
											transaction.setTypeOfTransaction(WalletTransactionTypes.M_TO_P);
										}
									}else {
										//here always null value for non register person
										transaction.setPayee(null);
									}
								}else{
									transaction.setPayee(sendMoney.getReceiverAuthId());
								}
										
								transaction = transactionWalletRepository.initiateTransaction(transaction);
								transactionId = transaction.getId();
										
								NonRegisterWallet nonRegisterWallet = WalletTransactionUtil.prepareNonRegisterWallet(sendMoney, transactionId);
								//create non reg wallet account
								if(DestinationTypes.NON_REGISTERED_PERSON.equals(sendMoney.getReceiverType())){
									//checking for user is registered or not
									Authentication authentication = commonService.getAuthentication(sendMoney.getReceiverMail(), CUSTOMER_USER_TYPE);
									if(null == authentication){
										commonService.createNonRegisterWallet(nonRegisterWallet);
									}else{
										WalletTransaction settleTxn = transactionWalletRepository.settleTransaction(transactionId);
										LOGGER.info(" transaction db id " + settleTxn.getId());
									}
								}else{
									//Settlement transaction
									WalletTransaction settleTxn = transactionWalletRepository.settleTransaction(transactionId);
									LOGGER.info(" transaction db id " + settleTxn.getId());
								}
								status = WalletTransactionStatus.SUCCESS;
							}catch (Exception ex){
								status = WalletTransactionStatus.FAILED;
								failureMsg = "fails.tocreate.sendmoney.request.by.scheduler";
								// add error message for showing technical reason fail
								LOGGER.error(ex.getMessage(), ex);
								try {
									transactionManager.rollback(txstatus);
								}catch (Exception e){
									LOGGER.error(e.getMessage(), e);
								}
							}
						}else{
							// add error message for showing insufficient balance
							status = WalletTransactionStatus.FAILED;
							failureMsg = WalletTransactionConstants.IN_SUFFICIENT_BALANCE;
						}
					}else{
						status = WalletTransactionStatus.FAILED;
						failureMsg = WalletTransactionConstants.USER_NOT_APPROVED;
					}
				}else{
					status = WalletTransactionStatus.FAILED;
					failureMsg = WalletTransactionConstants.USER_INACTIVE;
				}
			}else if(UserStatusConstants.DELETED.equals(receiver.getStatus())){
				status = WalletTransactionStatus.FAILED;
				failureMsg = WalletTransactionConstants.USER_DELETED;
			}else if(UserStatusConstants.LOCKED.equals(receiver.getStatus())){
				// add error message for receiver account locked
				status = WalletTransactionStatus.FAILED;
				failureMsg = WalletTransactionConstants.USER_LOCKED;	
			}else {
				status = WalletTransactionStatus.FAILED;
				failureMsg = WalletTransactionConstants.USER_REJECTED;	
			}
			SendMoneyTxn sendMoneyTxn = WalletTransactionUtil.getSendMoneyTxn(occurences, transactionId, status);
			sendMoneyTxn.setSendMoneyId(sendMoney.getId());
			if(failureMsg != null){
				sendMoneyTxn.setFailureMessage(failureMsg);
				sendMoneyTxn.incrementRepeat();
			}
			sendMoneyTxn = sendMoneyDao.createSendMoneyTxn(sendMoneyTxn);
			transactionManager.commit(txstatus);
			//Send payment failed notification to customer and merchant
			if(WalletTransactionStatus.FAILED.equals(status)){
				sendEmailNotificationForSendMoneyFailed(sendMoney, sender, receiver);
			}
			return sendMoneyTxn;
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			//No roleback, track every record
		}
		return null;
	}
	
	/**
	 * @param sendMoney
	 * @return
	 * @throws WalletException
	 */
	private Boolean calcuateUserWalletAvailableBalance(SendMoney sendMoney) throws WalletException{
		UserWallet userWallet = commonService.getUserWallet(sendMoney.getSenderAuthId(), sendMoney.getCurrency());
		Boolean negativeBalance = false;
		Double deductions = 0.0;
		Double reqAmount = sendMoney.getAmount();
		if(userWallet != null){
			deductions = feeMgmtService.calcuateDeductions(reqAmount, sendMoney.getCountryId(), 
					sendMoney.getCurrency(), sendMoney.getTransactionType(), Boolean.FALSE);
		}
		if(null == userWallet || userWallet.getAmount() < reqAmount + deductions ){
			negativeBalance = true;
		}
		return negativeBalance;
	}

	@Override
	public SendMoneyDto getSendMoneyById(Long id) throws WalletException {
		SendMoneyDto sendMoneyDto = new SendMoneyDto();
		SendMoney sendMoney = sendMoneyDao.getSendMoney(id);
		SendMoneyRecurring sendMonetRecurring = sendMoneyDao.getSendMoneyRecurringBySendMoneyId(sendMoney.getId());
		sendMoneyDto.setActualAmount(sendMoney.getAmount());
		sendMoneyDto.setActualCurrency(sendMoney.getCurrency());
		sendMoneyDto.setMessage(sendMoney.getMessage());
		sendMoneyDto.setEmailId(sendMoney.getReceiverMail());
		sendMoneyDto.setFrequency(sendMonetRecurring.getFrequency());
		sendMoneyDto.setTotalOccurences(sendMonetRecurring.getTotalOccurences());
		sendMoneyDto.setId(id);
		return sendMoneyDto;
	}

	@Override
	public SendMoneyRecurring updateSendMoneyRecurring(SendMoneyRecurring sendMoneyRecurring) throws WalletException {
		SendMoneyRecurring sMRecurring = sendMoneyDao.getSendMoneyRecurringBySendMoneyId(sendMoneyRecurring.getSendMoneyId());
		sMRecurring.setFrequency(sendMoneyRecurring.getFrequency());
		sMRecurring.setTotalOccurences(sendMoneyRecurring.getTotalOccurences());
		sMRecurring.setToDate(sendMoneyRecurring.getToDate());
		return sendMoneyDao.updateSendMoneyRecurring(sMRecurring);				
	}
	
	@Override
	public Boolean isJobNameExist(Long senderAuthId, String jobName) {
		return sendMoneyDao.isJobNameExist(senderAuthId, jobName);
	}
	
	private String getUserType(Long receivertype){
		String usertype = null ;
		if(receivertype.equals(DestinationTypes.REGISTERED_PERSON)){
			usertype = CUSTOMER_USER_TYPE;
		}else if(receivertype.equals(DestinationTypes.MERCHANT)){
			usertype = MERCHANT_USER_TYPE;
		}else if(receivertype.equals(DestinationTypes.NON_REGISTERED_PERSON)){
			usertype = CUSTOMER_USER_TYPE;
		}
		return usertype;
	}

	

}