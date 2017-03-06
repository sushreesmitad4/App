package com.tarang.ewallet.audittrail.business.impl;

import java.util.List;

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.repository.AuditTrailRepository;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Audit;


/**
 * @author vasanthar
 *
 */
public class AuditTrailServiceImpl implements AuditTrailService{
	
	private AuditTrailRepository auditTrailRepository;

	public AuditTrailServiceImpl(AuditTrailRepository auditTrailRepository) {
		this.auditTrailRepository = auditTrailRepository;
	}

	public Audit createAuditTrail(Long authId, String moduleName, Long status, String emailId, String userType, Object oldObject, Object newObject) throws WalletException {
		return auditTrailRepository.createAuditTrail(authId, moduleName, status, emailId, userType, oldObject, newObject);
	}
	
	public Audit createAuditTrail(Long authId, String moduleName, Long status, String emailId, String userType) throws WalletException {
		return auditTrailRepository.createAuditTrail(authId, moduleName, status, emailId, userType);
	}

	public Audit getAuditTrail(Long id) throws WalletException {
		return auditTrailRepository.getAuditTrail(id);
	}

	public List<Audit> getAllAuditTrailObjects() throws WalletException {
		return auditTrailRepository.getAllAuditTrailObjects();
	}

	@Override
	public Audit createAuditTrailForDispute(Long authId, String moduleName, Long status,String emailId, String userType, 
			Long disputeStatus, Double disputeAmount, Long newDisputeStatus, Double newDisputeAmount) throws WalletException {
		return auditTrailRepository.createAuditTrailForDispute(authId, moduleName, status, emailId, userType, disputeStatus, disputeAmount, newDisputeStatus, newDisputeAmount);
	}

	@Override
	public Audit createAuditTrailForRequestMoney(Long authId, String moduleName, Long status, String emailId, String userType,
			Long oldRequestMoneyStatus, Long newRequestMoneyStatus) throws WalletException {
		return auditTrailRepository.createAuditTrailForRequestMoney(authId, moduleName, status, emailId, userType, oldRequestMoneyStatus, newRequestMoneyStatus);
	}

	@Override
	public Audit createAuditTrailForEmailVarification(Long authId, String moduleName, Long status, String emailId, 
			String userType) throws WalletException {
		return auditTrailRepository.createAuditTrailForEmailVarification(authId, moduleName, status, emailId, userType);
	}

	@Override
	public Audit createAuditTrailForRole(Long authId, String moduleName, Long status, String emailId, String userType, Long roleId) throws WalletException {
		return auditTrailRepository.createAuditTrailForRole(authId, moduleName, status, emailId, userType, roleId);
	}

	@Override
	public Audit createAuditTrailForAccountCloser(Long authId, String moduleName, Long status, String emailId, String userType,
			Long oldAccountCloserStatus, Long newAccountCloserStatus) throws WalletException {
		return auditTrailRepository.createAuditTrailForAccountCloser(authId, moduleName, status, emailId, userType, oldAccountCloserStatus, newAccountCloserStatus);
	}

}
