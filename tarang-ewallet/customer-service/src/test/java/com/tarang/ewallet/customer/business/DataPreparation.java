/**
 * 
 */
package com.tarang.ewallet.customer.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.ForgotPasswordDto;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author  : prasadj
 * @date    : Oct 19, 2012
 * @time    : 3:03:24 PM
 * @version : 1.0.0
 * @comments: 
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
    	dto.setPhoneNo("801234245");
    	
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

		return dto;
	}
	
	@SuppressWarnings("deprecation")
	public static List<CustomerDto> getCustomerDtos(int total){
		
		List<CustomerDto> dtos = new ArrayList<CustomerDto>();
		
		CustomerDto dto = new CustomerDto();
		for(int i = 0; i < total; i++){
			dto = new CustomerDto();
	    	dto.setEmailId(i + "abc@tarangtech.com");
	    	dto.setPassword("xyz" + i);
	    	dto.setHintQuestion1(1L);
	    	dto.setAnswer1("answer1" + i);
	    	dto.setHintQuestion2(2L);
	    	dto.setAnswer2("answer2" + i);
	    	dto.setTitle(1L);
	    	dto.setFirstName("First name" + i);
	    	dto.setLastName("Last name" + i);
	    	dto.setPhoneCode("+91");
	    	dto.setPhoneNo("80123456" + i);
	    	
	    	dto.setAddrOne("address one" + i);
	    	dto.setAddrTwo("address two" + i);
	    	dto.setCity("Bangalore" + i);
	    	dto.setState(1L);
	    	dto.setCountry(1L);
	    	dto.setPostelCode("560048" + i);
	    	dto.setDateOfBirth(new Date("10/10/1980"));
	    	dto.setKycRequired(false);
	    	dto.setDefaultCurrency(1L);
	    	dtos.add(dto);
		}
		return dtos;
	}
	
	public static ChangePasswordDto prepareChangePasswordDto(){
		ChangePasswordDto changePasswordDto=new ChangePasswordDto();
		changePasswordDto.setEmailId("vasanthar@tarangtech.com");
		changePasswordDto.setConfirmNewPassword("vasanthar@tarangtech.com");
		changePasswordDto.setOldPassword("aaaa1A@");
		changePasswordDto.setNewPassword("Kedar@321");
		changePasswordDto.setConfirmNewPassword("Kedar@321");
		changePasswordDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);
		
		return changePasswordDto;
	}
	
	public static ForgotPasswordDto prepareForgotPasswordDto(){
		ForgotPasswordDto forgotPassDto = new ForgotPasswordDto();
		forgotPassDto.setEmailId("vasanthar@tarangtech.com");
		forgotPassDto.setQuestion1(new Long(1));
		forgotPassDto.setHint1("answer1");
		forgotPassDto.setLanguageId(1L);
		forgotPassDto.setUserType("C");
		return forgotPassDto;
	}
	public static Date makeDate(String dateString) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return formatter.parse(dateString);
	}

	public static String dateToString(Date date) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return formatter.format(date);
	}

}
