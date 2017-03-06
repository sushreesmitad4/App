/**
 * 
 */
package com.tarang.ewallet.walletui.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.MerchantPayInfo;
import com.tarang.ewallet.model.UserIP;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.reports.business.ReportsService;
import com.tarang.ewallet.transaction.business.FraudCheckService;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.service.FileService;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Merchant;
import com.tarang.ewallet.walletui.form.ImageUploadForm;
import com.tarang.ewallet.walletui.form.MerchantForm;
import com.tarang.ewallet.walletui.util.CustomerDataUtil;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MerchantDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.MerchantValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


/**
* @author  : prasadj
* @date    : Oct 5, 2012
* @time    : 8:52:17 AM
* @version : 1.0.0
* @comments: MerchantController is a controller class for merchant activities.
 *            This includes registration, Change password, update profile. 
* 			 
*/
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("/merchant")
public class MerchantController implements Merchant, AttributeConstants, AttributeValueConstants, GlobalLitterals {
	
	private static final Logger LOGGER = Logger.getLogger(MerchantController.class);
	
	@Autowired
    private MerchantService merchantService;
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
	private AuditTrailService auditTrailService;
    
    @Autowired
    private ReportsService reportsService;

    @Autowired
	private FraudCheckService fraudCheckService;

    @Autowired
  	private ApplicationContext context;
    
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String merchantPageLoad(HttpServletRequest request, Map model, 
			Locale locale, @Valid MerchantForm merchantForm) {
		LOGGER.debug( " merchantPageLoad " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String changePassMsg = (String)session.getAttribute(CHANGE_PASSWORD_SUCS_MSG);
		if(changePassMsg != null){
			model.put(SUCCESS_MESSAGE, changePassMsg);
			session.removeAttribute(CHANGE_PASSWORD_SUCS_MSG);
		}
		
		Long authId = (Long)request.getSession().getAttribute(AUTHENTICATION_ID);
		Long merchantId = (Long)request.getSession().getAttribute(DB_ID);
		UserWallet userWallet = null;
		MerchantDto dto = null;
		Long currency = null;
		try {
			dto = merchantService.getMerchantDetailsById(merchantId);
			currency = MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), dto.getCountryBI());
			userWallet = commonService.getUserWallet(authId, currency);
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
		}
		if(userWallet == null){
			userWallet = new UserWallet();
		}
		model.put("userwalletamount", userWallet.getAmount() != null? UIUtil.getConvertedAmountInString(userWallet.getAmount()):0.00);
		model.put("userwalletcurrency" , MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
				dto.getCountryBI()));
		model.put("userstatusname", MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataUtil.MD_USER_STATUSES).get(session.getAttribute(USER_STATUS)));
		String balanceUrl = WALLET_PATH_PREFIX + "home/allbalancerecords";
		request.setAttribute("urlBalanceList", balanceUrl);
		String url = WALLET_PATH_PREFIX + "merchant/txnrecords";
		request.setAttribute("urlAccountList", url);
		return MERCHANT_LOGIN_SUCCESS_VIEW;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrationPageLoad(HttpServletRequest request, Map model, 
			@Valid MerchantForm merchantForm, Locale locale) {
		LOGGER.debug(  " registrationPageLoad " );

		model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
			request.getSession().getServletContext(), 
			(Long) request.getSession().getAttribute(LANGUAGE_ID),MasterDataConstants.HINT_QUESTIONS));
		return REGISTRATION_VIEW;
	}
  
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationNext(HttpServletRequest request,
			Map model, Locale locale,
			@Valid MerchantForm merchantForm, BindingResult result) {
		LOGGER.debug( " registrationNext " );
		String adminOrmerchant = MERCHANT_PATH;
		String mode = merchantForm.getMode();
		MerchantValidator merchantValidator = new MerchantValidator(true, adminOrmerchant, REGISTRATION);
		merchantValidator.validate(merchantForm, result); 	
		if(mode != null && mode.equalsIgnoreCase(REQUEST_FOR_REGISTRATION_AFTER_24HOURS)){
			isPhoneNumberExist(result, merchantForm, UPDATE , adminOrmerchant);
		} else {
			isPhoneNumberExist(result, merchantForm, REGISTRATION, adminOrmerchant);
		}
		/*   FRAUD CHECK START-13   */
		try {
			Boolean flag = fraudCheckService.domainCheck(merchantForm.getEmailId());
			if(flag){
				result.rejectValue(EMAIL_ID, CommonConstrain.EMAIL_DOMAIN_NOT_ALLOWED);
			}
		} catch (WalletException e) {
			LOGGER.info(e.getMessage() , e);
		}
		         /*   FRAUD CHECK END   */
		//keep first email id as old email id
		if(merchantForm.getOldEmailId() != null && merchantForm.getOldEmailId().equals(EMPTY_STRING)){
			merchantForm.setOldEmailId(merchantForm.getEmailId());
		}
		Boolean isRequestForRegistrationAfter24hours = Boolean.FALSE;
		Long recordStatus = null;
		Date creationDate = null;
		Authentication authentication = null;
		Boolean isRetriveExistingRecords = Boolean.FALSE;
		try{
			recordStatus = commonService.emailIdExist(merchantForm.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE);
			if(recordStatus != null){
				authentication = commonService.getAuthentication(merchantForm.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE);
			}
		} catch (WalletException e) {
			LOGGER.error(e.getMessage() , e);
		}
		if(authentication != null){
			creationDate = authentication.getCreationDate();
		}
		isRequestForRegistrationAfter24hours = CommonValidator.validateEmail(recordStatus, creationDate, result);
		if (result.hasErrors()) {
			model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.HINT_QUESTIONS));
			return REGISTRATION_VIEW;
		} else {
			
			if(mode != null && !"back".equalsIgnoreCase(mode) && 
					merchantForm.getOldEmailId().equals(merchantForm.getEmailId())){
				isRetriveExistingRecords = Boolean.TRUE;
			} else if(mode != null && "back".equalsIgnoreCase(mode) && 
					!merchantForm.getOldEmailId().equals(merchantForm.getEmailId())){
				isRetriveExistingRecords = Boolean.TRUE;
			} else if(mode != null && "back".equalsIgnoreCase(mode) && 
					merchantForm.getOldEmailId().equals(merchantForm.getEmailId())){
				//continue existing record in form
				isRetriveExistingRecords = Boolean.FALSE;
			}
			if(isRequestForRegistrationAfter24hours){
				//retrieve old records and continue for registration
				if(isRetriveExistingRecords){
					try {
						Long longMerchantId = null;
						longMerchantId = merchantService.getMerchantId(merchantForm.getEmailId(), MERCHANT_USER_TYPE);
						MerchantDto merchantDto = null;
						merchantDto = merchantService.getMerchantDetailsById(longMerchantId);
						MerchantForm newmerchantForm = new MerchantForm();
						MerchantDataUtil.convertMerchantDtoToMerchantForm(newmerchantForm, merchantDto);
						
						newmerchantForm.setEmailId(merchantForm.getEmailId());
						newmerchantForm.setOldEmailId(merchantForm.getEmailId());
						newmerchantForm.setPhoneCountryCode1(merchantForm.getPhoneCountryCode1());
						newmerchantForm.setPhoneNumber(merchantForm.getPhoneNumber());
						//newmerchantForm.setConfirmEmailId(merchantForm.getConfirmEmailId());
						newmerchantForm.setPassword(merchantForm.getPassword());
						newmerchantForm.setQuestion1(merchantForm.getQuestion1());
						newmerchantForm.setHint1(merchantForm.getHint1());
						newmerchantForm.setMode(REQUEST_FOR_REGISTRATION_AFTER_24HOURS);
						model.put(ERROR_MESSAGE, context.getMessage(Common.ERROR_MSG_RECORD_EXIST_UPDATE_AND_COMPLETE_REGISTRATION, null, locale));
						UIUtil.populateMerchantMapValues(request, model, newmerchantForm.getCountryBI(), newmerchantForm.getCountryBO(), 
								newmerchantForm.getBusinessCategory(), newmerchantForm.getStatus(), Boolean.FALSE);
						model.put(MERCHANT_FORM, newmerchantForm);
					} catch (WalletException e) {
						LOGGER.error(e.getMessage() , e);
						model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
								request.getSession().getServletContext(), 
								(Long) request.getSession().getAttribute(LANGUAGE_ID),
								MasterDataConstants.HINT_QUESTIONS));
						model.put(ERROR_MESSAGE, context.getMessage(UserValidator.UNABLE_TO_PROCESS_MSG, null, locale));
						return REGISTRATION_VIEW;
					}
				} else{
					//continue existing record in form and change the mode to call update record
					merchantForm.setMode(REQUEST_FOR_REGISTRATION_AFTER_24HOURS);
					UIUtil.populateMerchantMapValues(request, model, merchantForm.getCountryBI(), merchantForm.getCountryBO(), 
							merchantForm.getBusinessCategory(), merchantForm.getStatus(), Boolean.FALSE);
				}
			} else{
				//create new registration
				if(!isRetriveExistingRecords){  
					merchantForm.setMode(EMPTY_STRING);
					UIUtil.populateMerchantMapValues(request, model, merchantForm.getCountryBI(), merchantForm.getCountryBO(), 
							merchantForm.getBusinessCategory(), merchantForm.getStatus(), Boolean.FALSE);
				} else{
					MerchantForm newmerchantForm = new MerchantForm();
					newmerchantForm.setEmailId(merchantForm.getEmailId());
					newmerchantForm.setOldEmailId(merchantForm.getEmailId());
					//newmerchantForm.setConfirmEmailId(merchantForm.getConfirmEmailId());
					newmerchantForm.setPhoneCountryCode1(merchantForm.getPhoneCountryCode1());
					newmerchantForm.setPhoneNumber(merchantForm.getPhoneNumber());
					newmerchantForm.setPassword(merchantForm.getPassword());
					newmerchantForm.setQuestion1(merchantForm.getQuestion1());
					newmerchantForm.setHint1(merchantForm.getHint1());
					newmerchantForm.setMode(EMPTY_STRING);
					UIUtil.populateMerchantMapValues(request, model, newmerchantForm.getCountryBI(), newmerchantForm.getCountryBO(), 
							newmerchantForm.getBusinessCategory(), newmerchantForm.getStatus(), Boolean.FALSE);
					model.put(MERCHANT_FORM, newmerchantForm);
				}
				
			}
			return REGISTRATION_BUSINFO_VIEW;
		}
	}
	
	@RequestMapping(value = "/registrationsave", method = RequestMethod.POST)
	public String registrationPageTwoSubmit(HttpServletRequest request,
			HttpServletResponse response, Map model, Locale locale,
			@Valid MerchantForm merchantForm, BindingResult result) {
		LOGGER.debug( " registrationPageTwoSubmit " );
		try{
			String mode = merchantForm.getMode();
			if (mode != null && "back".equalsIgnoreCase(mode)) {
				model.put(MERCHANT_FORM, merchantForm);
				LOGGER.debug(" registrationPageTwoSubmit :: mode back");
				model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID),
						MasterDataConstants.HINT_QUESTIONS));
				return REGISTRATION_VIEW;
			}
			String adminOrmerchant = MERCHANT_PATH;
			MerchantValidator merchantValidator = new MerchantValidator(false, adminOrmerchant, REGISTRATION);
			merchantValidator.validate(merchantForm, result);
			if(mode != null && mode.equalsIgnoreCase(REQUEST_FOR_REGISTRATION_AFTER_24HOURS)){
				isPhoneNumberExist(result, merchantForm, UPDATE , adminOrmerchant);
			} else {
				isPhoneNumberExist(result, merchantForm, REGISTRATION, adminOrmerchant);
			}
			
			model.put(MERCHANT_FORM, merchantForm);
			if (!result.hasErrors()) {
				Long recordStatus = null;
				Date creationDate = null;
				Authentication authentication = null;
				try{
					recordStatus = commonService.emailIdExist(merchantForm.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE);
					if(recordStatus != null){
						authentication = commonService.getAuthentication(merchantForm.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE);
					}
				} catch (WalletException e) {
					LOGGER.error(e.getMessage() , e);
				}
				if(authentication != null){
					creationDate = authentication.getCreationDate();
				}
				if(CustomerDataUtil.validateEmailForADuration(recordStatus, creationDate, model, locale)){
					model.put(MERCHANT_FORM, merchantForm);
					LOGGER.debug(" registrationPageTwoSubmit :: email already registered in this duration");
					model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(), 
								(Long) request.getSession().getAttribute(LANGUAGE_ID),MasterDataConstants.HINT_QUESTIONS));
					model.put(ERROR_MESSAGE, context.getMessage(Common.EMAIL_ALREADY_REGISTRED_DURATION_ERRMSG, null, locale));
					return REGISTRATION_VIEW;
				} 
				MerchantDto merchantDto = merchantForm.getMerchantDto();
				merchantDto.setIpAddress(UIUtil.getClientIpAddr(request));
				merchantDto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
				merchantDto.setDefaultCurrency(MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), 
						merchantForm.getCountryBI()));
				MerchantDto merchantDto1 = null;
				if(mode != null && mode.equalsIgnoreCase(REQUEST_FOR_REGISTRATION_AFTER_24HOURS)){
					Long longMerchantId = merchantService.getMerchantId(merchantForm.getEmailId(), MERCHANT_USER_TYPE);
					MerchantDto oldMerchantDto = merchantService.getMerchantDetailsById(longMerchantId);
					
					Boolean active = merchantForm.getActive();
					Boolean blocked = merchantForm.getBlocked();
					Long status = merchantForm.getStatus();
					merchantDto.setId(longMerchantId);
					merchantDto.setActive(active);
					merchantDto.setBlocked(blocked);
					merchantDto.setStatus(status);
					
					if(merchantForm.getCodeCheck()){
						merchantDto.setMerchantCode(merchantForm.getMerchantCode().trim());
						merchantDto.setSuccessUrl(merchantForm.getSuccessUrl().trim()); 
						merchantDto.setFailureUrl(merchantForm.getFailureUrl().trim());
					} else {
						merchantDto.setMerchantCode(null);
						merchantDto.setSuccessUrl(null);
						merchantDto.setFailureUrl(null);
					}
					merchantDto1 = merchantService.registrationAfter24hours(merchantDto);
					//Audit Trail service
					auditTrailService.createAuditTrail(oldMerchantDto.getAuthenticationId(), 
							AuditTrailConstrain.MODULE_MERCHANT_UPDATE, AuditTrailConstrain.STATUS_UPDATE, 
							oldMerchantDto.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE, oldMerchantDto, merchantDto);
				} else {
					merchantDto1 = merchantService.newregistration(merchantDto);
					//Audit Trail service
					auditTrailService.createAuditTrail(merchantDto1.getAuthenticationId(), AuditTrailConstrain.MODULE_MERCHANT_REGISTRATION, 
							AuditTrailConstrain.STATUS_REGISTRATION, merchantDto1.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE);				
					// set default preferences
					PreferencesDto preferencesDto = new PreferencesDto();
					preferencesDto.setCurrency(CommonConstrain.DEFAULT_CURRENCY);
				    preferencesDto.setLanguage(CommonConstrain.DEFAULT_LANGUAGE);
					preferencesDto.setAuthentication(merchantDto1.getAuthenticationId());
					commonService.createPreferences(preferencesDto);
				}
				
				 //************   FRAUD CHECK START - 3,7  ***************//
				Integer hours = utilService.getRegistrationAllowedHours();
				Integer noOfAccounts = utilService.getRegistrationAllowedAccounts();
				Date fromDate= DateConvertion.getPastDateByHours(hours);
				Boolean flag = fraudCheckService.ipMultipleAccountsVelocity(merchantDto.getIpAddress(), fromDate, new Date(), noOfAccounts);
				commonService.updateIpAddressCheck(merchantDto1.getAuthenticationId(), flag);
				hours = utilService.getEmailPatternAllowedHours();
				noOfAccounts = utilService.getEmailPatternAllowedAccounts();
				fromDate= DateConvertion.getPastDateByHours(hours);
				flag = fraudCheckService.emailPatternCheck(merchantDto.getEmailId(), fromDate, new Date(), noOfAccounts);
				commonService.updateEmailPatternCheck(merchantDto1.getAuthenticationId(), flag);
				 //************   FRAUD CHECK END   ***************//
				
				// Validating user ip-address is existing or not and login with old ipaddress will allow here
				UserIP userIp = commonService.getUserIPAddress(merchantDto1.getAuthenticationId(), merchantDto1.getIpAddress());
				if(null == userIp){
					//saving user ip-address
					commonService.saveUserIPAddress(new UserIP(merchantDto1.getAuthenticationId(), merchantDto1.getIpAddress(), new Date(), null));
				}
				return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_REG_SUCC_PATH);
			}
		} catch (NullPointerException e) {
			LOGGER.error(e.getMessage() , e );
			model.put(ERROR_MESSAGE, context.getMessage("merchant.registration.failed", null, locale));
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);	
			String em = null;
			if(e instanceof NullPointerException){
				em = UserValidator.UNABLE_TO_PROCESS_MSG;
			} else if(e.getMessage().contains("ConstraintViolationException")){
				em = "merchant.registration.failed.email.exist";
			} else if(e.getMessage().contains("DataIntegrityViolationException")){
				em = "merchant.registration.failed.db.error";
			} else if(e.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
				em = Common.DUPLICATE_PHONE_NO_ERR;
			} else {
				em = "merchant.registration.failed";
			}
			model.put(ERROR_MESSAGE, context.getMessage(em, null, locale));
		}
		
		UIUtil.populateMerchantMapValues(request, model, merchantForm.getCountryBI(), 
				merchantForm.getCountryBO() , merchantForm.getBusinessCategory(), null, Boolean.FALSE);
		
		return REGISTRATION_BUSINFO_VIEW;
	}
	
	@RequestMapping(value = "/registrationsuccess", method = RequestMethod.GET)
	public String registrationSuccessPage(HttpServletRequest request, Map model) {
		LOGGER.debug(  " registrationSuccessPage " );
		return REGISTRATION_SUCCESS_VIEW;
	}
	
	/**
	 * @comments: This method is call when customer click on registration verification link from his/her email.
	 */
	@RequestMapping(value = "/emailStatusUpdate", method = RequestMethod.GET)
	public String emailStatusUpdate(HttpServletRequest request,
			HttpServletResponse response) throws WalletException {
		LOGGER.debug( " emailStatusUpdate " );
		HttpSession session = request.getSession();
		try{
			Long id = Long.parseLong(request.getParameter(ID));
			commonService.registrationEmailVarification(id);
			session.setAttribute(SUCCESS_MESSAGE, CommonConstrain.EMAIL_VARIFIED_SUCCESS_MSG);
			//Audit Trail service
			auditTrailService.createAuditTrailForEmailVarification(id, AuditTrailConstrain.MODULE_MERCHANT_EMAILVERIFICATION_UPDATE, 
					AuditTrailConstrain.STATUS_UPDATE, commonService.getUserEmailById(id), GlobalLitterals.MERCHANT_USER_TYPE);	
			
			return UIUtil.redirectPath(AttributeValueConstants.HOME_LOGIN_PATH);
		} catch(NullPointerException ex){
			LOGGER.error(ex.getMessage() , ex);
			session.setAttribute(ERROR_MESSAGE, CommonConstrain.EMAIL_VARIFIED_FAILED_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.HOME_LOGIN_PATH);
		} catch(Exception e){
			LOGGER.error(e.getMessage() , e);
			String em = null;
			if(e.getMessage().contains(CommonConstrain.EMAIL_VARIFICATION_LINK_EXPIRED)){
				em = CommonConstrain.EMAIL_VARIFICATION_LINK_EXPIRED;
			} else {
				em = CommonConstrain.EMAIL_VARIFIED_FAILED_MSG;
			}
			session.setAttribute(ERROR_MESSAGE, em);
			return UIUtil.redirectPath(AttributeValueConstants.HOME_LOGIN_PATH);
		}
	}
	
	/* This method will called when clicked update profile in merchant profile */
	@RequestMapping(value = "/updateprofile", method = RequestMethod.GET)
	public String merchantdetails(HttpServletRequest request, Map model,
			Locale locale, @Valid MerchantForm merchantForm) {
		HttpSession session = request.getSession();
		/* In case session expired in update profile page */
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String merchantId = EMPTY_STRING;
		String userId = (String) session.getAttribute(USER_ID);
		String userType = (String) session.getAttribute(USER_TYPE);
		Long longMerchantId = null;
		try {
			longMerchantId = merchantService.getMerchantId(userId, userType);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.UNABLE_TO_PROCESS_MSG, null, locale));
		}
		merchantId = longMerchantId != null ? longMerchantId.toString() : EMPTY_STRING;
		MerchantDto merchantDto = null;
		try {
			merchantDto = merchantService.getMerchantDetailsById(Long.parseLong(merchantId));
			MerchantDataUtil.convertMerchantDtoToMerchantForm(merchantForm, merchantDto);
			
			merchantForm.setId(Long.parseLong(merchantId));
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage() , e);
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.UNABLE_TO_PROCESS_MSG, null, locale));
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.UNABLE_TO_PROCESS_MSG, null, locale));
		}
		UIUtil.populateMerchantMapValues(request, model, merchantForm.getCountryBI(), 
				merchantForm.getCountryBO() , merchantForm.getBusinessCategory(), null, Boolean.TRUE);

		model.put(MERCHANT_FORM, merchantForm);
		return MERCHANT_UPDATE_PROFILE;
	}
	
	/* This method will called when clicked update profile in merchant profile */
	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	public String merchantupdate(HttpServletRequest request, Locale locale,
			Map model, @Valid MerchantForm merchantForm, BindingResult result) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String mode = merchantForm.getMode();
		Long id = merchantForm.getId();
		/* for differentiating the update by ADMIN */
		request.setAttribute(MERCHANT_FORM, merchantForm);
		
		UIUtil.populateMerchantMapValues(request, model, merchantForm.getCountryBI(), 
				merchantForm.getCountryBO() , merchantForm.getBusinessCategory(), null, Boolean.TRUE);

		if (UPDATE.equalsIgnoreCase(mode)) {
			String adminOrmerchant = MERCHANT_PATH;
			MerchantValidator merchantMgmtValidator = new MerchantValidator(false, adminOrmerchant, "update");
			merchantForm.setTerms(true);			

			merchantMgmtValidator.validate(merchantForm, result);
			isPhoneNumberExist(result, merchantForm, UPDATE , adminOrmerchant);
			if (result.hasErrors()) {
				request.setAttribute(MERCHANT_FORM, merchantForm);
				return MERCHANT_UPDATE_PROFILE;
			}

			Boolean active = merchantForm.getActive();
			Boolean blocked = merchantForm.getBlocked();
			Long status = merchantForm.getStatus();
			MerchantDto merchantDto = merchantForm.getMerchantDto();
			merchantDto.setId(id);
			merchantDto.setActive(active);
			merchantDto.setBlocked(blocked);
			merchantDto.setStatus(status);
			if(merchantForm.getCodeCheck()){
				merchantDto.setMerchantCode(merchantForm.getMerchantCode().trim());
				merchantDto.setSuccessUrl(merchantForm.getSuccessUrl().trim()); 
				merchantDto.setFailureUrl(merchantForm.getFailureUrl().trim());
			} else{
				merchantDto.setMerchantCode(null);
				merchantDto.setSuccessUrl(null);
				merchantDto.setFailureUrl(null);
			}
			try {
				MerchantDto oldMerchantDto = merchantService.getMerchantDetailsById(merchantForm.getId());
				merchantService.updateMerchantDetails(merchantDto, MERCHANT_PATH);
				//Audit Trail service
				auditTrailService.createAuditTrail(oldMerchantDto.getAuthenticationId(), 
						AuditTrailConstrain.MODULE_MERCHANT_UPDATE, AuditTrailConstrain.STATUS_UPDATE, 
						oldMerchantDto.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE, oldMerchantDto, merchantDto);
				session.setAttribute(NAME, merchantDto.getBusinessLegalname());
				model.put("successMessage", context.getMessage("merchant.updated.successfully", null, locale));
			}  catch (Exception e) {
				LOGGER.error(e.getMessage() , e);
				String em = null;
				if(e instanceof NullPointerException){
					em = UserValidator.UNABLE_TO_PROCESS_MSG;
				} else if(e.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
					em = Common.DUPLICATE_PHONE_NO_ERR;
				} else{
					em = UserValidator.UNABLE_TO_PROCESS_MSG;
				}
				model.put(ERROR_MESSAGE, context.getMessage(em, null, locale));
			}	

		}
		if (CANCEL.equalsIgnoreCase(mode)) {
			return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_PATH);
		}
		merchantForm.setExistPersonPhoneNo(merchantForm.getPhoneCountryCode1()+merchantForm.getPhoneNumber());
		merchantForm.setExistServicePhoneNo(merchantForm.getCode()+merchantForm.getPhone());
		return MERCHANT_UPDATE_PROFILE;
	}
	
	@RequestMapping(value="/underconstruction", method = RequestMethod.GET)
	public String underConstructionPage(HttpServletRequest request, Map model){
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		LOGGER.debug( " underConstructionPage " );
		return MERCHANT_VIEW_UNDERCONSTRUCTION;
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
			List<CustomerReportModel> modelList = reportsService.merchantLastNTransactions(
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
			int n = modelList.size() / ps;
			if( modelList.size() / ps * ps != modelList.size()){
				n++;
			}
			response.setTotal(EMPTY_STRING + n);
			response.setPage(EMPTY_STRING + 1);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		return response;
	}
	
	@RequestMapping(value = "/imageUpload", method = RequestMethod.GET)
	public String displayForm(Map model, HttpServletRequest request ) {
		HttpSession session = request.getSession();
		LOGGER.debug( " displayForm " );
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		boolean flag = false;
		flag = isMerchantPayInfoChecked(session);
		model.put("flag", flag);
		return WALLET_MERCHANT_FILE_UPLOAD;
	}
	
	@RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
	public String save(Map model, HttpServletRequest request, @Valid ImageUploadForm imageUploadForm, Locale locale) {
		LOGGER.debug( " save " );
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		boolean flag = false;
		flag = isMerchantPayInfoChecked(session);
		if(flag){
			model.put(ERROR_MESSAGE, context.getMessage(ERROR_MSG_IMAGE_UNABLE_TO_UPLOAD_IS_ONLINE_PAYMENT_NOT_SET, null, locale));
			return WALLET_MERCHANT_FILE_UPLOAD;
		}
		MultipartFile files = imageUploadForm.getFileData();
		String fileName  = files.getOriginalFilename();
		
		int mid = fileName.lastIndexOf('.');
		String ext = fileName.substring(mid + 1, fileName.length());  
		String configuredExtension = utilService.getUploadImageFileExtenstion();
		Boolean saveFileFlag = fileService.isValidExtention(configuredExtension, ext);
		Integer configuredFileSize = utilService.getUploadImageFileSize();
		String configuredFileLocation = utilService.getUploadImageFileLocation(); 		
		if(files.getSize() == 0){
			model.put(ERROR_MESSAGE, context.getMessage(LOGO_REQUIRED,  null, locale));
			return WALLET_MERCHANT_FILE_UPLOAD;
		} else if(!saveFileFlag){
			model.put(ERROR_MESSAGE, context.getMessage(IMAGE_UPLOAD_FILE_FORMATE, new Object[] { configuredExtension }, locale));
			return WALLET_MERCHANT_FILE_UPLOAD;
		} else if( files.getSize() > configuredFileSize ){
			model.put(ERROR_MESSAGE, context.getMessage(IMAGE_UPLOAD_FILE_SIZE,   new Object[] { configuredFileSize }, locale));
			return WALLET_MERCHANT_FILE_UPLOAD;
		} else {
			OutputStream out = null;
			try {
				Long authId = (Long)session.getAttribute(AUTHENTICATION_ID);
				String exisFileName = fileService.getImageFileName(configuredFileLocation, authId);
				if(exisFileName != null){
					fileService.deleteImageFile(configuredFileLocation, exisFileName);
				}	
				byte[] bytes = files.getBytes();
			    out = new FileOutputStream(configuredFileLocation + "\\" + authId + "." + ext);
			    out.write(bytes);
				model.put(SUCCESS_MESSAGE, context.getMessage(IMAGE_UPLOAD_SUCCESFULLY, null, locale));
				return WALLET_MERCHANT_FILE_UPLOAD;
			} catch(Exception ex){
				LOGGER.error(ex.getMessage() , ex);
				model.put(ERROR_MESSAGE, context.getMessage(IMAGE_UNABLE_TO_STORE_IMAGE, null, locale));
				return WALLET_MERCHANT_FILE_UPLOAD;
			} finally {
			    if (out != null){
			    	try{
			    		out.close();
			    	} catch(Exception e){
			    		LOGGER.error(e.getMessage() , e);
			    	}
			    }
			}
		}
	}
	
	private Boolean isMerchantPayInfoChecked(HttpSession session){
		Boolean flag = false;
		try {
			MerchantPayInfo merchantPayInfo = merchantService.getMerchantPayInfo((String) session.getAttribute(USER_ID));
			if(merchantPayInfo == null || (merchantPayInfo != null && merchantPayInfo.getMerchantCode() == null) ){
				flag = true;
			}
		} catch (WalletException e) {
			LOGGER.error(e.getMessage() , e);
		}
		return flag;
	}

	/**This method used for Unique phone number validation
	 * @param result
	 * @param form
	 * @param regOrUpdate
	 * @param adminOrmerchant
	 */
	private void isPhoneNumberExist(BindingResult result, MerchantForm form, String regOrUpdate, String adminOrmerchant){
		if (UPDATE.equals(regOrUpdate) && adminOrmerchant.equals(MERCHANT_PATH)) {
			String newPhoneNo = form.getPhoneCountryCode1() + form.getPhoneNumber();
			String existPhoneNo = form.getExistPersonPhoneNo();
			String newServicePhoneNo = form.getCode() + form.getPhone();
			String existServicePhoneNo = form.getExistServicePhoneNo();			
			if(!existPhoneNo.equals(EMPTY_STRING) && !newPhoneNo.equalsIgnoreCase(existPhoneNo) && commonService.phoneNOExist(form.getPhoneCountryCode1(), form.getPhoneNumber())){			
				result.rejectValue(MerchantValidator.PHONE_NUBER_VAR, MerchantValidator.DUPLICATE_PHONE_NO_ERR);	
			}	
			if(!newServicePhoneNo.equals(EMPTY_STRING) && newServicePhoneNo.equalsIgnoreCase(newPhoneNo)){
				result.rejectValue(MerchantValidator.PHONE_VAR, MerchantValidator.SERVICE_PHONE_NUBER_EXIST);	
			} else if(!existServicePhoneNo.equals(EMPTY_STRING) && !newServicePhoneNo.equalsIgnoreCase(existServicePhoneNo) && commonService.phoneNOExist(form.getCode(), form.getPhone())){
				result.rejectValue(MerchantValidator.PHONE_VAR, MerchantValidator.DUPLICATE_PHONE_NO_ERR);
			}
		} else if (regOrUpdate.equals(REGISTRATION) && adminOrmerchant.equals(MERCHANT_PATH)) {
			String newPhoneNo = form.getPhoneCountryCode1() + form.getPhoneNumber();
			String newServicePhoneNo = form.getCode() + form.getPhone();
						
			if(!newPhoneNo.equals(EMPTY_STRING) && commonService.phoneNOExist(form.getPhoneCountryCode1(), form.getPhoneNumber())){			
				result.rejectValue(MerchantValidator.PHONE_NUBER_VAR, MerchantValidator.DUPLICATE_PHONE_NO_ERR);	
			}	
			if(!newServicePhoneNo.equals(EMPTY_STRING) && newServicePhoneNo.equalsIgnoreCase(newPhoneNo)){
				result.rejectValue(MerchantValidator.PHONE_VAR, MerchantValidator.SERVICE_PHONE_NUBER_EXIST);	
			}if(!newServicePhoneNo.equals(EMPTY_STRING) && commonService.phoneNOExist(form.getCode(), form.getPhone())){
				result.rejectValue(MerchantValidator.PHONE_VAR, MerchantValidator.DUPLICATE_PHONE_NO_ERR);
			}
			
		}
	}
}