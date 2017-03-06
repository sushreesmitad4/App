/**
 * 
 */
package com.tarang.ewallet.util;

/**
 * @author  : prasadj
 * @date    : Dec 3, 2012
 * @time    : 5:45:07 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface GlobalLitterals {
	
	long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

	String DOT_STRING = ".";
	
	String SLASH_STRING = "/";
	
	String REVERSE_SLASH_STRING = "\\";

	String EMPTY_STRING = "";
	
	String SPACE_STRING = " ";
	
	String FILE_LINE_SPLIT = ",";
	
	String EQUAL_STRING = "=";
	
	String DB_DUPLICATE_ENTRY = "duplicate.entry";
	
	String CUSTOMER_USER_TYPE = "C";
	
	String MERCHANT_USER_TYPE = "M";
	
	String ADMIN_USER_TYPE = "A";
	
	Long CUSTOMER_USER_TYPE_ID = 1L;
	
	Long MERCHANT_USER_TYPE_ID = 2L;
	
	Long ADMIN_USER_TYPE_ID = 3L;
	
	Long CUSTOMER_NON_REGISTER_TYPE_ID = 4L;

	String ADMIN_MODULE = "Admin";
	
	String CUSTOMER_MODULE = "Customer";
	
	String MERCHANT_MODULE = "Merchant";

	/**
	 * for unique combination of operation type and Country and currency
	 */
	String DB_COM_DUPLICATE_ENTRY = "Unique conistrains exception while creating feemgmt";
	
	/**
	 * for date converting to string format
	 */
	String DATE_FORMAT = "MM/dd/yyyy";
	
	/**
	 * for date converting to string format
	 */
	String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	
	/**
	 * for date search in db
	 */
	String DB_DATE_FORMAT = "MM-DD-YYYY";
	
	/**
	 * for date used in datepicker
	 */
	String DATE_PICKER_DATE_FORMAT = "mm/dd/yy";
	
	/**
	 * for uniquer country in tax
	 */
	String DB_COUNTRY_DUPLICATE_ENTRY = "uniquer.country.voilation.exception";
	
	/**
	 * number of days per week
	 */
	int WEEK_DAYS = 7;
	
	int MONTH_DAYS = 30;
	/**
	 * number of milli seconds for second
	 */
	int MILLI = 1000;

	/**
	 * real number floating format
	 */
	String DOUBLE_FORMAT = "%.2f";

	Double PERCENTAGE_FACTOR = 100.0;
	
	Double ZERO_DOUBLE = 0.0;
	
	Long ZERO_LONG = 0L;
	
	Integer ZERO_INTEGER = 0;
	
	double MAX_PERCENTAGE = 100.0;
	
	String AT_THE_RATE = "@";
	
	String TRUE_STR = "1";
	
	String CUSTOMER = "customer";
	
	String MERCHANT = "merchant";
	
	String ADMIN = "admin";
	
	String ACTIVE = "Active";
	
	String IN_ACTIVE = "Inactive";
	
	Long ACTIVE_STATUS = 1L;
	
	Long IN_ACTIVE_STATUS = 2L;
	
	String NO_COMMENT = "No comment";
	
	String CHARGE_BACK_LABEL = "Chargeback";
	
	String REFUND_LABEL = "Refund";
	
	String DISPUTE_UPDATED_BY_SYSTEM = "system";
	
	String REQUEST_FOR_REGISTRATION_AFTER_24HOURS = "requestForRegistrationAfter24hours";
	
	Long USERTYPE_CUSTOMER = 1L; 
	
	Long USERTYPE_MERCHANT = 2L; 
	
	String OPERATION_LIST = "operationList";
}
