package com.tarang.ewallet.walletui.controller;

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

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.business.LoginService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.dto.ForgotPasswordDto;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.walletui.controller.constants.Login;
import com.tarang.ewallet.walletui.form.ChangePasswordForm;
import com.tarang.ewallet.walletui.form.ForgotPasswordForm;
import com.tarang.ewallet.walletui.form.LoginUserForm;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.ChangePasswordValidator;
import com.tarang.ewallet.walletui.validator.ForgotPasswordValidator;


/**
 * @Author : kedarnath
 * @Date : Oct 19, 2012
 * @Time : 3:27:50 PM
 * @Version : 1.0
 * @Comments: Admin User Login Controller class for Login module
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/merchantlogin")
public class MerchantLoginController implements Login, AttributeConstants, AttributeValueConstants {

	private static final Logger LOGGER = Logger.getLogger(MerchantLoginController.class);

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private ForgotPasswordValidator forgotPasswordValidator;

	@Autowired
	private ChangePasswordValidator changePasswordValidator;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private CryptService cryptService;
	
	/**
	 * Method to to show Login page, once logout is called
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(Map model, HttpServletRequest request) {
		LOGGER.debug(" logout ");
		HttpSession session = request.getSession();

		String isSelfInvoke = (String)request.getParameter("selfinvoke");
		if(isSelfInvoke != null && "true".equals(isSelfInvoke)){
			session.removeAttribute(ERROR_MESSAGE);
		}
		try {
			String email = (String) session.getAttribute(USER_ID);
			String userType = (String) session.getAttribute(USER_TYPE);
			if (email == null && userType == null) {
				return VIEW_RESOLVER_SESSION_EXPIRED_MERCHANT;
			}
			loginService.logout(email, userType);
			session.invalidate();
		} catch (Exception e) {
			LOGGER.error("ERROR:: CustomerLoginController:: logout method", e);
		}
		LOGGER.debug("Exiting:: CustomerLoginController:: logout method");
		return WALLET_LOGOUT;
	}

	/**
	 * Method to to show Login page, once logout is called
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = VIEW_RESOLVER_CHECK_SESSION_NAME, method = RequestMethod.GET)
	public String checkSession(HttpSession session) {
		LOGGER.debug(" checkSession  ");
		LoginUserForm loginUser = (LoginUserForm) session
				.getAttribute(CommonConstrain.USER_SESSION_NAME);
		if (null != loginUser) {
			return VIEW_RESOLVER_HOME_NAME;
		}
		LOGGER.debug("Exiting:: CustomerLoginController:: checkSession method");
		return VIEW_RESOLVER_INVALID_SESSION_NAME;
	}

	/**
	 * Method to to show Login page, once logout is called
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = VIEW_RESOLVER_SESSION_EXPIRED_MERCHANT, method = RequestMethod.GET)
	public String sessionExpired(HttpSession session) {
		LOGGER.debug(" sessionExpired ");
		try {
			session.invalidate();
		} catch (Exception e) {
			LOGGER.error("ERROR:: CustomerLoginController:: sessionExpired method", e);
		}
		LOGGER.debug("Exiting:: CustomerLoginController:: sessionExpired method");
		return VIEW_RESOLVER_SESSION_EXPIRED_MERCHANT;
	}

	/**
	 * Method to to show Forgot password page, once forgot password link is
	 * called
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "forgotpassword", method = RequestMethod.GET)
	public String forgotPasswordPage(Map model,
			@Valid ForgotPasswordForm forgotPasswordForm, HttpServletRequest request) {
		LOGGER.debug(" forgotPasswordPage ");
		try {
			model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.HINT_QUESTIONS));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.debug("Exiting:: CustomerLoginController:: forgotPasswordPage method");
		return VIEW_RESOLVER_MERCHANT_FORGOT_PASSWORD_PAGE;
	}

	/**
	 * Method to to show Forgot password page, once forgot password link clicked
	 * is called
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "forgotpassword", method = RequestMethod.POST)
	public String forgotPasswordCreatePage(Map model, Locale locale,
			@Valid ForgotPasswordForm forgotPasswordForm, BindingResult result, HttpServletRequest request) {
		
		LOGGER.debug(" forgotPasswordCreatePage ");
		HttpSession session = request.getSession();
		ForgotPasswordDto forgotPasswordDto = null;
		forgotPasswordDto = forgotPasswordForm.getForgotPassword();
		try {
			forgotPasswordValidator.validate(forgotPasswordDto, result);
			if (result.hasErrors()) {
				model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID),
						MasterDataConstants.HINT_QUESTIONS));
			}  else {
				LoginUserForm loginUserForm = new LoginUserForm();
				forgotPasswordDto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
				forgotPasswordDto.setUserType(MERCHANT_USER_TYPE);
				Authentication auth = commonService.getAuthentication(forgotPasswordForm.getEmailId(), MERCHANT_USER_TYPE);
				if(auth != null){
					String personName = merchantService.getPersonName(forgotPasswordForm.getEmailId(), forgotPasswordDto.getUserType());
					Boolean flag = loginService.forgotPassword(forgotPasswordDto,personName);
					//Audit Trail service
					auditTrailService.createAuditTrail(auth.getId(), AuditTrailConstrain.MODULE_USER_FORGOT_PASSWORD, 
							AuditTrailConstrain.STATUS_EDIT, forgotPasswordDto.getEmailId(), forgotPasswordDto.getUserType());					
					if ( flag ) {
						session.setAttribute(SUCCESS_MESSAGE, FORGOT_PASSWORD_SUCCESS);
						model.put(MODEL_ATTRIBUTE_LOG_USER_NAME, loginUserForm);
						return UIUtil.redirectPath(AttributeValueConstants.HOME_LOGIN_PATH);
					}
				} else{
					model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(), 
							(Long) request.getSession().getAttribute(LANGUAGE_ID),
							MasterDataConstants.HINT_QUESTIONS));
					model.put(ERROR_MESSAGE, context.getMessage(CommonConstrain.EMAIL_ID_DOES_NOT_MATCH, null, locale));
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataConstants.HINT_QUESTIONS));
			model.put(ERROR_MESSAGE, context.getMessage(papulateErrorMessageForForgotpassword(e), null, locale));
		}
		if (!result.hasErrors()) {
			forgotPasswordForm.setForgotPassword(forgotPasswordDto);
		}
		LOGGER.debug("Exiting:: CustomerLoginController:: forgotPasswordCreatePage method");
		return VIEW_RESOLVER_MERCHANT_FORGOT_PASSWORD_PAGE;
	}

	/**
	 * Method to to show change password page, once change password link clicked
	 * under merchant profile.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "changepassword", method = RequestMethod.GET)
	public String changePasswordPage(Map model,
			@Valid ChangePasswordForm changePasswordForm, HttpServletRequest request) {
		
		LOGGER.debug( " changePasswordPage ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		LOGGER.debug("Entering:: CustomerLoginController:: changePasswordPage method");
		changePasswordForm.setEmailId(session.getAttribute(USER_ID).toString());
		LOGGER.debug("Exiting:: CustomerLoginController:: changePasswordPage method");
		return MERCHANT_CHANGE_PASSWORD_VIEW;
	}

	@RequestMapping(value = "changepassword", method = RequestMethod.POST)
	public String changePasswordSubmitPage(Map model, Locale locale,
			@ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
			BindingResult result, HttpServletRequest request) {
		
		LOGGER.debug(" changePasswordSubmitPage ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		if ("cancel".equals(changePasswordForm.getMode())) {
			return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_PATH);
		}
		changePasswordValidator.validate(changePasswordForm, result);
		if (result.hasErrors()) {
			return MERCHANT_CHANGE_PASSWORD_VIEW;
		}  else {
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
				session.setAttribute(CHANGE_PASSWORD_SUCS_MSG, context.getMessage(CHANGE_PASSWORD_SUCCESS, null, locale));
				return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_PATH);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				model.put(ERROR_MESSAGE, context.getMessage(papulateErrorMessageForChangepassword(e), null, locale));
				return MERCHANT_CHANGE_PASSWORD_VIEW;
			}
		}
	}

	private String papulateErrorMessageForChangepassword(Exception e) {
		
		LOGGER.debug( " papulateErrorMessageForChangepassword " );
		String em = null;
		if (e.getMessage().contains(CommonConstrain.EMAIL_MATCH_FAIL)) {
			em = CommonConstrain.EMAIL_MATCH_FAIL;
		} else if (e.getMessage().contains(CommonConstrain.PASSWORD_MATCH_FAIL)) {
			em = CommonConstrain.OLD_PASSWORD_MATCH_FAIL;
		} else if (e.getMessage().contains(CommonConstrain.OLD_AND_NEW_PASSWORD_SAME)) {
			em = CommonConstrain.OLD_AND_NEW_PASSWORD_SAME;
		} else if (e.getMessage().contains(CommonConstrain.PASSWORD_HISTORY_EXIST)) {
			em = PASSWORD_HISTORY_EXIST;
		} else {
			em = CHANGE_PASSWORD_FAIL;
		}
		return em;
		
	}

	private String papulateErrorMessageForForgotpassword(Exception e) {
		String em = null;
		LOGGER.debug( " papulateErrorMessageForForgotpassword " );

		if (e.getMessage().contains(CommonConstrain.EMAIL_ID_DOES_NOT_MATCH)) {
			em = CommonConstrain.EMAIL_ID_DOES_NOT_MATCH;
		} else if (e.getMessage().contains(CommonConstrain.HINT_QUESTION_ONE_NOT_MATCHING)) {
			em = CommonConstrain.HINT_QUESTION_ONE_NOT_MATCHING;
		} else if (e.getMessage().contains(CommonConstrain.HINT_ANSW_ONE_NOT_MATCHING)) {
			em = CommonConstrain.HINT_ANSW_ONE_NOT_MATCHING;
		} else if(e.getMessage().contains(CommonConstrain.UNIQUE_ADDRESS_EXCEPTION)){
			em = CommonConstrain.EMAIL_ID_DOES_NOT_MATCH;
		} else if (e.getMessage().contains(CommonConstrain.ACCOUNT_DEACTIVE)) {
			em = FORGOT_PASSWORD_FAILED_INACTIVE_USER;
		} else if (e.getMessage().contains(CommonConstrain.ACCOUNT_REJECTED)) {
			em = FORGOT_PASSWORD_FAILED_ACCOUNT_REJECTED;
		} else {
			em = FORGOT_PASSWORD_FAIL;
		}
		return em;
	}

}
