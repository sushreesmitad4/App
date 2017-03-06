package com.tarang.ewallet.scheduler.jobs;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.scheduler.util.JobConstants;

public class UnblockedUsersJob extends WalletJob {
	
	private static final Logger LOGGER = Logger.getLogger(SendMoneyJob.class);
	
	private CommonService commonService;
	
	protected void initialize(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:/wallet-applicationContext.xml", 
				"classpath*:/scheduler-applicationContext.xml"});
		commonService = (CommonService)context.getBean("commonService"); 
	}


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		initialize();
		try {
			LOGGER.info("Entering into execute for unblocking user ");
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
			Long authenticationId = Long.parseLong(jobDataMap.get(JobConstants.AUTH_ID).toString());
			LOGGER.info("User AuthenticationId :: " + authenticationId);
			Authentication authentication = commonService.getAuthentication(authenticationId);
			LOGGER.info("User Email ID :: " + authentication.getEmailId());
			LOGGER.info("User Authentication MPIN Blocked Date :: " + authentication.getmPinBlocked());
			LOGGER.info("User Authentication MPIN Attempts :: " + authentication.getmPinAttempts());
			LOGGER.info("User Authentication MPIN Blocked :: " + authentication.getmPinBlocked());
			
			authentication.setmPinBlocked(Boolean.FALSE);
			authentication.setmPinBlockedDate(null);
			authentication.setmPinAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
			commonService.updateAuthentication(authentication);
			LOGGER.info("User authenticationId :: " + authentication.getId() + "At"  + new Date() );
			String personName = jobDataMap.get(JobConstants.PERSON_NAME).toString();
			String userTypeName = jobDataMap.get(JobConstants.USER_TYPE_NAME).toString();
			LOGGER.info("PERSON_NAME :: " + personName);
			LOGGER.info("USER TYPE NAME :: " + userTypeName);
			commonService.sendEmailNotificationToMobileUser(personName, userTypeName, authenticationId, CommonConstrain.DEFAULT_LANGUAGE, 
					EmailServiceConstants.MOBILE_WALLET_UNBLOCK_CONFIRMATION_ACTION_TAKENBY_SYSTEM);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}


}
