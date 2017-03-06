package com.tarang.ewallet.audittrail.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.audittrail.dao.AuditTrailDao;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Audit;
import com.tarang.ewallet.model.AuditField;


/**
 * @author vasanthar
 *
 */
@SuppressWarnings("unchecked")
public class AuditTrailDaoImpl implements AuditTrailDao{
	
	private HibernateOperations hibernateOperations;

	public AuditTrailDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}	
	
	public Audit updateAudit(Audit audit) throws WalletException {
		try{
			hibernateOperations.saveOrUpdate(audit);
		} catch(Exception e){
			throw new WalletException(e.getMessage(), e);
		}
		return audit;		
	}
	public Audit createAudit(Audit audit) throws WalletException {
		try{
			hibernateOperations.save(audit);
		} catch(Exception e){
			throw new WalletException(e.getMessage(), e);
		}
		return audit;
	}

	public Audit getAuditTrail(Long id) throws WalletException {
		List<Audit> list = (List<Audit>) hibernateOperations.findByNamedQuery("getAuditTrailById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else {
			throw new WalletException(CommonConstrain.UNIQUE_ADDRESS_EXCEPTION);
		}
	}

	public List<AuditField> getAuditFields(Long id) throws WalletException {
		return hibernateOperations.findByNamedQuery("findAuditFieldsById", new Object[]{id});
	}

	public List<Audit> getAllAuditTrailObjects() throws WalletException {
		return hibernateOperations.findByNamedQuery("findAllAuditObjects");
	}

}
