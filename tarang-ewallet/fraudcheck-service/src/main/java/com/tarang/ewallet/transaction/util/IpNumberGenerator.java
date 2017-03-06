package com.tarang.ewallet.transaction.util;

import java.util.StringTokenizer;

/**
 * 
 * @author rajasekhar
 * 
 * 
 */

public class IpNumberGenerator {

	public static final String DOT = ".";
	
	public static final Integer IP_DOT_COUNTER = 3;
	
	public static final Integer IP_MAX_VALUE = 256;
	
	public static Long getIpNumber(String ipAddress) {
		
		StringTokenizer tokens = new StringTokenizer(ipAddress, DOT, Boolean.FALSE);
		int counter = IP_DOT_COUNTER;
		long ipValue = 0L;
		while (tokens.hasMoreTokens() && counter >= 0) {
			long read = new Long(tokens.nextToken()).longValue();
			long calculated = new Double(read * (Math.pow(IP_MAX_VALUE, counter))).longValue();
			ipValue += calculated;
			counter--;
		}
		return ipValue;
	}
}
