/**
 * 
 */
package com.tarang.controller;

/**
 * @author  : prasadj
 * @date    : Feb 14, 2013
 * @time    : 1:24:17 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class ConfigureConstants {

	
	private String walletUrl;
	
	private String currency;
	
	private String merchantId;
	
	private String merchantCode;
	
	private String selfUrlSession;
	
	public ConfigureConstants(String walletUrl, String currency, String merchantId, String merchantCode, String selfUrlSession){
		this.walletUrl = walletUrl;
		this.currency = currency;
		this.merchantId = merchantId;
		this.merchantCode = merchantCode;
		this.selfUrlSession = selfUrlSession;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public String getWalletUrl() {
		return walletUrl;
	}

	public String getCurrency() {
		return currency;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public String getSelfUrlSession() {
		return selfUrlSession;
	}
}

