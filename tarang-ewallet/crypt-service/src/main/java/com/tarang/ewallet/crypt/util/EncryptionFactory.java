/*
 * 
 */
package com.tarang.ewallet.crypt.util;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This class follows factory pattern for creating Encryption objects based on Algorithm Types.
 */
public final class EncryptionFactory {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	/**
	 * Instantiates a new encryption factory.
	 */
	private EncryptionFactory() {
	}
	
	/**
	 * Gets the different encryption class based on the Algorithm type.
	 * 
	 * @param algorithmType
	 *            the encryption type
	 * @return the encryption
	 */
	public static Encryption getEncryptionInstance(String algorithmType) {

		Encryption encryption = null;
		if (EncryptConstants.ALGTHM_TYPE_3DES.equalsIgnoreCase(algorithmType)) {
			encryption = new DesEncryption();
		} else if (EncryptConstants.ALGTHM_TYPE_AES.equalsIgnoreCase(algorithmType)) {
			encryption = new AesEncryption();
		}		
		return encryption;
	}	
}