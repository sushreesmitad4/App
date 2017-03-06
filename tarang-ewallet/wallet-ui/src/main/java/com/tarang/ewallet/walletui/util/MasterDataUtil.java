/**
 * 
 */
package com.tarang.ewallet.walletui.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.model.Country;
import com.tarang.ewallet.model.Currency;
import com.tarang.ewallet.model.FeeOperationType;
import com.tarang.ewallet.model.Language;
import com.tarang.ewallet.model.Menu;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;


/**
 * @author  : prasadj
 * @date    : Oct 10, 2012
 * @time    : 6:48:07 PM
 * @version : 1.0.0
 * @comments: The MasterData Util service is provides the utility methods to get master data from the cache (servlet context)
 *
 */
@SuppressWarnings("unchecked")
public class MasterDataUtil implements MasterDataConstants, AttributeConstants, GlobalLitterals, UserStatusConstants {

	private static final Logger LOGGER = Logger.getLogger(MasterDataUtil.class);
	
	/**
	 * @param servletContext
	 * @param languageId
	 * @param type - data type (ex: currencies, card.types etc)
	 * @return a map it holds the string value and its id as a key
	 */
	public static Map<Long, String> getSimpleDataMap(ServletContext servletContext, Long languageId, String type){
		Map<Long, Map<Long, String>> dataMap = (Map<Long, Map<Long, String>>) servletContext.getAttribute(type);
		
		Map<Long, String> map = dataMap.get(languageId);
		if(map == null || map.isEmpty()) {
			map = dataMap.get(MD_DEFAULT_PROPS_CODE);
		}
		return sortByValue(map);
	}

	/**
	 * @param servletContext
	 * @param languageId
	 * @param type - data type (ex: currencies, card.types etc)
	 * @return a map it holds the string value and its id as a key
	 */
	public static Map<Long, String> getSimpleDataMap(ServletContext servletContext, Long languageId, Long userTypeId, String type){
		Map<Long, Map<Long, Map<Long, String>>> dataMap = (Map<Long, Map<Long, Map<Long, String>>>) servletContext.getAttribute(type);
		
		Map<Long, Map<Long, String>> map = dataMap.get(languageId);
		if(map == null || map.isEmpty()) {
			map = dataMap.get(MD_DEFAULT_PROPS_CODE);
		}
		Map<Long, String> reportTypesMap = map.get(userTypeId);
		return sortByValue(reportTypesMap);
	}

	/**
	 * @param servletContext
	 * @param languageId
	 * @param type - data type (ex: currencies, card.types etc)
	 * @return a map it holds the string value and its id as a key
	 */
	public static Map<Long, String> getSimpleDataMapForUserTypeCustMer(ServletContext servletContext, Long languageId, String type){
		Map<Long, String> dataMap = getSimpleDataMap(servletContext, languageId, type);
		Map<Long, String> map = new HashMap<Long, String>();
		for(Map.Entry<Long, String> entry : dataMap.entrySet()){
			if(!(entry.getKey().equals(ADMIN_USER_TYPE_ID) || entry.getKey().equals(CUSTOMER_NON_REGISTER_TYPE_ID))){
				map.put(entry.getKey(), entry.getValue());
			}
		}
		return map;
	}

	/**
	 * @param servletContext
	 * @param languageId
	 * @param type - data type (ex: currencies, card.types etc)
	 * @param propertyId
	 * @return a String value of specified property in specific language
	 */
	public static String getSimpleData(ServletContext servletContext, Long languageId, String type, Long propertyId){
		return getSimpleDataMap(servletContext, languageId, type).get(propertyId);
	}
	
	/**
	 * @param servletContext
	 * @param languageId
	 * @param type - data type (ex: languages, menus etc)
	 * @return a map it holds the Object(Language, Function, Country etc.) as value and its id as a key
	 */
	public static Map<Long, Object> getObjectDataMap(ServletContext servletContext, Long languageId, String type){
		Map<Long, Map<Long, Object>> dataMap = (Map<Long, Map<Long, Object>>) servletContext.getAttribute(type);
		
		Map<Long, Object> map = dataMap.get(languageId);
		if(map == null || map.isEmpty()) {
			map = dataMap.get(MD_DEFAULT_PROPS_CODE);
		}
		return map;
	}

