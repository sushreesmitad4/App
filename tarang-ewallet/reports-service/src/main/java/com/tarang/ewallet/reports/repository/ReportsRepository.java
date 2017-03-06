/**
 * 
 */
package com.tarang.ewallet.reports.repository;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.CustomerReportModel;


/**
 * @author  : prasadj
 * @date    : Jan 17, 2013
 * @time    : 3:51:39 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface ReportsRepository {

	List<CustomerReportModel> customerLastNTransactions(Integer limit, Long languageId, Long authenticationId, String cr, String dr);
	
	List<CustomerReportModel> customerSendMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payerId);
	
	List<CustomerReportModel> customerReceiveMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId);
	
	List<CustomerReportModel> customerMerchantWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long customerId, Long merchantId);
	
	List<CustomerReportModel> merchantLastNTransactions(Integer limit, Long languageId, Long authenticationId, String cr, String dr);
	
	List<CustomerReportModel> merchantSendMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payerId);
	
	List<CustomerReportModel> merchantReceiveMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId);
	
	List<CustomerReportModel> merchantPersonWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long merchantId, Long customerId);
	
	List<CustomerReportModel> userLastNTransactions(Integer limit, Long languageId);
	
	List<CustomerReportModel> userCustomerWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long customerId);
	
	List<CustomerReportModel> userMerchantWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long merchantId);
	
	List<CustomerReportModel> userCommissions(Integer limit, Long languageId, Date fromDate, Date toDate);
	
	List<CustomerReportModel> dormantAccounts(Integer limit, Date fromDate, Date toDate, Integer interval );
	
	List<CustomerReportModel> userCustomerBalances(Long languageId);
	
	List<CustomerReportModel> userMerchantBalances(Long languageId);
	
	List<CustomerReportModel> userCustomerHavingMoneyExceedsThresholdLimit(Long languageId, Long countryId);
	
	List<CustomerReportModel> userCustomerRequestFails(Integer limit, Long languageId, Date fromDate, Date toDate);
	
	List<CustomerReportModel> customerRequestFails(Integer limit, Long languageId, Date fromDate, Date toDate, Long customerId);
	
	List<CustomerReportModel> userCustomerFailedTransactions(Integer limit, Date fromDate, Date toDate);
	
	List<CustomerReportModel> merchantCustomerFailedTransactions(Integer limit, Date fromDate, Date toDate, Long merchantId);
	
	List<CustomerReportModel> customerFailedTransactions(Integer limit, Date fromDate, Date toDate, Long customerId);
	
	List<CustomerReportModel> merchantCommissions(Integer limit, Long languageId, Long merchantId, Date fromDate, Date toDate);
	
	List<CustomerReportModel> userCustomerDisputes(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType);
	
	List<CustomerReportModel> userMerchantDisputes(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType);
	
	List<CustomerReportModel> userAdminDisputes(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType);
	
	List<CustomerReportModel> listOfUnusedAccounts(Integer limit, Date date);
	
	List<CustomerReportModel> listOfMerchantsThresholds(Integer limit, Long languageId);
	
}
