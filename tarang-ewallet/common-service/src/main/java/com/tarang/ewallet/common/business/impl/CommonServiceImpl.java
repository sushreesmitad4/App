package com.tarang.ewallet.common.business.impl;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.repository.CommonRepository;
import com.tarang.ewallet.dto.FeedbackDto;
import com.tarang.ewallet.dto.PreferencesDto;
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


/**
 * @Author : kedarnathd
 * @Date : Oct 08, 2012
 * @Time : 10:09:24 PM
 * @Version : 1.0
 * @Comments: Service Class to provide eWallet Services and it implements
 *            CommonService interface
 */
public class CommonServiceImpl implements CommonService {

	private CommonRepository commonRepository;

	public CommonServiceImpl(CommonRepository commonRepository) {
		this.commonRepository = commonRepository;
	}

	@Override
	public Address createAddress(Address address) throws WalletException {
		return commonRepository.createAddress(address);
	}

	@Override
	public Address getAddress(Long id) throws WalletException {
		return commonRepository.getAddress(id);
	}

	@Override
	public void updateAddress(Address address) throws WalletException {
		commonRepository.updateAddress(address);
	}

	@Override
	public PersonName createPersonName(PersonName personName) throws WalletException {
		return commonRepository.createPersonName(personName);
	}

	@Override
	public PersonName getPersonName(Long id) throws WalletException {
		return commonRepository.getPersonName(id);
	}

	@Override
	public void updatePersonName(PersonName personName) throws WalletException {
		commonRepository.updatePersonName(personName);
	}

	@Override
	public Authentication createAuthentication(Authentication authentication) throws WalletException {
		return commonRepository.createAuthentication(authentication);
	}
	
	@Override
	public Authentication createMobileAuthentication(Authentication authentication) throws WalletException {
		return commonRepository.createMobileAuthentication(authentication);
	}

	@Override
	public Authentication getAuthentication(Long id) throws WalletException {
		return commonRepository.getAuthentication(id);
	}

	@Override
	public void updateAuthentication(Authentication authentication) throws WalletException {
		commonRepository.updateAuthentication(authentication);
	}

	@Override
	public Boolean deleteAuthentication(Long authenticationId) throws WalletException {
		return commonRepository.deleteAuthentication(authenticationId);
	}

	@Override
	public PhoneNumber createPhone(PhoneNumber phoneNumber) throws WalletException {
		return commonRepository.createPhone(phoneNumber);
	}

	@Override
	public PhoneNumber getPhone(Long id) throws WalletException {
		return commonRepository.getPhone(id);
	}

	@Override
	public void updatePhone(PhoneNumber phoneNumber) throws WalletException {
		commonRepository.updatePhone(phoneNumber);
	}

	@Override
	public Hints createHints(Hints hints) throws WalletException {
		return commonRepository.createHints(hints);
	}

	@Override
	public Hints getHints(Long id) throws WalletException {
		return commonRepository.getHints(id);
	}

	@Override
	public void updateHints(Hints hints) throws WalletException {
		commonRepository.updateHints(hints);
	}

	@Override
	public void sendRegistrationMail(String name, Long authenticationId, String userType, Long languageId) throws WalletException {
		commonRepository.sendRegistrationMail(name, authenticationId, userType, languageId);
	}

	@Override
	public Authentication getAuthentication(String email, String password, String userType, Long typeOfRequest) throws WalletException {
		return commonRepository.getAuthentication(email, password, userType, typeOfRequest);
	}

	@Override
	public Boolean resetPassword(String emailId, String userType, Long languageId,String name) throws WalletException {
		return commonRepository.resetPassword(emailId, userType, languageId,name);
	}

	@Override
	public Long emailIdExist(String emailId, String userType) {
		return commonRepository.emailIdExist(emailId, userType);
	}
	
	@Override
	public Boolean emailIdExist(String emailId) {
		return commonRepository.emailIdExist(emailId);
	}

	@Override
	public Authentication getAuthentication(String email, String userType) throws WalletException {
		return commonRepository.getAuthentication(email, userType);
	}
	
	@Override
	public Authentication getAuthentication(String email) throws WalletException {
		return commonRepository.getAuthentication(email);
	}
	
	@Override
	public void sendForgotPasswordMail(String email, String newPassword, String userType, Long languageId,String personName) throws WalletException {
		commonRepository.sendForgotPasswordMail(email, newPassword, userType, languageId,personName);
	}

	@Override
	public void sendAdminRegistrationMail(String name, String email, String newPassword, String userType, Long languageId) throws WalletException {
		commonRepository.sendAdminRegistrationMail(name, email, newPassword, userType, languageId);
	}

	@Override
	public Boolean phoneNOExist(String phoneCode, String phoneNO) {
		return commonRepository.phoneNOExist(phoneCode, phoneNO);
	}

	@Override
	public void validateUserSession(Long authenticationId, String userType) throws WalletException {
		commonRepository.validateUserSession(authenticationId, userType);
	}
	
