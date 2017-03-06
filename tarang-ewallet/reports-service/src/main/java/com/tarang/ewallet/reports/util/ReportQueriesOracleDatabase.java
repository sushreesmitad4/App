/**
 * 
 */
package com.tarang.ewallet.reports.util;

import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionStatus;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;


/**
 * @author  : kedarnathd
 * @date    : Aug 23, 2013
 * @time    : 8:30:37 AM
 * @version : 1.0.0
 * @comments: To hold report queries which for Oracle database. 
 *
 */
public interface ReportQueriesOracleDatabase extends WalletTransactionTypes, WalletTransactionStatus, UserStatusConstants{

	/*Mine Changes*/
	String CUSTOMER_LAST_N_TRANSACTIONS = "select TXNID transactionid, SELFCURRENCY selfcurrency, emailid emailid, " 
			+ "OTHERCURRENCY otherscurrency, TransactionType transactiontype, TransactionStatus status, " 
			+ "updatededdate updateddate, amount amount, FEETAX deductions,payeebalance, balancestatus " 
			+ "from (select * from(select * from (select TT.TXNID, coalesce(AA.emailid,(select email from nonregisterwallet where txnid in "
			+ "(select id from wallettransaction where id = TT.TXNID and typeofTransaction in (" + P_TO_NP + "," +  M_TO_NP + "))))emailid, TT.SELFCURRENCY, TT.OTHERCURRENCY, " 
			+ "coalesce((select name from feeoperationtype_locale where feeoperationtype = TT.TType and languageid = :_languageId ), " 
			+ "(select name from feeoperationtype where id = TT.TType))  TransactionType, "
			+ "coalesce((select name from transactionstatus_locale where transactionstatusid = TT.status and languageid = :_languageId ), " 
			+ "(select name from transactionstatus where id = TT.status))  TransactionStatus , "
			+ "TT.updatededdate, TT.amount, TT.FEETAX,TT.payeebalance, TT.balancestatus from (select T.id TXNID, (select code from currency " 
			+ "where id = T.payercurrency)  SELFCURRENCY, T.payee  pay, A.emailid  EMAIL, T.updatededdate, payeramount amount, " 
			+ "(select code from currency where id = T.payercurrency)  OTHERCURRENCY, (payerfee + payertax) FEETAX , "
			+ "T.status,T.typeoftransaction  TType,T.payerbalance  payeebalance, :_DR  balancestatus "
			+ "from wallettransaction T join authentication A on T.payer = A.id  where A.id = :_authenticationId "			
			+ "Union All select T.id TXNID, (select code from currency where id = T.payeecurrency)  SELFCURRENCY, " 
			+ "T.payer  pay, A.emailid  EMAIL, T.updatededdate, payeeamount  amount, (select code from currency " 
			+ "where id = T.payercurrency)  OTHERCURRENCY, (payeefee + payeetax) FEETAX, T.status, T.typeoftransaction  TType, "
			+ "T.payeebalance, :_CR  balancestatus from wallettransaction T join authentication A on T.payee = A.id where A.id = :_authenticationId   ) TT  "
			+ "left join authentication AA on TT.pay = AA.id ) TXN order by updatededdate desc) SS WHERE ROWNUM <= :_limit)XYZ order by updatededdate desc";
	
	/*Mine Changes*/
	String CUSTOMER_SEND_MONEY_TRANSACTIONS = "select TXNID transactionid, SELFCURRENCY selfcurrency, emailid emailid, "
			+ "OTHERCURRENCY otherscurrency, TransactionType transactiontype, TransactionStatus status, "
			+ "updatededdate updateddate, amount amount, FEETAX  deductions "
			+ "from(select * from (select * from (select TT.TXNID, TT.SELFCURRENCY, coalesce(AA.emailid,(select email from nonregisterwallet where txnid in "
			+ "(select id from wallettransaction where id = TT.TXNID and typeofTransaction in (" + P_TO_NP + "," +  M_TO_NP + "))))emailid, TT.OTHERCURRENCY, "
			+ "coalesce((select name from feeoperationtype_locale where feeoperationtype = TT.TType and languageid = :_languageId ), " 
			+ "(select name from feeoperationtype where id = TT.TType)) TransactionType, "
			+ "coalesce((select name from transactionstatus_locale where transactionstatusid = TT.status and languageid = :_languageId ), "
			+ "(select name from transactionstatus where id = TT.status)) TransactionStatus, updatededdate, TT.amount, "
			+ "TT.FEETAX from (select T.id TXNID, (select code from currency where id = T.payercurrency) SELFCURRENCY, T.payee pay, "
			+ "A.emailid EMAIL, T.updatededdate, payeramount amount, "
			+ "(select code from currency where id = T.payeecurrency) OTHERCURRENCY, (payerfee + payertax) FEETAX, T.status, "
			+ "T.typeoftransaction TType from wallettransaction T join authentication A on  T.payer = A.id  where  T.payer = :_payerId ) TT "
			+ "left join authentication AA on TT.pay = AA.id ) TA "
			+ "where updatededdate between :_fromDate and :_toDate) TXN order by updatededdate desc) SS  where rownum <= :_limit";
		
	/*Mine Changes*/	
	String CUSTOMER_MERCHANT_WISE_TRANSACTIONS = "select TXNID transactionid, SELFCURRENCY selfcurrency, emailid emailid, " 
			+ "OTHERCURRENCY otherscurrency, TransactionType transactiontype, TransactionStatus status, " 
			+ "updatededdate updateddate, amount amount, FEETAX deductions " 
			+ "from(select * from(select * from (select TT.TXNID, TT.SELFCURRENCY, AA.emailid, TT.OTHERCURRENCY, " 
			+ "coalesce((select name from feeoperationtype_locale where feeoperationtype = TT.TType and languageid = :_languageId ), "
			+ "(select name from feeoperationtype where id = TT.TType)) TransactionType, " 
			+ "coalesce((select name from transactionstatus_locale where transactionstatusid = TT.status and languageid = :_languageId ), "
			+ "(select name from transactionstatus where id = TT.status)) TransactionStatus, "
			+ "TT.updatededdate, TT.amount, TT.FEETAX from (select T.id TXNID, "
			+ "(select code from currency where id = T.payercurrency) SELFCURRENCY, T.payee pay, A.emailid EMAIL, "
			+ "T.updatededdate, payeramount amount, (select code from currency where id = T.payeecurrency) OTHERCURRENCY, "
			+ "(payerfee + payertax) FEETAX, T.status, T.typeoftransaction TType "
			+ "from wallettransaction T join authentication A on T.payer = A.id  where T.payer = :_customerId and T.payee = :_merchantId  union  all "
			+ "select T.id TXNID,(select code from currency where id = T.payeecurrency) SELFCURRENCY, T.payer pay, "
			+ "A.emailid EMAIL, T.updatededdate, payeeamount amount, (select code from currency where id = T.payercurrency) OTHERCURRENCY, "
			+ "(payeefee + payeetax) FEETAX, T.status, T.typeoftransaction TType from wallettransaction T join "
			+ "authentication A on T.payer = A.id  where T.payer = :_merchantId and T.payee = :_customerId ) TT "
			+ "left join authentication AA on TT.pay = AA.id ) TA "
			+ "where updatededdate between :_fromDate and :_toDate) TXN  order by updatededdate desc) SS  WHERE ROWNUM <= :_limit";
	
