package com.tarang.ewallet.transaction.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.transaction.dao.FraudCheckDao;
import com.tarang.ewallet.transaction.util.FraudCheckQueries;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryConstants;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class FraudCheckDaoImpl implements FraudCheckDao, FraudCheckQueries, QueryConstants, GlobalLitterals {
	private HibernateOperations hibernateOperations;
	
	FraudCheckDaoImpl(HibernateOperations hibernateOperations){
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public Boolean ipMultipleAccountsVelocity(final String ipAddress, final Date fromDate, final Date toDate, final Integer noOfAccounts) throws WalletException{
		List<Integer> list = (List<Integer>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(IP_MULTIPLE_ACCOUNTS_VELOCITY);
				q.setParameter(Q_IP_ADDRESS, ipAddress);
				q.setParameter(Q_FROM_DATE, fromDate);   
				q.setParameter(Q_TO_DATE, toDate);   
				q.setParameter(Q_NO_OF_ACCOUNTS, noOfAccounts);   
				return q.list();
			}
		});
		return list.get(0).toString().equals(TRUE_STR) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public List<String> getIpAddressByAuthId(final Long authId) throws WalletException{
		List<String> list = (List<String>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(IS_LOGIN_FROM_DIFFERENT_IP_ONE);
				q.setParameter(Q_AUTH_ID, authId);   
				return q.list();
			}
		});
		return list;
	}
	
	@Override
	public Boolean isLoginFromDifferentIp(final Long ipNumber1, final Long ipNumber2) throws WalletException{
		
		List<Integer> list = (List<Integer>) hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)throws SQLException {
				Query q = session.createSQLQuery(IS_LOGIN_FROM_DIFFERENT_IP_TWO);
				q.setParameter(Q_IP_ADDRESS_1,ipNumber1);
				q.setParameter(Q_IP_ADDRESS_2,ipNumber2);
				return q.list();
			}
		});
		return list.get(0).toString().equals(TRUE_STR) ? Boolean.TRUE : Boolean.FALSE;
		
	}
	
	@Override
	public Boolean emailPatternCheck(final String emailFirstFiveChars, final String domain, final Date fromDate, final Date toDate,
			final Integer noOfAccounts) throws WalletException{
		
		List<Integer> list = (List<Integer>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(EMAIL_PATTERN_CHECK);
				q.setParameter(Q_EMAIL, emailFirstFiveChars);   
				q.setParameter(Q_DOMAIN, domain);   
				q.setParameter(Q_FROM_DATE, fromDate);   
				q.setParameter(Q_TO_DATE, toDate);    
				q.setParameter(Q_NO_OF_PATTERN, noOfAccounts);   
				return q.list();
			}
		});
		return list.get(0).toString().equals(TRUE_STR) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean fundingSourcePatternCheckCard(final String cardbin,
			final Date fromDateCard, final Date toDateCard, final Integer noofcards)throws WalletException{
		List<Integer> list = (List<Integer>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(FUNDING_SOURCE_PATTERN_CHECK_CARD);
				q.setParameter(Q_CARD_BIN, cardbin);   
				q.setParameter(Q_FROM_DATE, fromDateCard);   
				q.setParameter(Q_TO_DATE, toDateCard);   
				q.setParameter(Q_NO_OF_CARDS, noofcards);   
				return q.list();
			}
		});
		return list.get(0).toString().equals(TRUE_STR) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	@Override
	public Boolean fundingSourcePatternCheck(final String bankCode,
			final  Date fromDateBank, final Date toDateBank, final Integer noOfAccountsBank) throws WalletException{
		List<Integer> list = (List<Integer>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(FUNDING_SOURCE_PATTERN_CHECK);
				q.setParameter(Q_BANK_CODE, bankCode);   
				q.setParameter(Q_FROM_DATE, fromDateBank);   
				q.setParameter(Q_TO_DATE, toDateBank);   
				q.setParameter(Q_NO_OF_ACCOUNTS, noOfAccountsBank);   
				return q.list();
			}
		});
		return list.get(0).toString().equals(TRUE_STR) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean ipCardBinGeoCheck(final Long ipNumber, final String cardBin) throws WalletException{
		List<Integer> list = (List<Integer>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(IP_CARD_BIN_GEO_CHECK);
				q.setParameter(Q_IP_ADDRESS, ipNumber);   
				q.setParameter(Q_CARD_BIN, cardBin);   
				return q.list();
			}
		});
		return list.get(0).toString().equals(TRUE_STR) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean domainCheck(final String email, final String domain) throws WalletException{
		List<Integer> list = (List<Integer>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(DOMAIN_CHECK);
				q.setParameter(Q_EMAIL, email);   
				q.setParameter(Q_DOMAIN, domain);   
				return q.list();
			}
		});
		return list.get(0).toString().equals(TRUE_STR) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean merchantThresholdCheck(final Long merchantAuthId,
			final Integer noOfdispute, final Date fromDate, final Date toDate) throws WalletException{
		List<Integer> list = (List<Integer>)hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(MERCHANT_THRESHOLD_CHECK);
				q.setParameter(Q_MERCHANT_AUTH_ID, merchantAuthId);   
				q.setParameter(Q_NO_OF_DISPUTES, noOfdispute);   
				q.setParameter(Q_FROM_DATE, fromDate);   
				q.setParameter(Q_TO_DATE, toDate);   
				return q.list();
			}
		});
		return list.get(0).toString().equals(TRUE_STR) ? Boolean.TRUE : Boolean.FALSE;
	}
	
}