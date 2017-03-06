/**
 * 
 */
package com.tarang.ewallet.walletui.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.DataFile;
import com.tarang.ewallet.util.service.FileService;
import com.tarang.ewallet.util.service.UtilService;


/**
 * @author  : prasadj
 * @date    : May 29, 2013
 * @time    : 4:31:07 PM
 * @version : 1.0.0
 * @comments: 
 *
 */

public class ImgServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static final int PATH_STRING_COUNT = 4;

    private static final int IMG_NAME_POSITION_IN_PATH = 3;
    
    private static final String IMG_CONTENT_TYPE = "image/gif";

	@Autowired
	private UtilService utilService;

	@Autowired
	private FileService fileService;

	private static final Logger LOGGER = Logger.getLogger(ImgServlet.class);
	
	public void init(ServletConfig config){
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    	utilService = (UtilService) applicationContext.getBean("utilService");
    	fileService = (FileService) applicationContext.getBean("fileService");
	}
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DataFile dataFile = null;
        try {
        	dataFile = fileService.getDataFile(utilService.getUploadImageFileLocation(), getImageId(request.getRequestURI()));
        	if(dataFile == null){
            	dataFile = fileService.getDataFile(utilService.getUploadImageFileLocation(), utilService.getDefaultImageFileName());
        	}
        } catch(Exception ex){
        	LOGGER.error(ex.getMessage() , ex );
        	dataFile = fileService.getDataFile(utilService.getUploadImageFileLocation(), utilService.getDefaultImageFileName());
        }
        if(dataFile != null){
        	doImageProcess(dataFile, response);
        }
	}
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	doGet(request, response);
	}
    
    private void doImageProcess(DataFile dataFile, HttpServletResponse response)throws IOException{
        byte[] data = dataFile.getData();
         //depends on file extension type
        response.setContentType(IMG_CONTENT_TYPE);
        response.setContentLength(data.length);
        OutputStream out = response.getOutputStream();
        out.write(data);
        out.close();
    }
    
    private String getImageId(String uri){
    	String a[] = uri.split(GlobalLitterals.SLASH_STRING);
    	if( a.length == PATH_STRING_COUNT ){
    		return a[IMG_NAME_POSITION_IN_PATH];
    	}
    	return utilService.getDefaultImageFileName();
    }
    
}