	/*Mine Changes*/
	String MERCHANT_CUSTOMER_WISE_TRANSACTIONS = "select TXNID transactionid, SELFCURRENCY selfcurrency, emailid emailid, " 
			+ "OTHERCURRENCY otherscurrency, TransactionType transactiontype, TransactionStatus status, " 
			+ "updatededdate updateddate, amount amount, FEETAX deductions " 
			+ "from(select * from(select * from (select TT.TXNID, TT.SELFCURRENCY, AA.emailid, TT.OTHERCURRENCY, " 
			+ "coalesce((select name from feeoperationtype_locale where feeoperationtype = TT.TType and languageid = :_languageId ), "
			+ "(select name from feeoperationtype where id = TT.TType)) TransactionType, " 
			+ "coalesce((select name from transactionstatus_locale where transactionstatusid = TT.status and languageid = :_languageId ), "
			+ "(select name from transactionstatus where id = TT.status)) TransactionStatus, "
			+ "TT.updatededdate, TT.amount, TT.FEETAX from (select T.id TXNID, "
			+ "(select code from currency where id = T.payercurrency) SELFCURRENCY, T.payee  pay, A.emailid  EMAIL, "
			+ "T.updatededdate, payeramount amount, (select code from currency where id = T.payeecurrency)  OTHERCURRENCY, "
			+ "(payerfee + payertax) FEETAX, T.status, T.typeoftransaction TType "
			+ "from wallettransaction T join authentication A on T.payer = A.id  where T.payee = :_customerId and T.payer = :_merchantId  union  all "
			+ "select T.id TXNID,(select code from currency where id = T.payeecurrency) SELFCURRENCY, T.payer pay, "
			+ "A.emailid EMAIL, T.updatededdate, payeeamount amount, (select code from currency where id = T.payercurrency) OTHERCURRENCY, "
			+ "(payeefee + payeetax) FEETAX, T.status, T.typeoftransaction TType from wallettransaction T join "
			+ "authentication A on T.payer = A.id  where T.payee = :_merchantId and T.payer = :_customerId ) TT "
			+ "left join authentication AA on TT.pay = AA.id ) TA "
			+ "where updatededdate between :_fromDate and :_toDate) TXN  order by updatededdate desc) SS  WHERE ROWNUM <= :_limit";
	
	/*Mine Changes*/
	String CUSTOMER_RECEIVE_MONEY_TRANSACTIONS = "select TXNID transactionid, SELFCURRENCY selfcurrency, emailid emailid, "
			+ "OTHERCURRENCY otherscurrency, TransactionType transactiontype, TransactionStatus status, "
			+ "updatededdate updateddate, amount amount, FEETAX deductions " 
			+ "from(select * from (select * from (select TT.TXNID, TT.SELFCURRENCY, AA.EMAILID, TT.OTHERCURRENCY, "
			+ "coalesce((select name from feeoperationtype_locale where feeoperationtype = TT.TType and languageid = :_languageId), "
			+ "(select name from feeoperationtype where id = TT.TType)) TransactionType, "
			+ "coalesce((select name from transactionstatus_locale where transactionstatusid = TT.status and languageid = :_languageId), "
			+ "(select name from transactionstatus where id = TT.status)) TransactionStatus, TT.updatededdate, TT.amount, "
			+ "TT.FEETAX from (select T.id TXNID,(select code from currency where id = T.payeecurrency) SELFCURRENCY, "
			+ "A.emailid EMAIL, T.updatededdate, payeeamount amount, "
			+ "(select code from currency where id = T.payercurrency) OTHERCURRENCY, T.payer pay, "
			+ "(payeefee + payeetax) FEETAX, T.status, T.typeoftransaction TType from wallettransaction T "
			+ "join authentication A on T.payee = A.id  where T.payee = :_payeeId) TT "
			+ "left join authentication AA on TT.pay = AA.id ) TA "
			+ "where updatededdate between :_fromDate and :_toDate ) TXN order by updatededdate desc) SS  WHERE ROWNUM <= :_limit ";
	
	//updated
	String USER_LAST_NTRANSACTIONS = "select id  transactionid, payeremail  payeremail, payertype  payertype, "
			+ "payeramount  payeramount, payercurrency  payercurrency, payerbalance  payerbalance, "
			+ "payeeemail  payeeemail, payeetype  payeetype, payeeamount  payeeamount, payeecurrency  payeecurrency, "
			+ "payeebalance  payeebalance, transactiontype  transactiontype, orderdate  updateddate  from " 
			+ "(select * from(select * from (SELECT AT.id, A1.emailid  payeremail, A1.usertype  payertype, AT.payeramount, "
			+ "C1.code  payercurrency, AT.payerbalance, coalesce(A2.emailid,(select email from nonregisterwallet where txnid in "
			+ "(select id from wallettransaction where id = AT.id and typeofTransaction in (" + P_TO_NP + "," +  M_TO_NP + "))))  payeeemail, "
			+ "A2.usertype  payeetype, AT.payeeamount, c2.code  payeecurrency, AT.payeebalance, coalesce(FL.name,F.name)  Transactiontype, "
			+ "AT.updatededdate orderdate FROM wallettransaction AT " 
			+ "left join authentication A1 on A1.id = AT.payer left join authentication A2 on A2.id = AT.payee "
			+ "left join currency C1 on C1.id = AT.payercurrency "
			+ "left join currency C2 on C2.id = AT.payeecurrency "
			+ "left join feeoperationtype_locale FL on FL.feeoperationtype = AT.typeoftransaction and FL.languageid = :_languageId "
			+ "left join feeoperationtype F on F.id = AT.typeoftransaction )TT "
			+ "order by orderdate desc )TXN WHERE ROWNUM <= :_limit)XYZ order by id desc";
	
