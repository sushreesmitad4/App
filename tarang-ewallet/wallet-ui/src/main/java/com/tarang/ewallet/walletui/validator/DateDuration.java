package com.tarang.ewallet.walletui.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class DateDuration {
	
	private static final Logger LOGGER = Logger.getLogger(DateDuration.class);
	
	public static int diff(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);
		int diffDay = 0;

		if (c1.before(c2)) {
			diffDay = countDiffDay(c1, c2);
		} else {
			diffDay = countDiffDay(c2, c1);
		}
		return diffDay;
	}

	public static long dateDiff(Date date1, Date date2) {
		long diffDay = diff(date1, date2);
		LOGGER.debug("Different Day : " + diffDay);
		return diffDay;
	}

	public static int countDiffDay(Calendar c1, Calendar c2) {
		int returnInt = 0;
		while (!c1.after(c2)) {
			c1.add(Calendar.DAY_OF_MONTH, 1);
			returnInt++;
		}
		if (returnInt > 0) {
			returnInt = returnInt - 1;
		}
		return returnInt;
	}
	  
	public static Boolean validateDOJ(String doj){   
		
		/* the date is passed as dd/mm/yyyy */  
		StringTokenizer st = new StringTokenizer(doj, "/"); 
		int month = Integer.parseInt(st.nextToken());  
		int day = Integer.parseInt(st.nextToken());  
		int year = Integer.parseInt(st.nextToken());  
		   
		Calendar today = Calendar.getInstance();  
		int currentYear = today.get(Calendar.YEAR);  
		
		/* since system months start from 0 */ 
		int currentMonth = today.get(Calendar.MONTH) + 1;    
		int currentDay = today.get(Calendar.DAY_OF_MONTH);  
		        
		if (year > currentYear){
			return false;
		}
		if (year == currentYear) {
			if (month > currentMonth) {
				return false;
			}
			if (month == currentMonth && day >= currentDay) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean dateComparison(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);     

		if (c1.before(c2)) {
			/* date1 has to come before date2 , then it return true */	
           return true;  
		} 
		/* date1 has to come after date2, then it return false */
		return false; 
	}	  
}
