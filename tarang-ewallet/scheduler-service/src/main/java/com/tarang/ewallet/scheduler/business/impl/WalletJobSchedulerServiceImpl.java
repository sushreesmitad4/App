/**
 * 
 */
package com.tarang.ewallet.scheduler.business.impl;

import java.util.HashMap;
import java.util.Map;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.scheduler.business.WalletJobSchedulerService;
import com.tarang.ewallet.scheduler.business.SchedulerService;
import com.tarang.ewallet.scheduler.util.JobConstants;
import com.tarang.ewallet.scheduler.util.JobNames;
import com.tarang.ewallet.scheduler.util.SchedulerGroupNames;


/**
 * @author vasanthar
 *
 */
public class WalletJobSchedulerServiceImpl implements WalletJobSchedulerService{
	
	
	private SchedulerService schedulerService;

	public WalletJobSchedulerServiceImpl(SchedulerService schedulerService){
		this.schedulerService = schedulerService;
	}

	@Override
	public void disputeForceWithdrawalService() throws WalletException {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put(JobConstants.GROUP_NAME, SchedulerGroupNames.DISPUTE_FORCE_WITHDRAWAL);
		map.put(JobConstants.JOB_NAME, JobNames.DISPUTE_JOB);
		schedulerService.scheduleNewJob(map);		
	}

	@Override
	public void reconsilationService() throws WalletException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(JobConstants.GROUP_NAME, SchedulerGroupNames.RECONCILATION);
		map.put(JobConstants.JOB_NAME, JobNames.RECONCILE_JOB);
		schedulerService.scheduleNewJob(map);
	}

	@Override
	public void pgSettlementService() throws WalletException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(JobConstants.GROUP_NAME, SchedulerGroupNames.NON_SETTLEMENT);
		map.put(JobConstants.JOB_NAME, JobNames.SETTLEMENT_JOB);
		schedulerService.scheduleNewJob(map);
	}

	@Override
	public void cancelNonRegisterWalletTxnsService() throws WalletException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(JobConstants.GROUP_NAME, SchedulerGroupNames.CANCEL_NON_REGISTER_WALLET_TXNS);
		map.put(JobConstants.JOB_NAME, JobNames.CANCEL_NON_REGISTER_WALLET_TXNS_JOB);
		schedulerService.scheduleNewJob(map);
		
	}

	@Override
	public void accountCloserService() throws WalletException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(JobConstants.GROUP_NAME, SchedulerGroupNames.PENDING_ACCOUNT_CLOSERS );
		map.put(JobConstants.JOB_NAME, JobNames.PENDING_ACCOUNT_CLOSERS_JOB);
		schedulerService.scheduleNewJob(map);
		
	}
	
	@Override
	public void emailService() throws WalletException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(JobConstants.GROUP_NAME, SchedulerGroupNames.RESENT_FAILED_EMAIL );
		map.put(JobConstants.JOB_NAME, JobNames.RESENT_EMAIL_JOB);
		schedulerService.scheduleNewJob(map);
		
	}
	
}
