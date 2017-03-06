package com.tarang.mwallet.rest.services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.dto.FeedbackDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.form.FeedbackForm;
import com.tarang.mwallet.rest.services.model.RestRequest;
import com.tarang.mwallet.rest.services.util.CommonRestServiceHelper;
import com.tarang.mwallet.rest.services.util.CommonWebserviceUtil;
import com.tarang.mwallet.rest.services.util.Constants;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;
import com.tarang.mwallet.rest.services.util.ServerUtility;

/**
 * @author hari.santosh
 *
 */
@Path("/feedback")
public class FeedbackRestService implements AttributeConstants,Constants {

	private static final Logger LOGGER = Logger.getLogger(FeedbackRestService.class);
	
	private AuditTrailService auditTrailService;
	
	private CommonService commonService;
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response feedback(@Context HttpServletRequest request, String feedbackInput) throws WalletException {

		papulateServices(request);
		LOGGER.info(" >>>>> Entering feedback <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER, "feedback", feedbackInput);
		RestRequest restRequest = null;
		Authentication authentication = null;
		Response response = null;
		Boolean exit = false;

		try {

			if (CommonWebserviceUtil.isEmpty(feedbackInput)) {

				response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;
			}

			Gson gson = new Gson();
			restRequest = gson.fromJson(feedbackInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return response;
		}
		finally {
			if (exit) {
				CommonRestServiceHelper.responseLog(LOGGER, "feedback", response);
				LOGGER.info(" >>>>> Exiting feedback <<<<< ");
			}
		}
		LOGGER.info("QueryType :"+restRequest.getQueryType() + USER_EMAIL + ":" + restRequest.getEmail() + USER_TYPE
				+ restRequest.getUserType() + "MsisdnNumber" +restRequest.getMsisdnNumber()
				+ "simnumber" + restRequest.getSimNumber() + "ImeiNumber" + restRequest.getImeiNumber() );
		
		try {
			authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
			/* validate requested user phone number */
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), restRequest.getUserType());
			 response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest,
							phoneNumber, authentication, Boolean.TRUE);
			if (response != null) {
				return response;
			}
			if(restRequest.getQueryType()!= null && restRequest.getQueryType() <= 2 
					&& !restRequest.getQueryType().equals(EMPTY_STRING) && restRequest.getQueryType() > 0){
				FeedbackForm feedbackForm = getFeedbackForm(restRequest);
				FeedbackDto feedbackDto = feedbackForm.getFeedbackDto();
				feedbackDto.setUserId(authentication.getId());
				feedbackDto.setUserType(restRequest.getUserType());
				feedbackDto.setUserMail(restRequest.getEmail());
				feedbackDto.setDateAndTime(new Date());
				commonService.createFeedback(feedbackDto);
				
				auditTrailService.createAuditTrail(feedbackDto.getUserId(), AuditTrailConstrain.MODULE_QUERY_FEEDBACK_CREATE, 
						AuditTrailConstrain.STATUS_CREATE, feedbackDto.getUserMail(), feedbackDto.getUserType());			
				if(restRequest.getQueryType() == 1){
					response = ServerUtility.papulateSuccessCode(request,
							ServerProcessorStatus.QUERY_SENT_SUCCESSFULLY.getValue(), null);
					return response;
				}else{
					response =  ServerUtility.papulateSuccessCode(request,
							ServerProcessorStatus.FEEDBACK_SUBMITTED_SUCCESSFULLY.getValue(), null);
					return response;
				}
				
			}else {
				response = ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.FEEDBACK_QUERY_TYPE_NOT_SUPPORT.getValue()); 
				return response;
			}
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		   response = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.QUERY_SENT_FAILED.getValue());
		   return response;
		}
		finally {
			
				CommonRestServiceHelper.responseLog(LOGGER, "feedback", response);
				LOGGER.info(" >>>>> Exiting feedback <<<<< ");
		
		}
	}
	
	/**
	 * Convert the request object to the actual form object
	 * @param restRequest
	 * @return
	 */
	private FeedbackForm getFeedbackForm(RestRequest restRequest){
		FeedbackForm feedbackForm = new FeedbackForm();
		if(null == restRequest){
			return null;
		}
		feedbackForm.setQuerryType(restRequest.getQueryType());
		feedbackForm.setSubject(restRequest.getSubject());
		feedbackForm.setMessage(restRequest.getMessage());
		
		return feedbackForm;
	}
	
	/**
	 * Get the service instance from context
	 * @param request
	 */
	private void papulateServices(HttpServletRequest request){
		commonService = (CommonService) ServerUtility.getServiceInstance(request.getSession(), COMMON_SERVICE);
		auditTrailService = (AuditTrailService) ServerUtility.getServiceInstance(request.getSession(), AUDIT_TRAIL_SERVICE);
	}
}