	//updated
	String USER_MERCHANT_WISE_TRANSACTIONS = "select TXNID  transactionid, emailid  emailid, usertype  usertype, "
			+ "otheramount  amountother, OTHERCURRENCY  otherscurrency, selfamount  amountself, SELFCURRENCY  selfcurrency, " 
			+ "balance  selfbalance, TransactionType  transactiontype, updatededdate  updateddate " 
			+ "from(select * from(select * from (select TT.TXNID, TT.SELFCURRENCY, AA.emailid, TT.OTHERCURRENCY, " 
			+ "TT.usertype, coalesce(FL.name, F.name)  TransactionType, TT.updatededdate, TT.selfamount, TT.otheramount, TT.balance from " 
	        + "(select T.id TXNID, (select code from currency where id = T.payercurrency)  SELFCURRENCY, case when (T.payee =null or T.payee=0) "
	        + "then  t.payer else T.payee end  pay, A.emailid  EMAIL, A.usertype, T.updatededdate, payeramount  Selfamount, "
	        + "case when (T.payee =null or T.payee=0) then  payeramount else payeeamount end  otheramount, (select code from currency where id = "
	        + "case when (T.payee =null or T.payee=0) then  T.payercurrency else T.payeecurrency end )  OTHERCURRENCY, T.typeoftransaction  TType,  "
	        + "T.payerbalance  balance from wallettransaction T join authentication A on  A.id  = case when (T.payee =null or T.payee=0) then  t.payer "
	        + "else T.payee end where T.payer = :_merchantId union all select T.id TXNID, (select code from currency where id = T.payeecurrency)  " 
	        + "SELFCURRENCY, case when (T.payer=null or T.payer=0) then  t.payee else T.payer end  pay, A.emailid  EMAIL, A.usertype, T.updatededdate, "
	        + "payeeamount  selfamount, case when (T.payer=null or T.payer=0) then  payeeamount else payeramount end otheramount, " 
	        + "(select code from currency where id = case when (T.payer=null or T.payer=0) then T.payeecurrency else T.payercurrency end)  OTHERCURRENCY, " 
	        + "T.typeoftransaction  TType, T.payeebalance  balance from wallettransaction T join authentication A on  A.id = case when "
	        + "(T.payer=null or T.payer=0) then t.payee else T.payer end where T.payee = :_merchantId) TT left join authentication AA "
	        + "on TT.pay = AA.id left join feeoperationtype_locale FL on FL.feeoperationtype = TT.TType and FL.languageid = :_languageId "
	        + "left join feeoperationtype F on F.id = TT.TType ) TA where updatededdate between :_fromDate and :_toDate "
	        + ") TXN order by updatededdate desc) SS WHERE ROWNUM <= :_limit ";
	
	//updated
	String USER_CUSTOMER_WISE_TRANSACTIONS = "select TXNID  transactionid, emailid  emailid, usertype  usertype, "
			+ "otheramount  amountother, OTHERCURRENCY  otherscurrency, selfamount  amountself, SELFCURRENCY  selfcurrency, " 
			+ "balance  selfbalance, TransactionType  transactiontype, updatededdate  updateddate " 
			+ "from(select * from(select * from (select TT.TXNID, TT.SELFCURRENCY, AA.emailid, TT.OTHERCURRENCY, " 
			+ "TT.usertype, coalesce(FL.name, F.name)  TransactionType, TT.updatededdate, TT.selfamount, TT.otheramount, TT.balance from " 
	        + "(select T.id TXNID, (select code from currency where id = T.payercurrency)  SELFCURRENCY, case when (T.payee =null or T.payee=0) "
	        + "then  t.payer else T.payee end  pay, A.emailid  EMAIL, A.usertype, T.updatededdate, payeramount  Selfamount, "
	        + "case when (T.payee =null or T.payee=0) then  payeramount else payeeamount end  otheramount, (select code from currency where id = "
	        + "case when (T.payee =null or T.payee=0) then  T.payercurrency else T.payeecurrency end )  OTHERCURRENCY, T.typeoftransaction  TType,  "
	        + "T.payerbalance  balance from wallettransaction T join authentication A on  A.id  = case when (T.payee =null or T.payee=0) then  t.payer "
	        + "else T.payee end where T.payer = :_customerId union all select T.id TXNID, (select code from currency where id = T.payeecurrency)  " 
	        + "SELFCURRENCY, case when (T.payer=null or T.payer=0) then  t.payee else T.payer end  pay, A.emailid  EMAIL, A.usertype, T.updatededdate, "
	        + "payeeamount  selfamount, case when (T.payer=null or T.payer=0) then  payeeamount else payeramount end otheramount, " 
	        + "(select code from currency where id = case when (T.payer=null or T.payer=0) then T.payeecurrency else T.payercurrency end)  OTHERCURRENCY, " 
	        + "T.typeoftransaction  TType, T.payeebalance  balance from wallettransaction T join authentication A on  A.id = case when "
	        + "(T.payer=null or T.payer=0) then t.payee else T.payer end where T.payee = :_customerId) TT left join authentication AA "
	        + "on TT.pay = AA.id left join feeoperationtype_locale FL on FL.feeoperationtype = TT.TType and FL.languageid = :_languageId "
	        + "left join feeoperationtype F on F.id = TT.TType ) TA where updatededdate between :_fromDate and :_toDate "
	        + ") TXN order by updatededdate desc) SS WHERE ROWNUM <= :_limit ";

	/*Mine Changes*/
	String MERCHANT_LAST_N_TRANSACTIONS = "select TXNID transactionid, SELFCURRENCY selfcurrency, emailid emailid, " 
		+ "OTHERCURRENCY otherscurrency, TransactionType transactiontype, TransactionStatus status, " 
		+ "updatededdate updateddate, amount amount, FEETAX deductions,payeebalance,balancestatus " 
		+ "from (select * from(select * from (select TT.TXNID, coalesce(AA.emailid,(select email from nonregisterwallet where txnid in "
		+ "(select id from wallettransaction where id = TT.TXNID and typeofTransaction in (" + P_TO_NP + "," +  M_TO_NP + "))))emailid, TT.SELFCURRENCY, TT.OTHERCURRENCY, " 
		+ "coalesce((select name from feeoperationtype_locale where feeoperationtype = TT.TType and languageid = :_languageId ), " 
		+ "(select name from feeoperationtype where id = TT.TType)) TransactionType, "
		+ "coalesce((select name from transactionstatus_locale where transactionstatusid = TT.status and languageid = :_languageId ), " 
		+ "(select name from transactionstatus where id = TT.status)) TransactionStatus , "
		+ "TT.updatededdate, TT.amount, TT.FEETAX,TT.payeebalance,TT.balancestatus from (select T.id TXNID, (select code from currency " 
		+ "where id = T.payercurrency) SELFCURRENCY, T.payee pay, A.emailid EMAIL, T.updatededdate, payeramount amount, " 
		+ "(select code from currency where id = T.payercurrency) OTHERCURRENCY, (payerfee + payertax) FEETAX , "
		+ "T.status,T.typeoftransaction TType,T.payerbalance payeebalance, :_DR balancestatus "
		+ "from wallettransaction T join authentication A on T.payer = A.id  where A.id = :_authenticationId "			
		+ "Union All select T.id TXNID, (select code from currency where id = T.payeecurrency) SELFCURRENCY, " 
		+ "T.payer pay, A.emailid EMAIL, T.updatededdate, payeeamount amount, (select code from currency " 
		+ "where id = T.payercurrency) OTHERCURRENCY, (payeefee + payeetax) FEETAX, T.status, T.typeoftransaction TType, "
		+ "T.payeebalance, :_CR balancestatus from wallettransaction T join authentication A on T.payee = A.id where A.id = :_authenticationId   ) TT  "
		+ "left join authentication AA on TT.pay = AA.id ) TXN order by updatededdate desc) SS WHERE ROWNUM <= :_limit)XYZ order by updatededdate desc";
	
