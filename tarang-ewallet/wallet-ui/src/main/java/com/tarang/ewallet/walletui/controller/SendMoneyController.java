package com.tarang.ewallet.walletui.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.SelfTransferDto;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.scheduler.business.SchedulerService;
import com.tarang.ewallet.scheduler.util.JobConstants;
import com.tarang.ewallet.scheduler.util.SchedulerGroupNames;
import com.tarang.ewallet.transaction.business.SendMoneyService;
import com.tarang.ewallet.transaction.util.CurrencyConversion;
import com.tarang.ewallet.transaction.util.DestinationTypes;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.CSVFileConstants;
import com.tarang.ewallet.walletui.controller.constants.Login;
import com.tarang.ewallet.walletui.controller.constants.SendMoney;
import com.tarang.ewallet.walletui.form.SelfTransferForm;
import com.tarang.ewallet.walletui.form.SendMoneyForm;
import com.tarang.ewallet.walletui.form.SendMoneyMultipleForm;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.SendMoneyHelper;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.SelfTransferValidator;
import com.tarang.ewallet.walletui.validator.SendMoneyMultipleValidator;
import com.tarang.ewallet.walletui.validator.SendMoneySingleValidator;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


@Controller
@SuppressWarnings({ "unchecked", "rawtypes" })
@RequestMapping("/sendmoney")
public class SendMoneyController implements SendMoney, AttributeConstants, GlobalLitterals, AttributeValueConstants, CSVFileConstants {
	
