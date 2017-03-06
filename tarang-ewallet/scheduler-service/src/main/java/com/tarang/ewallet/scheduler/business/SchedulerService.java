/**
 * 
 */
package com.tarang.ewallet.scheduler.business;

import java.util.List;
import java.util.Map;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.SendMoneyJobDetailsModel;
import com.tarang.ewallet.model.SendMoneyJobSummaryModel;


/**
 * @author  : prasadj
 * @date    : Dec 19, 2012
 * @time    : 11:43:01 AM
 * @version : 1.0.0
 * @comments: 
 *
 */

public interface SchedulerService {

	void stopScheduler() throws WalletException;
	
	void startScheduler() throws WalletException;
	
	void scheduleNewJob(Map<String, String> jobProperties) throws WalletException;
	
	void scheduleSendMoneyNewJob(Map<String, String> jobProperties) throws WalletException;
	
	void scheduleUnblockedUsersNewJob(Map<String, String> jobProperties) throws WalletException;
	
	List<String> jobNamesByGroupName(String groupName);

	Boolean deleteJob(String jobId, String groupName);
	
	Boolean deleteJobs(String groupName);
	
	Boolean deleteSendMoneyJob(Long authId, Long sendmoneyId);
		
	List<SendMoneyJobSummaryModel> getSendMoneyJobs(Long authId);
	
	List<SendMoneyJobSummaryModel> getSendMoneyActiveJobs(Long authId);
	
	SendMoneyJobDetailsModel getSendMoneyJobDetais(Long authId, Long sendmoneyId);
	
	Boolean updateSendMoneyJob(Map<String, String> jobProperties);
	
}
