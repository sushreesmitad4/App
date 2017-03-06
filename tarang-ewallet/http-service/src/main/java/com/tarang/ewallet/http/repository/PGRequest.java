/**
 * 
 */
package com.tarang.ewallet.http.repository;

import com.tarang.ewallet.exception.WalletException;

import net.sf.json.JSONObject;

/**
 * @author  : prasadj
 * @date    : Dec 27, 2012
 * @time    : 1:19:25 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface PGRequest {

	void initialize() throws WalletException;

	String connectGetMethod(String requestData) throws WalletException;
	
	String connectPostMethod(JSONObject jsonObject) throws WalletException;
	
	String getEwalletMerchantId();
	
	String getEwalletUserId();
}
