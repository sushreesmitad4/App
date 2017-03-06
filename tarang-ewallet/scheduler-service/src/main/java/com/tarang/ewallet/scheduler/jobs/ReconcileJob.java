/**
 * 
 */
package com.tarang.ewallet.scheduler.jobs;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.reconcile.business.ReconcileService;

/**
 * @author  : prasadj
 * @date    : Mar 7, 2013
 * @time    : 6:19:33 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class ReconcileJob extends WalletJob {

	private static final Logger LOGGER = Logger.getLogger(ReconcileJob.class);
	
	private ReconcileService reconcileService;

	protected void initialize(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:/wallet-applicationContext.xml", 
				"classpath*:/scheduler-applicationContext.xml"});
		reconcileService = (ReconcileService)context.getBean("reconcileService");
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		executeMethod();
	}
	
	public void executeMethod(){
		try {
			LOGGER.info("ReconcileJob started at: " + new Date() );
			initialize();
			reconcileService.startReconcile();
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}