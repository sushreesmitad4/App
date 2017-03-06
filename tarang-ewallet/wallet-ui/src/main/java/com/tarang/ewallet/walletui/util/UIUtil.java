package com.tarang.ewallet.walletui.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.tarang.ewallet.http.util.HttpServiceConstants;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.AttributeValueConstants;
import com.tarang.ewallet.walletui.controller.constants.Accounts;
import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


@SuppressWarnings({"unchecked", "rawtypes"})
public class UIUtil implements AttributeConstants, AttributeValueConstants{
	
	private static final Logger LOGGER = Logger.getLogger(UIUtil.class);
	
	public static String redirectPath(String path){
		return AttributeValueConstants.REDIRECT_PREFIX + AttributeValueConstants.WALLET_PATH_PREFIX + path;
	}
	
	public static void populateMerchantMapValues(HttpServletRequest request, Map model, 
			Long countryBI, Long countryBO, Long businessCategory, Long status, Boolean hintQuestionsRequired){

		model.put(ESTABLISH_MONTH, MerchantDataUtil.getMonthMap());
		
		model.put(ESTABLISH_YEAR, MerchantDataUtil.getYearMap());

		model.put(OWNER_TYPES, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataUtil.MD_MERCHANT_OWNER_TYPES));

		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));

		model.put(BUSINESS_CATEGORIES, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.MD_MERCHANT_BUSINESS_CATEGORIES));

		// except MerchantController 1
		model.put(SUB_CATEGORIES, MasterDataUtil.getMerchantSubCategories(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				businessCategory));

		model.put(CURRENCY_LIST, MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));

		model.put(MERCHANT_PERCENTAGE_ANUAL_REVENUES,MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.MD_MERCHANT_PERCENTAGE_ANUAL_REVENUES));
		
		if( countryBI != null ) {
			model.put(STATE_LIST, MasterDataUtil.getRegions(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), 
					countryBI));
		}

		if( countryBO != null ) {
			model.put(STATE_LIST2, MasterDataUtil.getRegions(
					request.getSession().getServletContext(),
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					countryBO));
		}
		
		if( status != null ){
			model.put(STATUS_LIST, MasterDataUtil.getStatusListById(request, status));
		}

		if( hintQuestionsRequired ) {
			model.put(QUESTIONS, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.HINT_QUESTIONS));
		}
		
		model.put(COUNTRY_PHONE_CODES, MasterDataUtil.getPhoneCodes(
				request.getSession().getServletContext()));

	}	
	
	public static String getClientIpAddr(HttpServletRequest request) {
		String unknownStr = "unknown";
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || unknownStr.equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || unknownStr.equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || unknownStr.equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || unknownStr.equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || unknownStr.equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
	
	public static void populateSendMoneyMapValues(HttpServletRequest request,Map model) {
		model.put(CURRENCY_LIST, MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		model.put(DURATION_TIME, MasterDataUtil.getSimpleDataMap(
			    request.getSession().getServletContext(), 
			    (Long) request.getSession().getAttribute(LANGUAGE_ID), 
			    MasterDataUtil.TRX_TIME_PERIOD));
		Map<Long,String> userTypes = MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.MD_USER_TYPES);
		Map<Long, String> destinationTypes = new HashMap<Long, String>();
		for(Map.Entry<Long, String> entry : userTypes.entrySet()){
			if(!(entry.getKey().equals(ADMIN_USER_TYPE_ID))){
				destinationTypes.put(entry.getKey(), entry.getValue());
			}
		}
		/* here we remove "admin" from userTypes list to set this list as destination types list */
		model.put(DESTINATION_TYPE, destinationTypes); 
	}
	
	public static void populateSendMoneyMultipleMapValues(HttpServletRequest request,Map model) {
		model.put(CURRENCY_LIST, MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		Map<Long,String> userTypes = MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.MD_USER_TYPES);
		Map<Long, String> destinationTypes = new HashMap<Long, String>();
		for(Map.Entry<Long, String> entry : userTypes.entrySet()){
			if(!(entry.getKey().equals(ADMIN_USER_TYPE_ID))){
				destinationTypes.put(entry.getKey(), entry.getValue());
			}
		}
		/* here we remove "ADMIN" from userTypes list to set this list as destination types list */
		model.put(DESTINATION_TYPE, destinationTypes); 
	}
	
	public static void populateSelfTransferMapValues(HttpServletRequest request, Map model, Map<Long, UserWallet> userWallets,Long selectWallet) {
		
		Map<Long, String>  toWallets = MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), (Long) request.getSession().getAttribute(LANGUAGE_ID));
		
		Map<Long, String>  fromwallets = new HashMap<Long, String>();
		Set qset = userWallets.entrySet();
		Iterator i = qset.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			fromwallets.put((Long) me.getKey(), toWallets.get(me.getKey()));
		}
		if(selectWallet!=null){
			toWallets.remove(selectWallet);
		}
		model.put("towallets", toWallets );
		model.put("fromwallets", fromwallets);
	}
	
	public static void papulatePgResponseCode(HttpSession session, ApplicationContext context, 
			Map model, Locale locale, String pgResponseCode){
		String defaultMessage = Accounts.ERROR_MSG_UNKNOWN_PG_RES;
		if(null == pgResponseCode){
			session.setAttribute(ERROR_MESSAGE, defaultMessage);
		} else {
			if(!pgResponseCode.equals(HttpServiceConstants.RESPONSE_DECISION_SUCCESS)){
				String pgCode = HttpServiceConstants.PG_ERROR_PRIFIX + pgResponseCode;
				if(context.getMessage(pgCode, null, defaultMessage, locale).equals(defaultMessage)){
					session.setAttribute(ERROR_MESSAGE, defaultMessage);
				} else{
					session.setAttribute(ERROR_MESSAGE, pgCode);
				}
			}
		}
	}
	
	public static String getConvertedAmountInString(Double value){
		if(value == null){
			return EMPTY_STRING;
		} else {
			return String.format(DOUBLE_FORMAT, value);
		}
	}
	
	/**
	 * Return user type id based on user type
	 * @param userType
	 * @return string
	 */
	public static Long getUserTypeID(String userType){
		if(CUSTOMER_USER_TYPE.equals(userType)){
			return CUSTOMER_USER_TYPE_ID; 
		} else if(MERCHANT_USER_TYPE.equals(userType)){
			return MERCHANT_USER_TYPE_ID; 
		} else if(ADMIN_USER_TYPE.equals(userType)){
			return ADMIN_USER_TYPE_ID; 
		} else {
			return null;
		}
	}
	
	/**
	 * Return full name based on user type
	 * @param ut
	 * @return string
	 */
	public static String getUserType(String ut) {
		String usertyps = ut;
		if (usertyps != null && !usertyps.equals(EMPTY_STRING)) {
			usertyps = usertyps.trim();
		} else{
			return null;
		}
		return usertyps.equalsIgnoreCase(CUSTOMER_USER_TYPE) ? CUSTOMER : MERCHANT;
	}
	
	/**
	 * Validate currency formate
	 * @param value
	 * @return
	 */
	public static Boolean currencyValidator(String value){
		Boolean isError = Boolean.FALSE;
		try{
			if(!CommonValidator.expressionPattern(Common.CURRENCY_EXPRESSION, value)){
				isError = Boolean.TRUE;
			} else{
				if(Double.parseDouble(value) > Common.CURRENCY_MAX_LIMIT){
					isError = Boolean.TRUE;
				}
				if( ZERO_DOUBLE.equals(Double.parseDouble(value)) ){
					isError = Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			isError = Boolean.TRUE;
		}
		return isError;
	}
	
	/**
	 * Validate request as per user type
	 * @param request
	 * @param exceptedUserType
	 * @return
	 */
	public static Boolean isAuthrised(HttpServletRequest request, String exceptedUserType){
		HttpSession session = request.getSession();
		int errorCode = getAuthrisedRequestCode(session, exceptedUserType);
		if(errorCode != VALID_USER_ACCESS){
			papulateInvalidAccessError(request, errorCode);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * this method is only to verify customer or merchant type users
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
	 * this method is to verify customer or merchant or admin type users
	 * @param request
	 * @param exceptedUserType1
	 * @param exceptedUserType2
	 * @param exceptedUserType3
	 * @return
	 */
	public static Boolean isAuthrised(HttpServletRequest request, String exceptedUserType1, String exceptedUserType2, String exceptedUserType3){
		HttpSession session = request.getSession();
		if(getAuthrisedRequestCode(session, exceptedUserType1) != VALID_USER_ACCESS 
				&& getAuthrisedRequestCode(session, exceptedUserType2) != VALID_USER_ACCESS 
				&& getAuthrisedRequestCode(session, exceptedUserType3) != VALID_USER_ACCESS){
			papulateInvalidAccessError(request, getAuthrisedRequestCode(session, exceptedUserType3));
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	private static int getAuthrisedRequestCode(HttpSession session, String exceptedUserType){
		if(session.getAttribute(USER_TYPE) == null){
			return INVALID_SESSION;
		} else if(!session.getAttribute(USER_TYPE).equals(exceptedUserType)){
			return INVALID_USER_ACCESS;
		}
		return VALID_USER_ACCESS;
	}
	
	private static void papulateInvalidAccessError(HttpServletRequest request, int errorCode){
		HttpSession session = request.getSession();
		if(INVALID_SESSION == errorCode){
			session.invalidate();
			HttpSession newSession = request.getSession(true);
			newSession.setAttribute(ERROR_MESSAGE, ERROR_MSG_INVALID_SESSION);
		} else if(INVALID_USER_ACCESS == errorCode){
			session.setAttribute(ERROR_MESSAGE, ERROR_MSG_INVALID_USER_ACCESS);
		}
	}
	
}