/**
 * 
 */
package com.tarang.ewallet.transaction.util;

import java.util.HashMap;
import java.util.Map;

import com.tarang.ewallet.exception.WalletException;


/**
 * @author  : prasadj
 * @date    : Jan 16, 2013
 * @time    : 9:15:33 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CurrencyConversion {

	private static CurrencyConversion currencyConversion;
	
	private Map<String, Double> currencyConvertionFactors;
	
	private static final String CURRECY_NOT_SUPPORTING_ERR_MSG = "currecy.not.supporting.err.msg";
	
	private static final String KEY_SEPARATOR = "-";
	
	private static final String USD_KEY = "1";
	private static final String JPY_KEY = "2";
	private static final String THB_KEY = "3";
	private static final String INR_KEY = "4";
	
	private static final Double SELF_RATIO = 1.0;
	private static final Double UJ_RATIO = 88.5504295;
	private static final Double UT_RATIO = 29.9850075;
	private static final Double JU_RATIO = 0.011293;
	private static final Double JT_RATIO = 0.33862069;
	private static final Double TU_RATIO = 0.03335;
	private static final Double TJ_RATIO = 2.95315682; 
	private static final Double IU_RATIO = 68.21; 
	private static final Double IJ_RATIO = 0.6; 
	private static final Double IT_RATIO = 1.91; 
	
	public CurrencyConversion(){
		populateCurrencyFactors();
	}
	
	private void populateCurrencyFactors(){
		currencyConvertionFactors = new HashMap<String, Double>();
		
		currencyConvertionFactors.put(USD_KEY + KEY_SEPARATOR + USD_KEY, SELF_RATIO);
		currencyConvertionFactors.put(USD_KEY + KEY_SEPARATOR + JPY_KEY, UJ_RATIO);
		currencyConvertionFactors.put(USD_KEY + KEY_SEPARATOR + THB_KEY, UT_RATIO);
		currencyConvertionFactors.put(USD_KEY + KEY_SEPARATOR + INR_KEY, IU_RATIO);

		currencyConvertionFactors.put(JPY_KEY + KEY_SEPARATOR + USD_KEY, JU_RATIO);
		currencyConvertionFactors.put(JPY_KEY + KEY_SEPARATOR + JPY_KEY, SELF_RATIO);
		currencyConvertionFactors.put(JPY_KEY + KEY_SEPARATOR + THB_KEY, JT_RATIO);
		currencyConvertionFactors.put(JPY_KEY + KEY_SEPARATOR + INR_KEY, IJ_RATIO);

		currencyConvertionFactors.put(THB_KEY + KEY_SEPARATOR + USD_KEY, TU_RATIO);
		currencyConvertionFactors.put(THB_KEY + KEY_SEPARATOR + JPY_KEY, TJ_RATIO);
		currencyConvertionFactors.put(THB_KEY + KEY_SEPARATOR + THB_KEY, SELF_RATIO);
		currencyConvertionFactors.put(THB_KEY + KEY_SEPARATOR + INR_KEY, IT_RATIO);
		
		currencyConvertionFactors.put(INR_KEY + KEY_SEPARATOR + USD_KEY, IU_RATIO);
		currencyConvertionFactors.put(INR_KEY + KEY_SEPARATOR + JPY_KEY, IJ_RATIO);
		currencyConvertionFactors.put(INR_KEY + KEY_SEPARATOR + THB_KEY, IT_RATIO);
		currencyConvertionFactors.put(INR_KEY + KEY_SEPARATOR + INR_KEY, SELF_RATIO);

	}
	
	public static CurrencyConversion getInstance(){
		if(null == currencyConversion){
			currencyConversion = new CurrencyConversion();
		}
		return currencyConversion;
	}
	
	public Double getConvertionFactor(Long fromCurrencyId, Long toCurrencyId) throws WalletException {
		Double factor = currencyConvertionFactors.get(fromCurrencyId + KEY_SEPARATOR + toCurrencyId);
		if(null == factor){
			throw new WalletException(CURRECY_NOT_SUPPORTING_ERR_MSG);
		}
		return factor;	
	}

	public void setConvertionFactor(Long fromCurrencyId, Long toCurrencyId, Double factor) throws WalletException {
		if(null == fromCurrencyId || fromCurrencyId.equals(0L) 
				|| null == toCurrencyId || toCurrencyId.equals(0L) 
				|| null == factor || factor.equals(0.0)){
			throw new WalletException(CURRECY_NOT_SUPPORTING_ERR_MSG);
		}
		String key = fromCurrencyId + KEY_SEPARATOR + toCurrencyId;
		Double factorOrg = currencyConvertionFactors.get(key);
		if(null == factorOrg){
			throw new WalletException(CURRECY_NOT_SUPPORTING_ERR_MSG);
		}
		currencyConvertionFactors.put(key, factor);
	}

}
