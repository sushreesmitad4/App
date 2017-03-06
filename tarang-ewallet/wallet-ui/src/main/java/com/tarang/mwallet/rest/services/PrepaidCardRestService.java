/**
 * 
 */
package com.tarang.mwallet.rest.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.PrepaidCardDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.prepaidcard.business.PrepaidCardService;
import com.tarang.ewallet.prepaidcard.util.AccountsConstants;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.controller.constants.Login;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
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
 * @comment This is a rest class to represent prepaid card request and response calls from mobile device.
 */
@Path("/prepaidcard")
public class PrepaidCardRestService implements Login, AttributeConstants, AttributeValueConstants, Constants, Common{
	
	private static final Logger LOGGER = Logger.getLogger(LoginRestService.class);
	
	private CommonService commonService;
	
	private CustomerService customerService;
	
	private PrepaidCardService prepaidCardService;
	
	@Path("/apply")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response applyCard(@Context HttpServletRequest request, String prepaidInputs) throws WalletException {
		papulateServices(request);		
		Response responce = null;
		RestRequest restRequest = null;
		CustomerDto customerDto = null;
		Authentication authentication = null;
		LOGGER.info(" >>>>> Entering prepaid applyCard <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER," prepaid applyCard",prepaidInputs);
		Boolean exit = false;
		
		try{
			LOGGER.info(" prepaidInputs ::  "+prepaidInputs);
			if(CommonWebserviceUtil.isEmpty(prepaidInputs)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(prepaidInputs, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER," prepaid applyCard",responce);
				LOGGER.info(" >>>>> Exiting prepaid applyCard <<<<< ");
			}			
		}
		/*responce = mandatoryFieldsCheckForMobilePhone(request, restRequest);
		if(responce != null){
			return responce;
		}*/
			
		try {
			LOGGER.info("PHONE_CODE :" + restRequest.getPhoneCode() + "PHONE_NUMBER : " + restRequest.getPhoneNo());
			/*this check will take mPin should not be empty and it should not be null and it should be four digit number*/
			if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());
				return responce;
			}
			
			customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			if(null == customerDto){
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			authentication = commonService.getAuthentication(customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			if(null == authentication){
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			/*Skip phone number check, so passed value as null*/
			responce = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					null, authentication, Boolean.FALSE);
			if(responce != null){
				return responce;
			}
			if(null == authentication.getSimNumber() && null == authentication.getImeiNumber()){
				throw new WalletException(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET);   
			}else if(Boolean.TRUE.equals(authentication.getmPinBlocked())){
				throw new WalletException(CommonConstrain.MOBILE_USER_BLOCK);
			}else if(authentication.getSimNumber().equals(restRequest.getSimNumber())
				    && authentication.getImeiNumber().equals(restRequest.getImeiNumber())){
				
				LOGGER.info("Email :" + authentication.getEmailId() + "UserType : " + authentication.getUserType());
				
				/* Validate MPIN */
				if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
					responce = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());
					return responce;
				}								
				commonService.validateMpin(authentication.getEmailId(), authentication.getUserType(), restRequest.getmPin());
				PrepaidCardDto prepaidCardDto = null;
				prepaidCardDto = prepaidCardService.getPrepaidCard(customerDto.getId());
				if(null != prepaidCardDto){
					responce = ServerUtility.papulateSuccessCode(request,
					ServerProcessorStatus.PREPAID_CARD_GOT_SUCCESSFULLY.getValue(), prepaidCardDto);
					return responce;
				}else{
					prepaidCardDto = new PrepaidCardDto();
					prepaidCardDto.setCardHolderName(customerDto.getFirstName() +" "+customerDto.getLastName());
					String cardNumber = getPrepaidCardNumber();
					prepaidCardDto.setCardNumber(cardNumber);
					prepaidCardDto.setIsSameAsProfileAddress(Boolean.TRUE);
					prepaidCardDto.setCardType(4L);
					prepaidCardDto.setCvv(getCvv());
					prepaidCardDto.setCardExpairyDate(getCardExpiryDate());
					prepaidCardDto.setSimNumber(restRequest.getSimNumber());
					prepaidCardDto.setImeiNumber(restRequest.getImeiNumber());
					prepaidCardDto.setMsisdnNumber(restRequest.getMsisdnNumber());
					prepaidCardDto.setCustomerNumber(customerDto.getId());
					prepaidCardDto.setAtype(AccountsConstants.CARD_ACCOUNT);
					prepaidCardDto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
							(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.MOBILE.getValue()));
					prepaidCardDto.setUserAgent(null);
					prepaidCardDto.setClientIpAddress("172.30.66.144");
					prepaidCardDto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
							customerDto.getCountry()));
					
