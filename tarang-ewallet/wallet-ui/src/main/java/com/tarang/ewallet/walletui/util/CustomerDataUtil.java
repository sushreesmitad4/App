/**
 * 
 */
package com.tarang.ewallet.walletui.util;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.form.CustomerRegFormTwo;
import com.tarang.mwallet.rest.services.model.RestRequest;

/**
 * @author vasanthar
 *
 */
public class CustomerDataUtil implements AttributeConstants ,GlobalLitterals {
	
	public static CustomerRegFormTwo convertCustomerDtoToCustomerRegFormTwo(HttpServletRequest req, CustomerDto customerDto,
			Boolean editOrUpdatePage) {
		
		CustomerRegFormTwo crform = new CustomerRegFormTwo();
		if(editOrUpdatePage){
			crform.setEmailId(customerDto.getEmailId());
			crform.setHintQuestion1(customerDto.getHintQuestion1());
			crform.setHintQuestion2(customerDto.getHintQuestion2());
			crform.setAnswer1(customerDto.getAnswer1());
			crform.setAnswer2(customerDto.getAnswer2());
			crform.setActive(customerDto.isActive());
			crform.setBlocked(customerDto.isBlocked());
			crform.setDeleted(customerDto.isDeleted());
			crform.setStatus(customerDto.getStatus());
			//new
			/*crform.setPhoneCode(customerDto.getPhoneCode());
			crform.setPhoneNo(customerDto.getPhoneNo());*/
			
		}
		crform.setPtitle(customerDto.getTitle());
		crform.setFirstName(customerDto.getFirstName());
		crform.setLastName(customerDto.getLastName());
		crform.setAddrOne(customerDto.getAddrOne());
		crform.setAddrTwo(customerDto.getAddrTwo());
		crform.setCity(customerDto.getCity());
		crform.setCountry(customerDto.getCountry());
		crform.setState(customerDto.getState());
		crform.setPostalCode(customerDto.getPostelCode());
		crform.setPhoneCode(customerDto.getPhoneCode());
		crform.setPhoneNo(customerDto.getPhoneNo());
		crform.setDateOfBirth(DateConvertion.dateToString(customerDto.getDateOfBirth()));
		crform.setCreationDate(DateConvertion.dateToString(customerDto.getCreationDate()));
		return crform;
	}
	
	public static CustomerDto setCustomerDto(CustomerRegFormTwo customerRegFormTwo, CustomerDto customerDto) {

		customerDto.setTitle(customerRegFormTwo.getPtitle());
		customerDto.setFirstName(customerRegFormTwo.getFirstName());
		customerDto.setLastName(customerRegFormTwo.getLastName());
		customerDto.setDateOfBirth(DateConvertion.stirngToDate(customerRegFormTwo.getDateOfBirth()));
		customerDto.setCountry(customerRegFormTwo.getCountry());
		customerDto.setAddrOne(customerRegFormTwo.getAddrOne());
		customerDto.setAddrTwo(customerRegFormTwo.getAddrTwo());
		customerDto.setCity(customerRegFormTwo.getCity());
		customerDto.setState(customerRegFormTwo.getState());
		customerDto.setPostelCode(customerRegFormTwo.getPostalCode());
		customerDto.setPhoneCode(customerRegFormTwo.getPhoneCode());
		customerDto.setPhoneNo(customerRegFormTwo.getPhoneNo());
		customerDto.setPostelCode(customerRegFormTwo.getPostalCode());
		if (customerRegFormTwo.getStatus() != null) {
			customerDto.setStatus(customerRegFormTwo.getStatus());
		}
		if (customerRegFormTwo.getActive() != null){
			customerDto.setActive(customerRegFormTwo.getActive());
		}
		if (customerRegFormTwo.getDeleted() != null && customerRegFormTwo.getDeleted()){
			customerDto.setStatus(UserStatusConstants.DELETED);
		}
		if (customerRegFormTwo.getBlocked() != null){
			customerDto.setBlocked(customerRegFormTwo.getBlocked());
		}
		if (customerRegFormTwo.getKycRequired() != null){
			customerDto.setKycRequired(customerRegFormTwo.getKycRequired());
		}
		if (customerRegFormTwo.getIpAddressCheck() != null){
			customerDto.setIpAddressCheck(customerRegFormTwo.getIpAddressCheck());
		}
		if (customerRegFormTwo.getEmailPatternCheck() != null){
			customerDto.setEmailPatternCheck(customerRegFormTwo.getEmailPatternCheck());
		}
		if(customerRegFormTwo.getUpdateReason() != null){
			customerDto.setUpdateReason(customerRegFormTwo.getUpdateReason());
		}
		return customerDto;
	}
	
