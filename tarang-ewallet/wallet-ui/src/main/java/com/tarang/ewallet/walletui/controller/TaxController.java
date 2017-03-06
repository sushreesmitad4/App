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
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.model.Tax;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.walletui.controller.constants.Admin;
import com.tarang.ewallet.walletui.form.TaxForm;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.TaxValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;

/**
 * @author vasanthar
 * @comment Tax controller for admin management module 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/tax")
public class TaxController implements AttributeConstants, AttributeValueConstants, Admin{
	
	private static final Logger LOGGER = Logger.getLogger(TaxController.class);
	
	@Autowired
	private FeeMgmtService feeService;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String taxListPage(Map model, Locale locale, HttpServletRequest request){
		
		LOGGER.debug(" taxListPage GET ");
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TAX_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
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
		LOGGER.debug( " taxListPage " );		
		String url = WALLET_PATH_PREFIX + "tax/taxrecords";
		request.setAttribute("urlTaxList", url);		
		return TAXS_DEFAULT_VIEW;
	}
	
	@RequestMapping(value = "/taxrecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<Tax> taxRecords(
			@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			Map model, Locale locale, HttpSession session,
			HttpServletRequest request) {

		JqgridResponse<Tax> response = new JqgridResponse<Tax>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TAX_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
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
    
		List<Tax> taxList = new ArrayList<Tax>();
		try {
			taxList = feeService.getTaxs();
			for(Tax t:taxList){
				Long countryId = t.getCountry();
				String countryName = MasterDataUtil.getCountryName(request, MasterDataUtil.MD_COUNTRIES,countryId);
				t.setCountryName(countryName);
				t.setPercentageAmount(UIUtil.getConvertedAmountInString(t.getPercentage()));
			}
		} catch (Exception e) {
			String errorMessage = UserValidator.NO_RECORDS_FOUND;
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			LOGGER.error("customerRecords", e);
		}
		
		response.setRows(taxList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = taxList.size()/ps;
		if( taxList.size()/ps*ps != taxList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
	}

	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String taxAdd(Map model, @Valid TaxForm taxForm, Locale locale, HttpServletRequest request) {

		LOGGER.debug(" taxSave GET ");
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
				MenuConstants.TAX_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
		if(!adminAccessCheck){
			session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
		}
		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		return TAX_ADD_VIEW;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String taxSave(Map model, HttpServletRequest request, Locale locale,  @Valid TaxForm taxForm, BindingResult result) {

		LOGGER.debug(" taxSave POST ");
		String viewPage = TAX_ADD_VIEW;
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TAX_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(
				session.getServletContext(), 
				(Long) session.getAttribute(LANGUAGE_ID)));
		TaxValidator taxValidator = new TaxValidator();
		taxValidator.validate(taxForm, result);
		if (result.hasErrors()) {
			return viewPage;
		} else {
			try{
				Tax tax = new Tax();
				tax.setCountry(taxForm.getCountry());
				tax.setPercentage(Double.valueOf(taxForm.getPercentage()));
				feeService.createTax(tax);
				//Audit Trail service
				auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), 
						AuditTrailConstrain.MODULE_TAX_CREATE, AuditTrailConstrain.STATUS_CREATE, 
						session.getAttribute(USER_ID).toString(),GlobalLitterals.ADMIN_USER_TYPE);
				session.setAttribute(SUCCESS_MESSAGE, TAX_ADD_SUCCESS_MSG);
				return UIUtil.redirectPath(AttributeValueConstants.TAXS_MGMT_PATH);
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
				if(e.getMessage().equals(GlobalLitterals.DB_COUNTRY_DUPLICATE_ENTRY)){
					model.put(ERROR_MESSAGE, context.getMessage(TAX_COU_EXIST, null, locale));
					return viewPage;
				} else{
					model.put(ERROR_MESSAGE, context.getMessage(TAX_CREATION_FAILED_ERRMSG, null, locale));
					return viewPage;
				}
			}
		}
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String taxEdit(Map model, @Valid TaxForm taxForm, Locale locale, HttpServletRequest request) {

		LOGGER.debug(" taxEdit GET ");
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TAX_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String taxId = (String)request.getParameter("taxId");
		try{
			Tax tax = feeService.getTax(Long.valueOf(taxId != null ? taxId : "0"));
			taxForm.setId(tax.getId());
			taxForm.setCountry(tax.getCountry());
			taxForm.setPercentage(UIUtil.getConvertedAmountInString(tax.getPercentage()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			session.setAttribute(ERROR_MESSAGE, TAX_FAILED_TO_LOAD);
			return UIUtil.redirectPath(AttributeValueConstants.TAXS_MGMT_PATH);
		}
		model.put(COUNTRY_NAME, MasterDataUtil.getCountryName(request,
				MasterDataUtil.MD_COUNTRIES, taxForm.getCountry()));
		
		return TAX_EDIT_VIEW;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String taxEditSave(Map model, HttpServletRequest request, Locale locale,  @Valid TaxForm taxForm, BindingResult result) {

		LOGGER.debug(" taxEditSave POST ");
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.TAX_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		TaxValidator taxValidator = new TaxValidator();
		taxValidator.validate(taxForm, result);
		if (result.hasErrors()) {
			model.put(COUNTRY_NAME, MasterDataUtil.getCountryName(request,
					MasterDataUtil.MD_COUNTRIES, taxForm.getCountry()));
			return TAX_EDIT_VIEW;			
		} else{
			try{
				Tax tax = null;
				tax = feeService.getTax(taxForm.getId());
				Tax oldObject = (Tax)tax.clone();
				tax.setId(taxForm.getId());
				tax.setCountry(taxForm.getCountry());
				tax.setPercentage(Double.valueOf(taxForm.getPercentage()));
				feeService.updateTax(tax);
				//Audit Trail service
				auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), 
						AuditTrailConstrain.MODULE_TAX_EDIT, AuditTrailConstrain.STATUS_EDIT, 
						session.getAttribute(USER_ID).toString(),GlobalLitterals.ADMIN_USER_TYPE, oldObject, tax);
				session.setAttribute(SUCCESS_MESSAGE, TAX_EDIT_SUCCESS_MSG);
				return UIUtil.redirectPath(AttributeValueConstants.TAXS_MGMT_PATH);
			} catch(CloneNotSupportedException e){
				LOGGER.error(AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR, e);
				model.put(ERROR_MESSAGE, context.getMessage(TAX_EDIT_ERROR_MSG, null, locale));						
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
				model.put(ERROR_MESSAGE, context.getMessage(TAX_EDIT_ERROR_MSG, null, locale));						
			}
			return TAX_EDIT_VIEW;
		}
	}
}