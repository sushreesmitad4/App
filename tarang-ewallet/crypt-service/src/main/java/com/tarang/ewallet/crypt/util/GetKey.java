/*
 * 
 */
package com.tarang.ewallet.crypt.util;

import javax.crypto.SecretKey;

import com.tarang.ewallet.exception.WalletException;


/**
 * This Class follow the Singleton pattern ,Where it will get the Secret
 * Key for the different Algorithm types.
 * 
 */
public class GetKey implements EncryptConstants {

	private static SecretKey desSecretKey;
	private static SecretKey aesSecretKey;

	/**
	 * This method will return 3DES Secret Key
	 * 
	 * @return
	 * @throws WalletException
	 */
	public static SecretKey getDESSecretKey() throws WalletException {
		if (null == desSecretKey) {
			CustomKeyStore customKeyStore = new CustomKeyStore();
			desSecretKey = customKeyStore.getSecretKey(ALGTHM_TYPE_3DES);
		}
		return desSecretKey;
	}

	/**
	 * This method will return 3DES Secret Key
	 * 
	 * @return
	 * @throws WalletException
	 */
	public static SecretKey getAESSecretKey() throws WalletException {
		if (null == aesSecretKey) {
			CustomKeyStore customKeyStore = new CustomKeyStore();
			aesSecretKey = customKeyStore.getSecretKey(ALGTHM_TYPE_AES);
		}

		return aesSecretKey;
	}

}
