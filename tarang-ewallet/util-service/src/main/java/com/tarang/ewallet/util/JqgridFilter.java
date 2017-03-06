package com.tarang.ewallet.util;

import java.util.List;


/**
* @author  : prasadj
* @date    : Oct 10, 2012
* @time    : 8:57:02 AM
* @version : 1.0.0
* @comments: 
*
 * A POJO that represents a jQgrid JSON requests {@link String}<br/>
 * A sample filter follows the following format:
 * <pre>
 * {"groupOp":"AND","rules":[{"field":"firstName","op":"eq","data":"John"}]}
 * </pre>
*/
public class JqgridFilter {
	
	private String source;
	
	/**
	 * logical group operation AND/OR 
	 */
	private String groupOp;
	
	/**
	 * filter rule against columns
	 */
	private List<FilterRule> rules;
	
	public JqgridFilter() {
		super();
	}
	
	public JqgridFilter(String source) {
		super();
		this.source = source;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getGroupOp() {
		return groupOp;
	}
	
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	
	public List<FilterRule> getRules() {
		return rules;
	}
	
	public void setRules(List<FilterRule> rules) {
		this.rules = rules;
	}

}

