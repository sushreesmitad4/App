package com.tarang.ewallet.common.repository.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.repository.LoginRepository;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.dto.ForgotPasswordDto;
import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Hints;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @Author : kedarnath
 * @Date : Oct 17, 2012
 * @Time : 4:51:52 PM
 * @Version : 1.0
 * @Comments: Repository Implementation to provide Ewallet Login Services 
 * for Admin, Merchant and Customer.
 */
public class LoginRepositoryImpl implements LoginRepository {

	private static final Logger LOGGER = Logger.getLogger(LoginRepositoryImpl.class);
	
	private CommonService commonService;
	
	private CryptService cryptService;
	
	private HibernateTransactionManager transactionManager;

	public LoginRepositoryImpl(CommonService commonService, CryptService cryptService, HibernateTransactionManager transactionManager) {
		LOGGER.debug( " LoginRepositoryImpl " );
		this.commonService = commonService;
		this.cryptService = cryptService;
		this.transactionManager = transactionManager;
	}

	@Override
	 public Authentication login(String email, String password, String userType, Long typeOfRequest)
	   throws WalletException {
	  LOGGER.debug( " login " );
	  Authentication authentication = commonService.getAuthentication(email, password, userType, typeOfRequest);
	  Authentication newAuthentication = new Authentication();
	  newAuthentication.setActive(authentication.isActive());
	  newAuthentication.setCreationDate(authentication.getCreationDate());
	  newAuthentication.setEmailId(authentication.getEmailId());
	  newAuthentication.setLastLogin(authentication.getLastLogin());
	  newAuthentication.setLoginStatus(authentication.isLoginStatus());
	  newAuthentication.setResetPassword(authentication.isResetPassword());
	  newAuthentication.setStatus(authentication.getStatus());
	  newAuthentication.setUserType(authentication.getUserType());
	  newAuthentication.setId(authentication.getId());
	  newAuthentication.setMsisdnNumber(authentication.getMsisdnNumber() != null 
			  && !"null".equals(authentication.getMsisdnNumber())
			  ? authentication.getMsisdnNumber() : GlobalLitterals.EMPTY_STRING);
	  newAuthentication.setSimNumber(authentication.getSimNumber() != null
			  && !"null".equals(authentication.getSimNumber())
			  ? authentication.getSimNumber() : GlobalLitterals.EMPTY_STRING);
	  newAuthentication.setImeiNumber(authentication.getImeiNumber() != null 
			  && !"null".equals(authentication.getImeiNumber())
			  ? authentication.getImeiNumber() : GlobalLitterals.EMPTY_STRING);
	  return newAuthentication;
	 }

	@Override
	public Boolean forgotPassword(ForgotPasswordDto forgotPasswordDto, String personName) throws WalletException {
		LOGGER.debug( " forgotPassword " );
		Authentication authentication = commonService.getAuthentication(forgotPasswordDto.getEmailId(),
						forgotPasswordDto.getUserType());
		if (null == authentication) {
			throw new WalletException(CommonConstrain.EMAIL_ID_DOES_NOT_MATCH);
		}
		//EWALLET-59
		Boolean isActive = authentication.isActive() != null ? authentication.isActive() : false;
		Long status = authentication.getStatus() != null ? authentication.getStatus() : new Long(0);
		if(!isActive){
			throw new WalletException(CommonConstrain.ACCOUNT_DEACTIVE);
		}else if(UserStatusConstants.REJECTED.equals(status)){
			throw new WalletException(CommonConstrain.ACCOUNT_REJECTED);
		}
		validateAnswerAndQuestion(authentication, forgotPasswordDto.getQuestion1(),
				forgotPasswordDto.getHint1());
		String newPassword = CommonUtil.getRandomPassword();
		authentication.setPassword(cryptService.encryptData(newPassword));
		authentication.setAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
		authentication.setBlocked(CommonConstrain.FALSE);
		authentication.setResetPassword(CommonConstrain.TRUE);
		commonService.updateAuthentication(authentication);
		commonService.sendForgotPasswordMail(authentication.getEmailId(),
				newPassword, authentication.getUserType(), forgotPasswordDto.getLanguageId(),personName);
		return true;
	}

