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

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Reconcile;
import com.tarang.ewallet.reconcile.business.ReconcileService;



@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class ReconcileServicesTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
    private ReconcileService reconcileService;
	
	@Test
	public void startReconcileTest() throws WalletException{
		reconcileService.startReconcile();
	}
	
	@Test
	public void createReconcileTest() throws WalletException{
		Reconcile reconcile = reconcileService.createReconcile(DataPreparation.dataPreReconcile());
		Assert.assertNotNull(reconcile);
		Assert.assertNotNull(reconcile.getId());
	}
	
	@Test
	public void updateReconcileTest() throws WalletException{
		Reconcile reconcile = reconcileService.createReconcile(DataPreparation.dataPreReconcile());
		Assert.assertNotNull(reconcile);
		Assert.assertNotNull(reconcile.getId());
		
		Reconcile newreconcile = reconcileService.getReconcileRecordById(reconcile.getId());
		newreconcile.setFinalAmount(110.0);
		newreconcile.setStatus(2L);
		newreconcile.setUpdateDate(new Date());
		Reconcile recon = reconcileService.updateReconcile(newreconcile.getId(), 
				newreconcile.getFinalAmount(), newreconcile.getStatus());
		Assert.assertNotNull(recon);
		Assert.assertNotNull(recon.getId());
	}
}