	/*Mine Changes*/		
	String MERCHANT_RECEIVE_MONEY_TRANSACTIONS = "select TXNID transactionid, SELFCURRENCY selfcurrency, emailid emailid, "
		+ "OTHERCURRENCY otherscurrency, TransactionType transactiontype, TransactionStatus status, "
		+ "updatededdate updateddate, amount amount, FEETAX deductions " 
		+ "from(select * from (select * from (select TT.TXNID, TT.SELFCURRENCY, AA.EMAILID, TT.OTHERCURRENCY, "
		+ "coalesce((select name from feeoperationtype_locale where feeoperationtype = TT.TType and languageid = :_languageId), "
		+ "(select name from feeoperationtype where id = TT.TType)) TransactionType, "
		+ "coalesce((select name from transactionstatus_locale where transactionstatusid = TT.status and languageid = :_languageId), "
		+ "(select name from transactionstatus where id = TT.status)) TransactionStatus, TT.updatededdate, TT.amount, "
		+ "TT.FEETAX from (select T.id TXNID,(select code from currency where id = T.payeecurrency) SELFCURRENCY, "
		+ "A.emailid EMAIL, T.updatededdate, payeeamount amount, "
		+ "(select code from currency where id = T.payercurrency) OTHERCURRENCY, T.payer pay, "
		+ "(payeefee + payeetax) FEETAX, T.status, T.typeoftransaction TType from wallettransaction T "
		+ "join authentication A on T.payee = A.id  where T.payee = :_payeeId) TT "
		+ "left join authentication AA on TT.pay = AA.id ) TA "
		+ "where updatededdate between :_fromDate and :_toDate ) TXN order by updatededdate desc) SS  WHERE ROWNUM <= :_limit ";

	/*Mine Changes*/
	String MERCHANT_SEND_MONEY_TRANSACTIONS = "select TXNID transactionid, SELFCURRENCY selfcurrency, emailid emailid, "
		+ "OTHERCURRENCY otherscurrency, TransactionType transactiontype, TransactionStatus status, "
		+ "updatededdate updateddate, amount amount, FEETAX  deductions "
		+ "from(select * from (select * from (select TT.TXNID, TT.SELFCURRENCY, coalesce(AA.emailid,(select email from nonregisterwallet where txnid in "
		+ "(select id from wallettransaction where id = TT.TXNID and typeofTransaction in (" + P_TO_NP + "," +  M_TO_NP + "))))emailid, TT.OTHERCURRENCY, "
		+ "coalesce((select name from feeoperationtype_locale where feeoperationtype = TT.TType and languageid = :_languageId ), " 
		+ "(select name from feeoperationtype where id = TT.TType)) TransactionType, "
		+ "coalesce((select name from transactionstatus_locale where transactionstatusid = TT.status and languageid = :_languageId ), "
		+ "(select name from transactionstatus where id = TT.status)) TransactionStatus, updatededdate, TT.amount, "
		+ "TT.FEETAX from (select T.id TXNID, (select code from currency where id = T.payercurrency) SELFCURRENCY, T.payee pay, "
		+ "A.emailid EMAIL, T.updatededdate, payeramount amount, "
		+ "(select code from currency where id = T.payeecurrency) OTHERCURRENCY, (payerfee + payertax) FEETAX, T.status, "
		+ "T.typeoftransaction TType from wallettransaction T join authentication A on  T.payer = A.id  where  T.payer = :_payerId ) TT "
		+ "left join authentication AA on TT.pay = AA.id ) TA "
		+ "where updatededdate between :_fromDate and :_toDate) TXN order by updatededdate desc) SS  WHERE ROWNUM <= :_limit";
	
	//udpated
	String DARMAT_ACCOUNTS = "select emailid, usertype, lasttransactiondate as updateddate from(select emailid, "
			+ "usertype, lasttransactiondate as lasttransactiondate from(select * from ( select AC.emailid, AC.usertype, "
			+ "latestDate as lasttransactiondate from (select max(updateddate) " 
			+ "latestDate, id from (select AT.updatededdate updateddate, A.id from wallettransaction AT "
			+ "join authentication A on A.id = AT.payer or A.id = AT.payee) group by id )TT join authentication AC on AC.id = TT.id ) "
			+ "TXN where lasttransactiondate between :_fromDate and :_toDate) SS order by lasttransactiondate desc) MAINTXN WHERE ROWNUM <= :_limit";
	
	//updated
	String USER_COMMISSIONS = "select TXNID  transactionid, email  emailid, usertype, amount, country, currency  selfcurrency, "
			+ "Type  feeType, paydate  paydate, parentid, reversal from (select * from( select  UC.ID TXNID, UC.emailid email, UC.usertype, UC.amount, "
			+ "UC.amount1, coalesce(CL.name,C.name) country, CU.code currency, " 
			+ "(case when UC.type = 1 then 'Sender' when UC.type = 2 then 'Receiver' else '' end)  "
			+ "Type, UC.paydate, UC.parentid, UC.reversal from " 
			+ "( select M.id, M.type, M.id2, M.pay, A.emailid, A.usertype, M.amount, M.amount1, "
			+ " M.currency, M.paydate, M.parentid, M.reversal, M.country from " 
			+ "(select AF.id,AF.type,AT.id ID2,AF.currency,AF.amount, "
			+ "AT.payeramount amount1, AT.payer pay, AF.paydate, AF.parentid, AF.reversal, AF.country from WalletFee AF " 
			+ "join wallettransaction AT on AT.id = AF.transactionid where AF.type = 1) M "
			+ "join authentication A on A.id = M.pay union all select M.id, M.type, M.id2, "
			+ "M.pay, A.emailid, A.usertype, M.amount, M.amount1, M.currency, M.paydate, M.parentid, M.reversal, M.country  from "
			+ "(select AF.id, AF.type, AT.id ID2, AF.currency, AF.amount, AT.payeeamount amount1, AT.payee pay, AF.paydate, " 
			+ "AF.parentid, AF.reversal, AF.country from WalletFee AF join wallettransaction AT on AT.id = AF.transactionid where AF.type = 2) M "
			+ "join authentication A on A.id = M.pay ) UC left join currency_locale CUL on CUL.currencyid = UC.currency  " 
			+ "and CUL.languageid = :_languageId left join currency CU on CU.id = UC.currency left join country_locale CL " 
			+ "on CL.countryid = UC.country  and CL.languageid = :_languageId left join country C on "
            + "C.id = UC.country ) TXN where paydate between :_fromDate and :_toDate  order by paydate desc) WHERE ROWNUM <= :_limit";
	
