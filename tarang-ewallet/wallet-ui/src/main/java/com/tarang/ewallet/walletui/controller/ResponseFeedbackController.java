package com.tarang.ewallet.walletui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.usermgmt.business.UserService;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.constants.FeedBack;
import com.tarang.ewallet.walletui.form.FeedbackForm;
import com.tarang.ewallet.walletui.util.FeedbackList;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.ResponseFeedbackUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.FeedbackValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("/responsefeedback")
public class ResponseFeedbackController implements FeedBack,AttributeConstants, AttributeValueConstants, GlobalLitterals{
        
	private static final Logger LOGGER = Logger.getLogger(ResponseFeedbackController.class);
	
	@Autowired
	private ApplicationContext context;
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MerchantService merchantService;
		
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private FeedbackValidator feedbackValidator;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@RequestMapping(value = "/feedbacklist", method = RequestMethod.GET)
	public String feedbackload(HttpServletRequest request, Map model, @Valid FeedbackForm feedbackForm, Locale locale) {
		
		LOGGER.debug("feedback load");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
                MenuConstants.QUERY_FEEDBACK, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		String url = WALLET_PATH_PREFIX + "responsefeedback/feedbackrecords";
		request.setAttribute("urlFeedbackList", url);
		return FeedBack.ADMIN_RESPONSE_FEEDBACK_LIST;
		
	}
	
