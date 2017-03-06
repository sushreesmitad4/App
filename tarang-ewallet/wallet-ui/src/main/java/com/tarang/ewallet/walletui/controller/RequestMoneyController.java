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
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.transaction.business.RequestMoneyService;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionStatus;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.constants.Customer;
import com.tarang.ewallet.walletui.form.RequestMoneyForm;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.TransactionsUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.RequestMoneyValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/requestmoney")
public class RequestMoneyController implements Customer, AttributeConstants, AttributeValueConstants {
	
	private static final Logger LOGGER = Logger.getLogger(RequestMoneyController.class);
	
	@Autowired
	private RequestMoneyService requestMoneyService;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String requestMoneyPage( Map model, Locale locale, HttpServletRequest request) {
		
		LOGGER.debug( " Request Money Page " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		
		String errMsg = (String)session.getAttribute(ERROR_MESSAGE);
		if(errMsg != null){
			model.put(ERROR_MESSAGE, context.getMessage(errMsg, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		String url = WALLET_PATH_PREFIX + "requestmoney/records";
		request.setAttribute("urlCustomerReceiveList", url);			
		return Customer.CUSTOMER_REQUEST_MONEY_LIST;
		
	}
	
	@RequestMapping(value = "/records", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<RequestMoneyDto> taxRecords(
			Map model, Locale locale, HttpSession session,
			HttpServletRequest request) {

		List<RequestMoneyDto> requestMoneyList = new ArrayList<RequestMoneyDto>();
		try {
			Long authId = (Long)session.getAttribute(AUTHENTICATION_ID);
			requestMoneyList = requestMoneyService.getRequestMoneyList(authId);
			for(RequestMoneyDto requestMoneyDto:requestMoneyList){
			    requestMoneyDto.setCurrencyName(MasterDataUtil.getCountryCurrencyCode(
			    		request.getSession().getServletContext(), requestMoneyDto.getCurrencyId()));
			    requestMoneyDto.setStatusName(MasterDataUtil.getSimpleDataMap(
			    		request.getSession().getServletContext(), 
			    		(Long) request.getSession().getAttribute(LANGUAGE_ID),
			    		MasterDataConstants.RECEIVE_MONEY_STATUS).get(requestMoneyDto.getStatus()));
			    requestMoneyDto.setAmountString(UIUtil.getConvertedAmountInString(requestMoneyDto.getAmount()));			    
			    requestMoneyDto.setDateToString(DateConvertion.dateToString(requestMoneyDto.getRequestDate()));
			   
			    if(authId.equals(requestMoneyDto.getRequesterId())){
			    	requestMoneyDto.setIsSelfRequest("self");
			    } else{
			    	requestMoneyDto.setIsSelfRequest("other");
			    }
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error("TransactionRecords", e);
		}
		
		JqgridResponse<RequestMoneyDto> response = new JqgridResponse<RequestMoneyDto>();
		response.setRows(requestMoneyList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = requestMoneyList.size()/ps;
		if( requestMoneyList.size()/ps*ps != requestMoneyList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
	}	
	
	@RequestMapping(value = "/requestmoney", method = RequestMethod.GET)
	public String requestMoney(Map model, HttpServletRequest request, 
			@Valid RequestMoneyForm requestMoneyForm, Locale locale) {
		LOGGER.debug( " requestMoney :: GET " );
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		model.put(CURRENCY_LIST, MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		model.put(COUNTRY_PHONE_CODES, MasterDataUtil.getPhoneCodes(request.getSession().getServletContext()));
		return  CUSTOMER_REQUEST_MONEY_CREATE;
	}
	
	@RequestMapping(value = "/requestmoney", method = RequestMethod.POST)
	public String requestMoneyReceived(Map model, HttpServletRequest request, Locale locale,
			@Valid RequestMoneyForm requestMoneyForm, BindingResult result) {
		LOGGER.debug(" requestmoney :: POST ");
		String viewPage = "";
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		RequestMoneyValidator requestMoneyValidator = new RequestMoneyValidator();
		requestMoneyValidator.validate(requestMoneyForm, result);

		if(!result.hasErrors() && requestMoneyForm.getEmailId().equals(session.getAttribute(USER_ID))){
			result.rejectValue(EMAIL_ID, "receivemoney.emailId.errmsg.exist");
		}
		Authentication responserAuth = null;
		try {
			if(!result.hasErrors()){
				responserAuth = commonService.getAuthentication(requestMoneyForm.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			}
		} catch (WalletException e1) {
			LOGGER.error(e1.getMessage(), e1);
			result.rejectValue(EMAIL_ID, "customer.emailid.notexist");
		}
		
		if(!result.hasErrors()){
			if(null == responserAuth){
				result.rejectValue(EMAIL_ID, "customer.emailid.notexist");
			} else if(UserStatusConstants.DELETED.equals(responserAuth.getStatus())){
				result.rejectValue(EMAIL_ID, CommonConstrain.DELETED_USER_FAILS_EXCEPTION);
			} else if(!UserStatusConstants.APPROVED.equals(responserAuth.getStatus())){
				result.rejectValue(EMAIL_ID, "error.msg.not.approved.customer.account");
			} else if(!responserAuth.isActive()){
				result.rejectValue(EMAIL_ID, "error.msg.not.active.customer.account");
			}
		}
		if(result.hasErrors()){
			viewPage = CUSTOMER_REQUEST_MONEY_CREATE;
		} else {
			if(requestMoneyForm.getMode() != null){
				if ("back".equals(requestMoneyForm.getMode())) {
					model.put(CURRENCY_LIST, MasterDataUtil.getCurrencyNames(
							request.getSession().getServletContext(),
							(Long) request.getSession().getAttribute(LANGUAGE_ID)));
					model.put(COUNTRY_PHONE_CODES, MasterDataUtil.getPhoneCodes(request.getSession().getServletContext()));
					viewPage = CUSTOMER_REQUEST_MONEY_CREATE;
				} else if( "confirm".equals(requestMoneyForm.getMode())){
					try{
						RequestMoneyDto requestMoneyDto = new RequestMoneyDto();
						requestMoneyDto.setRequesterEmail((String)session.getAttribute(USER_ID));
						TransactionsUtil.convertRequestMoneyFormToDto(requestMoneyForm, requestMoneyDto);
						papulateDefultRequestMoneyDtoInfo(request, requestMoneyDto);
						requestMoneyService.createRequestMoney(requestMoneyDto);
						//Audit Trail service
						auditTrailService.createAuditTrail(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_REQUEST_MONEY_CREATE, 
								AuditTrailConstrain.STATUS_CREATE, requestMoneyDto.getRequesterEmail(), GlobalLitterals.CUSTOMER_USER_TYPE);
						
						viewPage = UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						String em = null;
						if(e.getMessage().contains(CommonConstrain.CUSTOMER_EMAILID_NOT_EXIST)){
							em = "customer.emailid.notexist";
						} else if(e.getMessage().contains(CommonConstrain.RECEIVEMONEY_CREATE_FAILS_EXCEPTION)){
							em = "receivemoney.create.failed";
						} else {
							em = "receivemoney.create.failed";
						}
						model.put(ERROR_MESSAGE, context.getMessage(em, null, locale));
						viewPage = CUSTOMER_REQUEST_MONEY_CREATE;
					}
				} else if("submit".equals(requestMoneyForm.getMode())){
					requestMoneyForm.setCurrencyCodeName(MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(), 
							(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(requestMoneyForm.getCurrency()));
					viewPage = CUSTOMER_REQUEST_MONEY_SUCCESS;
				}
			}
		}
		if(!CUSTOMER_REQUEST_MONEY_SUCCESS.equals(viewPage)){
			model.put(CURRENCY_LIST, MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)));
			model.put(COUNTRY_PHONE_CODES, MasterDataUtil.getPhoneCodes(request.getSession().getServletContext()));
		}
		return  viewPage;
	}
	
	@RequestMapping(value = "/viewrequestmoney", method = RequestMethod.GET)
	public String viewReveiveMoney(Map model, HttpServletRequest request, Locale locale,
			@Valid RequestMoneyForm requestMoneyForm, BindingResult result) {
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		try {
			String requesterEmail = request.getParameter(REQUESTER_EMAIL);
			Long id = Long.valueOf(request.getParameter(ID));
			RequestMoneyDto requestMoneyDto = requestMoneyService.getRequestMoney(id);
			requestMoneyForm.setAmount(UIUtil.getConvertedAmountInString(requestMoneyDto.getAmount()));
			requestMoneyForm.setEmailId(requesterEmail);
			requestMoneyForm.setCurrency(requestMoneyDto.getCurrencyId());
			requestMoneyForm.setStatus(requestMoneyDto.getStatus());
			requestMoneyForm.setRequestDate(DateConvertion.dateToString(requestMoneyDto.getRequestDate()));
			requestMoneyForm.setRequesterMsg(requestMoneyDto.getRequesterMsg());
			requestMoneyForm.setResponserMsg(requestMoneyDto.getResponserMsg());
			requestMoneyForm.setStatusName(MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), (Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.RECEIVE_MONEY_STATUS).get(requestMoneyDto.getStatus()));
			requestMoneyForm.setCurrencyCodeName(MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(requestMoneyDto.getCurrencyId()));
		} catch (WalletException e) {
			LOGGER.error(e.getMessage() , e);
		}
		return  CUSTOMER_REQUEST_MONEY_VIEW;
	}
	
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public String acceptRequestMoney(Map model, HttpServletRequest request, Locale locale,
			@Valid RequestMoneyForm requestMoneyForm, BindingResult result) {
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		try {
			Long authId = (Long)session.getAttribute(AUTHENTICATION_ID);
			String requesterEmail = request.getParameter(REQUESTER_EMAIL);
			Long id = Long.valueOf(request.getParameter("id"));
			RequestMoneyDto requestMoneyDto = requestMoneyService.getRequestMoney(id);
			Long requestMoneyStatus = requestMoneyDto.getStatus();
			if(authId.equals(requestMoneyDto.getRequesterId())){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_NOT_AUTHORIZED_TO_ACCEPT_REQUESTED_MONEY);
				return UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
			}
			if(!requestMoneyStatus.equals(WalletTransactionStatus.PENDING)){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_PENDING_RECORDS_REQUIRED);
				return  UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
			}
			requestMoneyForm.setAmount(UIUtil.getConvertedAmountInString(requestMoneyDto.getAmount()));
			requestMoneyForm.setEmailId(requesterEmail);
			requestMoneyForm.setCurrency(requestMoneyDto.getCurrencyId());
			requestMoneyForm.setStatus(requestMoneyDto.getStatus());
			requestMoneyForm.setRequestDate(DateConvertion.dateToString(requestMoneyDto.getRequestDate()));
			requestMoneyForm.setRequesterMsg(requestMoneyDto.getRequesterMsg());
			requestMoneyForm.setResponserMsg(requestMoneyDto.getResponserMsg());
			requestMoneyForm.setUserWalletBalance(UIUtil.getConvertedAmountInString(requestMoneyDto.getUserWalletBalance()));
			requestMoneyForm.setTxnDeductedBalance(UIUtil.getConvertedAmountInString(requestMoneyDto.getTxnDeductedBalance()));
			requestMoneyForm.setCurrencyCodeName(MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(requestMoneyDto.getCurrencyId()));
			if(requestMoneyDto.getUserWalletBalance() < requestMoneyDto.getTxnDeductedBalance()){
				request.setAttribute(IS_ENOUGH_CREDIT, Boolean.FALSE);
				model.put(ERROR_MESSAGE, context.getMessage(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE, null, locale));
			}
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
		}		
		return  CUSTOMER_REQUEST_MONEY_ACCEPT;
	 }
	
	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	public String acceptedRequestMoney(Map model, HttpServletRequest request, Locale locale,
			@Valid RequestMoneyForm requestMoneyForm, BindingResult result) {
		HttpSession session = request.getSession();
		String viewPage = CUSTOMER_REQUEST_MONEY_ACCEPT;
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		RequestMoneyValidator requestMoneyValidator = new RequestMoneyValidator();
		requestMoneyValidator.validate(requestMoneyForm, result);
		if(result.hasErrors()){
			viewPage = CUSTOMER_REQUEST_MONEY_ACCEPT;
		} else {
			try{
				RequestMoneyDto requestMoneyDto = requestMoneyService.getRequestMoney(requestMoneyForm.getId());
				String userId = (String) session.getAttribute(USER_ID);
				String userType = (String) session.getAttribute(USER_TYPE);
				Long customerId = customerService.getCustomerId(userId, userType);
				CustomerDto customerDto = customerService.getCustomerDto(customerId);
				requestMoneyDto.setCountryId(customerDto.getCountry());	
				Long requestMoneyStatus = requestMoneyDto.getStatus();
				if(requestMoneyDto.getStatus().equals(WalletTransactionStatus.PENDING)){
					requestMoneyDto.setStatus(WalletTransactionStatus.SUCCESS);
				} else {
					session.setAttribute(ERROR_MESSAGE, ERROR_MSG_REQUESTMONEY_FAILED_ACCEPT);
					return  UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
				}
				requestMoneyDto.setResponserMsg(requestMoneyForm.getResponserMsg());
				requestMoneyDto.setRequesterEmail(requestMoneyForm.getEmailId());
				requestMoneyDto.setIpAddress(UIUtil.getClientIpAddr(request));
				requestMoneyDto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
				        (Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
				papulateDefultRequestMoneyDtoInfo(request, requestMoneyDto);
				requestMoneyService.acceptRequestMoney(requestMoneyDto);
				//Audit Trail service
				auditTrailService.createAuditTrailForRequestMoney(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_REQUEST_MONEY_ACCEPT, 
						AuditTrailConstrain.STATUS_UPDATE, session.getAttribute(USER_ID).toString(), GlobalLitterals.CUSTOMER_USER_TYPE, requestMoneyStatus, requestMoneyDto.getStatus());
				
				viewPage = UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				String em = null;
				if(e.getMessage().contains(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE)){
					em = WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE;
				} else if(e.getMessage().contains(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT)){
					em = WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT;
				} else {
					em = Common.UNABLE_TO_PROCESS_MSG;
				}
				model.put(ERROR_MESSAGE, context.getMessage(em, null, locale));
				viewPage = CUSTOMER_REQUEST_MONEY_ACCEPT;
			}	
		}
		return  viewPage;
	 }
	
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public String rejectRequestMoney(Map model, HttpServletRequest request, Locale locale,
			@Valid RequestMoneyForm requestMoneyForm, BindingResult result) {
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		try {
			Long authId = (Long)session.getAttribute(AUTHENTICATION_ID);
			String requesterEmail = request.getParameter(REQUESTER_EMAIL);
			Long id = Long.valueOf(request.getParameter(ID));
			RequestMoneyDto requestMoneyDto = requestMoneyService.getRequestMoney(id);
			Long requestMoneyStatus = requestMoneyDto.getStatus();
			if(authId.equals(requestMoneyDto.getRequesterId())){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_NOT_AUTHORIZED_TO_REJECT_REQUESTED_MONEY);
				return UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
			}
			if(!requestMoneyStatus.equals(WalletTransactionStatus.PENDING)){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_PENDING_RECORDS_REQUIRED);
				return  UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
			}
			requestMoneyForm.setAmount(UIUtil.getConvertedAmountInString(requestMoneyDto.getAmount()));
			requestMoneyForm.setEmailId(requesterEmail);
			requestMoneyForm.setCurrency(requestMoneyDto.getCurrencyId());
			requestMoneyForm.setStatus(requestMoneyDto.getStatus());
			requestMoneyForm.setRequestDate(DateConvertion.dateToString(requestMoneyDto.getRequestDate()));
			requestMoneyForm.setRequesterMsg(requestMoneyDto.getRequesterMsg());
			requestMoneyForm.setResponserMsg(requestMoneyDto.getResponserMsg());
			requestMoneyForm.setCurrencyCodeName(MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(requestMoneyDto.getCurrencyId()));
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
		}		
		return  CUSTOMER_REQUEST_MONEY_REJECT;
	 }
	
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public String rejectedRequestMoney(Map model, HttpServletRequest request, Locale locale,
			@Valid RequestMoneyForm requestMoneyForm, BindingResult result) {
		HttpSession session = request.getSession();
		String viewPage = "";
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		RequestMoneyValidator requestMoneyValidator = new RequestMoneyValidator();
		requestMoneyValidator.validate(requestMoneyForm, result);
		if(result.hasErrors()){
			viewPage = CUSTOMER_REQUEST_MONEY_REJECT;
		} else {
			try{
				RequestMoneyDto requestMoneyDto = requestMoneyService.getRequestMoney(requestMoneyForm.getId());
				Long requestMoneyStatus = requestMoneyDto.getStatus();
				if(requestMoneyDto.getStatus().equals(WalletTransactionStatus.PENDING)){
					requestMoneyDto.setStatus(WalletTransactionStatus.REJECT);
				} else {
					session.setAttribute(ERROR_MESSAGE, ERROR_MSG_REQUESTMONEY_FAILED_REJECT);
					return  UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
				}
				requestMoneyDto.setResponserMsg(requestMoneyForm.getResponserMsg());
				requestMoneyDto.setRequesterEmail(requestMoneyForm.getEmailId());
				papulateDefultRequestMoneyDtoInfo(request, requestMoneyDto);
				requestMoneyService.rejectRequestMoney(requestMoneyDto);
				//Audit Trail service
				auditTrailService.createAuditTrailForRequestMoney(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_REQUEST_MONEY_REJECT, 
						AuditTrailConstrain.STATUS_UPDATE, session.getAttribute(USER_ID).toString(), GlobalLitterals.CUSTOMER_USER_TYPE, requestMoneyStatus, requestMoneyDto.getStatus());
				
				viewPage = UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
			} catch (Exception e) {
				LOGGER.error(e.getMessage() , e);
				viewPage = CUSTOMER_REQUEST_MONEY_REJECT;
			}
		}
		return  viewPage;
	 }
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public String cancelRequestMoney(Map model, HttpServletRequest request, Locale locale,
			@Valid RequestMoneyForm requestMoneyForm, BindingResult result) {
		LOGGER.debug(" requestMoney :: cancel ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		
		Long id = Long.valueOf(request.getParameter(ID));
		try{
			Long authId = (Long)session.getAttribute(AUTHENTICATION_ID);
			RequestMoneyDto rquestMoney = requestMoneyService.getRequestMoney(id);
			Long requestMoneyStatus = rquestMoney.getStatus();
			if(!authId.equals(rquestMoney.getRequesterId())){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_NOT_AUTHORIZED_TO_CANCEL_REQUESTED_MONEY);
				return UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
			}
			if(rquestMoney.getStatus().equals(WalletTransactionStatus.PENDING)){
				rquestMoney.setStatus(WalletTransactionStatus.CANCEL);
			} else {
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_REQUESTMONEY_FAILED_CANCEL);
				return  UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
			}
			String requesterEmail = request.getParameter(REQUESTER_EMAIL);
			rquestMoney.setRequesterEmail(requesterEmail);
			papulateDefultRequestMoneyDtoInfo(request, rquestMoney);
			requestMoneyService.cancelRequestMoney(rquestMoney);
			//Audit Trail service
			auditTrailService.createAuditTrailForRequestMoney(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), AuditTrailConstrain.MODULE_REQUEST_MONEY_CANCEL, 
					AuditTrailConstrain.STATUS_UPDATE, session.getAttribute(USER_ID).toString(), GlobalLitterals.CUSTOMER_USER_TYPE, requestMoneyStatus, rquestMoney.getStatus());
						
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);			
		}
		return  UIUtil.redirectPath(Customer.CUSTOMER_REQUESTMONEY_PATH);
	}

	private void papulateDefultRequestMoneyDtoInfo(HttpServletRequest request, RequestMoneyDto dto) throws WalletException{
		
		dto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
		dto.setCurrencyCode(MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(dto.getCurrencyId()));
		
		String personName = customerService.getPersonName(dto.getRequesterEmail(), AttributeConstants.CUSTOMER_USER_TYPE);
		dto.setPayeeName(personName);
		try{
			dto.setPayerName((String)request.getSession().getAttribute(NAME));
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);
		}
	}
	
}
