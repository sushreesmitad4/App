package com.tarang.ewallet.walletui.form;

import java.util.ArrayList;
import java.util.List;

import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.util.GlobalLitterals;


public class FeeMgmtForm {

	public static final int MAX_SLABS = 3;
	
	private Long id;
	
	private Long userType;
	
	private String userTypeName;
	
	private Long services;
	
	private String serviceName;
		
	private Long operationType;
	
	private String operationTypeName;
	
	private Long country;
	
	private String countryName;
	
	private Long currency;
	
	private String currencyName;
	
	private Long payingentity;
	
	private String payingentityName;
	
	private Long feeType;
	
	private String feeTypeName;
	
	private String fixCharSen;
	
	private String percentageSen;
	
	private String fixCharRec;
	
	private String percentageRec;
	
	//slab1 related properties
	
	private String lowerLimSlb1;
	
	private String upperLimSlb1;
	
	private String fixCharSenSlb1;
	
	private String percentageSenSlb1;
	
	private String fixCharRecSlb1;
	
	private String percentageRecSlb1;
	
	//slab2 related properties
	
	private String lowerLimSlb2;
	
	private String upperLimSlb2;
	
	private String fixCharSenSlb2;
	
	private String percentageSenSlb2;
	
	private String fixCharRecSlb2;
	
	private String percentageRecSlb2;
	
	//slab3 related properties
	
	private String lowerLimSlb3;
	
	private String upperLimSlb3;
	
	private String fixCharSenSlb3;
	
	private String percentageSenSlb3;
	
	private String fixCharRecSlb3;
	
	private String percentageRecSlb3;
	
	private Long timeFreequency;
	
	private String timeFreequencyName;
	
	private Integer validation;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	
	public Long getServices() {
		return services;
	}

