/**
 * 
 */
package com.tarang.ewallet.dispute.util;

/**
 * @author vasanthar
 *
 */
public interface DisputeQueries {
	
	String DISPUTE_TRANSACTIONS_FOR_CUSTOMER_OLD = "SELECT transactionid,payeeemailid,transactionamount,currency,transactiondate,statusid,status," 
			+" disputelogdate,disputetypeid,disputetype,disputeid from(SELECT * FROM (SELECT SUB.transactionid,SUB.payeeemailid,SUB.transactionamount,SUB.currency,SUB.transactiondate, "
			+" SUB.status as statusid, coalesce(DSL.name,DS.name) status,SUB.disputelogdate,SUB.type as disputetypeid, coalesce(DTL.name,DT.name) "
			+" disputetype, SUB.disputeid from (select TXN.txnid as transactionid,AUT.emailid as payeeemailid,TXN.payeeamount transactionamount, "
			+" CUR.code currency, TXN.creationdate as transactiondate, DIS.type,DIS.creationdate disputelogdate,DIS.id as disputeid, DIS.status from "
			+" (select id txnid,payee,payer,payeeamount,payercurrency,creationdate from wallettransaction where (( nullif(:_payeeId, 0) "
			+" is null AND payer = :_payerId and typeoftransaction = :_typeOfTransaction) OR (nullif(:_payeeId, 0) is not null AND payee = :_payeeId and payer = :_payerId " 
       		+" and typeoftransaction = :_typeOfTransaction)))TXN join authentication AUT on AUT.id = TXN.payee join dispute DIS on "
			+" DIS.transactionid = TXN.txnid left join currency CUR on CUR.id = TXN.payercurrency )SUB left join disputetype DT on DT.id = SUB.type "
       		+" left join disputetype_locale DTL on DTL.disputetypeid = SUB.type and DTL.languageid = :_languageId left join disputestatus DS "
			+" on DS.id = SUB.status left join disputestatus_locale DSL on DSL.disputestatuslocaleid = SUB.status " 
       		+" and  DSL.languageid = :_languageId where ((nullif(:_disputeType, 0) is not null AND SUB.type = :_disputeType) "
			+" or (nullif(:_disputeType, 0) is null AND SUB.type is  not null)) and  SUB.disputelogdate BETWEEN :_fromDate AND :_toDate ) DETAIL order by transactiondate desc)MAINTXN WHERE ROWNUM <= :_limit";
	
	/*Mine Changes Tasted*/
	String	DISPUTE_TRANSACTIONS_FOR_CUSTOMER = "SELECT transactionid,txnamount,txncurrencyname,txndate as txndate,disputeamount,disputestatusid,disputestatusname,disputelogdate "
			+" ,disputetypeid,disputetypename,disputeid,customeremail from(SELECT * FROM  "
			+" (SELECT SUB.transactionid,SUB.payeeemailid customeremail,SUB.disputeamount, "
			+" SUB.transactionamount txnamount,SUB.currency txncurrencyname,SUB.transactiondate txndate, " 
			+" SUB.status as disputestatusid, coalesce(DSL.name,DS.name) disputestatusname,SUB.disputelogdate,SUB.type as disputetypeid, coalesce(DTL.name,DT.name)  "
			+"  disputetypename, SUB.disputeid from  "
			+" (select TXN.txnid as transactionid,AUT.emailid as payeeemailid,TXN.payeeamount transactionamount,DIS.requestamount as disputeamount, "
			+" CUR.code currency, TXN.creationdate as transactiondate, DIS.type,DIS.creationdate disputelogdate,DIS.id as disputeid, DIS.status from  "
			+" (select id txnid,payee,payer,payeeamount,payercurrency,creationdate from wallettransaction where (  "
			+" (nullif(:_payeeId, 0) is null AND payer= :_payerId) OR (nullif(:_payeeId, 0) is not null AND payee = :_payeeId and payer = :_payerId)  " 
			+" ))TXN join authentication AUT on AUT.id = TXN.payee join dispute DIS on  "
			+" DIS.transactionid = TXN.txnid left join currency CUR on CUR.id = TXN.payercurrency )SUB left join disputetype DT on DT.id = SUB.type " 
			+" left join disputetype_locale DTL on DTL.disputetypeid = SUB.type and DTL.languageid = :_languageId left join disputestatus DS  "
			+"  on DS.id = SUB.status left join disputestatus_locale DSL on DSL.disputestatuslocaleid = SUB.status "  
			+" and DSL.languageid = :_languageId where ((nullif(:_disputeType, 0) is not null AND SUB.type = :_disputeType)  "
			+"  or (nullif(:_disputeType, 0) is null AND SUB.type is not null)) and SUB.disputelogdate BETWEEN :_fromDate AND :_toDate ) DETAIL order by txndate desc)MAINTXN WHERE ROWNUM <=:_limit ";
	
