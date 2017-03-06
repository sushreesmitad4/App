package com.tarang.ewallet.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.tarang.ewallet.dto.FeedbackDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @Author : kedarnathd
 * @Date : Oct 19, 2012
 * @Time : 10:09:24 PM
 * @Version : 1.0
 * @Comments: Common util class for eWallet service
 */
public class CommonUtil implements GlobalLitterals {
	
	private static final String RANDUM_PW = "G12HIJdefgPQRSTUVWXYZabc56hijklmnopqAB78CDEF0KLMNO3rstu4vwxyz9";
	
	private static final int RANDUM_PASSWORD_LENGTH = 10;
	
	private static final int NEXT_RANDOM_NUMBER = 62;
	
	public static Boolean isPasswordEqual(String oldPassword,
			String newPassword) throws WalletException {
		return oldPassword.equals(newPassword);
	}

	public static Boolean validateAttempts(Integer attempts,
			Integer attemptsCount) {
		if(null == attempts){
			return false;
		}
		return attempts >= attemptsCount;
	}

	public static String getRandomPassword() {
		StringBuffer sb = new StringBuffer();
		String ar = null;
		Random r = new Random();
		int te = 0;
		
		/* Minimum length 10 */
		for (int i = 1; i <= RANDUM_PASSWORD_LENGTH; i++) {
			te = r.nextInt(NEXT_RANDOM_NUMBER);
			ar = ar + RANDUM_PW.charAt(te);
			sb.append(RANDUM_PW.charAt(te));
		}
		return sb.toString();
	}

	public static String getDate(Date date){
		Format formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.format(date);
	}
	
	public static String getYesterdayDate(Date date){
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}
	
	public static String getOneWeekBackDate(Date date){
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		cal.add(Calendar.DATE, -WEEK_DAYS);
		return dateFormat.format(cal.getTime());
	}
	
	public static Feedback getFeedback(FeedbackDto feedbackDto){
		Feedback feedback = new Feedback();
		feedback.setParentId(feedbackDto.getParentId());
		feedback.setQuerryType(feedbackDto.getQuerryType());
		feedback.setSubject(feedbackDto.getSubject());
		feedback.setMessage(feedbackDto.getMessage());
		feedback.setDateAndTime(feedbackDto.getDateAndTime());
		feedback.setUserType(feedbackDto.getUserType());
		feedback.setUserId(feedbackDto.getUserId());
		feedback.setUserMail(feedbackDto.getUserMail());
		
		return feedback;
	}
	
	public static Timestamp getCurrentDateTimestamp(){
		Date date = new Date() ; 
		return  new Timestamp(date.getTime()); 
	}
	
	public static String convertToString(Double amount){
		if(null == amount){
			return EMPTY_STRING;
		}
		Long amountLong = (long) (amount*MILLI);
		return amountLong.toString();
	}
	
	public static Double convertToDouble(String amount){
		if(null == amount || amount.equals(EMPTY_STRING)){
			return ZERO_DOUBLE;
		}
		Double amountLong = Double.valueOf(amount);
		return (amountLong/MILLI);
	}
	
	public static String getConvertedAmountInString(Double value){
	  if(value == null){
		  return EMPTY_STRING;
	  }else{
		  return EMPTY_STRING + String.format(DOUBLE_FORMAT, value);
	  }
	}
	
	public static Date getBeforeDateAsmmddyyyy(int day){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -day);
		return cal.getTime();
	}
	
	public static String getBeforeDates(int day){
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(GlobalLitterals.DATE_FORMAT);
		cal.add(Calendar.DATE, -day);
		return  dateFormat.format(cal.getTime());
	}
	
	public static Map<Long, UserWallet> getWalletMap(List<UserWallet> wallets){
		
		Map<Long, UserWallet> map = new HashMap<Long, UserWallet>();
		for(UserWallet wallet: wallets){
			map.put(wallet.getCurrency(), wallet);
		}
		return map;
	}
	
	/**
	 * Checks the provided string for null or empty.
	 * 
	 * @param s String to check.
	 * @return True if Empty, false otherwise.
	 */
	public static boolean isEmpty(String s) {
		if ((s != null) && (s.trim().length() > 0)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Check user has login from device as first time
	 * @param authentication
	 * @return
	 */
	public static Boolean isFirstLogin(Authentication authentication){
		return isEmpty(authentication.getMsisdnNumber()) 
				| isEmpty(authentication.getSimNumber()) 
				| isEmpty(authentication.getImeiNumber());
	}
	
	/**
	 * If any one value is not matching with requested value then return true
	 * @param authentication
	 * @param request
	 * @return
	 */
	public static Boolean checkUserHasRequestedFromRegisterMobileWallet(Authentication authentication, 
			String msisdnNumber, String simNumber, String imeiNumber){
		return !isFirstLogin(authentication) 
					&& authentication.getMsisdnNumber().equals(msisdnNumber)
					&& authentication.getSimNumber().equals(simNumber)
					&& authentication.getImeiNumber().equals(imeiNumber); 
	}
	
	/**
	 * Check the user status
	 * @param authentication
	 * @throws WalletException
	 */
	public static void checkMobileUserState(Authentication authentication, Boolean isMwalletActiveCheckRequired) throws WalletException{
	   Boolean isBlocked = authentication.getmPinBlocked() != null ? authentication.getmPinBlocked() : false;
	   Boolean isActive = authentication.isActive() != null ? authentication.isActive() : false;
	   Long status = authentication.getStatus() != null ? authentication.getStatus() : new Long(0);
	   if(isBlocked) {
		   throw new WalletException(CommonConstrain.USER_BLOCK);
	   }else if(!isActive){
		   throw new WalletException(CommonConstrain.ACCOUNT_DEACTIVE);
	   }else if(UserStatusConstants.DELETED.equals(status)){
		   throw new WalletException(CommonConstrain.USER_ACCOUNT_DELETED);
	   }else if(UserStatusConstants.REJECTED.equals(status)){
		   throw new WalletException(CommonConstrain.ACCOUNT_REJECTED);
	   }else if(isMwalletActiveCheckRequired 
			   && !authentication.getIsMwalletActive()){
			throw new WalletException(CommonConstrain.MOBILE_WALLET_ACCOUNT_IS_NOT_ACTIVE);
		}
	}
}