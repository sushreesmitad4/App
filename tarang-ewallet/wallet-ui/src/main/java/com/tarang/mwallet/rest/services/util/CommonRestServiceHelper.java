/**
 * 
 */
package com.tarang.mwallet.rest.services.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.sms.dto.OtpDto;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.util.ManageAccountUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.mwallet.rest.services.model.RestRequest;

/**
 * @author kedarnathd
 * This helper class holds the common business logic for all type of rest API(s) call.
 */
public class CommonRestServiceHelper implements AttributeConstants {
	
	/**
	 * This method will called both validation methods 
	 * isAuthrised and emptyCheckAndMSISDNValidation.
	 * @param request
	 * @param restRequest
	 * @param phoneNumber
	 * @param authentication
	 * @return
	 */
	public static Response checkAutherizedUserAccess(HttpServletRequest request, RestRequest restRequest, 
			PhoneNumber phoneNumber, Authentication authentication, Boolean isUserLoginStatus){
		Response response = null;
		if (null == authentication) {
			return ServerUtility.papulateErrorCode(request,	
					ServerProcessorStatus.USER_DOES_NOT_EXIST.getValue());
		}
		response = isAuthrised(request, authentication);
		if(response != null){
			return response;
		}
		response = emptyCheckAndMSISDNValidation(request, restRequest, 
				phoneNumber, authentication, isUserLoginStatus);
		if(response != null){
			return response;
		}
		/*if(isUserLoginStatus){
		response = checkUserLoginStatus(request, authentication, restRequest);
			if(response != null){
				return response;
			}
		}*/
		return response;
	}
	
	/**
	 * Null check for authentication object and isAuthrised check for requested user
	 * @param request
	 * @param authentication
	 * @return
	 */
	public static Response isAuthrised(HttpServletRequest request, Authentication authentication){
		HttpSession session = request.getSession();
		session.setAttribute(USER_TYPE, authentication.getUserType());
		/*Mobile wallet is access by only customer/merchant*/
		if(!isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			String errMsg = (String)session.getAttribute(ERROR_MESSAGE);
			if(errMsg != null){
				session.removeAttribute(ERROR_MESSAGE);
				return ServerUtility.papulateErrorCode(request, errMsg);
			}
		}
		return null;
	}
	
	/**
	 * This method is only to verify customer or merchant type users access.
	 * This has to be called before calling actual business logic.
	 * @param request
	 * @param exceptedUserType1
	 * @param exceptedUserType2
	 * @return
	 */
	public static Boolean isAuthrised(HttpServletRequest request, String exceptedUserType1, String exceptedUserType2){
		HttpSession session = request.getSession();
		if(getAuthrisedRequestCode(session, exceptedUserType1) != VALID_USER_ACCESS 
				&& getAuthrisedRequestCode(session, exceptedUserType2) != VALID_USER_ACCESS){
			papulateInvalidAccessError(request, getAuthrisedRequestCode(session, exceptedUserType2));
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * Check mandatory fields are in the request object or not else return error code
	 * Check request phone number is register in wallet database or not else return error code
	 * @param request
	 * @param restRequest
	 * @return
	 */
	public static Response emptyCheckAndMSISDNValidation(HttpServletRequest request, RestRequest restRequest,
			PhoneNumber phoneNumber, Authentication authentication, Boolean isUserLogin){
		if(null != phoneNumber && CommonWebserviceUtil.isEmpty(restRequest.getMsisdnNumber())){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.MSISDN_NUMBER_IS_EMPTY.getValue());
		}
		if(CommonWebserviceUtil.isEmpty(restRequest.getSimNumber())){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.SIM_NUMBER_IS_EMPTY.getValue());
		}
		if(CommonWebserviceUtil.isEmpty(restRequest.getImeiNumber())){
			return ServerUtility.papulateErrorCode(request,
					ServerProcessorStatus.IMEI_NUMBER_IS_EMPTY.getValue());
		}
		/*MSISDN should not be same as phone number, it is a MSISDN ID*/
		/*if(null != phoneNumber && !phoneNumber.getPnumber().equals(restRequest.getMsisdnNumber())){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.USER_MOBILE_DOESNOT_MATCHES_REG_NUMBER.getValue());

		}*/
		/*if(isUserLogin){
			Response response = checkUserLoginStatus(request, authentication, restRequest);
			if(response != null){
				return response;
			}
		}*/
		return null;
	}
	
