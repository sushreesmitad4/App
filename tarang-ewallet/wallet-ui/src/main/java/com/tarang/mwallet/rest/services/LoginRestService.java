/**
 * 
 */
package com.tarang.mwallet.rest.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import com.tarang.ewallet.common.business.LoginService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.scheduler.business.SchedulerService;
import com.tarang.ewallet.scheduler.util.JobConstants;
import com.tarang.ewallet.scheduler.util.SchedulerGroupNames;
import com.tarang.ewallet.sms.dto.OtpDto;
import com.tarang.ewallet.sms.service.SmsService;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.controller.constants.Login;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.webservice.validation.RestCustomValidation;
import com.tarang.mwallet.rest.services.model.RestRequest;
import com.tarang.mwallet.rest.services.util.CommonRestServiceHelper;
import com.tarang.mwallet.rest.services.util.CommonWebserviceUtil;
import com.tarang.mwallet.rest.services.util.Constants;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;
import com.tarang.mwallet.rest.services.util.ServerUtility;

/**
 * @author kedarnathd
 * This service will publish as rest full access for login and 
 * logout functionality of the wallet system.
 */

@Path("/devicehome")
public class LoginRestService implements Login, AttributeConstants, AttributeValueConstants, Constants, Common{
	
	private static final Logger LOGGER = Logger.getLogger(LoginRestService.class);
	
	private LoginService loginService;
	
	private CommonService commonService;
	
	private CustomerService customerService;
	
	private MerchantService merchantService;
	
	private SchedulerService schedulerService;
	
	private CryptService cryptService;
	
	private AuditTrailService auditTrailService;
	
	private SmsService smsService;
	
