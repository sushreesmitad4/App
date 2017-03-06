package com.tarang.mwallet.rest.services;

import java.util.ArrayList;
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
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.controller.constants.Customer;
import com.tarang.ewallet.walletui.util.AllWalletsAmmount;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.mwallet.rest.services.model.RestRequest;
import com.tarang.mwallet.rest.services.util.CommonRestServiceHelper;
import com.tarang.mwallet.rest.services.util.CommonWebserviceUtil;
import com.tarang.mwallet.rest.services.util.Constants;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;
import com.tarang.mwallet.rest.services.util.ServerUtility;


@Path("/viewwalletbalace")
public class ViewWalletBalance implements Customer, AttributeConstants, AttributeValueConstants, GlobalLitterals, Constants{
   private static final Logger LOGGER = Logger.getLogger(ViewWalletBalance.class);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewBalance(@Context HttpServletRequest request, String balanceInput) throws WalletException {
		
				
		LOGGER.info(" >>>>> Entering viewBalance <<<<< ");
		CommonRestServiceHelper.requestLog(LOGGER,"viewBalance",balanceInput);
		Authentication authentication = null;
		Response response = null;
		List<Object[]> balanceList = null;
	    RestRequest	restRequest = null;
	    boolean exit = false;
		try{

			if(CommonWebserviceUtil.isEmpty(balanceInput)){
				response = ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.EMPTY_DATA.getValue());
				exit = true;
				return response;
			}
			Gson gson = new Gson();
			restRequest = gson.fromJson(balanceInput, RestRequest.class);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.EMPTY_DATA.getValue());
			exit = true;
			return response;
		}
		finally {
			if (exit) {
				CommonRestServiceHelper.responseLog(LOGGER, "viewBalance", response);
				LOGGER.info(" >>>>> Exiting viewBalance <<<<< ");
			}
		}
		
		LOGGER.info(" email: " + restRequest.getEmail() + " userType: "+ restRequest.getUserType());
		
		HttpSession session = request.getSession(true);
		CommonService commonService = (CommonService) ServerUtility.getServiceInstance(session, COMMON_SERVICE);
		
		try{	
			authentication = commonService.getAuthentication(restRequest.getEmail(), restRequest.getUserType());
			PhoneNumber phoneNumber = commonService.getPhoneNumber(restRequest.getEmail(), 
					restRequest.getUserType());
			response = CommonRestServiceHelper.checkAutherizedUserAccess(request, restRequest, 
					phoneNumber, authentication, Boolean.TRUE);
			if(response != null){
				return response;
			} else {
	    	Long authenticationId = authentication.getId();
		    LOGGER.info("The authentication id " + authenticationId);
	    	List<AllWalletsAmmount> allWalletsAmmountsList = new ArrayList<AllWalletsAmmount>();
	    	balanceList = commonService.getCustomerBalancesByUserId(authenticationId);
	    	AllWalletsAmmount allWalletsAmmount = null;
	    	for(int i = 0; i < balanceList.size(); i++ ){
				allWalletsAmmount = new AllWalletsAmmount();
				allWalletsAmmount.setCurrency((Long)balanceList.get(i)[1]);
				allWalletsAmmount.setAmount((Double)(balanceList.get(i))[0]);
				allWalletsAmmount.setCurrencyName(MasterDataUtil.getCurrencyNames(
						request.getSession().getServletContext(), 
						null).get((Long)balanceList.get(i)[1]));
				allWalletsAmmount.setAmountString(UIUtil.getConvertedAmountInString((Double)(balanceList.get(i))[0]));
				allWalletsAmmountsList.add(allWalletsAmmount);
		     }
	    	response = ServerUtility.papulateSuccessCode(request, 
					ServerProcessorStatus.RECORDS_FOUND.getValue(), allWalletsAmmountsList);
	    	return response;
		   }
		} catch(WalletException ex){
			LOGGER.error(ex.getMessage(), ex);
			response = ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.UNABLE_RETRIVE_WALLET_BALANCE_LIST.getValue());
			return  response;
			
		}finally {
			
				CommonRestServiceHelper.responseLog(LOGGER, "viewBalance", response);
				LOGGER.info(" >>>>> Exiting viewBalance <<<<< ");
			
		}
	    
	}
}