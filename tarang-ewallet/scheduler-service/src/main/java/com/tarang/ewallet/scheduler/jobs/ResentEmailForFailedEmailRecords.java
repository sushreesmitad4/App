/**
 * 
 */
package com.tarang.ewallet.scheduler.jobs;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tarang.ewallet.email.service.EmailService;

/**
 * @author  : kedarnathd
 * @date    : Aug 1, 2013
 * @time    : 6:19:33 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class ResentEmailForFailedEmailRecords extends WalletJob {

	private static final Logger LOGGER = Logger.getLogger(ResentEmailForFailedEmailRecords.class);
	
	private EmailService emailService;

	protected void initialize(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:/wallet-applicationContext.xml", 
				"classpath*:/scheduler-applicationContext.xml"});
		emailService = (EmailService)context.getBean("emailService");
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		executeMethod();
	}
	
	public void executeMethod(){
		try {
			LOGGER.info("sendMail job started at: " + new Date() );
			initialize();
			emailService.startSendEmail();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}