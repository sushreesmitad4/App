package com.tarang.ewallet.walletui.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.masterdata.util.CountryIds;
import com.tarang.ewallet.masterdata.util.CurrencyIds;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.NonRegisterWallet;
import com.tarang.ewallet.model.UserIP;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.reports.business.ReportsService;
import com.tarang.ewallet.transaction.business.FraudCheckService;
import com.tarang.ewallet.transaction.business.SendMoneyService;
import com.tarang.ewallet.transaction.util.WalletTransactionStatus;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Customer;
import com.tarang.ewallet.walletui.form.CustomerRegFormTwo;
import com.tarang.ewallet.walletui.util.CustomerDataUtil;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.CROneValidator;
import com.tarang.ewallet.walletui.validator.CRTwoValidator;
import com.tarang.ewallet.walletui.validator.CRUpdateValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


/* @author  : prasadj
* @date    : Oct 5, 2012
* @time    : 8:52:17 AM
* @version : 1.0.0
* @comments: CustomerController is a controller class for customer activities 
* 			 
*/

@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/customer")
public class CustomerController implements Customer, AttributeConstants, AttributeValueConstants, GlobalLitterals {	

	private static final Logger LOGGER = Logger.getLogger(CustomerController.class);
	

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
    private CommonService commonService;
	
	@Autowired
	private SendMoneyService sendMoneyService;
	
	@Autowired
	private ReportsService reportsService;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private FraudCheckService fraudCheckService;
	
	@Autowired
	private ApplicationContext context;
 
