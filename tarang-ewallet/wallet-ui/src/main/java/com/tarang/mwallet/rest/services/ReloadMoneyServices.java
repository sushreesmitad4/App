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
import com.tarang.ewallet.accounts.business.AccountsService;
import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.accounts.util.FundingAccountStatus;
import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.ReloadMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.util.ErrorCodeConstants;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.transaction.business.ReloadMoneyService;
import com.tarang.ewallet.transaction.business.VelocityAndThresholdService;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.form.ReloadMoneyForm;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.webservice.validation.RestCustomValidation;
import com.tarang.mwallet.rest.services.model.RestRequest;
import com.tarang.mwallet.rest.services.util.CommonRestServiceHelper;
import com.tarang.mwallet.rest.services.util.CommonWebserviceUtil;
import com.tarang.mwallet.rest.services.util.Constants;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;
import com.tarang.mwallet.rest.services.util.ServerUtility;
import com.tarang.mwallet.rest.services.util.TransactionServiceHelper;

/**
 * @author yasin
 * This rest service will provide the reload fund functionality to the device
 */
@Path("/reloadmoney")
public class ReloadMoneyServices implements AttributeConstants , GlobalLitterals, Constants{
	
	private static final Logger LOGGER = Logger.getLogger(ReloadMoneyServices.class);
	
	private AccountsService accountsService;
	
	private ReloadMoneyService reloadMoneyService;
	
	private CommonService commonService;
	
	private CustomerService customerService;
	
	private MerchantService merchantService;
	
	private VelocityAndThresholdService velocityAndThresholdService;
	
	private AuditTrailService auditTrailService;

