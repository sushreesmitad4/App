package com.tarang.ewallet.transaction.repository.impl;

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
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.email.service.EmailService;
import com.tarang.ewallet.email.service.EmailTemplateService;
import com.tarang.ewallet.email.util.EmailLitterals;
import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Customer;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.model.RequestMoney;
import com.tarang.ewallet.model.Tax;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.transaction.dao.RequestMoneyDao;
import com.tarang.ewallet.transaction.repository.RequestMoneyRepository;
import com.tarang.ewallet.transaction.repository.VelocityAndThresholdRepository;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.transaction.util.WalletTransactionUtil;
import com.tarang.ewallet.util.FeeUtil;
import com.tarang.ewallet.util.GlobalLitterals;


public class RequestMoneyRepositoryImpl implements RequestMoneyRepository, GlobalLitterals {
	
	private static final Logger LOGGER = Logger.getLogger(RequestMoneyRepositoryImpl.class);
	
	private RequestMoneyDao requestMoneyDao;
	
	private CommonService commonService;
	
	private TransactionWalletService transactionWalletService;
	
	private FeeMgmtService feeMgmtService;
	
	private HibernateTransactionManager transactionManager;
	
	private VelocityAndThresholdRepository velocityAndThresholdRepository;
	
	private EmailService emailService;
	
	private EmailTemplateService emailTemplateService;
	
	private CustomerService customerService;
	
	RequestMoneyRepositoryImpl(RequestMoneyDao transactionDao, HibernateTransactionManager transactionManager, 
			CommonService commonService, TransactionWalletService transactionWalletService, 
			VelocityAndThresholdRepository velocityAndThresholdRepository, FeeMgmtService feeMgmtService, 
			EmailService emailService, EmailTemplateService emailTemplateService, CustomerService customerService){
		this.requestMoneyDao = transactionDao;
		this.transactionManager = transactionManager;
		this.commonService = commonService;
		this.transactionWalletService = transactionWalletService;
		this.velocityAndThresholdRepository = velocityAndThresholdRepository;
		this.feeMgmtService = feeMgmtService;
		this.emailService = emailService;
		this.emailTemplateService = emailTemplateService;
		this.customerService = customerService;
	}
	
