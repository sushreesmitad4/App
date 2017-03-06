/**
 * 
 */
package com.tarang.ewallet.merchant.business;

import java.util.ArrayList;
import java.util.List;

import com.tarang.ewallet.dto.MerchantDto;


/**
 * @author  : prasadj
 * @date    : Oct 19, 2012
 * @time    : 5:28:24 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class DataPreparation {

	public static MerchantDto getMerchantDto(){
    	MerchantDto merchantDto = new MerchantDto();
    	
    	merchantDto.setEmailId("kedarnathd@tarangtech.com");
    	merchantDto.setPassword("Kedar@123");

    	//merchantDto.setConfirmPassword("  ");
    	merchantDto.setQuestion1(1L);
    	merchantDto.setHint1("Ans1");//answer for question1
    	merchantDto.setQuestion2(2L);
    	merchantDto.setHint2("Ans2");//answer for question2
    	//merchantDto.setSecurityCode("F!pUac");//security code


    		// Business information
    	merchantDto.setOwnerType(1L);
    	merchantDto.setBusinessLegalname("businessLegalname");
    		
    	merchantDto.setAddress1BI("address1");
    	merchantDto.setAddress2BI("address2");
    	merchantDto.setCityOrTownBI("city");
    	merchantDto.setStateorRegionBI(1L);
    	merchantDto.setCountryBI(1L);
    	merchantDto.setPostalCodeBI("12345");
    		
    	merchantDto.setPhoneCountryCode1("1");
    	merchantDto.setPhoneNumber("phoneNumber");
    		
    	merchantDto.setBusinessCategory(1L);
    	merchantDto.setSubCategory(1L);
    	merchantDto.setBusinessEstablishedMonth("10");
    	merchantDto.setBusinessEstablishedYear("2012");
    	merchantDto.setWebsite("website");
    	merchantDto.setCurrency(1L);
    	merchantDto.setAverageTransactionAmount(10L);
    	merchantDto.setHighestMonthlyVolume(1000L);
    	merchantDto.setPercentageOfAnnualRevenueFromOnlineSales(1L);
    		
    		//Business Owner Information
    	merchantDto.setFirstName("firstName");
    	//merchantDto.setMiddleName("middleName");
    	merchantDto.setLastName("lastName");
    		
    	merchantDto.setHomeAddress(true); // is same as business address
    		
    	merchantDto.setAddress1BO("address1");
    	merchantDto.setAddress2BO("address2");
    	merchantDto.setCityOrTownBO("cityr");
    	merchantDto.setStateOrRegionBO(1L);
    	merchantDto.setCountryBO(1L);
    	merchantDto.setPostalCodeBO("12345");
    		
    		//Customer service information
    	merchantDto.setEmailCSI("email");
    	merchantDto.setCode("1");
    	merchantDto.setPhone("phone");
    	merchantDto.setDefaultCurrency(1L);
    	
    	merchantDto.setCodeCheck(true);
    	merchantDto.setMerchantCode("12354ABCDE");
    	merchantDto.setSuccessUrl("http://localhost:8080/wallet-ui/tarang/home/login");
    	merchantDto.setFailureUrl("http://localhost:8080/wallet-ui/tarang/home/login");
    	return merchantDto;
	}
	
	public static List<MerchantDto> getMerchantDtos(int total){
		
		List<MerchantDto> list = new ArrayList<MerchantDto>();
		MerchantDto merchantDto = null;
		for(int i = 0; i < total; i++){
			merchantDto = new MerchantDto();
	    	merchantDto.setEmailId("kedarnathd" + i + "@tarangtech.com");
	    	merchantDto.setPassword("Kedar@123");
	    	merchantDto.setQuestion1(1L);
	    	merchantDto.setHint1("Ans1" + i);
	    	merchantDto.setQuestion2(2L);
	    	merchantDto.setHint2("Ans2" + i);
	    	merchantDto.setOwnerType(1L);
	    	merchantDto.setBusinessLegalname("businessLegalname" + i);
	    		
	    	merchantDto.setAddress1BI("address1" + i);
	    	merchantDto.setAddress2BI("address2" + i);
	    	merchantDto.setCityOrTownBI("Bangalore" + i);
	    	merchantDto.setStateorRegionBI(1L);
	    	merchantDto.setCountryBI(1L);
	    	merchantDto.setPostalCodeBI("12345" + i);
	    		
	    	merchantDto.setPhoneCountryCode1("+91");
	    	merchantDto.setPhoneNumber("12345600" + i);
	    		
	    	merchantDto.setBusinessCategory(1L);
	    	merchantDto.setSubCategory(1L);
	    	merchantDto.setBusinessEstablishedMonth("10");
	    	merchantDto.setBusinessEstablishedYear("2012");
	    	merchantDto.setWebsite("website");
	    	merchantDto.setCurrency(1L);
	    	merchantDto.setAverageTransactionAmount(1L);
	    	merchantDto.setHighestMonthlyVolume(1L);
	    	merchantDto.setPercentageOfAnnualRevenueFromOnlineSales(1L);
	    		
	    		//Business Owner Information
	    	merchantDto.setFirstName("firstName");
	    //	merchantDto.setMiddleName("middleName");
	    	merchantDto.setLastName("lastName");
	    		
	    	merchantDto.setHomeAddress(true); // is same as business address
	    		
	    	merchantDto.setAddress1BO("address1");
	    	merchantDto.setAddress2BO("address2");
	    	merchantDto.setCityOrTownBO("cityr");
	    	merchantDto.setStateOrRegionBO(1L);
	    	merchantDto.setCountryBO(1L);
	    	merchantDto.setPostalCodeBO("12345");
	    	merchantDto.setDefaultCurrency(1L);	
	    		//Customer service information
	    	merchantDto.setEmailCSI(i + "email@abc.com");
	    	merchantDto.setCode("1");
	    	merchantDto.setPhone("1234567000" + i);
	    	list.add(merchantDto);
		}
		return list;
	}
}
