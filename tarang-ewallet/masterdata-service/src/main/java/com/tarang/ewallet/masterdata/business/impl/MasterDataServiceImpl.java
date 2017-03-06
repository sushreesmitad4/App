/**
 * 
 */
package com.tarang.ewallet.masterdata.business.impl;


import java.util.List;
import java.util.Map;

import com.tarang.ewallet.masterdata.business.MasterDataService;
import com.tarang.ewallet.masterdata.repository.MasterDataRepository;
import com.tarang.ewallet.model.Country;
import com.tarang.ewallet.model.Currency;
import com.tarang.ewallet.model.FeeOperationType;
import com.tarang.ewallet.model.Language;
import com.tarang.ewallet.model.MasterVelocity;
import com.tarang.ewallet.model.Menu;
import com.tarang.ewallet.model.VelocitySetting;



/**
 * @author prasadj
 *
 */
public class MasterDataServiceImpl implements MasterDataService {

    private MasterDataRepository masterDataRepository;
    
    public MasterDataServiceImpl(MasterDataRepository masterDataRepository) {
        this.masterDataRepository = masterDataRepository;
    }

    @Override
	public Map<Long, Map<Long, Language>> getLanguages() {
		return masterDataRepository.getLanguages();
	}

    @Override
	public Map<Long, Map<Long, Country>> getCountries() {
		return masterDataRepository.getCountries();
	}
    
    @Override
	public Country getCountryById(Long countryId) {
		return masterDataRepository.getCountryById(countryId);
	}

    @Override
	public Map<Long, Map<Long, Currency>> getCurrencies() {
		return masterDataRepository.getCurrencies();
	}
    
    @Override
	public Map<String, Long> getCurrencyCodes() {
		return masterDataRepository.getCurrencyCodes();
	}

    @Override
	public Map<Long, Map<Long, String>> getCardTypes() {
		return masterDataRepository.getCardTypes();
	}

    @Override
	public Map<Long, Map<Long, String>> getFrequencies() {
		return masterDataRepository.getFrequencies();
	}

    @Override
	public Map<Long, Map<Long, String>> getUserTypes() {
		return masterDataRepository.getUserTypes();
	}

    @Override
	public Map<Long, Map<Long, String>> getCTransactionTypes() {
		return masterDataRepository.getCTransactionTypes();
	}

    @Override
	public Map<Long, Map<Long, String>> getMTransactionTypes() {
		return masterDataRepository.getMTransactionTypes();
	}

    @Override
	public Map<Long, Map<Long, String>> getATransactionTypes() {
		return masterDataRepository.getATransactionTypes();
	}

    @Override
	public Map<Long, Map<Long, String>> getMerchantOwnerTypes() {
		return masterDataRepository.getMerchantOwnerTypes();
	}

    @Override
	public Map<Long, Map<Long, String>> getMerchantBusinessCategories() {
		return masterDataRepository.getMerchantBusinessCategories();
	}
	
    @Override
	public Map<Long, Map<Long, Map<Long, String>>> getMerchantBusinessSubCategories() {
		return masterDataRepository.getMerchantBusinessSubCategories();
	}

    @Override
	public Map<Long, Map<Long, String>> getMerchantAvgTxAmounts() {
		return masterDataRepository.getMerchantAvgTxAmounts();
	}

    @Override
	public Map<Long, Map<Long, String>> getMerchantHighestMonthlyVolumes() {
		return masterDataRepository.getMerchantHighestMonthlyVolumes();
	}

    @Override
	public Map<Long, Map<Long, String>> getMerchantPercentageAnualRevenues() {
		return masterDataRepository.getMerchantPercentageAnualRevenues();
	}

    @Override
	public Map<Long, Map<Long, String>> getUserStatuses() {
		return masterDataRepository.getUserStatuses();
	}

    @Override
	public Map<Long, Map<Long, String>> getHintQuestions() {
		return masterDataRepository.getHintQuestions();
	}

    @Override
	public Map<Long, Map<Long, Map<Long, String>>> getRegions() {
    	return masterDataRepository.getRegions();
	}

	@Override
	public Map<Long, Map<Long, String>> getTitles() {
		return masterDataRepository.getTitles();
	}
	
