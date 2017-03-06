package com.tarang.mwallet.rest.services.util;

import org.apache.commons.codec.binary.Base64;
/**
 * @author kedarnathd
 *
 */
public class encryptDecryptUtil {
	/**
	 * @param encrptString
	 * @return
	 */
	public static String encrypt(String encrptString){
		  byte[] encoded = Base64.encodeBase64(encrptString.getBytes());
		  return new String(encoded);
	  }
	  
     /**
     * @param decrptString
     * @return
     */
    public static String decrypt(String decrptString){
		  byte[] decoded = Base64.decodeBase64(decrptString.getBytes());
		  return new String(decoded);
	  }
}