	String DISPUTE_TRANSACTIONS_FOR_MERCHANT_OLD = "SELECT transactionid,payeremailid,disputedate,typeid,typeName,disputeamount,currency,disputeid," 
			+" statusid,statusName from (SELECT * FROM (SELECT SUB.transactionid,SUB.payeremailid,SUB.disputedate,SUB.type as typeid, coalesce(DTL.name,DT.name) "
			+" typeName, SUB.disputeamount, CUR.code as currency,disputeid, SUB.status as statusid, coalesce(DSL.name, DS.name) statusName from "
			+" (select TXN.txnid as transactionid, AUT.emailid as payeremailid, DIS.creationdate as disputedate, DIS.type," 
			+" DIS.requestamount as disputeamount, DIS.requestcurrency, DIS.id as disputeid, DIS.status from (select id txnid, payee, payer "
			+" from wallettransaction where( (nullif(:_payerId, 0 ) is null AND payee = :_payeeId and typeoftransaction = :_typeOfTransaction) " 
			+" or (nullif(:_payerId, 0 ) is not null AND payee = :_payeeId and payer = :_payerId and typeoftransaction = :_typeOfTransaction)) )TXN join authentication AUT on " 
			+" AUT.id = TXN.payer join dispute DIS on DIS.transactionid = TXN.txnid )SUB left join currency  CUR on CUR.id = SUB.requestcurrency "
			+" left join disputetype DT on DT.id = SUB.type left join disputetype_locale DTL on DTL.disputetypeid = SUB.type and " 
			+" DTL.languageid = :_languageId left join disputestatus DS on DS.id = SUB.status "
			+" left join disputestatus_locale DSL on DSL.disputestatuslocaleid = SUB.status and  DSL.languageid = :_languageId "
			+" where ((nullif(:_disputeType, 0) is not null AND SUB.type= :_disputeType)OR((nullif(:_disputeType, 0) is null AND SUB.type is  not null)) "
			+" and  SUB.disputedate BETWEEN :_fromDate AND :_toDate) DETAIL order by disputedate desc)MAINTXN WHERE ROWNUM <= :_limit ";
	
	/*Mine Changes Tasted*/
	String DISPUTE_TRANSACTIONS_FOR_MERCHANT = "SELECT transactionid,txnamount,txncurrencyname,txndate,disputeamount,disputestatusid,disputestatusName, "
			+" disputelogdate,disputetypeid , disputetypeName, disputeid,merchantemail from (SELECT * FROM (SELECT SUB.transactionid,SUB.txnamount,SUB.txndate, "
			+" SUB.payeremailid merchantemail,SUB.disputelogdate,SUB.type as disputetypeid, coalesce(DTL.name,DT.name) disputetypeName, SUB.disputeamount, "
			+" CUR.code as txncurrencyname,disputeid, SUB.status as disputestatusid, coalesce(DSL.name, DS.name) disputestatusName from (select TXN.txnid "
			+" as transactionid,TXN.txndate,TXN.payeramount as txnamount, AUT.emailid as payeremailid, DIS.creationdate as disputelogdate, DIS.type, "
			+" DIS.requestamount as disputeamount, DIS.requestcurrency, DIS.id as disputeid, DIS.status from (select id txnid, payee, payer,payeramount, "
			+" creationdate txndate from wallettransaction where ((nullif(:_payerId, 0 ) is null AND payee = :_payeeId) "
			+" or (nullif(:_payerId, 0 ) is not null AND payee = :_payeeId and payer = :_payerId)) )TXN "
			+" join authentication AUT on AUT.id = TXN.payer join dispute DIS on DIS.transactionid = TXN.txnid )SUB left join currency "
			+" CUR on CUR.id = SUB.requestcurrency left join disputetype DT on DT.id = SUB.type left join disputetype_locale DTL on "
			+" DTL.disputetypeid = SUB.type and DTL.languageid = :_languageId left join disputestatus DS on DS.id = SUB.status " 
			+" left join disputestatus_locale DSL on DSL.disputestatuslocaleid = SUB.status and DSL.languageid = :_languageId " 
			+" where ((nullif(:_disputeType, 0) is not null AND SUB.type= :_disputeType) or (nullif(:_disputeType, 0) is null AND SUB.type is not null)) ) "
			+" DETAIL where disputelogdate BETWEEN :_fromDate AND :_toDate order by disputelogdate desc)MAINTXN WHERE ROWNUM <= :_limit ";
	