	@RequestMapping(value = "/feedbackrecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<FeedbackList> accountRecords(Map model, Locale locale, HttpServletRequest request) {

		List<FeedbackList> feedbackLists = new ArrayList<FeedbackList>();
		HttpSession session = request.getSession();
		JqgridResponse<FeedbackList> response = new JqgridResponse<FeedbackList>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.QUERY_FEEDBACK, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return response;	
	    }
		try {
		
			List<Feedback> list = userService.loadFeedbackDetails(null);
			feedbackLists = ResponseFeedbackUtil.getFeedbackList(list,request);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		response.setRows(feedbackLists);
		int n = feedbackLists.size() / PAGE_SIZE;
		if( feedbackLists.size() / PAGE_SIZE * PAGE_SIZE != feedbackLists.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String feedbackView(HttpServletRequest request, Map model, FeedbackForm feedbackForm, Locale locale) throws WalletException {
		
		LOGGER.debug("feedback View");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
                MenuConstants.QUERY_FEEDBACK, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }

		String id = request.getParameter(ID);
		Long id1 = null;
		if(id != null){
			id1 = Long.parseLong(id);
		}
		List<Feedback> feedbackList = null;
		try {
			feedbackList = userService.loadFeedback(id1);
            Feedback feedback = feedbackList.get(0);
            convertFeedbackToFeedbackForm(feedback, feedbackForm,request);
            
            feedbackList = userService.loadFeedbackDetails(id1);
            
            Iterator itr= feedbackList.iterator();
            while(itr.hasNext()){
            	feedback = (Feedback)itr.next();
            	Long responseSendorId = feedback.getResponseSender();
            	feedback.setResponseSenderMailId(commonService.getAuthentication(responseSendorId).getEmailId());
            	
            	feedback.setResponseDateAsString(DateConvertion.dateToString(feedback.getResponseDate()));
            
            }
		}  catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		model.put("feedbackresponses", feedbackList);	
		return FeedBack.ADMIN_RESPONSE_FEEDBACK;
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String feedbackReply(HttpServletRequest request, Map model, FeedbackForm feedbackForm, Locale locale) throws WalletException {
		
		LOGGER.debug("feedbackReply");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
                MenuConstants.QUERY_FEEDBACK, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }

		String id = request.getParameter(ID);
		Long id1 = null;
		if(id != null){
			id1 = Long.parseLong(id);
		}
		List<Feedback> feedbackList = null;
		try {
			feedbackList = userService.loadFeedback(id1);
            Feedback feedback = feedbackList.get(0);
            convertFeedbackToFeedbackForm(feedback, feedbackForm,request);
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return FeedBack.ADMIN_FEEDBACK_REPLY;
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String sendFeedbackReply(HttpServletRequest request, Map model, Locale locale,  
			FeedbackForm feedbackForm,BindingResult result ) throws WalletException {
		
		LOGGER.debug("sendFeedbackReply");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS), 
                MenuConstants.QUERY_FEEDBACK, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	          session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	          return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);
	    }
		String id = request.getParameter(ID);
		Long id1 = null;
		if(id != null){
			id1 = Long.parseLong(id);
		}
		List<Feedback> feedbackList = null;
		try {
			feedbackList = userService.loadFeedback(id1);
            Feedback feedback = feedbackList.get(0);
            Long authId = (Long)session.getAttribute("authenticationId");
            Long responseSenderId = commonService.getAuthentication(authId).getId();
            
            feedback.setResponseDate(new Date());
            feedback.setResponseSender(responseSenderId);
            feedback.setResponseMessage(feedbackForm.getResponseMessage());
            feedback.setParentId(id1);
            //inputs for email template
            feedback.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
            feedback.setQueryTypeName(feedbackForm.getQueryTypeName());
            String name = null;
            if(feedback.getUserType().equals(CUSTOMER_USER_TYPE)){
            	name = customerService.getPersonName(feedback.getUserMail(), feedback.getUserType());
            	
            } else{
            	name = merchantService.getLegalName(feedback.getUserMail());
            }
            feedback.setReciverName(name);
           
           feedbackValidator.validate(feedbackForm, result);
   		   if(result.hasErrors()){
   			   return FeedBack.ADMIN_FEEDBACK_REPLY;
   		   }
   		   userService.createFeedbackReply(feedback); 
   		   //Audit Trail service
   		   auditTrailService.createAuditTrail(feedback.getResponseSender(), AuditTrailConstrain.MODULE_QUERY_FEEDBACK_REPLY, 
				AuditTrailConstrain.STATUS_UPDATE, session.getAttribute(USER_ID).toString(), GlobalLitterals.ADMIN_USER_TYPE);
			
   		   //to update parent feedback
   		  feedbackList = userService.loadFeedback(id1);
   		  feedback = feedbackList.get(0);
   		  feedback.setResponseDate(new Date());
   		  userService.updateFeedback(feedback);
   		  
   		  model.put(SUCCESS_MESSAGE, context.getMessage(REPLY_SEND_SUCCESS, null, locale));
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(REPLY_SEND_FAILED, null, locale));
		}
		return FeedBack.ADMIN_FEEDBACK_REPLY;
	}
	
	private void convertFeedbackToFeedbackForm(Feedback feedback, FeedbackForm feedbackForm,HttpServletRequest request) {
		feedbackForm.setId(feedback.getId());
		feedbackForm.setQuerryType(feedback.getQuerryType());
		Map<Long, String> qf = MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.QUERY_OR_FEEDBACK);
		feedbackForm.setQueryTypeName(qf.get(feedback.getQuerryType()));
		feedbackForm.setSubject(feedback.getSubject());
		feedbackForm.setMessage(feedback.getMessage());
		feedbackForm.setParentId(feedback.getParentId());
		feedbackForm.setUserType(feedback.getUserType());
		Map<Long, String> userTypeName = MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.MD_USER_TYPES);
		if(feedback.getUserType().equals(CUSTOMER_USER_TYPE)){
			feedbackForm.setUserTypeName(userTypeName.get(CUSTOMER_USER_TYPE_ID));
		} else if(feedback.getUserType().equals(MERCHANT_USER_TYPE)){
			feedbackForm.setUserTypeName(userTypeName.get(MERCHANT_USER_TYPE_ID));
		}
		feedbackForm.setUserMail(feedback.getUserMail());
		feedbackForm.setResponseSender(feedback.getResponseSender());
		feedbackForm.setDateAndTime(DateConvertion.dateToString(feedback.getDateAndTime()));
		feedbackForm.setResponseMessage(feedback.getResponseMessage());
		feedbackForm.setResponseDate(feedback.getResponseDate());
	}
}
