package com.tarang.ewallet.email.service.impl;

import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.email.service.EmailService;
import com.tarang.ewallet.email.service.repository.EmailRepository;
import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.EmailHistory;


/**
 * @author  : prasadj
 * @date    : Oct 12, 2012
 * @time    : 11:09:50 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class EmailServiceImpl implements EmailService  {
	

	private CryptService cryptService;
	
	private JavaMailSender mailSenders;
	
	private SimpleMailMessage templateMessage;
	
	private EmailRepository emailRepository;
	
	private static final Logger LOGGER = Logger.getLogger(EmailServiceImpl.class);	
	
	public EmailServiceImpl(JavaMailSender mailSenders, SimpleMailMessage templateMessage,
			EmailRepository emailRepository, CryptService cryptService){
		this.mailSenders = mailSenders;
		this.templateMessage = templateMessage;
		this.emailRepository = emailRepository;
		this.cryptService = cryptService;
	}

	@Override
	public void sendMessage(String emailTo, String msg) throws WalletException {
		sendMessage(emailTo, msg, null);
	}

	@Override
	public void sendMessage(String emailTo, String msg, String subject) throws WalletException {
		sendMessage(emailTo, msg, subject, null);
	}

	@Override
	public void sendMessage(final String emailTo, final String msg, final String subject, final String files[]) 
			throws WalletException{
		EmailHistory emailHistory = null;
		Boolean mailStatus = null;
		try{
			emailHistory = createEmailHistory(emailTo, msg, subject);
			mailStatus = getSendMessageStatus(emailTo, msg, subject, files);
			if(!mailStatus){
				emailHistory.setEmailStatus(EmailServiceConstants.EMAIL_SENT_FAILED);
				emailRepository.updateEmailHistory(emailHistory);
			}
		}
		catch(Exception e){
			LOGGER.error(EmailServiceConstants.EMAIL_SEND_FAILED, e);
		}
	}
	
	@Override
	public void startSendEmail() throws WalletException {
		List<EmailHistory> emailHistoryList = emailRepository.getFailedEmails();
		if(emailHistoryList != null){
			for(EmailHistory emailHistory: emailHistoryList){
				Boolean mailsStatus = getSendMessageStatus(emailHistory.getEmailTo(), 
						cryptService.decryptData(emailHistory.getMessage()), 
						cryptService.decryptData(emailHistory.getSubject()), null);
				if(mailsStatus){
					emailHistory.setEmailSentDate(new Date());
					emailHistory.setEmailStatus(EmailServiceConstants.EMAIL_SENT_SUCCESS);
					emailRepository.updateEmailHistory(emailHistory);
				}
				else{
					emailHistory.incrementOccurance();
					emailRepository.updateEmailHistory(emailHistory);
				}
			}
		}	
	}
	
	/**
	 * @author yasina
	 * @param emailTo
	 * @param msg
	 * @param subject
	 * @param files
	 * @return
	 */
	private Boolean getSendMessageStatus(final String emailTo, final String msg, final String subject, final String files[]){
		Boolean mailStatus = Boolean.FALSE;
		MimeMessage message = mailSenders.createMimeMessage();
		try {
		    MimeMessageHelper helper = new MimeMessageHelper(message, true);
		    helper.setFrom(templateMessage.getFrom());
		    helper.setTo(emailTo);
		    helper.setSubject(subject);
		    helper.setText(msg, true);
			mailSenders.send(message);
			mailStatus = Boolean.TRUE;
		} 
		catch (Exception e) {
			LOGGER.error(EmailServiceConstants.EMAIL_SEND_FAILED, e);
			mailStatus = Boolean.FALSE;
		}
		return mailStatus;
	}
	
	/**
	 * @author yasina
	 * @param emailTo
	 * @param msg
	 * @param subject
	 * @return
	 * @throws WalletException
	 */
	private EmailHistory createEmailHistory(String emailTo, String msg, String subject) throws WalletException{
		EmailHistory emailHistory = new EmailHistory();
		emailHistory.setEmailTo(emailTo);
		emailHistory.setMessage(cryptService.encryptData(msg));
		emailHistory.setSubject(cryptService.encryptData(subject));
		emailHistory.setCreationDate(new Date());
		emailHistory.setEmailStatus(EmailServiceConstants.EMAIL_SENT_SUCCESS);
		emailHistory = emailRepository.createEmailHistory(emailHistory);
		return emailHistory;
	}
}
