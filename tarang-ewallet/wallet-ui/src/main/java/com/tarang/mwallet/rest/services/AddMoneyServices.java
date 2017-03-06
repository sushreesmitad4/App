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
import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.AddMoneyDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.util.ErrorCodeConstants;
import com.tarang.ewallet.masterdata.util.CountryIds;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.WalletThreshold;
import com.tarang.ewallet.transaction.business.AddMoneyService;
import com.tarang.ewallet.transaction.business.VelocityAndThresholdService;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.mwallet.rest.services.model.RestRequest;
import com.tarang.mwallet.rest.services.util.CommonRestServiceHelper;
import com.tarang.mwallet.rest.services.util.CommonWebserviceUtil;
import com.tarang.mwallet.rest.services.util.Constants;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;
import com.tarang.mwallet.rest.services.util.ServerUtility;

/**
 * @author kedarnathd
 * This rest service will provide the add fund functionality to the device
 */
@Path("/addmoney")
public class AddMoneyServices implements AttributeConstants , GlobalLitterals, Constants{
	
	private static final Logger LOGGER = Logger.getLogger(AddMoneyServices.class);
	
	private AddMoneyService addMoneyService;
	
	private CommonService commonService;
	
	private CustomerService customerService;
	
	private MerchantService merchantService;
	
	private VelocityAndThresholdService velocityAndThresholdService;
	
	private AuditTrailService auditTrailService;