	String DISPUTE_TRANSACTIONS_FOR_ADMIN_OLD="SELECT txnid,transactiondate,disputeid,payeeemailid,payeremailid,transactionamount,transactioncurrency,statusName," +
			" disputelogdate,disputetype,disputetypeid,statusid from ( SELECT * FROM (SELECT WALLET_TRX.ID AS txnid,WALLET_TRX.creationdate AS transactiondate, WALLET_DISPUTE.ID AS disputeid, "+
            " WALLET_AUTH.emailId AS payeeemailid,WALLET_AUTH2.emailid AS payeremailid,COALESCE(WALLET_TRX.payeeAmount,0.0) AS transactionamount, WALLET_CURRENCY.CODE AS transactioncurrency, "+ 
			" disputestatus_txn.disputeStatus  AS statusName,WALLET_DISPUTE.creationDate AS disputelogdate,disputType_txn.disputeType AS disputetype,WALLET_DISPUTE.type as disputetypeid ,WALLET_DISPUTE.status statusid FROM "+ 
			" WALLETTRANSACTION AS WALLET_TRX "+
			" JOIN AUTHENTICATION AS WALLET_AUTH ON WALLET_TRX.payee=WALLET_AUTH.ID "+ 
			" JOIN AUTHENTICATION AS WALLET_AUTH2 ON WALLET_TRX.payer=WALLET_AUTH2.ID "+ 
			" JOIN DISPUTE AS WALLET_DISPUTE ON WALLET_TRX.id= "+ 
			" WALLET_DISPUTE.TRANSACTIONID "+ 
			" JOIN ( SELECT  disputetypeid,name as disputeType  from disputetype_locale where languageid= :_languageId "+ 
			" UNION SELECT id as disputetypeid,name as disputeType  from disputetype WHERE NOT EXISTS (SELECT * FROM disputetype_locale l1 WHERE "+  
			" l1.languageid= :_languageId )) AS disputType_txn ON  WALLET_DISPUTE.type=disputType_txn.disputetypeid "+
			" JOIN (SELECT  disputestatuslocaleid,name "+ 
			" as disputeStatus  from disputestatus_locale where languageid= :_languageId  UNION SELECT id as disputestatuslocaleid,name as disputeStatus "+ 
			" from disputestatus WHERE NOT EXISTS ( SELECT * FROM disputestatus_locale l1 WHERE  l1.languageid= :_languageId )) AS disputestatus_txn ON "+ 
			" WALLET_DISPUTE.status=disputestatus_txn.disputestatuslocaleid JOIN CURRENCY WALLET_CURRENCY ON  WALLET_CURRENCY.ID=WALLET_TRX.payeeCurrency WHERE "+ 
			"  ((nullif(:_payeeId,0) is not null AND WALLET_TRX.payee= "+
			" :_payeeId) or (nullif(:_payeeId,0) is null AND WALLET_TRX.payee is not null)) "+ 
			" and (( nullif(:_payerId,0) is not null AND WALLET_TRX.PAYER= "+
			" :_payerId) or ( nullif(:_payerId,0) is null AND WALLET_TRX.PAYER is not null)) "+
			" AND ((nullif(:_disputeType,0) is not null AND disputType_txn.disputetypeid= "+
			" =:_disputeType) or "+
			" (nullif(:_disputeType,0) is null AND disputType_txn.disputetypeid is not null)) "+
			" AND WALLET_DISPUTE.creationDate BETWEEN :_fromDate AND :_toDate)DETAIL order by transactiondate desc)MAINTXN WHERE ROWNUM <= :_limit ";
	/*Mine Changes Tasted */
	