	@Override
	public void registrationEmailVarification(Long authenticationId) throws WalletException {
		commonRepository.registrationEmailVarification(authenticationId);
	}
	
    //feedback or query methods
	@Override
	public	Feedback createFeedback(FeedbackDto feedbackDto) throws WalletException {
		return commonRepository.createFeedback(feedbackDto);
	}
	
	@Override
	public void updatePassword(Authentication authentication) throws WalletException {
		commonRepository.updatePassword(authentication);
	}

	@Override
	public List<PasswordHistory> getPasswordHistory(Long authenticationId) throws WalletException {
		return commonRepository.getPasswordHistory(authenticationId);
	}

	@Override
	public Preferences createPreferences(PreferencesDto preferencesDto)throws WalletException {
		return commonRepository.createPreferences(preferencesDto);
	}

	@Override
	public void updatePreferences(PreferencesDto preferencesDto)throws WalletException {
		commonRepository.updatePreferences(preferencesDto);
	}	

	@Override
	public PreferencesDto getPreferences(Long authId) throws WalletException {
		return commonRepository.getPreferences(authId);
	}

	@Override
	public UserWallet createUserWallet(Long authId, Long currency) throws WalletException {
		return commonRepository.createUserWallet(authId, currency);
	}
	
	@Override
	public UserWallet getUserWallet(Long authId, Long currency) throws WalletException {
		return commonRepository.getUserWallet(authId, currency);
	}

	@Override
	public UserWallet updateUserWallet(UserWallet userWallet) throws WalletException {
		return commonRepository.updateUserWallet(userWallet);
	}
	
	@Override
	public Long getLastDayCreatedNumberOfAccounts(Date fromDate, Date toDate, String userType) throws WalletException {
		return commonRepository.getLastDayCreatedNumberOfAccounts(fromDate, toDate, userType);
	}

	@Override
	public Long getLastWeekCreatedNumberOfAccounts(Date date, String userType)throws WalletException {
		return commonRepository.getLastWeekCreatedNumberOfAccounts(date, userType);
	}

	@Override
	public Long getTotalNumberOfAccounts(String userType) throws WalletException {
		return commonRepository.getTotalNumberOfAccounts(userType);
	}

	@Override
	public MasterAmountWallet getMasterAmountWallet(Long currencyId) {
		return commonRepository.getMasterAmountWallet(currencyId);
	}

	@Override
	public MasterAmountWallet updateMasterAmountWallet(MasterAmountWallet wallet) {
		return commonRepository.updateMasterAmountWallet(wallet);
	}

	@Override
	public MasterFeeWallet getMasterFeeWallet(Long currencyId) {
		return commonRepository.getMasterFeeWallet(currencyId);
	}

	@Override
	public MasterFeeWallet updateMasterFeeWallet(MasterFeeWallet wallet) {
		return commonRepository.updateMasterFeeWallet(wallet);
	}

	@Override
	public MasterTaxWallet getMasterTaxWallet(Long currencyId) {
		return commonRepository.getMasterTaxWallet(currencyId);
	}

	@Override
	public MasterTaxWallet updateMasterTaxWallet(MasterTaxWallet wallet) {
		return commonRepository.updateMasterTaxWallet(wallet);
	}

	@Override
	public List<Object[]> getCustomersTotalAmountsByCountry(String userType)
			throws WalletException {
		
		return commonRepository.getCustomersTotalAmountsByCountry(userType);
	}

	@Override
	public NonRegisterWallet createNonRegisterWallet(NonRegisterWallet nonRegisterWallet) {
		return commonRepository.createNonRegisterWallet(nonRegisterWallet);
	}

	@Override
	public List<NonRegisterWallet> getMoneyFromTemporaryWallet(String email) {
		return commonRepository.getMoneyFromTemporaryWallet(email);
	}
	
	@Override
	public void updateTemporaryWalletRecord(NonRegisterWallet nonRegisterWallet) {
		commonRepository.updateTemporaryWalletRecord(nonRegisterWallet);
	}

	@Override
	public void sendReloadMoneyEmail(String payeeName, String payeeEmail, String payeeAmount, String payeeCurrency, Long languageId) throws WalletException {
		commonRepository.sendReloadMoneyEmail(payeeName, payeeEmail, payeeAmount, payeeCurrency, languageId);
	}
	@Override
	public List<Object[]> getCustomerBalancesByUserId(Long authId) {
		return commonRepository.getCustomerBalancesByUserId(authId);
	}

	@Override
	public List<UserWallet> getUserWallets(Long authId) {
		return commonRepository.getUserWallets(authId);
	}
	
	@Override
	public UserIP saveUserIPAddress(UserIP userIP) throws WalletException {
		return commonRepository.saveUserIPAddress(userIP);
	}
	
	@Override
	public UserIP sendOTPToUser(UserIP userIP, Long languageId, String personName) throws WalletException {
		return commonRepository.sendOTPToUser(userIP, languageId, personName);
	}