	/*
	 * This method will called when logout clicked.
	 */
	@Override
	public Boolean logout(String email, String userType) throws WalletException {
		LOGGER.debug( " logout " );
		Authentication authentication = commonService
				.getAuthentication(email, userType);
		authentication.setLastLogin(new Date());
		authentication.setLoginStatus(CommonConstrain.FALSE);
		commonService.updateAuthentication(authentication);
		return true;
	}

	@Override
	public Boolean changePassword(ChangePasswordDto changePasswordDto) throws WalletException {
		LOGGER.debug( " changePassword " );
		
		Authentication authentication = commonService.getAuthentication(changePasswordDto.getEmailId(),
				changePasswordDto.getUserType());
		
		if (null == authentication) {
			/*email does not match*/
			throw new WalletException(CommonConstrain.EMAIL_ID_DOES_NOT_MATCH);
		}

		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{

			if ( authentication.getPassword().equals( cryptService.encryptData( changePasswordDto.getOldPassword() ) ) ) {
				
				String newChangePassword = cryptService.encryptData(changePasswordDto.getNewPassword());
				authentication.setPassword(newChangePassword);
				/*updating login status after reset password*/
				if(authentication.isResetPassword()){
					authentication.setAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
					authentication.setLastLogin(new Date());
					authentication.setLoginStatus(CommonConstrain.TRUE);
				}
				authentication.setResetPassword(CommonConstrain.FALSE);
				commonService.updatePassword(authentication);
				transactionManager.commit(txstatus);
			}else {
                  /*Password does not match*/
				throw new WalletException(CommonConstrain.PASSWORD_MATCH_FAIL);
			}
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		} 
		
		return true;
	}

	
	@Override
	public Authentication loginWithDevice(String email, String mPin, String userType, Long typeOfRequest) throws WalletException {
		 LOGGER.debug( " loginWithDevice " );
		 Authentication authentication = commonService.getDeviceLoginAuthentication(email, mPin, userType, typeOfRequest);
		 Authentication newAuthentication = new Authentication();
		 newAuthentication.setActive(authentication.isActive());
		 newAuthentication.setCreationDate(authentication.getCreationDate());
		 newAuthentication.setEmailId(authentication.getEmailId());
		 newAuthentication.setLastLogin(authentication.getLastLogin());
		 newAuthentication.setLoginStatus(authentication.isLoginStatus());
		 newAuthentication.setResetPassword(authentication.isResetPassword());
		 newAuthentication.setStatus(authentication.getStatus());
		 newAuthentication.setUserType(authentication.getUserType());
		 newAuthentication.setId(authentication.getId());
		 newAuthentication.setmPinBlocked(authentication.getmPinBlocked());
		 newAuthentication.setMsisdnNumber(authentication.getMsisdnNumber() != null 
			  && !"null".equals(authentication.getMsisdnNumber())
			  ? authentication.getMsisdnNumber() : GlobalLitterals.EMPTY_STRING);
		 newAuthentication.setSimNumber(authentication.getSimNumber() != null
			  && !"null".equals(authentication.getSimNumber())
			  ? authentication.getSimNumber() : GlobalLitterals.EMPTY_STRING);
		 newAuthentication.setImeiNumber(authentication.getImeiNumber() != null 
			  && !"null".equals(authentication.getImeiNumber())
			  ? authentication.getImeiNumber() : GlobalLitterals.EMPTY_STRING);
		 return newAuthentication;
		 
	}

