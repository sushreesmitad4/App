/**
 * 
 */
package com.tarang.ewallet.reconcile.business;

import java.util.Date;

import com.tarang.ewallet.model.Reconcile;
import com.tarang.ewallet.reconcile.util.ReconcileStatusConstants;



/**
 * @author  : kedarnathd
 * @date    : March 07, 2013
 * @time    : 9:58:50 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class DataPreparation {
	
	public static Reconcile dataPreReconcile(){
		Reconcile reconcile = new Reconcile();
		reconcile.setActualAmount(112.00);
		reconcile.setPgTxnId(3921890L); 
		reconcile.setScheme("Visa");
		reconcile.setPurchaseDate("12/18/2012");
		reconcile.setPurchaseTime("20:01:47");
		reconcile.setTxnAmount(112.0);
		reconcile.setTxnCurrency("USD");
		reconcile.setCreationDate(new Date());
		reconcile.setStatus(ReconcileStatusConstants.PENDING);
		return reconcile;
	}
}