	private UtilService utilService;
	
	
	@Path("/activate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateDevice(@Context HttpServletRequest request, String loginInput) throws WalletException {
		
		papulateServices(request);
		LOGGER.info(" >>>>> Entering activateDevice <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"Activate Device",loginInput);
		Response responce = null;
		RestRequest restRequest = null;
		Authentication authentication = null;
		CustomerDto customerDto = null;
		Boolean exit = false;
		
		
		try{	
			if(CommonWebserviceUtil.isEmpty(loginInput)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());	
				exit = true;
				return responce;
			}
			
			Gson gson = new Gson();
			restRequest = gson.fromJson(loginInput, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());		
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"Activate Device",responce);
				LOGGER.info(" >>>>> Exiting activateDevice <<<<< ");
			}			
		}
		
		/*responce = mandatoryFieldsCheck(request, restRequest);
		if(responce != null){
			return responce;
		}*/
		
		try {
			LOGGER.info(USER_EMAIL + ":" + restRequest.getEmail() + USER_TYPE +" :" + restRequest.getUserType());
			customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			if(null == customerDto){
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			authentication = commonService.getAuthentication(customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			if(null == authentication){
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			restRequest.setEmail(authentication.getEmailId());
			restRequest.setUserType(authentication.getUserType());
			/*Skip phone number check, so passed value as null*/
			/* so passed as false to skip login validation */ 
			responce = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					null, authentication, Boolean.FALSE);
			if(responce != null){				
				return responce;
			}else{
				/* Device has changed/Login request from different device */
			  	if(!checkDeviceActiveStatus(authentication, restRequest)){
			  		/*Validate OTP*/
					if(RestCustomValidation.numberValidator(restRequest.getOTP(), null , utilService.getOtpLength())){
						responce = ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.INVALID_OTP_FORMAT.getValue());
						return responce;
					}
					restRequest.setEmail(customerDto.getEmailId());
					//TODO need to uncomment after implementation
					//Boolean isValidOtp = smsService.authenticateOTP(CommonRestServiceHelper.getOtpDto(restRequest, customerDto.getId()));
					Boolean isValidOtp = true;
					if(isValidOtp){
						Boolean flag = commonService.activateMobileRegistration(restRequest.getEmail(), restRequest.getUserType(), 
								restRequest.getmPin(), restRequest.getMsisdnNumber(), 
								restRequest.getSimNumber(), restRequest.getImeiNumber());
						if(!flag){
							responce = ServerUtility.papulateErrorCode(request, 
									ServerProcessorStatus.UNABLE_TO_REGISTERED_YOUR_MOBILE_WALLET.getValue());			
							return responce;
						}
						responce = ServerUtility.papulateSuccessCode(request,
								ServerProcessorStatus.SUCCESSFULLY_REGISTERED_WITH_DEVICE.getValue(), null);		
						return responce;
					}
			  	}
			  	
			  	responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.ALREADY_REGISTOR_AS_MOBILE_WALLET.getValue());
			  	
				return responce;
			  }
		} catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					CommonWebserviceUtil.papulateErrorMessage(ex));		
			return responce;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"Activate Device",responce);
			LOGGER.info(" >>>>> Exiting activateDevice <<<<< ");
		}
	}
	
	@Path("/customervalidate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Customervalidate(@Context HttpServletRequest request, String loginInput) throws WalletException {
		
		papulateServices(request);
		LOGGER.info(" >>>>> Entering Customervalidate <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"Customer Validate",loginInput);
		Response response = null;
		RestRequest restRequest = null;
		Authentication authentication = null;
		Boolean exit = false;
		
		try{
			if(CommonWebserviceUtil.isEmpty(loginInput)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(loginInput, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());	
			exit = true;
			return response;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"Customer Validate",response);
				LOGGER.info(" >>>>> Exiting Customervalidate <<<<< ");
			}
			
		}
		
		
		try{
			
			LOGGER.info(USER_EMAIL + ":" + restRequest.getEmail() + USER_TYPE +" :" + restRequest.getUserType());
			String srtResponse = RestCustomValidation.checkFieldInput(restRequest, CUSTOMER_REGISTRATION_SERVICE);
			if(srtResponse != null){
				response = ServerUtility.papulateErrorCode(request, srtResponse);
				return response;
			}
			
			if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
				response =  ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());
				return response;
			}
			
			
			/*Validate Mobile Number*/
			authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
			Boolean isPhoneExist = commonService.phoneNOExist(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			if(isPhoneExist && authentication != null){
				response =  ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.CUSTOMER_MOBILE_AND_EMAIL_EXIST.getValue());			
				return response;
			}else if(isPhoneExist){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.CUSTOMER_MOBILE_EXIST.getValue());				
				return response;
			}else if(authentication != null){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.CUSTOMER_EMAIL_EXIST.getValue());				
				return response;
			}
			/*Send OTP now*/
			OtpDto otpDto = new OtpDto(); 
			
			otpDto.setMobileCode(restRequest.getPhoneCode());
			otpDto.setMobileNumber(restRequest.getPhoneNo());
			otpDto.setCustomerId(null);
			otpDto.setEmailId(restRequest.getEmail());
			otpDto.setOtpModuleName(restRequest.getOtpModuleName());
			otpDto.setMessage(restRequest.getMessage());
						
			OtpDto otp = new OtpDto();
			otp = smsService.sendOTP(otpDto);
			
			response = ServerUtility.papulateSuccessCode(request,
					ServerProcessorStatus.SUCCESSFULLY_OTP_SENT_TO_DEVICE.getValue(), otp);	
			return response;
			
		} catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					CommonWebserviceUtil.papulateErrorMessage(ex));			
			return response;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"Customer Validate",response);
			LOGGER.info(" >>>>> Exiting Customervalidate <<<<< ");
		}
	}
	
	
	
	
	@Path("/customerregistration")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerRegistration(@Context HttpServletRequest request, String loginInput) throws WalletException {
		
		papulateServices(request);
		LOGGER.info(" >>>>> Entering customerRegistration <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"Customer Registration",loginInput);
		
		String bypassSmsGateway = "Y";
		RestRequest restRequest = null;
		Authentication authentication = null;
		Response response = null;
		Boolean exit = false;
		
		try{
			if(CommonWebserviceUtil.isEmpty(loginInput)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());		
				exit = true;
				return response;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(loginInput, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return response;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"Customer Registration",response);
				LOGGER.info(" >>>>> Exiting customerRegistration <<<<< ");
			}
			
		}
		
		
		
		
		try{
			LOGGER.info(USER_EMAIL + ":" + restRequest.getEmail() + USER_TYPE +" :" + restRequest.getUserType());
			
			if("REGFLOW1".equalsIgnoreCase(restRequest.getRequestFor())){
				String srtResponse = RestCustomValidation.checkFieldInput(restRequest, CUSTOMER_REGISTRATION_SERVICE);
				if(srtResponse != null){
					response = ServerUtility.papulateErrorCode(request, srtResponse);					
					return response;
				}
				
				
				if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
					response = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());					
					return response;
				}
				
				
				/*Validate Mobile Number*/
				authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
				Boolean isPhoneExist = commonService.phoneNOExist(restRequest.getPhoneCode(), restRequest.getPhoneNo());
				if(isPhoneExist && authentication != null){
					Boolean isAleryRegisterWithDevice = CommonUtil.checkUserHasRequestedFromRegisterMobileWallet(authentication, restRequest.getMsisdnNumber(), 
							restRequest.getSimNumber(), restRequest.getImeiNumber());
					if(isAleryRegisterWithDevice){
						response = ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.ALREADY_REGISTOR_AS_MOBILE_WALLET.getValue());
						return response;
					}else{
						response = ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.CUSTOMER_MOBILE_AND_EMAIL_EXIST.getValue());						
						return response;
					}
				}else if(isPhoneExist){
					response = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.CUSTOMER_MOBILE_EXIST.getValue());					
					return response;
				}else if(authentication != null){
					response = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.CUSTOMER_EMAIL_EXIST.getValue());					
					return response;
				}				
				
				
				OtpDto otpDto = new OtpDto(); 
				
				otpDto.setMobileCode(restRequest.getPhoneCode());
				otpDto.setMobileNumber(restRequest.getPhoneNo());
				otpDto.setCustomerId(null);
				otpDto.setEmailId(restRequest.getEmail());
				otpDto.setOtpModuleName(restRequest.getOtpModuleName());
				otpDto.setMessage(restRequest.getMessage());
							
				OtpDto otp = new OtpDto();
				otp.setOtpNumber(smsService.sendOTP(otpDto).getOtpNumber());
				
				response = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.SUCCESSFULLY_OTP_SENT_TO_DEVICE.getValue(), otp);			
				return response;	
				
				
			}else{
				
				bypassSmsGateway = utilService.getSmsGatewayBypass();
				Boolean isValidOtp = false;
				
				if("N".equalsIgnoreCase(bypassSmsGateway)){
					if(RestCustomValidation.numberValidator(restRequest.getOTP(), null , utilService.getOtpLength())){
						response = ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.INVALID_OTP_FORMAT.getValue());						
						return response;
					}
					
					isValidOtp = smsService.authenticateOTP(CommonRestServiceHelper.getOtpDto(restRequest, null));
				}else{
					
					isValidOtp = true;
				}
				
				
				if(isValidOtp){
					CustomerDto customerDto = new CustomerDto();
					customerDto.setEmailId(restRequest.getEmail());
					customerDto.setPassword("aaaa1A@");
					customerDto.setHintQuestion1(restRequest.getQuestion());
					customerDto.setAnswer1(restRequest.getAnswer());
					customerDto.setPhoneCode(restRequest.getPhoneCode());
					customerDto.setPhoneNo(restRequest.getPhoneNo());
					customerDto.setKycRequired(false);
					customerDto.setLanguageId(restRequest.getLanguageId());//ENG
					customerDto.setDefaultCurrency(restRequest.getDefaultCurrency());//INR
					customerDto.setMsisdnNumber(restRequest.getMsisdnNumber());
					customerDto.setSimNumber(restRequest.getSimNumber());
					customerDto.setImeiNumber(restRequest.getImeiNumber());
					customerDto.setmPin(restRequest.getmPin());
					CustomerDto customerDto1 = null;
					/*if(mode != null && mode.equalsIgnoreCase(REQUEST_FOR_REGISTRATION_AFTER_24HOURS)){
						Note: User is requested for registration after 24hours from his/her first time registration, 
						 * then the system will update the existing record including IP address, password and creation date
						Long customerId = customerService.getCustomerId(customerRegFormTwo.getEmailId(), CUSTOMER_USER_TYPE);
						CustomerDto customerDtoCopy = customerService.getCustomerDto(customerId);
						customerDto1 = customerService.registrationAfter24hours(customerDto);
						auditTrailService.createAuditTrail(customerDto.getAuthenticationId(), 
								AuditTrailConstrain.MODULE_CUSTOMER_REGISTRATION_AFTER_24HOURS, AuditTrailConstrain.STATUS_UPDATE, 
								customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, 
								customerDtoCopy, customerDto1);
					} else{*/
					customerDto1 = getUpdatedCustomer(customerService.mobileNewRegistration(customerDto));
					//call transaction update method for non register person txn records.
					/*	List<NonRegisterWallet> nonRegWallets = commonService.getMoneyFromTemporaryWallet(customerDto.getEmailId());
						if(nonRegWallets != null){
							for(NonRegisterWallet nonRegWallet: nonRegWallets){
								Date date = nonRegWallet.getCreationDate();
								int nonRegWalletExpDays = utilService.getCancelNonregWalletTxnsAllowedDays();
								Date nonWalletExpireDate = DateConvertion.futureDate(date, nonRegWalletExpDays);
								customerDto.setCreationDate(new Date());
								if(customerDto.getCreationDate().compareTo(nonWalletExpireDate) <= GlobalLitterals.ZERO_INTEGER 
										&& !nonRegWallet.getRegister().equals(WalletTransactionStatus.CANCEL)){
									nonRegWallet.setRegister(WalletTransactionStatus.SUCCESS);
									commonService.updateTemporaryWalletRecord(nonRegWallet);
									sendMoneyService.updateSendMoneyForNonRegisters(nonRegWallet.getTxnId(), customerDto1.getAuthenticationId());
								}
							}
						}*/
					//}//else block
					//Audit Trail service
					auditTrailService.createAuditTrail(customerDto1.getAuthenticationId(), AuditTrailConstrain.MODULE_CUSTOMER_REGISTRATION, 
							AuditTrailConstrain.STATUS_REGISTRATION, customerDto1.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
					// to set default preferences
					PreferencesDto preferencesDto = new PreferencesDto();
				    preferencesDto.setCurrency(CommonConstrain.DEFAULT_CURRENCY);
				    preferencesDto.setLanguage(CommonConstrain.DEFAULT_LANGUAGE);
					preferencesDto.setAuthentication(customerDto1.getAuthenticationId());
					commonService.createPreferences(preferencesDto);
					Boolean flag = Boolean.TRUE;
					if(!flag){
						response = ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.UNABLE_TO_REGISTERED_YOUR_MOBILE_WALLET.getValue());
						return response;
					}
					
					response = ServerUtility.papulateSuccessCode(request,
							ServerProcessorStatus.SUCCESSFULLY_REGISTERED_WITH_DEVICE.getValue(), null);
					return response;
				}else{
					response = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.INVALID_OTP_OR_EXPIRED_PLEASE_TRY_AGAIN.getValue());
					return response; 
				}
				
			}
			
			
			
			
		} catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					CommonWebserviceUtil.papulateErrorMessage(ex));			
			return response;
		}finally {
			CommonRestServiceHelper.responseLog(LOGGER,"Customer Registration",response);
			LOGGER.info(" >>>>> Exiting customerRegistration <<<<< ");
		}
	}
	
	
	
	
	
	
	
	@Path("/mpingeneration")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response mPinGeneration(@Context HttpServletRequest request, String loginInput) throws WalletException {
		
		
		LOGGER.info(" >>>>> Entering mPinGeneration <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"MPIN Generation",loginInput);
		
		Response response = null;
		RestRequest restRequest = null;
		Authentication authentication = null;
		Boolean exit = false;
		
		
		try{
			papulateServices(request);
			if(CommonWebserviceUtil.isEmpty(loginInput)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(loginInput, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());	
			exit = true;	
			return response;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"MPIN Generation",response);
				LOGGER.info(" >>>>> Exiting mPinGeneration <<<<< ");
			}
			
		}
		
		
		
		try {
			
			response = mandatoryFieldsCheckExcludePassword(request, restRequest);
			if(response != null){
				return response;
			}
			
			LOGGER.info(USER_EMAIL +": " + restRequest.getEmail() + USER_TYPE + " :" + restRequest.getUserType());
			
			if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());				
				return response; 
			}
			authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.FALSE);
			if(response != null){
				return response;
			}else{
				String personName = getPersonName(restRequest.getEmail(), restRequest.getUserType());
				String userTypeName = UIUtil.getUserType(restRequest.getUserType());
				Boolean flag = commonService.mPinGeneration(restRequest.getEmail(), restRequest.getUserType(), restRequest.getMsisdnNumber(),
							restRequest.getSimNumber(), restRequest.getImeiNumber(), restRequest.getmPin(), personName, userTypeName);
				//Audit Trail service
				auditTrailService.createAuditTrail(authentication.getId(), AuditTrailConstrain.MODULE_CUSTOMER_MPIN_GENERATION, 
						AuditTrailConstrain.STATUS_CREATE, authentication.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
				if(!flag){
					response = ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.UNABLE_TO_GENERATE_MPIN.getValue());					
					return response;  
				}
				response = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.SUCCESSFULLY_MPIN_GENERATED.getValue(), null);		
				return response;  
			}
		} catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					CommonWebserviceUtil.papulateErrorMessage(ex));			
			return response; 
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"MPIN Generation",response);	
			LOGGER.info(" >>>>> Exiting mPinGeneration <<<<< ");
		}
	}
	
	
	
	@Path("/logout")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpServletRequest request, String logoutInput) throws IOException {
				
		LOGGER.info(" >>>>> Entering logout <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"Logout",logoutInput);
		
		Response response = null;
		RestRequest restRequest = null;
		Authentication authentication = null;
		Boolean exit = false;
		
		try{
			papulateServices(request);
			if(CommonWebserviceUtil.isEmpty(logoutInput)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;  
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(logoutInput, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());	
			exit = true;
			return response; 
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"Logout",response);
				LOGGER.info(" >>>>> Exiting logout <<<<< ");
			}
			
		}
		
		LOGGER.info(USER_EMAIL +": " + restRequest.getEmail() + USER_TYPE +": " + restRequest.getUserType());
		try {
			authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			}
			Boolean flag = loginService.logout(restRequest.getEmail(), restRequest.getUserType());
			if(flag){
				removeUserDetailsFromSession(request.getSession());
				response = ServerUtility.papulateSuccessCode(request, 
						ServerProcessorStatus.SUCCESSFULLY_LOGOUT.getValue(), null);		
				return response; 
			}else{
				response = ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.FAILED_TO_LOGOUT_FROM_MOBILE_WALLET.getValue());
				return response; 
			}
			
		} catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			response =  ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.FAILED_TO_LOGOUT_FROM_MOBILE_WALLET.getValue());
			return response;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"Logout",response);
			LOGGER.info(" >>>>> Exiting logout <<<<< ");
		}
		
	}
	
	
	
	@Path("/devicelogin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deviceLogin(@Context HttpServletRequest request, String loginInput) throws WalletException {
		
		LOGGER.info(" >>>>> Entering deviceLogin <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"Device Login",loginInput);
		
		Response response = null;
		RestRequest restRequest = null;
		CustomerDto customerDto = null;
		Authentication authentication = null;
		Boolean exit = false;
		
		try{
			papulateServices(request);
			if(CommonWebserviceUtil.isEmpty(loginInput)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;			
				return response;  
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(loginInput, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());	
			exit = true;
			return response; 
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"Device Login",response);
				LOGGER.info(" >>>>> Exiting deviceLogin <<<<< ");
			}			
		}
		
		
		try {
			
			response = mandatoryFieldsCheckForMobilePhone(request, restRequest);
			if(response != null){
				return response;
			}
			
			LOGGER.info("PHONE_CODE :" + restRequest.getPhoneCode() + "PHONE_NUMBER : " + restRequest.getPhoneNo());
			
			/*this check will take mPin should not be empty and it should not be null and it should be four digit number*/
			if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());				
				return response; 
			}
			CustomerDto cdto = new CustomerDto();
			customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			if(null == customerDto){
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			authentication = commonService.getAuthentication(customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			if(null == authentication){
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			restRequest.setEmail(authentication.getEmailId());
			restRequest.setUserType(authentication.getUserType());
			/*Skip phone number check, so passed value as null*/
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					null, authentication, Boolean.FALSE);
			if(response != null){
				return response;
			}
			if(null == authentication.getSimNumber() && null == authentication.getImeiNumber()){
				throw new WalletException(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET);   
			}else if(Boolean.TRUE.equals(authentication.getmPinBlocked())){
				throw new WalletException(CommonConstrain.MOBILE_USER_BLOCK);
			}else if(authentication.getSimNumber().equals(restRequest.getSimNumber())
				    && authentication.getImeiNumber().equals(restRequest.getImeiNumber())){
				LOGGER.info("Email :" + authentication.getEmailId() + "UserType : " + authentication.getUserType());
				Authentication loginAuth = loginService.loginWithDevice(authentication.getEmailId(), 
					restRequest.getmPin(), authentication.getUserType(), 
					MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
							(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.MOBILE.getValue()));
				
				if(Boolean.TRUE.equals(loginAuth.getmPinBlocked())){
					Map<String, String> jobProperties = new HashMap<String, String>();
					jobProperties.put(JobConstants.GROUP_NAME, SchedulerGroupNames.UNBLOCKED_USERS);
					jobProperties.put(JobConstants.USER_JOB_NAME, JobConstants.UNBLOCKED_USER_JOB_NAME);
					jobProperties.put(JobConstants.AUTH_ID, authentication.getId().toString());
					jobProperties.put(JobConstants.PERSON_NAME, getPersonName(restRequest.getEmail(), restRequest.getUserType()));
					jobProperties.put(JobConstants.USER_TYPE_NAME, UIUtil.getUserType(restRequest.getUserType()));
					
			    	schedulerService.scheduleUnblockedUsersNewJob(jobProperties);
			    		throw new WalletException(CommonConstrain.MOBILE_USER_BLOCK);
				}
				adddUserDetailsToSession(request, loginAuth);
				if(customerDto.getProfileComplet())
				{
					cdto.setFirstName(customerDto.getFirstName());
					cdto.setLastName(customerDto.getLastName());
				}
				cdto.setDefaultCurrency(customerDto.getDefaultCurrency());
				cdto.setEmailId(customerDto.getEmailId());
				cdto.setId(customerDto.getId());
				cdto.setLastLogin(customerDto.getLastLogin());
				cdto.setPhoneCode(customerDto.getPhoneCode());
				cdto.setPhoneNo(customerDto.getPhoneNo());
				cdto.setProfileComplet(customerDto.getProfileComplet());
				response = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.USER_AUTHENTICATION_SUCCESS.getValue(), cdto);
				return response;  
			}else{
				/*throw new WalletException(CommonConstrain.USER_REQUESTED_FROM_DIFFERENT_DEVICE_OR_SIM);*/
				cdto = new CustomerDto();
				cdto.setPhoneCode(restRequest.getPhoneCode());
				cdto.setPhoneNo(restRequest.getPhoneNo());
				response = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.USER_REQUESTED_FROM_DIFFERENT_DEVICE_OR_SIM.getValue(), cdto);
				return response; 
			}
		} catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			if(ex.getMessage().contains("phone.number.does.not.exist")){
				response = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.PLEASE_CHECK_YOUR_MOBILE_NUMBER_AND_MPIN.getValue(), customerDto);
				return response; 
			}else{
				response = ServerUtility.papulateErrorCode(request, 
						CommonWebserviceUtil.papulateErrorMessage(ex));				
				return response;  
			}
			
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"Device Login",response);
			LOGGER.info(" >>>>> Exiting deviceLogin <<<<< ");
		}
	}
	
	
	
	
	
	@Path("/devicechangempin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeMPin(@Context HttpServletRequest request, String loginInput) throws WalletException {
		
		LOGGER.info(" >>>>> Entering changeMPin <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"Change MPIN",loginInput);
		
		Response response = null;
		RestRequest restRequest = null;
		Authentication authentication = null;
		Boolean exit = false;
		
		try{
			papulateServices(request);
			if(CommonWebserviceUtil.isEmpty(loginInput)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response; 
			}			
			Gson gson = new Gson();
			restRequest = gson.fromJson(loginInput, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());	
			exit = true;
			return response;  
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"Change MPIN",response);
				LOGGER.info(" >>>>> Exiting changeMPin <<<<< ");
			}
			
		}
		
		
		
		try {
			response = mandatoryFieldsCheckExcludePassword(request, restRequest);
			if(response != null){
				return response;
			}
			
			LOGGER.info(USER_EMAIL +": " + restRequest.getEmail() + USER_TYPE +": " + restRequest.getUserType());
			
			/*this check will take mPin should not be empty and it should not be null and it should be four digit number*/
			
			authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			
			if(response != null){
				return response;
			}
			String personName = getPersonName(restRequest.getEmail(), restRequest.getUserType());
			String userTypeName = UIUtil.getUserType(restRequest.getUserType());
			Boolean flagForChangPin = null;
			if(null != restRequest.getIsNewMPIN() && restRequest.getIsNewMPIN()){
				/*Validate OTP*/
				/*if(RestCustomValidation.numberValidator(restRequest.getOTP(), null , utilService.getOtpLength())){
					return ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.INVALID_OTP_FORMAT.getValue());
				}*/
				restRequest.setEmail(authentication.getEmailId());
				//Boolean isValidOtp = smsService.authenticateOTP(CommonRestServiceHelper.getOtpDto(restRequest, restRequest.getCustomerNumber()));
				//if(isValidOtp){
				if(RestCustomValidation.numberValidator(restRequest.getNewMPin(), null , Common.M_PIN_MAX_LENGHT)){
					response = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());
					return response;  
				}
				
				flagForChangPin = loginService.newMPin(restRequest.getEmail(), restRequest.getUserType(), 
						restRequest.getNewMPin(), personName, userTypeName);
				
				auditTrailService.createAuditTrail(restRequest.getCustomerNumber(), AuditTrailConstrain.MODULE_NEW_MPIN_REQUEST, 
						AuditTrailConstrain.STATUS_UPDATE, authentication.getEmailId(), authentication.getUserType());
				
				/*}else{
					return ServerUtility.papulateSuccessCode(request,
							ServerProcessorStatus.INVALID_OTP_OR_EXPIRED_PLEASE_TRY_AGAIN.getValue(), null);
				}*/
			}else{
				if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT) 
						|| RestCustomValidation.numberValidator(restRequest.getNewMPin(), null , Common.M_PIN_MAX_LENGHT)){
					response = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());					
					return response;  
				}else if(restRequest.getmPin().equals (restRequest.getNewMPin())){	
					response = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.OLDMPIN_NEWMPIN_SHOULDNOT_BE_SAME.getValue());		
					return response; 
				}
				else if(!authentication.getmPin().equalsIgnoreCase(cryptService.encryptData(restRequest.getmPin()))){	
					response = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.OLD_MPIN_INCORRECT.getValue());					
					return response; 
				}
				flagForChangPin = loginService.changeMPin(restRequest.getEmail(), restRequest.getUserType(), 
						restRequest.getmPin(), restRequest.getNewMPin(), personName, userTypeName);
				
				auditTrailService.createAuditTrail(restRequest.getCustomerNumber(), AuditTrailConstrain.MODULE_CHANGE_MPIN_REQUEST, 
						AuditTrailConstrain.STATUS_UPDATE, authentication.getEmailId(), authentication.getUserType());
			}
			if(!flagForChangPin){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.MPIN_MATCH_FAIL.getValue());				
				return response; 
			}
			
			response = ServerUtility.papulateSuccessCode(request,
					ServerProcessorStatus.SUCCESSFULLY_MPIN_CHANGED.getValue(), null);
			return response;  
		}catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					CommonWebserviceUtil.papulateErrorMessage(ex));			
			return response; 
		}finally {
			CommonRestServiceHelper.responseLog(LOGGER,"Change MPIN",response);
			LOGGER.info(" >>>>> Exiting changeMPin <<<<< ");
		}
	}
	
	
	
	
	@Path("/deactivate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deactivateDevice(@Context HttpServletRequest request, String deactivateInput) throws WalletException {
				
		LOGGER.info(" >>>>> Entering deactivateDevice <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"Deactivate Device",deactivateInput);
		
		Response response = null;
		RestRequest restRequest = null;
		Authentication authentication = null;
		Boolean exit = false;
		
		try{
			papulateServices(request);
			if(CommonWebserviceUtil.isEmpty(deactivateInput)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());	
				exit = true;
				return response; 				 
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(deactivateInput, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return response;  
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"Deactivate Device",response);
				LOGGER.info(" >>>>> Exiting deactivateDevice <<<<< ");
			}
			
		}
		
		
		
		try {
			
			response = mandatoryFieldsCheckExcludePassword(request, restRequest);
			if(response != null){
				return response;
			}
			
			LOGGER.info(USER_EMAIL +": " + restRequest.getEmail() + USER_TYPE + ": " + restRequest.getUserType());
			
			if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());
				
				return response;  
			}
			commonService.validateMpin(restRequest.getEmail(), restRequest.getUserType(),restRequest.getmPin());
			authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			}else{
				String personName = getPersonName(restRequest.getEmail(), restRequest.getUserType());
				String userTypeName = UIUtil.getUserType(restRequest.getUserType());
				Boolean flag  = commonService.deActivateDevice(restRequest.getEmail(), restRequest.getUserType(),
						personName, userTypeName);
				if(!flag){
					response = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.UNABLE_TO_DEACTIVATE_YOUR_MOBILE_WALLET.getValue());		
					return response;  
				}
				response = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.SUCCESSFULLY_DEACTIVATED_WITH_DEVICE.getValue(), null);
				return response;  
			}
		}catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					CommonWebserviceUtil.papulateErrorMessage(ex));			
			return response; 
		}finally {
			CommonRestServiceHelper.responseLog(LOGGER,"Deactivate Device",response);
			LOGGER.info(" >>>>> Exiting deactivateDevice <<<<< ");
		}
	}
	
	
	
	/* MPIN will not send to the user as email notification. 
	 * 1. User will enter mobile number & Secrete Q & A
	 * 2. System will validate the inputs
	 * 3. System will send OTP to user
	 * 4. Once OTP validated, mobile will navigate to new MPIN screen 
	 * 5. User will choose new MPIN */
	@Path("/forgotmpin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response forgotMpin(@Context HttpServletRequest request, String forgotMpinInput) throws WalletException {
		
		LOGGER.info(" >>>>> Entering forgotMpin <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"Forgot MPIN",forgotMpinInput);		
		
		Response response = null;
		RestRequest restRequest = null;
		CustomerDto customerDto = null;
		Authentication authentication = null;
		Boolean exit = false;
		
		try{
			papulateServices(request);
			if(CommonWebserviceUtil.isEmpty(forgotMpinInput)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());	
				exit = true;
				return response;  
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(forgotMpinInput, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());	
			exit = true;
			return response;  
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"Forgot MPIN",response);
				LOGGER.info(" >>>>> Exiting forgotMpin <<<<< ");				
			}
			
		}
		
		
		try {
			
			response = mandatoryFieldsCheckForMobilePhone(request, restRequest);
			if(response != null){
				return response;
			}
			
			LOGGER.info("PHONE_CODE :" + restRequest.getPhoneCode() + "PHONE_NUMBER : " + restRequest.getPhoneNo());
						
			customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			if(null == customerDto){
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			authentication = commonService.getAuthentication(customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			if(null == authentication){
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			restRequest.setEmail(authentication.getEmailId());
			restRequest.setUserType(authentication.getUserType());
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.FALSE);
			if(response != null){
				return response;
			}else{
			   String personName = getPersonName(restRequest.getEmail(), restRequest.getUserType());	
			   String userTypeName = UIUtil.getUserType(restRequest.getUserType());
			   Boolean flag  = loginService.forgotMpin(restRequest.getEmail(),restRequest.getUserType(),
							restRequest.getMsisdnNumber(), restRequest.getSimNumber(), restRequest.getImeiNumber(),restRequest.getQuestion()
							, restRequest.getAnswer(), personName , userTypeName);
					if(!flag){
						response = ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.FORGOT_MPIN_FAIL.getValue());						
						return response;  
					}
					auditTrailService.createAuditTrail(customerDto.getId(), AuditTrailConstrain.MODULE_FORGOT_MPIN_REQUEST, 
							AuditTrailConstrain.STATUS_UPDATE, authentication.getEmailId(), authentication.getUserType());
					restRequest.setQuestion(null);
					restRequest.setAnswer(null);
					restRequest.setImeiNumber(null);
					restRequest.setSimNumber(null);
					restRequest.setMsisdnNumber(null);
					restRequest.setCustomerNumber(customerDto.getId());
					restRequest.setIsNewMPIN(Boolean.TRUE);
					response = ServerUtility.papulateSuccessCode(request,
							ServerProcessorStatus.SECRET_QA_SUCCESS.getValue(), restRequest);		
					return response; 
			}
		}catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					CommonWebserviceUtil.papulateErrorMessage(ex));		
			return response; 
		}finally {
			CommonRestServiceHelper.responseLog(LOGGER,"Forgot MPIN",response);	
			LOGGER.info(" >>>>> Exiting forgotMpin <<<<< ");
		}
	}
	
	
	
	
	/**
	 * Empty check for mandatory fields
	 * @param request
	 * @param restRequest
	 * @return
	 */
	private Response mandatoryFieldsCheck(HttpServletRequest request, RestRequest restRequest) {
		if(CommonWebserviceUtil.isEmpty(restRequest.getEmail())
				|| restRequest.getEmail().equals(NULL_STRING)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMAIL_SHOULD_NOT_BE_EMPTY.getValue());
		}
		if(CommonWebserviceUtil.isEmpty(restRequest.getUserType())
				|| restRequest.getUserType().equals(NULL_STRING)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.USERTYPE_SHOULD_NOT_BE_EMPTY.getValue());
		}
		if(CommonWebserviceUtil.isEmpty(restRequest.getPassword())
				|| restRequest.getPassword().equals(NULL_STRING)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.PASSWORD_SHOULD_NOT_BE_EMPTY.getValue());
		}
		return null;
	}
	
	/**
	 * Empty check for mandatory fields for mobile
	 * @param request
	 * @param restRequest
	 * @return
	 */
	private Response mandatoryFieldsCheckForMobilePhone(HttpServletRequest request, RestRequest restRequest) {
		
		if(CommonWebserviceUtil.isEmpty(restRequest.getPhoneCode()) || restRequest.getPhoneCode().equals(NULL_STRING)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.MOBILE_CODE_SHOULD_NOT_BE_EMPTY.getValue());
		}
		if(CommonWebserviceUtil.isEmpty(restRequest.getPhoneNo()) || restRequest.getPhoneNo().equals(NULL_STRING)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.MOBILE_NUMBER_SHOULD_NOT_BE_EMPTY.getValue());
		}
		
		return null;
	}
		
	
	/**
	 * Empty check for mandatory fields exclude password
	 * @param request
	 * @param restRequest
	 * @return
	 */
	private Response mandatoryFieldsCheckExcludePassword(HttpServletRequest request, RestRequest restRequest) {
		if(CommonWebserviceUtil.isEmpty(restRequest.getEmail())
				|| restRequest.getEmail().equals(NULL_STRING)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMAIL_SHOULD_NOT_BE_EMPTY.getValue());
		}
		if(CommonWebserviceUtil.isEmpty(restRequest.getUserType())
				|| restRequest.getUserType().equals(NULL_STRING)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.USERTYPE_SHOULD_NOT_BE_EMPTY.getValue());
		}
		return null;
	}
	
	private String getPersonName(String email, String userType) throws WalletException{
		String personName = null;
		if(GlobalLitterals.CUSTOMER_USER_TYPE.equals(userType)){
			personName = customerService.getPersonName(email, userType);
			LOGGER.info("Customer Name :: " + personName);
		}else if (GlobalLitterals.MERCHANT_USER_TYPE.equals(userType)){
			personName = merchantService.getPersonName(email, userType);
			LOGGER.info("Merchant Name :: " + personName);
		}
		return personName;
	}
	
	/**
	 * Deactivated user requested for activate mobile wallet
	 * @param authentication
	 * @param restRequest
	 * @return
	 */
	private Boolean checkDeviceActiveStatus(Authentication authentication, RestRequest restRequest){
		Boolean flag = CommonUtil.checkUserHasRequestedFromRegisterMobileWallet(authentication, restRequest.getMsisdnNumber(), 
				restRequest.getSimNumber(), restRequest.getImeiNumber());
		return flag;
	}

	/**
	 * @param request
	 * @param authentication
	 */
	private void adddUserDetailsToSession(HttpServletRequest request,Authentication authentication){
		HttpSession session = request.getSession(true);
		session.setAttribute(USER_ID, authentication.getEmailId());
		session.setAttribute(USER_TYPE, authentication.getUserType());
		session.setAttribute(USER_STATUS, authentication.getStatus());
	}
	
	/**
	 * After logout removed user details from session object
	 * @param session
	 */
	private void removeUserDetailsFromSession(HttpSession session){
		if(session.getAttribute(USER_ID) != null){
			session.removeAttribute(USER_ID);
		}
		if(session.getAttribute(USER_TYPE) != null){
			session.removeAttribute(USER_TYPE);
		}
		if(session.getAttribute(USER_STATUS) != null){
			session.removeAttribute(USER_STATUS);
		}
	}
	
	
	
	/**
	 * Get the service instance from context
	 * @param request
	 */
	private void papulateServices(HttpServletRequest request){
		HttpSession oldsession = request.getSession();
		if (oldsession != null) {
			oldsession.invalidate();
		}
		HttpSession session = request.getSession(true);
		loginService = (LoginService) ServerUtility.getServiceInstance(session, LOGIN_SERVICE);
		commonService = (CommonService) ServerUtility.getServiceInstance(session, COMMON_SERVICE);
		customerService = (CustomerService) ServerUtility.getServiceInstance(session, CUSTOMER_SERVICE);
		merchantService = (MerchantService) ServerUtility.getServiceInstance(session, MERCHANT_SERVICE);
		schedulerService = (SchedulerService) ServerUtility.getServiceInstance(session, SCHEDULER_SERVICE);
		cryptService = (CryptService) ServerUtility.getServiceInstance(session, CRYPT_SERVICE);
		auditTrailService = (AuditTrailService) ServerUtility.getServiceInstance(session, AUDIT_TRAIL_SERVICE);
		smsService = (SmsService) ServerUtility.getServiceInstance(request.getSession(), SMS_SERVICE);
		utilService = (UtilService) ServerUtility.getServiceInstance(request.getSession(), UTIL_SERVICE);
		//sendMoneyService  = (SendMoneyService) ServerUtility.getServiceInstance(session, SENDMONEY_SERVICE);
	}
	
	
	
	private CustomerDto getUpdatedCustomer(CustomerDto CustomerDto){
		CustomerDto newdto = new CustomerDto();
		newdto.setPhoneCode(CustomerDto.getPhoneCode());
		newdto.setPhoneNo(CustomerDto.getPhoneNo());
		newdto.setId(CustomerDto.getId());
		newdto.setFirstName(CustomerDto.getFirstName());
		newdto.setLastName(CustomerDto.getLastName());
		newdto.setEmailId(CustomerDto.getEmailId());
		newdto.setAuthenticationId(CustomerDto.getAuthenticationId());
		return newdto;
	}
	
}

