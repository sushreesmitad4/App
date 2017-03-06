/*
 * 
 */
package com.tarang.ewallet.crypt.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * This Class is used to generate the new key based on the inputs provided.
 */
public class GenerateKey implements EncryptConstants {

	private final byte[] indexArray = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
			30, 31 };
	
	private final int indexArrayLength = indexArray.length;

	private Random random;
	
	/**
	 * Instantiates a new generate key.
	 */
	public GenerateKey() {
		random = new Random();
		random.nextBytes(indexArray);
	}

	/**
	 * Gets the random index.
	 * 
	 * @return the random index
	 */
	private int getRandomIndex() {
		if (null == random) {
			random = new Random();
			return random.nextInt(indexArrayLength);
		} else {
			return random.nextInt(indexArrayLength);
		}
	}

	/**
	 * Generate secure random no.
	 * 
	 * @return the string
	 */
	private String generateSecureRandomNo() {
		SecureRandom secureRandom = new SecureRandom();
		BigInteger bigInteger = new BigInteger(RANDUM_BIGINT_LENGTH, 0, secureRandom);
		return bigInteger.toString(RANDUM_BIGINT_STRING_LENGTH);
	}

	/**
	 * This method will check key length based on the Algorithm Type.It will
	 * return no of chars need to be pad.
	 * 
	 * @param keyLength
	 *            the key length
	 * @param algorithmType
	 *            the algorithm type
	 * @return the int
	 */
	private int checkKeyLength(int keyLength, String algorithmType) {
		int charsToPad = 0;
		if (ALGTHM_TYPE_3DES.equalsIgnoreCase(algorithmType)) {
			charsToPad = KEY_SIZE_3DES - keyLength;

		} else if (ALGTHM_TYPE_AES.equalsIgnoreCase(algorithmType)) {
			charsToPad = KEY_SIZE_AES - keyLength;
		}
		return charsToPad;
	}

	/**
	 * This method generate the new key based on the algorithmType. This will
	 * internally generate the new key based on the inputs provided.
	 * 
	 * @param key
	 *            the key
	 * @param algorithmType
	 *            the algorithm type
	 * @return the string
	 */
	public String generateFinalKey(String key, String algorithmType) {
		String randomNo = generateSecureRandomNo();
		String finalKeyValue = "";
		int charsToPad = checkKeyLength(key.length(), algorithmType);
		if (charsToPad > 0) {
			StringBuilder sb = new StringBuilder(key);
			for (int i = 0; i < charsToPad; i++) {
				sb.append(randomNo.charAt(getRandomIndex()));
			}
			finalKeyValue = sb.toString();
		} else if (charsToPad < 0) {
			int keyLength = key.length() + charsToPad;
			finalKeyValue = key.substring(0, keyLength);
		} else {
			finalKeyValue = key;
		}
		return finalKeyValue;
	}

}