	//updated
	String USER_CUSTOMER_BALANCES = "SELECT AUTH_TT.EMAILID AS emailid, CON_TT.name AS country, Dollor as usdcurrency, Yen as yencurrency, "
			+ " Baht as bahtcurrency FROM (SELECT authid, SUM(USERWAL_TT.USD) AS Dollor, SUM(USERWAL_TT.JPN) AS Yen, SUM(USERWAL_TT.THI) "
			+ " AS Baht FROM( SELECT authid, CASE WHEN CURRENCY = 1 THEN AMOUNT ELSE 0 END AS USD, CASE WHEN CURRENCY = 2 " 
			+ " THEN AMOUNT ELSE 0 END AS  JPN, CASE WHEN CURRENCY = 3 THEN AMOUNT ELSE 0 END  AS THI FROM userwallet) USERWAL_TT " 
			+ " GROUP BY authid) USERWALLET_TT JOIN authentication AUTH_TT ON USERWALLET_TT.authid = AUTH_TT.id JOIN ( SELECT * FROM "
			+ " (SELECT M_TT.authenticationid,BUSS_TT.addressid FROM merchant M_TT JOIN businessinformation  BUSS_TT ON "
			+ " M_TT.businessinformationid = BUSS_TT.id) MERC_DET UNION SELECT CUS_TT.authenticationid, CUS_TT.addressid "
			+ " FROM customer CUS_TT ) USERDATA_TT ON USERWALLET_TT.authid = USERDATA_TT.authenticationid JOIN address ADDRESS_TT "
			+ " ON USERDATA_TT.addressid = ADDRESS_TT.ID JOIN ( SELECT countryid ,name from country_locale where languageid= :_languageId "
            + " UNION SELECT id as countryid,name  from country WHERE NOT EXISTS ( SELECT * FROM country_locale c1 WHERE  c1.languageid= :_languageId )) "
			+ " CON_TT ON CON_TT.countryid=ADDRESS_TT.country WHERE AUTH_TT.USERTYPE='C' ";
	
	//updated
	String USER_MERCHANT_BALANCES = "SELECT AUTH_TT.EMAILID AS emailid, CON_TT.name AS country, Dollor as usdcurrency, Yen as yencurrency, "
			+ " Baht as bahtcurrency FROM (SELECT authid, SUM(USERWAL_TT.USD) AS Dollor, SUM(USERWAL_TT.JPN) AS Yen, SUM(USERWAL_TT.THI) "
			+ " AS Baht FROM( SELECT authid, CASE WHEN CURRENCY = 1 THEN AMOUNT ELSE 0 END AS USD, CASE WHEN CURRENCY = 2 " 
			+ " THEN AMOUNT ELSE 0 END AS  JPN, CASE WHEN CURRENCY = 3 THEN AMOUNT ELSE 0 END  AS THI FROM userwallet) USERWAL_TT " 
			+ " GROUP BY authid) USERWALLET_TT JOIN authentication AUTH_TT ON USERWALLET_TT.authid = AUTH_TT.id JOIN ( SELECT * FROM "
			+ " (SELECT M_TT.authenticationid,BUSS_TT.addressid FROM merchant M_TT JOIN businessinformation  BUSS_TT ON "
			+ " M_TT.businessinformationid = BUSS_TT.id) MERC_DET UNION SELECT CUS_TT.authenticationid, CUS_TT.addressid "
			+ " FROM customer CUS_TT ) USERDATA_TT ON USERWALLET_TT.authid = USERDATA_TT.authenticationid JOIN address ADDRESS_TT "
			+ " ON USERDATA_TT.addressid = ADDRESS_TT.ID JOIN ( SELECT countryid ,name from country_locale where languageid= :_languageId "
            + " UNION SELECT id as countryid,name  from country WHERE NOT EXISTS ( SELECT * FROM country_locale c1 WHERE  c1.languageid= :_languageId )) "
			+ " CON_TT ON CON_TT.countryid=ADDRESS_TT.country WHERE AUTH_TT.USERTYPE='M' ";
	
	//updated
	String USER_CUSTOMER_REQUEST_FAILS = "SELECT requesteremail payeremail,requestedamount payeramount,requestedcurrency payercurrency, " 
            + "creationdate updateddate,emailid payeeemail,TransactionStatus status FROM (SELECT * FROM (SELECT  REQ_TT.id, AUTH_TT.emailid requesteremail, "
			+ "REQ_TT.amount requestedamount, CURRENCYLOCAL_TT.code requestedcurrency, REQ_TT.requestdate creationdate , TRXSTATUS_TT.NAME TransactionStatus, "
            + "SAUTH_TT.emailid FROM requestmoney REQ_TT JOIN authentication AUTH_TT ON REQ_TT.requesterid = AUTH_TT.id JOIN currency CURRENCYLOCAL_TT "
			+ "ON REQ_TT.currencyid=CURRENCYLOCAL_TT.id JOIN (SELECT transactionstatusid,name from transactionstatus_locale " 
			+ "where languageid = :_languageId UNION " 
            + "SELECT id transactionstatusid,name  from transactionstatus WHERE NOT EXISTS ( SELECT * FROM transactionstatus_locale c1 " 
            + "WHERE  c1.languageid= :_languageId )) TRXSTATUS_TT ON TRXSTATUS_TT.transactionstatusid = REQ_TT.status JOIN authentication SAUTH_TT ON "
            + "SAUTH_TT.id=REQ_TT.responserid WHERE (TRXSTATUS_TT.transactionstatusid in ( " + CANCEL + "," + REJECT + "," + EXPIRED + ")) "  
            + "AND REQ_TT.requestdate between :_fromDate and :_toDate ) TAB1 order by creationdate) MAINTXN WHERE ROWNUM <= :_limit ";
	
