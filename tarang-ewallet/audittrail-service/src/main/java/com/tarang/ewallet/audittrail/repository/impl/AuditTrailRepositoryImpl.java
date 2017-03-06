package com.tarang.ewallet.audittrail.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.audittrail.dao.AuditTrailDao;
import com.tarang.ewallet.audittrail.repository.AuditTrailRepository;
import com.tarang.ewallet.audittrail.util.AuditTrailFieldConstrain;
import com.tarang.ewallet.audittrail.util.AuditTrailUtil;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Audit;
import com.tarang.ewallet.model.AuditField;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.model.Tax;
import com.tarang.ewallet.model.UserIP;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.WalletThreshold;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author vasanthar
 *
 */
public class AuditTrailRepositoryImpl implements AuditTrailRepository,GlobalLitterals{
	
	private static final Logger LOGGER = Logger.getLogger(AuditTrailRepositoryImpl.class);
	
	private AuditTrailDao auditTrailDao;

	private HibernateTransactionManager transactionManager;
	
	public AuditTrailRepositoryImpl(AuditTrailDao auditTrailDao, HibernateTransactionManager transactionManager) {
		this.auditTrailDao = auditTrailDao;
		this.transactionManager = transactionManager;
	}

	public Audit createAuditTrail(Long authId, String moduleName, Long status, String emailId, 
			String userType, Object oldObject, Object newObject) throws WalletException {

		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		Audit audit = new Audit();
		try {
			audit.setAuthId(authId);
			audit.setEmailId(emailId);
			audit.setModuleName(moduleName);
			audit.setStatus(status);
			audit.setUserType(userType);
			audit.setCreationDate(new Date());
			audit.setAuditFields(getFields(oldObject, newObject));
			if( audit.getAuditFields() != null){
				for(AuditField field: audit.getAuditFields()){
					field.setAudits(audit);
				}
			}
			audit = auditTrailDao.updateAudit(audit);
			transactionManager.commit(txstatus);
		} catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			} catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
		}
		return audit;
	}
	
	public Audit createAuditTrail(Long authId, String moduleName, Long status, String emailId, 
			String userType) throws WalletException {
         
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		Audit audit = new Audit();
		try {
			audit.setAuthId(authId);
			audit.setEmailId(emailId);
			audit.setModuleName(moduleName);
			audit.setStatus(status);
			audit.setUserType(userType);
			audit.setCreationDate(new Date());
			audit = auditTrailDao.createAudit(audit);
			transactionManager.commit(txstatus);
		} catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			} catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
		}
		return audit;
	}
	
