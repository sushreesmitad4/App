package com.tarang.ewallet.audittrail.repository;

import java.util.List;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Audit;



/**
 * @author vasanthar
 *
 */
public interface AuditTrailRepository {
	
	Audit createAuditTrail(Long authId, String moduleName, Long status, String emailId, String userType, Object oldObject, Object newObject) throws WalletException;
	
	Audit createAuditTrailForDispute(Long authId, String moduleName, Long status, String emailId, String userType, Long disputeStatus, Double disputeAmount, Long newDisputeStatus, Double newDisputeAmount) throws WalletException;
	
	Audit createAuditTrailForRequestMoney(Long authId, String moduleName, Long status, String emailId, String userType, Long oldRequestMoneyStatus, Long newRequestMoneyStatus) throws WalletException;
	
	Audit createAuditTrail(Long authId, String moduleName, Long status, String emailId, String userType) throws WalletException;
	
	Audit createAuditTrailForEmailVarification(Long authId, String moduleName, Long status, String emailId, String userType) throws WalletException;
	
	Audit createAuditTrailForRole(Long authId, String moduleName, Long status, String emailId, String userType, Long roleId) throws WalletException;
	
	Audit createAuditTrailForAccountCloser(Long authId, String moduleName, Long status, String emailId, String userType, Long oldAccountCloserStatus, Long newAccountCloserStatus) throws WalletException;
	
	Audit getAuditTrail(Long id) throws WalletException;
	
	List<Audit> getAllAuditTrailObjects() throws WalletException;
	
}
