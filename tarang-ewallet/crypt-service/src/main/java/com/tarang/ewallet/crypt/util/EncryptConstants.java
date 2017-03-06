/*
 * 
 */
package com.tarang.ewallet.crypt.util;

/**
 * This interface will have Encryption and Decryption Constants.
 */
public interface EncryptConstants {

	String ALGTHM_TYPE_3DES = "DESede";

	String ALGTHM_TYPE_HASH_SHA_256 = "SHA-256";

	String ALGTHM_TYPE_AES = "AES";
	
	String ALGTHM_CBC_PAD_AES = "AES/CBC/PKCS7Padding";
	
	String ALGTHM_CBC_PAD_3DES = "DESede/CBC/PKCS7Padding";
	
	String ALGTHM_PROVIDER_BC = "BC";

	String ENC_DEC_KEY = "T9!&S7V&53A$";

	int KEY_SIZE_3DES = 24;

	int KEY_SIZE_AES = 64;
	
	int IV_PARAM_BYTE_SIZE_3DES = 8;
	
	int IV_PARAM_BYTE_SIZE_AES = 16;
	
	String KEYSTORE_FILE_PATH = "/PayTMKeyStore.jceks";
	
	String KEYSTORE_TYPE = "JCEKS";
	
	String KEYSTORE_PASSWORD = "T9!&S7V&53A$PAYTM";
	
	String ALIAS_TYPE_AES = "ALIAS_AES";
	
	String ALIAS_TYPE_3DES = "ALIAS_3DES";
	
	String KEYSTORE_NOT_FOUND_MSG = "Not able to load or generate the KeyStore";
	
	int MERCHANT_KEY_LENGTH = 20;
	
	int DES_RANDUM_KEY_SIZE = 168;
	
	int AES_RANDUM_KEY_SIZE = 256;
	
	int RANDUM_BIGINT_LENGTH = 155;
	
	int RANDUM_BIGINT_STRING_LENGTH = 30;
	
	String  ENCRYPT = "encrypt";
	
	String  DECRYPT = "decrypt";
	
}