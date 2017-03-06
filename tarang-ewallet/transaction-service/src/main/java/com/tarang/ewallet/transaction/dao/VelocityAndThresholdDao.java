package com.tarang.ewallet.transaction.dao;

import java.util.List;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.MasterVelocity;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.VelocitySetting;
import com.tarang.ewallet.model.WalletThreshold;


public interface VelocityAndThresholdDao {

	VelocityAndThreshold createVelocityAndThreshold(VelocityAndThreshold velocityAndThreshold) throws WalletException;
	
	VelocityAndThreshold updateVelocityAndThreshold(VelocityAndThreshold velocityAndThreshold) throws WalletException;
	
	VelocityAndThreshold getVelocityAndThreshold(Long id) throws WalletException;
	
	List<VelocityAndThreshold> getVelocityAndThresholdList() throws WalletException;

	VelocityAndThreshold getThreshold(Long countryId, Long currencyId, Long transactiontype, Long userType) throws WalletException;
	
    WalletThreshold createWalletThreshold(WalletThreshold walletThreshold) throws WalletException;
	
	WalletThreshold updateWalletThreshold(WalletThreshold walletThreshold) throws WalletException;
	
	WalletThreshold getWalletThreshold(Long id) throws WalletException;
	
	List<WalletThreshold> getWalletThresholdList() throws WalletException;
	
	WalletThreshold getWallet(Long countryId, Long currencyId) throws WalletException;
	
	VelocitySetting getVelocityFromVelocitySetting(Long userTypeId, Long countryId, Long currencyId, Long transactionTypeId) throws WalletException;
	
	MasterVelocity getDefaultVelocityFromMasterVelocity(Long userTypeId, Long countryId, Long currencyId, Long transactionTypeId) throws WalletException;
	
	VelocitySetting createVelocity(VelocitySetting velocitySetting) throws WalletException;
}
