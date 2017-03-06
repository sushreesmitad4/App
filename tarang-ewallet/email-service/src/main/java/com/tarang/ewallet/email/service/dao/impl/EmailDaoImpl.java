package com.tarang.ewallet.email.service.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.email.service.dao.EmailDao;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.EmailHistory;



public class EmailDaoImpl implements EmailDao {

	private HibernateOperations hibernateOperations;

	public EmailDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public EmailHistory createEmailHistory(EmailHistory emailHistory)
			throws WalletException {
		try {
			hibernateOperations.save(emailHistory);
			hibernateOperations.flush();
		} catch (Exception e) {
			throw new WalletException(e.getMessage(), e);
		}
		return emailHistory;
	}

	@Override
	public void updateEmailHistory(EmailHistory emailHistory)
			throws WalletException {
		try {
			hibernateOperations.update(emailHistory);
			hibernateOperations.flush();
		} catch (Exception e) {
			throw new WalletException(e.getMessage(), e);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailHistory> getFailedEmails() throws WalletException {
		List<EmailHistory> list = (List<EmailHistory>) hibernateOperations.findByNamedQuery("getFailedEmailHistory");
		return list;
	}

}
