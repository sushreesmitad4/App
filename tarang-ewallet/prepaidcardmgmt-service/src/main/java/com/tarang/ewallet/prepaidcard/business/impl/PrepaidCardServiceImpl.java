/**
 * 
 */
package com.tarang.ewallet.prepaidcard.business.impl;

import com.tarang.ewallet.dto.PrepaidCardDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.prepaidcard.business.PrepaidCardService;
import com.tarang.ewallet.prepaidcard.repository.PrepaidCardRepository;


/**
 * @author  : kedarnathd
 * @date    : July 26, 2016
 * @time    : 4:21:39 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class PrepaidCardServiceImpl implements PrepaidCardService {

	private PrepaidCardRepository prepaidCardRepository;

	public PrepaidCardServiceImpl(PrepaidCardRepository prepaidCardRepository){
		this.prepaidCardRepository = prepaidCardRepository;	
	}
	
	@Override
	public PrepaidCardDto createPrepaidCard(PrepaidCardDto prepaidCardDto) throws WalletException {
		return prepaidCardRepository.createPrepaidCard(prepaidCardDto);
	}

	@Override
	public PrepaidCardDto getPrepaidCard(Long customerId) throws WalletException {
		return prepaidCardRepository.getPrepaidCard(customerId);
	}

	
}
