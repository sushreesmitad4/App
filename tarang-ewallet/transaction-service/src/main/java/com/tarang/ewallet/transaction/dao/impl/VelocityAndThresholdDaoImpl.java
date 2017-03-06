package com.tarang.ewallet.transaction.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.MasterVelocity;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.VelocitySetting;
import com.tarang.ewallet.model.WalletThreshold;
import com.tarang.ewallet.transaction.dao.VelocityAndThresholdDao;


@SuppressWarnings({"unchecked"})
public class VelocityAndThresholdDaoImpl implements VelocityAndThresholdDao{
	
	private static final Logger LOGGER = Logger.getLogger(VelocityAndThresholdDaoImpl.class);
	
	private HibernateOperations hibernateOperations;

	public VelocityAndThresholdDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}
	
	@Override
	public VelocityAndThreshold createVelocityAndThreshold(VelocityAndThreshold velocityAndThreshold) throws WalletException {
		hibernateOperations.save(velocityAndThreshold);
		return velocityAndThreshold;
	}

	@Override
	public VelocityAndThreshold updateVelocityAndThreshold(VelocityAndThreshold velocityAndThreshold) throws WalletException {
		hibernateOperations.update(velocityAndThreshold);
		return velocityAndThreshold;
	}

	@Override
	public VelocityAndThreshold getVelocityAndThreshold(Long id) throws WalletException {
		List<VelocityAndThreshold> velocityList = null;
		try{
			velocityList = hibernateOperations.findByNamedQuery("getVelocityAndThresholdListById",id);
			 return velocityList.get(0);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.VELOCITY_RETRIVE_EXCEPTION, e);	
		}
	}

	@Override
	public List<VelocityAndThreshold> getVelocityAndThresholdList() throws WalletException {
		List<VelocityAndThreshold> velocityList = null;
		try{
			velocityList = hibernateOperations.findByNamedQuery("getVelocityAndThresholdList");
		}catch(Exception e){
			throw new WalletException(CommonConstrain.VELOCITY_RETRIVE_EXCEPTION, e);	
		}
		return velocityList;
	}
	
	@Override
	public VelocityAndThreshold getThreshold(Long countryId, Long currencyId,
			Long transactiontype, Long userType) throws WalletException {
		List<VelocityAndThreshold> velocityList = null;
		try{
			if(countryId != null && currencyId != null && transactiontype != null && userType != null ){
				velocityList = hibernateOperations.findByNamedQuery("getVelocityAndThresholdByCountry", 
						new Object[]{countryId, currencyId, transactiontype, userType});
				if(velocityList != null && velocityList.size() == 1){ 
					return velocityList.get(0);
				}else{
					return null;
				}
			}
			return null;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public WalletThreshold createWalletThreshold(WalletThreshold walletThreshold) throws WalletException {
		hibernateOperations.save(walletThreshold);
		return walletThreshold;
	}

	@Override
	public WalletThreshold updateWalletThreshold(WalletThreshold walletThreshold) throws WalletException {
		hibernateOperations.update(walletThreshold);
		return walletThreshold;
	}

	@Override
	public WalletThreshold getWalletThreshold(Long id) throws WalletException {
		List<WalletThreshold> walletThresholdList = null;
		try{
			walletThresholdList = hibernateOperations.findByNamedQuery("getWalletThresholdListById", id);
			 return walletThresholdList.get(0);
		}catch(Exception e){
			throw new WalletException(CommonConstrain.VELOCITY_RETRIVE_EXCEPTION, e);	
		}
	}

	@Override
	public List<WalletThreshold> getWalletThresholdList() throws WalletException {
		List<WalletThreshold> walletThresholdList = null;
		try{
			walletThresholdList = hibernateOperations.findByNamedQuery("getWalletThresholdList");
		}catch(Exception e){
			throw new WalletException(CommonConstrain.VELOCITY_RETRIVE_EXCEPTION, e);	
		}
		return walletThresholdList;
	}

	@Override
	public WalletThreshold getWallet(Long countryId, Long currencyId) throws WalletException {
			List<WalletThreshold> walletThresholdList = null;
			try{
				if(countryId != null && currencyId != null){
					walletThresholdList = hibernateOperations.findByNamedQuery("getWalletThresholdByCountry", 
							new Object[]{countryId, currencyId});
					if(walletThresholdList != null && walletThresholdList.size() == 1){ 
						return walletThresholdList.get(0);
					}else{
						return null;
					}
				}
				return null;
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
				return null;
			}
		}

	@Override
	public VelocitySetting getVelocityFromVelocitySetting(Long userTypeId, Long countryId, Long currencyId,
			Long transactionTypeId) throws WalletException {
		
		List<VelocitySetting> list = (List<VelocitySetting>)hibernateOperations.findByNamedQuery("getVelocityFromVelocitySetting", 
				new Object[]{userTypeId, countryId, currencyId, transactionTypeId});
		if(list != null && list.size() > 0){
			LOGGER.info("list size is now :: "+list.size());
			return (list.get(0));
		}
		return null;
	}

	@Override
	public MasterVelocity getDefaultVelocityFromMasterVelocity(Long userTypeId, Long countryId, Long currencyId,
			Long transactionTypeId) throws WalletException {
		List<MasterVelocity> list = (List<MasterVelocity>)hibernateOperations.findByNamedQuery("getDefaultVelocityFromMasterVelocity", 
				new Object[]{userTypeId, countryId, currencyId, transactionTypeId});
		if(list != null && list.size() > 0){
			LOGGER.info("list size is now :: "+list.size());
			return (list.get(0));
		}
		return null;
	}

	@Override
	public VelocitySetting createVelocity(VelocitySetting velocitySetting)  throws WalletException{
		hibernateOperations.save(velocitySetting);
		return velocitySetting;
	}
}

