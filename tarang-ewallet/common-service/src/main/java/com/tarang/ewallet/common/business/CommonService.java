package com.tarang.ewallet.common.business;

import java.util.Date;
import java.util.List;

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
 * @Comments: Service interface to provide eWallet Services
 */
public interface CommonService {

	// address business services
	Address createAddress(Address address) throws WalletException;

	Address getAddress(Long id) throws WalletException;

	void updateAddress(Address address) throws WalletException;

	// person name business services
	PersonName createPersonName(PersonName personName) throws WalletException;

	PersonName getPersonName(Long id) throws WalletException;

	void updatePersonName(PersonName personName) throws WalletException;

	// authentication business services
	Authentication createAuthentication(Authentication authentication) throws WalletException;
	
	Authentication createMobileAuthentication(Authentication authentication) throws WalletException;

	void updateAuthentication(Authentication authentication) throws WalletException;

	Boolean deleteAuthentication(Long authenticationId) throws WalletException;

	// phone number business services
	PhoneNumber createPhone(PhoneNumber phoneNumber) throws WalletException;

	PhoneNumber getPhone(Long id) throws WalletException;
	
	Boolean deletePhone(PhoneNumber phoneNumber) throws WalletException;

	void updatePhone(PhoneNumber phoneNumber) throws WalletException;

	// hints business services

	Hints createHints(Hints hints) throws WalletException;

	Hints getHints(Long id) throws WalletException;

	void updateHints(Hints hints) throws WalletException;

	void sendRegistrationMail(String name, Long authenticationId, String userType, Long languageId) throws WalletException;

	Boolean resetPassword(String emailId, String userType, Long languageId,String name) throws WalletException;
	
	Long emailIdExist(String emailId, String userType);
	
	Boolean emailIdExist(String emailId);
	
	Boolean phoneNOExist(String phoneCode, String phoneNO);
	
	Authentication getAuthentication(Long id) throws WalletException;

	Authentication getAuthentication(String email, String password, String userType, Long typeOfRequest) throws WalletException;

	Authentication getAuthentication(String email, String userType) throws WalletException;
	
	Authentication getAuthentication(String email) throws WalletException;
	
	void sendForgotPasswordMail(String email, String newPassword, String userType, Long languageId,String personName) throws WalletException;
	
	void sendAdminRegistrationMail(String name, String email, String newPassword, String userType, Long languageId) throws WalletException;
	
	void validateUserSession(Long authenticationId, String userType) throws WalletException;
	
	void registrationEmailVarification(Long authenticationId) throws WalletException;
	
	//feedback or querry methods
	
	Feedback  createFeedback(FeedbackDto feedbackDto)throws WalletException;
	
	/* Update new record in password history table*/
	void updatePassword(Authentication authentication) throws WalletException;
	
	List<PasswordHistory> getPasswordHistory(Long authenticationId) throws WalletException;
	
	Preferences  createPreferences(PreferencesDto preferencesDto)throws WalletException;
	
	void updatePreferences(PreferencesDto preferencesDto)throws WalletException;
	
	PreferencesDto getPreferences(Long authId) throws WalletException;
	
	UserWallet createUserWallet(Long authId, Long currency) throws WalletException;
	
	UserWallet getUserWallet(Long authId, Long currency) throws WalletException;
	
	List<UserWallet> getUserWallets(Long authId);
	
	UserWallet updateUserWallet(UserWallet userWallet) throws WalletException;
	
	Long getLastDayCreatedNumberOfAccounts(Date fromDate, Date toDate, String userType) throws WalletException;
	
	Long getLastWeekCreatedNumberOfAccounts(Date date, String userType) throws WalletException;
	
	Long getTotalNumberOfAccounts(String userType) throws WalletException;
	
	MasterAmountWallet getMasterAmountWallet(Long currencyId);
	
	MasterAmountWallet updateMasterAmountWallet(MasterAmountWallet wallet);

	MasterFeeWallet getMasterFeeWallet(Long currencyId);
	
	MasterFeeWallet updateMasterFeeWallet(MasterFeeWallet wallet);

	MasterTaxWallet getMasterTaxWallet(Long currencyId);
	
	MasterTaxWallet updateMasterTaxWallet(MasterTaxWallet wallet);
	
	List<Object[]> getCustomersTotalAmountsByCountry(String userType) throws WalletException;
	
	NonRegisterWallet createNonRegisterWallet(NonRegisterWallet nonRegisterWallet);
	
	List<NonRegisterWallet> getMoneyFromTemporaryWallet(String email);
	
	void updateTemporaryWalletRecord(NonRegisterWallet nonRegisterWallet);

	
	//Send confirmation mail for transaction
	void sendReloadMoneyEmail(String payeeName, String payeeEmail, String payeeAmount, String payeeCurrency, Long languageId) throws WalletException;
	
	List<Object[]> getCustomerBalancesByUserId(Long authId);
	
	UserIP saveUserIPAddress(UserIP userIP) throws WalletException;
	
	UserIP sendOTPToUser(UserIP userIP, Long languageId, String personName) throws WalletException;
	
	UserIP getUserIPAddress(Long authId, String ipAddress) throws WalletException;
	
	UserIP updateUserIPAddress(UserIP userIP) throws WalletException;
	
	Integer updateIpAddressCheck(Long authId, Boolean flag) throws WalletException;
	
	Integer updateEmailPatternCheck(Long authId, Boolean flag) throws WalletException;
	
	Integer updateChargeBackCheck(Long authId, Boolean flag) throws WalletException;
	
	List<Long> getTxnIdsForNonRegisterWallets(Long status, Date date) throws WalletException;
	
	NonRegisterWallet updateNonRegisterWallet(Long txnId, Long status) throws WalletException;
	
	void sendMailUserAccountReject(String fullName, String email, String rejectMessage, String userType, Long languageId);
	
	void sendMailForUserStatusUpdate(String fullName, String email, String userStatus, Boolean isActive,  String reason, String userType, Long languageId);

	String getUserEmailById(Long id) throws WalletException;
	
	void sendMailForFeedbackReply(Feedback feedback);
	
	PhoneNumber getPhoneNumber(String emailid, String userType);
	
	Boolean mobileRegistration(String emailid, String userType, String password, String msisdnNumber, String simNumber, String imeiNumber)throws WalletException;
	
	Boolean mPinGeneration(String emailid, String userType, String msisdnNumber, String simNumber, String imeiNumber, String mPin, String personName, String userTypeName)throws WalletException;
	
	Authentication getDeviceLoginAuthentication(String email, String mPin, String userType, Long typeOfRequest) throws WalletException ;

	Boolean deActivateDevice(String emailId, String userType, String personName, String userTypeName)throws WalletException;
	
	Boolean validateMpin(String email, String userType, String mPin) throws WalletException;

	void sendEmailNotificationToMobileUser(String name, String userTypeName, Long authId, Long languageId, String emailTemplate) throws WalletException;
	
	void sendForgotMPINEmailNotificationToMobileUser(String name, String userTypeName, Long authId, Long languageId, String emailTemplate) throws WalletException;
	
	PhoneNumber getPhoneNumberByCode(String phoneCode, String phoneNo);
	
	Boolean activateMobileRegistration(String emailid, String userType, String mPin, String msisdnNumber, String simNumber, String imeiNumber)throws WalletException;

	PhoneNumber getPhoneNumber(String phoneNo);

}
