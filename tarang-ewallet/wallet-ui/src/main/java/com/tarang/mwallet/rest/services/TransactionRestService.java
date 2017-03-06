package com.tarang.mwallet.rest.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.SelfTransferDto;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.sms.service.SmsService;
import com.tarang.ewallet.transaction.business.SendMoneyService;
import com.tarang.ewallet.transaction.util.CurrencyConversion;
import com.tarang.ewallet.transaction.util.DestinationTypes;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.controller.constants.CSVFileConstants;
import com.tarang.ewallet.walletui.controller.constants.SendMoney;
import com.tarang.ewallet.walletui.form.SelfTransferForm;
import com.tarang.ewallet.walletui.form.SendMoneyForm;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.SendMoneyHelper;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.webservice.validation.RestCustomValidation;
import com.tarang.mwallet.rest.services.model.FundTransferInputResponse;
import com.tarang.mwallet.rest.services.model.RestRequest;
import com.tarang.mwallet.rest.services.model.SelfTransferInputResponse;
import com.tarang.mwallet.rest.services.util.CommonRestServiceHelper;
import com.tarang.mwallet.rest.services.util.CommonWebserviceUtil;
import com.tarang.mwallet.rest.services.util.Constants;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;
import com.tarang.mwallet.rest.services.util.ServerUtility;
import com.tarang.mwallet.rest.services.util.TransactionServiceHelper;
/**
 * @author hari.santosh
 * This Rest service will hold the list of post methods for wallet transaction
 */
