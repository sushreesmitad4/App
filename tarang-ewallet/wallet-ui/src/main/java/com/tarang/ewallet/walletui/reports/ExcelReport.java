/**
 * 
 */
package com.tarang.ewallet.walletui.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.lowagie.text.DocumentException;


/**
 * @author  : prasadj
 * @date    : Feb 1, 2013
 * @time    : 12:08:43 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class ExcelReport {

	private static final Logger LOGGER = Logger.getLogger(ExcelReport.class);
	
	private ReportData reportData;
	
	public ExcelReport(ReportData reportData){
		this.reportData = reportData;
	}
	
	public void buildReport(OutputStream outputStream){
		
		try {			
			HSSFWorkbook hwb = new HSSFWorkbook();			
			HSSFSheet sheet = hwb.createSheet(reportData.getTitleName());
			constructHeader(sheet.createRow(0));
			constructBody(sheet, 1);
			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			hwb.write(fileOut);
			outputStream.write(fileOut.toByteArray());
			outputStream.flush();
			outputStream.close();
		} catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void constructHeader(HSSFRow header) throws DocumentException {
		for(int i = 0; i < reportData.getHeaderNames().length; i++){
			header.createCell(i).setCellValue(reportData.getHeaderNames()[i]);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void constructBody(HSSFSheet sheet, int startingRowNumber){
		List dataList = reportData.getData();
		int rowNum = startingRowNumber;
		if(dataList != null) {
			HSSFRow row = null;
			
			for (Object data : dataList){
				row = sheet.createRow(rowNum++);
				Class clazz = data.getClass();
				String valueStr = null;
				for(int i = 0; i < reportData.getHeaderFields().length; i++){
					String fName = reportData.getHeaderFields()[i];
					valueStr = ReportUtil.getFieldValue(clazz, data, fName);
					row.createCell(i).setCellValue(valueStr);
				}
			}
		}
	}
	
}