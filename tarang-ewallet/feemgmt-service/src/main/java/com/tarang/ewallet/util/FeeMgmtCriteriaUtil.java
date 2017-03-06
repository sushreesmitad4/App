package com.tarang.ewallet.util;

import org.apache.log4j.Logger;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.feemgmt.repository.impl.FeeMgmtRepositoryImpl;
import com.tarang.ewallet.util.CriteriaOperations;
import com.tarang.ewallet.util.FilterRule;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.JqgridFilter;
import com.tarang.ewallet.util.JqgridObjectMapper;
import com.tarang.ewallet.util.QueryFilter;


public class FeeMgmtCriteriaUtil implements CriteriaOperations, GlobalLitterals{

	private static final Logger LOGGER = Logger.getLogger(FeeMgmtRepositoryImpl.class);
	
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
	
	public static String getRoleSearchQuery(QueryFilter qf, final String servicetype ){
		StringBuilder query = new StringBuilder("select new com.tarang.ewallet.dto.FeeDto(f.id, f.userType, f.services, f.operationType, " 
				+ "f.country, f.currency, f.payingentity, f.feeType, f.fixCharSen, f.percentageSen, f.fixCharRec, f.percentageRec)" );
		query.append( roleSearchQuery(qf, ORDER_REQUIRED, servicetype));
		return query.toString();
	}
	
	public static String getRoleSearchCountQuery(QueryFilter qf, final String servicetype){
		StringBuilder query = new StringBuilder("select count(*) ");
		query.append( roleSearchQuery(qf, ORDER_NOT_REQUIRED, servicetype));
		return query.toString();
	}
	
	private static String roleSearchQuery(QueryFilter qf, Boolean order,final String servicetype ){
		
		StringBuilder query = new StringBuilder();
		query.append(" from Fee f where f.services=" + servicetype);
		try{
			if(qf.getFilterString() != null && qf.getFilterString().length() > 0) {
				JqgridFilter jqgridFilter = JqgridObjectMapper.map(qf.getFilterString());
				
				query.append(SPACE_STRING);
				
				if( jqgridFilter != null && jqgridFilter.getRules() != null && jqgridFilter.getRules().size() > 0){
					query.append("and  (");
				
					String separator = SPACE_STRING;
					for(FilterRule rule: jqgridFilter.getRules()){
						String field = getFieldName(rule.getField());
						int type =	getFieldType(rule.getField());
						String rdata = rule.getData().replaceAll("'", "");
						if(type == NONE_TYPE){
							// ignore
							continue;
						}
						if(type == NUMBER_TYPE){ 
							// number
							if(CONTAINS.equals(rule.getOp())){
								// ignore
								continue;
							} else if(EQUAL.equals(rule.getOp())){
								query.append(separator + field + " = " + rdata + SPACE_STRING);
							} else if(NOTEQUAL.equals(rule.getOp())){
								query.append(separator + field + " != " + rdata + SPACE_STRING);
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
								query.append(separator + field + " = " + data + SPACE_STRING);
							} else if(NOTEQUAL.equals(rule.getOp())){
								query.append(separator + field + " != " + data + SPACE_STRING);
							} else if(BEGINSWITH.equals(rule.getOp())){
								//ignore
								continue;
							} else if(ENDSWITH.equals(rule.getOp())){
								// ignore
								continue;
							}
						} else { // String
							if(CONTAINS.equals(rule.getOp())){
								query.append(separator + "upper(" + field + ") like upper('%" + rdata + "%') ");
							} else if(EQUAL.equals(rule.getOp())){
								query.append(separator + field + " = '" + rdata + "' ");
							} else if(NOTEQUAL.equals(rule.getOp())){
								query.append(separator + field + " != '" + rdata + "' ");
							} else if(BEGINSWITH.equals(rule.getOp())){
								query.append(separator + "upper(" + field + ") like upper('" + rdata + "%') ");
							} else if(ENDSWITH.equals(rule.getOp())){
								query.append(separator + "upper(" + field + ") like upper('%" + rdata + "') ");
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
				query.append(" order by " + sidx + SPACE_STRING + qf.getSord());
			}
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		return query.toString();
	}
	
	private static String getFieldName(String fieldName){
		String field = " ";
		if( "userTypeName".equalsIgnoreCase(fieldName) ){
			field = "f.userType";
		} else if( "id".equalsIgnoreCase(fieldName) ){
			field = "f.id";
		} else if( CommonConstrain.SERVICES.equalsIgnoreCase(fieldName) ){
			field = "f.services";
		} else if( CommonConstrain.OPERATION_TYPE.equalsIgnoreCase(fieldName)){
			field = "f.operationType";
		} else if( "countryName".equalsIgnoreCase(fieldName) ){
			field = "f.country";
		} else if( "currencyName".equalsIgnoreCase(fieldName) ){
			field = "f.currency";
		} else if( CommonConstrain.PAYING_ENTITY.equalsIgnoreCase(fieldName) ){
			field = "f.payingentity";
		} else if( CommonConstrain.FEE_TYPE.equalsIgnoreCase(fieldName) ){
			field = "f.feeType";
		} else if( CommonConstrain.FIXCHAR_SEN.equalsIgnoreCase(fieldName) ){
			field = "f.fixCharSen";
		} else if( CommonConstrain.PERCENTAGE_SEN.equalsIgnoreCase(fieldName) ){
			field = "f.percentageSen";
		} else if( CommonConstrain.FIXCHAR_REC.equalsIgnoreCase(fieldName) ){
			field = "f.fixCharRec";
		} else if( CommonConstrain.PERCENTAGE_REC.equalsIgnoreCase(fieldName) ){
			field = "f.percentageRec";
		}
		// ignore remaining
		
		return field;
	}
	
	public static int getFieldType(String filedName){
		int type = NONE_TYPE;
		if("userType".equalsIgnoreCase(filedName) || CommonConstrain.SERVICES.equalsIgnoreCase(filedName) 
				|| CommonConstrain.OPERATION_TYPE.equalsIgnoreCase(filedName) || "country".equalsIgnoreCase(filedName) 
				|| "currency".equalsIgnoreCase(filedName) || CommonConstrain.PAYING_ENTITY.equalsIgnoreCase(filedName)
				|| CommonConstrain.FEE_TYPE.equalsIgnoreCase(filedName) || CommonConstrain.FIXCHAR_SEN.equalsIgnoreCase(filedName)
				|| CommonConstrain.PERCENTAGE_SEN.equalsIgnoreCase(filedName) || CommonConstrain.FIXCHAR_REC.equalsIgnoreCase(filedName)
				|| CommonConstrain.PERCENTAGE_REC.equalsIgnoreCase(filedName) ){
			type = NUMBER_TYPE;
		}
		return type;
	}
}
