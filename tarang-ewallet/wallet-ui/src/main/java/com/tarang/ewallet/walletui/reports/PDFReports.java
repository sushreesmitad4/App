/**
 * 
 */
package com.tarang.ewallet.walletui.reports;

import java.awt.Color;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * @author  : prasadj
 * @date    : Jan 17, 2013
 * @time    : 9:00:41 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class PDFReports {

	private static final int PAGE_HEADER_FONT_SIZE = 10;
	private static final String PAGE_HEADER_FONT_STYLE = "bold";
	private static final Color PAGE_HEADER_FONT_COLOR = new Color(255, 255, 255);
	private static final Color PAGE_HEADER_CELL_BACKGROUND_COLOR = new Color(20,105,160);

	private static final int TABLE_HEADER_FONT_SIZE = 6;
	private static final String TABLE_HEADER_FONT_STYLE = "bold";
	private static final Color TABLE_HEADER_FONT_COLOR = new Color(20, 105, 160);

	private static final int TABLE_CELL_FONT_SIZE = 5;
	private static final Color TABLE_CELL_FONT_COLOR = new Color(0, 0, 0);

	private static final int WIDTH_PERCENTAGE = 105;
	
	private Document document;
	
	private ReportData reportData;
	
	private PdfPTable table;
	
	public PDFReports(Document document, ReportData reportData){
		this.document = document;
		this.reportData = reportData;
	}
	
	public void buildReport() throws DocumentException {
		document.open();
		table = new PdfPTable(reportData.getHeaderNames().length);
		constructHeader();
		constructBody();
		document.add(table);
		document.close();
	}
	
	private void constructHeader() throws DocumentException {
		//to set Style For header
        Font headerStyle = new Font();
        headerStyle.setSize(PAGE_HEADER_FONT_SIZE);
        headerStyle.setColor(PAGE_HEADER_FONT_COLOR);
        headerStyle.setStyle(PAGE_HEADER_FONT_STYLE);      
        
        PdfPTable tableHeader = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Paragraph(reportData.getTitleName(), headerStyle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(PAGE_HEADER_CELL_BACKGROUND_COLOR);
        
        tableHeader.addCell(cell);
        tableHeader.setWidthPercentage(WIDTH_PERCENTAGE);       
        document.add(tableHeader);
        
	       //to Set style For table header 
        Font tableHeaderFont = new Font();
        tableHeaderFont.setSize(TABLE_HEADER_FONT_SIZE);
        tableHeaderFont.setColor(TABLE_HEADER_FONT_COLOR);
        tableHeaderFont.setStyle(TABLE_HEADER_FONT_STYLE);
        document.add(new Paragraph(" "));

		table.setWidthPercentage(WIDTH_PERCENTAGE);

		for(String headerName: reportData.getHeaderNames()){
			table.addCell(new PdfPCell(new Phrase(headerName, tableHeaderFont)));	
		}
		table.setWidths(reportData.getColumnWidths());

	}
	
	@SuppressWarnings("rawtypes")
	private void constructBody() {
		//to Set style For table data 
        Font tableDataStyle = new Font();
        tableDataStyle.setSize(TABLE_CELL_FONT_SIZE);
        tableDataStyle.setColor(TABLE_CELL_FONT_COLOR);
        
        List dataList = reportData.getData();
		for (Object data : dataList){
			Class clazz = data.getClass();
			String valueStr = null;
			for(String fName: reportData.getHeaderFields()){
				valueStr = ReportUtil.getFieldValue(clazz, data, fName);
				table.addCell(new PdfPCell(new Phrase(valueStr, tableDataStyle)));
			}
		}
    }
}