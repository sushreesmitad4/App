package com.tarang.mwallet.rest.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.sms.dto.OtpDto;
import com.tarang.ewallet.sms.service.SmsService;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.controller.constants.Login;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.webservice.validation.RestCustomValidation;
import com.tarang.mwallet.rest.services.model.RestRequest;
import com.tarang.mwallet.rest.services.util.CommonRestServiceHelper;
import com.tarang.mwallet.rest.services.util.CommonWebserviceUtil;
import com.tarang.mwallet.rest.services.util.Constants;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;
import com.tarang.mwallet.rest.services.util.ServerUtility;

/**
 * @author kedarnathd This service will publish as rest full access for OTP
 *         functionality of the wallet system.
 */

@Path("/otpservice")
public class OTPNotificationService implements Login, AttributeConstants, AttributeValueConstants, Constants, Common {

	private static final Logger LOGGER = Logger.getLogger(OTPNotificationService.class);

	private CommonService commonService;

	private CustomerService customerService;

	private AuditTrailService auditTrailService;

	private SmsService smsService;

	private UtilService utilService;

	@Path("/sendOTP")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendOTP(@Context HttpServletRequest request, String otpInput) throws WalletException {

		papulateServices(request);
		Response responce = null;
		RestRequest restRequest = null;
		CustomerDto customerDto = null;
		Authentication authentication = null;
		LOGGER.info(" >>>>> Entering sendOTP <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"sendOTP",otpInput);
		Boolean exit = false;

		try {
			if (CommonWebserviceUtil.isEmpty(otpInput)) {
				responce = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(otpInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"sendOTP",responce);
				LOGGER.info(" >>>>> sendOTP <<<<< ");
			}			
		}

		try {
			LOGGER.info(USER_EMAIL + ":" + restRequest.getEmail() + USER_TYPE + " :" + restRequest.getUserType());
			customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			if (null == customerDto) {
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			authentication = commonService.getAuthentication(customerDto.getEmailId(),
					GlobalLitterals.CUSTOMER_USER_TYPE);
			if (null == authentication) {
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			/* Skip phone number check, so passed value as null */
			responce = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, null, authentication,
					Boolean.FALSE);
			if (responce != null) {
				return responce;
			}
			if (null == authentication.getSimNumber() && null == authentication.getImeiNumber()) {
				throw new WalletException(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET);
			}

			LOGGER.info("Email :" + authentication.getEmailId() + "UserType : " + authentication.getUserType());

			OtpDto otpDto = getOtpDto(restRequest);
			OtpDto otp = new OtpDto();
			//otp.setMessage(message);
			otp = smsService.sendOTP(otpDto);
			
			auditTrailService.createAuditTrail(customerDto.getId(),
					AuditTrailConstrain.MODULE_OTP_NOTIFICATION_CREATE, AuditTrailConstrain.STATUS_CREATE,
					authentication.getEmailId(), authentication.getUserType());

			responce = ServerUtility.papulateSuccessCode(request,
					ServerProcessorStatus.SUCCESSFULLY_OTP_SENT_TO_DEVICE.getValue(), otp);
			return responce;

		} catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, CommonWebserviceUtil.papulateErrorMessage(ex));
			return responce;
			
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"sendOTP",responce);
			LOGGER.info(" >>>>> Exiting sendOTP <<<<< ");
		}
	}

	/*@Path("/sendregistrationOTP")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendregistrationOTP(@Context HttpServletRequest request, String otpInput) throws WalletException {

		papulateServices(request);
		LOGGER.info(" Entering sendregistrationOTP.... ");
		RestRequest restRequest = null;
		if (CommonWebserviceUtil.isEmpty(otpInput)) {
			return ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
		}
		try {
			Gson gson = new Gson();
			restRequest = gson.fromJson(otpInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
		}

		LOGGER.info(USER_EMAIL + ":" + restRequest.getEmail() + USER_TYPE + " :" + restRequest.getUserType());
		

		 Validate OTP 
		if (RestCustomValidation.numberValidator(restRequest.getOTP(), null, utilService.getOtpLength())) {
			return ServerUtility.papulateErrorCode(request, ServerProcessorStatus.INVALID_OTP_FORMAT.getValue());
		}

		Boolean isValidOtp = smsService.authenticateOTP(CommonRestServiceHelper.getOtpDto(restRequest, null));
		if (isValidOtp) {
			CustomerDto customerDto = new CustomerDto();
			customerDto.setEmailId(restRequest.getEmail());
			customerDto.setPassword("aaaa1A@");
			customerDto.setHintQuestion1(restRequest.getQuestion());
			customerDto.setAnswer1(restRequest.getAnswer());
			customerDto.setPhoneCode(restRequest.getPhoneCode());
			customerDto.setPhoneNo(restRequest.getPhoneNo());
			customerDto.setKycRequired(false);
			customerDto.setLanguageId(restRequest.getLanguageId());// ENG
			customerDto.setDefaultCurrency(restRequest.getDefaultCurrency());// INR
			customerDto.setMsisdnNumber(restRequest.getMsisdnNumber());
			customerDto.setSimNumber(restRequest.getSimNumber());
			customerDto.setImeiNumber(restRequest.getImeiNumber());
			customerDto.setmPin(restRequest.getmPin());
			CustomerDto customerDto1 = null;
			
			customerDto1 = getUpdatedCustomer(customerService.mobileNewRegistration(customerDto));
			
			// Audit Trail service
			auditTrailService.createAuditTrail(customerDto1.getAuthenticationId(),
					AuditTrailConstrain.MODULE_CUSTOMER_REGISTRATION, AuditTrailConstrain.STATUS_REGISTRATION,
					customerDto1.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			// to set default preferences
			PreferencesDto preferencesDto = new PreferencesDto();
			preferencesDto.setCurrency(CommonConstrain.DEFAULT_CURRENCY);
			preferencesDto.setLanguage(CommonConstrain.DEFAULT_LANGUAGE);
			preferencesDto.setAuthentication(customerDto1.getAuthenticationId());
			commonService.createPreferences(preferencesDto);
			Boolean flag = Boolean.TRUE;
			if (!flag) {
				return ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.UNABLE_TO_REGISTERED_YOUR_MOBILE_WALLET.getValue());
			}
			return ServerUtility.papulateSuccessCode(request,
					ServerProcessorStatus.SUCCESSFULLY_REGISTERED_WITH_DEVICE.getValue(), null);
		} else {
			return ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.INVALID_OTP_OR_EXPIRED_PLEASE_TRY_AGAIN.getValue());
		}
	}*/

	/**
	 * Get the service instance from context
	 * 
	 * @param request
	 */
	private void papulateServices(HttpServletRequest request) {
		HttpSession oldsession = request.getSession();
		if (oldsession != null) {
			oldsession.invalidate();
		}
		HttpSession session = request.getSession(true);
		commonService = (CommonService) ServerUtility.getServiceInstance(session, COMMON_SERVICE);
		smsService = (SmsService) ServerUtility.getServiceInstance(session, SMS_SERVICE);
		customerService = (CustomerService) ServerUtility.getServiceInstance(session, CUSTOMER_SERVICE);
		// merchantService = (MerchantService)
		// ServerUtility.getServiceInstance(session, MERCHANT_SERVICE);
		auditTrailService = (AuditTrailService) ServerUtility.getServiceInstance(session, AUDIT_TRAIL_SERVICE);
		utilService = (UtilService) ServerUtility.getServiceInstance(request.getSession(), UTIL_SERVICE);
	}

	/*private CustomerDto getUpdatedCustomer(CustomerDto CustomerDto) {
		CustomerDto newdto = new CustomerDto();
		newdto.setPhoneCode(CustomerDto.getPhoneCode());
		newdto.setPhoneNo(CustomerDto.getPhoneNo());
		newdto.setId(CustomerDto.getId());
		newdto.setFirstName(CustomerDto.getFirstName());
		newdto.setLastName(CustomerDto.getLastName());
		newdto.setEmailId(CustomerDto.getEmailId());
		newdto.setAuthenticationId(CustomerDto.getAuthenticationId());
		return newdto;
	}*/

	@Path("/resendOTP")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response resendOTP(@Context HttpServletRequest request, String otpInput) throws WalletException {
		
		Response responce = null;
		LOGGER.info(" >>>>> Entering resendOTP <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"resendOTP",otpInput);
		Boolean exit = false;
		papulateServices(request);
		RestRequest restRequest = null;			
		
		try {
			if (CommonWebserviceUtil.isEmpty(otpInput)) {
				responce = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(otpInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"resendOTP",responce);
				LOGGER.info(" >>>>> Exiting resendOTP <<<<< ");
			}			
		}

		try {
			LOGGER.info(USER_EMAIL + ":" + restRequest.getEmail() + USER_TYPE + " :" + restRequest.getUserType());
			OtpDto otpDto = getOtpDto(restRequest);
			otpDto.setCustomerId(null);
			OtpDto otp = new OtpDto();
			otp.setOtpNumber(smsService.sendOTP(otpDto).getOtpNumber());
			/*auditTrailService.createAuditTrail(otpDto.getCustomerId(),
					AuditTrailConstrain.MODULE_OTP_NOTIFICATION_CREATE, AuditTrailConstrain.STATUS_CREATE,
					authentication.getEmailId(), authentication.getUserType());*/
			responce = ServerUtility.papulateSuccessCode(request,
					ServerProcessorStatus.SUCCESSFULLY_OTP_SENT_TO_DEVICE.getValue(), otp);
			return responce;

		} catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, CommonWebserviceUtil.papulateErrorMessage(ex));
			return responce;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"resendOTP",responce);
			LOGGER.info(" >>>>> Exiting resendOTP <<<<< ");
		}
	}

	private OtpDto getOtpDto(RestRequest restRequest) {
		OtpDto otpDto = new OtpDto();
		otpDto.setMobileCode(restRequest.getPhoneCode());
		otpDto.setMobileNumber(restRequest.getPhoneNo());
		otpDto.setEmailId(restRequest.getEmail());
		otpDto.setOtpModuleName(restRequest.getOtpModuleName());
		otpDto.setMessage(restRequest.getMessage());
		return otpDto;

	}
}