	@Path("/relmonservice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reloadmoneyrequestSave(@Context HttpServletRequest request, 
			String reloadfundInput) throws WalletException {
		
		papulateServices(request);
		LOGGER.info(" >>>>> Entering reloadmoneyrequestSave <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"reloadmoneyrequestSave",reloadfundInput);
		Response response = null;
		RestRequest restRequest = null;
		CustomerDto customerDto = null;
		Authentication authentication = null;
		boolean exit = false;

		try {

			if (CommonWebserviceUtil.isEmpty(reloadfundInput)) {
				response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(reloadfundInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return response;
		} finally {
			if (exit) {
				CommonRestServiceHelper.responseLog(LOGGER, "reloadmoneyrequestSave", response);
				LOGGER.info(" >>>>> Exiting reloadmoneyrequestSave <<<<< ");
			}
		}
		LOGGER.info("emailId: " + restRequest.getEmail() + "userType: " + restRequest.getUserType() 
				+ "accountId: " + restRequest.getAccountId() +" amount: "+restRequest.getAmount() 
				+ " cvv: "+restRequest.getCvv());
		ReloadMoneyForm reloadMoneyForm = getReloadMoneyForm(restRequest);
		String userType = restRequest.getUserType();
		try {
			customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			authentication = commonService.getAuthentication(customerDto.getEmailId(), userType);
			restRequest.setEmail(authentication.getEmailId());
			
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			}
			Long status = authentication.getStatus();
			response = CommonRestServiceHelper.checkTransaction(request, userType, 
		    		status);
			if(response != null){
				return response;
			} 
			AccountDto accountDto  = accountsService.getAccount(Long.parseLong(reloadMoneyForm.getAccountId()));
			response = CommonRestServiceHelper.isAccountBelongsToUser(request, authentication, accountDto);
			if(response != null){
				return response;
			}
			if(RestCustomValidation.numberValidation(reloadMoneyForm.getCvv(), accountDto.getCardType())){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_CVV_FORMATE.getValue());
				return response;
			}
			if(!AccountsConstants.CARD_ACCOUNT.equals(accountDto.getAtype())){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.ACCOUNT_TYPE_IS_NOT_CARD.getValue());
				return response;
			}
			if(!FundingAccountStatus.VERIFIED_STATUS.equals(accountDto.getStatus())){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.ACCOUNT_IS_NOT_VARIFIED.getValue());
				return response;
			}
			ReloadMoneyDto reloadMnyDto = getReloadMoneyDto(request, reloadMoneyForm, restRequest.getEmail(), 
					restRequest.getUserType(), accountDto);
			
			reloadMnyDto.setAuthId(authentication.getId());
			VelocityAndThreshold velocity = velocityAndThresholdService.getThreshold(reloadMnyDto.getCountryId(), 
					reloadMnyDto.getCurrencyId(), reloadMnyDto.getTypeOfTransaction(), reloadMnyDto.getUserType());
			if(reloadMoneyForm.getAmount() == null || reloadMoneyForm.getAmount().equals(EMPTY_STRING)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_AMOUNT_FORMAT.getValue());
				return response;
			} else {
				if(!reloadMoneyForm.getAmount().equals(EMPTY_STRING)){
					Boolean amountError = UIUtil.currencyValidator(reloadMoneyForm.getAmount());
					if(amountError){
						response = ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.INVALID_AMOUNT_FORMAT.getValue());
						return response;
					} else {
						reloadMnyDto.setReloadAmount(Double.parseDouble(reloadMoneyForm.getAmount()));
					}
					if((velocity != null && reloadMnyDto.getReloadAmount() != null) &&
							!(velocity.getMinimumamount() <= reloadMnyDto.getReloadAmount() && 
							velocity.getMaximumamount() >= reloadMnyDto.getReloadAmount())){
						response = ServerUtility.papulateErrorCode(request, 
								ServerProcessorStatus.THRESHOLD_DOEST_SUPPORT_FOR_RELOAD_FUND.getValue());
						return response;
					}
				}	
			}
			try {
				ReloadMoneyDto reloadMoneyDto = reloadMoneyService.createReloadMoney(reloadMnyDto);
				auditTrailService.createAuditTrail(reloadMnyDto.getAuthId(), AuditTrailConstrain.MODULE_RELAOD_MONEY_CREATE, 
						AuditTrailConstrain.STATUS_CREATE, reloadMnyDto.getPayeeEmail(), restRequest.getUserType());
				
				if(reloadMoneyDto != null && reloadMoneyDto.getIsReloadMoneySucc()){
					response =  ServerUtility.papulateSuccessCode(request, 
							ServerProcessorStatus.MONEY_RELOADED_SUCCESSFULLY.getValue(), null);
					return response;
				} else {
					//failed
					response = ServerUtility.papulatePGErrorCode(request, 
							TransactionServiceHelper.papulatePgResponseCode(reloadMoneyDto.getPgResponseCode()));
					return response;
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
				response = ServerUtility.papulateErrorCode(request,	em);
				return response;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			if(AccountsConstants.ERROR_MSG_NON_EXISTS.equals(ex.getMessage())){
				LOGGER.error(ex.getMessage(), ex);
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.FAILEDTO_RETRIEVE_ACCOUNT_DETAILS_RELOAD_MONEY_ERRMSG.getValue());
				return response;
			}
		}
		finally{
			CommonRestServiceHelper.responseLog(LOGGER,"reloadmoneyrequestSave",response);
			LOGGER.info(" >>>>> Exiting reloadmoneyrequestSave <<<<< ");
		}
		
		return null;
	}
	
	private ReloadMoneyDto getReloadMoneyDto(HttpServletRequest request, ReloadMoneyForm reloadMoneyForm, String emailId, String userType, AccountDto accountDto) throws WalletException{
		ReloadMoneyDto reloadMoneyDto = new ReloadMoneyDto();
		if(CUSTOMER_USER_TYPE.equals(userType)){
			reloadMoneyDto.setTypeOfTransaction(WalletTransactionTypes.RELOAD_MONEY_FROM_CARD_CUSTOMER);
			reloadMoneyDto.setUserType(USERTYPE_CUSTOMER);
		} else if(MERCHANT_USER_TYPE.equals(userType)){
			reloadMoneyDto.setTypeOfTransaction(WalletTransactionTypes.RELOAD_MONEY_FROM_CARD_MERCHANT);
			reloadMoneyDto.setUserType(USERTYPE_MERCHANT);
		}
		reloadMoneyDto.setAccountId(accountDto.getId());
		reloadMoneyDto.setCvv(reloadMoneyForm.getCvv());
		reloadMoneyDto.setCardNumber(accountDto.getCardNumber());
		reloadMoneyDto.setCardExpairyDate(accountDto.getCardExpairyDate());
		reloadMoneyDto.setCountryId(accountDto.getCountry());
		reloadMoneyDto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(
				request.getSession().getServletContext(), accountDto.getCountry()));
		reloadMoneyDto.setCurrencyId(MasterDataUtil.getCountryCurrencyId(
				request.getSession().getServletContext(), accountDto.getCountry()));
		reloadMoneyDto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.MOBILE.getValue()));
		String personName = null;
		if(GlobalLitterals.CUSTOMER_USER_TYPE.equals(userType)){
			personName = customerService.getPersonName(emailId, userType);
			LOGGER.info("Customer Name :: " + personName);
		} else if (GlobalLitterals.MERCHANT_USER_TYPE.equals(userType)){
			personName = merchantService.getPersonName(emailId, userType);
			LOGGER.info("Merchant Name :: " + personName);
		}
		reloadMoneyDto.setPayeeName(personName);
		reloadMoneyDto.setPayeeEmail(emailId);
		//Time being take it as a null
		reloadMoneyDto.setLanguageId(null);
		reloadMoneyDto.setAccountOrCardHolderName(accountDto.getAccountHolderName());
		return reloadMoneyDto;
	}
	
	/**
	 * Convert the request object to the actual form object
	 * @param restRequest
	 * @return
	 */
	private ReloadMoneyForm getReloadMoneyForm(RestRequest restRequest){
		ReloadMoneyForm reloadMoneyForm = new ReloadMoneyForm();
		if(null == restRequest){
			return null;
		}
		reloadMoneyForm.setAccountId(GlobalLitterals.EMPTY_STRING + restRequest.getAccountId());
		reloadMoneyForm.setAmount(restRequest.getAmount());
		reloadMoneyForm.setCvv(restRequest.getCvv());
		return reloadMoneyForm;
	}
	
	/**
	 * Get service instances from request object
	 * @param request
	 */
	private void papulateServices(HttpServletRequest request){
		accountsService = (AccountsService) ServerUtility.getServiceInstance(request.getSession(), ACCOUNTS_SERVICE );
		reloadMoneyService = (ReloadMoneyService) ServerUtility.getServiceInstance(request.getSession(), RELOAD_MONEY_SERVICE);
		commonService = (CommonService) ServerUtility.getServiceInstance(request.getSession(), COMMON_SERVICE);
		customerService = (CustomerService) ServerUtility.getServiceInstance(request.getSession(), CUSTOMER_SERVICE);
		merchantService = (MerchantService) ServerUtility.getServiceInstance(request.getSession(), MERCHANT_SERVICE);
		velocityAndThresholdService = (VelocityAndThresholdService) ServerUtility.getServiceInstance(request.getSession(), VELOCITY_AND_THRESHOLD_SERVICE);
		auditTrailService = (AuditTrailService) ServerUtility.getServiceInstance(request.getSession(), AUDIT_TRAIL_SERVICE);
	}
}
