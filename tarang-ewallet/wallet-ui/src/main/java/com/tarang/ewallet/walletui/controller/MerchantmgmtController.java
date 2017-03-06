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

import com.tarang.ewallet.accountcloser.business.AccountCloserService;
import com.tarang.ewallet.accountcloser.util.AccountCloserStatus;
import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Admin;
import com.tarang.ewallet.walletui.form.MerchantForm;
import com.tarang.ewallet.walletui.form.MerchantMgmtView;
import com.tarang.ewallet.walletui.form.SearchUserForm;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.MerchantDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.MerchantValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


@SuppressWarnings({"unchecked", "rawtypes"})
@Controller
@RequestMapping("/merchantmgmt")
public class MerchantmgmtController implements Admin, AttributeConstants, AttributeValueConstants {
	
	private static final Logger LOGGER = Logger.getLogger(MerchantmgmtController.class);
	
	@Autowired
	private MerchantService merchantService;
	
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
	public String merchantsListPage(Map model,
			Locale locale, @Valid SearchUserForm searchUserForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.MERCHANT_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String url = WALLET_PATH_PREFIX + "merchantmgmt/merchantrecords";
		request.setAttribute(URLUSER_LIST , url);

		model.put(STATUS_LIST, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.MD_USER_STATUSES));
		
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
		return MERCHANT_MGMT_DEFAULT_VIEW;
	}

	@RequestMapping(value = "/merchantmgmt", method = RequestMethod.POST)
	public String merchantsListSearchPage(Map model,
			@Valid SearchUserForm searchUserForm, Locale locale,
			HttpServletRequest request) {
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.MERCHANT_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String fromdate = searchUserForm.getFromDate() != null ? searchUserForm.getFromDate() : EMPTY_STRING;
		String todate = searchUserForm.getToDate() != null ? searchUserForm.getToDate() : EMPTY_STRING;
		String name = searchUserForm.getName() != null ? searchUserForm.getName() : EMPTY_STRING;
		String emailid = searchUserForm.getEmailId() != null ? searchUserForm.getEmailId() : EMPTY_STRING;
		Long status = searchUserForm.getStatus() != null ? searchUserForm.getStatus() : 0;
		/*LOGGER.debug("fromDate   " + fromdate);
		LOGGER.debug("toDate   " + todate);
		LOGGER.debug("name   " + name);
		LOGGER.debug("emailid   " + emailid);
		LOGGER.debug("Status   " + status);*/

		String url = WALLET_PATH_PREFIX + "merchantmgmt/merchantrecords?fromdate=" + fromdate
				+ "&todate=" + todate + "&name=" + name + "&emailid=" + emailid + "&status=" + status;
		request.setAttribute(URLUSER_LIST, url);
		model.put(STATUS_LIST, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.MD_USER_STATUSES));
		return MERCHANT_MGMT_DEFAULT_VIEW;
	}

	@RequestMapping(value = "/merchantrecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody
	public JqgridResponse<UserDto> roleRecords(
			@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			HttpSession session, HttpServletRequest request, Map model,
			Locale locale) {

		JqgridResponse<UserDto> response = new JqgridResponse<UserDto>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.MERCHANT_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
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

		List<UserDto> userDtos = new ArrayList<UserDto>();
		try {
			userDtos = merchantService.getMerchantsList(qf, fromDate, toDate, name, emailId, status);
			MasterDataUtil.getUserStatus(userDtos, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), 
					MasterDataUtil.MD_USER_STATUSES));
		} catch (Exception e) {
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error(e.getMessage() , e);
		}
		response.setRows(userDtos);
		response.setRecords(qf.getTotal().toString());
		response.setTotal(qf.getTotalPages().toString());
		response.setPage(qf.getPage().toString());
		return response;
	}

	/* This method will called when clicked edit in merchant management page by admin*/
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String merchantdetails(HttpServletRequest request,
			Map model, Locale locale, @Valid MerchantForm merchantForm) {
		HttpSession session = request.getSession();
		/* In case session expired in edit page */
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.MERCHANT_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String merchantId = EMPTY_STRING;
		merchantId = request.getParameter(MERCHANT_ID);
		MerchantDto merchantDto = null;
		try {
			merchantDto = merchantService.getMerchantDetailsById(Long.parseLong(merchantId));
			if(null == merchantDto){
				LOGGER.info(" merchantDto object is null ");
				session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
				return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
			}
			if(UserStatusConstants.DELETED.equals(merchantDto.getStatus())){
				LOGGER.info(" Merchant account was deleted");
				session.setAttribute(ERROR_MESSAGE, CommonConstrain.SELECTED_RECORD_DELETED_ERRMSG);
				return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
			}
			MerchantDataUtil.convertMerchantDtoToMerchantForm(merchantForm, merchantDto);
			merchantForm.setId(Long.parseLong(merchantId));
			
			//approved status check whether account has closed
			AccountCloserDto accountCloserDto = accountCloserService.getAccountCloserByUser(merchantDto.getAuthenticationId());		
			if(accountCloserDto != null 
					&& ( accountCloserDto.getStatus().equals(AccountCloserStatus.APPROVAL) 
							|| accountCloserDto.getStatus().equals(AccountCloserStatus.CLOSED) ) ){
				model.put(HIDE_ACCOUNT_STATUS, Boolean.TRUE);
			} else{
				model.put(HIDE_ACCOUNT_STATUS, Boolean.FALSE);
			}
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage(), e);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
		}
		Boolean flag = false;
		try{
			flag = accountCloserService.getAccountCloserStatusById(merchantDto.getAuthenticationId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
		}
		model.put(HIDE_DELETE_STATUS, flag);
		
		UIUtil.populateMerchantMapValues(request, model, merchantForm.getCountryBI(), 
				merchantForm.getCountryBO(), merchantForm.getBusinessCategory(), merchantForm.getStatus(), Boolean.FALSE);

		model.put(MERCHANT_FORM, merchantForm);
		merchantForm.setStatus2(merchantForm.getStatus());
		model.put(COUNTRY_SELECTED, merchantForm.getCountryBI());
		model.put(IS_EDIT_MERCHANT_PAGE, true);
		return MERCHANT_MGMT_EDIT;
	}
	
	/* This method will called when clicked edit in merchant management page by admin*/
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String merchantedit(HttpServletRequest request, Locale locale,
			Map model, @Valid MerchantForm merchantForm, BindingResult result) {
		/* In case session expired in admin edit page */
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.MERCHANT_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String mode = merchantForm.getMode();
		Long id = merchantForm.getId();
		String emailid = merchantForm.getEmailId();
		String adminOrmerchant = request.getParameter("adminOrMerchant");
		/* for differentiating the update by merchant or ADMIN */
		request.setAttribute(MERCHANT_FORM, merchantForm);
		UIUtil.populateMerchantMapValues(request, model, merchantForm.getCountryBI(), 
				merchantForm.getCountryBO(), merchantForm.getBusinessCategory(), merchantForm.getStatus2(), Boolean.FALSE);
		if ("edit".equalsIgnoreCase(mode)) {
			MerchantValidator merchantValidator = new MerchantValidator(false, adminOrmerchant, UPDATE);
			merchantForm.setTerms(true);
			merchantValidator.validate(merchantForm, result);
			isPhoneNumberExist(result, merchantForm, UPDATE , adminOrmerchant);
			if (result.hasErrors()) {
				papulateHideDeleteStatus(merchantForm, model);
				papulateHideAccountStatus(request, model);
				if (adminOrmerchant != null && !(adminOrmerchant.equals(MERCHANT_PATH))) {
					model.put(STATUS_LIST , MasterDataUtil.getStatusListById(request, merchantForm.getStatus2()));
				}
				request.setAttribute(MERCHANT_FORM, merchantForm);
				model.put(COUNTRY_SELECTED, merchantForm.getCountryBI());
				model.put(IS_EDIT_MERCHANT_PAGE, true);
				return MERCHANT_MGMT_EDIT;
			}

			Boolean active = merchantForm.getActive();
			Boolean blocked = merchantForm.getBlocked();
			Boolean deleted = merchantForm.getDeleted();
			Long status = merchantForm.getStatus();
			MerchantDto merchantDto = merchantForm.getMerchantDto();
			merchantDto.setId(id);
			if(active != null){
				merchantDto.setActive(active);
			}
			if(blocked != null){
				merchantDto.setBlocked(blocked);
			}
			if(status != null){
				merchantDto.setStatus(status);
			}
			
			if(deleted){
				merchantDto.setStatus(UserStatusConstants.DELETED);
			}
			
			/*Set default message for approved status*/
			if(UserStatusConstants.APPROVED.equals(merchantDto.getStatus())){
				merchantDto.setUpdateReason(context.getMessage(USER_APPROVE_STATUS_DEFAULT_MSG, null, locale));
			} else{
				merchantDto.setUpdateReason(merchantForm.getUpdateReason());
			}
			
			merchantDto.setIpAddressCheck(merchantForm.getIpAddressCheck());
			merchantDto.setEmailPatternCheck(merchantForm.getEmailPatternCheck());
			merchantDto.setChargeBackCheck(merchantForm.getChargeBackCheck());
			merchantDto.setStatusName(MasterDataUtil.getStatusName(request, merchantDto.getStatus() != null 
					? merchantDto.getStatus() : merchantForm.getOldStatus()));
			if(merchantForm.getCodeCheck()){
				merchantDto.setMerchantCode(merchantForm.getMerchantCode().trim());
				merchantDto.setSuccessUrl(merchantForm.getSuccessUrl().trim()); 
				merchantDto.setFailureUrl(merchantForm.getFailureUrl().trim());
			}
			try {
				Boolean accountCloserStatusClosed = Boolean.FALSE;
				if(merchantDto.isDeleted() != null && merchantDto.isDeleted().equals(Boolean.TRUE)){
					accountCloserStatusClosed = accountCloserService.getAccountCloserStatusById(
							commonService.getAuthentication(merchantDto.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE).getId());
					if(accountCloserStatusClosed.equals(Boolean.TRUE)){
						MerchantDto oldMerchantDto = merchantService.getMerchantDetailsById(merchantForm.getId());
						merchantService.updateMerchantDetails(merchantDto, adminOrmerchant);	
						merchantDto.setQuestion1(oldMerchantDto.getQuestion1());
						merchantDto.setHint1(oldMerchantDto.getHint1());
						//Audit Trail service
						auditTrailService.createAuditTrail(oldMerchantDto.getAuthenticationId(), 
								AuditTrailConstrain.MODULE_MERCHANT_EDIT, AuditTrailConstrain.STATUS_EDIT, 
								oldMerchantDto.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE, oldMerchantDto, merchantDto);
						
						merchantForm.setExistPersonPhoneNo(merchantForm.getPhoneCountryCode1() + merchantForm.getPhoneNumber());
						merchantForm.setExistServicePhoneNo(merchantForm.getCode() + merchantForm.getPhone());
						session.setAttribute(SUCCESS_MESSAGE, "merchant.updated.successfully");
						return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
					} else {
						throw new WalletException(CommonConstrain.USER_ACCOUNT_CLOSED_EXCEPTION);
					}
				} else {
					MerchantDto oldMerchantDto = merchantService.getMerchantDetailsById(merchantForm.getId());
					merchantService.updateMerchantDetails(merchantDto, adminOrmerchant);	
					merchantDto.setQuestion1(oldMerchantDto.getQuestion1());
					merchantDto.setHint1(oldMerchantDto.getHint1());
					//Audit Trail service
					auditTrailService.createAuditTrail(oldMerchantDto.getAuthenticationId(), 
							AuditTrailConstrain.MODULE_MERCHANT_EDIT, AuditTrailConstrain.STATUS_EDIT, 
							oldMerchantDto.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE, oldMerchantDto, merchantDto);
					
					merchantForm.setExistPersonPhoneNo(merchantForm.getPhoneCountryCode1() + merchantForm.getPhoneNumber());
					merchantForm.setExistServicePhoneNo(merchantForm.getCode() + merchantForm.getPhone());
					session.setAttribute(SUCCESS_MESSAGE, "merchant.updated.successfully");
					return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
				}
			} catch (NullPointerException e) {
				LOGGER.error(e.getMessage(), e);
				session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
				return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
			} catch (Exception e) {
				String errorMessage ;
				if(e.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
				    errorMessage = Common.DUPLICATE_PHONE_NO_ERR;
				} else if(e.getMessage().contains(CommonConstrain.USER_ACCOUNT_CLOSED_EXCEPTION)){
				    errorMessage = "accountcloser.error.useraccountclosed";
				} else{
					errorMessage = UserValidator.UNABLE_TO_PROCESS_MSG;
				}
				LOGGER.error(e.getMessage() , e);
				papulateHideDeleteStatus(merchantForm, model);
				papulateHideAccountStatus(request, model);
				if (adminOrmerchant != null && !(adminOrmerchant.equals(MERCHANT_PATH))) {
					model.put(STATUS_LIST , MasterDataUtil.getStatusListById(request, merchantForm.getStatus2()));
				}
				request.setAttribute(MERCHANT_FORM, merchantForm);
				model.put(COUNTRY_SELECTED, merchantForm.getCountryBI());
				model.put(IS_EDIT_MERCHANT_PAGE, true);
				model.put("errorMessage", context.getMessage(errorMessage, null, locale));
				return MERCHANT_MGMT_EDIT;
			}
		} else if ("resetpassword".equalsIgnoreCase(mode)) {
			try {
				String name = merchantForm.getFirstName() + SPACE_STRING + merchantForm.getLastName();
				commonService.resetPassword(emailid, GlobalLitterals.MERCHANT_USER_TYPE, 
						(Long) request.getSession().getAttribute(LANGUAGE_ID),name);
				session.setAttribute(SUCCESS_MESSAGE, Common.RESET_PASSWORD_SUCCESS);
				return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
			} catch (Exception e) {
				LOGGER.error(e.getMessage() , e);
				session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
				return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
			}
		} else if ("cancel".equalsIgnoreCase(mode)) {
			return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
		}
		return  MERCHANT_MGMT_DEFAULT_VIEW;
	}

	/*This method will called when clicked view in merchant management page*/
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String merchantView(HttpServletRequest request, Map model, Locale locale) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.MERCHANT_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String merchantId = request.getParameter("merchantId");
		MerchantMgmtView merchantMgmtView = new MerchantMgmtView();
		MerchantDto merchantDto = null;
		try {
			merchantDto = merchantService.getMerchantDetailsById(Long.parseLong(merchantId));
			merchantMgmtView = MerchantDataUtil.convertMerchantDtoToMerchantMgmtView(request, merchantDto);
			merchantMgmtView.setId(Long.parseLong(merchantId));
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage(), e);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_MGMT_PATH);
		}

		model.put(STATUS_LIST, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.MD_USER_STATUSES));
		model.put("merchantMgmtView", merchantMgmtView);

		return MERCHANT_MGMT_VIEW;
	}
	
	private void papulateHideDeleteStatus(MerchantForm merchantForm, Map model){
		Boolean flag = Boolean.FALSE;
		try{
			flag = accountCloserService.getAccountCloserStatusById(
					commonService.getAuthentication(merchantForm.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE).getId());
		} catch (Exception e) {
			LOGGER.error("customerEditPage", e);
		}
		model.put(HIDE_DELETE_STATUS, flag);
	}
	
	private void papulateHideAccountStatus(HttpServletRequest request, Map model){
		String accountStatus = request.getParameter("hideStatus");
		model.put(HIDE_ACCOUNT_STATUS, accountStatus != null && "true".equals(accountStatus)? Boolean.TRUE : Boolean.FALSE );
	}
	
	private void isPhoneNumberExist(BindingResult result, MerchantForm form, String regOrUpdate, String adminOrmerchant){
		//---------------- Unique phone number validation --------------
		if ( UPDATE.equals(regOrUpdate) && adminOrmerchant.equals(ADMIN_PATH)) {
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
		}
	}
}
