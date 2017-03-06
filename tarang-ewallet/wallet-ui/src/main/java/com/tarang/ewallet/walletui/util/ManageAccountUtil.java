package com.tarang.ewallet.walletui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.constants.Accounts;


public class ManageAccountUtil implements AttributeConstants{

	public static List<DisplayAccount> getDisplayAccounts(List<AccountDto> list,
			Map<Long, String> moneyAccountStatusMap, Map<Long, Object> bankAccountTypeMap,
			Map<Long, String> cardTypeMap){
		
		List<DisplayAccount> displayAccounts = new ArrayList<DisplayAccount>();
		DisplayAccount displayAccount = null;
		for(AccountDto dto:list){
			
			displayAccount = new DisplayAccount();
			
			displayAccount.setId(dto.getId());
			displayAccount.setAtype(dto.getAtype());
			
			Map<Long, String> bankAccountTyps = getBankAccountType(bankAccountTypeMap);
			
			String bankOrCardType = null;
			String bankOrCardNumber = null;
			String bankOrCardName = null;
			
			if( dto.getAtype().equals(Accounts.BANK_ACCOUNT)){
				bankOrCardType = bankAccountTyps.get(dto.getBankAccountType());
				bankOrCardNumber = dto.getAccountNumber();
				bankOrCardName = dto.getBankName();
			} else {
				bankOrCardNumber = dto.getCardNumber();
				bankOrCardName = cardTypeMap.get(dto.getCardType());
			}
			displayAccount.setBankOrCardType(bankOrCardType);
			displayAccount.setBankOrCardNumber(xNumber(bankOrCardNumber));
			displayAccount.setBankOrCardName(bankOrCardName);
			
			displayAccount.setStatus(dto.getStatus());
			displayAccount.setStatusName(moneyAccountStatusMap.get(dto.getStatus()));

			displayAccount.setDefaultAccount(dto.getDefaultAccount());
			displayAccount.setDefaultValue( dto.getDefaultAccount() ? DEFAULT_ACCOUNT : EMPTY_STRING);

			displayAccounts.add(displayAccount);
		}
		return displayAccounts;
	}
	
	public static List<AccountDto> getDisplayAccountsForDevice(List<AccountDto> manageAccountDtos,
			Map<Long, String> moneyAccountStatusMap, Map<Long, Object> bankAccountTypeMap,
			Map<Long, String> cardTypeMap){
		
		for(AccountDto dto:manageAccountDtos){
			dto.setCardName(cardTypeMap.get(dto.getCardType()));
			dto.setCardNumberDisplay(xNumber(dto.getCardNumber()));
			dto.setStatusName(moneyAccountStatusMap.get(dto.getStatus()));
			dto.setDefaultValue( dto.getDefaultAccount() ? DEFAULT_ACCOUNT : EMPTY_STRING);
			dto.setCardBin(GlobalLitterals.EMPTY_STRING);
			dto.setCardNumber(GlobalLitterals.EMPTY_STRING);
			dto.setCardExpairyDate(GlobalLitterals.EMPTY_STRING);
			dto.setCvv(GlobalLitterals.EMPTY_STRING);
		}
		return manageAccountDtos;
	}
	
	@SuppressWarnings({"unchecked" })
	private static Map<Long, String> getBankAccountType(Map<Long, Object> bankAccountTypeMap){
		Map<Long, String> bankAccountMap = new HashMap<Long, String>();
		Map<Long, String> bankAccountMap1 = null;

			Iterator<Long> i = bankAccountTypeMap.keySet().iterator();
			while (i.hasNext()) {
				Long cid =  i.next();
				bankAccountMap1 = (Map<Long, String>)bankAccountTypeMap.get(cid);
				Iterator<Long> ir = bankAccountMap1.keySet().iterator();
				while (ir.hasNext()) {
					Long aid =  ir.next();
					bankAccountMap.put(aid, bankAccountMap1.get(aid));
				}
				
			}
		return bankAccountMap;
	}
	
	public static String xNumber(String number){
		final int visibleLengh = 4;
		int num = number.length() - visibleLengh;
		StringBuffer str = new StringBuffer();
		for(int i = 0; i < num; i++){
			str.append("x");
		}
		return str.toString() + number.substring(number.length() - visibleLengh);
	}

}
