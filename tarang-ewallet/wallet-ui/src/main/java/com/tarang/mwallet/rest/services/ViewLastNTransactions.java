package com.tarang.mwallet.rest.services;

import java.util.List;

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
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.reports.business.ReportsService;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.controller.constants.Customer;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.mwallet.rest.services.model.RestRequest;
import com.tarang.mwallet.rest.services.util.CommonRestServiceHelper;
import com.tarang.mwallet.rest.services.util.CommonWebserviceUtil;
import com.tarang.mwallet.rest.services.util.Constants;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;
import com.tarang.mwallet.rest.services.util.ServerUtility;

@Path("/viewlastntransactions")
public class ViewLastNTransactions implements Customer, AttributeConstants, AttributeValueConstants, GlobalLitterals, Constants{
	
	private static final Logger LOGGER = Logger.getLogger(ViewLastNTransactions.class);
	
	private CommonService commonService;
	
	private ReportsService reportsService;
	
	private UtilService utilService;
	private CustomerService customerService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerOrMerchantLastNTransactions(@Context HttpServletRequest request, String trasnctionInput) throws WalletException {
		
		
		papulateServices(request);
		LOGGER.info(" >>>>> Entering customerOrMerchantLastNTransactions <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"customerOrMerchantLastNTransactions",trasnctionInput);
		RestRequest	restRequest = null;
		Authentication authentication = null;
		Response response = null;
		boolean exit = false;
		
		try {
			if (CommonWebserviceUtil.isEmpty(trasnctionInput)) {
				response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(trasnctionInput, RestRequest.class);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return response;
		} finally {
			if (exit) {
				CommonRestServiceHelper.responseLog(LOGGER, "customerOrMerchantLastNTransactions", response);
				LOGGER.info(" >>>>> Exiting customerOrMerchantLastNTransactions <<<<< ");
			}
		}
		LOGGER.info(" email: " + restRequest.getEmail() + " userType: " + restRequest.getUserType());
		try{
			CustomerDto customerDto = customerService.getCustomerByPhoneNo(restRequest.getPhoneCode(), restRequest.getPhoneNo());
			authentication = commonService.getAuthentication(customerDto.getEmailId(), restRequest.getUserType());
			restRequest.setEmail(authentication.getEmailId());		
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), 
					restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			} else {
		    	Long authenticationId = authentication.getId();
		    	LOGGER.info("The authentication id is " + authenticationId);
		    	List<CustomerReportModel> modelList = reportsService.customerLastNTransactions(utilService.getDefaultLastNTransactions(), 
					1L, authenticationId, "CR", "DR");   
		    	if(null == modelList || modelList.size() <= 0){
		    		response = ServerUtility.papulateErrorCode(request,
							ServerProcessorStatus.NO_TRNASACTION_RECORD_FOUND.getValue());
		    		return response;
		    	} else {   
		    		for(CustomerReportModel cu:modelList){
						cu.setDisplayamount(UIUtil.getConvertedAmountInString(cu.getAmount()));
						cu.setDisplaydeduction(UIUtil.getConvertedAmountInString(cu.getDeductions()));
						cu.setDisplayPayeeBalance(UIUtil.getConvertedAmountInString(cu.getPayeebalance()));
						if(null == cu.getEmailid()){
							cu.setEmailid("NA");
						}
			    	}
		    		response = ServerUtility.papulateSuccessCode(request, 
							ServerProcessorStatus.RECORDS_FOUND.getValue(), modelList); 
		    		return response;
		    	}
		    	
			}
		} catch(WalletException ex) {
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.NO_TRNASACTION_RECORD_FOUND.getValue());
			return response;
		}
		finally {
			
				CommonRestServiceHelper.responseLog(LOGGER, "customerOrMerchantLastNTransactions", response);
				LOGGER.info(" >>>>> Exiting customerOrMerchantLastNTransactions <<<<< ");
			
		}
	}
	
	private void papulateServices(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		commonService = (CommonService) ServerUtility.getServiceInstance(session, COMMON_SERVICE);
		reportsService = (ReportsService) ServerUtility.getServiceInstance(session, REPORTS_SERVICE);
		utilService = (UtilService) ServerUtility.getServiceInstance(session, UTIL_SERVICE);
		customerService = (CustomerService) ServerUtility.getServiceInstance(request.getSession(), CUSTOMER_SERVICE);
	}
	
}
