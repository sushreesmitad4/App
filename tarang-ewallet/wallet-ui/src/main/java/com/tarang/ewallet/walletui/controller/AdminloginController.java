package com.tarang.ewallet.walletui.controller;

import java.util.ArrayList;
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

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.business.LoginService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AdminUser;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.business.UserService;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.constants.Admin;
import com.tarang.ewallet.walletui.controller.constants.Login;
import com.tarang.ewallet.walletui.form.ChangePasswordForm;
import com.tarang.ewallet.walletui.form.LoginUserForm;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.ChangePasswordValidator;
import com.tarang.ewallet.walletui.validator.LoginValidator;


/**
 * @Author : kedarnath
 * @Date : Oct 19, 2012
 * @Time : 3:27:50 PM
 * @Version : 1.0
 * @Comments: Admin User Login Controller class for Login module
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("/adminlogin")
public class AdminloginController implements Login, AttributeConstants, AttributeValueConstants {

	private static final Logger LOGGER = Logger.getLogger(AdminloginController.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private ChangePasswordValidator changePasswordValidator;

	@Autowired
	private LoginValidator loginValidator;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private CryptService cryptService;
	
	/**
	 * Method to to show Admin Login page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showLoginPage(Map model, Locale locale, @Valid LoginUserForm loginUserForm, HttpServletRequest request) {
		LOGGER.debug( " showLoginPage " );
		loginUserForm.setUserType(GlobalLitterals.ADMIN_USER_TYPE);
		model.put(Login.MODEL_ATTRIBUTE_LOG_USER_NAME, loginUserForm);
		HttpSession session = request.getSession();
		String errMsg = (String)session.getAttribute(ERROR_MESSAGE);
		if(errMsg != null){
			model.put(ERROR_MESSAGE, context.getMessage(errMsg, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		LOGGER.debug("showLoginPage method");
		return Login.VIEW_RESOLVER_LOGIN_NAME;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String validateLoginPage(Map model, Locale locale, @Valid LoginUserForm loginUserForm, 
			BindingResult result, HttpServletRequest request) {
		LOGGER.debug( " validateLoginPage " );
		HttpSession sess = request.getSession(false);
		if(null != sess){
			sess.invalidate();
		}
		
		HttpSession session = request.getSession(true);

		loginValidator.validate(loginUserForm, result);
		if (!result.hasErrors()) {
			try {
				Authentication authentication = loginService.login(loginUserForm.getEmail(), 
						loginUserForm.getPassword(), GlobalLitterals.ADMIN_USER_TYPE, MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
								(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
				try {
					papulateMenus(session, authentication);
				} catch (Exception e) {
					/*No role assigned, so login failed and reset login status*/
					loginService.logout(loginUserForm.getEmail(), GlobalLitterals.ADMIN_USER_TYPE);
					LOGGER.error(e.getMessage(), e);
					session.setAttribute(ERROR_MESSAGE, LOGIN_ERROR_NO_ROLES);
					return UIUtil.redirectPath(AttributeValueConstants.ADMIN_LOGIN_PATH);
				}
				session.setAttribute(AUTHENTICATION_ID, authentication.getId());
				session.setAttribute(USER_ID, authentication.getEmailId());
				session.setAttribute(USER_TYPE, authentication.getUserType());
				session.setAttribute(USER_EMAIL, authentication.getEmailId());
				session.setAttribute("lastLoginTime", authentication.getLastLogin().getTime());
				
				AdminUser adminUser = userService.findAdminUserByAuthId(authentication.getId());
				Role role = userService.getRole(adminUser.getRoleId());
				session.setAttribute(MENU_DETAILS, MenuUtils.adminMenuDetailsList(role.getMenuDetails()));
							
				if (authentication.isResetPassword()) {
					session.setAttribute(ERROR_MESSAGE, CommonConstrain.RESET_PASSWORD);
					session.setAttribute(IS_RESET_PASSWORD, Boolean.TRUE);
					return UIUtil.redirectPath(AttributeValueConstants.ADMIN_LOGIN_CHANGE_PASS_PATH);
				} else{
					session.setAttribute(IS_RESET_PASSWORD, Boolean.FALSE);
				}
				session.removeAttribute(Login.APPLICATIONS_SESSION_NAME);
				model.put(Login.MODEL_ATTRIBUTE_LOG_USER_NAME, loginUserForm);
				
				model.put("user", loginUserForm);
				LOGGER.debug("Logging In Email: " + loginUserForm.getEmail());
				return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				model.put(ERROR_MESSAGE, context.getMessage(papulateErrorMessage(e), null, locale));
				LOGGER.error("validateLoginPage:: Method Exception");
			}
		}
		model.put(Login.MODEL_ATTRIBUTE_LOG_USER_NAME, loginUserForm);

		return Login.VIEW_RESOLVER_LOGIN_NAME;
	}

	/**
	 * Method to to show Login page, once logout is called
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, Map model, HttpSession session) {
		LOGGER.debug("logout GET");

		String isSelfInvoke = (String)request.getParameter("selfinvoke");
		if(isSelfInvoke != null && "true".equals(isSelfInvoke)){
			session.removeAttribute(ERROR_MESSAGE);
		}
		try {
			String email = (String) session.getAttribute(USER_ID);
			String userType = (String) session.getAttribute(USER_TYPE);
			if (email == null && userType == null){
				return Login.VIEW_RESOLVER_SESSION_EXPIRED_ADMIN;
			}
			loginService.logout(email, userType);
			/* Session invalidate is taking care by listener */
			session.invalidate();
		} catch (Exception e) {
			LOGGER.error("logout Method", e);
		}
		return Admin.LOGOUT;
	}

	/**
	 * Method to to show Login page, once logout is called
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = Login.VIEW_RESOLVER_CHECK_SESSION_NAME, method = RequestMethod.GET)
	public String checkSession(HttpSession session) {
		LOGGER.debug("checkSession method");
		LoginUserForm loginUser = (LoginUserForm) session.getAttribute(CommonConstrain.USER_SESSION_NAME);
		if (null != loginUser) {
			return Login.VIEW_RESOLVER_HOME_NAME;
		}
		return Login.VIEW_RESOLVER_INVALID_SESSION_NAME;
	}

	/**
	 * Method to to show Login page, once logout is called
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = Login.VIEW_RESOLVER_SESSION_EXPIRED, method = RequestMethod.GET)
	public String sessionExpired(HttpSession session) {
		LOGGER.debug( " sessionExpired " );
		try {
			session.invalidate();
		} catch (Exception e) {
			LOGGER.error("sessionExpired method", e);
		}
		return Login.VIEW_RESOLVER_SESSION_EXPIRED_ADMIN;
	}

	/**
	 * Method to to show Resret password page, once Reset password link clicked
	 * is called
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "changepassword", method = RequestMethod.GET)
	public String changePasswordPage(Map model, Locale locale,
			@Valid ChangePasswordForm changePasswordForm, HttpServletRequest request) {
		LOGGER.debug( " changePasswordPage " );
		HttpSession session = request.getSession();
		String viewPage = ADMIN_CHANGE_PASSWORD_VIEW;
		
		Boolean isResetPassword = (Boolean)session.getAttribute(IS_RESET_PASSWORD);
		if(null == isResetPassword){
			session.invalidate();
			HttpSession newSession = request.getSession(true);
			newSession.setAttribute(ERROR_MESSAGE, USER_SESSION_EXPIRED);
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		if (isResetPassword) {
			String errMsg = (String)session.getAttribute(ERROR_MESSAGE);
			if(errMsg != null){
				model.put(ERROR_MESSAGE, context.getMessage(errMsg, null, locale));
				session.removeAttribute(ERROR_MESSAGE);
			}
			viewPage = VIEW_RESOLVER_LOGIN_CHANGEPASSWORD;
		}/*else {//for profile change password
			if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
				return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
			}
			Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
					MenuConstants.CHANGE_PASSWORD_MI, MenuConstants.VIEW_PERMISSION);
		    if(!adminAccessCheck){
		    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
		    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
		    }
			viewPage = ADMIN_CHANGE_PASSWORD_VIEW;
		}*/
		changePasswordForm.setEmailId(session.getAttribute(USER_ID).toString());
		return viewPage;
	}

	@RequestMapping(value = "changepassword", method = RequestMethod.POST)
	public String changePasswordSubmitPage(Map model, Locale locale,
			@ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
			BindingResult result, HttpServletRequest request) {
		LOGGER.debug( " changePasswordSubmitPage " );
		HttpSession session = request.getSession();
		Boolean isResetPassword = (Boolean)session.getAttribute(IS_RESET_PASSWORD);
		if(null == isResetPassword){
			session.invalidate();
			HttpSession newSession = request.getSession(true);
			newSession.setAttribute(ERROR_MESSAGE, USER_SESSION_EXPIRED);
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		if(!isResetPassword && !UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		if ("cancel".equals(changePasswordForm.getMode())) {
			if (isResetPassword) {
				return UIUtil.redirectPath(AttributeValueConstants.ADMIN_LOGIN_PATH);
			} else {
				return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
			}
		}
		
		changePasswordValidator.validate(changePasswordForm, result);
		if (result.hasErrors()) {
			if (isResetPassword) {
				return VIEW_RESOLVER_LOGIN_CHANGEPASSWORD;
			} else {
				return ADMIN_CHANGE_PASSWORD_VIEW;
			}
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
				session.setAttribute(IS_RESET_PASSWORD, Boolean.FALSE);
				session.setAttribute(SUCCESS_MESSAGE, CHANGE_PASSWORD_SUCCESS);
				return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				model.put(ERROR_MESSAGE, context.getMessage(papulateErrorMessageForChangepassword(e), null, locale));
				if (isResetPassword) {
					return VIEW_RESOLVER_LOGIN_CHANGEPASSWORD;
				} else {
					return ADMIN_CHANGE_PASSWORD_VIEW;
				}
			}
		}
	}

	private void papulateMenus(HttpSession session,
			Authentication authentication) throws WalletException {

		LOGGER.debug( " papulateMenus " );
		AdminUser adminUser = userService.findAdminUserByAuthId(authentication.getId());
		if (adminUser == null) {
			throw new WalletException("No user found");
		}
		setAdminUserNameInSession(adminUser.getNameId(), session);
		LOGGER.debug(adminUser.getRoleId());
		Role role = userService.getRole(adminUser.getRoleId());
		if (role == null) {
			throw new WalletException(CommonConstrain.NOROLES_FOUND);
		}
		session.setAttribute("roleId", role.getId());
		session.setAttribute("roleName", role.getName());
		String sroles = role.getMenuDetails();
		if (sroles == null) {
			throw new WalletException(CommonConstrain.NOROLES_FOUND);
		}
		if (sroles != null && sroles.equals(EMPTY_STRING)) {
			throw new WalletException(CommonConstrain.NOROLES_FOUND);
		}
		String splitroleMenu[] = sroles.split(MenuConstants.SPLIT_COLON);
		List<Long> validMenuList = new ArrayList<Long>();
		String[] ridStrs = null;
		for (int i = 0; i < splitroleMenu.length; i++) {
			ridStrs = splitroleMenu[i].split(MenuConstants.SPLIT_UNDER_SCORE);
			validMenuList.add(Long.parseLong(ridStrs[0]));
		}
		//List<Menu> parentMenuList = masterDataService.getParentMenus(0L);
		//Map<Long, List<Menu>> parentChildMap = masterDataService.getParentChildMenusMap(0L);
		
		session.setAttribute("role", role);
		session.setAttribute("validMenuList", validMenuList);
		//session.setAttribute("parentMenuList", parentMenuList);
		//session.setAttribute("parentChildMap", parentChildMap);
	}
	
	private void setAdminUserNameInSession(Long nameId, HttpSession session) throws WalletException{
		PersonName personName = commonService.getPersonName(nameId);
		session.setAttribute(ADMIN_NAME, personName.getFirstName() + SPACE_STRING + personName.getLastName());
	}

	private String papulateErrorMessage(Exception e) {
		LOGGER.debug( " papulateErrorMessage " );
		String errorMessage =  null;
		if (e.getMessage().equals(CommonConstrain.LOGIN_ERROR_INVALID_USER)) {
			errorMessage = CommonConstrain.LOGIN_ERROR_INVALID_USER;
		} else if (e.getMessage().equals(CommonConstrain.LOGIN_STATUS)) {
			errorMessage = CommonConstrain.LOGIN_STATUS;
		} else if (e.getMessage().equals(CommonConstrain.USER_BLOCK)) {
			errorMessage = CommonConstrain.USER_BLOCK;
		} else if (e.getMessage().equals(CommonConstrain.USER_ACCOUNT_DELETED)) {
			errorMessage = CommonConstrain.USER_ACCOUNT_DELETED;
		} else if (e.getMessage().equals(CommonConstrain.PASSWORD_MATCH_FAIL)) {
			errorMessage = CommonConstrain.PASSWORD_MATCH_FAIL;
		} else if (e.getMessage().equals(CommonConstrain.EMAIL_MATCH_FAIL)) {
			errorMessage = CommonConstrain.EMAIL_MATCH_FAIL;
		} else if (e.getMessage().equals(CommonConstrain.ACCOUNT_REJECTED)) {
			errorMessage = CommonConstrain.ACCOUNT_REJECTED;
		} else if (e.getMessage().equals(CommonConstrain.ACCOUNT_DEACTIVE)) {
			errorMessage = CommonConstrain.ACCOUNT_DEACTIVE;
		} else {
			errorMessage = CommonConstrain.EMAIL_OR_PASSWORD_FAIL;
		}
		return errorMessage;
	}
	
	private String papulateErrorMessageForChangepassword(Exception e) {
		LOGGER.debug( " papulateErrorMessageForChangepassword " );
		String errorMessage = null;
		if (e.getMessage().equals(CommonConstrain.EMAIL_MATCH_FAIL)) {
			errorMessage = CommonConstrain.EMAIL_MATCH_FAIL;
		} else if (e.getMessage().equals(CommonConstrain.PASSWORD_MATCH_FAIL)) {
			errorMessage = CommonConstrain.OLD_PASSWORD_MATCH_FAIL;
		} else if (e.getMessage().equals(CommonConstrain.OLD_AND_NEW_PASSWORD_SAME)) {
			errorMessage = CommonConstrain.OLD_AND_NEW_PASSWORD_SAME;
		} else if (e.getMessage().contains(CommonConstrain.PASSWORD_HISTORY_EXIST)) {
			errorMessage = PASSWORD_HISTORY_EXIST;
		} else {
			errorMessage = CHANGE_PASSWORD_FAIL;
		}
		return errorMessage;
		
	}
}