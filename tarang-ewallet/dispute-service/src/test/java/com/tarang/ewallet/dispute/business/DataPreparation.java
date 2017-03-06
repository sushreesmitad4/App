package com.tarang.ewallet.dispute.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.util.GlobalLitterals;

public class DataPreparation {
	
	public static void prepareDisputeDto(DisputeDto disputeDto){
		disputeDto.setTransactionId(1L);
		disputeDto.setApprovedamount(1000.00);
		disputeDto.setApprovedAmount(1000.00);
		disputeDto.setApprovedcurrency("1");
		disputeDto.setDisputetype("2");
		disputeDto.setType(2L);
		disputeDto.setMessage("dispute message");
		disputeDto.setPayeeemailid("vasanthar@tarangtech.com");
		disputeDto.setPayeremailid("kedarnathd@tarangtech.com");
		disputeDto.setRequestamount(1000.00);
		disputeDto.setRequestAmount(1000.00);
		disputeDto.setRequestcurrency("1");
		disputeDto.setRequestCurrency(1L);
		disputeDto.setStatus(2L);
		disputeDto.setTxnid(new BigDecimal("1"));
		disputeDto.setCreator(new BigDecimal(GlobalLitterals.CUSTOMER_USER_TYPE_ID.toString()));
		//disputeDto.setCreator(1L);
		disputeDto.setUpdationdate(new Date());
		disputeDto.setCreationdate(new Date());		
	}
	
	@SuppressWarnings("deprecation")
	public static CustomerDto getCustomerDto(){
		
		CustomerDto dto = new CustomerDto();
		
    	dto.setEmailId("vasanthar@tarangtech.com");
    	dto.setPassword("aaaa1A@");
    	dto.setHintQuestion1(new Long(1));
    	dto.setAnswer1("answer1");
    	dto.setHintQuestion2(new Long(2));
    	dto.setAnswer2("answer2");
    	dto.setTitle(1L);
    	dto.setFirstName("First name");
    	dto.setLastName("Last name");
    	dto.setPhoneCode("91");
    	dto.setPhoneNo("801234");
    	
    	dto.setAddrOne("address one");
    	dto.setAddrTwo("address two");
    	dto.setCity("Bangalore");
    	dto.setState(1L);
    	dto.setCountry(1L);
    	dto.setPostelCode("560048");
    	dto.setDateOfBirth(new Date("10/10/1980"));
    	dto.setActive(false);
    	dto.setBlocked(false);
    	dto.setStatus(4L);
    	dto.setKycRequired(false);
    	dto.setLanguageId(1L);
    	dto.setDefaultCurrency(1L);

		return dto;
	}
	
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
    	
    	return merchantDto;
	}
	
	public static void getReceiveMoney(RequestMoneyDto receiveMoneyDto){
		receiveMoneyDto.setRequesterEmail("vasanthar@tarangtech.com");
		receiveMoneyDto.setRequesterName("First name");
		receiveMoneyDto.setAmount(20000.00);
		receiveMoneyDto.setCurrencyId(1L);
		receiveMoneyDto.setRequesterMsg("Request Msg");
		receiveMoneyDto.setRequestDate(new Date());
		receiveMoneyDto.setResponserEmail("vasanthar@tarangtech.com");
		receiveMoneyDto.setResponserName("First name");
		receiveMoneyDto.setResponserMsg("Response Msg");
		receiveMoneyDto.setResponseDate(new Date());
		receiveMoneyDto.setStatus(1L);
		receiveMoneyDto.setCountryId(1L);
	}
	
	public static void dataPrepCreateForFeeDto(FeeDto feeDto){
		feeDto.setUserType(2L);
		feeDto.setServices(2L);
		feeDto.setOperationType(2L);
		feeDto.setCountry(1L);
		feeDto.setCurrency(1L);
		feeDto.setPayingentity(2L);
		feeDto.setFeeType(2L);
		feeDto.setFixCharSen(666.666);
		feeDto.setPercentageSen(666.666);
		feeDto.setFixCharRec(666.666);
		feeDto.setPercentageRec(666.666);
	
		List<FeeSlab> fsList = new ArrayList<FeeSlab>();
		fsList.add(new FeeSlab(0.0, 100.0, 500.0, 10.0, 500.0, 10.0));
		fsList.add(new FeeSlab(100.0, 1000.0, 1000.0, 20.0, 1000.0, 20.0));
		feeDto.setFeeSlabs(fsList);
	}
	
	public static SendMoneyDto getSendMoneyDto(CustomerDto dto,
			MerchantDto merchantDto) {
		SendMoneyDto sendMoneyDto = new SendMoneyDto();
		   sendMoneyDto.setIpAddress("172.30.66.177");
		   sendMoneyDto.setActualAmount(10.00);
		   sendMoneyDto.setRequestedAmount(10.00);
		   sendMoneyDto.setRequestedCurrency(1L);
		   sendMoneyDto.setActualCurrency(1L);
		   sendMoneyDto.setSenderAuthId(dto.getAuthenticationId());
		   sendMoneyDto.setReceiverAuthId(merchantDto.getAuthenticationId());
		   sendMoneyDto.setTransactionType(WalletTransactionTypes.P_TO_M);
		   sendMoneyDto.setEmailId(merchantDto.getEmailId());
		return sendMoneyDto;
	}
}