					Long defultCurrency = MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), customerDto.getCountry());
					prepaidCardDto.setCurrency(defultCurrency);
					prepaidCardDto.setCustomerNumber(customerDto.getId());
					prepaidCardDto.setAddressId(customerDto.getAddressId());
					prepaidCardDto = prepaidCardService.createPrepaidCard(prepaidCardDto);
					if(null != prepaidCardDto){
						responce = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.PREPAID_CARD_APPLY_SUCCESS.getValue(), prepaidCardDto);
						return responce;
					}else{
						responce = ServerUtility.papulateSuccessCode(request,
								ServerProcessorStatus.PREPAID_CARD_APPLY_FAILED.getValue(), null);
						return responce;
					}
				}
			}else{
				throw new WalletException(CommonConstrain.USER_REQUESTED_FROM_DIFFERENT_DEVICE_OR_SIM);
			}
		} catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					CommonWebserviceUtil.papulateErrorMessage(ex));
			return responce;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER," prepaid applyCard",responce);
			LOGGER.info(" >>>>> Exiting prepaid applyCard <<<<< ");
		}
	}
	
	@Path("/view")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewCard(@Context HttpServletRequest request, String prepaidInputs) throws WalletException {
		papulateServices(request);		
		Response responce = null;
		RestRequest restRequest = null;
		CustomerDto customerDto = null;
		Authentication authentication = null;
		LOGGER.info(" >>>>> Entering prepaid viewCard <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER," prepaid viewCard",prepaidInputs);
		Boolean exit = false;
		
		try{
			if(CommonWebserviceUtil.isEmpty(prepaidInputs)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			LOGGER.info(" prepaidInputs ::  "+prepaidInputs);
			Gson gson = new Gson();
			restRequest = gson.fromJson(prepaidInputs, RestRequest.class);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"prepaid viewCard",responce);
				LOGGER.info(" >>>>> Exiting prepaid viewCard <<<<< ");
			}			
		}
		/*responce = mandatoryFieldsCheckForMobilePhone(request, restRequest);
		if(responce != null){
			return responce;
		}*/
		
		try {
			/*this check will take mPin should not be empty and it should not be null and it should be four digit number*/
			LOGGER.info("PHONE_CODE :" + restRequest.getPhoneCode() + "PHONE_NUMBER : " + restRequest.getPhoneNo());
			if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());
				return responce;
			}
			
			customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			if(null == customerDto){
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			authentication = commonService.getAuthentication(customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			if(null == authentication){
				throw new WalletException(CUSTOMER_MOBILE_NOT_EXIST);
			}
			/*Skip phone number check, so passed value as null*/
			responce = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					null, authentication, Boolean.FALSE);
			if(responce != null){
				return responce;
			}
			if(null == authentication.getSimNumber() && null == authentication.getImeiNumber()){
				throw new WalletException(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET);   
			}else if(Boolean.TRUE.equals(authentication.getmPinBlocked())){
				throw new WalletException(CommonConstrain.MOBILE_USER_BLOCK);
			}else if(authentication.getSimNumber().equals(restRequest.getSimNumber())
				    && authentication.getImeiNumber().equals(restRequest.getImeiNumber())){
				
				LOGGER.info("Email :" + authentication.getEmailId() + "UserType : " + authentication.getUserType());
				
				/* Validate MPIN */
				if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
					responce = ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());
					return responce;
				}								
				commonService.validateMpin(authentication.getEmailId(), authentication.getUserType(), restRequest.getmPin());
				PrepaidCardDto prepaidCardDto = null;
				prepaidCardDto = prepaidCardService.getPrepaidCard(customerDto.getId());
				if(null != prepaidCardDto){
					responce = ServerUtility.papulateSuccessCode(request,
					ServerProcessorStatus.PREPAID_CARD_GOT_SUCCESSFULLY.getValue(), prepaidCardDto);
					return responce;
				}else{
					responce = ServerUtility.papulateSuccessCode(request,
							ServerProcessorStatus.PREPAID_CARD_NOT_APPLIED.getValue(), null);
					return responce;
				}
			}else{
				throw new WalletException(CommonConstrain.USER_REQUESTED_FROM_DIFFERENT_DEVICE_OR_SIM);
			}
		} catch (WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			return ServerUtility.papulateErrorCode(request, 
					CommonWebserviceUtil.papulateErrorMessage(ex));
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"prepaid viewCard",responce);
			LOGGER.info(" >>>>> Exiting prepaid viewCard <<<<< ");
		}
	}
	
	/**
	 * Empty check for mandatory fields for mobile
	 * @param request
	 * @param restRequest
	 * @return
	 */
	private Response mandatoryFieldsCheckForMobilePhone(HttpServletRequest request, RestRequest restRequest) {
		
		Response responce = null;
		
		if(CommonWebserviceUtil.isEmpty(restRequest.getPhoneCode()) || restRequest.getPhoneCode().equals(NULL_STRING)){
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.MOBILE_CODE_SHOULD_NOT_BE_EMPTY.getValue());
			return responce;
		}
		if(CommonWebserviceUtil.isEmpty(restRequest.getPhoneNo()) || restRequest.getPhoneNo().equals(NULL_STRING)){
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.MOBILE_NUMBER_SHOULD_NOT_BE_EMPTY.getValue());
			return responce;
		}
		
		return null;
	}
	
	/***
	  * for generating random number
	  * 
	  * @param len length of the random number ging to generate
	  * 
	  * @return String  represent random string
	  */
	 public static String getPrepaidCardNumber() {
	  long staticNumber = 412365;
	  long len = 10;
	  long tLen = (long) Math.pow(10, len - 1) * 9;

	  long number = (long) (Math.random() * tLen)
	    + (long) Math.pow(10, len - 1) * 1;

	  String tVal = staticNumber +""+ number + "";
	  return tVal;
	 }
	 
		/***
	  * for generating random number
	  * 
	  * @param len length of the random number ging to generate
	  * 
	  * @return String  represent random string
	  */
	 public static String getCvv() {
	  long len = 3;
		 
	  long tLen = (long) Math.pow(10, len - 1) * 9;

	  long number = (long) (Math.random() * tLen)
	    + (long) Math.pow(10, len - 1) * 1;
	  String tVal = number + "";
	  
	  return tVal;
	 }
	 
	 private static String getCardExpiryDate(){
		 Calendar now = Calendar.getInstance();
		 now.add(Calendar.YEAR, 4);
		 SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
		 return formatter.format(now.getTime());
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
		commonService = (CommonService) ServerUtility.getServiceInstance(session, COMMON_SERVICE);
		customerService = (CustomerService) ServerUtility.getServiceInstance(session, CUSTOMER_SERVICE);
		prepaidCardService = (PrepaidCardService) ServerUtility.getServiceInstance(session, PREPAID_CARD_SERVICE);
	}
}