	public void setServices(Long services) {
		this.services = services;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public Long getOperationType() {
		return operationType;
	}

	public void setOperationType(Long operationType) {
		this.operationType = operationType;
	}

	public String getOperationTypeName() {
		return operationTypeName;
	}

	public void setOperationTypeName(String operationTypeName) {
		this.operationTypeName = operationTypeName;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Long getPayingentity() {
		return payingentity;
	}

	public void setPayingentity(Long payingentity) {
		this.payingentity = payingentity;
	}

	public String getPayingentityName() {
		return payingentityName;
	}

	public void setPayingentityName(String payingentityName) {
		this.payingentityName = payingentityName;
	}

	public Long getFeeType() {
		return feeType;
	}

	public void setFeeType(Long feeType) {
		this.feeType = feeType;
	}

	public String getFeeTypeName() {
		return feeTypeName;
	}

	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}

	public String getFixCharSen() {
		return fixCharSen;
	}

	public void setFixCharSen(String fixCharSen) {
		this.fixCharSen = fixCharSen;
	}

	public String getPercentageSen() {
		return percentageSen;
	}

	public void setPercentageSen(String percentageSen) {
		this.percentageSen = percentageSen;
	}

	public String getFixCharRec() {
		return fixCharRec;
	}

	public void setFixCharRec(String fixCharRec) {
		this.fixCharRec = fixCharRec;
	}

	public String getPercentageRec() {
		return percentageRec;
	}

	public void setPercentageRec(String percentageRec) {
		this.percentageRec = percentageRec;
	}
	
	//slab1 related getters and setters
	public String getLowerLimSlb1() {
		return lowerLimSlb1;
	}

	public void setLowerLimSlb1(String lowerLimSlb1) {
		this.lowerLimSlb1 = lowerLimSlb1;
	}

	public String getUpperLimSlb1() {
		return upperLimSlb1;
	}

	public void setUpperLimSlb1(String upperLimSlb1) {
		this.upperLimSlb1 = upperLimSlb1;
	}

	public String getFixCharSenSlb1() {
		return fixCharSenSlb1;
	}

	public void setFixCharSenSlb1(String fixCharSenSlb1) {
		this.fixCharSenSlb1 = fixCharSenSlb1;
	}

	public String getPercentageSenSlb1() {
		return percentageSenSlb1;
	}

	public void setPercentageSenSlb1(String percentageSenSlb1) {
		this.percentageSenSlb1 = percentageSenSlb1;
	}

	public String getFixCharRecSlb1() {
		return fixCharRecSlb1;
	}

	public void setFixCharRecSlb1(String fixCharRecSlb1) {
		this.fixCharRecSlb1 = fixCharRecSlb1;
	}

	public String getPercentageRecSlb1() {
		return percentageRecSlb1;
	}

	public void setPercentageRecSlb1(String percentageRecSlb1) {
		this.percentageRecSlb1 = percentageRecSlb1;
	}

	//slab2 related getters and setters
	public String getLowerLimSlb2() {
		return lowerLimSlb2;
	}

	public void setLowerLimSlb2(String lowerLimSlb2) {
		this.lowerLimSlb2 = lowerLimSlb2;
	}

	public String getUpperLimSlb2() {
		return upperLimSlb2;
	}

	public void setUpperLimSlb2(String upperLimSlb2) {
		this.upperLimSlb2 = upperLimSlb2;
	}

	public String getFixCharSenSlb2() {
		return fixCharSenSlb2;
	}

	public void setFixCharSenSlb2(String fixCharSenSlb2) {
		this.fixCharSenSlb2 = fixCharSenSlb2;
	}

	public String getPercentageSenSlb2() {
		return percentageSenSlb2;
	}

	public void setPercentageSenSlb2(String percentageSenSlb2) {
		this.percentageSenSlb2 = percentageSenSlb2;
	}

	public String getFixCharRecSlb2() {
		return fixCharRecSlb2;
	}

	public void setFixCharRecSlb2(String fixCharRecSlb2) {
		this.fixCharRecSlb2 = fixCharRecSlb2;
	}

	public String getPercentageRecSlb2() {
		return percentageRecSlb2;
	}

	public void setPercentageRecSlb2(String percentageRecSlb2) {
		this.percentageRecSlb2 = percentageRecSlb2;
	}

	//slab3 related getters and setters
	public String getLowerLimSlb3() {
		return lowerLimSlb3;
	}

	public void setLowerLimSlb3(String lowerLimSlb3) {
		this.lowerLimSlb3 = lowerLimSlb3;
	}

	public String getUpperLimSlb3() {
		return upperLimSlb3;
	}

	public void setUpperLimSlb3(String upperLimSlb3) {
		this.upperLimSlb3 = upperLimSlb3;
	}

	public String getFixCharSenSlb3() {
		return fixCharSenSlb3;
	}

	public void setFixCharSenSlb3(String fixCharSenSlb3) {
		this.fixCharSenSlb3 = fixCharSenSlb3;
	}

	public String getPercentageSenSlb3() {
		return percentageSenSlb3;
	}

	public void setPercentageSenSlb3(String percentageSenSlb3) {
		this.percentageSenSlb3 = percentageSenSlb3;
	}

	public String getFixCharRecSlb3() {
		return fixCharRecSlb3;
	}

	public void setFixCharRecSlb3(String fixCharRecSlb3) {
		this.fixCharRecSlb3 = fixCharRecSlb3;
	}

	public String getPercentageRecSlb3() {
		return percentageRecSlb3;
	}

	public void setPercentageRecSlb3(String percentageRecSlb3) {
		this.percentageRecSlb3 = percentageRecSlb3;
	}
	
	public Long getTimeFreequency() {
		return timeFreequency;
	}

	public void setTimeFreequency(Long timeFreequency) {
		this.timeFreequency = timeFreequency;
	}
	
	public String getTimeFreequencyName() {
		return timeFreequencyName;
	}

	public void setTimeFreequencyName(String timeFreequencyName) {
		this.timeFreequencyName = timeFreequencyName;
	}
	
	public Integer getValidation() {
		return validation;
	}

	public void setValidation(Integer validation) {
		this.validation = validation;
	}

	public void setFeeMgmt(FeeDto feeMgmtDto) {
		if (feeMgmtDto != null) {
			id = feeMgmtDto.getId();
			userType = feeMgmtDto.getUserType();
			services = feeMgmtDto.getServices();
			operationType = feeMgmtDto.getOperationType();
			country = feeMgmtDto.getCountry();
			currency = feeMgmtDto.getCurrency();
			payingentity = feeMgmtDto.getPayingentity();
			feeType = feeMgmtDto.getFeeType();
			fixCharSen = setEmptyString(feeMgmtDto.getFixCharSen());
			percentageSen = setEmptyString(feeMgmtDto.getPercentageSen());
			fixCharRec = setEmptyString(feeMgmtDto.getFixCharRec());
			percentageRec = setEmptyString(feeMgmtDto.getPercentageRec());
			timeFreequency = feeMgmtDto.getTimeFreequency();
			List<FeeSlab> listFeeSlabs = feeMgmtDto.getFeeSlabs();
			FeeSlab feeSlab = null;
			
			if(!listFeeSlabs.isEmpty() ){
				if(listFeeSlabs.size() > 0){
					feeSlab = listFeeSlabs.get(0);
					lowerLimSlb1 = setEmptyString(feeSlab.getLowerLimit());
					upperLimSlb1 = setEmptyString(feeSlab.getUpperLimit());
					fixCharSenSlb1 = setEmptyString(feeSlab.getFixedChargeSender());
					percentageSenSlb1 = setEmptyString(feeSlab.getPercentageOfSender());
					fixCharRecSlb1 = setEmptyString(feeSlab.getFixedChargeReceiver());
					percentageRecSlb1 = setEmptyString(feeSlab.getPercentageOfReceiver());
				}
				if(listFeeSlabs.size() > 1){
					feeSlab = listFeeSlabs.get(1);
					lowerLimSlb2 = setEmptyString(feeSlab.getLowerLimit());
					upperLimSlb2 = setEmptyString(feeSlab.getUpperLimit());
					fixCharSenSlb2 = setEmptyString(feeSlab.getFixedChargeSender());
					percentageSenSlb2 = setEmptyString(feeSlab.getPercentageOfSender());
					fixCharRecSlb2 = setEmptyString(feeSlab.getFixedChargeReceiver());
					percentageRecSlb2 = setEmptyString(feeSlab.getPercentageOfReceiver());
				}
				if(listFeeSlabs.size() > 2){
					feeSlab = listFeeSlabs.get(2);
					lowerLimSlb3 = setEmptyString(feeSlab.getLowerLimit());
					upperLimSlb3 = setEmptyString(feeSlab.getUpperLimit());
					fixCharSenSlb3 = setEmptyString(feeSlab.getFixedChargeSender());
					percentageSenSlb3 = setEmptyString(feeSlab.getPercentageOfSender());
					fixCharRecSlb3 = setEmptyString(feeSlab.getFixedChargeReceiver());
					percentageRecSlb3 = setEmptyString(feeSlab.getPercentageOfReceiver());
				}	
			}
		}
	}

	public FeeDto getFeeMgmt() {
		FeeDto feeMgmtDto = new FeeDto();
		if (feeMgmtDto != null) {
			feeMgmtDto.setId(id);
			feeMgmtDto.setUserType(userType);
			feeMgmtDto.setServices(services);
			feeMgmtDto.setOperationType(operationType);
			feeMgmtDto.setCountry(country);
			feeMgmtDto.setCurrency(currency);
			feeMgmtDto.setPayingentity(payingentity);
			feeMgmtDto.setFeeType(feeType);
			feeMgmtDto.setFixCharSen(checkNull(fixCharSen));
			feeMgmtDto.setPercentageSen(checkNull(percentageSen));
			feeMgmtDto.setFixCharRec(checkNull(fixCharRec));
			feeMgmtDto.setPercentageRec(checkNull(percentageRec));
			feeMgmtDto.setTimeFreequency(timeFreequency);
			List<FeeSlab> listFeeSlab = new ArrayList<FeeSlab>(MAX_SLABS);
			
			if(upperLimSlb1 != null && !upperLimSlb1.equals(GlobalLitterals.EMPTY_STRING)){
				FeeSlab feeSlab=new FeeSlab();
				feeSlab.setLowerLimit(checkNull(lowerLimSlb1));
				feeSlab.setUpperLimit(checkNull(upperLimSlb1));
				feeSlab.setFixedChargeSender(checkNull(fixCharSenSlb1));
				feeSlab.setPercentageOfSender(checkNull(percentageSenSlb1));
				feeSlab.setFixedChargeReceiver(checkNull(fixCharRecSlb1));
				feeSlab.setPercentageOfReceiver(checkNull(percentageRecSlb1));
				listFeeSlab.add(feeSlab);
				feeMgmtDto.setFeeSlabs(listFeeSlab);
			}
			
			if(upperLimSlb2 != null && !upperLimSlb2.equals(GlobalLitterals.EMPTY_STRING)){
				FeeSlab feeSlab1=new FeeSlab();
				feeSlab1.setLowerLimit(checkNull(upperLimSlb1));
				feeSlab1.setUpperLimit(checkNull(upperLimSlb2));
				feeSlab1.setFixedChargeSender(checkNull(fixCharSenSlb2));
				feeSlab1.setPercentageOfSender(checkNull(percentageSenSlb2));
				feeSlab1.setFixedChargeReceiver(checkNull(fixCharRecSlb2));
				feeSlab1.setPercentageOfReceiver(checkNull(percentageRecSlb2));
				listFeeSlab.add(feeSlab1);
			}
			
			if(upperLimSlb3 != null && !upperLimSlb3.equals(GlobalLitterals.EMPTY_STRING)){
				FeeSlab feeSlab2=new FeeSlab();
				feeSlab2.setLowerLimit(checkNull(upperLimSlb2));
				feeSlab2.setUpperLimit(checkNull(upperLimSlb3));
				feeSlab2.setFixedChargeSender(checkNull(fixCharSenSlb3));
				feeSlab2.setPercentageOfSender(checkNull(percentageSenSlb3));
				feeSlab2.setFixedChargeReceiver(checkNull(fixCharRecSlb3));
				feeSlab2.setPercentageOfReceiver(checkNull(percentageRecSlb3));
				listFeeSlab.add(feeSlab2);
			}	
			feeMgmtDto.setFeeSlabs(listFeeSlab);
		}
		return feeMgmtDto;
	}

	private Double checkNull(String value){
		if(value == null || value.equals(GlobalLitterals.EMPTY_STRING)){
			return null;
		} else {
			return Double.parseDouble(value);
		}
	}

	private String setEmptyString(Double value){
		if(value == null){
			return GlobalLitterals.EMPTY_STRING;
		} else {
			return String.format(GlobalLitterals.DOUBLE_FORMAT, value);
		}
	}
	
}