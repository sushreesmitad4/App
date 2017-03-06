/*
 * 
 */
package com.tarang.ewallet.crypt.util;

import java.security.MessageDigest;
import java.util.Formatter;

import org.apache.log4j.Logger;

import com.tarang.ewallet.exception.WalletException;


/**
 * This CryptoUtils class is used for generating Hash value,Random String and Merchant Key.
 */
public class CryptoUtils implements EncryptConstants {

	private static final Logger LOGGER = Logger.getLogger(CryptoUtils.class);

	/**
	 * This method is used to get the hash value using SHA-256 Algorithm.
	 * 
	 * @param value
	 *            - String to be converted into hash Value.
	 * @return String - return the hash Value.
	 * @throws WalletException
	 *             the security exception
	 */
	public static String getHashFromSHA(String value) throws WalletException {
		String hashValue = "";
		try {
			MessageDigest messageDigest = MessageDigest
					.getInstance(ALGTHM_TYPE_HASH_SHA_256);
			hashValue = byteArray2Hex(messageDigest.digest(value.getBytes()));
		} catch (Exception e) {
			LOGGER.error("getHashFromSHA", e);
			throw new WalletException(e.getMessage(), e);
		}
		return hashValue;
	}

	/**
	 * This method is used to convert the byte array into Hexa format.
	 * 
	 * @param hash
	 *            - byte array to be converted into Hexa format.
	 * 
	 * @return String - return the Hexa Value.
	 * 
	 */
	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	/**
	 * This method will generate random string based on given length.
	 * 
	 * @param length
	 *            - length of the String.
	 * @return the string
	 */
	public static String generateRandomString(int length) {
		String alphaNum = "9876543210ZYXWVUTSRQPONMLKJIHGFEDCBAabcdefghijklmnopqrstuvwxyz!@#$&_";
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			int ndx = (int) (Math.random() * alphaNum.length());
			sb.append(alphaNum.charAt(ndx));
		}
		return sb.toString();
	}
	
	/**
	 * This method gets the merchant key based on the Algorithm Type.
	 *
	 * @param algorithmType the algorithm type
	 * @return the merchant key
	 * @throws WalletException the security exception
	 */
	public static String getMerchantKey(String algorithmType) throws WalletException {
		String randomString = null;
		if(ALGTHM_TYPE_AES.equals(algorithmType)) {	
		  randomString = generateRandomString(EncryptConstants.KEY_SIZE_AES);
		} else if(ALGTHM_TYPE_3DES.equals(algorithmType)) {
			randomString = generateRandomString(EncryptConstants.KEY_SIZE_3DES);
		}
		Encryption encryption = EncryptionFactory.getEncryptionInstance(algorithmType);
		return encryption.encrypt(randomString);
	}

}
