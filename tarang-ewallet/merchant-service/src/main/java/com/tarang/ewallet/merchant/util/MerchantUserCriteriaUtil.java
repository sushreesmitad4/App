package com.tarang.ewallet.merchant.util;

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
* @date    : Oct 19, 2012
* @time    : 4:33:31 PM
* @version : 1.0.0
* @comments: 
*
*/
public class MerchantUserCriteriaUtil implements CriteriaOperations, GlobalLitterals {
	
	private static final Logger LOGGER = Logger.getLogger(MerchantUserCriteriaUtil.class);
	
	public static void updateCount(int size, QueryFilter qf){

		qf.setTotal(size);

		int tp = size/qf.getRows();
		if ( qf.getRows()*tp < size) {
			tp++;
		}
		qf.setTotalPages(tp);
		if(tp < qf.getPage()) {
			qf.setPage(tp);
		}
		if(size <= qf.getRows() ){
			qf.setRows(size);
		}
	}
	
	public static String getMerchantSearchQuery(QueryFilter qf, String fromDate, String toDate, String name, String emailId, Long status ){
		StringBuilder query = new StringBuilder("select new com.tarang.ewallet.dto.UserDto(mr.id, bi.legalName, " +
				"au.emailId, au.active, au.status, au.blocked, au.creationDate) " );
		query.append( merchantSearchQuery(qf, fromDate, toDate, name, emailId, status, ORDER_REQUIRED));
		return query.toString();
	}
	
	public static String getMerchantSearchCountQuery(QueryFilter qf, String fromDate, String toDate, String name, String emailId, Long status){
		StringBuilder query = new StringBuilder("select count(*) ");
		query.append( merchantSearchQuery(qf, fromDate, toDate, name, emailId, status, ORDER_NOT_REQUIRED));
		return query.toString();
	}
	
	private static String merchantSearchQuery(QueryFilter qf, String fromDate, String toDate, String name, String emailId, Long status, Boolean order  ){
		
		String cama = "','";
		String cama1 = "'),'";
		
		StringBuilder query = new StringBuilder(
				" from Merchant mr, Authentication au, BusinessInformation bi where " 
						+ "(mr.authenticationId=au.id and mr.businessInformation=bi.id and au.emailVarification=true)" );
		try{
			if(fromDate != null && toDate != null && !"".equals(fromDate) && !"".equals(toDate) && status != 0){
				query.append( "and ( to_date(to_char(au.creationDate,'" + DB_DATE_FORMAT + cama1 + DB_DATE_FORMAT + "') >= to_date('" 
						+ fromDate + cama + DB_DATE_FORMAT + "') and to_date(to_char(au.creationDate,'" + DB_DATE_FORMAT + cama1 
						+ DB_DATE_FORMAT + "') <= to_date('" + toDate + cama + DB_DATE_FORMAT + "') and au.status = " + status + ") ");
			} else if(fromDate != null && toDate != null && !"".equals(fromDate) && !"".equals(toDate) && status == 0){
				query.append( "and ( to_date(to_char(au.creationDate,'" + DB_DATE_FORMAT + cama1 + DB_DATE_FORMAT + "') >= to_date('"
						+ fromDate + cama + DB_DATE_FORMAT + "') and to_date(to_char(au.creationDate,'" + DB_DATE_FORMAT + cama1
						+ DB_DATE_FORMAT + "') <= to_date('" + toDate + cama + DB_DATE_FORMAT + "') )" );
			} else if(fromDate != null && toDate != null && "".equals(fromDate) && "".equals(toDate) && status != 0){
				query.append( "and ( au.status = " + status + ")");
			}
			if(name != null && !"".equals(name) ){
				query.append( "and ( upper(bi.legalName) like upper('%" + name + "%'))");
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
						} else if(type == BOOLEAN_TYPE){ // boolean
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
						} else { // String
							if(CONTAINS.equals(rule.getOp())){
								query.append(separator + "upper("+field + ") like upper('%" + rdata + "%') ");
							} else if(EQUAL.equals(rule.getOp())){
								query.append(separator + field + " = '" + rdata + "' ");
							} else if(NOTEQUAL.equals(rule.getOp())){
								query.append(separator + field + " != '" + rdata + "' ");
							} else if(BEGINSWITH.equals(rule.getOp())){
								query.append(separator +  "upper("+field + ") like upper('" + rdata + "%') ");
							} else if(ENDSWITH.equals(rule.getOp())){
								query.append(separator +  "upper("+field + ") like upper('%" + rdata + "') ");
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
			LOGGER.error(ex.getMessage(), ex);
		}
		
		return query.toString();
	}

	private static String getFieldName(String fieldName){
		String field = " ";
		if( CommonConstrain.LEGAL_NAME.equalsIgnoreCase(fieldName) ){
			field = "bi.legalName";
		} else if( CommonConstrain.EMAIL_ID.equalsIgnoreCase(fieldName) ){
			field = "au.emailId";
		} else if( "id".equalsIgnoreCase(fieldName) ){
			field = "au.id";
		} else if( CommonConstrain.STATUS.equalsIgnoreCase(fieldName) ){
			field = "au.status";
		} else if(CommonConstrain.CREATION_DATE.equalsIgnoreCase(fieldName)){
			field = "au.creationDate";
		}
		// ignore remaining
		
		return field;
	}
	
	public static int getFieldType(String filedName){
		int type = NONE_TYPE;
		if( CommonConstrain.LEGAL_NAME.equalsIgnoreCase(filedName) || CommonConstrain.EMAIL_ID.equalsIgnoreCase(filedName) ){
			type = STRING_TYPE;
		} else if(CommonConstrain.STATUS.equalsIgnoreCase(filedName)){
			type = NUMBER_TYPE;
		} else if(CommonConstrain.CREATION_DATE.equalsIgnoreCase(filedName)){
			type = DATE_TYPE;
		}
		return type;

	}
}