	String DISPUTE_TRANSACTIONS_FOR_ADMIN = "SELECT transactionid,txnamount,txncurrencyname,txndate,disputeamount,disputestatusid,disputestatusName,disputelogdate,disputetypeid, "
			 +" disputetypename,disputeid,customeremail,merchantemail from ( SELECT * FROM  "
			 +" (SELECT WALLET_TRX.ID AS transactionid, WALLET_TRX.creationdate AS txndate, WALLET_DISPUTE.ID AS disputeid,WALLET_DISPUTE.requestamount as disputeamount,  "
			 +" WALLET_AUTH.emailId AS merchantemail,WALLET_AUTH2.emailid AS customeremail,COALESCE(WALLET_TRX.payeeAmount,0.0) AS txnamount, WALLET_CURRENCY.CODE AS txncurrencyname, "  
			 +" disputestatus_txn.disputeStatus AS disputestatusName,WALLET_DISPUTE.creationDate AS disputelogdate,disputType_txn.disputeType AS disputetypename, "
			 +" WALLET_DISPUTE.type as disputetypeid,WALLET_DISPUTE.status disputestatusid FROM   "
			 +" WALLETTRANSACTION WALLET_TRX  "
			 +" JOIN AUTHENTICATION WALLET_AUTH ON WALLET_TRX.payee=WALLET_AUTH.ID "  
			 +" JOIN AUTHENTICATION WALLET_AUTH2 ON WALLET_TRX.payer=WALLET_AUTH2.ID "  
			 +" JOIN DISPUTE WALLET_DISPUTE ON WALLET_TRX.id= WALLET_DISPUTE.TRANSACTIONID "  
			 +" JOIN ( SELECT disputetypeid,name as disputeType from disputetype_locale where languageid= :_languageId "  
			 +" UNION SELECT id as disputetypeid,name as disputeType from disputetype WHERE NOT EXISTS (SELECT * FROM disputetype_locale l1 WHERE "  
			 +" l1.languageid= :_languageId )) disputType_txn ON WALLET_DISPUTE.type=disputType_txn.disputetypeid  "
			 +" JOIN (SELECT disputestatuslocaleid,name   "
			 +" as disputeStatus from disputestatus_locale where languageid= :_languageId UNION SELECT id as disputestatuslocaleid,name as disputeStatus "  
			 +" from disputestatus WHERE NOT EXISTS ( SELECT * FROM disputestatus_locale l1 WHERE l1.languageid= :_languageId )) disputestatus_txn ON "  
			 +" WALLET_DISPUTE.status=disputestatus_txn.disputestatuslocaleid JOIN CURRENCY WALLET_CURRENCY ON WALLET_CURRENCY.ID=WALLET_TRX.payeeCurrency WHERE  " 
			 +" ((nullif(:_payeeId,0) is not null AND WALLET_TRX.payee=  "
			 +" :_payeeId) or (nullif(:_payeeId,0) is null AND WALLET_TRX.payee is not null)) "
			 +" and ((nullif(:_payerId,0) is not null AND WALLET_TRX.PAYER=  "
			+" :_payerId) or (nullif(:_payerId,0) is null AND WALLET_TRX.PAYER is not null))  "
			 +" AND ((nullif(:_disputeType,0) is not null AND disputType_txn.disputetypeid =  "
			 +" :_disputeType) or (nullif(:_disputeType,0) is null AND disputType_txn.disputetypeid is not null))  "
			 +" AND WALLET_DISPUTE.creationDate BETWEEN :_fromDate AND :_toDate)DETAIL order by txndate desc) MAINTXN WHERE ROWNUM <= :_limit ";

