package com.tarang.ewallet.common.repository.impl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.common.dao.CommonDao;
import com.tarang.ewallet.common.repository.CommonRepository;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.FeedbackDto;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.email.service.EmailService;
import com.tarang.ewallet.email.service.EmailTemplateService;
import com.tarang.ewallet.email.util.EmailLitterals;
import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.model.Hints;
import com.tarang.ewallet.model.MasterAmountWallet;
import com.tarang.ewallet.model.MasterFeeWallet;
import com.tarang.ewallet.model.MasterTaxWallet;
import com.tarang.ewallet.model.NonRegisterWallet;
import com.tarang.ewallet.model.PasswordHistory;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.model.Preferences;
import com.tarang.ewallet.model.UserIP;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;


/**
 * @Author : kedarnathd
 * @Date : Oct 08, 2012
 * @Time : 10:09:24 PM
 * @Version : 1.0
 * @Comments: Repository Class to provide eWallet Services and it implements
 *            CommonRepository interface
 */
public class CommonRepositoryImpl implements CommonRepository {

	private static final Logger LOGGER = Logger.getLogger(CommonRepositoryImpl.class);
	
	private CommonDao commonDao;
	
	private CryptService cryptService;
	
	private EmailService emailService;
	
	private EmailTemplateService emailTemplateService;
	
	private UtilService utilService;

	private HibernateTransactionManager transactionManager;

	public CommonRepositoryImpl(CommonDao commonDao, EmailService emailService, CryptService cryptService, 
			EmailTemplateService emailTemplateService, HibernateTransactionManager transactionManager, 
			UtilService utilService) {
		LOGGER.debug( " CommonRepositoryImpl " );
		this.commonDao = commonDao;
		this.emailService = emailService;
		this.cryptService = cryptService;
		this.emailTemplateService = emailTemplateService;
		this.transactionManager = transactionManager;
		this.utilService = utilService;
	}

	@Override
	public Address createAddress(Address address) throws WalletException {
		LOGGER.debug(" createAddress ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Address addr = commonDao.createAddress(address);
			transactionManager.commit(txstatus);
			return addr;
		}catch (Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}     
	}

	@Override
	public Address getAddress(Long id) throws WalletException {
		LOGGER.debug(" getAddress ");
		return commonDao.getAddress(id);
	}

	@Override
	public void updateAddress(Address address) throws WalletException {
		LOGGER.debug(" updateAddress ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			commonDao.updateAddress(address);
			transactionManager.commit(txstatus);
		}catch(Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}
	}

	@Override
	public PersonName createPersonName(PersonName personName) throws WalletException {
		LOGGER.debug(" createPersonName ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			PersonName person = commonDao.createPersonName(personName);
			transactionManager.commit(txstatus);
			return person;
		}catch(Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}
	}

	@Override
	public PersonName getPersonName(Long id) throws WalletException {
		LOGGER.debug(" getPersonName ");
		return commonDao.getPersonName(id);
	}

	@Override
	public void updatePersonName(PersonName personName) throws WalletException {
		LOGGER.debug(" updatePersonName ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			commonDao.updatePersonName(personName);
			transactionManager.commit(txstatus);
		}catch(Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}
	}

	@Override
	public Authentication createAuthentication(Authentication authentication) throws WalletException {
		LOGGER.debug(" createAuthentication ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Authentication authen = commonDao.createAuthentication(authentication);
			PasswordHistory ph = new PasswordHistory();
			ph.setAuthentication(authen.getId());
			ph.setCreationDate( new Date());
			ph.setPassword(authen.getPassword());
			commonDao.createPasswordHistory(ph);
			transactionManager.commit(txstatus);
			return authen;
		}catch(Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}
	}
	
	@Override
	public Authentication createMobileAuthentication(Authentication authentication) throws WalletException {
		LOGGER.debug(" createAuthentication ");
		try{
			Authentication authen = commonDao.createAuthentication(authentication);
			PasswordHistory ph = new PasswordHistory();
			ph.setAuthentication(authen.getId());
			ph.setCreationDate( new Date());
			ph.setPassword(authen.getPassword());
			commonDao.createPasswordHistory(ph);
			return authen;
		}catch(Exception ex){
			throw new WalletException(ex.getMessage(), ex);
		}
	}

	@Override
	public Authentication getAuthentication(Long id) throws WalletException {
		LOGGER.debug(" getAuthentication ");
		return commonDao.getAuthentication(id);
	}
	
	@Override
	public Authentication getAuthentication(String email) throws WalletException{
		return commonDao.getAuthentication(email);
	}

