package com.tarang.ewallet.walletui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dispute.business.DisputeService;
import com.tarang.ewallet.dispute.util.DisputeStatusConstants;
import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.dto.DisputeGridDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.transaction.util.ReversalType;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Dispute;
import com.tarang.ewallet.walletui.form.DisputeForm;
import com.tarang.ewallet.walletui.form.DisputeSearchForm;
import com.tarang.ewallet.walletui.util.DisputeSearchUtil;
import com.tarang.ewallet.walletui.util.DisputeUtil;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.DisputeValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


/**
 * @author  : kedarnathd
 * @date    : Feb 12, 2013
 * @time    : 7:54:03 PM
 * @version : 1.0.0
 * @comments: 
 *
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/admindispute")
public class AdminDisputeController implements Dispute, AttributeConstants, 
		AttributeValueConstants, GlobalLitterals, DisputeStatusConstants {
	
	private static final Logger LOGGER = Logger.getLogger(AdminDisputeController.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private DisputeService disputeService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired 
	private UtilService utilService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MerchantService merchantService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showDisputePage(@Valid DisputeSearchForm disputeSearchForm, Map model,HttpServletRequest request, Locale locale) throws WalletException{
		
		LOGGER.debug( " Admin dispute default page " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
                MenuConstants.DISPUTE, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		String sucMessage = (String)session.getAttribute(SUCCESS_MESSAGE);
		
		if(sucMessage != null){
			model.put(SUCCESS_MESSAGE, sucMessage);
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		getDisputeTypes(request, model);
		String customerId = (String)request.getParameter("cemailid");
		String merchantId = (String)request.getParameter("memailid");
		String fromDate = (String)request.getParameter("fdate");
		String toDate = (String)request.getParameter("tdate");
		String disputeType = (String)request.getParameter("disputetype");
		
		model.put("disputeSearchForm", disputeSearchForm);
		disputeSearchForm.setCustomerEmailId(customerId);
		disputeSearchForm.setMerchantEmailId(merchantId);
		disputeSearchForm.setDisputeType(disputeType);
		disputeSearchForm.setFromDate(fromDate);
		disputeSearchForm.setToDate(toDate);
		disputeSearchForm.setUserType(disputeType);
		
		
		String url = null;
		int disputeDays = utilService.getDisputeDays();
		String errorMessage = null;
		if(customerId != null && !DisputeSearchUtil.mailValidationForC(customerId, commonService)){
			url = WALLET_PATH_PREFIX + ADMIN_DISPUTE_LIST_PATH;
			request.setAttribute(URL_DISPUTE_LIST, url);
			model.put(ERROR_MESSAGE, context.getMessage("error.msg.notexist.customer.emailid", null, locale));
			return ADMIN_DISPUTE_VIEW;
		}
		if(merchantId != null && !DisputeSearchUtil.mailValidation(merchantId, commonService)){
			url = WALLET_PATH_PREFIX + ADMIN_DISPUTE_LIST_PATH;
			request.setAttribute(URL_DISPUTE_LIST, url);
			model.put(ERROR_MESSAGE, context.getMessage("error.msg.notexist.merchant.emailid", null, locale));
			return ADMIN_DISPUTE_VIEW;
		}
		if(fromDate != null){	
			if(!DisputeSearchUtil.checkDate(fromDate, disputeDays)){
				url = WALLET_PATH_PREFIX + ADMIN_DISPUTE_LIST_PATH+"?search=false";
				request.setAttribute(URL_DISPUTE_LIST, url);
				model.put(ERROR_MESSAGE, context.getMessage("error.msg.notallow.toget.disputes", null, locale));
				return ADMIN_DISPUTE_VIEW;
			}
		} else {
			fromDate = CommonUtil.getBeforeDates(disputeDays);
		}
		if(toDate != null){
			if(!DisputeSearchUtil.checkDate(toDate, disputeDays)){
				url = WALLET_PATH_PREFIX + ADMIN_DISPUTE_LIST_PATH+"?search=false";
				request.setAttribute(URL_DISPUTE_LIST, url);
				errorMessage = "error.msg.notallow.toget.disputes";
				model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
				return ADMIN_DISPUTE_VIEW;
			}
		} else{
			toDate = CommonUtil.getBeforeDates(0);
		}
		
		url = WALLET_PATH_PREFIX + "admindispute/list?search=true&merchantId=" + merchantId + "&customerId=" + customerId 
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&disputeType=" + disputeType;
		
		request.setAttribute(URL_DISPUTE_LIST, url);
		return ADMIN_DISPUTE_VIEW;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<DisputeGridDto> disputeRecords(@Valid DisputeForm disputeForm, Map model, Locale locale, HttpServletRequest request) {

		JqgridResponse<DisputeGridDto> response = new JqgridResponse<DisputeGridDto>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}		
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.DISPUTE, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return response;	
	    }
	    
	    List<DisputeGridDto> displayList = null;
		String search = request.getParameter("search");
		Long customerId = 0L;
		Long merchantId = 0L;
		Long disputeType = 0L;
		try {
			if("true".equals(search)){				
				Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
				if(languageId == null){
					languageId = MasterDataConstants.DEFAULT_LANGUAGE;
				}
				Authentication cusAuth = commonService.getAuthentication((String)request.getParameter("customerId"), GlobalLitterals.CUSTOMER_USER_TYPE);
				
				if(cusAuth != null){
					customerId = cusAuth.getId();
				}
				Authentication merAuth = commonService.getAuthentication((String)request.getParameter("merchantId"), GlobalLitterals.MERCHANT_USER_TYPE);
				if(merAuth != null){
					merchantId = merAuth.getId();
				}
				int disputeDays = utilService.getDisputeDays();
			    String fromd = (String)request.getParameter("fromDate");
			    String tod = (String)request.getParameter("toDate");
			    if( fromd == null || NULL.equals(fromd)) {
			    	fromd = CommonUtil.getBeforeDates(disputeDays);
			    }
			    if(tod == null || NULL.equals(tod)) {
			    	tod = CommonUtil.getBeforeDates(0);
			    }
			    Date fromDate =DateConvertion.getFromDate( DateConvertion.stirngToDate(fromd));
			    Date toDate = DateConvertion.getToDate(DateConvertion.stirngToDate(tod)); 
				try{
					disputeType = Long.valueOf(request.getParameter("disputeType"));
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
				
				displayList = disputeService.getDisputesForAdmin(LIMIT, languageId, fromDate, toDate, merchantId,customerId, disputeType);					
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error("disputeRecords", e);
		}
		if(displayList == null) {
			displayList = new ArrayList<DisputeGridDto>();
		}
		response.setRows(displayList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = displayList.size()/ps;
		if( displayList.size()/ps*ps != displayList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
	}
	
	@RequestMapping(value="update", method=RequestMethod.GET)
	public String loadUpdateDispute(@Valid DisputeForm disputeForm, Map model,HttpServletRequest request) throws WalletException{
		
		LOGGER.debug( " Admin update dispute default page " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
                MenuConstants.DISPUTE, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }

		Long disputeId = Long.valueOf(request.getParameter("disputeid"));
		Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
		if(languageId == null){
			languageId = MasterDataConstants.DEFAULT_LANGUAGE;
		}
		
		DisputeDto disputeDto = disputeService.getDisputeById(disputeId, languageId);
		DisputeUtil.convertdisputeDtoToForm(disputeDto, disputeForm);
		disputeForm.setDisputeId(disputeId);
		validateAproveAmountAndStatus(request, model, disputeDto.getType(), disputeDto.getStatus());
		return ADMIN_DISPUTE_UPDATE;
	}
	
	@RequestMapping(value="updateDispute", method=RequestMethod.POST)
	public String updateDispute(HttpServletRequest request,Map model, 
			Locale locale, @Valid DisputeForm disputeForm, BindingResult result) throws WalletException {
		
		LOGGER.debug( " updateDispute " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
                MenuConstants.DISPUTE, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
		if(languageId == null){
			languageId = 1L;
		}
		DisputeDto disputeDto = null;
		try{
			disputeForm.setUserType(ADMIN_USER_TYPE_ID);
			new DisputeValidator().validate(disputeForm, result);
			disputeDto = disputeService.getDisputeById(disputeForm.getDisputeId(), languageId);			
			if(result.hasErrors()){
				disputeForm.setDtoMessages(disputeDto.getDtomessages());
				disputeForm.setTransactionAmount(UIUtil.getConvertedAmountInString(disputeDto.getTransactionamount()));
				disputeForm.setTransactionCurrency(disputeDto.getTransactioncurrency());
				validateAproveAmountAndStatus(request, model, disputeDto.getType(), disputeDto.getStatus());
				return ADMIN_DISPUTE_UPDATE;
			}
			Long dStatus = disputeForm.getStatus();
			if((disputeForm.getStatus().equals(DisputeStatusConstants.PENDING)) 
							|| (disputeForm.getStatus().equals(DisputeStatusConstants.MERCHANT_REJECTED))){
				dStatus = disputeForm.getFormStatus() != null?disputeForm.getFormStatus():disputeForm.getStatus();
			}
			DisputeDto updateDto = new DisputeDto();
			updateDto.setId(disputeForm.getDisputeId());
			updateDto.setStatus(dStatus);
			updateDto.setMessage(disputeForm.getMessage());
			updateDto.setCreator(new BigDecimal(GlobalLitterals.ADMIN_USER_TYPE_ID.toString()));
			updateDto.setRequestamount(Double.parseDouble(disputeForm.getRequestAmount()));
			updateDto.setRequestcurrency(disputeForm.getRequestCurrency());
			updateDto.setApprovedAmount(Double.parseDouble(disputeForm.getApprovedAmount()));
			updateDto.setApprovedcurrency(disputeForm.getApprovedCurrency());
			
			/*For email template*/
			updateDto.setPayeeemailid(disputeForm.getPayeeEmailId());
			updateDto.setPayeremailid(disputeForm.getPayerEmailId());
			String payerName = null;
			try{
				payerName = customerService.getPersonName(disputeForm.getPayerEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			updateDto.setPayerName(payerName != null ? payerName : EMPTY_STRING);
			String payeeName = null;
			try{
				payeeName = merchantService.getLegalName(disputeForm.getPayeeEmailId());
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			updateDto.setPayeeName(payeeName != null ? payeeName : EMPTY_STRING);
			updateDto.setCurrencyCode(disputeForm.getApprovedCurrency());
			if(disputeForm.getDistype().equals(ReversalType.CHARGE_BACK)){
				updateDto.setDisputetype(context.getMessage(CHARGE_BACK_LBL, null, locale));
			} else{
				updateDto.setDisputetype(context.getMessage(REFUND_LBL, null, locale));
			}
			/*Dispute is updated by admin*/
			updateDto.setUpdatedby(context.getMessage(DISPUTE_UPDATED_BY_ADMIN, null, locale));
			disputeService.updateDispute(updateDto);
			
			//Audit Trail service
			
			auditTrailService.createAuditTrailForDispute(Long.parseLong(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_DISPUTE_UPDATE, 
					AuditTrailConstrain.STATUS_UPDATE, session.getAttribute(USER_ID).toString(), GlobalLitterals.ADMIN_USER_TYPE, disputeDto.getStatus(), disputeDto.getApprovedamount(), dStatus, Double.valueOf(disputeForm.getApprovedAmount()));
			
			session.setAttribute(SUCCESS_MESSAGE, context.getMessage(DISPUTE_UPDATE_SUCCESS, null, locale));
			return UIUtil.redirectPath(DISPUTE_ADMIN_PATH);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			disputeForm.setDtoMessages(disputeDto.getDtomessages());
			validateAproveAmountAndStatus(request, model, disputeDto.getType(), disputeDto.getStatus());
			model.put(ERROR_MESSAGE, context.getMessage(DISPUTE_UPDATE_FAILED, null, locale));
			return ADMIN_DISPUTE_UPDATE;
		}
	}
	
	private void getDisputeTypes(HttpServletRequest request, Map model) {
		Map<Long, String> disputeTypeList = MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.DISPUTE_TYPES);
		model.put(DISPUTE_TYPE_LIST, disputeTypeList);
	}
	
	private void validateAproveAmountAndStatus(HttpServletRequest request, Map model, Long type, Long status){
		if((type.equals(ReversalType.REFUND)) 
				&& ((status.equals(DisputeStatusConstants.PENDING)) 
						|| (status.equals(DisputeStatusConstants.MERCHANT_REJECTED)))){
			model.put(ADMIN_APPROVED_AMOUNT_REQUIRED, true);
		} else{
			model.put(ADMIN_APPROVED_AMOUNT_REQUIRED, false);
		}
		
		if((status.equals(DisputeStatusConstants.PENDING)) 
				|| (status.equals(DisputeStatusConstants.MERCHANT_REJECTED))){
			model.put(ADMIN_STATUS_REQUIRED, true);
			papulateDisputeStatus(request, model);
		} else{
			model.put(ADMIN_STATUS_REQUIRED, false);
		}
	}
	
	private void papulateDisputeStatus(HttpServletRequest request, Map model){
		Map<Long, String> disputeStatusMap  = new HashMap<Long, String>();
		disputeStatusMap.put(DisputeStatusConstants.APPROVED, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.DISPUTE_STATUS).get(DisputeStatusConstants.APPROVED));
		
		disputeStatusMap.put(DisputeStatusConstants.MERCHANT_TO_PAY, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.DISPUTE_STATUS).get(DisputeStatusConstants.MERCHANT_TO_PAY));
		
		disputeStatusMap.put(DisputeStatusConstants.ADMIN_REJECTED, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.DISPUTE_STATUS).get(DisputeStatusConstants.ADMIN_REJECTED));
		
		model.put(DISPUTE_STATUS_MAP, disputeStatusMap);
	}
}