	/*String CUSTOMER_ALL_TRANSACTIONS_FOR_RAISE_DISPUTE = "SELECT WALLET_TRX.ID AS TRANSACTIONID,WALLET_AUTH.emailId AS PAYEEEMAILID, COALESCE( " +
			"WALLET_TRX.payerAmount, 0.0) AS TRANSACTIONAMOUNT,WALLET_CURRENCY.CODE AS TRANSACTIONCURRENCY,WALLET_TRX.creationDate AS TRANSACTIONDATE FROM" +
			"  WALLETTRANSACTION AS WALLET_TRX JOIN AUTHENTICATION AS WALLET_AUTH ON WALLET_TRX.payee=WALLET_AUTH.ID JOIN CURRENCY AS WALLET_CURRENCY ON " +
			"WALLET_CURRENCY.ID=WALLET_TRX.payerCurrency WHERE WALLET_TRX.payee = :_payeeId AND WALLET_TRX.PAYER= :_payerId AND WALLET_TRX.creationDate BETWEEN :_fromDate " +
			"AND :_toDate ORDER BY WALLET_TRX.ID,WALLET_TRX.creationDate  limit :_limit";*/
	
	String CUSTOMER_ALL_TRANSACTIONS_FOR_RAISE_DISPUTE_OLD = "SELECT TRANSACTIONID,PAYEEEMAILID,TRANSACTIONAMOUNT,TRANSACTIONCURRENCY,TRANSACTIONDATE from (SELECT * FROM " +
			"(SELECT WALLET_TRX.ID AS TRANSACTIONID,WALLET_AUTH.emailId AS PAYEEEMAILID,"+
			"COALESCE(WALLET_TRX.payerAmount, 0.0) AS TRANSACTIONAMOUNT,WALLET_CURRENCY.CODE AS TRANSACTIONCURRENCY,WALLET_TRX.creationDate AS TRANSACTIONDATE FROM " +
			"WALLETTRANSACTION AS WALLET_TRX JOIN AUTHENTICATION AS WALLET_AUTH ON WALLET_TRX.payee=WALLET_AUTH.ID JOIN CURRENCY AS WALLET_CURRENCY ON " +
			"WALLET_CURRENCY.ID=WALLET_TRX.payerCurrency WHERE WALLET_TRX.payee = :_payeeId AND WALLET_TRX.PAYER = :_payerId and WALLET_TRX.creationDate BETWEEN :_fromDate " +
			"AND :_toDate ORDER BY WALLET_TRX.ID,WALLET_TRX.creationDate) MAIN where TRANSACTIONID NOT in (select transactionid from dispute) order by TRANSACTIONDATE desc) MAINTXN WHERE ROWNUM <= :_limit";
	
	/*Mine Changes Tasted*/
	String CUSTOMER_ALL_TRANSACTIONS_FOR_RAISE_DISPUTE = "SELECT TRANSACTIONID,MERCHANTEMAIL,TXNAMOUNT,TXNCURRENCYNAME,TXNDATE  TXNDATE from (SELECT * FROM (SELECT WALLET_TRX.ID TRANSACTIONID,WALLET_AUTH.emailId "
			+" MERCHANTEMAIL, COALESCE(WALLET_TRX.payerAmount, 0.0) TXNAMOUNT,WALLET_CURRENCY.CODE TXNCURRENCYNAME,WALLET_TRX.creationDate TXNDATE FROM " 
			+" WALLETTRANSACTION  WALLET_TRX JOIN AUTHENTICATION WALLET_AUTH ON WALLET_TRX.payee=WALLET_AUTH.ID JOIN CURRENCY WALLET_CURRENCY ON WALLET_CURRENCY.ID=WALLET_TRX.payerCurrency "
			+" WHERE ((NULLIF(:_payeeId,0) IS NOT NULL and WALLET_TRX.payee = :_payeeId AND WALLET_TRX.PAYER = :_payerId) or (NULLIF(:_payeeId,0) IS NULL and WALLET_TRX.PAYER =:_payerId)) " 
			+" and WALLET_TRX.typeoftransaction = :_typeOfTransaction and WALLET_TRX.creationDate BETWEEN :_fromDate AND :_toDate ORDER BY WALLET_TRX.ID,WALLET_TRX.creationDate) "
			+" MAIN where TRANSACTIONID NOT in (select transactionid from dispute) order by TXNDATE desc)MAINTXN WHERE ROWNUM <=:_limit ";

