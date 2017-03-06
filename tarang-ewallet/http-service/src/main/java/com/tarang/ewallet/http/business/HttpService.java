package com.tarang.ewallet.http.business;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.CancelResponse;
import com.tarang.ewallet.model.PgRequest;
import com.tarang.ewallet.model.PgResponse;
import com.tarang.ewallet.model.RefundResponse;
import com.tarang.ewallet.model.SettlementResponse;


public interface HttpService {
	
	PgResponse postAuthRequest(PgRequest authAPIRequest) throws WalletException;
	
	CancelResponse postCancelRequest(PgRequest cancelAPIRequest) throws WalletException;
	
	SettlementResponse postSettlementRequest(PgRequest settlementAPIRequest) throws WalletException;
	
	RefundResponse postRefundRequest(PgRequest refundAPIRequest) throws WalletException;
	
	void initializeHttpService() throws WalletException;
	
}
