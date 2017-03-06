package com.tarang.ewallet.walletui.controller;
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
import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.util.FeeTaxConstants;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Admin;
import com.tarang.ewallet.walletui.form.FeeMgmtForm;
import com.tarang.ewallet.walletui.util.FeePopulationUtil;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.FeeValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/feemgmt")
public class FeeMgmtController implements Admin, AttributeConstants, AttributeValueConstants, FeeTaxConstants {
	
	private static final Logger LOGGER = Logger.getLogger(FeeMgmtController.class);
	
	@Autowired
	private FeeMgmtService feeMgmtService;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String feesListPage(Map model, Locale locale, Long servicetype, HttpServletRequest request){
		
		LOGGER.debug( " feesListPage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		if(!validateAdminAccess(session, servicetype, MenuConstants.VIEW_PERMISSION)){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		String url = WALLET_PATH_PREFIX+"feemgmt/feerecords?servicetype=" + servicetype;
		model.put("urlFeedbackList", url);
		model.put(FEE_MGMT_SERVICE_TYPE, servicetype);
		
		String message = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(message != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(message, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		LOGGER.debug("Entering :: AdminUserMgmtController :: loadUserListPage method");
		return FEE_GRID_VIEW;
	}
	
	@RequestMapping(value="/addfee", method = RequestMethod.GET)
	public String addFeePage(Map model, @Valid FeeMgmtForm feeMgmtForm, 
			HttpServletRequest request){
		
		LOGGER.debug( " addFeePage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Long servicetype = Long.valueOf(request.getParameter(FEE_MGMT_SERVICE_TYPE));
		if(!validateAdminAccess(session, servicetype, MenuConstants.MANAGE_PERMISSION)){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		model.put(FEEMGMT_FORM , feeMgmtForm);

		feeMgmtForm.setServices(servicetype);
		if(FEE_FINANTIAL_TYPE.equals(servicetype)){
			feeMgmtForm.setValidation(Common.FINANCIAL_ADD_FEE_VALIDATOR);
		} else if( FEE_NON_FINANTIAL_TYPE.equals(servicetype) ){
			feeMgmtForm.setValidation(Common.NON_FINANCIAL_ADD_FEE_VALIDATOR);
			feeMgmtForm.setPayingentity(1L);
			feeMgmtForm.setFeeType(1L);
		} else if( FEE_NON_FINANTIAL_VARY_TYPE.equals(servicetype) ){
			feeMgmtForm.setValidation(Common.NON_FINANCIAL_VARY_ADD_FEE_VALIDATOR);
			feeMgmtForm.setPayingentity(2L);
			feeMgmtForm.setUserType(2L);
			feeMgmtForm.setUserTypeName(MasterDataUtil.getUserTypeName(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), feeMgmtForm.getUserType()));
		}
		
		FeePopulationUtil.populateMasterDataLists(model, request, servicetype, feeMgmtForm, FeePopulationUtil.FEE_ADD);
		return getAddPage(servicetype);
	}

	@RequestMapping(value="/addfee", method = RequestMethod.POST)
	public String addFeePageSave(Map model, Locale locale, @Valid FeeMgmtForm feeMgmtForm, 
			BindingResult result, HttpServletRequest request){
		
		LOGGER.debug( " addFeePageSave " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		if(!validateAdminAccess(session, feeMgmtForm.getServices(), MenuConstants.MANAGE_PERMISSION)){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		FeeValidator feeValidator = new FeeValidator(utilService);
		feeValidator.validate(feeMgmtForm, result);
		FeeDto feeMgmtDto = new FeeDto();
		if(result.hasErrors()){
			FeePopulationUtil.populateMasterDataLists(model, request, feeMgmtForm.getServices(), feeMgmtForm, FeePopulationUtil.FEE_ADD);
			return getAddPage(feeMgmtForm.getServices());
		} else {
			try{
				if(feeMgmtForm.getCurrency() == null){
					feeMgmtForm.setCurrency(MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), feeMgmtForm.getCountry()));
				}
				feeMgmtDto = feeMgmtForm.getFeeMgmt();
				if (createFeeMgmt(feeMgmtDto, model, locale)) {
					//Audit Trail service
					if(feeMgmtDto.getServices().equals(FeeTaxConstants.FEE_FINANTIAL_TYPE)){
						auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_FEE_FINANCIAL_CREATE, 
							AuditTrailConstrain.STATUS_CREATE, (String)session.getAttribute(USER_ID), GlobalLitterals.ADMIN_USER_TYPE);
					} else if(feeMgmtDto.getServices().equals(FeeTaxConstants.FEE_NON_FINANTIAL_TYPE)){
						auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_FEE_NON_FINANCIAL_CREATE, 
							AuditTrailConstrain.STATUS_CREATE, (String)session.getAttribute(USER_ID), GlobalLitterals.ADMIN_USER_TYPE);
					} else{
						auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_FEE_FINANCIAL_VELOCITY_CREATE, 
								AuditTrailConstrain.STATUS_CREATE, (String)session.getAttribute(USER_ID), GlobalLitterals.ADMIN_USER_TYPE);					
					}
					session.setAttribute(SUCCESS_MESSAGE, FEE_CREATE_SUCCESS_MSG);
					return UIUtil.redirectPath("feemgmt?servicetype=" + feeMgmtForm.getServices());
				} else {
					FeePopulationUtil.populateMasterDataLists(model, request, feeMgmtForm.getServices(), feeMgmtForm, FeePopulationUtil.FEE_ADD);
					return getAddPage(feeMgmtForm.getServices());
				}
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
				FeePopulationUtil.populateMasterDataLists(model, request, feeMgmtForm.getServices(), feeMgmtForm, FeePopulationUtil.FEE_ADD);
				return getAddPage(feeMgmtForm.getServices());
			}
		}	
	}
	
	private String getAddPage(Long servicetype){
		String avp = "";
		if(FEE_FINANTIAL_TYPE.equals(servicetype)){
			avp = FEE_FIN_ADD;
		} else if(FEE_NON_FINANTIAL_TYPE.equals(servicetype)){
			avp = FEE_NONFIN_ADD;
		} else if(FEE_NON_FINANTIAL_VARY_TYPE.equals(servicetype)){
			avp = FEE_NONFINVARY_ADD;
		}
		return avp;
	}
	
	@RequestMapping(value = "/editfee", method = RequestMethod.GET)
	public String editFeePage(Map model,
			Locale locale, @Valid FeeMgmtForm feeMgmtForm,
			BindingResult result, Long id, Long servicetype, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		if(!validateAdminAccess(session, servicetype, MenuConstants.MANAGE_PERMISSION)){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		FeeDto  feeMgmtDto = null;
		try {
			feeMgmtDto = feeMgmtService.getFee(id);
			feeMgmtForm.setFeeMgmt(feeMgmtDto);
			FeePopulationUtil.populateMasterDataLists(model, request, feeMgmtForm.getServices(), feeMgmtForm, FeePopulationUtil.FEE_EDIT);
		} catch (Exception e) {
			LOGGER.error("editAdminUserPage", e);
			// redirect to dashboard page
		}
		return getEditPage(servicetype);
	}
	
	private String getEditPage(Long servicetype){
		String avp = EMPTY_STRING;
		if(FEE_FINANTIAL_TYPE.equals(servicetype)){
			avp = FEE_FIN_EDIT;
		} else if(FEE_NON_FINANTIAL_TYPE.equals(servicetype)){
			avp = FEE_NONFIN_EDIT;
		} else if(FEE_NON_FINANTIAL_VARY_TYPE.equals(servicetype)){
			avp = FEE_NONFINVARY_EDIT;
		}
		return avp;
	}

	@RequestMapping(value = "/editfee", method = RequestMethod.POST)
	public String editFeePageSave(Map model,
			Locale locale, @Valid FeeMgmtForm feeMgmtForm,
			BindingResult result,  HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		if(!validateAdminAccess(session, feeMgmtForm.getServices(), MenuConstants.MANAGE_PERMISSION)){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		FeeValidator feeValidator = new FeeValidator(utilService);
		feeValidator.validate(feeMgmtForm, result);
		if(result.hasErrors()){
			FeePopulationUtil.populateMasterDataLists(model, request, feeMgmtForm.getServices(), feeMgmtForm, FeePopulationUtil.FEE_EDIT);
			model.put("feeMgmtForm", feeMgmtForm);
			return getEditPage(feeMgmtForm.getServices());
		} else{
			FeeDto  feeMgmtDto = feeMgmtForm.getFeeMgmt();
			try {	
				FeeDto feeDto = feeMgmtService.getFee(feeMgmtForm.getId());	
				FeeDto oldFeeDto = (FeeDto)feeDto.clone();
				feeMgmtDto = feeMgmtService.updateFee(feeMgmtDto);
				//Audit Trail service
				if(oldFeeDto.getServices().equals(FeeTaxConstants.FEE_FINANTIAL_TYPE)){
					auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_FEE_FINANCIAL_UPDATE, 
						AuditTrailConstrain.STATUS_UPDATE, (String)session.getAttribute(USER_ID), GlobalLitterals.ADMIN_USER_TYPE, oldFeeDto, feeMgmtDto);
				} else if(oldFeeDto.getServices().equals(FeeTaxConstants.FEE_NON_FINANTIAL_TYPE)){
					auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_FEE_NON_FINANCIAL_UPDATE, 
						AuditTrailConstrain.STATUS_UPDATE, (String)session.getAttribute(USER_ID), GlobalLitterals.ADMIN_USER_TYPE, oldFeeDto, feeMgmtDto);
				} else{
					auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_FEE_FINANCIAL_VELOCITY_UPDATE, 
							AuditTrailConstrain.STATUS_UPDATE, (String)session.getAttribute(USER_ID), GlobalLitterals.ADMIN_USER_TYPE, oldFeeDto, feeMgmtDto);					
				}
				session.setAttribute(SUCCESS_MESSAGE, FEE_SUCC_UPDATED);
			} catch (CloneNotSupportedException e) {
				LOGGER.error(AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR, e);
				FeePopulationUtil.populateMasterDataLists(model, request, feeMgmtForm.getServices(), feeMgmtForm, FeePopulationUtil.FEE_EDIT);
			} catch (WalletException e) {
				LOGGER.error("editFeePage", e);
				FeePopulationUtil.populateMasterDataLists(model, request, feeMgmtForm.getServices(), feeMgmtForm, FeePopulationUtil.FEE_EDIT);
			}
		}
		return UIUtil.redirectPath("feemgmt?servicetype=" + feeMgmtForm.getServices());
	}
	
