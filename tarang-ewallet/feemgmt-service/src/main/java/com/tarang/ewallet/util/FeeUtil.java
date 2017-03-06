package com.tarang.ewallet.util;

import java.util.ArrayList;
import java.util.List;

import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.model.Fee;
import com.tarang.ewallet.model.FeeSlab;


public class FeeUtil {
	
	public static void createFee(Fee fee, FeeDto feeDto) throws CloneNotSupportedException {
		fee.setUserType(feeDto.getUserType());
		fee.setServices(feeDto.getServices());
		fee.setOperationType(feeDto.getOperationType());
		fee.setCountry(feeDto.getCountry());
		fee.setCurrency(feeDto.getCurrency());
		fee.setPayingentity(feeDto.getPayingentity());
		fee.setFeeType(feeDto.getFeeType());
		fee.setFixCharSen(feeDto.getFixCharSen());
		fee.setPercentageSen(feeDto.getPercentageSen());
		fee.setFixCharRec(feeDto.getFixCharRec());
		fee.setPercentageRec(feeDto.getPercentageRec());
		fee.setTimefreequency(feeDto.getTimeFreequency());
		
		List<FeeSlab> slabs = new ArrayList<FeeSlab>();
		if(feeDto.getFeeSlabs()!=null){
			for( FeeSlab slab: feeDto.getFeeSlabs()){
				slabs.add((FeeSlab)slab.clone());
			}
			fee.setFeeSlabs(slabs);
		}	
	}
	
	public static void loadFee(Fee fee, FeeDto feeDto) throws CloneNotSupportedException {
		
		feeDto.setId(fee.getId());
		feeDto.setUserType(fee.getUserType());
		feeDto.setServices(fee.getServices());
		feeDto.setOperationType(fee.getOperationType());
		feeDto.setCountry(fee.getCountry());
		feeDto.setCurrency(fee.getCurrency());
		feeDto.setTimeFreequency(fee.getTimefreequency());
		feeDto.setPayingentity(fee.getPayingentity());
		feeDto.setFeeType(fee.getFeeType());
		feeDto.setFixCharSen(fee.getFixCharSen());
		feeDto.setPercentageSen(fee.getPercentageSen());
		feeDto.setFixCharRec(fee.getFixCharRec());
		feeDto.setPercentageRec(fee.getPercentageRec());
		feeDto.setFeeSlabs(fee.getFeeSlabs());

		List<FeeSlab> slabs = new ArrayList<FeeSlab>();
		if(feeDto.getFeeSlabs()!=null){
			for( FeeSlab slab: fee.getFeeSlabs()){
				slabs.add((FeeSlab)slab.clone());
			}
		feeDto.setFeeSlabs(slabs);
		}
	}
	
	public static void update(Fee fee, FeeDto feeDto) throws CloneNotSupportedException {
		
		fee.setId(fee.getId());
		fee.setUserType(feeDto.getUserType());
		fee.setServices(feeDto.getServices());
		fee.setOperationType(feeDto.getOperationType());
		fee.setCountry(feeDto.getCountry());
		fee.setCurrency(feeDto.getCurrency());
		fee.setPayingentity(feeDto.getPayingentity());
		fee.setFeeType(feeDto.getFeeType());
		fee.setTimefreequency(feeDto.getTimeFreequency());
		fee.setFixCharSen(feeDto.getFixCharSen());
		fee.setPercentageSen(feeDto.getPercentageSen());
		fee.setFixCharRec(feeDto.getFixCharRec());
		fee.setPercentageRec(feeDto.getPercentageRec());

		List<FeeSlab> slabs = new ArrayList<FeeSlab>();
		 
		  for( FeeSlab slab: feeDto.getFeeSlabs()){
		   slabs.add((FeeSlab)slab.clone());
		  }
		  fee.setFeeSlabs(slabs);
	}
	
	public static FeeSlab getFeeSlabs(FeeDto feedto, Double transactionAmount){
		FeeSlab matchingFeeSlab = null;
		
		matchingFeeSlab = null;
		if(feedto != null){
			List<FeeSlab> feeSlabs = feedto.getFeeSlabs();
			if(feeSlabs != null){
				for(FeeSlab feeSlab:feeSlabs){
					if(feeSlabs.get(0).getLowerLimit().equals(transactionAmount) 
							|| (transactionAmount > feeSlab.getLowerLimit() &&  transactionAmount <= feeSlab.getUpperLimit())){
						matchingFeeSlab = new FeeSlab();
						matchingFeeSlab.setFixedChargeSender(feeSlab.getFixedChargeSender());
						matchingFeeSlab.setPercentageOfSender(feeSlab.getPercentageOfSender());
						matchingFeeSlab.setFixedChargeReceiver(feeSlab.getFixedChargeReceiver());
						matchingFeeSlab.setPercentageOfReceiver(feeSlab.getPercentageOfReceiver());
						break;
					}
				}
			}
		}
		if(matchingFeeSlab == null && feedto != null){
			matchingFeeSlab = new FeeSlab();
			matchingFeeSlab.setFixedChargeReceiver(feedto.getFixCharRec());
			matchingFeeSlab.setFixedChargeSender(feedto.getFixCharSen());
			matchingFeeSlab.setPercentageOfReceiver(feedto.getPercentageRec());
			matchingFeeSlab.setPercentageOfSender(feedto.getPercentageSen());
		}
		return matchingFeeSlab;
	}
	
}