@Path("/wallettransaction")
public class TransactionRestService implements SendMoney, AttributeConstants,
		GlobalLitterals, AttributeValueConstants, CSVFileConstants, Constants {

	private static final Logger LOGGER = Logger.getLogger(TransactionRestService.class);

	private CommonService commonService;

	private CustomerService customerService;

	private MerchantService merchantService;

	private SendMoneyService sendMoneyService;

	private FeeMgmtService feeMgmtService;
	
	private SmsService smsService;
	
	private UtilService utilService;
	
	/**
	 * This post method provides the self transfer functionality to the device request.
	 * This is the first request, which will calculate the fee or tax and send back to the device for confirmation
	 * @param request
	 * @param selfTransferInput
	 * @return
	 * @throws WalletException
	 */
	@Path("/selftransfer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response selftransfer(@Context HttpServletRequest request, String selfTransferInput) 
			throws WalletException {

        papulateServices(request);
		LOGGER.info(" >>>>> Entering selftransfer <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"selftransfer",selfTransferInput);
		Authentication authentication = null;
		Long countryId = null;
		Long transactionType = null;
		Long uType = null;
		RestRequest restRequest = null;
		Double deductions = ZERO_DOUBLE;
		Double actualAmount = ZERO_DOUBLE;
		Response response = null;
		Double reqAmount = ZERO_DOUBLE;
		Boolean exit = false;
		
		try {

			if (CommonWebserviceUtil.isEmpty(selfTransferInput)) {
				response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(selfTransferInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return response;
		} finally {
			if (exit) {
				CommonRestServiceHelper.responseLog(LOGGER, "selftransfer", response);
				LOGGER.info(" >>>>> Exiting selftransfer <<<<< ");
			}
		}
		LOGGER.info("FromWallet : " + restRequest.getFromWallet()
				+ " ToWallet : " + restRequest.getTowallet()
				+ " requestedAmount : " + restRequest.getRequestedAmount()
				+ USER_EMAIL +":"+ restRequest.getEmail() + USER_TYPE 
				+ restRequest.getUserType() + "simnumber"
				+ restRequest.getSimNumber());
		
		
		try {
			
			response = CommonRestServiceHelper.validAmountCheck(request, restRequest.getRequestedAmount());
			if(response!=null){
				return response;
			}
			response = CommonRestServiceHelper.validWalletCheck(request, restRequest.getFromWallet(), restRequest.getTowallet());
			if(response!=null){
				return response;
			}
			String email = restRequest.getEmail();
			String userType = restRequest.getUserType();
			
			authentication = commonService.getAuthentication(email, userType);
			/*validate requested user phone number*/
			PhoneNumber phoneNumber = commonService.getPhoneNumber(email, userType);
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			}
			Long authId = authentication.getId();
			Long status = authentication.getStatus();
			LOGGER.info(AUTHENTICATION_ID +":" + authId);
			/*validate requested user is authorized to do a transaction*/
			response = CommonRestServiceHelper.checkTransaction(request, userType, status);
			if(response != null){
				return response;
			}
			Long fromWalletCurrency = restRequest.getFromWallet();
			Long toWalletCurrency = restRequest.getTowallet();
			reqAmount = Double.parseDouble(restRequest.getRequestedAmount());
			/*Find out the type of transaction based on user type*/    
			if (CUSTOMER_USER_TYPE.equals(userType)) {
				LOGGER.info(" UserType as Customer: ");
				CustomerDto customerDto = customerService.getRegisteredMember(email, userType);
				countryId = customerDto.getCountry();
				LOGGER.info(" Country ID: " + countryId);
				uType = CUSTOMER_USER_TYPE_ID;
				transactionType = WalletTransactionTypes.P_TO_S;
			} else if (MERCHANT_USER_TYPE.equals(restRequest.getUserType())) {
				MerchantDto merchantDto = merchantService.getRegisteredMember(email, userType);
				countryId = merchantDto.getCountryBO();
				uType = MERCHANT_USER_TYPE_ID;
				transactionType = WalletTransactionTypes.M_TO_S;
			}
			if (!(sendMoneyService.validateThresholdLimitForSelfTransfer(
					countryId, toWalletCurrency, transactionType, reqAmount, uType))) {
				response =  ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT.getValue());
				return response;
			}
			/*calculate the fee and tax deduction and check available balance of the user*/
			UserWallet userWallet = commonService.getUserWallet(authId, fromWalletCurrency);
			Double convertionRate = CurrencyConversion.getInstance().getConvertionFactor(fromWalletCurrency, toWalletCurrency);
			actualAmount = reqAmount / convertionRate;
			if (userWallet != null) {
				deductions = feeMgmtService.calcuateDeductions(actualAmount, countryId, 
						fromWalletCurrency, transactionType, Boolean.TRUE);
			}
			if (userWallet == null || userWallet.getAmount() < actualAmount + deductions) {
				response = ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE.getValue());
				return response;
			}
			
			/*Prepared the list of items needs to send back to the device for confirmation*/
			String requestAmountDisplay = UIUtil.getConvertedAmountInString(reqAmount)
					+ SPACE_STRING + TransactionServiceHelper.getCurrencyCode(request, toWalletCurrency);
			String actuaAmountDisplay = UIUtil.getConvertedAmountInString(actualAmount)
					+ SPACE_STRING + TransactionServiceHelper.getCurrencyCode(request, fromWalletCurrency);
			String taxFee = UIUtil.getConvertedAmountInString(deductions)
					+ SPACE_STRING + TransactionServiceHelper.getCurrencyCode(request, fromWalletCurrency);
			String totalDeductions = UIUtil.getConvertedAmountInString(actualAmount + deductions)
					+ SPACE_STRING + TransactionServiceHelper.getCurrencyCode(request, fromWalletCurrency);
	
			SelfTransferInputResponse selfTransferInputResponse = new SelfTransferInputResponse();
			
			selfTransferInputResponse.setRequestedAmount(UIUtil.getConvertedAmountInString(reqAmount));
			selfTransferInputResponse.setRequestedAmountDisplay(requestAmountDisplay);
			selfTransferInputResponse.setActualAmount(UIUtil.getConvertedAmountInString(actualAmount));
			selfTransferInputResponse.setActualAmountDisplay(actuaAmountDisplay);
			selfTransferInputResponse.setTaxfee(taxFee);
			selfTransferInputResponse.setTotalDeductions(totalDeductions);
    		/*Response the sent for user confirmation to complete transaction*/
			response = ServerUtility.papulateSuccessCode(request, 
    					ServerProcessorStatus.SELF_TRANSFER_CONFORMATION_REQUIRED.getValue(), selfTransferInputResponse);
			return response;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.SELF_TRANSFER_FAILED.getValue());
			return response;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER, "selftransfer", response);
			LOGGER.info(" >>>>> Exiting selftransfer <<<<< ");
		}
	}

	@Path("/selftransferconfirm")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response selfTransferConfirm(@Context HttpServletRequest request,
			String selfTransferConfirmInput) {
		
		papulateServices(request);
		LOGGER.info(" >>>>> Entering selfTransferConfirm <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"selfTransferConfirm",selfTransferConfirmInput);
		Authentication authentication = null;
		RestRequest restRequest = null;
		Long transactionType = null;
		Response response = null;
		boolean exit = false;
		
		try {

			if (CommonWebserviceUtil.isEmpty(selfTransferConfirmInput)) {
				response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(selfTransferConfirmInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return response;
		}finally {
			if (exit) {
				CommonRestServiceHelper.responseLog(LOGGER, "selfTransferConfirm", response);
				LOGGER.info(" >>>>> Exiting selfTransferConfirm <<<<< ");
			}
		}
		LOGGER.info( "fromWallet:" + restRequest.getFromWallet()
				+ "towallet :" + restRequest.getTowallet()
				+ " RequestedAmount : " + restRequest.getRequestedAmount()
				+ USER_EMAIL + restRequest.getEmail() + USER_TYPE
				+ restRequest.getUserType() + "SimNumber"
				+ restRequest.getSimNumber());
		
		
		try {
			String email = restRequest.getEmail();
			String userType = restRequest.getUserType();
			
			response = CommonRestServiceHelper.validAmountCheck(request, restRequest.getRequestedAmount());
			if(response!=null){
				return response;
			}
			response = CommonRestServiceHelper.validAmountCheck(request, restRequest.getActualAmount());
			if(response!=null){
				return response;
			}
			response = CommonRestServiceHelper.validWalletCheck(request, restRequest.getFromWallet(), restRequest.getTowallet());
			if(response!=null){
				return response;
			}
			authentication = commonService.getAuthentication(email, userType);			
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), 
					restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			}
			Long authId = authentication.getId();
			Long status = authentication.getStatus();
		    LOGGER.info(AUTHENTICATION_ID +":" + authId);
		    response = CommonRestServiceHelper.checkTransaction(request, userType, status);
			if(response != null){
				return response;
			}
			/*Find out the type of transaction based on user type*/   
			if (CUSTOMER_USER_TYPE.equals(userType)) {
				transactionType = WalletTransactionTypes.P_TO_S;
			} else if (MERCHANT_USER_TYPE.equals(userType)) {
				transactionType = WalletTransactionTypes.M_TO_S;
			}

			Map<Long, UserWallet> userWallets = CommonUtil.getWalletMap(commonService.getUserWallets(authId));
			String walletBalances = SendMoneyHelper.getAllWalletBalances(userWallets);

			SelfTransferForm selfTransferForm = new SelfTransferForm();
			selfTransferForm.setRequestedAmount(restRequest.getRequestedAmount());
			
			selfTransferForm.setFromWallet(restRequest.getFromWallet());
			selfTransferForm.setTowallet(restRequest.getTowallet());
			selfTransferForm.setTransactionType(transactionType);
			selfTransferForm.setWalletBalances(walletBalances);
			
			Response responce = checkNegativeBalance(request, authId, restRequest);
			if(responce != null){
				return responce;
			}
			selfTransferForm.setActualAmount(restRequest.getActualAmount());
			SelfTransferDto selfTransferDto = SendMoneyHelper
					.convertSelfTransferFormToDto(request, selfTransferForm, authId, restRequest.getSimNumber(), restRequest.getImeiNumber(), TypeOfRequest.MOBILE);
			
			/* Validate MPIN */
			if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());
				return response;
			}
			commonService.validateMpin(email, userType, restRequest.getmPin());
			sendMoneyService.createSelfMoney(selfTransferDto);
			response = ServerUtility.papulateSuccessCode(request, 
							ServerProcessorStatus.SELF_TRANSFERRED_TRANSACTION_SUCCESS.getValue(), null);
			return response;
		  } catch(WalletException ex){
			 LOGGER.error(ex.getMessage() , ex );
			 String eMsg = null;
				if(ex.getMessage().equals(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET)) {
					eMsg = ServerProcessorStatus.USER_NOT_REGISTER_AS_MOBILE_WALLET.getValue();
				}
				if(ex.getMessage().equals(CommonConstrain.MPIN_MATCH_FAIL)){
					eMsg = ServerProcessorStatus.MPIN_MATCH_FAIL.getValue();
				}
				if(ex.getMessage().equals(CommonConstrain.USER_DOES_NOT_EXIST)){
					eMsg = ServerProcessorStatus.USER_DOES_NOT_EXIST.getValue();
				}
				response = ServerUtility.papulateErrorCode(request, eMsg);
				return response;
		 } catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.SELF_TRANSFER_FAILED.getValue());
			return response;
		 }
		finally{
				CommonRestServiceHelper.responseLog(LOGGER,"selfTransferConfirm",response);
				LOGGER.info(" >>>>> Exiting selfTransferConfirm <<<<< ");
			}
	}

	/**
	 * This rest service method return the list of supporting wallet list to the device.
	 * This method will be using at the self transfer functionality.
	 * @param request
	 * @param supportWalletsInput
	 * @return
	 * @throws WalletException
	 */
	@Path("/listofwallets")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response listOfSupportWallets(@Context HttpServletRequest request,
			String supportWalletsInput) throws WalletException {

		papulateServices(request);
		LOGGER.info(" >>>>> Entering listOfSupportWallets <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"listOfSupportWallets",supportWalletsInput);
		Authentication authentication = null;
		RestRequest restRequest = null;
		Response response = null;
		boolean exit = false;
		
		try {
			if (CommonWebserviceUtil.isEmpty(supportWalletsInput)) {
				response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;
			}

			Gson gson = new Gson();
			restRequest = gson.fromJson(supportWalletsInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return response;
		} finally {
			if (exit) {
				CommonRestServiceHelper.responseLog(LOGGER, "listOfSupportWallets", response);
				LOGGER.info(" >>>>> Exiting listOfSupportWallets <<<<< ");
			}
		}
		LOGGER.info(USER_EMAIL + restRequest.getEmail() + USER_TYPE
				+ restRequest.getUserType());

		String email = restRequest.getEmail();
		String userType = restRequest.getUserType();

		try {
			authentication = commonService.getAuthentication(email, userType);
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), 
					restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			}
			Long authId = authentication.getId();
			Long status = authentication.getStatus();
		    LOGGER.info(AUTHENTICATION_ID +":" + authId);
		    Response responce = CommonRestServiceHelper.checkTransaction(request, userType, status);
			if(responce != null){
				return responce;
			} 
			Long fromWalletCurrency = restRequest.getFromWallet();
			Map<Long, UserWallet> userWallets = CommonUtil.getWalletMap(commonService.getUserWallets(authId));

			List<Object> list = populateSelfTransferMapValues(request, userWallets, fromWalletCurrency, null);
			if (null == list || list.size() <= 0) {
				response = ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.USER_DOES_NOT_HAVE_WALLET.getValue());
				return response;
			} else {
				response =  ServerUtility.papulateSuccessCode(request, 
						ServerProcessorStatus.RECORDS_FOUND.getValue(), list); 
				return response;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(
					request, ServerProcessorStatus.ERROR_WHILE_GETING_WALLETS.getValue());
			return response;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"listOfSupportWallets",response);
			LOGGER.info(" >>>>> Exiting listOfSupportWallets <<<<< ");
		}
	}

	/**
	 * This is the common post method for all type of transaction, here we have to
	 * give as input the type of destination i.e customer / merchant / non registered person
	 * @param request
	 * @param transferMoneyInput
	 * @return
	 * @throws WalletException
	 */
	@Path("/tosingle")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transferMoneytoSingle(@Context HttpServletRequest request, String transferMoneyInput) throws WalletException {
		
		
		papulateServices(request);
		LOGGER.info(" >>>>> Entering transferMoneytoSingle <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"transferMoneytoSingle",transferMoneyInput);
		CustomerDto senderCustomerDto = null;
		CustomerDto receiverCustomerDto = null;
		Authentication receiverAuthentication = null;
		MerchantDto receiverMerchantDto = null;	
		RestRequest restRequest = null;
		Response response = null;
		boolean exit = false;
		
		String email =  null;
		String sendMoneyEmail = null;
		String userType = null;
		Long requestedCurrency = null;
		String requestAmount = null;
		Long destinationType = null;
		FundTransferInputResponse fundTransferInputResponse = new FundTransferInputResponse();
			
		try{
			if(CommonWebserviceUtil.isEmpty(transferMoneyInput)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(transferMoneyInput, RestRequest.class);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return response;
		}
		finally {
			if (exit) {
				CommonRestServiceHelper.responseLog(LOGGER, "transferMoneytoSingle", response);
				LOGGER.info(" >>>>> Exiting transferMoneytoSingle <<<<< ");
			}
		}
		
		

		try {
			
			response = CommonRestServiceHelper.validAmountCheck(request, restRequest.getRequestedAmount());
			if(response!=null){
				exit = true;
				return response;
			}
			
		
			
			senderCustomerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			if(null == senderCustomerDto){
				response = ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.CUSTOMER_MOBILE_NOT_EXIST.getValue());
				exit = true;
				return response;
			}
			restRequest.setEmail(senderCustomerDto.getEmailId());
			
			if(null == restRequest.getRecieverPhoneNo()){
				response = ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.MOBILE_NUMBER_SHOULD_NOT_BE_EMPTY.getValue());
				exit = true;
				return response;
			}
			
			userType = restRequest.getUserType();
			destinationType = restRequest.getDestinationType() != null ? restRequest.getDestinationType():DestinationTypes.REGISTERED_PERSON;
			if (DestinationTypes.REGISTERED_PERSON.equals(destinationType)) {
				receiverCustomerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getRecieverPhoneNo());
				if(null == receiverCustomerDto){
					response =  ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.CUSTOMER_MOBILE_NOT_EXIST.getValue());
					exit = true;
					return response;
				}
				receiverAuthentication = commonService.getAuthentication(receiverCustomerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
				if(null == receiverAuthentication){
					response = ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.CUSTOMER_MOBILE_NOT_EXIST.getValue());
					exit = true;
					return response;
				}
				restRequest.setReceiverEmail(receiverAuthentication.getEmailId());
			}
			
			if (DestinationTypes.MERCHANT.equals(destinationType)) {
				receiverMerchantDto = merchantService.getMerchantByPhoneNumber(restRequest.getPhoneCode(), restRequest.getRecieverPhoneNo());
				if(null == receiverMerchantDto){
					response = ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.MERCHANT_MOBILE_NOT_EXIST.getValue());
					exit = true;
					return response;
				}
				receiverAuthentication = commonService.getAuthentication(receiverMerchantDto.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE);
				if(null == receiverAuthentication){
					response = ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.MERCHANT_MOBILE_NOT_EXIST.getValue());
					exit = true;
					return response;
				}
				restRequest.setReceiverEmail(receiverAuthentication.getEmailId());
			}
			/*Checking Authorized Email User and Registered Member */
			email =  restRequest.getEmail();
			sendMoneyEmail = restRequest.getReceiverEmail();
			
			requestedCurrency = restRequest.getRequestedCurrency();
			
			requestAmount = restRequest.getRequestedAmount();
			
			//authentication = commonService.getAuthentication(email, userType);
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, receiverAuthentication, Boolean.TRUE);
			if(response != null){
				exit = true;
				return response;
			}
			Long status = receiverAuthentication.getStatus();
		    LOGGER.info("status " + status);
		    response = CommonRestServiceHelper.checkTransaction(request, userType, status);
			if(response != null){
				exit = true;
				return response;
			} 
			if (phoneNumber.getPnumber().equals(restRequest.getRecieverPhoneNo())) {
				
				if (userType.equals(CUSTOMER_USER_TYPE) && destinationType.equals(DestinationTypes.REGISTERED_PERSON)) {
					response = ServerUtility.papulateErrorCode(request,
									ServerProcessorStatus.NOT_ALLOWED_MOBILE_NUMBER.getValue());
					exit = true;
					return response;
				} else if (userType.equals(MERCHANT_USER_TYPE) && destinationType.equals(DestinationTypes.MERCHANT)) {
					response = ServerUtility.papulateErrorCode(request,
									ServerProcessorStatus.NOT_ALLOWED_MOBILE_NUMBER.getValue());
					exit = true;
					return response;
				}
			}
		
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			response = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.CUSTOMER_MOBILE_NOT_EXIST.getValue());
			exit = true;
			return response;
		}finally {
			if (exit) {
				CommonRestServiceHelper.responseLog(LOGGER, "transferMoneytoSingle", response);
				LOGGER.info(" >>>>> Exiting transferMoneytoSingle <<<<< ");
			}
		}
		SendMoneyForm sendMoneyForm  = new SendMoneyForm();    
		sendMoneyForm.setRequestedAmount(requestAmount);
		sendMoneyForm.setRequestedCurrency(requestedCurrency);
		sendMoneyForm.setEmailId(sendMoneyEmail);
		sendMoneyForm.setDestinationType(destinationType);
		sendMoneyForm.setActualCurrency(requestedCurrency);
		sendMoneyForm.setMessage(restRequest.getMessage());
	  
		
		try {
			
			response = SendMoneyHelper.getRegisteredOrNotRegisteredOrMerchant(request, sendMoneyForm, commonService);
			if(response!= null){
				exit = true;
				return response ; 
			}
			
			SendMoneyDto sendMoneyDto = constructSendMoneyDto(sendMoneyForm,
					request, userType, email, restRequest.getSimNumber(),restRequest.getImeiNumber());
	
			sendMoneyForm.setRequestedAmount(UIUtil.getConvertedAmountInString(Double.parseDouble(requestAmount)));
			UserWallet userWallet = commonService.getUserWallet(sendMoneyDto.getSenderAuthId(),
					sendMoneyDto.getRequestedCurrency());
			Double deductions = ZERO_DOUBLE;
			Double reqAmount = sendMoneyDto.getRequestedAmount();
			if (userWallet != null) {
				deductions = feeMgmtService.calcuateDeductions(reqAmount,
						sendMoneyDto.getCountryId(),
						sendMoneyDto.getRequestedCurrency(),
						sendMoneyDto.getTransactionType(), Boolean.FALSE);
			}
			if (null == userWallet 
					|| userWallet.getAmount() < reqAmount + deductions) {
				response =  ServerUtility.papulateErrorCode(request, 										
						ServerProcessorStatus.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE.getValue());
				exit = true;
				return response ; 
			}
			sendMoneyForm.setActualCurrency(sendMoneyForm.getRequestedCurrency());
			sendMoneyForm.setActualAmount(sendMoneyForm.getRequestedAmount());
	
			fundTransferInputResponse.setRequestedAmount(sendMoneyForm.getRequestedAmount());
			fundTransferInputResponse.setDeductions(UIUtil.getConvertedAmountInString(deductions));
			fundTransferInputResponse.setTotalAmountFeeTax(UIUtil.getConvertedAmountInString(reqAmount + deductions));
		   
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
			String errorMessage = null;
			if (e.getMessage().contains(C_EMAIL_NOT_REGISTERED_ERR_MSG)) {
				errorMessage = ServerProcessorStatus.C_EMAIL_NOT_REGISTERED_ERR_MSG.getValue();
			} else if (e.getMessage().contains(CUSTOMER_MOBILE_NOT_EXIST)) {
				errorMessage = ServerProcessorStatus.CUSTOMER_MOBILE_NOT_EXIST.getValue();
			} else if (e.getMessage().contains(M_EMAIL_NOT_REGISTERED_ERR_MSG)) {
				errorMessage = ServerProcessorStatus.M_EMAIL_NOT_REGISTERED_ERR_MSG.getValue();
			} else {
				errorMessage = ServerProcessorStatus.TRANSACTION_FAILED.getValue();
			}
			response = ServerUtility.papulateErrorCode(request, errorMessage);
			exit = true;
			return response;
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.UNKNOW_ERROR.getValue());
			exit = true;
			return response;
		}
		finally {
			if (exit) {
				CommonRestServiceHelper.responseLog(LOGGER, "transferMoneytoSingle", response);
				LOGGER.info(" >>>>> Exiting transferMoneytoSingle <<<<< ");
			}
		}
		try{
			fundTransferInputResponse.setRequestedCurrency(sendMoneyForm.getRequestedCurrency());
			fundTransferInputResponse.setRequestedCurrencyCode(MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(sendMoneyForm.getRequestedCurrency()));
			
			Map<Long,String> userTypes = MasterDataUtil.getSimpleDataMap(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), MasterDataConstants.MD_USER_TYPES);
			Map<Long, String> destinationTypes = new HashMap<Long, String>();
			for(Map.Entry<Long, String> entry : userTypes.entrySet()){
				if(!(entry.getKey().equals(ADMIN_USER_TYPE_ID))){
					destinationTypes.put(entry.getKey(), entry.getValue());
				}
			}
			fundTransferInputResponse.setDestinationType(sendMoneyForm.getDestinationType());
			fundTransferInputResponse.setDestinationTypeName(destinationTypes.get(sendMoneyForm.getDestinationType()));
			
			response = ServerUtility.papulateSuccessCode(request, 
					ServerProcessorStatus.TRANSACTION_CONFORMATION_REQUIRED.getValue(), fundTransferInputResponse); 
			return response;
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.UNKNOW_ERROR.getValue());
			return response;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"transferMoneytoSingle",response);
			LOGGER.info(" >>>>> Exiting transferMoneytoSingle <<<<< ");
		}
	}
	
	/**
	 * This is the common post confirmation method for all type of transaction, here we have to
	 * give as input the type of destination i.e customer / merchant / non registered person.
	 * @param request
	 * @param transferConfirmInput
	 * @return
	 * @throws WalletException
	 */
	@Path("/tosingleconfirm")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response transferMoneyToSingleConfirmation(@Context HttpServletRequest request,
    		String transferConfirmInput)throws WalletException {
		
		papulateServices(request);
		LOGGER.info(" >>>>> Entering transferMoneyToSingleConfirmation <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"transferMoneyToSingleConfirmation",transferConfirmInput);
    	RestRequest restRequest = null;
		Response response = null;
		CustomerDto senderCustomerDto = null;
		CustomerDto receiverCustomerDto = null;
		Authentication receiverAuthentication = null;
		MerchantDto receiverMerchantDto = null;
		boolean exit = false;
		
		String email =  null;
		String sendMoneyEmail = null;
		String userType = null;
		Long requestedCurrency = null;
		String requestAmount = null;
		Long destinationType = null;
		String atualAmount = null;
	
		
		try{
			if(CommonWebserviceUtil.isEmpty(transferConfirmInput)){
				 response =  ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				 exit = true;
				 return response;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(transferConfirmInput, RestRequest.class);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			 exit = true;
			 return response;
		}
		finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"transferMoneyToSingleConfirmation",response);
				LOGGER.info(" >>>>> Exiting transferMoneyToSingleConfirmation <<<<< ");
			}			
		}
			
		try{
			
			response = CommonRestServiceHelper.validAmountCheck(request,restRequest.getRequestedAmount());
			if(response!=null){
				exit = true;
				return response;
			}
				
			userType = restRequest.getUserType();			
			senderCustomerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			if(null == senderCustomerDto){
				response =  ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.CUSTOMER_MOBILE_NOT_EXIST.getValue());
				exit = true;
				return response;
			}
			restRequest.setEmail(senderCustomerDto.getEmailId());
			
			if(null == restRequest.getRecieverPhoneNo()){
				response = ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.MOBILE_NUMBER_SHOULD_NOT_BE_EMPTY.getValue());
				exit = true;
				return response;
			}
			
			userType = restRequest.getUserType();
			destinationType = restRequest.getDestinationType() != null ? restRequest.getDestinationType():1L;
			if (DestinationTypes.REGISTERED_PERSON.equals(destinationType)) {
				receiverCustomerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getRecieverPhoneNo());
				if(null == receiverCustomerDto){
					response = ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.CUSTOMER_MOBILE_NOT_EXIST.getValue());
					exit = true;
					return response;
				}
				receiverAuthentication = commonService.getAuthentication(receiverCustomerDto.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
				if(null == receiverAuthentication){
					response = ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.CUSTOMER_MOBILE_NOT_EXIST.getValue());
					exit = true;
					return response;
				}
				restRequest.setReceiverEmail(receiverAuthentication.getEmailId());
			}
			
			if (DestinationTypes.MERCHANT.equals(destinationType)) {
				receiverMerchantDto = merchantService.getMerchantByPhoneNumber(restRequest.getPhoneCode(), restRequest.getRecieverPhoneNo());
				
				if(null == receiverMerchantDto){
					response =  ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.MERCHANT_MOBILE_NOT_EXIST.getValue());
					exit = true;
					return response;
				}
				receiverAuthentication = commonService.getAuthentication(receiverMerchantDto.getEmailId(), GlobalLitterals.MERCHANT_USER_TYPE);
				if(null == receiverAuthentication){
					response = ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.MERCHANT_MOBILE_NOT_EXIST.getValue());
					exit = true;
					return response;
				}
				//atualAmount = restRequest.getTotalAmountFeeTax();
				restRequest.setReceiverEmail(receiverAuthentication.getEmailId());
			}
			/*Checking Authorized Email User and Registered Member */
			email =  restRequest.getEmail();
			sendMoneyEmail = restRequest.getReceiverEmail();
			
			requestedCurrency = restRequest.getRequestedCurrency();
			
			requestAmount = restRequest.getRequestedAmount();
			//atualAmount = restRequest.getTotalAmountFeeTax();
			atualAmount = requestAmount;
			
			PhoneNumber phoneNumber = commonService.getPhoneNumber(email, restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, receiverAuthentication, Boolean.TRUE);
			if(response != null){
				exit = true;
				return response;
			}
			Long status = receiverAuthentication.getStatus();
		    LOGGER.info("Status " + status);
		    response = CommonRestServiceHelper.checkTransaction(request, restRequest.getUserType(), 
		    		status);
			if(response != null){
				exit = true;
				return response;
			}
			if (phoneNumber.getPnumber().equals(restRequest.getRecieverPhoneNo())) {
				if (userType.equals(CUSTOMER_USER_TYPE) && restRequest.getDestinationType().equals(DestinationTypes.REGISTERED_PERSON)) {
					response = ServerUtility.papulateErrorCode(request,
									ServerProcessorStatus.NOT_ALLOWED_MOBILE_NUMBER.getValue());
					exit = true;
					return response;
				} else if (restRequest.getUserType().equals(MERCHANT_USER_TYPE) && restRequest.getDestinationType().equals(DestinationTypes.MERCHANT)) {
					response = ServerUtility.papulateErrorCode(request,
									ServerProcessorStatus.NOT_ALLOWED_MOBILE_NUMBER.getValue());
					exit = true;
					return response;
				}
			}
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"transferMoneyToSingleConfirmation",response);
				LOGGER.info(" >>>>> Exiting transferMoneyToSingleConfirmation <<<<< ");
			}			
		}
    	
    	SendMoneyForm sendMoneyForm = new SendMoneyForm();
    	sendMoneyForm.setRequestedAmount(requestAmount);
		sendMoneyForm.setRequestedCurrency(requestedCurrency);
		sendMoneyForm.setEmailId(sendMoneyEmail);
		sendMoneyForm.setDestinationType(destinationType);
		sendMoneyForm.setActualCurrency(requestedCurrency);
		sendMoneyForm.setMessage(restRequest.getMessage());
		sendMoneyForm.setActualAmount(atualAmount);
	  
    	
    	
    	try{
    		
    		response = SendMoneyHelper.getRegisteredOrNotRegisteredOrMerchant(request, sendMoneyForm, commonService);
        	if(response!= null){
        	 return response ; 
        	}
			SendMoneyDto sendMoneyDto = constructSendMoneyDto(sendMoneyForm, request, restRequest.getUserType(), restRequest.getEmail(),
					restRequest.getSimNumber(),restRequest.getImeiNumber());
			sendMoneyForm.setRequestedAmount(UIUtil.getConvertedAmountInString(Double.parseDouble(requestAmount)));
			
			UserWallet userWallet = commonService.getUserWallet(sendMoneyDto.getSenderAuthId(), sendMoneyDto.getRequestedCurrency());		
			Double reqAmount = sendMoneyDto.getRequestedAmount();
			
			if(userWallet == null || userWallet.getAmount() < reqAmount){
				response =  ServerUtility.papulateErrorCode(request,
						ServerProcessorStatus.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE.getValue());
				 return response ; 
			}
			sendMoneyForm.setActualCurrency(sendMoneyForm.getRequestedCurrency());
			sendMoneyForm.setActualAmount(UIUtil.getConvertedAmountInString(Double.parseDouble(atualAmount)));
			
			if(sendMoneyDto.getMessage().equals(EMPTY_STRING)){
				//PAYMENT_SENT_DEFAULT_MSG
				sendMoneyDto.setMessage(PAYMENT_SENT_DEFAULT_MSG);
			}
			/* Validate MPIN 
			if(RestCustomValidation.numberValidator(restRequest.getmPin(), null , Common.M_PIN_MAX_LENGHT)){
				return ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_MPIN_FORMAT.getValue());
			}								
			commonService.validateMpin(restRequest.getEmail(), restRequest.getUserType(), restRequest.getmPin());*/
			/*Validate OTP*/
			if(RestCustomValidation.numberValidator(restRequest.getOTP(), null , utilService.getOtpLength())){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_OTP_FORMAT.getValue());
				 return response ; 
			}
			restRequest.setEmail(senderCustomerDto.getEmailId());
			
			String bypassSmsGateway = utilService.getSmsGatewayBypass();
			Boolean isValidOtp = false;
			if("N".equalsIgnoreCase(bypassSmsGateway)){
				isValidOtp = smsService.authenticateOTP(CommonRestServiceHelper.getOtpDto(restRequest, senderCustomerDto.getId()));
			}else{
				
				isValidOtp = true;
			}
			
			
			
			
			if(isValidOtp){
				sendMoneyDto = sendMoneyService.createSendMoney(sendMoneyDto);
				restRequest.setTransactionId(sendMoneyDto.getTransactionId());
				restRequest.setTransactionDate(sendMoneyDto.getTransactionDate());
				if(sendMoneyDto.getTransactionType().equals(WalletTransactionTypes.P_TO_P)){
					restRequest.setTransactionTypeName("P_TO_P");
				}else if(sendMoneyDto.getTransactionType().equals(WalletTransactionTypes.P_TO_M)){
					restRequest.setTransactionTypeName("P_TO_M");
				}
				response = ServerUtility.papulateSuccessCode(request, 
						ServerProcessorStatus.TRANSACTION_SUCCESS.getValue(), restRequest);
				return response ; 
			}else{
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.INVALID_OTP_OR_EXPIRED_PLEASE_TRY_AGAIN.getValue());
				return response ;
			}
		} catch (WalletException e) {
			LOGGER.error(e.getMessage(), e);
			response = ServerUtility.papulateErrorCode(request, TransactionServiceHelper.papulateErrorMessage(e));
			return response ;
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.TRANSACTION_FAILED.getValue());
			return response ;
		}finally {
		
				CommonRestServiceHelper.responseLog(LOGGER,"transferMoneyToSingleConfirmation",response);
				LOGGER.info(" >>>>> Exiting transferMoneyToSingleConfirmation <<<<< ");
					
		}
    }
	
	/**
	 * @param sendMoneyForm
	 * @param request
	 * @param senderUserType
	 * @param senderEmailId
	 * @param simNumber
	 * @param imeiNumber
	 * @return
	 * @throws WalletException
	 */
	private SendMoneyDto constructSendMoneyDto(SendMoneyForm sendMoneyForm,
			HttpServletRequest request, String senderUserType, String senderEmailId, String simNumber ,String imeiNumber )throws WalletException {
	
		Long typeOfRequest = MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
		        (Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.MOBILE.getValue());
		Long destinationType = sendMoneyForm.getDestinationType();
		SendMoneyDto sendMoneyDto = new SendMoneyDto();
		/*For mobile transaction populate the SIM number*/
		sendMoneyDto.setSimNumber(simNumber);
		sendMoneyDto.setImeiNumber(imeiNumber);
		
		sendMoneyDto.setTypeOfRequest(typeOfRequest);
		sendMoneyDto.setEmailId(sendMoneyForm.getEmailId());
		sendMoneyDto.setRequestedAmount(Double.parseDouble(sendMoneyForm.getRequestedAmount()));
		sendMoneyDto.setRequestedCurrency(sendMoneyForm.getRequestedCurrency());
		String amount = sendMoneyForm.getActualAmount();
		if(amount != null){
			sendMoneyDto.setActualAmount(Double.parseDouble(amount));
			sendMoneyDto.setActualCurrency(sendMoneyForm.getActualCurrency());
		}
		sendMoneyDto.setMessage(sendMoneyForm.getMessage());
		sendMoneyDto.setDestinationType(sendMoneyForm.getDestinationType());
		sendMoneyDto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
		sendMoneyDto.setCurrencyCode(MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(sendMoneyForm.getRequestedCurrency()));
		
		Long countryId = null;
		String payerName = null;
		Long uType = null;
		Long senderAuthId = null;		
		String personName = null;
	
		if(CUSTOMER_USER_TYPE.equals(senderUserType)){
			CustomerDto customerDto = customerService.getRegisteredMember(senderEmailId, senderUserType);
			countryId = customerDto.getCountry();
			payerName = customerDto.getFullName();
			senderAuthId = customerDto.getAuthenticationId();
			uType = CUSTOMER_USER_TYPE_ID; 
		} else if(MERCHANT_USER_TYPE.equals(senderUserType)){
			MerchantDto merchantDto = merchantService.getRegisteredMember(senderEmailId, senderUserType);
			countryId = merchantDto.getCountryBO();
			payerName = merchantDto.getBusinessLegalname();
			senderAuthId = merchantDto.getAuthenticationId();
			uType = MERCHANT_USER_TYPE_ID;
		}
		if(sendMoneyForm.getDestinationType().equals(DestinationTypes.REGISTERED_PERSON)){
			personName = customerService.getPersonName(sendMoneyForm.getEmailId(), AttributeConstants.CUSTOMER_USER_TYPE);
			sendMoneyDto.setPayeeName(personName);
		} else if(sendMoneyForm.getDestinationType().equals(DestinationTypes.MERCHANT)){
			personName = merchantService.getPersonName(sendMoneyForm.getEmailId(), AttributeConstants.MERCHANT_USER_TYPE);
			sendMoneyDto.setPayeeName(personName);
		}		
		sendMoneyDto.setSenderAuthId(senderAuthId);
		sendMoneyDto.setCountryId(countryId);
		sendMoneyDto.setPayerName(payerName);
		sendMoneyDto.setSenderUserType(uType);
		
		String receiverUserType = null;
		Long receiverAuthId = null;
		Long transactionType = null;
		String payeeName = null;
		String receiverEmailId = sendMoneyForm.getEmailId();
		
		//to register customer
		if(DestinationTypes.REGISTERED_PERSON.equals(destinationType)){
			receiverUserType = CUSTOMER_USER_TYPE;
			CustomerDto customerDto = customerService.getRegisteredMember(receiverEmailId, receiverUserType);
			receiverAuthId = customerDto.getAuthenticationId();
			payeeName = customerDto.getFullName();
			
			if(CUSTOMER_USER_TYPE.equals(senderUserType)){
				transactionType = WalletTransactionTypes.P_TO_P;	
			} else {
				transactionType = WalletTransactionTypes.M_TO_P;	
			}
		} else if(DestinationTypes.NON_REGISTERED_PERSON.equals(destinationType)){
			//to non register customer
			receiverUserType = CUSTOMER_USER_TYPE;
			payeeName = receiverEmailId;
			
			if(CUSTOMER_USER_TYPE.equals(senderUserType)){
				transactionType = WalletTransactionTypes.P_TO_NP;	
			} else {
				transactionType = WalletTransactionTypes.M_TO_NP;	
			}
		} else if(DestinationTypes.MERCHANT.equals(destinationType)){
			//to merchant
			receiverUserType = MERCHANT_USER_TYPE;
			MerchantDto merchantDto = merchantService.getRegisteredMember(receiverEmailId, receiverUserType);
			receiverAuthId = merchantDto.getAuthenticationId();
			payeeName = merchantDto.getBusinessLegalname();
			
			if(CUSTOMER_USER_TYPE.equals(senderUserType)){
				transactionType = WalletTransactionTypes.P_TO_M;	
			} else {
				transactionType = WalletTransactionTypes.M_TO_M;	
			}
		}
		sendMoneyDto.setReceiverAuthId(receiverAuthId);
		sendMoneyDto.setPayeeName(payeeName);
		sendMoneyDto.setTransactionType(transactionType);	
		
  		return sendMoneyDto;
	} 
	
	/**
	 * This will prepared the supporting list of wallets 
	 * i.e "From Wallet" and "To Wallet" 
	 * @param request
	 * @param userWallets
	 * @param selectWallet
	 * @param languageId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<Object> populateSelfTransferMapValues(HttpServletRequest request, 
			Map<Long, UserWallet> userWallets, Long selectWallet, Long languageId) {
		/*Get all wallet map supported by system*/
		Map<Long, String> allWallets = MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), languageId);
		
		List<Map<String, String>> fromWalletsList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> toWalletsList = new ArrayList<Map<String, String>>();
		
		Map<String, String> fromWallets = null;
		Map<String, String> toWallets = null;
		
		/*Prepared the fromWallet map from userWallet*/
		Set qset = userWallets.entrySet();
		Iterator i = qset.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			fromWallets = new HashMap<String, String>();
			fromWallets.put(CURRENCY_ID, me.getKey().toString());
			fromWallets.put(CURRENCY_CODE, allWallets.get(me.getKey()));
			fromWalletsList.add(fromWallets);
		}
		/*Removed the selected wallet from all wallet map*/
		if (selectWallet != null) {
			allWallets.remove(selectWallet);
		}
		/*Prepared the toWallet map from userWallet*/
		Set set = allWallets.entrySet();
		Iterator j = set.iterator();
		while (j.hasNext()) {
			Map.Entry me = (Map.Entry) j.next();
			toWallets = new HashMap<String, String>();
			toWallets.put(CURRENCY_ID, me.getKey().toString());
			toWallets.put(CURRENCY_CODE, allWallets.get(me.getKey()));
			toWalletsList.add(toWallets);
		}
		Map<String, List<Map<String, String>>> walletMap = new HashMap<String, List<Map<String, String>>>();
		/*Prepared the final map and it to the device*/
		walletMap.put(TO_WALLETS, toWalletsList);
		walletMap.put(FROM_WALLETS, fromWalletsList);

		List<Object> listOfwallets = new ArrayList<Object>();

		listOfwallets.add(walletMap);

		return listOfwallets;
	}
	
	/**This method will check for negative balance for self transfer confirmation
	 * @param request
	 * @param authId
	 * @param restRequest
	 * @return
	 * @throws WalletException
	 */
	private Response checkNegativeBalance(HttpServletRequest request,Long authId,RestRequest restRequest) throws WalletException{
		Long transactionType = null;
		Long uType = null;
		Long countryId = null;
		Double actualAmount = ZERO_DOUBLE;
		
		if (CUSTOMER_USER_TYPE.equals(restRequest.getUserType())) {
			LOGGER.info(" customer user type : ");
			CustomerDto customerDto = customerService.getRegisteredMember(restRequest.getEmail(), restRequest.getUserType());
			countryId = customerDto.getCountry();
			LOGGER.info(" countryID: " + countryId);
			uType = CUSTOMER_USER_TYPE_ID;
			transactionType = WalletTransactionTypes.P_TO_S;
		} else if (MERCHANT_USER_TYPE.equals(restRequest.getUserType())) {
			MerchantDto merchantDto = merchantService.getRegisteredMember(restRequest.getEmail(), restRequest.getUserType());
			countryId = merchantDto.getCountryBO();
			uType = MERCHANT_USER_TYPE_ID;
			transactionType = WalletTransactionTypes.M_TO_S;
		}
		if (!(sendMoneyService.validateThresholdLimitForSelfTransfer(
				countryId, restRequest.getTowallet(), transactionType, Double.parseDouble(restRequest.getRequestedAmount()), uType))) {
			return ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT.getValue());
		}
		actualAmount = Double.parseDouble(restRequest.getActualAmount());
	    UserWallet userWallet = commonService.getUserWallet(authId, restRequest.getFromWallet());
		if (null == userWallet || userWallet.getAmount() < actualAmount) {
			return ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE.getValue());
		}
		return null;
	}
	
	
	/**
	 * Get service instances from request object
	 * 
	 * @param request
	 */
	private void papulateServices(HttpServletRequest request) {
		commonService = (CommonService) ServerUtility.getServiceInstance(request.getSession(), COMMON_SERVICE);
		customerService = (CustomerService) ServerUtility.getServiceInstance(request.getSession(), CUSTOMER_SERVICE);
		merchantService = (MerchantService) ServerUtility.getServiceInstance(request.getSession(), MERCHANT_SERVICE);
		sendMoneyService = (SendMoneyService) ServerUtility.getServiceInstance(request.getSession(), SENDMONEY_SERVICE);
		feeMgmtService = (FeeMgmtService) ServerUtility.getServiceInstance(request.getSession(), FEEMGMT_SERVICE);
		smsService = (SmsService) ServerUtility.getServiceInstance(request.getSession(), SMS_SERVICE);
		utilService = (UtilService) ServerUtility.getServiceInstance(request.getSession(), UTIL_SERVICE);
	}
}
