package com.tarang.ewallet.scheduler.jobs;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tarang.ewallet.accountcloser.business.AccountCloserService;
import com.tarang.ewallet.accountcloser.util.AccountCloserStatus;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;


public class AccountCloserJob extends WalletJob {

	private static final Logger LOGGER = Logger.getLogger(AccountCloserJob.class);
	
	private AccountCloserService accountCloserService;
	private UtilService utilService;

	protected void initialize(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:/wallet-applicationContext.xml", 
				"classpath*:/scheduler-applicationContext.xml"});
		accountCloserService = (AccountCloserService)context.getBean("accountCloserService");
		utilService = (UtilService)context.getBean("utilService");
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		executeMethod();
	}
	
	public void executeMethod(){
		try {
			initialize();
			LOGGER.info("AccountCloserJob started at: " + new Date() );
			Integer days = utilService.getDaysForPendingAccountClosers();
			String message = utilService.getMessageForPendingAccountClosers();
			Date beforeDate = DateConvertion.pastDate(new Date(), days);
			List<Long> list= accountCloserService.getApprovalAccountClosers(AccountCloserStatus.APPROVAL, beforeDate);
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						accountCloserService.addMessageByScheduler(list.get(i), message, GlobalLitterals.ADMIN_USER_TYPE_ID, AccountCloserStatus.CLOSED);
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
