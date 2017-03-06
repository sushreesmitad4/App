/**
 * 
 */
package com.tarang.ewallet.masterdata.dao;


import java.util.List;
import java.util.Map;

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
public interface MasterDataDao {

	List<Long> getLanguageIds();
	
	Map<Long, Language> getLanguages();
	Map<Long, Language> getLanguages(Long langugeId);

	Map<Long, Menu> getMenus(); 
	Map<Long, Menu> getMenus(Long languageId);
	
	Map<Long, Country> getCountries(); 
	Map<Long, Country> getCountries(Long languageId);
	Country getCountryById(Long countryId);
	
	Map<Long, Currency> getCurrencies();
	Map<String, Long> getCurrencyCodes();
	Map<Long, Currency> getCurrencies(Long languageId);
		 
	Map<Long, String> getCardTypes() ;
	Map<Long, String> getCardTypes(Long languageId) ;

	Map<Long, String> getFrequencies();
	Map<Long, String> getFrequencies(Long languageId) ;

	Map<Long, String> getUserTypes();
	Map<Long, String> getUserTypes(Long languageId) ;

	Map<Long, String> getCTransactionTypes();
	Map<Long, String> getCTransactionTypes(Long languageId) ;

	Map<Long, String> getMTransactionTypes();
	Map<Long, String> getMTransactionTypes(Long languageId) ;

	Map<Long, String> getATransactionTypes();
	Map<Long, String> getATransactionTypes(Long languageId) ;

	Map<Long, String> getMerchantOwnerTypes();
	Map<Long, String> getMerchantOwnerTypes(Long languageId) ;

	Map<Long, String> getMerchantBusinessCategories();
	Map<Long, String> getMerchantBusinessCategories(Long languageId) ;

	Map<Long, Map<Long, String>> getMerchantBusinessSubCategories();
	Map<Long, Map<Long, String>> getMerchantBusinessSubCategories(Long languageId) ;

	Map<Long, String> getMerchantAvgTxAmounts();
	Map<Long, String> getMerchantAvgTxAmounts(Long languageId) ;

	Map<Long, String> getMerchantHighestMonthlyVolumes();
	Map<Long, String> getMerchantHighestMonthlyVolumes(Long languageId) ;

	Map<Long, String> getMerchantPercentageAnualRevenues();
	Map<Long, String> getMerchantPercentageAnualRevenues(Long languageId) ;
	
	Map<Long, String> getUserStatuses() ;
	Map<Long, String> getUserStatuses(Long languageId) ;

	Map<Long, String> getHintQuestions() ;
	Map<Long, String> getHintQuestions(Long languageId) ;

	Map<Long, Map<Long, String>> getRegions();
	Map<Long, Map<Long, String>> getRegions(Long languageId);

	Map<Long, String> getTitles();
	Map<Long, String> getTitles(Long languageId);
	
	List<Menu> getParentMenus(Long languageId);
	Map<Long,List<Menu>> getParentChildMenusMap(Long languageId);
	
	Map<Long, String> getMoneyAccountStatuses();
	Map<Long, String> getMoneyAccountStatuses(Long languageId);

	Map<Long, String> getMoneyAccountDeleteStatuses();
	Map<Long, String> getMoneyAccountDeleteStatuses(Long languageId);

	Map<Long, Map<Long, String>> getBankAccountTypes();
	Map<Long, Map<Long, String>> getBankAccountTypes(Long languageId);
	
	Map<Long, String> getFeeTypes();
	Map<Long, String> getFeeTypes(Long id);
	
	Map<Long, String> getFeeServiceTypes();
	Map<Long, String> getFeeServiceTypes(Long languageId);

	Map<Long, FeeOperationType> getFeeOperationTypes();
	Map<Long, FeeOperationType> getFeeOperationTypes(Long languageId);
	
	Map<Long, String> getFeePayingEntities() ;
	Map<Long, String> getFeePayingEntities(Long languageId) ;

	Map<Long, String> getTrxTimePeriod();
	Map<Long, String> getFeeTimeFrequencys(Long id);

	Map<Long, String> getFeeTimeFrequency();
	
	Map<Long, String> getTrxTimePeriods(Long id);
	
	Map<Long, Double> getMasterAmountWalletCurrencyWise();

	Map<Long, String> getTransactionStatusName();
	Map<Long, String> getTransactionStatusNames(Long id);

	Map<Long, String> getReceiveMoneyStatusName();
	Map<Long, String> getReceiveMoneyStatusNames(Long id);
	
	Map<Long, String> getDestinationType();
	Map<Long, String> getDestinationTypes(Long id);

	Map<Long, Map<Long, String>> getReportTypes();
	Map<Long, Map<Long, String>> getReportTypes(Long languageId);

	Map<Long, String> getDisputeType();
	Map<Long, String> getDisputeTypes(Long languageId);

	Map<Long, String> getAccountClosureStatus();
	Map<Long, String> getAccountClosureStatus(Long languageId);

	Map<Long, String> getDisputeStatus();
	Map<Long, String> getDisputeStatus(Long languageId);
	
	Map<Long, Double> getTaxesCurrencyWise();
	
	Map<Long, Double> getFeesCurrencyWise();

	Map<String, String> getPaymentMessages();
	
	Map<Long, String> getQueryOrFeedback();
	Map<Long, String> getQueryOrFeedback(Long languageId);

	Map<String, String> getErrorCodes();
	
	Map<Long, String> getChannelType();
	Map<Long, String> getChannelTypes(Long languageId);

	Map<String, String> getPGCodes();
	
	MasterVelocity createMasterVelocity(MasterVelocity masterVelocity);
	VelocitySetting createVelocitySetting(VelocitySetting velocitySetting);

}
