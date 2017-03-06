package com.tarang.ewallet.merchant.dao;

import java.util.List;

import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.BusinessInformation;
import com.tarang.ewallet.model.BusinessOwnerInformation;
import com.tarang.ewallet.model.Merchant;
import com.tarang.ewallet.model.MerchantPayInfo;
import com.tarang.ewallet.util.QueryFilter;


public interface MerchantDao {

	BusinessInformation saveBusinessInformation(BusinessInformation businessInformation) throws WalletException;
	
	BusinessOwnerInformation saveBusinessOwnerInformation(BusinessOwnerInformation businessOwnerInformation) throws WalletException;
	
	MerchantPayInfo saveMerchantPayInfo(MerchantPayInfo merchantPayInfo) throws WalletException;
	
	List<UserDto> getMerchantsList(QueryFilter qf, String fromDate, String toDate, String name, String emailId, Long status) throws WalletException;
	
	BusinessInformation getBusinessInformation(Long id) throws WalletException;

	void updateBusinessInformation(BusinessInformation businessInformation) throws WalletException;

	BusinessOwnerInformation getBusinessOwnerInformation(Long id) throws WalletException;

	void updateBusinessOwnerInformation(BusinessOwnerInformation businessOwnerInformation) throws WalletException;

	Merchant getMerchant(Long id) throws WalletException;

	void updateMerchant(Merchant merchant) throws WalletException;
	
	Long getMerchantId(Long authenticationId, String userType) throws WalletException;

	Merchant getMerchantByAuthId(Long authId) throws WalletException;

	String getLegalName(String email);
	
	String getMerchantCode(String email);
	
	Merchant saveMerchant(Merchant merchant) throws WalletException;
	
	void updateMerchantPayInfo(MerchantPayInfo merchantPayInfo) throws WalletException ;
	
	MerchantPayInfo getMerchantPayInfo(Long merchantPayInfoId) throws WalletException ;

	void deleteMerchantPayInfo(MerchantPayInfo merchantPayInfo) throws WalletException;

	Merchant getMerchantByPhoneId(Long phoneId) throws WalletException;
}
