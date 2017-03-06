/**
 * 
 */
package com.tarang.ewallet.masterdata.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.masterdata.dao.MasterDataDao;
import com.tarang.ewallet.masterdata.util.MasterDataUtil;
import com.tarang.ewallet.model.ATransactionType;
import com.tarang.ewallet.model.AccountClosureStatus;
import com.tarang.ewallet.model.BankAccountType;
import com.tarang.ewallet.model.CTransactionType;
import com.tarang.ewallet.model.CardType;
import com.tarang.ewallet.model.ChannelType;
import com.tarang.ewallet.model.Country;
import com.tarang.ewallet.model.Currency;
import com.tarang.ewallet.model.DestinationType;
import com.tarang.ewallet.model.DisputeStatus;
import com.tarang.ewallet.model.DisputeType;
import com.tarang.ewallet.model.FeeOperationType;
import com.tarang.ewallet.model.FeePayingEntity;
import com.tarang.ewallet.model.FeeService;
import com.tarang.ewallet.model.FeeTimeFrequency;
import com.tarang.ewallet.model.FeeType;
import com.tarang.ewallet.model.Frequency;
import com.tarang.ewallet.model.HintQuestion;
import com.tarang.ewallet.model.Language;
import com.tarang.ewallet.model.MTransactionType;
import com.tarang.ewallet.model.MasterVelocity;
import com.tarang.ewallet.model.Menu;
import com.tarang.ewallet.model.MerchantAvgTxAmount;
import com.tarang.ewallet.model.MerchantBusinessCategory;
import com.tarang.ewallet.model.MerchantBusinessSubCategory;
import com.tarang.ewallet.model.MerchantHighestMonthlyVolume;
import com.tarang.ewallet.model.MerchantOwnerType;
import com.tarang.ewallet.model.MerchantPercentageAnualRevenue;
import com.tarang.ewallet.model.MoneyAccountDeleteStatus;
import com.tarang.ewallet.model.MoneyAccountStatus;
import com.tarang.ewallet.model.QueryOrFeedback;
import com.tarang.ewallet.model.ReceiveMoneyStatus;
import com.tarang.ewallet.model.Region;
import com.tarang.ewallet.model.ReportType;
import com.tarang.ewallet.model.Title;
import com.tarang.ewallet.model.TransactionStatus;
import com.tarang.ewallet.model.TrxTimePeriod;
import com.tarang.ewallet.model.UserStatus;
import com.tarang.ewallet.model.UserType;
import com.tarang.ewallet.model.VelocitySetting;


/**
 * @author prasadj
 *
 */
@SuppressWarnings({ "unchecked"})
public class MasterDataDaoImpl implements MasterDataDao {

	private static final Logger LOGGER = Logger.getLogger(MasterDataDaoImpl.class);
	
    private HibernateOperations hibernateOperations;
    
    private String getparentMenus = "getParentMenus";
    
    private String getDefaultCurrency = "getDefaultCurrency";
    
    private String getMenusForGivenLanguage = "getMenusForGivenLanguage";

    private String getParentLangMenus = "getParentLangMenus";
    
    private String getDefaultMenus = "getDefaultMenus";
    
    public MasterDataDaoImpl(HibernateOperations hibernateOperations) {
        this.hibernateOperations = hibernateOperations;
    }
    
    @Override
    public List<Long> getLanguageIds() {
		return (List<Long>)hibernateOperations.findByNamedQuery("getDefaultLanguageIds");
	}
    
    @Override
	public Map<Long, Language> getLanguages() {
		List<Language> list = (List<Language>)hibernateOperations.findByNamedQuery("getDefaultLanguages"); 
		Map<Long, Language> map = new HashMap<Long, Language>();
		
		if(list != null){
			for(Language language: list){
				map.put(language.getId(), language);
			}
		}
		return map;
	}

    @Override
	public Map<Long, Language> getLanguages(Long languageId) {
		List<Language> list = (List<Language>)hibernateOperations.findByNamedQuery("getLanguagesForGivenLanguage", languageId);
																																		
		Map<Long, Language> map = new HashMap<Long, Language>();
		if(list != null){
			for(Language language: list){
				map.put(language.getId(), language);
			}
		}
		return map;
	}

  
    @Override
	public List<Menu> getParentMenus(Long languageId){
    	List<Menu> defaultlist = new ArrayList<Menu>();
    	if (languageId==0){
    		defaultlist=(List<Menu>)hibernateOperations.findByNamedQuery(getparentMenus);
    	}else{
    		 defaultlist=(List<Menu>)hibernateOperations.findByNamedQuery(getParentLangMenus,languageId);
    	}
		return defaultlist;
	}
    
