package com.tarang.ewallet.reconcile.business.impl;
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
import com.tarang.ewallet.reconcile.business.ReconcileService;
import com.tarang.ewallet.reconcile.repository.ReconcileRepository;



public class ReconcileServiceImpl implements ReconcileService{
	
	private ReconcileRepository reconcileRepository;
	
	public ReconcileServiceImpl(ReconcileRepository reconcileRepository) {
		this.reconcileRepository = reconcileRepository;
	}

	@Override
	public void startReconcile() throws WalletException {
		reconcileRepository.startReconcile();
	}
	
	@Override
	public Reconcile createReconcile(Reconcile reconcile) throws WalletException {
		return reconcileRepository.createReconcile(reconcile);
	}

	@Override
	public List<ReconcileDto> getReconcileRecords(Date fromDate, Date toDate, Long status) throws WalletException {
		return reconcileRepository.getReconcileRecords(fromDate, toDate, status );
	}

	@Override
	public ReconcileDto getReconcileRecordById(Long id) throws WalletException {
		return reconcileRepository.getReconcileRecordById(id);
	}

	@Override
	public Reconcile updateReconcile(Long reconcileId, Double finalAmount, Long status) throws WalletException {
		return reconcileRepository.updateReconcile(reconcileId, finalAmount, status);
	}
}
