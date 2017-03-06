/**
 * 
 */
package com.tarang.ewallet.walletui.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.dto.VelocityAndThresholdDto;
import com.tarang.ewallet.dto.WalletThresholdDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.business.HttpService;
import com.tarang.ewallet.masterdata.business.MasterDataService;
import com.tarang.ewallet.masterdata.util.MasterWalletGridDisplayDto;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.WalletThreshold;
import com.tarang.ewallet.scheduler.jobs.AccountCloserJob;
import com.tarang.ewallet.scheduler.jobs.CancelNonRegisterWalletTxnsJob;
import com.tarang.ewallet.scheduler.jobs.DisputeForceWithdrawalJob;
import com.tarang.ewallet.scheduler.jobs.PgSettlementJob;
import com.tarang.ewallet.scheduler.jobs.ReconcileJob;
import com.tarang.ewallet.scheduler.jobs.ResentEmailForFailedEmailRecords;
import com.tarang.ewallet.scheduler.util.SchedulerGroupNames;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.transaction.business.VelocityAndThresholdService;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Admin;
import com.tarang.ewallet.walletui.form.VelocityAndThresholdForm;
import com.tarang.ewallet.walletui.form.WalletThresholdForm;
import com.tarang.ewallet.walletui.util.AdminUtil;
import com.tarang.ewallet.walletui.util.AllWalletsAmmount;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.VelocityAndThresholdValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


