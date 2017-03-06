/**
 * 
 */
package com.tarang.ewallet.walletui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.document.AbstractPdfView;


import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.reports.business.ReportsService;
import com.tarang.ewallet.reports.util.ReportConstants;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Reports;
import com.tarang.ewallet.walletui.form.ReportsForm;
import com.tarang.ewallet.walletui.reports.ExcelReport;
import com.tarang.ewallet.walletui.reports.PDFReports;
import com.tarang.ewallet.walletui.reports.ReportData;
import com.tarang.ewallet.walletui.reports.ReportUtil;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.common.Common;

/**
 * @author  : prasadj
 * @date    : Feb 6, 2013
 * @time    : 10:43:57 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"}) 
@Controller
@RequestMapping("/reports")
public class ReportsController extends AbstractPdfView implements Reports, ReportConstants, 
				AttributeConstants, AttributeValueConstants, AccountsConstants, GlobalLitterals {

	private static final Logger LOGGER = Logger.getLogger(ReportsController.class);
	
	@Autowired
	private ReportsService reportsService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UtilService utilService;

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReportData reportData = (ReportData)model.get(REPORTS_DATA);
		LOGGER.info("constructing pdf report: " + reportData.getTitleName());
		response.setContentType(CONTENT_TYPE_PDF);
		PdfWriter.getInstance(document, response.getOutputStream());
		new PDFReports(document, reportData).buildReport();
	}
	
	@RequestMapping(value = "/cm",method = RequestMethod.GET)
	public String customerOrMerchantReports(Map model, HttpServletRequest request, ReportsForm reportsForm, Locale locale) {
		LOGGER.debug(" customerOrMerchantReports ");
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		return cmOrAdminReports(model, request, reportsForm, locale);
	}
	
	@RequestMapping(value = "/admin",method = RequestMethod.GET)
	public String adminReports(Map model, HttpServletRequest request, ReportsForm reportsForm, Locale locale) {
		
		LOGGER.debug(" adminReports ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
                MenuConstants.USER_REPORTS, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }

		return cmOrAdminReports(model, request, reportsForm, locale);
	}
	
	@RequestMapping(value = "/cmsearch",method = RequestMethod.GET)
	public String customerOrMerchantReportsSearch(Map model, HttpServletRequest request, ReportsForm reportsForm, Locale locale) {
		LOGGER.debug(" customerOrMerchantReportsSearch ");
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		return cmOrAdminReportsSearch(model, request, reportsForm, locale);
	}
	
	@RequestMapping(value = "/adminsearch",method = RequestMethod.GET)
	public String adminReportsSearch(Map model, HttpServletRequest request, ReportsForm reportsForm, Locale locale) {
		
		LOGGER.debug(" adminReportsSearch ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
                MenuConstants.USER_REPORTS, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		return cmOrAdminReportsSearch(model, request, reportsForm, locale);
	}
	
	private String cmOrAdminReports(Map model, HttpServletRequest request, ReportsForm reportsForm, Locale locale) {
		LOGGER.debug(" customerOrAdminReports ");
		HttpSession session = request.getSession();

		String ut = (String)session.getAttribute(USER_TYPE);
		String viewPage = getViewName(ut);
		Long userType = getUserTypeId(ut);
		model.put(REPORT_PAGE_HEADER, context.getMessage(getHeaderName(ut), null, locale));
		String type = (String)request.getParameter(REPORTTYPE);
		Long typeValue = 0L;
		if(type != null ){
			try{
				typeValue = Long.valueOf(type);
				List<CustomerReportModel> custReps = new ArrayList<CustomerReportModel>();
				String title = MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
					      (Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID),
					      userType, MasterDataConstants.REPORT_TYPES).get(typeValue);

				request.setAttribute(REPORTS_DATA, ReportUtil.populateReportData(typeValue, title, custReps, context, locale));
				request.setAttribute(TYPE_VALUE , typeValue);
			} catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
			}
			reportsForm.setReportType(typeValue);
		}	
		
		model.put("reportTypesList", MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
			      (Long) request.getSession().getAttribute(LANGUAGE_ID),
			      userType, MasterDataConstants.REPORT_TYPES));
		
		if(typeValue.equals(MERCHANT_DISPUTED_TRANSACTION) || typeValue.equals(USER_DISPUTED_TRANSACTION)){
			model.put(DISPUTETYPE_LIST , MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.DISPUTE_TYPES));
		}	
		
		String url = WALLET_PATH_PREFIX + "reports/txnrecords?reportid=" + type;
		request.setAttribute(URL_REPORTS_LIST_ATTR, url);
		reportsForm.setUserType(ut);
		return viewPage;
	}
	
	private String cmOrAdminReportsSearch(Map model, HttpServletRequest request, ReportsForm reportsForm, Locale locale) {
		LOGGER.debug(" customerOrAdminReportsSearch ");
		HttpSession session = request.getSession();

		String ut = (String)session.getAttribute(USER_TYPE);
		String viewPage = getViewName(ut);
		Long userType = getUserTypeId(ut);
		model.put(REPORT_PAGE_HEADER, context.getMessage(getHeaderName(ut), null, locale));
		String fromDate = (String)request.getParameter(FDATE);
		String toDate = (String)request.getParameter(TDATE);
		String emailId = (String)request.getParameter(EMAIL_ID);
		String type = (String)request.getParameter(REPORTID);
		String disputeType = request.getParameter(DISPUTETYPE);
		String cEmailId = request.getParameter(CEMAIL);
		String mEmailId = request.getParameter(MEMAIL);
		Long typeValue = 0L;
		
		model.put(REPORTS_FORM, reportsForm );
		reportsForm.setFromDate(fromDate);
		reportsForm.setToDate(toDate);
		reportsForm.setEmailId(emailId);
		reportsForm.setDisputeType(disputeType);
		reportsForm.setcEmailId(cEmailId);
		reportsForm.setmEmailId(mEmailId);
		
		if(type != null ){
			typeValue = Long.parseLong(type);
			try{
				List<CustomerReportModel> list = null;
				if(list == null){
					list = new ArrayList<CustomerReportModel>();
				}
				String title = MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
					      (Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID),
					      userType, MasterDataConstants.REPORT_TYPES).get(typeValue);

				request.setAttribute(REPORTS_DATA, ReportUtil.populateReportData(typeValue, title, list, context, locale));
			} catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
			}
			LOGGER.info("Type is :" + type);
			reportsForm.setReportType(typeValue);
		}
		model.put(REPORTS_TYPES_LIST, MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
			      (Long) request.getSession().getAttribute(LANGUAGE_ID),
			      userType, MasterDataConstants.REPORT_TYPES));
		if(typeValue.equals(MERCHANT_DISPUTED_TRANSACTION) || typeValue.equals(USER_DISPUTED_TRANSACTION)){
			model.put(DISPUTETYPE_LIST , MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.DISPUTE_TYPES));
		}
		String url = WALLET_PATH_PREFIX + "reports/txnrecords?reportid=" + type +"&search=true";
		url = appendUrl(reportsForm, url);
		request.setAttribute(URL_REPORTS_LIST_ATTR, url);
		request.setAttribute(TYPE_VALUE , typeValue);
		return viewPage;
	}
	
	private String appendUrl(ReportsForm reportsForm, String url){
		String u = url;
		if(reportsForm.getFromDate() != null && !reportsForm.getFromDate().equals(UNDEFINED) ){
			u = u + "&fdate=" + reportsForm.getFromDate();
		}
		if(reportsForm.getToDate() != null && !reportsForm.getToDate().equals(UNDEFINED)){
			u = u + "&tdate=" + reportsForm.getToDate(); 
		}
		if(reportsForm.getEmailId() != null && !reportsForm.getEmailId().equals(UNDEFINED)){
			u = u + "&emailId=" + reportsForm.getEmailId();
		}
		if(reportsForm.getDisputeType() != null && !reportsForm.getDisputeType().equals(UNDEFINED) ){
			u = u + "&disputetype=" + reportsForm.getDisputeType();
		}
		if(reportsForm.getcEmailId() != null && !reportsForm.getcEmailId().equals(UNDEFINED)){
			u = u + "&cemail=" + reportsForm.getcEmailId();
		}
		if(reportsForm.getmEmailId() != null && !reportsForm.getmEmailId().equals(UNDEFINED)){
			u = u + "&memail=" + reportsForm.getmEmailId();
		}
		return u;
	}
	
	@RequestMapping(value = "/adminexportreports", method = RequestMethod.GET)
	public String adminExport(Map model, Document document, PdfWriter writer, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value=REPORTTYPE, required = false) Long reporttype, 
			@RequestParam(value=REPORTID, required = false) Long reportId, Locale locale) {
		
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE, ADMIN_USER_TYPE)){
			String url = null;
			if(MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
				      (Long) request.getSession().getAttribute(LANGUAGE_ID),
				      ADMIN_USER_TYPE_ID, MasterDataConstants.REPORT_TYPES).containsKey(reportId)){
				url = ADMIN_LOGIN_PATH;
			} else {
				url = HOME_LOGIN_PATH;
			}
			request.setAttribute(REP_SESSION_URL, url);
			return ADMIN_CUST_MER_SESSION_EXPIRED;
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
                MenuConstants.USER_REPORTS, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		String ut = (String)session.getAttribute(USER_TYPE);
		model.put(REPORT_PAGE_HEADER, context.getMessage(getHeaderName(ut), null, locale));
		try{
			exportReport(model, document, writer, request, response, session, locale, reportId, reporttype);
			return null;
		} catch(Exception e){
			LOGGER.error(e.getMessage() , e);
			ReportsForm reportsForm = new ReportsForm();
			if(REPORT_EXCEPTION_EMPTY.equals(e.getMessage())){
				model.put(ERROR_MESSAGE, context.getMessage(Common.NO_RECORD_IN_PDF_ERRMSG, null, locale));
				return cmOrAdminReportsSearch(model, request, reportsForm, locale);
			} else{
				model.put(ERROR_MESSAGE, context.getMessage(Common.UNABLE_TO_DOWNLOAD_PDF_UNKNOWN_ERRMSG, null, locale));
				return cmOrAdminReportsSearch(model, request, reportsForm, locale);
			}
		}
	}
	
	@RequestMapping(value = "/cmexportreports", method = RequestMethod.GET)
	public String customerOrMerchantExport(Map model, Document document, PdfWriter writer, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value=REPORTTYPE, required = false) Long reporttype, 
			@RequestParam(value=REPORTID, required = false) Long reportId, Locale locale) {
		
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE, ADMIN_USER_TYPE)){
			String url = null;
			if(MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
				      (Long) request.getSession().getAttribute(LANGUAGE_ID),
				      ADMIN_USER_TYPE_ID, MasterDataConstants.REPORT_TYPES).containsKey(reportId)){
				url = ADMIN_LOGIN_PATH;
			} else {
				url = HOME_LOGIN_PATH;
			}
			request.setAttribute(REP_SESSION_URL, url);
			return ADMIN_CUST_MER_SESSION_EXPIRED;
		}
		String ut = (String)session.getAttribute(USER_TYPE);
		model.put(REPORT_PAGE_HEADER, context.getMessage(getHeaderName(ut), null, locale));
		try {
			exportReport(model, document, writer, request, response, session, locale, reportId, reporttype);
			return null;
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);
			ReportsForm reportsForm = new ReportsForm();
			if(REPORT_EXCEPTION_EMPTY.equals(e.getMessage())){
				model.put(ERROR_MESSAGE, context.getMessage(Common.NO_RECORD_IN_PDF_ERRMSG, null, locale));
				return cmOrAdminReportsSearch(model, request, reportsForm, locale);
			} else{
				model.put(ERROR_MESSAGE, context.getMessage(Common.UNABLE_TO_DOWNLOAD_PDF_UNKNOWN_ERRMSG, null, locale));
				return cmOrAdminReportsSearch(model, request, reportsForm, locale);
			}
		}
	}
	
	private void exportReport(Map model, Document document, PdfWriter writer, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session, Locale locale, Long reportId, Long reporttype) throws  Exception{
		
		if(reportId != null ){
				if(reporttype != null){
					List<CustomerReportModel> list = null;
					String errorMsg = null;
					try{
						list = getData(request);
					} catch(Exception ex){
						LOGGER.error(ex.getMessage(), ex);
						//look up properties file else add directly
						errorMsg = ex.getMessage();
					}
					if(list == null ){
						throw new WalletException(REPORT_EXCEPTION_EMPTY);
					} else if(list.isEmpty()){
						throw new WalletException(REPORT_EXCEPTION_EMPTY);
					}
					String title = MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
						      (Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID),
						      getUserTypeId((String)session.getAttribute(USER_TYPE)), MasterDataConstants.REPORT_TYPES).get(reportId);
					
					ReportData reportData = ReportUtil.populateReportData(reportId, title, list, context, locale);
					String filename = title.replace(EMPTY_VALUE,EMPTY_SPACE);
					if(errorMsg != null){
						reportData.setTitleName(reportData.getTitleName() + " - " + errorMsg);
					}
					
					if(reporttype.longValue() == 1L){ 
						// excel report
						response.setHeader(RESPONSE_HEADER_NAME_XL, RESPONSE_HEADER_VALUE_XL + filename + XSL_FILE_EXT);
						response.setContentType(RESPONSE_CONTENTY_TYPE_XL);
						new ExcelReport(reportData).buildReport(response.getOutputStream());
					} else if(reporttype.equals(2L)){ 
						//PDF Report
						model.put(REPORTS_DATA, reportData);
						response.setHeader(RESPONSE_HEADER_NAME_XL, RESPONSE_HEADER_VALUE_XL + filename + PDF_FILE_EXT);
						buildPdfDocument(model, document, writer, request, response);
					}
				}
		}
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
    		HttpServletRequest request,ReportsForm reportsForm, Map model, Locale locale) throws WalletException {
		LOGGER.debug(" transactionRecords ");
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(filters);
		qf.setPage(page);
		qf.setRows(rows);
		qf.setSidx(sidx);
		qf.setSord(sord); 	

		JqgridResponse<CustomerReportModel> response = new JqgridResponse<CustomerReportModel>();		
		Long type = Long.parseLong(request.getParameter(REPORTID));
		String search1 = request.getParameter("search");
		
		List<CustomerReportModel> modelList = new ArrayList<CustomerReportModel>();
		Boolean performSearch = false;
		if(search1 != null){
			performSearch = true;
		} else if( !type.equals(CUSTOMER_MERCHANT_WISE_TRANSACTION) 
				&& !type.equals(MERCHANT_CUSTOMER_WISE_TRANSACTION) 
				&& !type.equals(USER_CUSTOMER_WISE_TRANSACTION) 
				&& !type.equals(USER_MERCHANT_WISE_TRANSACTION)){
			performSearch = true;
		}

		try{
			if(performSearch){
				modelList = getData(request);
			}
		} catch(Exception ex){
			LOGGER.error(ex.getMessage() , ex);
		}

		if(modelList == null) {
			modelList = new ArrayList<CustomerReportModel>();
		}
		response.setRows(modelList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = modelList.size() / ps;
		if( modelList.size() / ps * ps != modelList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		
		return response;
	}
	
	private String getViewName(String ut){
		String viewPage = EMPTY_STRING;
		if(ut != null){
			if(ut.equals(CUSTOMER_USER_TYPE)){
				viewPage = CUSTOMER_REPORTS;
			} else if(ut.equals(MERCHANT_USER_TYPE)){
				viewPage = MERCHANT_REPORTS;
			} else if(ut.equals(ADMIN_USER_TYPE)){
				viewPage = ADMIN_REPORTS;
			}
		}
		return viewPage;
	}
	
	private Long getUserTypeId(String ut){
		Long userType = 0L;
		if(ut != null){
			if(ut.equals(CUSTOMER_USER_TYPE)){
				userType = CUSTOMER_USER_TYPE_ID;
			} else if(ut.equals(MERCHANT_USER_TYPE)){
				userType = MERCHANT_USER_TYPE_ID;
			} else if(ut.equals(ADMIN_USER_TYPE)){
				userType = ADMIN_USER_TYPE_ID;
			}
		}
		return userType;
	}

	private String getHeaderName(String ut){
		String headerName = EMPTY_STRING;
		if(ut != null){
			if(ut.equals(CUSTOMER_USER_TYPE)){
				headerName = "customer.reports.lbl";
			} else if(ut.equals(MERCHANT_USER_TYPE)){
				headerName = "merchant.reports.lbl";
			} else if(ut.equals(ADMIN_USER_TYPE)){
				headerName = "admin.reports.lbl";
			}
		}
		return headerName;
	}

	private Long getAuthId(String emailId, String userType) throws WalletException {
		Authentication auth = commonService.getAuthentication(emailId, userType);
		if(null == auth){
			throw new WalletException(INVALID_EMAIL_ERR_MSG);
		}
		Long authId = auth.getId();
		if(authId == null){
			throw new WalletException(INVALID_EMAIL_ERR_MSG);
		}
		return authId;
	}
	
	private List<CustomerReportModel> getData(HttpServletRequest request) throws Exception {
		
		Locale locale = request.getLocale();
		String fDate = (String)request.getParameter(FDATE);
		String tDate = (String)request.getParameter(TDATE);
		Long languageId = (Long) request.getSession().getAttribute(LANGUAGE_ID);
		Long authenticationId = (Long)request.getSession().getAttribute(AUTHENTICATION_ID);
		String emailId = (String)request.getParameter(EMAIL_ID);
		Long reportId = Long.parseLong(request.getParameter(REPORTID));
		Long disputeType = null;
		if(request.getParameter(DISPUTETYPE) != null){
			if(request.getParameter(DISPUTETYPE).trim().equals(UNDEFINED)){
				disputeType = null;
			} else {
				disputeType = Long.parseLong(request.getParameter(DISPUTETYPE));
			}
		}
		
		String customerMailId = (String)request.getParameter(CEMAIL);
		if((customerMailId != null && customerMailId.trim().equals(EMPTY_STRING)) 
				|| (customerMailId != null && customerMailId.trim().equals(UNDEFINED))) {
			customerMailId = null;
		}
		
		String merchantMailId = (String)request.getParameter(MEMAIL);
		if((merchantMailId != null && merchantMailId.trim().equals(EMPTY_STRING)) 
				|| (merchantMailId != null && merchantMailId.trim().equals(UNDEFINED))) {
			merchantMailId = null;
		}

		if(languageId == null){
			languageId = 1L;
		}
		int limit = utilService.getReportLimit();
		
		Date fromDate = null;
		Date toDate = null;
		int reportDays = utilService.getReportDays();
		try{
			if(fDate != null){
				fromDate = DateConvertion.stirngToDate(fDate);
			} else {
				String frDate = CommonUtil.getBeforeDates(reportDays);
				fromDate = DateConvertion.getFromDate( DateConvertion.stirngToDate(frDate));
			}
		} catch(Exception ex){
			LOGGER.error(ex.getMessage() , ex );
		}
		
		try{
			if(tDate != null){
				toDate = DateConvertion.getToDate(DateConvertion.stirngToDate(tDate), 0);
			} else{
				String tod = CommonUtil.getBeforeDates(0);
				toDate = DateConvertion.getToDate(DateConvertion.stirngToDate(tod));
			}
		} catch(Exception ex){
			LOGGER.error(ex.getMessage() , ex);
		}
		List<CustomerReportModel> list = null;
		
		if(CUSTOMER_LAST_N_TRANSACTIONS.equals(reportId)){
			list = reportsService.customerLastNTransactions(limit, languageId, authenticationId, 
					context.getMessage(BALANCE_STATUS_CR, null, locale),
					context.getMessage(BALANCE_STATUS_DR, null, locale));
		} else if(CUSTOMER_MERCHANT_WISE_TRANSACTION.equals(reportId)){
			list = reportsService.customerMerchantWiseTransactions(limit, languageId, fromDate, toDate, 
					authenticationId, getAuthId(emailId, GlobalLitterals.MERCHANT_USER_TYPE));
		} else if(CUSTOMER_RECEIVE_MONEY_TRANSACTION.equals(reportId)){
			list = reportsService.customerReceiveMoneyTransactions(limit, languageId, fromDate, toDate, authenticationId);
		} else if(CUSTOMER_SEND_MONEY_TRANSACTION.equals(reportId)){
			list = reportsService.customerSendMoneyTransactions(limit, languageId, fromDate, toDate, authenticationId);
		} else if(CUSTOMER_REQUEST_FAILS.equals(reportId)){
			list = reportsService.customerRequestFails(limit, languageId, fromDate, toDate, authenticationId);
		} else if(CUSTOMER_FAILED_TRANSACTION.equals(reportId)){
			list = reportsService.customerFailedTransactions(limit, fromDate, toDate, authenticationId);
		} else if(MERCHANT_LAST_N_TRANSACTIONS.equals(reportId)){
			list = reportsService.merchantLastNTransactions(limit, languageId, authenticationId, 
					context.getMessage(BALANCE_STATUS_CR, null, locale),
					context.getMessage(BALANCE_STATUS_DR, null, locale));
		} else if(MERCHANT_RECEIVE_MONEY_TRANSACTION.equals(reportId)){
			list = reportsService.merchantReceiveMoneyTransactions(limit, languageId, fromDate, toDate, authenticationId);
		} else if(MERCHANT_SEND_MONEY_TRANSACTION.equals(reportId)){
			list = reportsService.merchantSendMoneyTransactions(limit, languageId, fromDate, toDate, authenticationId);
		} else if(MERCHANT_CUSTOMER_WISE_TRANSACTION.equals(reportId)){
			list = reportsService.merchantPersonWiseTransactions(limit, languageId, fromDate, toDate, authenticationId, 
					getAuthId(emailId, GlobalLitterals.CUSTOMER_USER_TYPE));
		} else if(MERCHANT_FAILED_TRANSACTION.equals(reportId)){
			list = reportsService.merchantCustomerFailedTransactions(limit, fromDate, toDate, authenticationId);
		} else if(USER_LAST_N_TRANSACTIONS.equals(reportId)){
			list = reportsService.userLastNTransactions(limit, languageId);
		} else if(USER_CUSTOMER_WISE_TRANSACTION.equals(reportId)){
			list = reportsService.userCustomerWiseTransactions(limit, languageId, fromDate, toDate, 
					getAuthId(emailId, GlobalLitterals.CUSTOMER_USER_TYPE));
		} else if(USER_MERCHANT_WISE_TRANSACTION.equals(reportId)){
			list = reportsService.userMerchantWiseTransactions(limit, languageId, fromDate, toDate, 
					getAuthId(emailId, GlobalLitterals.MERCHANT_USER_TYPE));
		} else if(COMMISSION_SUMMARY.equals(reportId)){
			list = reportsService.userCommissions(limit, languageId, fromDate, toDate );
		} else if(USER_CUSTOMER_BALANCES.equals(reportId)){
			list = reportsService.userCustomerBalances(languageId);
		} else if(USER_MERCHANT_BALANCES.equals(reportId)){
			list = reportsService.userMerchantBalances(languageId);
		} else if(USER_CUSTOMER_HAVING_OVER_LIMIT.equals(reportId)){
			list = reportsService.userCustomerHavingMoneyExceedsThresholdLimit(languageId, languageId);// second parameter should be country id
		} else if(USER_CUSTOMER_REQUEST_FAILS.equals(reportId)){
			list = reportsService.userCustomerRequestFails(limit, languageId, fromDate, toDate);
		} else if(USER_CUSTOMER_FAILED_TRANSACTION.equals(reportId)){
			list = reportsService.userCustomerFailedTransactions(limit, fromDate, toDate);
		} else if(USER_DORMANT_ACCOUNTS.equals(reportId)){
			list = reportsService.dormantAccounts(limit, fromDate, toDate, utilService.getDormantAccountInterval() );
		} else if(MERCHANT_COMMISSION_SUMMARY.equals(reportId)){
			list = reportsService.merchantCommissions(limit, languageId, authenticationId, fromDate, toDate);
		} else if(MERCHANT_DISPUTED_TRANSACTION.equals(reportId)){
			list = reportsService.userMerchantDisputes(limit, languageId, fromDate, toDate, authenticationId, 
					customerMailId != null ? getAuthId(customerMailId, GlobalLitterals.CUSTOMER_USER_TYPE) : 0L, disputeType != null ? disputeType : 0);
		} else if(USER_DISPUTED_TRANSACTION.equals(reportId)){
			list = reportsService.userAdminDisputes(limit, languageId, fromDate, toDate, 
					merchantMailId != null ? getAuthId(merchantMailId, GlobalLitterals.MERCHANT_USER_TYPE) : 0L, 
							customerMailId != null ? getAuthId(customerMailId, GlobalLitterals.CUSTOMER_USER_TYPE) : 0L, 
									disputeType != null ? disputeType : 0);
		} else if(LIST_OF_ACCOUNT_NOT_USED_IN_X_TIME.equals(reportId)){
			list = reportsService.listOfUnusedAccounts(limit, fromDate);
		} else if(FRAUDULENT_TRANSACTION.equals(reportId)){
			list = reportsService.listOfMerchantsThresholds(limit, languageId);
		}
		return list;
	}
}
