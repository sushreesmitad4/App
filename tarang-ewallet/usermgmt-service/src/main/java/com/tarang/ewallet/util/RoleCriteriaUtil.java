package com.tarang.ewallet.util;

import com.tarang.ewallet.util.CriteriaOperations;
import com.tarang.ewallet.util.FilterRule;
import com.tarang.ewallet.util.JqgridFilter;
import com.tarang.ewallet.util.JqgridObjectMapper;
import com.tarang.ewallet.util.QueryFilter;

/**
* @author  : prasadj
* @date    : Oct 10, 2012
* @time    : 8:50:18 AM
* @version : 1.0.0
* @comments: 
*
*/
public class RoleCriteriaUtil implements CriteriaOperations {

	public static void updateCount(int size, QueryFilter qf){

		qf.setTotal(size);

		int tp = size/qf.getRows();
		if ( qf.getRows()*tp < size) {
			tp++;
		}
		qf.setTotalPages(tp);
		if(tp < qf.getPage()){
			qf.setPage(tp);
		}
		if(size <= qf.getRows() ){
			qf.setRows(size);
		}
	}
	
	public static String getRoleSearchQuery(QueryFilter qf ){
		StringBuilder query = new StringBuilder("select r " );
		query.append( roleSearchQuery(qf, ORDER_REQUIRED));
		return query.toString();
	}
	
	public static String getRoleSearchCountQuery(QueryFilter qf){
		StringBuilder query = new StringBuilder("select count(*) ");
		query.append( roleSearchQuery(qf, ORDER_NOT_REQUIRED));
		return query.toString();
	}
	
	private static String roleSearchQuery(QueryFilter qf, Boolean order ){
		StringBuilder query = new StringBuilder(" from Role r " );
		
		if(qf.getFilterString() != null && qf.getFilterString().length() > 0) {
			JqgridFilter jqgridFilter = JqgridObjectMapper.map(qf.getFilterString());
			
			if( jqgridFilter != null && jqgridFilter.getRules() != null && jqgridFilter.getRules().size() > 0){
				query.append("where  (");
			
				String separator = " ";
				for(FilterRule rule: jqgridFilter.getRules()){
					String field = rule.getField();
					String rdata = rule.getData().replaceAll("'", "");
					
					if(CONTAINS.equals(rule.getOp())){
						query.append(separator + "upper("+field + ") like upper('%" + rdata + "%') ");
					} else if(EQUAL.equals(rule.getOp())){
						query.append(separator + field + " = '" + rdata + "' ");
					} else if(NOTEQUAL.equals(rule.getOp())){
						query.append(separator + field + " != '" + rdata + "' ");
					} else if(BEGINSWITH.equals(rule.getOp())){
						query.append(separator +  "upper("+field + ") like upper('" + rdata + "%') ");
					} else if(ENDSWITH.equals(rule.getOp())){
						query.append(separator +  "upper("+field + " like upper('%" + rdata + "') ");
					}
					
					if( LOGICAL_AND.equals(jqgridFilter.getGroupOp()) ){
						separator = " and ";
					} else {
						separator = " or ";
					}
				}
				query.append(" ) ");
			}
		}
		
		String sidx = qf.getSidx();
		if(sidx != null && !"".equals(sidx) && order){
			query.append(" order by r." + sidx + " " + qf.getSord());
		}
		return query.toString();
	}

}
