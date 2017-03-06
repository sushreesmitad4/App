/**
 * 
 */
package com.tarang.ewallet.walletui.controller.constants;

/**
 * @author  : prasadj
 * @date    : Jan 17, 2013
 * @time    : 11:42:41 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface Reports {

	String CUSTOMER_REPORTS = "customer.reports";
	
	String MERCHANT_REPORTS = "merchant.reports";
	
	String ADMIN_REPORTS = "user.reports";
	
	Integer DEFAUALT_NO_OF_TXN = 10;
	
	Long LAST_N_TRANSACTIONS = 1L;
	
	Long MERCHANT_WISE_TRANSACTION = 2L;
	
	Long CUSTOMER_WISE_TRANSACTION = 3L;
	
	String URL_REPORTS_LIST_ATTR = "urlReportsList";
	
	String REPORTTYPE = "reporttype";
	
	String FDATE = "fdate";
	
	String TDATE = "tdate";

	String REPORTID = "reportid";
	
	String DISPUTETYPE = "disputetype";
	
	String CEMAIL = "cemail";

	String MEMAIL = "memail";

	String REPORTS_FORM = "reportsForm";
	
	String RESPONSE_HEADER_NAME_XL = "Content-Disposition";
	
	String RESPONSE_HEADER_VALUE_XL = "Attachment;filename= ";
	
	String RESPONSE_CONTENTY_TYPE_XL = "application/vnd.ms-excel";
	
	String INVALID_EMAIL_ERR_MSG = "invalid.email";
	
	String ADMIN_CUST_MER_SESSION_EXPIRED = "admin.cust.mer.session.expired";
	
	String REP_SESSION_URL = "repSessionUrl";
	
	String REPORT_EXCEPTION_EMPTY = "report.exception.empty";

}