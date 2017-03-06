package com.tarang.ewallet.util;


/**
* @author  : prasadj
* @date    : Oct 8, 2012
* @time    : 8:54:31 AM
* @version : 1.0.0
* @comments: constants for filter criteria
*
*/
public interface CriteriaOperations {

	//'cn', 'eq', 'ne', 'lt', 'gt', 'bw', 'ew'
	
	String CONTAINS = "cn";
	
	String EQUAL = "eq";
	
	String NOTEQUAL = "ne";
	
	String LESSTHAN = "lt";
	
	String GREATERTHAN = "gt";
	
	String BEGINSWITH = "bw";
	
	String ENDSWITH = "ew";
	
	//'AND', 'OR'
	
	String LOGICAL_AND = "AND";
	
	String LOGICAL_OR = "OR";
	
	Boolean ORDER_REQUIRED = true;
	
	Boolean ORDER_NOT_REQUIRED = false;
	
	int NONE_TYPE = -1;
	
	int STRING_TYPE = 0;
	
	int NUMBER_TYPE = 1;

	int BOOLEAN_TYPE = 2;

	int DATE_TYPE = 3;

}
