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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.constants.Preferences;
import com.tarang.ewallet.walletui.form.PereferencesForm;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.PreferencesValidator;



@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("/preferences")
public class PreferencesController implements Preferences, AttributeConstants, AttributeValueConstants{
	
	private static final Logger LOGGER = Logger.getLogger(PreferencesController.class);
	
	@Autowired
	private ApplicationContext context;
	 
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	 
	@RequestMapping(method = RequestMethod.GET)
	public String preferencesload(HttpServletRequest request, Map model, 
			@Valid PereferencesForm pereferencesForm, Locale locale) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String userType = (String)session.getAttribute(USER_TYPE);
		
		try{
			Long authId = Long.parseLong(session.getAttribute(AUTHENTICATION_ID).toString());
			PreferencesDto preferencesDto = commonService.getPreferences(authId);
	        convertDtoToForm(pereferencesForm, preferencesDto);
			return papulateMapForPreferencesSave(userType, request, model);
		} catch(WalletException ae) {
			model.put(ERROR_MESSAGE, context.getMessage(PREFERENCES_RETRIVE_EXCEPTION, null, locale));
			LOGGER.error(ae.getMessage(), ae);
			return papulateMapForPreferencesSave(userType, request, model);
		}
	}
  
	@RequestMapping(method = RequestMethod.POST)
	public String preferencesSend(HttpServletRequest request, Map model, Locale locale,
			@Valid PereferencesForm pereferencesForm, BindingResult result) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		PreferencesValidator pf=new PreferencesValidator();
		pf.validate(pereferencesForm, result);		
		if(result.hasErrors()){
			return papulateMapForPreferencesSave(pereferencesForm.getUserType(), request, model);
		}	
		try {
			Long authId = Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString());
			PreferencesDto preferencesDto = commonService.getPreferences(authId);
			PreferencesDto oldObject = (PreferencesDto) preferencesDto.clone();
			preferencesDto = pereferencesForm.getPreferencesDto();
			preferencesDto.setAuthentication(authId);
			commonService.updatePreferences(preferencesDto);
			//Audit Trail service
			auditTrailService.createAuditTrail(authId, AuditTrailConstrain.MODULE_PREFEREANCES_UPDATE, 
					AuditTrailConstrain.STATUS_UPDATE, (String)session.getAttribute(USER_ID), (String)session.getAttribute(USER_TYPE), oldObject, preferencesDto);			
			model.put(SUCCESS_MESSAGE, context.getMessage(PREFERENCES_UPDATE_SUCCESS, null, locale));
			return papulateMapForPreferencesSave(pereferencesForm.getUserType(), request, model);
		} catch (CloneNotSupportedException e) {
			LOGGER.error(AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR, e);
			model.put(ERROR_MESSAGE, context.getMessage(PREFERENCES_UPDATE_FAILS, null, locale));
			return papulateMapForPreferencesSave(pereferencesForm.getUserType(), request, model);
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(PREFERENCES_UPDATE_FAILS, null, locale));
			return papulateMapForPreferencesSave(pereferencesForm.getUserType(), request, model);
		}
		
	}
	
	private String papulateMapForPreferencesSave(String userType, HttpServletRequest request, Map model){
		String viewPage = null;
		if(userType.equals(GlobalLitterals.CUSTOMER_USER_TYPE)){
			viewPage = PREFERENCES_CUSTOMER_PAGE_VIEW;
		} else{
			viewPage = PREFERENCES_MERCHANT_PAGE_VIEW;
		}
		model.put(LANGUAGE_ID, MasterDataUtil.getLanguageNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		/**
		 * Commented Currency In Preferences
		 */
		/*
		model.put(CURRENCY_LIST, MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		*/		
		return viewPage;
	}
	
	private void convertDtoToForm(PereferencesForm pereferencesForm, PreferencesDto preferencesDto){
		pereferencesForm.setId(preferencesDto.getId());
		pereferencesForm.setCurrency(preferencesDto.getCurrency());
		pereferencesForm.setLanguage(preferencesDto.getLanguage());
	}
	
}