/**
 * @author prasadj
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/admin")
public class AdminController implements Admin, AttributeConstants, AttributeValueConstants, GlobalLitterals {
	
	private static final Logger LOGGER = Logger.getLogger(AdminController.class);
	
	@Autowired
	private VelocityAndThresholdService velocityAndThresholdService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private MasterDataService masterDataService;
	
	@Autowired
	private TransactionWalletService transactionWalletService;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private HttpService httpService;

	@RequestMapping(method = RequestMethod.GET)
	public String defaultPage(Map model,HttpServletRequest request, Locale locale) throws WalletException{
		
		LOGGER.debug( " defaultPage " );
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		String message = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(message != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(message, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		
		message = (String)session.getAttribute(ERROR_MESSAGE);
		if(message != null){
			model.put(ERROR_MESSAGE, context.getMessage(message, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		
		request.setAttribute(URL_VELOCITY_LIST, VELOCITY_RECORDS_URL);	
		request.setAttribute(URL_WALLET_THRESHOLD_LIST, VELOCITY_RECORDS_URL);
		
		model.put(LASTDAY_CUSTOMERS_COUNT, 
				commonService.getLastDayCreatedNumberOfAccounts(DateConvertion.getFromDate(1), DateConvertion.getToDate(1), CUSTOMER_USER_TYPE));
		model.put(LASTWEEK_CUSTOMERS_COUNT, 
				commonService.getLastWeekCreatedNumberOfAccounts(DateConvertion.getFromDate(WEEK_DAYS), CUSTOMER_USER_TYPE));
		model.put(TOTAL_CUSTOMERS_COUNT, commonService.getTotalNumberOfAccounts(CUSTOMER_USER_TYPE));
		
		model.put(LASTDAY_MERCHANTS_COUNT, 
				commonService.getLastDayCreatedNumberOfAccounts(DateConvertion.getFromDate(1), DateConvertion.getToDate(1), MERCHANT_USER_TYPE));
		model.put(LASTWEEK_MERCHANTS_COUNT, 
				commonService.getLastWeekCreatedNumberOfAccounts(DateConvertion.getFromDate(WEEK_DAYS), MERCHANT_USER_TYPE));
		model.put(TOTAL_MERCHANTS_COUNT, commonService.getTotalNumberOfAccounts(MERCHANT_USER_TYPE));
		
		List<Object[]> p2p = transactionWalletService.getLastNdaysTransactionsCountDayWise(DateConvertion.getFromDate(MONTH_DAYS), 
				DateConvertion.getToDate(1), 1L);
		request.setAttribute("p2p", convertListToString(getDateCountMap(p2p), MONTH_DAYS));
		
		List<Object[]> p2m = transactionWalletService.getLastNdaysTransactionsCountDayWise(DateConvertion.getFromDate(MONTH_DAYS), 
				DateConvertion.getToDate(1), 2L);
		request.setAttribute("p2m", convertListToString(getDateCountMap(p2m), MONTH_DAYS));
		String url1 = WALLET_PATH_PREFIX + "admin/allwallettablerecords";
		String url2 = WALLET_PATH_PREFIX + "admin/adminwallettablerecords";
		model.put("allWalletsUrl", url1);
		model.put("adminWalletUrl", url2);
		
		return DEFAULT_VIEW;
	}

	@RequestMapping(value = "/underconstruction", method = RequestMethod.GET)
	public String underConstructionPage(HttpServletRequest request, Map model){
		
		LOGGER.debug( " underConstructionPage " );
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		return VIEW_UNDERCONSTRUCTION;
	}
	
	//----------Transaction Threshold--------------
	@RequestMapping(value="/velocityandthreshold", method = RequestMethod.GET)
	public String velocityAndThreshold(Map model, VelocityAndThresholdForm velocityAndThresholdForm, Locale locale, HttpServletRequest request){
		
		LOGGER.debug(" velocityandthresholdGetPage ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TRANSACTION_THRESHOLD_MI, MenuConstants.VIEW_PERMISSION);
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
		LOGGER.debug( " defaultPage :" );		
		String url = WALLET_PATH_PREFIX + "admin/velocityrecords";
		request.setAttribute(URL_VELOCITY_LIST, url);	
		return VELOCITY_THRESHOLD_GRID;
	}
	
	@RequestMapping(value = "/velocityrecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<VelocityAndThresholdDto> velocityRecords(
			@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			Map model, Locale locale, HttpSession session,
			HttpServletRequest request) {

		JqgridResponse<VelocityAndThresholdDto> response = new JqgridResponse<VelocityAndThresholdDto>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TRANSACTION_THRESHOLD_MI, MenuConstants.VIEW_PERMISSION);
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
		
		List<VelocityAndThresholdDto> velocityList = new ArrayList<VelocityAndThresholdDto>();
		try {
			velocityList = velocityAndThresholdService.getVelocityAndThresholdList();
			for(VelocityAndThresholdDto t:velocityList){
				t.setCountryName(MasterDataUtil.getCountryName(request, MasterDataUtil.MD_COUNTRIES, t.getCountry()));
				t.setCurrencyName(MasterDataUtil.getCurrencyName(request.getSession().getServletContext(), 
											(Long) request.getSession().getAttribute(LANGUAGE_ID), t.getCurrency()));
				t.setTransactiontypeName(getOperationTypesList(request, t.getUserType()).get(t.getTransactiontype()));
				t.setStrMinimumAmount(UIUtil.getConvertedAmountInString(t.getMinimumamount()));
				t.setStrMaximumAmount(UIUtil.getConvertedAmountInString(t.getMaximumamount()));
				t.setUserTypeName(MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataConstants.MD_USER_TYPES).get(t.getUserType()));
			}
		} catch (Exception e) {
			String errorMessage = UserValidator.NO_RECORDS_FOUND;
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			LOGGER.error("velocityrecords", e);
		}
		response.setRows(velocityList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = velocityList.size()/ps;
		if( velocityList.size()/ps*ps != velocityList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
	}
	
	@RequestMapping(value = "/addvelocity", method = RequestMethod.GET)
	public String addVelocityPage(Map model, @Valid VelocityAndThresholdForm velocityAndThresholdForm, 
				HttpServletRequest request){
		
		LOGGER.debug( " addValocityPage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TRANSACTION_THRESHOLD_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		papulateVelocityMasterData(request, model,velocityAndThresholdForm, true);
		return VELOCITY_THRESHOLD_ADD;
	}
	
	@RequestMapping(value="/addvelocity", method = RequestMethod.POST)
	public String addvelocitySave(@Valid VelocityAndThresholdForm velocityAndThresholdForm, BindingResult result,
			Map model, HttpServletRequest request, Locale locale){
		
		LOGGER.debug(" addvelocitySavePostPage ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TRANSACTION_THRESHOLD_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		VelocityAndThresholdValidator velocityValidator = new VelocityAndThresholdValidator();
		velocityValidator.validate(velocityAndThresholdForm, result);
		if(result.hasErrors()){
			papulateVelocityMasterData(request, model,velocityAndThresholdForm, true);
			return VELOCITY_THRESHOLD_ADD;
		} else{
			VelocityAndThresholdDto velocityAndThresholdDto = new VelocityAndThresholdDto();
			AdminUtil.convertVelocityFormToDto(velocityAndThresholdForm, velocityAndThresholdDto);
			try {
				velocityAndThresholdService.createVelocityAndThreshold(velocityAndThresholdDto);
				//Audit Trail service
				auditTrailService.createAuditTrail((Long)session.getAttribute(AUTHENTICATION_ID), AuditTrailConstrain.MODULE_VELOCITY_AND_THRESHOLD_CREATION, 
						AuditTrailConstrain.STATUS_CREATE, (String)session.getAttribute(USER_ID), (String)session.getAttribute(USER_TYPE));
				session.setAttribute(SUCCESS_MESSAGE, TRANSACTION_THRESHOLD_CREATE_SUCCESS_MSG);
				return UIUtil.redirectPath(TRANSACTION_THRESHOLD_PATH);
			} catch (WalletException e) {
				LOGGER.error("Add velocity page "+ e.getMessage() , e);
				papulateVelocityMasterData(request, model,velocityAndThresholdForm, true);
				String errorMessage = null;
				if(e.getMessage().contains(CommonConstrain.DUPLICATE_ENTRY)){
					errorMessage = TRANSACTION_THRESHOLD_ALREADY_EXISTS;
				} else{
					errorMessage = TRANSACTION_THRESHOLD_FAILS_TO_CREATE;
				}
				model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
				return VELOCITY_THRESHOLD_ADD;
			}
		}
	}
	
	@RequestMapping(value="/editvelocity", method = RequestMethod.GET)
	public String editVelocityPage(Map model, @Valid VelocityAndThresholdForm velocityAndThresholdForm, Locale locale,
		HttpServletRequest request){
		
		LOGGER.debug( " editValocityPage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TRANSACTION_THRESHOLD_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
 		Long velocityAndThresholdID = Long.valueOf(request.getParameter("id"));
 		try{
 			VelocityAndThreshold velocityAndThreshold = velocityAndThresholdService.getVelocityAndThreshold(velocityAndThresholdID);
 			papulateVelocityAndThresholdFrom(request, velocityAndThresholdForm, velocityAndThreshold, true);
 		} catch (Exception e) {
 			LOGGER.error(e.getMessage() , e);
 			model.put(ERROR_MESSAGE, context.getMessage(TRANSACTION_THRESHOLD_FAILS_TO_LOAD, null, locale));
 			String url = WALLET_PATH_PREFIX + "admin/velocityrecords";
			request.setAttribute(URL_VELOCITY_LIST, url);
 			return VELOCITY_THRESHOLD_GRID;
 		}
 		return VELOCITY_THRESHOLD_EDIT;
 	}
     
    @RequestMapping(value = "/editvelocity", method = RequestMethod.POST)
 	public String editVelocitySave(@Valid VelocityAndThresholdForm velocityAndThresholdForm, BindingResult result, 
 			Map model, HttpServletRequest request, Locale locale) {
    	
    	HttpSession session = request.getSession();    	
    	if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
    	Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TRANSACTION_THRESHOLD_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
 		VelocityAndThresholdValidator velocityValidator = new VelocityAndThresholdValidator();
		velocityValidator.validate(velocityAndThresholdForm, result);
		if(result.hasErrors()){
			return VELOCITY_THRESHOLD_EDIT;
		} else{
			try{
				VelocityAndThreshold velocityAndThreshold = velocityAndThresholdService.getVelocityAndThreshold(velocityAndThresholdForm.getId());	
				VelocityAndThreshold oldObject = (VelocityAndThreshold)velocityAndThreshold.clone();
				
				VelocityAndThresholdDto velocityDto = new VelocityAndThresholdDto();
				velocityDto.setId(velocityAndThresholdForm.getId());
				velocityDto.setMinimumamount(Double.parseDouble(velocityAndThresholdForm.getMinimumamount()));
				velocityDto.setMaximumamount(Double.parseDouble(velocityAndThresholdForm.getMaximumamount()));
				velocityAndThresholdService.updateVelocityAndThreshold(velocityDto);
				
				//Audit Trail service
				velocityAndThreshold.setMinimumamount(Double.parseDouble(velocityAndThresholdForm.getMinimumamount()));
				velocityAndThreshold.setMaximumamount(Double.parseDouble(velocityAndThresholdForm.getMaximumamount()));
				auditTrailService.createAuditTrail((Long)session.getAttribute(AUTHENTICATION_ID), AuditTrailConstrain.MODULE_VELOCITY_AND_THRESHOLD_EDIT, 
						AuditTrailConstrain.STATUS_EDIT, (String)session.getAttribute(USER_ID), (String)session.getAttribute(USER_TYPE), oldObject, velocityAndThreshold);
				session.setAttribute(SUCCESS_MESSAGE, TRANSACTION_THRESHOLD_UPDATE_SUC_MSG);
				return UIUtil.redirectPath(TRANSACTION_THRESHOLD_PATH);
			} catch(CloneNotSupportedException e){
				LOGGER.error(e.getMessage() , e);
				model.put(ERROR_MESSAGE, context.getMessage(TRANSACTION_THRESHOLD_FAILS_TO_UPDATE, null, locale));
		 		return VELOCITY_THRESHOLD_EDIT;
			} catch(Exception e){
				LOGGER.error(e.getMessage() , e);
				model.put(ERROR_MESSAGE, context.getMessage(TRANSACTION_THRESHOLD_FAILS_TO_UPDATE, null, locale));
		 		return VELOCITY_THRESHOLD_EDIT;
			}
		}
	}
 		
	@RequestMapping(value="/viewvelocity", method = RequestMethod.GET)
	public String viewVelocityPage(Map model, @Valid VelocityAndThresholdForm velocityAndThresholdForm,Locale locale, 
			HttpServletRequest request){
		
		LOGGER.debug( " viewvelocitypage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TRANSACTION_THRESHOLD_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		Long velocityAndThresholdID = Long.valueOf(request.getParameter("id"));
 		try{
 			VelocityAndThreshold velocityAndThreshold = velocityAndThresholdService.getVelocityAndThreshold(velocityAndThresholdID);
 			velocityAndThresholdForm.setId(velocityAndThreshold.getId());
 			velocityAndThresholdForm.setCountryName(MasterDataUtil.getCountryName(request, 
 					MasterDataUtil.MD_COUNTRIES, velocityAndThreshold.getCountry()));
 			velocityAndThresholdForm.setCountry(velocityAndThreshold.getCountry());
 			velocityAndThresholdForm.setCurrencyName(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
 					velocityAndThreshold.getCurrency()));
 			velocityAndThresholdForm.setCurrency(velocityAndThreshold.getCurrency());
 			velocityAndThresholdForm.setTransactiontype(velocityAndThreshold.getTransactiontype());
 			velocityAndThresholdForm.setTransactiontypeName(getOperationTypesList(request, 
 					velocityAndThreshold.getUserType()).get(velocityAndThreshold.getTransactiontype()));
 			velocityAndThresholdForm.setMinimumamount(UIUtil.getConvertedAmountInString(velocityAndThreshold.getMinimumamount()));
 			velocityAndThresholdForm.setMaximumamount(UIUtil.getConvertedAmountInString(velocityAndThreshold.getMaximumamount()));
 			velocityAndThresholdForm.setUserTypeName(MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
 					(Long) request.getSession().getAttribute(LANGUAGE_ID), 
 					MasterDataConstants.MD_USER_TYPES).get(velocityAndThreshold.getUserType()));
 			return VELOCITY_THRESHOLD_VIEWPAGE;	 		
 		} catch (Exception e) {
 			LOGGER.error(e.getMessage(), e);
 			session.setAttribute(ERROR_MESSAGE, TRANSACTION_THRESHOLD_FAILS_TO_LOAD);
			return UIUtil.redirectPath(TRANSACTION_THRESHOLD_PATH);
 		}
 	}
	
	// -- Wallet Threshold
	@RequestMapping(value="/walletthreshold", method = RequestMethod.GET)
	public String walletThreshold(Map model, WalletThresholdForm walletThresholdForm, Locale locale, HttpServletRequest request){
		
		LOGGER.debug(" walletVelocityAndThreshold ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.WALLET_THRESHOLD, MenuConstants.VIEW_PERMISSION);
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
		LOGGER.debug( " defaultPage ::: " );		
		String url = WALLET_PATH_PREFIX + "admin/walletthresholdrecords";
		request.setAttribute(URL_WALLET_THRESHOLD_LIST, url);	
		return WALLET_THRESHOLD_GRID;
	}
	
	@RequestMapping(value = "/walletthresholdrecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<WalletThresholdDto> walletThresholdRecords(
			@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			Map model, Locale locale, HttpSession session,
			HttpServletRequest request) {

		JqgridResponse<WalletThresholdDto> response = new JqgridResponse<WalletThresholdDto>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.WALLET_THRESHOLD, MenuConstants.VIEW_PERMISSION);
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
		
		List<WalletThresholdDto> walletThresholdList = new ArrayList<WalletThresholdDto>();
		try {
			walletThresholdList = velocityAndThresholdService.getWalletThresholdList();
			for(WalletThresholdDto t:walletThresholdList){
				t.setCountryName(MasterDataUtil.getCountryName(request, MasterDataUtil.MD_COUNTRIES, t.getCountry()));
				t.setCurrencyName(MasterDataUtil.getCurrencyName(request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID), t.getCurrency()));
				t.setStrMaximumAmount(UIUtil.getConvertedAmountInString(t.getMaximumamount()));
			}
		} catch (Exception e) {
			String errorMessage = UserValidator.NO_RECORDS_FOUND;
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			LOGGER.error("walletThresholdRecords", e);
		}
		response.setRows(walletThresholdList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = walletThresholdList.size()/ps;
		if( walletThresholdList.size()/ps*ps != walletThresholdList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
	}
	
	@RequestMapping(value="/addwalletthreshold", method = RequestMethod.GET)
	public String addWalletThresholdPage(Map model, @Valid WalletThresholdForm walletThresholdForm, 
	HttpServletRequest request){
		
		LOGGER.debug( " addWalletThresholdPage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.WALLET_THRESHOLD, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		papulateWalletThresholdMasterData(request, model);
		return WALLET_THRESHOLD_ADD;
	}
	
	@RequestMapping(value="/addwalletthreshold", method = RequestMethod.POST)
	public String addWalletThresholdSave(@Valid WalletThresholdForm walletThresholdForm, BindingResult result,
			Map model, HttpServletRequest request, Locale locale){
		
		LOGGER.debug(" addWalletThresholdSave ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.WALLET_THRESHOLD, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		VelocityAndThresholdValidator velocityValidator = new VelocityAndThresholdValidator();
		velocityValidator.validates(walletThresholdForm, result);
		if(result.hasErrors()){
			papulateWalletThresholdMasterData(request, model);
			return WALLET_THRESHOLD_ADD;
		} else{
			WalletThresholdDto walletThresholdDto = new WalletThresholdDto();
			AdminUtil.convertWalletThresholdFormToDto(walletThresholdForm, walletThresholdDto);
			try {
				velocityAndThresholdService.createWalletThreshold(walletThresholdDto);
				//Audit Trail service
				auditTrailService.createAuditTrail((Long)session.getAttribute(AUTHENTICATION_ID), AuditTrailConstrain.MODULE_WALLET_THRESHOLD_CREATION, 
						AuditTrailConstrain.STATUS_CREATE, (String)session.getAttribute(USER_ID), (String)session.getAttribute(USER_TYPE));
				session.setAttribute(SUCCESS_MESSAGE, WALLET_THRESHOLD_CREATE_SUCCESS_MSG);
				return UIUtil.redirectPath(WALLET_THRESHOLD_PATH);
			} catch (WalletException e) {
				LOGGER.error("Add velocity "+ e.getMessage() , e);
				papulateWalletThresholdMasterData(request, model);
				String errorMessage = null;
				if(e.getMessage().contains(CommonConstrain.DUPLICATE_ENTRY)){
					errorMessage = WALLET_THRESHOLD_ALREADY_EXISTS;
				} else{
					errorMessage = WALLET_THRESHOLD_FAILS_TO_CREATE;
				} 
				model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
				return WALLET_THRESHOLD_ADD;
			}
		}
	}
	
	@RequestMapping(value="/editwalletthreshold", method = RequestMethod.GET)
	public String editWalletThresholdPage(Map model, @Valid WalletThresholdForm walletThresholdForm, Locale locale,
		HttpServletRequest request){
		
		LOGGER.debug( " editWalletThresholdPage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.WALLET_THRESHOLD, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
 		Long walletThresholdID = Long.valueOf(request.getParameter("id"));
 		try{
 			WalletThreshold walletThreshold = velocityAndThresholdService.getWalletThreshold(walletThresholdID);
 			papulateWalletThresholdFrom(request, walletThresholdForm, walletThreshold, true);
 		} catch (Exception e) {
 			LOGGER.error(e.getMessage(), e);
 			session.setAttribute(ERROR_MESSAGE, WALLET_THRESHOLD_FAILS_TO_LOAD);
			return UIUtil.redirectPath(WALLET_THRESHOLD_PATH);
 		}
 		return WALLET_THRESHOLD_EDIT;
 	}
	
	@RequestMapping(value = "/editwalletthreshold", method = RequestMethod.POST)
 	public String editWalletThresholdSave(@Valid WalletThresholdForm walletThresholdForm, BindingResult result, 
 			Map model, HttpServletRequest request, Locale locale) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.WALLET_THRESHOLD, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		VelocityAndThresholdValidator velocityValidator = new VelocityAndThresholdValidator();
		velocityValidator.validates(walletThresholdForm, result);
		if(result.hasErrors()){
			return WALLET_THRESHOLD_EDIT;
		} else{
			try{
				WalletThreshold walletThreshold = velocityAndThresholdService.getWalletThreshold(walletThresholdForm.getId());
				WalletThreshold oldObject = (WalletThreshold)walletThreshold.clone();
				
				WalletThresholdDto walletThresholdDto = new WalletThresholdDto();
				walletThresholdDto.setId(walletThresholdForm.getId());
				walletThresholdDto.setMaximumamount(Double.parseDouble(walletThresholdForm.getMaximumamount()));
				velocityAndThresholdService.updateWalletThreshold(walletThresholdDto);
				
				//Audit Trail service
				walletThreshold.setMaximumamount(Double.valueOf(walletThresholdForm.getMaximumamount()));
				auditTrailService.createAuditTrail((Long)session.getAttribute(AUTHENTICATION_ID), AuditTrailConstrain.MODULE_WALLET_THRESHOLD_EDIT, 
						AuditTrailConstrain.STATUS_CREATE, (String)session.getAttribute(USER_ID), (String)session.getAttribute(USER_TYPE), oldObject, walletThreshold);
				session.setAttribute(SUCCESS_MESSAGE, WALLET_THRESHOLD_UPDATE_SUCCESS_MSG);
				return UIUtil.redirectPath(WALLET_THRESHOLD_PATH);
			} catch(CloneNotSupportedException e){
				LOGGER.error(AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR, e);
				model.put(ERROR_MESSAGE, context.getMessage(WALLET_THRESHOLD_FAILS_TO_UPDATE, null, locale));
		 		return WALLET_THRESHOLD_EDIT;
			} catch(Exception e){
				LOGGER.error(e.getMessage() ,e );
				model.put(ERROR_MESSAGE, context.getMessage(WALLET_THRESHOLD_FAILS_TO_UPDATE, null, locale));
		 		return WALLET_THRESHOLD_EDIT;
			}
		}
	 }
	 
	@RequestMapping(value="/viewwalletthreshold", method = RequestMethod.GET)
	public String viewWalletThresholdPage(Map model, @Valid WalletThresholdForm walletThresholdForm,Locale locale, 
			HttpServletRequest request){
		
		LOGGER.debug( " viewwalletthresholdpage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.WALLET_THRESHOLD, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		Long walletThresholdID = Long.valueOf(request.getParameter("id"));
 		try{
 			WalletThreshold walletThreshold = velocityAndThresholdService.getWalletThreshold(walletThresholdID);
 			walletThresholdForm.setId(walletThreshold.getId());
 			walletThresholdForm.setCountryName(MasterDataUtil.getCountryName(request, 
 					MasterDataUtil.MD_COUNTRIES, walletThreshold.getCountry()));
 			walletThresholdForm.setCountry(walletThreshold.getCountry());
 			walletThresholdForm.setCurrencyName(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
 					walletThreshold.getCurrency()));
 			walletThresholdForm.setCurrency(walletThreshold.getCurrency());
 			walletThresholdForm.setMaximumamount(UIUtil.getConvertedAmountInString(walletThreshold.getMaximumamount()));
 			return WALLET_THRESHOLD_VIEWPAGE;
 	 		
 		} catch (Exception e) {
 			LOGGER.error(e.getMessage() , e);
 			session.setAttribute(ERROR_MESSAGE, WALLET_THRESHOLD_FAILS_TO_LOAD);
			return UIUtil.redirectPath(WALLET_THRESHOLD_PATH);
 		}
 	}

	@RequestMapping(value = "/allwallettablerecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<AllWalletsAmmount> allWalletAccountRecords(Map model, Locale locale, HttpServletRequest request) {
		
		List<Object[]> list1 = null;
		List<Object[]> list2 = null;
		try{
			list1 = commonService.getCustomersTotalAmountsByCountry(CUSTOMER_USER_TYPE);
			list2 = commonService.getCustomersTotalAmountsByCountry(MERCHANT_USER_TYPE);
		} catch(Exception e){
			LOGGER.error(e.getMessage() , e);
		}
		
		Double customerAmount = 0d;
		Double merchantAmount = 0d;
		List<AllWalletsAmmount> allWalletsAmmountsList = new ArrayList<AllWalletsAmmount>();
		Map<Long,String> currencyKeysValues = MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID));
		Set keys = currencyKeysValues.keySet();
		Iterator it = keys.iterator();
		Long c = 0L;
		int i = 0;
		while(it.hasNext()){
			AllWalletsAmmount allWalletsAmmount = new AllWalletsAmmount();
			c = (Long)it.next();
			int check = checkListContainsGivenValue(list1,c);
			if(check == -1){
				customerAmount = 0d;
			} else {
				customerAmount = (Double)(list1.get(check)[0]);
			}
			
			int check1 = checkListContainsGivenValue(list2, c);
			if(check1 == -1){
				merchantAmount = 0d;
			} else {
				merchantAmount = (Double)(list2.get(check1)[0]);
			}
			double totalAmount = customerAmount + merchantAmount;
			allWalletsAmmount.setCurrency(c);
			allWalletsAmmount.setCustomerAmount(customerAmount);
			allWalletsAmmount.setMerchantAmount(merchantAmount);
			allWalletsAmmount.setAmount(totalAmount);
			allWalletsAmmount.setCustomerAmountString(UIUtil.getConvertedAmountInString(customerAmount));
			allWalletsAmmount.setMerchantAmountString(UIUtil.getConvertedAmountInString(merchantAmount));
			allWalletsAmmount.setAmountString(UIUtil.getConvertedAmountInString(totalAmount));
			String currencyName = MasterDataUtil.getCurrencyNames(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(c);
			allWalletsAmmount.setCurrencyName(currencyName);
			allWalletsAmmountsList.add(i,allWalletsAmmount);
			i++;
		}
		
		JqgridResponse<AllWalletsAmmount> response = new JqgridResponse<AllWalletsAmmount>();
		response.setRows(allWalletsAmmountsList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = allWalletsAmmountsList.size()/ps;
		if( allWalletsAmmountsList.size()/ps*ps != allWalletsAmmountsList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;	
	}
	
	@RequestMapping(value = "/adminwallettablerecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<MasterWalletGridDisplayDto> adminWalletaAccountRecords(Map model, Locale locale, HttpServletRequest request) {
		
		Map<Long, Double> mapAmount = masterDataService.getMasterAmountWalletCurrencyWise();
		Map<Long, Double> mapTax = masterDataService.getTaxesCurrencyWise();
		Map<Long, Double> mapFee = masterDataService.getFeesCurrencyWise();
		List<MasterWalletGridDisplayDto> masterWalletGridDisplayDtoList = new ArrayList<MasterWalletGridDisplayDto>();
		Iterator<Long> iter = mapAmount.keySet().iterator();
		MasterWalletGridDisplayDto masterWalletGridDisplayDto = null;
		Long currency = null;
		while(iter.hasNext()){
			masterWalletGridDisplayDto = new MasterWalletGridDisplayDto();
			currency = iter.next(); 
			masterWalletGridDisplayDto.setCurrency(currency);
			masterWalletGridDisplayDto.setAmount(mapAmount.get(currency));
			masterWalletGridDisplayDto.setAmountString(UIUtil.getConvertedAmountInString(mapAmount.get(currency)));
			masterWalletGridDisplayDto.setFeeString(UIUtil.getConvertedAmountInString(mapFee.get(currency)));
			masterWalletGridDisplayDto.setTaxString(UIUtil.getConvertedAmountInString(mapTax.get(currency)));
			Double total = mapAmount.get(currency).doubleValue() + mapFee.get(currency).doubleValue() + mapTax.get(currency).doubleValue();
			masterWalletGridDisplayDto.setTotalString(UIUtil.getConvertedAmountInString(total));
			String currencyName = MasterDataUtil.getCurrencyNames(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(currency);
			masterWalletGridDisplayDto.setCurrencyName(currencyName);
			masterWalletGridDisplayDtoList.add(masterWalletGridDisplayDto);
		}
		JqgridResponse<MasterWalletGridDisplayDto> response = new JqgridResponse<MasterWalletGridDisplayDto>();
		response.setRows(masterWalletGridDisplayDtoList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = masterWalletGridDisplayDtoList.size()/ps;
		if( masterWalletGridDisplayDtoList.size()/ps*ps != masterWalletGridDisplayDtoList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
	}
	
	@RequestMapping(value = "/setup", method = RequestMethod.GET)
	public String setupPage(HttpServletRequest request, Map model, Locale locale){
		
		LOGGER.debug( " setupPage " );
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		return ADMIN_RELOAD_PROPERTIES_VIEWPAGE;
	}
	
	@RequestMapping(value = "/setup", method = RequestMethod.POST)
	public String setuppostPage(HttpServletRequest request, Map model, Locale locale){
		LOGGER.debug( " setuppostPage " );

		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		String groupName = request.getParameter(GROUP_NAME);
		if(groupName != null){
			if(ADMIN_RELOAD_POPERTIES.equals(groupName)){
				try{
					utilService.reloadProperties();
					model.put(SUCCESS_MESSAGE, context.getMessage(ADMIN_RELOAD_POPERTIES_SUCCESS_MSG, null, locale));
				} catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					model.put(ERROR_MESSAGE, context.getMessage(ADMIN_RELOAD_POPERTIES_FAILED, null, locale));
				}
			} else if(ADMIN_SETUP_HTTP_POPERTIES.equals(groupName)){
				try{
					httpService.initializeHttpService();
					model.put(SUCCESS_MESSAGE, context.getMessage(HTTP_POPERTIES_UPDATE_SUCCESS_MSG, null, locale));
				} catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					model.put(ERROR_MESSAGE, context.getMessage(HTTP_POPERTIES_UPDATE_FAILED, null, locale));
				}
			} else if(SchedulerGroupNames.DISPUTE_FORCE_WITHDRAWAL.equals(groupName)){
				try{
					LOGGER.info("Dispute force withdrawal started " + new java.util.Date());
					new DisputeForceWithdrawalJob().executeMethod();
					model.put(SUCCESS_MESSAGE, context.getMessage(DISPUTE_FORCE_WITHDRAWAL_SUCCESS_MSG, null, locale));
					LOGGER.info("Dispute force withdrawal ended " + new java.util.Date());
				} catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					model.put(ERROR_MESSAGE, context.getMessage(DISPUTE_FORCE_WITHDRAWAL_FAILED, null, locale));
				}
			} else if(SchedulerGroupNames.RECONCILATION.equals(groupName)){
				try{
					LOGGER.info("Reconcilation started " + new java.util.Date());
					new ReconcileJob().executeMethod();
					model.put(SUCCESS_MESSAGE, context.getMessage(RECONCILATION_SUCCESS_MSG, null, locale));
					LOGGER.info("Reconcilation ended " + new java.util.Date());
				} catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					model.put(ERROR_MESSAGE, context.getMessage(RECONCILATION_FAILED, null, locale));
				}
			} else if(SchedulerGroupNames.NON_SETTLEMENT.equals(groupName)){
				try{
					LOGGER.info("Non settlement started " + new java.util.Date());
					new PgSettlementJob().executeMethod();
					model.put(SUCCESS_MESSAGE, context.getMessage(NON_SETTLEMENT_SUCCESS_MSG, null, locale));
					LOGGER.info("Non settlement ended " + new java.util.Date());
				} catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					model.put(ERROR_MESSAGE, context.getMessage(NON_SETTLEMENT_FAILED, null, locale));
				}
			} else if(SchedulerGroupNames.CANCEL_NON_REGISTER_WALLET_TXNS.equals(groupName)){
				try{
					LOGGER.info("Cancel_non_register_wallet_txns started " + new java.util.Date());
					new CancelNonRegisterWalletTxnsJob().executeMethod();
					model.put(SUCCESS_MESSAGE, context.getMessage(CANCEL_NON_REGISTER_WALLET_TXNS_SUCCESS_MSG, null, locale));
					LOGGER.info("Cancel_non_register_wallet_txns ended " + new java.util.Date());
				} catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					model.put(ERROR_MESSAGE, context.getMessage(CANCEL_NON_REGISTER_WALLET_TXNS_FAILED, null, locale));
				}
			} else if(SchedulerGroupNames.PENDING_ACCOUNT_CLOSERS.equals(groupName)){
				try{
					LOGGER.info("Pending_account_closers started " + new java.util.Date());
					new AccountCloserJob().executeMethod();
					model.put(SUCCESS_MESSAGE, context.getMessage(PENDING_ACCOUNT_CLOSERS_SUCCESS_MSG, null, locale));
					LOGGER.info("Pending_account_closers ended " + new java.util.Date());
				} catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					model.put(ERROR_MESSAGE, context.getMessage(PENDING_ACCOUNT_CLOSERS_FAILED, null, locale));
				}
			} else if(SchedulerGroupNames.RESENT_FAILED_EMAIL.equals(groupName)){
				try{
					LOGGER.info("Resent failed email started " + new java.util.Date());
					new ResentEmailForFailedEmailRecords().executeMethod();
					model.put(SUCCESS_MESSAGE, context.getMessage(RESENT_FAILED_EMAIL_SUCCESS_MSG, null, locale));
					LOGGER.info("Resent failed email ended " + new java.util.Date());
				} catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					model.put(ERROR_MESSAGE, context.getMessage(RESENT_FAILED_EMAIL_SUCCESS_MSG, null, locale));
				}
			}
			groupName = null;
		}
		return ADMIN_RELOAD_PROPERTIES_VIEWPAGE;
	}
	
	private Map<String, Integer> getDateCountMap(List<Object[]> list){
		Map<String, Integer> data = new HashMap<String, Integer>();
		for(int i = 0; i < list.size(); i++){
			data.put(DateConvertion.dateToString( (Date)list.get(i)[0] ), ((BigInteger)list.get(i)[1]).intValue());
		}
		return data;
	}
	
	private String convertListToString(Map<String, Integer> map, int arraySize){
		StringBuilder sb = new StringBuilder();
		
		for(int j = 0; j < arraySize; j++){
			Integer value = map.get(CommonUtil.getBeforeDates( arraySize - j ));
			if(value == null){
				value = 0;
			}
			sb.append("" + value);
			if(j != arraySize - 1){
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	private void papulateVelocityAndThresholdFrom(HttpServletRequest request, VelocityAndThresholdForm velocityAndThresholdForm, 
			VelocityAndThreshold velocityAndThreshold, Boolean isEditSavePage){
		Map<Long, String> trnsType = getOperationTypesList(request, velocityAndThreshold.getUserType());
		
		velocityAndThresholdForm.setId(velocityAndThreshold.getId());
		velocityAndThresholdForm.setCountryName(MasterDataUtil.getCountryName(request, 
				MasterDataUtil.MD_COUNTRIES, velocityAndThreshold.getCountry()));
		velocityAndThresholdForm.setCountry(velocityAndThreshold.getCountry());
		velocityAndThresholdForm.setCurrencyName(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
				velocityAndThreshold.getCurrency()));
		velocityAndThresholdForm.setCurrency(velocityAndThreshold.getCurrency());
		velocityAndThresholdForm.setTransactiontype(velocityAndThreshold.getTransactiontype());
		velocityAndThresholdForm.setTransactiontypeName(trnsType.get(velocityAndThreshold.getTransactiontype()));
		velocityAndThresholdForm.setUsertype(velocityAndThreshold.getUserType());
		velocityAndThresholdForm.setUserTypeName(MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
					(Long) request.getSession().getAttribute(LANGUAGE_ID), 
					MasterDataConstants.MD_USER_TYPES).get(velocityAndThreshold.getUserType()));
		if(isEditSavePage){
			velocityAndThresholdForm.setMinimumamount(UIUtil.getConvertedAmountInString(velocityAndThreshold.getMinimumamount()));
			velocityAndThresholdForm.setMaximumamount(UIUtil.getConvertedAmountInString(velocityAndThreshold.getMaximumamount()));
		}
	}
		
	private Map<Long, String> getOperationTypesList(HttpServletRequest request, Long userType){
		Map<Long, String> map = new HashMap<Long, String>();
		map = MasterDataUtil.getOperationTypes(request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 1L, userType);
		return map;
	}
	
	private void papulateVelocityMasterData(HttpServletRequest request, Map model, 
			VelocityAndThresholdForm velocityAndThresholdForm, Boolean isOpRequired){
		model.put(USER_TYPE_LIST, MasterDataUtil.getSimpleDataMapForUserTypeCustMer(request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataConstants.MD_USER_TYPES));
		model.put(CURRENCY_LIST, MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		if (isOpRequired && velocityAndThresholdForm != null) {
			model.put(OPERATION_TYPE_LIST, MasterDataUtil.getOperationTypes(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				1L, velocityAndThresholdForm.getUsertype()));
		}
	}
	
	private void papulateWalletThresholdMasterData(HttpServletRequest request, Map model){
		model.put(CURRENCY_LIST, MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
	}
	
	private void papulateWalletThresholdFrom(HttpServletRequest request, WalletThresholdForm walletThresholdForm, 
			WalletThreshold walletThreshold, Boolean isEditSavePage){
		
		walletThresholdForm.setId(walletThreshold.getId());
		walletThresholdForm.setCountryName(MasterDataUtil.getCountryName(request, 
				MasterDataUtil.MD_COUNTRIES, walletThreshold.getCountry()));
		walletThresholdForm.setCountry(walletThreshold.getCountry());
		walletThresholdForm.setCurrencyName(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
				walletThreshold.getCurrency()));
		walletThresholdForm.setCurrency(walletThreshold.getCurrency());
					
		if(isEditSavePage){
			walletThresholdForm.setMaximumamount(UIUtil.getConvertedAmountInString(walletThreshold.getMaximumamount()));
		}
	}
	
	private int checkListContainsGivenValue(List<Object[]> list, Long key){
		for(int i=0;i<list.size();i++){
			long key1 = key.longValue();
			long key2= ((Long)list.get(i)[1]).longValue();
			if(key1 == key2){
				return i;
			}
		}
		return -1;
	}
}