	/**
	 * @param servletContext
	 * @param languageId
	 * @param type - data type (ex: languages, menus etc)
	 * @return a map it holds the Function Object as value and its id as a key
	 */
	public static Map<Long, Menu> getMenusDataMap(ServletContext servletContext, Long languageId, String type){
		Map<Long, Map<Long, Menu>> dataMap = (Map<Long, Map<Long, Menu>>) servletContext.getAttribute(type);
		
		Map<Long, Menu> map = dataMap.get(languageId);
		if(map == null || map.isEmpty()) {
			map = dataMap.get(MD_DEFAULT_PROPS_CODE);
		}
		return map;
	}
	
	/**
	 * @param servletContext
	 * @param languageId
	 * @param type - data type (ex: languages, menus etc)
	 * @param propertyId
	 * @return a Object(Language, Menus, Country etc) value of specified property in specific language
	 */
	public static Object getObjectData(ServletContext servletContext, Long languageId, String type, Long propertyId){
		return getObjectDataMap(servletContext, languageId, type).get(propertyId);
	}
	
	/**
	 * @param servletContext
	 * @param languageId
	 * @return a map it holds the language code as value and its id as key for a specific language
	 */
	public static Map<Long, String> getLanguageCodes(ServletContext servletContext, Long languageId){
		
		Map<Long, Object> data = getObjectDataMap(servletContext, languageId, MD_LANGUAGES);
		Iterator<Long> keys = data.keySet().iterator();
		Map<Long, String> namesMap = new HashMap<Long, String>();
		Long key = null;
		while(keys.hasNext()){
			key = keys.next();
			namesMap.put(key, ((Language)data.get(key)).getCode());
		}
		return sortByValue(namesMap);
	}
	
	/**
	 * @param servletContext
	 * @param languageId
	 * @return a map it holds the language name as value and its id as key for a specific language
	 */
	public static Map<Long, String> getLanguageNames(ServletContext servletContext, Long languageId){
		
		Map<Long, Object> data = getObjectDataMap(servletContext, languageId, MD_LANGUAGES);
		
		if(data == null || data.isEmpty()) {
			data = getObjectDataMap(servletContext, MD_DEFAULT_PROPS_CODE, MD_LANGUAGES);
		}
		
		Iterator<Long> keys = data.keySet().iterator();
		Map<Long, String> namesMap = new HashMap<Long, String>();
		Long key = null;
		while(keys.hasNext()){
			key = keys.next();
			namesMap.put(key, ((Language)data.get(key)).getName());
		}
		return sortByValue(namesMap);
	}
	
	/**
	 * @param servletContext
	 * @param languageId
	 * @return a map it holds the country code as value and its id as key for a specific language
	 */
	public static Map<Long, String> getCountryCodes(ServletContext servletContext, Long languageId){
		
		Map<Long, Object> data = getObjectDataMap(servletContext, languageId, MD_COUNTRIES);
		if(data == null || data.isEmpty()) {
			data = getObjectDataMap(servletContext, MD_DEFAULT_PROPS_CODE, MD_COUNTRIES);
		}
		Iterator<Long> keys = data.keySet().iterator();
		Map<Long, String> namesMap = new HashMap<Long, String>();
		Long key = null;
		while(keys.hasNext()){
			key = keys.next();
			namesMap.put(key, ((Country)data.get(key)).getCode());
		}
		return sortByValue(namesMap);
	}
	
