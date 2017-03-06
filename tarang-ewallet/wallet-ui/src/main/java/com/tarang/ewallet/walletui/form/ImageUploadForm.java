package com.tarang.ewallet.walletui.form;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadForm implements Serializable{

	private static final long serialVersionUID = 1L;
	//for file upload
	private String file;
	private MultipartFile fileData;
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public MultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
	
}
