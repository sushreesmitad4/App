package com.tarang.ewallet.common.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.common.dao.CommonDao;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.UserStatusConstants;
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
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryConstants;


/**
 * @Author : kedarnathd
 * @Date : Oct 08, 2012
 * @Time : 10:09:24 PM
 * @Version : 1.0
 * @Comments: DaoImpl Class to provide eWallet Services and it implements
 *            CommonDao interface
 */
@SuppressWarnings({ "unchecked", "rawtypes"})
public class CommonDaoImpl implements CommonDao, QueryConstants {
	
	private static final Logger LOGGER = Logger.getLogger(CommonDaoImpl.class);
	private static final String CUSTOMER_PHONE_NUMBER_QUERY = "select * from phonenumber where id in (select phoneidone from customer" +
			" where authenticationid in (select id from authentication where emailid= :_emailid and usertype=:_userType))";
	
	private static final String MERCHANT_PHONE_NUMBER_QUERY = "select * from phonenumber where id in (select phoneid from businessinformation " +
			"where id in (select businessinformationid from merchant where authenticationid in " +
			"(select id from authentication where emailid=:_emailid and usertype=:_userType)))";
	
	private static final String GET_PHONE_NUMBER_QUERY = "select * from phonenumber where code= :_pcode and pnumber= :_pnumber";
	
	
	private static final String LASTDAY_LASTWEEK_CREATEDACCOUNTS_QUERY = "select count(*) from Authentication as au where emailVarification=true and au.creationDate between :_fromDate and :_toDate and au.userType = :_type";
	private HibernateOperations hibernateOperations;
	
