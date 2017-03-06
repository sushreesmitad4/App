/**
 * 
 */
package com.tarang.ewallet.util.service.impl;

import com.tarang.ewallet.util.service.DataFile;

/**
 * @author  : prasadj
 * @date    : May 30, 2013
 * @time    : 12:10:39 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class DataFileImpl implements DataFile {

	private String fileName;
	private byte[] data;

	public DataFileImpl(String fileName, byte[] data){
		this.fileName = fileName;
		this.data = data;
	}
	
	//@Override
	public byte[] getData() {
		return data;
	}

	//@Override
	public String getName() {
		return fileName;
	}

}