	@Override
	public RequestMoney createRequestMoney(RequestMoneyDto requestMoneyDto) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			//get payer auth id 
			Authentication requesterAuth = commonService.getAuthentication(requestMoneyDto.getRequesterEmail(), GlobalLitterals.CUSTOMER_USER_TYPE);
			//get payee auth id
			Authentication responserAuth = commonService.getAuthentication(requestMoneyDto.getResponserEmail(), GlobalLitterals.CUSTOMER_USER_TYPE);
			if(null == requesterAuth || null == responserAuth){
				throw new WalletException(CommonConstrain.CUSTOMER_EMAILID_NOT_EXIST);
			}
			RequestMoney requestMoney = WalletTransactionUtil.getRequestMoney(requestMoneyDto, requesterAuth.getId(), responserAuth.getId());
			requestMoneyDao.createRequestMoney(requestMoney);
			transactionManager.commit(txstatus);
			requestMoneyDto.setAmount(requestMoney.getAmount());
			sendEmailNotificationForRequestMoney(requestMoneyDto, EmailServiceConstants.RECEIVE_MONEY_MAIL_PENDING);
			return requestMoney;
		}catch (WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}catch (Exception ex){
			LOGGER.error(ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(CommonConstrain.RECEIVEMONEY_CREATE_FAILS_EXCEPTION, ex);
		}
	}

	@Override
	public RequestMoneyDto getRequestMoney(Long id) throws WalletException {
		RequestMoneyDto requestMoneyDto = null;
		RequestMoney requestMoney = requestMoneyDao.getRequestMoney(id);
		requestMoneyDto = WalletTransactionUtil.getReceiveMoneyDtoFromModelObject(requestMoney);
		Customer customer = customerService.getCustomerByAuthId(requestMoney.getResponserId());
		CustomerDto customerDto = customerService.getCustomerDto(customer.getId());
		requestMoneyDto.setCountryId(customerDto.getCountry());
		getUserWalletAmountAndFeeTax(requestMoneyDto);
		return requestMoneyDto;
	}

	@Override
	public List<RequestMoneyDto> getRequestMoneyList(Long authId) throws WalletException {
		return requestMoneyDao.getRequestMoneyDtoList(authId);
	}

	@Override
	public RequestMoney updateRequestMoney(RequestMoneyDto requestMoneyDto) throws WalletException {
		RequestMoney requestMoney = requestMoneyDao.getRequestMoney(requestMoneyDto.getId());
		requestMoney.setStatus(requestMoneyDto.getStatus());
		requestMoney.setResponserMsg(requestMoneyDto.getResponserMsg());
		return requestMoneyDao.updateRequestMoney(requestMoney);
	}

	@Override
	public RequestMoney acceptRequestMoney(RequestMoneyDto requestMoneyDto) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		RequestMoney requestMoney = null;
		try{
			WalletTransaction txone = new WalletTransaction();
			WalletTransactionUtil.getTransactionFromRequestMoney(requestMoneyDto, txone);
			UserWallet userWallet = commonService.getUserWallet(requestMoneyDto.getResponserId(), requestMoneyDto.getCurrencyId());
			if(userWallet == null){
				commonService.createUserWallet(requestMoneyDto.getResponserId(), requestMoneyDto.getCurrencyId());
			}
			if(!validateThresholdLimit(requestMoneyDto)){
				throw new WalletException(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT);
			}
			validateUserWalletAmountForAcceptTxn(userWallet.getAmount(), requestMoneyDto);
			
			WalletTransaction txtwo = transactionWalletService.initiateTransaction(txone);
			WalletTransaction transaction = transactionWalletService.settleTransaction(txtwo.getId());
			Long transactionId = transaction.getId();
			Long attempts = requestMoneyDto.getAttempts();
			
			requestMoney = requestMoneyDao.getRequestMoney(requestMoneyDto.getId());
			requestMoney.setStatus(requestMoneyDto.getStatus());
			requestMoney.setResponserMsg(requestMoneyDto.getResponserMsg());
			requestMoney.setTransactionId(transactionId);
			requestMoney.setAttempts(++attempts);
			requestMoney = requestMoneyDao.updateRequestMoney(requestMoney);
			transactionManager.commit(txstatus);
			requestMoneyDto.setAmount(requestMoney.getAmount());
			sendEmailNotificationForRequestMoney(requestMoneyDto, EmailServiceConstants.RECEIVE_MONEY_MAIL_ACCEPT);
		}catch (WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(WalletTransactionConstants.ERROR_ACCEPT_REQUEST_MONEY, ex);
		}
		return requestMoney;
	}
	
	@Override
	public RequestMoney rejectRequestMoney(RequestMoneyDto requestMoneyDto) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		RequestMoney requestMoney = null;
		try{
			requestMoney = requestMoneyDao.getRequestMoney(requestMoneyDto.getId());
			requestMoney.setStatus(requestMoneyDto.getStatus());
			requestMoney.setResponserMsg(requestMoneyDto.getResponserMsg());
			requestMoney = requestMoneyDao.updateRequestMoney(requestMoney);
			
			transactionManager.commit(txstatus);
			requestMoneyDto.setAmount(requestMoney.getAmount());
			sendEmailNotificationForRequestMoney(requestMoneyDto, EmailServiceConstants.RECEIVE_MONEY_MAIL_REJECT);
		}catch (WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(WalletTransactionConstants.ERROR_REJECT_REQUEST_MONEY, ex);
		}
		return requestMoney;
	}

	@Override
	public RequestMoney cancelRequestMoney(RequestMoneyDto requestMoneyDto) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		RequestMoney requestMoney = null;
		try{
			requestMoney = requestMoneyDao.getRequestMoney(requestMoneyDto.getId());
			requestMoney.setStatus(requestMoneyDto.getStatus());
			requestMoney = requestMoneyDao.updateRequestMoney(requestMoney);
			transactionManager.commit(txstatus);
			requestMoneyDto.setAmount(requestMoney.getAmount());
			sendEmailNotificationForRequestMoney(requestMoneyDto, EmailServiceConstants.RECEIVE_MONEY_MAIL_CANCEL);
		}catch (WalletException ex){
			LOGGER.error(ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(ex.getMessage(), ex);
		}catch (Exception ex){
			LOGGER.error(ex);
			try {
				transactionManager.rollback(txstatus);
			}catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(WalletTransactionConstants.ERROR_CANCEL_REQUEST_MONEY, ex);
		}
		return requestMoney;
	}
	
	private Boolean validateThresholdLimit(RequestMoneyDto requestMoneyDto) throws WalletException{
		VelocityAndThreshold velocity = null;
		velocity = velocityAndThresholdRepository.getThreshold(requestMoneyDto.getCountryId(), 
				requestMoneyDto.getCurrencyId(), WalletTransactionTypes.P_TO_P, GlobalLitterals.CUSTOMER_USER_TYPE_ID);
		if(null == velocity){
			return true;
		}else{  
			return velocity.getMinimumamount() <= requestMoneyDto.getAmount()
					&& velocity.getMaximumamount() >= requestMoneyDto.getAmount();
		}
		
	}
	private void validateUserWalletAmountForAcceptTxn(Double balance, RequestMoneyDto requestMoneyDto) throws WalletException{
        
        Long payercountryId = requestMoneyDto.getCountryId();
        FeeSlab feeSlab = null;
        Double payerFee = ZERO_DOUBLE;
        Double payerTax = ZERO_DOUBLE;
        
        if(payercountryId != null && requestMoneyDto.getCurrencyId() != null && requestMoneyDto.getAmount() != null){
        	feeSlab = FeeUtil.getFeeSlabs(feeMgmtService.getFee(WalletTransactionTypes.P_TO_P, payercountryId, 
        			requestMoneyDto.getCurrencyId()), requestMoneyDto.getAmount());
        }
        
       if(feeSlab != null){
            if(feeSlab.getPercentageOfSender() != null && feeSlab.getPercentageOfSender() > ZERO_DOUBLE){
                payerFee = requestMoneyDto.getAmount() * feeSlab.getPercentageOfSender() / PERCENTAGE_FACTOR;
            }
	          if(feeSlab.getFixedChargeSender() != null && feeSlab.getFixedChargeSender() > ZERO_DOUBLE){
	                payerFee += feeSlab.getFixedChargeSender();
	          }
                    
              if(payerFee > 0.0 && payercountryId != null){
                    Tax tax = feeMgmtService.getTaxByCountry(payercountryId);
                    if(tax != null){
                          payerTax = payerFee * tax.getPercentage() / PERCENTAGE_FACTOR;
                    }
              }       
        }
       
        if(balance < requestMoneyDto.getAmount()+payerFee+payerTax){
           throw new WalletException(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE);
        }
	}
	
	private void getUserWalletAmountAndFeeTax(RequestMoneyDto dto) throws WalletException{
		Double payerFee = ZERO_DOUBLE;
        Double payerTax = ZERO_DOUBLE;
        Long payercountryId = dto.getCountryId();
		FeeSlab feeSlab = null;
		
        UserWallet userWallet = commonService.getUserWallet(dto.getResponserId(), dto.getCurrencyId());
		if(userWallet == null){
			userWallet = commonService.createUserWallet(dto.getResponserId(), dto.getCurrencyId());
		}
		
		if(payercountryId != null && dto.getCurrencyId() != null && dto.getAmount() != null){
			feeSlab = FeeUtil.getFeeSlabs(feeMgmtService.getFee(WalletTransactionTypes.P_TO_P, payercountryId, 
					dto.getCurrencyId()), dto.getAmount());
		}
        
       if(feeSlab != null){
            if(feeSlab.getPercentageOfSender() != null && feeSlab.getPercentageOfSender() > ZERO_DOUBLE){
                payerFee = dto.getAmount() * feeSlab.getPercentageOfSender()/PERCENTAGE_FACTOR;
            }
            if(feeSlab.getFixedChargeSender() != null && feeSlab.getFixedChargeSender() > ZERO_DOUBLE){
            	payerFee += feeSlab.getFixedChargeSender();
            }
                    
            if(payerFee > 0.0 && payercountryId != null){
            	Tax tax = feeMgmtService.getTaxByCountry(payercountryId);
            	if(tax != null){
            		payerTax = payerFee*tax.getPercentage()/PERCENTAGE_FACTOR;
            	}
            }       
       }
       
       dto.setUserWalletBalance(userWallet.getAmount());
       dto.setTxnDeductedBalance(dto.getAmount() + payerFee + payerTax);
	}
	
	/**
	 * This method will send confirmation mail for pending, reject, cancel and accept receive money
	 * @param sendMoneyDto
	 * @throws WalletException 
	 */
	private void sendEmailNotificationForRequestMoney(RequestMoneyDto requestMoneyDto, String type) throws WalletException{
		Properties dvalues = new Properties();
		dvalues.put(EmailLitterals.PAYER_NAME, requestMoneyDto.getPayerName()!= null ? requestMoneyDto.getPayerName() : EMPTY_STRING);
		dvalues.put(EmailLitterals.PAYEE_NAME, requestMoneyDto.getPayeeName()!= null ? requestMoneyDto.getPayeeName() : EMPTY_STRING);
		dvalues.put(EmailLitterals.PAYEE_AMOUNT, requestMoneyDto.getAmount()!= null? CommonUtil.getConvertedAmountInString(requestMoneyDto.getAmount()) : EMPTY_STRING );
		dvalues.put(EmailLitterals.PAYEE_CURRENCY, requestMoneyDto.getCurrencyCode() != null? requestMoneyDto.getCurrencyCode() : EMPTY_STRING);
		sendEmailNotificationRequestMoney(dvalues, requestMoneyDto.getLanguageId(), requestMoneyDto.getRequesterEmail(), type);
	 }
	
	/**
	 * Send mail notification
	 * @param dvalues
	 * @param languageId
	 * @param email
	 * @param type
	 * @throws WalletException
	 */
	private void sendEmailNotificationRequestMoney(Properties dvalues, Long languageId, String email, String type) throws WalletException{
		try{
			Long langId = languageId != null ? languageId:1L;
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
}
