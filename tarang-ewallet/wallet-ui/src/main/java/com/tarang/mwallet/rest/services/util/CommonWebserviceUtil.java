/**
 * 
 */
package com.tarang.mwallet.rest.services.util;

import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.exception.WalletException;

/**
 * @author kedarnathd
 *
 */
public class CommonWebserviceUtil implements Constants{
	
	/**
	 * Checks the provided string for null or empty.
	 * 
	 * @param s String to check.
	 * @return True if Empty, false otherwise.
	 */
	public static boolean isEmpty(String s) {
		if ((s != null) && (s.trim().length() > ZERO)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Method to convert JavaObject to JSON
	 * 
	 * @param object
	 * @return
	 * @throws WalletException
	 */
	public static String objectToJSON(final Object object) throws WalletException{
		Writer strWriter = new StringWriter();
	    try {
	      (new ObjectMapper().configure(org.codehaus.jackson.JsonGenerator.Feature.ESCAPE_NON_ASCII,true))
	      .writeValue(strWriter, object);
	      return strWriter.toString();
	    } catch(Exception e) {
	      throw new WalletException(e.getMessage(), e);
	    } finally {
	      try {
	        strWriter.close();
	        strWriter = null;
	      } catch(Exception e) {
	    	  throw new WalletException(e.getMessage(), e);
	      }
	    }
	    
	  }

	  /**
	   * Method to convert JSON to Java Object
	   * 
	   * @param object
	   * @return
	 * @throws WalletException 
	   */
	  public static Object jsonToObject(final String jsonData, Class<?> c) throws WalletException {
	    try {
	    	return (new ObjectMapper().configure(org.codehaus.jackson.JsonGenerator.Feature.ESCAPE_NON_ASCII,true))
	        .readValue(jsonData, c);
	    } catch(Exception e) {
	    	throw new WalletException(e.getMessage(), e);
	    }
	  }
	  
	  public static String getJSONObject(Object object){
		  Gson gson = new Gson();  
		  return gson.toJson(object);
	  }
	  
	  public static Object getObjectFromJSON(String jsonValue){
		  Gson gson = new Gson();
		  return gson.fromJson(jsonValue, Object.class);
	  }
	  
	  public static String papulateErrorMessage(Exception e) {
			String errorCode = null;
			
			if (e.getMessage().contains(CommonConstrain.ALL_READY_REGISTER_USER)) {
				errorCode = ServerProcessorStatus.ALREADY_REGISTOR_AS_MOBILE_WALLET.getValue();
			} else if (e.getMessage().contains(CommonConstrain.LOGIN_ERROR_INVALID_USER)) {
				errorCode = ServerProcessorStatus.LOGIN_ERROR_INVALID_USER.getValue();
			} else if (e.getMessage().contains(CommonConstrain.EMAIL_VARIFICATION_NOT_DONE)) {
				errorCode = ServerProcessorStatus.EMAIL_VARIFICATION_NOT_DONE.getValue();
			} else if (e.getMessage().contains(CommonConstrain.USER_BLOCK)) {
				errorCode = ServerProcessorStatus.USER_BLOCK.getValue();
			} else if (e.getMessage().contains(CommonConstrain.USER_ACCOUNT_DELETED)) {
				errorCode = ServerProcessorStatus.USER_ACCOUNT_DELETED.getValue();
			} else if (e.getMessage().contains(CommonConstrain.PASSWORD_MATCH_FAIL)) {
				errorCode = ServerProcessorStatus.PASSWORD_MATCH_FAIL.getValue();
			} else if (e.getMessage().contains(CommonConstrain.EMAIL_MATCH_FAIL)) {
				errorCode = ServerProcessorStatus.EMAIL_MATCH_FAIL.getValue();
			} else if (e.getMessage().contains(CommonConstrain.ACCOUNT_REJECTED)) {
				errorCode = ServerProcessorStatus.ACCOUNT_REJECTED.getValue();
			} else if (e.getMessage().contains(CommonConstrain.LOGIN_STATUS)) {
				errorCode = ServerProcessorStatus.LOGIN_STATUS.getValue();
			} else if (e.getMessage().contains(CommonConstrain.ACCOUNT_DEACTIVE)) {
				errorCode = ServerProcessorStatus.ACCOUNT_DEACTIVE.getValue();
			} else if (e.getMessage().contains(CommonConstrain.ALREADY_REGISTOR_AS_MOBILE_WALLET)) {
				errorCode = ServerProcessorStatus.ALREADY_REGISTOR_AS_MOBILE_WALLET.getValue();
			} else if (e.getMessage().contains(CommonConstrain.USER_REQUESTED_FROM_DIFFERENT_DEVICE_OR_SIM)) {
				errorCode = ServerProcessorStatus.USER_REQUESTED_FROM_DIFFERENT_DEVICE_OR_SIM.getValue();
			} else if (e.getMessage().contains(CommonConstrain.DUPLICATE_ENTRY)) {
				errorCode = ServerProcessorStatus.DUPLICATE_MOBILE_REGISTRATION.getValue();
			} else if (e.getMessage().contains(CommonConstrain.FAILED_TO_GENERATE_M_PIN)) {
				errorCode = ServerProcessorStatus.UNABLE_TO_GENERATE_MPIN.getValue();
			} else if (e.getMessage().contains(CommonConstrain.MOBILE_USER_BLOCK)) {
				errorCode = ServerProcessorStatus.MOBILE_USER_BLOCK.getValue();
			} else if (e.getMessage().contains(CommonConstrain.MPIN_MATCH_FAIL)) {
				errorCode = ServerProcessorStatus.MPIN_MATCH_FAIL.getValue();
			} else if (e.getMessage().contains(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET)) {
				errorCode = ServerProcessorStatus.USER_NOT_REGISTER_AS_MOBILE_WALLET.getValue();
			} else if (e.getMessage().contains(CommonConstrain.FAILED_TO_DEACTIVATE_DEVICE)) {
				errorCode = ServerProcessorStatus.UNABLE_TO_DEACTIVATE_YOUR_MOBILE_WALLET.getValue();
			} else if (e.getMessage().contains(CommonConstrain.HINT_QUESTION_ONE_NOT_MATCHING)) {
				errorCode = ServerProcessorStatus.HINT_QUESTION_ONE_NOT_MATCHING.getValue();
			} else if (e.getMessage().contains(CommonConstrain.HINT_ANSW_ONE_NOT_MATCHING)) {
				errorCode = ServerProcessorStatus.HINT_ANSW_ONE_NOT_MATCHING.getValue();
			} else if (e.getMessage().contains(CommonConstrain.MOBILE_WALLET_ACCOUNT_IS_NOT_ACTIVE)) {
				errorCode = ServerProcessorStatus.MOBILE_WALLET_ACCOUNT_IS_NOT_ACTIVE.getValue();
			} else if (e.getMessage().contains(CommonConstrain.USER_DOES_NOT_EXIST)){
				errorCode = ServerProcessorStatus.USER_DOES_NOT_EXIST.getValue();
			} else if (e.getMessage().contains(CommonConstrain.COULD_NOT_EXECUTE_JDBC_BATCH_UPDATE)){
				errorCode = ServerProcessorStatus.ALREADY_REGISTOR_AS_MOBILE_WALLET.getValue();
			} else if (e.getMessage().contains(CommonConstrain.PHONE_NUMBER_CREATE_EXCEPTION)){
				errorCode = ServerProcessorStatus.CUSTOMER_MOBILE_EXIST.getValue();
			} else if (e.getMessage().contains(CommonConstrain.AUTHENTICATION_CREATE_EXCEPTION)){
				errorCode = ServerProcessorStatus.CUSTOMER_EMAIL_EXIST.getValue();
			} else if (e.getMessage().contains(CommonConstrain.CUSTOMER_REGISTRATION_FAILED)){
				errorCode = ServerProcessorStatus.CUSTOMER_REGISTRATION_FAILED.getValue();
			} else if (e.getMessage().contains(CommonConstrain.FAILED_TO_SENT_OTP_TO_DEVICE)){
				errorCode = ServerProcessorStatus.FAILED_TO_SENT_OTP_TO_DEVICE.getValue();
			} else if (e.getMessage().contains(CommonConstrain.FAILED_TO_AUTHENTICAT_OTP)){
				errorCode = ServerProcessorStatus.INVALID_OTP_OR_EXPIRED_PLEASE_TRY_AGAIN.getValue();
			} else if (e.getMessage().contains(CommonConstrain.CUSTOMER_MOBILE_NOT_EXIST)){
				errorCode = ServerProcessorStatus.CUSTOMER_MOBILE_NOT_EXIST.getValue();
			} else if (e.getMessage().contains(CommonConstrain.MERCHANT_MOBILE_NOT_EXIST)){
				errorCode = ServerProcessorStatus.MERCHANT_MOBILE_NOT_EXIST.getValue();
			} 
			else if (e.getMessage().contains(CommonConstrain.PHONE_NUMBER_NOT_EXIST)){
				errorCode = ServerProcessorStatus.PHONE_NUMBER_NOT_EXIST.getValue();
			} 
			else {
				errorCode = ServerProcessorStatus.EMAIL_OR_PASSWORD_FAIL.getValue();
			}
			return errorCode;
		}
}
