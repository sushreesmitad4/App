/**
 * 
 */
package com.tarang.ewallet.crypt.repository;

import com.tarang.ewallet.exception.WalletException;

/**
 * @author  : prasadj
 * @date    : Nov 21, 2012
 * @time    : 12:25:20 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface CryptRepository {

	String encryptData(String data) throws WalletException;
	
	String decryptData(String data) throws WalletException;
	
}
