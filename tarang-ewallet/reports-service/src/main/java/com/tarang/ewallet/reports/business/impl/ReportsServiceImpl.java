/**
 * 
 */
package com.tarang.ewallet.reports.business.impl;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.reports.business.ReportsService;
import com.tarang.ewallet.reports.repository.ReportsRepository;


/**
 * @author  : prasadj
 * @date    : Jan 17, 2013
 * @time    : 3:52:31 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class ReportsServiceImpl implements ReportsService {

	private ReportsRepository reportsRepository;
	
	public ReportsServiceImpl(ReportsRepository reportsRepository){
		this.reportsRepository = reportsRepository;
	}

	@Override
	public List<CustomerReportModel> customerLastNTransactions(Integer limit, Long languageId, Long authenticationId, String cr, String dr) {
		return reportsRepository.customerLastNTransactions(limit, languageId, authenticationId, cr, dr);
	}

	@Override
	public List<CustomerReportModel> customerSendMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payerId) {
		return reportsRepository.customerSendMoneyTransactions(limit, languageId, fromDate, toDate, payerId);
	}

	@Override
	public List<CustomerReportModel> customerReceiveMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId) {
		return reportsRepository.customerReceiveMoneyTransactions(limit, languageId, fromDate, toDate, payeeId);
	}

	@Override
	public List<CustomerReportModel> customerMerchantWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, 
			Long customerId, Long merchantId) {
		return reportsRepository.customerMerchantWiseTransactions(limit, languageId, fromDate, toDate, customerId, merchantId);
	}

	@Override
	public List<CustomerReportModel> merchantLastNTransactions(Integer limit, Long languageId, Long authenticationId, String cr, String dr) {
		return reportsRepository.merchantLastNTransactions(limit, languageId, authenticationId, cr, dr);
	}

	@Override
	public List<CustomerReportModel> merchantSendMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payerId) {
		return reportsRepository.merchantSendMoneyTransactions(limit, languageId, fromDate, toDate, payerId);
	}

	@Override
	public List<CustomerReportModel> merchantReceiveMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId) {
		return reportsRepository.merchantReceiveMoneyTransactions(limit, languageId, fromDate, toDate, payeeId);
	}

	@Override
	public List<CustomerReportModel> merchantPersonWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, 
			Long merchantId, Long customerId) {
		return reportsRepository.merchantPersonWiseTransactions(limit, languageId, fromDate, toDate, merchantId, customerId);
	}

	@Override
	public List<CustomerReportModel> userLastNTransactions(Integer limit, Long languageId) {
		return reportsRepository.userLastNTransactions(limit, languageId);
	}

	@Override
	public List<CustomerReportModel> userCustomerWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long customerId) {
		return reportsRepository.userCustomerWiseTransactions(limit, languageId, fromDate, toDate, customerId);
	}

	@Override
	public List<CustomerReportModel> userMerchantWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long merchantId) {
		return reportsRepository.userMerchantWiseTransactions(limit, languageId, fromDate, toDate, merchantId);
	}

	@Override
	public List<CustomerReportModel> userCommissions(Integer limit, Long languageId, Date fromDate, Date toDate) {
		return reportsRepository.userCommissions(limit, languageId, fromDate, toDate);
	}

	@Override
	public List<CustomerReportModel> dormantAccounts(Integer limit, Date fromDate, Date toDate, Integer interval) {
		return reportsRepository.dormantAccounts(limit, fromDate, toDate, interval);
	}

	@Override
	public List<CustomerReportModel> userCustomerBalances(Long languageId) {
		return reportsRepository.userCustomerBalances(languageId);
	}

	@Override
	public List<CustomerReportModel> userMerchantBalances(Long languageId) {
		return reportsRepository.userMerchantBalances(languageId);
	}

	@Override
	public List<CustomerReportModel> userCustomerHavingMoneyExceedsThresholdLimit(Long languageId, Long countryId) {
		return reportsRepository.userCustomerHavingMoneyExceedsThresholdLimit(languageId, countryId);
	}

	@Override
	public List<CustomerReportModel> userCustomerRequestFails(Integer limit, Long languageId, Date fromDate, Date toDate) {
		return reportsRepository.userCustomerRequestFails(limit, languageId, fromDate, toDate);
	}

	@Override
	public List<CustomerReportModel> customerRequestFails(Integer limit, Long languageId, Date fromDate, Date toDate, Long customerId) {
		return reportsRepository.customerRequestFails(limit, languageId, fromDate, toDate, customerId);
	}

	@Override
	public List<CustomerReportModel> userCustomerFailedTransactions(Integer limit, Date fromDate, Date toDate) {
		return reportsRepository.userCustomerFailedTransactions(limit, fromDate, toDate);
	}

	@Override
	public List<CustomerReportModel> merchantCustomerFailedTransactions(Integer limit, Date fromDate, Date toDate, Long merchantId) {
		return reportsRepository.merchantCustomerFailedTransactions(limit, fromDate, toDate, merchantId);
	}

	@Override
	public List<CustomerReportModel> customerFailedTransactions(Integer limit, Date fromDate, Date toDate, Long customerId) {
		return reportsRepository.customerFailedTransactions(limit, fromDate, toDate, customerId);
	}
	
	@Override
	public List<CustomerReportModel> merchantCommissions(Integer limit, Long languageId, Long merchantId, Date fromDate, Date toDate) {
		return reportsRepository.merchantCommissions(limit, languageId, merchantId, fromDate, toDate);
	}

	@Override
	public List<CustomerReportModel> userCustomerDisputes(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType) {
		return reportsRepository.userCustomerDisputes(limit, languageId, fromDate, toDate, payeeId, payerId, disputeType);
	}

	@Override
	public List<CustomerReportModel> userMerchantDisputes(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType) {
		return reportsRepository.userMerchantDisputes(limit, languageId, fromDate, toDate, payeeId, payerId, disputeType);
	}

	@Override
	public List<CustomerReportModel> userAdminDisputes(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType) {
		return reportsRepository.userAdminDisputes(limit, languageId, fromDate, toDate, payeeId, payerId, disputeType);
	}

	@Override
	public List<CustomerReportModel> listOfUnusedAccounts(Integer limit, Date date) {
		return reportsRepository.listOfUnusedAccounts(limit, date);
	}

	@Override
	public List<CustomerReportModel> listOfMerchantsThresholds(Integer limit,
			Long languageId) {
		return reportsRepository.listOfMerchantsThresholds(limit, languageId);
	}

}
