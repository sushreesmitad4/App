/**
 * 
 */
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.business.UserService;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Admin;
import com.tarang.ewallet.walletui.form.AdminUserForm;
import com.tarang.ewallet.walletui.form.SearchUserForm;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.MerchantValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


/**
 * @author prasadj
 * 
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Controller
@RequestMapping("/adminusermgmt")
public class AdminUserMgmtController implements Admin, AttributeConstants, AttributeValueConstants, GlobalLitterals {

	private static final Logger LOGGER = Logger.getLogger(AdminUserMgmtController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private UtilService utilService;

	@Autowired
	private ApplicationContext context;
	
	@RequestMapping(method = RequestMethod.GET)
	public String loadUserListPage(Map model, Locale locale, @Valid SearchUserForm searchUserForm,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }

		model.put(STATUS_LIST, MasterDataUtil.getUserActiveMap(context.getMessage(ACTIVE_LBL, null, locale),
				context.getMessage(IN_ACTIVE_LBL, null, locale)));
		
		String url = WALLET_PATH_PREFIX + "adminusermgmt/userrecords";
		request.setAttribute(URLUSER_LIST , url);
		
		String successMessage = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(successMessage != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(successMessage, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}

		String errorMessage = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorMessage != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}

		LOGGER.debug("Entering :: AdminUserMgmtController :: loadUserListPage method");
		return USER_MGMT_DEFAULT_VIEW;
	}

	@RequestMapping(value = "/adminusermgmt", method = RequestMethod.POST)
	public String loadUserListPageOnSearch(Map model, @Valid SearchUserForm searchUserForm, HttpServletRequest request, Locale locale) {
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }

		model.put(STATUS_LIST, MasterDataUtil.getUserActiveMap(context.getMessage(ACTIVE_LBL, null, locale),
				context.getMessage(IN_ACTIVE_LBL, null, locale)));
		
		String fromdate = searchUserForm.getFromDate() != null ? searchUserForm.getFromDate() : EMPTY_STRING;
		String todate = searchUserForm.getToDate() != null ? searchUserForm.getToDate() : EMPTY_STRING;
		String name = searchUserForm.getName() != null ? searchUserForm.getName() : EMPTY_STRING;
		String emailid = searchUserForm.getEmailId() != null ? searchUserForm.getEmailId() : EMPTY_STRING;
		Long status = searchUserForm.getStatus() != null ? searchUserForm.getStatus() : 0;

		String url = WALLET_PATH_PREFIX + "adminusermgmt/userrecords?fromdate=" 
				+ fromdate + "&todate=" + todate + "&name=" + name + "&emailid=" + emailid + "&status=" + status;
		request.setAttribute(URLUSER_LIST, url);

		LOGGER.debug("Entering :: AdminUserMgmtController :: loadUserListPageOnSearch method");
		return USER_MGMT_DEFAULT_VIEW;
	}

	@RequestMapping(value = "/userrecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<UserDto> adminUserRecords(
			@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			Map model, Locale locale, HttpSession session,
			HttpServletRequest request) {

		JqgridResponse<UserDto> response = new JqgridResponse<UserDto>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return response;	
	    }

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(filters);
		qf.setPage(page);
		qf.setRows(utilService.getReportLimit());
		qf.setSidx(sidx);
		qf.setSord(sord);
		
		String fromDate = request.getParameter(FROMDATE) != null ? request.getParameter(FROMDATE) : EMPTY_STRING;
		String toDate = request.getParameter(TODATE) != null ? request.getParameter(TODATE) : EMPTY_STRING;
		String name = request.getParameter(NAME) != null ? request.getParameter(NAME) : EMPTY_STRING;
		String emailId = request.getParameter(EMAILID) != null ? request.getParameter(EMAILID) : EMPTY_STRING;
		Long status = Long.parseLong(request.getParameter(STATUS) != null ? request.getParameter(STATUS) : "0");
		/*EWALLET-56: User management status is active/inactive */
		Boolean active = null;
		active = MasterDataUtil.papulateActiveData(status);
		List<UserDto> userDtos = new ArrayList<UserDto>();
		try {
			userDtos = userService.getAdminUsersList(qf, fromDate, toDate, name, emailId, active);
			MasterDataUtil.getUserActive(userDtos, context.getMessage(ACTIVE_LBL, null, locale),
					context.getMessage(IN_ACTIVE_LBL, null, locale));

			model.put(STATUS_LIST, MasterDataUtil.getUserActiveMap(context.getMessage(ACTIVE_LBL, null, locale),
					context.getMessage(IN_ACTIVE_LBL, null, locale)));
		} catch (Exception e) {
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error("JqgridResponse", e);
		}
		LOGGER.debug("userDtos list size-> " + userDtos.size());
		if (userDtos != null && userDtos.isEmpty()) {
			LOGGER.error("ERROR :: AdminUserMgmtController :: userDtos list is empty");
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
		}
		response.setRows(userDtos);
		response.setRecords(qf.getTotal().toString());
		response.setTotal(qf.getTotalPages().toString());
		response.setPage(qf.getPage().toString());
		return response;

	}

	@RequestMapping(value = "/addadminuser", method = RequestMethod.GET)
	public String addAdminUserPage(Map model,
			Locale locale, @Valid AdminUserForm adminUserForm,
			BindingResult result, Long id, HttpServletRequest request) {
		LOGGER.debug( " addAdminUserPage " );
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
	    populateModel(model, request, adminUserForm.getCountryId());
	
		/* load roles */
		List<Role> roles = loadRoles();
		if (roles != null && roles.size() > 0) {
			model.put(ROLES_LIST , roles);
		} else {
			LOGGER.debug("ERROR:: addAdminUserPage:: Roles list is null");
		}
		return USER_MGMT_DETAILS_ADD;
		
	}

	@RequestMapping(value = "/editadminuser", method = RequestMethod.GET)
	public String editAdminUserPage(Map model, Locale locale, 
			@Valid AdminUserForm adminUserForm, BindingResult result, long id, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String viewPage = USER_MGMT_DETAILS_EDIT;
		AdminUserDto adminUserDto = null;
		if (id != 0){
			try {
				adminUserDto = userService.loadAdminUser(id);
				if(null == adminUserDto){
					LOGGER.info(" adminUserDto object is null ");
					session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
					return UIUtil.redirectPath(ADMIN_USERMGMT_PATH);
				}
				if(UserStatusConstants.DELETED.equals(adminUserDto.getStatus())){
					LOGGER.info(" Admin user account was deleted ");
					session.setAttribute(ERROR_MESSAGE, CommonConstrain.SELECTED_RECORD_DELETED_ERRMSG);
					return UIUtil.redirectPath(ADMIN_USERMGMT_PATH);
				}
				session.setAttribute(ADMIN_USERID, id);
			} catch (Exception e) {
				LOGGER.error("editAdminUserPage", e);
				session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
				return UIUtil.redirectPath(ADMIN_USERMGMT_PATH);
			}
		}
		if(adminUserDto != null){
			adminUserForm.setAdminUser(adminUserDto);
		}
		populateModel(model, request, adminUserForm.getCountryId());
		
		/* load roles */
		List<Role> roles = loadRoles();
		if (roles != null && roles.size() > 0) {
			model.put(ROLES_LIST, roles);
		} else {
			LOGGER.debug("editAdminUserPage:: Roles list is null");
		}
		model.put(USER_ID, id);
		model.put(COUNTRY_SELECTED, adminUserForm.getCountryId());
		model.put(IS_EDIT_PAGE, true);

		return viewPage;
	}

	@RequestMapping(value = "/viewadminuser", method = RequestMethod.GET)
	public String viewAdminUserPage(Map model, Locale locale, 
			@Valid AdminUserForm adminUserForm, BindingResult result, long id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		AdminUserDto adminUserDto = null;
		if (id != 0) {
			try {
				adminUserDto = userService.loadAdminUser(id);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
				return UIUtil.redirectPath(ADMIN_USERMGMT_PATH);
			}
		}
		if (adminUserDto != null) {
			adminUserForm.setAdminUser(adminUserDto);
			model.put(COUNTRY_NAME, MasterDataUtil.getCountryName(request,
					MasterDataUtil.MD_COUNTRIES, adminUserDto.getCountryId()));
			model.put(STATE_NAME, MasterDataUtil.getRegions(
					request.getSession().getServletContext(), 
					(Long) request.getSession(false).getAttribute(LANGUAGE_ID), 
					adminUserDto.getCountryId()).get(adminUserDto.getStateId()) );
			model.put(STATUS_NAME, MasterDataUtil.getStatusName(request, adminUserDto.getStateId()));
			return USER_MGMT_DETAILS_VIEW;
		} else {
			return USER_MGMT_DEFAULT_VIEW;
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createUpdateAdminUserPage(
			Map model, Locale locale,
			@Valid AdminUserForm adminUserForm, BindingResult result,
			HttpServletRequest request) {
		LOGGER.debug( " createUpdateAdminUserPage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
			MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }

		String viewPage = "";
		String mode = adminUserForm.getMode();
		AdminUserDto adminUser = adminUserForm.getAdminUser();
		/* Set view page for validation fail */
		if (CREATE.equals(mode)) {
			viewPage = USER_MGMT_DETAILS_ADD;
		}  else if (UPDATE.equals(mode)) {
			/*For country select confirmation pop up*/
			model.put(COUNTRY_SELECTED, adminUserForm.getCountryId());
			model.put(IS_EDIT_PAGE, true);
			
			viewPage = USER_MGMT_DETAILS_EDIT;
		}

		userValidator.validate(adminUserForm, result);
		//validatePhoneNumber(result, adminUser);
		if (CREATE.equals(mode)) {
			String loginUser = (String) session.getAttribute(USER_ID);
			if(adminUserForm.getEmailId() != null && adminUserForm.getEmailId().equals(loginUser)){
				result.rejectValue(UserValidator.EMAIL_VAR, UserValidator.EMAIL_DUPLICATE);
			} else{
				validateEmailAddress(result, adminUser.getEmailId());
			}
		}
		populateModel(model, request, adminUserForm.getCountryId());

		adminUser.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
		adminUser.setIpAddress(UIUtil.getClientIpAddr(request));
		
		/* load roles */
		List<Role> roles = loadRoles();
		if (roles != null && roles.size() > 0) {
			model.put(ROLES_LIST, roles);
		} else {
			LOGGER.debug("addAdminUserPage:: Roles list is null");
		}
		String errorMessage = null;
		if (!result.hasErrors()) {
			if (CREATE.equals(mode)) {
				try {
					if (createAdminUser(adminUser)) {
						session.setAttribute(SUCCESS_MESSAGE, UserValidator.CREATE_SUCCESS_MSG);
						return UIUtil.redirectPath(AttributeValueConstants.ADMIN_USERMGMT_PATH);
					} else {
						viewPage = USER_SAVE_FAIL_VIEW;
					}
				} catch (Exception ex) {
					LOGGER.error("saveAdminUser::Exception", ex);
					if(ex instanceof NullPointerException){
						errorMessage = UserValidator.UNABLE_TO_PROCESS_MSG;
					} else if(ex.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
					    errorMessage = Common.DUPLICATE_PHONE_NO_ERR;
					} else if (ex instanceof org.springframework.dao.DataIntegrityViolationException) {
						result.rejectValue(UserValidator.EMAIL_VAR, UserValidator.EMAIL_DUPLICATE);
					} else {
						errorMessage = UserValidator.UNKNOW_SAVE_ERROR;
					}
				}
			} else if (UPDATE.equals(mode)) {
				try {
					//for satus update mail
					adminUser.setStatusName(MasterDataUtil.getStatusName(request, adminUser.getStatus()));
					if (updateAdminUser(adminUser,(Long)session.getAttribute(ADMIN_USERID))) {
						session.setAttribute(SUCCESS_MESSAGE, UserValidator.UPDATE_SUCCESS_MSG);
						return UIUtil.redirectPath(AttributeValueConstants.ADMIN_USERMGMT_PATH);
					} else {
						viewPage = USER_MGMT_DETAILS_EDIT;
					}
				} catch (org.springframework.dao.DataIntegrityViolationException ex){
                    LOGGER.error(ex.getMessage() , ex);
					result.rejectValue(UserValidator.EMAIL_VAR, UserValidator.EMAIL_DUPLICATE);
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage() , ex);
					if(ex instanceof NullPointerException){
						errorMessage = UserValidator.UNABLE_TO_PROCESS_MSG;
					} else if(ex.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
					    errorMessage = Common.DUPLICATE_PHONE_NO_ERR;
					} else if (ex.getMessage().contains("ConstraintViolationException")) {
						result.rejectValue(UserValidator.EMAIL_VAR, UserValidator.EMAIL_DUPLICATE);
					} else {
						errorMessage = UserValidator.UNKNOW_SAVE_ERROR;
					}
				}
			}
			if (!result.hasErrors()) {
				model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			}
		}
		return viewPage;
	}

	@RequestMapping(value = "/deleteadminuser", method = RequestMethod.GET)
	public String deleteAdminUserPage(Map model,
			Locale locale, long id, HttpServletRequest request) {
		LOGGER.debug("deleteAdminUserPage method:: id " + id);
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		/* update ADMIN user authentication isDeleted to true */
		if (id != 0) {
			return deleteAuthentication(id, request);
		}
		return USER_MGMT_DEFAULT_VIEW;
	}

	@RequestMapping(value = "/resetpasswordadminuser", method = RequestMethod.GET)
	public String resetPasswordAdminUserPage(Map model, Locale locale, 
			HttpServletRequest request,@Valid AdminUserForm adminUserForm) {
		
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String emailId = request.getParameter("email");
		String name = request.getParameter("name");
		LOGGER.debug("resetPasswordAdminUserPage method:: id " + emailId);
		/* update admin user authentication resetPassword to true */
		if (emailId != null && !emailId.equals(EMPTY_STRING)){
			return resetPasswordAuthentication(emailId, (Long) request.getSession().getAttribute(LANGUAGE_ID), name, request);
		}
		return USER_MGMT_DEFAULT_VIEW;
	}

	private boolean createAdminUser(AdminUserDto adminUserDto) throws WalletException {
		boolean saveFlag = true;
		
		AdminUserDto dto = userService.createAdminUser(adminUserDto);
		
		if (dto == null) {
			LOGGER.error("ERROR::createAdminUser:: adminUserDto is null");
			saveFlag = false;
		}
		//Audit Trail service
		auditTrailService.createAuditTrail(dto.getAuthenticationId(), AuditTrailConstrain.MODULE_ADMIN_REGISTRATION,
				AuditTrailConstrain.STATUS_REGISTRATION, dto.getEmailId(), GlobalLitterals.ADMIN_USER_TYPE);
		//to set default preferences
				PreferencesDto preferencesDto = new PreferencesDto();
				preferencesDto.setLanguage(CommonConstrain.DEFAULT_LANGUAGE);
				preferencesDto.setAuthentication(dto.getAuthenticationId());
				commonService.createPreferences(preferencesDto);
		LOGGER.debug("createAdminUser:: saveFlag: " + saveFlag);
		return saveFlag;
	}

	private boolean updateAdminUser(AdminUserDto adminUserDto, Long adminId) throws WalletException {
		boolean updateFlag = true;
		AdminUserDto oldAdminUserDto = userService.loadAdminUser(adminId);
		AdminUserDto dto = userService.updateAdminUser(adminUserDto);
		if (dto == null) {
			LOGGER.error("updateAdminUser: AdminUser object is null");
			updateFlag = false;
		}
		//Audit Trail service
		dto.setRoleName(oldAdminUserDto.getRoleName());
		auditTrailService.createAuditTrail(dto.getAuthenticationId(), AuditTrailConstrain.MODULE_ADMIN_EDIT,
			AuditTrailConstrain.STATUS_EDIT, dto.getEmailId(), GlobalLitterals.ADMIN_USER_TYPE, 
			oldAdminUserDto, dto);
		LOGGER.debug("updateAdminUser:updateFlag: " + updateFlag);
		return updateFlag;
	}

	private List<Role> loadRoles() {
		List<Role> roles = new ArrayList<Role>();
		try {
			roles = userService.getAllRoles();
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);
		}
		return roles != null ? roles : null;
	}

	private String deleteAuthentication(long id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			userService.deleteAdminUser(id);
			session.setAttribute(SUCCESS_MESSAGE, UserValidator.ADMIN_USER_DELETE_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.ADMIN_USERMGMT_PATH);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);
			session.setAttribute(ERROR_MESSAGE, UserValidator.ADMIN_USER_DELETE_FAIL_MSG);
			LOGGER.error("deleteAuthentication::Exception", e);
			return UIUtil.redirectPath(AttributeValueConstants.ADMIN_USERMGMT_PATH);
		}
	}
	
	private String resetPasswordAuthentication(String emailId, Long languageId, String name, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			commonService.resetPassword(emailId, GlobalLitterals.ADMIN_USER_TYPE, languageId, name);
			session.setAttribute(SUCCESS_MESSAGE, UserValidator.RESET_PASSWORD_SUCCESS);
			return UIUtil.redirectPath(AttributeValueConstants.ADMIN_USERMGMT_PATH);

		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);
			session.setAttribute(ERROR_MESSAGE, UserValidator.RESET_PASSWORD_FAIL);
			LOGGER.error("resetPasswordAuthentication::Exception", e);
			return UIUtil.redirectPath(AttributeValueConstants.ADMIN_USERMGMT_PATH);
		}
	}

	/*Validate email is exist in database*/
	private void validateEmailAddress(BindingResult result, String email){
		Long recordStatus = commonService.emailIdExist(email, GlobalLitterals.ADMIN_USER_TYPE);
		if(recordStatus.equals(CommonConstrain.EMAIL_ALLREADY_REGISTER)){
			result.rejectValue(UserValidator.EMAIL_VAR, UserValidator.EMAIL_DUPLICATE);
		} else if(recordStatus.equals(CommonConstrain.EMAIL_REGISTER_NOT_VARIFIED)){
			result.rejectValue(UserValidator.EMAIL_VAR, Common.DUPLICATE_MAIL_REG_FAIL);
			result.rejectValue(MerchantValidator.EMAIL_NOTE_VAR, Common.EMAIL_FAILED_NOTE);
		} else if(recordStatus.equals(CommonConstrain.EMAIL_REGISTER_ACC_DELETED)){
			result.rejectValue(UserValidator.EMAIL_VAR, Common.ADMIN_USER_EMAIL_ID_DELETED_MSG);
		}
	}
	
	private void populateModel(Map model, HttpServletRequest request, Long countryId){
		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		
		model.put(STATE_LIST, MasterDataUtil.getRegions(
				request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(LANGUAGE_ID), countryId));
		
		model.put(STATUS_LIST, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataUtil.MD_USER_STATUSES));
		
		model.put(COUNTRY_PHONE_CODES, MasterDataUtil.getPhoneCodes(
				request.getSession().getServletContext()));

	}
}
