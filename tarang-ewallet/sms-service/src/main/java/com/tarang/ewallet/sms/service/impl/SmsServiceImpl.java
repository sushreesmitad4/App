package com.tarang.ewallet.sms.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.sms.dao.SmsDao;
import com.tarang.ewallet.sms.dto.OtpDto;
import com.tarang.ewallet.sms.model.OtpModel;
import com.tarang.ewallet.sms.service.SmsService;
import com.tarang.ewallet.sms.util.SmsConstants;
import com.tarang.ewallet.sms.util.SmsUtil;
import com.tarang.ewallet.util.service.UtilService;

public class SmsServiceImpl implements SmsService, SmsConstants{

	private static final Logger LOGGER = Logger.getLogger(SmsServiceImpl.class);
	
	private SmsDao smsDao;
	
	private UtilService utilService;
	
	//String URL_STR = "http://info.bulksms-service.com/WebServiceSMS.aspx?";
	String USER_STR = "User=";
	String PASSWORD_STR = "&passwd=";
	String MOBILENUMBER_STR = "&mobilenumber=";
	String MESSAGE_STR = "&message=";
	String SID_STR = "&sid=";
	String MTYPE_STR = "&mtype=N";

	/*String USER_VAL = "T20160401803";
	String PASSWORD_VAL = "Ghfr5Se34";
	String SID_VAL = "PSPUPI";*/

	
	public SmsServiceImpl(UtilService utilService, SmsDao smsDao){
		this.utilService = utilService;
		this.smsDao = smsDao;
	}
	public OtpDto sendOTP(OtpDto otpDto) throws WalletException {
		// TODO:
		/*
		 * 1. Generate 6 digit OTP code.
		 * 2. Save OTP based on mobile number & customer id.
		 * 3. Send OTP as SMS.
		 * */
		LOGGER.debug("Sending OTP to mobile number...");
		try{
			otpDto.setOtpNumber(SmsUtil.getOtpNumber(utilService.getOtpLength().intValue()));
			OtpModel otpModel = SmsUtil.getOtpModel(otpDto, utilService.getOtpExpiredInMinutes().intValue());
			String bypassSmsGateway = utilService.getSmsGatewayBypass();
			
			if("N".equalsIgnoreCase(bypassSmsGateway)){
				sendSmsApi(otpModel);
			}
			
			otpModel = smsDao.saveOTP(otpModel);
			LOGGER.debug("Sending OTP to mobile number completed...");
			return otpDto;
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			throw new WalletException("failed.to.sent.otp.to.device", ex);
		}
		
	}
		
	
	public Boolean authenticateOTP(OtpDto otpDto) throws WalletException {
		// TODO Auto-generated method stub
		/*
		 * 1. Get OTP as per customer id & mobile number.
		 * 2. If found the OTP then validate the OTP.
		 *  
		 * 
		 * */
		LOGGER.debug("Authenticating OTP for mobile number...");
		try{
			Boolean isValidOtp = Boolean.FALSE; 
			OtpModel otpModel = smsDao.authenticateOTP(otpDto);
			if(null != otpModel){
				if(new Date().getTime() <= otpModel.getOtpExpDate().getTime()){
					isValidOtp = Boolean.TRUE;
				}else{
					isValidOtp = Boolean.FALSE;
					otpModel.setIsOtpExpired(Boolean.TRUE);
					smsDao.updateOTP(otpModel);
				}
			}			
			
			LOGGER.debug("Authenticating OTP for mobile number completed...");
			return isValidOtp;
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			throw new WalletException("failed.to.authenticat.otp", ex);
		}
	}
	
	private void sendSmsApi(OtpModel otpModel){
		try{
			
			String smsStatus = null;
			String message = "Dear customer Thanks for registering with us. Your OTP is "+otpModel.getOtpNumber();
			otpModel.setMessage(message);
			String urlStr = utilService.getSmsGatewayUrl() + USER_STR + utilService.getSmsGatewayUserName() + PASSWORD_STR + utilService.getSmsGatewayPassword() 
					+ MOBILENUMBER_STR + otpModel.getMobileNumber() + MESSAGE_STR + message  + SID_STR + utilService.getSmsGatewaySenderId() + MTYPE_STR;
			urlStr = urlStr.replaceAll(" ", "%20");
			URL url = new URL(urlStr);
			HttpURLConnection conn =(HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.connect();
			BufferedReader rd =new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuffer buffer =new StringBuffer();
			while ((line =rd.readLine()) != null){
				buffer.append(line).append("\n");
			}
			smsStatus = buffer.toString(); 
			LOGGER.debug("SMS gateway status : "+smsStatus);
			rd.close();
			conn.disconnect();
			if(!CommonUtil.isEmpty(smsStatus) && smsStatus.contains(CommonConstrain.OK)){
				otpModel.setIsSmsSent(Boolean.TRUE);
			}else{
				otpModel.setIsSmsSent(Boolean.FALSE);
			}			
			
		}catch(Exception ex){
			otpModel.setIsSmsSent(Boolean.FALSE);
		}
	}
	
	
	
	
}
