/**
 * 
 */
package com.tarang.ewallet.util.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.DataFile;
import com.tarang.ewallet.util.service.FileService;

/**
 * @author  : prasadj
 * @date    : May 30, 2013
 * @time    : 11:26:42 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class FileServiceImpl implements FileService {

	private static final Logger LOGGER = Logger.getLogger(FileServiceImpl.class);
	
	public FileServiceImpl(){
	}
	
	
	public Boolean isValidExtention(String extnListStr, String fileExtn){
		String a[] = extnListStr.split(GlobalLitterals.FILE_LINE_SPLIT);
		for(int i = 0; i < a.length; i++){
			if(a[i].equalsIgnoreCase(fileExtn)){
				return Boolean.TRUE;
			}
    	}
		return Boolean.FALSE;
	}
	
	
	public String getImageFileName(String location, Long id){
		try {
			File dir = new File(location);
			if(dir.isDirectory()){
				File[] files = dir.listFiles();
				for(int i = 0; i < files.length; i++){
					String fn = files[i].getName();
					String ns[] = fn.split(GlobalLitterals.REVERSE_SLASH_STRING + GlobalLitterals.DOT_STRING);
					if(ns[0].equals(id.toString())){
						return fn;
					}
				}
			}
		} catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		return null;
	}
	

	public Boolean deleteImageFile(String location, String name){
		try {
			File file = new File(location, name);
			return file.delete();
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		return Boolean.FALSE;
	}
	
	
	public DataFile getDataFile(String location, String fileName){
	    try {
			File file = new File(location, fileName);
		    byte[] result = new byte[(int)file.length()];
	    	InputStream input = null;
	    	try {
	    		int totalBytesRead = 0;
	    		input = new BufferedInputStream(new FileInputStream(file));
	    		while(totalBytesRead < result.length){
	    			int bytesRemaining = result.length - totalBytesRead;
	    			int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
	    			if (bytesRead > 0){
	    				totalBytesRead = totalBytesRead + bytesRead;
	    			}
	    		}
	    	} finally {
	    		input.close();
	    	}
			return new DataFileImpl(fileName, result);
	    } catch (FileNotFoundException e) {
	    	LOGGER.error(e.getMessage(), e);
	    } catch (Exception ex) {
	    	LOGGER.error(ex.getMessage(), ex);
	    }
	    return null;
	}
	
}
