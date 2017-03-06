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

import com.tarang.ewallet.accountcloser.business.AccountCloserService;
import com.tarang.ewallet.accountcloser.util.AccountCloserStatus;
import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.dispute.business.DisputeService;
import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.dto.AccountCloserMessageDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.walletui.controller.constants.AccountCloserConstant;
import com.tarang.ewallet.walletui.form.AccountCloserForm;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.AccountCloserValidator;


@SuppressWarnings({"rawtypes"})
@Controller
@RequestMapping("/accountcloser")
public class AccountCloserController implements AccountCloserConstant, AttributeConstants, AttributeValueConstants {
	
	private static final Logger LOGGER = Logger.getLogger(ResponseFeedbackController.class);
	
    @Autowired
	private ApplicationContext context;
    
	@Autowired
	private  AccountCloserService  accountCloserService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private DisputeService disputeService;
		
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String accountCloserRequest(Map model, @Valid AccountCloserForm accountCloserForm, 
	HttpServletRequest request, Locale locale) throws WalletException{
		LOGGER.debug( " accountCloserRequest " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String sugMsg = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(sugMsg != null){
			model.put(SUCCESS_MESSAGE, sugMsg);
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		
		String errorMsg = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorMsg != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorMsg, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		
		List<AccountCloserMessageDto> messages = null;
		try{
			Long authId = (Long)session.getAttribute(AUTHENTICATION_ID);
			LOGGER.info("accountCloserRequest :: auth id " + authId);
			AccountCloserDto dto = accountCloserService.getAccountCloserByUser(authId);
			
			if(dto != null){
				LOGGER.info("accountCloserRequest :: Request list page");
				accountCloserForm.setRequestedDate(dto.getRequestedDate());
				accountCloserForm.setAcountCloserId(dto.getId());
				messages = dto.getMessageList();
				if(null == messages){
					messages = new ArrayList<AccountCloserMessageDto>();
				}
				LOGGER.info("messages size :: "+messages.size());
				if(!messages.isEmpty()){
					accountCloserForm.setMessageList(messages);
				}
				
				Integer disputeCount = null;
				if(CUSTOMER_USER_TYPE.equals((String)session.getAttribute(USER_TYPE))){
					disputeCount =  disputeService.getDisputeCountForAccClose((Long)session.getAttribute(AUTHENTICATION_ID));
				}else{
					disputeCount =  disputeService.getActiveDisputeForMerchant((Long)session.getAttribute(AUTHENTICATION_ID));
				}
			
				if(disputeCount > 0){
					model.put("activeDispute", true);
				}
				return getView(session, REQUEST_LIST_VIEW);
			} else{
				LOGGER.info("accountCloserRequest ::Create Request page");
				return getView(session, CREATE_AC_REQUEST_VIEW);
			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getView(session, CREATE_AC_REQUEST_VIEW);
		}
		
		
	}
	/*Show add request page*/
	@RequestMapping(value="/createrequest", method = RequestMethod.GET)
	public String createAccountCloserRequest(Map model, @Valid AccountCloserForm accountCloserForm, 
	HttpServletRequest request) throws WalletException{
		LOGGER.debug( " createAccountCloserRequest " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		return getView(session, CREATE_AC_REQUEST_VIEW);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/createrequest", method = RequestMethod.POST)
	public String createAccountCloserRequestSave(@Valid AccountCloserForm accountCloserForm, BindingResult result,
			Map model, HttpServletRequest request, Locale locale){
		LOGGER.debug(" createAccountCloserRequestSave ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		
		try{
			Integer disputeCount = null;
			if(CUSTOMER_USER_TYPE.equals((String)session.getAttribute(USER_TYPE))){
				disputeCount =  disputeService.getDisputeCountForAccClose((Long)session.getAttribute(AUTHENTICATION_ID));
			} else {
				disputeCount =  disputeService.getActiveDisputeForMerchant((Long)session.getAttribute(AUTHENTICATION_ID));
			}
			
			if(disputeCount > 0){
				result.rejectValue(AccountCloserValidator.ACCOUNTCLOSE_MESSAGE_VAR, CommonConstrain.UNABLE_TOCLOSE_ACCOUNT_DISPUTE);
				return getView(session, CREATE_AC_REQUEST_VIEW);
			}
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(ERROR_MSG_ACCOUNT_CLOSER, null, locale));
			return getView(session, CREATE_AC_REQUEST_VIEW);
		}
		AccountCloserValidator accountCloseValidator = new AccountCloserValidator();
		accountCloseValidator.validate(accountCloserForm, result);
		if(result.hasErrors()){
			return getView(session, CREATE_AC_REQUEST_VIEW);
		} else {
			String userType = (String)session.getAttribute(USER_TYPE);
			Long authId = (Long)session.getAttribute(AUTHENTICATION_ID);
			AccountCloserDto accountCloserDto = new AccountCloserDto();
			accountCloserDto.setMessage(accountCloserForm.getMessage());
			accountCloserDto.setCreator(UIUtil.getUserTypeID(userType) );
			accountCloserDto.setStatus(AccountCloserStatus.PENDING);
			accountCloserDto.setAuthId(authId);
			accountCloserDto.setUserType(userType);
			try {
				accountCloserService.createAccountCloser(accountCloserDto);
				//Audit Trail service
				auditTrailService.createAuditTrail(authId, AuditTrailConstrain.MODULE_ACCOUNT_CLOSER_CREATION, 
						AuditTrailConstrain.STATUS_CREATE, (String)session.getAttribute(USER_ID), userType);			
				session.setAttribute(SUCCESS_MESSAGE, context.getMessage(SUCCESS_REQUESTMSG_ACCOUNT_CLOSER, null, locale));
				return UIUtil.redirectPath(ACCOUNT_CLOSER_SUCCESS_PATH);
			} catch (WalletException e) {
				LOGGER.error(e.getMessage(), e);
				model.put(ERROR_MESSAGE, context.getMessage(ERROR_MSG_ACCOUNT_CLOSER, null, locale));
				return getView(session, CREATE_AC_REQUEST_VIEW);
			}
		}
	}
	
	/*Show add message page*/
	@RequestMapping(value="/addmessage", method = RequestMethod.GET)
	public String addMessage(Map model, @Valid AccountCloserForm accountCloserForm, 
	HttpServletRequest request) throws WalletException{
		LOGGER.debug( " addMessage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		Integer disputeCount = null;
		if(CUSTOMER_USER_TYPE.equals((String)session.getAttribute(USER_TYPE))){
			disputeCount =  disputeService.getDisputeCountForAccClose((Long)session.getAttribute(AUTHENTICATION_ID));
		} else {
			disputeCount =  disputeService.getActiveDisputeForMerchant((Long)session.getAttribute(AUTHENTICATION_ID));
		}
	
		if(disputeCount > 0){
			session.setAttribute(ERROR_MESSAGE, ERROR_MSG_DISPUTE_EXIST_FAILS_TO_ADD_MESSAGE);
			return UIUtil.redirectPath(ACCOUNT_CLOSER_SUCCESS_PATH);
		}
		String id = request.getParameter(ID);
		if(id != null){
			accountCloserForm.setAcountCloserId(Long.parseLong(id));
		}
		return getView(session, ADD_MESSAGE_REQUEST_VIEW);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addmessage", method = RequestMethod.POST)
	public String addMessageSave(@Valid AccountCloserForm accountCloserForm, BindingResult result,
			Map model, HttpServletRequest request, Locale locale){
		LOGGER.debug(" addMessageSave ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		
		try{
			Integer disputeCount = null;
			if(CUSTOMER_USER_TYPE.equals((String)session.getAttribute(USER_TYPE))){
				disputeCount =  disputeService.getDisputeCountForAccClose((Long)session.getAttribute(AUTHENTICATION_ID));
			}else{
				disputeCount =  disputeService.getActiveDisputeForMerchant((Long)session.getAttribute(AUTHENTICATION_ID));
			}
		
			if(disputeCount > 0){
				result.rejectValue(AccountCloserValidator.ACCOUNTCLOSE_MESSAGE_VAR, CommonConstrain.UNABLE_TOCLOSE_ACCOUNT_DISPUTE);
				return getView(session, CREATE_AC_REQUEST_VIEW);
			}
		} catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(ERROR_MSG_ACCOUNT_CLOSER, null, locale));
			return getView(session, CREATE_AC_REQUEST_VIEW);
		}
		
		AccountCloserValidator accountCloseValidator = new AccountCloserValidator();
		accountCloseValidator.validate(accountCloserForm, result);
		if(result.hasErrors()){
			return getView(session, ADD_MESSAGE_REQUEST_VIEW); 
		} else {
			try {
				String userType = (String)session.getAttribute(USER_TYPE);
				accountCloserService.addMessage(accountCloserForm.getAcountCloserId(), accountCloserForm.getMessage(), UIUtil.getUserTypeID(userType));
				session.setAttribute(SUCCESS_MESSAGE, context.getMessage(SUCCESS_ADDMSG_ACCOUNT_CLOSER, null, locale));
				return UIUtil.redirectPath(ACCOUNT_CLOSER_SUCCESS_PATH);
			} catch (WalletException e) {
				LOGGER.error(e.getMessage(), e);
				model.put(ERROR_MESSAGE, context.getMessage(ERRORSENDMSG_ACCOUNT_CLOSER, null, locale));
				return getView(session, ADD_MESSAGE_REQUEST_VIEW);
			}
			
		}
	}
	
	private String getView(HttpSession session, String view){
		String uview = view;
		String uType = (String)session.getAttribute(USER_TYPE);
		
		if(CUSTOMER_USER_TYPE.equals(uType)){
			uview = CUSTOMER_VIEW + view;
		} else if(MERCHANT_USER_TYPE.equals(uType)){
			uview = MERCHANT_VIEW + view;
		}
		return uview;
	}
	
}