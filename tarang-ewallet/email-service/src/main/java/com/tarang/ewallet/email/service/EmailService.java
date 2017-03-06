/**
 * 
 */
package com.tarang.ewallet.email.service;

import com.tarang.ewallet.exception.WalletException;


/**
 * @author  : prasadj
 * @date    : Oct 12, 2012
 * @time    : 8:03:57 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface EmailService {

	void sendMessage(final String emailTo, final String msg) throws WalletException;

	void sendMessage(final String emailTo, final String msg, final String subject) throws WalletException;
	
	void sendMessage(final String emailTo, final String msg, final String subject, final String files[]) throws WalletException;
	
	void startSendEmail()throws WalletException;
	
}
