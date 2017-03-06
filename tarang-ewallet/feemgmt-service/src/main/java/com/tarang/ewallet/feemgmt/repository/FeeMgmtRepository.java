package com.tarang.ewallet.feemgmt.repository;

import java.util.List;

import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Tax;


public interface FeeMgmtRepository {
	
	List<FeeDto> feeList(Long servicetype) throws WalletException;
	
	FeeDto createFee(FeeDto feeDto) throws WalletException;
	
	FeeDto loadFee(Long feeId) throws WalletException;
	
	FeeDto updateFee(FeeDto feeDto) throws WalletException;
	
	Boolean deleteFee(Long id)	throws WalletException;
	
	FeeDto getFee(Long operationType, Long country, Long currency) throws WalletException;

	Tax createTax(Tax tax) throws WalletException;
	
	Tax getTax(Long id) throws WalletException;
	
	List<Tax> getTaxs()throws WalletException;
	
	Tax updateTax(Tax tax) throws WalletException;
	
	Tax getTaxByCountry(Long country) throws WalletException;
	
	Double calcuateDeductions(Double amount, Long countryId, Long currencyId, Long transType, Boolean domestic);
	
}
