package com.tarang.ewallet.scheduler.util;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.scheduler.jobs.UnblockedUsersJob;
import com.tarang.ewallet.util.DateConvertion;

public class UnblockedUsersJobService {
	
	private static final Logger LOGGER = Logger.getLogger(SendMoneyJobService.class);

	private Scheduler scheduler;
	
	private Properties walletJobProperties;

	public UnblockedUsersJobService(Scheduler scheduler, Properties walletJobProperties){
		this.scheduler = scheduler;
		this.walletJobProperties = walletJobProperties;
	}
	
	public void scheduleNewJob(Map<String, String> jobProps) throws WalletException {
		try {
			LOGGER.info("scheduleNewJob for unblocking users");
			String groupName = jobProps.get(JobConstants.GROUP_NAME) + jobProps.get(JobConstants.AUTH_ID);
			
			String jobName = JobNames.UNBLOCKED_USER_JOB + jobProps.get(JobConstants.AUTH_ID);
			JobKey jobKeyA = new JobKey(jobName, groupName);
			
			JobDetail jobDetail = JobBuilder.newJob(UnblockedUsersJob.class).withIdentity(jobKeyA).build();
			
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			
			jobDataMap.put(JobConstants.AUTH_ID, jobProps.get(JobConstants.AUTH_ID));
			
			jobDataMap.put(JobConstants.USER_JOB_NAME, jobProps.get(JobConstants.USER_JOB_NAME));
			jobDataMap.put(JobConstants.CREATION_DATE, new Date());
			jobDataMap.put(JobConstants.AUTH_ID, jobProps.get(JobConstants.AUTH_ID));
			jobDataMap.put(JobConstants.PERSON_NAME, jobProps.get(JobConstants.PERSON_NAME));
			jobDataMap.put(JobConstants.USER_TYPE_NAME, jobProps.get(JobConstants.USER_TYPE_NAME));
			
		    //get future date after adding Configure Hour in present date 
		    //at present it is taking 4 hour
			Date futureDate = DateConvertion.getFutureDateByHours(
					Integer.parseInt((String) walletJobProperties.get(JobConstants.UNBLOCKED_USER_AT_HOUR)));
			LOGGER.info("Future Unblocked Date :: " + futureDate);
		    Trigger trigger = TriggerBuilder
					.newTrigger()
			        .withIdentity(jobName, groupName)
			        .withSchedule(
			        		CronScheduleBuilder.
			        		atHourAndMinuteOnGivenDaysOfWeek
			        		(DateConvertion.getDayOfHour(futureDate), DateConvertion.getDayOfHourMinute(futureDate), 
			        				DateConvertion.getDayOfWeek(futureDate)))
			        .build();
		    jobDataMap.put(JobConstants.START_DATE, trigger.getStartTime());
		    scheduler.scheduleJob(jobDetail, trigger);
		} catch(Exception ex){
			LOGGER.error( ex.getMessage(), ex);
		}
	}

}