	/**
	 * @param servletContext
	 * @param languageId
	 * @return a map it holds the country name as value and its id as key for a specific language
	 */
	public static Map<Long, String> getCountryNames(ServletContext servletContext, Long languageId){
		
		Map<Long, Object> data = getObjectDataMap(servletContext, languageId, MD_COUNTRIES);
		
		if(data == null || data.isEmpty()) {
			data = getObjectDataMap(servletContext, MD_DEFAULT_PROPS_CODE, MD_COUNTRIES);
		}

		Iterator<Long> keys = data.keySet().iterator();
		Map<Long, String> namesMap = new HashMap<Long, String>();
		Long key = null;
		while(keys.hasNext()){
			key = keys.next();
			namesMap.put(key, ((Country)data.get(key)).getName());
		}
		return sortByValue(namesMap);
	}

	/**
	 * @param servletContext
	 * @param languageId
	 * @return a map it holds the merchant business sub-category as value 
	 * and its id as key for a specific language for a specific merchant business category
	 */
	public static Map<Long, String> getMerchantSubCategories(ServletContext servletContext, Long languageId, Long mbCategoryId){
		
		Map<Long, Map< Long, Map<Long, String>>> data = (Map<Long, Map< Long, Map<Long, String>>>)servletContext.getAttribute(MD_MERCHANT_SUB_CATEGORIES);
		
		Map< Long, Map<Long, String>> categories = data.get(languageId);
		if(categories == null || categories.isEmpty()) {
			categories = data.get(MD_DEFAULT_PROPS_CODE);
		}
		return sortByValue(categories.get(mbCategoryId));
	}

	/**
	 * @param servletContext
	 * @param languageId
	 * @return a map it holds the region as value 
	 * and its id as key for a specific language for a specific country
	 */
	public static Map<Long, String> getRegions(ServletContext servletContext, Long languageId, Long countryId){
		
		Map<Long, Map< Long, Map<Long, String>>> data = (Map<Long, Map< Long, Map<Long, String>>>)servletContext.getAttribute(REGIONS);
		
		Map< Long, Map<Long, String>> countries = data.get(languageId);
		if( null == countries || countries.isEmpty()){
			countries = data.get(MD_DEFAULT_PROPS_CODE);
		}
		Map<Long, String> regions = countries.get(countryId);
		
		if( null == regions) {
			regions = new HashMap<Long, String>();
		}
		return sortByValue(regions);
	}
	
	/**
	 * @param servletContext
	 * @param languageId
	 * @param operationParentId
	 * @return a map it holds the operation type as value 
	 * and its id as key for a specific language for a specific operation
	 */
	public static Map<Long, String> getOperationTypes(ServletContext servletContext, Long languageId, Long feeServiceId, Long userTypeId){
		  
		Map<Long, Map<Long, FeeOperationType>> data = (Map<Long, Map<Long, FeeOperationType>>) servletContext.getAttribute(FEE_OPERATION_TYPES);

		Map<Long, FeeOperationType> opfeeService = data.get(languageId);
		if ( null == opfeeService || opfeeService.isEmpty()) {
			opfeeService = data.get(MD_DEFAULT_PROPS_CODE);
		}
		Map<Long, String> operationType = new HashMap<Long, String>();
		for (Map.Entry<Long, FeeOperationType> entry : opfeeService.entrySet()) {
			FeeOperationType fee = entry.getValue();
			if (fee.getFeeServiceId().equals(feeServiceId) && fee.getUserTypeId().equals(userTypeId)) {
				operationType.put(fee.getId(), fee.getName());
			}
		}
		return sortByValue(operationType);
	}
	
	/**
	 * @param servletContext
	 * @param languageId
	 * @return a map it holds the operation type as value 
	 * and its id as key for a specific language for a specific operation
	 */
	public static Map<Long, String> getAllOperationTypes(ServletContext servletContext, Long languageId){
		  
		Map<Long, Map<Long, FeeOperationType>> data = (Map<Long, Map<Long, FeeOperationType>>) servletContext.getAttribute(FEE_OPERATION_TYPES);

		Map<Long, FeeOperationType> opfeeService = data.get(languageId);
		if ( null == opfeeService || opfeeService.isEmpty()) {
			opfeeService = data.get(MD_DEFAULT_PROPS_CODE);
		}
		Map<Long, String> operationType = new HashMap<Long, String>();
		for (Map.Entry<Long, FeeOperationType> entry : opfeeService.entrySet()) {
			FeeOperationType fee = entry.getValue();
			operationType.put(fee.getId(), fee.getName());
		}
		return sortByValue(operationType);
	}
	