	private String getViewPage(Long servicetype){
		String avp = EMPTY_STRING;
		if(FEE_FINANTIAL_TYPE.equals(servicetype)){
			avp = FEE_FIN_VIEW;
		} else if(FEE_NON_FINANTIAL_TYPE.equals(servicetype)){
			avp = FEE_NONFIN_VIEW;
		} else if(FEE_NON_FINANTIAL_VARY_TYPE.equals(servicetype)){
			avp = FEE_NONFINVARY_VIEW;
		}
		return avp;
	}
	
	@RequestMapping(value = "/deletefee", method = RequestMethod.GET)
	public String deleteFeePage(HttpServletRequest request, Map model, Locale locale, Long id, Long servicetype) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		if(!validateAdminAccess(session, servicetype, MenuConstants.MANAGE_PERMISSION)){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		try {
			//Delete selected fee and slab
			Boolean flag = feeMgmtService.deleteFee(id);
			if(flag){
				session.setAttribute(SUCCESS_MESSAGE, FEE_DELETED_SUC);
			} else{
				session.setAttribute(ERROR_MESSAGE, FAILED_TO_DELETE_FEE);
			}
			
		} catch (Exception e) {
			LOGGER.error("deleteFeePage", e);
			session.setAttribute(ERROR_MESSAGE, FAILED_TO_DELETE_FEE);
		}
		return UIUtil.redirectPath("feemgmt?servicetype=" + servicetype);
	}
	
