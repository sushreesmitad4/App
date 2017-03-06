/**
 * 
 */
package com.tarang.ewallet.usermgmt.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.FeedbackDto;
import com.tarang.ewallet.model.AdminUser;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.PersonName;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author prasadj
 * 
 */
public class DataPreparation {

	@SuppressWarnings("deprecation")
	public static List<Object> getDataPreparation() {

		List<Object> list = new ArrayList<Object>();

		PersonName person = new PersonName("sunil", "sabat");
		list.add(person);

		Authentication auth = new Authentication();

		// auth.setId((long)1);
		auth.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);
		auth.setEmailId("asdfd34@tarangtech.com");
		auth.setPassword("asdfasdf");
		auth.setHints((long) 1);
		auth.setResetPassword(false);
		auth.setLastLogin(new Date("9/10/2012"));
		auth.setActive(true);
		//auth.setDeleted(false);
		auth.setBlocked(false);
		auth.setAttempts(0);
		auth.setStatus((long) 1);
		auth.setEmailVarification(false);
		auth.setLoginStatus(false);
		auth.setKycRequired(false);

		list.add(auth);

		AdminUser adminUser = new AdminUser(new Long(1), new Long(1), new Long(
				1), new Long(1), new Long(1));
		list.add(adminUser);

		return list;
	}
	public static AdminUserDto prepareAdminUserDto() {
		AdminUserDto adminUserDto = new AdminUserDto();
		adminUserDto.setFirstName("super");
		adminUserDto.setLastName("admin");
		adminUserDto.setUserType(GlobalLitterals.ADMIN_USER_TYPE);
		adminUserDto.setEmailId("kedarnathd@tarangtech.com");
		adminUserDto.setPassword("bangalore");
		adminUserDto.setCountryId(1L);
		adminUserDto.setStateId(1L);
		adminUserDto.setCity("Bangalore");
		adminUserDto.setAddressOne("Bangalore");
		adminUserDto.setAddressTwo("Bangalore1");
		adminUserDto.setStatus(1L);
		adminUserDto.setZipcode("123132");
		adminUserDto.setRoleId(new Long(1));
		adminUserDto.setPhoneCode("8234");
		adminUserDto.setPhoneNo("123456789");

		return adminUserDto;
	}
	public static AdminUserDto prepareAdminUserDto1() {
		AdminUserDto adminUserDto = new AdminUserDto();
		adminUserDto.setFirstName("super");
		adminUserDto.setLastName("admin");
		adminUserDto.setUserType(GlobalLitterals.ADMIN_USER_TYPE);
		adminUserDto.setEmailId("daskedar2003@yahoo.com");
		adminUserDto.setPassword("bangalore");
		adminUserDto.setCountryId(1L);
		adminUserDto.setStateId(1L);
		adminUserDto.setCity("Bangalore");
		adminUserDto.setAddressOne("Bangalore");
		adminUserDto.setAddressTwo("Bangalore");
		adminUserDto.setStatus(1L);
		adminUserDto.setZipcode("123132");
		adminUserDto.setRoleId(new Long(1));
		adminUserDto.setPhoneCode("3234");
		adminUserDto.setPhoneNo("123456789");

		return adminUserDto;
	}

	public static List<AdminUserDto> getAUDataPreparation() {

		List<AdminUserDto> list = new ArrayList<AdminUserDto>();

		AdminUserDto adminUserDto = null;

		for (int i = 0; i < 30; i++) {
			adminUserDto = new AdminUserDto();
			adminUserDto.setFirstName("firstName" + i);
			adminUserDto.setLastName("lastName" + i);
			adminUserDto.setUserType(GlobalLitterals.ADMIN_USER_TYPE);
			adminUserDto.setEmailId("bangalore102@tarangtech.com" + i);
			adminUserDto.setPassword("bangalore" + i);
			adminUserDto.setCountryId(12L);
			adminUserDto.setStateId(10L);
			adminUserDto.setCity("Bangalore" + i);
			adminUserDto.setAddressOne("Bangalore" + i);
			adminUserDto.setAddressTwo("Bangalore" + i);
			adminUserDto.setStatus(1L);
			adminUserDto.setZipcode("123132" + i);
			adminUserDto.setRoleId(new Long(i));
			adminUserDto.setPhoneCode("4" +i);
			adminUserDto.setPhoneNo("987456" +i);
			list.add(adminUserDto);
		}
		return list;

	}
	public static String dateToString(Date date){
		  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		  return formatter.format(date);
	}
	
	/*
	 * Data Preparation related to admin feedback reply
	 */
	public static FeedbackDto createFeedbackDto(){
		
		FeedbackDto feedbackDto = new FeedbackDto();
		feedbackDto.setQuerryType(1L);
		feedbackDto.setSubject("question");
		feedbackDto.setMessage("can i open another account with same id");
		feedbackDto.setDateAndTime(new Date());
		feedbackDto.setUserType(GlobalLitterals.CUSTOMER_USER_TYPE);
		feedbackDto.setUserId(11L);
		feedbackDto.setUserMail("abhi@gmail.com");
		
		return feedbackDto;
	}
}