	/*Mine Changes*/
	String CUSTOMER_REQUEST_FAILS = "SELECT requesteremail payeremail,requestedamount payeramount,requestedcurrency payercurrency, " 
            + "creationdate updateddate,emailid payeeemail,TransactionStatus status FROM (SELECT * FROM (SELECT  REQ_TT.id, AUTH_TT.emailid requesteremail, "
			+ "REQ_TT.amount requestedamount, CURRENCYLOCAL_TT.code requestedcurrency, REQ_TT.requestdate creationdate , TRXSTATUS_TT.NAME TransactionStatus, "
            + "SAUTH_TT.emailid FROM requestmoney REQ_TT JOIN authentication AUTH_TT ON REQ_TT.requesterid = AUTH_TT.id JOIN currency CURRENCYLOCAL_TT "
			+ "ON REQ_TT.currencyid=CURRENCYLOCAL_TT.id JOIN (SELECT transactionstatusid,name from transactionstatus_locale " 
			+ "where languageid = :_languageId UNION " 
            + "SELECT id transactionstatusid,name  from transactionstatus WHERE NOT EXISTS ( SELECT * FROM transactionstatus_locale c1 " 
            + "WHERE  c1.languageid= :_languageId )) TRXSTATUS_TT ON TRXSTATUS_TT.transactionstatusid = REQ_TT.status JOIN authentication SAUTH_TT ON "
            + "SAUTH_TT.id=REQ_TT.responserid WHERE AUTH_TT.id = :_customerId AND (TRXSTATUS_TT.transactionstatusid in ( '" + CANCEL + "','" + REJECT + "','" + EXPIRED + "')) "  
            + "AND REQ_TT.requestdate between :_fromDate and :_toDate ) TAB1 order by creationdate)MAINTXN WHERE ROWNUM <= :_limit"; 
	
	//updated
	String USER_CUSTOMER_FAILED_TRANSACTIONS = "SELECT SendDbId as sendmoneyid, senderemail as emailid, amount as amount,CURRENCY " 
			+ " as selfcurrency, creationdate as updateddate FROM (SELECT * FROM (SELECT  SEND_TT.id SendDbId, AUTH_TT.emailid senderemail, "  
            + " SEND_TT.amount,CURRENCYLOCAL_TT.code CURRENCY, SEND_TT.requestdate creationdate, TRXSTATUS_TT.NAME TransactionStatus " 
            + " FROM sendmoney SEND_TT JOIN authentication AUTH_TT ON SEND_TT.senderauthid = AUTH_TT.id " 
            + " JOIN currency CURRENCYLOCAL_TT ON SEND_TT.currency=CURRENCYLOCAL_TT.id " 
            + " JOIN sendmoneytxn SEND_TXN on SEND_TXN.sendmoneyid=SEND_TT.id " 
            + " JOIN transactionstatus TRXSTATUS_TT ON TRXSTATUS_TT.ID=SEND_TXN.transactionstatus "
            + " AND TRXSTATUS_TT.ID = " + FAILED + " AND (SEND_TT.requestdate between :_fromDate and :_toDate)) "
            + " TAB1  order by creationdate) MAINTXN WHERE ROWNUM <= :_limit ";
	
	/*Mine Changes*/
	String MERCHANT_FAILED_TRANSACTIONS = "SELECT SendDbId sendmoneyid, receiveremail emailid, amount amount, "
			+ " CURRENCY selfcurrency, creationdate updateddate FROM(SELECT * FROM ( SELECT SEND_TT.id SendDbId, AUTH_TT.emailid receiveremail, "
			+ " SEND_TT.amount,CURRENCYLOCAL_TT.code CURRENCY, SEND_TT.requestdate creationdate, TRXSTATUS_TT.NAME TransactionStatus "
			+ " FROM sendmoney SEND_TT JOIN authentication AUTH_TT ON SEND_TT.receiverauthid = AUTH_TT.id "
			+ " JOIN currency CURRENCYLOCAL_TT ON SEND_TT.currency=CURRENCYLOCAL_TT.id " 
			+ " JOIN sendmoneytxn SEND_TXN on SEND_TXN.sendmoneyid=SEND_TT.id "
			+ " JOIN transactionstatus TRXSTATUS_TT ON TRXSTATUS_TT.ID=SEND_TXN.transactionstatus WHERE SEND_TT.senderauthid = :_merchantId AND "
			+ " TRXSTATUS_TT.ID = " + FAILED + " AND (SEND_TT.requestdate between :_fromDate and :_toDate)) TAB1  order by creationdate) WHERE ROWNUM <= :_limit";
	
	/*Mine Changes*/
	String CUSTOMER_FAILED_TRANSACTIONS = "SELECT SendDbId sendmoneyid, receiveremail emailid, amount amount,CURRENCY selfcurrency, "
			+ " creationdate updateddate FROM(SELECT * FROM  ( SELECT  SEND_TT.id SendDbId, AUTH_TT.emailid receiveremail, "
			+ " SEND_TT.amount,CURRENCYLOCAL_TT.code CURRENCY, SEND_TT.requestdate creationdate ,TRXSTATUS_TT.NAME TransactionStatus FROM "
			+ " sendmoney SEND_TT JOIN authentication AUTH_TT ON SEND_TT.receiverauthid = AUTH_TT.id JOIN currency CURRENCYLOCAL_TT ON " 
			+ " SEND_TT.currency=CURRENCYLOCAL_TT.id JOIN sendmoneytxn SEND_TXN on SEND_TXN.sendmoneyid=SEND_TT.id JOIN "
            + " transactionstatus TRXSTATUS_TT ON TRXSTATUS_TT.ID=SEND_TXN.transactionstatus WHERE "
			+ " SEND_TT.senderauthid = :_customerId  AND TRXSTATUS_TT.ID = " + FAILED + " AND  (SEND_TT.requestdate between :_fromDate and :_toDate)) "
			+ " TAB1 order by creationdate) WHERE ROWNUM <=:_limit";
	
