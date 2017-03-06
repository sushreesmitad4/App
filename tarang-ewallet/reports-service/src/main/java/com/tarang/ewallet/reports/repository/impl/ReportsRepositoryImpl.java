/**
 * 
 */
package com.tarang.ewallet.reports.repository.impl;

import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.reports.dao.ReportsDao;
import com.tarang.ewallet.reports.repository.ReportsRepository;


/**
 * @author  : prasadj
 * @date    : Jan 17, 2013
 * @time    : 3:53:13 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class ReportsRepositoryImpl implements ReportsRepository {

	private ReportsDao reportsDao;
	
	public ReportsRepositoryImpl(ReportsDao reportsDao){
		this.reportsDao = reportsDao;
	}

	@Override
	public List<CustomerReportModel> customerLastNTransactions(Integer limit, Long langid, Long authenticationId, String cr, String dr) {
		return reportsDao.customerLastNTransactions(limit, langid, authenticationId, cr, dr);
	}

	@Override
	public List<CustomerReportModel> customerSendMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payerId) {
		return reportsDao.customerSendMoneyTransactions(limit, languageId, fromDate, toDate, payerId);
	}

	@Override
	public List<CustomerReportModel> customerReceiveMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId) {
		return reportsDao.customerReceiveMoneyTransactions(limit, languageId, fromDate, toDate, payeeId);
	}

	@Override
	public List<CustomerReportModel> customerMerchantWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, 
			Long customerId, Long merchantId) {
		return reportsDao.customerMerchantWiseTransactions(limit, languageId, fromDate, toDate, customerId, merchantId);
	}

	@Override
	public List<CustomerReportModel> merchantLastNTransactions(Integer limit, Long languageId, Long authenticationId, String cr, String dr) {
		return reportsDao.merchantLastNTransactions(limit, languageId, authenticationId, cr, dr);
	}
	
	@Override
	public List<CustomerReportModel> merchantSendMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payerId) {
		return reportsDao.merchantSendMoneyTransactions(limit, languageId, fromDate, toDate, payerId);
	}

	@Override
	public List<CustomerReportModel> merchantReceiveMoneyTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId) {
		return reportsDao.merchantReceiveMoneyTransactions(limit, languageId, fromDate, toDate, payeeId);
	}

	@Override
	public List<CustomerReportModel> merchantPersonWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, 
			Long merchantId, Long customerId) {
		return reportsDao.merchantPersonWiseTransactions(limit, languageId, fromDate, toDate, merchantId, customerId);
	}

	@Override
	public List<CustomerReportModel> userLastNTransactions(Integer limit, Long languageId) {
		return reportsDao.userLastNTransactions(limit, languageId);
	}

	@Override
	public List<CustomerReportModel> userCustomerWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long customerId) {
		return reportsDao.userCustomerWiseTransactions(limit, languageId, fromDate, toDate, customerId);
	}

	@Override
	public List<CustomerReportModel> userMerchantWiseTransactions(Integer limit, Long languageId, Date fromDate, Date toDate, Long merchantId) {
		return reportsDao.userMerchantWiseTransactions(limit, languageId, fromDate, toDate, merchantId);
	}
	
	@Override
	public List<CustomerReportModel> userCommissions(Integer limit, Long languageId, Date fromDate, Date toDate) {
		return reportsDao.userCommissions(limit, languageId, fromDate, toDate);
	}

	@Override
	public List<CustomerReportModel> dormantAccounts(Integer limit, Date fromDate, Date toDate, Integer interval) {
		return reportsDao.dormantAccounts(limit, fromDate, toDate, interval);
	}

	@Override
	public List<CustomerReportModel> userCustomerBalances(Long languageId) {
		return reportsDao.userCustomerBalances(languageId);
	}

	@Override
	public List<CustomerReportModel> userMerchantBalances(Long languageId) {
		return reportsDao.userMerchantBalances(languageId);
	}

	@Override
	public List<CustomerReportModel> userCustomerHavingMoneyExceedsThresholdLimit(Long languageId, Long countryId) {
		return reportsDao.userCustomerHavingMoneyExceedsThresholdLimit(languageId, countryId);
	}

	@Override
	public List<CustomerReportModel> userCustomerRequestFails(Integer limit, Long languageId, Date fromDate, Date toDate) {
		return reportsDao.userCustomerRequestFails(limit, languageId, fromDate, toDate);
	}

	@Override
	public List<CustomerReportModel> customerRequestFails(Integer limit, Long languageId, Date fromDate, Date toDate, Long customerId) {
		return reportsDao.customerRequestFails(limit, languageId, fromDate, toDate, customerId);
	}

	@Override
	public List<CustomerReportModel> userCustomerFailedTransactions(Integer limit, Date fromDate, Date toDate) {
		return reportsDao.userCustomerFailedTransactions(limit, fromDate, toDate);
	}

	@Override
	public List<CustomerReportModel> merchantCustomerFailedTransactions(Integer limit, Date fromDate, Date toDate, Long merchantId) {
		return reportsDao.merchantCustomerFailedTransactions(limit, fromDate, toDate, merchantId);
	}

	@Override
	public List<CustomerReportModel> customerFailedTransactions(Integer limit, Date fromDate, Date toDate, Long customerId) {
		return reportsDao.customerFailedTransactions(limit, fromDate, toDate, customerId);
	}
	
	@Override
	public List<CustomerReportModel> merchantCommissions(Integer limit, Long languageId, Long merchantId, Date fromDate, Date toDate) {
		return reportsDao.merchantCommissions(limit, languageId, merchantId, fromDate, toDate);
	}

	@Override
	public List<CustomerReportModel> userCustomerDisputes(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType) {
		return reportsDao.userCustomerDisputes(limit, languageId, fromDate, toDate, payeeId, payerId, disputeType);
	}

	@Override
	public List<CustomerReportModel> userMerchantDisputes(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType) {
		return reportsDao.userMerchantDisputes(limit, languageId, fromDate, toDate, payeeId, payerId, disputeType);
	}

	@Override
	public List<CustomerReportModel> userAdminDisputes(Integer limit, Long languageId, Date fromDate, Date toDate, Long payeeId, Long payerId, Long disputeType) {
		return reportsDao.userAdminDisputes(limit, languageId, fromDate, toDate, payeeId, payerId, disputeType);
	}

	@Override
	public List<CustomerReportModel> listOfUnusedAccounts(Integer limit, Date date) {
		return reportsDao.listOfUnusedAccounts(limit, date);
	}

	@Override
	public List<CustomerReportModel> listOfMerchantsThresholds(Integer limit,
			Long languageId) {
		return reportsDao.listOfMerchantsThresholds(limit, languageId);
	}

}
