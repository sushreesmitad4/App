/*
 * 
 */
package com.tarang.ewallet.crypt.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

import com.tarang.ewallet.exception.WalletException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *  This DesEncryption class implements 3DES Encryption Algorithm.
 */
public class DesEncryption implements Encryption,  EncryptConstants {

	private static final Logger LOGGER = Logger.getLogger(AesEncryption.class);

	private final BASE64Encoder base64Encoder = new BASE64Encoder();
	private final BASE64Decoder base64Decoder = new BASE64Decoder();

	public String encrypt(String toEncrypt) throws WalletException {
		String encryptedValue = "";
		try {
			
			Cipher cipher = Cipher.getInstance(ALGTHM_CBC_PAD_3DES,ALGTHM_PROVIDER_BC);
			byte[] ivParamBytes = new byte[IV_PARAM_BYTE_SIZE_3DES];
			cipher.init(Cipher.ENCRYPT_MODE, GetKey.getDESSecretKey(),new IvParameterSpec(ivParamBytes));
			encryptedValue = base64Encoder.encode(cipher.doFinal(toEncrypt
					.getBytes()));
			
		} catch (Exception e) {
			LOGGER.error(ENCRYPT , e);
			throw new WalletException(e.getMessage(), e);
		} 
		return encryptedValue;
	}

	public String encrypt(String toEncrypt,String key) throws WalletException {
		String encryptedValue = "";
		try {
			
			Cipher cipher = Cipher.getInstance(ALGTHM_CBC_PAD_3DES,ALGTHM_PROVIDER_BC);
			byte[] ivParamBytes = new byte[IV_PARAM_BYTE_SIZE_3DES];
			cipher.init(Cipher.ENCRYPT_MODE, SecretKeyFactory.getInstance(EncryptConstants.ALGTHM_TYPE_3DES)
					.generateSecret(new DESedeKeySpec(key.getBytes())),new IvParameterSpec(ivParamBytes));
			encryptedValue = base64Encoder.encode(cipher.doFinal(toEncrypt
					.getBytes()));
			
		} catch (Exception e) {
			LOGGER.error(ENCRYPT , e);
			throw new WalletException(e.getMessage(), e);
		} 
		return encryptedValue;
	}
	
	public String decrypt(String toDecrypt) throws WalletException {
		String decryptedValue = "";
		try {
			Cipher cipher = Cipher.getInstance(ALGTHM_CBC_PAD_3DES,ALGTHM_PROVIDER_BC);
			byte[] ivParamBytes = new byte[IV_PARAM_BYTE_SIZE_3DES]; 
			cipher.init(Cipher.DECRYPT_MODE, GetKey.getDESSecretKey(),new IvParameterSpec(ivParamBytes));
			decryptedValue = new String(cipher.doFinal(base64Decoder
					.decodeBuffer(toDecrypt)));
			
		} catch (Exception e) {
			LOGGER.error(DECRYPT , e);
			throw new WalletException(e.getMessage(), e);
		}
		return decryptedValue;
	}
	
	public String decrypt(String toDecrypt,String key) throws WalletException {
		String decryptedValue = "";
		try {
			Cipher cipher = Cipher.getInstance(ALGTHM_CBC_PAD_3DES,ALGTHM_PROVIDER_BC);
			byte[] ivParamBytes = new byte[IV_PARAM_BYTE_SIZE_3DES]; 
			cipher.init(Cipher.DECRYPT_MODE, SecretKeyFactory.getInstance(EncryptConstants.ALGTHM_TYPE_3DES)
					.generateSecret(new DESedeKeySpec(key.getBytes())),new IvParameterSpec(ivParamBytes));
			decryptedValue = new String(cipher.doFinal(base64Decoder
					.decodeBuffer(toDecrypt)));
			
		} catch (Exception e) {
			LOGGER.error(DECRYPT , e);
			throw new WalletException(e.getMessage(), e);
		} 
		return decryptedValue;
	}
}