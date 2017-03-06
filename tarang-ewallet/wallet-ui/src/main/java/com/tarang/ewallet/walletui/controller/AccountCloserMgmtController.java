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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.accountcloser.business.AccountCloserService;
import com.tarang.ewallet.accountcloser.util.AccountCloserStatus;
import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Admin;
import com.tarang.ewallet.walletui.form.AccountCloserFormView;
import com.tarang.ewallet.walletui.form.SearchAccountCloserForm;
import com.tarang.ewallet.walletui.util.AccountCloserHelper;
import com.tarang.ewallet.walletui.util.DisputeSearchUtil;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.AccountCloserMgmtValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/accountclosermgmt")

public class AccountCloserMgmtController implements Admin, AttributeConstants, AttributeValueConstants{
	
	private static final Logger LOGGER = Logger.getLogger(AccountCloserMgmtController.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private AccountCloserService accountCloserService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired 
	private UtilService utilService;
	
	@Autowired 
	private AuditTrailService auditTrailService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String accountCloserListPage(Map model, Locale locale, 
			@Valid SearchAccountCloserForm searchAccountCloserForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String sucMsg = (String) session.getAttribute(SUCCESS_MESSAGE);
		if(sucMsg != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(sucMsg, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
	
		String errMsg = (String) session.getAttribute(ERROR_MESSAGE);
		if(errMsg != null){
			model.put(ERROR_MESSAGE, context.getMessage(errMsg, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		
		String fromDate = request.getParameter("fdate"); 
		String toDate = request.getParameter("tdate"); 
		Long userType = Long.parseLong(request.getParameter(USER_TYPE) != null ? request.getParameter(USER_TYPE) : "0");
		
		model.put(USER_TYPE_LIST, MasterDataUtil.getSimpleDataMapForUserTypeCustMer(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.MD_USER_TYPES));
		
		String url = null;
		model.put("searchAccountCloserForm", searchAccountCloserForm );
		searchAccountCloserForm.setFromDate(fromDate);
		searchAccountCloserForm.setToDate(toDate);
		
		int accountCloseDay = utilService.getAccountCloseDays();
		
		if(fromDate != null && !"".equals(fromDate)){	
			if(!DisputeSearchUtil.checkDate(fromDate, accountCloseDay)){
				url = WALLET_PATH_PREFIX + "accountclosermgmt/accountcloserRecords";
				request.setAttribute(URLUSER_LIST, url);
				model.put(ERROR_MESSAGE, context.getMessage("error.msg.notallow.toget.accountclose", null, locale));
				return ACCOUNT_CLOSER_LIST_VIEW;
			}
		} else {
			fromDate = CommonUtil.getBeforeDates(accountCloseDay);
		}
		
		if(toDate != null && !"".equals(toDate)){
			boolean b1 = DisputeSearchUtil.checkDate(toDate, accountCloseDay);
			if(!b1){
				url = WALLET_PATH_PREFIX + "accountclosermgmt/accountcloserRecords";
				request.setAttribute(URLUSER_LIST , url);
				model.put(ERROR_MESSAGE, context.getMessage("error.msg.notallow.toget.accountclose", null, locale));
				return ACCOUNT_CLOSER_LIST_VIEW;
			}
		} else {
			toDate = CommonUtil.getBeforeDates(0);
		}
	
		url = WALLET_PATH_PREFIX + "accountclosermgmt/accountcloserRecords?fromDate="
				+ fromDate + "&toDate=" + toDate + "&userType=" + userType;
		request.setAttribute(URLUSER_LIST, url);
		return ACCOUNT_CLOSER_LIST_VIEW;
	}
	
	@RequestMapping(value = "/accountcloserRecords", method = RequestMethod.GET, headers = JSON_HEADER,  produces= JSON_PRODUCER)
	@ResponseBody
	public JqgridResponse<AccountCloserDto> roleRecords(
			@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			HttpSession session, HttpServletRequest request, Map model, @Valid SearchAccountCloserForm searchAccountCloserForm,
			Locale locale) {

		JqgridResponse<AccountCloserDto> response = new JqgridResponse<AccountCloserDto>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return response;	
	    }
	    
	    QueryFilter qf = new QueryFilter();
		qf.setFilterString(filters);
		qf.setPage(page);
		qf.setRows(rows);
		qf.setSidx(sidx);
		qf.setSord(sord);		
	    
		String fromd = request.getParameter("fromDate")  ;
		String tod = request.getParameter("toDate") ;
				
		int accountCloseDay =  utilService.getAccountCloseDays();
		if( fromd == null || NULL.equals(fromd)) {
			fromd = CommonUtil.getBeforeDates(accountCloseDay);
		}
		if(tod == null || NULL.equals(tod)) {
			tod = CommonUtil.getBeforeDates(0);
		}
		Date fromDate = DateConvertion.stirngToDate(fromd);
		Date toDate = DateConvertion.stirngToDate(tod);
		
		Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
		if(languageId == null){
			languageId = MasterDataConstants.DEFAULT_LANGUAGE;
		}
		
		Long userType = 0L;
		if( request.getParameter(USER_TYPE) != null && !NULL.equals(request.getParameter(USER_TYPE))){
			userType = Long.valueOf(request.getParameter(USER_TYPE));
		}

		String userTypeString = "0";
		if(userType.equals(CUSTOMER_USER_TYPE_ID)){
			userTypeString = CUSTOMER_USER_TYPE;
		} else if(userType.equals(MERCHANT_USER_TYPE_ID)){
			userTypeString = MERCHANT_USER_TYPE;
		}
		Integer limit = utilService.getReportLimit();
		List<AccountCloserDto> accountCloserDtos = new ArrayList<AccountCloserDto>();
		try {
			//pass toDate as one day future date.
			accountCloserDtos = accountCloserService.getAccountCloserList(limit, languageId, fromDate, 
					DateConvertion.getToDate(toDate), userTypeString);
		} catch (Exception e) {
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error(e.getMessage() , e);
		}
		response.setRows(accountCloserDtos);
		int ps = DEFAULT_PAGE_SIZE;
		int n = accountCloserDtos.size()/ps;
		if( accountCloserDtos.size()/ps*ps != accountCloserDtos.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
	}
	
	@RequestMapping(value = "/editOrView", method = RequestMethod.GET)
	public String accountCloserView(HttpServletRequest request, Map model, Locale locale) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		String accountCloserId = request.getParameter("accountcloserId");
		Long languageId = (Long) request.getSession().getAttribute(LANGUAGE_ID) != null ? (Long) request.getSession().getAttribute(LANGUAGE_ID) : 1L;
		AccountCloserFormView accountCloserFormView = new AccountCloserFormView();
		AccountCloserDto accountCloserDto = null;
		try {
			accountCloserDto = accountCloserService.getAccountCloserForView(Long.parseLong(accountCloserId), languageId);
			getWalletBalances(request, accountCloserDto);
			AccountCloserHelper.convertAccCloserDtoToAccCloserFormView(request, accountCloserDto, accountCloserFormView);
			accountCloserFormView.setId(accountCloserDto.getId());
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage() , e);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.ACCOUNT_CLOSER_PATH);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.ACCOUNT_CLOSER_PATH);
		}
		
		model.put(ACCOUNTCLOSER_FORMVIEW , accountCloserFormView);

		Map<Long, String> maps = AccountCloserHelper.getAdminAccountCloserStatuses(request);
		model.put(ACCOUNTCLOSER_STATUS , maps);
		if("view".equalsIgnoreCase(request.getParameter("mode"))){
			if(MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT, MenuConstants.VIEW_PERMISSION)){
				model.put("isViewAccountClose", true);
				return ACCOUNT_CLOSER_VIEW;	
			} else {
				session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
				return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
			}
		} else if("edit".equalsIgnoreCase(request.getParameter("mode"))){
			if(MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT, MenuConstants.MANAGE_PERMISSION)){
				model.put("isEditAccountClose", true);
				return ACCOUNT_CLOSER_UPDATE;
			} else {
				session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
				return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
			}
		}
		return UIUtil.redirectPath(AttributeValueConstants.ACCOUNT_CLOSER_PATH);
	}

