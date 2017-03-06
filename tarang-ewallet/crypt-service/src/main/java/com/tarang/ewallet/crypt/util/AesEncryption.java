package com.tarang.ewallet.crypt.util;

/*
 * 
 */

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.tarang.ewallet.exception.WalletException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * This AesEncryption class implements AES Encryption Algorithm.
 */
public class AesEncryption implements Encryption, EncryptConstants {

	private static final Logger LOGGER = Logger.getLogger(AesEncryption.class);

	private final BASE64Encoder base64Encoder = new BASE64Encoder();
	private final BASE64Decoder base64Decoder = new BASE64Decoder();

	public String encrypt(String toEncrypt) throws WalletException {

		String encryptedValue = "";
		try {
			Cipher cipher = Cipher.getInstance(ALGTHM_CBC_PAD_AES,ALGTHM_PROVIDER_BC);
			byte[] ivParamBytes = new byte[IV_PARAM_BYTE_SIZE_AES]; 
			cipher.init(Cipher.ENCRYPT_MODE, GetKey.getAESSecretKey(),new IvParameterSpec(ivParamBytes));
			encryptedValue = base64Encoder.encode(cipher.doFinal(toEncrypt.getBytes()));

		} catch (Exception e) {
			LOGGER.error(ENCRYPT, e);
			throw new WalletException(e.getMessage(), e);
		} 
		return encryptedValue;
	}

	public String encrypt(String toEncrypt, String key) throws WalletException {
		String encryptedValue = "";
		try {
			Cipher cipher = Cipher.getInstance(ALGTHM_CBC_PAD_AES,ALGTHM_PROVIDER_BC);
			byte[] ivParamBytes = new byte[IV_PARAM_BYTE_SIZE_AES];
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), EncryptConstants.ALGTHM_TYPE_AES),new IvParameterSpec(ivParamBytes));
			encryptedValue = base64Encoder.encode(cipher.doFinal(toEncrypt.getBytes()));

		} catch (Exception e) {
			LOGGER.error(ENCRYPT, e);
			throw new WalletException(e.getMessage(), e);
		} 
		return encryptedValue;
	}

	public String decrypt(String toDecrypt) throws WalletException {
		String decryptedValue = "";
		try {
			Cipher cipher = Cipher.getInstance(ALGTHM_CBC_PAD_AES,ALGTHM_PROVIDER_BC);
			byte[] ivParamBytes = new byte[IV_PARAM_BYTE_SIZE_AES]; 
			cipher.init(Cipher.DECRYPT_MODE, GetKey.getAESSecretKey(),new IvParameterSpec(ivParamBytes));
			decryptedValue = new String(cipher.doFinal(base64Decoder.decodeBuffer(toDecrypt)));
			
		} catch (Exception e) {
			LOGGER.error(DECRYPT, e);
			throw new WalletException(e.getMessage(), e);
		} 
		return decryptedValue;
	}

	public String decrypt(String toDecrypt, String key) throws WalletException {
		String decryptedValue = "";
		try {
			Cipher cipher = Cipher.getInstance(ALGTHM_CBC_PAD_AES,ALGTHM_PROVIDER_BC);
			byte[] ivParamBytes = new byte[IV_PARAM_BYTE_SIZE_AES];
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), EncryptConstants.ALGTHM_TYPE_AES),new IvParameterSpec(ivParamBytes));
			decryptedValue = new String(cipher.doFinal(base64Decoder.decodeBuffer(toDecrypt)));
			
		} catch (Exception e) {
			LOGGER.error(DECRYPT, e);
			throw new WalletException(e.getMessage(), e);
		} 
		return decryptedValue;
	}
}
