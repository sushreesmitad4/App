/**
 * 
 */
package com.tarang.ewallet.crypt.business.impl;

import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.crypt.repository.CryptRepository;
import com.tarang.ewallet.exception.WalletException;


/**
 * @author  : prasadj
 * @date    : Nov 21, 2012
 * @time    : 12:22:08 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CryptServiceImpl implements CryptService {

	private CryptRepository cryptRepository;

	public CryptServiceImpl(CryptRepository cryptRepository) {
		this.cryptRepository = cryptRepository;
	}
	
	public String encryptData(String data) throws WalletException{
		return cryptRepository.encryptData(data);
	}

	public String decryptData(String data) throws WalletException{
		return cryptRepository.decryptData(data);
	}

}
