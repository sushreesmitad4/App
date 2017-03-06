package com.tarang.ewallet.util;

import org.apache.log4j.Logger;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.util.CriteriaOperations;
import com.tarang.ewallet.util.FilterRule;
import com.tarang.ewallet.util.GlobalLitterals;
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
public class AdminUserCriteriaUtil implements CriteriaOperations, GlobalLitterals {
	
	private static final Logger LOGGER = Logger.getLogger(AdminUserCriteriaUtil.class);
	
	public AdminUserCriteriaUtil() {
	}
	
	public static void updateCount(int size, QueryFilter qf){

		qf.setTotal(size);

		int tp = size/qf.getRows();
		if ( qf.getRows()*tp < size){
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
	
	public static String getAdminUserSearchQuery(QueryFilter qf, String fromDate, String toDate, String name, String emailId, Boolean active ){
		StringBuilder query = new StringBuilder("select new com.tarang.ewallet.dto.UserDto(admin.id, pn.firstName, pn.lastName, rn.name," +
				"au.emailId, au.active, au.status, au.blocked, au.creationDate) " );
		query.append( adminUserSearchQuery(qf, fromDate, toDate, name, emailId, active, ORDER_REQUIRED));
		return query.toString();
	}
	
	public static String getAdminUserSearchCountQuery(QueryFilter qf, String fromDate, String toDate, String name, String emailId, Boolean active){
		StringBuilder query = new StringBuilder("select count(*) ");
		query.append( adminUserSearchQuery(qf, fromDate, toDate, name, emailId, active, ORDER_NOT_REQUIRED));
		return query.toString();
	}
	
	private static String adminUserSearchQuery(QueryFilter qf, String fromDate, String toDate, String name, String emailId, Boolean active, Boolean order  ){
		
		StringBuilder query = new StringBuilder(
				" from AdminUser admin, Authentication au, PersonName pn,Role rn where (admin.authenticationId=au.id and admin.nameId=pn.id and admin.roleId=rn.id)" );
		try{
			query = papulateSearchWithDate(query, fromDate, toDate, active);
			
			if(name != null && !"".equals(name) ){
				query.append( "and ( upper(pn.firstName || ' ' || pn.lastName) like upper('%" + name + "%'))");
			}
			if(emailId != null && !"".equals(emailId) ){
				query.append( "and ( upper(au.emailId) like upper('%" + emailId + "%'))");
			}
			
			if(qf.getFilterString() != null && qf.getFilterString().length() > 0) {
				JqgridFilter jqgridFilter = JqgridObjectMapper.map(qf.getFilterString());
				
				query.append(" " );
			
				if( jqgridFilter != null && jqgridFilter.getRules() != null && jqgridFilter.getRules().size() > 0){
					query.append(" and (");
				
					String separator = " ";
					for(FilterRule rule: jqgridFilter.getRules()){
						String field = getFieldName(rule.getField());
						int type = getFieldType(rule.getField());
						
						String rdata = rule.getData().replaceAll("'", "");
						if(type == NONE_TYPE){
							// ignore
							continue;
						}
						if(type == NUMBER_TYPE){ // number
							if(CONTAINS.equals(rule.getOp())){
								// ignore
								continue;
							} else if(EQUAL.equals(rule.getOp())){
								query.append(separator + field + " = " + rdata + " ");
							} else if(NOTEQUAL.equals(rule.getOp())){
								query.append(separator + field + " != " + rdata + " ");
							} else if(BEGINSWITH.equals(rule.getOp())){
								//ignore
								continue;
							} else if(ENDSWITH.equals(rule.getOp())){
								// ignore
								continue;
							}
						} else if(type == BOOLEAN_TYPE){ 
							// boolean
							Boolean data = false;
							if( "true".equalsIgnoreCase(rdata) || "T".equalsIgnoreCase(rdata) ) {
								data = true;
							}
							if(CONTAINS.equals(rule.getOp())){
								// ignore
								continue;
							} else if(EQUAL.equals(rule.getOp())){
								query.append(separator + field + " = " + data + " ");
							} else if(NOTEQUAL.equals(rule.getOp())){
								query.append(separator + field + " != " + data + " ");
							} else if(BEGINSWITH.equals(rule.getOp())){
								//ignore
								continue;
							} else if(ENDSWITH.equals(rule.getOp())){
								// ignore
								continue;
							}
						} else { 
							// String
							if(CONTAINS.equals(rule.getOp())){
								query.append(separator +"upper("+ field + ") like upper('%" + rdata + "%') ");
							} else if(EQUAL.equals(rule.getOp())){
								query.append(separator + field + " = '" + rdata + "' ");
							} else if(NOTEQUAL.equals(rule.getOp())){
								query.append(separator + field + " != '" + rdata + "' ");
							} else if(BEGINSWITH.equals(rule.getOp())){
								query.append(separator +"upper("+ field + ") like upper('" + rdata + "%') ");
							} else if(ENDSWITH.equals(rule.getOp())){
								query.append(separator +"upper("+ field + ") like upper('%" + rdata + "') ");
							}
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
			String sidx = getFieldName(qf.getSidx());
			if(sidx != null && !"".equals(sidx) && order){
				query.append(" order by " + sidx + " " + qf.getSord());
			}
			} catch(Exception ex){
				LOGGER.error(ex.getMessage() , ex);
			}
		
		return query.toString();
	}

	private static String getFieldName(String fieldName){
		String field = " ";
		if( CommonConstrain.FIRST_NAME.equalsIgnoreCase(fieldName) ){
			field = "pn.firstName";
		} else if( "id".equalsIgnoreCase(fieldName) ){
			field = "au.id";
		} else if( CommonConstrain.EMAIL_ID.equalsIgnoreCase(fieldName) ){
			field = "au.emailId";
		} else if( CommonConstrain.ROLE_NAME.equalsIgnoreCase(fieldName) ){
			field = "rn.name";
		} else if( CommonConstrain.STATUS.equalsIgnoreCase(fieldName) ){
			field = "au.status";
		} else if( CommonConstrain.CREATION_DATE.equalsIgnoreCase(fieldName) ){
			field = "au.creationDate";
		} else if( CommonConstrain.BLOCKED.equalsIgnoreCase(fieldName) ){
			field = "au.blocked";
		} else if( CommonConstrain.ACTIVE.equalsIgnoreCase(fieldName) ){
			field = "au.active";
		}
		// ignore remaining
		return field;
	}
	
	public static int getFieldType(String filedName){
		int type = NONE_TYPE;
		if( CommonConstrain.FIRST_NAME.equalsIgnoreCase(filedName) || CommonConstrain.EMAIL_ID.equalsIgnoreCase(filedName) 
				|| CommonConstrain.ROLE_NAME.equalsIgnoreCase(filedName) ){
			type = STRING_TYPE;
		} else if( CommonConstrain.STATUS.equalsIgnoreCase(filedName) ){
			type = NUMBER_TYPE;
		} else if( CommonConstrain.CREATION_DATE.equalsIgnoreCase(filedName) ){
			type = DATE_TYPE;
		} else if( CommonConstrain.BLOCKED.equalsIgnoreCase(filedName) ||  CommonConstrain.ACTIVE.equalsIgnoreCase(filedName) ) { 
			type = BOOLEAN_TYPE;
		}
		return type;
	}
	
	private static StringBuilder papulateSearchWithDate(StringBuilder query, String fromDate, String toDate, Boolean active){
		String cama = "','";
		String cama1 = "'),'";
		
		if(getValidateSearchInputNotNullActive(fromDate, toDate, active)){
			query.append( "and ( to_date(to_char(au.creationDate,'" + DB_DATE_FORMAT + cama1 + DB_DATE_FORMAT + "') >= to_date('" 
					+ fromDate + cama + DB_DATE_FORMAT + "') and to_date(to_char(au.creationDate,'" + DB_DATE_FORMAT + cama1
					+ DB_DATE_FORMAT + "') <= to_date('" + toDate + cama + DB_DATE_FORMAT + "') and au.active = " + active + ") ");
		} else if( validateSearchInput(fromDate, toDate) && active == null){
			query.append( "and ( to_date(to_char(au.creationDate,'" + DB_DATE_FORMAT + cama1 + DB_DATE_FORMAT + "') >= to_date('"
					+ fromDate + cama + DB_DATE_FORMAT + "') and to_date(to_char(au.creationDate,'" + DB_DATE_FORMAT + cama1 
					+ DB_DATE_FORMAT + "') <= to_date('" + toDate + cama + DB_DATE_FORMAT + "') )" );
		} else if(validateSearchInputWithEmptyDate(fromDate, toDate, active)){
			query.append( "and ( au.active = " + active + ")");
		}
		return query;
	}
	
	private static Boolean getValidateSearchInputNotNullActive(String fromDate, String toDate, Boolean active){
		return validateSearchInput(fromDate, toDate) && active != null;
	}
	
	private static Boolean validateSearchInputWithEmptyDate(String fromDate, String toDate, Boolean active){
		return fromDate != null && toDate != null && "".equals(fromDate) && "".equals(toDate) && active != null;
	}
	
	private static Boolean validateSearchInput(String fromDate, String toDate){
		return fromDate != null && toDate != null && !"".equals(fromDate) && !"".equals(toDate);
	}
}