	/**
	 * This method called is conditional to check transaction 
	 * eligibility for the requested user.
	 * @param request
	 * @param userType
	 * @param userStatus
	 * @return
	 */
	public static Response checkTransaction(HttpServletRequest request, String userType, Long userStatus){
		if(userType.equals(CUSTOMER_USER_TYPE) && !userStatus.equals(UserStatusConstants.APPROVED)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.NOT_AUTHORIZED_TRANSACTION.getValue());
	 	}
		if(userType.equals(MERCHANT_USER_TYPE) && !userStatus.equals(UserStatusConstants.APPROVED)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.NOT_AUTHORIZED_TRANSACTION.getValue());
	 	}
		return null;
	}
	
	/**
	 * For mobile wallet request, it will validate the user status
	 * This method will called  by every requested method except login functionality
	 * @param authentication
	 * @return
	 */
	private static Response checkUserLoginStatus(HttpServletRequest request, Authentication authentication,
			RestRequest restRequest){
		
		   Boolean isBlocked = authentication.getmPinBlocked() != null ? authentication.getmPinBlocked() : false;
		   Boolean isActive = authentication.isActive() != null ? authentication.isActive() : false;
		   Long status = authentication.getStatus()!= null?authentication.getStatus() : new Long(0);
		   if(CommonUtil.isFirstLogin(authentication)) {
			   return ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.USER_NOT_REGISTER_AS_MOBILE_WALLET.getValue());
		   }else if(!CommonUtil.checkUserHasRequestedFromRegisterMobileWallet(authentication, restRequest.getMsisdnNumber(),
				   restRequest.getSimNumber(), restRequest.getImeiNumber())){
			   return ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.USER_REQUESTED_FROM_DIFFERENT_DEVICE_OR_SIM.getValue());
		   }else if(isBlocked) {
			   return ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.USER_BLOCK.getValue());
		   }else if(!isActive){
			   return ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.ACCOUNT_DEACTIVE.getValue());
		   }else if(UserStatusConstants.DELETED.equals(status)){
			   return ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.USER_ACCOUNT_DELETED.getValue());
		   }else if(UserStatusConstants.REJECTED.equals(status)){
			   return ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.ACCOUNT_REJECTED.getValue());
		   }else if(authentication.getIsMwalletActive()== null 
				   || !authentication.getIsMwalletActive()){
			   return ServerUtility.papulateErrorCode(request, 
					   ServerProcessorStatus.MOBILE_WALLET_ACCOUNT_IS_NOT_ACTIVE.getValue());
		   }else if(authentication.isLoginStatus() != null && !authentication.isLoginStatus()){
			   return ServerUtility.papulateErrorCode(request, 
						ServerProcessorStatus.USER_NOT_YET_LOGIN.getValue());
		   }
		   return null;
	}
	
	
	private static int getAuthrisedRequestCode(HttpSession session, String exceptedUserType){
		if(session.getAttribute(USER_TYPE) == null){
			return INVALID_SESSION;
		}else if(!session.getAttribute(USER_TYPE).equals(exceptedUserType)){
			return INVALID_USER_ACCESS;
		}
		return VALID_USER_ACCESS;
	}
	
	private static void papulateInvalidAccessError(HttpServletRequest request, int errorCode){
		HttpSession session = request.getSession();
		if(INVALID_SESSION == errorCode){
			session.invalidate();
			HttpSession newSession = request.getSession(true);
			newSession.setAttribute(ERROR_MESSAGE, 
					ServerProcessorStatus.ERROR_MSG_INVALID_SESSION.getValue());
		}else if(INVALID_USER_ACCESS == errorCode){
			session.setAttribute(ERROR_MESSAGE, 
					ServerProcessorStatus.ERROR_MSG_INVALID_USER_ACCESS.getValue());
		}
	}
	
	/**
	 * This will validate the account details belongs to the user
	 * @param request
	 * @param authentication
	 * @param dto
	 * @return
	 */
	public static Response isAccountBelongsToUser(HttpServletRequest request, Authentication authentication, AccountDto dto){
		if(null == dto){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.ERROR_MSG_FAILS_TO_GET_ACCOUNT.getValue());
		}else if(AccountsConstants.SOFT_DELETE.equals(dto.getDeletedStatus())){
		    return ServerUtility.papulateErrorCode(request, 
		    		ServerProcessorStatus.ACCOUNT_HAS_ALREADY_DELETED.getValue());
		}else if(!authentication.getId().equals(dto.getAuthId())){
		    return ServerUtility.papulateErrorCode(request, 
		    		ServerProcessorStatus.WRONG_ACCOUNT_DETAILS.getValue());
		}
		return null;
	}
    
	/**This method will check for validate amount
	 * @param request
	 * @param amount
	 * @return
	 */
	public static Response validAmountCheck(HttpServletRequest request,String amount){
		if(amount == null || amount.equals(EMPTY_STRING)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.AMOUNT_SHOULDNOTBE_EMPTY.getValue());
		}else{
			if(!amount.equals(EMPTY_STRING)){
				Boolean amountError = UIUtil.currencyValidator(amount.toString());
				if(amountError){
					return ServerUtility.papulateErrorCode(request, 
							ServerProcessorStatus.INVALID_AMOUNT_FORMAT.getValue());
				}
		 }
		}	
		return null;
	}
	
	/**This method will check for validate wallet
	 * @param request
	 * @param fromWallet
	 * @param toWallet
	 * @return
	 */
	public static Response validWalletCheck(HttpServletRequest request,Long fromWallet,Long toWallet){
		if(fromWallet == null || toWallet == null ){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.FROM_TOWALLET_SHOULDNOTBE_EMPTY.getValue());
		}else if(fromWallet.equals(EMPTY_STRING) || toWallet.equals(EMPTY_STRING)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.FROM_TOWALLET_SHOULDNOTBE_EMPTYSTRING.getValue());
		}else if(fromWallet.equals(toWallet)){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.FROM_TOWALLET_SHOULDNOTBE_SAME.getValue());
		}else if(fromWallet > 3 || toWallet > 3){
			return ServerUtility.papulateErrorCode(request, 
					ServerProcessorStatus.FROM_TOWALLET_COULDNOT_SUPPORT.getValue());
		} 
		return null;
	}
	
	public static OtpDto getOtpDto(RestRequest restRequest, Long customerId){
		OtpDto otpDto = new OtpDto(); 
		otpDto.setMobileCode(restRequest.getPhoneCode());
		otpDto.setMobileNumber(restRequest.getPhoneNo());
		otpDto.setCustomerId(customerId);
		otpDto.setEmailId(restRequest.getEmail());
		otpDto.setOtpNumber(Long.parseLong(restRequest.getOTP()));
		return otpDto;
	}
	
	public static AccountDto getDisplayAccountDto(AccountDto rstDto){
		
		AccountDto displayAccount = new AccountDto();
		displayAccount.setAccountHolderName(rstDto.getAccountHolderName());
		displayAccount.setAccountNumber(rstDto.getAccountNumber());
		displayAccount.setCardName(rstDto.getCardName());
		displayAccount.setCardType(rstDto.getCardType());
		displayAccount.setCardNumberDisplay(ManageAccountUtil.xNumber(rstDto.getCardNumber()));
		displayAccount.setCurrencyCode(rstDto.getCurrencyCode());
		displayAccount.setTransactionAmount(rstDto.getTransactionAmount());
		displayAccount.setOrderId(rstDto.getOrderId());
		displayAccount.setTypeOfRequest(rstDto.getTypeOfRequest());
		displayAccount.setStatus(rstDto.getStatus());
		displayAccount.setId(rstDto.getId());
		return displayAccount;
	}
	
	public static void requestLog(Logger logger,String requestType,Object request){
		logger.info("mWallet Request Packet For "+requestType+"  =====> "+request);
	}
	
	public static void responseLog(Logger logger,String responseType,Response response){
		if(null != response){
			logger.info("mWallet Response Packet For "+responseType  +"  =====> "+response.getEntity());
		}else{
			logger.info("mWallet Response Packet For "+ responseType+"  =====> "+response);
		}
		
	}
}