	private static final Logger LOGGER = Logger.getLogger(SendMoneyController.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private SendMoneyService sendMoneyService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private FeeMgmtService feeMgmtService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SchedulerService schedulerService;
	
	@RequestMapping(value = "/tosingle", method = RequestMethod.GET)
	public String transferMoney(Map model, HttpServletRequest request,
			@Valid SendMoneyForm sendMoneyForm, Locale locale) {
		LOGGER.debug(" transferMoney :: GET ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String sucMsg = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(sucMsg != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(sucMsg, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		String mode = (String)request.getParameter("mode");
		
		if(mode != null && CANCEL.equals(mode)){
			if(session.getAttribute(USER_TYPE).equals(GlobalLitterals.CUSTOMER_USER_TYPE)){
				return UIUtil.redirectPath(CUSTOMER_PATH);
			} else if(session.getAttribute(USER_TYPE).equals(GlobalLitterals.MERCHANT_USER_TYPE)){
				return UIUtil.redirectPath(MERCHANT_PATH);
			}
		}
		sendMoneyForm.setRecurring(false);
		model.put(SEND_MONEY_FORM_NAME, sendMoneyForm);
		UIUtil.populateSendMoneyMapValues(request, model);
		return getView(session, SEND_MONEY_TO_SINGLE_VIEW);
	}

	@RequestMapping(value = "/tosingle", method = RequestMethod.POST)
	public String transferMoneySend( HttpServletRequest request, Map model, Locale locale,
			 SendMoneyForm sendMoneyForm, BindingResult result) {
		LOGGER.debug(" transferMoneySend :: Post ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		try {
			SendMoneySingleValidator sendMoneySingleValidator = new SendMoneySingleValidator();
			sendMoneySingleValidator.validate(sendMoneyForm, result);
			if(sendMoneyForm.getEmailId().equals(session.getAttribute(USER_ID))){
				String uType = (String)session.getAttribute(USER_TYPE);
				if(uType.equals(CUSTOMER_USER_TYPE) 
						&& sendMoneyForm.getDestinationType().equals(DestinationTypes.REGISTERED_PERSON)){
					result.rejectValue(Common.EMAIL_VAR, NOT_ALLOWED_EMAIL_ID);
				} else if(uType.equals(MERCHANT_USER_TYPE) 
						&& sendMoneyForm.getDestinationType().equals(DestinationTypes.MERCHANT)){
					result.rejectValue(Common.EMAIL_VAR, NOT_ALLOWED_EMAIL_ID);
				}
			}
			if(sendMoneyForm.getRecurring() && !sendMoneyForm.getUserJobName().equals(EMPTY_STRING)){
				Boolean isJobNameExist = sendMoneyService.isJobNameExist((Long) session.getAttribute(AUTHENTICATION_ID), 
						sendMoneyForm.getUserJobName());
				if(isJobNameExist){
					result.rejectValue(Common.USER_JOBNAME_VAR, USER_JOB_NAME_EXIST);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		if(result.hasErrors()){
			UIUtil.populateSendMoneyMapValues(request, model);
			return getView(session, SEND_MONEY_TO_SINGLE_VIEW);
		}
		SendMoneyHelper.getRegisteredOrNotRegisteredOrMerchant( sendMoneyForm, result, commonService);
		if(result.hasErrors()){
			UIUtil.populateSendMoneyMapValues(request, model);
			return getView(session, SEND_MONEY_TO_SINGLE_VIEW);
		}
		String ut = (String)session.getAttribute(USER_TYPE);
		try {
			SendMoneyDto sendMoneyDto = constructSendMoneyDto(sendMoneyForm, request, ut, (String)session.getAttribute(USER_ID));
			
			if( !validateThresholdLimit(sendMoneyDto) ){
				UIUtil.populateSendMoneyMapValues(request, model);
				model.put(ERROR_MESSAGE, context.getMessage(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT, null, locale));
				return getView(session,SEND_MONEY_TO_SINGLE_VIEW);
			}
			sendMoneyForm.setRequestedAmount(UIUtil.getConvertedAmountInString(Double.parseDouble(sendMoneyForm.getRequestedAmount())));
			UserWallet userWallet = commonService.getUserWallet(sendMoneyDto.getSenderAuthId(), sendMoneyDto.getRequestedCurrency());		
			Double deductions = 0.0;
			Double reqAmount = sendMoneyDto.getRequestedAmount();
			if(userWallet != null){
				deductions = feeMgmtService.calcuateDeductions(reqAmount, sendMoneyDto.getCountryId(), 
						sendMoneyDto.getRequestedCurrency(), sendMoneyDto.getTransactionType(), Boolean.FALSE);
			}
			if(!sendMoneyForm.getRecurring()){
				Boolean negativeBalance = false;
				if(userWallet == null || userWallet.getAmount() < reqAmount + deductions ){
					negativeBalance = true;
				}
				if(negativeBalance){
					throw new WalletException(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE);
				}			
			}
			sendMoneyForm.setActualCurrency(sendMoneyForm.getRequestedCurrency());
			sendMoneyForm.setActualAmount(sendMoneyForm.getRequestedAmount());
			
		    model.put(TOTAL_AMOUNT_FEE_TAX, UIUtil.getConvertedAmountInString(reqAmount + deductions));
		} catch (WalletException e) {
			UIUtil.populateSendMoneyMapValues(request, model);
			LOGGER.error(e.getMessage(), e);
			String errorMessage = Common.UNABLE_TO_PROCESS_MSG; 
			if(e.getMessage().contains(C_EMAIL_NOT_REGISTERED_ERR_MSG)){
				errorMessage = C_EMAIL_NOT_REGISTERED_ERR_MSG;
			} else if(e.getMessage().contains(M_EMAIL_NOT_REGISTERED_ERR_MSG)){
				errorMessage = M_EMAIL_NOT_REGISTERED_ERR_MSG;
			} else if(e.getMessage().contains(USER_NOT_HAVE_BALANCE_ERR_MSG)){
				errorMessage = USER_NOT_HAVE_BALANCE_ERR_MSG;
			} else {
				errorMessage = WalletTransactionConstants.TRANSACTION_FAILED;
			}
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			return getView(session,SEND_MONEY_TO_SINGLE_VIEW);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			UIUtil.populateSendMoneyMapValues(request, model);
			model.put(ERROR_MESSAGE, UNKNOW_ERROR);
			return getView(session, SEND_MONEY_TO_SINGLE_VIEW);
		}
		String usertype = session.getAttribute(USER_TYPE).toString();
		
		request.setAttribute(USER_TYPE, usertype);
		model.put(REQUESTED_CURRENCY_CODE, MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(sendMoneyForm.getRequestedCurrency()));
		model.put(ACTUAL_CURRENCY_CODE, MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(sendMoneyForm.getActualCurrency()));
		Map<Long,String> userTypes = MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.MD_USER_TYPES);
		Map<Long, String> destinationTypes = new HashMap<Long, String>();
		for(Map.Entry<Long, String> entry : userTypes.entrySet()){
			if(!(entry.getKey().equals(ADMIN_USER_TYPE_ID))){
				destinationTypes.put(entry.getKey(), entry.getValue());
			}
		}
		model.put(DESTINATION_TYPE_NAME, destinationTypes.get(sendMoneyForm.getDestinationType())); // here we remove "admin" from userTypes list to set this list as detination types list
		Map<Long, String> frequencyNamesList = MasterDataUtil.getSimpleDataMap(
			    request.getSession().getServletContext(), 
			    (Long) request.getSession().getAttribute(LANGUAGE_ID), 
			    MasterDataUtil.TRX_TIME_PERIOD);
		
		model.put(FREEQUENCY_NAME, frequencyNamesList.get(sendMoneyForm.getFrequency()));
		model.put(SEND_MONEY_FORM_NAME, sendMoneyForm);
		return getView(session, SEND_MONEY_TO_SINGLE_CONFIRMATION);
	}
	
	@RequestMapping(value = "/tosingleconfirm", method = RequestMethod.POST)
	public String transferMoneyConfirm( HttpServletRequest request,Map model,Locale locale,
		 SendMoneyForm sendMoneyForm, BindingResult result) {
		LOGGER.debug(" transferMoneyConfirm :: Post ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String mode = (String) request.getParameter("mode");
		if(mode != null){
			if(CANCEL.equals(mode)){
				if(session.getAttribute(USER_TYPE).equals(GlobalLitterals.CUSTOMER_USER_TYPE)){
					return UIUtil.redirectPath(CUSTOMER_PATH);
				} else if(session.getAttribute(USER_TYPE).equals(GlobalLitterals.MERCHANT_USER_TYPE)){
					return UIUtil.redirectPath(MERCHANT_PATH);
				}
				 
			}
			if("back".equals(mode)){
				UIUtil.populateSendMoneyMapValues(request, model);
				model.put(SEND_MONEY_FORM_NAME, sendMoneyForm);
				return getView(session,SEND_MONEY_TO_SINGLE_VIEW);	
			}
		}
		
		model.put(SEND_MONEY_FORM_NAME, sendMoneyForm);
	    UIUtil.populateSendMoneyMapValues(request, model);
		try{
			SendMoneyDto sendMoneyDto = constructSendMoneyDto(sendMoneyForm, request,
					(String)session.getAttribute(USER_TYPE), (String)session.getAttribute(USER_ID));
			if(sendMoneyDto.getMessage().equals(EMPTY_STRING)){
				sendMoneyDto.setMessage(context.getMessage(PAYMENT_SENT_DEFAULT_MSG, null, locale));
			}
			sendMoneyDto = sendMoneyService.createSendMoney(sendMoneyDto);
			//Schedule Job For Recurring Send Money
			if(sendMoneyDto.getRecurring()){
				Map<String, String> jobProperties = new HashMap<String, String>();
				jobProperties.put(JobConstants.GROUP_NAME, SchedulerGroupNames.SEND_MONEY_RECURRING);
				jobProperties.put(JobConstants.USER_JOB_NAME, sendMoneyDto.getUserJobName());
				jobProperties.put(JobConstants.AUTH_ID, session.getAttribute(AUTHENTICATION_ID).toString());
				jobProperties.put(JobConstants.SEND_MONEY_ID, sendMoneyDto.getId().toString());
				jobProperties.put(JobConstants.FROM_DATE, sendMoneyForm.getFromDate());
				jobProperties.put(JobConstants.TO_DATE, sendMoneyForm.getToDate());
				jobProperties.put(JobConstants.SEND_MONEY_FREQUENCY, sendMoneyDto.getFrequency().toString());
			
				schedulerService.scheduleSendMoneyNewJob(jobProperties);
			}
			session.setAttribute(SUCCESS_MESSAGE, WalletTransactionConstants.TRANSACTION_SUCCESS);
			return UIUtil.redirectPath(SEND_MONEY_TO_SINGLE_SUC_PATH);
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
			String errorMessage = Common.UNABLE_TO_PROCESS_MSG;
			if(e.getMessage().contains(C_EMAIL_NOT_REGISTERED_ERR_MSG)){
				errorMessage = C_EMAIL_NOT_REGISTERED_ERR_MSG;
			} else if(e.getMessage().contains(M_EMAIL_NOT_REGISTERED_ERR_MSG)){
				errorMessage = M_EMAIL_NOT_REGISTERED_ERR_MSG;
			} else if(e.getMessage().contains(USER_NOT_HAVE_BALANCE_ERR_MSG)){
				errorMessage = USER_NOT_HAVE_BALANCE_ERR_MSG;
			} else if(e.getMessage().contains(WalletTransactionConstants.USER_DELETED)){
				errorMessage = WalletTransactionConstants.USER_DELETED;
			} else if(e.getMessage().contains(WalletTransactionConstants.USER_LOCKED)){
				errorMessage = WalletTransactionConstants.USER_LOCKED;
			} else if(e.getMessage().contains(WalletTransactionConstants.USER_REJECTED)){
				errorMessage = WalletTransactionConstants.USER_REJECTED;
			} else if(e.getMessage().contains(WalletTransactionConstants.USER_INACTIVE)){
				errorMessage = WalletTransactionConstants.USER_INACTIVE;
			} else if(e.getMessage().contains(WalletTransactionConstants.USER_NOT_APPROVED)){
				errorMessage = WalletTransactionConstants.USER_NOT_APPROVED;
			} else {
				errorMessage = WalletTransactionConstants.TRANSACTION_FAILED;
			}
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			model.put(ERROR_MESSAGE, UNKNOW_ERROR);
			return getView(session, SEND_MONEY_TO_SINGLE_VIEW);
		}

		return getView(session, SEND_MONEY_TO_SINGLE_VIEW);
	}
	
	@RequestMapping(value = "/tomultiple", method = RequestMethod.GET)
	public String transferMoneyMultiple(Map model, HttpServletRequest request, 
			@Valid SendMoneyMultipleForm sendMoneyMultipleForm, Locale locale) {
		LOGGER.debug( " transferMoneyMultiple :: GET " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String sucMsg = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(sucMsg != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(sucMsg, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		UIUtil.populateSendMoneyMultipleMapValues(request, model);
		model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, sendMoneyMultipleForm);
		return SEND_MONEY_TO_MULTIPLE_VIEW;
	}

	@RequestMapping(value = "/tomultiple", method = RequestMethod.POST)
	public String transferMoneyMultipleConfirm(Map model, HttpServletRequest request, 
			@Valid SendMoneyMultipleForm sendMoneyMultipleForm, BindingResult result, Locale locale) {
		
		LOGGER.debug( " transferMoneyMultipleConfirm :: Post " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		SendMoneyMultipleValidator sendMoneyMultipleValidator =	new SendMoneyMultipleValidator(commonService);
		sendMoneyMultipleValidator.validate(sendMoneyMultipleForm, result);
		UIUtil.populateSendMoneyMultipleMapValues(request, model);
		sendMoneyMultipleForm.setSlabsize(sendMoneyMultipleForm.getEmailId().length);
		String viewPage = SEND_MONEY_TO_MULTIPLE_VIEW;
		
		String uId = (String)session.getAttribute(USER_ID);
		String uType = (String)session.getAttribute(USER_TYPE);
		for(int i = 0; i < sendMoneyMultipleForm.getEmailId().length; i++){
			if(sendMoneyMultipleForm.getEmailId()[i].equals(uId)){
				if(uType.equals(CUSTOMER_USER_TYPE) 
						&& sendMoneyMultipleForm.getDestinationType()[i].equals(DestinationTypes.REGISTERED_PERSON)){
					result.rejectValue("emailId[" + i + "]", NOT_ALLOWED_EMAIL_ID);
				} else if(uType.equals(MERCHANT_USER_TYPE) 
						&& sendMoneyMultipleForm.getDestinationType()[i].equals(DestinationTypes.MERCHANT)){
					result.rejectValue("emailId[" + i + "]", NOT_ALLOWED_EMAIL_ID);
				}
			}
		}
		if(result.hasErrors()){
			return viewPage;
		}
	
		model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, sendMoneyMultipleForm);
		try{
			List<SendMoneyDto> listofTrans = constructSendMoneyMultipleDtos(request, sendMoneyMultipleForm, 
					(String)session.getAttribute(USER_TYPE), (String)session.getAttribute(USER_ID));
			Map<Long, Double> amounts = new HashMap<Long, Double>();
			Double amount = null;
			for(int i = 0; i < listofTrans.size(); i++) {

				if(!validateThresholdLimit(listofTrans.get(i))){
					UIUtil.populateSendMoneyMapValues(request, model);
					model.put(ERROR_MESSAGE, context.getMessage(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT, null, locale));
					return viewPage;
				}
				
				SendMoneyDto sendMoneyDto = listofTrans.get(i);
				amount = amounts.get(sendMoneyDto.getRequestedCurrency());
				if(amount == null){
					amount = 0.0;
				}
				Double reqAmount = sendMoneyDto.getRequestedAmount();
				Double deductions = feeMgmtService.calcuateDeductions(reqAmount, sendMoneyDto.getCountryId(), 
							sendMoneyDto.getRequestedCurrency(), sendMoneyDto.getTransactionType(), Boolean.FALSE);
				amount = amount + reqAmount + deductions;
				amounts.put(sendMoneyDto.getRequestedCurrency(), amount);
				
			}
			Map<Long, UserWallet> wallets = CommonUtil.getWalletMap(commonService.getUserWallets(listofTrans.get(0).getSenderAuthId()));
			if(amounts.size() > wallets.size()){
				UIUtil.populateSendMoneyMapValues(request, model);
				model.put(ERROR_MESSAGE, context.getMessage(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE, null, locale));
				model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, sendMoneyMultipleForm);
				return viewPage;
				
			}
			
			for(Long currency: amounts.keySet()){
				UserWallet uw = wallets.get(currency);
				if(uw == null || uw.getAmount() < amounts.get(currency)) {
					UIUtil.populateSendMoneyMapValues(request, model);
					model.put(ERROR_MESSAGE, context.getMessage(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE, null, locale));
					model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, sendMoneyMultipleForm);
					return viewPage;	
				}
			}
			sendMoneyMultipleForm.setSlabsize(0);
			model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, new SendMoneyMultipleForm());
			sendMoneyService.createSendMoneyToMultiple(listofTrans);
			session.setAttribute(SUCCESS_MESSAGE, WalletTransactionConstants.TRANSACTION_SUCCESS);
			return UIUtil.redirectPath(SEND_MONEY_TO_MULTIPLE_SUC_PATH);
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
			String errorMessage = Common.UNABLE_TO_PROCESS_MSG;
			if(e.getMessage().contains(C_EMAIL_NOT_REGISTERED_ERR_MSG)){
				errorMessage = C_EMAIL_NOT_REGISTERED_ERR_MSG;
			} else if(e.getMessage().contains(M_EMAIL_NOT_REGISTERED_ERR_MSG)){
				errorMessage = M_EMAIL_NOT_REGISTERED_ERR_MSG;
			} else if(e.getMessage().contains(USER_NOT_HAVE_BALANCE_ERR_MSG)){
				errorMessage = USER_NOT_HAVE_BALANCE_ERR_MSG;
			} else{
				errorMessage = WalletTransactionConstants.TRANSACTION_FAILED;
			}
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			return viewPage;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(WalletTransactionConstants.TRANSACTION_FAILED, null, locale));
			return viewPage;
		}
	}
	
	@RequestMapping(value = "/fileupload", method = RequestMethod.GET)
	public String fileUploadPageLoad(Map model, HttpServletRequest request, 
			 Locale locale) {
		LOGGER.debug( " fileUploadPageLoad :: GET " );
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		String sucMsg = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(sucMsg != null){
			model.put(SUCCESS_MESSAGE, sucMsg);
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		UIUtil.populateSendMoneyMultipleMapValues(request, model);
		model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, new SendMoneyMultipleForm());
		return SEND_MONEY_TO_MULTIPLE_FILE_UPLOAD_VIEW;
	}

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public String fileUploadConfirm(Map model, HttpServletRequest request, 
			 @Valid SendMoneyMultipleForm sendMoneyMultipleForm, Locale locale) {
		LOGGER.debug( " fileUploadConfirm :: Post " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		MultipartFile strFile = sendMoneyMultipleForm.getFileData();
		// validation for select text/csv file only
		String file1 = strFile.getOriginalFilename();
		String[] compareFileName = file1.split("\\.");
		int length = compareFileName.length;
		if (length == 0) {
			model.put(ERROR_MESSAGE, context.getMessage(SEND_MONEY_FILE_EMPTY, null, locale));
			return SEND_MONEY_TO_MULTIPLE_FILE_UPLOAD_VIEW;
		} else {
		    String originalFile = compareFileName[length - 1];
		    if (!(originalFile.equalsIgnoreCase(SEND_MONEY_FILE_TXT)  || originalFile.equalsIgnoreCase(SEND_MONEY_FILE_CSV)  )) {
				model.put(ERROR_MESSAGE, context.getMessage(SEND_MONEY_FILE_FORMATE, null, locale));
				return SEND_MONEY_TO_MULTIPLE_FILE_UPLOAD_VIEW;
		    }
		}
		List<SendMoneyDto>  list = getCsvDetails(request, strFile, (String)session.getAttribute(USER_TYPE), 
				(String)session.getAttribute(USER_ID));
		int fileMaxLimit = utilService.getSendMoneyFileUploadRecordLimit();
		if (list.isEmpty()) {
			model.put(ERROR_MESSAGE, context.getMessage(SEND_MONEY_FILE_EMPTY, null, locale));
			return SEND_MONEY_TO_MULTIPLE_FILE_UPLOAD_VIEW;
		}
		if (list.size() > fileMaxLimit) {
			model.put(ERROR_MESSAGE, context.getMessage(SEND_MONEY_FILE_RECORD_LIMIT, new Object[] { fileMaxLimit }, locale));
			return SEND_MONEY_TO_MULTIPLE_FILE_UPLOAD_VIEW;
		}
		
		try {
			List<SendMoneyDto> listofTrans = getValidRecordsForPost(list);
			if(listofTrans.isEmpty()){
				return prepareErrorMessage(model, locale);	
			}
			Map<Long, Double> amounts = new HashMap<Long, Double>();
			Double amount = null;
			
			for(int i = 0; i < listofTrans.size(); i++) {
				if(!validateThresholdLimit(listofTrans.get(i))){
					listofTrans.get(i).setStatus(true);
				}
			}
			listofTrans = getValidRecordsForPost(listofTrans);
			if(listofTrans.isEmpty()){
				return prepareErrorMessage(model, locale);	
			}
			for(int i = 0; i < listofTrans.size(); i++) {
				SendMoneyDto sendMoneyDto = listofTrans.get(i);
				amount = amounts.get(sendMoneyDto.getRequestedCurrency());
				if(amount == null){
					amount = 0.0;
				}
				Double reqAmount = sendMoneyDto.getRequestedAmount();
				Double deductions = feeMgmtService.calcuateDeductions(reqAmount, sendMoneyDto.getCountryId(), 
							sendMoneyDto.getRequestedCurrency(), sendMoneyDto.getTransactionType(), Boolean.FALSE);
				amount = amount + reqAmount + deductions;
				amounts.put(sendMoneyDto.getRequestedCurrency(), amount);
				
			}
			Map<Long, UserWallet> wallets = CommonUtil.getWalletMap(commonService.getUserWallets(listofTrans.get(0).getSenderAuthId()));
			if(amounts.size() > wallets.size()){
				UIUtil.populateSendMoneyMapValues(request, model);
				model.put(ERROR_MESSAGE, context.getMessage(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE, null, locale));
				model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, sendMoneyMultipleForm);
				return SEND_MONEY_TO_MULTIPLE_FILE_UPLOAD_VIEW;
				
			}
			
			for(Long currency: amounts.keySet()){
				UserWallet uw = wallets.get(currency);
				if(uw == null || uw.getAmount() < amounts.get(currency)) {
					UIUtil.populateSendMoneyMapValues(request, model);
					model.put(ERROR_MESSAGE, context.getMessage(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE, null, locale));
					model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, sendMoneyMultipleForm);
					return SEND_MONEY_TO_MULTIPLE_FILE_UPLOAD_VIEW;	
				}
			}

			List<SendMoneyDto> serverFailedDtos = sendMoneyService.createSendMoneyToMultiple(listofTrans);
			session.setAttribute(SUCCESS_MESSAGE, context.getMessage(WalletTransactionConstants.TRANSACTION_SUCCESS_FILE_UPLOAD, new Object[] { listofTrans.size() }, locale));
			List<SendMoneyDto> validationFailedDtos = getInvalidRecordsForPost(list);
			List<SendMoneyDto> allFailedDtos = validationFailedDtos;
			
			if(!serverFailedDtos.isEmpty()){
				for(int i=0;i<serverFailedDtos.size();i++){
					allFailedDtos.add(serverFailedDtos.get(i));
				}
			}
			if(!allFailedDtos.isEmpty()){
				// request.setAttribute("listofTrans", getInvalidRecordsForPost(list));
				 //request.setAttribute(LIST_OF_TRANS, allFailedDtos);
				session.setAttribute(LIST_OF_TRANS, allFailedDtos);
				model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, sendMoneyMultipleForm);
				return UIUtil.redirectPath(SEND_MONEY_THROW_FILE_SUC_PATH);
			}
			model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, sendMoneyMultipleForm);
			return UIUtil.redirectPath(SEND_MONEY_THROW_FILE_PATH);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(WalletTransactionConstants.TRANSACTION_FAILED, null, locale));
			model.put(SEND_MONEY_FORM_NAME_FOR_MULTIPLE, sendMoneyMultipleForm);
			return SEND_MONEY_TO_MULTIPLE_FILE_UPLOAD_VIEW;
		}
	}
	
	@RequestMapping(value = "/fileuploadconfirm", method = RequestMethod.GET)
	public String sendMoneyThrowFileConfirmation(Map model, HttpServletRequest request, 
			 @Valid SendMoneyMultipleForm sendMoneyMultipleForm, Locale locale){
		LOGGER.debug(" sendMoneyThrowFileConfirmation :: Get ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		List<SendMoneyDto> allFailedDtos = (List<SendMoneyDto>) session.getAttribute(LIST_OF_TRANS);
		if(allFailedDtos != null){
			request.setAttribute(LIST_OF_TRANS,  allFailedDtos);
			session.removeAttribute(LIST_OF_TRANS);
		} else{
			request.setAttribute(LIST_OF_TRANS,  new ArrayList<SendMoneyDto>());
		}
		String sucMsg = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(sucMsg != null){
			model.put(SUCCESS_MESSAGE, sucMsg);
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		return SEND_MONEY_TO_MULTIPLE_FILE_UPLOAD_CONFIRMATION;
	}
	
	@RequestMapping(value = "/tosingleconfirmcancel", method = RequestMethod.GET)
	public String transferMoneyConfirmCancel( HttpServletRequest request,Map model,Locale locale,
		 SendMoneyForm sendMoneyForm, BindingResult result) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		if(session.getAttribute(USER_TYPE).equals(GlobalLitterals.CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(CUSTOMER_PATH);
		} else if(session.getAttribute(USER_TYPE).equals(GlobalLitterals.MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(MERCHANT_PATH);
		}
		 return Login.VIEW_RESOLVER_SESSION_EXPIRED_MERCHANT;
	}
	
	@RequestMapping(value = "/selftransfer", method = RequestMethod.GET)
	public String selftransfer(Map model, HttpServletRequest request,
			@Valid SelfTransferForm selfTransferForm, Locale locale) {
		LOGGER.debug(" selftransfer :: GET ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String sucMsg = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(sucMsg != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(sucMsg, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		Long authId = (Long)request.getSession().getAttribute(AUTHENTICATION_ID);
		Map<Long, UserWallet> userWallets = CommonUtil.getWalletMap(commonService.getUserWallets(authId));
		String walletBalances = SendMoneyHelper.getAllWalletBalances(userWallets);
		UIUtil.populateSelfTransferMapValues(request, model, userWallets, null);
		selfTransferForm.setWalletBalances(walletBalances);
		model.put(SEND_MONEY_FORM_NAME_FOR_SELF, selfTransferForm);
		return getView(session, SELF_TRANSFER);
	}

	@RequestMapping(value = "/selftransfer", method = RequestMethod.POST)
	public String selftransfer( HttpServletRequest request,Map model,Locale locale,
			SelfTransferForm selfTransferForm, BindingResult result) throws WalletException{
		LOGGER.debug(" selftransfer :: POST ");
		Map<Long, UserWallet> userWallets = null;
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		Long authId = (Long)request.getSession().getAttribute(AUTHENTICATION_ID);
		userWallets = CommonUtil.getWalletMap(commonService.getUserWallets(authId));
		SelfTransferValidator selfTransferValidator = new SelfTransferValidator();
		selfTransferValidator.validate(selfTransferForm, result);
		if(result.hasErrors()){
			UIUtil.populateSelfTransferMapValues(request, model, userWallets,selfTransferForm.getFromWallet());
			return getView(session,SELF_TRANSFER);
		}
		String userType = (String)session.getAttribute(USER_TYPE);
		String emailId = (String)session.getAttribute(USER_ID);
		Long fromWalletCurrency = selfTransferForm.getFromWallet();
		Long toWalletCurrency = selfTransferForm.getTowallet();
		Double reqAmount = Double.parseDouble(selfTransferForm.getRequestedAmount());
		Long countryId = null;
		Long transactionType = null;
		Long uType = null;
		try{
			if(CUSTOMER_USER_TYPE.equals(userType)){
				CustomerDto customerDto = customerService.getRegisteredMember(emailId, userType);
				countryId = customerDto.getCountry();
				uType = CUSTOMER_USER_TYPE_ID;
				transactionType=WalletTransactionTypes.P_TO_S;
			} else if(MERCHANT_USER_TYPE.equals(userType)){
				MerchantDto merchantDto = merchantService.getRegisteredMember(emailId, userType);
				countryId = merchantDto.getCountryBO();
				uType = MERCHANT_USER_TYPE_ID;
				transactionType=WalletTransactionTypes.M_TO_S;
			}
			if( !(sendMoneyService.validateThresholdLimitForSelfTransfer(countryId, toWalletCurrency, transactionType, reqAmount, uType))){
				UIUtil.populateSelfTransferMapValues(request, model, userWallets,selfTransferForm.getFromWallet());
				model.put(ERROR_MESSAGE, context.getMessage(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT, null, locale));
				return getView(session,SELF_TRANSFER);
			}
			
			UserWallet userWallet = commonService.getUserWallet(authId, selfTransferForm.getFromWallet());
			Double deductions = 0.0;
			Double actualAmount = 0.0;
			Double convertionRate = CurrencyConversion.getInstance().getConvertionFactor(fromWalletCurrency, toWalletCurrency);
			actualAmount = reqAmount/convertionRate;
			if(userWallet != null){
				deductions = feeMgmtService.calcuateDeductions(actualAmount, countryId, 
						fromWalletCurrency, transactionType, Boolean.TRUE);
			}
			if(userWallet == null || userWallet.getAmount() < actualAmount + deductions ){
				throw new WalletException(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE);
			}
			selfTransferForm.setActualAmount(actualAmount.toString());
			selfTransferForm.setTransactionType(transactionType);
			model.put(REQUESTED_AMOUNT, UIUtil.getConvertedAmountInString(reqAmount)+ SPACE_STRING + getCurrencyCode(request, toWalletCurrency));
			model.put(ACTUAL_AMOUNT, UIUtil.getConvertedAmountInString(actualAmount) + SPACE_STRING + getCurrencyCode(request, fromWalletCurrency));
			model.put(TAX_FEE, UIUtil.getConvertedAmountInString(deductions)+ SPACE_STRING + getCurrencyCode(request, fromWalletCurrency));
			model.put(TOTAL_DEDUCTIONS, UIUtil.getConvertedAmountInString(actualAmount + deductions)+ SPACE_STRING + getCurrencyCode(request, fromWalletCurrency));
			model.put(SEND_MONEY_FORM_NAME_FOR_SELF, selfTransferForm);
			return getView(session, SELF_TRANSFER_CONFIRM);
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
			userWallets = CommonUtil.getWalletMap(commonService.getUserWallets(authId));
			UIUtil.populateSelfTransferMapValues(request, model, userWallets, selfTransferForm.getFromWallet());
			model.put(ERROR_MESSAGE, context.getMessage(e.getMessage(), null, locale));
			model.put(SEND_MONEY_FORM_NAME_FOR_SELF, selfTransferForm);
			return getView(session, SELF_TRANSFER);
		}
		
	}
	
	@RequestMapping(value = "/selfTransferConfirm", method = RequestMethod.POST)
	public String selfTransferConfirm( HttpServletRequest request, Map model, Locale locale,
			SelfTransferForm selfTransferForm) throws WalletException{
		LOGGER.debug(" selftransferConfirm :: POST ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		Long authId = (Long)request.getSession().getAttribute(AUTHENTICATION_ID);
		String mode = selfTransferForm.getMode();
		Map<Long, UserWallet> userWallets = CommonUtil.getWalletMap(commonService.getUserWallets(authId));
		String walletBalances = SendMoneyHelper.getAllWalletBalances(userWallets);
		selfTransferForm.setWalletBalances(walletBalances);
		UIUtil.populateSelfTransferMapValues(request, model, userWallets, selfTransferForm.getFromWallet());
		model.put(SEND_MONEY_FORM_NAME_FOR_SELF, selfTransferForm);
		
		if("back".equals(mode)){
			return getView(session, SELF_TRANSFER);
		}
		
		try{
			SelfTransferDto selfTransferDto = SendMoneyHelper.convertSelfTransferFormToDto(request, selfTransferForm, authId,
					null, null , TypeOfRequest.WEB);
		  	sendMoneyService.createSelfMoney(selfTransferDto);
			session.setAttribute(SUCCESS_MESSAGE, WalletTransactionConstants.TRANSACTION_SUCCESS);
			return UIUtil.redirectPath(SELF_TRANSFER_SUC_PATH);
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(SELF_TRANSFER_FAILED, null, locale));
			return getView(session, SELF_TRANSFER);
		}
	}
	
	 private  List<SendMoneyDto> getInvalidRecordsForPost(List<SendMoneyDto> list) {
		 List<SendMoneyDto> forPostTrans = new ArrayList<SendMoneyDto>();
		 for(int i = 0; i < list.size(); i++){
			 SendMoneyDto sendMoneyDto = list.get(i);
			 if( Boolean.TRUE.equals(sendMoneyDto.getStatus()) ){
				 forPostTrans.add(sendMoneyDto);
			 }
		 }
		 return forPostTrans;
	 }

	private String getView(HttpSession session, String view){
		String uview = view;
		String uType = (String)session.getAttribute(USER_TYPE);
		
		if(CUSTOMER_USER_TYPE.equals(uType)){
			uview = CUSTOMER_VIEW + view;
		} else if(MERCHANT_USER_TYPE.equals(uType)){
			uview = MERCHANT_VIEW + view;
		}
		return uview;
	}
	
	private List<SendMoneyDto> constructSendMoneyMultipleDtos( HttpServletRequest request, SendMoneyMultipleForm merchantTransactionForm,
			String senderUserType, String senderEmailId)throws WalletException{
		
		List<SendMoneyDto> listdto = new ArrayList<SendMoneyDto>();
		int slabs = merchantTransactionForm.getEmailId().length;
		String[] emailArray = merchantTransactionForm.getEmailId(); 
		String[] amountArray = merchantTransactionForm.getAmount();
		Long[] currencyArray = merchantTransactionForm.getCurrency();
		Long[] destinationTypeArray = merchantTransactionForm.getDestinationType();
		String[] messageArray = merchantTransactionForm.getMessage();
		
		MerchantDto merchantDto = merchantService.getRegisteredMember(senderEmailId, senderUserType);
		Long countryId = merchantDto.getCountryBO();
		String payerName = merchantDto.getBusinessLegalname();
		Long senderAuthId = merchantDto.getAuthenticationId();
		Long uType = 2L;

		SendMoneyDto sendMoneyDto = null;
		Long destinationType = null;
		String receiverEmailId = null;
		Long requestedCurrency = null;
		Double requestedAmount = null;
		String message = null;
		
		String receiverUserType = null;
		Long receiverAuthId = null;
		String payeeName = null;
		Long transactionType = null;
		
		for(int i = 0; i < slabs; i++){
			
			sendMoneyDto = new SendMoneyDto();

			receiverEmailId = emailArray[i];
			destinationType = destinationTypeArray[i];
			requestedCurrency = currencyArray[i];
			requestedAmount = Double.parseDouble(amountArray[i]);
			message = messageArray[i];
			
			if(DestinationTypes.REGISTERED_PERSON.equals(destinationType)){
				receiverUserType = CUSTOMER_USER_TYPE;
				CustomerDto customerDto = customerService.getRegisteredMember(receiverEmailId, receiverUserType);
				receiverAuthId = customerDto.getAuthenticationId();
				payeeName = customerDto.getFullName();
				transactionType = WalletTransactionTypes.M_TO_P;	
			} else if(DestinationTypes.NON_REGISTERED_PERSON.equals(destinationType)){
				//to non register customer
				receiverUserType = CUSTOMER_USER_TYPE;
				payeeName = receiverEmailId;
				transactionType = WalletTransactionTypes.M_TO_NP;	
			} else if(DestinationTypes.MERCHANT.equals(destinationType)){
				//to merchant
				receiverUserType = MERCHANT_USER_TYPE;
				MerchantDto mDto = merchantService.getRegisteredMember(receiverEmailId, receiverUserType);
				receiverAuthId = mDto.getAuthenticationId();
				payeeName = mDto.getBusinessLegalname();
				transactionType = WalletTransactionTypes.M_TO_M;	
			}
			
			sendMoneyDto.setSenderUserType(uType);
			sendMoneyDto.setIpAddress(UIUtil.getClientIpAddr(request));
			sendMoneyDto.setSenderAuthId(senderAuthId);
			sendMoneyDto.setEmailId(receiverEmailId);
			sendMoneyDto.setDestinationType(destinationType);
			sendMoneyDto.setRequestedCurrency(requestedCurrency);
			sendMoneyDto.setRequestedAmount(requestedAmount);
			sendMoneyDto.setActualCurrency(requestedCurrency);
			sendMoneyDto.setActualAmount(requestedAmount);
			sendMoneyDto.setMessage(message);
			sendMoneyDto.setCountryId(countryId);
			sendMoneyDto.setPayerName(payerName);
			sendMoneyDto.setReceiverAuthId(receiverAuthId);
			sendMoneyDto.setPayeeName(payeeName);
			sendMoneyDto.setTransactionType(transactionType);
			sendMoneyDto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
			sendMoneyDto.setCurrencyCode(MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(requestedCurrency));
			sendMoneyDto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
			        (Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
			listdto.add(sendMoneyDto);
		}
		return listdto;
	}
	
	private SendMoneyDto constructSendMoneyDto(SendMoneyForm sendMoneyForm, HttpServletRequest request,
			String senderUserType, String senderEmailId )throws WalletException{
		
		Long destinationType = sendMoneyForm.getDestinationType();
		SendMoneyDto sendMoneyDto = new SendMoneyDto();
		sendMoneyDto.setIpAddress(UIUtil.getClientIpAddr(request));
		/*For type of request in transaction table*/		
		sendMoneyDto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
		        (Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue())); 
		sendMoneyDto.setEmailId(sendMoneyForm.getEmailId());
		sendMoneyDto.setRequestedAmount(Double.parseDouble(sendMoneyForm.getRequestedAmount()));
		sendMoneyDto.setRequestedCurrency(sendMoneyForm.getRequestedCurrency());
		String amount = sendMoneyForm.getActualAmount();
		if(amount != null){
			sendMoneyDto.setActualAmount(Double.parseDouble(amount));
			sendMoneyDto.setActualCurrency(sendMoneyForm.getActualCurrency());
		}
		sendMoneyDto.setMessage(sendMoneyForm.getMessage());
		sendMoneyDto.setDestinationType(sendMoneyForm.getDestinationType());
		sendMoneyDto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
		sendMoneyDto.setCurrencyCode(MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(sendMoneyForm.getRequestedCurrency()));
		
		Long countryId = null;
		String payerName = null;
		Long uType = null;
		Long senderAuthId = null;		
		String personName = null;
	
		if(CUSTOMER_USER_TYPE.equals(senderUserType)){
			CustomerDto customerDto = customerService.getRegisteredMember(senderEmailId, senderUserType);
			countryId = customerDto.getCountry();
			payerName = customerDto.getFullName();
			senderAuthId = customerDto.getAuthenticationId();
			uType = CUSTOMER_USER_TYPE_ID; 
		} else if(MERCHANT_USER_TYPE.equals(senderUserType)){
			MerchantDto merchantDto = merchantService.getRegisteredMember(senderEmailId, senderUserType);
			countryId = merchantDto.getCountryBO();
			payerName = merchantDto.getBusinessLegalname();
			senderAuthId = merchantDto.getAuthenticationId();
			uType = MERCHANT_USER_TYPE_ID;
		}
		if(sendMoneyForm.getDestinationType().equals(DestinationTypes.REGISTERED_PERSON)){
			personName = customerService.getPersonName(sendMoneyForm.getEmailId(), AttributeConstants.CUSTOMER_USER_TYPE);
			sendMoneyDto.setPayeeName(personName);
		} else if(sendMoneyForm.getDestinationType().equals(DestinationTypes.MERCHANT)){
			personName = merchantService.getPersonName(sendMoneyForm.getEmailId(), AttributeConstants.MERCHANT_USER_TYPE);
			sendMoneyDto.setPayeeName(personName);
		}		
		sendMoneyDto.setPayerName((String)request.getSession().getAttribute(NAME));
		sendMoneyDto.setSenderAuthId(senderAuthId);
		sendMoneyDto.setCountryId(countryId);
		sendMoneyDto.setPayerName(payerName);
		sendMoneyDto.setSenderUserType(uType);
		
		String receiverUserType = null;
		Long receiverAuthId = null;
		Long transactionType = null;
		String payeeName = null;
		String receiverEmailId = sendMoneyForm.getEmailId();
		
		//to register customer
		if(DestinationTypes.REGISTERED_PERSON.equals(destinationType)){
			
			receiverUserType = CUSTOMER_USER_TYPE;
			CustomerDto customerDto = customerService.getRegisteredMember(receiverEmailId, receiverUserType);
			receiverAuthId = customerDto.getAuthenticationId();
			payeeName = customerDto.getFullName();
			
			if(CUSTOMER_USER_TYPE.equals(senderUserType)){
				transactionType = WalletTransactionTypes.P_TO_P;	
			} else{
				transactionType = WalletTransactionTypes.M_TO_P;	
			}
		} else if(DestinationTypes.NON_REGISTERED_PERSON.equals(destinationType)){
			//to non register customer
			receiverUserType = CUSTOMER_USER_TYPE;
			payeeName = receiverEmailId;
			
			if(CUSTOMER_USER_TYPE.equals(senderUserType)){
				transactionType = WalletTransactionTypes.P_TO_NP;	
			} else {
				transactionType = WalletTransactionTypes.M_TO_NP;	
			}
		} else if(DestinationTypes.MERCHANT.equals(destinationType)){
			//to merchant
			receiverUserType = MERCHANT_USER_TYPE;
			MerchantDto merchantDto = merchantService.getRegisteredMember(receiverEmailId, receiverUserType);
			receiverAuthId = merchantDto.getAuthenticationId();
			payeeName = merchantDto.getBusinessLegalname();
			
			if(CUSTOMER_USER_TYPE.equals(senderUserType)){
				transactionType = WalletTransactionTypes.P_TO_M;	
			} else{
				transactionType = WalletTransactionTypes.M_TO_M;	
			}
		}
		
		sendMoneyDto.setReceiverAuthId(receiverAuthId);
		sendMoneyDto.setRecurring(sendMoneyForm.getRecurring());
		sendMoneyDto.setPayeeName(payeeName);
		sendMoneyDto.setTransactionType(transactionType);	
		
		if(sendMoneyForm.getRecurring() == Boolean.TRUE){
			sendMoneyDto.setUserJobName(sendMoneyForm.getUserJobName());
			sendMoneyDto.setFromDate(DateConvertion.stirngToDate(sendMoneyForm.getFromDate()));
			sendMoneyDto.setToDate(DateConvertion.stirngToDate(sendMoneyForm.getToDate()));
			sendMoneyDto.setFrequency(sendMoneyForm.getFrequency());
			sendMoneyDto.setTotalOccurences(sendMoneyForm.getTotalOccurences());
		}
  		return sendMoneyDto;
	}
	
	private List<SendMoneyDto> getCsvDetails(HttpServletRequest request, MultipartFile filename, String senderUserType, String senderEmailId) {
		List<SendMoneyDto> listDto = null;
		try {
		    MultipartFile strFile = filename;
            BufferedReader br = new BufferedReader(new InputStreamReader(strFile.getInputStream()));
		    String strLine = EMPTY_STRING;
		    listDto = new ArrayList<SendMoneyDto>();
		    ServletContext servletContext = request.getSession().getServletContext();
			Map<String, Long> currencyCodesMap = (Map<String, Long>) 
					servletContext.getAttribute(MasterDataConstants.MD_CURRENCY_CODES);
			    while ((strLine = br.readLine()) != null) {
				String[] oneRow = strLine.split(FILE_LINE_SPLIT);
				SendMoneyDto sendMoneyDto = new SendMoneyDto();
				sendMoneyDto.setIpAddress(UIUtil.getClientIpAddr(request));
				Boolean status = false;
				String emailId = EMPTY_STRING;
				String amount = EMPTY_STRING;
				Long currency = ZERO_LONG;
				String message = EMPTY_STRING;
				String receiverUserType = EMPTY_STRING;
				Long destinationType = ZERO_LONG;
				Long senderId = merchantService.getMerchantId(senderEmailId, senderUserType);
				MerchantDto merchantDtoo = merchantService.getMerchantDetailsById(senderId);
				sendMoneyDto.setCountryId(merchantDtoo.getCountryBO());
				Authentication	senderAuth = commonService.getAuthentication(senderEmailId, senderUserType);
				sendMoneyDto.setSenderAuthId(senderAuth.getId());
				sendMoneyDto.setSenderUserType(MERCHANT_USER_TYPE_ID);
				sendMoneyDto.setPayerName(merchantDtoo.getBusinessLegalname());
				sendMoneyDto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
				sendMoneyDto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
				        (Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
				
				int i = 0;
				for (String columnValue : oneRow) {
				    if (i == EMAIL_COLUMN_NUMBER) {
				    	emailId = columnValue;
				    	//email
				    	sendMoneyDto.setEmailId(emailId);
				    	if( !CommonValidator.expressionPattern(Common.EMAIL_PATTERN, columnValue)
				    			|| emailId.length( )>= Common.EMAIL_MAX_LENGTH){
				    		status = true;
				    	}
				    	
				    }
				    if (i == AMOUNT_COLUMN_NUMBER) {
				    	amount = columnValue;
				    	//amount
				    	sendMoneyDto.setRequestedAmount(Double.parseDouble(amount));
				    	sendMoneyDto.setActualAmount(Double.parseDouble(amount));
				    	if(!CommonValidator.expressionPattern(Common.CURRENCY_EXPRESSION, columnValue )){
				    		status = true;
				    	}
				    	if(Double.parseDouble(amount) <= 0){
				    		status = true;
				    	}
				    }
				    if (i == MESSAGE_COLUMN_NUMBER) {
				    	message = columnValue;
				    	//message
					    sendMoneyDto.setMessage(message);
					    
					    if(message.length() < Common.MESSAGE_MIN_LENGTH 
					    		|| message.length() > Common.MESSAGE_MAX_LENGTH){
					    	status = true;	
					    }
				    }
				    if (i == CURRENCY_COLUMN_NUMBER) {
				    	try{
				    	    if(!CommonValidator.expressionPattern(Common.ALPHA_BIT, columnValue)){
					    		status = true;
					    	} else {
				    	    	currency = currencyCodesMap.get(columnValue.toUpperCase());
				    	    }
				    	    if(currency != null && !currency.equals(ZERO_LONG)){
				    	    	//currency
				    	    	sendMoneyDto.setRequestedCurrency(currency);
				    	    	sendMoneyDto.setActualCurrency(currency);
				    	    } else {
				    	    	status = true;
				    	    	// wrong currency
				    	    	sendMoneyDto.setCurrencyCode(columnValue);
				    	    }
				    	} catch (Exception e) {
				    		LOGGER.error(e.getMessage(), e);
				    		status = true;
				    		sendMoneyDto.setRequestedCurrency(0L);
						}
				    }
				    if (i == DESTINATION_TYPE_COLUMN_NUMBER) {
						try{
							destinationType = Long.parseLong(columnValue);
							//destination type
							sendMoneyDto.setDestinationType(destinationType);
							if(destinationType.equals(ADMIN_USER_TYPE_ID)){
								status = true;
							} else if(sendMoneyDto.getEmailId().equalsIgnoreCase(senderEmailId) &&
									UIUtil.getUserTypeID(senderUserType).equals(destinationType)){
					    		status = true;
					    	}
				    	} catch (Exception e) {
				    		LOGGER.error(e.getMessage(), e);
				    		status = true;
				    		sendMoneyDto.setDestinationType(0L);
						}
					}
				    i++;
				    sendMoneyDto.setStatus(status);
				}
				try{
					if(!status){
						String payeeName = EMPTY_STRING;
						//to register customer
						if(DestinationTypes.REGISTERED_PERSON.equals(destinationType)){
							receiverUserType = GlobalLitterals.CUSTOMER_USER_TYPE;
							CustomerDto customerDto = null;
							try{
								customerDto = customerService.getRegisteredMember(emailId, receiverUserType);
							} catch(Exception e){
								LOGGER.error(e.getMessage() , e);
								status = true;
							}
							if(customerDto != null){
								payeeName = customerDto.getFullName();
							} else {
								status=true;
							}
							
							Authentication	receiverAuth = commonService.getAuthentication(emailId, receiverUserType);
							if(receiverAuth == null){
								status = true;
							} else if(!UserStatusConstants.APPROVED.equals(receiverAuth.getStatus())){
								status = true;
							} else if(!receiverAuth.isActive()){
								status = true;
							} else {
								sendMoneyDto.setReceiverAuthId(receiverAuth.getId());
							}
							sendMoneyDto.setTransactionType(WalletTransactionTypes.M_TO_P);
							
						} else if(DestinationTypes.NON_REGISTERED_PERSON.equals(destinationType)){
							//to non register customer
							try{
								   Authentication auth = commonService.getAuthentication(emailId, GlobalLitterals.CUSTOMER_USER_TYPE);
								   if(auth != null){
									   status = true;
								   }
							   } catch(Exception e){
								   LOGGER.error(e.getMessage() , e );
								   status = true;
							   }
							receiverUserType = GlobalLitterals.CUSTOMER_USER_TYPE;
							sendMoneyDto.setTransactionType(WalletTransactionTypes.M_TO_NP);
						} else if (DestinationTypes.MERCHANT.equals(destinationType)){
							//to merchant
							receiverUserType = GlobalLitterals.MERCHANT_USER_TYPE;
							MerchantDto mDto = null;
							try{
								mDto = merchantService.getRegisteredMember(emailId, receiverUserType);
							} catch(Exception e){
								LOGGER.error(e.getMessage() , e);
								status = true;
							}
							if(mDto != null){
								payeeName = mDto.getBusinessLegalname();
							} else{
								status = true;
							}
							
							Authentication	receiverAuth = commonService.getAuthentication(emailId, receiverUserType);
							if(receiverAuth == null){
								status = true;
							} else if(!UserStatusConstants.APPROVED.equals(receiverAuth.getStatus())){
								status = true;
							} else if(receiverAuth != null && !receiverAuth.isActive()){
								status = true;
							} else {
								sendMoneyDto.setReceiverAuthId(receiverAuth.getId());
							}
							sendMoneyDto.setTransactionType(WalletTransactionTypes.M_TO_M);
						} else {
							status = true;
						}
						sendMoneyDto.setPayeeName(payeeName);
					}
					String currencyCode = MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(), 
							(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(sendMoneyDto.getRequestedCurrency());
					if(currencyCode != null){
						sendMoneyDto.setCurrencyCode(currencyCode);
					}
				} catch (WalletException e) {
					LOGGER.error(e.getMessage(), e);
					status = true;
				}
				sendMoneyDto.setStatus(status);
				listDto.add(sendMoneyDto);
		    }
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return listDto;
	}
	
	 private  List<SendMoneyDto> getValidRecordsForPost(List<SendMoneyDto> list) {
		 List<SendMoneyDto> forPostTrans = new ArrayList<SendMoneyDto>();
		 for(int i = 0; i < list.size(); i++){
			 SendMoneyDto sendMoneyDto = list.get(i);
			 if(!sendMoneyDto.getStatus()){
				 forPostTrans.add(sendMoneyDto);
			 }
		 }
		 return forPostTrans;
	}
	 
	private Boolean validateThresholdLimit(SendMoneyDto sendMoneyDto)throws WalletException{
		return sendMoneyService.validateThresholdLimit(sendMoneyDto.getCountryId(), sendMoneyDto.getRequestedCurrency(), 
				sendMoneyDto.getTransactionType(), sendMoneyDto.getRequestedAmount(), sendMoneyDto.getSenderUserType());
	 }
	
	
	private String getCurrencyCode(HttpServletRequest request,Long currencyId) {
		return	MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(currencyId);
	}
	
	private String prepareErrorMessage(Map model, Locale locale) {
		model.put(ERROR_MESSAGE, context.getMessage(NO_RECORDS_TO_PROCESS, null, locale));
		return SEND_MONEY_TO_MULTIPLE_FILE_UPLOAD_VIEW;
	}
}
