package com.tarang.ewallet.merchant.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.merchant.repository.MerchantRepository;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.BusinessInformation;
import com.tarang.ewallet.model.Merchant;
import com.tarang.ewallet.model.MerchantPayInfo;
import com.tarang.ewallet.util.QueryFilter;


public class MerchantServiceImpl implements MerchantService{
	
	private MerchantRepository merchantRepository;
	
	public MerchantServiceImpl(MerchantRepository merchantRepository) {
		this.merchantRepository = merchantRepository;
	}
	
	@Override
	public MerchantDto newregistration(MerchantDto merchantDto)
			throws WalletException {
	 return	merchantRepository.newregistration(merchantDto);
	}
	
	@Override
	public MerchantDto registrationAfter24hours(MerchantDto merchantDto) throws WalletException {
		return merchantRepository.registrationAfter24hours(merchantDto);
	}

	@Override
	public List<UserDto> getMerchantsList(QueryFilter qf, String fromDate,
			String toDate, String name, String emailId, Long status) throws WalletException {
		return	merchantRepository.getMerchantsList(qf, fromDate, toDate, name, emailId, status);

	}

	@Override
	public MerchantDto getMerchantDetailsById(Long merchantId) throws WalletException {
		return merchantRepository.getMerchantDetailsById(merchantId);
		
	}

	@Override
	public Boolean updateMerchantDetails(MerchantDto merchantDto,String adminOrmerchant)
			throws WalletException {
		merchantRepository.updateMerchantDetails(merchantDto,adminOrmerchant);
		return true;
	}

	@Override
	public Long getMerchantId(String userMail, String userType) throws WalletException {
		 return	merchantRepository.getMerchantId(userMail, userType);
	}
	
	@Override
	public String getPersonName(String userMail, String userType)throws WalletException {
		return merchantRepository.getPersonName(userMail, userType);
	}

	@Override
	public Address getMerchantAddress(Long merchantId) throws WalletException {
		return merchantRepository.getMerchantAddress(merchantId);
	}
	
	@Override
	public BusinessInformation getMerchantBusinessInfo(Long merchantId) throws WalletException {
		return merchantRepository.getMerchantBusinessInfo(merchantId);
	}
	
	

	@Override
	public MerchantDto getRegisteredMember(String userMail, String userType) throws WalletException {
		return merchantRepository.getRegisteredMember(userMail, userType);
	}

	@Override
	public Merchant getMerchantByAuthId(Long authId) throws WalletException {
		return merchantRepository.getMerchantByAuthId(authId);
	}

	@Override
	public String getLegalName(String email) throws WalletException {
		return merchantRepository.getLegalName(email);
	}

	@Override
	public MerchantPayInfo getMerchantPayInfo(String email) throws WalletException {
		return merchantRepository.getMerchantPayInfo(email);
	}

	@Override
	public MerchantDto getMerchantByPhoneNumber(String code, String phoneNumber) throws WalletException {
		return merchantRepository.getMerchantByPhoneNumber(code, phoneNumber);
	}
	
}