	@Override
	public void updateAuthentication(Authentication authentication) throws WalletException {
		LOGGER.debug(" updateAuthentication ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			commonDao.updateAuthentication(authentication);
			transactionManager.commit(txstatus);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			if(ex instanceof ConstraintViolationException){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, ex);
			}
			if(ex.getMessage().contains(CommonConstrain.COULD_NOT_EXECUTE_JDBC_BATCH_UPDATE)){
				throw new WalletException(CommonConstrain.COULD_NOT_EXECUTE_JDBC_BATCH_UPDATE, ex);
			}
			throw new WalletException(ex.getMessage(), ex);
		}
	}
	
	/*This service method called for deleteAdminUser*/
	@Override
	public Boolean deleteAuthentication(Long authenticationId) throws WalletException {
		LOGGER.debug(" deleteAuthentication ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			 Boolean deleteFlag = commonDao.deleteAuthentication(authenticationId);
			 transactionManager.commit(txstatus);
			 return deleteFlag;
		}catch(Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}
	}

	@Override
	public PhoneNumber createPhone(PhoneNumber phoneNumber) throws WalletException {
		LOGGER.debug(" createPhone ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			PhoneNumber phone = commonDao.createPhone(phoneNumber);
			transactionManager.commit(txstatus);
			return phone;
		}catch(Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}
	}

	@Override
	public PhoneNumber getPhone(Long id) throws WalletException {
		LOGGER.debug(" getPhone ");
		return commonDao.getPhone(id);
	}

	@Override
	public void updatePhone(PhoneNumber phoneNumber) throws WalletException {
		LOGGER.debug(" updatePhone ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			commonDao.updatePhone(phoneNumber);
			transactionManager.commit(txstatus);
		}catch (Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		} 
	}

	@Override
	public Hints createHints(Hints hints) throws WalletException {
		LOGGER.debug(" createHints ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Hints hint = commonDao.createHints(hints);
			transactionManager.commit(txstatus);
			return hint;
		}catch (Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		} 
	}

	@Override
	public Hints getHints(Long id) throws WalletException {
		LOGGER.debug(" getHints ");
		return commonDao.getHints(id);
	}

	@Override
	public void updateHints(Hints hints) throws WalletException {
		LOGGER.debug(" updateHints ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			commonDao.updateHints(hints);
			transactionManager.commit(txstatus);
		}catch (Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		} 
	}
		
	@Override
	public Authentication getAuthentication(String email, String password, String userType, Long typeOfRequest) throws WalletException {
		
		Authentication authentication = null;
		/*if(GlobalLitterals.CUSTOMER_USER_TYPE.equalsIgnoreCase(userType)){
			PhoneNumber phoneNumber = null;
			Customer customer = null;
			phoneNumber = commonDao.getPhoneNumber(email);
			if(null == phoneNumber){
				throw new WalletException(CommonConstrain.PHONE_NUMBER_NOT_EXIST);
			}
			customer = customerDao.getCustomerByPhoneId(phoneNumber.getId());
			if(null == customer){
				throw new WalletException(CommonConstrain.UNIQUE_CUSTOMER_EXCEPTION);
			}
			return getCustomerDto(customer.getId());
			//authentication = commonDao.getAuthenticationByphoneNo(email, userType);
		}else{
			authentication = commonDao.getAuthentication(email, userType);
		}*/
		authentication = commonDao.getAuthentication(email, userType);
	  
		if(null == authentication){
			throw new WalletException(CommonConstrain.LOGIN_ERROR_INVALID_USER);
		}
		Date lastLogin = authentication.getLastLogin();
		if (authentication != null) {
			if(authentication.getUserType() != null && !authentication.getUserType().equals(GlobalLitterals.ADMIN_USER_TYPE)
					&& authentication.isEmailVarification() != CommonConstrain.TRUE){
				throw new WalletException(CommonConstrain.EMAIL_VARIFICATION_NOT_DONE);
	   }
	     
	   if (authentication.getPassword().equals(cryptService.encryptData(password))) {
	    
		   Boolean isBlocked = authentication.isBlocked() != null ? authentication.isBlocked() : false;
		   Boolean isActive = authentication.isActive() != null ? authentication.isActive() : false;
		   Long status = authentication.getStatus()!= null?authentication.getStatus() : new Long(0);
		   if(isBlocked) {
			   throw new WalletException(CommonConstrain.USER_BLOCK);
		   }else if(!isActive){
			   throw new WalletException(CommonConstrain.ACCOUNT_DEACTIVE);
		   }else if(UserStatusConstants.DELETED.equals(status)){
			   throw new WalletException(CommonConstrain.USER_ACCOUNT_DELETED);
		   }else if(UserStatusConstants.REJECTED.equals(status)){
			   throw new WalletException(CommonConstrain.ACCOUNT_REJECTED);
		   }
		   else{
			   /*Allow to update if the user has login in second time. In case of
			    * first time login need to update in change password call.*/
			   if(!authentication.isResetPassword()){
				   authentication.setAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
				   authentication.setLastLogin(new Date());
				   //authentication.setLoginStatus(CommonConstrain.TRUE);
				   authentication.setTypeOfRequest(typeOfRequest);
				   updateAuthentication(authentication);
			   }
			   //if block ends
		   }
	   }else if (GlobalLitterals.CUSTOMER_USER_TYPE.equalsIgnoreCase(userType) && authentication.getmPin().equals(cryptService.encryptData(password))) {
		    
			   Boolean isBlocked = authentication.isBlocked() != null ? authentication.isBlocked() : false;
			   Boolean isActive = authentication.isActive() != null ? authentication.isActive() : false;
			   Long status = authentication.getStatus()!= null?authentication.getStatus() : new Long(0);
			   if(isBlocked) {
				   throw new WalletException(CommonConstrain.USER_BLOCK);
			   }else if(!isActive){
				   throw new WalletException(CommonConstrain.ACCOUNT_DEACTIVE);
			   }else if(UserStatusConstants.DELETED.equals(status)){
				   throw new WalletException(CommonConstrain.USER_ACCOUNT_DELETED);
			   }else if(UserStatusConstants.REJECTED.equals(status)){
				   throw new WalletException(CommonConstrain.ACCOUNT_REJECTED);
			   }
			   else{
				   /*Allow to update if the user has login in second time. In case of
				    * first time login need to update in change password call.*/
				   if(!authentication.isResetPassword()){
					   authentication.setAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
					   authentication.setLastLogin(new Date());
					   //authentication.setLoginStatus(CommonConstrain.TRUE);
					   authentication.setTypeOfRequest(typeOfRequest);
					   updateAuthentication(authentication);
				   }
				   //if block ends
			   }
		   } else {
		   /*Password does not match*/
	    if (CommonUtil.validateAttempts(authentication.getAttempts(),
	    		utilService.getLoginAttemptsLimit())) {
	    	authentication.setBlocked(CommonConstrain.TRUE);
	    	updateAuthentication(authentication);
	    		throw new WalletException(CommonConstrain.USER_BLOCK);
	    }else{ 
	    	Integer attemps = authentication.getAttempts();
	    	authentication.setAttempts(attemps+1);
	    	updateAuthentication(authentication);
	    		throw new WalletException(CommonConstrain.PASSWORD_MATCH_FAIL);
	    }
	   }
	   if(null == lastLogin){
		   authentication.setLastLogin(new Date());
	   }else{
		   authentication.setLastLogin(lastLogin);
	   }
	  } 
	  return authentication;
	 }
	
	@Override
	public void sendRegistrationMail(String name, Long authenticationId, String userType, Long languageId)
			throws WalletException {
		try{
			LOGGER.debug(" sendRegistrationMail ");
			Authentication authentication = commonDao.getAuthentication(authenticationId);
			
			Long langId = languageId != null ? languageId : CommonConstrain.DEFAULT_LANGUAGE;
			String emailTo = authentication.getEmailId();
			Properties dvalues = new Properties();
			String mailMsg = null;
			String mailSubject = null;
			String type = null;
			if(userType.equalsIgnoreCase(GlobalLitterals.CUSTOMER_USER_TYPE)){
				dvalues.put(EmailLitterals.CUSTOMER_NAME, name);
				dvalues.put(EmailLitterals.AUTHENTICATION_ID, authenticationId.toString() );
				type = EmailServiceConstants.CUSTOMER_REGISTRATION;
			}else if(userType.equalsIgnoreCase(GlobalLitterals.MERCHANT_USER_TYPE)) {
				dvalues.put(EmailLitterals.MERCHANT_NAME, name);
				dvalues.put(EmailLitterals.AUTHENTICATION_ID, authenticationId.toString() );
				type = EmailServiceConstants.MERCHANT_REGISTRATION;
			}
			mailMsg = emailTemplateService.getEmailBodyMessage(langId, type, dvalues);
			mailSubject = emailTemplateService.getEmailSubjectMessage(langId, type, dvalues);
			
			emailService.sendMessage(emailTo, mailMsg, mailSubject);
		}catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
			throw ex;
		}catch(Exception e){
			throw new WalletException(EmailServiceConstants.EMAIL_SEND_FAILED, e);
		}
	}

	@Override
	public Boolean resetPassword(String emailId, String userType, Long languageId, String name) throws WalletException {
		LOGGER.debug(" resetPassword");
		Boolean resetFlag = false;
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		if(null == emailId) {
			throw new WalletException(CommonConstrain.EMAIL_ID_DOES_NOT_MATCH);
		}
		try{
			Authentication authentication = commonDao.getAuthentication(emailId, userType);
			authentication.setAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
			authentication.setBlocked(CommonConstrain.FALSE);
			authentication.setResetPassword(CommonConstrain.TRUE);
			String newPassword = CommonUtil.getRandomPassword();
			authentication.setPassword(cryptService.encryptData(newPassword));
			
			Long langId = languageId != null ? languageId : 1L;
			commonDao.updateAuthentication(authentication);
			Properties dvalues = new Properties();
			dvalues.put(EmailLitterals.CUSTOMER_NAME, name);
			dvalues.put(EmailLitterals.NEW_PASSWORD, newPassword);
			String mailMsg = emailTemplateService.getEmailBodyMessage(langId, EmailServiceConstants.RESET_PASSWORD, dvalues);
			String mailSubject = emailTemplateService.getEmailSubjectMessage(langId, EmailServiceConstants.RESET_PASSWORD, dvalues);
		    emailService.sendMessage(authentication.getEmailId(), mailMsg, mailSubject);
			transactionManager.commit(txstatus);
			resetFlag = true;
		}catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
			transactionManager.rollback(txstatus);
			throw ex;
		}catch(Exception e){
			transactionManager.rollback(txstatus);
			throw new WalletException(EmailServiceConstants.EMAIL_SEND_FAILED, e);
		}
		return resetFlag;
	}
	
	@Override
	public Long emailIdExist(String emailId, String userType){
		return commonDao.emailIdExist(emailId, userType);
	}
	
	@Override
	public Boolean emailIdExist(String emailId){
		return commonDao.emailIdExist(emailId);
	}
	
	@Override
	public Authentication getAuthentication(String email, String userType) throws WalletException {
		return commonDao.getAuthentication(email, userType);
	}
	
	@Override
	public void sendForgotPasswordMail(String email, String newPassword, String userType, Long languageId, String name) throws WalletException{
		LOGGER.debug(" sendForgotPasswordMail ::languageId " + languageId);
		try{
			Long langId = languageId != null ? languageId : CommonConstrain.DEFAULT_LANGUAGE;
			Properties dvalues = new Properties();
		    dvalues.put(EmailLitterals.CUSTOMER_NAME, name);
			dvalues.put(EmailLitterals.NEW_PASSWORD, newPassword);
			String mailMsg = emailTemplateService.getEmailBodyMessage(langId, EmailServiceConstants.FORGOT_PASSWORD_MAIL, dvalues);
			String mailSubject = emailTemplateService.getEmailSubjectMessage(langId, EmailServiceConstants.FORGOT_PASSWORD_MAIL, dvalues);
		    emailService.sendMessage(email, mailMsg, mailSubject);
		}catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
			throw ex;
		}catch(Exception e){
			throw new WalletException(EmailServiceConstants.EMAIL_SEND_FAILED, e);
		}
	}

	@Override
	public void sendAdminRegistrationMail(String name, String email, String newPassword, String userType, Long languageId) throws WalletException {
		LOGGER.debug(" sendAdminRegistrationMail ");
	    try{
			Long langId = languageId != null ? languageId : CommonConstrain.DEFAULT_LANGUAGE;
		    Properties dvalues = new Properties();
		    dvalues.put(EmailLitterals.ADMIN_NAME,name);
			dvalues.put(EmailLitterals.NEW_PASSWORD,newPassword);
			String mailMsg = emailTemplateService.getEmailBodyMessage(langId, EmailServiceConstants.ADMIN_REGISTRATION, dvalues);
			String mailSubject = emailTemplateService.getEmailSubjectMessage(langId, EmailServiceConstants.ADMIN_REGISTRATION, dvalues);
		    emailService.sendMessage(email, mailMsg, mailSubject);
	    }catch(WalletException ex){
	    	LOGGER.error(ex.getMessage(), ex);
			throw ex;
		}catch(Exception e){
			throw new WalletException(EmailServiceConstants.EMAIL_SEND_FAILED, e);
		}
	}

	@Override
	public Boolean phoneNOExist(String phoneCode, String phoneNO) {
		LOGGER.debug(" phoneNOExist ");
		boolean flag = false;
		try {
			flag = commonDao.phoneNOExist(phoneCode, phoneNO);
		}catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public void validateUserSession(Long authenticationId, String userType) throws WalletException {
		LOGGER.debug(" validateUserSession ");
		commonDao.validateUserSession(authenticationId, userType);
		
	}
	
	/**
	 * Update email status if the request is not older than 24hrs
	 * @param authenticationId
	 * @throws WalletException
	 */
	@Override
	public void registrationEmailVarification(Long authenticationId) throws WalletException {
		Authentication authentication = commonDao.getAuthentication(authenticationId);
		if(null == authentication){
			throw new WalletException(CommonConstrain.EMAIL_VARIFIED_FAILED_MSG);
		}
		Date creationDate = authentication.getCreationDate();
		Date todayDate = new Date();
		Integer diffInDays = (int) ((creationDate.getTime() - todayDate.getTime())/ GlobalLitterals.DAY_IN_MILLIS );
		if(diffInDays == 0){
			authentication.setEmailVarification(true);
			authentication.setActive(true);
			updateAuthentication(authentication);
			LOGGER.debug("registrationEmailVarification :: email verification success");
		}else{
			LOGGER.error("registrationEmailVarification :: email verification failed");
			throw new WalletException(CommonConstrain.EMAIL_VARIFICATION_LINK_EXPIRED);
		}
	}

	@Override
	public	Feedback  createFeedback(FeedbackDto feedbackDto)throws WalletException{
		return commonDao.createFeedback(CommonUtil.getFeedback(feedbackDto));
	}

	@Override
	public void updatePassword(Authentication authentication) throws WalletException {
		LOGGER.debug(" updatePasswordHistory ");
		int maxNumberOfPassword = utilService.getMaxPasswordHistory();
		List<PasswordHistory> phList = commonDao.getPasswordHistory(authentication.getId());
		
		if(passwordIsExists(phList, authentication.getPassword())){
			throw new WalletException(CommonConstrain.PASSWORD_HISTORY_EXIST);
		}
		if(maxNumberOfPassword == phList.size()){
			commonDao.deletePasswordHistory(phList.get(0));
			phList.remove(0);
		}else{
			while(phList.size() >= maxNumberOfPassword){
				commonDao.deletePasswordHistory(phList.get(0));
				phList.remove(0);
			}
		}
		PasswordHistory  ph = new PasswordHistory();
		ph.setCreationDate(new Date());
		ph.setPassword(authentication.getPassword());
		ph.setAuthentication(authentication.getId());
		commonDao.createPasswordHistory(ph);
		commonDao.updateAuthentication(authentication);
	}

	public List<PasswordHistory> getPasswordHistory(Long authenticationId) throws WalletException {
		return commonDao.getPasswordHistory(authenticationId);
	}
	
	private boolean passwordIsExists(List<PasswordHistory> phList, String newPassword) {
		for(PasswordHistory ph: phList){
			if(ph.getPassword().equals(newPassword)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void updatePreferences(PreferencesDto preferencesDto)throws WalletException {
		Preferences preferences = new Preferences();
		preferences.setId(preferencesDto.getId());
		preferences.setAuthentication(preferencesDto.getAuthentication());
		preferences.setLanguage(preferencesDto.getLanguage());
		preferences.setCurrency(preferencesDto.getCurrency());
	    commonDao.updatePreferences(preferences);
	}

	@Override
	public Preferences createPreferences(PreferencesDto preferencesDto) throws WalletException {
		Preferences preferences = new Preferences();
		preferences.setAuthentication(preferencesDto.getAuthentication());
		preferences.setLanguage(preferencesDto.getLanguage());
		preferences.setCurrency(preferencesDto.getCurrency());
		preferences = commonDao.createPreferences(preferences);
		return preferences;
	}
	
	@Override
	public PreferencesDto getPreferences(Long authId) throws WalletException {
	   Preferences preferences = commonDao.getPreferences(authId);
	   return convertModelToDto(preferences);
	  
	}
	
	private PreferencesDto convertModelToDto(Preferences preferences) {
	   PreferencesDto preferencesDto = new PreferencesDto();
	   preferencesDto.setAuthentication(preferences.getAuthentication());
	   preferencesDto.setId(preferences.getId());
	   preferencesDto.setCurrency(preferences.getCurrency());
	   preferencesDto.setLanguage(preferences.getLanguage());
	   
	   return preferencesDto;
	}

	@Override
	public UserWallet createUserWallet(Long authId, Long currency) throws WalletException {
		UserWallet userWallet = new UserWallet();
		userWallet.setAuthId(authId);
		userWallet.setCurrency(currency);
		userWallet.setAmount(CommonConstrain.DEFAULT_WALLET_AMOUNT);
		userWallet.setCreationDate(CommonUtil.getCurrentDateTimestamp());
		userWallet = commonDao.createUserWallet(userWallet);
		return userWallet;
	}
	
	@Override
	public UserWallet getUserWallet(Long authId, Long currency) throws WalletException {
		return commonDao.getUserWallet(authId, currency);
	}

	@Override
	public UserWallet updateUserWallet(UserWallet userWallet) throws WalletException {
		return commonDao.updateUserWallet(userWallet);
	}

	@Override
	public Long getLastDayCreatedNumberOfAccounts(Date fromDate, Date toDate, String userType)throws WalletException {
		return commonDao.getLastDayCreatedNumberOfAccounts(fromDate, toDate, userType);
	}

	@Override
	public Long getLastWeekCreatedNumberOfAccounts(Date date, String userType) throws WalletException {
		return commonDao.getLastWeekCreatedNumberOfAccounts(date, userType);
	}
		
	@Override
	public Long getTotalNumberOfAccounts(String userType)throws WalletException {
		return commonDao.getTotalNumberOfAccounts(userType);
	}

	@Override
	public MasterAmountWallet getMasterAmountWallet(Long currencyId) {
		return commonDao.getMasterAmountWallet(currencyId);
	}

	@Override
	public MasterAmountWallet updateMasterAmountWallet(MasterAmountWallet wallet) {
		return commonDao.updateMasterAmountWallet(wallet);
	}

	@Override
	public MasterFeeWallet getMasterFeeWallet(Long currencyId) {
		return commonDao.getMasterFeeWallet(currencyId);
	}

	@Override
	public MasterFeeWallet updateMasterFeeWallet(MasterFeeWallet wallet) {
		return commonDao.updateMasterFeeWallet(wallet);
	}

	@Override
	public MasterTaxWallet getMasterTaxWallet(Long currencyId) {
		return commonDao.getMasterTaxWallet(currencyId);
	}

	@Override
	public MasterTaxWallet updateMasterTaxWallet(MasterTaxWallet wallet) {
		return commonDao.updateMasterTaxWallet(wallet);
	}

	@Override
	public List<Object[]> getCustomersTotalAmountsByCountry(String userType)
			throws WalletException {
		return commonDao.getCustomersTotalAmountsByCountry(userType);
	}

	@Override
	public NonRegisterWallet createNonRegisterWallet(NonRegisterWallet nonRegisterWallet) {
		return commonDao.createNonRegisterWallet(nonRegisterWallet);
	}

	@Override
	public List<NonRegisterWallet> getMoneyFromTemporaryWallet(String email) {
		return commonDao.getMoneyFromTemporaryWallet(email);
	}
	
	@Override
	public void updateTemporaryWalletRecord(NonRegisterWallet nonRegisterWallet) {
		commonDao.updateTemporaryWalletRecord(nonRegisterWallet);
		
	}

	@Override
	public void sendReloadMoneyEmail(String payeeName, String payeeEmail, String payeeAmount, 
			String payeeCurrency, Long languageId) throws WalletException {
		try{
			LOGGER.debug(" sendReloadMoneyEmail ");
			
			Long langId = languageId != null ? languageId : CommonConstrain.DEFAULT_LANGUAGE;
			String emailTo = payeeEmail;
			Properties dvalues = new Properties();
			String mailMsg = null;
			String mailSubject = null;
			String type = null;
			
			dvalues.put(EmailLitterals.PAYEE_NAME, payeeName);
			dvalues.put(EmailLitterals.PAYEE_AMOUNT, payeeAmount );
			dvalues.put(EmailLitterals.PAYEE_CURRENCY, payeeCurrency );
			type = EmailServiceConstants.RELOAD_MONEY_MAIL;
			
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
	public List<Object[]> getCustomerBalancesByUserId(Long authId) {
		return commonDao.getCustomerBalancesByUserId(authId);
	}

	@Override
	public List<UserWallet> getUserWallets(Long authId) {
		return commonDao.getUserWallets(authId);
	}
	
	@Override
	public UserIP saveUserIPAddress(UserIP userIP) throws WalletException {
		return commonDao.saveUserIPAddress(userIP);
	}
	
	@Override
	public UserIP sendOTPToUser(UserIP userIP, Long languageId, String personName) throws WalletException {
		LOGGER.debug(" sendOTPToUser ");
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		/*First time login, so create code for this IP address*/
		try{
			Long langID = languageId != null ? languageId : CommonConstrain.DEFAULT_LANGUAGE;
			Authentication auth = getAuthentication(userIP.getAuthId());
			if(null == auth){
				throw new WalletException("Fails to retrieve user authentication");
			}
			String code = DateConvertion.getUniqueCode(new Date());
			userIP.setCode(code);
			commonDao.saveUserIPAddress(userIP);
			transactionManager.commit(txstatus);
			sendMailToUserWithCode(langID, personName, auth.getEmailId(), code);
		}catch (Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		} 
		return userIP;
	}

	@Override
	public UserIP getUserIPAddress(Long authId, String ipAddress) throws WalletException {
		return commonDao.getUserIPAddress(authId, ipAddress);
	}
	
	@Override
	public UserIP updateUserIPAddress(UserIP userIP) throws WalletException {
		return commonDao.updateUserIPAddress(userIP);
	}
	
	private void sendMailToUserWithCode(Long languageId, String personName, String email, String code) throws WalletException {
		LOGGER.debug(" sendMailToUserWithCode ");
	    try{
			Long langId = languageId != null ? languageId : CommonConstrain.DEFAULT_LANGUAGE;
		    Properties dvalues = new Properties();
		    dvalues.put(EmailLitterals.USER_NAME, personName);
			dvalues.put(EmailLitterals.NEW_CODE, code);
			String mailMsg = emailTemplateService.getEmailBodyMessage(langId, EmailServiceConstants.ONE_TIME_IP_CHECK_CODE, dvalues);
			String mailSubject = emailTemplateService.getEmailSubjectMessage(langId, EmailServiceConstants.ONE_TIME_IP_CHECK_CODE, dvalues);
		    emailService.sendMessage(email, mailMsg, mailSubject);
	    }catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	@Override
	public Integer updateIpAddressCheck(Long authId, Boolean flag) throws WalletException {
		return commonDao.updateIpAddressCheck(authId, flag);
	}

	@Override
	public Integer updateEmailPatternCheck(Long authId, Boolean flag) throws WalletException {
		return commonDao.updateEmailPatternCheck(authId, flag);
	}

	@Override
	public Integer updateChargeBackCheck(Long authId, Boolean flag) throws WalletException {
		return commonDao.updateChargeBackCheck(authId, flag);
	}

	@Override
	public Boolean deletePhone(PhoneNumber phoneNumber) throws WalletException {
		return commonDao.deletePhone(phoneNumber);
	}

	@Override
	public List<Long> getTxnIdsForNonRegisterWallets(Long status, Date date) throws WalletException {
		return commonDao.getTxnIdsForNonRegisterWallets(status, date);
	}

	@Override
	public NonRegisterWallet updateNonRegisterWallet(Long txnId, Long status)throws WalletException {
		return commonDao.updateNonRegisterWallet(txnId, status);
	}

	@Override
	public void sendMailUserAccountReject(String fullName, String email, String rejectMessage, String userType, Long languageId) {
		LOGGER.debug(" sendMailUserAccountReject ");
	    try{
			Long langId = languageId != null ? languageId : CommonConstrain.DEFAULT_LANGUAGE;
		    Properties dvalues = new Properties();
		    dvalues.put(EmailLitterals.USER_NAME, fullName);
		    dvalues.put(EmailLitterals.ACCOUNT_TYPE, userType.equals(GlobalLitterals.MERCHANT_USER_TYPE)? GlobalLitterals.MERCHANT : GlobalLitterals.CUSTOMER);
			dvalues.put(EmailLitterals.REJECT_MESSAGE, rejectMessage);
			String mailMsg = emailTemplateService.getEmailBodyMessage(langId, EmailServiceConstants.USER_ACCOUNT_REJECTED_BY_ADMIN, dvalues);
			String mailSubject = emailTemplateService.getEmailSubjectMessage(langId, EmailServiceConstants.USER_ACCOUNT_REJECTED_BY_ADMIN, dvalues);
		    emailService.sendMessage(email, mailMsg, mailSubject);
	    }catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	@Override
	public void sendMailForUserStatusUpdate(String fullName, String email, String userStatus, Boolean isActive, String reason, String userType, Long languageId) {
		LOGGER.debug(" sendMailUserAccountApproved ");
	    try{
			Long langId = languageId != null ? languageId : CommonConstrain.DEFAULT_LANGUAGE;
		    Properties dvalues = new Properties();
		    dvalues.put(EmailLitterals.USER_NAME, fullName);
		    if(GlobalLitterals.ADMIN_USER_TYPE.equals(userType)){
		    	dvalues.put(EmailLitterals.ACCOUNT_TYPE, GlobalLitterals.ADMIN);
		    }else{
		    	dvalues.put(GlobalLitterals.ADMIN_USER_TYPE, GlobalLitterals.MERCHANT_USER_TYPE.equals(userType) ? 
		    			GlobalLitterals.MERCHANT : GlobalLitterals.CUSTOMER);
		    }
		    dvalues.put(EmailLitterals.USER_STATUS , userStatus);
		    dvalues.put(EmailLitterals.USER_ACTIVE , Boolean.TRUE.equals(isActive) ? GlobalLitterals.ACTIVE : GlobalLitterals.IN_ACTIVE);
		    dvalues.put(EmailLitterals.ADMIN_COMMENT , reason != null ? reason : GlobalLitterals.NO_COMMENT);
			String mailMsg = emailTemplateService.getEmailBodyMessage(langId, EmailServiceConstants.USER_STATUS_UPDATED_BY_ADMIN, dvalues);
			String mailSubject = emailTemplateService.getEmailSubjectMessage(langId, EmailServiceConstants.USER_STATUS_UPDATED_BY_ADMIN, dvalues);
		    emailService.sendMessage(email, mailMsg, mailSubject);
	    }catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		
	}
	@Override
	public String getUserEmailById(Long id) throws WalletException {
		return commonDao.getUserEmailById(id);
	}

	@Override
	public void sendMailForFeedbackReply(Feedback feedback) {
		LOGGER.debug(" sendMailForFeedbackReply ");
	    try{
			Long langId = feedback.getLanguageId() != null ? feedback.getLanguageId() : CommonConstrain.DEFAULT_LANGUAGE;
		    Properties dvalues = new Properties();
		    dvalues.put(EmailLitterals.USER_NAME, feedback.getReciverName() != null ? feedback.getReciverName() : GlobalLitterals.EMPTY_STRING);
		    dvalues.put(EmailLitterals.QUERY_TYPE_NAME, feedback.getQueryTypeName() != null ? feedback.getQueryTypeName() :  GlobalLitterals.EMPTY_STRING);
		    dvalues.put(EmailLitterals.POSTED_DATE, feedback.getDateAndTime() != null ? DateConvertion.dateTimeToString(feedback.getDateAndTime()) :  GlobalLitterals.EMPTY_STRING);
		    dvalues.put(EmailLitterals.OLD_MESSAGE, feedback.getMessage() != null ? feedback.getMessage() : GlobalLitterals.EMPTY_STRING);
		    dvalues.put(EmailLitterals.RESPONSE_MESSAGE, feedback.getResponseMessage() != null ? feedback.getResponseMessage() : GlobalLitterals.EMPTY_STRING);
		    
			String mailMsg = emailTemplateService.getEmailBodyMessage(langId, EmailServiceConstants.FEEDBACK_REPLY_BY_ADMIN_TO_CUSTOMER_OR_MERCHANT, dvalues);
			emailService.sendMessage(feedback.getUserMail(), mailMsg, feedback.getSubject());
	    }catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	@Override
	public PhoneNumber getPhoneNumber(String emailid, String userType) {
		return commonDao.getPhoneNumber(emailid, userType);
	}
	
	@Override
	public void sendEmailNotificationToMobileUser(String name, String userTypeName, Long authId, 
			Long languageId, String emailTemplate) throws WalletException {
		LOGGER.debug(" sendEmailNotificationToMobileUser ");
	    try{
	    	Authentication authentication = commonDao.getAuthentication(authId);
			Long langId = languageId != null ? languageId : CommonConstrain.DEFAULT_LANGUAGE;
		    Properties dvalues = new Properties();
		    dvalues.put(EmailLitterals.PERSON_NAME, name);
		    dvalues.put(EmailLitterals.USER_TYPE_NAME, userTypeName);
		    if(EmailServiceConstants.MOBILE_WALLET_FORGOT_MPIN_CONFIRMATION.equals(emailTemplate)){
		    	dvalues.put(EmailLitterals.CURRENT_MPIN, cryptService.decryptData(authentication.getmPin()));
		    }
			String mailMsg = emailTemplateService.getEmailBodyMessage(langId, emailTemplate, dvalues);
			String mailSubject = emailTemplateService.getEmailSubjectMessage(langId, emailTemplate, dvalues);
		    emailService.sendMessage(authentication.getEmailId(), mailMsg, mailSubject);
	    }catch(WalletException ex){
	    	LOGGER.error(ex.getMessage(), ex);
		}catch(Exception e){
			/*Don't throw just catch it*/
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public Boolean mobileRegistration(String emailid, String userType, String password,
			String msisdnNumber, String simNumber, String imeiNumber) throws WalletException {
		Boolean flag = Boolean.FALSE;
		Authentication authentication = commonDao.getAuthentication(emailid, userType);
	  
		if(null == authentication){
			throw new WalletException(CommonConstrain.LOGIN_ERROR_INVALID_USER);
		}
		if (authentication != null) {
			if(authentication.getUserType() != null && !authentication.getUserType().equals(GlobalLitterals.ADMIN_USER_TYPE)
					&& authentication.isEmailVarification() != CommonConstrain.TRUE){
				throw new WalletException(CommonConstrain.EMAIL_VARIFICATION_NOT_DONE);
			}
		   if (authentication.getPassword().equals(cryptService.encryptData(password))) {
			    CommonUtil.checkMobileUserState(authentication, Boolean.FALSE);
			    /*On first login / requested from different device / SIM, update wallet database*/	
			  	if(CommonUtil.isFirstLogin(authentication) 
			   			|| !CommonUtil.checkUserHasRequestedFromRegisterMobileWallet(authentication, msisdnNumber, simNumber, imeiNumber)){
			  		authentication.setMsisdnNumber(msisdnNumber);
			  		authentication.setSimNumber(simNumber);
			  		authentication.setImeiNumber(imeiNumber);
			  		updateAuthentication(authentication);
			  		flag = Boolean.TRUE ;
			   	}else{
			   		throw new WalletException(CommonConstrain.ALREADY_REGISTOR_AS_MOBILE_WALLET);
			   	}
		   }else{
			   throw new WalletException(CommonConstrain.PASSWORD_MATCH_FAIL);
		   }
		   
		}
		return flag;
	}
	
	@Override
	public Authentication getDeviceLoginAuthentication(String email, String mPin, String userType, Long typeOfRequest) throws WalletException {
		Authentication authentication = commonDao.getAuthentication(email, userType);
		if(null == authentication){
			throw new WalletException(CommonConstrain.LOGIN_ERROR_INVALID_USER);
		}
		if(null == authentication.getmPin()){
			throw new WalletException(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET);
		}
		Date lastLogin = authentication.getLastLogin();
		if (authentication != null) {
			if(authentication.getUserType() != null && !authentication.getUserType().equals(GlobalLitterals.ADMIN_USER_TYPE)
					&& authentication.isEmailVarification() != CommonConstrain.TRUE){
				throw new WalletException(CommonConstrain.EMAIL_VARIFICATION_NOT_DONE);
			}else if(TypeOfRequest.getLongValue(TypeOfRequest.WEB)
					.equals(authentication.getTypeOfRequest())
					&& authentication.isLoginStatus() != null 
					&& authentication.isLoginStatus()){
				/*Allow him to login from mobile more than once. 
				if he already login from web throw exception*/
				/*But for time being let it allow to login*/
				   //throw new WalletException(CommonConstrain.LOGIN_STATUS);
			}
		   if (cryptService.encryptData(mPin).equals(authentication.getmPin())) {
			   CommonUtil.checkMobileUserState(authentication, Boolean.TRUE);
		      /*Allow to update if the user has login in second time. In case of
			   * first time login need to update in change password call.*/
			   if(!authentication.isResetPassword()){
				 /* default MPIN attempts */
				   authentication.setmPinAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
				   authentication.setLastLogin(new Date());
				   //authentication.setLoginStatus(CommonConstrain.TRUE);
				   authentication.setTypeOfRequest(typeOfRequest);
				   updateAuthentication(authentication);
			   }
		   }else {
			   /*Password does not match*/
			    if (CommonUtil.validateAttempts(authentication.getmPinAttempts(),
			    		utilService.getLoginAttemptsLimit())) {
			    	/* set MPIN block true */
			    	authentication.setmPinBlocked(CommonConstrain.TRUE);
			    	authentication.setmPinBlockedDate(new Date());
			    	updateAuthentication(authentication);
			    		//throw new WalletException(CommonConstrain.MOBILE_USER_BLOCK);
			    }else{ 
			    	Integer attemps = authentication.getmPinAttempts();
			    	/* set MPIN attempt */
			    	authentication.setmPinAttempts(attemps + 1); 
			    	updateAuthentication(authentication);
			    		throw new WalletException(CommonConstrain.MPIN_MATCH_FAIL);
			    }
		   }
		   if(null == lastLogin){
			   authentication.setLastLogin(new Date());
		   }else{
			   authentication.setLastLogin(lastLogin);
		   }
	  } 
	  return authentication;
	 }
	
	@Override
	public Boolean mPinGeneration(String emailid, String userType, String msisdnNumber, String simNumber, 
			String imeiNumber , String mPin, String personName, String userTypeName) throws WalletException {
		Boolean flag = Boolean.FALSE;
		Authentication authentication = commonDao.getAuthentication(emailid, userType);
	  
		if(null == authentication){
			throw new WalletException(CommonConstrain.LOGIN_ERROR_INVALID_USER);
		}
		if (authentication != null) {
			if(authentication.getUserType() != null && !authentication.getUserType().equals(GlobalLitterals.ADMIN_USER_TYPE)
					&& authentication.isEmailVarification() != CommonConstrain.TRUE){
				throw new WalletException(CommonConstrain.EMAIL_VARIFICATION_NOT_DONE);
			}
			CommonUtil.checkMobileUserState(authentication, Boolean.FALSE);
		   /*The device / SIM not changed and requested from register device*/	
		   if (CommonUtil.checkUserHasRequestedFromRegisterMobileWallet(authentication, msisdnNumber, simNumber, imeiNumber )){
			   if(authentication.getmPin() != null && authentication.getIsMwalletActive() != null && authentication.getIsMwalletActive()){
		   			throw new WalletException(CommonConstrain.ALREADY_REGISTOR_AS_MOBILE_WALLET);
		   		}else{
		   			authentication.setmPin(cryptService.encryptData(mPin));
		   			authentication.setmWalletActicationDate(new Date());
		   			authentication.setIsMwalletActive(Boolean.TRUE);
		   			updateAuthentication(authentication);
		   			flag = true ;
		   			sendEmailNotificationToMobileUser(personName, userTypeName, authentication.getId(), 
		   					CommonConstrain.DEFAULT_LANGUAGE, EmailServiceConstants.MOBILE_WALLET_MPIN_GENERATION_CONFIRMATION);
		   		}
		   }else{ 
			   throw new WalletException(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET);
		   }
		}
		return flag;
	}
		
	@Override
	public Boolean deActivateDevice(String emailId, String userType, String personName, String userTypeName) throws WalletException {
		Authentication authentication = getAuthentication(emailId, userType);
		if(null == authentication){
			throw new WalletException(CommonConstrain.LOGIN_ERROR_INVALID_USER);
		}
		authentication.setmWalletDeacticationDate(new Date());
		authentication.setIsMwalletActive(Boolean.FALSE);
		updateAuthentication(authentication);
		Boolean deActivateflag = Boolean.TRUE;	
		sendEmailNotificationToMobileUser(personName, userTypeName, authentication.getId(), 
   					CommonConstrain.DEFAULT_LANGUAGE, EmailServiceConstants.MOBILE_WALLET_DEACTIVATE_CONFIRMATION);
		return deActivateflag;
	}

	@Override
	public Boolean validateMpin(String email, String userType, String mPin)
			throws WalletException {
		try{
			Authentication authentication = commonDao.getAuthentication(email, userType);
			if(null == authentication){
	        	throw new WalletException(CommonConstrain.USER_DOES_NOT_EXIST);
	        }else{
		        if(null == authentication.getmPin()
		        		|| GlobalLitterals.EMPTY_STRING.equals(authentication.getmPin())){
						throw new WalletException(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET);
			    }else if(!(cryptService.encryptData(mPin)).equals(authentication.getmPin())){
                /* Checking MPin with authentication, if does not match then send error code*/		        	
			    	throw new WalletException(CommonConstrain.MPIN_MATCH_FAIL);
		        }
	        }
		}catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			throw new WalletException(CommonConstrain.MPIN_MATCH_FAIL);
		}
		return Boolean.TRUE;
	}
	
	@Override
	public PhoneNumber getPhoneNumberByCode(String phoneCode, String phoneNo) {
		return commonDao.getPhoneNumberByCode(phoneCode, phoneNo);
	}

	@Override
	public void sendForgotMPINEmailNotificationToMobileUser(String name, String userTypeName, Long authId,
			Long languageId, String emailTemplate) throws WalletException {
		sendEmailNotificationToMobileUser(name, userTypeName, authId, 
					CommonConstrain.DEFAULT_LANGUAGE, EmailServiceConstants.MOBILE_WALLET_FORGOT_MPIN_NOTIFICATION);;
		
	}

	@Override
	public Boolean activateMobileRegistration(String emailid, String userType, String mPin, String msisdnNumber,
			String simNumber, String imeiNumber) throws WalletException {
		Boolean flag = Boolean.FALSE;
		Authentication authentication = commonDao.getAuthentication(emailid, userType);
	  
		if(null == authentication){
			throw new WalletException(CommonConstrain.LOGIN_ERROR_INVALID_USER);
		}
		if (authentication != null) {
			if(authentication.getUserType() != null && !authentication.getUserType().equals(GlobalLitterals.ADMIN_USER_TYPE)
					&& authentication.isEmailVarification() != CommonConstrain.TRUE){
				throw new WalletException(CommonConstrain.EMAIL_VARIFICATION_NOT_DONE);
			}
		   if (authentication.getmPin().equals(cryptService.encryptData(mPin))) {
			    CommonUtil.checkMobileUserState(authentication, Boolean.FALSE);
			    /*On first login or requested from different device / SIM, update wallet database*/	
			  	if(CommonUtil.isFirstLogin(authentication) 
			   			|| !CommonUtil.checkUserHasRequestedFromRegisterMobileWallet(authentication, msisdnNumber, simNumber, imeiNumber)){
			  		authentication.setMsisdnNumber(msisdnNumber);
		  		    authentication.setSimNumber(simNumber);
			  		authentication.setImeiNumber(imeiNumber);
			  		updateAuthentication(authentication);
			  		flag = Boolean.TRUE ;
			   	}else{
			   		throw new WalletException(CommonConstrain.ALREADY_REGISTOR_AS_MOBILE_WALLET);
			   	}
		   }else{
			   throw new WalletException(CommonConstrain.PASSWORD_MATCH_FAIL);
		   }
		   
		}
		return flag;
	}
	
	@Override
	public PhoneNumber getPhoneNumber(String phoneNo) {
		return commonDao.getPhoneNumber(phoneNo);
	}
}
