package com.tarang.ewallet.transaction.business;

import java.util.List;

import com.tarang.ewallet.dto.VelocityAndThresholdDto;
import com.tarang.ewallet.dto.VelocityDto;
import com.tarang.ewallet.dto.WalletThresholdDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.WalletThreshold;


public interface VelocityAndThresholdService {

	VelocityAndThreshold createVelocityAndThreshold(VelocityAndThresholdDto velocityAndThresholdDto) throws WalletException;
	
	VelocityAndThreshold updateVelocityAndThreshold(VelocityAndThresholdDto velocityDto) throws WalletException;
	
	VelocityAndThreshold getVelocityAndThreshold(Long id) throws WalletException;
	
	List<VelocityAndThresholdDto> getVelocityAndThresholdList() throws WalletException;
	
	VelocityAndThreshold getThreshold(Long countryId, Long currencyId, Long transactiontype, Long userType) throws WalletException;
	
	WalletThreshold createWalletThreshold(WalletThresholdDto walletThresholdDto) throws WalletException;
	
	WalletThreshold updateWalletThreshold(WalletThresholdDto walletThresholdDto) throws WalletException;
	
	WalletThreshold getWalletThreshold(Long id) throws WalletException;
	
	List<WalletThresholdDto> getWalletThresholdList() throws WalletException;
	
	WalletThreshold getWallet(Long countryId, Long currencyId) throws WalletException;
	
	VelocityDto getVelocityFromVelocitySetting(VelocityDto velocityDto) throws WalletException;
	
	VelocityDto getDefaultVelocityFromMasterVelocity(VelocityDto velocityDto) throws WalletException;
	
	VelocityDto createVelocity(VelocityDto velocityDto) throws WalletException;
}
