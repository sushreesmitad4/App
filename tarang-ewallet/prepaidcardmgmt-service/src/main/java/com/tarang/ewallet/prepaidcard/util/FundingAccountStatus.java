package com.tarang.ewallet.prepaidcard.util;

public interface FundingAccountStatus {

	Long NOT_VERIFIED_STATUS = 1L;
	
	Long PENDING_STATUS = 2L;
	
	Long REJECTED_STATUS = 3L;
	
	Long VERIFIED_STATUS = 4L;
}
