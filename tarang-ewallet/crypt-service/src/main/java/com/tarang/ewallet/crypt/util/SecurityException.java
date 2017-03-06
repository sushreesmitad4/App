/*
 * This code contains copyright information which is the proprietary property
 * of Tarang Software Technologies Pvt Ltd . No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior written permission.
 *
 * Copyright (C) Tarang Software Technologies Pvt Ltd 2012. All rights reserved.
 * ------------------------------------------------------------------------------
 * Version : 1.0
 * Created on : 
 * Author : 
 * Description : This Class will give the security exception details.
 * ------------------------------------------------------------------------------
 * Change History
 * ------------------------------------------------------------------------------
 * 
 * ------------------------------------------------------------------------------
 */
package com.tarang.ewallet.crypt.util;

import java.io.PrintStream;
import org.apache.log4j.Logger;

/**
 * This Class will give the security exception details.
 */
public class SecurityException extends Exception {

	private static final long serialVersionUID = -3956900350777254445L;
	private String errorCode;
	private String errorMessage;
	private Exception exception;
    
	private static final Logger LOGGER = Logger.getLogger(SecurityException.class);
	
	/**
	 * Instantiates a new security exception.
	 *
	 * @param errorCode the error code
	 * @param errorMessage the error message
	 */
	public SecurityException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		
	}
	
	
	/**
	 * Instantiates a new security exception.
	 *
	 * @param errorMessage the error message
	 */
	public SecurityException(String errorMessage) {
		super(errorMessage);
	}

	
	/**
	 * Instantiates a new security exception.
	 *
	 * @param cause the cause
	 */
	public SecurityException(Throwable cause) {
		super(cause);
	}

	
	/**
	 * Instantiates a new security exception.
	 *
	 * @param errorMessage the error message
	 * @param cause the cause
	 */
	public SecurityException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	
	/**
	 * Instantiates a new security exception.
	 *
	 * @param errorMessage the error message
	 * @param exception the exception
	 */
	public SecurityException(String errorMessage, Exception exception) {
		this.errorMessage = errorMessage;
		this.exception = exception;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	public void printStackTrace(PrintStream stream) {
		if (exception != null) {
			LOGGER.error(stream, exception);
		}
	}
}
