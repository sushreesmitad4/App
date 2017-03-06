/**
 * 
 */
package com.tarang.ewallet.walletui.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tarang.ewallet.masterdata.business.MasterDataService;
import com.tarang.ewallet.scheduler.business.WalletJobSchedulerService;
import com.tarang.ewallet.scheduler.business.SchedulerService;
import com.tarang.ewallet.walletui.util.MasterDataConstants;

/**
 * @author  : prasadj
 * @date    : Oct 10, 2012
 * @time    : 6:43:14 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class MasterDataContextListener implements ServletContextListener, MasterDataConstants {

	private static final Logger LOGGER = Logger.getLogger(MasterDataContextListener.class);
	
	//@Override
	public void contextDestroyed(ServletContextEvent event) {
		LOGGER.debug("context destroyed");
		try{
			WebApplicationContext servletContext =  WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());  
			SchedulerService schedulerService = (SchedulerService) servletContext.getBean("schedulerService");
			schedulerService.stopScheduler();
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
	
	}

	//@Override
	public void contextInitialized(ServletContextEvent event) {
		LOGGER.debug("context initialized");

		WebApplicationContext servletContext =  WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());  

		try{
			SchedulerService schedulerService = (SchedulerService) servletContext.getBean("schedulerService");
			schedulerService.startScheduler();
			WalletJobSchedulerService walletJobSchedulerService = (WalletJobSchedulerService) servletContext.getBean("walletJobSchedulerService");
			walletJobSchedulerService.disputeForceWithdrawalService();
			walletJobSchedulerService.reconsilationService();
			walletJobSchedulerService.pgSettlementService();
			walletJobSchedulerService.cancelNonRegisterWalletTxnsService();
			walletJobSchedulerService.accountCloserService();
			walletJobSchedulerService.emailService();
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		
		MasterDataService masterDataService = (MasterDataService) servletContext.getBean("masterDataService"); 

		event.getServletContext().setAttribute(MD_LANGUAGES, masterDataService.getLanguages());
		event.getServletContext().setAttribute(MD_COUNTRIES, masterDataService.getCountries());
		event.getServletContext().setAttribute(MD_CURRENCIES, masterDataService.getCurrencies());
		event.getServletContext().setAttribute(MD_CURRENCY_CODES, masterDataService.getCurrencyCodes());
		event.getServletContext().setAttribute(MD_CARD_TYPES, masterDataService.getCardTypes());
		event.getServletContext().setAttribute(MD_FREQUENCIES, masterDataService.getFrequencies());
		event.getServletContext().setAttribute(MD_USER_TYPES, masterDataService.getUserTypes());
		event.getServletContext().setAttribute(MD_CUSTOMER_TRANSACTION_TYPES, masterDataService.getCTransactionTypes());
		event.getServletContext().setAttribute(MD_MERCHANT_TRANSACTION_TYPES, masterDataService.getMTransactionTypes());
		event.getServletContext().setAttribute(MD_ADMIN_TRANSACTION_TYPES, masterDataService.getATransactionTypes());
		event.getServletContext().setAttribute(MD_MERCHANT_OWNER_TYPES, masterDataService.getMerchantOwnerTypes());
		event.getServletContext().setAttribute(MD_MERCHANT_BUSINESS_CATEGORIES, masterDataService.getMerchantBusinessCategories());
		event.getServletContext().setAttribute(MD_MERCHANT_AVERAGE_TX_AMOUNTS, masterDataService.getMerchantAvgTxAmounts());
		event.getServletContext().setAttribute(MD_MERCHANT_HIGHEST_MONTHLY_VOLUMES, masterDataService.getMerchantHighestMonthlyVolumes());
		event.getServletContext().setAttribute(MD_MERCHANT_PERCENTAGE_ANUAL_REVENUES, masterDataService.getMerchantPercentageAnualRevenues());
		event.getServletContext().setAttribute(MD_USER_STATUSES, masterDataService.getUserStatuses());
		event.getServletContext().setAttribute(HINT_QUESTIONS, masterDataService.getHintQuestions());
		event.getServletContext().setAttribute(MD_MERCHANT_SUB_CATEGORIES, masterDataService.getMerchantBusinessSubCategories());
		event.getServletContext().setAttribute(REGIONS, masterDataService.getRegions());
		event.getServletContext().setAttribute(TITLES, masterDataService.getTitles());
		event.getServletContext().setAttribute(MONEY_ACCOUNT_STATUSES, masterDataService.getMoneyAccountStatuses());
		event.getServletContext().setAttribute(MONEY_ACCOUNT_DELETE_STATUSES, masterDataService.getMoneyAccountDeleteStatuses());
		event.getServletContext().setAttribute(BANK_ACCOUNT_TYPES, masterDataService.getBankAccountTypes());
		event.getServletContext().setAttribute(FEE_TYPES, masterDataService.getFeeTypes());
		event.getServletContext().setAttribute(FEE_OPERATION_TYPES, masterDataService.getFeeOperationTypes());
		event.getServletContext().setAttribute(FEE_SERVICE_TYPES, masterDataService.getFeeServiceTypes());
		event.getServletContext().setAttribute(FEE_PAYING_ENTITIES, masterDataService.getFeePayingEntities());
		event.getServletContext().setAttribute(FEE_TIME_FREQUENCY, masterDataService.getFeeTimeFrequency());
		event.getServletContext().setAttribute(TRX_TIME_PERIOD, masterDataService.getTrxTimePeriod());
		event.getServletContext().setAttribute(TRX_STATUS, masterDataService.getTransactionStatusName());
		event.getServletContext().setAttribute(RECEIVE_MONEY_STATUS, masterDataService.getReceiveMoneyStatusName());
		event.getServletContext().setAttribute(TRX_STATUS, masterDataService.getTransactionStatusName());
		event.getServletContext().setAttribute(RECEIVE_MONEY_STATUS, masterDataService.getReceiveMoneyStatusName());
		event.getServletContext().setAttribute(DESTINATION_TYPE, masterDataService.getDestinationType());
		event.getServletContext().setAttribute(REPORT_TYPES, masterDataService.getReportTypes());
		event.getServletContext().setAttribute(DISPUTE_TYPES, masterDataService.getDisputeType());
		event.getServletContext().setAttribute(ACCOUNT_CLOSURE_STATUS, masterDataService.getAccountClosureStatus());
		event.getServletContext().setAttribute(DISPUTE_STATUS, masterDataService.getDisputeStatus());
		event.getServletContext().setAttribute(PAYMENT_MESSAGES, masterDataService.getPaymentMessages());
		event.getServletContext().setAttribute(QUERY_OR_FEEDBACK, masterDataService.getQueryOrFeedback());
		event.getServletContext().setAttribute(TYPE_OF_REQUEST, masterDataService.getChannelType());
		event.getServletContext().setAttribute(MOBILE_WALLET_ERROR_CODE, masterDataService.getErrorCodes());
		event.getServletContext().setAttribute(PG_ERROR_CODE, masterDataService.getPGCodes());

	}
}
