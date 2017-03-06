/**
 * 
 */
package com.tarang.ewallet.masterdata.business;

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
public interface MasterDataService {

	Map<Long, Map<Long, Language>> getLanguages();

	Map<Long, Map<Long, Country>> getCountries();
	
	Country getCountryById(Long countryId);
	
	Map<Long, Map<Long, Currency>> getCurrencies();
	
	Map<String, Long> getCurrencyCodes();

	Map<Long, Map<Long, String>> getCardTypes();

	Map<Long, Map<Long, String>> getFrequencies();

	Map<Long, Map<Long, String>> getUserTypes();

	Map<Long, Map<Long, String>> getCTransactionTypes();

	Map<Long, Map<Long, String>> getMTransactionTypes();

	Map<Long, Map<Long, String>> getATransactionTypes();

	Map<Long, Map<Long, String>> getMerchantOwnerTypes();

	Map<Long, Map<Long, String>> getMerchantBusinessCategories();

	Map<Long, Map<Long, Map<Long, String>>> getMerchantBusinessSubCategories();

	Map<Long, Map<Long, String>> getMerchantAvgTxAmounts();

	Map<Long, Map<Long, String>> getMerchantHighestMonthlyVolumes();

	Map<Long, Map<Long, String>> getMerchantPercentageAnualRevenues();
	
	Map<Long, Map<Long,String>> getUserStatuses();
	
	Map<Long, Map<Long,String>> getHintQuestions();

	Map<Long, Map<Long, Map<Long, String>>> getRegions();
	
	Map<Long, Map<Long, String>> getTitles();
	
	List<Menu> getParentMenus(Long languageId);
	
	Map<Long,List<Menu>> getParentChildMenusMap(Long languageId);
	
	Map<Long, Map<Long, String>> getMoneyAccountStatuses();

	Map<Long, Map<Long, String>> getMoneyAccountDeleteStatuses();

	Map<Long, Map<Long, Map<Long, String>>> getBankAccountTypes();
	
	Map<Long, Map<Long, String>> getFeeTypes();
	
	Map<Long, Map<Long, String>> getFeeServiceTypes();

	Map<Long, Map<Long, FeeOperationType>> getFeeOperationTypes();

	Map<Long, Map<Long, String>> getFeePayingEntities();

	Map<Long, Map<Long, String>> getFeeTimeFrequency();
	
	Map<Long, Map<Long, String>> getTrxTimePeriod();
	
	Map<Long, Double> getMasterAmountWalletCurrencyWise();
	
	Map<Long, Map<Long, String>> getTransactionStatusName();
	
	Map<Long, Map<Long, String>> getReceiveMoneyStatusName();
	
	Map<Long, Map<Long, String>> getDestinationType();
	
	Map<Long, Map<Long, Map<Long,String>>> getReportTypes();

	Map<Long, Map<Long, String>> getDisputeType();

	Map<Long, Map<Long, String>> getAccountClosureStatus();
	
	Map<Long, Map<Long, String>> getDisputeStatus();
	
	Map<Long, Double> getTaxesCurrencyWise();
	
	Map<Long, Double> getFeesCurrencyWise();
	
    Map<String, String> getPaymentMessages();
    
    Map<Long, Map<Long, String>> getQueryOrFeedback();
   
    Map<String, String> getErrorCodes();
    
    Map<Long, Map<Long, String>> getChannelType();
    
    Map<String, String> getPGCodes();
    
    MasterVelocity createMasterVelocity(MasterVelocity masterVelocity);
    
    VelocitySetting createVelocitySetting(VelocitySetting velocitySetting);
    
}
