package com.tarang.ewallet.common.dao;

import java.util.Date;
import java.util.List;

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
 * @Comments: Dao interface to provide eWallet Services
 */
public interface CommonDao {

	// address dao services
	Address createAddress(Address address) throws WalletException;

	Address getAddress(Long id) throws WalletException;

	void updateAddress(Address address) throws WalletException;

	// person name dao services
	PersonName createPersonName(PersonName personName) throws WalletException;

	PersonName getPersonName(Long id) throws WalletException;

	void updatePersonName(PersonName personName) throws WalletException;

	// authentication dao services
	Authentication createAuthentication(Authentication authentication)
			throws WalletException;

	Authentication getAuthentication(Long id) throws WalletException;

	void updateAuthentication(Authentication authentication)
			throws WalletException;

	// phone number business services
	PhoneNumber createPhone(PhoneNumber phoneNumber) throws WalletException;

	PhoneNumber getPhone(Long id) throws WalletException;
	
	Boolean deletePhone(PhoneNumber phoneNumber) throws WalletException;

	void updatePhone(PhoneNumber phoneNumber) throws WalletException;

	// hints dao services
	Hints createHints(Hints hints) throws WalletException;

	Hints getHints(Long id) throws WalletException;

	void updateHints(Hints hints) throws WalletException;

	Boolean deleteAuthentication(Long authenticationId) throws WalletException;

	//Reset password
	Authentication getAuthentication(String email) throws WalletException;

	Authentication getAuthentication(String email, String userType)throws WalletException;
	
	Boolean phoneNOExist(String phoneNO, String phoneCode) throws WalletException;

	void validateUserSession(Long authenticationId, String userType)throws WalletException;
	
	Feedback createFeedback(Feedback feedback) throws WalletException;
	
	Long emailIdExist(String emailId, String userType);
	
	Boolean emailIdExist(String emailId);

	List<PasswordHistory> getPasswordHistory(Long authenticationId) throws WalletException;
	
	PasswordHistory createPasswordHistory(PasswordHistory passwordHistory) throws WalletException;
	
	void deletePasswordHistory(PasswordHistory passwordHistory) throws WalletException;
	
    Preferences createPreferences(Preferences preferences)throws WalletException;
	
	void updatePreferences(Preferences preferences)throws WalletException;
	
	Preferences getPreferences(Long authId)throws WalletException;

	UserWallet createUserWallet(UserWallet userWallet)throws WalletException;
	
	UserWallet getUserWallet(Long authId, Long currency) throws WalletException;
	
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
	
	List<Object[]> getCustomersTotalAmountsByCountry(String userType)throws WalletException;

	NonRegisterWallet createNonRegisterWallet(NonRegisterWallet nonRegisterWallet);

	List<NonRegisterWallet> getMoneyFromTemporaryWallet(String email);
	
	List<Object[]> getCustomerBalancesByUserId(Long authId);

	void updateTemporaryWalletRecord(NonRegisterWallet nonRegisterWallet);

	List<UserWallet> getUserWallets(Long authId);
	
	UserIP saveUserIPAddress(UserIP userIP) throws WalletException;
	
	UserIP getUserIPAddress(Long authId, String ipAddress) throws WalletException;

	UserIP updateUserIPAddress(UserIP userIP) throws WalletException;

	Integer updateIpAddressCheck(Long authId, Boolean flag) throws WalletException;
	
	Integer updateEmailPatternCheck(Long authId, Boolean flag) throws WalletException;
	
	Integer updateChargeBackCheck(Long authId, Boolean flag) throws WalletException;
	
	List<Long> getTxnIdsForNonRegisterWallets(Long status, Date date) throws WalletException;
	
	NonRegisterWallet updateNonRegisterWallet(Long txnId,Long status) throws WalletException;
	
	String getUserEmailById(Long id) throws WalletException;

	PhoneNumber getPhoneNumber(String emailid, String userType);

	PhoneNumber getPhoneNumberByCode(String phoneCode, String phoneNo);

	PhoneNumber getPhoneNumber(String phoneNo);
	
}