	@Path("/addmoneyservice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addmoneyrequestSave(@Context HttpServletRequest request, 
			String addfundInput) throws WalletException {
		papulateServices(request);
		RestRequest restRequest = null;
		CustomerDto customerDto = null;
		Authentication authentication = null;
		UserWallet userWallet = null;
		WalletThreshold walletThreshold = null;
		Response responce = null;
		LOGGER.info(" >>>>> Entering addmoneyrequestSave <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"addmoneyrequestSave",addfundInput);
		Boolean exit = false;
		
		try {
			if (CommonWebserviceUtil.isEmpty(addfundInput)) {
				responce = ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(addfundInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"addmoneyrequestSave",responce);
				LOGGER.info(" >>>>> Exiting addmoneyrequestSave <<<<< ");
			}			
		}
		/*Required input fields for Add money service*/
		
		try {
			LOGGER.info("emailId: " + restRequest.getEmail() + "userType: " + restRequest.getUserType() 
			+ "orderId: " + restRequest.getOrderId() +" amount: "+restRequest.getAmount()
			+ "requestedCurrency: "+restRequest.getRequestedCurrency());
	
			String userType = restRequest.getUserType();
			customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			authentication = commonService.getAuthentication(customerDto.getEmailId(), userType);
			restRequest.setEmail(authentication.getEmailId());
			AddMoneyDto addMnyDto = getAddMoneyDto(request, restRequest.getEmail(), 
					restRequest.getUserType());
			addMnyDto.setOrderId(restRequest.getOrderId());
			addMnyDto.setAuthId(authentication.getId());
			VelocityAndThreshold velocity = velocityAndThresholdService.getThreshold(addMnyDto.getCountryId(), 
					addMnyDto.getCurrencyId(), addMnyDto.getTypeOfTransaction(), addMnyDto.getUserType());
			if(restRequest.getAmount() == null || restRequest.getAmount().equals(EMPTY_STRING)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_AMOUNT_FORMAT.getValue());
				return responce;
			} else {
				if(!restRequest.getAmount().equals(EMPTY_STRING)){
					Boolean amountError = UIUtil.currencyValidator(restRequest.getAmount());
					if(amountError){
						responce = ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.INVALID_AMOUNT_FORMAT.getValue());
						return responce;
					} else {
						addMnyDto.setAddMoneyAmount(Double.parseDouble(restRequest.getAmount()));
					}
					if((velocity != null && String.valueOf(addMnyDto.getAddMoneyAmount()) != null) &&
							!(velocity.getMinimumamount() <= addMnyDto.getAddMoneyAmount() && 
							velocity.getMaximumamount() >= addMnyDto.getAddMoneyAmount())){
						responce = ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.TXN_THRESHOLD_DOEST_SUPPORT_FOR_ADDMONEY_FUND.getValue());
						return responce;
					}
					
					userWallet = commonService.getUserWallet(authentication.getId(), addMnyDto.getCurrencyId());
					walletThreshold = velocityAndThresholdService.getWallet(addMnyDto.getCountryId(),
							addMnyDto.getCurrencyId());

					if (userWallet != null && walletThreshold != null && userWallet.getAmount() != null
							&& walletThreshold.getMaximumamount() != null) {
						Double userAddAmount = userWallet.getAmount() + Double.valueOf(restRequest.getAmount());
						if (Double.valueOf(userAddAmount) > walletThreshold.getMaximumamount()
								|| userWallet.getAmount() > walletThreshold.getMaximumamount()) {
							responce = ServerUtility.papulateErrorCode(request, 
									ServerProcessorStatus.WALLET_MAXIMUM_LIMIT_EXCEED.getValue());
							return responce;
						}
					}
				}	
			}
			try {
				AddMoneyDto addMoneyDto = addMoneyService.createAddMoney(addMnyDto);
				auditTrailService.createAuditTrail(addMnyDto.getAuthId(), AuditTrailConstrain.MODULE_RELAOD_MONEY_CREATE, 
						AuditTrailConstrain.STATUS_CREATE, addMnyDto.getPayeeEmail(), restRequest.getUserType());
				
				if(addMnyDto.getOrderId() != null && addMoneyDto != null && addMoneyDto.getIsReloadMoneySucc()){
					responce = ServerUtility.papulateSuccessCode(request, 
							ServerProcessorStatus.MONEY_ADDED_SUCCESSFULLY.getValue(), null);
					return responce;
				} else {
					//failed
					responce = ServerUtility.papulateSuccessCode(request, 
							ServerProcessorStatus.FAILED_TO_ADD_MONEY.getValue(), null);
					return responce;
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				String em = null;
				if(e.getMessage().contains(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT)){
					em = ServerProcessorStatus.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT.getValue();
				} else if(e.getMessage().equals(ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH)){
					em = ServerProcessorStatus.PG_SERVICE_IS_NOT_ESTABLISH.getValue();
				} else if(e.getMessage().equals(ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH)){
					em = ServerProcessorStatus.PG_SERVICE_IS_NOT_ESTABLISH.getValue();
				} else if(e.getMessage().equals(ErrorCodeConstants.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT)){
					em = ServerProcessorStatus.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT.getValue();
				} else{
					em = ServerProcessorStatus.RELOAD_FAILED_MESSAGE.getValue();
				}
				responce = ServerUtility.papulateErrorCode(request,	em);
				return responce;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			if(AccountsConstants.ERROR_MSG_NON_EXISTS.equals(ex.getMessage())){
				LOGGER.error(ex.getMessage(), ex);
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.FAILEDTO_RETRIEVE_ACCOUNT_DETAILS_RELOAD_MONEY_ERRMSG.getValue());
				return responce;
		}
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"addmoneyrequestSave",responce);
			LOGGER.info(" >>>>> Exiting addmoneyrequestSave <<<<< ");
		}
		return null;
	}
	
	private AddMoneyDto getAddMoneyDto(HttpServletRequest request, String emailId, String userType) throws WalletException{
		AddMoneyDto addMoneyDto = new AddMoneyDto();
		if(CUSTOMER_USER_TYPE.equals(userType)){
			addMoneyDto.setTypeOfTransaction(WalletTransactionTypes.ADD_MONEY_FROM_CARD_CUSTOMER);
			addMoneyDto.setUserType(USERTYPE_CUSTOMER);
		} else if(MERCHANT_USER_TYPE.equals(userType)){
			addMoneyDto.setTypeOfTransaction(WalletTransactionTypes.ADD_MONEY_FROM_CARD_MERCHANT);
			addMoneyDto.setUserType(USERTYPE_MERCHANT);
		}
		addMoneyDto.setCountryId(CountryIds.IND_COUNTRY_ID);
		addMoneyDto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(
				request.getSession().getServletContext(), CountryIds.IND_COUNTRY_ID));
		addMoneyDto.setCurrencyId(MasterDataUtil.getCountryCurrencyId(
				request.getSession().getServletContext(), CountryIds.IND_COUNTRY_ID));
		addMoneyDto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.MOBILE.getValue()));
		String personName = null;
		if(GlobalLitterals.CUSTOMER_USER_TYPE.equals(userType)){
			personName = customerService.getPersonName(emailId, userType);
			LOGGER.info("Customer Name :: " + personName);
		} else if (GlobalLitterals.MERCHANT_USER_TYPE.equals(userType)){
			personName = merchantService.getPersonName(emailId, userType);
			LOGGER.info("Merchant Name :: " + personName);
		}
		addMoneyDto.setPayeeName(personName);
		addMoneyDto.setPayeeEmail(emailId);
		//Time being take it as a null
		addMoneyDto.setLanguageId(null);
		addMoneyDto.setAccountOrCardHolderName(personName);
		return addMoneyDto;
	}
	
	/**
	 * Get service instances from request object
	 * @param request
	 */
	private void papulateServices(HttpServletRequest request){
		addMoneyService = (AddMoneyService) ServerUtility.getServiceInstance(request.getSession(), ADD_MONEY_SERVICE);
		commonService = (CommonService) ServerUtility.getServiceInstance(request.getSession(), COMMON_SERVICE);
		customerService = (CustomerService) ServerUtility.getServiceInstance(request.getSession(), CUSTOMER_SERVICE);
		merchantService = (MerchantService) ServerUtility.getServiceInstance(request.getSession(), MERCHANT_SERVICE);
		velocityAndThresholdService = (VelocityAndThresholdService) ServerUtility.getServiceInstance(request.getSession(), VELOCITY_AND_THRESHOLD_SERVICE);
		auditTrailService = (AuditTrailService) ServerUtility.getServiceInstance(request.getSession(), AUDIT_TRAIL_SERVICE);
	}
}