	@RequestMapping(value = "/viewfee", method = RequestMethod.GET)
	public String viewFeePage(Map model,
			Locale locale, @Valid FeeMgmtForm feeMgmtForm,
			BindingResult result, Long id, Long servicetype, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		if(!validateAdminAccess(session, servicetype, MenuConstants.VIEW_PERMISSION)){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		FeeDto feeMgmtDto = null;
		try {
			feeMgmtDto = feeMgmtService.getFee(id);
			feeMgmtForm.setFeeMgmt(feeMgmtDto);
			feeMgmtForm.setUserTypeName(MasterDataUtil.getUserTypeName(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), feeMgmtForm.getUserType()));
			feeMgmtForm.setServiceName(MasterDataUtil.getServiceName(request.getSession().getServletContext(),
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					feeMgmtForm.getServices()));
			feeMgmtForm.setOperationTypeName(MasterDataUtil.getAllOperationTypes(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(feeMgmtForm.getOperationType()));
			feeMgmtForm.setCountryName(MasterDataUtil.getCountryName(request, MasterDataUtil.MD_COUNTRIES, feeMgmtForm.getCountry()));
			feeMgmtForm.setCurrencyName(MasterDataUtil.getCountryCurrencyCode(
					request.getSession().getServletContext(), feeMgmtForm.getCurrency()));
			feeMgmtForm.setPayingentityName(MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
					(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataUtil.FEE_PAYING_ENTITIES).get(feeMgmtForm.getPayingentity()));
			feeMgmtForm.setFeeTypeName(MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataUtil.FEE_TYPES).get(feeMgmtForm.getFeeType()));
			feeMgmtForm.setTimeFreequencyName(MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), (Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.FEE_TIME_FREQUENCY).get(feeMgmtForm.getTimeFreequency()));
			
			model.put(FEEMGMT_FORM , feeMgmtForm);
			/*convert time frequency to correspond values*/
		}  catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return getViewPage(servicetype);
	}
	
	@RequestMapping(value="/feerecords", method = RequestMethod.GET, headers=JSON_HEADER, produces=JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<FeeDto> feeRecords(
    		@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sidx,
    		@RequestParam(value="sord", required=false) String sord,
    		HttpServletRequest request) throws WalletException {

		HttpSession session = request.getSession();
		JqgridResponse<FeeDto> response = new JqgridResponse<FeeDto>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}
		Long serviceType = null;
		try{
			serviceType = Long.valueOf(request.getParameter(FEE_MGMT_SERVICE_TYPE));
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		if(!validateAdminAccess(session, serviceType, MenuConstants.VIEW_PERMISSION)){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return response;
	    }
		
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(filters);
		qf.setPage(page);
		qf.setRows(rows);
		qf.setSidx(sidx);
		qf.setSord(sord);      
		
		List<FeeDto> fees = feeMgmtService.feeList(serviceType);
		populateNameWithIds(fees, request);

		response.setRows(fees);
		int ps = DEFAULT_PAGE_SIZE;
		int n = fees.size()/ps;
		if( fees.size()/ps*ps != fees.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
		
	}
	
	private void populateNameWithIds(List<FeeDto> fees, HttpServletRequest request){
		for(FeeDto feeDto:fees){
			feeDto.setUserTypeName(MasterDataUtil.getUserTypeName(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), feeDto.getUserType()));
			feeDto.setServiceName(MasterDataUtil.getServiceName(request.getSession().getServletContext(),
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					feeDto.getServices()));
			feeDto.setOperationTypeName(MasterDataUtil.getAllOperationTypes(request.getSession().getServletContext(), 
				     (Long) request.getSession().getAttribute(LANGUAGE_ID)).get(feeDto.getOperationType()));
			feeDto.setCountryName(MasterDataUtil.getCountryName(request, MasterDataUtil.MD_COUNTRIES, feeDto.getCountry()));
			feeDto.setCurrencyName(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext() 
					, feeDto.getCurrency()));
			feeDto.setFeeTypeName(MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataUtil.FEE_TYPES).get(feeDto.getFeeType()));
			feeDto.setPayingEntity(MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(),
					(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataUtil.FEE_PAYING_ENTITIES).get(feeDto.getPayingentity()));
		}
		
	}

	private boolean createFeeMgmt(FeeDto feeMgmtDto, Map model,Locale locale) throws WalletException {
		boolean saveFlag = true;
		FeeDto tempDto = null;
		try{
			tempDto = feeMgmtService.createFee(feeMgmtDto);
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			if(e.getMessage().equals(GlobalLitterals.DB_COM_DUPLICATE_ENTRY)){
				model.put(ERROR_MESSAGE, context.getMessage(FEE_OPE_COU_CUR_COM_EXIST + feeMgmtDto.getServices(), null, locale));
				return false;
			} else{
				model.put(ERROR_MESSAGE, context.getMessage(FEE_FAILS_TO_CREATE, null, locale));
			}
			
		}
		if (tempDto == null) {
			LOGGER.error("ERROR::createAdminUser:: feeMgmtDto is null");
			saveFlag = false;
		}
		LOGGER.debug("createAdminUser:: saveFlag: " + saveFlag);
		return saveFlag;
	}
	
	private Boolean validateAdminAccess(HttpSession session, Long servicetype, Long permission){
		Boolean adminAccessCheck = false;
		if(FEE_FINANTIAL_TYPE.equals(servicetype)){
			adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
		            MenuConstants.FEE_MANAGEMENT_F_MI, permission);
		} else if( FEE_NON_FINANTIAL_TYPE.equals(servicetype) ){
			adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
	                MenuConstants.FEE_MANAGEMENT_NF_MI, permission);
		} else if( FEE_NON_FINANTIAL_VARY_TYPE.equals(servicetype) ){
			adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
	                MenuConstants.FEE_MANAGEMENT_NFV_MI, permission);
		}
		return adminAccessCheck;
	}
	
}