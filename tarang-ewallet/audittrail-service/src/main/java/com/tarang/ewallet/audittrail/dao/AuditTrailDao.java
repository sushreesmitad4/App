package com.tarang.ewallet.audittrail.dao;

import java.util.List;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Audit;
import com.tarang.ewallet.model.AuditField;


/**
 * @author vasanthar
 *
 */
public interface AuditTrailDao {
	
	Audit updateAudit(Audit audit) throws WalletException;
	
	Audit createAudit(Audit audit)throws WalletException;

	Audit getAuditTrail(Long id) throws WalletException;
	
	List<AuditField> getAuditFields(Long id) throws WalletException;
	
	List<Audit> getAllAuditTrailObjects() throws WalletException;

}
