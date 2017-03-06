package com.tarang.ewallet.transaction.dao;

import java.util.List;

import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.RequestMoney;


public interface RequestMoneyDao {
	 
	RequestMoney createRequestMoney(RequestMoney receiveMoney)throws WalletException;

	RequestMoney getRequestMoney(Long id) throws WalletException;
	
	List<RequestMoneyDto> getRequestMoneyDtoList(final Long authId)throws WalletException;
	
	RequestMoney updateRequestMoney(RequestMoney receiveMoney)throws WalletException;

}
