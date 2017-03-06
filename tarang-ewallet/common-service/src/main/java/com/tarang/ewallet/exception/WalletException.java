package com.tarang.ewallet.exception;

/**
 * @Author : kedarnathd
 * @Date : Oct 08, 2012
 * @Time : 10:09:24 PM
 * @Version : 1.0
 * @Comments: Customized Exception Class to handle Exceptions
 */
public class WalletException extends Exception {

	private static final long serialVersionUID = 1L;

	public WalletException() {
		super();
	}

	public WalletException(final String errorMessage) {
		super(errorMessage);
	}

	public WalletException(final String errorMessage, final Throwable throwable) {
		super(errorMessage, throwable);
	}
}
