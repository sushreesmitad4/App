package com.tarang.ewallet.walletui.controller;

import java.util.Date;
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
import com.tarang.ewallet.dto.FeedbackDto;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.constants.FeedBack;
import com.tarang.ewallet.walletui.form.FeedbackForm;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.FeedbackValidator;



@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("/feedback")
public class FeedbackController implements FeedBack, AttributeConstants, AttributeValueConstants {
	
	private static final Logger LOGGER = Logger.getLogger(FeedbackController.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private FeedbackValidator feedbackValidator;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String feedbackload(HttpServletRequest request, Map model, 
			@Valid FeedbackForm feedbackForm, Locale locale) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		model.put(FEEDBACK_FORM, new FeedbackForm());
		String userType=(String)session.getAttribute(USER_TYPE);
		return papulateViewPage(userType);
	}
  
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public String feedbackSend(HttpServletRequest request,
			Map model, Locale locale,
			@Valid FeedbackForm feedbackForm, BindingResult result) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String userType=(String)session.getAttribute(USER_TYPE);
		String mode = feedbackForm.getMode();
		if(mode != null && CANCEL.equalsIgnoreCase(mode)){
			if(userType.equalsIgnoreCase(GlobalLitterals.CUSTOMER_USER_TYPE)){
				return UIUtil.redirectPath(AttributeValueConstants.CUSTOMER_PATH);
			} else{
				return UIUtil.redirectPath(AttributeValueConstants.MERCHANT_PATH);
			}
		}
		
		feedbackValidator.validate(feedbackForm, result);
		if(result.hasErrors()){
			return papulateViewPage(userType);
		}		
		try{
			Long userId = (Long)session.getAttribute(AUTHENTICATION_ID);
			String userMail = (String)session.getAttribute(USER_ID);
			FeedbackDto feedbackDto = feedbackForm.getFeedbackDto();
			feedbackDto.setUserId(userId);
			feedbackDto.setUserType(userType);
			feedbackDto.setUserMail(userMail);
			feedbackDto.setDateAndTime(new Date());
			commonService.createFeedback(feedbackDto);
			//Audit Trail service
			auditTrailService.createAuditTrail(feedbackDto.getUserId(), AuditTrailConstrain.MODULE_QUERY_FEEDBACK_CREATE, 
					AuditTrailConstrain.STATUS_CREATE, feedbackDto.getUserMail(), feedbackDto.getUserType());			
			model.put(SUCCESS_MESSAGE, context.getMessage(QUERY_SENT_SUCCESS, null, locale));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(QUERY_SENT_FAILED, null, locale));
		}
		model.put(FEEDBACK_FORM , new FeedbackForm());
		return papulateViewPage(userType);
	}
	
	private String papulateViewPage(String userType){
		if(userType.equalsIgnoreCase(GlobalLitterals.CUSTOMER_USER_TYPE)){
			return CUSTOMER_FEEDBACK_PAGE_VIEW;
		} else{
			return MERCHANT_FEEDBACK_PAGE_VIEW;
		}
	}

}
