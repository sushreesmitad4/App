package com.tarang.ewallet.walletui.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.constants.Dispute;


public class DisputeSearchUtil implements Dispute, GlobalLitterals {
	
	private static final Logger LOGGER = Logger.getLogger(DisputeSearchUtil.class);

	public static boolean mailValidation(String email, CommonService commonService){
		try{
			Authentication authentication = commonService.getAuthentication(email, MERCHANT_USER_TYPE);
			//bug fix 4372 by sivaram
			if(authentication != null){
				return true;
			}
		} catch(Exception e){
			LOGGER.error( e.getMessage() , e );
		}
		return false;
	}
	
	public static boolean mailValidationForC(String email, CommonService commonService){
		try{
			Authentication authentication = commonService.getAuthentication(email, CUSTOMER_USER_TYPE);
			//bug fix 4372 by sivaram
			if(authentication != null){
				return true;
			}
		} catch(Exception e){
			LOGGER.error( e.getMessage() , e );
		}
		return false;
	}
	
	public static boolean checkDate(String date, int disputeDays ){
		String maxDisputeDate = CommonUtil.getBeforeDates(disputeDays);
		if(dateCompare(maxDisputeDate, date)){
			return true;
		}
		return false;	
	}
	
	private static boolean dateCompare(String maxDisputeDate, String date){
		SimpleDateFormat sdf = new SimpleDateFormat(GlobalLitterals.DATE_FORMAT);
		boolean result = false;
		try{
			Date date1 = sdf.parse(maxDisputeDate);
	    	Date date2 = sdf.parse(date);
	    	Calendar cal1 = Calendar.getInstance();
	    	Calendar cal2 = Calendar.getInstance();
	    	cal1.setTime(date1);
	    	cal2.setTime(date2);
	    	if(cal1.after(cal2)){
	    		result = false;
	    	}
	    	if(cal1.before(cal2)){
	    		result =  true;
	    	}
	    	if(cal1.equals(cal2)){
	    		result =  true;
	    	}
		} catch(Exception e){
              LOGGER.error(e.getMessage() , e );		
 		}
		return result;
	}
}