	public static CustomerDto setCustomerDto(RestRequest restRequest, CustomerDto customerDto) {
		customerDto.setTitle(restRequest.getPtitle());
		customerDto.setFirstName(restRequest.getFirstName());
		customerDto.setLastName(restRequest.getLastName());
		customerDto.setDateOfBirth(DateConvertion.stirngToDate(restRequest.getDateOfBirth()));
		customerDto.setCountry(restRequest.getCountry());
		customerDto.setAddrOne(restRequest.getAddrOne());
		customerDto.setAddrTwo(restRequest.getAddrTwo());
		customerDto.setCity(restRequest.getCity());
		customerDto.setState(restRequest.getState());
		customerDto.setPostelCode(restRequest.getPostalCode());
		customerDto.setPhoneCode(restRequest.getPhoneCode());
		customerDto.setPhoneNo(restRequest.getPhoneNo());
		customerDto.setPostelCode(restRequest.getPostalCode());
		return customerDto;
	}
	
	@SuppressWarnings("rawtypes")
	public static Boolean validateEmailForADuration(Long recordStatus, Date creationDate, Map model, Locale locale){
		Boolean errorFlag = false;
		if(null == recordStatus || null == creationDate){
			return errorFlag;
		}
		if(recordStatus.equals(CommonConstrain.EMAIL_REGISTER_ACC_DELETED)) {
			errorFlag = true;
		} else if(recordStatus.equals(CommonConstrain.EMAIL_ALLREADY_REGISTER)) {
			errorFlag = true;
		} else if(recordStatus.equals(CommonConstrain.EMAIL_REGISTER_NOT_VARIFIED)){
			Date todayDate = new Date();
			Integer diffInDays = (int) ((creationDate.getTime() - todayDate.getTime())/ GlobalLitterals.DAY_IN_MILLIS );
			if(diffInDays == 0){
				errorFlag = true;
			}
		}
		return errorFlag;
	}
	
	public static CustomerRegFormTwo convertCustomerDtoToCustomerForm(HttpServletRequest request, 
			CustomerDto customerDto){
		
		CustomerRegFormTwo customerRegFormTwo = new CustomerRegFormTwo();
		
		customerRegFormTwo.setHintQuestion1(customerDto.getHintQuestion1());
		customerRegFormTwo.setAnswer1(customerDto.getAnswer1());
		
		customerRegFormTwo.setTitleName(MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataConstants.TITLES).get(customerDto.getTitle()));
		
		customerRegFormTwo.setFirstName(customerDto.getFirstName());
		
		customerRegFormTwo.setLastName(customerDto.getLastName());
		
		customerRegFormTwo.setDateOfBirth(DateConvertion.dateToString(customerDto.getDateOfBirth()));
		
		customerRegFormTwo.setCountryName( MasterDataUtil.getCountryNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(customerDto.getCountry()));
		
		customerRegFormTwo.setAddrOne(customerDto.getAddrOne());
		customerRegFormTwo.setAddrTwo(customerDto.getAddrTwo());
		
		customerRegFormTwo.setCity(customerDto.getCity());
			
		customerRegFormTwo.setStateName(MasterDataUtil.getRegions(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), customerDto.getCountry()).get(customerDto.getState()));	
		
		customerRegFormTwo.setPhoneCode(customerDto.getPhoneCode());
		
		customerRegFormTwo.setPhoneNo(customerDto.getPhoneNo());
		
		customerRegFormTwo.setCreationDate(DateConvertion.dateToString(customerDto.getCreationDate()));
		
		customerRegFormTwo.setEmailId(customerDto.getEmailId());
		
		customerRegFormTwo.setPostalCode(customerDto.getPostelCode());
		
		return customerRegFormTwo;
	}
}	
	