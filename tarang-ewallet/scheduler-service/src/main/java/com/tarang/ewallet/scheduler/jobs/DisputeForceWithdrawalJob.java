/**
 * 
 */
package com.tarang.ewallet.scheduler.jobs;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dispute.business.DisputeService;
import com.tarang.ewallet.dispute.util.DisputeStatusConstants;
import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.scheduler.util.JobConstants;
import com.tarang.ewallet.scheduler.util.SchedulerManager;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.transaction.util.ReversalType;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author  : prasadj
 * @date    : Mar 7, 2013
 * @time    : 7:14:00 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class DisputeForceWithdrawalJob extends WalletJob {

	private static final Logger LOGGER = Logger.getLogger(DisputeForceWithdrawalJob.class);
	
	private DisputeService disputeService;
	
	private TransactionWalletService txnWalletService;
	
	private CommonService commonService;
	
	private CustomerService customerService;
	
	private MerchantService merchantService;

	protected void initialize(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:/wallet-applicationContext.xml", 
				"classpath*:/scheduler-applicationContext.xml"});
		disputeService = (DisputeService)context.getBean("disputeService");
		txnWalletService = (TransactionWalletService)context.getBean("transactionWalletService");
		commonService = (CommonService)context.getBean("commonService");
		customerService = (CustomerService)context.getBean("customerService");
		merchantService = (MerchantService)context.getBean("merchantService");
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		executeMethod();
	}
	
	public void executeMethod(){
		try {
			LOGGER.info("DisputeForceWithdrawalJob started at: " + new Date() );
			initialize();
			List<Long> listOfMerchantToPay = disputeService.getMerchantToPayStatusDisputeIds(DisputeStatusConstants.MERCHANT_TO_PAY, 
					DateConvertion.pastDate(new Date(), Integer.parseInt(SchedulerManager.getWalletJobProperty(JobConstants.MERCHANT_TOPAY_WITHIN_DAYS))));
			if(listOfMerchantToPay != null && listOfMerchantToPay.size() > 0 ) {
				for (int i = 0; i < listOfMerchantToPay.size(); i++) {
					try{
						DisputeDto disputeDto = disputeService.getDisputeById(listOfMerchantToPay.get(i));
						LOGGER.info("dispute id: " + disputeDto.getId());
						LOGGER.info("disputeDto Object" + disputeDto);
						DisputeDto  updateDto = new DisputeDto();
						updateDto.setId(listOfMerchantToPay.get(i));
						updateDto.setStatus(DisputeStatusConstants.FORCE_WITHDRAWAL);
						updateDto.setMessage(SchedulerManager.getWalletJobProperty(JobConstants.MERCHANT_TOPAY_MESSAGE));
						updateDto.setCreator(new BigDecimal(GlobalLitterals.ADMIN_USER_TYPE_ID.toString()));
						updateDto.setApprovedAmount(Double.valueOf(disputeDto.getApprovedamount()));
						
						/*For email template*/
						WalletTransaction walletTxn = txnWalletService.getTransaction(disputeDto.getTransactionId());
						updateDto.setPayeeemailid(commonService.getUserEmailById(walletTxn.getPayee()));
						updateDto.setPayeremailid(commonService.getUserEmailById(walletTxn.getPayer()));
												
						String personName = customerService.getPersonName(updateDto.getPayeremailid(), GlobalLitterals.CUSTOMER_USER_TYPE);
						updateDto.setPayerName(personName);
						
						personName = merchantService.getPersonName(updateDto.getPayeeemailid(), GlobalLitterals.MERCHANT_USER_TYPE);												
						updateDto.setPayeeName(personName);
						/*Dispute is updated by system*/
						updateDto.setUpdatedby(GlobalLitterals.DISPUTE_UPDATED_BY_SYSTEM);
						updateDto.setCurrencyCode(disputeDto.getApprovedcurrency());
						if(disputeDto.getType().equals(ReversalType.CHARGE_BACK)){
							updateDto.setDisputetype(GlobalLitterals.CHARGE_BACK_LABEL);
						} else{
							updateDto.setDisputetype(GlobalLitterals.REFUND_LABEL);
						}
						disputeService.updateDispute(updateDto);
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
