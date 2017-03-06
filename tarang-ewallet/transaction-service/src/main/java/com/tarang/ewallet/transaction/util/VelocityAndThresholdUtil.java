package com.tarang.ewallet.transaction.util;

import com.tarang.ewallet.dto.VelocityAndThresholdDto;
import com.tarang.ewallet.dto.VelocityDto;
import com.tarang.ewallet.dto.WalletThresholdDto;
import com.tarang.ewallet.model.MasterVelocity;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.VelocitySetting;
import com.tarang.ewallet.model.WalletThreshold;


public class VelocityAndThresholdUtil {

	public static void prepareVelocityAndThreshold(VelocityAndThresholdDto velocityDto, VelocityAndThreshold velocity) {
		velocity.setId(velocityDto.getId());
		velocity.setCountry(velocityDto.getCountry());
		velocity.setCurrency(velocityDto.getCurrency());
		velocity.setTransactiontype(velocityDto.getTransactiontype());
		velocity.setMinimumamount(velocityDto.getMinimumamount());
		velocity.setMaximumamount(velocityDto.getMaximumamount());
		velocity.setUserType(velocityDto.getUserType());
	}
	
	public static void prepareWalletThreshold(WalletThresholdDto walletThresholdDto, WalletThreshold walletThreshold) {
		walletThreshold.setId(walletThresholdDto.getId());
		walletThreshold.setCountry(walletThresholdDto.getCountry());
		walletThreshold.setCurrency(walletThresholdDto.getCurrency());
		walletThreshold.setMaximumamount(walletThresholdDto.getMaximumamount());
	
	}

	public static VelocityDto convertToDto(Object object){
		VelocityDto velocityThresholdDto = new VelocityDto();
		if(null == object){
			return null;
		}
		if(object instanceof VelocitySetting){
			VelocitySetting velocitySetting = (VelocitySetting)object;
			
			velocityThresholdDto.setId(velocitySetting.getId());
			velocityThresholdDto.setCountryId(velocitySetting.getCountryId());
			velocityThresholdDto.setCurrencyId(velocitySetting.getCurrencyId());
			velocityThresholdDto.setTransactionTypeId(velocitySetting.getTransactionTypeId());
			velocityThresholdDto.setTypeOfFrequenciesId(velocitySetting.getTypeOfFrequenciesId());
			velocityThresholdDto.setTotalNoOfTransaction(velocitySetting.getTotalNoOfTransaction());
			velocityThresholdDto.setTotalVolumeOfAmount(velocitySetting.getTotalVolumeOfAmount());
			
			velocityThresholdDto.setCreatedBy(velocitySetting.getCreatedBy());
			velocityThresholdDto.setCreationDate(velocitySetting.getCreationDate());
			
		}else if(object instanceof MasterVelocity){
			MasterVelocity masterVelocity = (MasterVelocity)object;
			velocityThresholdDto.setId(masterVelocity.getId());
			velocityThresholdDto.setCountryId(masterVelocity.getCountryId());
			velocityThresholdDto.setCurrencyId(masterVelocity.getCurrencyId());
			velocityThresholdDto.setTransactionTypeId(masterVelocity.getTransactionTypeId());
			velocityThresholdDto.setTypeOfFrequenciesId(masterVelocity.getTypeOfFrequenciesId());
			velocityThresholdDto.setTotalNoOfTransaction(masterVelocity.getTotalNoOfTransaction());
			velocityThresholdDto.setTotalVolumeOfAmount(masterVelocity.getTotalVolumeOfAmount());
		}else{
			return null;
		}
		return velocityThresholdDto;
	}
	
	public static VelocitySetting convertFromDtoToVelocitySetting(VelocityDto velocityThresholdDto){
		
		VelocitySetting velocitySetting = new VelocitySetting();
			
		velocitySetting.setCountryId(velocityThresholdDto.getCountryId());
		velocitySetting.setCurrencyId(velocityThresholdDto.getCurrencyId());
		velocitySetting.setTransactionTypeId(velocityThresholdDto.getTransactionTypeId());
		velocitySetting.setTypeOfFrequenciesId(velocityThresholdDto.getTypeOfFrequenciesId());
		velocitySetting.setTotalNoOfTransaction(velocityThresholdDto.getTotalNoOfTransaction());
		velocitySetting.setTotalVolumeOfAmount(velocityThresholdDto.getTotalVolumeOfAmount());
		
		/*Default Data*/
		velocitySetting.setCreationDate(velocityThresholdDto.getCreationDate());
		velocitySetting.setModifyDate(velocityThresholdDto.getModifyDate());
		velocitySetting.setCreatedBy(velocityThresholdDto.getCreatedBy());
		velocitySetting.setModifyBy(velocityThresholdDto.getModifyBy());
		
		return velocitySetting;
	}
}
