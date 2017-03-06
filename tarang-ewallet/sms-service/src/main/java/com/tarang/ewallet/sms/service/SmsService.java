package com.tarang.ewallet.sms.service;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.sms.dto.OtpDto;

public interface SmsService {

	public OtpDto sendOTP(OtpDto otpDto) throws WalletException;
	
	public Boolean authenticateOTP(OtpDto otpDto) throws WalletException;
	
}