    @Override
	public Map<Long,List<Menu>> getParentChildMenusMap(Long languageId){
    	Map<Long,List<Menu>> mp = new HashMap<Long,List<Menu>>();
    	List<Menu> childList = new ArrayList<Menu>(); 
    	if (languageId==0){
		    	List<Menu> listParent=(List<Menu>)hibernateOperations.findByNamedQuery(getparentMenus);
		    	List<Menu> listDefault=(List<Menu>)hibernateOperations.findByNamedQuery(getDefaultMenus);
		    
		    	if(listParent!=null){
					for(Menu parentMenu:listParent){
						 childList = new ArrayList<Menu>(); 
						for(Menu defaultMenu:listDefault){
						 if (defaultMenu.getParentId().equals(parentMenu.getId())){
							 LOGGER.debug(defaultMenu.getParentId()+"=="+parentMenu.getId());
							LOGGER.debug("id :"+defaultMenu.getId()+" name :"+defaultMenu.getMenuName());
							 childList.add(defaultMenu);
						 }
						}//inner
						mp.put(parentMenu.getId(),childList);
					}//outer
				}
	    }else{
	    	 	List<Menu> listLangParent=(List<Menu>)hibernateOperations.findByNamedQuery(getParentLangMenus,languageId);
	    	 	List<Menu> listLangMenu=(List<Menu>)hibernateOperations.findByNamedQuery(getMenusForGivenLanguage, languageId);	
	    	 	if(listLangParent!=null){
	    	 		for(Menu parentMenu:listLangParent){
	    	 			childList = new ArrayList<Menu>(); 
	    				for(Menu defaultMenu:listLangMenu){
	    				 if (defaultMenu.getParentId().equals(parentMenu.getId())){
	    					 childList.add(defaultMenu);
	    				 }
	    				}//inner
	    				mp.put(parentMenu.getId(),childList);
	    			}//outer
	    		}
    		
    	}
 		return mp;
	}

    @Override
	public Map<Long, Menu> getMenus(){
		List<Menu> list=(List<Menu>)hibernateOperations.findByNamedQuery(getDefaultMenus);
		Map<Long, Menu> map=new HashMap<Long,Menu>();
		if(list!=null){
			for(Menu function:list){
				map.put(function.getId(), function);
			}
		}
		return map;
	}
	

    @Override
	public Map<Long, Menu> getMenus(Long languageId){
		List<Menu> list=(List<Menu>)hibernateOperations.findByNamedQuery(getMenusForGivenLanguage, languageId);
		Map<Long, Menu> map = new HashMap<Long, Menu>();
		if(list != null){
			for(Menu function: list){
				map.put(function.getId(),function);
			}
		}
				return map;
	}
	
    @Override
	public Map<Long, Country> getCountries() {
		List<Country> list=(List<Country>)hibernateOperations.findByNamedQuery("getDefaultCountries");
		Map<Long, Country> map=new HashMap<Long,Country>();
		if(list!=null){
			for(Country country:list){
				map.put(country.getId(), country);
			}
		}
		return map;
	}

    @Override
	public Map<Long, Country> getCountries(Long languageId) {
		List<Country> list=(List<Country>)hibernateOperations.findByNamedQuery("getCountriesForGivenLanguage",languageId);
		Map<Long, Country> map = new HashMap<Long,Country>();
		if(list != null){
			for(Country country: list){
				map.put(country.getId(),country);
			}
		}
		return map;
	}
    
    @Override
	public Country getCountryById(Long countryId) {
    	List<Country> list = (List<Country>)hibernateOperations.findByNamedQuery("getCountrieById", countryId);
		if(list != null && list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}

    @Override
	public Map<Long, Currency> getCurrencies() {
		List<Currency> list = (List<Currency>)hibernateOperations.findByNamedQuery(getDefaultCurrency);
		Map<Long, Currency> map = new HashMap<Long, Currency>();
		if(list != null){
			for(Currency currency :list){
				map.put(currency.getId(), currency);
			}
		}
		return map;
	}

    @Override
	public Map<Long, Currency> getCurrencies(Long languageId) {
		List<Currency> list = (List<Currency>)hibernateOperations.findByNamedQuery("getCurrencyForGivenLanguage", languageId);
		Map<Long, Currency> map = new HashMap<Long, Currency>();
		if(list != null){
			for(Currency currency: list){
				map.put(currency.getId(), currency);
			}
		}
		return map;
	}
    
	@Override
	public Map<String, Long> getCurrencyCodes() {
		List<Currency> list = (List<Currency>)hibernateOperations.findByNamedQuery(getDefaultCurrency);
		Map<String, Long> map = new HashMap<String, Long>();
		if(list != null){
			for(Currency currency :list){
				map.put(currency.getCode(), currency.getId());
			}
		}
		return map;
	}
	
    @Override
	public Map<Long, String> getCardTypes() {
		List<CardType> list = (List<CardType>)hibernateOperations.findByNamedQuery("getDefaultCardType");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(CardType cardType :list){
				map.put(cardType.getId(),cardType.getName());
			}
		}
		return map;

	}

