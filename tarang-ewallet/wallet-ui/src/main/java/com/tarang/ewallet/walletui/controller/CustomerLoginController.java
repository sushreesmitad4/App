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
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.dto.ForgotPasswordDto;
import com.tarang.ewallet.email.util.EmailServiceConstants;
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
 * @Comments: Customer User Login Controller class for Login module
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/customerlogin")
public class CustomerLoginController implements Login, AttributeConstants, AttributeValueConstants {

	private static final Logger LOGGER = Logger.getLogger(CustomerLoginController.class);

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CustomerService customerService;

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
		LOGGER.debug( " logout " );
		HttpSession session = request.getSession();

		String isSelfInvoke = (String)request.getParameter("selfinvoke");
		if(isSelfInvoke != null && "true".equals(isSelfInvoke)){
			session.removeAttribute(ERROR_MESSAGE);
		}
		try {
			String email = (String) session.getAttribute(USER_ID);
			String userType = (String) session.getAttribute(USER_TYPE);
			if (email == null && userType == null) {
				return VIEW_RESOLVER_SESSION_EXPIRED_CUSTOMER;
			}
			loginService.logout(email, userType);
			/* if timeout then session invalidate is taking care by listener */
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
		LOGGER.debug(" checkSession ");
		LoginUserForm loginUser = (LoginUserForm) session.getAttribute(CommonConstrain.USER_SESSION_NAME);
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
	@RequestMapping(value = VIEW_RESOLVER_SESSION_EXPIRED, method = RequestMethod.GET)
	public String sessionExpired(HttpSession session) {
		LOGGER.debug(" sessionExpired ");
		try {
			session.invalidate();
		} catch (Exception e) {
			LOGGER.error("ERROR:: CustomerLoginController:: sessionExpired method", e);
		}
		LOGGER.debug("Exiting:: CustomerLoginController:: sessionExpired method");
		return VIEW_RESOLVER_SESSION_EXPIRED_CUSTOMER;
	}

	/**
	 * Method to to show Forgot password page, once forgot password link clicked
	 * is called
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "forgotpassword", method = RequestMethod.GET)
	public String forgotPasswordPage(Map model, @Valid ForgotPasswordForm forgotPasswordForm,
			HttpServletRequest request) {
		LOGGER.debug(" forgotPasswordPage ");
		try {
			model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.HINT_QUESTIONS));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return VIEW_RESOLVER_CUSTOMER_FORGOT_PASSWORD_PAGE;
	}

	/**
	 * Method to to show Forgot password page, once forgot password link clicked
	 * is called
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "forgotpassword", method = RequestMethod.POST)
	public String forgotPasswordSubmitPage(Map model, Locale locale,
			@Valid ForgotPasswordForm forgotPasswordForm, BindingResult result,
			HttpServletRequest request) {
		LOGGER.debug(" forgotPasswordSubmitPage ");
		HttpSession session = request.getSession();
		ForgotPasswordDto forgotPasswordDto = null;
		forgotPasswordDto = forgotPasswordForm.getForgotPassword();
		String viewPage = VIEW_RESOLVER_CUSTOMER_LOGIN_PAGE;
		try {

			forgotPasswordValidator.validate(forgotPasswordDto, result);
			if (result.hasErrors()) {
				model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataConstants.HINT_QUESTIONS));
				viewPage = VIEW_RESOLVER_CUSTOMER_FORGOT_PASSWORD_PAGE;
			} else {
				LoginUserForm loginUserForm = new LoginUserForm();
				forgotPasswordDto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
				forgotPasswordDto.setUserType(CUSTOMER_USER_TYPE);
				Authentication auth = commonService.getAuthentication(forgotPasswordForm.getEmailId(), CUSTOMER_USER_TYPE);
				if(auth != null){
					String personName = customerService.getPersonName(forgotPasswordForm.getEmailId(),forgotPasswordDto.getUserType());
					Boolean flag = loginService.forgotPassword(forgotPasswordDto, personName);
					//Audit Trail service
					auditTrailService.createAuditTrail(auth.getId(), AuditTrailConstrain.MODULE_USER_FORGOT_PASSWORD, 
							AuditTrailConstrain.STATUS_EDIT, forgotPasswordDto.getEmailId(), forgotPasswordDto.getUserType());					
					if ( flag ) {
						session.setAttribute(SUCCESS_MESSAGE, FORGOT_PASSWORD_SUCCESS);
						model.put(MODEL_ATTRIBUTE_LOG_USER_NAME, loginUserForm);
						return UIUtil.redirectPath(AttributeValueConstants.HOME_LOGIN_PATH);
					}
				} else{
					model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
							request.getSession().getServletContext(), (Long) request
									.getSession().getAttribute(LANGUAGE_ID),
							MasterDataConstants.HINT_QUESTIONS));
					viewPage = VIEW_RESOLVER_CUSTOMER_FORGOT_PASSWORD_PAGE;
					model.put(ERROR_MESSAGE, context.getMessage(CommonConstrain.EMAIL_ID_DOES_NOT_MATCH, null, locale));
				}
			}
		} catch (Exception e) {
			LOGGER.error("exception block in forgotPasswordPage method", e);
			model.put(ERROR_MESSAGE, context.getMessage(papulateErrorMessageForForgotpassword(e), null, locale));
			model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.HINT_QUESTIONS));
			viewPage = VIEW_RESOLVER_CUSTOMER_FORGOT_PASSWORD_PAGE;
		}
		if (!result.hasErrors()) {
			forgotPasswordForm.setForgotPassword(forgotPasswordDto);
		}
		LOGGER.debug("end of forgotPasswordPage method");
		return viewPage;
	}

	/**
	 * Method to to show change password page, once change password link clicked
	 * under customer profile.
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "changepassword", method = RequestMethod.GET)
	public String changePasswordPage(Map model, @Valid ChangePasswordForm changePasswordForm,
			HttpServletRequest request) {
		
		LOGGER.debug(" changePasswordPage ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		LOGGER.debug("Entering:: CustomerLoginController:: changePasswordPage method");
		changePasswordForm.setEmailId(session.getAttribute(USER_ID).toString());

		return CUSTOMER_CHANGE_PASSWORD_VIEW;
	}

	@RequestMapping(value = "changepassword", method = RequestMethod.POST)
	public String changePasswordSubmitPage(Map model, Locale locale, 
			@ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
			BindingResult result, HttpServletRequest request) {
		
		LOGGER.debug(" changePasswordSubmitPage ");
		String viewPage = VIEW_RESOLVER_CUSTOMER_LOGIN_PAGE;
		HttpSession session = request.getSession();

		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		if ("cancel".equals(changePasswordForm.getMode())) {
			return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_PATH);
		}

		changePasswordValidator.validate(changePasswordForm, result);
		if (result.hasErrors()) {
			viewPage = CUSTOMER_CHANGE_PASSWORD_VIEW;
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
				viewPage = CUSTOMER_LOGIN_SUCCESS_VIEW;
				session.setAttribute(CHANGE_PASSWORD_SUCS_MSG, context.getMessage(CHANGE_PASSWORD_SUCCESS, null, locale));
				return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_PATH);
			} catch (Exception e) {
				LOGGER.debug(e.getMessage(), e);
				model.put(ERROR_MESSAGE, context.getMessage(papulateErrorMessageForChangepassword(e), null, locale));
				viewPage = CUSTOMER_CHANGE_PASSWORD_VIEW;
			}
			LOGGER.debug("end of changePasswordCreatePage method");
		}
		return viewPage;
	}

	private String papulateErrorMessageForChangepassword(Exception e) {
		LOGGER.debug( " papulateErrorMessageForChangepassword " );
		String errorMessage = null;
		if (e.getMessage().contains(CommonConstrain.EMAIL_MATCH_FAIL)) {
			errorMessage = CommonConstrain.EMAIL_MATCH_FAIL;
		} else if (e.getMessage().contains(CommonConstrain.PASSWORD_MATCH_FAIL)) {
			errorMessage = CommonConstrain.OLD_PASSWORD_MATCH_FAIL;
		} else if (e.getMessage().contains(CommonConstrain.OLD_AND_NEW_PASSWORD_SAME)) {
			errorMessage = CommonConstrain.OLD_AND_NEW_PASSWORD_SAME;
		} else if (e.getMessage().contains(CommonConstrain.PASSWORD_HISTORY_EXIST)) {
			errorMessage = PASSWORD_HISTORY_EXIST;
		} else {
			errorMessage = CHANGE_PASSWORD_FAIL;
		}
		return errorMessage;
	}
	
	private String papulateErrorMessageForForgotpassword(Exception e) {
		LOGGER.debug( " papulateErrorMessageForForgotpassword " );
		String errorMessage = null;
		if (e.getMessage().contains(CommonConstrain.EMAIL_ID_DOES_NOT_MATCH)) {
			errorMessage = CommonConstrain.EMAIL_ID_DOES_NOT_MATCH;
		} else if (e.getMessage().contains(CommonConstrain.HINT_QUESTION_ONE_NOT_MATCHING)) {
			errorMessage = CommonConstrain.HINT_QUESTION_ONE_NOT_MATCHING;
		} else if (e.getMessage().contains(CommonConstrain.HINT_ANSW_ONE_NOT_MATCHING)) {
			errorMessage = CommonConstrain.HINT_ANSW_ONE_NOT_MATCHING;
		} else if (e.getMessage().contains(EmailServiceConstants.EMAIL_SEND_FAILED)) {
			errorMessage = FORGOT_PASSWORD_MAIL_SEND_FAIL;
		} else if(e.getMessage().contains(CommonConstrain.UNIQUE_ADDRESS_EXCEPTION)){
			errorMessage = CommonConstrain.EMAIL_ID_DOES_NOT_MATCH;
		} else if (e.getMessage().contains(CommonConstrain.ACCOUNT_DEACTIVE)) {
			errorMessage = FORGOT_PASSWORD_FAILED_INACTIVE_USER;
		} else if (e.getMessage().contains(CommonConstrain.ACCOUNT_REJECTED)) {
			errorMessage = FORGOT_PASSWORD_FAILED_ACCOUNT_REJECTED;
		} else {
			errorMessage = FORGOT_PASSWORD_FAIL;
		}
		return errorMessage;
	}

}
