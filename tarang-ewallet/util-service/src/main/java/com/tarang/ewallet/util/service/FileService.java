/**
 * 
 */
package com.tarang.ewallet.util.service;

/**
 * @author  : prasadj
 * @date    : May 30, 2013
 * @time    : 12:22:39 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface FileService {
	
	Boolean isValidExtention(String extnListStr, String fileExtn);

	String getImageFileName(String location, Long id);
	
	DataFile getDataFile(String location, String fileName);
	
	Boolean deleteImageFile(String location, String name);
	
}
