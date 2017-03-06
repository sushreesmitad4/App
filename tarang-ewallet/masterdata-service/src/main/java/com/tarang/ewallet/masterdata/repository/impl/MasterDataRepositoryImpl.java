/**
 * 
 */
package com.tarang.ewallet.masterdata.repository.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tarang.ewallet.masterdata.dao.MasterDataDao;
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
public class MasterDataRepositoryImpl implements MasterDataRepository {

	private static final Logger LOGGER = Logger.getLogger(MasterDataRepositoryImpl.class);
	
	private MasterDataDao masterDataDao;

	public MasterDataRepositoryImpl(MasterDataDao masterDataDao) {
		LOGGER.debug(  " MasterDataRepositoryImpl " );
		this.masterDataDao = masterDataDao;
	}

	@Override
	public Map<Long, Map<Long, Language>> getLanguages() {
		LOGGER.debug( " getLanguages " );
		Map<Long, Language> defaultLanguage = masterDataDao.getLanguages();
		Iterator<Long> iter = defaultLanguage.keySet().iterator();

		Map<Long, Map<Long, Language>> allLanguages = new HashMap<Long, Map<Long, Language>>();
		allLanguages.put(0L, defaultLanguage);

		Map<Long, Language> map = null;
		Long id = null;

		while (iter.hasNext()) {
			id = iter.next();
			map = masterDataDao.getLanguages(id);
			allLanguages.put(id, map);
		}
		return allLanguages;
	}

