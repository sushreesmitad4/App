/*
 * 
 */
package com.tarang.ewallet.crypt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Key;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.log4j.Logger;

import com.tarang.ewallet.exception.WalletException;

/**
 * This CustomKeyStore class will get/generate the KeyStore from the file.
 */
public class CustomKeyStore implements 	EncryptConstants {

	private static final Logger LOGGER = Logger.getLogger(CustomKeyStore.class);
	
	private String getKeyStore ="getKeyStore";
	
	private String storeKeyStore = "storeKeyStore";
	
	/**
	 * This method will get the SecretKey from the KeyStore file or generate the
	 * SecretKey and store it in the KeyStore File based on the Algorithm Type
	 * 
	 * @param algorithmType
	 *            the algorithm type
	 * @return the secret key
	 * @throws WalletException
	 *             the wallet exception
	 */
	public SecretKey getSecretKey(String algorithmType) throws WalletException {
		SecretKey secretKey = null;
		try {
			KeyStore keyStore = getKeyStore();
			if (null != keyStore) {
				if (ALGTHM_TYPE_3DES.equalsIgnoreCase(algorithmType)) {
					Key key = keyStore.getKey(ALIAS_TYPE_3DES,
							KEYSTORE_PASSWORD.toCharArray());
					if (key instanceof SecretKey) {
						secretKey = (SecretKey) key;
					} else {
						secretKey = getStoredSecretKey(algorithmType, keyStore);
					}

				} else if (ALGTHM_TYPE_AES.equalsIgnoreCase(algorithmType)) {
					Key key = keyStore.getKey(ALIAS_TYPE_AES,
							KEYSTORE_PASSWORD.toCharArray());
					if (key instanceof SecretKey) {
						secretKey = (SecretKey) key;
					} else {
						secretKey = getStoredSecretKey(algorithmType, keyStore);
					}
				}
			} else {
				throw new SecurityException("KEYSTORE_NOT_FOUND",
						KEYSTORE_NOT_FOUND_MSG);
			}
		} catch (Exception e) {
			LOGGER.error("getSecretKey", e);
			throw new WalletException(e.getMessage(), e);
		} 

		return secretKey;
	}

	/**
	 * This method will generate the SecretKey based on the Algorithm types and
	 * store the Key in the KeyStore File.
	 * 
	 * @param algorithmType
	 *            the algorithm type
	 * @param keyStore
	 *            the key store
	 * @return the stored secret key
	 * @throws WalletException
	 *             the wallet exception
	 */
	private SecretKey getStoredSecretKey(String algorithmType, KeyStore keyStore)
			throws WalletException {
		SecretKey secretKey = null;
		try {
			KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(
					KEYSTORE_PASSWORD.toCharArray());
			GenerateKey generateKey = new GenerateKey();
			String keyValue = generateKey.generateFinalKey(ENC_DEC_KEY,
					algorithmType);
			File keyFile = getKeyStoreFile();
			if (ALGTHM_TYPE_3DES.equalsIgnoreCase(algorithmType)) {
				secretKey = generateCustomSecretKey(algorithmType, keyValue);
				KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(
						secretKey);
				keyStore.setEntry(ALIAS_TYPE_3DES, skEntry, passwordProtection);
				storeKeyStore(keyFile, keyStore);
			} else if (ALGTHM_TYPE_AES.equalsIgnoreCase(algorithmType)) {
				secretKey = generateCustomSecretKey(algorithmType, keyValue);
				KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(
						secretKey);
				keyStore.setEntry(ALIAS_TYPE_AES, skEntry, passwordProtection);
				storeKeyStore(keyFile, keyStore);
			}
		} catch (Exception e) {
			LOGGER.error("getStoredSecretKey", e);
			throw new WalletException(e.getMessage(), e);
		}
		return secretKey;
	}

	/**
	 * This method will store the SecretKey in the KeyStore File.
	 * 
	 * @param keyFile
	 *            the key file
	 * @param keyStore
	 *            the key store
	 * @throws WalletException
	 *             the wallet exception
	 */
	private void storeKeyStore(File keyFile, KeyStore keyStore)
			throws WalletException {

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(keyFile);
			keyStore.store(fos, KEYSTORE_PASSWORD.toCharArray());
		} catch (Exception e) {
			LOGGER.error( storeKeyStore , e);
			throw new WalletException(e.getMessage(), e);
		} finally {
			if (null != fos) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					LOGGER.error( storeKeyStore , e);
					throw new WalletException(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * This method is used to get the key store file.
	 * 
	 * @return the key store file
	 * @throws WalletException
	 *             the wallet exception
	 */
	private File getKeyStoreFile() throws WalletException {
		File keyFile = null;
		try {
			URL path = this.getClass().getResource(KEYSTORE_FILE_PATH);
			if (null != path) {
				keyFile = new File(path.toURI());
			} else {
				keyFile = new File(KEYSTORE_FILE_PATH);
			}
		} catch (URISyntaxException e) {
			LOGGER.error("getKeyStoreFile", e);
			throw new WalletException(e.getMessage(), e);
		}
		return keyFile;
	}

	/**
	 * This method will load the KeyStore from the File or Create the New
	 * KeyStore File.
	 * 
	 * @return the key store
	 * @throws WalletException
	 *             the wallet exception
	 */
	private KeyStore getKeyStore() throws WalletException {
		KeyStore keyStore = null;
		FileInputStream fis = null;
		try {
			keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
			File keyFile = getKeyStoreFile();
			if (null != keyFile && keyFile.exists()) {
				fis = new FileInputStream(keyFile);
				keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());
			} else {
				keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());
				storeKeyStore(keyFile, keyStore);
			}
		} catch (Exception e) {
			LOGGER.error(getKeyStore, e);
			throw new WalletException(e.getMessage(), e);
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					LOGGER.error(getKeyStore, e);
					throw new WalletException(e.getMessage(), e);
				}
			}
		}
		return keyStore;
	}

	/**
	 * This method is used to generate the SecretKey based on the Key Value and
	 * Algorithm type.This Secret key is generated based on 256 bit Encryption
	 * for AES, So Please Download the Java Cryptography Extension (JCE)
	 * Unlimited Strength Jurisdiction Policy Files 6 or 7 jar file (based on
	 * the java version you are using) from Oracle Site and place the two jar
	 * files in the java installed directory path(Ex:java/jre/lib/security
	 * or/and java/jdk-1.X/jre/lib/security path).
	 * 
	 * 
	 * @param algorithmType
	 *            - Algorithm Type.
	 * @return SecretKey - return the key.
	 * @throws SecurityException
	 *             the security exception
	 */
	private SecretKey generateCustomSecretKey(String algorithmType,
			String keyValue) throws WalletException {
		SecretKey secretKey = null;
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithmType);
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.setSeed(keyValue.getBytes());

			if (ALGTHM_TYPE_3DES.equalsIgnoreCase(algorithmType)) {
				keyGenerator.init(DES_RANDUM_KEY_SIZE, secureRandom);
				secretKey = keyGenerator.generateKey();
			} else if (ALGTHM_TYPE_AES.equalsIgnoreCase(algorithmType)) {
				keyGenerator.init(AES_RANDUM_KEY_SIZE, secureRandom);
				secretKey = keyGenerator.generateKey();
			}
		} catch (Exception e) {
			LOGGER.error("generateCustomSecretKey", e);
			throw new WalletException(e.getMessage(), e);
		}
		return secretKey;
	}

}
