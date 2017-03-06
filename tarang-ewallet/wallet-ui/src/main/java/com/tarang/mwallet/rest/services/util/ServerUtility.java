/**
 * 
 */
package com.tarang.mwallet.rest.services.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.util.MasterDataConstants;

/**
 * @author kedarnathd
 *
 */
public class ServerUtility implements Constants{
	
	private ServerUtility(){
	}
	
	/**
	 * Get bean instances from web application context
	 * @param session
	 * @param beanName
	 * @return
	 */
	public static Object getServiceInstance(HttpSession session, String beanName){
		ApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(session.getServletContext());
		return applicationContext.getBean(beanName);
	}
	
	/**
	 * Get error code from context and return as code and description
	 * @param request
	 * @param code
	 * @return
	 */
	public static Response papulatePGErrorCode(HttpServletRequest request, String code){
		Map<String, String> codeValue = getPGCode(request, code);
		JSONObject resobject = new JSONObject();
		for(Map.Entry<String, String> entry : codeValue.entrySet()) {
			resobject.put(ERROR_CODE, entry.getKey());
			resobject.put(RESPONS_EMESSAGE, entry.getValue());
		}
		resobject.put(RESPONSE_TYPE, RESPONSE_TYPE_FAILURE);
		return Response.status(SERVER_ERROR).entity(CommonWebserviceUtil.getJSONObject(resobject)).build();
	}
	
	/**
	 * Get error code from context and return as code and description
	 * @param request
	 * @param errorCode
	 * @return
	 */
	public static Response papulateErrorCode(HttpServletRequest request, String errorCode){
		Map<String, String> codeValue = getErrorOrSuccessCode(request, errorCode);
		JSONObject resobject = new JSONObject();
		for(Map.Entry<String, String> entry : codeValue.entrySet()) {
			resobject.put(ERROR_CODE, entry.getKey());
			resobject.put(RESPONS_EMESSAGE, entry.getValue());
		}
		resobject.put(RESPONSE_TYPE, RESPONSE_TYPE_FAILURE);
		return Response.status(Constants.SUCCESS).entity(CommonWebserviceUtil.getJSONObject(resobject)).build();
	}
	
	
	
	public static JSONObject papulateErrorCodeString(HttpServletRequest request, String errorCode){
		Map<String, String> codeValue = getErrorOrSuccessCode(request, errorCode);
		JSONObject resobject = new JSONObject();
		for(Map.Entry<String, String> entry : codeValue.entrySet()) {
			resobject.put(ERROR_CODE, entry.getKey());
			resobject.put(RESPONS_EMESSAGE, entry.getValue());
		}
		resobject.put(RESPONSE_TYPE, RESPONSE_TYPE_FAILURE);
		return resobject;
	}
	
	
	
	/**
	 * Get success code from context and return success code and description
	 * @param request
	 * @param code
	 * @return
	 */
	public static Response papulateSuccessCode(HttpServletRequest request, String successCode,
			Object responseData){
		Map<String, String> codeValue = getErrorOrSuccessCode(request, successCode);
		JSONObject resobject = new JSONObject();
		for(Map.Entry<String, String> entry : codeValue.entrySet()) {
			resobject.put(SUCCESS_CODE, entry.getKey());
			resobject.put(RESPONS_EMESSAGE, entry.getValue());
			resobject.put(RESPONSE_TYPE, RESPONSE_TYPE_SUCCESS);
			resobject.put(REQUESTED_DATA, responseData != null ? CommonWebserviceUtil.getJSONObject(responseData) : null);
		}
		return Response.status(Constants.SUCCESS).entity(CommonWebserviceUtil.getJSONObject(resobject)).build();
	}
	
	private static Map<String, String> getErrorOrSuccessCode(HttpServletRequest request, String code){
		return getErrorOrSuccessCode(request.getSession(), code);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> getErrorOrSuccessCode(HttpSession session, String code){
		//get error codes from master data
		Map<String, String> codes = (Map<String, String>)session.getServletContext().getAttribute(MasterDataConstants.MOBILE_WALLET_ERROR_CODE);
		Map<String, String> codeValue = new HashMap<String, String>();
		if(codes.get(code) != null && !codes.get(code).equals(GlobalLitterals.EMPTY_STRING)){
			codeValue.put(code, codes.get(code));
		} else {
			codeValue.put(ServerProcessorStatus.UNKNOW_ERROR.getValue(), 
					codes.get(ServerProcessorStatus.UNKNOW_ERROR.getValue()));
		}
		return codeValue;
	}
	
	/**
	 * Get code and description from context as per payment gateway response code 
	 * @param request
	 * @param code
	 * @return
	 */
	private static Map<String, String> getPGCode(HttpServletRequest request, String code){
		return getPGCode(request.getSession(), code);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> getPGCode(HttpSession session, String code){
		//get PG error codes from master data
		Map<String, String> codes = (Map<String, String>)session.getServletContext().getAttribute(MasterDataConstants.PG_ERROR_CODE);
		Map<String, String> pgCodeValues = new HashMap<String, String>();
		Map<String, String> errorCodeValues = new HashMap<String, String>();
		if(codes.get(code) != null && !codes.get(code).equals(GlobalLitterals.EMPTY_STRING)){
			pgCodeValues.put(code, codes.get(code));
			return pgCodeValues;
		} else {
			errorCodeValues.put(ServerProcessorStatus.UNKNOW_ERROR.getValue(),
					getErrorOrSuccessCode(session, code).get(ServerProcessorStatus.UNKNOW_ERROR.getValue()));
			return errorCodeValues;
		}
	}
}
