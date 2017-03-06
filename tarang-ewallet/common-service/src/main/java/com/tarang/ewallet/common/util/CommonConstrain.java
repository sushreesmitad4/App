package com.tarang.ewallet.common.util;


/**
 * @Author : kedarnathd
 * @Date : Oct 18 2012
 * @Time : 6.30PM
 * @Version : 1.0
 * @Comments: Common constrain file
 */
public interface CommonConstrain extends UserStatusConstants{

	String REGIONS = "regions";
	String SUB_CATEGORIES = "subcategories";
	String FEE_TYPES = "feetypes";
	String OPERATION_TYPES = "operationtypes";
	String REGISTERED_MEMBER = "registeredmember";
	String SELF_TRANS_CURRENCY = "selftranscurrency";
	
	String USER_ACCOUNT_DELETED = "login.account.deleted";
	String USER_BLOCK = "login.user.blocked";
	String EMAIL_MATCH_FAIL = "login.email.not.matched";
	String PASSWORD_MATCH_FAIL = "login.password.not.matched";
	String EMAIL_OR_PASSWORD_FAIL = "login.email.password.failed";
	String ACCOUNT_DEACTIVE = "login.account.deactive";
	String ACCOUNT_REJECTED = "login.account.rejected";
	String OLD_AND_NEW_PASSWORD_SAME = "change.error.oldnewpasswordsame";
	
	String USER_SESSION_NAME = "EWALLET_MERCHANT_USER";
	
	String WRONG_USER_TYPE = "User type does not match. It should be A or C or M";
	String EMAIL_ID_DOES_NOT_MATCH = "login.email.not.matched";
	
	Boolean FALSE = false;
	Boolean TRUE = true;
	Boolean KYC_REQUIRED = false;
	
	String HINT_QUESTION_ONE_NOT_MATCHING = "select.correct.question.errmsg";
	
	String HINT_ANSW_ONE_NOT_MATCHING = "enter.correct.answer.errmsg";
	
	String OLD_PASSWORD_MATCH_FAIL = "old.new.password.match.errmsg";
	
	String RESET_PASSWORD = "password.reset";
	String LOGIN_STATUS = "account.allready.login";
	
	Double DEFAULT_WALLET_AMOUNT = 0.0; 
	Long DEFAULT_CURRENCY = 1L;
	Long DEFAULT_LANGUAGE = 1L;
	Long DEFAULT_HINTS = 0L;
	Long DEFAULT_STATUS_ADMIN = UserStatusConstants.APPROVED;
	
	String EMAIL_VARIFICATION_NOT_DONE = "login.email.varification.not.done";
	
	Long DEFAULT_STATUS = UserStatusConstants.PENDING;
	
	Long DEFAULT_STATUS_MOBILE = UserStatusConstants.APPROVED;
	
	Integer DEFAULT_ATTEMPTS = 0;
	String DEFAULT_CODE = "0";
	
	String LOGIN_ERROR_INVALID_USER = "login.error.invalid.user";
	String PHONE_NUMBER_CREATE_EXCEPTION = "common.phone.duplicate";
	String ADDRESS_CREATE_EXCEPTION = "Fails to create address";
	String ADDRESS_UPDATE_EXCEPTION = "Fails to update address";
	String PERSON_NAME_CREATE_EXCEPTION = "Fails to create person name";
	String PERSON_NAME_UPDATE_EXCEPTION = "Fails to update person name";
	String AUTHENTICATION_UPDATE_EXCEPTION = "Fails to update authentication";
	String AUTHENTICATION_CREATE_EXCEPTION = "Fails to create authentication";
	String UNIQUE_ADDRESS_EXCEPTION = "unique.address.exception";
	String AUTHENTICATION_FAILS_TO_DELETE = "failure delete authentication";
	String UNIQUE_PHONE_NUMBER_EXCEPTON = "unique PhoneNumber exception";
	String PHONE_NUMBER_UPDATE_EXCEPTION = "Fails to update phone number";
	String HINTS_CREATE_EXCEPTION = "Fails to create hints";
	String UNIQUE_HINTS_EXCEPTION = "unique Hints exception";
	String CUSTOMER_REGISTRATION_FAILED = "Customer registration failed";
	String INVALID_ADDRESS = "Invalid Addresses";
	String DATA_INTEGRITY_VIOLATION = "DataIntegrityViolationException";
	String ROLE_CREATE_EXCEPTION = "role.create.fails.errmsg";
	String DUPLICATE_ENTRY = "Duplicate entry";
	String ROLE_UPDATE_EXCEPTION = "role.update.fails";
	String CUSTOMER_UPDATE_FAILED = "Customer update failed";
	String FEEDBACK_CREATE_EXCEPTION = "Fails to create feedback";
	String PREFERENCES_CREATE_EXCEPTION = "fails.set.preferences";
	String PREFERENCES_UPDATE_EXCEPTION = "fails.update.preferences";
	String PREFERENCES_RETRIVE_EXCEPTION = "fails.retrive.preferences";
	String FEEDBACK_RETRIVE_EXCEPTION = "fails.retrive.feedbacklist";
	String REPLY_SEND_EXCEPTION = "fails.send.feedbackreply";
	String USER_WALLET_CREATE_EXCEPTION = "fails to create user wallet";
	String USER_WALLET_RETRIVE_EXCEPTION = "fails to retrive user wallet";
	String USERIP_CREATE_EXCEPTION = "Fails to create user IP address";
	String USERIP_UPDATE_EXCEPTION = "Fails to update user IP address";
	String PHONE_DELETE_EXCEPTION = "fails.delete.phonenumber";
	
