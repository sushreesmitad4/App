package com.tarang.ewallet.scheduler.business;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.model.SendMoneyJobDetailsModel;
import com.tarang.ewallet.model.SendMoneyJobSummaryModel;
import com.tarang.ewallet.scheduler.util.JobConstants;
import com.tarang.ewallet.scheduler.util.SchedulerGroupNames;
import com.tarang.ewallet.transaction.business.SendMoneyService;
import com.tarang.ewallet.util.DateConvertion;

/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:27:02 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class SchedulerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	private static final Logger LOGGER = Logger.getLogger(SchedulerServiceTest.class);

	@Autowired
	private SchedulerService schedulerService;
	
	@Autowired
	private WalletJobSchedulerService walletJobSchedulerService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SendMoneyService sendMoneyService;
	
	@Autowired
	private FeeMgmtService feeService;
	
	@Autowired
	private  HibernateTemplate  hibernateTemplate;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void sample() throws Exception {
		schedulerService.startScheduler();
		Map scheduleProperties = new HashMap();
		schedulerService.scheduleNewJob(scheduleProperties);
		schedulerService.deleteJobs(SchedulerGroupNames.DISPUTE_FORCE_WITHDRAWAL);
		schedulerService.deleteJobs(SchedulerGroupNames.RECONCILATION);
		schedulerService.deleteJobs(SchedulerGroupNames.NON_SETTLEMENT);
		schedulerService.deleteJobs(SchedulerGroupNames.CANCEL_NON_REGISTER_WALLET_TXNS);
		walletJobSchedulerService.disputeForceWithdrawalService();
		walletJobSchedulerService.reconsilationService();
		walletJobSchedulerService.pgSettlementService();
		walletJobSchedulerService.cancelNonRegisterWalletTxnsService();
		Thread.sleep( 60*1000);
		List<String> list = schedulerService.jobNamesByGroupName(SchedulerGroupNames.DISPUTE_FORCE_WITHDRAWAL);
		for(String jname: list){
			LOGGER.info(jname);
		}
		list = schedulerService.jobNamesByGroupName(SchedulerGroupNames.RECONCILATION);
		for(String jname: list){
			LOGGER.info(jname);
		}
		list = schedulerService.jobNamesByGroupName(SchedulerGroupNames.NON_SETTLEMENT);
		for(String jname: list){
			LOGGER.info(jname);
		}
		list = schedulerService.jobNamesByGroupName(SchedulerGroupNames.CANCEL_NON_REGISTER_WALLET_TXNS);
		for(String jname: list){
			LOGGER.info(jname);
		}
		schedulerService.stopScheduler();
	}
	
	@Test
	public void schedulerForSendMoneyRecurringTest() throws WalletException{
		try{
			List<Object> data = DataPreparation.getMasterWalletsData();
	    	Iterator<Object> iter = data.iterator();
	    	while(iter.hasNext()){
	    		hibernateTemplate.save(iter.next());
	    	}
	    	
			CustomerDto dto = DataPreparation.getCustomerDto();		
			dto = customerService.newregistration(dto);
			CustomerDto dto2 = DataPreparation.getCustomerDto();
			dto2.setEmailId("kedarnathd@tarangtech.com");
			dto2.setPhoneCode("22");
			dto2.setPhoneNo("12344622");
			dto2 = customerService.newregistration(dto2);
	
			dto.setStatus(1L);
			dto.setStatusName("1");
			customerService.updateCustomerDetails(dto);
			dto2.setStatus(1L);
			dto2.setStatusName("1");
			customerService.updateCustomerDetails(dto2);
	
			FeeDto feeDto = new FeeDto();
			DataPreparation.dataPrepCreateForFeeDto(feeDto);
			FeeDto feeDtoSaved = feeService.createFee(feeDto);
			Assert.assertNotNull(feeDtoSaved.getId());
			Assert.assertNotNull(feeDtoSaved.getFeeSlabs());
			Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(0).getId());
			Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(1).getId());
			
			SendMoneyDto sendMoneyDto = new SendMoneyDto();
			sendMoneyDto.setEmailId(dto.getEmailId());
			sendMoneyDto.setSenderAuthId(dto.getAuthenticationId());
			sendMoneyDto.setReceiverAuthId(dto2.getAuthenticationId());
			sendMoneyDto.setRequestedAmount(20000.00);
			sendMoneyDto.setRequestedCurrency(1L);
			sendMoneyDto.setMessage("hai");
			sendMoneyDto.setDestinationType(1L);
			sendMoneyDto.setRecurring(true);
			sendMoneyDto.setFromDate(new Date());
			sendMoneyDto.setToDate(DateConvertion.getFutureDateByHours(24));
			sendMoneyDto.setFrequency(1L);
			sendMoneyDto.setTotalOccurences(2);
			sendMoneyDto.setTransactionType(2L);
			sendMoneyDto.setRequestDate(new Date());
			sendMoneyDto.setCurrencyCode("USD");
			sendMoneyDto.setTypeOfRequest(1L);
			LOGGER.info(" About to call createSendMoney ");
			SendMoneyDto sendMoneyToSingle = sendMoneyService.createSendMoney(sendMoneyDto);
			Assert.assertNotNull(sendMoneyToSingle);
			LOGGER.info(" Successfully created SendMoney");
			
			/* Start job scheduling for recurring send money */
			schedulerService.startScheduler();
			Map<String, String> scheduleProperties = DataPreparation.getJobProperties();
			scheduleProperties.put(JobConstants.AUTH_ID, sendMoneyToSingle.getSenderAuthId().toString());
			scheduleProperties.put(JobConstants.SEND_MONEY_ID, sendMoneyToSingle.getId().toString());
			
			schedulerService.deleteJobs(SchedulerGroupNames.SEND_MONEY_RECURRING);
			schedulerService.scheduleSendMoneyNewJob(scheduleProperties);
			
			List<SendMoneyJobSummaryModel> sendMoneyJobsList = schedulerService.getSendMoneyJobs(sendMoneyToSingle.getSenderAuthId());
			Assert.assertNotNull(sendMoneyJobsList);
			LOGGER.info(" sendMoneyJobsList size :: " + sendMoneyJobsList.size());
			
			SendMoneyJobDetailsModel sendMoneyJobDetailsModel = schedulerService.getSendMoneyJobDetais(sendMoneyToSingle.getSenderAuthId(), sendMoneyToSingle.getId());
			Assert.assertNotNull(sendMoneyJobDetailsModel);
			LOGGER.info(" sendMoneyJobDetailsModel message :: " + sendMoneyJobDetailsModel.getMessage());
			
			List<SendMoneyJobSummaryModel> activeJobsList = schedulerService.getSendMoneyActiveJobs(sendMoneyToSingle.getSenderAuthId());
			Assert.assertNotNull(activeJobsList);
			LOGGER.info(" activeJobsList size :: " + activeJobsList.size());
			
			schedulerService.updateSendMoneyJob(scheduleProperties);
			
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage(),e);
			}
			List<String> list = schedulerService.jobNamesByGroupName(SchedulerGroupNames.SEND_MONEY_RECURRING);
			for(String jname: list){
				LOGGER.info("Job Name :: "+jname+" started on "+new Date());
				schedulerService.deleteJob(jname, SchedulerGroupNames.SEND_MONEY_RECURRING);
			}
			schedulerService.stopScheduler();
		}catch(WalletException ex){
			LOGGER.error(ex.getMessage(),ex);
			throw ex;
		}
	}
	
	@Test
	public void scheduleUnblockedUsersTest() throws WalletException{
		try{
			List<Object> data = DataPreparation.getMasterWalletsData();
	    	Iterator<Object> iter = data.iterator();
	    	while(iter.hasNext()){
	    		hibernateTemplate.save(iter.next());
	    	}
	    	
			CustomerDto dto = DataPreparation.getCustomerDto();		
			dto = customerService.newregistration(dto);
			CustomerDto dto2 = DataPreparation.getCustomerDto();
			dto2.setEmailId("kedarnathd@tarangtech.com");
			dto2.setPhoneCode("22");
			dto2.setPhoneNo("12344622");
			dto2 = customerService.newregistration(dto2);
	
			dto.setStatus(1L);
			dto.setStatusName("1");
			customerService.updateCustomerDetails(dto);
			dto2.setStatus(1L);
			dto2.setStatusName("1");
			customerService.updateCustomerDetails(dto2);
	
			/* Start job scheduling for recurring send money */
			schedulerService.startScheduler();
			Map<String, String> scheduleUnblockedProperties = DataPreparation.getJobUnblockUserBlockProperties();
			scheduleUnblockedProperties.put(JobConstants.AUTH_ID, dto2.getAuthenticationId().toString());
			
			schedulerService.deleteJobs(SchedulerGroupNames.UNBLOCKED_USERS);
			schedulerService.scheduleUnblockedUsersNewJob(scheduleUnblockedProperties);
			try {
					Thread.sleep(20*1000);
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage(),e);
			}
			List<String> list2 = schedulerService.jobNamesByGroupName(SchedulerGroupNames.UNBLOCKED_USERS);
			for(String jname: list2){
				LOGGER.info("Job Name :: "+jname+" started on "+new Date());
				schedulerService.deleteJob(jname, SchedulerGroupNames.UNBLOCKED_USERS);
			}
			schedulerService.stopScheduler();
		}catch(WalletException ex){
			LOGGER.error(ex.getMessage(),ex);
			throw ex;
		}
	}
	
}
