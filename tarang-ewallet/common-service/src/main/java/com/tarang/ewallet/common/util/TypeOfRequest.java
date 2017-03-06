/**
 * 
 */
package com.tarang.ewallet.common.util;

/**
 * @author sanojitn
 * This defines from which channel request has come to the wallet system
 */
public enum TypeOfRequest {
	
	WEB("Web"),
	MOBILE("Mobile");
	
	private final String value;
	private static final Long LONG_MOBILE = 1L;
	private static final Long LONG_WEB = 2L;
	
	private TypeOfRequest(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static Long getLongValue(TypeOfRequest value) {
		if (TypeOfRequest.WEB.equals(value)) {
			return LONG_WEB;
		} else {
			return LONG_MOBILE;
		}
	}
}
