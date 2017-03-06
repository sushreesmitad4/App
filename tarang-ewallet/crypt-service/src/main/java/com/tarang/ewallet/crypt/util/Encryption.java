/*
 * 
 */
package com.tarang.ewallet.crypt.util;

import com.tarang.ewallet.exception.WalletException;

/**
 * This Encryption Interface is a base interface,for different
 * algorithms. The respective class has to implement this interface.
 */
public interface Encryption {

	/**
	 * This method will encrypt the given data.
	 *
	 * @param toEncrypt the to encrypt
	 * @return the string value in encoded format.
	 * @throws WalletException the security exception
	 */
	String encrypt(String toEncrypt) throws WalletException;
	
	/**
	 * This method will encrypt the data based on the given key.
	 *
	 * @param toEncrypt the to encrypt
	 * @param password the password
	 * @return the string
	 * @throws WalletException the security exception
	 */
	String encrypt(String toEncrypt,String key) throws WalletException;

	/**
	 * This method will decrypt the given data.
	 *
	 * @param toDecrypt the to decrypt
	 * @return the string in readable format.
	 * @throws WalletException the security exception
	 */
	String decrypt(String toDecrypt) throws WalletException;
	
	/**
	 * This method will decrypt the data based on the given key.
	 *
	 * @param toEncrypt the to encrypt
	 * @param password the password
	 * @return the string
	 * @throws WalletException the security exception
	 */
	String decrypt(String toDecrypt,String key) throws WalletException;

}
