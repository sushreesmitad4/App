package com.tarang.ewallet.transaction.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.dto.VelocityAndThresholdDto;
import com.tarang.ewallet.dto.VelocityDto;
import com.tarang.ewallet.dto.WalletThresholdDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.MasterVelocity;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.VelocitySetting;
import com.tarang.ewallet.model.WalletThreshold;
import com.tarang.ewallet.transaction.dao.VelocityAndThresholdDao;
import com.tarang.ewallet.transaction.repository.VelocityAndThresholdRepository;
import com.tarang.ewallet.transaction.util.VelocityAndThresholdUtil;



public class VelocityAndThresholdRepositoryImpl implements VelocityAndThresholdRepository{

	private static final Logger LOGGER = Logger.getLogger(VelocityAndThresholdRepository.class);

	private VelocityAndThresholdDao velocityAndThresholdDao ;

	private HibernateTransactionManager transactionManager;

	public VelocityAndThresholdRepositoryImpl(VelocityAndThresholdDao velocityAndThresholdDao, 
			HibernateTransactionManager transactionManager) {
		this.velocityAndThresholdDao = velocityAndThresholdDao;
		this.transactionManager = transactionManager;
	}
	
	@Override
	public VelocityAndThreshold createVelocityAndThreshold(VelocityAndThresholdDto velocityAndThresholdDto) 
			throws WalletException {
		LOGGER.debug( " createVelocityAndThreshold " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			VelocityAndThreshold velocityAndThreshold = new VelocityAndThreshold();
			VelocityAndThresholdUtil.prepareVelocityAndThreshold(velocityAndThresholdDto, velocityAndThreshold);
			VelocityAndThreshold velocity = velocityAndThresholdDao.createVelocityAndThreshold(velocityAndThreshold);
			transactionManager.commit(txstatus);
			return velocity;
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			if(ex instanceof org.springframework.dao.DataIntegrityViolationException){
				throw new WalletException(CommonConstrain.DUPLICATE_ENTRY, ex);
			}else{
				throw new WalletException(CommonConstrain.VELOCITY_CREATE_EXCEPTION, ex);
			}
		}
	}
	
	@Override
	public VelocityAndThreshold updateVelocityAndThreshold(VelocityAndThresholdDto velocityDto) 
			throws WalletException {
		LOGGER.debug( " updateVelocityAndThreshold " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			VelocityAndThreshold velocity = velocityAndThresholdDao.getVelocityAndThreshold(velocityDto.getId()); 
			velocity.setMinimumamount(velocityDto.getMinimumamount());
			velocity.setMaximumamount(velocityDto.getMaximumamount());
			
			velocityAndThresholdDao.updateVelocityAndThreshold(velocity);
			transactionManager.commit(txstatus);
			return velocity;
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(CommonConstrain.VELOCITY_UPDATE_EXCEPTION, ex);
		}
	}
	
	@Override
	public VelocityAndThreshold getVelocityAndThreshold(Long id) throws WalletException {
		return velocityAndThresholdDao.getVelocityAndThreshold(id);
	}
	
	@Override
	public List<VelocityAndThresholdDto> getVelocityAndThresholdList() throws WalletException {
		List<VelocityAndThreshold> velocitys = velocityAndThresholdDao.getVelocityAndThresholdList();
		VelocityAndThresholdDto dto = null;
		List<VelocityAndThresholdDto> velocitydtos = new ArrayList<VelocityAndThresholdDto>();
		for(VelocityAndThreshold v:velocitys){
			
			dto = new VelocityAndThresholdDto();
			dto.setId(v.getId());
			dto.setCountry(v.getCountry());
			dto.setCurrency(v.getCurrency());
			dto.setTransactiontype(v.getTransactiontype());
			dto.setMinimumamount(v.getMinimumamount());
			dto.setMaximumamount(v.getMaximumamount());
			dto.setUserType(v.getUserType());
			velocitydtos.add(dto);
		}
		return velocitydtos;
	}

	@Override
	public VelocityAndThreshold getThreshold(Long countryId, Long currencyId,
			Long transactiontype, Long userType) throws WalletException {
		return velocityAndThresholdDao.getThreshold(countryId, currencyId, transactiontype, userType);
	}