	private void getWalletBalances(HttpServletRequest request,
			AccountCloserDto accountCloserDto) {
		Map<Long, UserWallet> userWallets = CommonUtil.getWalletMap(commonService.getUserWallets(accountCloserDto.getAuthId()));
		accountCloserDto.setWallets(AccountCloserHelper.getAllWallets(userWallets, request));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateMessage(HttpServletRequest request, Map model,
			Locale locale, @Valid AccountCloserFormView accountCloserFormView,
			BindingResult result) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		Long userType = UIUtil.getUserTypeID((String)request.getSession().getAttribute(USER_TYPE));
		new AccountCloserMgmtValidator().validate(accountCloserFormView, result);
		try {
			if(result.hasErrors()){
				Long languageId = (Long) request.getSession().getAttribute(LANGUAGE_ID) != null ? (Long) request.getSession().getAttribute(LANGUAGE_ID) : 1L;
				AccountCloserDto accountCloserDto = accountCloserService.getAccountCloserForView(accountCloserFormView.getId(), languageId);
				getWalletBalances(request, accountCloserDto);
				AccountCloserHelper.convertAccCloserDtoToAccCloserFormView(request, accountCloserDto, accountCloserFormView);
				accountCloserFormView.setId(accountCloserDto.getId());
				model.put(ACCOUNTCLOSER_FORMVIEW , accountCloserFormView);
				Map<Long, String> statusmap = AccountCloserHelper.getAdminAccountCloserStatuses(request);
				model.put( ACCOUNTCLOSER_STATUS , statusmap);
				return ACCOUNT_CLOSER_UPDATE;
			}
			 accountCloserService.addMessageByAdmin(accountCloserFormView.getId(), accountCloserFormView.getMessage(),
					 userType, accountCloserFormView.getAccCloserStatus());
			 
			//Audit Trail service
			auditTrailService.createAuditTrailForAccountCloser(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_ACCOUNT_CLOSER_UPDATE, 
					AuditTrailConstrain.STATUS_UPDATE, (String)session.getAttribute(USER_ID), (String)session.getAttribute(USER_TYPE), AccountCloserStatus.PENDING, accountCloserFormView.getAccCloserStatus());			
				
			 session.setAttribute(SUCCESS_MESSAGE, "account.closer.updated.successfully");
			 return UIUtil.redirectPath(AttributeValueConstants.ACCOUNT_CLOSER_PATH);
		} catch (Exception e) {
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.UNABLE_TO_PROCESS_MSG, null, locale));
			LOGGER.error(e.getMessage() , e);
			return UIUtil.redirectPath(AttributeValueConstants.ACCOUNT_CLOSER_PATH);
		}
	}
	
}