package com.tarang.ewallet.reconcile.dao.impl;
/**
 * @author  : kedarnathd
 * @date    : March 07, 2013
 * @time    : 9:58:50 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Reconcile;
import com.tarang.ewallet.reconcile.dao.ReconcileDao;



@SuppressWarnings({ "unchecked"})
public class ReconcileDaoImpl implements ReconcileDao{
	
private HibernateOperations hibernateOperations;
	
	ReconcileDaoImpl(HibernateOperations hibernateOperations){
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public Reconcile createReconcile(Reconcile reconcile) throws WalletException {
		try {
			hibernateOperations.save(reconcile);
			hibernateOperations.flush();
		} catch(Exception ex){
			throw new WalletException(ex.getMessage(), ex);
		}
		return reconcile;
	}

	@Override
	public List<Reconcile> getReconcileRecords(Date fromDate, Date toDate,Long status) throws WalletException {
		return (List<Reconcile>) hibernateOperations.findByNamedQuery("getReconcileRecords",status, fromDate, toDate );
	}

	@Override
	public Reconcile getReconcileRecordById(Long id) throws WalletException {
		List<Reconcile> list = (List<Reconcile>) hibernateOperations.findByNamedQuery("getReconcileRecordById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} 
		return null;
	}

	@Override
	public Reconcile updateReconcile(Long reconcileId, Double finalAmount, Long status) throws WalletException {
		Reconcile updateReconcile = null;
		try {
			updateReconcile = getReconcileRecordById(reconcileId);
			updateReconcile.setFinalAmount(finalAmount);
			updateReconcile.setStatus(status);
			updateReconcile.setUpdateDate(new Date());
			hibernateOperations.update(updateReconcile);
		} catch(Exception ex){
			throw new WalletException(ex.getMessage(), ex);
		}
		return updateReconcile;
	}
}
