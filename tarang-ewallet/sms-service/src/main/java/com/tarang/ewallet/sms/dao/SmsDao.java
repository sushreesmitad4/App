package com.tarang.ewallet.sms.dao;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.sms.dto.OtpDto;
import com.tarang.ewallet.sms.model.OtpModel;

public interface SmsDao {
	
	public OtpModel saveOTP(OtpModel otpModel) throws WalletException;

	public OtpModel authenticateOTP(OtpDto otpDto) throws WalletException;

	public void updateOTP(OtpModel otpModel) throws WalletException;

}
