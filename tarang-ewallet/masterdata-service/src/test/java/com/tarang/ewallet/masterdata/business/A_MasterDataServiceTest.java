/**
 * 
 */

package com.tarang.ewallet.masterdata.business;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.masterdata.business.MasterDataService;
import com.tarang.ewallet.model.Country;
import com.tarang.ewallet.model.Currency;
import com.tarang.ewallet.model.FeeOperationType;
import com.tarang.ewallet.model.Language;



/**
 * @author prasadj
 *
 */

@ContextConfiguration(locations={"classpath*:/wallet-applicationContext.xml",
		"classpath*:/wallettest-applicationContext.xml"})
@TransactionConfiguration(defaultRollback=true)
public class A_MasterDataServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	
    @Autowired
    private MasterDataService masterDataService;
    
    @Autowired
    private HibernateTemplate hibernateTemplate;
    
    public void prepareData(int datatype){
    	//prepare data
    	List<Object> data = DataPreparation.getData(datatype);
    	Iterator<Object> iter = data.iterator();
    	while(iter.hasNext()){
    		hibernateTemplate.save(iter.next());
    	}
    }

    @Test
	public void getLanguagesTest(){
    	
    	prepareData(DataPreparation.LANGUAGES);
    	Map<Long, Map<Long, Language>> data = masterDataService.getLanguages();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, Language> defaultLang = data.get(0L);
		Assert.assertEquals(3, defaultLang.keySet().size());
		Map<Long, Language> lj = data.get(2L);
		Language jj = lj.get(2L);
		Assert.assertEquals("JJ", jj.getCode());
	}

    /*
    @Test
	public void getMenusTest(){
    	
    	prepareData(DataPreparation.MENUS);
		Map<Long, Map<Long, Menu>> data = masterDataService.getMenus();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, Menu> defaultMenus = data.get(0L);
		Assert.assertEquals(17, defaultMenus.keySet().size());
		Map<Long, Menu> fj = data.get(2L);
		Menu one = fj.get(10L);
		Assert.assertEquals("Transaction History - jp", one.getMenuName());
		Menu two = fj.get(17L);
		Assert.assertEquals("Preferences - jp", two.getMenuName());
	}
*/
    @Test
	public void getCountriesTest(){
    	
    	prepareData(DataPreparation.COUNTRIES);
		Map<Long, Map<Long, Country>> data = masterDataService.getCountries();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, Country> defaultCountries = data.get(0L);
		Assert.assertEquals(3, defaultCountries.keySet().size());
		Assert.assertEquals("001", defaultCountries.get(1L).getPcode());
		Map<Long, Country> fj = data.get(1L);
		Country one = fj.get(1L);
		Assert.assertEquals("USe", one.getCode());
		Country two = fj.get(2L);
		Assert.assertEquals("Japan - en", two.getName());
	}

    @Test
	public void getCurrenciesTest(){
    	
    	prepareData(DataPreparation.CURRENCIES);
		Map<Long, Map<Long, Currency>> data = masterDataService.getCurrencies();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, Currency> defaultCurrencies = data.get(0L);
		Assert.assertEquals(3, defaultCurrencies.keySet().size());
		Map<Long, Currency> fj = data.get(3L);
		Assert.assertEquals("Yen - th", fj.get(2L).getName());
		Assert.assertEquals("Baht - th", fj.get(3L).getName());
	}
    
    @Test
	public void getCurrencyCodesTest(){
    	
    	prepareData(DataPreparation.CURRENCIES);
		Map<String, Long> data = masterDataService.getCurrencyCodes();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(3, data.keySet().size());
		
		Assert.assertEquals(new Long(1), data.get("USD"));
		Assert.assertEquals(new Long(2), data.get("JPY"));
		Assert.assertEquals(new Long(3), data.get("THB"));
		
	}

    @Test
	public void getCardTypesTest(){
    	
    	prepareData(DataPreparation.CARDTYPES);
		Map<Long, Map<Long, String>> data = masterDataService.getCardTypes();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> defaultCardTypes = data.get(0L);
		Assert.assertEquals(4, defaultCardTypes.keySet().size());
		Map<Long, String> fj = data.get(1L);
		Assert.assertEquals("Visa - en", fj.get(1L));
		Assert.assertEquals("Master - en", fj.get(2L));
	}

    @Test
	public void getFrequenciesTest(){
    	
    	prepareData(DataPreparation.FREQUENCIES);
		Map<Long, Map<Long, String>> data = masterDataService.getFrequencies();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> defaultFrequencies = data.get(0L);
		Assert.assertEquals(4, defaultFrequencies.keySet().size());
		Map<Long, String> fj = data.get(3L);
		Assert.assertEquals("By-Weekly - th", fj.get(3L));
		Assert.assertEquals("Monthly - th", fj.get(4L));
	}

    @Test
	public void getUserTypesTest(){
    	
    	prepareData(DataPreparation.USER_TYPES);
		Map<Long, Map<Long, String>> data = masterDataService.getUserTypes();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> defaultUserTypes = data.get(0L);
		Assert.assertEquals(4, defaultUserTypes.keySet().size());
		Map<Long, String> fj = data.get(2L);
		Assert.assertEquals("Customer - jp", fj.get(1L));
		Assert.assertEquals("Merchant - jp", fj.get(2L));
		Assert.assertEquals("Admin - jp", fj.get(3L));
	}

    @Test
	public void getCTransactionTypesTest(){
    	
    	prepareData(DataPreparation.CTRANSACTION_TYPES);
		Map<Long, Map<Long, String>> data = masterDataService.getCTransactionTypes();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> dctt = data.get(0L);
		Assert.assertEquals(3, dctt.keySet().size());
		Map<Long, String> fj = data.get(3L);
		Assert.assertEquals("Refunds - th", fj.get(1L));
		Assert.assertEquals("Payments Received - th", fj.get(2L));
	}

    @Test
	public void getMTransactionTypesTest(){
    	
    	prepareData(DataPreparation.MTRANSACTION_TYPES);
		Map<Long, Map<Long, String>> data = masterDataService.getMTransactionTypes();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> dmtt = data.get(0L);
		Assert.assertEquals(3, dmtt.keySet().size());
		Map<Long, String> fj = data.get(1L);
		Assert.assertEquals("Refunds - en", fj.get(1L));
		Assert.assertEquals("Payments Sent - en", fj.get(3L));
	}

    @Test
	public void getATransactionTypesTest(){
    	
    	prepareData(DataPreparation.ATRANSACTION_TYPES);
		Map<Long, Map<Long, String>> data = masterDataService.getATransactionTypes();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> datt = data.get(0L);
		Assert.assertEquals(3, datt.keySet().size());
		Map<Long, String> fj = data.get(2L);
		Assert.assertEquals("Refunds - jp", fj.get(1L));
		Assert.assertEquals("Payments Sent - jp", fj.get(3L));
	}

    @Test
	public void getMerchantOwnerTypesTest(){
    	
    	prepareData(DataPreparation.MERCHANT_OWNER_TYPES);
		Map<Long, Map<Long, String>> data = masterDataService.getMerchantOwnerTypes();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(6, def.keySet().size());
		Map<Long, String> fj = data.get(3L);
		Assert.assertEquals("Other Corporate Body - th", fj.get(4L));
		Assert.assertEquals("Government Entity - th", fj.get(5L));
	}
    
    @Test
	public void getMerchantBusinessCategoriesTest(){
    	
    	prepareData(DataPreparation.MERCHANT_BUSINESS_CATEGORY);
		Map<Long, Map<Long, String>> data = masterDataService.getMerchantBusinessCategories();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(5, def.keySet().size());
		Map<Long, String> fj = data.get(1L);
		Assert.assertEquals("Baby - en", fj.get(2L));
		Assert.assertEquals("Government - en", fj.get(3L));
	}
    

    @Test
	public void getMerchantBusinessSubCategoriesTest(){
    	
    	prepareData(DataPreparation.MERCHANT_BUSINESS_SUB_CATEGORY);
		Map<Long, Map<Long, Map<Long, String>>> data = masterDataService.getMerchantBusinessSubCategories();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, Map<Long, String>> def = data.get(0L);
		Assert.assertEquals(5, def.keySet().size());
		Assert.assertEquals(2, def.get(1L).size());
		
		Assert.assertEquals("Arts - en", data.get(1L).get(1L).get(1L));
	}
    
    @Test
	public void getMerchantAvgTxAmountsTest(){
    	
    	prepareData(DataPreparation.MERCHANT_AVERAGE_TX_AMOUNT);
		Map<Long, Map<Long, String>> data = masterDataService.getMerchantAvgTxAmounts();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(5, def.keySet().size());
		Map<Long, String> fj = data.get(2L);
		Assert.assertEquals("1 - 10 dollers - jp", fj.get(1L));
		Assert.assertEquals("above 100 dollers - jp", fj.get(5L));
	}

    @Test
	public void getMerchantHighestMonthlyVolumesTest(){
    	
    	prepareData(DataPreparation.MERCHANT_HIGHTEST_MONTYLY_VOLUMES);
		Map<Long, Map<Long, String>> data = masterDataService.getMerchantHighestMonthlyVolumes();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(5, def.keySet().size());
		Map<Long, String> fj = data.get(3L);
		Assert.assertEquals("2001 - 5000 dollers - th", fj.get(3L));
		Assert.assertEquals("5001 - 10000 dollers - th", fj.get(4L));
	}

    @Test
	public void getMerchantPercentageAnualRevenuesTest(){
    	
    	prepareData(DataPreparation.MERCHANT_PERCENTAGE_ANUAL_REVENUE);
		Map<Long, Map<Long, String>> data = masterDataService.getMerchantPercentageAnualRevenues();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(5, def.keySet().size());
		Map<Long, String> fj = data.get(1L);
		Assert.assertEquals("06 - 10 Percent - en", fj.get(2L));
		Assert.assertEquals("20 - 50 Percent - en", fj.get(4L));
	}

    @Test
	public void getUserStatusesTest(){
    	
    	prepareData(DataPreparation.USER_STATUS);
		Map<Long, Map<Long, String>> data = masterDataService.getUserStatuses();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(5, def.keySet().size());
		Map<Long, String> fj = data.get(2L);
		Assert.assertEquals("Approved - jp", fj.get(1L));
		Assert.assertEquals("Locked - jp", fj.get(3L));
	}

    @Test
	public void getHintQuestionsTest(){
    	
    	prepareData(DataPreparation.HINT_QUESTIONS);
		Map<Long, Map<Long, String>> data = masterDataService.getHintQuestions();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(3, def.keySet().size());
		Map<Long, String> fj = data.get(3L);
		Assert.assertEquals("What was the name of your first school? - th", fj.get(2L));
		Assert.assertEquals("What is the name of your best friend? - th", fj.get(3L));
	}

    @Test
	public void getRegionsTest(){
    	
    	prepareData(DataPreparation.REGIONS);
		Map<Long, Map<Long, Map<Long, String>>> data = masterDataService.getRegions();
		Long en_languageId = 1L;
		Long us_countryId = 1L; // US
		Long jp_countryId = 2L; // Japan
		Long r_1Id = 1L;
		Long r_2Id = 68L;

		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, Map<Long, String>> def = data.get(0L);
		Assert.assertEquals(3, def.keySet().size());// no. of languages
		Assert.assertEquals(50, def.get(us_countryId).size()); // no. of states
		
		Assert.assertEquals("Alabama - en", data.get(en_languageId).get(us_countryId).get(r_1Id));
		Assert.assertEquals("Kagoshima - en", data.get(en_languageId).get(jp_countryId).get(r_2Id));
	}
    
    @Test
	public void getTitlesTest(){
    	
    	prepareData(DataPreparation.TITLES);
		Map<Long, Map<Long, String>> data = masterDataService.getTitles();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(5, def.keySet().size());
		Map<Long, String> fj = data.get(2L);
		Assert.assertEquals("Mr. - jp", fj.get(1L));
		Assert.assertEquals("Ms - jp", fj.get(4L));
	}

    @Test
	public void getMoneyAccountStatusTest(){
    	
    	prepareData(DataPreparation.MONEY_ACCOUNT_STATUS);
		Map<Long, Map<Long, String>> data = masterDataService.getMoneyAccountStatuses();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(4, def.keySet().size());
		Map<Long, String> fj = data.get(2L);
		Assert.assertEquals("Not Verified - jp", fj.get(1L));
		Assert.assertEquals("Verified - jp", fj.get(4L));
	}

    @Test
	public void getMoneyAccountDeleteStatusTest(){
    	
    	prepareData(DataPreparation.MONEY_ACCOUNT_DELETE_STATUS);
		Map<Long, Map<Long, String>> data = masterDataService.getMoneyAccountDeleteStatuses();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(2, def.keySet().size());
		Map<Long, String> fj = data.get(3L);
		Assert.assertEquals("Not Delete - th", fj.get(1L));
		Assert.assertEquals("Soft Delete - th", fj.get(2L));
	}

    @Test
	public void getBankAccountTypesTest(){
    	
    	prepareData(DataPreparation.BANK_ACCOUNT_TYPES);
		Map<Long, Map<Long, Map<Long, String>>> data = masterDataService.getBankAccountTypes();
		Long df_landuageId = 0L; //default language
		Long en_languageId = 1L;
		Long us_countryId = 1L; // US
		Long jp_countryId = 2L; // Japan
		Long b_1Id = 9L;
		Long b_2Id = 2L;
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, Map<Long, String>> def = data.get(df_landuageId);
		Assert.assertEquals(3, def.keySet().size()); // number of countries
		Assert.assertEquals(2, def.get(us_countryId).size()); // number of types for us country
		
		Assert.assertEquals("Sonota", data.get(df_landuageId).get(jp_countryId).get(b_1Id));
		Assert.assertEquals("Sonota - en", data.get(en_languageId).get(jp_countryId).get(b_1Id));
		
		Assert.assertEquals("Touza", data.get(df_landuageId).get(jp_countryId).get(b_2Id));
		Assert.assertEquals("Touza - en", data.get(en_languageId).get(jp_countryId).get(b_2Id));

    }
    
    @Test
	public void getReportTypesTest(){
    	prepareData(DataPreparation.REPORT_TYPE);
		Map<Long, Map<Long, Map<Long, String>>> data = masterDataService.getReportTypes();
		
		Long df_landuageId = 0L; //default language
		Long en_languageId = 1L;
		Long jp_languageId = 2L;
		Long th_languageId = 3L;
		
		Long customer_user = 1L;
		Long merchant_user = 2L;
		Long admin_user = 3L;

		Long reportId = 1L;

		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());
		
		Map<Long, Map<Long, String>> def = data.get(df_landuageId);
		Assert.assertEquals(3, def.keySet().size()); // number of usertype
		Assert.assertEquals(6, def.get(customer_user).size()); // number of reportType for  customer
		Assert.assertEquals(7, def.get(merchant_user).size()); // number of reportType for  merchant
		Assert.assertEquals(13, def.get(admin_user).size()); // number of reportType for  admin

		def = data.get(en_languageId);
		Assert.assertEquals(3, def.keySet().size()); // number of usertype
		Assert.assertEquals(6, def.get(customer_user).size()); // number of reportType for  customer
		Assert.assertEquals(7, def.get(merchant_user).size()); // number of reportType for  merchant
		Assert.assertEquals(13, def.get(admin_user).size()); // number of reportType for  admin
	
		def = data.get(jp_languageId);
		Assert.assertEquals(3, def.keySet().size()); // number of usertype
		Assert.assertEquals(6, def.get(customer_user).size()); // number of reportType for  customer
		Assert.assertEquals(7, def.get(merchant_user).size()); // number of reportType for  merchant
		Assert.assertEquals(13, def.get(admin_user).size()); // number of reportType for  admin
		
		def = data.get(th_languageId);
		Assert.assertEquals(3, def.keySet().size()); // number of usertype
		Assert.assertEquals(6, def.get(customer_user).size()); // number of reportType for  customer
		Assert.assertEquals(7 ,def.get(merchant_user).size()); // number of reportType for  merchant
		Assert.assertEquals(13, def.get(admin_user).size()); // number of reportType for  admin
		
		Assert.assertEquals("Last N Transactions", data.get(df_landuageId).get(customer_user).get(reportId));
		reportId = 4L;
		Assert.assertEquals("Send Money Summary", data.get(df_landuageId).get(customer_user).get(reportId));
		
		reportId = 1L;
		Assert.assertEquals("Last N Transactions - en", data.get(en_languageId).get(customer_user).get(reportId));
		reportId = 4L;
		Assert.assertEquals("Send Money Summary - en", data.get(en_languageId).get(customer_user).get(reportId));
		
		reportId = 1L;
		Assert.assertEquals("Last N Transactions - jp", data.get(jp_languageId).get(customer_user).get(reportId));
		reportId = 4L;
		Assert.assertEquals("Send Money Summary - jp", data.get(jp_languageId).get(customer_user).get(reportId));
		
		reportId = 1L;       
		Assert.assertEquals("Last N Transactions - th", data.get(th_languageId).get(customer_user).get(reportId));
		reportId = 4L;
		Assert.assertEquals("Send Money Summary - th", data.get(th_languageId).get(customer_user).get(reportId));


    }	
    @Test
	public void getFeeTypesTest(){
    	
    	prepareData(DataPreparation.FEE_TYPE);
		Map<Long, Map<Long, String>> data = masterDataService.getFeeTypes();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(3, def.keySet().size());
		Map<Long, String> fj = data.get(2L);
		Assert.assertEquals("Flat fee - jp", fj.get(1L));
		Assert.assertEquals("Flat fee + percentage of the transaction amount - jp", fj.get(3L));
	}
    
    @Test
	public void getFeeServiceTypesTest(){
    	
    	prepareData(DataPreparation.FEE_SERVICE_TYPES);
		Map<Long, Map<Long, String>> data = masterDataService.getFeeServiceTypes();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> def = data.get(0L);
		Assert.assertEquals(3, def.keySet().size());
		Map<Long, String> fj = data.get(2L);
		Assert.assertEquals("Financial Services - jp", fj.get(1L));
		Assert.assertEquals("Non Financial Services - jp", fj.get(2L));
	}
    
    
    @Test
   	public void getFeeOperationTypesDataTest(){
    	prepareData(DataPreparation.FEE_OPERATION_TYPES);
		Map<Long, Map<Long, FeeOperationType>> data = masterDataService.getFeeOperationTypes();
       	Assert.assertNotNull(data );
		Assert.assertEquals(4, data.size());
		Map<Long, FeeOperationType> fotMap = data.get(0L);
		Long expected =1L;
		Long expone = 1L;
		Long exptwo = 2L;
		
		expected =4L;
		Assert.assertEquals(expected, fotMap.get(4L).getId());
		Assert.assertEquals(expone, fotMap.get(4L).getFeeServiceId());	
		Assert.assertEquals(expone, fotMap.get(4L).getUserTypeId());
		Assert.assertEquals("Withdraw money to Card", fotMap.get(4L).getName());
		
		expected =12L;
		Assert.assertEquals(expected, fotMap.get(12L).getId());
		Assert.assertEquals(exptwo, fotMap.get(12L).getFeeServiceId());	
		Assert.assertEquals(expone, fotMap.get(12L).getUserTypeId());
		Assert.assertEquals("Customer annual fee", fotMap.get(12L).getName());
		
		expected =20L;
		Assert.assertEquals(expected, fotMap.get(20L).getId());
		Assert.assertEquals(expone, fotMap.get(20L).getFeeServiceId());	
		Assert.assertEquals(exptwo, fotMap.get(20L).getUserTypeId());
		Assert.assertEquals("Withdraw money to Bank", fotMap.get(20L).getName());
		                     
		expected =32L;
		Assert.assertEquals(expected, fotMap.get(32L).getId());
		Assert.assertEquals(expone, fotMap.get(32L).getFeeServiceId());	
		Assert.assertEquals(expone, fotMap.get(32L).getUserTypeId());
		Assert.assertEquals("P 2 S", fotMap.get(32L).getName());

		expected =33L;
		Assert.assertEquals(expected, fotMap.get(33L).getId());
		Assert.assertEquals(expone, fotMap.get(33L).getFeeServiceId());	
		Assert.assertEquals(exptwo, fotMap.get(33L).getUserTypeId());
		Assert.assertEquals("M 2 S", fotMap.get(33L).getName());
		
		
		fotMap = data.get(1L);
		expected =1L;
		expone = 1L;
		exptwo = 2L;
		
		expected =4L;
		Assert.assertEquals(expected, fotMap.get(4L).getId());
		Assert.assertEquals(expone, fotMap.get(4L).getFeeServiceId());	
		Assert.assertEquals(expone, fotMap.get(4L).getUserTypeId());
		Assert.assertEquals("Withdraw money to Card - en", fotMap.get(4L).getName());
		
		expected =12L;
		Assert.assertEquals(expected, fotMap.get(12L).getId());
		Assert.assertEquals(exptwo, fotMap.get(12L).getFeeServiceId());	
		Assert.assertEquals(expone, fotMap.get(12L).getUserTypeId());
		Assert.assertEquals("Customer annual fee - en", fotMap.get(12L).getName());
		
		expected =20L;
		Assert.assertEquals(expected, fotMap.get(20L).getId());
		Assert.assertEquals(expone, fotMap.get(20L).getFeeServiceId());	
		Assert.assertEquals(exptwo, fotMap.get(20L).getUserTypeId());
		Assert.assertEquals("Withdraw money to Bank - en", fotMap.get(20L).getName());
		
		expected =32L;
		Assert.assertEquals(expected, fotMap.get(32L).getId());
		Assert.assertEquals(expone, fotMap.get(32L).getFeeServiceId());	
		Assert.assertEquals(expone, fotMap.get(32L).getUserTypeId());
		Assert.assertEquals("P 2 S - en", fotMap.get(32L).getName());

		expected =33L;
		Assert.assertEquals(expected, fotMap.get(33L).getId());
		Assert.assertEquals(expone, fotMap.get(33L).getFeeServiceId());	
		Assert.assertEquals(exptwo, fotMap.get(33L).getUserTypeId());
		Assert.assertEquals("M 2 S - en", fotMap.get(33L).getName());

   	}  
    
   
    @Test
	public void getFeePayingEntitiesTest(){
    	
    	prepareData(DataPreparation.FEE_PAYING_ENTITY);
		Map<Long, Map<Long, String>> data = masterDataService.getFeePayingEntities();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> defaultFpe = data.get(0L);
		Assert.assertEquals(3, defaultFpe.keySet().size());
		Map<Long, String> fj = data.get(1L);
		Assert.assertEquals("Receiver - en", fj.get(2L));
		Assert.assertEquals("Both - en", fj.get(3L));
	}
    
    @Test
    public void getCountryByIdTest(){
    	prepareData(DataPreparation.COUNTRIES);
		Country country = masterDataService.getCountryById(3L);
    	Assert.assertNotNull( country.getId() );
    	Assert.assertNotNull( country.getCurrency());
    }
    
    @Test
	public void getFeeTimeFrequencyTest(){
    	
    	prepareData(DataPreparation.FEE_TIME_FREQUENCY);
		Map<Long, Map<Long, String>> data = masterDataService.getFeeTimeFrequency();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> defaultFpe = data.get(0L);
		Assert.assertEquals(5, defaultFpe.keySet().size());
		Map<Long, String> fj = data.get(1L);
		Assert.assertEquals("Monthly - en", fj.get(2L));
		Assert.assertEquals("Quarterly - en", fj.get(3L));
	}
    
    @Test
	public void getTrxTimePeriodTest(){
    	
    	prepareData(DataPreparation.TRX_TIME_PERIOD);
		Map<Long, Map<Long, String>> data = masterDataService.getTrxTimePeriod();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> defaultFpe = data.get(0L);
		Assert.assertEquals(3, defaultFpe.keySet().size());
		Map<Long, String> fj = data.get(1L);
		Assert.assertEquals("Weekly - en", fj.get(2L));
		Assert.assertEquals("Monthly - en", fj.get(3L));
	}
    
    @Test
	public void getTransactionStatusTest(){
    	
    	prepareData(DataPreparation.TRX_STATUS);
		Map<Long, Map<Long, String>> data = masterDataService.getTransactionStatusName();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> defaultTs = data.get(0L);
		Assert.assertEquals(8, defaultTs.keySet().size());
		Map<Long, String> fj = data.get(1L);
		Assert.assertEquals("Cancel - en", fj.get(2L));
		Assert.assertEquals("Reject - en", fj.get(3L));
		Assert.assertEquals("Failed - en", fj.get(6L));
		Assert.assertEquals("Expired - en", fj.get(7L));
		Assert.assertEquals("Fraud Check - en", fj.get(8L));
	}

    @Test
	public void getReceiveMoneyStatusTest(){
    	
    	prepareData(DataPreparation.RECEIVE_MONEY_STATUS);
		Map<Long, Map<Long, String>> data = masterDataService.getReceiveMoneyStatusName();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> defaultRm = data.get(0L);
		Assert.assertEquals(5, defaultRm.keySet().size());
		Map<Long, String> fj = data.get(1L);
		Assert.assertEquals("Cancel - en", fj.get(2L));
		Assert.assertEquals("Reject - en", fj.get(3L));
	}
	
	@Test
    public void getDestinationTypeTest(){
    	
    	prepareData(DataPreparation.DESTINATION_TYPE);
    	Map<Long, Map<Long, String>> data = masterDataService.getDestinationType();
    	
    	Assert.assertNotNull(data );
    	Assert.assertEquals(4, data.keySet().size());
    	
    	Map<Long, String> defaultDt = data.get(0L);
    	Assert.assertEquals(3, defaultDt.keySet().size());
    	Map<Long, String> dt = data.get(1L);
    	Assert.assertEquals("Non Registered Member - en", dt.get(4L));
    	Assert.assertEquals("Merchant - en", dt.get(2L));
    }

    @Test
	public void getDisputeTypeTest(){
    	
    	prepareData(DataPreparation.DISPUTE_TYPE);
		Map<Long, Map<Long, String>> data = masterDataService.getDisputeType();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> defaultDt = data.get(0L);
		Assert.assertEquals(2, defaultDt.keySet().size());
		
		Map<Long, String> dten = data.get(1L);
		Assert.assertEquals("Refund - en", dten.get(1L));
		Assert.assertEquals("Chargeback - en", dten.get(2L));

		Map<Long, String> dtjp = data.get(2L);
		Assert.assertEquals("Refund - jp", dtjp.get(1L));
		Assert.assertEquals("Chargeback - jp", dtjp.get(2L));
		
		Map<Long, String> dtth = data.get(3L);
		Assert.assertEquals("Refund - th", dtth.get(1L));
		Assert.assertEquals("Chargeback - th", dtth.get(2L));
	}

    @Test
    public void getDisputeStatusTest(){
    	
    	prepareData(DataPreparation.DISPUTED_STATUS);
    	Map<Long, Map<Long, String>> data = masterDataService.getDisputeStatus();
    	
    	Assert.assertNotNull(data );
    	Assert.assertEquals(4, data.keySet().size());
    	
    	Map<Long, String> defaultACS = data.get(0L);
    	Assert.assertEquals(6, defaultACS.keySet().size());
    	
    	Map<Long, String> acsen = data.get(1L);
    	Assert.assertEquals("Pending - en", acsen.get(1L));
    	Assert.assertEquals("Admin rejected - en", acsen.get(2L));
    	
    	Map<Long, String> acsjp = data.get(2L);
    	Assert.assertEquals("Pending - jp", acsjp.get(1L));
    	Assert.assertEquals("Admin rejected - jp", acsjp.get(2L));
    	
    	Map<Long, String> acsth = data.get(3L);
    	Assert.assertEquals("Pending - th", acsth.get(1L));
    	Assert.assertEquals("Admin rejected - th", acsth.get(2L));
    }

    @Test
    public void getAccountClosureStatusTest(){
    	
    	prepareData(DataPreparation.ACCOUNT_CLOSURE_STATUS);
    	Map<Long, Map<Long, String>> data = masterDataService.getAccountClosureStatus();
    	
    	Assert.assertNotNull(data );
    	Assert.assertEquals(4, data.keySet().size());
    	
    	Map<Long, String> defaultACS = data.get(0L);
    	Assert.assertEquals(4, defaultACS.keySet().size());
    	
    	Map<Long, String> acsen = data.get(1L);
    	Assert.assertEquals("Pending - en", acsen.get(1L));
    	Assert.assertEquals("Approval - en", acsen.get(2L));
    	
    	Map<Long, String> acsjp = data.get(2L);
    	Assert.assertEquals("Pending - jp", acsjp.get(1L));
    	Assert.assertEquals("Approval - jp", acsjp.get(2L));
    	
    	Map<Long, String> acsth = data.get(3L);
    	Assert.assertEquals("Pending - th", acsth.get(1L));
    	Assert.assertEquals("Approval - th", acsth.get(2L));
    }
    
    @Test
    public void getPaymentMessagesTest(){
    	
    	prepareData(DataPreparation.PAYMENT_MESSAGES);
    	Map<String, String> data = masterDataService.getPaymentMessages();
    	
    	Assert.assertNotNull(data );
    	Assert.assertEquals(25, data.keySet().size());
    }
    
    @Test
    public void getQueryOrFeedbackTest(){
    	
    	prepareData(DataPreparation.QUERY_OR_FEEDBACK);
    	Map<Long,Map<Long, String>> data = masterDataService.getQueryOrFeedback();
    	
    	Assert.assertNotNull(data );
    	Assert.assertEquals(4, data.keySet().size());
    }
    
    @Test
    public void getErrorCodesTest(){
    	
    	prepareData(DataPreparation.ERROR_CODE);
    	Map<String, String> data = masterDataService.getErrorCodes();
    	
    	Assert.assertNotNull(data );
    	Assert.assertEquals(133, data.keySet().size());
    }

	@Test
	public void getChannelTypeTest(){
    	
    	prepareData(DataPreparation.CHANNEL_TYPE);
		Map<Long, Map<Long, String>> data = masterDataService.getChannelType();
		
		Assert.assertNotNull(data );
		Assert.assertEquals(4, data.keySet().size());

		Map<Long, String> defaultCt = data.get(0L);
		Assert.assertEquals(2, defaultCt.keySet().size());
		
		Map<Long, String> cten = data.get(1L);
		Assert.assertEquals("Mobile - en", cten.get(1L));
		Assert.assertEquals("Web - en", cten.get(2L));

		Map<Long, String> ctjp = data.get(2L);
		Assert.assertEquals("Mobile - jp", ctjp.get(1L));
		Assert.assertEquals("Web - jp", ctjp.get(2L));
		
		Map<Long, String> ctth = data.get(3L);
		Assert.assertEquals("Mobile - th", ctth.get(1L));
		Assert.assertEquals("Web - th", ctth.get(2L));
	}
	
 	@Test
    public void getPGCodesTest(){
    	
    	prepareData(DataPreparation.PG_CODE);
    	Map<String, String> data = masterDataService.getPGCodes();
    	
    	Assert.assertNotNull(data );
    	Assert.assertEquals(39, data.keySet().size());
    }
}
