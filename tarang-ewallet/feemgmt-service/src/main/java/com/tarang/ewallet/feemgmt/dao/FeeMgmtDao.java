package com.tarang.ewallet.feemgmt.dao;

import java.util.List;

import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Fee;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.model.Tax;


public interface FeeMgmtDao {

	Fee createFee(Fee fee) throws WalletException ;
	
	List<FeeDto> feeList(Long servicetype) throws WalletException;
	
	Fee findFeeById(Long id) throws WalletException;
	
	Fee updateFee(Fee fee, List<FeeSlab> oldSlabs) throws WalletException;
	
	Boolean deleteFee(Long id) throws WalletException;
	
	Fee getFee(Long operationType, Long country, Long currency) throws WalletException;

	Tax createTax(Tax tax) throws WalletException;
	
	Tax getTax(Long id) throws WalletException;
	
	List<Tax> getTaxs()throws WalletException;
	
	Tax updateTax(Tax tax) throws WalletException;
	
	Tax getTaxByCountry(Long country) throws WalletException;
	
}
