package com.tarang.ewallet.walletui.util;

import com.tarang.ewallet.dto.VelocityAndThresholdDto;
import com.tarang.ewallet.dto.WalletThresholdDto;
import com.tarang.ewallet.walletui.form.VelocityAndThresholdForm;
import com.tarang.ewallet.walletui.form.WalletThresholdForm;


/**
 * @author vasanthar
 *
 */
public class AdminUtil {
	
	public static void convertVelocityFormToDto(VelocityAndThresholdForm velocityForm, VelocityAndThresholdDto velocityDto){
		velocityDto.setCountry(velocityForm.getCountry());
		velocityDto.setCurrency(velocityForm.getCurrency());
		velocityDto.setTransactiontype(velocityForm.getTransactiontype());
		velocityDto.setMinimumamount(Double.parseDouble(velocityForm.getMinimumamount()));
		velocityDto.setMaximumamount(Double.parseDouble(velocityForm.getMaximumamount()));
		velocityDto.setUserType(velocityForm.getUsertype());
	}

	public static void convertWalletThresholdFormToDto(WalletThresholdForm walletThresholdForm, WalletThresholdDto walletThresholdDto){
	   walletThresholdDto.setCountry(walletThresholdForm.getCountry());
	   walletThresholdDto.setCurrency(walletThresholdForm.getCurrency());
	   walletThresholdDto.setMaximumamount(Double.parseDouble(walletThresholdForm.getMaximumamount()));
	}

}