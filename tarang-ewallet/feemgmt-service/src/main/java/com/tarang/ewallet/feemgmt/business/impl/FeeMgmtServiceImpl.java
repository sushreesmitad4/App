package com.tarang.ewallet.feemgmt.business.impl;

import java.util.List;

import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.feemgmt.repository.FeeMgmtRepository;
import com.tarang.ewallet.model.Tax;


public class FeeMgmtServiceImpl implements FeeMgmtService{

	private FeeMgmtRepository feeMgmtRepository;

	public FeeMgmtServiceImpl(FeeMgmtRepository userRepository) {
		this.feeMgmtRepository = userRepository;
	}
	
	@Override
	public List<FeeDto> feeList(Long servicetype) throws WalletException {
		return feeMgmtRepository.feeList(servicetype);
	}

	@Override
	public FeeDto createFee(FeeDto feeDto) throws WalletException {
		return feeMgmtRepository.createFee(feeDto);
	}

	@Override
	public FeeDto getFee(Long feeId) throws WalletException {
		return feeMgmtRepository.loadFee(feeId);
	}

	@Override
	public FeeDto getFee(Long operationType, Long country, Long currency) throws WalletException {
		return feeMgmtRepository.getFee(operationType, country, currency);
	}

	@Override
	public FeeDto updateFee(FeeDto feeDto) throws WalletException {
		return feeMgmtRepository.updateFee(feeDto);
	}
	
	@Override
	public Boolean deleteFee(Long id) throws WalletException {
		return feeMgmtRepository.deleteFee(id);
	}

	@Override
	public Tax createTax(Tax tax) throws WalletException {
		return feeMgmtRepository.createTax(tax);
	}

	@Override
	public Tax getTax(Long id) throws WalletException {
		return feeMgmtRepository.getTax(id);
	}

	@Override
	public List<Tax> getTaxs() throws WalletException {
		return feeMgmtRepository.getTaxs();
	}

	@Override
	public Tax updateTax(Tax tax) throws WalletException {
		return feeMgmtRepository.updateTax(tax);
	}

	@Override
	public Tax getTaxByCountry(Long country) throws WalletException {
		return feeMgmtRepository.getTaxByCountry(country);
	}

	@Override
	public Double calcuateDeductions(Double amount, Long countryId, Long currencyId, Long transType, Boolean domestic){
		return feeMgmtRepository.calcuateDeductions(amount, countryId, currencyId, transType, domestic);
	}
	
}