	/*String CUSTOMER_RAISE_OR_UPDATE_DISPUTE_PAGE="SELECT * FROM(SELECT WALLETTRNS.ID AS TRANSACTIONID,WALLETTRNS.creationdate AS TRANSACTIONDATE,WALLETTRNS.payeramount AS TRANSACTIONAMOUNT," +
			"PAYEECURRENCY.code AS TRANSACTIONCURRENCY,disputType_txn.disputeType,DISPUTETRANS.CREATIONDATE AS DISPUTELOGDATE,DISPUTETRANS.updationdate " +
			"AS UPDATIONDATE,DISPUTETRANS.REQUESTAMOUNT AS REQUESTAMOUNT, TRANSREQUESTCURR.CODE AS REQUESTCURRENCY, DISPUTETRANS.approvedamount AS " +
			"APPROVEDAMOUNT,TRANSAPPROVECURR.CODE AS APPROVEDCURRENCY,disputestatus_txn.disputeStatus AS STATUS FROM WALLETTRANSACTION AS WALLETTRNS JOIN " +
			"DISPUTE AS DISPUTETRANS ON WALLETTRNS.ID=DISPUTETRANS.TRANSACTIONID JOIN ( SELECT  disputestatuslocaleid,NAME AS disputeStatus  FROM " +
			"disputestatus_locale WHERE languageid= :_languageId UNION SELECT id AS disputestatuslocaleid,NAME AS disputeStatus  FROM disputestatus WHERE NOT " +
			"EXISTS ( SELECT * FROM disputestatus_locale l1 WHERE  l1.languageid= :_languageId) ) AS disputestatus_txn ON disputestatus_txn.disputestatuslocaleid= " +
			"DISPUTETRANS.STATUS JOIN CURRENCY AS TRANSREQUESTCURR ON TRANSREQUESTCURR.ID=DISPUTETRANS.REQUESTCURRENCY JOIN CURRENCY AS TRANSAPPROVECURR " +
			" ON TRANSAPPROVECURR.ID=DISPUTETRANS.APPROVEDCURRENCY JOIN (SELECT WALLETTRANS.ID,TRANSCURRENCY.CODE FROM CURRENCY AS TRANSCURRENCY JOIN " +
			"WALLETTRANSACTION AS WALLETTRANS ON WALLETTRANS.PAYERCURRENCY=TRANSCURRENCY.ID) AS PAYEECURRENCY ON WALLETTRNS.ID=PAYEECURRENCY.ID JOIN ( SELECT " +
			"disputetypeid,NAME AS disputeType  FROM disputetype_locale WHERE languageid= :_languageId UNION SELECT id AS disputetypeid,NAME AS disputeType  FROM " +
			"disputetype WHERE NOT EXISTS (SELECT * FROM disputetype_locale l1 WHERE  l1.languageid= :_languageId)) AS disputType_txn ON DISPUTETRANS.TYPE= " +
			"disputType_txn.disputetypeid) AS TAB1 WHERE TAB1.TRANSACTIONID= :_txnId ORDER BY TRANSACTIONDATE,TRANSACTIONID"; */
	
