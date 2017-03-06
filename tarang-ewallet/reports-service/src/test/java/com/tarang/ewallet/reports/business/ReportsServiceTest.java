/**
 * 
 */
package com.tarang.ewallet.reports.business;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.reports.business.ReportsService;
import com.tarang.ewallet.util.DateConvertion;


/**
 * @author  : prasadj
 * @date    : Jan 18, 2013
 * @time    : 5:58:07 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@ContextConfiguration(locations={"classpath*:/wallet-applicationContext.xml",
		"classpath*:/wallettest-applicationContext.xml"})
@TransactionConfiguration(defaultRollback=false)
public class ReportsServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	
    @Autowired
    private ReportsService reportsService;

    @Test
    public void customerLastNTransactionsTest(){
    	List<CustomerReportModel> list = reportsService.customerLastNTransactions(5, 1L, 86L, "CR", "DR");
    	System.out.println("size of records: " + list.size());
    	/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }

    @Test
    public void merchantLastNTransactionsTest(){
    	List<CustomerReportModel> list = reportsService.merchantLastNTransactions(5, 1L, 305L,  "CR", "DR");
    	System.out.println("size of records: " + list.size());
    	/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }

    @Test
    public void userLastNTransactionsTest(){
    	List<CustomerReportModel> list = reportsService.userLastNTransactions(500, 1L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
		}*/
    }
    
    @Test
    public void customerSendMoneyTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("02/05/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.customerSendMoneyTransactions(5, 1L, fromDate, toDate, 305L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }

    @Test
    public void merchantSendMoneyTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("02/05/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.merchantSendMoneyTransactions(50, 1L, fromDate, toDate, 31L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			mmodel.displayCommonData();
		}*/
    }

    @Test
    public void merchantReceiveMoneyTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("02/05/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.merchantReceiveMoneyTransactions(50, 1L, fromDate, toDate, 31L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }

    @Test
    public void customerReceiveMoneyTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("02/05/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.customerReceiveMoneyTransactions(50, 1L, fromDate, toDate, 54L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }

    @Test
    public void customerMerchantWiseTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("02/05/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.customerMerchantWiseTransactions(50, 1L, fromDate, toDate, 54L, 31L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }

    @Test
    public void merchantCustomerWiseTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("02/05/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.merchantPersonWiseTransactions(50, 1L, fromDate, toDate, 31L, 54L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }

    @Test
    public void userCustomerWiseTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("08/01/2012");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("08/22/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.userCustomerWiseTransactions(500, 2L, fromDate, toDate, 40L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }

    @Test
    public void userMerchantWiseTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("08/01/2012");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("08/22/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.userMerchantWiseTransactions(500, 2L, fromDate, toDate, 22L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }
    
    @Test
    public void userCustomerBalancesTest(){
    	List<CustomerReportModel> list = reportsService.userCustomerBalances(3L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayBalance();
		}*/
    }
    
    @Test
    public void userMerchantBalancesTest(){
    	List<CustomerReportModel> list = reportsService.userMerchantBalances(2L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayBalance();
		}*/
    }
    
    @Test
    public void darmatAccountsTest(){
    	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("02/16/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.dormantAccounts(50, fromDate, toDate, 5);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }
    
    @Test
    public void userCustomerRequestFailsTest(){
    	Date fromDate = DateConvertion.stirngToDate("08/01/2012");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("08/22/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.userCustomerRequestFails(500, 1L, fromDate, toDate);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.display12();
		}*/
    }
    
    @Test
    public void customerRequestFailsTest(){
    	Date fromDate = DateConvertion.stirngToDate("08/01/2012");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("08/22/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.customerRequestFails(500, 1L, fromDate, toDate, 40L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.display12();
		}*/
    }
    
    @Test
    public void userCustomerFailedTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("08/01/2012");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("08/22/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.userCustomerFailedTransactions(500, fromDate, toDate);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }
    
    @Test
    public void merchantCustomerFailedTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("05/08/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("08/30/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.merchantCustomerFailedTransactions(50, fromDate, toDate, 115L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayCommonData();
		}*/
    }
    
    @Test
    public void customerFailedTransactionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("05/08/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("08/30/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.customerFailedTransactions(50, fromDate, toDate, 149L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.display12();
		}*/
    }
    
    @Test
    public void userCustomerHavingMoneyExceedsThresholdLimitTest(){
    	List<CustomerReportModel> list = reportsService.userCustomerHavingMoneyExceedsThresholdLimit(3L, 1L);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayBalance();
		}*/
    }
    
    @Test
    public void userCommissionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("08/01/2012");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("08/22/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.userCommissions(500, 2L, fromDate, toDate);
    	System.out.println("size of records: " + list.size());
		/*for(CustomerReportModel model: list){
			System.out.println("\n");
			model.displayBalance();
		}*/
    }
    
    @Test
    public void merchantCommissionsTest(){
    	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("02/27/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.merchantCommissions(50, 2L, 54L, fromDate, toDate);
    	System.out.println("size of records: " + list.size());
    }
    
    @Test
    public void userCustomerDisputesTest(){
    	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("02/16/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.userCustomerDisputes(50, 2L, fromDate, toDate, 54L, 54L, 2L);
    	System.out.println("size of records: " + list.size());
    }
    
    @Test
    public void userMerchantDisputesTest(){
    	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("02/16/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.userMerchantDisputes(50, 2L, fromDate, toDate, 54L, 54L, 2L);
    	System.out.println("size of records: " + list.size());
    }
    
    @Test
    public void userAdminDisputesTest(){
    	Date fromDate = DateConvertion.stirngToDate("08/01/2012");//MM/dd/YYYY;
    	Date toDate = DateConvertion.stirngToDate("08/22/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.userAdminDisputes(50, 2L, fromDate, toDate, 22L, 40L, 2L);
    	System.out.println("size of records: " + list.size());
    }
    
    @Test
    public void listOfUnusedAccountsTest(){
    	Date date = DateConvertion.stirngToDate("08/22/2013");//MM/dd/YYYY;
    	List<CustomerReportModel> list = reportsService.listOfUnusedAccounts(10, date);
    	System.out.println("size of records: " + list.size());
    }
    
    @Test
    public void listOfMerchantsThresholdsTest(){
    	List<CustomerReportModel> list = reportsService.listOfMerchantsThresholds(50, 1L);
    	System.out.println("size of records: " + list.size());
    }
    
}
