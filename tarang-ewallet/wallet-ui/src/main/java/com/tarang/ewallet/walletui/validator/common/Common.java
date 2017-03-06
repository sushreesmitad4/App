/**
 * 
 */
package com.tarang.ewallet.walletui.validator.common;

/**
 * @author  : prasadj
 * @date    : Dec 1, 2012
 * @time    : 12:30:15 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface Common {
	
	int LOGIN_SERVICE_REQ = 1;
	
	int LOGOUT_SERVICE = 2;
	
	int CUSTOMER_REGISTRATION_SERVICE = 3;

	String CUSTOMER_MOBILE_NOT_EXIST ="customer.mobile.not.exist";
	
	String PLEASE_CHECK_YOUR_INPUTS ="please.check.your.inputs";
	
	String FIRST_NAME = "firstname";
	
	String LAST_NAME = "lastname";
	
	String NAME = "name";
	
	String PASSWORD_ERROR_MSG = "password.error.msg";
	
	String EMAIL_ERROR_MSG = "email.error.msg";
	
	String URL_ERROR_MSG = "url.error.msg";
			                          
	String NAME_EXPRESSION = "[^!$%^#@(),&*=+|?<>\\\\`~{}\\[\\];:]*";
	
	String NAME_EXPRESSION_JAVA_SCRIPT = "/^" + NAME_EXPRESSION + "$/";

	String ADDRESS_EXPRESSION = "[^!$%^&*=+|?<>\\\\`~{}\\[\\];:]*";
	
	String MESSAGE_EXPRESSION = "[^!%^&*=+|<>\\\\`~{}\\[\\];:]*";

	String NUMBER_EXPRESSION = "[0-9]*";
	
	String DOUBLE_EXPRESSION = "\\d{0,2}\\.\\d{1,3}";
	
	String CURRENCY_EXPRESSION = "\\d{0,9}(\\.\\d{1,2})?";
	
	Double CURRENCY_MINIMUM_LIMIT = 100.0;
	
	Double CURRENCY_MAX_LIMIT = 1000000000.0;

	String CARD_NUMBER_FIELD = "cardNumber";

	String CARD_NUMBER_ERROR_MSG = "card.number.required.errmsg";
	
	String ALPHA_BIT = "[a-zA-Z]*";
	
	String ALPHA_NUMERIC = "[a-zA-Z0-9]*";
	
	String ROUTING_NUMBER_EXP = ALPHA_NUMERIC;
	
	String BARNCH_CODE_EXP = ALPHA_NUMERIC;
	
	String ZINGIN_BANK_AND_BARNCH_CODE_EXP = ALPHA_NUMERIC;
	
	String TERMS_VAR = "terms";
	
	String ACCOUNT_NUMBER_EXP = ALPHA_NUMERIC;
	
	String USER_JOBNAME_EXP = ALPHA_NUMERIC;
	
	String REAL_NUM_EXP = "\\d{0,10000}(\\.\\d{1,2})?";
	
	String EMAIL_VAR = "emailId";
	
	String EMAIL_NOTE_VAR = "emailNote";
	
	String USER_JOBNAME_VAR = "userJobName";
	
	char[] PHONE_NUMBER_ZERO_CHECK = {'0'};
	
	//firstname 
	int NAME_MIN_LENGTH = 1;
	int NAME_MAX_LENGTH = 25;
	
	//firstname 
	int FIRST_NAME_MIN_LENGTH = NAME_MIN_LENGTH;
	int FIRST_NAME_MAX_LENGTH = NAME_MAX_LENGTH;
	String FIRST_NAME_REQUIRED = "firstname.required.errmsg";
	String FIRST_NAME_MAX_LENGTH_MSG = "firstname.maxlength.errmsg";
	String FIRST_NAME_MATCHER_MSG = "firstname.contains.errmsg";
	
	//lastname
	int LAST_NAME_MIN_LENGTH = NAME_MIN_LENGTH;
    int LAST_NAME_MAX_LENGTH = NAME_MAX_LENGTH;
	String LAST_NAME_REQUIRED = "lastname.required.errmsg";
	String LAST_NAME_MAX_LENGTH_MSG = "lastname.maxlength.errmsg";
	String LAST_NAME_MATCHER_MSG = "lastname.contains.errmsg";
	
	//address one
	int ADDRESS_ONE_MIN_LENGTH = 1;
	int ADDRESS_ONE_MAX_LENGTH = 100;
	String ADDR_ONE_REQUIRED = "addressone.required.errmsg";
	String ADDR_ONE_MIN_LENGTH_MSG = "addressone.min.length.errmsg";
	String ADDR_ONE_MAX_LENGTH_MSG = "addressone.max.length.errmsg";
	String ADDR_ONE_EXPRESSION_MSG = "addressone.contains.errmsg";
	
	//address two
	int ADDRESS_TWO_MIN_LENGTH = 1;
	int ADDRESS_TWO_MAX_LENGTH = 100;
	String ADDR_TWO_MAX_LENGTH_MSG = "addresstwoe.max.length.errmsg";
	String ADDR_TWO_EXPRESSION_MSG = "addresstwo.contains.errmsg";
	
	//phone code
	int PHONE_CODE_MIN_LENGTH = 1;
	int PHONE_CODE_MAX_LENGTH = 5;
	String PHONE_CODE_REQUIRED = "phonecode.required.errmsg";
	String PHONE_CODE_LENGTH_MAX_MSG = "phonecode.max.length.errmsg";
	String PHONE_CODE_PATTERN_MATCHER = "phonecode.contains.errmsg";
	String PHONECODE_CONTAINS_VALID_ERRMSG = "phonecode.contains.valid.errmsg";
		
	//phonenumber
	int PHONE_NUMBER_MIN_LENGTH = 6;
	int PHONE_NUMBER_MAX_LENGTH = 18;
	String PHONE_NUMBER_REQUIRED = "phonenumber.required.errmsg";
	String PHONE_NUMBER_MIN_LENGTH_MSG = "phonenumber.min.length.errmsg";
	String PHONE_NUMBER_MAX_LENGTH_MSG = "phonenumber.max.length.errmsg";
	String PHONE_NUMBER_MATCHER_MSG = "phonenumber.contains.errmsg";
	String PHONENUMBER_CONTAINS_VALID_ERRMSG = "phonenumber.contains.valid.errmsg";
	
	//city
	int CITY_MIN_LENGTH = 2;
	int CITY_MAX_LENGTH = 25;
	String CITY_REQUIRED = "city.required.errmsg";
	String CITY_MIN_LENGTH_MSG = "city.minlength.errmsg";
	String CITY_MAX_LENGTH_MSG = "city.maxlength.errmsg";
	String CITY_MATCHER_MSG = "city.errmsg.contains";
	
	//zip/postalcode
	int POSTAL_CODE_MIN_LENGTH = 2;
	int POSTAL_CODE_MAX_LENGTH = 10;
	String POSTAL_CODE_EXP = "[a-zA-Z0-9-]*";
	String POSTAL_CODE_MIN_LENGTH_MSG = "postalcode.min.length.errmsg";
	String POSTAL_CODE_MAX_LENGTH_MSG = "postalcode.max.length.errmsg";
	String POSTAL_CODE_MATCHER_MSG = "postalcode.contains.errmsg";
	String POSTAL_CODE_EXP_US = "^\\d{5}$|^\\d{5}-\\d{4}$";
	String POSTAL_CODE_MATCHER_MSG_US = "postalcode.contains.us.errmsg";
	String POSTAL_CODE_EXP_JP = "^\\d{7}$|^\\d{3}-\\d{4}$";
	String POSTAL_CODE_MATCHER_MSG_JP = "postalcode.contains.jp.errmsg";
		
   //password		
	int PASSWORD_MIN_LENGTH = 6;
	int PASSWORD_MAX_LENGTH = 20;
	String PASSWORD_EXP = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{6,20})";
	String PASSWORD_REQUIRED = "password.required.errmsg";
	String PASSWORD_MIN_LENGTH_MSG = "password.min.length.errmsg";
	String PASSWORD_MAX_LENGTH_MSG = "password.max.length.errmsg";
	String PASSWORD_MATCHER_MSG = "password.contains.errmsg";
	String CONF_PASSWORD_MISMATCH = "confpassword.mismatch.errmsg";
	String EMAIL_AS_PASSWORD = "email.as.password.errmsg";
	String NEW_AS_OLD_PASSWORD = "change.error.oldnewpasswordsame";
	
	String PASSWORD_REGEXP_NUMBER =  "((?=.*\\d)([a-z]*)([A-Z]*)([!@#$%^&*]*).{6,20})";
	String PASSWORD_REGEXP_LOWER_CASE =   "((\\d*)(?=.*[a-z])([A-Z]*)([!@#$%^&*]*).{6,20})";
	String PASSWORD_REGEXP_UPPER_CASE =   "((\\d*)([a-z]*)(?=.*[A-Z])([!@#$%^&*]*).{6,20})";
	String PASSWORD_REGEXP_SPECIAL_CAHR = "((\\d*)([a-z]*)([A-Z]*)(?=.*[!@#$%^&*]).{6,20})";
	String RE_DATE_EXP = "/^((0?[1-9]|1[012])[- /.](0?[1-9]|[12][0-9]|3[01])[- /.](19|20)?[0-9]{2})*$/";
	
	//Old password
	String OLD_PASSWORD_REQUIRED = "oldpassword.required.errmsg";
	String PASSWORD_LENGTH = "password.lengthrange.errmsg";
	
	//answer field
	int ANSWER_MIN_LENGTH = 2;
	int ANSWER_MAX_LENGTH = 20;
	String ANSWER_REQUIRED = "answer.required.errmsg";
	String ANSWER_MIN_LENGTH_MSG = "answer.min.length.errmsg";
	String ANSWER_MAX_LENGTH_MSG = "answer.max.length.errmsg";
		
	//email
	int EMAIL_MAX_LENGTH = 50;
	String EMAIL_REQUIRED = "email.required.errmsg";
	String EMAIL_MAX_LENGTH_MSG = "email.max.length.errmsg";
	String EMAIL_MATCHER_MSG = "email.contains.errmsg";
	String CONF_EMAIL_MISMATCH = "email.mismatch.errmsg";
	String EMAIL_ID_DELETED_MSG = "email.deleted.errmsg";
	String ADMIN_USER_EMAIL_ID_DELETED_MSG = "admin.user.email.deleted.errmsg";
	
	String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	String EMAIL_PATTERN_JAVA_SCRIPT = "/" + EMAIL_PATTERN + "/";
	
	//DateofBirth
	//18 years
	Long AGE_MIN = 6575L; 
	//120 years
	Long AGE_MAX = 43830L; 
	String DATE_REQUIRED_MIN_AGE = "dateofbirth.min.age.errmsg";
	String DATE_REQUIRED_MAX_AGE = "dateofbirth.max.age.errmsg";
	String DATE_PATTERN_MATCHER  = "dateofbirth.contains.errmsg";
	
	//website
	String WEBSITE_EXP = "^((https?|ftp)://|(www|WWW|ftp)\\.)[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+([/?].*)?$";
	String URL_EXP = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	int WEBSITE_MIN_LENGTH = 1;
	int WEBSITE_MAX_LENGTH = 40;
	String WEBSITE_MIN_LENGTH_MSG = "website.min.length.errmsg";
	String WEBSITE_MAX_LENGTH_MSG = "website.max.length.errmsg";
	String WEBSITE_EXPRESSION_MSG = "website.contains.errmsg";
	
	//terms
	String TERMS_REQUIRED = "terms.condition.required.errmsg";
	
	//legal name
	String LEGAL_NAME_AND_NAME_PATTERN_MATCHER = "first.last.names.same.errmsg";
		
	//Minimum and Maximum
	String MINIMUM_AMOUNT_REQUIRED = "minimumamount.required.errmsg";
	String MAXIMUM_AMOUNT_REQUIRED = "maximumamount.required.errmsg";
	
	String MINIMUM_AMOUNT_RECFRACTION = "minimumaount.fraction.errmsg";
	String MAXIMUM_AMOUNT_RECFRACTION = "maximumamount.fraction.errmsg";
	String MIN_MAX_COMPARE = "minmaxcompare.errmsg";	
	
	//common message
 	Integer MESSAGE_MIN_LENGTH = 3;
    Integer MESSAGE_MAX_LENGTH = 1000;
	String MESSAGE_MATCHER = "message.contains.errmsg";
	String MESSAGE_MIN_LENGTH_RANGE = "message.min.length.errmsg";
	String MESSAGE_MAX_LENGTH_RANGE = "message.max.length.errmsg";

	Double SEND_MONEY_MIN_LIMIT = 0.1;
	Double SEND_MONEY_MAX_LIMIT = 1000000.0;
	String AMOUNT_VAR = "requestedAmount";
	String SEND_MONEY_MAX_ERR_MSG = "amount.format.max.errmsg";
	String AMOUNT_ERROR_FORMAT = "amount.format.errmsg";
	String AMOUNT_REQUIRED = "amount.required.errmsg";
	String AMOUNT_ERROR_ZERO = "valid.amount.errmsg";
	
	Integer FINANCIAL_ADD_FEE_VALIDATOR = 1;
	Integer NON_FINANCIAL_ADD_FEE_VALIDATOR = 2;
	Integer NON_FINANCIAL_VARY_ADD_FEE_VALIDATOR = 3;
	Integer FINANCIAL_EDIT_FEE_VALIDATOR = 4;
	Integer NON_FINANCIAL_EDIT_FEE_VALIDATOR = 5;
	Integer NON_FINANCIAL_VARY_EDIT_FEE_VALIDATOR = 6;
	
	String EMAIL_DUPLICATE = "email.already.registred.errmsg";
	
	String UNABLE_TO_PROCESS_MSG = "unable.to.process.errmsg";

	String RESET_PASSWORD_SUCCESS = "reset.password.successmsg";

	String TITLE_REQUIRED = "title.required.errmsg";
	
	String QUE_ONE_REQUIRED = "question.required.errmsg";
	
	String REGISTATION_FAILED = "registration.failed.errmsg";
	
	String REGISTRTAION_FAIL_DB_ERR = "registration.failure.db.errmsg";

	String DUPLICATE_PHONE_NO_ERR = "duplicate.phoneno.errmsg";
	
	String REGISTRATION_SESSION_EXPIRED = "reg.session.expired.errmsg";
	
	String USER_TYPE_REQUIRED = "user.type.required.errmsg";
	
	String MESSAGE_REQUIRED = "message.required.errmsg";
	
	String UPDATE_REASON_REQUIRED = "update.reason.required.errmsg";
	
	String UPDATE_REASON_MATCHER = "update.reason.contains.errmsg";
	
	String UPDATE_REASON_MIN_LENGTH_RANGE = "update.reason.min.length.errmsg";
	
	String UPDATE_REASON_MAX_LENGTH_RANGE = "update.reason.max.length.errmsg";
	
	String DUPLICATE_MAIL_REG_FAIL = "duplicate.mail.registration.fail.errmsg";

	String EMAIL_FAILED_NOTE = "mail.send.failed.note";
	
	String DATE_REQUIRED = "dateofbirth.required.errmsg";

	String DESTINATION_TYPE_REQUIRED = "destination.type.required.errmsg";
	
	String FROMDATE_REQUIRED = "fromdate.required.errmsg";

	String TODATE_REQUIRED = "todate.required.errmsg";

	String PERCENTAGE_REQUIRED = "percentage.required.errmsg";
	
	String UPDATE_REASON_VAR = "updateReason";
	
	String DUPLICATE_CARD_ACCOUNT = "duplicate.card.account.errmsg";
	
	String DUPLICATE_BANK_ACCOUNT = "duplicate.bank.account.errmsg";
	
	String CARD_REGISTER_SUCCESS_MSG = "card.register.success.msg";
	
	String EXISTING_ACCOUNT_ADDED_AGAIN = "existing.account.added.again";
	
	String CARD_UPDATED_SUCCESS_MSG = "card.updated.success.msg";
	
	String AUTHORIZATION_FAILURE_ERROR_MSG = "authorization.failure.error.msg";

	String RECURRING_DELETED_SUCCESSFULLY = "recurring.deleted.successfully";
	
	String RECURRING_DELETED_FAILURE = "recurring.deleted.failure";
	
	String RECURRING_UPDATED_SUCCESSFULLY = "recurring.updated.successfully";
	
	String RECURRING_UPDATED_FAILURE = "recurring.updated.failure";
	
	String EMAIL_ALREADY_REGISTRED_DURATION_ERRMSG = "email.already.registred.duration.errmsg";
	
	String NO_RECORD_IN_PDF_ERRMSG = "no.record.in.pdf.errmsg";
	
	String  UNABLE_TO_DOWNLOAD_PDF_UNKNOWN_ERRMSG = "unable.to.download.pdf.unknown.errmsg";
	
	String ERROR_MSG_RECORD_EXIST_UPDATE_AND_COMPLETE_REGISTRATION = "error.msg.record.exist.update.and.complete.registration";

    String NULL_STRING = "null";
    
    
    
  //mpin
    Integer M_PIN_MAX_LENGHT = 4;
  	String MPIN_REQUIRED = "mpin.required.errmsg";
  	String MPIN_MIN_LENGTH_MSG = "mpin.min.length.errmsg";
  	String MPIN_LENGTH_MSG = "mpin.length.errmsg";
  	String MPIN_NOT_A_NUMBER_MSG = "mpin.not.a.number.errmsg";
}