private List<AuditField> getFields(Object oldObject, Object newObject) throws WalletException {
		
		List<AuditField> list = null;
		
		if(oldObject instanceof CustomerDto){
			list = AuditTrailUtil.updateAuditCustomer((CustomerDto)oldObject, (CustomerDto)newObject);
		} else if(oldObject instanceof MerchantDto){
			list = AuditTrailUtil.updateAuditMerchant((MerchantDto)oldObject, (MerchantDto)newObject);
		} else if(oldObject instanceof AdminUserDto){
			list = AuditTrailUtil.updateAuditAdmin((AdminUserDto)oldObject, (AdminUserDto)newObject);
		} else if(oldObject instanceof AccountDto){	
			list = AuditTrailUtil.updateAuditAccount((AccountDto)oldObject, (AccountDto)newObject);
		} else if(oldObject instanceof Role){	
			list = AuditTrailUtil.updateAuditRole((Role)oldObject, (Role)newObject);
		} else if(oldObject instanceof VelocityAndThreshold){	
			list = AuditTrailUtil.updateAuditVelocityAndThreshold((VelocityAndThreshold)oldObject, (VelocityAndThreshold)newObject);
		} else if(oldObject instanceof WalletThreshold){	
			list = AuditTrailUtil.updateAuditWalletThreshold((WalletThreshold)oldObject, (WalletThreshold)newObject);
		} else if(oldObject instanceof PreferencesDto){	
			list = AuditTrailUtil.updateAuditPrefereances((PreferencesDto)oldObject, (PreferencesDto)newObject);
		} else if(oldObject instanceof FeeDto){	
			list = AuditTrailUtil.updateAuditFeeManagement((FeeDto)oldObject, (FeeDto)newObject);
		} else if(oldObject instanceof UserIP){	
			list = AuditTrailUtil.updateAuditUserIP((UserIP)oldObject, (UserIP)newObject);
		} else if(oldObject instanceof ChangePasswordDto){	
			list = AuditTrailUtil.updateAuditChangePassword((ChangePasswordDto)oldObject, (ChangePasswordDto)newObject);
		} else if(oldObject instanceof Tax){	
			list = AuditTrailUtil.updateAuditTax((Tax)oldObject, (Tax)newObject);
		}
		return list;
	}

	public Audit getAuditTrail(Long id) throws WalletException {
		Audit audit = auditTrailDao.getAuditTrail(id);
		audit.setAuditFields(auditTrailDao.getAuditFields(id));
		return audit;
	}

	public List<Audit> getAllAuditTrailObjects() throws WalletException {
		return auditTrailDao.getAllAuditTrailObjects();
	}

	@Override
	public Audit createAuditTrailForDispute(Long authId, String moduleName, Long status, String emailId, String userType, 
			Long disputeStatus, Double disputeAmount, Long newDisputeStatus, Double newDisputeAmount) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		Audit audit = new Audit();
		try {
			audit.setAuthId(authId);
			audit.setEmailId(emailId);
			audit.setModuleName(moduleName);
			audit.setStatus(status);
			audit.setUserType(userType);
			audit.setCreationDate(new Date());
			audit.setAuditFields(AuditTrailUtil.getFields(disputeStatus, disputeAmount, newDisputeStatus, newDisputeAmount));
			if( audit.getAuditFields() != null){
				for(AuditField field: audit.getAuditFields()){
					field.setAudits(audit);
				}
			}
			audit = auditTrailDao.updateAudit(audit);
			transactionManager.commit(txstatus);
		} catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			} catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
		}
		return audit;
	}

	@Override
	public Audit createAuditTrailForRequestMoney(Long authId, String moduleName, Long status, String emailId, String userType,
			Long oldRequestMoneyStatus, Long newRequestMoneyStatus) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		Audit audit = new Audit();
		try {
			audit.setAuthId(authId);
			audit.setEmailId(emailId);
			audit.setModuleName(moduleName);
			audit.setStatus(status);
			audit.setUserType(userType);
			audit.setCreationDate(new Date());
			List<AuditField> list = new ArrayList<AuditField>();		
			list.add(new AuditField(AuditTrailFieldConstrain.REQUEST_MONEY_STATUS, oldRequestMoneyStatus.toString(), newRequestMoneyStatus.toString()));			
			audit.setAuditFields(list);
			if( audit.getAuditFields() != null){
				for(AuditField field: audit.getAuditFields()){
					field.setAudits(audit);
				}
			}
			audit = auditTrailDao.updateAudit(audit);
			transactionManager.commit(txstatus);
		} catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			} catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
		}
		return audit;
	}

	@Override
	public Audit createAuditTrailForEmailVarification(Long authId, String moduleName, Long status, String emailId, 
			String userType) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		Audit audit = new Audit();
		try {
			audit.setAuthId(authId);
			audit.setEmailId(emailId);
			audit.setModuleName(moduleName);
			audit.setStatus(status);
			audit.setUserType(userType);
			audit.setCreationDate(new Date());
			List<AuditField> list = new ArrayList<AuditField>();
			list.add(new AuditField(AuditTrailFieldConstrain.EMAIL_VARIFICATION_STATUS, Boolean.FALSE.toString(), Boolean.TRUE.toString()));
			list.add(new AuditField(AuditTrailFieldConstrain.ACTIVE_STATUS, Boolean.FALSE.toString(), Boolean.TRUE.toString()));	
			audit.setAuditFields(list);
			if( audit.getAuditFields() != null){
				for(AuditField field: audit.getAuditFields()){
					field.setAudits(audit);
				}
			}
			audit = auditTrailDao.updateAudit(audit);
			transactionManager.commit(txstatus);
		} catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			} catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
		}
		return audit;
	}

	@Override
	public Audit createAuditTrailForRole(Long authId, String moduleName,
			Long status, String emailId, String userType, Long roleId) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		Audit audit = new Audit();
		try {
			audit.setAuthId(authId);
			audit.setEmailId(emailId);
			audit.setModuleName(moduleName);
			audit.setStatus(status);
			audit.setUserType(userType);
			audit.setCreationDate(new Date());
			List<AuditField> list = new ArrayList<AuditField>();
			list.add(new AuditField(AuditTrailFieldConstrain.ROLE_ID, roleId.toString(), null));
			audit.setAuditFields(list);
			if( audit.getAuditFields() != null){
				for(AuditField field: audit.getAuditFields()){
					field.setAudits(audit);
				}
			}
			audit = auditTrailDao.updateAudit(audit);
			transactionManager.commit(txstatus);
		} catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			} catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
		}
		return audit;
	}

	@Override
	public Audit createAuditTrailForAccountCloser(Long authId, String moduleName, Long status, String emailId, String userType,
			Long oldAccountCloserStatus, Long newAccountCloserStatus) throws WalletException {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         
		TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
		Audit audit = new Audit();
		try {
			audit.setAuthId(authId);
			audit.setEmailId(emailId);
			audit.setModuleName(moduleName);
			audit.setStatus(status);
			audit.setUserType(userType);
			audit.setCreationDate(new Date());
			List<AuditField> list = new ArrayList<AuditField>();		
			list.add(new AuditField(AuditTrailFieldConstrain.ACCOUNT_CLOSER_STATUS, oldAccountCloserStatus.toString(), newAccountCloserStatus.toString()));			
			audit.setAuditFields(list);
			if( audit.getAuditFields() != null){
				for(AuditField field: audit.getAuditFields()){
					field.setAudits(audit);
				}
			}
			audit = auditTrailDao.updateAudit(audit);
			transactionManager.commit(txstatus);
		} catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			try {
				transactionManager.rollback(txstatus);
			} catch (Exception e){
				LOGGER.error(e.getMessage(), e);
			}
		}
		return audit;
	}
	
}
