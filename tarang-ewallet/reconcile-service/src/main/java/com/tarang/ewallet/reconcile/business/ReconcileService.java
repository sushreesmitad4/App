package com.tarang.ewallet.reconcile.business;
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

import com.tarang.ewallet.dto.ReconcileDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Reconcile;


public interface ReconcileService {
	
	void startReconcile() throws WalletException;
	
	Reconcile createReconcile(Reconcile reconcile) throws WalletException;
	
	List<ReconcileDto> getReconcileRecords(Date fromDate, Date toDate,Long status) throws WalletException;
	
	ReconcileDto getReconcileRecordById(Long id)throws WalletException;
	
	Reconcile updateReconcile(Long reconcileId, Double finalAmount, Long status)throws WalletException;
}
