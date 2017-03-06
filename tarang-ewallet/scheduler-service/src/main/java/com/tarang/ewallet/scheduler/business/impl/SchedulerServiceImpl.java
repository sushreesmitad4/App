/**
 * 
 */
package com.tarang.ewallet.scheduler.business.impl;

import java.util.List;
import java.util.Map;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.SendMoneyJobDetailsModel;
import com.tarang.ewallet.model.SendMoneyJobSummaryModel;
import com.tarang.ewallet.scheduler.business.SchedulerService;
import com.tarang.ewallet.scheduler.util.SchedulerManager;


/**
 * @author  : prasadj
 * @date    : Dec 19, 2012
 * @time    : 11:44:05 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class SchedulerServiceImpl implements SchedulerService {

	private SchedulerManager schedulerManager;

	public SchedulerServiceImpl(SchedulerManager schedulerManager){
		this.schedulerManager = schedulerManager;
	}
	
	@Override
	public void startScheduler() throws WalletException {
		schedulerManager.startScheduler();
	}

	@Override
	public void stopScheduler() throws WalletException {
		schedulerManager.stopScheduler();
	}

	@Override
	public void scheduleNewJob( Map<String, String> jobProperties) throws WalletException {
		schedulerManager.scheduleNewJob(jobProperties);
	}

	@Override
	public void scheduleSendMoneyNewJob(Map<String, String> jobProperties) throws WalletException{
		schedulerManager.scheduleSendMoneyNewJob(jobProperties);
	}
	@Override
	public List<String> jobNamesByGroupName(String groupName) {
		return schedulerManager.jobNamesByGroupName(groupName);
	}
	
	@Override
	public Boolean deleteJob(String jobId, String groupName) {
		return schedulerManager.deleteJob(jobId, groupName);
	}

	@Override
	public Boolean deleteJobs(String groupName) {
		return schedulerManager.deleteJobs(groupName);
	}

	@Override
	public Boolean deleteSendMoneyJob(Long authId, Long sendmoneyId) {
		return schedulerManager.deleteSendMoneyJob(authId, sendmoneyId);
	}

	@Override
	public List<SendMoneyJobSummaryModel> getSendMoneyJobs(Long authId) {
		return schedulerManager.getSendMoneyJobsSummary(authId, Boolean.FALSE);
	}

	@Override
	public List<SendMoneyJobSummaryModel> getSendMoneyActiveJobs(Long authId) {
		return schedulerManager.getSendMoneyJobsSummary(authId, Boolean.TRUE);
	}

	@Override
	public SendMoneyJobDetailsModel getSendMoneyJobDetais(Long authId, Long sendmoneyId) {
		return schedulerManager.getSendMoneyJobDetailsModel(authId, sendmoneyId);
	}

	@Override
	public Boolean updateSendMoneyJob(Map<String, String> jobProperties) {
		return schedulerManager.updateSendMoneyJob(jobProperties);
	}

	@Override
	public void scheduleUnblockedUsersNewJob(Map<String, String> jobProperties) throws WalletException {
		schedulerManager.scheduleUnblockedUsersNewJob(jobProperties);
	}
	
}
