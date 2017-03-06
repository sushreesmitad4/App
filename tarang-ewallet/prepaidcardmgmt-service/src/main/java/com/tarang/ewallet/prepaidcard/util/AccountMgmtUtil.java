/**
 * 
 */
package com.tarang.ewallet.prepaidcard.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.PrepaidCardDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.PrepaidBank;
import com.tarang.ewallet.model.PrepaidCard;
import com.tarang.ewallet.model.PrepaidCardAccount;


/**
 * @author  : prasadj
 * @date    : Nov 27, 2012
 * @time    : 12:40:33 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class AccountMgmtUtil {

	private static final int CARD_BIN_LENGTH = 6;
	
	public static PrepaidCardDto getAcccountDto(PrepaidCardAccount account, Address address, CryptService cryptService) throws WalletException {
		
		PrepaidCardDto dto = new PrepaidCardDto();
		
		dto.setCustomerNumber(account.getId());	
		dto.setAuthId(account.getAuthId());
		dto.setCardHolderName(account.getAccountHolderName());
		dto.setStatus(account.getStatus());
		dto.setDefaultAccount(account.getDefaultAccount());
		dto.setDeletedStatus(account.getDeletedStatus());
		dto.setAtype(account.getAtype());
		dto.setTypeOfRequest(account.getTypeOfRequest());
		dto.setSimNumber(account.getSimNumber());
		dto.setImeiNumber(account.getImeiNumber());

		if(account.getAtype().equals(AccountsConstants.BANK_ACCOUNT)){
			PrepaidBank bank = (PrepaidBank)account;
			dto.setCountry(bank.getCountry());
			dto.setBankAccountType(bank.getBankAccountType());
			dto.setSortCode(bank.getSortCode());
			dto.setBankName(bank.getBankName());
			dto.setAccountNumber(bank.getAccountNumber());
			dto.setBankAddress(bank.getBankAddress());
		}else if(dto.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
			PrepaidCard card = (PrepaidCard)account;
			dto.setCardType(card.getCardType());
			dto.setCardNumber(cryptService.decryptData(card.getCardNumber()));
			dto.setCardBin(card.getCardBin());
			dto.setCardExpairyDate(cryptService.decryptData(card.getCardExpairyDate()));
			dto.setIsSameAsProfileAddress(card.getSameAsProfileAddress());
			dto.setCvv(cryptService.decryptData(card.getCvv()));
			if(address != null){
				dto.setCountry(address.getCountry());
				dto.setAddrOne(address.getAddressOne());
				dto.setAddrTwo(address.getAddressTwo());
				dto.setCity(address.getCity());
				dto.setState(address.getRegion());
				dto.setPostalCode(address.getZipcode());
			}
		}else {
			throw new WalletException(AccountsConstants.ERROR_MSG_INVALID_ACCOUNT);
		}
		
		return dto;
	}
	
	public static PrepaidCardAccount getPrepaidCardAccount(PrepaidCardDto dto, Long addressId, CryptService cryptService) throws WalletException {
		PrepaidCardAccount account = null;
		if(dto.getAtype().equals(AccountsConstants.BANK_ACCOUNT)){
			PrepaidBank bank = new PrepaidBank();
			bank.setCountry(dto.getCountry());
			bank.setBankAccountType(dto.getBankAccountType());
			bank.setSortCode(dto.getSortCode());
			bank.setBankName(dto.getBankName());
			bank.setAccountNumber(dto.getAccountNumber());
			bank.setBankAddress(dto.getBankAddress());
			bank.setHoldBank(dto.getHoldBank());
			account = bank;
		}else if(dto.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
			PrepaidCard card = new PrepaidCard();
			card.setAddressId(addressId);
			card.setCardType(dto.getCardType());
			card.setCardNumber(cryptService.encryptData(dto.getCardNumber()));
			card.setCardExpairyDate(cryptService.encryptData(dto.getCardExpairyDate()));
			card.setSameAsProfileAddress(dto.getIsSameAsProfileAddress());
			card.setCardBin(dto.getCardNumber().substring(0, CARD_BIN_LENGTH));
			card.setCvv(cryptService.encryptData(dto.getCvv()));
			account = card;
		}else {
			throw new WalletException(AccountsConstants.ERROR_MSG_INVALID_ACCOUNT);
		}
		
		account.setAuthId(dto.getAuthId());
		account.setIpAddress(dto.getClientIpAddress());
		account.setSimNumber(dto.getSimNumber());
		account.setImeiNumber(dto.getImeiNumber());
		account.setAccountHolderName(dto.getCardHolderName());
		account.setStatus(dto.getStatus());
		account.setDefaultAccount(dto.getDefaultAccount());
		account.setDeletedStatus(dto.getDeletedStatus());
		account.setAtype(dto.getAtype());
		account.setCreationDate(new Date());
		account.setJointAccount(dto.getJointAccount());
		account.setTypeOfRequest(dto.getTypeOfRequest());
		return account; 

	}
	
	public static List<PrepaidCardDto> getAccountDtos(List<PrepaidCardAccount> accounts, CryptService cryptService) throws WalletException{
		List<PrepaidCardDto> dtos = new ArrayList<PrepaidCardDto>();
		
		for(PrepaidCardAccount account: accounts){
			dtos.add(getAcccountDto(account, null, cryptService));
		}
		
		return dtos;
	}
	
	
	
	public static void getBillingAddress(PrepaidCardDto dto, Address address){
		if(address != null){
			address.setCountry(dto.getCountry());
			address.setAddressOne(dto.getAddrOne());
			address.setAddressTwo(dto.getAddrTwo());
			address.setCity(dto.getCity());
			address.setRegion(dto.getState());
			address.setZipcode(dto.getPostalCode());
		}
	}
	
}
