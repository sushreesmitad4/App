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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.accountcloser.business.AccountCloserService;
import com.tarang.ewallet.accountcloser.util.AccountCloserStatus;
import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Admin;
import com.tarang.ewallet.walletui.form.CustomerRegFormTwo;
import com.tarang.ewallet.walletui.form.SearchUserForm;
import com.tarang.ewallet.walletui.util.CustomerDataUtil;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.CRTwoValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


/**
 * @author : prasadj
 * @date : Oct 9, 2012
 * @time : 8:52:17 AM
 * @version : 1.0.0
 * @comments: CustomermgmtController is a controller class for manage customer
 *            activities by admin user
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/customermgmt")
public class CustomermgmtController implements Admin, AttributeConstants, AttributeValueConstants {

	private static final Logger LOGGER = Logger.getLogger(AdminUserMgmtController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private AccountCloserService accountCloserService;
	
	@Autowired
	private UtilService utilService;

	@Autowired
	private ApplicationContext context;

	@RequestMapping(method = RequestMethod.GET)
	public String customersListPage(Map model, Locale locale, HttpServletRequest request, @Valid SearchUserForm searchUserForm) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.CUSTOMER_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		LOGGER.debug( " customersListPage " );
		model.put(STATUS_LIST, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataUtil.MD_USER_STATUSES));
		String url = WALLET_PATH_PREFIX + "customermgmt/customerrecords";
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
		return CUSTOMER_MGMT_DEFAULT_VIEW;
	}

	@RequestMapping(value = "/customermgmt", method = RequestMethod.POST)
	public String customersListSearchPage(Map model,
			@Valid SearchUserForm searchUserForm, HttpServletRequest request) {
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.CUSTOMER_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		model.put(STATUS_LIST, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataUtil.MD_USER_STATUSES));

		String fromdate = searchUserForm.getFromDate() != null ? searchUserForm.getFromDate() : EMPTY_STRING;
		String todate = searchUserForm.getToDate() != null ? searchUserForm.getToDate() : EMPTY_STRING;
		String name = searchUserForm.getName() != null ? searchUserForm.getName() : EMPTY_STRING;
		String emailid = searchUserForm.getEmailId() != null ? searchUserForm.getEmailId() : EMPTY_STRING;
		Long status = searchUserForm.getStatus() != null ? searchUserForm.getStatus() : 0;
		LOGGER.debug(FROMDATE + fromdate);
		LOGGER.debug(TODATE + todate);
		LOGGER.debug(NAME + name);
		LOGGER.debug(EMAILID + emailid);
		LOGGER.debug(STATUS + status);

		String url = WALLET_PATH_PREFIX + "customermgmt/customerrecords?fromdate=" + fromdate
				+ "&todate=" + todate + "&name=" + name + "&emailid=" + emailid + "&status=" + status;
		request.setAttribute(URLUSER_LIST , url);

		return CUSTOMER_MGMT_DEFAULT_VIEW;
	}

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public String customerDetailsPage(HttpServletRequest request, Map model, Long id) {
		LOGGER.debug( " customerDetailsPage " );
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		model.put("customerId", id);
		return CUSTOMER_DETAILS_VIEW;
	}

	@RequestMapping(value = "/customerrecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<UserDto> customerRecords(
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
				MenuConstants.CUSTOMER_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
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
		/* For Pagination, it should not hard coded */
		/*
		 * qf.setTotal(0); qf.setTotalPages(0); qf.setPage(0);
		 */
		
		String fromDate = request.getParameter(FROMDATE) != null ? request.getParameter(FROMDATE) : EMPTY_STRING;
		String toDate = request.getParameter(TODATE) != null ? request.getParameter(TODATE) : EMPTY_STRING;
		String name = request.getParameter(NAME) != null ? request.getParameter(NAME) : EMPTY_STRING;
		String emailId = request.getParameter(EMAILID) != null ? request.getParameter(EMAILID) : EMPTY_STRING;
		Long status = Long.parseLong(request.getParameter(STATUS) != null ? request.getParameter(STATUS) : "0");
		LOGGER.debug(FROMDATE + fromDate);
		LOGGER.debug(TODATE + toDate);
		LOGGER.debug(STATUS + status);
		List<UserDto> customerDtos = new ArrayList<UserDto>();
		try {
			customerDtos = customerService.getCustomersList(qf, fromDate, toDate, name, emailId, status);
			MasterDataUtil.getUserStatus(customerDtos, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), 
					MasterDataUtil.MD_USER_STATUSES));
		} catch (Exception e) {
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error("customerRecords", e);
		}
		LOGGER.debug("userDtos list size: " + customerDtos.size());
		if (customerDtos != null && customerDtos.isEmpty()) {
			LOGGER.debug("customerDtos list is empty");
		}

		response.setRows(customerDtos);
		response.setRecords(qf.getTotal().toString());
		response.setTotal(qf.getTotalPages().toString());
		response.setPage(qf.getPage().toString());
		return response;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String customerEditPage(HttpServletRequest request, Map model, Locale locale,
			@Valid CustomerRegFormTwo customerRegFormTwo, Long id) {
		
		LOGGER.debug( " customerEditPage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.CUSTOMER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		CustomerDto customerDto = null;
		if (id != 0) {
			try {
				customerDto = customerService.getCustomerDto(id);
				if(null == customerDto){
					LOGGER.info("customerDto object is null");
					session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
					return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_MGMT_PATH);
				}
				if(UserStatusConstants.DELETED.equals(customerDto.getStatus())){
					LOGGER.info(" Customer account was deleted");
					session.setAttribute(ERROR_MESSAGE, CommonConstrain.SELECTED_RECORD_DELETED_ERRMSG);
					return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_MGMT_PATH);
				}
				session.setAttribute(CUS_ID , customerDto.getId());
				
				//approved status check whether account has closed
				papulateHideAccountStatus(customerDto.getAuthenticationId(), model);
			} catch (Exception e) {
				LOGGER.error("customerEditPage", e);
				session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
				return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_MGMT_PATH);
			}
		}
		Boolean flag = false;
		try{
			flag = accountCloserService.getAccountCloserStatusById(customerDto.getAuthenticationId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		model.put(HIDE_DELETE_STATUS, flag);
		model.put("isphoneupdate", "update");
		
		if (customerDto != null) {
			customerRegFormTwo.setCustomer(customerDto);
			Long statusid = customerRegFormTwo.getStatus();
			request.getSession().setAttribute(STATUS_ID, statusid);
			customerRegFormTwo.setDateOfBirth(DateConvertion.dateToString(customerDto.getDateOfBirth()));
			customerRegFormTwo.setCreationDate(DateConvertion.dateToString(customerDto.getCreationDate()));	
			customerRegFormTwo.setIpAddressCheck(customerDto.getIpAddressCheck());
			customerRegFormTwo.setEmailPatternCheck(customerDto.getEmailPatternCheck());
			papulateMapForEditPage(request, model, customerRegFormTwo, customerDto);			
			return CUSTOMER_MGMT_EDIT;
		}
		return CUSTOMER_MGMT_DEFAULT_VIEW;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String customerUpdate(HttpServletRequest request, Map model, Locale locale,
			@ModelAttribute("customerRegFormTwo") CustomerRegFormTwo customerRegFormTwo, BindingResult result) {
		LOGGER.debug( " customerUpdate " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.CUSTOMER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		model.put(STATUS_LIST, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataUtil.MD_USER_STATUSES));
		String mode = customerRegFormTwo.getMode();
		String errorMessage ;
		if (UPDATE.equalsIgnoreCase(mode)) {
			Long customerId = (Long) session.getAttribute(CUS_ID);
			CustomerDto customerDto = null;
			try {
				customerDto = customerService.getCustomerDto(customerId);
				customerRegFormTwo.setTerms(true);
				customerRegFormTwo.setExistphone(customerDto.getPhoneNo());
				//In jsp profile skip means True but in Db save as a false
				customerRegFormTwo.setProfileComplet(!customerDto.getProfileComplet());
				CRTwoValidator crTwoValidator = new CRTwoValidator();
				crTwoValidator.validate(customerRegFormTwo, result);				
				//approved status check whether account has closed
				papulateHideAccountStatus(customerDto.getAuthenticationId(), model);
				model.put("isphoneupdate", "update");
			} catch (Exception e) {
				LOGGER.error(e.getMessage() , e );
				if(e instanceof NullPointerException){
					errorMessage = UserValidator.UNABLE_TO_PROCESS_MSG;
				} else{
					errorMessage = UserValidator.UNABLE_TO_PROCESS_MSG;
				}
				//approved status check whether account has closed
				papulateHideDeleteStatus(customerDto.getAuthenticationId(), model);
				papulateHideAccountStatus(customerDto.getAuthenticationId(), model);
				papulateMapForEditPage(request, model, customerRegFormTwo, customerDto);
				model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
				LOGGER.error("customerUpdate", e);
				return CUSTOMER_MGMT_EDIT;
			}
			
			if (result.hasErrors()) {
				papulateHideDeleteStatus(customerDto.getAuthenticationId(), model);
				papulateHideAccountStatus(customerDto.getAuthenticationId(), model);
				papulateMapForEditPage(request, model, customerRegFormTwo, customerDto);
				return CUSTOMER_MGMT_EDIT;
			}
			try {
				CustomerDto customerDtoCopy = (CustomerDto)customerDto.clone();
				CustomerDto customerDto2 = CustomerDataUtil.setCustomerDto(customerRegFormTwo,
						customerDto != null ? customerDto: new CustomerDto());
				/*Set default message for approved status*/
				if(UserStatusConstants.APPROVED.equals(customerDto2.getStatus())){
					customerDto2.setUpdateReason(context.getMessage(USER_APPROVE_STATUS_DEFAULT_MSG, null, locale));
				}
				customerDto2.setStatusName(MasterDataUtil.getStatusName(request, customerDto2.getStatus() != null ?
						customerDto2.getStatus() : customerRegFormTwo.getOldStatus()));
				Boolean accountCloserStatusClosed = Boolean.FALSE;
				if(customerDto2.isDeleted() != null && customerDto2.isDeleted().equals(Boolean.TRUE)){
					accountCloserStatusClosed = accountCloserService.getAccountCloserStatusById(customerDto2.getAuthenticationId());
					if(accountCloserStatusClosed.equals(Boolean.TRUE)){
						customerService.updateCustomerDetails(customerDto2);
						//Audit Trail service
						auditTrailService.createAuditTrail(customerDto.getAuthenticationId(), 
								AuditTrailConstrain.MODULE_CUSTOMER_EDIT, AuditTrailConstrain.STATUS_EDIT, 
								customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, customerDtoCopy, customerDto2);
						session.setAttribute(SUCCESS_MESSAGE, CRTwoValidator.CUSTOMER_UPDATE_SUCCESS_MSG);
						return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_MGMT_PATH);
					} else {
						model.put(ERROR_MESSAGE, context.getMessage(CommonConstrain.USER_ACCOUNT_CLOSED_EXCEPTION, null, locale));
						papulateMapForEditPage(request, model, customerRegFormTwo, customerDto);
						return CUSTOMER_MGMT_EDIT;
					}
				} else{
					customerService.updateCustomerDetails(customerDto2);
					//Audit Trail service
					auditTrailService.createAuditTrail(customerDto.getAuthenticationId(), 
							AuditTrailConstrain.MODULE_CUSTOMER_EDIT, AuditTrailConstrain.STATUS_EDIT, 
							customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, customerDtoCopy, customerDto2);
					session.setAttribute(SUCCESS_MESSAGE, CRTwoValidator.CUSTOMER_UPDATE_SUCCESS_MSG);
					return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_MGMT_PATH);
				}
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage() , ex );
				if(ex instanceof NullPointerException){
					errorMessage = UserValidator.UNABLE_TO_PROCESS_MSG;
				} else if(ex.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
				    errorMessage = Common.DUPLICATE_PHONE_NO_ERR;
				} else if(ex.getMessage().contains(CommonConstrain.USER_ACCOUNT_CLOSED_EXCEPTION)){
				    errorMessage = "accountcloser.error.useraccountclosed";
				} else if(ex instanceof CloneNotSupportedException){
				    errorMessage = AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR;
				} else{
					errorMessage = UserValidator.UNABLE_TO_PROCESS_MSG;
				}
				model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
				papulateHideDeleteStatus(customerDto.getAuthenticationId(), model);
				papulateHideAccountStatus(customerDto.getAuthenticationId(), model);
				papulateMapForEditPage(request, model, customerRegFormTwo, customerDto);
				LOGGER.error(ex);
				return CUSTOMER_MGMT_EDIT;
			}
		} else if ("resetpassword".equalsIgnoreCase(mode)) {
			String emailid = customerRegFormTwo.getEmailId();
			String name = customerRegFormTwo.getFirstName()+ SPACE_STRING + customerRegFormTwo.getLastName();
			try {
				if (commonService.resetPassword(emailid, GlobalLitterals.CUSTOMER_USER_TYPE, 
						(Long) request.getSession().getAttribute(LANGUAGE_ID), name)) {
					model.put(STATUS_LIST, MasterDataUtil.getSimpleDataMap(
							request.getSession().getServletContext(),
							(Long) request.getSession().getAttribute(LANGUAGE_ID),
							MasterDataUtil.MD_USER_STATUSES));
					session.setAttribute(SUCCESS_MESSAGE, Common.RESET_PASSWORD_SUCCESS);
					return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_MGMT_PATH);
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage() , e);
				session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
				return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_MGMT_PATH);
			}
		} else if (CANCEL.equalsIgnoreCase(mode)) {
			return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_MGMT_PATH);
		}
		return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_MGMT_PATH);
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String customerViewPage(HttpServletRequest request, Map model,
			@ModelAttribute("customerRegFormTwo") CustomerRegFormTwo customerRegFormTwo, Long id) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.CUSTOMER_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		CustomerDto customerDto = null;
		if (id != 0) {
			try {
				customerDto = customerService.getCustomerDto(id);
			} catch (Exception e) {
				LOGGER.error(e.getMessage() , e);
				session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
				return UIUtil.redirectPath(CUSTOMER_MGMT_PATH);
			}
		}
		if (customerDto != null) {
			customerRegFormTwo.setCustomer(customerDto);
			model.put(COUNTRY_NAME, MasterDataUtil.getCountryName(request,
					MasterDataUtil.MD_COUNTRIES, customerDto.getCountry()));
			model.put(STATE_NAME, MasterDataUtil.getRegions(request.getSession().getServletContext(), 
						(Long) request.getSession(false).getAttribute(LANGUAGE_ID), 
						customerDto.getCountry()).get(customerDto.getState()));
			
			model.put(STATUS_NAME, MasterDataUtil.getStatusName(request, customerDto.getStatus()));
			String str =null;
			str = DateConvertion.dateToString(customerDto.getDateOfBirth());
			model.put("dateOfBirth", str);

			return CUSTOMER_MGMT_VIEW;
		}
		return CUSTOMER_MGMT_DEFAULT_VIEW;
	}

	@RequestMapping(value = "/customerviewcancel", method = RequestMethod.GET)
	public String customerViewCancel( HttpServletRequest request, Map model,
			@ModelAttribute("customerRegFormTwo") CustomerRegFormTwo customerRegFormTwo) {
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.CUSTOMER_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String mode = customerRegFormTwo.getMode();
		if (CANCEL.equals(mode)) {
			return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_MGMT_PATH);
		}
		return CUSTOMER_MGMT_DEFAULT_VIEW;
	}
	
	private void papulateMapForEditPage(HttpServletRequest request, Map model, 
			CustomerRegFormTwo customerRegFormTwo, CustomerDto customerDto){
		
		model.put(STATUS_LIST, MasterDataUtil.getStatusListById(
				request, (Long) request.getSession().getAttribute(STATUS_ID)));
	
		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
	
		model.put(STATE_LIST, MasterDataUtil.getRegions(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				customerRegFormTwo.getCountry()));
	
		model.put(PERSON_TITLES, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.TITLES));
		
		model.put(COUNTRY_PHONE_CODES, MasterDataUtil.getPhoneCodes(
				request.getSession().getServletContext()));

		model.put(COUNTRY_SELECTED, customerDto.getCountry());
		model.put(IS_EDIT_CUSTOMER_PAGE, true);
		
	}
	
	private void papulateHideDeleteStatus(Long customerAuthId, Map model){
		Boolean flag = false;
		try{
			flag = accountCloserService.getAccountCloserStatusById(customerAuthId);
		} catch (Exception e) {
			LOGGER.error("customeredit page", e);
		}
		model.put(HIDE_DELETE_STATUS, flag);
	}
	
	private void papulateHideAccountStatus(Long customerAuthId, Map model){
		AccountCloserDto accountCloserDto = null;
		try {
			accountCloserDto = accountCloserService.getAccountCloserByUser(customerAuthId);
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
		}
		if(accountCloserDto != null 
				&& ( accountCloserDto.getStatus().equals(AccountCloserStatus.APPROVAL) 
						|| accountCloserDto.getStatus().equals(AccountCloserStatus.CLOSED) ) ){
			model.put(HIDE_ACCOUNT_STATUS, Boolean.TRUE);
		} else{
			model.put(HIDE_ACCOUNT_STATUS, Boolean.FALSE);
		}
	}
}
