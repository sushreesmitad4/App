package com.tarang.ewallet.walletui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.business.LoginService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.masterdata.util.CurrencyIds;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.BusinessInformation;
import com.tarang.ewallet.model.Customer;
import com.tarang.ewallet.model.Merchant;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.model.UserIP;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.constants.Login;
import com.tarang.ewallet.walletui.form.ChangePasswordForm;
import com.tarang.ewallet.walletui.form.LoginUserForm;
import com.tarang.ewallet.walletui.util.AllWalletsAmmount;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.ChangePasswordValidator;
import com.tarang.ewallet.walletui.validator.LoginValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


/**
 * @Author : kedarnath
 * @Date : Oct 19, 2012
 * @Time : 3:27:50 PM
 * @Version : 1.0
 * @Comments: Home Controller class for Login module
 */

@SuppressWarnings({"rawtypes", "unchecked"}) 
@Controller
@RequestMapping("/home")
public class HomeController implements Login, AttributeConstants, AttributeValueConstants {

	private static final Logger LOGGER = Logger.getLogger(HomeController.class);

	@Autowired
	private ChangePasswordValidator changePasswordValidator;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private LoginValidator loginValidator;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
    private CommonService commonService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private CryptService cryptService;
	
	/**
	 * Method to to show home page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showHomePage(Map model, @Valid LoginUserForm loginUserForm) {
		
		LOGGER.debug(" showHomePage ");
		return VIEW_RESOLVER_HOME_PAGE;
	}

	/**
	 * Method to show login page for customer and merchant.
	 * 
	 * @param model
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String showLoginPage(Map model, Locale locale, @Valid LoginUserForm loginUserForm, HttpServletRequest request) {
		LOGGER.debug("showHomePage method");
		HttpSession session = request.getSession();
		String sucMsg = (String) session.getAttribute(SUCCESS_MESSAGE);
		if (sucMsg != null) {
			model.put(SUCCESS_MESSAGE, context.getMessage(sucMsg, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		
		String errMsg = (String) session.getAttribute(ERROR_MESSAGE);
		if (errMsg != null) {
			model.put(ERROR_MESSAGE, context.getMessage(errMsg, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		if(null != session){
			session.invalidate();
			session = request.getSession(Boolean.TRUE);
		}
		return VIEW_RESOLVER_LOGIN_PAGE;
	}

	/**
	 * Method to validate login page for customer and merchant.
	 * 
	 * @param model
	 * @param locale
	 * @param loginUserForm
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String validateLoginPage(Map model,
			Locale locale, @Valid LoginUserForm loginUserForm,
			BindingResult result, HttpServletRequest request) {
		LOGGER.debug(" validateLoginPage ");
		HttpSession oldsession = request.getSession();
		if(oldsession != null){
			oldsession.invalidate();
		}
		HttpSession session = request.getSession(true);
		String user = getUserType(loginUserForm.getUserType());
		loginValidator.validate(loginUserForm, result);
		
		if (result.hasErrors()) {
			return VIEW_RESOLVER_LOGIN_PAGE;
		} else {
			try {
				CustomerDto customerDto = null;
				Authentication authentication = null;
				if(GlobalLitterals.CUSTOMER_USER_TYPE.equalsIgnoreCase(user)){
					CustomerDto cdto = new CustomerDto();
					customerDto = customerService.getCustomerByPhoneNo(loginUserForm.getEmail());
					if(null == customerDto){
						throw new WalletException(Common.CUSTOMER_MOBILE_NOT_EXIST);
					}
					
					authentication = loginService.login(customerDto.getEmailId(), loginUserForm.getPassword(), user,
							MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
							(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
				}else{
					authentication = loginService.login(loginUserForm.getEmail(), loginUserForm.getPassword(), user,
							MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
							(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
				}			
 				
				
				
				session.setAttribute(AUTHENTICATION_ID, authentication.getId());
				setPersonNameinSession(authentication, session);
				/*Other values are populated in method papulateSessionValues*/
				model.put(MODEL_ATTRIBUTE_LOG_USER_NAME, loginUserForm);
				
