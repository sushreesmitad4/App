/**
 * 
 */
package com.tarang.ewallet.crypt.business;

import com.tarang.ewallet.exception.WalletException;

/**
 * @author  : prasadj
 * @date    : Nov 21, 2012
 * @time    : 12:19:57 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface CryptService {

	String encryptData(String data) throws WalletException;
	
	String decryptData(String data) throws WalletException;
	
}
