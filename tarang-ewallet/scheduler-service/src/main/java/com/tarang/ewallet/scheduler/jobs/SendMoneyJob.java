package com.tarang.ewallet.scheduler.jobs;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tarang.ewallet.model.SendMoneyTxn;
import com.tarang.ewallet.scheduler.util.JobConstants;
import com.tarang.ewallet.transaction.business.SendMoneyService;


@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SendMoneyJob extends WalletJob {

	private static final Logger LOGGER = Logger.getLogger(SendMoneyJob.class);
	
	private SendMoneyService sendMoneyService;

	protected void initialize(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:/wallet-applicationContext.xml", 
				"classpath*:/scheduler-applicationContext.xml"});
		sendMoneyService = (SendMoneyService)context.getBean("sendMoneyService");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		initialize();
		try {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
			Long sendMoneyId = Long.parseLong(jobDataMap.get(JobConstants.SEND_MONEY_ID).toString());
			List<SendMoneyTxn> occurenceDetailsList = (List<SendMoneyTxn>) jobDataMap.get(JobConstants.SEND_MONEY_OCCURENCES_DETAILS);
			Integer occurence = occurenceDetailsList.size();
			++occurence;
			SendMoneyTxn sendMoneyTxn = sendMoneyService.startSendMoneyRecurring(sendMoneyId, occurence);
			occurenceDetailsList.add(sendMoneyTxn);
			jobDataMap.put(JobConstants.SEND_MONEY_OCCURENCES_DETAILS, occurenceDetailsList);
			LOGGER.info("SendMoneyJob started at: " + new Date() + "\t occurence: " + occurence);
			LOGGER.info("Send Money Id: " + sendMoneyId + " \t auth Id: " + jobDataMap.get(JobConstants.AUTH_ID));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