	//updated
	String USER_CUSTOMER_HAVING_EXCEEDS_THRESHOLD = "SELECT TAB1.EMAILID as emailid,TAB1.COUNTRY as country, TAB1.Dollor as usdcurrency," 
			+ " TAB1.Yen as yencurrency, TAB1.Baht as bahtcurrency FROM (SELECT USERWALLET_TT.authid, AUTH_TT.EMAILID,CON_TT.name AS COUNTRY, "
			+ " CON_TT.countryid AS COUNTRYID ,Dollor,Yen,Baht FROM (SELECT authid,SUM(USERWAL_TT.USD) AS Dollor,SUM(USERWAL_TT.JPN) AS Yen, "
			+ " SUM(USERWAL_TT.THI) AS Baht FROM( SELECT authid, CASE WHEN CURRENCY=1 THEN AMOUNT ELSE 0 END AS USD, CASE WHEN CURRENCY=2 "
			+ " THEN AMOUNT ELSE 0 END AS  JPN, CASE WHEN CURRENCY=3 THEN AMOUNT ELSE 0 END  AS THI FROM userwallet) USERWAL_TT "
			+ " GROUP BY USERWAL_TT.authid ) USERWALLET_TT JOIN authentication AUTH_TT ON USERWALLET_TT.authid=AUTH_TT.id JOIN "
			+ " (SELECT * FROM (SELECT M_TT.authenticationid,BUSS_TT.addressid FROM merchant M_TT JOIN businessinformation BUSS_TT ON " 
			+ " M_TT.businessinformationid=BUSS_TT.id) MERC_DET UNION SELECT CUS_TT.authenticationid, CUS_TT.addressid FROM customer CUS_TT ) "
			+ " USERDATA_TT ON USERWALLET_TT.authid=USERDATA_TT.authenticationid JOIN address ADDRESS_TT ON USERDATA_TT.addressid=ADDRESS_TT.ID JOIN " 
            + " ( SELECT  countryid,name FROM country_locale where countryid = :_countryId and languageid = :_languageId " 
            + "union SELECT id as countryid, name  from country WHERE NOT EXISTS "
            + " ( SELECT * FROM country_locale c1 WHERE  c1.languageid = :_languageId and c1.countryid = :_countryId ) and id = :_countryId ) "
            + " CON_TT ON CON_TT.countryid=ADDRESS_TT.country WHERE AUTH_TT.USERTYPE='C' ORDER BY EMAILID) TAB1 JOIN "
            + " (SELECT country,SUM(walle_TT.USD) AS Dollor, SUM(walle_TT.JPN) AS Yen ,SUM(walle_TT.THI) AS Baht FROM( SELECT country, CASE WHEN CURRENCY=1 "
            + " THEN MAXIMUMAMOUNT ELSE 0 END AS USD, CASE WHEN CURRENCY=2 THEN MAXIMUMAMOUNT ELSE 0 END AS  JPN, CASE WHEN CURRENCY=3 "
            + " THEN MAXIMUMAMOUNT ELSE 0 END  AS THI FROM walletthreshold) walle_TT GROUP BY country) walletthreshold_TT ON "
            + " (walletthreshold_TT.Dollor < TAB1.Dollor OR walletthreshold_TT.Yen < TAB1.Yen OR walletthreshold_TT.Baht < TAB1.Baht) "
            + " AND TAB1.COUNTRYID=walletthreshold_TT.country GROUP BY TAB1.EMAILID,TAB1.COUNTRY, TAB1.Dollor,TAB1.Yen, TAB1.Baht ORDER BY TAB1.EMAILID ";
	
	/*Mine Changes*/
	String MERCHANT_COMMISSIONS = "select transactionid,amount,feetax,currency selfcurrency,istype,paydate,reversal from(SELECT * FROM(select " 
			+ "TXN.transactionid ,TXN.amount, TXN.feetax,CUR.code currency,coalesce(FPL.name,FPE.name) istype, APF.paydate " 
			+ " paydate, APF.reversal from(select id transactionid,payeeamount amount,(payeefee + payeetax) " 
			+ "feetax, payeecurrency currency, 2 typeid from wallettransaction where payee = :_merchantId union select id transactionid, " 
			+ "payeramount amount, (payerfee + payertax) feetax,payercurrency currency, 1 typeid  from wallettransaction " 
			+ "where payer = :_merchantId)TXN join currency CUR on CUR.id = TXN.currency join WalletFee APF on " 
			+ "APF.transactionid = TXN.transactionid and APF.type = TXN.typeid left join FeePayingEntity_Locale FPL on " 
			+ "FPL.feepayingentityid = TXN.typeid and FPL.languageid = :_languageId left join FeePayingEntity FPE ON FPE.id = TXN.typeid ) " 
			+ " MAIN where paydate BETWEEN :_fromDate AND :_toDate ) WHERE ROWNUM <= :_limit";
	
	/*Updated*/
	String ADMIN_DISPUTES = "select SUB.transactionid, SUB.transactiondate as updateddate, SUB.transactionamount as amount, " 
			+ "SUB.transactioncurrency as selfcurrency, SUB.customeremail as payeremail,SUB.merchantemail as payeeemail, " 
			+ "SUB.requestamount as payeramount,SUB.approvedamount as payeeamount, SUB.disputedate as disputedate, coalesce(DTL.name, DT.name) " 
			+ " disputetype, coalesce(DSL.name,DS.name) disputestatus from (select DIS.transactionid,WALLET.transactiondate, " 
			+ "WALLET.transactionamount,CUR.code transactioncurrency,AU1.emailid customeremail, AU2.emailid merchantemail,DIS.requestamount, " 
			+ "DIS.approvedamount,DIS.creationdate disputedate,DIS.type disputetype,DIS.status from(select id txnid,payee,payer,payeramount " 
			+ "transactionamount,payercurrency,creationdate transactiondate from wallettransaction where ((nullif(:_payerId , 0) is null " 
			+ "and payer is not null) OR (nullif(:_payerId , 0) is not null and payer = :_payerId)) and ((nullif(:_payeeId , 0) is null " 
			+ "and payee is not null) OR (nullif(:_payeeId , 0) is not null and payee = :_payeeId)) AND typeoftransaction = 2) WALLET join dispute DIS on DIS.transactionid = WALLET.txnid " 
			+ "join currency CUR on CUR.id = WALLET.payercurrency " 
			+ " left join authentication AU1 on AU1.id = WALLET.payer left join authentication AU2 on AU2.id = WALLET.payee where (( " 
			+ "nullif(:_disputeType,0) is null and DIS.type is not null) OR (nullif(:_disputeType,0) is not null and DIS.type = :_disputeType ))) SUB left join disputetype DT " 
			+ "on DT.id = SUB.disputetype left join disputetype_locale DTL on DTL.disputetypeid = SUB.disputetype and DTL.languageid " 
			+ " = :_languageId left join disputestatus DS on DS.id = SUB.status left join disputestatus_locale DSL on DSL.disputestatuslocaleid " 
			+ "= SUB.status and  DSL.languageid = :_languageId where SUB.disputedate between :_fromDate and :_toDate AND ROWNUM <= :_limit";
	
	
	/*Updated*/
	String MERCHANT_DISPUTES = "select SUB.transactionid, SUB.transactiondate as updateddate,SUB.transactionamount as amount,SUB.transactioncurrency" 
			+ " as selfcurrency,SUB.customeremail as payeremail,SUB.requestamount as payeramount,SUB.approvedamount as payeeamount," 
			+ "SUB.disputedate as disputedate, coalesce(DTL.name, DT.name) disputetype,coalesce(DSL.name,DS.name) disputestatus" 
			+ " from (select DIS.transactionid,WALLET.transactiondate,WALLET.transactionamount,CUR.code transactioncurrency,AU1.emailid customeremail," 
			+ "DIS.requestamount,DIS.approvedamount,DIS.creationdate disputedate,DIS.type disputetype,DIS.status from(select id txnid,payee,payer, " 
			+ "payeramount transactionamount,payercurrency,creationdate transactiondate from wallettransaction where ((nullif(:_payerId , 0) is " 
			+ "null AND payer is not null) or (nullif(:_payerId , 0) is not null AND payer = :_payerId )) and payee = :_payeeId and typeoftransaction = 2) WALLET join dispute DIS on " 
			+ "DIS.transactionid = WALLET.txnid join currency CUR on CUR.id = WALLET.payercurrency left join authentication AU1 on AU1.id = WALLET.payer where " 
			+ "((nullif(:_disputeType,0) is null and DIS.type is not null) or (nullif(:_disputeType,0) is not null and DIS.type = :_disputeType ))) SUB left join disputetype DT on DT.id " 
			+ " = SUB.disputetype left join disputetype_locale DTL on DTL.disputetypeid = SUB.disputetype and DTL.languageid = :_languageId left join " 
			+ "disputestatus DS on DS.id = SUB.status left join disputestatus_locale DSL on DSL.disputestatuslocaleid = SUB.status and  DSL.languageid = " 
			+ " :_languageId where SUB.disputedate between :_fromDate and :_toDate and ROWNUM <= :_limit";
	
