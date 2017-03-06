package com.tarang.ewallet.scheduler.jobs;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.transaction.util.WalletTransactionStatus;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.service.UtilService;


public class CancelNonRegisterWalletTxnsJob extends WalletJob {

	private static final Logger LOGGER = Logger.getLogger(CancelNonRegisterWalletTxnsJob.class);
	
	private CommonService commonService;
	private UtilService utilService;
	private TransactionWalletService transactionWalletService;

	protected void initialize(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:/wallet-applicationContext.xml", 
				"classpath*:/scheduler-applicationContext.xml"});
		commonService = (CommonService)context.getBean("commonService");
		utilService = (UtilService)context.getBean("utilService");
		transactionWalletService = (TransactionWalletService)context.getBean("transactionWalletService");
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		executeMethod();
	}
	
	public void executeMethod(){
		try {
			LOGGER.info("CancelNonRegisterWalletTxnsJob started at: " + new Date() );
			initialize();
			Integer days = utilService.getCancelNonregWalletTxnsAllowedDays();
			Date beforeDate = DateConvertion.pastDate(new Date(), days);
			List<Long> list = commonService.getTxnIdsForNonRegisterWallets(WalletTransactionStatus.PENDING, beforeDate);
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						transactionWalletService.cancelTransaction(list.get(i));
						commonService.updateNonRegisterWallet(list.get(i),WalletTransactionStatus.CANCEL);
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