	public static String getCountryName(HttpServletRequest request, String type, Long propertyId) {
		Country country = (Country) MasterDataUtil.getObjectData(request.getSession().getServletContext(),
				(Long) request.getSession(false).getAttribute(LANGUAGE_ID), type, propertyId);
		return country.getName();
	}
	
	public static String getStatusName(HttpServletRequest request, Long statusId) {
		return MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataUtil.MD_USER_STATUSES).get(statusId);
	}
	
	public static Map<Long, String> getPhoneCodes(ServletContext servletContext){
		Map<Long, Object> countries = getObjectDataMap(servletContext, MD_DEFAULT_PROPS_CODE, MD_COUNTRIES);
		Iterator<Long> keys = countries.keySet().iterator();
		Long key = null;
		Map<Long, String> countriesMap = new HashMap<Long, String>();
		while(keys.hasNext()){
			key = keys.next();
			countriesMap.put(key, ((Country)countries.get(key)).getPcode());
		}
		return sortByValue(countriesMap);
		
	}

	public static Map<Long, Long> getCountryCurrencyIds(ServletContext servletContext){
		Map<Long, Object> countries = getObjectDataMap(servletContext, MD_DEFAULT_PROPS_CODE, MD_COUNTRIES);
		Iterator<Long> keys = countries.keySet().iterator();
		Long key = null;
		Map<Long, Long> countriesMap = new HashMap<Long, Long>();
		while(keys.hasNext()){
			key = keys.next();
			countriesMap.put(key, ((Country)countries.get(key)).getCurrency());
		}
		return sortByValue(countriesMap);
		
	}

	public static Long getCountryCurrencyId(ServletContext servletContext, Long countryId){
		Map<Long, Object> countries = getObjectDataMap(servletContext, MD_DEFAULT_PROPS_CODE, MD_COUNTRIES);
		return((Country)countries.get(countryId)).getCurrency();
	}
	
	public static String getCountryCurrencyCode(ServletContext servletContext, Long countryId){
		Map<Long, Object> currencies = getObjectDataMap(servletContext, MD_DEFAULT_PROPS_CODE, MasterDataConstants.MD_CURRENCIES);
		Long currencyId = getCountryCurrencyId(servletContext, countryId);
		return ((Currency) currencies.get(currencyId)).getCode();
	}

	public static Map<Long, String> getCurrencyNames(ServletContext servletContext, Long languageId){
		Map<Long, Object> currencies = getObjectDataMap(servletContext, languageId, MasterDataConstants.MD_CURRENCIES);
		
		Iterator<Long> keys = currencies.keySet().iterator();
		Long key = null;
		Map<Long, String> courencyMap = new HashMap<Long, String>();
		while(keys.hasNext()){
			key = keys.next();
			courencyMap.put(key, ((Currency)currencies.get(key)).getCode());
		}
		return sortByValue(courencyMap);
	}
	
	public static String getCurrencyName(ServletContext servletContext, Long languageId, Long currencyId){
		Map<Long, String> currencies = getCurrencyNames(servletContext, languageId);
		return currencies.get(currencyId);
	}
	
	public static String getUserTypeName(ServletContext servletContext, Long languageId, Long userTypeId){
		Map<Long, String> userTypes = MasterDataUtil.getSimpleDataMap(servletContext,
				languageId, MasterDataConstants.MD_USER_TYPES); 
		return userTypes.get(userTypeId);
	}
	
	public static String getServiceName(ServletContext servletContext, Long languageId, Long serviceId){
		Map<Long, String> services = MasterDataUtil.getSimpleDataMap(servletContext,
				languageId, MasterDataConstants.FEE_SERVICE_TYPES); 
		return services.get(serviceId);
	}
	
	

	public static Map<Long, String> getStatusListById(HttpServletRequest request, Long statusId) {

		Map<Long, String> specificMap = new HashMap<Long, String>();
		
		Map<Long, String> map = MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute("languageId"), 
				MasterDataUtil.MD_USER_STATUSES);
		 
		for (Map.Entry<Long, String> entry : map.entrySet()) {
			specificMap.put(entry.getKey(), entry.getValue());
		} 
				
		/*  1  Approve | 2  Rejected | 3  Lock | 4  Pending | 5 Deleted */
		//we are not going to display 5.deleted
		specificMap.remove(DELETED); 
		if (statusId .equals(REJECTED) || statusId.equals(LOCKED)) {
			/* if 2.Rejected & 3.Lock then we are going to  displaying only 1.Approve */
			specificMap.remove(REJECTED);
			specificMap.remove(LOCKED);
			specificMap.remove(PENDING);
		} else if (statusId.equals(APPROVED)) {
			/* if 1.Approve then  we are going to displaying  3.Lock */
			specificMap.remove(APPROVED);
			specificMap.remove(REJECTED);
			specificMap.remove(PENDING);
		} else if (statusId.equals(PENDING)) {
			/* if 4.Pending  then we are going to  displaying only 1.Approve 2.Rejected */
			specificMap.remove(PENDING);
			specificMap.remove(LOCKED);
		
		}
		return specificMap;
	}
	
	@SuppressWarnings({ "rawtypes" })
	private	static Map sortByValue(Map map) {
		try{
		     List list = new LinkedList(map.entrySet());
		     Collections.sort(list, new Comparator() {
		          public int compare(Object o1, Object o2) {
		               return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
		          }
		     });

		    Map result = new LinkedHashMap();
		    for (Iterator it = list.iterator(); it.hasNext();) {
		        Map.Entry entry = (Map.Entry)it.next();
		        result.put(entry.getKey(), entry.getValue());
		    }
		    return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			return map;
		}
	}
	
	public static void getUserStatus(List<UserDto> userDtos, Map<Long, String> map){
		for(UserDto userDto : userDtos){
			userDto.setStatusName(map.get(userDto.getStatus()));
		}
	}
	
	public static void getUserActive(List<UserDto> userDtos, String active, String inactive){
		for(UserDto userDto : userDtos){
			if(userDto.isActive()){
				userDto.setActiveName(active);
			} else {
				userDto.setActiveName(inactive);
			}
			
		}
	}
	
	public static Boolean papulateActiveData(Long status){
		Boolean active = null;
		if(GlobalLitterals.ACTIVE_STATUS.equals(status)){
			active = Boolean.TRUE;
		} else if(GlobalLitterals.IN_ACTIVE_STATUS.equals(status)){
			active = Boolean.FALSE;
		}
		return active;
	}
	
	public static Map<Long, String> getUserActiveMap(String active, String inactive){
		Map<Long, String> activeMap = new HashMap<Long, String>();
		activeMap.put(GlobalLitterals.ACTIVE_STATUS, active);
		activeMap.put(GlobalLitterals.IN_ACTIVE_STATUS, inactive);
		return activeMap;
	}
	
	public static Long getTypeOfRequest(ServletContext servletContext, Long languageId, String typeOfRequest){
		  Long channelId = null;
		  Map<Long, String> services = MasterDataUtil.getSimpleDataMap(servletContext, 
		    languageId, MasterDataConstants.TYPE_OF_REQUEST);
		  for (Map.Entry<Long, String> entry : services.entrySet()) {
		   if(typeOfRequest.equals(entry.getValue())){
		    channelId = entry.getKey();
		    break;
		   }
		  }
		  return channelId;
	}
}