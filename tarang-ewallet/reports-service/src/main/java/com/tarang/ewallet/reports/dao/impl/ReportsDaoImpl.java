/**
 * 
 */
package com.tarang.ewallet.reports.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.reports.dao.ReportsDao;
import com.tarang.ewallet.reports.util.ReportQueriesPostgressDatabase;
import com.tarang.ewallet.reports.util.ReportUtilPostgresDatabase;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.QueryConstants;


/**
 * @author  : prasadj
 * @date    : Jan 17, 2013
 * @time    : 3:53:42 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ReportsDaoImpl implements ReportsDao, ReportQueriesPostgressDatabase, QueryConstants {

	private static final Logger LOGGER = Logger.getLogger(ReportsDaoImpl.class);
	
	private HibernateOperations hibernateOperations;
	
	ReportsDaoImpl(HibernateOperations hibernateOperations){
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public List<CustomerReportModel> customerLastNTransactions(final Integer limit,  final Long languageId, final Long authenticationId,
			final String cr, final String dr) {
		
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(CUSTOMER_LAST_N_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_AUTHENTICATION_ID, authenticationId);
                q.setParameter(Q_CR, cr);
                q.setParameter(Q_DR, dr);
                return ReportUtilPostgresDatabase.getGridReportCustomerLastNTxn(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> customerSendMoneyTransactions(final Integer limit, final Long languageId, 
			final Date fromDate, final Date toDate, final Long payerId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(CUSTOMER_SEND_MONEY_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_PAYER_ID, payerId);
                return ReportUtilPostgresDatabase.getGridReportCustomerSendMoneyTxn(q.list());
			}
		});
		return list;
	}

	@Override          
	public List<CustomerReportModel> customerReceiveMoneyTransactions(final Integer limit, final Long languageId, 
			final Date fromDate, final Date toDate, final Long payeeId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(CUSTOMER_RECEIVE_MONEY_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_PAYEE_ID, payeeId);
                return ReportUtilPostgresDatabase.getGridReportCustomerReceiveMoneyTxn(q.list());
			}
		});
		return list;
	}
	
	@Override
	public List<CustomerReportModel> customerMerchantWiseTransactions(final Integer limit, final Long languageId, final Date fromDate, final Date toDate, 
			final Long customerId, final Long merchantId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(CUSTOMER_MERCHANT_WISE_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_CUSTOMER_ID, customerId);
                q.setParameter(Q_MERCHANT_ID, merchantId);
                return ReportUtilPostgresDatabase.getGridReportCustomerMerchantwiseTxn(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> merchantLastNTransactions(final Integer limit, final Long languageId, final Long authenticationId,
			final String cr, final String dr) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(MERCHANT_LAST_N_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_AUTHENTICATION_ID, authenticationId);
                q.setParameter(Q_CR, cr);
                q.setParameter(Q_DR, dr);
                return ReportUtilPostgresDatabase.getReportGridUserMerchantLastNTxn(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> merchantSendMoneyTransactions(final Integer limit, final Long languageId, 
			final Date fromDate, final Date toDate, final Long payerId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(MERCHANT_SEND_MONEY_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_PAYER_ID, payerId);
                return ReportUtilPostgresDatabase.getReportGridUserMerchantSendMoneyTxn(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> merchantReceiveMoneyTransactions(final Integer limit, final Long languageId, 
			final Date fromDate, final Date toDate, final Long payeeId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(MERCHANT_RECEIVE_MONEY_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_PAYEE_ID, payeeId);
                return ReportUtilPostgresDatabase.getReportGridUserMerchantReceiveMoneyTxn(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> merchantPersonWiseTransactions(final Integer limit, final Long languageId, final Date fromDate, 
			final Date toDate, final Long merchantId, final Long customerId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(MERCHANT_CUSTOMER_WISE_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_CUSTOMER_ID, customerId);
                q.setParameter(Q_MERCHANT_ID, merchantId);
                return ReportUtilPostgresDatabase.getReportGridUserMerchantCustomerWiseTxn(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userLastNTransactions(final Integer limit, final Long languageId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(USER_LAST_NTRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
				return ReportUtilPostgresDatabase.getReportGridUserLastNTransactions(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userCustomerWiseTransactions(final Integer limit, final Long languageId, 
			final Date fromDate, final Date toDate, final Long customerId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(USER_CUSTOMER_WISE_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_CUSTOMER_ID, customerId);
                return ReportUtilPostgresDatabase.getReportGridUserCustomerWiseTransactions(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userMerchantWiseTransactions(final Integer limit, final Long languageId, 
			final Date fromDate, final Date toDate, final Long merchantId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(USER_MERCHANT_WISE_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_MERCHANT_ID, merchantId);
				return ReportUtilPostgresDatabase.getReportGridUserMerchantWiseTransactions(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userCommissions(final Integer limit, final Long languageId, final Date fromDate, final Date toDate) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(USER_COMMISSIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                return ReportUtilPostgresDatabase.getReportGridUserCommissions(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> dormantAccounts(final Integer limit, final Date fromDate, final Date toDate, final Integer interval) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(DARMAT_ACCOUNTS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_FROM_DATE, DateConvertion.pastDate(fromDate, interval));
                q.setParameter(Q_TO_DATE, DateConvertion.pastDate(toDate, interval));
                return ReportUtilPostgresDatabase.getReportGridDarmatAccounts(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userCustomerBalances(final Long languageId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(USER_CUSTOMER_BALANCES);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                return ReportUtilPostgresDatabase.getReportGridUserCustomerBalances(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userMerchantBalances(final Long languageId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(USER_MERCHANT_BALANCES);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                return ReportUtilPostgresDatabase.getReportGridUserMerchantBalances(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userCustomerHavingMoneyExceedsThresholdLimit(final Long languageId, final Long countryId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(USER_CUSTOMER_HAVING_EXCEEDS_THRESHOLD);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_COUNTRY_ID, countryId);
                return ReportUtilPostgresDatabase.getReportGridUserCustomerHavingExceedsThreshold(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userCustomerRequestFails(final Integer limit, final Long languageId, final Date fromDate, 
			final Date toDate) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(USER_CUSTOMER_REQUEST_FAILS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                return ReportUtilPostgresDatabase.getReportGridUserCustomerRequestFails(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> customerRequestFails(final Integer limit, final Long languageId, final Date fromDate, 
			final Date toDate, final Long customerId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(CUSTOMER_REQUEST_FAILS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_CUSTOMER_ID, customerId);
                return ReportUtilPostgresDatabase.getGridReportCustomerRequestFailed(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userCustomerFailedTransactions(final Integer limit, final Date fromDate, 
			final Date toDate) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(USER_CUSTOMER_FAILED_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                return ReportUtilPostgresDatabase.getReportGridUserCustomerFailedTransactions(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> merchantCustomerFailedTransactions(final Integer limit, final Date fromDate, final Date toDate, 
			final Long merchantId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(MERCHANT_FAILED_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_MERCHANT_ID, merchantId);
                return ReportUtilPostgresDatabase.getReportGridUserMerchantFailedTxn(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> customerFailedTransactions(final Integer limit, final Date fromDate, 
			final Date toDate, final Long customerId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(CUSTOMER_FAILED_TRANSACTIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_CUSTOMER_ID, customerId);
                return ReportUtilPostgresDatabase.getGridReportCustomerFailedTxn(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> merchantCommissions(final Integer limit, final Long languageId, final Long merchantId, 
			final Date fromDate, final Date toDate) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(MERCHANT_COMMISSIONS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_MERCHANT_ID, merchantId);
                return ReportUtilPostgresDatabase.getReportGridUserMerchantCummissionSummary(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userCustomerDisputes(final Integer limit, final Long languageId, final Date fromDate, final Date toDate, final Long payeeId, final Long payerId, final Long disputeType) {
		try{
			List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws SQLException {
	                Query q = session.createSQLQuery(CUSTOMER_DISPUTES);
	                q.setParameter(Q_LIMIT, limit);
	                q.setParameter(Q_LANGUAGE_ID, languageId);
	                q.setParameter(Q_FROM_DATE, fromDate);
	                q.setParameter(Q_TO_DATE, toDate);
	                q.setParameter(Q_PAYER_ID, payerId);
	                q.setParameter(Q_PAYEE_ID, payeeId);
	                q.setParameter(Q_DISPUTE_TYPE, disputeType);
					q.setResultTransformer( Transformers.aliasToBean(CustomerReportModel.class));
	                return q.list();
				}
			});
			return list;
		} catch (Exception e) {
			LOGGER.error( e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<CustomerReportModel> userMerchantDisputes(final Integer limit, final Long languageId, final Date fromDate, final Date toDate, final Long payeeId, final Long payerId, final Long disputeType) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(MERCHANT_DISPUTES);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_PAYER_ID, payerId);
                q.setParameter(Q_PAYEE_ID, payeeId);
                q.setParameter(Q_DISPUTE_TYPE, disputeType);
                return ReportUtilPostgresDatabase.getReportGridUserMerchantDisputedTxn(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> userAdminDisputes(final Integer limit, final Long languageId, final Date fromDate, final Date toDate, final Long payeeId, final Long payerId, final Long disputeType) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(ADMIN_DISPUTES);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                q.setParameter(Q_FROM_DATE, fromDate);
                q.setParameter(Q_TO_DATE, toDate);
                q.setParameter(Q_PAYER_ID, payerId);
                q.setParameter(Q_PAYEE_ID, payeeId);
                q.setParameter(Q_DISPUTE_TYPE, disputeType);
                return ReportUtilPostgresDatabase.getReportGridAdminDisputes(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> listOfUnusedAccounts(final Integer limit, final Date date) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(LIST_OF_UNUSED_ACCOUNTS);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_FROM_DATE, date);
                return ReportUtilPostgresDatabase.getReportGridListOfUnusedAccounts(q.list());
			}
		});
		return list;
	}

	@Override
	public List<CustomerReportModel> listOfMerchantsThresholds(final Integer limit,
			final Long languageId) {
		List<CustomerReportModel> list = (List<CustomerReportModel>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
                Query q = session.createSQLQuery(LIST_OF_MERCHANT_THRESHOLD);
                q.setParameter(Q_LIMIT, limit);
                q.setParameter(Q_LANGUAGE_ID, languageId);
                return ReportUtilPostgresDatabase.getReportGridListOfMerchantThreshold(q.list());
			}
		});
		return list;
	}
}