    @Override
	public Map<Long, String> getCardTypes(Long languageId) {
		List<CardType> list = (List<CardType>)hibernateOperations.findByNamedQuery("getCardTypeForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(CardType cardType: list){
				map.put(cardType.getId(), cardType.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getFrequencies() {
		List<Frequency> list = (List<Frequency>)hibernateOperations.findByNamedQuery("getDefaultFrequency");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(Frequency frequency:list){
				map.put(frequency.getId(),frequency.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getFrequencies(Long languageId) {
		List<Frequency> list = (List<Frequency>)hibernateOperations.findByNamedQuery("getFrequencyForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(Frequency frequency: list){
				map.put(frequency.getId(), frequency.getName());
			}
		}
		return map;
	}

	
	
    @Override
	public Map<Long, String> getUserTypes() {
		List<UserType> list = (List<UserType>)hibernateOperations.findByNamedQuery("getDefaultUserType");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(UserType userType :list){
				map.put(userType.getId(),userType.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getUserTypes(Long languageId) {
		List<UserType> list = (List<UserType>)hibernateOperations.findByNamedQuery("getUserTypeForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(UserType userType: list){
				map.put(userType.getId(), userType.getName());
			}
		}
		return map;
	}
	

    @Override
	public Map<Long, String> getCTransactionTypes() {
		List<CTransactionType> list = (List<CTransactionType>)hibernateOperations.findByNamedQuery("getDefaultCTransactionType");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(CTransactionType cTransactionType:list){
				map.put(cTransactionType.getId(),cTransactionType.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getCTransactionTypes(Long languageId) {
		List<CTransactionType> list = (List<CTransactionType>)hibernateOperations.findByNamedQuery("getCTransactionTypeForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(CTransactionType cTransactionType: list){
				map.put(cTransactionType.getId(), cTransactionType.getName());
			}
		}
		return map;
	}
	
    @Override
	public Map<Long, String> getMTransactionTypes() {
		List<MTransactionType> list = (List<MTransactionType>)hibernateOperations.findByNamedQuery("getDefaultMTransactionType");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MTransactionType mTransactionType:list){
				map.put(mTransactionType.getId(),mTransactionType.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMTransactionTypes(Long languageId) {
		List<MTransactionType> list = (List<MTransactionType>)hibernateOperations.findByNamedQuery("getMTransactionTypeForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MTransactionType mTransactionType: list){
				map.put(mTransactionType.getId(), mTransactionType.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getATransactionTypes() {
		List<ATransactionType> list = (List<ATransactionType>)hibernateOperations.findByNamedQuery("getDefaultATransactionType");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(ATransactionType aTransactionType :list){
				map.put(aTransactionType.getId(),aTransactionType.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getATransactionTypes(Long languageId) {
		List<ATransactionType> list = (List<ATransactionType>)hibernateOperations.findByNamedQuery("getATransactionTypeForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(ATransactionType aTransactionType: list){
				map.put(aTransactionType.getId(), aTransactionType.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMerchantOwnerTypes() {
		List<MerchantOwnerType> list = (List<MerchantOwnerType>)hibernateOperations.findByNamedQuery("getDefaultMerchantOwnerType");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MerchantOwnerType merchantOwnerType:list){
				map.put(merchantOwnerType.getId(),merchantOwnerType.getType());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMerchantOwnerTypes(Long languageId) {
		List<MerchantOwnerType> list = (List<MerchantOwnerType>)hibernateOperations.findByNamedQuery("getMerchantOwnerTypeForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MerchantOwnerType merchantOwnerType: list){
				map.put(merchantOwnerType.getId(), merchantOwnerType.getType());
			}
		}
		return map;
	}
	
    @Override
	public Map<Long, String> getMerchantBusinessCategories() {
		List<MerchantBusinessCategory> list = (List<MerchantBusinessCategory>)hibernateOperations.findByNamedQuery("getDefaultMerchantBusinessCategory");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MerchantBusinessCategory merchantBusinessCategory :list){
				map.put(merchantBusinessCategory.getId(),merchantBusinessCategory.getCategory());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMerchantBusinessCategories(Long languageId) {
		List<MerchantBusinessCategory> list = (List<MerchantBusinessCategory>)hibernateOperations.findByNamedQuery("getMerchantBusinessCategoryForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MerchantBusinessCategory merchantBusinessCategory: list){
				map.put(merchantBusinessCategory.getId(), merchantBusinessCategory.getCategory());
			}
		}
		return map;
	}

    @Override
	public Map<Long, Map<Long,String>> getMerchantBusinessSubCategories(){
    	List<MerchantBusinessSubCategory> list = (List<MerchantBusinessSubCategory>)hibernateOperations.findByNamedQuery("getDefaultMerchantBusinessSubCategory");
    	return getSubCategoryMap(list);
	}
	
    @Override
	public Map<Long, Map<Long,String>> getMerchantBusinessSubCategories(Long languageId){
    	List<MerchantBusinessSubCategory> list = (List<MerchantBusinessSubCategory>)hibernateOperations.findByNamedQuery("getMerchantBusinessSubCategoryForGivenLangauge", languageId);
    	return getSubCategoryMap(list);
	}

    private Map<Long, Map<Long,String>> getSubCategoryMap(List<MerchantBusinessSubCategory> list){
    	Map<Long, Map<Long,String>> totalMap = new HashMap<Long, Map<Long,String>>();
    	Map<Long, String> map = null;
    	if(list != null){
    		for(MerchantBusinessSubCategory subCategory: list){
    			map = totalMap.get(subCategory.getCategory());
    			if(map == null) {
    				map = new HashMap<Long, String>();
    				totalMap.put(subCategory.getCategory(), map);
    			}
				map.put(subCategory.getId(), subCategory.getSubCategory());
			}
    	}
    		
    	return totalMap;
    }
    
    @Override
	public Map<Long, String> getMerchantAvgTxAmounts() {
		List<MerchantAvgTxAmount> list = (List<MerchantAvgTxAmount>)hibernateOperations.findByNamedQuery("getDefaultMerchantAvgTxAmount");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MerchantAvgTxAmount merchantAvgTxAmount :list){
				map.put(merchantAvgTxAmount.getId(),merchantAvgTxAmount.getAvgAmount());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMerchantAvgTxAmounts(Long languageId) {
		List<MerchantAvgTxAmount> list = (List<MerchantAvgTxAmount>)hibernateOperations.findByNamedQuery("getMerchantAvgTxAmountForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MerchantAvgTxAmount merchantAvgTxAmount: list){
				map.put(merchantAvgTxAmount.getId(), merchantAvgTxAmount.getAvgAmount());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMerchantHighestMonthlyVolumes() {
		List<MerchantHighestMonthlyVolume> list = (List<MerchantHighestMonthlyVolume>)hibernateOperations.findByNamedQuery("getDefaultMerchantHighestMonthlyVolume");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MerchantHighestMonthlyVolume merchantHighestMonthlyVolume :list){
				map.put(merchantHighestMonthlyVolume.getId(),merchantHighestMonthlyVolume.getHighestMonthlyVolume());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMerchantHighestMonthlyVolumes(Long languageId) {
		List<MerchantHighestMonthlyVolume> list = (List<MerchantHighestMonthlyVolume>)hibernateOperations.findByNamedQuery("getMerchantHighestMonthlyVolumeForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MerchantHighestMonthlyVolume merchantHighestMonthlyVolume: list){
				map.put(merchantHighestMonthlyVolume.getId(), merchantHighestMonthlyVolume.getHighestMonthlyVolume());
			}
		}
		return map;
	}
	
    @Override
	public Map<Long, String> getMerchantPercentageAnualRevenues() {
		List<MerchantPercentageAnualRevenue> list = (List<MerchantPercentageAnualRevenue>)hibernateOperations.findByNamedQuery("getDefaultMerchantPercentageAnualRevenue");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MerchantPercentageAnualRevenue merchantPercentageAnualRevenue :list){
				map.put(merchantPercentageAnualRevenue.getId(),merchantPercentageAnualRevenue.getAnualRevenue());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMerchantPercentageAnualRevenues(Long languageId) {
		List<MerchantPercentageAnualRevenue> list = (List<MerchantPercentageAnualRevenue>)hibernateOperations.findByNamedQuery("getMerchantPercentageAnualRevenueForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MerchantPercentageAnualRevenue merchantPercentageAnualRevenue: list){
				map.put(merchantPercentageAnualRevenue.getId(), merchantPercentageAnualRevenue.getAnualRevenue());
			}
		}
		return map;
	}
	
    @Override
	public Map<Long, String> getUserStatuses() {
		List<UserStatus> list = (List<UserStatus>)hibernateOperations.findByNamedQuery("getDefaultUserStatus");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(UserStatus userStatus :list){
				map.put(userStatus.getId(),userStatus.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getUserStatuses(Long languageId) {
		List<UserStatus> list = (List<UserStatus>)hibernateOperations.findByNamedQuery("getUserStatusForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(UserStatus userStatus: list){
				map.put(userStatus.getId(), userStatus.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getHintQuestions() {
		List<HintQuestion> list = (List<HintQuestion>)hibernateOperations.findByNamedQuery("getDefaultHints");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(HintQuestion hint :list){
				map.put(hint.getId(),hint.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getHintQuestions(Long languageId) {
		List<HintQuestion> list = (List<HintQuestion>)hibernateOperations.findByNamedQuery("getHintsForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(HintQuestion hint: list){
				map.put(hint.getId(), hint.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, Map<Long,String>> getRegions(){
    	List<Region> list = (List<Region>)hibernateOperations.findByNamedQuery("getDefaultRegions");
    	return MasterDataUtil.getRegionsMap(list);
	}
	
    @Override
	public Map<Long, Map<Long,String>> getRegions(Long languageId){
    	List<Region> list = (List<Region>)hibernateOperations.findByNamedQuery("getRegionsForGivenLangauge", languageId);
    	return MasterDataUtil.getRegionsMap(list);
	}

	@Override
	public Map<Long, String> getTitles() {
		List<Title> list = (List<Title>)hibernateOperations.findByNamedQuery("getDefaultTitles");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(Title title :list){
				map.put(title.getId(),title.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getTitles(Long languageId) {
		List<Title> list = (List<Title>)hibernateOperations.findByNamedQuery("getTitlesForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(Title title: list){
				map.put(title.getId(), title.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMoneyAccountStatuses() {
		List<MoneyAccountStatus> list = (List<MoneyAccountStatus>)hibernateOperations.findByNamedQuery("getDefaultMoneyAccountStatus");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MoneyAccountStatus moneyAccountStatus :list){
				map.put(moneyAccountStatus.getId(), moneyAccountStatus.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMoneyAccountStatuses(Long languageId) {
		List<MoneyAccountStatus> list = (List<MoneyAccountStatus>)hibernateOperations.findByNamedQuery("getMoneyAccountStatusForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MoneyAccountStatus moneyAccountStatus: list){
				map.put(moneyAccountStatus.getId(), moneyAccountStatus.getName());
			}
		}
		return map;
	}
    
    @Override
	public Map<Long, String> getMoneyAccountDeleteStatuses() {
		List<MoneyAccountDeleteStatus> list = (List<MoneyAccountDeleteStatus>)hibernateOperations.findByNamedQuery("getDefaultMoneyAccountDeleteStatus");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MoneyAccountDeleteStatus moneyAccountDeleteStatus :list){
				map.put(moneyAccountDeleteStatus.getId(), moneyAccountDeleteStatus.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getMoneyAccountDeleteStatuses(Long languageId) {
		List<MoneyAccountDeleteStatus> list = (List<MoneyAccountDeleteStatus>)hibernateOperations.findByNamedQuery("getMoneyAccountDeleteStatusForGivenLangauge", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(MoneyAccountDeleteStatus moneyAccountDeleteStatus: list){
				map.put(moneyAccountDeleteStatus.getId(), moneyAccountDeleteStatus.getName());
			}
		}
		return map;
	}    

    @Override
	public Map<Long, Map<Long, String>> getBankAccountTypes(){
    	List<BankAccountType> list = (List<BankAccountType>)hibernateOperations.findByNamedQuery("getDefaultBankAccountTypes");
    	return MasterDataUtil.getBankAccountTypesMap(list);
	}
	
    @Override
	public Map<Long, Map<Long,String>> getBankAccountTypes(Long languageId){
    	List<BankAccountType> list = (List<BankAccountType>)hibernateOperations.findByNamedQuery("getBankAccountTypesForGivenLangauge", languageId);
    	return MasterDataUtil.getBankAccountTypesMap(list);
	}
    
    @Override
 	public Map<Long, String> getFeeTypes() {
 		List<FeeType> list = (List<FeeType>)hibernateOperations.findByNamedQuery("getDefaultFeeTypes");
 		Map<Long, String> map = new HashMap<Long, String>();
 		if(list != null){
 			for(FeeType feeType :list){
 				map.put(feeType.getId(), feeType.getName());
 			}
 		}
 		return map;
 	}

    @Override
 	public Map<Long, String> getFeeTypes(Long languageId) {
 		List<FeeType> list = (List<FeeType>)hibernateOperations.findByNamedQuery("getFeeTypesForGivenLangauge", languageId);
 		Map<Long, String> map = new HashMap<Long, String>();
 		if(list != null){
 			for(FeeType feeType: list){
 				map.put(feeType.getId(), feeType.getName());
 			}
 		}
 		return map;
 	}
    
	@Override
	public Map<Long, String> getFeeServiceTypes() {
		List<FeeService> list = (List<FeeService>)hibernateOperations.findByNamedQuery("getDefaultFeeServices");
 		Map<Long, String> map = new HashMap<Long, String>();
 		if(list != null){
 			for(FeeService servicesType :list){
 				map.put(servicesType.getId(), servicesType.getName());
 			}
 		}
 		return map;
	}

	@Override
	public Map<Long, String> getFeeServiceTypes(Long languageId) {
		List<FeeService> list = (List<FeeService>)hibernateOperations.findByNamedQuery("getFeeServicesForGivenLangauge", languageId);
 		Map<Long, String> map = new HashMap<Long, String>();
 		if(list != null){
 			for(FeeService servicesType: list){
 				map.put(servicesType.getId(), servicesType.getName());
 			}
 		}
 		return map;
	}

	@Override
	public Map<Long, FeeOperationType> getFeeOperationTypes(){
    	List<FeeOperationType> list = (List<FeeOperationType>)hibernateOperations.findByNamedQuery("getDefaultFeeOperationTypes");
    	return getOperationTypeMap(list);
	}
	
    @Override
	public Map<Long, FeeOperationType> getFeeOperationTypes(Long languageId){
    	List<FeeOperationType> list = (List<FeeOperationType>)hibernateOperations.findByNamedQuery("getFeeOperationTypesForGivenLangauge", languageId);
    	return getOperationTypeMap(list);
	}
    
    private Map<Long, FeeOperationType> getOperationTypeMap(List<FeeOperationType> list){
    	Map<Long, FeeOperationType> totalMap = new HashMap<Long, FeeOperationType>();
    	
    	if(list != null){
    		for(FeeOperationType operationType: list){
    				totalMap.put(operationType.getId(), operationType);
			}
    	}
    	return totalMap;
    }

    @Override
	public Map<Long, String> getFeePayingEntities() {
		List<FeePayingEntity> list = (List<FeePayingEntity>)hibernateOperations.findByNamedQuery("getDefaultFeePayingEntity");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(FeePayingEntity fpe :list){
				map.put(fpe.getId(),fpe.getName());
			}
		}
		return map;
	}

    @Override
	public Map<Long, String> getFeePayingEntities(Long languageId) {
		List<FeePayingEntity> list = (List<FeePayingEntity>)hibernateOperations.findByNamedQuery("getFeePayingEntityForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(FeePayingEntity fpe: list){
				map.put(fpe.getId(), fpe.getName());
			}
		}
		return map;
	}
    
    @Override
	public Map<Long, String> getFeeTimeFrequency() {
    	List<FeeTimeFrequency> list = (List<FeeTimeFrequency>)hibernateOperations.findByNamedQuery("getDefaultFeeTimeFrequency");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(FeeTimeFrequency ftf :list){
				map.put(ftf.getId(),ftf.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getFeeTimeFrequencys(Long languageId) {
		List<FeeTimeFrequency> list = (List<FeeTimeFrequency>)hibernateOperations.findByNamedQuery("getFeeTimeFrequencyForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(FeeTimeFrequency ftf: list){
				map.put(ftf.getId(), ftf.getName());
			}
		}
		return map;
	}

	
	@Override
	public Map<Long, String> getTrxTimePeriod() {
		List<TrxTimePeriod> list = (List<TrxTimePeriod>)hibernateOperations.findByNamedQuery("getDefaultTrxTimePeriod");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(TrxTimePeriod ttp :list){
				map.put(ttp.getId(), ttp.getName());
			}
		}
		return map;
	}
	
	@Override
	public Map<Long, String> getTrxTimePeriods(Long languageId) {
		List<TrxTimePeriod> list = (List<TrxTimePeriod>)hibernateOperations.findByNamedQuery("getTrxTimePeriodForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(TrxTimePeriod ttp: list){
				map.put(ttp.getId(), ttp.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, Double> getMasterAmountWalletCurrencyWise() {
		 List<Object[]> list = (List<Object[]>)hibernateOperations.findByNamedQuery("getMasterAmountWalletCurrencyWise");
		 Map<Long, Double> map = new HashMap<Long, Double>();
		 if(list != null){
			 for(int i = 0; i < list.size(); i++){
				 map.put((Long)list.get(i)[0], (Double)list.get(i)[1]);
			 }
		 }
		 return map;
	}

	@Override
	public Map<Long, String> getTransactionStatusName() {
		List<TransactionStatus> list = (List<TransactionStatus>)hibernateOperations.findByNamedQuery("getDefaultTransactionStatus");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(TransactionStatus ts :list){
				map.put(ts.getId(), ts.getName());
			}
		}
		return map;
	}
	
	@Override
	public Map<Long, String> getTransactionStatusNames(Long languageId) {
		List<TransactionStatus> list = (List<TransactionStatus>)hibernateOperations.findByNamedQuery("getTransactionStatusGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(TransactionStatus ts: list){
				map.put(ts.getId(), ts.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getReceiveMoneyStatusName() {
		List<ReceiveMoneyStatus> list = (List<ReceiveMoneyStatus>)hibernateOperations.findByNamedQuery("getDefaultReceiveMoneyStatus");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(ReceiveMoneyStatus rm :list){
				map.put(rm.getId(), rm.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getReceiveMoneyStatusNames(Long languageId) {
		List<ReceiveMoneyStatus> list = (List<ReceiveMoneyStatus>)hibernateOperations.findByNamedQuery("getReceiveMoneyStatusGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(ReceiveMoneyStatus rm: list){
				map.put(rm.getId(), rm.getName());
			}
		}
		return map;
	}
	
	@Override
	public Map<Long, String> getDestinationType() {
		List<DestinationType> list = (List<DestinationType>)hibernateOperations.findByNamedQuery("getDefaultDestinationType");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(DestinationType dt :list){
				map.put(dt.getId(), dt.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getDestinationTypes(Long languageId) {
		List<DestinationType> list = (List<DestinationType>)hibernateOperations.findByNamedQuery("getDestinationTypeForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(DestinationType dt: list){
				map.put(dt.getId(), dt.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, Map<Long, String>> getReportTypes() {
		List<ReportType> list = (List<ReportType>)hibernateOperations.findByNamedQuery("getDefaultReportTypes");
		return MasterDataUtil.getReportTypesMap(list);
	}

	@Override
	public Map<Long, Map<Long, String>> getReportTypes(Long languageId) {
    	List<ReportType> list = (List<ReportType>)hibernateOperations.findByNamedQuery("getReportTypesForGivenLangauge", languageId);
    	return MasterDataUtil.getReportTypesMap(list);
	}

	@Override
	public Map<Long, String> getDisputeType() {
		List<DisputeType> list = (List<DisputeType>)hibernateOperations.findByNamedQuery("getDefaultDisputeType");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(DisputeType dt :list){
				map.put(dt.getId(), dt.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getDisputeTypes(Long languageId) {
		List<DisputeType> list = (List<DisputeType>)hibernateOperations.findByNamedQuery("getDisputeTypeForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(DisputeType dt: list){
				map.put(dt.getId(), dt.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getAccountClosureStatus() {
		List<AccountClosureStatus> list = (List<AccountClosureStatus>)hibernateOperations.findByNamedQuery("getDefaultAccountClosureStatus");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(AccountClosureStatus acs :list){
				map.put(acs.getId(), acs.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getAccountClosureStatus(Long languageId) {
		List<AccountClosureStatus> list = (List<AccountClosureStatus>)hibernateOperations.findByNamedQuery("getAccountClosureStatusForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(AccountClosureStatus acs: list){
				map.put(acs.getId(), acs.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getDisputeStatus() {
		List<DisputeStatus> list = (List<DisputeStatus>)hibernateOperations.findByNamedQuery("getDefaultDisputeStatus");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(DisputeStatus ds :list){
				map.put(ds.getId(), ds.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getDisputeStatus(Long languageId) {
		List<DisputeStatus> list = (List<DisputeStatus>)hibernateOperations.findByNamedQuery("getDisputeStatusForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(DisputeStatus ds: list){
				map.put(ds.getId(), ds.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, Double> getTaxesCurrencyWise() {
		 List<Object[]> list = (List<Object[]>)hibernateOperations.findByNamedQuery("getTaxesCurrencyWise");
		 Map<Long, Double> map = new HashMap<Long, Double>();
		 if(list != null){
			 for(int i = 0; i < list.size(); i++){
				 map.put((Long)list.get(i)[0], (Double)list.get(i)[1]);
			 }
		 }
		 return map;
	}

	@Override
	public Map<Long, Double> getFeesCurrencyWise() {
		 List<Object[]> list = (List<Object[]>)hibernateOperations.findByNamedQuery("getFeesCurrencyWise");
		 Map<Long, Double> map = new HashMap<Long, Double>();
		 if(list != null){
			 for(int i = 0; i < list.size(); i++){
				 map.put((Long)list.get(i)[0], (Double)list.get(i)[1]);
			 }
		 }
		 return map;
	}

	@Override
	public Map<String, String> getPaymentMessages() {
		List<Object[]> list = (List<Object[]>)hibernateOperations.findByNamedQuery("getPaymentMessages");
		 Map<String, String> map = new HashMap<String, String>();
		 if(list != null){
			 for(int i = 0; i < list.size(); i++){
				 map.put((String)list.get(i)[0],(String) list.get(i)[1]);
			 }
		 }
		 return map;
	}

	@Override
	public Map<Long, String> getQueryOrFeedback() {
		List<QueryOrFeedback> list = (List<QueryOrFeedback>)hibernateOperations.findByNamedQuery("getQueryOrFeedback");
		 Map<Long, String> map = new HashMap<Long, String>();
		 if(list != null){
			 for(QueryOrFeedback qf : list){
					map.put(qf.getId(), qf.getName());
		      }
		 }
		 return map;
	}

	@Override
	public Map<Long, String> getQueryOrFeedback(Long languageId) {
		List<QueryOrFeedback> list = (List<QueryOrFeedback>)hibernateOperations.findByNamedQuery("getQueryOrFeedbackForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(QueryOrFeedback qf : list){
				map.put(qf.getId(), qf.getName());
			}
		}
		return map;
	}

	@Override
	public Map<String, String> getErrorCodes() {
		List<Object[]> list = (List<Object[]>)hibernateOperations.findByNamedQuery("getErrorCodes");
		 Map<String, String> map = new HashMap<String, String>();
		 if(list != null){
			 for(int i = 0; i < list.size(); i++){
				 map.put((String)list.get(i)[0],(String) list.get(i)[1]);
			 }
		 }
		 return map;
	}
	
	@Override
	public Map<Long, String> getChannelType() {
		List<ChannelType> list = (List<ChannelType>)hibernateOperations.findByNamedQuery("getDefaultChannelType");
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(ChannelType ct :list){
				map.put(ct.getId(), ct.getName());
			}
		}
		return map;
	}

	@Override
	public Map<Long, String> getChannelTypes(Long languageId) {
		List<ChannelType> list = (List<ChannelType>)hibernateOperations.findByNamedQuery("getChannelTypeForGivenLanguage", languageId);
		Map<Long, String> map = new HashMap<Long, String>();
		if(list != null){
			for(ChannelType ct: list){
				map.put(ct.getId(), ct.getName());
			}
		}
		return map;
	}

	@Override
	public Map<String, String> getPGCodes() {
		List<Object[]> list = (List<Object[]>)hibernateOperations.findByNamedQuery("getPGCodes");
		 Map<String, String> map = new HashMap<String, String>();
		 if(list != null){
			 for(int i = 0; i < list.size(); i++){
				 map.put((String)list.get(i)[0],(String) list.get(i)[1]);
			 }
		 }
		 return map;
	}
	
	@Override
	public MasterVelocity createMasterVelocity(MasterVelocity masterVelocity) {
		try{
			hibernateOperations.save(masterVelocity);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		return masterVelocity;
	}

	@Override
	public VelocitySetting createVelocitySetting(VelocitySetting velocitySetting) {
		try{
			hibernateOperations.save(velocitySetting);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		return velocitySetting;
	}
}