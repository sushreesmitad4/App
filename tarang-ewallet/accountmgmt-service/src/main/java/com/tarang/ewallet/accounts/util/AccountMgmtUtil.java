/**
 * 
 */
package com.tarang.ewallet.accounts.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.crypt.business.CryptService;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.util.HttpServiceConstants;
import com.tarang.ewallet.model.Account;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.AuthTxn;
import com.tarang.ewallet.model.Bank;
import com.tarang.ewallet.model.Card;
import com.tarang.ewallet.model.History;
import com.tarang.ewallet.model.PgRequest;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;


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
	
	private static final int EXP_YEAR_LENGTH3 = 3;
	
	private static final int EXP_YEAR_LENGTH4 = 4;
	
	public static AccountDto getAcccountDto(Account account, Address address, CryptService cryptService) throws WalletException {
		
		AccountDto dto = new AccountDto();
		
		dto.setId(account.getId());
		dto.setAuthId(account.getAuthId());
		dto.setAccountHolderName(account.getAccountHolderName());
		dto.setStatus(account.getStatus());
		dto.setDefaultAccount(account.getDefaultAccount());
		dto.setDeletedStatus(account.getDeletedStatus());
		dto.setAtype(account.getAtype());
		dto.setTypeOfRequest(account.getTypeOfRequest());
		dto.setSimNumber(account.getSimNumber());
		dto.setImeiNumber(account.getImeiNumber());

		if(account.getAtype().equals(AccountsConstants.BANK_ACCOUNT)){
			Bank bank = (Bank)account;
			dto.setCountry(bank.getCountry());
			dto.setBankAccountType(bank.getBankAccountType());
			dto.setSortCode(bank.getSortCode());
			dto.setBankName(bank.getBankName());
			dto.setAccountNumber(bank.getAccountNumber());
			dto.setBankAddress(bank.getBankAddress());
		}else if(dto.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
			Card card = (Card)account;
			dto.setCardType(card.getCardType());
			dto.setCardNumber(cryptService.decryptData(card.getCardNumber()));
			dto.setCardBin(card.getCardBin());
			dto.setCardExpairyDate(cryptService.decryptData(card.getCardExpairyDate()));
			dto.setIsSameAsProfileAddress(card.getSameAsProfileAddress());
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
	
	public static Account getAcccount(AccountDto dto, Long addressId, CryptService cryptService) throws WalletException {
		Account account = null;
		if(dto.getAtype().equals(AccountsConstants.BANK_ACCOUNT)){
			Bank bank = new Bank();
			bank.setCountry(dto.getCountry());
			bank.setBankAccountType(dto.getBankAccountType());
			bank.setSortCode(dto.getSortCode());
			bank.setBankName(dto.getBankName());
			bank.setAccountNumber(dto.getAccountNumber());
			bank.setBankAddress(dto.getBankAddress());
			bank.setHoldBank(dto.getHoldBank());
			account = bank;
		}else if(dto.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
			Card card = new Card();
			card.setAddressId(addressId);
			card.setCardType(dto.getCardType());
			card.setCardNumber(cryptService.encryptData(dto.getCardNumber()));
			card.setCardExpairyDate(cryptService.encryptData(dto.getCardExpairyDate()));
			card.setSameAsProfileAddress(dto.getIsSameAsProfileAddress());
			card.setCardBin(dto.getCardNumber().substring(0, CARD_BIN_LENGTH));
			account = card;
		}else {
			throw new WalletException(AccountsConstants.ERROR_MSG_INVALID_ACCOUNT);
		}
		
		account.setAuthId(dto.getAuthId());
		account.setIpAddress(dto.getClientIpAddress());
		account.setSimNumber(dto.getSimNumber());
		account.setImeiNumber(dto.getImeiNumber());
		account.setAccountHolderName(dto.getAccountHolderName());
		account.setStatus(dto.getStatus());
		account.setDefaultAccount(dto.getDefaultAccount());
		account.setDeletedStatus(dto.getDeletedStatus());
		account.setAtype(dto.getAtype());
		account.setCreationDate(new Date());
		account.setJointAccount(dto.getJointAccount());
		account.setTypeOfRequest(dto.getTypeOfRequest());
		return account; 

	}
	
	public static List<AccountDto> getAccountDtos(List<Account> accounts, CryptService cryptService) throws WalletException{
		List<AccountDto> dtos = new ArrayList<AccountDto>();
		
		for(Account account: accounts){
			dtos.add(getAcccountDto(account, null, cryptService));
		}
		
		return dtos;
	}
	
	public static void updateEditAcccount(AccountDto dto, Account account, CryptService cryptService) throws WalletException {

		if(dto.getAtype().equals(AccountsConstants.BANK_ACCOUNT)){
			Bank bank = (Bank)account;
			bank.setCountry(dto.getCountry());
			bank.setBankAccountType(dto.getBankAccountType());
			bank.setSortCode(dto.getSortCode());
			bank.setBankName(dto.getBankName());
			bank.setAccountNumber(dto.getAccountNumber());
			bank.setBankAddress(dto.getBankAddress());
		}else if(dto.getAtype().equals(AccountsConstants.CARD_ACCOUNT)){
			Card card = (Card)account;
			card.setCardType(dto.getCardType());
			card.setCardNumber(cryptService.encryptData(dto.getCardNumber()));
			card.setCardExpairyDate(cryptService.encryptData(dto.getCardExpairyDate()));
			card.setSameAsProfileAddress(dto.getIsSameAsProfileAddress());
		}else {
			throw new WalletException(AccountsConstants.ERROR_MSG_INVALID_ACCOUNT);
		}
		account.setAccountHolderName(dto.getAccountHolderName());
		account.setTypeOfRequest(dto.getTypeOfRequest());
	}
	
	public static void getBillingAddress(AccountDto dto, Address address){
		if(address != null){
			address.setCountry(dto.getCountry());
			address.setAddressOne(dto.getAddrOne());
			address.setAddressTwo(dto.getAddrTwo());
			address.setCity(dto.getCity());
			address.setRegion(dto.getState());
			address.setZipcode(dto.getPostalCode());
		}
	}
	
	public static void getPgRequest(AccountDto dto, PgRequest authAPIRequest, Integer pennyDropAmount){
		/* Transaction Type :: AUTH, Cancel, Settle, Refund */
		authAPIRequest.setTxnType(HttpServiceConstants.AUTH_TXN_TYPE);
		authAPIRequest.setPaymentMode(HttpServiceConstants.PAYMENT_MODE);
		authAPIRequest.setDateAndTime(DateConvertion.getPgDateAndTime(new Date()));
		authAPIRequest.setOrderID(DateConvertion.getPgOrderId(new Date()));
		authAPIRequest.setReqType(HttpServiceConstants.REQ_TYPE_MOTO);
		authAPIRequest.setAmount(pennyDropAmount != null ? pennyDropAmount.toString() : GlobalLitterals.EMPTY_STRING);
		authAPIRequest.setCurrency(dto.getCurrencyCode());
		
		authAPIRequest.setCardNumber(dto.getCardNumber());
		authAPIRequest.setNameOnCard(dto.getAccountHolderName());
		if(dto.getCardExpairyDate() != null){
			String[] ced = dto.getCardExpairyDate().split("/");
			authAPIRequest.setCardExpiryMonth(ced[0]);
			//test case date is set 3
			if(ced[1].length() == EXP_YEAR_LENGTH3){
				authAPIRequest.setCardExpiryYear(ced[1].substring(1, EXP_YEAR_LENGTH3));
			}else{
				authAPIRequest.setCardExpiryYear(ced[1].substring(2, EXP_YEAR_LENGTH4));
			}
		}
		authAPIRequest.setCardCVV(dto.getCvv());
		
	}
	
	public static void getPgRequestFromHistory(History history, PgRequest authAPIRequest) throws WalletException{
		AuthTxn info = history.getAuthTxn();
		authAPIRequest.setTxnType(HttpServiceConstants.SETTLEMENT_TXN_TYPE);
		authAPIRequest.setPaymentMode(info.getPaymentMode());
		authAPIRequest.setDateAndTime(DateConvertion.getPgDateAndTime(new Date()));
		authAPIRequest.setOrderID(history.getOrderId());
		authAPIRequest.setReqType(history.getReqType());
		authAPIRequest.setAmount(CommonUtil.convertToString(history.getAmount()));
		authAPIRequest.setCurrency(history.getCurrency());
		authAPIRequest.setPgTxnId(info.getPgTxnId());
	}
	
}
