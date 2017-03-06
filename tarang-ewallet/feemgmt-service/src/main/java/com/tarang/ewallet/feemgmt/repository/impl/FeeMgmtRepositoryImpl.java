package com.tarang.ewallet.feemgmt.repository.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.dao.FeeMgmtDao;
import com.tarang.ewallet.feemgmt.repository.FeeMgmtRepository;
import com.tarang.ewallet.model.Fee;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.model.Tax;
import com.tarang.ewallet.util.FeeUtil;
import com.tarang.ewallet.util.GlobalLitterals;


public class FeeMgmtRepositoryImpl implements FeeMgmtRepository, GlobalLitterals {
	
	private static final Logger LOGGER = Logger.getLogger(FeeMgmtRepositoryImpl.class);

	private FeeMgmtDao feeMgmtDao;

	private HibernateTransactionManager transactionManager;

	public FeeMgmtRepositoryImpl(FeeMgmtDao feeDao, HibernateTransactionManager transactionManager) {
		
		LOGGER.debug( " UserRepositoryImpl constructor " );
		this.feeMgmtDao = feeDao;
		this.transactionManager = transactionManager;
	}
		
	@Override
	public FeeDto createFee(FeeDto feeDto) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Fee feeMgmt = new Fee();
			FeeUtil.createFee(feeMgmt, feeDto);
			feeMgmtDao.createFee(feeMgmt);
			transactionManager.commit(txstatus);
			FeeUtil.loadFee(feeMgmt, feeDto);
		} catch(Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}
		return feeDto;
	}

	@Override
	public List<FeeDto> feeList(Long servicetype) throws WalletException {
		return feeMgmtDao.feeList(servicetype);
	}

	@Override
	public FeeDto loadFee(Long feeMgmtId) throws WalletException {
		FeeDto feeMgmtDto = new FeeDto(); 
		try {
			Fee f = feeMgmtDao.findFeeById(feeMgmtId);
			FeeUtil.loadFee(f, feeMgmtDto);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return feeMgmtDto;
	}


	@Override
	public FeeDto updateFee(FeeDto feeDto) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		if (feeDto != null && feeDto.getId() != null) {
			try{
				Fee fee = feeMgmtDao.findFeeById(feeDto.getId());
				List<FeeSlab> oldSlabs = fee.getFeeSlabs();
				FeeUtil.update(fee, feeDto);
				feeMgmtDao.updateFee(fee,oldSlabs);
				transactionManager.commit(txstatus);
				FeeUtil.loadFee(fee, feeDto);
			} catch(Exception ex){
				transactionManager.rollback(txstatus);
				throw new WalletException(ex.getMessage(), ex);
			}	
		}
		return feeDto;
	}
	
	@Override
	public Boolean deleteFee(Long id) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		Boolean flag = false;
		if (id != null && id != 0) {
			try{
				flag = feeMgmtDao.deleteFee(id);
				transactionManager.commit(txstatus);
			} catch(Exception ex){
				transactionManager.rollback(txstatus);
				throw new WalletException(ex.getMessage(), ex);
			}	
		}
		return flag;
	}
	
	@Override
	public FeeDto getFee(Long operationType, Long country, Long currency) throws WalletException {
		FeeDto feeMgmtDto = new FeeDto(); 
		try {
			Fee f = feeMgmtDao.getFee(operationType, country, currency);
			if(f != null){
				FeeUtil.loadFee(f, feeMgmtDto);
			}
		} catch (CloneNotSupportedException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return feeMgmtDto;
	}

	@Override
	public Tax createTax(Tax tax) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Tax txs = feeMgmtDao.createTax(tax);
			transactionManager.commit(txstatus);
			return txs;
		} catch(Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}	
	}

	@Override
	public Tax getTax(Long id) throws WalletException {
		return feeMgmtDao.getTax(id);
	}

	@Override
	public List<Tax> getTaxs() throws WalletException {
		return feeMgmtDao.getTaxs();
	}

	@Override
	public Tax updateTax(Tax tax) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			Tax txs = feeMgmtDao.updateTax(tax);
			transactionManager.commit(txstatus);
			return txs;
		} catch(Exception ex){
			transactionManager.rollback(txstatus);
			throw new WalletException(ex.getMessage(), ex);
		}
	}
	
	@Override
	public Tax getTaxByCountry(Long country) throws WalletException {
		return feeMgmtDao.getTaxByCountry(country);
	}

	@Override
	public Double calcuateDeductions(Double amount, Long countryId, Long currencyId, Long transType, Boolean domestic){
		
        Double payerFee = ZERO_DOUBLE;
        Double payerTax = ZERO_DOUBLE;
        
        try {
			FeeSlab feeSlab = FeeUtil.getFeeSlabs(getFee(transType, countryId, currencyId), amount);
	        
	        if(feeSlab != null){
	        	
	            if(feeSlab.getPercentageOfSender() != null && feeSlab.getPercentageOfSender() > 0){
	            	payerFee = amount * feeSlab.getPercentageOfSender()/PERCENTAGE_FACTOR;
	            }
	            
	            if(feeSlab.getFixedChargeSender() != null && feeSlab.getFixedChargeSender() > 0){
	            	payerFee += feeSlab.getFixedChargeSender();
	            }
	                    
	            if(payerFee > ZERO_DOUBLE){
	            	Tax tax = getTaxByCountry(countryId);
	            	if(tax != null){
	            		
	            		payerTax = payerFee*tax.getPercentage()/PERCENTAGE_FACTOR;
	            		
	            		if(String.valueOf(payerTax).contains("E"))
	            		{
	            			//if (String.valueOf(payerTax).toLowerCase().contains("e")) {
	            		        
	            		        NumberFormat formatter = new DecimalFormat();
	            		        formatter.setMaximumFractionDigits(2);
	            		        String taxWithoutScintific = formatter.format(payerTax);
	            		        payerTax = Double.valueOf(taxWithoutScintific);
	            		    //}
	            		
	            		}
	            	}
	            }       
	        }
        } catch(Exception ex){
        	LOGGER.error(ex.getMessage(), ex);
        }
        
       
		
		
		return payerFee + payerTax;
	}
}
