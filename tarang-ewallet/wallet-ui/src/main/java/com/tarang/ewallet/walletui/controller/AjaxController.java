package com.tarang.ewallet.walletui.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.ReloadMoneyForm;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.DateDuration;


/**
 * @Author : kedarnathd
 * @Date : Oct 22 2012
 * @Time : 10:09:24 AM
 * @Version : 1.0
 * @Comments: Load list of master data in drop down list on page load
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/ajax")
public class AjaxController implements AttributeConstants, GlobalLitterals, AttributeValueConstants{

	private static final Logger LOGGER = Logger.getLogger(AjaxController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String loadMasterDataOnPageLoad(Map model, Locale locale,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.debug("loadMasterDataOnPageLoad method");

		Map<Long, String> map = loadAjaxRecords(request);

		if (map != null && map.size() > 0) {
			LOGGER.debug("list size" + map.size());
			try {
				response.getWriter().write(STRING_ZERO + AND_OPERATOR + PLEASE_SELECT + SEMICOLON);
			} catch (IOException e) {
				LOGGER.error(e.getMessage() , e);
			}
		} 
		if(map != null){
		Iterator<Map.Entry<Long, String>> i = map.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<Long, String> me = i.next();
			try {
				response.getWriter().write(me.getKey() + AND_OPERATOR + me.getValue() + SEMICOLON);
			} catch (IOException e) {
				LOGGER.error(e.getMessage() , e);
			}
		}
		}
		LOGGER.debug("loadMasterDataOnPageLoad ");
		return null;
	}
	
	@RequestMapping(value = "/registeredmember", method = RequestMethod.GET)
	public String loadRegisteredMemberInfo(Map model, Locale locale,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.debug("loadRegisteredMemberInfo method");

		Map<Long, String> map = loadRegisteredMemberRecords(request);

		if (!map.isEmpty()) {
			LOGGER.debug("Map size" + map.size());
			try {
				response.getWriter().write(STRING_ZERO + AND_OPERATOR + PLEASE_SELECT + SEMICOLON);
			} catch (IOException e) {
				LOGGER.error(e.getMessage() , e);
			}
		} 
		Iterator<Map.Entry<Long, String>> i = map.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<Long, String> me = i.next();
			try {
				response.getWriter().write(me.getKey() + AND_OPERATOR + me.getValue() + SEMICOLON);
			} catch (IOException e) {
				LOGGER.error(e.getMessage() , e);
			}
		}
		LOGGER.debug("loadRegisteredMemberInfo ");
		return null;
	}
	@RequestMapping(value = "/totalOccurences", method = RequestMethod.GET)
	public String totalOccurencesInfo(Map model, Locale locale,
			HttpServletRequest request, HttpServletResponse response) {
		String fromDate = request.getParameter("fromDate").toString();
		String toDate = request.getParameter("toDate").toString();
		String frequency = request.getParameter("frequency");
		try {
			if(CommonUtil.isEmpty(fromDate) | CommonUtil.isEmpty(toDate)){
				response.getWriter().write(Integer.toString(0));
				return null;
			}
			Date date1 = DateConvertion.stirngToDate(fromDate);
			Date date2 = DateConvertion.stirngToDate(toDate);
			if(!DateDuration.dateComparison(date1, date2)){
				response.getWriter().write(Integer.toString(0));
				return null;
			}
			int freq = Integer.parseInt(frequency);
			Long difference = DateDuration.dateDiff(date1, date2);
			int occurences =  difference.intValue();
			    if(freq == ZERO_INTEGER){
			    	response.getWriter().write(EMPTY_STRING);
			    } else if(freq == DAILY_FREQUENCY){
			    	response.getWriter().write(Integer.toString(occurences + 1));
			    } else if(freq == WEEKLY_FREQUENCY){
					occurences = (int) ((occurences / WEEK_DAYS) + 1) ;
					if(occurences >= 1) {
						response.getWriter().write(Integer.toString(occurences));
					} else {
						response.getWriter().write(Integer.toString(1));
					}
				} else if(freq == MONTHLY_FREQUENCY){
					occurences = (int) ((occurences / MONTH_DAYS) + 1);
					if(occurences >= 1) {
						response.getWriter().write(Integer.toString(occurences));
					} else {
						response.getWriter().write(Integer.toString(1));
					}
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage() , e);
				try{
					response.getWriter().write(EMPTY_STRING);
				} catch(Exception e1){
					LOGGER.error(e1.getMessage() , e1);
				}
		}
		return null;
	}
	 /**
	 * @comment This method to validate email enters in search input on wallet reports search page. 
	 * @author kedarnathd
	 * @param email
	 * @param utype
	 * @return
	 */
	@RequestMapping(value = "/checkemail", method = RequestMethod.GET)
	 @ResponseBody 
	 public JSONObject negativeSearchEmail(@RequestParam("email")String email, @RequestParam("utype")String utype){
		  LOGGER.info("negativeSearchEmail"); 
		  JSONObject obj = new JSONObject();
		try {
			Authentication authentication = commonService.getAuthentication(email,utype);
			if(authentication != null){
				obj.put("msg", EMPTY_STRING);
			} else{
				obj.put("msg", "error.msg.notexist.emailid");
			}
		} catch (WalletException e) {
			LOGGER.error(e.getMessage() , e);
			obj.put("msg", "error.msg.notexist.emailid");
		}
		return obj;
	 }
	
	private Map<Long, String> loadRegisteredMemberRecords(HttpServletRequest request) {
		String dataFor = request.getParameter(DATAFOR);
		String email = request.getParameter(USER_EMAIL);
		String userType = request.getParameter(USER_TYPE);
		if(dataFor.equalsIgnoreCase(CommonConstrain.REGISTERED_MEMBER)){
			return loadRegisteredMemberRecords(email, userType);
		}
		return null;
	}
	
	private Map<Long, String> loadRegisteredMemberRecords(String email, String userType){
		Map<Long, String> recordMap = new HashMap<Long, String>();
		CustomerDto dto = null;
		try {
			dto = customerService.getRegisteredMember(email, userType);
		} catch (WalletException e) {
			LOGGER.error(e.getMessage() , e);
		}
		recordMap.put(PHONE_CODE, dto.getPhoneCode());
		recordMap.put(PHONE_NO, dto.getPhoneNo());
		recordMap.put(FULL_NAME, dto.getFirstName() + SPACE_STRING + dto.getLastName());
		return recordMap;
	}
	
	private Map<Long, String> loadAjaxRecords(HttpServletRequest request) {
		String dataFor = request.getParameter(DATAFOR);
		Long id = Long.parseLong(request.getParameter(ID));
		Map<Long, String> map = new HashMap<Long, String>();
		if(dataFor.equalsIgnoreCase(CommonConstrain.REGIONS)){
			map = loadRegions(request, id);
		} else if(dataFor.equalsIgnoreCase(CommonConstrain.SUB_CATEGORIES)){
			map = loadSubCategories(request, id);
		} else if(dataFor.equalsIgnoreCase(CommonConstrain.OPERATION_TYPES)){
			Long userTypeId =  Long.parseLong(request.getParameter(USER_TYPE_ID));
			map = loadOperationTypes(request, id, userTypeId);
		} else if(dataFor.equalsIgnoreCase(CommonConstrain.SELF_TRANS_CURRENCY)){
			map = loadToWalletsCurrency(request, id);
		}
		if(map == null){
			map = new HashMap<Long, String>();
		}
		return map;
	}

	private Map<Long, String> loadRegions(HttpServletRequest request, Long countryId){
		 return MasterDataUtil.getRegions(request.getSession().getServletContext(), 
				 (Long) request.getSession().getAttribute(LANGUAGE_ID), countryId);
	}
	
	private Map<Long, String> loadSubCategories(HttpServletRequest request, Long categoryId){
		return MasterDataUtil.getMerchantSubCategories(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), categoryId);
	}
	
	private Map<Long, String> loadOperationTypes(HttpServletRequest request, Long feeServiceId, Long userTypeId){
		return MasterDataUtil.getOperationTypes(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), feeServiceId, userTypeId);
	}
	
	/**
	 * For transaction self transfer load currency details
	 * @param request
	 * @param wallet
	 * @return
	 */
	private Map<Long, String> loadToWalletsCurrency(HttpServletRequest request, Long wallet){
		Map<Long, String> toWallets = new HashMap<Long, String>();
		try {
			Map<Long, String>  wallets = MasterDataUtil.getCurrencyNames(
					request.getSession().getServletContext(), (Long) request.getSession().getAttribute(LANGUAGE_ID));
			Set qset = wallets.entrySet();
			Iterator i = qset.iterator();
			while (i.hasNext()) {
				Map.Entry me = (Map.Entry) i.next();
				toWallets.put((Long) me.getKey(), (String) me.getValue());
			}
			toWallets.remove(wallet);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return toWallets;
	}
	
	
	
	 /**
		 * @comment This method to validate email enters in search input on wallet reports search page. 
		 * @author kedarnathd
		 * @param email
		 * @param utype
		 * @return
		 */
		@RequestMapping(value = "/addMoney", method = RequestMethod.GET)
		 @ResponseBody 
		 public JSONObject addMoney(@RequestParam("email")String email, @RequestParam("utype")String utype){
			  LOGGER.info("addMoney"); 
			  JSONObject obj = new JSONObject();
			try {
				Authentication authentication = commonService.getAuthentication(email,utype);
				if(authentication != null){
					obj.put("msg", EMPTY_STRING);
				} else{
					obj.put("msg", "error.msg.notexist.emailid");
				}
			} catch (WalletException e) {
				LOGGER.error(e.getMessage() , e);
				obj.put("msg", "error.msg.notexist.emailid");
			}
			return obj;
		 }
		
		
		
		
		
	
		
		
		
		
		
		
}
