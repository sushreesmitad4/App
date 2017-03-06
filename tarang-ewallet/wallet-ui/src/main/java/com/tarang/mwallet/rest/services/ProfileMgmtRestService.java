package com.tarang.mwallet.rest.services;


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
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.form.CustomerRegFormTwo;
import com.tarang.ewallet.walletui.form.MerchantForm;
import com.tarang.ewallet.walletui.util.CustomerDataUtil;
import com.tarang.ewallet.walletui.util.MerchantDataUtil;
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
@Path("/profilemgmt")
public class ProfileMgmtRestService implements Constants , AttributeConstants, AttributeValueConstants, GlobalLitterals{

	private static final Logger LOGGER = Logger.getLogger(ProfileMgmtRestService.class);
	
	private CustomerService customerService;
	
	private MerchantService merchantService;
	
	private CommonService commonService;
	
	private AuditTrailService auditTrailService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response profileManagement(@Context HttpServletRequest request,String profileMgmtInput) 
			throws WalletException {
		
		Response responce = null;
		LOGGER.info(" >>>>> Entering profileManagement <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"profileManagement",profileMgmtInput);
		Boolean exit = false;
		papulateServices(request);

		RestRequest restRequest = null;
		Authentication authentication = null;

		
		try {
			if (CommonWebserviceUtil.isEmpty(profileMgmtInput)) {
				responce = ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(profileMgmtInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"profileManagement",responce);
				LOGGER.info(" >>>>> Exiting profileManagement <<<<< ");
			}			
		}
		Long customerId = null;
		Long longMerchantId = null;
		String merchantId = EMPTY_STRING;
		try {
			LOGGER.info(USER_EMAIL + ":" + restRequest.getEmail() + USER_TYPE
					+ restRequest.getUserType() + "MsisdnNumber" +restRequest.getMsisdnNumber()
					+ "simnumber" + restRequest.getSimNumber() + "ImeiNumber" + restRequest.getImeiNumber() );
			authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
			/* validate requested user phone number */
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), restRequest.getUserType());
			Response response = CommonRestServiceHelper
					.checkAutherizedUserAccess(request, restRequest,	phoneNumber, authentication, Boolean.TRUE);
			if (response != null) {
				return response;
			}

			if (CUSTOMER_USER_TYPE.equals(restRequest.getUserType())) {
				customerId = customerService.getCustomerId(restRequest.getEmail(), restRequest.getUserType());
				CustomerDto customerDto = customerService.getCustomerDto(customerId);
				CustomerRegFormTwo customerRegFormTwoObject = new CustomerRegFormTwo();
				if(customerDto.getProfileComplet().equals(Boolean.TRUE)){
					customerRegFormTwoObject = null;
					customerRegFormTwoObject = CustomerDataUtil.convertCustomerDtoToCustomerForm(request,
							customerDto);
				}
				
				customerRegFormTwoObject.setId(customerId);
				
				responce = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.RECORDS_FOUND.getValue(),customerRegFormTwoObject);
				return responce;

			} else if (MERCHANT_USER_TYPE.equals(restRequest.getUserType())) {
				longMerchantId = merchantService.getMerchantId(restRequest.getEmail(), restRequest.getUserType());

				merchantId = longMerchantId != null ? longMerchantId.toString(): EMPTY_STRING;
				MerchantDto merchantDto = merchantService.getMerchantDetailsById(Long.parseLong(merchantId));

				MerchantForm merchForm = MerchantDataUtil.convertMerchantDtoMerchantForm(request, merchantDto);
				merchForm.setId(Long.parseLong(merchantId));
				responce = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.RECORDS_FOUND.getValue(), merchForm);
				return responce;
			}
			responce = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.UNABLE_TO_PROCESS_MSG.getValue());
			return responce;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			responce = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.UNABLE_TO_PROCESS_MSG.getValue());
			return responce;
	}finally{
		CommonRestServiceHelper.responseLog(LOGGER,"profileManagement",responce);
		LOGGER.info(" >>>>> Exiting profileManagement <<<<< ");
	}
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProfileManagement(@Context HttpServletRequest request,String profileMgmtInput) 
			throws WalletException {

		Response responce = null;
		LOGGER.info(" >>>>> Entering updateProfileManagement <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"updateProfileManagement",profileMgmtInput);
		Boolean exit = false;
		papulateServices(request);

		RestRequest restRequest = null;
		Authentication authentication = null;

		try {
			if (CommonWebserviceUtil.isEmpty(profileMgmtInput)) {
				responce = ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(profileMgmtInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"updateProfileManagement",responce);
				LOGGER.info(" >>>>> Exiting updateProfileManagement <<<<< ");
			}			
		}
		Long customerId = null;
		Long longMerchantId = null;
		String merchantId = EMPTY_STRING;
		try {
			LOGGER.info(USER_EMAIL + ":" + restRequest.getEmail() + USER_TYPE
					+ restRequest.getUserType() + "MsisdnNumber" +restRequest.getMsisdnNumber()
					+ "simnumber" + restRequest.getSimNumber() + "ImeiNumber" + restRequest.getImeiNumber() );
			authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
			/* validate requested user phone number */
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), restRequest.getUserType());
			Response response = CommonRestServiceHelper
					.checkAutherizedUserAccess(request, restRequest,
							phoneNumber, authentication, Boolean.TRUE);
			if (response != null) {
				return response;
			}

			if (CUSTOMER_USER_TYPE.equals(restRequest.getUserType())) {
				customerId = customerService.getCustomerId(restRequest.getEmail(), restRequest.getUserType());
				CustomerDto customerDto = customerService.getCustomerDto(customerId);
				CustomerDto customerDtoCopy = (CustomerDto)customerDto.clone();
				CustomerDto customerDto2 = CustomerDataUtil.setCustomerDto(restRequest, customerDto);
				customerService.mobileUpdateProfile(customerDto2);
				//Audit Trail service
				auditTrailService.createAuditTrail(customerDto.getAuthenticationId(), 
						AuditTrailConstrain.MODULE_CUSTOMER_UPDATE, AuditTrailConstrain.STATUS_UPDATE, 
						customerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE, 
						customerDtoCopy, customerDto2);
				CustomerDto cdto = new CustomerDto();
				cdto.setFirstName(customerDto2.getFirstName());
				cdto.setLastName(customerDto2.getLastName());
				cdto.setEmailId(customerDto2.getEmailId());
				cdto.setId(customerDto2.getId());
				cdto.setLastLogin(customerDto2.getLastLogin());
				cdto.setPhoneCode(customerDto2.getPhoneCode());
				cdto.setPhoneNo(customerDto2.getPhoneNo());
				cdto.setProfileComplet(customerDto2.getProfileComplet());
				responce = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.CUSTOMER_UPDATE_SUCCESS.getValue(), cdto);
				return responce;

			} else if (MERCHANT_USER_TYPE.equals(restRequest.getUserType())) {
				longMerchantId = merchantService.getMerchantId(restRequest.getEmail(), restRequest.getUserType());

				merchantId = longMerchantId != null ? longMerchantId.toString(): EMPTY_STRING;
				MerchantDto merchantDto = merchantService.getMerchantDetailsById(Long.parseLong(merchantId));

				MerchantForm merchForm = MerchantDataUtil.convertMerchantDtoMerchantForm(request, merchantDto);
				merchForm.setId(Long.parseLong(merchantId));
				responce = ServerUtility.papulateSuccessCode(request,
						ServerProcessorStatus.RECORDS_FOUND.getValue(), merchForm);
				return responce;
			}
			responce = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.CUSTOMER_UPDATE_FAILED.getValue());
			return responce;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			responce = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.CUSTOMER_UPDATE_FAILED.getValue());
			return responce;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"updateProfileManagement",responce);
			LOGGER.info(" >>>>> Exiting updateProfileManagement <<<<< ");
		}
	}
	
	/**
	 * Get the service instance from context
	 * @param request
	 */
	private void papulateServices(HttpServletRequest request){
		commonService = (CommonService) ServerUtility.getServiceInstance(request.getSession(), COMMON_SERVICE);
		customerService = (CustomerService) ServerUtility.getServiceInstance(request.getSession(), CUSTOMER_SERVICE);
		merchantService = (MerchantService) ServerUtility.getServiceInstance(request.getSession(), MERCHANT_SERVICE);
		auditTrailService = (AuditTrailService) ServerUtility.getServiceInstance(request.getSession(), AUDIT_TRAIL_SERVICE);
	}
	
}
