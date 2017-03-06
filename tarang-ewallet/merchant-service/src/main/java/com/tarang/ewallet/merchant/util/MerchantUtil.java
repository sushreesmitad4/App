package com.tarang.ewallet.merchant.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.BusinessInformation;
import com.tarang.ewallet.model.BusinessOwnerInformation;
import com.tarang.ewallet.model.Hints;
import com.tarang.ewallet.model.Merchant;
import com.tarang.ewallet.model.MerchantPayInfo;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.model.PhoneNumber;
import com.tarang.ewallet.util.GlobalLitterals;


public class MerchantUtil {
    public  static final String BUSINESS_INFORMATION = "BusinessInformation";
	public  static final String BUSINESS_OWNER_INFORMATION = "BusinessOwnerInformation";
	public  static final String CUSTOMER_SERVICE_INFORMATION = "CustomerServiceInformation";

	public static Hints getHintsObj(MerchantDto merchantDto){
		Hints hints = new Hints();
		hints.setHintQuestion1(merchantDto.getQuestion1());
		hints.setHintAnswer1(merchantDto.getHint1());
		
		return hints;
	}
	
	public static Authentication getAuthenticationObj(MerchantDto merchantDto, Authentication authentication) throws WalletException{
		authentication.setUserType(GlobalLitterals.MERCHANT_USER_TYPE);
		authentication.setEmailId(merchantDto.getEmailId());
		authentication.setResetPassword(CommonConstrain.FALSE);
		authentication.setActive(CommonConstrain.FALSE);
		authentication.setBlocked(CommonConstrain.FALSE);
		authentication.setmPinBlocked(CommonConstrain.FALSE);
		authentication.setAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
		authentication.setmPinAttempts(CommonConstrain.DEFAULT_ATTEMPTS);
		authentication.setStatus(CommonConstrain.DEFAULT_STATUS); 
		authentication.setEmailVarification(CommonConstrain.FALSE);
		authentication.setLoginStatus(CommonConstrain.FALSE);
		authentication.setKycRequired(CommonConstrain.FALSE);
		authentication.setCreationDate(new Date());
		/*These values are need to set at the time of mobile wallet registration, 
		 * now set it to null*/
		authentication.setMsisdnNumber(null);
		authentication.setSimNumber(null);
		authentication.setImeiNumber(null);
		
		return authentication;
	}
	
	public static  PhoneNumber getPhoneNumberObj(MerchantDto merchantDto,
			String information) {
		
		PhoneNumber phoneNumber = new PhoneNumber();
		if (information.equalsIgnoreCase(BUSINESS_INFORMATION)) {
			phoneNumber.setCode(merchantDto.getPhoneCountryCode1());
			phoneNumber.setPnumber(merchantDto.getPhoneNumber());
		} else if(information.equalsIgnoreCase(CUSTOMER_SERVICE_INFORMATION)){
			/*As in input page it is commented, so passing value as zero*/
			phoneNumber.setCode(merchantDto.getCode());
			phoneNumber.setPnumber(merchantDto.getPhone());
		}
		return phoneNumber;
	}

	public  static Address getAddressObj(MerchantDto merchantDto, String information) {
		Address address = new Address();
		if (information.equalsIgnoreCase(BUSINESS_INFORMATION)) {
			address.setAddressOne(merchantDto.getAddress1BI());
			address.setAddressTwo(merchantDto.getAddress2BI());
			address.setCountry(merchantDto.getCountryBI());
			address.setRegion(merchantDto.getStateorRegionBI());
			address.setCity(merchantDto.getCityOrTownBI());
			address.setZipcode(merchantDto.getPostalCodeBI());
		} else if(information.equalsIgnoreCase(BUSINESS_OWNER_INFORMATION)){
			address.setAddressOne(merchantDto.getAddress1BO());
			address.setAddressTwo(merchantDto.getAddress2BO());
			address.setCountry(merchantDto.getCountryBO());
			address.setRegion(merchantDto.getStateOrRegionBO());
			address.setCity(merchantDto.getCityOrTownBO());
			address.setZipcode(merchantDto.getPostalCodeBO());
		}
		return address;
	}
	
