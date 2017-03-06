/**
 * 
 */
package com.tarang.ewallet.http.repository;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.CancelResponse;
import com.tarang.ewallet.model.PgRequest;
import com.tarang.ewallet.model.PgResponse;
import com.tarang.ewallet.model.RefundResponse;
import com.tarang.ewallet.model.SettlementResponse;


/**
 * @author  : prasadj
 * @date    : Dec 27, 2012
 * @time    : 12:40:46 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface HttpRepository {

	PgResponse postAuthRequest(PgRequest authAPIRequest) throws WalletException;
	
	CancelResponse postCancelRequest(PgRequest cancelAPIRequest) throws WalletException;
	
	SettlementResponse postSettlementRequest(PgRequest settlementAPIRequest)throws WalletException;
	
	RefundResponse postRefundRequest(PgRequest refundAPIRequest) throws WalletException;
	
	void initializeHttpService() throws WalletException;
	
}