	@Override
	public Boolean changeMPin(String email, String userType, String oldMpin,
			String newMPin, String personName, String userTypeName) throws WalletException {
		LOGGER.debug( " changeMpin " );
		Authentication authentication = commonService.getAuthentication(email, userType);
		if(null == authentication){
			throw new WalletException(CommonConstrain.LOGIN_ERROR_INVALID_USER);
		}
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			if (authentication.getmPin().equals(cryptService.encryptData(oldMpin))) {
				authentication.setmPin(cryptService.encryptData(newMPin));
				/*updating login status after reset password*/
				commonService.updateAuthentication(authentication);
				transactionManager.commit(txstatus);
				commonService.sendEmailNotificationToMobileUser(personName, userTypeName, authentication.getId(), 
						CommonConstrain.DEFAULT_LANGUAGE, EmailServiceConstants.MOBILE_WALLET_CHANGED_MPIN_CONFIRMATION);
			}else {
				/*Password does not match*/
				throw new WalletException(CommonConstrain.MPIN_MATCH_FAIL);
			}
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}
		return true;
	}

	@Override
	public Boolean forgotMpin(String email, String userType, String msisdnNumber, 
			String simNumber, String imeiNumber, Long question, String hint, 
			String personName, String userTypeName)	throws WalletException {
		try{
			Authentication authentication = commonService.getAuthentication(email, userType);
			if (null == authentication) {
				throw new WalletException(CommonConstrain.EMAIL_ID_DOES_NOT_MATCH);
			}
			if(null == authentication.getmPin() || CommonUtil.isFirstLogin(authentication)) {
				throw new WalletException(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET);
			}else if(!CommonUtil.checkUserHasRequestedFromRegisterMobileWallet(authentication, 
					msisdnNumber, simNumber, imeiNumber)) {
				throw new WalletException(CommonConstrain.USER_REQUESTED_FROM_DIFFERENT_DEVICE_OR_SIM);
			}
			CommonUtil.checkMobileUserState(authentication, Boolean.TRUE);
			validateAnswerAndQuestion(authentication, question, hint);
			/*MPIN will not send to the user as email notification, 
			 * 1. User will enter mobile number & Secrete Q & A
			 * 2. System will validate the inputs
			 * 3. System will send OTP to user
			 * 4. Once OTP validated, mobile will navigate to change MPIN screen 
			 * 5. User will choose new MPIN */
			
			commonService.sendForgotMPINEmailNotificationToMobileUser(personName, userTypeName, authentication.getId(), CommonConstrain.DEFAULT_LANGUAGE, 
					EmailServiceConstants.MOBILE_WALLET_FORGOT_MPIN_CONFIRMATION);
			return true;
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			throw new WalletException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * Validate the hint question and answer for the requested user.
	 * This method is the common for forgot password and forgot MPIN.
	 * @param authentication
	 * @param question1
	 * @param hint1
	 * @throws WalletException
	 */
	private void validateAnswerAndQuestion(Authentication authentication, Long question1, 
			String hint1) throws WalletException{
		Hints hints = commonService.getHints(authentication.getHints());
		if (!hints.getHintQuestion1().equals(question1)) {
			throw new WalletException(CommonConstrain.HINT_QUESTION_ONE_NOT_MATCHING);
		}else if (!hints.getHintAnswer1().equals(hint1)) {
			throw new WalletException(CommonConstrain.HINT_ANSW_ONE_NOT_MATCHING);
		} 
	}

	@Override
	public Boolean newMPin(String email, String userType, String newMPin, String personName, String userTypeName) throws WalletException {
		LOGGER.debug( " newMPin " );
		Authentication authentication = commonService.getAuthentication(email, userType);
		if(null == authentication){
			throw new WalletException(CommonConstrain.LOGIN_ERROR_INVALID_USER);
		}
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			authentication.setmPin(cryptService.encryptData(newMPin));
			/*updating login status after reset password*/
			commonService.updateAuthentication(authentication);
			transactionManager.commit(txstatus);
			commonService.sendEmailNotificationToMobileUser(personName, userTypeName, authentication.getId(), 
					CommonConstrain.DEFAULT_LANGUAGE, EmailServiceConstants.MOBILE_WALLET_CHANGED_MPIN_CONFIRMATION);
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}
		return true;
	}

}