	/*
	 * return type Map<langugaeId, Map<functionId, functionObject>> add default
	 * properties with the languageId as 0
	 */
	@Override
	public Map<Long, Map<Long, Menu>> getMenus() {
		LOGGER.debug( " getMenus " );
		Map<Long, Menu> defaultMenu = masterDataDao.getMenus();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, Menu>> allMenus = new HashMap<Long, Map<Long, Menu>>();
		allMenus.put(0L, defaultMenu);

		Map<Long, Menu> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getMenus(id);
			allMenus.put(id, map);
		}
		return allMenus;
	}

	@Override
	public Map<Long, Map<Long, Country>> getCountries() {
		LOGGER.debug( " getCountries " );
		Map<Long, Country> defaultCountry = masterDataDao.getCountries();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, Country>> allCountries = new HashMap<Long, Map<Long, Country>>();
		allCountries.put(0L, defaultCountry);

		Map<Long, Country> all = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			all = masterDataDao.getCountries(id);
			allCountries.put(id, all);
		}
		return allCountries;
	}
	
	@Override
	public Country getCountryById(Long countryId) {
		return masterDataDao.getCountryById(countryId);
	}

	@Override
	public Map<Long, Map<Long, Currency>> getCurrencies() {
		LOGGER.debug( " getCurrencies " );
		Map<Long, Currency> defaultCurrency = masterDataDao.getCurrencies();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, Currency>> allCurrencies = new HashMap<Long, Map<Long, Currency>>();
		allCurrencies.put(0L, defaultCurrency);

		Map<Long, Currency> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getCurrencies(id);
			allCurrencies.put(id, map);
		}
		return allCurrencies;
	}
	
	@Override
	public Map<String, Long> getCurrencyCodes() {
		return masterDataDao.getCurrencyCodes();
	}

	@Override
	public Map<Long, Map<Long, String>> getCardTypes() {
		LOGGER.debug( " getCardTypes " );
		Map<Long, String> defaultCardType = masterDataDao.getCardTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allCardTypes = new HashMap<Long, Map<Long, String>>();
		allCardTypes.put(0L, defaultCardType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getCardTypes(id);
			allCardTypes.put(id, map);
		}
		return allCardTypes;
	}

	@Override
	public Map<Long, Map<Long, String>> getFrequencies() {
		LOGGER.debug( " getFrequencies " );
		Map<Long, String> defaultFrequency = masterDataDao.getFrequencies();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allFrequencies = new HashMap<Long, Map<Long, String>>();
		allFrequencies.put(0L, defaultFrequency);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getFrequencies(id);
			allFrequencies.put(id, map);
		}
		return allFrequencies;
	}

	@Override
	public Map<Long, Map<Long, String>> getUserTypes() {
		LOGGER.debug( " getUserTypes " );
		Map<Long, String> defaultUserType = masterDataDao.getUserTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allUserTypes = new HashMap<Long, Map<Long, String>>();
		allUserTypes.put(0L, defaultUserType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getUserTypes(id);
			allUserTypes.put(id, map);
		}
		return allUserTypes;
	}

	@Override
	public Map<Long, Map<Long, String>> getCTransactionTypes() {
		LOGGER.debug( " getCTransactionTypes " );
		Map<Long, String> defaultCTransactionType = masterDataDao.getCTransactionTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allCTransactionTypes = new HashMap<Long, Map<Long, String>>();
		allCTransactionTypes.put(0L, defaultCTransactionType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getCTransactionTypes(id);
			allCTransactionTypes.put(id, map);
		}
		return allCTransactionTypes;
	}

	@Override
	public Map<Long, Map<Long, String>> getMTransactionTypes() {
		LOGGER.debug( " getMTransactionTypes " );
		Map<Long, String> defaultMTransactionType = masterDataDao.getMTransactionTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allMTransactionTypes = new HashMap<Long, Map<Long, String>>();
		allMTransactionTypes.put(0L, defaultMTransactionType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getMTransactionTypes(id);
			allMTransactionTypes.put(id, map);
		}
		return allMTransactionTypes;
	}

	@Override
	public Map<Long, Map<Long, String>> getATransactionTypes() {
		LOGGER.debug( " getATransactionTypes " );
		Map<Long, String> defaultATransactionType = masterDataDao.getATransactionTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allATransactionTypes = new HashMap<Long, Map<Long, String>>();
		allATransactionTypes.put(0L, defaultATransactionType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getATransactionTypes(id);
			allATransactionTypes.put(id, map);
		}
		return allATransactionTypes;
	}

	@Override
	public Map<Long, Map<Long, String>> getMerchantOwnerTypes() {
		LOGGER.debug( " getMerchantOwnerTypes " );
		Map<Long, String> defaultMerchantOwnerType = masterDataDao.getMerchantOwnerTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allMerchantOwnerTypes = new HashMap<Long, Map<Long, String>>();
		allMerchantOwnerTypes.put(0L, defaultMerchantOwnerType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getMerchantOwnerTypes(id);
			allMerchantOwnerTypes.put(id, map);
		}
		return allMerchantOwnerTypes;
	}

	@Override
	public Map<Long, Map<Long, String>> getMerchantBusinessCategories() {
		LOGGER.debug( " getMerchantBusinessCategories " );
		Map<Long, String> defaultMerchantBusinessCategory = masterDataDao.getMerchantBusinessCategories();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allMerchantBusinessCategories = new HashMap<Long, Map<Long, String>>();
		allMerchantBusinessCategories.put(0L, defaultMerchantBusinessCategory);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getMerchantBusinessCategories(id);
			allMerchantBusinessCategories.put(id, map);
		}
		return allMerchantBusinessCategories;
	}

	@Override
	public Map<Long, Map<Long, Map<Long, String>>> getMerchantBusinessSubCategories(){
		LOGGER.debug( " getMerchantBusinessSubCategories " );
		Map<Long, Map<Long, String>> defaultMerchantSubBusinessCategory = masterDataDao.getMerchantBusinessSubCategories();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, Map<Long, String>>> allMerchantSubBusinessCategories = new HashMap<Long, Map<Long, Map<Long, String>>>();
		allMerchantSubBusinessCategories.put(0L, defaultMerchantSubBusinessCategory);

		Map<Long, Map<Long, String>> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getMerchantBusinessSubCategories(id);
			allMerchantSubBusinessCategories.put(id, map);
		}
		return allMerchantSubBusinessCategories;
	}
	
	@Override
	public Map<Long, Map<Long, String>> getMerchantAvgTxAmounts() {
		LOGGER.debug( " getMerchantAvgTxAmounts " );
		Map<Long, String> defaultMerchantAvgTxAmount = masterDataDao.getMerchantAvgTxAmounts();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allMerchantAvgTxAmounts = new HashMap<Long, Map<Long, String>>();
		allMerchantAvgTxAmounts.put(0L, defaultMerchantAvgTxAmount);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getMerchantAvgTxAmounts(id);
			allMerchantAvgTxAmounts.put(id, map);
		}
		return allMerchantAvgTxAmounts;
	}

	@Override
	public Map<Long, Map<Long, String>> getMerchantHighestMonthlyVolumes() {
		LOGGER.debug( " getMerchantHighestMonthlyVolumes " );
		Map<Long, String> defaultMerchantHighestMonthlyVolume = masterDataDao.getMerchantHighestMonthlyVolumes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allMerchantHighestMonthlyVolumes = new HashMap<Long, Map<Long, String>>();
		allMerchantHighestMonthlyVolumes.put(0L,
				defaultMerchantHighestMonthlyVolume);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getMerchantHighestMonthlyVolumes(id);
			allMerchantHighestMonthlyVolumes.put(id, map);
		}
		return allMerchantHighestMonthlyVolumes;
	}

	@Override
	public Map<Long, Map<Long, String>> getMerchantPercentageAnualRevenues() {
		LOGGER.debug( " getMerchantPercentageAnualRevenues " );
		Map<Long, String> defaultMerchantPercentageAnualRevenue = masterDataDao.getMerchantPercentageAnualRevenues();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allMerchantPercentageAnualRevenues = new HashMap<Long, Map<Long, String>>();
		allMerchantPercentageAnualRevenues.put(0L,
				defaultMerchantPercentageAnualRevenue);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getMerchantPercentageAnualRevenues(id);
			allMerchantPercentageAnualRevenues.put(id, map);
		}
		return allMerchantPercentageAnualRevenues;
	}

	@Override
	public Map<Long, Map<Long, String>> getUserStatuses() {
		LOGGER.debug( " getUserStatuses " );
		Map<Long, String> defaultUserStatus = masterDataDao.getUserStatuses();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allUserStatuses = new HashMap<Long, Map<Long, String>>();
		allUserStatuses.put(0L, defaultUserStatus);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getUserStatuses(id);
			allUserStatuses.put(id, map);
		}
		return allUserStatuses;
	}

	@Override
	public Map<Long, Map<Long, String>> getHintQuestions() {
		LOGGER.debug( " getHintQuestions " );
		Map<Long, String> defaultHints = masterDataDao.getHintQuestions();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allHints = new HashMap<Long, Map<Long, String>>();
		allHints.put(0L, defaultHints);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getHintQuestions(id);
			allHints.put(id, map);
		}
		return allHints;
	}

	@Override
	public Map<Long, Map<Long, Map<Long, String>>> getRegions(){
		LOGGER.debug( " getRegions " );
		Map<Long, Map<Long, String>> defaultRegions = masterDataDao.getRegions();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, Map<Long, String>>> allregions = new HashMap<Long, Map<Long, Map<Long, String>>>();
		allregions.put(0L, defaultRegions);

		Map<Long, Map<Long, String>> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getRegions(id);
			allregions.put(id, map);
		}
		return allregions;
	}

	@Override
	public Map<Long, Map<Long, String>> getTitles() {
		LOGGER.debug( " getTitles " );
		Map<Long, String> defaultTitles = masterDataDao.getTitles();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allTitles = new HashMap<Long, Map<Long, String>>();
		allTitles.put(0L, defaultTitles);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getTitles(id);
			allTitles.put(id, map);
		}
		return allTitles;
	}

	@Override
	public List<Menu> getParentMenus(Long languageId) {
		LOGGER.debug( " getParentMenus " );	
		return masterDataDao.getParentMenus(languageId);
	}

	@Override
	public Map<Long, List<Menu>> getParentChildMenusMap(Long languageId) {
		LOGGER.debug( " getParentChildMenusMap ");
		return masterDataDao.getParentChildMenusMap(languageId);
	}
	
	@Override
	public Map<Long, Map<Long, String>> getMoneyAccountStatuses() {
		LOGGER.debug( " getMoneyAccountStatuses " );
		Map<Long, String> defaultMoneyAccountStatuses = masterDataDao.getMoneyAccountStatuses();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allMoneyAccountStatuses = new HashMap<Long, Map<Long, String>>();
		allMoneyAccountStatuses.put(0L, defaultMoneyAccountStatuses);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getMoneyAccountStatuses(id);
			allMoneyAccountStatuses.put(id, map);
		}
		return allMoneyAccountStatuses;
	}

	@Override
	public Map<Long, Map<Long, String>> getMoneyAccountDeleteStatuses() {
		LOGGER.debug( " getMoneyAccountDeleteStatuses " );
		Map<Long, String> defaultMoneyAccountDeleteStatuses = masterDataDao.getMoneyAccountDeleteStatuses();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allMoneyAccountDeleteStatuses = new HashMap<Long, Map<Long, String>>();
		allMoneyAccountDeleteStatuses.put(0L, defaultMoneyAccountDeleteStatuses);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getMoneyAccountDeleteStatuses(id);
			allMoneyAccountDeleteStatuses.put(id, map);
		}
		return allMoneyAccountDeleteStatuses;
	}
	
	@Override
	public Map<Long, Map<Long, Map<Long, String>>> getBankAccountTypes(){
		LOGGER.debug( " getBankAccountTypes " );
		Map<Long, Map<Long, String>> defaultBankAccountTypes = masterDataDao.getBankAccountTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, Map<Long, String>>> alltypes = new HashMap<Long, Map<Long, Map<Long, String>>>();
		alltypes.put(0L, defaultBankAccountTypes);

		Map<Long, Map<Long, String>> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getBankAccountTypes(id);
			alltypes.put(id, map);
		}
		return alltypes;
	}
	
	@Override
	public Map<Long, Map<Long, String>> getFeeTypes() {
		LOGGER.debug( " getFeeType " );
		Map<Long, String> defaultFeeType = masterDataDao.getFeeTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allFeeTypes = new HashMap<Long, Map<Long, String>>();
		allFeeTypes.put(0L, defaultFeeType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getFeeTypes(id);
			allFeeTypes.put(id, map);
		}
		return allFeeTypes;
	}
	
	@Override
	public Map<Long, Map<Long, String>> getFeeServiceTypes() {
		LOGGER.debug( " getFeeServiceTypes " );
		Map<Long, String> defaultServicesType = masterDataDao.getFeeServiceTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allServiceTypes = new HashMap<Long, Map<Long, String>>();
		allServiceTypes.put(0L, defaultServicesType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getFeeServiceTypes(id);
			allServiceTypes.put(id, map);
		}
		return allServiceTypes;
	}

	@Override
	public Map<Long, Map<Long, FeeOperationType>> getFeeOperationTypes(){
		
		LOGGER.debug( " getOperationType " );
		Map<Long, FeeOperationType> defaultOperationType = masterDataDao.getFeeOperationTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, FeeOperationType>> allOperationType = new HashMap<Long, Map<Long, FeeOperationType>>();
		allOperationType.put(0L, defaultOperationType);

		Map<Long, FeeOperationType> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getFeeOperationTypes(id);
			allOperationType.put(id, map);
		}
		return allOperationType;
	}

	@Override
	public Map<Long, Map<Long, String>> getFeePayingEntities() {
		LOGGER.debug( " getFeePayingEntities " );
		Map<Long, String> defaultFeePayingEntities = masterDataDao.getFeePayingEntities();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allFeePayingEntities = new HashMap<Long, Map<Long, String>>();
		allFeePayingEntities.put(0L, defaultFeePayingEntities);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getFeePayingEntities(id);
			allFeePayingEntities.put(id, map);
		}
		return allFeePayingEntities;
	}

	@Override
	public Map<Long, Map<Long, String>> getFeeTimeFrequency() {
		LOGGER.debug( " getFeeTimeFrequency " );
		Map<Long, String> defaultFeeTimeFrequency = masterDataDao.getFeeTimeFrequency();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allFeeTimeFrequency = new HashMap<Long, Map<Long, String>>();
		allFeeTimeFrequency.put(0L, defaultFeeTimeFrequency);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getFeeTimeFrequencys(id);
			allFeeTimeFrequency.put(id, map);
		}
		return allFeeTimeFrequency;
	}

	@Override
	public Map<Long, Map<Long, String>> getTrxTimePeriod() {
		LOGGER.debug( " getTrxTimePeriod " );
		Map<Long, String> defaultTrxTimePeriod = masterDataDao.getTrxTimePeriod();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allTrxTimePeriod = new HashMap<Long, Map<Long, String>>();
		allTrxTimePeriod.put(0L, defaultTrxTimePeriod);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getTrxTimePeriods(id);
			allTrxTimePeriod.put(id, map);
		}
		return allTrxTimePeriod;
	}

	@Override
	public Map<Long, Double> getMasterAmountWalletCurrencyWise() {
		return masterDataDao.getMasterAmountWalletCurrencyWise();
	}

	@Override
	public Map<Long, Map<Long, String>> getTransactionStatusName() {
		LOGGER.debug( " getTransactionStatusName " );
		Map<Long, String> defaultTransactionStatus = masterDataDao.getTransactionStatusName();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allTransactionStatus = new HashMap<Long, Map<Long, String>>();
		allTransactionStatus.put(0L, defaultTransactionStatus);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getTransactionStatusNames(id);
			allTransactionStatus.put(id, map);
		}
		return allTransactionStatus;
	}	

	@Override
	public Map<Long, Map<Long, String>> getReceiveMoneyStatusName() {
		LOGGER.debug( " getReceiveMoneyStatusName " );
		Map<Long, String> defaultReceiveStatusName = masterDataDao.getReceiveMoneyStatusName();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allReceiveMoneyStatus = new HashMap<Long, Map<Long, String>>();
		allReceiveMoneyStatus.put(0L, defaultReceiveStatusName);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getReceiveMoneyStatusNames(id);
			allReceiveMoneyStatus.put(id, map);
		}
		return allReceiveMoneyStatus;
	}
	
	@Override
	public Map<Long, Map<Long, String>> getDestinationType() {
		LOGGER.debug( " getDestinationType " );
		Map<Long, String> defaultDestinationType = masterDataDao.getDestinationType();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allDestinationType = new HashMap<Long, Map<Long, String>>();
		allDestinationType.put(0L, defaultDestinationType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getDestinationTypes(id);
			allDestinationType.put(id, map);
		}
		return allDestinationType;
	}

	@Override
	public Map<Long, Map<Long, Map<Long, String>>> getReportTypes() {
		LOGGER.debug( " getReportTypes " );
		Map<Long, Map<Long, String>> defaultReportType = masterDataDao.getReportTypes();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();
		
		Map<Long, Map<Long, Map<Long, String>>> allReportType = new HashMap<Long, Map<Long, Map<Long, String>>>();
		allReportType.put(0L, defaultReportType);
		
		Map<Long, Map<Long, String>> map = null;
		Long id = null;
		
		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getReportTypes(id);
			allReportType.put(id, map);
		}
		return allReportType;
	}

	@Override
	public Map<Long, Map<Long, String>> getDisputeType() {
		LOGGER.debug( " getDisputeType " );
		Map<Long, String> defaultDisputeType = masterDataDao.getDisputeType();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allDisputeType = new HashMap<Long, Map<Long, String>>();
		allDisputeType.put(0L, defaultDisputeType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getDisputeTypes(id);
			allDisputeType.put(id, map);
		}
		return allDisputeType;
	}

	@Override
	public Map<Long, Map<Long, String>> getAccountClosureStatus() {
		LOGGER.debug( " getAccountClosureStatus " );
		Map<Long, String> defaultAccountClosureStatus = masterDataDao.getAccountClosureStatus();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allAccountClosureStatus = new HashMap<Long, Map<Long, String>>();
		allAccountClosureStatus.put(0L, defaultAccountClosureStatus);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getAccountClosureStatus(id);
			allAccountClosureStatus.put(id, map);
		}
		return allAccountClosureStatus;
	}

	@Override
	public Map<Long, Map<Long, String>> getDisputeStatus() {
		LOGGER.debug( " getDisputeStatus " );
		Map<Long, String> defaultgetDisputeStatus = masterDataDao.getDisputeStatus();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allgetDisputeStatus = new HashMap<Long, Map<Long, String>>();
		allgetDisputeStatus.put(0L, defaultgetDisputeStatus);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getDisputeStatus(id);
			allgetDisputeStatus.put(id, map);
		}
		return allgetDisputeStatus;
	}

	@Override
	public Map<Long, Double> getTaxesCurrencyWise() {
		return masterDataDao.getTaxesCurrencyWise();
	}

	@Override
	public Map<Long, Double> getFeesCurrencyWise() {
		return masterDataDao.getFeesCurrencyWise();
	}

	@Override
	public Map<String, String> getPaymentMessages() {
		return masterDataDao.getPaymentMessages();
	}

	@Override
	public Map<Long, Map<Long, String>> getQueryOrFeedback() {
		LOGGER.debug( " getQueryOrFeedback " );
		Map<Long, String> defaultQueryOrFeedback = masterDataDao.getQueryOrFeedback();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allQueryOrFeedback = new HashMap<Long, Map<Long, String>>();
		allQueryOrFeedback .put(0L, defaultQueryOrFeedback);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getQueryOrFeedback(id);
			allQueryOrFeedback.put(id, map);
		}
		return allQueryOrFeedback;
	}

	@Override
	public Map<String, String> getErrorCodes() {
		return masterDataDao.getErrorCodes();
	}

	@Override
	public Map<Long, Map<Long, String>> getChannelType() {
		LOGGER.debug( " getChannelType " );
		Map<Long, String> defaultChannelType = masterDataDao.getChannelType();
		Iterator<Long> languageIds = masterDataDao.getLanguageIds().iterator();

		Map<Long, Map<Long, String>> allChannelType = new HashMap<Long, Map<Long, String>>();
		allChannelType.put(0L, defaultChannelType);

		Map<Long, String> map = null;
		Long id = null;

		while (languageIds.hasNext()) {
			id = languageIds.next();
			map = masterDataDao.getChannelTypes(id);
			allChannelType.put(id, map);
		}
		return allChannelType;
	}

	@Override
	public Map<String, String> getPGCodes() {
		return masterDataDao.getPGCodes();
	}

	@Override
	public MasterVelocity createMasterVelocity(MasterVelocity masterVelocity) {
		return masterDataDao.createMasterVelocity(masterVelocity);
	}

	@Override
	public VelocitySetting createVelocitySetting(VelocitySetting velocitySetting) {
		return masterDataDao.createVelocitySetting(velocitySetting);
	}
}
