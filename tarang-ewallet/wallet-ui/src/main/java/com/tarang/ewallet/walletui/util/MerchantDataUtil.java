package com.tarang.ewallet.walletui.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.merchant.util.MerchantUtil;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.form.MerchantForm;
import com.tarang.ewallet.walletui.form.MerchantMgmtView;


public class MerchantDataUtil {
	
	public static MerchantMgmtView convertMerchantDtoToMerchantMgmtView(
			HttpServletRequest request, MerchantDto merchantDto) {
		
		MerchantMgmtView merchantFormView = new MerchantMgmtView();

		/* Business information setting in to MerchantDto for view addressBI */
		merchantFormView.setAddress1BI(merchantDto.getAddress1BI());
		merchantFormView.setAddress2BI(merchantDto.getAddress2BI());
		merchantFormView.setCityOrTownBI(merchantDto.getCityOrTownBI());

		merchantFormView.setCountryBI(MasterDataUtil.getCountryName(
				request, MasterDataUtil.MD_COUNTRIES, merchantDto.getCountryBI()));
		
		merchantFormView.setStateorRegionBI(MasterDataUtil.getRegions(
				request.getSession().getServletContext(), 
				(Long) request.getSession(false).getAttribute(AttributeConstants.LANGUAGE_ID), 
				merchantDto.getCountryBI()).get(merchantDto.getStateorRegionBI()));
		
		merchantFormView.setPostalCodeBI(merchantDto.getPostalCodeBI());
		// phoneNumberBI
		merchantFormView.setPhoneCountryCode1(merchantDto.getPhoneCountryCode1());
		merchantFormView.setPhoneNumber(merchantDto.getPhoneNumber());
		// Business Information
		merchantFormView.setOwnerType(getSimpleData(request,
				MasterDataUtil.MD_MERCHANT_OWNER_TYPES,
				merchantDto.getOwnerType()));
		merchantFormView.setBusinessLegalname(merchantDto.getBusinessLegalname());
		merchantFormView.setBusinessCategory(getSimpleData(
				request, MasterDataUtil.MD_MERCHANT_BUSINESS_CATEGORIES,
				merchantDto.getBusinessCategory()));
		merchantFormView.setSubCategory(getMerchantSubCategories(
				request, merchantDto.getBusinessCategory(), merchantDto.getSubCategory()));
		merchantFormView.setBusinessEstablishedMonth(merchantDto.getBusinessEstablishedMonth());
		merchantFormView.setBusinessEstablishedYear(merchantDto.getBusinessEstablishedYear());
		merchantFormView.setWebsite(merchantDto.getWebsite());
		merchantFormView.setCurrency(
				MasterDataUtil.getCurrencyName(request.getSession().getServletContext(), 
				(Long)request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID), 
				merchantDto.getCurrency()));
		merchantFormView.setAverageTransactionAmount(merchantDto.getAverageTransactionAmount().toString());
		merchantFormView.setHighestMonthlyVolume(merchantDto.getHighestMonthlyVolume().toString());
		merchantFormView.setPercentageOfAnnualRevenueFromOnlineSales(
				getSimpleData(request,MasterDataUtil.MD_MERCHANT_PERCENTAGE_ANUAL_REVENUES,
				merchantDto.getPercentageOfAnnualRevenueFromOnlineSales()));
		if(merchantDto.getMerchantCode()!=null){
			merchantFormView.setCodeCheck(true);
			merchantFormView.setMerchantCode(merchantDto.getMerchantCode());
			merchantFormView.setSuccessUrl(merchantDto.getSuccessUrl());
			merchantFormView.setFailureUrl(merchantDto.getFailureUrl());
			merchantFormView.setHomeAddress(merchantDto.getHomeAddress());
		} else{
			merchantFormView.setCodeCheck(false);
		}

		/* Business owner information information setting in to MerchantDto for view personNameBO */
		merchantFormView.setFirstName(merchantDto.getFirstName());
		merchantFormView.setLastName(merchantDto.getLastName());
		// is same as business address addressBO
		merchantFormView.setAddress1BO(merchantDto.getAddress1BO());
		merchantFormView.setAddress2BO(merchantDto.getAddress2BO());
		merchantFormView.setCityOrTownBO(merchantDto.getCityOrTownBO());
		merchantFormView.setCountryBO(MasterDataUtil.getCountryName(
				request, MasterDataUtil.MD_COUNTRIES, merchantDto.getCountryBO()));

