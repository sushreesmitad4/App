/**
 * 
 */
package com.tarang.ewallet.prepaidcard.dao;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.PrepaidCardAccount;


/**
 * @author  : kedarnathd
 * @date    : July 26, 2016
 * @time    : 4:01:53 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface PrepaidCardDao {

	PrepaidCardAccount createPrepaidCard(PrepaidCardAccount prepaidCardAccount) throws WalletException;

	PrepaidCardAccount getPrepaidCard(Long customerId) throws WalletException;
	
	

	
}
