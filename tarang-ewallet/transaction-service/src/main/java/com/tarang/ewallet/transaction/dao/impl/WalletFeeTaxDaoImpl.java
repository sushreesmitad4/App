/**
 * 
 */
package com.tarang.ewallet.transaction.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.WalletFee;
import com.tarang.ewallet.model.WalletTax;
import com.tarang.ewallet.transaction.dao.WalletFeeTaxDao;


/**
 * @author  : prasadj
 * @date    : Jan 16, 2013
 * @time    : 12:45:07 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@SuppressWarnings("unchecked")
public class WalletFeeTaxDaoImpl implements WalletFeeTaxDao {

	private HibernateOperations hibernateOperations;
	
	public WalletFeeTaxDaoImpl(HibernateOperations hibernateOperations){
		this.hibernateOperations = hibernateOperations;
	}
	

	@Override
	public WalletFee addFee(WalletFee walletFee) throws WalletException {
		hibernateOperations.save(walletFee);
		return walletFee;
	}

	@Override
	public WalletFee getFeeById(Long feeId) throws WalletException {
		List<WalletFee> list = hibernateOperations.findByNamedQuery("findWalletFeeById", new Object[]{feeId});
		if(list != null && list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public WalletFee getFeeByTransaction(Long transactionId) throws WalletException {
		List<WalletFee> list = hibernateOperations.findByNamedQuery("findWalletFeeByTransaction", new Object[]{transactionId});
		if(list != null && list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public WalletTax addTax(WalletTax walletTax) throws WalletException {
		hibernateOperations.save(walletTax);
		return walletTax;
	}

	@Override
	public WalletTax getTaxById(Long taxId) throws WalletException {
		List<WalletTax> list = hibernateOperations.findByNamedQuery("findWalletTaxById", new Object[]{taxId});
		if(list != null && list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public WalletTax getTaxByTransaction(Long transactionId) throws WalletException {
		List<WalletTax> list = hibernateOperations.findByNamedQuery("findWalletTaxByTransaction", new Object[]{transactionId});
		if(list != null && list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}

}