	@Override
	public UserIP getUserIPAddress(Long authId, String ipAddress) throws WalletException {
		return commonRepository.getUserIPAddress(authId, ipAddress);
	}

	@Override
	public UserIP updateUserIPAddress(UserIP userIP) throws WalletException {
		return commonRepository.updateUserIPAddress(userIP);
	}


	@Override
	public Integer updateIpAddressCheck(Long authId, Boolean flag) throws WalletException {
		return commonRepository.updateIpAddressCheck(authId, flag);
	}

	@Override
	public Integer updateEmailPatternCheck(Long authId, Boolean flag) throws WalletException {
		return commonRepository.updateEmailPatternCheck(authId, flag);
	}

	@Override
	public Integer updateChargeBackCheck(Long authId, Boolean flag) throws WalletException {
		return commonRepository.updateChargeBackCheck(authId, flag);
	}

	@Override
	public Boolean deletePhone(PhoneNumber phoneNumber) throws WalletException {
		return commonRepository.deletePhone(phoneNumber);
	}

	@Override
	public List<Long> getTxnIdsForNonRegisterWallets(Long status, Date date) throws WalletException {
		return commonRepository.getTxnIdsForNonRegisterWallets(status, date);
	}

	@Override
	public NonRegisterWallet updateNonRegisterWallet(Long txnId, Long status) throws WalletException {
		return commonRepository.updateNonRegisterWallet(txnId, status);
	}

	@Override
	public void sendMailUserAccountReject(String fullName, String email, String rejectMessage, 
			String userType, Long languageId) {
		commonRepository.sendMailUserAccountReject(fullName, email, rejectMessage, userType, languageId);
	}

	@Override
	public void sendMailForUserStatusUpdate(String fullName, String email, String userStatus, Boolean isActive, 
			 String reason, String userType, Long languageId) {
		commonRepository.sendMailForUserStatusUpdate(fullName, email, userStatus, isActive,  reason, userType, languageId);
	}

	@Override
	public String getUserEmailById(Long id) throws WalletException {
		return commonRepository.getUserEmailById(id);
	}

	@Override
	public void sendMailForFeedbackReply(Feedback feedback) {
		commonRepository.sendMailForFeedbackReply(feedback);
		
	}

	@Override
	public PhoneNumber getPhoneNumber(String emailid, String userType) {
		return commonRepository.getPhoneNumber(emailid, userType);
	}

	@Override
	public Boolean mobileRegistration(String emailid, String userType, String password, String msisdnNumber, String simNumber, String imeiNumber) throws WalletException {
		return commonRepository.mobileRegistration(emailid, userType, password, msisdnNumber, simNumber, imeiNumber);
	}

	@Override
	public Boolean mPinGeneration(String emailid, String userType, String msisdnNumber, String simNumber, 
			String imeiNumber, String mPin, String personName, String userTypeName) throws WalletException {
		return commonRepository.mPinGeneration(emailid, userType, msisdnNumber, simNumber, imeiNumber, mPin, personName, userTypeName);
	}
	
	@Override
	public Authentication getDeviceLoginAuthentication(String email, String mPin, String userType, Long typeOfRequest) throws WalletException {
		return commonRepository.getDeviceLoginAuthentication(email, mPin, userType, typeOfRequest);
	}
	
	@Override
	public Boolean deActivateDevice(String emailId, String userType, String personName, String userTypeName)throws WalletException {
		return commonRepository.deActivateDevice(emailId, userType, personName, userTypeName);
	}

	@Override
	public Boolean validateMpin(String email, String userType, String mPin) throws WalletException {
		return commonRepository.validateMpin(email, userType,mPin);
	}
	
	@Override
	public void sendEmailNotificationToMobileUser(String name, String userTypeName, Long authId, 
			Long languageId, String emailTemplate) throws WalletException {
		commonRepository.sendEmailNotificationToMobileUser(name, userTypeName, authId, languageId, emailTemplate);
	}

	@Override
	public PhoneNumber getPhoneNumberByCode(String phoneCode, String phoneNo) {
		return commonRepository.getPhoneNumberByCode(phoneCode, phoneNo);
	}

	@Override
	public void sendForgotMPINEmailNotificationToMobileUser(String name, String userTypeName, Long authId, Long languageId,
			String emailTemplate) throws WalletException {
		commonRepository.sendForgotMPINEmailNotificationToMobileUser(name, userTypeName, authId, languageId, emailTemplate);
		
	}

	@Override
	public Boolean activateMobileRegistration(String emailid, String userType, String mPin, String msisdnNumber,
			String simNumber, String imeiNumber) throws WalletException {
		return commonRepository.activateMobileRegistration(emailid, userType, mPin, msisdnNumber,
				simNumber, imeiNumber);
	}
	
	@Override
	public PhoneNumber getPhoneNumber(String phoneNo) {
		return commonRepository.getPhoneNumber( phoneNo);
	}
	
}
