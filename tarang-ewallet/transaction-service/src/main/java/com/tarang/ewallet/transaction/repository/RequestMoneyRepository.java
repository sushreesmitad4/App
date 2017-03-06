package com.tarang.ewallet.transaction.repository;

import java.util.List;

import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.RequestMoney;


public interface RequestMoneyRepository {
	
	RequestMoney createRequestMoney(RequestMoneyDto receiveMoneyDto)throws WalletException;

	RequestMoneyDto getRequestMoney(Long id) throws WalletException;
	
	List<RequestMoneyDto> getRequestMoneyList(Long authId)throws WalletException;
	
	RequestMoney updateRequestMoney(RequestMoneyDto receiveMoneyDto)throws WalletException;

	RequestMoney acceptRequestMoney(RequestMoneyDto receiveMoneyDto)throws WalletException;
	
	RequestMoney rejectRequestMoney(RequestMoneyDto receiveMoneyDto)throws WalletException;
	
	RequestMoney cancelRequestMoney(RequestMoneyDto receiveMoneyDto)throws WalletException;

}
