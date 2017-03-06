package com.tarang.ewallet.scheduler.jobs;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.transaction.business.ReloadMoneyService;


public class PgSettlementJob extends WalletJob {

	private static final Logger LOGGER = Logger.getLogger(PgSettlementJob.class);
	
	private ReloadMoneyService reloadMoneyService;

	protected void initialize(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:/wallet-applicationContext.xml", 
				"classpath*:/scheduler-applicationContext.xml"});
		reloadMoneyService = (ReloadMoneyService)context.getBean("reloadMoneyService");
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		executeMethod();
	}
	
	public void executeMethod(){
		try {
			LOGGER.info("PgSettlementJob started at: " + new Date() );
			initialize();
			reloadMoneyService.postNonSettlementTransactions();
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
