package com.tarang.mwallet.rest.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.masterdata.util.CountryIds;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.mwallet.rest.services.model.RestRequest;
import com.tarang.mwallet.rest.services.util.CommonRestServiceHelper;
import com.tarang.mwallet.rest.services.util.CommonWebserviceUtil;
import com.tarang.mwallet.rest.services.util.Constants;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;
import com.tarang.mwallet.rest.services.util.ServerUtility;

import net.sf.json.JSONObject;

/**
 * @author kedarnathd
 * This rest service will hold the list of request methods for master data population at device end
 */
@Path("/masterdatarestservice")
public class MasterDataRestService implements AttributeConstants, AttributeValueConstants, Constants {
	
	private static final Logger LOGGER = Logger.getLogger(MasterDataRestService.class);
	
	private CustomerService customerService;
	
	private CommonService commonService;
	
	@Path("/listofcurrency")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response listOfCurrency(@Context HttpServletRequest request,	String listOfCurrencyInput) throws WalletException {
		Response responce = null;
		LOGGER.info(" >>>>> Entering listOfCurrency <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"listOfCurrency",listOfCurrencyInput);
		Boolean exit = false;
		Authentication authentication = null;
		RestRequest	restRequest = null;
		papulateServices(request);
		
		/* get service instances */
		
		try{
			if(CommonWebserviceUtil.isEmpty(listOfCurrencyInput)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(listOfCurrencyInput, RestRequest.class);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"listOfCurrency",responce);
				LOGGER.info(" >>>>> Exiting listOfCurrency <<<<< ");
			}			
		}
		
		try {
			LOGGER.info(USER_EMAIL +":" + restRequest.getEmail() + USER_TYPE +":" + restRequest.getUserType());
			//authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
			CustomerDto customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			authentication = commonService.getAuthentication(customerDto.getEmailId(), restRequest.getUserType());
			restRequest.setEmail(authentication.getEmailId());
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), 
					restRequest.getUserType());
			Response response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			}else{
				Long authenticationId = authentication.getId();
				LOGGER.info(AUTHENTICATION_ID + ":" + authenticationId);
				List<JSONObject> jsonList = new ArrayList<JSONObject>();
				JSONObject currencyJson = null;

				Map<Long, String> currencyMap = MasterDataUtil.getCurrencyNames(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID));
				
				for (Map.Entry<Long, String> entry : currencyMap.entrySet()){
					currencyJson = new JSONObject();
					currencyJson.put(CURRENCY_ID, entry.getKey());    
					currencyJson.put(CURRENCY_CODE, entry.getValue());
					jsonList.add(currencyJson);
				}
				response = ServerUtility.papulateSuccessCode(request, 
						ServerProcessorStatus.RECORDS_FOUND.getValue(), jsonList);  
				return response;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.UNABLE_TO_RETRIEVE_LISTOFCURRENCY.getValue());
			return responce;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"listOfCurrency",responce);
			LOGGER.info(" >>>>> Exiting listOfCurrency <<<<< ");
		}
    }
	
	@Path("/destinationtypes")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response listOfDestinationTypes(@Context HttpServletRequest request,	String destinationTypesInput) throws WalletException {
		
		Response responce = null;
		LOGGER.info(" >>>>> Entering listOfDestinationTypes <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"listOfDestinationTypes",destinationTypesInput);
		Boolean exit = false;
		Authentication authentication = null;
		RestRequest	restRequest = null;
		papulateServices(request);
				
		try{
			if(CommonWebserviceUtil.isEmpty(destinationTypesInput)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(destinationTypesInput, RestRequest.class);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"listOfDestinationTypes",responce);
				LOGGER.info(" >>>>> Exiting listOfDestinationTypes <<<<< ");
			}			
		}
		/* get service instances */
		try {
			LOGGER.info(USER_EMAIL +":" + restRequest.getEmail() + USER_TYPE +":"+ restRequest.getUserType());
			CustomerDto customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			authentication = commonService.getAuthentication(customerDto.getEmailId(), restRequest.getUserType());
			restRequest.setEmail(authentication.getEmailId());
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), 
					restRequest.getUserType());
			Response response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			} else{
				Long authenticationId = authentication.getId();
				LOGGER.info(AUTHENTICATION_ID +":" + authenticationId);
				List<JSONObject> jsonList = new ArrayList<JSONObject>();
				JSONObject destinationTypesJson = null;
				
				Map<Long,String> userTypes = MasterDataUtil.getSimpleDataMap(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID),
						MasterDataConstants.MD_USER_TYPES);
				
			    for(Map.Entry<Long, String> entry : userTypes.entrySet()){
					if(!(entry.getKey().equals(ADMIN_USER_TYPE_ID))){
						destinationTypesJson = new JSONObject();
						destinationTypesJson.put("destinationTypeId", entry.getKey());
						destinationTypesJson.put("destinationTypeName", entry.getValue());
						jsonList.add(destinationTypesJson);
					}
				}
			    responce = ServerUtility.papulateSuccessCode(request, 
							ServerProcessorStatus.RECORDS_FOUND.getValue(), jsonList);  
			    return responce;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
			responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.UNABLE_TO_RETRIEVE_DESTINATIONTYPES.getValue());
			return responce;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"listOfDestinationTypes",responce);
			LOGGER.info(" >>>>> Exiting listOfDestinationTypes <<<<< ");
		}
	}
	
	@Path("/typesofcards")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response listOfTypesOfCards(@Context HttpServletRequest request, String typesOfCardsInput) throws WalletException {
		
		Response responce = null;
		LOGGER.info(" >>>>> Entering listOfTypesOfCards <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"listOfTypesOfCards",typesOfCardsInput);
		Boolean exit = false;
		Authentication authentication = null;
		RestRequest	restRequest = null;
		papulateServices(request);
				
		try{
			if(CommonWebserviceUtil.isEmpty(typesOfCardsInput)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(typesOfCardsInput, RestRequest.class);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"listOfTypesOfCards",responce);
				LOGGER.info(" >>>>> Exiting listOfTypesOfCards <<<<< ");
			}			
		}
		
		/* get service instances */	
		try {
			LOGGER.info(USER_EMAIL +":" + restRequest.getEmail() + USER_TYPE +":"+ restRequest.getUserType());
			CustomerDto customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			authentication = commonService.getAuthentication(customerDto.getEmailId(), restRequest.getUserType());
			restRequest.setEmail(authentication.getEmailId());
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), 
					restRequest.getUserType());
			Response response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			} else{
				List<JSONObject> jsonList = new ArrayList<JSONObject>();
				JSONObject cardtypesjson = null;
				Long authenticationId = authentication.getId();
				LOGGER.info(AUTHENTICATION_ID +":"  + authenticationId);
				
				LOGGER.info( " getCardTypes " );
				Map<Long, String> cardTypesMap = MasterDataUtil.getSimpleDataMap(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID), 
						MasterDataUtil.MD_CARD_TYPES);
				for(Map.Entry<Long, String> entry : cardTypesMap.entrySet()){
					cardtypesjson = new JSONObject();
					cardtypesjson.put("cardTypeId", entry.getKey());
					cardtypesjson.put("cardTypeName", entry.getValue());
					jsonList.add(cardtypesjson);
				}
				responce = ServerUtility.papulateSuccessCode(request, 
							ServerProcessorStatus.RECORDS_FOUND.getValue(), jsonList); 
				return responce;
				}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.UNABLE_TO_RETRIEVE_TYPEOFCARDS.getValue());
			return responce;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"listOfTypesOfCards",responce);
			LOGGER.info(" >>>>> Exiting listOfTypesOfCards <<<<< ");
		}
	}
	
	@Path("/hintquestions")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response listOfHintQuestions(@Context HttpServletRequest request, String hintQuestionsInput) throws WalletException {
		
		Response responce = null;
		LOGGER.info(" >>>>> Entering listOfHintQuestions <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"listOfHintQuestions",hintQuestionsInput);
		Boolean exit = false;
		RestRequest	restRequest = null;
		papulateServices(request);
				
		try{
			if(CommonWebserviceUtil.isEmpty(hintQuestionsInput)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(hintQuestionsInput, RestRequest.class);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"listOfHintQuestions",responce);
				LOGGER.info(" >>>>> Exiting listOfHintQuestions <<<<< ");
			}			
		}
		/* get service instances */
		try {
			LOGGER.info(SIM_NUMBER + ":" + restRequest.getSimNumber() + IMEI_NUMBER + ":" + restRequest.getImeiNumber() + 
					LANGUAGE_ID + ":" +restRequest.getLanguageId());
			List<JSONObject> jsonList = new ArrayList<JSONObject>();
			JSONObject hintJson = null;
			
			Map<Long, String> hintQuestionsMap = MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), (Long) request
					.getSession().getAttribute(LANGUAGE_ID), MasterDataConstants.HINT_QUESTIONS);
			for(Map.Entry<Long, String> entry : hintQuestionsMap.entrySet()){
				hintJson = new JSONObject();
				hintJson.put("hintQuestionId", entry.getKey());
				hintJson.put("hintQuestionName", entry.getValue());
				jsonList.add(hintJson);
			}
			responce = ServerUtility.papulateSuccessCode(request, 
						ServerProcessorStatus.RECORDS_FOUND.getValue(), jsonList); 
			return responce;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.UNABLE_TO_RETRIEVE_MPIN_HINT_QUESTIONS.getValue());
			return responce;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"listOfHintQuestions",responce);
			LOGGER.info(" >>>>> Exiting listOfHintQuestions <<<<< ");
		}
	}
	
	@Path("/destinationtypesandcurrency")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response listOfDestinationTypesAndCurrency(@Context HttpServletRequest request,	String destinationTypesInput) throws WalletException {
		Response responce = null;
		LOGGER.info(" >>>>> Entering listOfDestinationTypesAndCurrency <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"listOfDestinationTypesAndCurrency",destinationTypesInput);
		Boolean exit = false;
		papulateServices(request);
		Authentication authentication = null;
		RestRequest	restRequest = null;
				
		try{
			if(CommonWebserviceUtil.isEmpty(destinationTypesInput)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(destinationTypesInput, RestRequest.class);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"listOfDestinationTypesAndCurrency",responce);
				LOGGER.info(" >>>>> Exiting listOfDestinationTypesAndCurrency <<<<< ");
			}			
		}
		
		/* get service instances */
		try {
			LOGGER.info(USER_EMAIL +":" + restRequest.getEmail() + USER_TYPE +":"+ restRequest.getUserType());
			CustomerDto customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			authentication = commonService.getAuthentication(customerDto.getEmailId(), restRequest.getUserType());
			restRequest.setEmail(authentication.getEmailId());
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), 
					restRequest.getUserType());
			Response response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			} else{
				Long authenticationId = authentication.getId();
				LOGGER.info(AUTHENTICATION_ID +":" + authenticationId);
				List<JSONObject> jsonList1 = new ArrayList<JSONObject>();
				List<JSONObject> jsonList2 = new ArrayList<JSONObject>();
				JSONObject destinationTypesJson = null;
				
				Map<Long,String> userTypes = MasterDataUtil.getSimpleDataMap(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID),
						MasterDataConstants.MD_USER_TYPES);
				
			    for(Map.Entry<Long, String> entry : userTypes.entrySet()){
					if(!(entry.getKey().equals(ADMIN_USER_TYPE_ID))){
						destinationTypesJson = new JSONObject();
						destinationTypesJson.put("destinationTypeId", entry.getKey());
						destinationTypesJson.put("destinationTypeName", entry.getValue());
						jsonList1.add(destinationTypesJson);
					}
				}
			    
			    Map<Long, String> currencyMap = MasterDataUtil.getCurrencyNames(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID));
			    JSONObject currencyJson = null;	
				for (Map.Entry<Long, String> entry : currencyMap.entrySet()){
					currencyJson = new JSONObject();
					currencyJson.put(CURRENCY_ID, entry.getKey());    
					currencyJson.put(CURRENCY_CODE, entry.getValue());
					jsonList2.add(currencyJson);
				}
				Map<String, List<JSONObject>> jsonMap = new HashMap<String, List<JSONObject>>();
				jsonMap.put(CURRENCY_LIST, jsonList2);
				jsonMap.put(DESTINATION_TYPE_LIST, jsonList1);
				
				responce = ServerUtility.papulateSuccessCode(request, 
							ServerProcessorStatus.RECORDS_FOUND.getValue(), jsonMap);  
				return responce;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
			responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.UNABLE_TO_RETRIEVE_DESTINATIONTYPES.getValue());
			return responce;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"listOfDestinationTypesAndCurrency",responce);
			LOGGER.info(" >>>>> Exiting listOfDestinationTypesAndCurrency <<<<< ");
		}
	}
	
	@Path("/titlestates")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response listOfTitlesStates(@Context HttpServletRequest request, String listOfTitlesStates) throws WalletException {
		
		Response responce = null;
		LOGGER.info(" >>>>> Entering listOfTitlesStates <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"listOfTitlesStates",listOfTitlesStates);
		Boolean exit = false;
		papulateServices(request);
		RestRequest	restRequest = null;
			
		try{
			if(CommonWebserviceUtil.isEmpty(listOfTitlesStates)){
				responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return responce;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(listOfTitlesStates, RestRequest.class);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			responce = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return responce;
		}finally {
			if(exit){
				CommonRestServiceHelper.responseLog(LOGGER,"listOfTitlesStates",responce);
				LOGGER.info(" >>>>> Exiting listOfTitlesStates <<<<< ");
			}			
		}
		
		/* get service instances */
	
		try {
			LOGGER.info(SIM_NUMBER + ":" + restRequest.getSimNumber() + IMEI_NUMBER + ":" + restRequest.getImeiNumber() + 
					LANGUAGE_ID + ":" +restRequest.getLanguageId());
			Map<String, List<JSONObject>> jsonMap = new HashMap<String, List<JSONObject>>();
			List<JSONObject> jsonList1 = new ArrayList<JSONObject>();
			List<JSONObject> jsonList2 = new ArrayList<JSONObject>();
			JSONObject titleJson = null;
			JSONObject statesJson = null;
			
			Map<Long, String> titleMap = MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), (Long) request
					.getSession().getAttribute(LANGUAGE_ID), MasterDataConstants.TITLES);
			for(Map.Entry<Long, String> entry : titleMap.entrySet()){
				titleJson = new JSONObject();
				titleJson.put("titleId", entry.getKey());
				titleJson.put("titleName", entry.getValue());
				jsonList1.add(titleJson);
			}
			jsonMap.put(MasterDataConstants.TITLES, jsonList1);
			Map<Long, String> statesMap = MasterDataUtil.getRegions(request.getSession().getServletContext(), 
						 (Long) request.getSession().getAttribute(LANGUAGE_ID), CountryIds.IND_COUNTRY_ID);
			
			for(Map.Entry<Long, String> entry : statesMap.entrySet()){
				statesJson = new JSONObject();
				statesJson.put("stateId", entry.getKey());
				statesJson.put("stateName", entry.getValue());
				jsonList2.add(statesJson);
			}
			jsonMap.put("states", jsonList2);
			responce = ServerUtility.papulateSuccessCode(request, 
						ServerProcessorStatus.RECORDS_FOUND.getValue(), jsonMap); 
			return responce;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			responce = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.UNABLE_TO_RETRIEVE_MPIN_HINT_QUESTIONS.getValue());
			return responce;
		}finally{
			CommonRestServiceHelper.responseLog(LOGGER,"listOfTitlesStates",responce);
			LOGGER.info(" >>>>> Exiting listOfTitlesStates <<<<< ");
		}
	}
	
	/**
	 * Get service instances from request object
	 * 
	 * @param request
	 */
	private void papulateServices(HttpServletRequest request) {
		commonService = (CommonService) ServerUtility.getServiceInstance(
				request.getSession(), COMMON_SERVICE);
		customerService = (CustomerService) ServerUtility.getServiceInstance(
				request.getSession(), CUSTOMER_SERVICE);
	}
	
}
