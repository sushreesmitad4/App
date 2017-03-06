package com.tarang.ewallet.feemgmt.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.dao.FeeMgmtDao;
import com.tarang.ewallet.model.Fee;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.model.Tax;
import com.tarang.ewallet.util.GlobalLitterals;


@SuppressWarnings({"unchecked"})
public class FeeMgmtDaoImpl implements FeeMgmtDao {
	
	private static final Logger LOGGER = Logger.getLogger(FeeMgmtDaoImpl.class);

	private static final String FAILS_TO_DELETE_FEE  = "fails.to.delete.fee";
	
	private HibernateOperations hibernateOperations;

	public FeeMgmtDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public List<FeeDto> feeList(Long servicetype) throws WalletException {
		List<FeeDto> feeList = null;
		try{
			feeList = hibernateOperations.findByNamedQuery("getFeeListOnServiceType", servicetype);
		} catch(Exception e){
			throw new WalletException("fails.retrive.feelist", e);	
		}
		return feeList;
	}

	@Override
	public Fee createFee(Fee feeMgmt) throws WalletException {
		try{
			hibernateOperations.save(feeMgmt);
			hibernateOperations.flush();
		} catch(Exception ex){
			LOGGER.error(ex);
			throw new WalletException(GlobalLitterals.DB_COM_DUPLICATE_ENTRY,ex);
		}
		return feeMgmt;	
	}
	
	@Override
	public Fee findFeeById(Long id) throws WalletException {
		List<Fee> list = (List<Fee>) hibernateOperations.findByNamedQuery("getFeeMgmtOnId", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else{ 
			throw new WalletException("unique AdminUser exception");
		}
	}

	@Override
	public Fee updateFee(Fee feeMgmt, List<FeeSlab> oldSlabs) throws WalletException {
		
		if(oldSlabs != null){
			for(FeeSlab old : oldSlabs){
				hibernateOperations.delete(old);
			}	
		}
		hibernateOperations.save(feeMgmt);
		hibernateOperations.flush();
		return feeMgmt;
	}
	
	@Override
	public Boolean deleteFee(Long id) throws WalletException {
		try{
			if(id != null){
				Fee fee = findFeeById(id);
				hibernateOperations.delete(fee);
				hibernateOperations.flush();
			}
		} catch(Exception e){
			LOGGER.error(FAILS_TO_DELETE_FEE, e);
			throw new WalletException(FAILS_TO_DELETE_FEE);
		}
		return Boolean.TRUE;
	}
	
	@Override
	public Fee getFee(Long operationType, Long country, Long currency) throws WalletException {
		if(operationType != null && country != null && currency != null ){
			List<Fee> list = (List<Fee>) hibernateOperations.findByNamedQuery("getFeeObjectOnServiceSearch", new Object[]{operationType, country, currency});
			if (list != null && list.size() == 1) {
				return list.get(0);
			} else{ 
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public Tax createTax(Tax tax) throws WalletException {
		try{
			hibernateOperations.save(tax);
			hibernateOperations.flush();
			return tax;
		} catch(Exception e){
			LOGGER.error(e);
			throw new WalletException(GlobalLitterals.DB_COUNTRY_DUPLICATE_ENTRY, e);
		}
	}

	@Override
	public Tax getTax(Long id) throws WalletException {
		List<Tax> list = (List<Tax>) hibernateOperations.findByNamedQuery("getTaxById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else{ 
			return null;
		}
	}

	@Override
	public List<Tax> getTaxs() throws WalletException {
		try{
			return (List<Tax>) hibernateOperations.findByNamedQuery("getTaxs");
		} catch (Exception e) {
			LOGGER.error(e);
			throw new WalletException(e.getMessage(), e);
		}		
	}

	@Override
	public Tax updateTax(Tax tax) throws WalletException {
		try{
			hibernateOperations.update(tax);
			return tax;
		} catch(Exception e){
			LOGGER.error(e);
			throw new WalletException("Unique conistrains exception while creating feemgmt", e);
		}
	}
	
	@Override
	public Tax getTaxByCountry(Long country) throws WalletException {
		List<Tax> list = (List<Tax>) hibernateOperations.findByNamedQuery("getTaxByCountryId", country);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else{ 
			return null;
		}
	}

}
