package com.tarang.ewallet.walletui.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tarang.ewallet.util.FeeTaxConstants;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.constants.Admin;
import com.tarang.ewallet.walletui.form.FeeMgmtForm;
import com.tarang.ewallet.walletui.validator.common.Common;


@SuppressWarnings({"unchecked", "rawtypes"})
public class FeePopulationUtil implements Admin, AttributeConstants, FeeTaxConstants {
	
	public static final int FEE_ADD = 1;

	public static final int FEE_EDIT = 2;

	public static void populateMasterDataLists(Map model, HttpServletRequest request, Long serviceType, FeeMgmtForm feeMgmtForm, int isAdd){
		
		if(FEE_ADD == isAdd){
			model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		}
		if(FEE_FINANTIAL_TYPE.equals(serviceType)){
			
			if(FEE_ADD == isAdd){
				model.put(USER_TYPE_LIST, MasterDataUtil.getSimpleDataMapForUserTypeCustMer(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID), 
						MasterDataConstants.MD_USER_TYPES));
	
				model.put(CURRENCY_LIST, MasterDataUtil.getCurrencyNames(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID)));
				
				if(feeMgmtForm.getServices() != null &&!feeMgmtForm.getServices().equals(0L) 
						&& feeMgmtForm.getUserType() != null &&!feeMgmtForm.getUserType().equals(0L)){
					model.put(OPERATION_TYPE_LIST, MasterDataUtil.getOperationTypes(
							request.getSession().getServletContext(), 
							(Long) request.getSession().getAttribute(LANGUAGE_ID),
							feeMgmtForm.getServices(), feeMgmtForm.getUserType()));
				}
			} else {
				feeMgmtForm.setValidation(Common.FINANCIAL_EDIT_FEE_VALIDATOR);
			}
			
			model.put(FEE_TYPE_LIST, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), 
					MasterDataUtil.FEE_TYPES));

			model.put(PEYING_ENTITY_LIST, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), 
					MasterDataUtil.FEE_PAYING_ENTITIES));
			
		} else if(FEE_NON_FINANTIAL_VARY_TYPE.equals(serviceType)){
			
			if(FEE_ADD == isAdd){
				model.put(FEE_TIME_FREEQUENCY_LIST, MasterDataUtil.getSimpleDataMap(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID),
						MasterDataConstants.FEE_TIME_FREQUENCY));
				
				model.put(OPERATION_TYPE_LIST, MasterDataUtil.getOperationTypes(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID),
						feeMgmtForm.getServices(), feeMgmtForm.getUserType()));
				
				feeMgmtForm.setUserTypeName(MasterDataUtil.getUserTypeName(request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID), feeMgmtForm.getUserType()));				
				
				if(feeMgmtForm != null){
					feeMgmtForm.setCurrency(null);
				}
			} else {
				feeMgmtForm.setValidation(Common.NON_FINANCIAL_VARY_EDIT_FEE_VALIDATOR);
			}
			
			model.put(FEE_TIME_FREEQUENCY_LIST, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.FEE_TIME_FREQUENCY));
			
			model.put(FEE_TYPE_LIST, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), 
					MasterDataUtil.FEE_TYPES));

		} else if(FEE_NON_FINANTIAL_TYPE.equals(serviceType)){
			if(FEE_ADD == isAdd){
				model.put(USER_TYPE_LIST, MasterDataUtil.getSimpleDataMapForUserTypeCustMer(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID), 
						MasterDataConstants.MD_USER_TYPES));
				
				if(feeMgmtForm.getServices() != null &&!feeMgmtForm.getServices().equals(0L) 
						&& feeMgmtForm.getUserType() != null &&!feeMgmtForm.getUserType().equals(0L)){
					model.put(OPERATION_TYPE_LIST, MasterDataUtil.getOperationTypes(
							request.getSession().getServletContext(), 
							(Long) request.getSession().getAttribute(LANGUAGE_ID),
							feeMgmtForm.getServices(), feeMgmtForm.getUserType()));
					feeMgmtForm.setCurrency(null);
				}
			} else {
				feeMgmtForm.setValidation(Common.NON_FINANCIAL_EDIT_FEE_VALIDATOR);
			}
			model.put(FEE_TIME_FREEQUENCY_LIST, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.FEE_TIME_FREQUENCY));
			
		}
	}
}