	/*It seems not used any where, need to find out and test it*/
	String USER_CUSTOMER_DISPUTES = "select SUB.transactionid, SUB.transactiondate as updateddate,SUB.transactionamount as amount,SUB.transactioncurrency" 
			+ " as selfcurrency,SUB.customeremail as payeremail,SUB.requestamount as payeramount,SUB.approvedamount as payeeamount," 
			+ "SUB.disputedate as disputedate, coalesce(DTL.name, DT.name) disputetype,coalesce(DSL.name,DS.name) disputestatus" 
			+ " from (select DIS.transactionid,WALLET.transactiondate,WALLET.transactionamount,CUR.code transactioncurrency,AU1.emailid customeremail," 
			+ "DIS.requestamount,DIS.approvedamount,DIS.creationdate disputedate,DIS.type disputetype,DIS.status from(select id txnid,payee,payer, " 
			+ "payeramount transactionamount,payercurrency,creationdate transactiondate from wallettransaction where ((nullif(:_payerId , 0) is " 
			+ "null AND payer is not null) or (nullif(:_payerId , 0) is not null AND payer = :_payerId )) and payee = :_payeeId and typeoftransaction = 2) WALLET join dispute DIS on " 
			+ "DIS.transactionid = WALLET.txnid join currency CUR on CUR.id = WALLET.payercurrency left join authentication AU1 on AU1.id = WALLET.payer where " 
			+ "((nullif(:_disputeType,0) is null and DIS.type is not null) or (nullif(:_disputeType,0) is not null and DIS.type = :_disputeType ))) SUB left join disputetype DT on DT.id " 
			+ " = SUB.disputetype left join disputetype_locale DTL on DTL.disputetypeid = SUB.disputetype and DTL.languageid = :_languageId left join " 
			+ "disputestatus DS on DS.id = SUB.status left join disputestatus_locale DSL on DSL.disputestatuslocaleid = SUB.status and  DSL.languageid = " 
			+ " :_languageId where SUB.disputedate between :_fromDate and :_toDate and ROWNUM <= :_limit";
	
	//udpated
	String LIST_OF_UNUSED_ACCOUNTS = "SELECT id authid,personororganizationName,country,usertype,lasttransactiondate,creationdate FROM ( SELECT * FROM ( SELECT * FROM " 
			+ "(SELECT TXN.personororganizationName,CON.name country, TXN.usertype, TXN.lasttransactiondate, TXN.id, TXN.creationdate creationdate FROM " 
			+ "address ADDR RIGHT JOIN (SELECT AUT.id,COALESCE(CUS.addressid,MER.addressid) addressid,AUT.usertype,WALLET.lasttransactiondate, " 
			+ "COALESCE(PERSON.perorgname,ORG.perorgname) personororganizationName,AUT.creationdate FROM authentication AUT LEFT JOIN ( SELECT " 
			+ "(PER.firstname || ' ' || PER.lastname) perorgname,CUS.authenticationid id FROM customer CUS JOIN personname PER ON PER.id = CUS.nameid )" 
			+ "PERSON ON PERSON.id = AUT.id LEFT JOIN (SELECT BI.legalname perorgname,ME.authenticationid id FROM merchant ME JOIN businessinformation BI " 
			+ "ON BI.id = ME.businessinformationid) ORG ON ORG.id = AUT.id LEFT JOIN (SELECT MAX(creationdate) lasttransactiondate,pay FROM ( SELECT "
			+ "id,payee AS pay, creationdate FROM wallettransaction UNION ALL SELECT id,payer AS pay, creationdate FROM wallettransaction) MAIN GROUP BY pay)WALLET "
			+ "ON WALLET.pay = AUT.id LEFT JOIN customer CUS ON CUS.authenticationid = AUT.id LEFT JOIN (SELECT ME.authenticationid,BI.addressid FROM merchant "
			+ "ME JOIN businessinformation BI ON BI.id = ME.businessinformationid) MER ON MER.authenticationid = AUT.id WHERE AUT.status <> " + DELETED + ") TXN ON "
			+ "TXN.addressid = ADDR.id JOIN country CON ON CON.id = ADDR.country) final_tab WHERE lasttransactiondate <= :_fromDate OR lasttransactiondate "
			+ "IS NULL) DETAIL ORDER BY creationdate DESC) MAINTXN WHERE ROWNUM <= :_limit";
	
	//updated
	String LIST_OF_MERCHANT_THRESHOLD = "SELECT * FROM(SELECT TXN.legalname as personororganizationname,TXN.emailid,coalesce(CON_1.name,CON_2.name) country, TXN.creationdate from "+
			" (select SUB.legalname, AUT.creationdate creationdate, SUB.addressid, AUT.emailid from authentication AUT join " +
			" (select MER.authenticationid,BI.legalname,BI.currency,BI.addressid from merchant MER join businessinformation BI on BI.id = MER.businessinformationid) SUB "+
			" ON AUT.id = SUB.authenticationid where AUT.usertype = 'M' and AUT.status <> " + DELETED + " and AUT.chargebackcheck = 1)TXN  "+
			" left join (select ADDR.id,CON.name from address ADDR join country CON on ADDR.country = CON.id)CON_1 on CON_1.id = TXN.addressid "+
			" left join (select ADDR.id,CON.name,CON.languageid from address ADDR join country_locale CON on ADDR.country = CON.countryid)CON_2 on CON_2.id = TXN.addressid "+ 
			" and CON_2.languageid = :_languageId order by TXN.creationdate desc)MAINTXN WHERE ROWNUM <= :_limit";
 
}