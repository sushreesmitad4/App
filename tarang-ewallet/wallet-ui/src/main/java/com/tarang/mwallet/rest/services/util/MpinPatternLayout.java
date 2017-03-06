package com.tarang.mwallet.rest.services.util;





import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import com.tarang.ewallet.util.CommonProjectUtil;
import com.tarang.ewallet.walletui.validator.common.Common;



public class MpinPatternLayout extends PatternLayout {

	private static final String CLASS_NAME = MpinPatternLayout.class.getName();

	private static final Logger LOGGER = Logger.getLogger(MpinPatternLayout.class);

	private static Properties projectProp;
	@Override
	public String format(LoggingEvent event) {
		
		String mpinMasking = null;
		String newMpinMasking = null;
		Integer mpinLength = Common.M_PIN_MAX_LENGHT;
		
		String MASK = "";
		String passwordParams = null;
		String passwordMask = "";
		
		
		try {
			
			projectProp = getProjectProperties();
			mpinMasking = projectProp.getProperty("mwallet.mpin.masking");	
			
			//passwordParams = projectProp.getProperty("PASSWORD_MASKING");
			
			int i = mpinLength;
			while(i >0){
				MASK+="*";
				i--;
			}
			
            
			Pattern PATTERN = Pattern.compile(mpinMasking);
			
			//Pattern passwordPattern = Pattern.compile(passwordParams);

			String maskedMessage = null;
			if (event.getMessage() instanceof String) {
				String message = event.getRenderedMessage();
				Matcher matcher = PATTERN.matcher(message);
				
				//Matcher passwordMatcher = passwordPattern.matcher(message);
				if (matcher.find() ) {
					StringTokenizer str = new StringTokenizer(mpinMasking, "|");
					while(str.hasMoreTokens() ){
						String jsonmpin = str.nextToken();
						
						if (message.contains(jsonmpin)) {

							String logmPin = null;
							if (null != maskedMessage) {
								logmPin = maskedMessage;
							} else {
								logmPin = message;
							}
							if (null != logmPin) {
								StringBuffer stringBuffer = new StringBuffer(
										logmPin);
								int start = logmPin.indexOf(jsonmpin)+jsonmpin.length();
								maskedMessage = stringBuffer.replace(start, start+mpinLength,
										MASK).toString();								
							}
							
							
						}
					}
					@SuppressWarnings({ "ThrowableResultOfMethodCallIgnored" })
					Throwable throwable = event.getThrowableInformation() != null ? event
							.getThrowableInformation().getThrowable() : null;
							LoggingEvent maskedEvent = new LoggingEvent(
									event.fqnOfCategoryClass, Logger.getLogger(event
											.getLoggerName()), event.timeStamp,
											event.getLevel(), maskedMessage, throwable);
							return super.format(maskedEvent);
				}
				
				/*if(passwordMatcher.find()){

					StringTokenizer str = new StringTokenizer(passwordParams, "|");
					while(str.hasMoreTokens()){
						String passwordParam = str.nextToken();

						if (message.contains(passwordParam)) {
							String logPasswordMask = null;
							if (null != maskedMessage) {
								logPasswordMask = maskedMessage;
							} else {
								logPasswordMask = message;
							}
							if (null != logPasswordMask) {
								StringBuffer stringBuffer = new StringBuffer(
										logPasswordMask);
								
								int start = logPasswordMask.indexOf(passwordParam)+passwordParam.length();
								String[] logParts =	logPasswordMask.split(passwordParam);
							    String secondPart = logParts[1];
							    int passwordEnd = secondPart.indexOf("\"");
							    if(passwordEnd > 0){
							    	String passwordContent = secondPart.substring(0, passwordEnd);
								    int j = passwordContent.length();
								    while(j >0){
								    	passwordMask+="*";
								    	j--;
								    }
								    maskedMessage = stringBuffer.replace(start, start+passwordContent.length(),
											passwordMask).toString();
							    }
							}
						}
					}
					@SuppressWarnings({ "ThrowableResultOfMethodCallIgnored" })
					Throwable throwable = event.getThrowableInformation() != null ? event
							.getThrowableInformation().getThrowable() : null;
							LoggingEvent maskedEvent = new LoggingEvent(
									event.fqnOfCategoryClass, Logger.getLogger(event
											.getLoggerName()), event.timeStamp,
											event.getLevel(), maskedMessage, throwable);
							return super.format(maskedEvent);
				
				}*/
			}
		} catch (FileNotFoundException f) {
			StringBuilder errorBuffer = new StringBuilder();
			errorBuffer.append("Exception occured while reading file").append(f.getMessage());


		} catch (IOException e) {
			StringBuilder errorBuffer = new StringBuilder();
			errorBuffer.append("Exception occured while reading file").append(e.getMessage());
		}
		catch (Exception e) {
			StringBuilder errorBuffer = new StringBuilder();
			errorBuffer.append("Exception occured while reading file").append(e.getMessage());
		}

		return super.format(event);
	}
	
	/**
	 * Gets the project properties.
	 *
	 * @return the project properties
	 * @throws IOException 
	 */
	public static Properties getProjectProperties() throws IOException
	{
		if(null == projectProp){
			projectProp = CommonProjectUtil.loadProperties("wallet.properties");
		}
		return projectProp;
	}
	
	

}