	@Override
	public List<Menu> getParentMenus(Long languageId){
		return masterDataRepository.getParentMenus(languageId);
	}
			
	@Override
	public Map<Long,List<Menu>> getParentChildMenusMap(Long languageId){
		return masterDataRepository.getParentChildMenusMap(languageId);
	}

    @Override
	public Map<Long, Map<Long, String>> getMoneyAccountStatuses() {
		return masterDataRepository.getMoneyAccountStatuses();
	}

    @Override
	public Map<Long, Map<Long, String>> getMoneyAccountDeleteStatuses() {
		return masterDataRepository.getMoneyAccountDeleteStatuses();
	}

    @Override
	public Map<Long, Map<Long, Map<Long, String>>> getBankAccountTypes() {
    	return masterDataRepository.getBankAccountTypes();
	}
    
    @Override
	public Map<Long, Map<Long, String>> getFeeTypes() {
		return masterDataRepository.getFeeTypes();
	}
    
	@Override
	public Map<Long, Map<Long, String>> getFeeServiceTypes() {
		return masterDataRepository.getFeeServiceTypes();
	}

	@Override 
	public Map<Long, Map<Long, FeeOperationType>> getFeeOperationTypes() {
		return masterDataRepository.getFeeOperationTypes();
	}
	
    @Override
	public Map<Long, Map<Long, String>> getFeePayingEntities() {
		return masterDataRepository.getFeePayingEntities();
	}

	@Override
	public Map<Long, Map<Long, String>> getFeeTimeFrequency() {
		return masterDataRepository.getFeeTimeFrequency();
	}

	@Override
	public Map<Long, Map<Long, String>> getTrxTimePeriod() {
		return masterDataRepository.getTrxTimePeriod();
	}

	@Override
	public Map<Long, Double> getMasterAmountWalletCurrencyWise() {
		return masterDataRepository.getMasterAmountWalletCurrencyWise();
	}

	@Override
	public Map<Long, Map<Long, String>> getTransactionStatusName() {
		return masterDataRepository.getTransactionStatusName();
	}

	@Override
	public Map<Long, Map<Long, String>> getReceiveMoneyStatusName() {
		return masterDataRepository.getReceiveMoneyStatusName();
	}
	
	@Override
	public Map<Long, Map<Long, String>> getDestinationType() {
		return masterDataRepository.getDestinationType();
	}

	@Override
	public Map<Long, Map<Long, Map<Long, String>>> getReportTypes() {
		return masterDataRepository.getReportTypes();
	}

	@Override
	public Map<Long, Map<Long, String>> getDisputeType() {
		return masterDataRepository.getDisputeType();
	}

	@Override
	public Map<Long, Map<Long, String>> getAccountClosureStatus() {
		return masterDataRepository.getAccountClosureStatus();
	}

	@Override
	public Map<Long, Map<Long, String>> getDisputeStatus() {
		return masterDataRepository.getDisputeStatus();	
	}

	@Override
	public Map<Long, Double> getTaxesCurrencyWise() {
		return masterDataRepository.getTaxesCurrencyWise();
	}

	@Override
	public Map<Long, Double> getFeesCurrencyWise() {
		return masterDataRepository.getFeesCurrencyWise();
	}

	@Override
	public Map<String, String> getPaymentMessages() {
		return masterDataRepository.getPaymentMessages();
	}

	@Override
	public Map<Long, Map<Long, String>> getQueryOrFeedback() {
		return masterDataRepository.getQueryOrFeedback();
	}

	@Override
	public Map<String, String> getErrorCodes() {
		return masterDataRepository.getErrorCodes();
	}

	@Override
	public Map<Long, Map<Long, String>> getChannelType() {
		return masterDataRepository.getChannelType();
	}

	@Override
	public Map<String, String> getPGCodes() {
		return masterDataRepository.getPGCodes();
	}

	@Override
	public MasterVelocity createMasterVelocity(MasterVelocity masterVelocity) {
		return masterDataRepository.createMasterVelocity(masterVelocity);
	}

	@Override
	public VelocitySetting createVelocitySetting(VelocitySetting velocitySetting) {
		return masterDataRepository.createVelocitySetting(velocitySetting);
	}
}
