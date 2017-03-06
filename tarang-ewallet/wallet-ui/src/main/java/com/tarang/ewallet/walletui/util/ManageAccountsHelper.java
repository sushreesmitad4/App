/**
 * 
 */
package com.tarang.ewallet.walletui.util;

import javax.servlet.http.HttpServletRequest;

import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.accounts.util.FundingAccountStatus;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.form.AddCardAccountForm;
import com.tarang.ewallet.walletui.form.NotVerifiedForm;
import com.tarang.mwallet.rest.services.model.RestRequest;

/**
 * @author kedarnathd
 *
 */
public class ManageAccountsHelper implements AttributeConstants {
	
	/**
	 * This is the common method called for both web and mobile request
	 * @param request
	 * @param addCardAccountForm
	 * @param profileAddress
	 * @param authId
	 * @param channel
	 * @return
	 * @throws WalletException
	 */
	public static AccountDto getAccountDto(HttpServletRequest request, AddCardAccountForm addCardAccountForm, 
			Address profileAddress, Long authId, TypeOfRequest channel) throws WalletException{
		
		AccountDto dto = new AccountDto();
		String userAgent = request.getHeader(USER_AGENT);
		String clientIpAddress = UIUtil.getClientIpAddr(request);
		
		Long typeOfReqWeb = MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
		        (Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue());
		
		Long typeOfReqMobile = MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
		        (Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.MOBILE.getValue());
		
		if(channel.equals(TypeOfRequest.WEB)){
			dto.setUserAgent(userAgent);
			dto.setClientIpAddress(clientIpAddress);
			dto.setTypeOfRequest(typeOfReqWeb);
		} else{
			dto.setTypeOfRequest(typeOfReqMobile);
			dto.setSimNumber(addCardAccountForm.getSimNumber());
			dto.setImeiNumber(addCardAccountForm.getImeiNumber());
		}
		
		dto.setAuthId(authId);
		dto.setAccountHolderName(addCardAccountForm.getAccountHolderName());
		dto.setStatus(FundingAccountStatus.NOT_VERIFIED_STATUS);
		dto.setDefaultAccount(Boolean.FALSE);
		dto.setDeletedStatus(AccountsConstants.NOT_DELETE);
		dto.setAtype(AccountsConstants.CARD_ACCOUNT);
		dto.setCardType(addCardAccountForm.getCardType());
		dto.setCardNumber(addCardAccountForm.getCardNumber());
		dto.setCardExpairyDate(addCardAccountForm.getExpireDateMonth() + "/" + addCardAccountForm.getExpireDateYear());
		dto.setCvv(addCardAccountForm.getCvv());
		dto.setIsSameAsProfileAddress(addCardAccountForm.getIsSameAsProfileAddress());
		dto.setJointAccount(addCardAccountForm.getJointAccount());
		if(addCardAccountForm.getIsSameAsProfileAddress()){
			dto.setCountry(profileAddress.getCountry());
			dto.setAddrOne(profileAddress.getAddressOne());
			dto.setAddrTwo(profileAddress.getAddressTwo());
			dto.setCity(profileAddress.getCity());
			dto.setState(profileAddress.getRegion());
			dto.setPostalCode(profileAddress.getZipcode());
			dto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
					profileAddress.getCountry()));
			Long defultCurrency = MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), profileAddress.getCountry());
			dto.setCurrency(defultCurrency);
		} else{
			addCardAccountForm.convertCardFormToDtoForAddress(dto);
			dto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
					addCardAccountForm.getCountry()));
			Long defultCurrency = MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), addCardAccountForm.getCountry());
			dto.setCurrency(defultCurrency);
		}
		return dto;
	}
	
	/**
	 * Convert the request object to the actual form object
	 * @param restRequest
	 * @return
	 */
	public static AddCardAccountForm getAddCardAccountForm(RestRequest restRequest){
		AddCardAccountForm addCardAccountForm = new AddCardAccountForm();
		if(null == restRequest){
			return null;
		}
		addCardAccountForm.setAccountHolderName(restRequest.getAccountHolderName());
		addCardAccountForm.setCardNumber(restRequest.getCardNumber());
		addCardAccountForm.setCardType(restRequest.getCardType());
		addCardAccountForm.setExpireDateYear(restRequest.getExpireDateYear());
		addCardAccountForm.setExpireDateMonth(restRequest.getExpireDateMonth());
		addCardAccountForm.setCvv(restRequest.getCvv());
		addCardAccountForm.setIsSameAsProfileAddress(restRequest.getIsSameAsProfileAddress());
		addCardAccountForm.setJointAccount(restRequest.getJointAccount());
		addCardAccountForm.setSimNumber(restRequest.getSimNumber());
		addCardAccountForm.setImeiNumber(restRequest.getImeiNumber());
		return addCardAccountForm;
	}

	public static NotVerifiedForm getNotVerifiedForm(RestRequest restRequest) {
		
		NotVerifiedForm notVerifiedForm = new NotVerifiedForm();
		if(null == restRequest){
			return null;
		}
		notVerifiedForm.setCvvNumber(restRequest.getCvv());
		notVerifiedForm.setAccountId(restRequest.getAccountId());
		return notVerifiedForm;
	}
	
	public static AccountDto getDtoForNotVerifedAccountStatus(HttpServletRequest request, AccountDto dto, 
			RestRequest restRequest){
		if(null == restRequest){
			return null;
		}
		dto.setId(restRequest.getAccountId());
		dto.setCvv(restRequest.getCvv());
		/*From mobile requested, so store SIM number in the database*/
		dto.setSimNumber(restRequest.getSimNumber());
		dto.setMsisdnNumber(restRequest.getMsisdnNumber());
		dto.setImeiNumber(restRequest.getImeiNumber());
		dto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
				dto.getCountry()));
		return dto;
	}
	
	public static AccountDto getDtoForPendingAccountStatus(HttpServletRequest request, AccountDto dto, 
			RestRequest restRequest){
		if(null == restRequest){
			return null;
		}
		dto.setOrderId(restRequest.getCode());
		dto.setTransactionAmount(restRequest.getAmount());
		dto.setId(restRequest.getAccountId());
		/*From mobile requested, so store SIM number in the database*/
		dto.setSimNumber(restRequest.getSimNumber());
		dto.setMsisdnNumber(restRequest.getMsisdnNumber());
		dto.setImeiNumber(restRequest.getImeiNumber());
		dto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
				dto.getCountry()));
		return dto;
	}
}