	Long EMAIL_ALLREADY_REGISTER = 1L;
	Long EMAIL_REGISTER_NOT_VARIFIED = 2L;
	Long EMAIL_NOT_EXIT = 3L;
	Long EMAIL_REGISTER_ACC_DELETED = 4L;
	
	String PASSWORD_HISTORY_EXIST = "password.history.exist";
	
	//Audit common constants
	String AUDIT_CREATE_EXCEPTION = "Fails to create audit";
	
	String VELOCITY_CREATE_EXCEPTION = "fails.create.velocity";
	
	String VELOCITY_UPDATE_EXCEPTION = "fails.update.velocity";
	
	String VELOCITY_RETRIVE_EXCEPTION = "fails.retrive.velocity";
	
	String ACCEPT_RECEIVE_MONEY_EXCEPTION = "fails.to.accept.money";
	
	String REJECT_RECEIVE_MONEY_EXCEPTION = "fails.to.reject.money";
	
	String RECEIVEMONEY_CREATE_FAILS_EXCEPTION = "receivemoney.create.failed";
	
	String DELETED_USER_FAILS_EXCEPTION = "error.msg.deleted.user.account";
	
	String USER_ACCOUNT_CLOSED_EXCEPTION ="accountcloser.error.useraccountclosed";

	String EMAIL_DOMAIN_NOT_ALLOWED = "domain.not.allowed";
	
	String CUSTOMER_EMAILID_NOT_EXIST = "customer.emailid.notexist";
	
	String EMAIL_VARIFIED_SUCCESS_MSG = "email.varified.success.msg";
	
	String EMAIL_VARIFIED_FAILED_MSG = "email.varified.failed.msg";
	
	String EMAIL_VARIFICATION_LINK_EXPIRED = "email.varification.link.expired";
	
	String SELECTED_RECORD_DELETED_ERRMSG = "selected.record.deleted.errmsg";
	
	String UNIQUE_CONSTRAINT = "could not execute update query";
	
	String UNABLE_TO_REGISTERED_YOUR_MOBILE_WALLET = "unable.to.registered.your.mobile.wallet";

	String ALREADY_REGISTOR_AS_MOBILE_WALLET = "already.registor.as.mobile.wallet";
	
	String USER_NOT_REGISTER_AS_MOBILE_WALLET = "user.not.register.as.mobile.wallet";
	
	String FAILED_TO_GENERATE_M_PIN = "failed.to.generate.m.pin";
	
	String MOBILE_USER_BLOCK = "device.login.user.blocked";
	
	String MPIN_MATCH_FAIL = "login.mpin.not.matched";
	
	String FAILED_TO_DEACTIVATE_DEVICE = "failed.to.deactivate.device";
	
    String USER_DOES_NOT_EXIST = "user.does.not.exist";
    
    String MOBILE_WALLET_ACCOUNT_IS_NOT_ACTIVE = "mobile.wallet.account.is.not.active";
    
    String USER_REQUESTED_FROM_DIFFERENT_DEVICE_OR_SIM = "user.requested.from.different.device.or.sim";

    String MOBILEWALLET_ACCOUNT_EXISTS_GENERATE_MPIN = "mobilewallet.account.exists.generate.mpin";
    
    String UPDATE_PHONENUMBER = "update PhoneNumber";
    
    String INSERT_PHONENUMBER ="insert into PhoneNumber";
    
    String UNIQUE_CUSTOMER_EXCEPTION = "unique.customer.exception";
    
	String FIRST_NAME = "firstName";
	
    String STATUS ="status";
    
	String CREATION_DATE ="creationDate";
	
    String EMAIL_ID = "emailId";
    
    String LEGAL_NAME ="legalName";
    
    String FIXCHAR_SEN ="fixCharSen";
    
    String FEE_TYPE = "feeType";
    
    String FIXCHAR_REC = "fixCharRec";
    
    String SERVICES = "services";
    
    String OPERATION_TYPE = "operationType";
    
    String PERCENTAGE_REC = "percentageRec";
    
    String  PAYING_ENTITY = "payingentity";
    
    String  PERCENTAGE_SEN = "percentageSen";
    
    String BLOCKED = "blocked" ;
    
    String ACTIVE = "active";
    
    String ROLE_NAME = "roleName" ;
    
    String NOROLES_FOUND = "No roles found";
    
    String UNABLE_TOCLOSE_ACCOUNT_DISPUTE = "unable.to.close.account.dispute";
    
    String COULD_NOT_EXECUTE_JDBC_BATCH_UPDATE = "Could not execute JDBC batch update";
    
    String PHONE_NUMBER_NOT_EXIST = "phone.number.does.not.exist";
    
    String MERCHANT_NOT_EXIST = "merchant.does.not.exist";
    
    String FAILED_TO_SENT_OTP_TO_DEVICE = "failed.to.sent.otp.to.device";
    
    String FAILED_TO_AUTHENTICAT_OTP = "failed.to.authenticat.otp";
    
    String CUSTOMER_MOBILE_NOT_EXIST = "customer.mobile.not.exist";
    
    String MERCHANT_MOBILE_NOT_EXIST = "merchant.mobile.not.exist";
    
    String INSERT_INTO_AUTHENTICATION = "insert into Authentication";
    
    String ALL_READY_REGISTER_USER = "all.ready.register.user";
    
    String OK = "OK";
    
     
}
