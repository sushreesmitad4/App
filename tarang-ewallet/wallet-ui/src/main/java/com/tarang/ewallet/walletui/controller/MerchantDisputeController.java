package com.tarang.ewallet.walletui.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.DisputeValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;


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
@RequestMapping("/merchantdispute")
public class MerchantDisputeController implements Dispute, AttributeConstants, AttributeValueConstants, GlobalLitterals {
	
	private static final Logger LOGGER = Logger.getLogger(MerchantDisputeController.class);
	
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
		
	@RequestMapping(method=RequestMethod.GET)
	public String showDisputePage(@Valid DisputeSearchForm disputeSearchForm, Map model,HttpServletRequest request, Locale locale) throws WalletException{
		LOGGER.debug( " merchant dispute default Page " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String sucMessage = (String)session.getAttribute(SUCCESS_MESSAGE);
		
		if(sucMessage != null){
			model.put(SUCCESS_MESSAGE, sucMessage);
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		model.put(DISPUTE_TYPE_LIST, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.DISPUTE_TYPES));
		
		String customerId = (String)request.getParameter(CEMAIL_ID);
		String fromDate = (String)request.getParameter(F_DATE);
		String toDate = (String)request.getParameter(T_DATE);
		String disputeType = (String)request.getParameter(DISPUTETYPE);
		// code for validating customerId, fromDate, toDate
		String url = null;
			model.put("disputeSearchForm", disputeSearchForm );
			disputeSearchForm.setFromDate((String)request.getParameter(F_DATE));
			disputeSearchForm.setToDate((String)request.getParameter(T_DATE));
			disputeSearchForm.setCustomerEmailId((String)request.getParameter(CEMAIL_ID));
			disputeSearchForm.setDisputeType(request.getParameter(DISPUTETYPE));

			int disputeDays = utilService.getDisputeDays();
			if(customerId != null && !DisputeSearchUtil.mailValidationForC(customerId, commonService) ){
				url = WALLET_PATH_PREFIX + "merchantdispute/list";
				request.setAttribute(URL_DISPUTE_LIST, url);
				model.put(ERROR_MESSAGE, context.getMessage("error.msg.notexist.customer.emailid", null, locale));
				return MERCHANT_DISPUTE_VIEW;
			}
			
			if(fromDate != null){	
				if(!DisputeSearchUtil.checkDate(fromDate, disputeDays)){
					url = WALLET_PATH_PREFIX + "merchantdispute/list";
					request.setAttribute(URL_DISPUTE_LIST, url);
					model.put(ERROR_MESSAGE, context.getMessage("error.msg.notallow.toget.disputes", null, locale));
					return MERCHANT_DISPUTE_VIEW;
				}
			} else {
				fromDate = CommonUtil.getBeforeDates(disputeDays);
			}
			
			if(toDate != null){
				if(!DisputeSearchUtil.checkDate(toDate, disputeDays)){
					url = WALLET_PATH_PREFIX + "merchantdispute/list";
					request.setAttribute(URL_DISPUTE_LIST, url);
					model.put(ERROR_MESSAGE, context.getMessage("error.msg.notallow.toget.disputes", null, locale));
					return MERCHANT_DISPUTE_VIEW;
				}
			} else{
				toDate = CommonUtil.getBeforeDates(0);
			}
			url = WALLET_PATH_PREFIX + "merchantdispute/list?search=disputerecords&customerId=" 
					+ customerId + "&fromDate=" + fromDate + "&toDate=" + toDate + "&disputeType=" + disputeType;
		
		request.setAttribute(URL_DISPUTE_LIST, url);
		return MERCHANT_DISPUTE_VIEW;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<DisputeGridDto> disputeRecords(Map model, Locale locale, HttpServletRequest request) {

		List<DisputeGridDto> displayList = null;
		
		try {
			String search = request.getParameter("search");
			if(search != null && "disputerecords".equals(search)){
				
				// validate fields
				int disputeDays = utilService.getDisputeDays();
				String fromd = (String)request.getParameter("fromDate");
				String tod = (String)request.getParameter("toDate");
				if( fromd == null || fromd.equals(NULL)) {
					fromd = CommonUtil.getBeforeDates(disputeDays);
				}
				if(tod == null || tod.equals(NULL)) {
					tod = CommonUtil.getBeforeDates(0);
				}
				Date fromDate = DateConvertion.stirngToDate(fromd);
				Date toDate = DateConvertion.getToDate(DateConvertion.stirngToDate(tod));
				
				Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
				if(languageId == null){
					languageId = MasterDataConstants.DEFAULT_LANGUAGE;
				}
				
				Long disputeType = 0L;
				if( request.getParameter(DISPUTE_TYPE) != null && !request.getParameter(DISPUTE_TYPE).equals(NULL)){
					disputeType = Long.valueOf(request.getParameter("disputeType"));
				}
				String customer = (String)request.getParameter("customerId");
				Long customerId = 0L;
				if(customer != null && !customer.equals(NULL)){
					Authentication customerAuth = commonService.getAuthentication(customer, GlobalLitterals.CUSTOMER_USER_TYPE);
					if(customerAuth == null){
						throw new WalletException("customer.not.exists");
					}
					customerId = customerAuth.getId();
				}
				
				Long merchantId = (Long) request.getSession().getAttribute(AUTHENTICATION_ID);
				Integer limit = utilService.getReportLimit();
				
				displayList = disputeService.getDisputesForMerchant(limit, languageId, fromDate, toDate, customerId, merchantId, disputeType);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error("disputeRecords", e);
		}
		if(displayList == null) {
			displayList = new ArrayList<DisputeGridDto>();
		}
		JqgridResponse<DisputeGridDto> response = new JqgridResponse<DisputeGridDto>();
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
	
	@RequestMapping(value = "/update", method=RequestMethod.GET)
	public String updateDisputePage(@Valid DisputeForm disputeForm, Map model,HttpServletRequest request) throws WalletException{
		LOGGER.debug( " merchant dispute update Page " );
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		Long disputeId = Long.valueOf(request.getParameter("id"));
		LOGGER.info("DISPUTE ID :: "+disputeId);
		
		Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
		if(languageId == null){
			languageId = 1L;
		}
		
		DisputeDto disputeDto = disputeService.getDisputeById(disputeId, languageId);
		
		DisputeUtil.convertdisputeDtoToForm(disputeDto, disputeForm);
		disputeForm.setDisputeId(disputeId);
		validateAproveAmountAndStatus(request, model, disputeDto.getType(), disputeDto.getStatus());
		return MERCHANT_DISPUTE_UPDATE;
	}
	
	@RequestMapping(value = "/update", method=RequestMethod.POST)
	public String updateDisputeSavePage(@Valid DisputeForm disputeForm, BindingResult result, Map model, 
			HttpServletRequest request,  Locale locale) throws WalletException{
		LOGGER.debug( " merchant dispute update Save Page " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		
		Long languageId = (Long)request.getAttribute(LANGUAGE_ID);
		if(languageId == null){
			languageId = 1L;
		}
		DisputeDto disputeDto = null;
		try{
			disputeForm.setUserType(MERCHANT_USER_TYPE_ID);
			new DisputeValidator().validate(disputeForm, result);
			disputeDto = disputeService.getDisputeById(disputeForm.getDisputeId(), languageId);
			if(result.hasErrors()){
				disputeForm.setDtoMessages(disputeDto.getDtomessages());
				disputeForm.setTransactionAmount(UIUtil.getConvertedAmountInString(disputeDto.getTransactionamount()));
				disputeForm.setTransactionCurrency(disputeDto.getTransactioncurrency());
				validateAproveAmountAndStatus(request, model, disputeDto.getType(), disputeDto.getStatus());
				return MERCHANT_DISPUTE_UPDATE;
			}
			Long dStatus = disputeForm.getStatus();
			if((disputeForm.getStatus().equals(DisputeStatusConstants.PENDING)) 
							|| (disputeForm.getStatus().equals(DisputeStatusConstants.MERCHANT_TO_PAY))){
				dStatus = disputeForm.getFormStatus() != null?disputeForm.getFormStatus():disputeForm.getStatus();
			}			
			DisputeDto  updateDto = new DisputeDto();
			updateDto.setId(disputeForm.getDisputeId());
			updateDto.setStatus(dStatus);
			updateDto.setMessage(disputeForm.getMessage());
			updateDto.setCreator(new BigDecimal(GlobalLitterals.MERCHANT_USER_TYPE_ID.toString()));
			updateDto.setRequestamount(Double.parseDouble(disputeForm.getRequestAmount()));
			updateDto.setRequestcurrency(disputeForm.getRequestCurrency());
			updateDto.setApprovedAmount(Double.parseDouble(disputeForm.getApprovedAmount()));
			updateDto.setApprovedcurrency(disputeForm.getApprovedCurrency());
			
			/*For email template*/
			updateDto.setPayeeemailid(disputeForm.getPayeeEmailId());
			updateDto.setPayeremailid(disputeForm.getPayerEmailId());
			updateDto.setPayeeName((String)session.getAttribute(NAME));
			/*Dispute is updated by merchant*/
			updateDto.setUpdatedby((String)session.getAttribute(NAME));
			String payerName = null; 
			try{
				payerName = customerService.getPersonName(disputeForm.getPayerEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			updateDto.setPayerName(payerName != null ? payerName : EMPTY_STRING);
			updateDto.setCurrencyCode(disputeForm.getApprovedCurrency());
			if(disputeForm.getDistype().equals(ReversalType.CHARGE_BACK)){
				updateDto.setDisputetype(context.getMessage(CHARGE_BACK_LBL, null, locale));
			} else{
				updateDto.setDisputetype(context.getMessage(REFUND_LBL, null, locale));
			}
			disputeService.updateDispute(updateDto);
						
			//Audit Trail service
			auditTrailService.createAuditTrailForDispute(Long.parseLong(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_DISPUTE_UPDATE, 
					AuditTrailConstrain.STATUS_UPDATE, disputeDto.getPayeeemailid(), GlobalLitterals.MERCHANT_USER_TYPE, disputeDto.getStatus(), disputeDto.getApprovedamount(), dStatus, Double.valueOf(disputeForm.getApprovedAmount()));
			
			session.setAttribute(SUCCESS_MESSAGE, context.getMessage(DISPUTE_UPDATE_SUCCESS, null, locale));
			return UIUtil.redirectPath(DISPUTE_MERCHANT_PATH);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			disputeDto = disputeService.getDisputeById(disputeForm.getDisputeId(), languageId);
			disputeForm.setDtoMessages(disputeDto.getDtomessages());
			validateAproveAmountAndStatus(request, model, disputeDto.getType(), disputeDto.getStatus());
			model.put(ERROR_MESSAGE, context.getMessage(DISPUTE_UPDATE_FAILED, null, locale));
			return MERCHANT_DISPUTE_UPDATE;
		}
	}
	
	private void validateAproveAmountAndStatus(HttpServletRequest request, Map model, Long type, Long status){
		if((type.equals(ReversalType.REFUND)) 
				&& ((status.equals(DisputeStatusConstants.PENDING)) 
						|| (status.equals(DisputeStatusConstants.MERCHANT_TO_PAY)))){
			model.put(MERCHANT_APPROVED_AMOUNT_REQUIRED, true);
		} else{
			model.put(MERCHANT_APPROVED_AMOUNT_REQUIRED, false);
		}
		
		if((status.equals(DisputeStatusConstants.PENDING)) 
				|| (status.equals(DisputeStatusConstants.MERCHANT_TO_PAY))){
			model.put(MERCHANT_STATUS_REQUIRED, true);
			papulateDisputeStatus(request, model);
		} else{
			model.put(MERCHANT_STATUS_REQUIRED, false);
		}
	}
	
	private void papulateDisputeStatus(HttpServletRequest request, Map model){
		Map<Long, String> disputeStatusMap  = new HashMap<Long, String>();
		disputeStatusMap.put(DisputeStatusConstants.APPROVED, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.DISPUTE_STATUS).get(DisputeStatusConstants.APPROVED));
		
		disputeStatusMap.put(DisputeStatusConstants.MERCHANT_REJECTED, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.DISPUTE_STATUS).get(DisputeStatusConstants.MERCHANT_REJECTED));
		model.put(DISPUTE_STATUS_MAP, disputeStatusMap);
	}
}
