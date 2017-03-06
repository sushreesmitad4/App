package com.tarang.ewallet.reconcile.dao;
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

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Reconcile;


public interface ReconcileDao {
	
	Reconcile createReconcile(Reconcile reconcile) throws WalletException;
	
	List<Reconcile> getReconcileRecords(Date fromDate, Date toDate, Long status) throws WalletException;
	
	Reconcile getReconcileRecordById(Long id)throws WalletException;
	
	Reconcile updateReconcile(Long reconcileId, Double finalAmount, Long status)throws WalletException;
}
