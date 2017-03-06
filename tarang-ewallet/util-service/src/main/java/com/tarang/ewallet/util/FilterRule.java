/**
 * 
 */
package com.tarang.ewallet.util;

/**
 * @author  : prasadj
 * @date    : Oct 12, 2012
 * @time    : 6:00:53 PM
 * @version : 1.0.0
 * @comments: class containing field rules
 *
 */
public class FilterRule {
	
	/**
	 * unknown parameter
	 */
	private String junction;
	
	/**
	 * column name in jqgrid table
	 */
	private String field;
	
	/**
	 * filter option from jqgrid
	 */
	private String op;
	
	/**
	 * filter data from jqgrid
	 */
	private String data;

	public FilterRule() {
	}
	
	public FilterRule(String junction, String field, String op, String data) {
		super();
		this.junction = junction;
		this.field = field;
		this.op = op;
		this.data = data;
	}

	public String getJunction() {
		return junction;
	}

	public void setJunction(String junction) {
		this.junction = junction;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
