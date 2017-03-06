package com.tarang.ewallet.merchant.business;

import java.util.List;

import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.BusinessInformation;
import com.tarang.ewallet.model.Merchant;
import com.tarang.ewallet.model.MerchantPayInfo;
import com.tarang.ewallet.util.QueryFilter;


public interface MerchantService {

	MerchantDto newregistration(MerchantDto merchantDto) throws WalletException;
	
	MerchantDto registrationAfter24hours(MerchantDto merchantDto) throws WalletException;

	List<UserDto> getMerchantsList(QueryFilter qf, String fromDate,
			String toDate, String name, String emailId, Long status) throws WalletException;

	MerchantDto getMerchantDetailsById(Long merchantId) throws WalletException;

	Boolean updateMerchantDetails(MerchantDto merchantDto,
			String adminOrmerchant) throws WalletException;

	Long getMerchantId(String userMail, String userType) throws WalletException;
	
	String getPersonName(String userMail, String userType)throws WalletException;

	Address getMerchantAddress(Long merchantId) throws WalletException;
	
	BusinessInformation  getMerchantBusinessInfo(Long merchantId) throws WalletException;
	
	MerchantDto getRegisteredMember(String userMail, String userType)throws WalletException;
	
	Merchant getMerchantByAuthId(Long authId) throws WalletException;
	
	String getLegalName(String email) throws WalletException;

	MerchantPayInfo getMerchantPayInfo(String email) throws WalletException;
	
	MerchantDto getMerchantByPhoneNumber(String code, String phoneNumber) throws WalletException;
	
}
