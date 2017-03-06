package com.tarang.ewallet.reports.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.util.GlobalLitterals;

/**
 * @author  : kedarnathd
 * @date    : Aug 23, 2013
 * @time    : 8:30:37 AM
 * @version : 1.0.0
 * @comments: To hold until methods for report queries which is developed using Oracle database. 
 *
 */
public class ReportUtilOracleDatabase {

	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;
	private static final int NINE = 9;
	private static final int TEN = 10;
	private static final int ELEVEN = 11;
	private static final int TWELVE = 12;
	
	/*Customer Report UTIL Methods*/
	/* Customer Failed Transactions*/
	public static List<CustomerReportModel> getGridReportCustomerFailedTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for(Object[] objects: list){
			customerReportModel = new CustomerReportModel();
			customerReportModel.setSendmoneyid((BigDecimal) objects[ZERO]);
			customerReportModel.setEmailid((String) objects[ONE]);
			customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[TWO]));
			customerReportModel.setSelfcurrency((String) objects[THREE]);
			customerReportModel.setUpdateddate((Date) objects[FOUR]);
			customerReportModelList.add(customerReportModel);
		  }
		return customerReportModelList;
	}
	
	/* Customer last N Transactions*/
	public static List<CustomerReportModel> getGridReportCustomerLastNTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for(Object[] objects: list){
			customerReportModel = new CustomerReportModel();	
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
           	customerReportModel.setSelfcurrency((String) objects[ONE]);
           	customerReportModel.setEmailid((String) objects[TWO]);
           	customerReportModel.setOtherscurrency((String) objects[THREE]);
           	customerReportModel.setTransactiontype((String) objects[FOUR]);
           	customerReportModel.setStatus((String) objects[FIVE]);
           	customerReportModel.setUpdateddate((Date) objects[SIX]);
           	customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[SEVEN]));
           	customerReportModel.setDeductions(getDoubleValue((BigDecimal) objects[EIGHT]));
           	customerReportModel.setPayeebalance(getDoubleValue((BigDecimal) objects[NINE]));
           	customerReportModel.setBalancestatus((String) objects[TEN]);
           	customerReportModelList.add(customerReportModel);
		  }
		return customerReportModelList;
	}
	
    /* Customer Merchant wise Transactions*/
	public static List<CustomerReportModel> getGridReportCustomerMerchantwiseTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for(Object[] objects: list){
			customerReportModel = new CustomerReportModel();	
	       	customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
	       	customerReportModel.setSelfcurrency((String) objects[ONE]);
	       	customerReportModel.setEmailid((String) objects[TWO]);
	       	customerReportModel.setOtherscurrency((String) objects[THREE]);
	       	customerReportModel.setTransactiontype((String) objects[FOUR]);
	       	customerReportModel.setStatus((String) objects[FIVE]);
	        customerReportModel.setUpdateddate((Date) objects[SIX]);
	        customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[SEVEN]));
	        customerReportModel.setDeductions(getDoubleValue((BigDecimal) objects[EIGHT]));
           	customerReportModelList.add(customerReportModel);
		  }
		return customerReportModelList;
	}
	
    /* Customer Receive money Transactions*/
	public static List<CustomerReportModel> getGridReportCustomerReceiveMoneyTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for(Object[] objects: list){
		customerReportModel = new CustomerReportModel();	
	       		customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
	       		customerReportModel.setSelfcurrency((String) objects[ONE]);
	       		customerReportModel.setEmailid((String) objects[TWO]);
	       		customerReportModel.setOtherscurrency((String) objects[THREE]);
	       		customerReportModel.setTransactiontype((String) objects[FOUR]);
	       		customerReportModel.setStatus((String) objects[FIVE]);
	           	customerReportModel.setUpdateddate((Date) objects[SIX]);
	           	customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[SEVEN]));
	           	customerReportModel.setDeductions(getDoubleValue((BigDecimal) objects[EIGHT]));
           	customerReportModelList.add(customerReportModel);
		  }
		return customerReportModelList;
	}
	
    /* Customer Request Failed*/
	public static List<CustomerReportModel> getGridReportCustomerRequestFailed(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for(Object[] objects: list){
			customerReportModel = new CustomerReportModel();	
			customerReportModel.setPayeremail((String) objects[ZERO]);
	       	customerReportModel.setPayeramount(getDoubleValue((BigDecimal)objects[ONE]));
	       	customerReportModel.setPayercurrency((String)objects[TWO]);
	       	customerReportModel.setUpdateddate((Date)objects[THREE]);
	       	customerReportModel.setPayeeemail((String)objects[FOUR]);
	       	customerReportModel.setStatus((String)objects[FIVE]);
           	customerReportModelList.add(customerReportModel);
		  }
		return customerReportModelList;
	}
	
    /* Customer Send money Transactions*/
	public static List<CustomerReportModel> getGridReportCustomerSendMoneyTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for(Object[] objects: list){
			customerReportModel = new CustomerReportModel();	
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
	        customerReportModel.setSelfcurrency((String) objects[ONE]);
	        customerReportModel.setEmailid((String) objects[TWO]);
	        customerReportModel.setOtherscurrency((String) objects[THREE]);
	        customerReportModel.setTransactiontype((String) objects[FOUR]);
	        customerReportModel.setStatus((String) objects[FIVE]);
	        customerReportModel.setUpdateddate((Date) objects[SIX]);
	        customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[SEVEN]));
	        customerReportModel.setDeductions(getDoubleValue((BigDecimal) objects[EIGHT]));
           	customerReportModelList.add(customerReportModel);
		  }
		return customerReportModelList;
	}
	
	/*Merchant Report Util Methods*/
	/*Merchant Commission Summary For Report*/
	public static List<CustomerReportModel> getReportGridUserMerchantCummissionSummary(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
       		customerReportModel.setAmount(getDoubleValue((BigDecimal)objects[ONE]));
       		customerReportModel.setFeetax(getDoubleValue((BigDecimal)objects[TWO]));
       		customerReportModel.setSelfcurrency((String)objects[THREE]);
       		customerReportModel.setIstype((String)objects[FOUR]);
       		customerReportModel.setPaydate((Date)objects[FIVE]);
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	/*Merchant Customer Wise Transaction For Report*/
	public static List<CustomerReportModel> getReportGridUserMerchantCustomerWiseTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
			customerReportModel.setSelfcurrency((String) objects[ONE]);
			customerReportModel.setEmailid((String) objects[TWO]);
			customerReportModel.setOtherscurrency((String) objects[THREE]);
			customerReportModel.setTransactiontype((String) objects[FOUR]);
			customerReportModel.setStatus((String) objects[FIVE]);
			customerReportModel.setUpdateddate((Date) objects[SIX]);
			customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[SEVEN]));
			customerReportModel.setDeductions(getDoubleValue((BigDecimal) objects[EIGHT]));
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	/*Merchant Disputed Transaction For Report*/
	public static List<CustomerReportModel> getReportGridUserMerchantDisputedTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
       		customerReportModel.setUpdateddate((Date) objects[ONE]);
       		customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[TWO]));
       		customerReportModel.setSelfcurrency((String) objects[THREE]);
       		customerReportModel.setPayeremail((String) objects[FOUR]);
       		customerReportModel.setPayeramount(getDoubleValue((BigDecimal) objects[FIVE]));
       		customerReportModel.setPayeeamount(getDoubleValue((BigDecimal) objects[SIX]));
       		customerReportModel.setDisputedate((Date) objects[SEVEN]);
       		customerReportModel.setDisputetype((String) objects[EIGHT]);
       		customerReportModel.setDisputestatus((String) objects[NINE]);
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}

	/*Merchant Failed Transaction For Report*/
	public static List<CustomerReportModel> getReportGridUserMerchantFailedTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setSendmoneyid((BigDecimal) objects[ZERO]);
	        customerReportModel.setEmailid((String) objects[ONE]);
	        customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[TWO]));
	        customerReportModel.setSelfcurrency((String) objects[THREE]);
	        customerReportModel.setUpdateddate((Date) objects[FOUR]);
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	/*Merchant Last N Transaction For Report*/
	public static List<CustomerReportModel> getReportGridUserMerchantLastNTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
       		customerReportModel.setSelfcurrency((String) objects[ONE]);
       		customerReportModel.setEmailid((String) objects[TWO]);
       		customerReportModel.setOtherscurrency((String) objects[THREE]);
       		customerReportModel.setTransactiontype((String) objects[FOUR]);
       		customerReportModel.setStatus((String) objects[FIVE]);
           	customerReportModel.setUpdateddate((Date) objects[SIX]);
           	customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[SEVEN]));
           	customerReportModel.setDeductions(getDoubleValue((BigDecimal) objects[EIGHT]));
           	customerReportModel.setPayeebalance(getDoubleValue((BigDecimal)objects[NINE]));
           	customerReportModel.setBalancestatus((String)objects[TEN]);	
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}

	/*Merchant Receive Money Transaction For Report*/
	public static List<CustomerReportModel> getReportGridUserMerchantReceiveMoneyTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
        	customerReportModel.setSelfcurrency((String) objects[ONE]);
        	customerReportModel.setEmailid((String) objects[TWO]);
        	customerReportModel.setOtherscurrency((String) objects[THREE]);
        	customerReportModel.setTransactiontype((String) objects[FOUR]);
        	customerReportModel.setStatus((String) objects[FIVE]);
            customerReportModel.setUpdateddate((Date) objects[SIX]);
            customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[SEVEN]));
            customerReportModel.setDeductions(getDoubleValue((BigDecimal) objects[EIGHT]));
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}

	/*Merchant Send Money Transaction For Report*/
	public static List<CustomerReportModel> getReportGridUserMerchantSendMoneyTxn(List<Object[]> list){
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
            customerReportModel.setSelfcurrency((String) objects[ONE]);
            customerReportModel.setEmailid((String) objects[TWO]);
            customerReportModel.setOtherscurrency((String) objects[THREE]);
            customerReportModel.setTransactiontype((String) objects[FOUR]);
            customerReportModel.setStatus((String) objects[FIVE]);
            customerReportModel.setUpdateddate((Date) objects[SIX]);
            customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[SEVEN]));
            customerReportModel.setDeductions(getDoubleValue((BigDecimal) objects[EIGHT]));
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	public static List<CustomerReportModel> getReportGridUserLastNTransactions(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
			customerReportModel.setPayeremail((String) objects[ONE]);
			customerReportModel.setPayertype((String) objects[TWO]);
			customerReportModel.setPayeramount(getDoubleValue((BigDecimal) objects[THREE]));
			customerReportModel.setPayercurrency((String) objects[FOUR]);
			customerReportModel.setPayerbalance(getDoubleValue((BigDecimal) objects[FIVE]));
			customerReportModel.setPayeeemail((String) objects[SIX]);
			customerReportModel.setPayeetype((String) objects[SEVEN]);
			customerReportModel.setPayeeamount(getDoubleValue((BigDecimal) objects[EIGHT]));
			customerReportModel.setPayeecurrency((String) objects[NINE]);
			customerReportModel.setPayeebalance(getDoubleValue((BigDecimal) objects[TEN]));
			customerReportModel.setTransactiontype((String) objects[ELEVEN]);
			customerReportModel.setUpdateddate((Date) objects[TWELVE]);
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	public static List<CustomerReportModel> getReportGridUserMerchantWiseTransactions(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
			customerReportModel.setEmailid((String) objects[ONE]);
			customerReportModel.setUsertype((String) objects[TWO]);
			customerReportModel.setAmountother(getDoubleValue((BigDecimal) objects[THREE]));
			customerReportModel.setOtherscurrency((String) objects[FOUR]);
			customerReportModel.setAmountself(getDoubleValue((BigDecimal) objects[FIVE]));
			customerReportModel.setSelfcurrency((String) objects[SIX]);
			customerReportModel.setSelfbalance(getDoubleValue((BigDecimal) objects[SEVEN]));
			customerReportModel.setTransactiontype((String) objects[EIGHT]);
			customerReportModel.setUpdateddate((Date) objects[NINE]);
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	public static List<CustomerReportModel> getReportGridUserCustomerWiseTransactions(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
			customerReportModel.setEmailid((String) objects[ONE]);
			customerReportModel.setUsertype((String) objects[TWO]);
			customerReportModel.setAmountother(getDoubleValue((BigDecimal) objects[THREE]));
			customerReportModel.setOtherscurrency((String) objects[FOUR]);
			customerReportModel.setAmountself(getDoubleValue((BigDecimal) objects[FIVE]));
			customerReportModel.setSelfcurrency((String) objects[SIX]);
			customerReportModel.setSelfbalance(getDoubleValue((BigDecimal) objects[SEVEN]));
			customerReportModel.setTransactiontype((String) objects[EIGHT]);
			customerReportModel.setUpdateddate((Date) objects[NINE]);
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	public static List<CustomerReportModel> getReportGridUserCommissions(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
			customerReportModel.setEmailid((String) objects[ONE]);
			customerReportModel.setUsertype((String) objects[TWO]);
			customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[THREE]));
			customerReportModel.setCountry((String) objects[FOUR]);
			customerReportModel.setSelfcurrency((String) objects[FIVE]);
			customerReportModel.setFeetype((String) objects[SIX]);
			customerReportModel.setPaydate((Date) objects[SEVEN]);
			customerReportModel.setParentid((BigDecimal) objects[EIGHT]);
			customerReportModel.setReversal(getBooleanValue((BigDecimal) objects[NINE]));
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	public static List<CustomerReportModel> getReportGridUserCustomerBalances(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setEmailid((String) objects[ZERO]);
			customerReportModel.setCountry((String) objects[ONE]);
			customerReportModel.setUsdcurrency(getDoubleValue((BigDecimal) objects[TWO]));
			customerReportModel.setYencurrency(getDoubleValue((BigDecimal) objects[THREE]));
			customerReportModel.setBahtcurrency(getDoubleValue((BigDecimal) objects[FOUR]));
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	public static List<CustomerReportModel> getReportGridUserMerchantBalances(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setEmailid((String) objects[ZERO]);
			customerReportModel.setCountry((String) objects[ONE]);
			customerReportModel.setUsdcurrency(getDoubleValue((BigDecimal) objects[TWO]));
			customerReportModel.setYencurrency(getDoubleValue((BigDecimal) objects[THREE]));
			customerReportModel.setBahtcurrency(getDoubleValue((BigDecimal) objects[FOUR]));
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	public static List<CustomerReportModel> getReportGridUserCustomerHavingExceedsThreshold(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setEmailid((String) objects[ZERO]);
			customerReportModel.setCountry((String) objects[ONE]);
			customerReportModel.setUsdcurrency(getDoubleValue((BigDecimal) objects[TWO]));
			customerReportModel.setYencurrency(getDoubleValue((BigDecimal) objects[THREE]));
			customerReportModel.setBahtcurrency(getDoubleValue((BigDecimal) objects[FOUR]));
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	public static List<CustomerReportModel> getReportGridUserCustomerRequestFails(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setPayeremail((String) objects[ZERO]);
			customerReportModel.setPayeramount(getDoubleValue((BigDecimal) objects[ONE]));
			customerReportModel.setPayercurrency((String) objects[TWO]);
			customerReportModel.setUpdateddate((Date) objects[THREE]);
			customerReportModel.setPayeeemail((String) objects[FOUR]);
			customerReportModel.setStatus((String) objects[FIVE]);
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	public static List<CustomerReportModel> getReportGridUserCustomerFailedTransactions(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setSendmoneyid((BigDecimal) objects[ZERO]);
			customerReportModel.setEmailid((String) objects[ONE]);
			customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[TWO]));
			customerReportModel.setSelfcurrency((String) objects[THREE]);
			customerReportModel.setUpdateddate((Date) objects[FOUR]);
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;
	}
	
	public static List<CustomerReportModel> getReportGridDarmatAccounts(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setEmailid((String) objects[ZERO]);
			customerReportModel.setUsertype((String) objects[ONE]);
			customerReportModel.setUpdateddate((Date) objects[TWO]);

			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;

	}
	 
	public static List<CustomerReportModel> getReportGridListOfUnusedAccounts(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
	
			customerReportModel = new CustomerReportModel();

			customerReportModel.setAuthid((BigDecimal) objects[ZERO]);
			customerReportModel.setPersonororganizationname((String) objects[ONE]);
			customerReportModel.setCountry((String) objects[TWO]);
			customerReportModel.setUsertype((String) objects[THREE]);
			customerReportModel.setLasttransactiondate((Date) objects[FOUR]);
			customerReportModel.setCreationdate((Date) objects[FIVE]);
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;

	}
	 
	public static List<CustomerReportModel> getReportGridListOfMerchantThreshold(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {		
			customerReportModel = new CustomerReportModel();
			customerReportModel.setPersonororganizationname((String) objects[ZERO]);
			customerReportModel.setEmailid((String) objects[ONE]);
			customerReportModel.setCountry((String) objects[TWO]);
			customerReportModel.setCreationdate((Date) objects[THREE]);
			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;

	}
	public static List<CustomerReportModel> getReportGridAdminDisputes(List<Object[]> list) {
		List<CustomerReportModel> customerReportModelList = new ArrayList<CustomerReportModel>();
		CustomerReportModel customerReportModel = null;
		for (Object[] objects : list) {
			customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionid((BigDecimal) objects[ZERO]);
			customerReportModel.setUpdateddate((Date) objects[ONE]);
			customerReportModel.setAmount(getDoubleValue((BigDecimal) objects[TWO]));
			customerReportModel.setSelfcurrency((String) objects[THREE]);
			customerReportModel.setPayeremail((String) objects[FOUR]);
			customerReportModel.setPayeeemail((String) objects[FIVE]);
			customerReportModel.setPayeramount(getDoubleValue((BigDecimal) objects[SIX]));
			customerReportModel.setPayeeamount(getDoubleValue((BigDecimal) objects[SEVEN]));
			customerReportModel.setDisputedate((Date) objects[EIGHT]);
			customerReportModel.setDisputetype((String) objects[NINE]);
			customerReportModel.setDisputestatus((String) objects[TEN]);

			customerReportModelList.add(customerReportModel);
		}
		return customerReportModelList;

	}
				
	private static Double getDoubleValue(BigDecimal value){
		if(null == value){
			return null;
		}
		return value.doubleValue();
	}
	
	private static Boolean getBooleanValue(BigDecimal value){
		return value != null && value.toString().equals(GlobalLitterals.TRUE_STR)? Boolean.TRUE : Boolean.FALSE;
	}
}