	public CommonDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public Address createAddress(Address address) throws WalletException {
		try{
			hibernateOperations.save(address);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.ADDRESS_CREATE_EXCEPTION, e);
		}
		return address;
	}

	@Override
	public Address getAddress(Long id) throws WalletException {
		List<Address> list = (List<Address>) hibernateOperations.findByNamedQuery("getAddressById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			throw new WalletException(CommonConstrain.UNIQUE_ADDRESS_EXCEPTION);
		}
	}

	@Override
	public void updateAddress(Address address) throws WalletException {
		try{
			hibernateOperations.update(address);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.ADDRESS_UPDATE_EXCEPTION, e);
		}

	}

	@Override
	public PersonName createPersonName(PersonName personName) throws WalletException {
		try{
			hibernateOperations.save(personName);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.PERSON_NAME_CREATE_EXCEPTION, e);
		}
		return personName;
	}

	@Override
	public PersonName getPersonName(Long id) throws WalletException {
		List<PersonName> list = (List<PersonName>) hibernateOperations.findByNamedQuery("getPersonNameById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else{
			throw new WalletException(CommonConstrain.UNIQUE_ADDRESS_EXCEPTION);
		}
	}

	@Override
	public void updatePersonName(PersonName personName) throws WalletException {
		try{
			hibernateOperations.update(personName);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.PERSON_NAME_UPDATE_EXCEPTION, e);
		}
	}

	@Override
	public Authentication createAuthentication(Authentication authentication)
			throws WalletException {
		try{
			hibernateOperations.save(authentication);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.AUTHENTICATION_CREATE_EXCEPTION, e);
		}
		return authentication;
	}

	@Override
	public Authentication getAuthentication(Long id) throws WalletException {
		List<Authentication> list = (List<Authentication>) hibernateOperations.findByNamedQuery(GETAUTHENTICATIONBYID, id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public void updateAuthentication(Authentication authentication)
			throws WalletException {
		try{
			hibernateOperations.update(authentication);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.AUTHENTICATION_UPDATE_EXCEPTION, e);
		}
	}


	@Override
	public PhoneNumber createPhone(PhoneNumber phoneNumber) throws WalletException {
		try{
			hibernateOperations.save(phoneNumber);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION, e);
		}
		return phoneNumber;
	}
	
	/*This service method called for deleteAdminUser*/
	@Override
	public Boolean deleteAuthentication(final Long authenticationId) throws WalletException {
		List<Authentication> list = (List<Authentication>) hibernateOperations.findByNamedQuery(GETAUTHENTICATIONBYID, authenticationId);
		if (list != null && list.size() == 1) {
			Authentication au = list.get(0);
			au.setActive(Boolean.FALSE);
			au.setStatus(UserStatusConstants.DELETED);
			hibernateOperations.update(au);
			return true;
		}else {
			throw new WalletException(CommonConstrain.AUTHENTICATION_FAILS_TO_DELETE);
		}
	}

	@Override
	public PhoneNumber getPhone(Long id) throws WalletException {
		List<PhoneNumber> list = (List<PhoneNumber>) hibernateOperations.findByNamedQuery("getPhoneById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			throw new WalletException(CommonConstrain.UNIQUE_PHONE_NUMBER_EXCEPTON);
		}
	}

	@Override
	public void updatePhone(PhoneNumber phoneNumber) throws WalletException {
		try{
			hibernateOperations.update(phoneNumber);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.PHONE_NUMBER_UPDATE_EXCEPTION, e);
		}

	}

	@Override
	public Hints createHints(Hints hints) throws WalletException {
		try{
			hibernateOperations.save(hints);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.HINTS_CREATE_EXCEPTION, e);
		}
		return hints;
	}

	@Override
	public Hints getHints(Long id) throws WalletException {
		List<Hints> list = (List<Hints>) hibernateOperations.findByNamedQuery(
				"getHintsById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			throw new WalletException(CommonConstrain.UNIQUE_HINTS_EXCEPTION);
		}
	}

	@Override
	public void updateHints(Hints hints) throws WalletException {
		hibernateOperations.update(hints);
	}

	@Override
	public Authentication getAuthentication(String emailId) throws WalletException {
		List<Authentication> list = hibernateOperations.findByNamedQuery("findAuthenticatonByEmail", emailId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}else {
			throw new WalletException(CommonConstrain.UNIQUE_ADDRESS_EXCEPTION);
		}
	}

	@Override
	public Authentication getAuthentication(String email, String userType) throws WalletException {
		List<Authentication> list = hibernateOperations.findByNamedQuery("findAuthenticatonByEmailAndUserType", new Object[]{email, userType});
		if (list != null && list.size() == 1) {
			return list.get(0);
		} 
		return null;
	}

	public Boolean phoneNOExist(String phoneCode, String phoneNO) throws WalletException {
		Boolean flag = false;
		List<PhoneNumber> list = hibernateOperations.findByNamedQuery("findAuthenticatonByPhoneNO", phoneNO);
		String newPhoneNumber = phoneCode + phoneNO;
		for(PhoneNumber pnum : list){
			if(newPhoneNumber.equals(pnum.getCode()+phoneNO)){
				flag = true;
			}
		}
		return flag;				
	}

	@Override
	public void validateUserSession(Long authenticationId, String userType)
			throws WalletException {
		if(authenticationId != null){
			Authentication authentication = getAuthentication(authenticationId);
			authentication.setLoginStatus(CommonConstrain.FALSE);
			authentication.setLastLogin(new Date());
			updateAuthentication(authentication);
		}
	}

	@Override
	public Feedback createFeedback(Feedback feedback) throws WalletException {
		try{
			hibernateOperations.save(feedback);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.ADDRESS_CREATE_EXCEPTION, e);
		}
		return feedback;
	}

	@Override
	public Long emailIdExist(String emailId, String userType){
		List<Authentication> deletedList = hibernateOperations.findByNamedQuery("findAuthenticatonwithAccountDeleted", new Object[]{UserStatusConstants.DELETED, emailId, userType});
		if (deletedList != null && deletedList.size() > 0) {
			return CommonConstrain.EMAIL_REGISTER_ACC_DELETED;
		}
		List<Authentication> verifiedList = hibernateOperations.findByNamedQuery("findAuthenticatonwithEmailVarified", new Object[]{emailId, userType});
		if (verifiedList != null && verifiedList.size() > 0) {
			return CommonConstrain.EMAIL_ALLREADY_REGISTER;
		} 
		List<Authentication> notVerifiedList = hibernateOperations.findByNamedQuery("findAuthenticatonwithEmailNotVarified", new Object[]{emailId, userType});
		if (notVerifiedList != null && notVerifiedList.size() > 0) {
			return CommonConstrain.EMAIL_REGISTER_NOT_VARIFIED;
		}
		return CommonConstrain.EMAIL_NOT_EXIT;
	}
	
	@Override
	public Boolean emailIdExist(String emailId){
		List<Authentication> isExistList = hibernateOperations.findByNamedQuery("findAuthenticatonwithEmail", emailId);
		if (isExistList != null && isExistList.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public PasswordHistory createPasswordHistory(PasswordHistory passwordHistory) throws WalletException {
		try{
			hibernateOperations.save(passwordHistory);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.AUTHENTICATION_CREATE_EXCEPTION, e);
		}
		return passwordHistory;
	}

	@Override
	public void deletePasswordHistory(PasswordHistory passwordHistory) throws WalletException {
		try{
			hibernateOperations.delete(passwordHistory);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.PERSON_NAME_CREATE_EXCEPTION, e);
		}
	}

	@Override
	public List<PasswordHistory> getPasswordHistory(Long authenticationId) throws WalletException {
		return hibernateOperations.findByNamedQuery("findPasswordHistoryByAuthId", new Object[]{authenticationId});
	}

	@Override
	public Preferences createPreferences(Preferences preferences) throws WalletException {
		try{
			hibernateOperations.save(preferences);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.PREFERENCES_CREATE_EXCEPTION, e);
		}
		return preferences;
	}

	@Override
	public void updatePreferences(Preferences preferences) throws WalletException {
		try{
			hibernateOperations.update(preferences);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.PREFERENCES_UPDATE_EXCEPTION, e);
		}
	}

	@Override
	public Preferences getPreferences(Long authId) throws WalletException {
		List<Preferences> list = (List<Preferences>) hibernateOperations.findByNamedQuery("getPrefereances", authId);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			throw new WalletException(CommonConstrain.PREFERENCES_RETRIVE_EXCEPTION);
		}
	}

	@Override
	public UserWallet createUserWallet(UserWallet userWallet) throws WalletException {
		try{
			hibernateOperations.save(userWallet);
			return userWallet;
		}catch(Exception e){
			throw new WalletException(CommonConstrain.USER_WALLET_CREATE_EXCEPTION, e);
		}
	}
	
	@Override
	public UserWallet getUserWallet(Long authId, Long currency) throws WalletException {
		List<UserWallet> list = (List<UserWallet>) hibernateOperations.findByNamedQuery("getUserWalletByCurrency",  new Object[]{authId, currency});
		if (list != null && list.size() == 1) {
			return list.get(0);
		} 
		return null;
	}

	@Override
	public UserWallet updateUserWallet(UserWallet userWallet) throws WalletException {
		hibernateOperations.update(userWallet);
		return userWallet;
	}
	
	@Override
	public Long getLastDayCreatedNumberOfAccounts(final Date fromDate, final Date toDate, final String type)throws WalletException {
		
		try{
			List list = hibernateOperations.executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws SQLException {
				    Query q = session.createQuery(LASTDAY_LASTWEEK_CREATEDACCOUNTS_QUERY);
				    q.setParameter(Q_FROM_DATE, fromDate);
			         q.setParameter(Q_TO_DATE, toDate);
			         q.setParameter(Q_TYPE, type);
			         return q.list();
				}
			});
			return (Long)list.get(0);
		}catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return null;
		}
	}
    
	@Override
	public Long getLastWeekCreatedNumberOfAccounts(final Date date, final String type) throws WalletException {
		
		try{
			List list = hibernateOperations.executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws SQLException {
				    Query q = session.createQuery(LASTDAY_LASTWEEK_CREATEDACCOUNTS_QUERY);
				    q.setParameter(Q_FROM_DATE, date);
				    q.setParameter(Q_TO_DATE, new Date());
				    q.setParameter(Q_TYPE, type);
				    return q.list();
				}
			});
			return (Long)list.get(0);
		}catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return null;
		}
	}

	@Override
	public Long getTotalNumberOfAccounts(String userType) throws WalletException{
		List l = hibernateOperations.findByNamedQuery("getTotalNoOfCustomersOrMerchants", userType);
		return (Long)l.get(0);
	}

	@Override
	public MasterAmountWallet getMasterAmountWallet(Long currencyId) {
		List<MasterAmountWallet> list = (List<MasterAmountWallet>) hibernateOperations.findByNamedQuery("getMasterAmountWalletByCurrencyId", currencyId);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public MasterAmountWallet updateMasterAmountWallet(MasterAmountWallet wallet) {
		hibernateOperations.update(wallet);
		return wallet;
	}

	
	@Override
	public MasterFeeWallet getMasterFeeWallet(Long currencyId) {
		List<MasterFeeWallet> list = (List<MasterFeeWallet>) hibernateOperations.findByNamedQuery("getMasterFeeWalletByCurrencyId", currencyId);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public MasterFeeWallet updateMasterFeeWallet(MasterFeeWallet wallet) {
		hibernateOperations.update(wallet);
		return wallet;
	}

	@Override
	public MasterTaxWallet getMasterTaxWallet(Long currencyId) {
		List<MasterTaxWallet> list = (List<MasterTaxWallet>) hibernateOperations.findByNamedQuery("getMasterTaxWalletByCurrencyId", currencyId);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public MasterTaxWallet updateMasterTaxWallet(MasterTaxWallet wallet) {
		hibernateOperations.update(wallet);
		return wallet;
	}

	@Override
	public List<Object[]> getCustomersTotalAmountsByCountry(String userType) throws WalletException {
	        return hibernateOperations.findByNamedQuery("getCustomersTotalAmountsByCountry", userType);
	}

	@Override
	public NonRegisterWallet createNonRegisterWallet(NonRegisterWallet nonRegisterWallet) {
		hibernateOperations.save(nonRegisterWallet);
		return nonRegisterWallet;
	}

	@Override
	public List<NonRegisterWallet> getMoneyFromTemporaryWallet(String email) {
		List<NonRegisterWallet> list = (List<NonRegisterWallet>) hibernateOperations.findByNamedQuery("getNonRegisterWalletsByemail", email);
		if (list != null && list.size() > 0) {
			return list;
		}
		return list;
	}

	@Override
	public void updateTemporaryWalletRecord(NonRegisterWallet nonRegisterWallet) {
		hibernateOperations.update(nonRegisterWallet);	
	}

	@Override
	public List<Object[]> getCustomerBalancesByUserId(Long authId) {
		return hibernateOperations.findByNamedQuery("getCustomerBalanceBycountryId", authId);
	}

	@Override
	public List<UserWallet> getUserWallets(Long authId) {
		List<UserWallet> list = (List<UserWallet>) hibernateOperations.findByNamedQuery("getAllUserWalletsByAuthId", authId);
		if (list != null && list.size() > 0) {
			return list;
		} 
		return null;
	}

	@Override
	public UserIP saveUserIPAddress(UserIP userIP) throws WalletException {
		try{
			hibernateOperations.save(userIP);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.USERIP_CREATE_EXCEPTION, e);
		}
		return userIP;
	}

	@Override
	public UserIP getUserIPAddress(Long authId, String ipAddress) throws WalletException {
		List<UserIP> list = (List<UserIP>) hibernateOperations.findByNamedQuery("findUserIpExistOrNot",  new Object[]{authId, ipAddress});
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public UserIP updateUserIPAddress(UserIP userIP) throws WalletException {
		try{
			hibernateOperations.update(userIP);
			hibernateOperations.flush();
		}catch(Exception e){
			throw new WalletException(CommonConstrain.USERIP_UPDATE_EXCEPTION, e);
		}
		return userIP;
	}

	@Override
	public Integer updateIpAddressCheck(final Long authId, final Boolean flag)
			throws WalletException {
		Authentication auth = getAuthentication(authId);
		auth.setIpAddressCheck(flag);
		hibernateOperations.update(auth);
		return Integer.valueOf(1);
	}

	@Override
	public Integer updateEmailPatternCheck(final Long authId, final Boolean flag)
			throws WalletException {
		Authentication auth = getAuthentication(authId);
		auth.setEmailPatternCheck(flag);
		hibernateOperations.update(auth);
		return Integer.valueOf(1);
	}

	@Override
	public Integer updateChargeBackCheck(final Long authId, final Boolean flag)
			throws WalletException {
		Authentication auth = getAuthentication(authId);
		auth.setChargeBackCheck(flag);
		hibernateOperations.update(auth);
		return Integer.valueOf(1);	
	
	}

	@Override
	public Boolean deletePhone(PhoneNumber phoneNumber) throws WalletException {
		try{
			hibernateOperations.delete(phoneNumber);
			hibernateOperations.flush();
			return Boolean.TRUE;
		}catch(Exception e){
			throw new WalletException(CommonConstrain.PHONE_DELETE_EXCEPTION, e);
		}
	}
	
	@Override
	public List<Long> getTxnIdsForNonRegisterWallets(Long status, Date date) throws WalletException {
		List<Object> listOfObjects = (List<Object>) hibernateOperations.findByNamedQuery("getTxnIdsForNonRegisterWallets", status, date);
		List<Long> list =new ArrayList<Long>();
		for (int i = 0; i < listOfObjects.size(); i++) {
			list.add((Long)listOfObjects.get(i));
		}
		return list;
	}
	
	@Override
	public NonRegisterWallet updateNonRegisterWallet(Long txnId,Long status) throws WalletException {
		List<Object> listOfObjects = (List<Object>) hibernateOperations.findByNamedQuery("getNonRegisterWalletByTxnId", txnId);
		NonRegisterWallet nonRegisterWallet=null;
		if( listOfObjects.size() == 1){
			nonRegisterWallet =(NonRegisterWallet) listOfObjects.get(0);
			nonRegisterWallet.setRegister(status);
			hibernateOperations.update(nonRegisterWallet);
		}else{
			throw new WalletException("Non register wallet not available");
		}
		return nonRegisterWallet;
	}

	@Override
	public String getUserEmailById(Long id) throws WalletException {
		List<String> list = (List<String>) hibernateOperations.findByNamedQuery("getUserEmailById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}else {
			throw new WalletException(CommonConstrain.UNIQUE_ADDRESS_EXCEPTION);
		}
	}

	@Override
	public PhoneNumber getPhoneNumber(final String emailid, final String userType) {
		List<PhoneNumber> list = (List<PhoneNumber>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(GlobalLitterals.CUSTOMER_USER_TYPE.equals(userType) ? CUSTOMER_PHONE_NUMBER_QUERY: MERCHANT_PHONE_NUMBER_QUERY);
				q.setParameter(EMAILID, emailid);
				q.setParameter(USERTYPE, userType);
				return getPhoneNumber(q.list());
			}
		});
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
	@Override
	public PhoneNumber getPhoneNumberByCode(final String phoneCode, final String phoneNo) {
		List<PhoneNumber> list = (List<PhoneNumber>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(GET_PHONE_NUMBER_QUERY);
				q.setParameter(PHONE_CODE, phoneCode);
				q.setParameter(PHONE_NUMBER, phoneNo);
				return getPhoneNumber(q.list());
			}
		});
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
	private List<PhoneNumber> getPhoneNumber(List<Object[]> list){
		List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
		PhoneNumber phoneNumber = null;
		if(null == list || list.isEmpty()){
			return null;
		}
		Object[] objects = list.get(0);
		phoneNumber = new PhoneNumber();
		phoneNumber.setId(((BigInteger)objects[0]).longValue());
		phoneNumber.setCode((String)objects[1]);
		phoneNumber.setPnumber((String)objects[2]);
		phoneNumberList.add(phoneNumber);
		return phoneNumberList;
	}
	
	@Override
	public PhoneNumber getPhoneNumber(final String phoneNo) {		
		List<PhoneNumber> list = hibernateOperations.findByNamedQuery("findAuthenticatonByPhoneNO", new Object[]{phoneNo});
		if (list != null && list.size() == 1) {
			return list.get(0);
		} 
		return null;
	}
	
	
	/*@Override
	public Authentication getAuthenticationByphoneNo(String phoneno, String userType) throws WalletException {
		List<Authentication> list = hibernateOperations.findByNamedQuery("findAuthenticatonByPhonenoAndUserType", new Object[]{phoneno, userType});
		if (list != null && list.size() == 1) {
			return list.get(0);
		} 
		return null;
	}*/
	
}