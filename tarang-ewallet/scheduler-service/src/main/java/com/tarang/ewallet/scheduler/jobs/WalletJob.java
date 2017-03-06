/**
 * 
 */
package com.tarang.ewallet.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;


/**
 * @author  : prasadj
 * @date    : Mar 7, 2013
 * @time    : 6:07:43 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public abstract class WalletJob implements Job {

	public void commonExecution(JobExecutionContext context) {
	}
	
}