				// Validating user ip-address is existing or not
				String ipAddress = UIUtil.getClientIpAddr(request);
				UserIP userIp = commonService.getUserIPAddress(authentication.getId(), ipAddress);
				Boolean userIPFlag = false;
				if(userIp != null && userIp.getCode() != null){
					userIPFlag = true;
				}
				if(userIp == null){
					Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
					if(languageId == null){
						languageId = MasterDataConstants.DEFAULT_LANGUAGE;
					}
					/*Generate code and send mail to user*/
					commonService.sendOTPToUser(new UserIP(authentication.getId(), ipAddress, new Date(), null), languageId, 
							(String)session.getAttribute(NAME));
					
					//Audit Trail service
					auditTrailService.createAuditTrail(authentication.getId(), AuditTrailConstrain.MODULE_USER_LOGIN_OTP_CREATE, 
							AuditTrailConstrain.STATUS_CREATE, authentication.getEmailId(), authentication.getUserType());
					
					userIPFlag = true;
					session.setAttribute(SUCCESS_MESSAGE, Login.CODE_SENT_MAIL);
				}
				
				if(userIPFlag){
					/*The user has not yet login to system, reset login flag and redirect to login code page*/
					resetLoginFlagInAuthentication(authentication.getId());
					return UIUtil.redirectPath(AttributeValueConstants.LOGIN_CODE_PATH);
				} else{
					/*If success then only sets values in session*/
					papulateSessionValues(session, authentication);
					return redirectToSuccessPage(authentication, user);
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				model.put(ERROR_MESSAGE, context.getMessage(papulateErrorMessage(e), null, locale));
				return VIEW_RESOLVER_LOGIN_PAGE;
			}
		}
	}
	@RequestMapping(value = "/logincode", method = RequestMethod.GET)
	public String loginCodePage(@Valid LoginUserForm loginUserForm, HttpServletRequest request, Map model, Locale locale) {
		LOGGER.debug("loginCodePage");
		HttpSession session = request.getSession();
		String message = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(message != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(message, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		return LOGIN_ONE_TIME_IP_ADD_CODE;
	}

	/**
	 * Authentication for one time IP check
	 * @param model
	 * @param loginUserForm
	 * @param result
	 * @param request
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/logincode", method = RequestMethod.POST)
	public String loginCode(Map model, @Valid LoginUserForm loginUserForm, BindingResult result, HttpServletRequest request, Locale locale) {
		LOGGER.debug("loginCode");
		String code = loginUserForm.getCode();
		if(code.equals(EMPTY_STRING)){
			result.rejectValue(Login.CODE_VAR_TO_CHECK_IP_ADD, Login.CODE_REQUIRED);
			return LOGIN_ONE_TIME_IP_ADD_CODE ;
		}
		HttpSession session = request.getSession();
		Long authId = (Long)session.getAttribute(AUTHENTICATION_ID);
		String ipAddress = UIUtil.getClientIpAddr(request);
		try {
			Authentication authentication = commonService.getAuthentication(authId);
			if(null == authentication){
				throw new WalletException(Login.FAILS_TO_RET_USER_AUTH);
			}
			UserIP userIp = commonService.getUserIPAddress(authId, ipAddress);
			if(null == userIp){
				throw new WalletException(Login.FAILS_TO_RET_USER_AUTH);
			}
			UserIP oldUserIP = (UserIP)userIp.clone();
			if(userIp.getCode().equals(loginUserForm.getCode())){
				userIp.setCode(null);
				commonService.updateUserIPAddress(userIp);
				/*The user has a login to system, set login flag and redirect to success page*/
				setLoginFlagInAuthentication(authentication);
				/*Set user details into session*/
				papulateSessionValues(session, authentication);
				//Audit Trail service
				auditTrailService.createAuditTrail(authId, AuditTrailConstrain.MODULE_USER_LOGIN_OTP_UPDATE, 
						AuditTrailConstrain.STATUS_UPDATE, authentication.getEmailId(), authentication.getUserType(), oldUserIP, userIp);
				return redirectToSuccessPage(authentication, (String)session.getAttribute(USER_TYPE));
			} else{
				result.rejectValue(Login.CODE_VAR_TO_CHECK_IP_ADD, Login.CODE_DOES_NOT_MATCHES);
			}
		} catch (WalletException e) {
			LOGGER.error(e.getMessage() , e );
			String em = null;
			if(e.getMessage().contains(Login.FAILS_TO_RET_USER_AUTH)){
				em = Login.FAILS_TO_RET_USER_AUTH;
			} else if(e.getMessage().contains(Login.FAILS_TO_RET_USER_AUTH)){
				em = Login.FAILS_TO_RET_USER_AUTH;
			} else{
				em = Login.CODE_DOES_NOT_MATCHES;
			}
			model.put(ERROR_MESSAGE, context.getMessage(em, null, locale));
			LOGGER.error(e.getMessage(), e);
			return LOGIN_ONE_TIME_IP_ADD_CODE ; 
		} catch (CloneNotSupportedException e) {
			LOGGER.error(AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR, e);
			model.put(ERROR_MESSAGE, context.getMessage(Login.FAILS_TO_RET_USER_AUTH, null, locale));
			return LOGIN_ONE_TIME_IP_ADD_CODE ; 
		}
		return LOGIN_ONE_TIME_IP_ADD_CODE ; 
	}
	/**
	 * Method to to show Change password page, once the user password has reset
	 * or the user has login in first time.
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "changepassword", method = RequestMethod.GET)
	public String changePasswordPage(Map model, Locale locale,
			@Valid ChangePasswordForm changePasswordForm, HttpServletRequest request) {
		LOGGER.debug("changePasswordPage method");
		HttpSession session = request.getSession();
		model.put(ERROR_MESSAGE, context.getMessage(CommonConstrain.RESET_PASSWORD, null, locale));
		changePasswordForm.setEmailId(session.getAttribute(USER_ID).toString());
		return VIEW_RESOLVER_LOGIN_CHANGEPASSWORD;
	}

	@RequestMapping(value = "changepassword", method = RequestMethod.POST)
	public String changePasswordSubmitPage(Map model, Locale locale, @ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
			BindingResult result, HttpServletRequest request) {
		LOGGER.debug(" changePasswordSubmitPage ");
		HttpSession session = request.getSession();

		if ("cancel".equals(changePasswordForm.getMode())) {
			return UIUtil.redirectPath(AttributeValueConstants.HOME_LOGIN_PATH);
		}
		if (session != null && session.getAttribute(USER_ID) == null) {
			return VIEW_RESOLVER_SESSION_EXPIRED_CUSTOMER;
		}

		changePasswordValidator.validate(changePasswordForm, result);
		if (result.hasErrors()) {
			return VIEW_RESOLVER_LOGIN_CHANGEPASSWORD;
		} else {
			// reading data from ChangePasswordForm and set to Dto
			ChangePasswordDto changePasswordDto = new ChangePasswordDto();

			changePasswordDto.setEmailId(changePasswordForm.getEmailId());
			changePasswordDto.setOldPassword(changePasswordForm.getOldPassword());
			changePasswordDto.setNewPassword(changePasswordForm.getNewPassword());
			changePasswordDto.setConfirmNewPassword(changePasswordForm.getConfirmNewPassword());
			changePasswordDto.setUserType(session.getAttribute(USER_TYPE).toString());
			try {
				loginService.changePassword(changePasswordDto);
				//Audit Trail service
				changePasswordDto.setOldPassword(cryptService.encryptData(changePasswordDto.getOldPassword()));
				changePasswordDto.setNewPassword(cryptService.encryptData(changePasswordDto.getNewPassword()));
				auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_USER_CHANGE_PASSWORD, 
						AuditTrailConstrain.STATUS_EDIT, changePasswordDto.getEmailId(), changePasswordDto.getUserType(), changePasswordDto, changePasswordDto);
				String user = session.getAttribute(USER_TYPE).toString();
				session.setAttribute(CHANGE_PASSWORD_SUCS_MSG, context.getMessage(CHANGE_PASSWORD_SUCCESS, null, locale));
				if (user.equalsIgnoreCase(GlobalLitterals.CUSTOMER_USER_TYPE)) {
					return UIUtil.redirectPath(CUSTOMER_LOGIN_SUCCESS_VIEW);
				} else if (user.equalsIgnoreCase(GlobalLitterals.MERCHANT_USER_TYPE)) {
					return UIUtil.redirectPath(MERCHANT_LOGIN_SUCCESS_VIEW);
				}
				return UIUtil.redirectPath(CUSTOMER_LOGIN_SUCCESS_VIEW);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				model.put(ERROR_MESSAGE, context.getMessage(papulateErrorMessageForChangepassword(e), null, locale));
				return VIEW_RESOLVER_LOGIN_CHANGEPASSWORD;
			}
		}
	}
	
	@RequestMapping(value = "/allbalancerecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<AllWalletsAmmount> allWalletAccountRecords(Map model, Locale locale, HttpServletRequest request) {
		
		List<Object[]> balanceList = commonService.getCustomerBalancesByUserId((Long)request.getSession().getAttribute(AUTHENTICATION_ID));
		List<AllWalletsAmmount> allWalletsAmmountsList = new ArrayList<AllWalletsAmmount>();
		for(int i=0;i<balanceList.size();i++ ){
			AllWalletsAmmount allWalletsAmmount = new AllWalletsAmmount();
			allWalletsAmmount.setCurrency((Long)balanceList.get(i)[1]);
			allWalletsAmmount.setAmount((Double)(balanceList.get(i))[0]);
			allWalletsAmmount.setCurrencyName(MasterDataUtil.getCurrencyNames(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get((Long)balanceList.get(i)[1]));
			allWalletsAmmount.setAmountString(UIUtil.getConvertedAmountInString((Double)(balanceList.get(i))[0]));
			allWalletsAmmountsList.add(i,allWalletsAmmount);
		}
		JqgridResponse<AllWalletsAmmount> response = new JqgridResponse<AllWalletsAmmount>();
		response.setRows(allWalletsAmmountsList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = allWalletsAmmountsList.size() / ps;
		if( allWalletsAmmountsList.size() / ps * ps != allWalletsAmmountsList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
		
	}
	
	private static String papulateErrorMessage(Exception e) {
		LOGGER.debug( " papulateErrorMessage " );
		String errorMes = null;
		if (e.getMessage().contains(CommonConstrain.LOGIN_ERROR_INVALID_USER)) {
			errorMes = CommonConstrain.LOGIN_ERROR_INVALID_USER;
		} else if (e.getMessage().contains(CommonConstrain.EMAIL_VARIFICATION_NOT_DONE)) {
			errorMes = CommonConstrain.EMAIL_VARIFICATION_NOT_DONE;
		} else if (e.getMessage().contains(CommonConstrain.USER_BLOCK)) {
			errorMes = CommonConstrain.USER_BLOCK;
		} else if (e.getMessage().contains(CommonConstrain.USER_ACCOUNT_DELETED)) {
			errorMes = CommonConstrain.USER_ACCOUNT_DELETED;
		} else if (e.getMessage().contains(CommonConstrain.PASSWORD_MATCH_FAIL)) {
			errorMes = CommonConstrain.PASSWORD_MATCH_FAIL;
		} else if (e.getMessage().contains(CommonConstrain.EMAIL_MATCH_FAIL)) {
			errorMes = CommonConstrain.EMAIL_MATCH_FAIL;
		} else if (e.getMessage().contains(CommonConstrain.ACCOUNT_REJECTED)) {
			errorMes = CommonConstrain.ACCOUNT_REJECTED;
		} else if (e.getMessage().contains(CommonConstrain.LOGIN_STATUS)) {
			errorMes = CommonConstrain.LOGIN_STATUS;
		} else if (e.getMessage().contains(CommonConstrain.ACCOUNT_DEACTIVE)) {
			errorMes = CommonConstrain.ACCOUNT_DEACTIVE;
		} else {
			errorMes = CommonConstrain.EMAIL_OR_PASSWORD_FAIL;
		}
		return errorMes;
	}

	private String papulateErrorMessageForChangepassword(Exception e) {
		LOGGER.debug( " papulateErrorMessageForChangepassword " );
		String errorMes = null;
		if (e.getMessage().contains(CommonConstrain.EMAIL_MATCH_FAIL)) {
			errorMes = CommonConstrain.EMAIL_MATCH_FAIL;
		} else if (e.getMessage().contains(CommonConstrain.PASSWORD_MATCH_FAIL)) {
			errorMes = CommonConstrain.OLD_PASSWORD_MATCH_FAIL;
		} else if (e.getMessage().contains(CommonConstrain.OLD_AND_NEW_PASSWORD_SAME)) {
			errorMes = CommonConstrain.OLD_AND_NEW_PASSWORD_SAME;
		} else if (e.getMessage().contains(CommonConstrain.PASSWORD_HISTORY_EXIST)) {
			errorMes = PASSWORD_HISTORY_EXIST;
		} else {
			errorMes = CHANGE_PASSWORD_FAIL;
		}
		return errorMes;
	}

	private String getUserType(String ut) {
		LOGGER.debug( " getUserType " );
		String usertyps = ut;
		if (usertyps != null && !usertyps.equals(EMPTY_STRING)) {
			usertyps = usertyps.trim();
		}
		return "Customer".equalsIgnoreCase(usertyps) ? GlobalLitterals.CUSTOMER_USER_TYPE : GlobalLitterals.MERCHANT_USER_TYPE;
	}

	private void setPersonNameinSession(Authentication authentication, HttpSession session) throws WalletException{
		if(authentication.getUserType().equals(GlobalLitterals.CUSTOMER_USER_TYPE)){
			Long customerId = customerService.getCustomerId(authentication.getEmailId(), authentication.getUserType());
			String personName = customerService.getPersonName(authentication.getEmailId(), authentication.getUserType());
			Customer customer =customerService.getCustomerByAuthId(authentication.getId());
			PhoneNumber phoneNumber= commonService.getPhone(customer.getPhoneIdOne());
			session.setAttribute(DB_ID, customerId);
			session.setAttribute(NAME, personName);
			session.setAttribute(PHONE_NUMER,phoneNumber.getCode() +""+ phoneNumber.getPnumber());
			session.setAttribute(DEFAULT_CURRENCY,  CurrencyIds.INR);
			//null != customerDto2.getDefaultCurrency() ? customerDto2.getDefaultCurrency() :
		} else if (authentication.getUserType().equals(GlobalLitterals.MERCHANT_USER_TYPE)){
			Long longMerchantId = merchantService.getMerchantId(authentication.getEmailId(), authentication.getUserType());
			String legalName = merchantService.getLegalName(authentication.getEmailId());
			Merchant merchant =merchantService.getMerchantByAuthId(authentication.getId());
			BusinessInformation businessInfo = merchantService.getMerchantBusinessInfo(merchant.getId());
			PhoneNumber phoneNumber= commonService.getPhone(businessInfo.getPhone());
			session.setAttribute(DB_ID, longMerchantId);
			session.setAttribute(NAME, legalName);
			//session.setAttribute(PHONE_NUMER,phoneNumber.getCode() +""+ phoneNumber.getPnumber());
			session.setAttribute(DEFAULT_CURRENCY,  CurrencyIds.INR);
		}
	}
	
	private void resetLoginFlagInAuthentication(Long authId) throws WalletException{
		Authentication newAuth = commonService.getAuthentication(authId);
		newAuth.setLoginStatus(Boolean.FALSE);
		commonService.updateAuthentication(newAuth);
	}
	
	private void setLoginFlagInAuthentication(Authentication authentication) throws WalletException{
		authentication.setLoginStatus(Boolean.TRUE);
		commonService.updateAuthentication(authentication);
	}
	
	private void papulateSessionValues(HttpSession session, Authentication authentication){
		session.setAttribute(USER_ID, authentication.getEmailId());
		session.setAttribute(USER_TYPE, authentication.getUserType());
		session.setAttribute(USER_STATUS, authentication.getStatus());
		session.setAttribute("lastLoginTime", authentication.getLastLogin().getTime());
		session.removeAttribute(APPLICATIONS_SESSION_NAME);
	}
	
	private String redirectToSuccessPage(Authentication authentication, String user){
		if (user.equalsIgnoreCase(GlobalLitterals.CUSTOMER_USER_TYPE)) {
			if (authentication.isResetPassword()) {
				return UIUtil.redirectPath(AttributeValueConstants.HOME_CHANGE_PASS_PATH);
			}
			return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_PATH);
		} else if (user.equalsIgnoreCase(GlobalLitterals.MERCHANT_USER_TYPE)) {
			if (authentication.isResetPassword()) {
				return UIUtil.redirectPath(AttributeValueConstants.HOME_CHANGE_PASS_PATH);
			}
			return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_PATH);
		}
		return null;
	}
	
	
}