	@Override
	public WalletThreshold createWalletThreshold(WalletThresholdDto walletThresholdDto) throws WalletException {
		LOGGER.debug( " createWalletThreshold " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			WalletThreshold walletThreshold = new WalletThreshold();
			VelocityAndThresholdUtil.prepareWalletThreshold(walletThresholdDto, walletThreshold);
			WalletThreshold walletThreshold1 = velocityAndThresholdDao.createWalletThreshold(walletThreshold);
			transactionManager.commit(txstatus);
			return walletThreshold1;
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			if(ex instanceof org.springframework.dao.DataIntegrityViolationException){
				throw new WalletException(CommonConstrain.DUPLICATE_ENTRY, ex);
			}else{
				throw new WalletException(CommonConstrain.VELOCITY_CREATE_EXCEPTION, ex);
			}
		}
		
	}

	@Override
	public WalletThreshold updateWalletThreshold(WalletThresholdDto walletThresholdDto) throws WalletException {
		LOGGER.debug( " Create velocity and threshold " );
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		try{
			WalletThreshold walletthreshold = velocityAndThresholdDao.getWalletThreshold(walletThresholdDto.getId()); 
			walletthreshold.setMaximumamount(walletThresholdDto.getMaximumamount());
			
			velocityAndThresholdDao.updateWalletThreshold(walletthreshold);
			transactionManager.commit(txstatus);
			return walletthreshold;
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try{
				transactionManager.rollback(txstatus);
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			throw new WalletException(CommonConstrain.VELOCITY_UPDATE_EXCEPTION, ex);
		}
	}
	

	@Override
	public WalletThreshold getWalletThreshold(Long id) throws WalletException {
		return velocityAndThresholdDao.getWalletThreshold(id);
	}

	@Override
	public List<WalletThresholdDto> getWalletThresholdList() throws WalletException {
		List<WalletThreshold> thresholds = velocityAndThresholdDao.getWalletThresholdList();
		WalletThresholdDto dto = null;
		List<WalletThresholdDto> thresholddtos = new ArrayList<WalletThresholdDto>();
		for(WalletThreshold v:thresholds){
			
			dto = new WalletThresholdDto();
			dto.setId(v.getId());
			dto.setCountry(v.getCountry());
			dto.setCurrency(v.getCurrency());
			dto.setMaximumamount(v.getMaximumamount());
			
			thresholddtos.add(dto);
		}
		return thresholddtos;
	}

	@Override
	public WalletThreshold getWallet(Long countryId, Long currencyId) throws WalletException {
		return velocityAndThresholdDao.getWallet(countryId, currencyId);
	}
	
	@Override
	public VelocityDto getVelocityFromVelocitySetting(VelocityDto velocityDto)  throws WalletException{
		LOGGER.info(" Entering getVelocityFromVelocitySetting ");
		VelocitySetting velocitySetting = velocityAndThresholdDao.
				getVelocityFromVelocitySetting(velocityDto.getUserTypeId(), velocityDto.getCountryId(), 
						velocityDto.getCurrencyId(), velocityDto.getTransactionTypeId());
		return VelocityAndThresholdUtil.convertToDto(velocitySetting);
	}

	@Override
	public VelocityDto getDefaultVelocityFromMasterVelocity(VelocityDto velocityDto)  throws WalletException{
		LOGGER.info(" Entering getDefaultVelocityFromMasterVelocity ");
		MasterVelocity masterVelocity = velocityAndThresholdDao.
				getDefaultVelocityFromMasterVelocity(velocityDto.getUserTypeId(), velocityDto.getCountryId(), 
						velocityDto.getCurrencyId(), velocityDto.getTransactionTypeId());
		return VelocityAndThresholdUtil.convertToDto(masterVelocity);
	}

	@Override
	public VelocityDto createVelocity(VelocityDto velocityDto) throws WalletException {
		VelocitySetting velocitySetting = velocityAndThresholdDao.createVelocity(VelocityAndThresholdUtil
				.convertFromDtoToVelocitySetting(velocityDto));
		return VelocityAndThresholdUtil.convertToDto(velocitySetting);
	}
}