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
import com.tarang.ewallet.walletui.controller.constants.Preferences;
import com.tarang.ewallet.walletui.form.PereferencesForm;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.PreferencesValidator;



@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("/adminpreferences")
public class AdminPreferencesController implements Preferences, AttributeConstants, AttributeValueConstants{
	
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
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		/*Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.PREFERENCES_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }*/
		try{
			Long authId = Long.parseLong(session.getAttribute(AUTHENTICATION_ID).toString());
			PreferencesDto preferencesDto = commonService.getPreferences(authId);
	        convertDtoToForm(pereferencesForm, preferencesDto);
			return papulateMapForPreferencesSave(request, model);
		} catch(WalletException ae) {
			model.put(ERROR_MESSAGE, context.getMessage(PREFERENCES_RETRIVE_EXCEPTION, null, locale));
			LOGGER.error(ae.getMessage(), ae);
			return papulateMapForPreferencesSave(request, model);
		}
	}
  
	@RequestMapping(method = RequestMethod.POST)
	public String preferencesSend(HttpServletRequest request, Map model, Locale locale,
			@Valid PereferencesForm pereferencesForm, BindingResult result) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		/*Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.PREFERENCES_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }*/
		PreferencesValidator pf=new PreferencesValidator();
		pf.validate(pereferencesForm, result);			
		if(result.hasErrors()){
			return papulateMapForPreferencesSave(request, model);
		}			
		try {
			PreferencesDto preferencesDto = commonService.getPreferences(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()));
			PreferencesDto oldObject = (PreferencesDto)preferencesDto.clone();
			preferencesDto = pereferencesForm.getPreferencesDto();
			preferencesDto.setAuthentication(Long.parseLong(session.getAttribute(AUTHENTICATION_ID).toString()));			
			commonService.updatePreferences(preferencesDto);
			//Audit Trail service
			auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_PREFEREANCES_UPDATE, 
					AuditTrailConstrain.STATUS_UPDATE, (String)session.getAttribute(USER_ID), (String)session.getAttribute(USER_TYPE), oldObject, preferencesDto);			
			
			model.put(SUCCESS_MESSAGE, context.getMessage(PREFERENCES_UPDATE_SUCCESS, null, locale));
			return papulateMapForPreferencesSave(request, model);
		} catch (CloneNotSupportedException e) {
			LOGGER.error(AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR, e);
			model.put(ERROR_MESSAGE, context.getMessage(PREFERENCES_UPDATE_FAILS, null, locale));
			return papulateMapForPreferencesSave(request, model);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(PREFERENCES_UPDATE_FAILS, null, locale));
			return papulateMapForPreferencesSave(request, model);
		}
	}
	
	private String papulateMapForPreferencesSave(HttpServletRequest request, Map model){
		model.put(LANGUAGE_ID, MasterDataUtil.getLanguageNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));

		return PREFERENCES_ADMIN_PAGE_VIEW;
	}
	
	private void convertDtoToForm(PereferencesForm pereferencesForm, PreferencesDto preferencesDto){
		pereferencesForm.setId(preferencesDto.getId());
		pereferencesForm.setLanguage(preferencesDto.getLanguage());
	}
	
}
