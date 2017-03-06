/**
 * 
 */
package com.tarang.ewallet.walletui.reports;

import java.util.List;

/**
 * @author  : prasadj
 * @date    : Jan 17, 2013
 * @time    : 9:06:58 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
@SuppressWarnings("rawtypes")
public class ReportData {

	private String[] headerNames;
	
	private String[] headerFields;
	
	private List data;
	
	private String dataType;
	
	private String titleName;
	
	/* {1.1f,0.6f,1f,1f,1f,1f,1f,0.6f,0.6f,1f,1f,1f} */
	private float[] columnWidths; 
	
	public ReportData(){
	}
	
	public ReportData(String[] headerNames, String[] headerFields, List data, String titleName, float[] columnWidths, String dataType){
		if(headerNames == null){
			this.headerNames = new String[0];
		} else {
			this.headerNames = new String[headerNames.length];
			for(int i = 0; i < headerNames.length; i++){
				this.headerNames[i] = headerNames[i];
			}
		}
		if(headerFields == null){
			this.headerFields = new String[0];
		} else {
			this.headerFields = new String[headerFields.length];
			for(int i = 0; i < headerFields.length; i++){
				this.headerFields[i] = headerFields[i];
			}
		}
		this.data = data;
		this.titleName = titleName;
		this.columnWidths = columnWidths;
		if(columnWidths == null){
			this.columnWidths = new float[0];
		} else {
			this.columnWidths = new float[columnWidths.length];
			for(int i = 0; i < columnWidths.length; i++){
				this.columnWidths[i] = columnWidths[i];
			}
		}
		this.dataType = dataType;
	}

	public String[] getHeaderNames() {
		return headerNames;
	}

	public String[] getHeaderFields() {
		return headerFields;
	}

	public List getData() {
		return data;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public float[] getColumnWidths() {
		return columnWidths;
	}

	public String getDataType() {
		return dataType;
	}
	
	public float getTotalColumnWidths(){
		float totalWidth = 0.0f;
		for(float column: columnWidths){
			totalWidth += column;
		}
		return totalWidth;
	}
}