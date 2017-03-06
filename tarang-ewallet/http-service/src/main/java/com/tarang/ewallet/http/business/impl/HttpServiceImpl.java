package com.tarang.ewallet.http.business.impl;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.business.HttpService;
import com.tarang.ewallet.http.repository.HttpRepository;
import com.tarang.ewallet.model.CancelResponse;
import com.tarang.ewallet.model.PgRequest;
import com.tarang.ewallet.model.PgResponse;
import com.tarang.ewallet.model.RefundResponse;
import com.tarang.ewallet.model.SettlementResponse;


public class HttpServiceImpl implements HttpService {
	
	private HttpRepository httpRepository;
	
	public HttpServiceImpl(HttpRepository httpRepository){
		this.httpRepository = httpRepository;
	}
	
	@Override
	public void initializeHttpService() throws WalletException {
		httpRepository.initializeHttpService();
	}

	@Override
	public PgResponse postAuthRequest(PgRequest  authAPIRequest) throws WalletException {
		return httpRepository.postAuthRequest(authAPIRequest);
	}
	
	@Override
	public CancelResponse postCancelRequest(PgRequest  cancelAPIRequest) throws WalletException {
		return httpRepository.postCancelRequest(cancelAPIRequest);
	}

	@Override
	public SettlementResponse postSettlementRequest(PgRequest settlementAPIRequest)
			throws WalletException {
		return httpRepository.postSettlementRequest(settlementAPIRequest);
	}

	@Override
	public RefundResponse postRefundRequest(PgRequest refundAPIRequest)
			throws WalletException {
		return httpRepository.postRefundRequest(refundAPIRequest);
	}
	
}
