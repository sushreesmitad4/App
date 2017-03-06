/**
 * 
 */
package com.tarang.ewallet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;



/**
 * @author  : prasadj
 * @date    : Mar 13, 2013
 * @time    : 3:12:19 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CommonProjectUtil {
	
	private static final Logger LOGGER = Logger.getLogger(CommonProjectUtil.class);

	private static String propfilePath ="propfilepath";
	
	public static String getAbsolutePath(String  fileName){
		String newFileName = fileName;
		if(System.getProperty(propfilePath) != null){
			newFileName = System.getProperty(propfilePath) + File.separator + fileName;
		}
		return newFileName;
	}
	
	public static Properties loadProperties(String fileName) {
		String propsFileName = getAbsolutePath(fileName);
		Properties props = new Properties();
		try {
			InputStream inputStream = new FileInputStream(new File(propsFileName));
			props.load(inputStream);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
		}
		return props;
	}
	
	public static Long stringToLong(Object str){
		Long strLong = null;
		try{
			if(null == str){
				return null;
			}
			strLong = Long.valueOf(str.toString());
		} catch(Exception ex){
			LOGGER.error(ex.getMessage() , ex );
		}
		return strLong;
	}
	
}
