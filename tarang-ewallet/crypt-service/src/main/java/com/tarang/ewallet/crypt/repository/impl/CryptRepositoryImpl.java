/**
 * 
 */
package com.tarang.ewallet.crypt.repository.impl;

import org.apache.log4j.Logger;

import com.tarang.ewallet.crypt.dao.CryptDao;
import com.tarang.ewallet.crypt.repository.CryptRepository;
import com.tarang.ewallet.crypt.util.EncryptConstants;
import com.tarang.ewallet.crypt.util.Encryption;
import com.tarang.ewallet.crypt.util.EncryptionFactory;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.util.service.UtilService;


/**
 * @author  : prasadj
 * @date    : Nov 21, 2012
 * @time    : 12:26:09 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CryptRepositoryImpl implements CryptRepository {

	private static final Logger LOGGER = Logger.getLogger(CryptRepositoryImpl.class);

	private CryptDao cryptDao;

	private UtilService utilService;

	private Encryption encryption;
	
	private String key1;

	public CryptRepositoryImpl(CryptDao cryptDao, UtilService utilService) {
		LOGGER.debug( " CryptRepositoryImpl " );
		this.cryptDao = cryptDao;
		this.utilService = utilService;
		this.key1 = getKey1();
	}

	public String encryptData(String data) throws WalletException {
		String encData = null;
		try{
			encData = encryption.encrypt(data, key1);
		} catch(Exception ex){
			throw new WalletException(ex.getMessage(), ex);
		}
		return encData;
	}
	
	public String decryptData(String data) throws WalletException {
		String decData = null;
		try{
			decData = encryption.decrypt(data, key1);
		} catch(Exception ex){
			throw new WalletException(ex.getMessage(), ex);
		}
		return decData;

	}
	
	private String getKey1() {
		String k1 = "";
		try {
			String key2 = cryptDao.readKey();
			encryption = EncryptionFactory.getEncryptionInstance(EncryptConstants.ALGTHM_TYPE_AES);
			k1 = encryption.decrypt(utilService.getEncryptedKey1(), key2);
		} catch (Exception ex){
			LOGGER.error("statup", ex);
		}
		return k1;
	}
	
}
