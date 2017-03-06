package com.tarang.ewallet.email.service.repository.impl;

import java.util.List;

import com.tarang.ewallet.email.service.dao.EmailDao;
import com.tarang.ewallet.email.service.repository.EmailRepository;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.EmailHistory;


public class EmailRepositoryImpl implements EmailRepository {

	private EmailDao emailDao;

	public EmailRepositoryImpl(EmailDao emailDao) {
		this.emailDao = emailDao;
	}

	@Override
	public EmailHistory createEmailHistory(EmailHistory emailHistory) throws WalletException {
		return emailDao.createEmailHistory(emailHistory);
	}

	@Override
	public void updateEmailHistory(EmailHistory emailHistory)
			throws WalletException {
		emailDao.updateEmailHistory(emailHistory);
	}

	@Override
	public List<EmailHistory> getFailedEmails() throws WalletException {
		return emailDao.getFailedEmails();
	}
	
}
