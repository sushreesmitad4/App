package com.tarang.ewallet.transaction.util;

public interface FraudCheckQueries {

	String	IP_MULTIPLE_ACCOUNTS_VELOCITY = "select (case when totalcount > :_noOfAccounts then 1 else 0 end ) ispassed "
			+ " from(select count(*) totalcount from (select distinct id from authentication "
			+ " where ipaddress = :_ipAddress and creationdate between :_fromDate and :_toDate )Main) TXN";
		
	String	IS_LOGIN_FROM_DIFFERENT_IP_ONE = "Select ipaddress from authentication where id = :_authId";
	
	String	IS_LOGIN_FROM_DIFFERENT_IP_TWO = "select (case when count(CON2.country_code2) > 0 then 1 else 0 end) ispassed from "
			+ " (select * from ip_country_details_v where :_ipAddress1  between ip_from and ip_to limit 1) CON1 "
			+ " join (select * from ip_country_details_v where :_ipAddress2  between ip_from and ip_to limit 1)CON2 " 
			+ " on CON2.country_code2 = CON1.country_code2 ";
	
	String	EMAIL_PATTERN_CHECK = "select (CASE WHEN COUNT(emailid) > :_noOfPattern THEN 1 ELSE 0 END) isexceede "
			+ " from(select * from authentication where emailid like :_email || '%' )MM where emailid like '%' || :_domain "
			+ " and creationdate between :_fromDate and :_toDate ";
	
	String	FUNDING_SOURCE_PATTERN_CHECK = "select (case  when count(id) > :_noOfAccounts then 1 else 0 end ) isexceede "
			+ " from account where id in(select bid from bank where sortcode = :_bankCode) and creationdate between :_fromDate and :_toDate ";

	String FUNDING_SOURCE_PATTERN_CHECK_CARD = " select ( case when count(cardbin)+1 > :_noOfCards then 1 else 0 end) isExceede "
			+ " from(select CRD.cardbin from account ACC join card CRD on CRD.cid = ACC.id  where ACC.atype ='CARD'" 
			+ " and ACC.creationdate between :_fromDate and :_toDate) TXN where cardbin = :_cardBin ";
	
	String	IP_CARD_BIN_GEO_CHECK = "select (case when count(CON2.code) > 0 then 1 else 0 end) isPassed from "
			+ " (select country_code2 code from ip_country_details_v where :_ipAddress between to_char(ip_from) and to_char(ip_to) and rownum <= 1)CON1 join "
			+ " (select country_a2_code code from bin_country_details_v where bin = :_cardBin)CON2 on CON2.code = CON1.code ";
	
	String	DOMAIN_CHECK = " select (case when count(email) >0 then 1 else 0 end) isblacklisted from t_email_neg where email in (:_email,:_domain)";
	
	String	MERCHANT_THRESHOLD_CHECK = "select (case when count(id) > :_noOfDisputes then 1 else 0 end ) as isexccede from "
			+ " (select DIS.id id,DIS.creationdate as creationdate from wallettransaction WALLETTXN join dispute DIS on DIS.transactionid = WALLETTXN.id "
			+ "where WALLETTXN.payee = :_merchantAuthId) Main where creationdate between :_fromDate and :_toDate";
}
