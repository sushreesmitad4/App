/**
 * 
 */
package com.tarang.ewallet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author vasanthar
 *
 */
@SuppressWarnings("deprecation")
public class DateConvertion {
	
	private static final Logger LOGGER = Logger.getLogger(DateConvertion.class);
	
	public static final int CALANDER_START_YEAR = 1900;
	
	public static final int TO_HOURS = 23;
	
	public static final int TO_MINUTES = 59;

	public static final int TO_SECANDS = 59;

	public static Date stirngToDate(String dateString) {
		try{
			SimpleDateFormat formatter = new SimpleDateFormat(GlobalLitterals.DATE_FORMAT);
			return formatter.parse(dateString);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			return null;
		}
	}
	
	public static Date stirngToDateTimeStamp(String dateString) {
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			return formatter.parse(dateString);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			return null;
		}
	}
	
	public static String dateTimeToString(Date date) {
		try{
			if(null == date){
				return null;
			}
			SimpleDateFormat formatter = new SimpleDateFormat(GlobalLitterals.DATE_TIME_FORMAT);
			return formatter.format(date);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			return null;
		}
	}
	
	public static String dateToString(Date date) {
		try{
			if(null == date){
				return null;
			}
			SimpleDateFormat formatter = new SimpleDateFormat(GlobalLitterals.DATE_FORMAT);
			return formatter.format(date);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			return null;
		}
	}	
	
	/*  used only for creating unique code for one time IP check service */
	public static String getUniqueCode(Date date) {
		try{
			if(null == date){
				return null;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("hhmmss");
			return formatter.format(date);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			return null;
		}
	}
	
	/* used only for creating unique order id for sending PG service */
	public static String dateToStringWithTimeZone(Date date) {
		try{
			if(null == date){
				return null;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
			return formatter.format(date);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			return null;
		}
	}	

	public static String getPgDateAndTime(Date date) {
		try{
			if(null == date){
				return null;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyhhmmss");
			return formatter.format(date);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			return null;
		}
	}	

	public static String getPgOrderId(Date date) {
		try{
			if(null == date){
				return null;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyhhmmss");
			return formatter.format(date);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			return null;
		}
	}	

	public static Date futureDate(Date date, int numberOfDays){
		try {
			if(null == date){
				return null;
			}
			Calendar cal = Calendar.getInstance();   
			cal.setTime(date);   
			cal.add(Calendar.DATE, numberOfDays);   
			return cal.getTime();  
		} catch(Exception ex){
			LOGGER.error(ex.getMessage() , ex );
			return null;
		}
	}
	
	public static Date pastDate(Date date, int numberOfDays){
		try {
			if(null == date){
				return null;
			}
			Calendar cal = Calendar.getInstance();   
			cal.setTime(date);   
			cal.add(Calendar.DATE, (-1) * numberOfDays);   
			return cal.getTime();  
		} catch(Exception ex){
			LOGGER.error(ex.getMessage() , ex );
			return null;
		}
	}

	// to get fromDate time stamp as 00:00:00
	public static Date getFromDate(int days){
		Date d = DateConvertion.pastDate(new Date(), days);
		Calendar cal = Calendar.getInstance();
		cal.set(d.getYear() + CALANDER_START_YEAR, d.getMonth(), d.getDate(), 0, 0, 0);
		return cal.getTime();
	}
	
	// to get toDate time stamp as 23:59:59
	public static Date getToDate(int days){
		Date d = DateConvertion.pastDate(new Date(), days);
		Calendar cal = Calendar.getInstance();
		cal.set(d.getYear()+ CALANDER_START_YEAR, d.getMonth(), d.getDate(), TO_HOURS, TO_MINUTES, TO_SECANDS);
		return cal.getTime();
	}
		
	// to get fromDate time stamp as 00:00:00
	public static Date getFromDate(Date fDate, int days){
		Date d = DateConvertion.pastDate(fDate, days);
		Calendar cal = Calendar.getInstance();
		cal.set(d.getYear() + CALANDER_START_YEAR, d.getMonth(), d.getDate(), 0, 0, 0);
		return cal.getTime();
	}
	
	// to get toDate time stamp as 23:59:59
	public static Date getToDate(Date tDate, int days){
		Date d = DateConvertion.pastDate(tDate, days);
		Calendar cal = Calendar.getInstance();
		cal.set(d.getYear() + CALANDER_START_YEAR, d.getMonth(), d.getDate(), TO_HOURS, TO_MINUTES, TO_SECANDS);
		return cal.getTime();
	}
	
	// to get fromDate time stamp as 00:00:00
	public static Date getFromDate(Date fDate){
		Calendar cal = Calendar.getInstance();
		cal.set(fDate.getYear() + CALANDER_START_YEAR, fDate.getMonth(), fDate.getDate(), 0, 0, 0);
		return cal.getTime();
	}
	
	// to get toDate time stamp as 23:59:59
	public static Date getToDate(Date tDate){
		Calendar cal = Calendar.getInstance();
		cal.set(tDate.getYear() + CALANDER_START_YEAR, tDate.getMonth(), tDate.getDate(), TO_HOURS, TO_MINUTES, TO_SECANDS);
		return cal.getTime();
	}		

	public static Date getPastDateByHours(int hours){
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.HOUR, -hours);
		return new Date(cal2.getTimeInMillis());
	}
	
	public static Date getFutureDateByHours(int hours){
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.HOUR, +hours);
		return new Date(cal2.getTimeInMillis());
	}
	
	public static Date getFutureDateByMinutes(int otpExpTime){
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.MINUTE, +otpExpTime);
		return new Date(cal2.getTimeInMillis());
	}
	
	public static int getDayOfWeek(Date date){
		Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static int getDayOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getDayOfHour(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static int getDayOfHourMinute(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}
	
	/* to get toDate time stamp as 23:59:59 */
	public static Date getToDateWithEndTime(String date){
		Date d = DateConvertion.pastDate(stirngToDate(date), 0);
		Calendar cal = Calendar.getInstance();
		cal.set(d.getYear()+ CALANDER_START_YEAR, d.getMonth(), d.getDate(), TO_HOURS, TO_MINUTES, TO_SECANDS);
		return cal.getTime();
	}
	
	public static boolean isExpire(String date){
	    if(date.isEmpty() || date.trim().equals("")){
	        return false;
	    }else{
	            SimpleDateFormat sdf =  new SimpleDateFormat("MMM-dd-yyyy hh:mm:ss a"); // Jan-20-2015 1:30:55 PM
	               Date d=null;
	               Date d1=null;
	            String today=   getToday("MMM-dd-yyyy hh:mm:ss a");
	            try {
	                //System.out.println("expdate>> "+date);
	                //System.out.println("today>> "+today+"\n\n");
	                d = sdf.parse(date);
	                d1 = sdf.parse(today);
	                if(d1.compareTo(d) <0){// not expired
	                    return false;
	                }else if(d.compareTo(d1)==0){// both date are same
	                            if(d.getTime() < d1.getTime()){// not expired
	                                return false;
	                            }else if(d.getTime() == d1.getTime()){//expired
	                                return true;
	                            }else{//expired
	                                return true;
	                            }
	                }else{//expired
	                    return true;
	                }
	            } catch (ParseException e) {
	                e.printStackTrace();                    
	                return false;
	            }
	    }
	}


	  public static String getToday(String format){
	     Date date = new Date();
	     return new SimpleDateFormat(format).format(date);
	 }
	
}
