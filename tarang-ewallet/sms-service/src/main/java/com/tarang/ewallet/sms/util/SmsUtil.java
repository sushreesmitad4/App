package com.tarang.ewallet.sms.util;

import java.util.Date;
import java.util.Random;

import com.tarang.ewallet.sms.dto.OtpDto;
import com.tarang.ewallet.sms.model.OtpModel;
import com.tarang.ewallet.util.DateConvertion;

public class SmsUtil {

	
	public static OtpModel getOtpModel(OtpDto otpDto, int otpExpTime){
		OtpModel otpModel = new OtpModel();
		
		otpModel.setMobileCode(otpDto.getMobileCode());
		otpModel.setMobileNumber(otpDto.getMobileNumber());
		otpModel.setCustomerId(otpDto.getCustomerId());
		otpModel.setEmailId(otpDto.getEmailId());
		otpModel.setOtpModuleName(otpDto.getOtpModuleName());
		otpModel.setMessage(otpDto.getMessage());
		otpModel.setOtpDate(new Date());
		otpModel.setOtpExpDate(DateConvertion.getFutureDateByMinutes(otpExpTime));
		otpModel.setOtpNumber(otpDto.getOtpNumber());
				
		return otpModel;
	}
	
	public static Long getOtpNumber(int otpLength){
		return getRandomNumber(otpLength, new Random());
	}
	
	public static Long getRandomNumber(final int digCount, Random rnd){
	    final char[] ch = new char[digCount];
	    for(int i = 0; i < digCount; i++){
	        ch[i] =
	            (char) ('0' + (i == 0 ? rnd.nextInt(9) + 1 : rnd.nextInt(10)));
	    }
	    return new Long(new String(ch));
	}
	
}
