package com.tarang.ewallet.transaction.business.impl;
import java.util.List;

import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.RequestMoney;
import com.tarang.ewallet.transaction.business.RequestMoneyService;
import com.tarang.ewallet.transaction.repository.RequestMoneyRepository;


public class RequestMoneyServiceImpl implements RequestMoneyService {
	
	private RequestMoneyRepository requestMoneyRepository;

	public RequestMoneyServiceImpl(RequestMoneyRepository requestMoneyRepository) {
		this.requestMoneyRepository = requestMoneyRepository;
	}

	@Override
	public RequestMoney createRequestMoney(RequestMoneyDto receiveMoneyDto)throws WalletException {
		return requestMoneyRepository.createRequestMoney(receiveMoneyDto);
	}

	@Override
	public RequestMoneyDto getRequestMoney(Long id) throws WalletException {
		return requestMoneyRepository.getRequestMoney(id);
	}

	@Override
	public List<RequestMoneyDto> getRequestMoneyList(Long authId)throws WalletException {
		return requestMoneyRepository.getRequestMoneyList(authId);
	}

	@Override
	public RequestMoney updateRequestMoney(RequestMoneyDto receiveMoneyDto) throws WalletException {
		return requestMoneyRepository.updateRequestMoney(receiveMoneyDto);
	}

	@Override
	public RequestMoney acceptRequestMoney(RequestMoneyDto receiveMoneyDto) throws WalletException {
		return requestMoneyRepository.acceptRequestMoney(receiveMoneyDto);
	}

	@Override
	public RequestMoney rejectRequestMoney(RequestMoneyDto receiveMoneyDto) throws WalletException {
		return requestMoneyRepository.rejectRequestMoney(receiveMoneyDto);
	}

	@Override
	public RequestMoney cancelRequestMoney(RequestMoneyDto receiveMoneyDto) throws WalletException {
		return requestMoneyRepository.cancelRequestMoney(receiveMoneyDto);
	}

}
