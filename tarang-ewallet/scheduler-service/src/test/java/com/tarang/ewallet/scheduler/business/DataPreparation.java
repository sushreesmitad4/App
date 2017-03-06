/**
 * 
 */
package com.tarang.ewallet.scheduler.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.model.MasterAmountWallet;
import com.tarang.ewallet.model.MasterFeeWallet;
import com.tarang.ewallet.model.MasterTaxWallet;
import com.tarang.ewallet.scheduler.util.JobConstants;
import com.tarang.ewallet.scheduler.util.SchedulerGroupNames;
import com.tarang.ewallet.util.DateConvertion;


/**
 * @author Hari
 *
 */
public class DataPreparation {
		
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
    	dto.setPhoneNo("80123411");
    	
    	dto.setAddrOne("address one");
    	dto.setAddrTwo("address two");
    	dto.setCity("Bangalore");
    	dto.setState(1L);
    	dto.setCountry(1L);
    	dto.setPostelCode("560048");
    	dto.setDateOfBirth(new Date("10/10/1980"));
    	dto.setActive(true);
    	dto.setBlocked(false);
    	dto.setStatus(4L);
    	dto.setKycRequired(false);
    	dto.setLanguageId(1L);
    	dto.setDefaultCurrency(1L);
    	dto.setDefaultCurrency(1L);

		return dto;
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
	
	  public static List<Object> getMasterWalletsData(){
		List<Object> list = new ArrayList<Object>();
		
		// MasterAmountWallet (id, currency, amount)
		list.add(new MasterAmountWallet(1L, 1L, 0.0));
		list.add(new MasterAmountWallet(2L, 2L, 0.0));
		list.add(new MasterAmountWallet(3L, 3L, 0.0));

		// MasterFeeWallet( id, currency, fee)
		list.add(new MasterFeeWallet(1L, 1L, 0.0));
		list.add(new MasterFeeWallet(2L, 2L, 0.0));
		list.add(new MasterFeeWallet(3L, 3L, 0.0));

		// MasterTaxWallet( id, currency, tax)
		list.add(new MasterTaxWallet(1L, 1L, 0.0));
		list.add(new MasterTaxWallet(2L, 2L, 0.0));
		list.add(new MasterTaxWallet(3L, 3L, 0.0));

		return list;
	}
	  
	public static Map<String, String> getJobProperties(){ 
	  Map<String, String> jobProperties = new HashMap<String, String>();
		jobProperties.put(JobConstants.GROUP_NAME, SchedulerGroupNames.SEND_MONEY_RECURRING);
		jobProperties.put(JobConstants.USER_JOB_NAME, "Customer Job One");
		jobProperties.put(JobConstants.AUTH_ID, "1");
		jobProperties.put(JobConstants.SEND_MONEY_ID, "1");
		jobProperties.put(JobConstants.FROM_DATE, DateConvertion.dateToString(new Date()));
		jobProperties.put(JobConstants.TO_DATE, DateConvertion.dateToString(DateConvertion.getFutureDateByHours(24)));
		jobProperties.put(JobConstants.SEND_MONEY_FREQUENCY, new Long(1).toString());
		return jobProperties;
	}
	
	public static Map<String, String> getJobUnblockUserBlockProperties(){
		Map<String, String> jobProperties = new HashMap<String, String>();
		jobProperties.put(JobConstants.GROUP_NAME, SchedulerGroupNames.UNBLOCKED_USERS);
		jobProperties.put(JobConstants.USER_JOB_NAME, JobConstants.UNBLOCKED_USER_JOB_NAME);
		jobProperties.put(JobConstants.AUTH_ID, "1");
		jobProperties.put(JobConstants.PERSON_NAME, "Unblocked user");
		jobProperties.put(JobConstants.USER_TYPE_NAME, "customer");
		
		return jobProperties;
	}
}