	public static  PersonName getPersonNameObj(MerchantDto merchantDto){
		PersonName personName = new PersonName();
		personName.setFirstName(merchantDto.getFirstName());
		personName.setLastName(merchantDto.getLastName());
		return personName;
	}
	
	public static  BusinessInformation getBusinessInformationObj(
			MerchantDto merchantDto, Long addressId, Long phoneId) {
		BusinessInformation businessInformation = new BusinessInformation();

		businessInformation.setOwnerType(merchantDto.getOwnerType());
		businessInformation.setLegalName(merchantDto.getBusinessLegalname());
		businessInformation.setPhone(phoneId);
		businessInformation.setCategory(merchantDto.getBusinessCategory());
		businessInformation.setSubCategory(merchantDto.getSubCategory());
		businessInformation.setAddress(addressId);
		businessInformation.setEstablishedMonth(merchantDto.getBusinessEstablishedMonth());
		businessInformation.setEstablishedYear(merchantDto.getBusinessEstablishedYear());
		businessInformation.setCurrency(merchantDto.getCurrency());
		businessInformation.setAverageTxnAmount(merchantDto.getAverageTransactionAmount());
		businessInformation.setHeighestMonthlyVolume(merchantDto.getHighestMonthlyVolume());
		businessInformation.setPercentageOfAnnualRevenue(merchantDto.getPercentageOfAnnualRevenueFromOnlineSales());
		businessInformation.setWebsite(merchantDto.getWebsite());
		return businessInformation;
	}

	public  static BusinessOwnerInformation getBusinessOwnerInformationObj(
			Long nameId, Long addressId,Boolean homeAddress) {

		BusinessOwnerInformation businessOwnerInformation = new BusinessOwnerInformation();
		businessOwnerInformation.setNameId(nameId);
		businessOwnerInformation.setAddressId(addressId);
		businessOwnerInformation.setHomeAddress(homeAddress);
		return businessOwnerInformation;
	}

	public static  Merchant getMerchantObj(Long authId, Long busInfoId,
			Long busOwnerInfoId, String email, Long phoneId, Long merchantId) {
		Merchant merchant = new Merchant();
		merchant.setAuthenticationId(authId);
		merchant.setBusinessInformation(busInfoId);
		merchant.setBusinessOwnerInformation(busOwnerInfoId);
		merchant.setCustomerServiceEmail(email);
		merchant.setPageInfoId(merchantId);
		if(phoneId != null){
			merchant.setCustomerServicePhone(phoneId);
		}
		return merchant;
	}

	public static  MerchantPayInfo getMerchantPayInfo(String merchantCode, String successUrl, String failureUrl) {
		MerchantPayInfo merPayInfo = new MerchantPayInfo();
		merPayInfo.setMerchantCode(merchantCode);
		merPayInfo.setSuccessUrl(successUrl);
		merPayInfo.setFailureUrl(failureUrl);
		return merPayInfo;
	}
	
	
	public static  MerchantDto constructMerchantDtoWithDefaultVal(
			MerchantDto merchantDto, Authentication authentication,
			Merchant merchant){
		
		merchantDto.setId(merchant.getId());
		merchantDto.setLegalName(merchantDto.getLegalName());
		merchantDto.setActive(authentication.isActive());
		merchantDto.setStatus(authentication.getStatus());
		merchantDto.setBlocked(authentication.isBlocked());
		merchantDto.setCreationDate(authentication.getCreationDate());
		merchantDto.setAuthenticationId(authentication.getId());
		return merchantDto;
	}
	 
	public static String dateToYYYYMMdd(Date date) {
		SimpleDateFormat monthDayformatter = new SimpleDateFormat("MM/dd/yyyy");
		return monthDayformatter.format(date);
    }
}
