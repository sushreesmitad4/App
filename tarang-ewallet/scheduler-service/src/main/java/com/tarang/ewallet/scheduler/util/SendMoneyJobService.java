/**
 * 
 */
package com.tarang.ewallet.scheduler.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.SendMoneyJobDetailsModel;
import com.tarang.ewallet.model.SendMoneyJobSummaryModel;
import com.tarang.ewallet.model.SendMoneyTxn;
import com.tarang.ewallet.scheduler.jobs.SendMoneyJob;
import com.tarang.ewallet.transaction.util.WalletTransactionStatus;
import com.tarang.ewallet.transaction.util.WalletTransactionTimePeriod;
import com.tarang.ewallet.util.CommonProjectUtil;
import com.tarang.ewallet.util.DateConvertion;


/**
 * @author  : prasadj
 * @date    : Jun 7, 2013
 * @time    : 12:18:52 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SendMoneyJobService {

	private static final Logger LOGGER = Logger.getLogger(SendMoneyJobService.class);
	
	private Scheduler scheduler;
	
	private Properties walletJobProperties;
	

	public SendMoneyJobService(Scheduler scheduler, Properties walletJobProperties){
		this.scheduler = scheduler;
		this.walletJobProperties = walletJobProperties;
	}
	
	public void scheduleNewJob(Map<String, String> jobProps) throws WalletException {
		try {
			LOGGER.info("Creating new send money job");
			String groupName = jobProps.get(JobConstants.GROUP_NAME) + jobProps.get(JobConstants.AUTH_ID);
			String jobName = JobNames.SEND_MONEY_JOB + jobProps.get(JobConstants.SEND_MONEY_ID);
			JobKey jobKeyA = new JobKey(jobName, groupName);
			JobDetail jobDetail = JobBuilder.newJob(SendMoneyJob.class).withIdentity(jobKeyA).build();
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			jobDataMap.put(JobConstants.SEND_MONEY_ID, jobProps.get(JobConstants.SEND_MONEY_ID));
			jobDataMap.put(JobConstants.USER_JOB_NAME, jobProps.get(JobConstants.USER_JOB_NAME));
			jobDataMap.put(JobConstants.CREATION_DATE, new Date());
			jobDataMap.put(JobConstants.AUTH_ID, jobProps.get(JobConstants.AUTH_ID));
			jobDataMap.put(JobConstants.SEND_MONEY_OCCURENCES_DETAILS, new ArrayList<SendMoneyTxn>());
			Date startDate = DateConvertion.stirngToDate(jobProps.get(JobConstants.FROM_DATE));
		    Date endDate = DateConvertion.getToDateWithEndTime(jobProps.get(JobConstants.TO_DATE));
		    Long frequency = Long.parseLong(jobProps.get(JobConstants.SEND_MONEY_FREQUENCY));
		    jobDataMap.put(JobConstants.SEND_MONEY_FREQUENCY, frequency);
		    LOGGER.info("startDate: " + startDate + " endDate: " + endDate + " frequency: " +frequency);				    	
		    Trigger trigger = TriggerBuilder
					.newTrigger()
			        .withIdentity(jobName, groupName)
			        .startAt(startDate)
			        .endAt(endDate)
			        .withSchedule(CronScheduleBuilder.cronSchedule(prepareTriggerExpression(startDate, frequency)))
			        .build();
		    jobDataMap.put(JobConstants.START_DATE, trigger.getStartTime());
		    scheduler.scheduleJob(jobDetail, trigger);
		} catch(Exception ex){
			LOGGER.error( ex.getMessage(), ex);
		}
	}
	
	//SchedulerServiceImpl.deleteSendMoneyJob()
	public Boolean deleteSendMoneyJob(Long authId, Long sendmoneyId) {
		try {
			JobKey jobKey = new JobKey(JobNames.SEND_MONEY_JOB + sendmoneyId, SchedulerGroupNames.SEND_MONEY_RECURRING + authId);
			return scheduler.deleteJob(jobKey);
		} catch(Exception ex){
			LOGGER.error( ex.getMessage(), ex);
			return Boolean.FALSE;
		}
	}
	
	public Boolean updateSendMoneyJob(Map<String, String> props) {
		try {
			//start date functionality
			String jobName = JobNames.SEND_MONEY_JOB + props.get(JobConstants.SEND_MONEY_ID);
			String groupName = SchedulerGroupNames.SEND_MONEY_RECURRING + props.get(JobConstants.AUTH_ID);
			
			Date startDate = DateConvertion.stirngToDate(props.get(JobConstants.FROM_DATE));
			Date endDate = DateConvertion.getToDateWithEndTime(props.get(JobConstants.TO_DATE));
			Long frequency = Long.parseLong(props.get(JobConstants.SEND_MONEY_FREQUENCY));
			
			Trigger newTrigger = TriggerBuilder
					.newTrigger()
			        .withIdentity(jobName, groupName)
			        .startAt(startDate)
			        .endAt(endDate)
			        .withSchedule(CronScheduleBuilder.cronSchedule(prepareTriggerExpression(startDate, frequency)))
			        .build();
			
			JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobName, groupName));
				Map<String, Object> oldDetails = getSendMoneyProperties(jobDetail);
				JobKey jobKeyA = new JobKey(jobName, groupName);
				deleteSendMoneyJob(Long.valueOf(props.get(JobConstants.AUTH_ID)), Long.valueOf(props.get(JobConstants.SEND_MONEY_ID)));
				jobDetail = JobBuilder.newJob(SendMoneyJob.class).withIdentity(jobKeyA).build();
				updateSendMoneyJobDetails(jobDetail, oldDetails);
				List statusDetailsList = (List) jobDetail.getJobDataMap().get(JobConstants.SEND_MONEY_OCCURENCES_DETAILS);
				if(statusDetailsList == null  || statusDetailsList.size() == 0){
					jobDetail.getJobDataMap().put(JobConstants.START_DATE, startDate);
				}
				jobDetail.getJobDataMap().put(JobConstants.UPDATED_DATE, new Date());
				jobDetail.getJobDataMap().put(JobConstants.TO_DATE, endDate);
				scheduler.scheduleJob(jobDetail, newTrigger);
			return Boolean.TRUE;
		} catch(Exception ex){
			LOGGER.error( ex.getMessage(), ex);
			return Boolean.FALSE;
		}
	}

	public void updateSendMoneyJobDetails(JobDetail jobDetail, Map<String, Object> map){
		JobDataMap jobDataMap = jobDetail.getJobDataMap();  
		jobDataMap.put(JobConstants.SEND_MONEY_ID, map.get(JobConstants.SEND_MONEY_ID));  
		jobDataMap.put(JobConstants.USER_JOB_NAME, map.get(JobConstants.USER_JOB_NAME));
		jobDataMap.put(JobConstants.SEND_MONEY_OCCURENCES_DETAILS, map.get(JobConstants.SEND_MONEY_OCCURENCES_DETAILS));
		jobDataMap.put(JobConstants.SEND_MONEY_TOTAL_OCCURENCES, map.get(JobConstants.SEND_MONEY_TOTAL_OCCURENCES));
		jobDataMap.put(JobConstants.SEND_MONEY_FREQUENCY, map.get(JobConstants.SEND_MONEY_FREQUENCY));
		jobDataMap.put(JobConstants.CREATION_DATE, map.get(JobConstants.CREATION_DATE));
		jobDataMap.put(JobConstants.START_DATE, map.get(JobConstants.START_DATE));
	}
	
	public Map<String, Object> getSendMoneyProperties(JobDetail jobDetail){
		Map<String, Object> map = new HashMap<String, Object>();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();  
		map.put(JobConstants.SEND_MONEY_ID, jobDataMap.get(JobConstants.SEND_MONEY_ID));  
		map.put(JobConstants.USER_JOB_NAME, jobDataMap.get(JobConstants.USER_JOB_NAME));
		map.put(JobConstants.SEND_MONEY_OCCURENCES_DETAILS, jobDataMap.get(JobConstants.SEND_MONEY_OCCURENCES_DETAILS));
		map.put(JobConstants.SEND_MONEY_TOTAL_OCCURENCES, jobDataMap.get(JobConstants.SEND_MONEY_TOTAL_OCCURENCES));
		map.put(JobConstants.SEND_MONEY_FREQUENCY, jobDataMap.get(JobConstants.SEND_MONEY_FREQUENCY));
		map.put(JobConstants.CREATION_DATE, jobDataMap.get(JobConstants.CREATION_DATE));
		map.put(JobConstants.START_DATE, jobDataMap.get(JobConstants.START_DATE));
		return map;
	}
	
	//SchedulerServiceImpl.getSendMoneyJobs(authId, false) && getSendMoneyActiveJobs(authId, true)
	public List<SendMoneyJobSummaryModel> getSendMoneyJobsSummary(Long authId, Boolean onlyActive){
		List<SendMoneyJobSummaryModel> summaryJobs = new ArrayList<SendMoneyJobSummaryModel>();
		try {
			Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(SchedulerGroupNames.SEND_MONEY_RECURRING + authId));
			for(JobKey job: jobs){
				JobDetail schJob = scheduler.getJobDetail(job);
				if(!onlyActive || !schJob.getJobDataMap().getBooleanValue("key has to replace with is expired key")){
					summaryJobs.add(getSendMoneyJobSummary(schJob));
				}
			}
		} catch(Exception ex){
			LOGGER.error( ex.getMessage(), ex);
		}
		return summaryJobs;
	}

	//SchedulerServiceImpl.getSendMoneyJobDetais()
	public SendMoneyJobDetailsModel getSendMoneyJobDetailsModel(Long authId, Long sendmoneyId){
		SendMoneyJobDetailsModel details = null;
		try {
			details = new SendMoneyJobDetailsModel();
			JobDetail schjobDetails = scheduler.getJobDetail(new JobKey(JobNames.SEND_MONEY_JOB + sendmoneyId, SchedulerGroupNames.SEND_MONEY_RECURRING + authId));
			details = getSendMoneyJobDetails(schjobDetails);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		return details;
	}
	
	 private SendMoneyJobSummaryModel getSendMoneyJobSummary(JobDetail schJob) throws SchedulerException{
		  
		  List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(schJob.getKey());
		  Trigger trigger = triggers.get(0);
		  SendMoneyJobSummaryModel summaryJob = new SendMoneyJobSummaryModel();
		  JobDataMap jobDataMap = schJob.getJobDataMap();
		  summaryJob.setSendMoneyId(CommonProjectUtil.stringToLong(jobDataMap.get(JobConstants.SEND_MONEY_ID)));
		  summaryJob.setUserJobName((String)jobDataMap.get(JobConstants.USER_JOB_NAME));
		  summaryJob.setCreationDate((Date)jobDataMap.get(JobConstants.CREATION_DATE));
		  summaryJob.setStartDate((Date)jobDataMap.get(JobConstants.START_DATE));
		  summaryJob.setEndDate(trigger.getEndTime());
		  summaryJob.setUpdatedDate((Date)jobDataMap.get(JobConstants.UPDATED_DATE));
		  summaryJob.setRecentFiredTime(trigger.getPreviousFireTime());
		  List<SendMoneyTxn> statusDetailsList = (List<SendMoneyTxn>) jobDataMap.get(JobConstants.SEND_MONEY_OCCURENCES_DETAILS);
		  if(statusDetailsList != null && statusDetailsList.size() > 0){
			  SendMoneyTxn sendMoneyTxn = statusDetailsList.get(statusDetailsList.size() - 1);
			  if(sendMoneyTxn != null){
				  summaryJob.setRecentFiredStatus(sendMoneyTxn.getTransactionStatus());
				  summaryJob.setRecentFiredTime(sendMoneyTxn.getTriggerDate());
			  }
		  } else{
			  summaryJob.setRecentFiredStatus(0L);
		  }
		  //Update summaryJob read required details from schJob
		  return summaryJob;
	}
	
	private SendMoneyJobDetailsModel getSendMoneyJobDetails(JobDetail schJob) throws SchedulerException{
		SendMoneyJobDetailsModel detailsJob = new SendMoneyJobDetailsModel();
		List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(schJob.getKey());
		Trigger trigger = triggers.get(0);
		JobDataMap jobDataMap = schJob.getJobDataMap();		
		List<SendMoneyTxn> statusDetailsList = (List<SendMoneyTxn>) jobDataMap.get(JobConstants.SEND_MONEY_OCCURENCES_DETAILS);
		detailsJob.setUserJobName((String)jobDataMap.get(JobConstants.USER_JOB_NAME));
		detailsJob.setSendMoneyId(CommonProjectUtil.stringToLong(jobDataMap.get(JobConstants.SEND_MONEY_ID)));
		detailsJob.setFrequency(CommonProjectUtil.stringToLong(jobDataMap.get(JobConstants.SEND_MONEY_FREQUENCY)));
		detailsJob.setCreationDate((Date)jobDataMap.get(JobConstants.CREATION_DATE));
		detailsJob.setStartDate((Date)jobDataMap.get(JobConstants.START_DATE));
		detailsJob.setEndDate(trigger.getEndTime());
		if(jobDataMap.get(JobConstants.UPDATED_DATE) != null){
			detailsJob.setUpdatedDate((Date)jobDataMap.get(JobConstants.UPDATED_DATE));		
		} else {
			detailsJob.setUpdatedDate(null);	
		}
		
		detailsJob.setRecentFiredTime(trigger.getPreviousFireTime());	
		detailsJob.setNextOccurrenceDate(trigger.getNextFireTime());
		Long successfulOccurrences = 0L;
		Long failureOccurrences = 0L;
		if(statusDetailsList != null && statusDetailsList.size() > 0){
			SendMoneyTxn sendMoneyTxn = statusDetailsList.get(statusDetailsList.size() - 1);
			if(sendMoneyTxn != null){
				detailsJob.setRecentFiredStatus(sendMoneyTxn.getTransactionStatus());
				detailsJob.setCompletedOccurrences(Long.valueOf(statusDetailsList.size()));
				detailsJob.setFailureMessage(sendMoneyTxn.getFailureMessage());
				for(SendMoneyTxn txn : statusDetailsList){
					if(txn.getTransactionStatus().equals(WalletTransactionStatus.SUCCESS)){
						++successfulOccurrences;
					} else if(txn.getTransactionStatus().equals(WalletTransactionStatus.FAILED) || txn.getTransactionStatus().equals(WalletTransactionStatus.EXPIRED )){
						++failureOccurrences;
					}
				}
			}
		} else{
			detailsJob.setRecentFiredStatus(0L);
			detailsJob.setCompletedOccurrences(0L);
		}
		detailsJob.setSuccessfulOccurrences(successfulOccurrences);
		detailsJob.setFailureOccurrences(failureOccurrences);
		
		return detailsJob;
	}

	/**
	 * @param startDate
	 * @param frequency
	 * @return
	 */
	private String prepareTriggerExpression(Date startDate, Long frequency){
		int weekDay = DateConvertion.getDayOfWeek(startDate);
    	int monthDay = DateConvertion.getDayOfMonth(startDate);
    	String timeExpression = (String) walletJobProperties.get(JobConstants.SENDMONEY_RECURRING_EXP);
    	String trigExpression = timeExpression + " * * ?";
    	
    	if(frequency.equals(WalletTransactionTimePeriod.WEEKLY)) {
    		trigExpression = timeExpression +" ? * "+weekDay+" *";
        } else if(frequency.equals(WalletTransactionTimePeriod.MONTHLY)){
    		trigExpression =  timeExpression +" "+ monthDay+" 1/1 ? *";
        } else if(frequency.equals(WalletTransactionTimePeriod.DAILY)){
        	trigExpression =  timeExpression +" 1/1 * ? *";
        }
    	LOGGER.info("prepareTriggerExpression: " + trigExpression);
    	return trigExpression;
	}
	
}