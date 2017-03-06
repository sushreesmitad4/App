package com.tarang.ewallet.sms.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.sms.dao.SmsDao;
import com.tarang.ewallet.sms.dto.OtpDto;
import com.tarang.ewallet.sms.model.OtpModel;

public class SmsDaoImpl implements SmsDao{
	
	private static final Logger LOGGER = Logger.getLogger(SmsDaoImpl.class);
	
    private HibernateOperations hibernateOperations;
    
    private SmsDaoImpl(HibernateOperations hibernateOperations){
    	this.hibernateOperations = hibernateOperations;
    }

	public OtpModel saveOTP(OtpModel otpModel) throws WalletException {
		LOGGER.debug("saving OTP...");
		hibernateOperations.save(otpModel);
		return otpModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OtpModel authenticateOTP(OtpDto otpDto) throws WalletException {
		try {
			List<OtpModel> list = null;
			if(otpDto.getCustomerId() != null){
				list = (List<OtpModel>) hibernateOperations
					.find("from OtpModel as walletotp where walletotp.customerId =" + otpDto.getCustomerId() 
					+ " and walletotp.emailId = '" + otpDto.getEmailId()
					+ "' and walletotp.otpNumber = " +otpDto.getOtpNumber());
			}else{
				list = (List<OtpModel>) hibernateOperations
						.find("from OtpModel as walletotp where walletotp.emailId = '"+ otpDto.getEmailId()
						+ "' and walletotp.otpNumber = " +otpDto.getOtpNumber());
				}
			if (list != null && list.size() == 1) {
				return list.get(0);
			} else {
				throw new WalletException("failed.to.authenticat.otp");
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return null;
		}
	}

	@Override
	public void updateOTP(OtpModel otpModel) throws WalletException {
		LOGGER.debug("updating OTP...");
		hibernateOperations.update(otpModel);
	}

}
