package com.tarang.ewallet.accountcloser.util;

public interface AccountCloserQueries {
	/*Updated*/ 
	String ACCOUNT_CLOSER_LIST = "SELECT ID id, emailid emailId, requestedDate, registrationdate registrationDate, " 
			+ "lasttransactiondate lastTransactionDate, STATUS  FROM (SELECT * FROM (SELECT ID,emailid,creationdate registrationdate,lasttransactiondate, "  
			+ "checkstatus STATUS,requestedDate FROM (SELECT * FROM (SELECT AC.id,AC.authId,A.emailid,COALESCE(ACSL.name,ACS.name) checkstatus,AC.status, "
			+ "A.creationdate,COALESCE(WALLET1.lastDate,WALLET2.lastDate) lasttransactiondate,AC.requestedDate FROM AccountCloser AC JOIN "
			+ "(SELECT id,emailid,creationdate FROM Authentication WHERE ((NULLIF(cast( :_userType as varchar(2)),'0') IS NULL and usertype in ('M')) or( NULLIF(cast( :_userType as varchar(2)),'0') IS NOT NULL and usertype in (cast(:_userType as varchar(2)))) "
			+ ") UNION SELECT id,emailid,creationdate FROM Authentication WHERE ((NULLIF(cast(:_userType as varchar(2)),'0') IS NULL and usertype in ('C')) or (NULLIF(cast(:_userType as varchar(2)),'0') IS NOT NULL and usertype in  (cast(:_userType as varchar(2)))) "
			+ ")) A ON AC.authid = A.id LEFT JOIN accountclosurestatus_locale ACSL ON ACSL.accountclosurestatusid = AC.status AND languageid = :_languageId "
			+ "LEFT JOIN accountclosurestatus ACS ON ACS.id = AC.status LEFT JOIN (SELECT MAX(creationdate) lastDate,payer FROM wallettransaction GROUP BY payer) "
			+ "WALLET1 ON WALLET1.PAYER = AC.authId LEFT JOIN (SELECT MAX(updatededdate) lastDate,payer FROM wallettransaction GROUP BY payer)WALLET2 ON "
			+ "WALLET2.PAYER = AC.authId )Main WHERE Main.status =1)TT WHERE requestedDate BETWEEN :_fromDate AND :_toDate ) "
			+ "TXN ORDER BY requestedDate DESC)MAINTXN WHERE ROWNUM <= :_limit ";
	
	/*Updated*/
	String ACCOUNT_CLOSER_VIEW = "select emailid,usertype,personOrOrganisationName,requestedDate,registrationDate,lasttransactiondate,userstatus,accountCloserStatus "
			+ "from(select TD.emailid,TD.usertype,TD.requestedDate,TD.registrationDate,TD.lasttransactiondate,US.name userstatus,TD.accountCloserStatus,personOrOrganisationName "
			+ "from (select AC.id,AC.authId,A.emailid,A.usertype,A.creationdate registrationDate,coalesce(ACSL.name,ACS.name) accountCloserStatus, "
			+ "A.status,A.creationdate,coalesce(WALLET1.lastDate,WALLET2.lastDate) lasttransactiondate,AC.requestedDate, "
			+ "(case "
			+ "when A.usertype='C' then (select ( P.firstname ||' ' || P.lastname) from customer c join personname p on p.id = c.nameid where c.authenticationid = AC.authId) "
			+ "when A.usertype='M' then (select BI.legalname  from merchant ME join businessinformation BI on BI.id = ME.businessinformationid where ME.authenticationid = AC.authid) "
			+ "else '' "
			+ "end "
			+ ") personOrOrganisationName "
			+ "from AccountCloser AC  "
			+ "join  Authentication A on A.id = AC.authId "
			+ "left join (select MAX(creationdate) lastDate,payer from wallettransaction group by payer) WALLET1 on WALLET1.PAYER = AC.authId "
			+ "left join (select MAX(updatededdate) lastDate,payer from wallettransaction group by payer) WALLET2 on WALLET2.PAYER = AC.authId "
			+ "left join accountclosurestatus_locale ACSL on ACSL.accountclosurestatusid = AC.status and languageid = :_languageId "
			+ "left join accountclosurestatus ACS on ACS.id = AC.status "
			+ ")TD join userstatus US on US.id = TD.status where TD.id = :_accountcloserid "
			+ ")TXN order by requestedDate desc";
}
