package com.tarang.ewallet.walletui.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.tarang.ewallet.accountcloser.util.AccountCloserStatus;
import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.form.AccountCloserFormView;


public class AccountCloserHelper implements AttributeConstants, GlobalLitterals{
	
	public static void convertAccCloserDtoToAccCloserFormView(
			HttpServletRequest request, AccountCloserDto accountCloserDto, AccountCloserFormView accountCloserFormView) {
		
		accountCloserFormView.setAccountCloserStatus(accountCloserDto.getStatusStr());
		accountCloserFormView.setAuthId(accountCloserDto.getAuthId());
		accountCloserFormView.setCreator(accountCloserDto.getCreator());
		accountCloserFormView.setEmailid(accountCloserDto.getEmailId());
		accountCloserFormView.setId(accountCloserDto.getId());
		accountCloserFormView.setIsRecurringPaymentExist(false);
		accountCloserFormView.setLastTransactionDate(accountCloserDto.getLastTransactionDate());
		accountCloserFormView.setMessageList(accountCloserDto.getMessageList());
		accountCloserFormView.setPersonOrOrganisationName(accountCloserDto.getPersonOrOrganisationName());
		accountCloserFormView.setRegistrationDate(accountCloserDto.getRegistrationDate());
		accountCloserFormView.setRequestedDate(accountCloserDto.getRequestedDate());
		accountCloserFormView.setUserStatus(accountCloserDto.getUserStatus());
		accountCloserFormView.setUserType(accountCloserDto.getUserType());
		accountCloserFormView.setUserWalletDetails(accountCloserDto.getWallets());
		
		accountCloserFormView.setRegistrationDateAsString(DateConvertion.dateToString(accountCloserDto.getRegistrationDate()));
		accountCloserFormView.setRequestedDateAsString(DateConvertion.dateToString(accountCloserDto.getRequestedDate()));
		accountCloserFormView.setLastTransactionDateAsString(DateConvertion.dateToString(accountCloserDto.getLastTransactionDate()));
		
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<Long,String> getAllWallets(Map<Long, UserWallet> userWallets,HttpServletRequest request) {

		Map<Long,String> map=new HashMap<Long, String>();
		
		Set qset = userWallets.entrySet();
		Iterator i = qset.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			UserWallet userWallet = (UserWallet)me.getValue();
			String amount = UIUtil.getConvertedAmountInString(userWallet.getAmount())
					+ SPACE_STRING + getCurrencyCode(request, userWallet.getCurrency());
			map.put(userWallet.getCurrency(), amount);
		}
		return map;
	}
	
	public static String getCurrencyCode(HttpServletRequest request,Long currencyId) {
		return	MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(currencyId);
	}
	
	public static Map<Long, String> getAdminAccountCloserStatuses(
			HttpServletRequest request) {
		Map<Long, String> maps =  MasterDataUtil.getSimpleDataMap(
			    request.getSession().getServletContext(), 
			    (Long) request.getSession().getAttribute(LANGUAGE_ID), 
			    MasterDataUtil.ACCOUNT_CLOSURE_STATUS);
		Map<Long, String> temp = new HashMap<Long, String>();
		temp.put(AccountCloserStatus.APPROVAL, maps.get(AccountCloserStatus.APPROVAL));
		temp.put(AccountCloserStatus.REJECT, maps.get(AccountCloserStatus.REJECT));
		return temp;
	}
}