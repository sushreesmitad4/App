/**
 * 
 */
package com.tarang.ewallet.scheduler.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.SendMoneyJobDetailsModel;
import com.tarang.ewallet.model.SendMoneyJobSummaryModel;
import com.tarang.ewallet.scheduler.jobs.AccountCloserJob;
import com.tarang.ewallet.scheduler.jobs.CancelNonRegisterWalletTxnsJob;
import com.tarang.ewallet.scheduler.jobs.DisputeForceWithdrawalJob;
import com.tarang.ewallet.scheduler.jobs.PgSettlementJob;
import com.tarang.ewallet.scheduler.jobs.ReconcileJob;
import com.tarang.ewallet.scheduler.jobs.ResentEmailForFailedEmailRecords;
import com.tarang.ewallet.util.CommonProjectUtil;
import com.tarang.ewallet.util.service.UtilService;

/**
 * @author  : prasadj
 * @date    : Dec 19, 2012
 * @time    : 11:55:10 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class SchedulerManager {

	private static final Logger LOGGER = Logger.getLogger(SchedulerManager.class);
	
	private static Scheduler scheduler;
	
	private Properties schedulerProperties;
	
	private static Properties walletJobProperties;
	
	private SendMoneyJobService sendMoneyJobService;
	
	private UnblockedUsersJobService unblockedUsersJobService;
	
	public SchedulerManager(UtilService utilService){
		schedulerProperties = CommonProjectUtil.loadProperties(utilService.getSchedularPropsFile());
		walletJobProperties = CommonProjectUtil.loadProperties(utilService.getWalletJobsPropsFile());
	}
	
	public void startScheduler() throws WalletException {
		
		try{  
			if(schedulerProperties != null && schedulerProperties.size() != 0) {
				if(scheduler == null || scheduler.isShutdown()){
					StdSchedulerFactory sf = new StdSchedulerFactory();
					sf.initialize(schedulerProperties);
					scheduler = sf.getScheduler();
			        scheduler.start();
			        sendMoneyJobService = new SendMoneyJobService(scheduler, walletJobProperties);
			        unblockedUsersJobService = new UnblockedUsersJobService(scheduler, walletJobProperties);//For Unblocked Users
			        LOGGER.info("--scheduler started at: " + new java.util.Date());
				}
			}else {
				throw new WalletException(JobConstants.PROPERTIES_NOT_FOUND);
			}
		 } catch(Exception ex){  
			 LOGGER.error( ex.getMessage(), ex);
			 throw new WalletException(ex.getMessage(), ex);
		 }  
	}
	
	public void stopScheduler() throws WalletException {
		if(scheduler != null){
			try {
				scheduler.shutdown(true);
			}catch(Exception ex){
				LOGGER.error( ex.getMessage(), ex);
				throw new WalletException(ex.getMessage(), ex);
			}
		}
	}
	public List<String> jobNamesByGroupName(String groupName){
		List<String> list = new ArrayList<String>();
		try {
			Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
			for(JobKey job: jobs){
				String jobName = job.getName();
				list.add(jobName);
			}

		} catch(Exception ex){
			LOGGER.error( ex.getMessage(), ex);
		}
		return list;
	}
	
	public Boolean deleteJob(String jobId, String groupName) {
		try {
			JobKey jobKey = new JobKey(jobId, groupName);
			return scheduler.deleteJob(jobKey);
		} catch(Exception ex){
			LOGGER.error( ex.getMessage(), ex);
			return Boolean.FALSE;
		}
	}

	public Boolean deleteJobs(String groupName) {
		try {
			Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
			LOGGER.info(" Jobs size "+jobs.size());
			for(JobKey job: jobs){
				scheduler.deleteJob(job);
				LOGGER.info(" Jobs name "+job.getName()+" deleted at "+new Date());
			}
			return Boolean.TRUE;
		} catch(Exception ex){
			LOGGER.error( ex.getMessage(), ex);
			return Boolean.FALSE;
		}
	}
	
	public void scheduleNewJob(Map<String, String> jobProps) throws WalletException {
		try {
			LOGGER.info("Creating new schedule job");
			String groupName = jobProps.get(JobConstants.GROUP_NAME);
			String tempGroupName = groupName;
			String jobName = jobProps.get(JobConstants.JOB_NAME);
			JobDetail jobDetail = null;
			Trigger trigger = null;
			if(SchedulerGroupNames.DISPUTE_FORCE_WITHDRAWAL.equals(groupName)){
				Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(tempGroupName));
				if(jobs.isEmpty()){
					JobKey jobKeyA = new JobKey(jobName, tempGroupName);
					jobDetail = JobBuilder.newJob(DisputeForceWithdrawalJob.class).withIdentity(jobKeyA).build();
					//jobDetail.getJobDataMap().put("displaykey", jobProps.get("displaykey")); add required properties
			    	trigger = TriggerBuilder
			    			.newTrigger()
			    			.withIdentity(jobName, tempGroupName)
			    			.withSchedule(CronScheduleBuilder.cronSchedule((String)walletJobProperties.get(JobConstants.DISPUTE_FROCE_WITHDRAWAL_TRIGGER_EXP)))
			    			.build();
				}
			} else if(SchedulerGroupNames.RECONCILATION.equals(groupName)){
				Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(tempGroupName));
				if(jobs.isEmpty()){
					JobKey jobKeyA = new JobKey(jobName, tempGroupName);
					jobDetail = JobBuilder.newJob(ReconcileJob.class).withIdentity(jobKeyA).build();
					//jobDetail.getJobDataMap().put("displaykey", jobProps.get("displaykey")); add required properties
			    	trigger = TriggerBuilder
			    			.newTrigger()
			    			.withIdentity(jobName, tempGroupName)
			    			.withSchedule(CronScheduleBuilder.cronSchedule((String)walletJobProperties.get(JobConstants.RECONCILE_TRIGGER_EXP)))
			    			.build();
				}
			} else if(SchedulerGroupNames.NON_SETTLEMENT.equals(groupName)){
				Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(tempGroupName));
				if(jobs.isEmpty()){
					JobKey jobKeyA = new JobKey(jobName, tempGroupName);
					jobDetail = JobBuilder.newJob(PgSettlementJob.class).withIdentity(jobKeyA).build();
					//jobDetail.getJobDataMap().put("displaykey", jobProps.get("displaykey")); add required properties
			    	trigger = TriggerBuilder
			    			.newTrigger()
			    			.withIdentity(jobName, tempGroupName)
			    			.withSchedule(CronScheduleBuilder.cronSchedule((String)walletJobProperties.get(JobConstants.PG_SETTLEMENT_TRIGGER_EXP)))
			    			.build();
				}
			} else if(SchedulerGroupNames.CANCEL_NON_REGISTER_WALLET_TXNS.equals(groupName)){
				Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(tempGroupName));
				if(jobs.isEmpty()){
					JobKey jobKeyA = new JobKey(jobName, tempGroupName);
					jobDetail = JobBuilder.newJob(CancelNonRegisterWalletTxnsJob.class).withIdentity(jobKeyA).build();
					//jobDetail.getJobDataMap().put("displaykey", jobProps.get("displaykey")); add required properties
			    	trigger = TriggerBuilder
			    			.newTrigger()
			    			.withIdentity(jobName, tempGroupName)
			    			.withSchedule(CronScheduleBuilder.cronSchedule((String)walletJobProperties.get(JobConstants.CANCEL_NON_REGISTER_WALLET_TXNS_EXP)))
			    			.build();
				}
			} else if(SchedulerGroupNames.PENDING_ACCOUNT_CLOSERS.equals(groupName)){
				Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(tempGroupName));
				if(jobs.isEmpty()){
					JobKey jobKeyA = new JobKey(jobName, tempGroupName);
					jobDetail = JobBuilder.newJob(AccountCloserJob.class).withIdentity(jobKeyA).build();
					//jobDetail.getJobDataMap().put("displaykey", jobProps.get("displaykey")); add required properties
			    	trigger = TriggerBuilder
			    			.newTrigger()
			    			.withIdentity(jobName, tempGroupName)
			    			.withSchedule(CronScheduleBuilder.cronSchedule((String)walletJobProperties.get(JobConstants.ACCOUNT_CLOSE_EXP)))
			    			.build();
				}
			} else if(SchedulerGroupNames.RESENT_FAILED_EMAIL.equals(groupName)){
				Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(tempGroupName));
				if(jobs.isEmpty()){
					JobKey jobKeyA = new JobKey(jobName, tempGroupName);
					jobDetail = JobBuilder.newJob(ResentEmailForFailedEmailRecords.class).withIdentity(jobKeyA).build();
					//jobDetail.getJobDataMap().put("displaykey", jobProps.get("displaykey")); add required properties
			    	trigger = TriggerBuilder
			    			.newTrigger()
			    			.withIdentity(jobName, tempGroupName)
			    			.withSchedule(CronScheduleBuilder.cronSchedule((String)walletJobProperties.get(JobConstants.RESENT_EMAIL_EXP)))
			    			.build();
				}
			}
			if(jobDetail != null){
				LOGGER.info("create job:" + jobName + " from group: " + tempGroupName);
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				LOGGER.info("job not created job:" + jobName + " from group: " + tempGroupName);
			}
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void scheduleSendMoneyNewJob(Map<String, String> jobProps) throws WalletException {
		sendMoneyJobService.scheduleNewJob(jobProps);
	}
	
	public static String getWalletJobProperty(String key){
		return (String)walletJobProperties.get(key);
	}
	
	//SchedulerServiceImpl.deleteSendMoneyJob()
	public Boolean deleteSendMoneyJob(Long authId, Long sendmoneyId) {
		return sendMoneyJobService.deleteSendMoneyJob(authId, sendmoneyId);
	}
	
	public Boolean updateSendMoneyJob(Map<String, String> props) {
		return sendMoneyJobService.updateSendMoneyJob(props);
	}

	//SchedulerServiceImpl.getSendMoneyJobs(authId, false) && getSendMoneyActiveJobs(authId, true)
	public List<SendMoneyJobSummaryModel> getSendMoneyJobsSummary(Long authId, Boolean onlyActive){
		return sendMoneyJobService.getSendMoneyJobsSummary(authId, onlyActive);
	}

	//SchedulerServiceImpl.getSendMoneyJobDetais()
	public SendMoneyJobDetailsModel getSendMoneyJobDetailsModel(Long authId, Long sendmoneyId){
		return sendMoneyJobService.getSendMoneyJobDetailsModel(authId, sendmoneyId);
	}

	public void scheduleUnblockedUsersNewJob(Map<String, String> jobProperties) throws WalletException {
		unblockedUsersJobService.scheduleNewJob(jobProperties);
	}

}