		merchantFormView.setStateOrRegionBO(MasterDataUtil.getRegions(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID), 
				merchantDto.getCountryBO()).get(merchantDto.getStateOrRegionBO()));
		
		merchantFormView.setPostalCodeBO(merchantDto.getPostalCodeBO());

		/* Customer service information setting in to MerchantDto for view phoneNumberCSI */
		merchantFormView.setEmailCSI(merchantDto.getEmailCSI());
		merchantFormView.setCode(merchantDto.getCode());
		merchantFormView.setPhone(merchantDto.getPhone());

		// authentication
		merchantFormView.setActive(merchantDto.isActive());
		merchantFormView.setDeleted(merchantDto.isDeleted());
		merchantFormView.setStatus(MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID),
				MasterDataUtil.MD_USER_STATUSES).get(merchantDto.getStatus()));
		merchantFormView.setBlocked(merchantDto.isBlocked());
		merchantFormView.setCreationDate(MerchantUtil.dateToYYYYMMdd(merchantDto.getCreationDate()));
		merchantFormView.setEmailId(merchantDto.getEmailId());

		return merchantFormView;
	}

	public static void convertMerchantDtoToMerchantForm(
			MerchantForm merchantForm, MerchantDto merchantDto) {
		
		
			merchantForm.setQuestion1(merchantDto.getQuestion1());
			merchantForm.setHint1(merchantDto.getHint1());
			merchantForm.setQuestion2(merchantDto.getQuestion2());
			merchantForm.setHint2(merchantDto.getHint2());
		
		/* Business information setting in to MerchantDto for view */  
		merchantForm.setAddress1BI(merchantDto.getAddress1BI());
		merchantForm.setAddress2BI(merchantDto.getAddress2BI());
		merchantForm.setCityOrTownBI(merchantDto.getCityOrTownBI());

		merchantForm.setCountryBI(merchantDto.getCountryBI());
		merchantForm.setStateorRegionBI(merchantDto.getStateorRegionBI());

		merchantForm.setPostalCodeBI(merchantDto.getPostalCodeBI());
		// phoneNumberBI
		merchantForm.setPhoneCountryCode1(merchantDto.getPhoneCountryCode1());
		merchantForm.setPhoneNumber(merchantDto.getPhoneNumber());
		// Business Information
		merchantForm.setOwnerType(merchantDto.getOwnerType());
		merchantForm.setBusinessLegalname(merchantDto.getBusinessLegalname());
		merchantForm.setBusinessCategory(merchantDto.getBusinessCategory());
		merchantForm.setSubCategory(merchantDto.getSubCategory());
		merchantForm.setBusinessEstablishedMonth(merchantDto.getBusinessEstablishedMonth());
		merchantForm.setBusinessEstablishedYear(merchantDto.getBusinessEstablishedYear());
		merchantForm.setWebsite(merchantDto.getWebsite());
		merchantForm.setCurrency(merchantDto.getCurrency());
		merchantForm.setAverageTransactionAmount(merchantDto.getAverageTransactionAmount().toString());
		merchantForm.setHighestMonthlyVolume(merchantDto.getHighestMonthlyVolume().toString());
		merchantForm.setPercentageOfAnnualRevenueFromOnlineSales(merchantDto.getPercentageOfAnnualRevenueFromOnlineSales());
		merchantForm.setHomeAddress(merchantDto.getHomeAddress());
		merchantForm.setMerchantCode(merchantDto.getMerchantCode());
		merchantForm.setSuccessUrl(merchantDto.getSuccessUrl());
		merchantForm.setFailureUrl(merchantDto.getFailureUrl());
		if(merchantDto.getMerchantCode()== null){
			merchantForm.setCodeCheck(false);
		} else{
			merchantForm.setCodeCheck(true);
		}

		/* Business owner information information setting in to MerchantDto FORVIEW personNameBO */
		merchantForm.setFirstName(merchantDto.getFirstName());
		merchantForm.setLastName(merchantDto.getLastName());
		merchantForm.setAddress1BO(merchantDto.getAddress1BO());
		merchantForm.setAddress2BO(merchantDto.getAddress2BO());
		merchantForm.setCityOrTownBO(merchantDto.getCityOrTownBO());
		merchantForm.setCountryBO(merchantDto.getCountryBO());
		merchantForm.setStateOrRegionBO(merchantDto.getStateOrRegionBO());
		merchantForm.setPostalCodeBO(merchantDto.getPostalCodeBO());

		/* Customer service information setting in to MerchantDto for view phoneNumberCSI */
		merchantForm.setEmailCSI(merchantDto.getEmailCSI());
		merchantForm.setCode(merchantDto.getCode());
		merchantForm.setPhone(merchantDto.getPhone());

		// authentication
		merchantForm.setActive(merchantDto.isActive());
		merchantForm.setDeleted(merchantDto.isDeleted());
		merchantForm.setStatus(merchantDto.getStatus());
		
		merchantForm.setOldActive(merchantDto.isActive());
		merchantForm.setOldDeleted(merchantDto.isDeleted());
		merchantForm.setOldStatus(merchantDto.getStatus());
		
		merchantForm.setBlocked(merchantDto.isBlocked());
		merchantForm.setCreationDate(merchantDto.getCreationDate());
		merchantForm.setCreationDateAsMMDDYYY(MerchantUtil.dateToYYYYMMdd(merchantDto.getCreationDate()));
		merchantForm.setEmailId(merchantDto.getEmailId());

		
		//Existing phone number
		merchantForm.setExistPersonPhoneNo(merchantDto.getPhoneCountryCode1()+merchantDto.getPhoneNumber());
		merchantForm.setExistServicePhoneNo(merchantDto.getCode()+merchantDto.getPhone());
		
		merchantForm.setIpAddressCheck(merchantDto.getIpAddressCheck());
		merchantForm.setEmailPatternCheck(merchantDto.getEmailPatternCheck());
		merchantForm.setChargeBackCheck(merchantDto.getChargeBackCheck());

	}

	private static String getSimpleData(HttpServletRequest request, String type, Long propertyId) {
		return (String) MasterDataUtil.getSimpleData(request.getSession().getServletContext(), 
				(Long) request.getSession(false).getAttribute(AttributeConstants.LANGUAGE_ID), type, propertyId);
	}

	@SuppressWarnings("rawtypes")
	private static String getMerchantSubCategories(HttpServletRequest request,
			Long category, Long subCategory) {
		HashMap map = (HashMap) MasterDataUtil.getMerchantSubCategories(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID), category);
		return (String) map.get(subCategory);
	}
	
	public static Map<Long, Long> getMonthMap(){
		Map<Long, Long> map = new HashMap<Long, Long>();
		final int months = 12;
		for(int i = 1; i <= months; i++){
			map.put(new Long(i), new Long(i));
		}
		return map;
	}
	
	public static Map<Long, Long> getYearMap(){
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		final int years = 1900;
		Map<Long,Long> map = new LinkedHashMap<Long,Long>();
		for(int i = year; i > years; i--){
			map.put(new Long(i), new Long(i));
		}
		return map;
	}

	public static Map<Long, Long> getIssueDateYearMap(){
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		Map<Long,Long> map = new LinkedHashMap<Long,Long>();
		final int syear = 2000;
		for(int i = syear; i <= year; i++){
			map.put(new Long(i), new Long(i));
		}
		return map;
	}

	public static Map<Long, Long> getExpiryDateYearMap(){
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		final int eyear = 20;
		Map<Long, Long> map = new LinkedHashMap<Long, Long>();
		for(int i = year; i <= year + eyear; i++){
			map.put(new Long(i), new Long(i));
		}
		return map;
	}
    
	/**This method used to give the Merchant Form object 
	 * @param request
	 * @param merchantDto
	 * @return
	 */
	public static MerchantForm  convertMerchantDtoMerchantForm(HttpServletRequest request,
			MerchantDto merchantDto){
		
		MerchantForm merchantForm = new MerchantForm() ;
		
		merchantForm.setEmailId(merchantDto.getEmailId());
		
		merchantForm.setOwnerTypeName(MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID),
				MasterDataUtil.MD_MERCHANT_OWNER_TYPES).get(merchantDto.getOwnerType()));
		
		merchantForm.setBusinessLegalname(merchantDto.getBusinessLegalname());
		
		merchantForm.setCountryBIName(MasterDataUtil.getCountryNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID)).get(merchantDto.getCountryBI()));
		
		merchantForm.setAddress1BI(merchantDto.getAddress1BI());
		
		merchantForm.setCityOrTownBI(merchantDto.getCityOrTownBI());
		
		merchantForm.setStateorRegionBIName(MasterDataUtil.getRegions(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID), 
				merchantDto.getCountryBI()).get(merchantDto.getStateorRegionBI()));
		
		merchantForm.setPostalCodeBI(merchantDto.getPostalCodeBI());
		
		merchantForm.setPhoneCountryCode1(merchantDto.getPhoneCountryCode1());
		
		merchantForm.setPhoneNumber(merchantDto.getPhoneNumber());
		
		merchantForm.setBusinessCategoryName(MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID),
				MasterDataConstants.MD_MERCHANT_BUSINESS_CATEGORIES).get(merchantDto.getBusinessCategory()));
		
		merchantForm.setSubCategoryName(MasterDataUtil.getMerchantSubCategories(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID),
				merchantDto.getBusinessCategory()).get(merchantDto.getSubCategory()));
		
		merchantForm.setBusinessEstablishedMonth(merchantDto.getBusinessEstablishedMonth());
		
		merchantForm.setBusinessEstablishedYear(merchantDto.getBusinessEstablishedYear());
		
		merchantForm.setCurrencyName(MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID)).get(merchantDto.getCurrency()));		
		
		merchantForm.setAverageTransactionAmount(merchantDto.getAverageTransactionAmount().toString());
		
		merchantForm.setHighestMonthlyVolume(merchantDto.getHighestMonthlyVolume().toString());
		
		merchantForm.setPercentageOfAnnualRevenueFromOnlineSalesName(MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(AttributeConstants.LANGUAGE_ID),
				MasterDataConstants.MD_MERCHANT_PERCENTAGE_ANUAL_REVENUES).get(merchantDto.getPercentageOfAnnualRevenueFromOnlineSales()));
		
		merchantForm.setCodeCheck(merchantDto.getCodeCheck());
		
		merchantForm.setWebsite(merchantDto.getWebsite());
		
		merchantForm.setAverageTransactionAmount(merchantDto.getAverageTransactionAmount().toString());
		
		return merchantForm;
	}
	
}