	@RequestMapping(method = RequestMethod.GET)
	public String defaultPage( Map model, Locale locale, HttpServletRequest request) {
		
		LOGGER.debug( " defaultPage " );
		HttpSession session = request.getSession();
		
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String changePassMsg = (String)session.getAttribute(CHANGE_PASSWORD_SUCS_MSG);
		if(changePassMsg != null){
			model.put(SUCCESS_MESSAGE, changePassMsg);
			session.removeAttribute(CHANGE_PASSWORD_SUCS_MSG);
		}
		
		Long authId = (Long)request.getSession().getAttribute(AUTHENTICATION_ID);
		Long customerDbId = (Long)request.getSession().getAttribute(DB_ID);
		UserWallet userWallet = null;
		CustomerDto dto = null;
		Long currency = null;
		Double amount = 0.00;
		try {
			dto = customerService.getCustomerDto(customerDbId);
			if(dto.getEmailPatternCheck())
			{
				currency = MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), dto.getCountry());
			}else{
				currency = MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), CountryIds.IND_COUNTRY_ID);
			}
			userWallet = commonService.getUserWallet(authId, currency);
			amount = userWallet.getAmount();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		if(userWallet == null){
			userWallet = new UserWallet();
		}
		String balanceUrl = WALLET_PATH_PREFIX + "home/allbalancerecords";
		request.setAttribute("urlBalanceList", balanceUrl);
		String url = WALLET_PATH_PREFIX + "customer/txnrecords";
		request.setAttribute("urlAccountList", url);
		model.put("userwalletamount", UIUtil.getConvertedAmountInString(amount));
		if(dto.getProfileComplet()){
			model.put("userwalletcurrency" , MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
					dto.getCountry()));	
		}else{
			model.put("userwalletcurrency" , MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
					CountryIds.IND_COUNTRY_ID));
		}
		
		model.put("userstatusname", MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataUtil.MD_USER_STATUSES).get(session.getAttribute(USER_STATUS)));
		return Customer.DEFAULT_VIEW;
		
	}
	/*Customer registration page one*/
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrationPageOne(Map model, HttpServletRequest request, 
			@Valid CustomerRegFormTwo customerRegFormTwo, Locale locale) {
		HttpSession session = request.getSession();
		LOGGER.debug( " registrationPageOne " );
		
		String sessionUniqueValue = (String)session.getAttribute(ERROR_MESSAGE);
		if(sessionUniqueValue != null){
			session.removeAttribute(ERROR_MESSAGE);
			model.put(ERROR_MESSAGE, context.getMessage(sessionUniqueValue, null, locale));
		}
		
		model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.HINT_QUESTIONS));
		return REG_VIEW_ONE;
		
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationPageOneSubmit(Map model, HttpServletRequest request,
			Locale locale, @Valid CustomerRegFormTwo customerRegFormTwo,
			BindingResult result){
		
		LOGGER.debug( " registrationPageOneSubmit " );
		String viewPage = REG_VIEW_ONE;
		CROneValidator crValidator = new CROneValidator();
		crValidator.validate(customerRegFormTwo, result);
		//************   FRAUD CHECK START-13   ***************//
		try {
			Boolean flag = fraudCheckService.domainCheck(customerRegFormTwo.getEmailId());
			if(flag){
				result.rejectValue(EMAIL_ID, CommonConstrain.EMAIL_DOMAIN_NOT_ALLOWED);
			}
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
		}
		//************   FRAUD CHECK END   ***************//
		//keep first email id as old email id
		if(customerRegFormTwo.getOldEmailId() != null && customerRegFormTwo.getOldEmailId().equals(EMPTY_STRING)){
			customerRegFormTwo.setOldEmailId(customerRegFormTwo.getEmailId());
		}
		Boolean isRequestForRegistrationAfter24hours = Boolean.FALSE;
		Long recordStatus = null;
		Date creationDate = null;
		Authentication authentication = null;
		Boolean isRetriveExistingRecords = Boolean.FALSE;
		try{
			isRetriveExistingRecords = commonService.phoneNOExist(customerRegFormTwo.getPhoneCode(), customerRegFormTwo.getPhoneNo());
			if(isRetriveExistingRecords){
			   CommonValidator.mandatoryValidator(result,CROneValidator.PHONE_NO_VAR,Common.DUPLICATE_PHONE_NO_ERR);
			}
			recordStatus = commonService.emailIdExist(customerRegFormTwo.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			if(recordStatus != null){
				authentication = commonService.getAuthentication(customerRegFormTwo.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			}
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
		}
		if(authentication != null){
			creationDate = authentication.getCreationDate();
		}
		isRequestForRegistrationAfter24hours = CommonValidator.validateEmail(recordStatus, creationDate, result);
		
		
	
		/*if(authentication != null){
			creationDate = authentication.getCreationDate();
		}
		isRequestForRegistrationAfter24hours = CommonValidator.phoneNumberValidator(recordStatus, creationDate, result);*/
		if(result.hasErrors()){
			model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.HINT_QUESTIONS));
			//new add
			//model.put(COUNTRY_PHONE_CODES, MasterDataUtil.getPhoneCodes(request.getSession().getServletContext()));
			
			viewPage = REG_VIEW_ONE;
		} else{
			String mode = customerRegFormTwo.getMode();
			if(mode != null && !BACK.equalsIgnoreCase(mode) && 
					customerRegFormTwo.getOldEmailId().equals(customerRegFormTwo.getEmailId())){
				isRetriveExistingRecords = Boolean.TRUE;
			} else if(mode != null && BACK.equalsIgnoreCase(mode) && 
					!customerRegFormTwo.getOldEmailId().equals(customerRegFormTwo.getEmailId())){
				isRetriveExistingRecords = Boolean.TRUE;
			} else if(mode != null && BACK.equalsIgnoreCase(mode) && 
					customerRegFormTwo.getOldEmailId().equals(customerRegFormTwo.getEmailId())){
				//continue existing record in form
				isRetriveExistingRecords = Boolean.FALSE;
			}
			if(isRequestForRegistrationAfter24hours){
				//retrieve old records and continue for registration
				if(isRetriveExistingRecords){
					try {
						Boolean editOrUpdatePage = Boolean.FALSE;
						Long customerId = customerService.getCustomerId(customerRegFormTwo.getEmailId(), CUSTOMER_USER_TYPE);
						CustomerDto customerDto = customerService.getCustomerDto(customerId);
						CustomerRegFormTwo customerRegFormTwonew = CustomerDataUtil.convertCustomerDtoToCustomerRegFormTwo(request, customerDto, editOrUpdatePage);
						
						customerRegFormTwonew.setEmailId(customerRegFormTwo.getEmailId());
						customerRegFormTwonew.setOldEmailId(customerRegFormTwonew.getEmailId());
						//customerRegFormTwonew.setCemailId(customerRegFormTwo.getEmailId());
						customerRegFormTwonew.setPassword(customerRegFormTwo.getPassword());
						customerRegFormTwonew.setHintQuestion1(customerRegFormTwo.getHintQuestion1());
						customerRegFormTwonew.setAnswer1(customerRegFormTwo.getAnswer1());
						customerRegFormTwonew.setPhoneCode(customerRegFormTwo.getPhoneCode());
						customerRegFormTwonew.setPhoneNo(customerRegFormTwo.getPhoneNo());
						customerRegFormTwonew.setMode(REQUEST_FOR_REGISTRATION_AFTER_24HOURS);
						model.put(CUSTOMER_REG_FORMTWO , customerRegFormTwonew);
						model.put(ERROR_MESSAGE, context.getMessage(Common.ERROR_MSG_RECORD_EXIST_UPDATE_AND_COMPLETE_REGISTRATION, null, locale));
						papulateMapValuesForRegistrationTwo(request, model, customerRegFormTwonew.getCountry());
					} catch (WalletException e) {
						LOGGER.error(e.getMessage() , e);
						model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
								request.getSession().getServletContext(), 
								(Long) request.getSession().getAttribute(LANGUAGE_ID),
								MasterDataConstants.HINT_QUESTIONS));
						model.put(ERROR_MESSAGE, context.getMessage(UserValidator.UNABLE_TO_PROCESS_MSG, null, locale));
						return REG_VIEW_ONE;
					}
				} else{
					//continue existing record in form and change the mode to call update record
					customerRegFormTwo.setMode(REQUEST_FOR_REGISTRATION_AFTER_24HOURS);
					papulateMapValuesForRegistrationTwo(request, model, customerRegFormTwo.getCountry());
				}
			} else{
				//create new registration
				if(!isRetriveExistingRecords){  
					customerRegFormTwo.setMode(EMPTY_STRING);
					papulateMapValuesForRegistrationTwo(request, model, customerRegFormTwo.getCountry());
				} else{
					CustomerRegFormTwo customerRegFormTwonew = new CustomerRegFormTwo();
					customerRegFormTwonew.setEmailId(customerRegFormTwo.getEmailId());
					customerRegFormTwonew.setOldEmailId(customerRegFormTwo.getEmailId());
					//customerRegFormTwonew.setCemailId(customerRegFormTwo.getEmailId());
					customerRegFormTwonew.setPassword(customerRegFormTwo.getPassword());
					customerRegFormTwonew.setHintQuestion1(customerRegFormTwo.getHintQuestion1());
					customerRegFormTwonew.setAnswer1(customerRegFormTwo.getAnswer1());
					customerRegFormTwonew.setPhoneCode(customerRegFormTwo.getPhoneCode());
					customerRegFormTwonew.setPhoneNo(customerRegFormTwo.getPhoneNo());
					customerRegFormTwonew.setMode(EMPTY_STRING);
					model.put(CUSTOMER_REG_FORMTWO , customerRegFormTwonew);
					papulateMapValuesForRegistrationTwo(request, model, customerRegFormTwonew.getCountry());
				}
			}
			viewPage = REG_VIEW_TWO;
		}
		return viewPage;
	}
	
	/*Customer registration page two*/
	@RequestMapping(value = "/registrationtwo", method = RequestMethod.POST)
	public String registrationPageTwoSubmit(Map model, HttpServletRequest request,
			Locale locale, @Valid CustomerRegFormTwo customerRegFormTwo,
			BindingResult result){
			LOGGER.debug( " registrationPageTwoSubmit " );
			/* For back button in registration page two */
			String mode = customerRegFormTwo.getMode();
			String viewPage = REG_VIEW_ONE;
			if (mode != null && "back".equalsIgnoreCase(mode)) {
				LOGGER.debug(" registrationPageTwoSubmit :: mode back");
				model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataConstants.HINT_QUESTIONS));
				return viewPage;
			}
			CRTwoValidator crTwoValidator = new CRTwoValidator();
			crTwoValidator.validate(customerRegFormTwo, result);
			if(result.hasErrors()){
				papulateMapValuesForRegistrationTwo(request, model, customerRegFormTwo.getCountry());
				viewPage = REG_VIEW_TWO;
			} else{
				//save reg page
				try{
					Long recordStatus = null;
					Date creationDate = null;
					Authentication authentication = null;
					try{
						recordStatus = commonService.emailIdExist(customerRegFormTwo.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
						if(recordStatus != null){
							authentication = commonService.getAuthentication(customerRegFormTwo.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
						}
					} catch (WalletException e) {
						LOGGER.error(e.getMessage() , e);
					}
					if(authentication != null){
						creationDate = authentication.getCreationDate();
					}
					if(CustomerDataUtil.validateEmailForADuration(recordStatus, creationDate, model, locale)){
						LOGGER.debug(" registrationPageTwoSubmit :: email already registered in this duration");
						model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(), 
								(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataConstants.HINT_QUESTIONS));
						model.put(ERROR_MESSAGE, context.getMessage(Common.EMAIL_ALREADY_REGISTRED_DURATION_ERRMSG, null, locale));
						return REG_VIEW_ONE;
					}
					CustomerDto customerDto = new CustomerDto();
					customerDto.setIpAddress(UIUtil.getClientIpAddr(request));
					customerDto.setEmailId(customerRegFormTwo.getEmailId());
					customerDto.setPassword(customerRegFormTwo.getPassword());
					customerDto.setHintQuestion1(customerRegFormTwo.getHintQuestion1());
					customerDto.setAnswer1(customerRegFormTwo.getAnswer1());
					customerDto.setTitle(customerRegFormTwo.getPtitle());
					//profile complete true means skip
					if(!customerRegFormTwo.getProfileComplet()){
						customerDto.setFirstName(customerRegFormTwo.getFirstName());
						customerDto.setLastName(customerRegFormTwo.getLastName());
						customerDto.setAddrOne(customerRegFormTwo.getAddrOne());
						customerDto.setAddrTwo(customerRegFormTwo.getAddrTwo());
						customerDto.setCity(customerRegFormTwo.getCity());
						customerDto.setState(customerRegFormTwo.getState());
						customerDto.setCountry(customerRegFormTwo.getCountry());
						customerDto.setPostelCode(customerRegFormTwo.getPostalCode());
						customerDto.setDateOfBirth(DateConvertion.stirngToDate(customerRegFormTwo.getDateOfBirth()));
						customerDto.setProfileComplet(Boolean.TRUE);
						customerDto.setDefaultCurrency(MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), 
								customerRegFormTwo.getCountry()));
					}else{
						customerDto.setProfileComplet(Boolean.FALSE);
						customerDto.setDefaultCurrency(CurrencyIds.INR);
					}
					customerDto.setPhoneCode(customerRegFormTwo.getPhoneCode());
					customerDto.setPhoneNo(customerRegFormTwo.getPhoneNo());
					
					customerDto.setKycRequired(false);
					customerDto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
					
					CustomerDto customerDto1 = null;
					if(mode != null && mode.equalsIgnoreCase(REQUEST_FOR_REGISTRATION_AFTER_24HOURS)){
						/*Note: User is requested for registration after 24hours from his/her first time registration, 
						 * then the system will update the existing record including IP address, password and creation date*/
						Long customerId = customerService.getCustomerId(customerRegFormTwo.getEmailId(), CUSTOMER_USER_TYPE);
						CustomerDto customerDtoCopy = customerService.getCustomerDto(customerId);
						customerDto1 = customerService.registrationAfter24hours(customerDto);
						auditTrailService.createAuditTrail(customerDto.getAuthenticationId(), 
								AuditTrailConstrain.MODULE_CUSTOMER_REGISTRATION_AFTER_24HOURS, AuditTrailConstrain.STATUS_UPDATE, 
								customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, 
								customerDtoCopy, customerDto1);
					} else{
						customerDto1 = customerService.newregistration(customerDto);
						//call transaction update method for non register person txn records.
						List<NonRegisterWallet> nonRegWallets = commonService.getMoneyFromTemporaryWallet(customerDto.getEmailId());
						if(nonRegWallets != null){
							for(NonRegisterWallet nonRegWallet: nonRegWallets){
								Date date = nonRegWallet.getCreationDate();
								int nonRegWalletExpDays = utilService.getCancelNonregWalletTxnsAllowedDays();
								Date nonWalletExpireDate = DateConvertion.futureDate(date, nonRegWalletExpDays);
								customerDto.setCreationDate(new Date());
								if(customerDto.getCreationDate().compareTo(nonWalletExpireDate) <= GlobalLitterals.ZERO_INTEGER 
										&& !nonRegWallet.getRegister().equals(WalletTransactionStatus.CANCEL)){
									nonRegWallet.setRegister(WalletTransactionStatus.SUCCESS);
									commonService.updateTemporaryWalletRecord(nonRegWallet);
									sendMoneyService.updateSendMoneyForNonRegisters(nonRegWallet.getTxnId(), customerDto1.getAuthenticationId());
								}
							}
						}
						//Audit Trail service
						auditTrailService.createAuditTrail(customerDto1.getAuthenticationId(), AuditTrailConstrain.MODULE_CUSTOMER_REGISTRATION, 
								AuditTrailConstrain.STATUS_REGISTRATION, customerDto1.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
						// to set default preferences
						PreferencesDto preferencesDto = new PreferencesDto();
					    preferencesDto.setCurrency(CommonConstrain.DEFAULT_CURRENCY);
					    preferencesDto.setLanguage(CommonConstrain.DEFAULT_LANGUAGE);
						preferencesDto.setAuthentication(customerDto1.getAuthenticationId());
						commonService.createPreferences(preferencesDto);
					}
					 
					//************   FRAUD CHECK START -3,7 ***************//
					Integer hours = utilService.getRegistrationAllowedHours();
					Integer noOfAccounts = utilService.getRegistrationAllowedAccounts();
					Date fromDate = DateConvertion.getPastDateByHours(hours);
					Boolean	flag = fraudCheckService.ipMultipleAccountsVelocity(customerDto.getIpAddress(), fromDate, new Date(), noOfAccounts);
					commonService.updateIpAddressCheck(customerDto1.getAuthenticationId(), flag);
					hours = utilService.getEmailPatternAllowedHours();
					noOfAccounts = utilService.getEmailPatternAllowedAccounts();
					fromDate= DateConvertion.getPastDateByHours(hours);
					flag = fraudCheckService.emailPatternCheck(customerDto.getEmailId(), fromDate, new Date(), noOfAccounts);
					commonService.updateEmailPatternCheck(customerDto1.getAuthenticationId(), flag);
					 //************   FRAUD CHECK END   ***************//
					
					// Validating user ip-address is existing or not and login with old ipaddress will allow here
					UserIP userIp = commonService.getUserIPAddress(customerDto1.getAuthenticationId(), customerDto1.getIpAddress());
					if(null == userIp){
						//saving user ip-address
						commonService.saveUserIPAddress(new UserIP(customerDto1.getAuthenticationId(), customerDto1.getIpAddress(), new Date(), null));
					}
					return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_REG_SUCC_PATH);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					papulateMapValuesForRegistrationTwo(request, model, customerRegFormTwo.getCountry());
					String errorMessage = null;
					if(e.getMessage().contains(CommonConstrain.DATA_INTEGRITY_VIOLATION)){
						errorMessage = Common.REGISTRTAION_FAIL_DB_ERR;
					} else if(e.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
					    errorMessage = Common.DUPLICATE_PHONE_NO_ERR;
					} else{
						errorMessage = Common.REGISTATION_FAILED;
					}
					model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
					viewPage = REG_VIEW_TWO;
				}			
			}
			return viewPage;
	}
	
	@RequestMapping(value = "/registrationsuccess", method = RequestMethod.GET)
	public String registrationSuccessPage(Map model, HttpServletRequest request) {
		LOGGER.debug( " registrationSuccessPage " );
		return REG_VIEW_SUCCESS;		
	}
	
	/**
	 * @comments: This method is call when customer click on registration verification link from his/her email.
	 */
	@RequestMapping(value = "/emailStatusUpdate", method = RequestMethod.GET)
	public String emailStatusUpdate(HttpServletRequest request, HttpServletResponse response) throws WalletException {
		LOGGER.debug( " emailStatusUpdate " );
		HttpSession session = request.getSession();
		try{
			Long id = Long.parseLong(request.getParameter(ID));
			commonService.registrationEmailVarification(id);
			session.setAttribute(SUCCESS_MESSAGE, CommonConstrain.EMAIL_VARIFIED_SUCCESS_MSG);
			//Audit Trail service
			auditTrailService.createAuditTrailForEmailVarification(id, AuditTrailConstrain.MODULE_CUSTOMER_EMAILVERIFICATION_UPDATE, 
					AuditTrailConstrain.STATUS_UPDATE, commonService.getUserEmailById(id), GlobalLitterals.CUSTOMER_USER_TYPE);	
			
			return UIUtil.redirectPath(AttributeValueConstants.HOME_LOGIN_PATH);
		} catch(NullPointerException ex){
			LOGGER.error(ex.getMessage(), ex);
			session.setAttribute(ERROR_MESSAGE, CommonConstrain.EMAIL_VARIFIED_FAILED_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.HOME_LOGIN_PATH);
		} catch(Exception e){
			LOGGER.error(e.getMessage() , e);
			String em = null;
			if(e.getMessage().contains(CommonConstrain.EMAIL_VARIFICATION_LINK_EXPIRED)){
				em = CommonConstrain.EMAIL_VARIFICATION_LINK_EXPIRED;
			} else{
				em = CommonConstrain.EMAIL_VARIFIED_FAILED_MSG;
			}
			session.setAttribute(ERROR_MESSAGE, em);
			return UIUtil.redirectPath(AttributeValueConstants.HOME_LOGIN_PATH);
		}
	}
	
	/**
	 * @comments: This method is call where clicked customer update profile
	 *            under customer dashboard.
	 */
	@RequestMapping(value = "/updateprofile", method = RequestMethod.GET)
	public String customerUpdateProfile(HttpServletRequest request, Map model, Locale locale) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String userId = (String) session.getAttribute(USER_ID);
		String userType = (String) session.getAttribute(USER_TYPE);
		Long customerId;
		try {
			Boolean editOrUpdatePage = Boolean.TRUE;
			customerId = customerService.getCustomerId(userId, userType);
			CustomerDto customerDto = customerService.getCustomerDto(customerId);
			CustomerRegFormTwo customerRegFormTwo = CustomerDataUtil.convertCustomerDtoToCustomerRegFormTwo(request, customerDto, editOrUpdatePage);
			
			customerRegFormTwo.setId(customerId);
			
			model.put(STATUS_LIST, MasterDataUtil.getStatusListById(request, customerDto.getStatus()));
			
			papulateMapValuesForRegistrationTwo(request, model, customerRegFormTwo.getCountry());
			
			model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.HINT_QUESTIONS));
			
			model.put(CUSTOMER_REG_FORM_TWO, customerRegFormTwo);
			model.put("isphoneupdate", "update");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.UNABLE_TO_PROCESS_MSG, null, locale));
		}
		return CUSTOMER_UPDATE_PROFILE;
	}

	/**
	 * @comments: This method is call where clicked customer update profile
	 *            under customer dashboard.
	 */
	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	public String customerUpdateProfile(HttpServletRequest request, Map model, 
			@ModelAttribute(CUSTOMER_REG_FORM_TWO) CustomerRegFormTwo customerRegFormTwo,
			BindingResult result, Locale locale, Long id) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String mode = customerRegFormTwo.getMode();
		if (UPDATE.equalsIgnoreCase(mode)) {
			CustomerDto customerDto = new CustomerDto();
			try {
				papulateMapValuesForRegistrationTwo(request, model, customerRegFormTwo.getCountry());
				model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID),
						MasterDataConstants.HINT_QUESTIONS));
				customerDto = customerService.getCustomerDto(customerRegFormTwo.getId());
				customerRegFormTwo.setExistphone(customerDto.getPhoneNo());
				CRUpdateValidator cRUpdateValidator = new CRUpdateValidator();
				cRUpdateValidator.validate(customerRegFormTwo, result);
				model.put("isphoneupdate", "update");
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				String errorMessage = null;
				if(e instanceof NullPointerException){
					errorMessage = UserValidator.UNABLE_TO_PROCESS_MSG;
				} else{
					errorMessage = UserValidator.UNABLE_TO_PROCESS_MSG;
				}
				model.put(ERROR_MESSAGE,context.getMessage(errorMessage, null, locale));
			}

			if (!result.hasErrors()) {
				try {
					CustomerDto customerDtoCopy = (CustomerDto)customerDto.clone();
					CustomerDto customerDto2 = CustomerDataUtil.setCustomerDto(customerRegFormTwo, customerDto);
					customerDto2.setHintQuestion1(customerRegFormTwo.getHintQuestion1());
					customerDto2.setHintQuestion2(customerRegFormTwo.getHintQuestion2());
					customerDto2.setAnswer1(customerRegFormTwo.getAnswer1());
					customerDto2.setAnswer2(customerRegFormTwo.getAnswer2());
					customerService.updateCustomerDetails(customerDto2);
					//Audit Trail service
					auditTrailService.createAuditTrail(customerDto.getAuthenticationId(), 
							AuditTrailConstrain.MODULE_CUSTOMER_UPDATE, AuditTrailConstrain.STATUS_UPDATE, 
							customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, 
							customerDtoCopy, customerDto2);
					model.put(SUCCESS_MESSAGE, context.getMessage(CRTwoValidator.CUSTOMER_UPDATE_SUCCESS_MSG, null, locale));
					session.setAttribute(NAME, customerDto2.getFirstName() + " " + customerDto2.getLastName());
					
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
					String em = null;
					if(ex instanceof NullPointerException){
						em = UserValidator.UNABLE_TO_PROCESS_MSG;
					} else if(ex.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
					    em = Common.DUPLICATE_PHONE_NO_ERR;
					} else if(ex instanceof CloneNotSupportedException){
					    em = AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR;
					} else{
						em = UserValidator.UNABLE_TO_PROCESS_MSG;
					}
					LOGGER.error(ex.getMessage(), ex);
					model.put(ERROR_MESSAGE, context.getMessage(em, null, locale));
				}
			}
		}
		return CUSTOMER_UPDATE_PROFILE;
	}
	
	@RequestMapping(value="/underconstruction", method = RequestMethod.GET)
	public String underConstructionPage(HttpServletRequest request, Map model){
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		LOGGER.debug( " underConstructionPage " );
		return CUSTOMER_VIEW_UNDERCONSTRUCTION;
	}
	
	private void papulateMapValuesForRegistrationTwo(HttpServletRequest request, Map model, Long countryId){
		
		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
	
		model.put(STATE_LIST, MasterDataUtil.getRegions(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), countryId));
	
		model.put(PERSON_TITLES, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.TITLES));
		
		model.put(COUNTRY_PHONE_CODES, MasterDataUtil.getPhoneCodes(request.getSession().getServletContext()));
		
	}
	

	@RequestMapping(value="/txnrecords", method = RequestMethod.GET, headers=JSON_HEADER, produces=JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<CustomerReportModel> transactionRecords(
    		@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sidx,
    		@RequestParam(value="sord", required=false) String sord,
    		HttpServletRequest request, Locale locale) throws WalletException {
		LOGGER.debug(" transactionRecords ");
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(filters);
		qf.setPage(page);
		qf.setRows(rows);
		qf.setSidx(sidx);
		qf.setSord(sord); 	
		JqgridResponse<CustomerReportModel> response = new JqgridResponse<CustomerReportModel>();		
		try{
			List<CustomerReportModel> modelList = reportsService.customerLastNTransactions(
					utilService.getDefaultLastNTransactions(), 
					1L, (Long)request.getSession().getAttribute(AUTHENTICATION_ID), 
					context.getMessage(BALANCE_STATUS_CR, null, locale),
							context.getMessage(BALANCE_STATUS_DR, null, locale));
			for(CustomerReportModel cu:modelList){
				cu.setDisplayamount(UIUtil.getConvertedAmountInString(cu.getAmount()));
				cu.setDisplaydeduction(UIUtil.getConvertedAmountInString(cu.getDeductions()));
				cu.setDisplayPayeeBalance(UIUtil.getConvertedAmountInString(cu.getPayeebalance()));
			}
			response.setRows(modelList);
			int ps = DEFAULT_PAGE_SIZE;
			int n = modelList.size()/ps;
			if( modelList.size()/ps*ps != modelList.size()){
				n++;
			}
			response.setTotal(EMPTY_STRING + n);
			response.setPage(EMPTY_STRING + 1);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		return response;
	}
}
