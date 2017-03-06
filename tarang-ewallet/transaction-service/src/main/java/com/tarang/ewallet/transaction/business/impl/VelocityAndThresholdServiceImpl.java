package com.tarang.ewallet.transaction.business.impl;

import java.util.List;

import com.tarang.ewallet.dto.VelocityAndThresholdDto;
import com.tarang.ewallet.dto.VelocityDto;
import com.tarang.ewallet.dto.WalletThresholdDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.WalletThreshold;
import com.tarang.ewallet.transaction.business.VelocityAndThresholdService;
import com.tarang.ewallet.transaction.repository.VelocityAndThresholdRepository;



public class VelocityAndThresholdServiceImpl implements VelocityAndThresholdService  {

	private VelocityAndThresholdRepository velocityAndThresholdRepository;

	public VelocityAndThresholdServiceImpl(VelocityAndThresholdRepository velocityAndThresholdRepository) {
		this.velocityAndThresholdRepository = velocityAndThresholdRepository;
	}
	
	@Override
	 public VelocityAndThreshold createVelocityAndThreshold(VelocityAndThresholdDto velocityAndThresholdDto) throws WalletException {
		  return velocityAndThresholdRepository.createVelocityAndThreshold(velocityAndThresholdDto);
	}

	@Override
	public VelocityAndThreshold updateVelocityAndThreshold(VelocityAndThresholdDto velocityDto) throws WalletException {
		return velocityAndThresholdRepository.updateVelocityAndThreshold(velocityDto);
	}

	@Override
	public VelocityAndThreshold getVelocityAndThreshold(Long id) throws WalletException {
		return velocityAndThresholdRepository.getVelocityAndThreshold(id);
	}

	@Override
	public List<VelocityAndThresholdDto> getVelocityAndThresholdList() throws WalletException {
		return velocityAndThresholdRepository.getVelocityAndThresholdList();
	}

	@Override
	public VelocityAndThreshold getThreshold(Long countryId, Long currencyId,
			Long transactiontype, Long userType) throws WalletException {
		return velocityAndThresholdRepository.getThreshold(countryId, currencyId,
				transactiontype, userType);
	}

	@Override
	public WalletThreshold createWalletThreshold(WalletThresholdDto walletThresholdDto) throws WalletException {
		 return velocityAndThresholdRepository.createWalletThreshold(walletThresholdDto);
	}

	@Override
	public WalletThreshold updateWalletThreshold(WalletThresholdDto walletThresholdDto) throws WalletException {
		return velocityAndThresholdRepository.updateWalletThreshold(walletThresholdDto);
	}

	@Override
	public WalletThreshold getWalletThreshold(Long id) throws WalletException {
		return velocityAndThresholdRepository.getWalletThreshold(id);
	}

	@Override
	public List<WalletThresholdDto> getWalletThresholdList() throws WalletException {
		return velocityAndThresholdRepository.getWalletThresholdList();
	}

	@Override
	public WalletThreshold getWallet(Long countryId, Long currencyId)
			throws WalletException {
		return velocityAndThresholdRepository.getWallet(countryId, currencyId);
	}

	@Override
	public VelocityDto getVelocityFromVelocitySetting(VelocityDto velocityDto) throws WalletException {
		return velocityAndThresholdRepository.getVelocityFromVelocitySetting(velocityDto);
	}

	@Override
	public VelocityDto getDefaultVelocityFromMasterVelocity(VelocityDto velocityDto) throws WalletException {
		return velocityAndThresholdRepository.getDefaultVelocityFromMasterVelocity(velocityDto);
	}

	@Override
	public VelocityDto createVelocity(VelocityDto velocityDto) throws WalletException {
		return velocityAndThresholdRepository.createVelocity(velocityDto);
	}
}