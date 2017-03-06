/**
 * 
 */
package com.tarang.ewallet.walletui.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.SendMoneyJobDetailsModel;
import com.tarang.ewallet.model.SendMoneyJobSummaryModel;
import com.tarang.ewallet.model.SendMoneyRecurring;
import com.tarang.ewallet.scheduler.business.SchedulerService;
import com.tarang.ewallet.scheduler.util.JobConstants;
import com.tarang.ewallet.transaction.business.SendMoneyService;
import com.tarang.ewallet.transaction.util.WalletTransactionStatus;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.walletui.controller.constants.Accounts;
import com.tarang.ewallet.walletui.controller.constants.Recurring;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


/**
 * @author vasanthar
 *
 */

@Controller
@RequestMapping("/scheduler")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SchedulerController implements AttributeConstants,AttributeValueConstants,Recurring{
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private SchedulerService schedulerService;
	
	@Autowired
	private SendMoneyService sendMoneyService;
	
	private static final Logger LOGGER = Logger.getLogger(SchedulerController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String listView(Map model, HttpServletRequest request,Locale locale) throws WalletException{
		LOGGER.debug( " Scheduler home page " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String url = WALLET_PATH_PREFIX  + "scheduler/list";
		model.put(URL_RECCURING_LIST, url);

		String message = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(message != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(message, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		
		message = (String)session.getAttribute(ERROR_MESSAGE);
		if(message != null){
			model.put(ERROR_MESSAGE, context.getMessage(message, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		
		return getView(session, SCHEDULER_LIST_VIEW);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<SendMoneyJobSummaryModel> listJobs(Map model, Locale locale, HttpServletRequest request) {
		
		String pgErrorCode = (String)request.getSession().getAttribute(ERROR_MESSAGE);
		HttpSession session = request.getSession();	
		JqgridResponse<SendMoneyJobSummaryModel> response = new JqgridResponse<SendMoneyJobSummaryModel>();
		if(pgErrorCode != null){
			model.put(ERROR_MESSAGE, pgErrorCode);
			request.getSession().removeAttribute(ERROR_MESSAGE);
		}
		List<SendMoneyJobSummaryModel> sendMoneyJobSummaryList = null;
		try{
			sendMoneyJobSummaryList = schedulerService.getSendMoneyActiveJobs((Long)session.getAttribute(AUTHENTICATION_ID));
			updateStatus(sendMoneyJobSummaryList, locale);
		} catch (Exception e) {
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error("accountRecords", e);
		}
		int listsize = 0; 
		if(null != sendMoneyJobSummaryList){
			listsize = sendMoneyJobSummaryList.size();	
		}
		
		response.setRows(sendMoneyJobSummaryList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = listsize/ps;
		if( listsize/ps*ps != listsize){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);

		return response;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String jobEditView(Map model, Long sendMoneyId, HttpServletRequest request, Locale locale) {
		LOGGER.debug( " Scheduler list page " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}

		Long userStatus = (Long)session.getAttribute(USER_STATUS);
		if(!UserStatusConstants.APPROVED.equals(userStatus)){
			return UIUtil.redirectPath(SCHEDULER_PATH);
		}	
		SendMoneyJobDetailsModel smjdModel = null;
		try {
			smjdModel = schedulerService.getSendMoneyJobDetais((Long)session.getAttribute(AUTHENTICATION_ID), sendMoneyId);
			SendMoneyDto sendMoneyDto = sendMoneyService.getSendMoneyById(smjdModel.getSendMoneyId());
			smjdModel.setAmount(sendMoneyDto.getActualAmount());
			smjdModel.setCurrency(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), sendMoneyDto.getActualCurrency()));
			smjdModel.setMessage(sendMoneyDto.getMessage());
			smjdModel.setPayeeEmailId(sendMoneyDto.getEmailId());
			smjdModel.setFrequency(sendMoneyDto.getFrequency());
			smjdModel.setTotalOccurences(Long.valueOf(sendMoneyDto.getTotalOccurences()));
			smjdModel.setRemainingOccurrences(smjdModel.getTotalOccurences() - smjdModel.getCompletedOccurrences());
			if(smjdModel.getFailureMessage()!= null){
				smjdModel.setFailureMessage(context.getMessage(smjdModel.getFailureMessage(), null, locale));
			}
		} catch (NullPointerException npe){
			LOGGER.error(npe.getMessage(), npe);
			model.put(ERROR_MESSAGE, context.getMessage(UNABLETOLOAD_RECURRING_INFO, null, locale));
			String url = WALLET_PATH_PREFIX + "scheduler/list";
			request.setAttribute("urlReccuringList", url);
			return getView(session, SCHEDULER_LIST_VIEW);
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
			return getView(session, SCHEDULER_EDIT_VIEW);
		}	
		model.put(DURATION_TIME, MasterDataUtil.getSimpleDataMap(
			    request.getSession().getServletContext(), 
			    (Long) request.getSession().getAttribute(LANGUAGE_ID), 
			    MasterDataUtil.TRX_TIME_PERIOD));
		smjdModel.setSendMoneyId(sendMoneyId);
		String newDateString = DateConvertion.dateToString(smjdModel.getNextOccurrenceDate());
		if(DateConvertion.dateToString(new Date()).equals(newDateString)){
			newDateString =  DateConvertion.dateToString(DateConvertion.futureDate(new Date(),1));
		}
		
		Map<Long, String> frequencyNamesList = MasterDataUtil.getSimpleDataMap(
			    request.getSession().getServletContext(), 
			    (Long) request.getSession().getAttribute(LANGUAGE_ID), 
			    MasterDataUtil.TRX_TIME_PERIOD);
		model.put(FREQUENCY_STR, frequencyNamesList.get(smjdModel.getFrequency()));
		model.put(AMOUNT_STR, UIUtil.getConvertedAmountInString(smjdModel.getAmount()));
		model.put(STARTDATE_STR, DateConvertion.dateToString(smjdModel.getStartDate()));
		model.put(ENDDATE_STR, DateConvertion.dateToString(smjdModel.getEndDate()));
		model.put(CREATIONDATE_STR, DateConvertion.dateToString(smjdModel.getCreationDate()));
		model.put(NEXTOCCURENCE_DATE_STR, DateConvertion.dateToString(smjdModel.getNextOccurrenceDate()));
		model.put(CURRENT_DATE, DateConvertion.dateToString(new Date()));
		model.put(NEWSTART_DATE, newDateString);
		model.put(NEWEND_DATE, DateConvertion.dateToString(smjdModel.getEndDate()));
		model.put(FREQUENCY, smjdModel.getFrequency());
		model.put(SENDMONEY_ID, smjdModel.getSendMoneyId());
		model.put(SendMoney_Job_Details, smjdModel);
		
		return getView(session, SCHEDULER_EDIT_VIEW);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String updateJob(Map model, String newStartDate, String newEndDate, Long frequency, Long sendMoneyId, Integer totalOccurences,
			HttpServletRequest request, Locale locale) throws WalletException{
		LOGGER.debug( " Scheduler edit page " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		
		try{
			SendMoneyJobDetailsModel smj = schedulerService.getSendMoneyJobDetais((Long)session.getAttribute(AUTHENTICATION_ID), sendMoneyId);
			updateSendMoneyRecurringDetails(sendMoneyId, smj.getCompletedOccurrences().intValue() + totalOccurences, frequency, newEndDate);
			Map<String, String> map = new HashMap<String, String>();
			map.put(JobConstants.SEND_MONEY_ID, sendMoneyId.toString());
			map.put(JobConstants.AUTH_ID, session.getAttribute(AUTHENTICATION_ID).toString());
			map.put(JobConstants.FROM_DATE, newStartDate);
			map.put(JobConstants.TO_DATE, newEndDate);
			map.put(JobConstants.SEND_MONEY_FREQUENCY, frequency.toString());
			schedulerService.updateSendMoneyJob(map);
			session.setAttribute(SUCCESS_MESSAGE, Common.RECURRING_UPDATED_SUCCESSFULLY);
			return UIUtil.redirectPath(SCHEDULER_PATH);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(Common.RECURRING_UPDATED_FAILURE, null, locale));
			SendMoneyJobDetailsModel smjdModel = schedulerService.getSendMoneyJobDetais((Long)session.getAttribute(AUTHENTICATION_ID),sendMoneyId);
			SendMoneyDto sendMoneyDto = sendMoneyService.getSendMoneyById(smjdModel.getSendMoneyId());
			smjdModel.setAmount(sendMoneyDto.getActualAmount());
			smjdModel.setCurrency(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), sendMoneyDto.getActualCurrency()));
			smjdModel.setMessage(sendMoneyDto.getMessage());
			smjdModel.setPayeeEmailId(sendMoneyDto.getEmailId());
			smjdModel.setFrequency(sendMoneyDto.getFrequency());
			smjdModel.setTotalOccurences(Long.valueOf(sendMoneyDto.getTotalOccurences()));
			smjdModel.setRemainingOccurrences(smjdModel.getTotalOccurences() - smjdModel.getCompletedOccurrences());
			model.put(NEWSTART_DATE, newStartDate);
			model.put(CURRENT_DATE, DateConvertion.dateToString(new Date()));
			model.put(NEWEND_DATE , newEndDate);
			model.put(FREQUENCY, frequency);
			model.put(SENDMONEY_ID, sendMoneyId);
			model.put(DURATION_TIME, MasterDataUtil.getSimpleDataMap(
				    request.getSession().getServletContext(), 
				    (Long) request.getSession().getAttribute(LANGUAGE_ID), 
				    MasterDataUtil.TRX_TIME_PERIOD));
			model.put(SendMoney_Job_Details, smjdModel);
			Map<Long, String> frequencyNamesList = MasterDataUtil.getSimpleDataMap(
				    request.getSession().getServletContext(), 
				    (Long) request.getSession().getAttribute(LANGUAGE_ID), 
				    MasterDataUtil.TRX_TIME_PERIOD);
			model.put(FREQUENCY_STR, frequencyNamesList.get(smjdModel.getFrequency()));
			model.put(AMOUNT_STR, UIUtil.getConvertedAmountInString(smjdModel.getAmount()));
			model.put(STARTDATE_STR, DateConvertion.dateToString(smjdModel.getStartDate()));
			model.put(ENDDATE_STR, DateConvertion.dateToString(smjdModel.getEndDate()));
			model.put(CREATIONDATE_STR, DateConvertion.dateToString(smjdModel.getCreationDate()));
			model.put(NEXTOCCURENCE_DATE_STR, DateConvertion.dateToString(smjdModel.getNextOccurrenceDate()));
			return getView(session, SCHEDULER_EDIT_VIEW);
		}		
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewJob(Map model, Long sendMoneyId,  HttpServletRequest request,Locale locale) throws WalletException{
		LOGGER.debug( " Scheduler view page " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		SendMoneyJobDetailsModel smjdModel = null;
		SendMoneyDto sendMoneyDto = null;
		try {
			smjdModel = schedulerService.getSendMoneyJobDetais((Long)session.getAttribute(AUTHENTICATION_ID), sendMoneyId);
			sendMoneyDto = sendMoneyService.getSendMoneyById(smjdModel.getSendMoneyId());
			smjdModel.setAmount(sendMoneyDto.getActualAmount());
			smjdModel.setCurrency(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), sendMoneyDto.getActualCurrency()));
			smjdModel.setMessage(sendMoneyDto.getMessage());
			smjdModel.setPayeeEmailId(sendMoneyDto.getEmailId());
			smjdModel.setFrequency(sendMoneyDto.getFrequency());
			smjdModel.setTotalOccurences(Long.valueOf(sendMoneyDto.getTotalOccurences()));
			smjdModel.setRemainingOccurrences(smjdModel.getTotalOccurences() - smjdModel.getCompletedOccurrences());
			if(smjdModel.getFailureMessage()!= null){
				smjdModel.setFailureMessage(context.getMessage(smjdModel.getFailureMessage(), null, locale));
			}
		} catch (NullPointerException npe){
			LOGGER.error(npe.getMessage(), npe);
			model.put(ERROR_MESSAGE, context.getMessage(UNABLETOLOAD_RECURRING_INFO, null, locale));
			String url = WALLET_PATH_PREFIX + "scheduler/list";
			request.setAttribute(URL_RECCURING_LIST, url);
			return getView(session, SCHEDULER_LIST_VIEW);
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
			return getView(session, SCHEDULER_EDIT_VIEW);
		}	
		model.put(SendMoney_Job_Details, smjdModel);
		Map<Long, String> frequencyNamesList = MasterDataUtil.getSimpleDataMap(
			    request.getSession().getServletContext(), 
			    (Long) request.getSession().getAttribute(LANGUAGE_ID), 
			    MasterDataUtil.TRX_TIME_PERIOD);
		model.put(FREQUENCY_STR, frequencyNamesList.get(smjdModel.getFrequency()));
		model.put(AMOUNT_STR, UIUtil.getConvertedAmountInString(smjdModel.getAmount()));
		model.put(STARTDATE_STR, DateConvertion.dateToString(smjdModel.getStartDate()));
		model.put(ENDDATE_STR, DateConvertion.dateToString(smjdModel.getEndDate()));
		model.put(CREATIONDATE_STR, DateConvertion.dateToString(smjdModel.getCreationDate()));
		model.put(NEXTOCCURENCE_DATE_STR, DateConvertion.dateToString(smjdModel.getNextOccurrenceDate()));
		return getView(session, SCHEDULER_VIEW);
	}

	@RequestMapping(value = "/deleterec", method = RequestMethod.GET)
	public String deleteJob(Map model, Long sendMoneyId, HttpServletRequest request) throws WalletException{
		LOGGER.debug( " Scheduler deleteJob" );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		
		Long userStatus = (Long)session.getAttribute(USER_STATUS);
		if(!UserStatusConstants.APPROVED.equals(userStatus)){
			return UIUtil.redirectPath(SCHEDULER_PATH);
		}	
		//add success or failure message and redirect to default list page
		try{
			SendMoneyDto dto = sendMoneyService.getSendMoneyById(sendMoneyId);
			SendMoneyJobDetailsModel sendMoneyJobDetailsModel = schedulerService.getSendMoneyJobDetais((Long)session.getAttribute(AUTHENTICATION_ID), sendMoneyId);
			Long completedOccurrences = sendMoneyJobDetailsModel.getCompletedOccurrences();
			// updating SendMoneyRecurring
			dto.setToDate(new Date());
			dto.setTotalOccurences(completedOccurrences.intValue());
			SendMoneyRecurring  recurring = new SendMoneyRecurring();
			recurring.setSendMoneyId(dto.getId());
			recurring.setToDate(new Date());
			recurring.setFrequency(dto.getFrequency());
			recurring.setTotalOccurences(completedOccurrences.intValue());
			sendMoneyService.updateSendMoneyRecurring(recurring);
			
			Boolean deleteRec = schedulerService.deleteSendMoneyJob((Long)session.getAttribute(AUTHENTICATION_ID), sendMoneyId);
			if(deleteRec){
				session.setAttribute(SUCCESS_MESSAGE, Common.RECURRING_DELETED_SUCCESSFULLY);
			} else {
				session.setAttribute(ERROR_MESSAGE, Common.RECURRING_DELETED_FAILURE);
			}
			
		} catch(Exception e){
			LOGGER.error(e.getMessage() , e);
			session.setAttribute(ERROR_MESSAGE, Common.RECURRING_DELETED_FAILURE);
		}
		return UIUtil.redirectPath(SCHEDULER_PATH); 
	}	
	
	private String getView(HttpSession session, String view){
		String uview = view;
		String uType = (String)session.getAttribute(USER_TYPE);
		
		if(CUSTOMER_USER_TYPE.equals(uType)){
			uview = Accounts.CUSTOMER_VIEW + view;
		} else if(MERCHANT_USER_TYPE.equals(uType)){
			uview = Accounts.MERCHANT_VIEW + view;
		}
		return uview;
	}

	private void updateStatus(List<SendMoneyJobSummaryModel> sendMoneyJobSummaryList, Locale locale){
		  String statusKey = UNKNOWN_KEY;
		  for(SendMoneyJobSummaryModel sendMoneyJSModel : sendMoneyJobSummaryList){
			  Long status = sendMoneyJSModel.getRecentFiredStatus();
			  if(sendMoneyJSModel.getRecentFiredStatus() != null){
				  if(status.equals(WalletTransactionStatus.NOT_STARTED)){
					  statusKey = NOT_STARTED_KEY;
				  } else if(status.equals(WalletTransactionStatus.SUCCESS)){
					  statusKey = SUCCESS_KEY;
				  } else if(status.equals(WalletTransactionStatus.FAILED)){
					  statusKey = FAIL_KEY;
				  }
			  }
			  sendMoneyJSModel.setSendMoneyTxnStatus(context.getMessage(statusKey, null, locale));
		  }
	}
	
	private void updateSendMoneyRecurringDetails(Long sendMoneyId, Integer totalOccurences, Long frequency, String endDate) throws WalletException{
		SendMoneyRecurring sendMoneyRecurring = new SendMoneyRecurring();
		sendMoneyRecurring.setSendMoneyId(sendMoneyId);
		sendMoneyRecurring.setFrequency(frequency);
		sendMoneyRecurring.setTotalOccurences(totalOccurences);
		sendMoneyRecurring.setToDate(DateConvertion.stirngToDate(endDate));
		sendMoneyService.updateSendMoneyRecurring(sendMoneyRecurring);
	}
	
}
