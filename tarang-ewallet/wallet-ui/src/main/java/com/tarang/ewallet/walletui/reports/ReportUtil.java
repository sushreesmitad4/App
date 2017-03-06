/**
 * 
 */
package com.tarang.ewallet.walletui.reports;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.reports.util.ReportConstants;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.walletui.util.UIUtil;


/**
 * @author  : prasadj
 * @date    : Jan 18, 2013
 * @time    : 2:27:52 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@SuppressWarnings({"rawtypes"}) 
public class ReportUtil implements ReportFieldConstants, ReportHeaderConstants, ReportConstants, ReportColumnWidth {

	private static final Logger LOGGER = Logger.getLogger(ReportUtil.class);

	public static ReportData populateReportData(Long reportType, String title, List data, ApplicationContext context, Locale locale){
		
		ReportData rd = null;
		String[] headerNames = null;
		String[] headerFields = null;
		float[]  columnWidths = null;
		String dataType = CustomerReportModel.class.getSimpleName();
		
		if(reportType > 0){
			if( reportType.equals(CUSTOMER_MERCHANT_WISE_TRANSACTION) //2
					|| reportType.equals(CUSTOMER_RECEIVE_MONEY_TRANSACTION) //3
					|| reportType.equals(CUSTOMER_SEND_MONEY_TRANSACTION) //4
					|| reportType.equals(MERCHANT_RECEIVE_MONEY_TRANSACTION) //8
					|| reportType.equals(MERCHANT_SEND_MONEY_TRANSACTION)  //9
					|| reportType.equals(MERCHANT_CUSTOMER_WISE_TRANSACTION)){ //10
				
				headerNames = new String[]{
						context.getMessage(TRANSACTION_ID_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_SELF_CURRENCY_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_EMAILID_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_OTHERS_CURRENCYD_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_TRANSACTIONTYPE_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_STATUS_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_DATE_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_AMOUNT_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_DEDUCTIONS_CUSTOMER, null, locale)
					};
				headerFields = new String[] {TRANSACTION_ID, SELF_CURRENCY, EMAIL_ID, OTHERS_CURRENCY, TRANSACTION_TYPE, STATUS, 
						UPDATED_DATE, AMOUNT, DEDUCTIONS};
				columnWidths = new float[]{CW_TRANSACTION_ID, CW_SELF_CURRENCY, CW_EMAIL_ID, CW_OTHERS_CURRENCY, CW_TRANSACTION_TYPE, CW_STATUS, CW_UPDATED_DATE, CW_AMOUNT, CW_DEDUCTIONS};
			}
			if( reportType.equals(CUSTOMER_LAST_N_TRANSACTIONS) //1
					|| reportType.equals(MERCHANT_LAST_N_TRANSACTIONS)){//7
				
				headerNames = new String[]{
						context.getMessage(TRANSACTION_ID_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_SELF_CURRENCY_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_EMAILID_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_OTHERS_CURRENCYD_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_TRANSACTIONTYPE_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_STATUS_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_DATE_CUSTOMER, null, locale),
						context.getMessage(USER_WALLET_BALANCE_STATUS_CR_DR, null, locale),
						context.getMessage(REPORTS_USER_WALLET_BALANCE, null, locale),
						context.getMessage(TRANSACTION_AMOUNT_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_DEDUCTIONS_CUSTOMER, null, locale)
					};
				headerFields = new String[] {TRANSACTION_ID, SELF_CURRENCY, EMAIL_ID, OTHERS_CURRENCY, TRANSACTION_TYPE, STATUS, 
						UPDATED_DATE, BALANCE_STATUS, PAYEE_BALANCE, AMOUNT, DEDUCTIONS};
				columnWidths = new float[]{CW_TRANSACTION_ID, CW_SELF_CURRENCY, CW_EMAIL_ID, CW_OTHERS_CURRENCY, CW_TRANSACTION_TYPE, 
						CW_STATUS, CW_UPDATED_DATE, CW_BALANCE_STATUS, CW_PAYEE_AMOUNT, CW_AMOUNT, CW_DEDUCTIONS};
			} else if(reportType.equals(USER_LAST_N_TRANSACTIONS)){
				//12
				headerNames = new String[]{
						context.getMessage(TRANSACTION_ID_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_PAYER_EMAIL, null, locale), 
						context.getMessage(TRANSACTION_PAYER_TYPE, null, locale), 
						context.getMessage(TRANSACTION_PAYER_AMOUNT, null, locale), 
						context.getMessage(TRANSACTION_PAYER_CURRENCY, null, locale), 
						context.getMessage(TRANSACTION_PAYER_BALANCE, null, locale), 
						context.getMessage(TRANSACTION_PAYEE_EMAIL, null, locale), 
						context.getMessage(TRANSACTION_PAYEE_TYPE, null, locale), 
						context.getMessage(TRANSACTION_PAYEE_AMOUNT, null, locale), 
						context.getMessage(TRANSACTION_PAYEE_CURRENCY, null, locale), 
						context.getMessage(TRANSACTION_PAYEE_BALANCE, null, locale), 
						context.getMessage(TRANSACTION_TRANSACTIONTYPE_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_DATE_CUSTOMER, null, locale)
					};
				 
				headerFields = new String[] {TRANSACTION_ID, PAYER_EMAIL, PAYER_TYPE, PAYER_AMOUNT, PAYER_CURRENCY, PAYER_BALANCE, 
						PAYEE_EMAIL, PAYEE_TYPE, PAYEE_AMOUNT, PAYEE_CURRENCY, PAYEE_BALANCE, TRANSACTION_TYPE, UPDATED_DATE};
				columnWidths = new float[]{CW_TRANSACTION_ID, CW_PAYER_EMAIL, CW_PAYER_TYPE, CW_PAYER_AMOUNT, CW_PAYER_CURRENCY, CW_PAYER_BALANCE, CW_PAYEE_EMAIL, CW_PAYEE_TYPE, CW_PAYEE_AMOUNT, CW_PAYEE_CURRENCY, CW_PAYEE_BALANCE, CW_TRANSACTION_TYPE, CW_UPDATED_DATE};
			} else if(reportType.equals(USER_CUSTOMER_WISE_TRANSACTION) //13
					|| reportType.equals(USER_MERCHANT_WISE_TRANSACTION)){ // 14
				headerNames = new String[]{
						context.getMessage(TRANSACTION_ID_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_OTHERS_EMAIL, null, locale),
						context.getMessage(TRANSACTION_OTHERS_USERTYPE, null, locale), 
						context.getMessage(TRANSACTION_OTHERS_AMOUNT, null, locale),
						context.getMessage(TRANSACTION_OTHERS_CURRENCYD_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_USER_AMOUNT, null, locale),
						context.getMessage(TRANSACTION_SELF_CURRENCY_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_USER_BALANCE, null, locale), 
						context.getMessage(TRANSACTION_TRANSACTIONTYPE_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_DATE_CUSTOMER, null, locale)
					};
				headerFields = new String[] {TRANSACTION_ID, EMAIL_ID, USER_TYPE, AMOUNT_OTHER, OTHERS_CURRENCY, AMOUNT_SELF, 
						SELF_CURRENCY, SELF_BALANCE, TRANSACTION_TYPE, UPDATED_DATE};
				columnWidths = new float[]{CW_TRANSACTION_ID, CW_EMAIL_ID, CW_USER_TYPE, CW_AMOUNT_OTHER, CW_OTHERS_CURRENCY, CW_AMOUNT_SELF, CW_SELF_CURRENCY, CW_SELF_BALANCE, CW_TRANSACTION_TYPE, CW_UPDATED_DATE};
			} else if(reportType.equals(COMMISSION_SUMMARY)){ // 15
				headerNames = new String[]{
						context.getMessage(TRANSACTION_ID_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_EMAILID_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_USER_TYPE, null, locale), 
						context.getMessage(TRANSACTION_AMOUNT_CUSTOMER, null, locale),
						context.getMessage(REPORTS_COUNTRY, null, locale), 
						context.getMessage(REPORTS_CURRENCY, null, locale), 
						context.getMessage(REPORTS_TYPE, null, locale), 
						context.getMessage(TRANSACTION_PAY_DATE, null, locale), 
						context.getMessage(TRANSACTION_PARENTID, null, locale), 
						context.getMessage(TRANSACTION_REVERSAL, null, locale)
					};
				headerFields = new String[] {TRANSACTION_ID, EMAIL_ID, USER_TYPE, AMOUNT, COUNTRY, SELF_CURRENCY, FEE_TYPE, 
						PAY_DATE, PARENT_ID, REVERSAL};
				columnWidths = new float[]{CW_TRANSACTION_ID, CW_EMAIL_ID, CW_USER_TYPE, CW_AMOUNT, CW_COUNTRY, CW_SELF_CURRENCY, CW_FEE_TYPE, CW_PAY_DATE, CW_PARENT_ID, CW_REVERSAL};
			} else if(reportType.equals(USER_DORMANT_ACCOUNTS)){ // 21
				headerNames = new String[]{
						context.getMessage(TRANSACTION_EMAILID_CUSTOMER, null, locale), 
						context.getMessage(TRANSACTION_USER_TYPE, null, locale),
						context.getMessage(TRANSACTION_DATE_CUSTOMER, null, locale)
					};
				headerFields = new String[] {EMAIL_ID, USER_TYPE, UPDATED_DATE};
				columnWidths = new float[]{CW_EMAIL_ID, CW_USER_TYPE, CW_UPDATED_DATE};
			} else if(reportType.equals(USER_CUSTOMER_BALANCES) //16
					|| reportType.equals(USER_MERCHANT_BALANCES) //17
					|| reportType.equals(USER_CUSTOMER_HAVING_OVER_LIMIT)){ // 18
				headerNames = new String[]{
						context.getMessage(TRANSACTION_EMAILID_CUSTOMER, null, locale), 
						context.getMessage(REPORTS_COUNTRY, null, locale), 
						context.getMessage(REPORTS_HEADER_USD, null, locale), 
						context.getMessage(REPORTS_HEADER_JPY, null, locale),
						context.getMessage(REPORTS_HEADER_THB, null, locale)
					};
				headerFields = new String[] {EMAIL_ID, COUNTRY, USD_CURRENCY, YEN_CURRENCY, BAHT_CURRENCY};
				columnWidths = new float[]{CW_EMAIL_ID, CW_COUNTRY, CW_USD_CURRENCY, CW_YEN_CURRENCY, CW_BAHT_CURRENCY};
			} else if (reportType.equals(CUSTOMER_FAILED_TRANSACTION) // 6
				     || reportType.equals(MERCHANT_FAILED_TRANSACTION)//11
				     || reportType.equals(USER_CUSTOMER_FAILED_TRANSACTION)){//20
				
			    headerNames = new String[]{ 
			    		context.getMessage(REPORTS_HEADER_SENDMONEYID, null, locale), 
			    		context.getMessage(TRANSACTION_EMAILID_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_AMOUNT_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_SELF_CURRENCY_CUSTOMER, null, locale),
						context.getMessage(TRANSACTION_DATE_CUSTOMER, null, locale),  
			    	};
			    headerFields = new String[] {SEND_MONEY_ID, EMAIL_ID, AMOUNT, SELF_CURRENCY, UPDATED_DATE};
			    columnWidths = new float[] {CW_SEND_MONEY_ID, CW_EMAIL_ID, CW_AMOUNT, CW_SELF_CURRENCY, CW_UPDATED_DATE};
			} else if (reportType.equals(CUSTOMER_REQUEST_FAILS)//5
			     || reportType.equals(USER_CUSTOMER_REQUEST_FAILS)){//19
			    headerNames = new String[]{ 
			    		context.getMessage(REPORTS_HEADER_REQUESTEREMAIL, null, locale), 
			    		context.getMessage(REPORTS_HEADER_REQUESTEDAMOUNT, null, locale), 
			    		context.getMessage(REPORTS_HEADER_REQUESTEDCURRENCY, null, locale), 
			    		context.getMessage(REPORTS_HEADER_REQUESTEDDATE, null, locale),
			    		context.getMessage(REPORTS_HEADER_RESPONSEREMAIL, null, locale), 
			    		context.getMessage(TRANSACTION_STATUS_CUSTOMER, null, locale)
			    	};
			    headerFields = new String[] {PAYER_EMAIL, PAYER_AMOUNT, PAYER_CURRENCY, UPDATED_DATE, PAYEE_EMAIL, STATUS};
			    columnWidths = new float[] {CW_PAYER_EMAIL, CW_PAYER_AMOUNT, CW_PAYER_CURRENCY, CW_UPDATED_DATE, CW_PAYEE_EMAIL, CW_STATUS};
			} else if (reportType.equals(MERCHANT_COMMISSION_SUMMARY)){//25
			    headerNames = new String[]{ 
			    		context.getMessage(TRANSACTION_ID_CUSTOMER, null, locale), 
			    		context.getMessage(TRANSACTION_USER_AMOUNT, null, locale),			
			    		context.getMessage(REPORTS_COMMISSION_FEETAX, null, locale), 
			    		context.getMessage(REPORTS_CURRENCY, null, locale),
			    		context.getMessage(REPORTS_TYPE, null, locale), 
			    		context.getMessage(TRANSACTION_PAY_DATE, null, locale),
			    		context.getMessage(TRANSACTION_REVERSAL, null, locale)
			    	};
			    headerFields = new String[] {TRANSACTION_ID, AMOUNT, FEE_TAX, SELF_CURRENCY, IS_TYPE, PAY_DATE, REVERSAL};
			    columnWidths = new float[] {CW_TRANSACTION_ID, CW_AMOUNT, CW_FEE_TAX, CW_SELF_CURRENCY, CW_IS_TYPE, CW_PAY_DATE, CW_REVERSAL};
			} else if (reportType.equals(CUSTOMER_DISPUTED_TRANSACTION)){//27
				    headerNames = new String[]{ 
				    		context.getMessage(TRANSACTION_ID_CUSTOMER, null, locale), 
				    		context.getMessage(TRANSACTION_PAYEE_EMAIL, null, locale), 
				    		context.getMessage(TRANSACTION_DATE_CUSTOMER, null, locale), 
				    		context.getMessage(TRANSACTION_AMOUNT_CUSTOMER, null, locale), 
				    		context.getMessage(REPORTS_CURRENCY, null, locale), 
				    		context.getMessage(REPORTS_DISPUTE_REQUEST_AMOUNT, null, locale),
				    		context.getMessage(REPORTS_DISPUTE_APPROVE_AMOUNT, null, locale), 
				    		context.getMessage(REPORTS_DISPUTE_LOG_DATE, null, locale), 
				    		context.getMessage(REPORTS_DISPUTE_TYPE, null, locale),
				    		context.getMessage(REPORTS_DISPUTE_STATUS, null, locale)
				    	};
				    headerFields = new String[] {TRANSACTION_ID, PAYEE_EMAIL, TRANSACTION_DATE, TRANSACTION_AMOUNT, TRANSACTION_CURRENCY, 
				    		REQUEST_AMOUNT, APROVED_AMOUNT, DISPUTE_DATE, DISPUTE_TYPE, DISPUTE_STATUS};
				    columnWidths = new float[] {CW_TRANSACTION_ID, CW_PAYEE_EMAIL, CW_TRANSACTION_DATE, CW_TRANSACTION_AMOUNT, CW_TRANSACTION_CURRENCY, CW_REQUEST_AMOUNT, CW_APROVED_AMOUNT, CW_DISPUTE_DATE, CW_DISPUTE_TYPE, CW_DISPUTE_STATUS};
				} else if (reportType.equals(MERCHANT_DISPUTED_TRANSACTION)){//26
				    headerNames = new String[]{ 
				    		context.getMessage(TRANSACTION_ID_CUSTOMER, null, locale), 
				    		context.getMessage(TRANSACTION_PAYER_EMAIL, null, locale), 
				    		context.getMessage(TRANSACTION_DATE_CUSTOMER, null, locale), 
				    		context.getMessage(TRANSACTION_AMOUNT_CUSTOMER, null, locale), 
				    		context.getMessage(REPORTS_CURRENCY, null, locale), 
				    		context.getMessage(REPORTS_DISPUTE_REQUEST_AMOUNT, null, locale),
				    		context.getMessage(REPORTS_DISPUTE_APPROVE_AMOUNT, null, locale), 
				    		context.getMessage(REPORTS_DISPUTE_LOG_DATE, null, locale), 
				    		context.getMessage(REPORTS_DISPUTE_TYPE, null, locale),
				    		context.getMessage(REPORTS_DISPUTE_STATUS, null, locale)
				    	};
				    headerFields = new String[] {TRANSACTION_ID, PAYER_EMAIL, UPDATED_DATE, AMOUNT, SELF_CURRENCY, 
				    		PAYER_AMOUNT, PAYEE_AMOUNT, DISPUTE_DATE, DISPUTE_TYPE, DISPUTE_STATUS};
				    columnWidths = new float[] {CW_TRANSACTION_ID, CW_PAYER_EMAIL, CW_UPDATED_DATE, CW_AMOUNT, CW_SELF_CURRENCY, CW_PAYER_AMOUNT, CW_PAYEE_AMOUNT, CW_DISPUTE_DATE, CW_DISPUTE_TYPE, CW_DISPUTE_STATUS};
				} else if (reportType.equals(USER_DISPUTED_TRANSACTION)){//22
			    headerNames = new String[]{ 
			    		context.getMessage(TRANSACTION_ID_CUSTOMER, null, locale), 
			    		context.getMessage(TRANSACTION_PAYER_EMAIL, null, locale), 
			    		context.getMessage(TRANSACTION_PAYEE_EMAIL, null, locale), 
			    		context.getMessage(REPORTS_TRANSACTION_DATE, null, locale), 
			    		context.getMessage(TRANSACTION_AMOUNT_CUSTOMER, null, locale), 
			    		context.getMessage(REPORTS_CURRENCY, null, locale), 
			    		context.getMessage(REPORTS_DISPUTE_REQUEST_AMOUNT, null, locale),
			    		context.getMessage(REPORTS_DISPUTE_APPROVE_AMOUNT, null, locale), 
			    		context.getMessage(REPORTS_DISPUTE_LOG_DATE, null, locale), 
			    		context.getMessage(REPORTS_DISPUTE_TYPE, null, locale),
			    		context.getMessage(REPORTS_DISPUTE_STATUS, null, locale)
			    	};
			    headerFields = new String[] {TRANSACTION_ID, PAYER_EMAIL, PAYEE_EMAIL, UPDATED_DATE, AMOUNT, SELF_CURRENCY, 
			    		PAYER_AMOUNT, PAYEE_AMOUNT, DISPUTE_DATE, DISPUTE_TYPE, DISPUTE_STATUS};
			    columnWidths = new float[] {CW_TRANSACTION_ID, CW_PAYER_EMAIL, CW_PAYEE_EMAIL, CW_TRANSACTION_DATE, CW_AMOUNT, CW_SELF_CURRENCY, CW_PAYER_AMOUNT, CW_PAYEE_AMOUNT, CW_DISPUTE_DATE, CW_DISPUTE_TYPE, CW_DISPUTE_STATUS};
			} else if (reportType.equals(LIST_OF_ACCOUNT_NOT_USED_IN_X_TIME)){//23
			    headerNames = new String[]{ 
			    		context.getMessage(REPORTS_LAST_TRANSACTION_AUTHENTICATIONID, null, locale), 
			    		context.getMessage(REPORTS_PERSON_OR_ORGANIZATION_NAME, null, locale),  
			    		context.getMessage(REPORTS_COUNTRY, null, locale), 
			    		context.getMessage(TRANSACTION_USER_TYPE, null, locale), 
			    		context.getMessage(REPORTS_LAST_TRANSACTION_DATE, null, locale), 
			    		context.getMessage(REPORTS_LAST_TRANSACTION_CREATIONDATE, null, locale) 
			    	};
			    headerFields = new String[] {AUTH_ID, PERSON_OR_ORGANIZATION_NAME, COUNTRY, USER_TYPE, LAST_TRANSACTION_DATE, CREATION_DATE};
			    columnWidths = new float[] {CW_AUTH_ID, CW_PERSON_OR_ORGANIZATION_NAME, CW_COUNTRY, CW_USER_TYPE, CW_LAST_TRANSACTION_DATE, CW_CREATION_DATE};
			} else if (reportType.equals(FRAUDULENT_TRANSACTION)){//24
			    headerNames = new String[]{context.getMessage(TRANSACTION_EMAILID_CUSTOMER, null, locale), context.getMessage(REPORTS_PERSON_OR_ORGANIZATION_NAME, null, locale),  
			    		context.getMessage(REPORTS_COUNTRY, null, locale), context.getMessage(REPORTS_LAST_TRANSACTION_CREATIONDATE, null, locale), 
			    	};
			    headerFields = new String[] {EMAIL_ID, PERSON_OR_ORGANIZATION_NAME, COUNTRY, CREATION_DATE};
			    columnWidths = new float[] {CW_EMAIL_ID, CW_PERSON_OR_ORGANIZATION_NAME, CW_COUNTRY, CW_CREATION_DATE};
			}
			
			rd = new ReportData(headerNames, headerFields, data, title, columnWidths, dataType);
		}
		return rd;
	}

	public static String getFieldValue(Class clazz, Object data, String fName){
		String valueStr = "";
		try{
		    Field f = clazz.getDeclaredField(fName);
		    f.setAccessible(true);
		    Object value = f.get(data);
		    if(value != null){
		    	if(value instanceof Double){
		    		valueStr = UIUtil.getConvertedAmountInString((Double)value);
		    	} else if(value instanceof Date){
		    		valueStr = DateConvertion.dateTimeToString((Date)value);
		    	} else {
		    		valueStr = f.get(data).toString();
		    	}
		    } else {
		    	valueStr = "";
		    }
		    
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		return valueStr;
	}

	public static Class getFieldType(Class clazz, String fName){
		Class c = null;
		try{
		    Field f = clazz.getDeclaredField(fName);
		    c = f.getType();
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
	    return c;
	}

}