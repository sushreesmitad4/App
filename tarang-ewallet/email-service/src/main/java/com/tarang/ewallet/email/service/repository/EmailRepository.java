package com.tarang.ewallet.email.service.repository;

import java.util.List;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.EmailHistory;



public interface EmailRepository {

	EmailHistory createEmailHistory(EmailHistory emailHistory)throws WalletException;
	
	void updateEmailHistory(EmailHistory emailHistory)throws WalletException;

	List<EmailHistory> getFailedEmails()throws WalletException;
}