	/*Mine Changes Tasted */
	String CUSTOMER_RAISE_OR_UPDATE_DISPUTE_PAGE = "SELECT * FROM(SELECT WALLETTRNS.ID AS TRANSACTIONID,WALLETTRNS.creationdate AS TRANSACTIONDATE,WALLETTRNS.payeramount AS TRANSACTIONAMOUNT, " +
		       "PAYEECURRENCY.code AS TRANSACTIONCURRENCY,disputType_txn.disputeType,DISPUTETRANS.CREATIONDATE AS DISPUTELOGDATE,DISPUTETRANS.updationdate " +
				"AS UPDATIONDATE,DISPUTETRANS.REQUESTAMOUNT AS REQUESTAMOUNT, TRANSREQUESTCURR.CODE AS REQUESTCURRENCY, DISPUTETRANS.approvedamount AS " +
		        "APPROVEDAMOUNT,TRANSAPPROVECURR.CODE AS APPROVEDCURRENCY,disputestatus_txn.disputeStatus AS STATUS,DISPUTETRANS.type AS disputetypeid, " +
				"DISPUTETRANS.requestcurrency AS requestcurrencyid,DISPUTETRANS.id AS disputid,DISPUTETRANS.status AS disputstatusid FROM WALLETTRANSACTION AS WALLETTRNS JOIN " +
		        "DISPUTE AS DISPUTETRANS ON WALLETTRNS.ID=DISPUTETRANS.TRANSACTIONID JOIN ( SELECT  disputestatuslocaleid,NAME AS disputeStatus  FROM " +
				"disputestatus_locale WHERE languageid= :_languageId UNION SELECT id AS disputestatuslocaleid,NAME AS disputeStatus  FROM disputestatus WHERE NOT " +
		        "EXISTS ( SELECT * FROM disputestatus_locale l1 WHERE  l1.languageid= :_languageId) ) AS disputestatus_txn ON disputestatus_txn.disputestatuslocaleid= " +
				"DISPUTETRANS.STATUS JOIN CURRENCY AS TRANSREQUESTCURR ON TRANSREQUESTCURR.ID=DISPUTETRANS.REQUESTCURRENCY JOIN CURRENCY AS TRANSAPPROVECURR " +
		        "ON TRANSAPPROVECURR.ID=DISPUTETRANS.APPROVEDCURRENCY JOIN (SELECT WALLETTRNS.ID,TRANSCURRENCY.CODE FROM CURRENCY AS TRANSCURRENCY JOIN " +
				"WALLETTRANSACTION AS WALLETTRNS ON WALLETTRNS.PAYERCURRENCY=TRANSCURRENCY.ID) AS PAYEECURRENCY ON WALLETTRNS.ID=PAYEECURRENCY.ID JOIN ( SELECT " +
		        "disputetypeid,NAME AS disputeType  FROM disputetype_locale WHERE languageid= :_languageId UNION SELECT id AS disputetypeid,NAME AS disputeType  FROM " +
				"disputetype WHERE NOT EXISTS (SELECT * FROM disputetype_locale l1 WHERE  l1.languageid= :_languageId)) AS disputType_txn ON DISPUTETRANS.TYPE= " +
		        "disputType_txn.disputetypeid) AS TAB1 WHERE TAB1.TRANSACTIONID= :_txnId ORDER BY TRANSACTIONDATE,TRANSACTIONID";
	
	/*Mine Changes Tasted*/
	String ADMIN_OR_MERCHANT_UPDATE_DISPUTE = " SELECT SUB.transactionid,SUB.payeremailid,SUB.payeeemailid,SUB.payeramount,SUB.payercurrency,"
			+" SUB.transactiondate,coalesce(DSL.name,DS.name) disputestatus, SUB.disputedate,coalesce(DTL.name,DT.name) disputetype,SUB.disputeamount, "
			+" CUR.code as disputecurrency,SUB.type,SUB.status,SUB.approvedamount, CUR2.code as approvedcurrency,updationdate from "
			+" (select WALLET.id as transactionid,AUT.emailid payeremailid,AUT2.emailid payeeemailid, WALLET.payeramount payeramount, "
			+" CUR.code payercurrency, WALLET.creationdate as transactiondate,DIS.status,DIS.creationdate disputedate, DIS.type,DIS.requestamount disputeamount, "
			+" DIS.requestcurrency,DIS.approvedamount, DIS.approvedcurrency,DIS.updationdate from wallettransaction WALLET join authentication AUT on AUT.id = WALLET.payer " 
			+" left join authentication AUT2 on AUT2.id = WALLET.payee join dispute DIS on DIS.transactionid = WALLET.id join currency CUR on CUR.id = WALLET.payercurrency )SUB "
			+" left join currency  CUR on CUR.id = SUB.requestcurrency left join currency  CUR2 on CUR2.id = SUB.approvedcurrency "
			+" left join disputetype DT on DT.id = SUB.type left join disputetype_locale DTL on DTL.disputetypeid = SUB.type and "
			+" DTL.languageid = :_languageId left join disputestatus DS on DS.id = SUB.status left join disputestatus_locale DSL on "
			+" DSL.disputestatuslocaleid = SUB.status and  DSL.languageid = :_languageId where SUB.transactionid = :_txnId ";
	
	String DISPUTE_COUNT_QUERY = "select count(*) from dispute dis, wallettransaction wallet, authentication aut where	(aut.id=wallet.payer and 	dis.transactionid=wallet.id and aut.id=?) and (dis.status =1 or dis.status=3)";
	
	String MERCHANT_ACTIVE_DISPUTE_QUERY = "select count(*) from dispute dis, wallettransaction wallet, authentication aut where	(aut.id=wallet.payee and 	dis.transactionid=wallet.id and aut.id=?) and (dis.status =1 or dis.status=3)";
	
	


}


