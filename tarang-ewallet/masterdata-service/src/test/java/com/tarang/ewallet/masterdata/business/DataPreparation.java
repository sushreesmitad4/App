/**
 * 
 */
package com.tarang.ewallet.masterdata.business;

import java.util.ArrayList;
import java.util.List;

import com.tarang.ewallet.masterdata.util.CountryIds;
import com.tarang.ewallet.masterdata.util.CurrencyIds;
import com.tarang.ewallet.model.ATransactionType;
import com.tarang.ewallet.model.ATransactionTypeLocale;
import com.tarang.ewallet.model.AccountClosureStatus;
import com.tarang.ewallet.model.AccountClosureStatusLocale;
import com.tarang.ewallet.model.BankAccountType;
import com.tarang.ewallet.model.BankAccountTypeLocale;
import com.tarang.ewallet.model.CTransactionType;
import com.tarang.ewallet.model.CTransactionTypeLocale;
import com.tarang.ewallet.model.CardType;
import com.tarang.ewallet.model.CardTypeLocale;
import com.tarang.ewallet.model.ChannelType;
import com.tarang.ewallet.model.ChannelTypeLocale;
import com.tarang.ewallet.model.Country;
import com.tarang.ewallet.model.CountryLocale;
import com.tarang.ewallet.model.Currency;
import com.tarang.ewallet.model.CurrencyLocale;
import com.tarang.ewallet.model.DestinationType;
import com.tarang.ewallet.model.DestinationTypeLocale;
import com.tarang.ewallet.model.DisputeStatus;
import com.tarang.ewallet.model.DisputeStatusLocale;
import com.tarang.ewallet.model.DisputeType;
import com.tarang.ewallet.model.DisputeTypeLocale;
import com.tarang.ewallet.model.ErrorCode;
import com.tarang.ewallet.model.FeeOperationType;
import com.tarang.ewallet.model.FeeOperationTypeLocale;
import com.tarang.ewallet.model.FeePayingEntity;
import com.tarang.ewallet.model.FeePayingEntityLocale;
import com.tarang.ewallet.model.FeeService;
import com.tarang.ewallet.model.FeeServiceLocale;
import com.tarang.ewallet.model.FeeTimeFrequency;
import com.tarang.ewallet.model.FeeTimeFrequencyLocale;
import com.tarang.ewallet.model.FeeType;
import com.tarang.ewallet.model.FeeTypeLocale;
import com.tarang.ewallet.model.Frequency;
import com.tarang.ewallet.model.FrequencyLocale;
import com.tarang.ewallet.model.HintQuestion;
import com.tarang.ewallet.model.HintQuestionLocale;
import com.tarang.ewallet.model.Language;
import com.tarang.ewallet.model.LanguageLocale;
import com.tarang.ewallet.model.MTransactionType;
import com.tarang.ewallet.model.MTransactionTypeLocale;
import com.tarang.ewallet.model.MasterAmountWallet;
import com.tarang.ewallet.model.MasterFeeWallet;
import com.tarang.ewallet.model.MasterTaxWallet;
import com.tarang.ewallet.model.MerchantAvgTxAmount;
import com.tarang.ewallet.model.MerchantAvgTxAmountLocale;
import com.tarang.ewallet.model.MerchantBusinessCategory;
import com.tarang.ewallet.model.MerchantBusinessCategoryLocale;
import com.tarang.ewallet.model.MerchantBusinessSubCategory;
import com.tarang.ewallet.model.MerchantBusinessSubCategoryLocale;
import com.tarang.ewallet.model.MerchantHighestMonthlyVolume;
import com.tarang.ewallet.model.MerchantHighestMonthlyVolumeLocale;
import com.tarang.ewallet.model.MerchantOwnerType;
import com.tarang.ewallet.model.MerchantOwnerTypeLocale;
import com.tarang.ewallet.model.MerchantPercentageAnualRevenue;
import com.tarang.ewallet.model.MerchantPercentageAnualRevenueLocale;
import com.tarang.ewallet.model.MoneyAccountDeleteStatus;
import com.tarang.ewallet.model.MoneyAccountDeleteStatusLocale;
import com.tarang.ewallet.model.MoneyAccountStatus;
import com.tarang.ewallet.model.MoneyAccountStatusLocale;
import com.tarang.ewallet.model.PGCode;
import com.tarang.ewallet.model.PaymentMessage;
import com.tarang.ewallet.model.QueryOrFeedback;
import com.tarang.ewallet.model.QueryOrFeedbackLocale;
import com.tarang.ewallet.model.ReceiveMoneyStatus;
import com.tarang.ewallet.model.ReceiveMoneyStatusLocale;
import com.tarang.ewallet.model.Region;
import com.tarang.ewallet.model.RegionLocale;
import com.tarang.ewallet.model.ReportType;
import com.tarang.ewallet.model.ReportTypeLocale;
import com.tarang.ewallet.model.Title;
import com.tarang.ewallet.model.TitleLocale;
import com.tarang.ewallet.model.TransactionStatus;
import com.tarang.ewallet.model.TransactionStatusLocale;
import com.tarang.ewallet.model.TrxTimePeriod;
import com.tarang.ewallet.model.TrxTimePeriodLocale;
import com.tarang.ewallet.model.UserStatus;
import com.tarang.ewallet.model.UserStatusLocale;
import com.tarang.ewallet.model.UserType;
import com.tarang.ewallet.model.UserTypeLocale;


/**
 * @author : prasadj
 * @date : Oct 1, 2012
 * @time : 10:11:07 AM
 * @version : 1.0.0
 * @comments: populate the master data in database
 * 
 */
public class DataPreparation{

	public static final int LANGUAGES = 0;
	//public static final int MENUS = 1;
	public static final int COUNTRIES = 2;
	public static final int CURRENCIES = 3;
	public static final int CARDTYPES = 4;
	public static final int FREQUENCIES = 5;
	public static final int USER_TYPES = 6;
	public static final int CTRANSACTION_TYPES = 7;
	public static final int MTRANSACTION_TYPES = 8;
	public static final int ATRANSACTION_TYPES = 9;
	public static final int MERCHANT_OWNER_TYPES = 10;
	public static final int MERCHANT_BUSINESS_CATEGORY = 11;
	public static final int MERCHANT_BUSINESS_SUB_CATEGORY = 12;
	public static final int MERCHANT_AVERAGE_TX_AMOUNT = 13;
	public static final int MERCHANT_HIGHTEST_MONTYLY_VOLUMES = 14;
	public static final int MERCHANT_PERCENTAGE_ANUAL_REVENUE = 15;
	public static final int USER_STATUS = 16;
	public static final int HINT_QUESTIONS = 17;
	public static final int REGIONS = 18;
	public static final int TITLES = 19;
	public static final int MONEY_ACCOUNT_STATUS = 20;
	public static final int MONEY_ACCOUNT_DELETE_STATUS = 21;
	public static final int BANK_ACCOUNT_TYPES = 22;
	public static final int FEE_TYPE = 23;
	public static final int FEE_SERVICE_TYPES = 24;
	public static final int FEE_OPERATION_TYPES = 25;
	public static final int FEE_PAYING_ENTITY = 26;
	public static final int MASTER_WALLETS = 27;
	public static final int FEE_TIME_FREQUENCY = 28;
	public static final int TRX_TIME_PERIOD = 29;
	public static final int TRX_STATUS = 30;
	public static final int RECEIVE_MONEY_STATUS = 31;
	public static final int DESTINATION_TYPE = 32;
	public static final int REPORT_TYPE = 33;
	public static final int DISPUTE_TYPE = 34;
	public static final int ACCOUNT_CLOSURE_STATUS = 35;
	public static final int DISPUTED_STATUS = 36;
	public static final int PAYMENT_MESSAGES = 37;
	public static final int QUERY_OR_FEEDBACK = 38;
	public static final int ERROR_CODE = 39;
	public static final int CHANNEL_TYPE = 40;
	public static final int PG_CODE = 41;
	
	/**
	 * @return preparing the master data for specific tables
	 */
	public static List<Object> getData(Integer dataType) {
		
		List<Object> list = new ArrayList<Object>();
		list.addAll(getLanguageData());
		
		switch(dataType){
			//case MENUS: list.addAll(getMenuData()); break;
			case COUNTRIES: list.addAll(getCountriesData()); break;
			case CURRENCIES: list.addAll(getCurrenciesData()); break;
			case CARDTYPES: list.addAll(getCardTypesData()); break;
			case FREQUENCIES: list.addAll(getFrequenciesData()); break;
			case USER_TYPES: list.addAll(getUserTypesData()); break;
			case CTRANSACTION_TYPES: list.addAll(getCTransactionTypesData()); break;
			case MTRANSACTION_TYPES: list.addAll(getMTransactionTypesData()); break;
			case ATRANSACTION_TYPES: list.addAll(getATransactionTypesData()); break;
			case MERCHANT_OWNER_TYPES: list.addAll(getMerchantOwnerTypesData()); break;
			case MERCHANT_BUSINESS_CATEGORY: list.addAll(getMerchantBusinessCategoriesData()); break;
			case MERCHANT_BUSINESS_SUB_CATEGORY: list.addAll(getMerchantBusinessSubCategoriesData()); break;
			case MERCHANT_AVERAGE_TX_AMOUNT: list.addAll(getMerchantAvgTxAmountsData()); break;
			case MERCHANT_HIGHTEST_MONTYLY_VOLUMES: list.addAll(getMerchantHighestMonthlyVolumesData()); break;
			case MERCHANT_PERCENTAGE_ANUAL_REVENUE: list.addAll(getMerchantPercentageAnualRevenuesData()); break;
			case USER_STATUS: list.addAll(getUserStatusesData()); break;
			case HINT_QUESTIONS: list.addAll(getHintQuestionsData()); break;
			case REGIONS: list.addAll(getRegionsData()); break;
			case TITLES: list.addAll(getTitlesData()); break;
			case MONEY_ACCOUNT_STATUS: list.addAll(getMoneyAccountStatusData()); break;
			case MONEY_ACCOUNT_DELETE_STATUS: list.addAll(getMoneyAccountDeleteStatusData()); break;
			case BANK_ACCOUNT_TYPES: list.addAll(getBankAccountTypesData()); break;
			case FEE_TYPE: list.addAll(getFeeTypesData()); break;
			case FEE_SERVICE_TYPES: list.addAll(getFeeServiceTypesData()); break;
			case FEE_OPERATION_TYPES: list.addAll(getFeeOperationTypesData()); break;
			case FEE_PAYING_ENTITY: list.addAll(getFeePayingEntitiesData()); break;
			case MASTER_WALLETS: list.addAll(getMasterWalletsData()); break;
			case FEE_TIME_FREQUENCY: list.addAll(getFeeTimeFrequencyData()); break;
			case TRX_TIME_PERIOD: list.addAll(getTrxTimePeriodData()); break;
			case TRX_STATUS: list.addAll(getTransactionStatusData()); break;
			case RECEIVE_MONEY_STATUS: list.addAll(getReceiveMoneyStatusData()); break;
			case DESTINATION_TYPE: list.addAll(getDestinationTypeData()); break;
			case REPORT_TYPE: list.addAll(getReportTypesData()); break;
			case DISPUTE_TYPE: list.addAll(getDisputeTypeData()); break;
			case ACCOUNT_CLOSURE_STATUS: list.addAll(getAccountClosureStatusData()); break;
			case DISPUTED_STATUS: list.addAll(getDisputeStatusData()); break;
			case PAYMENT_MESSAGES: list.addAll(getPaymentMessagesData()); break;
			case QUERY_OR_FEEDBACK: list.addAll(getQueryOrFeedbackData());break;
			case ERROR_CODE: list.addAll(getErrorCodes());break;
			case CHANNEL_TYPE: list.addAll(getChannelTypeData());break;
			case PG_CODE : list.addAll(getPGCodes());break;
		}
		return list;
	}

	/**
	 * @return preparing the master data for all tables
	 */
	public static List<Object> getData() {
		
		List<Object> list = new ArrayList<Object>();
		list.addAll(getLanguageData());
		//list.addAll(getMenuData());
		list.addAll(getCountriesData());
		list.addAll(getCurrenciesData());
		list.addAll(getCardTypesData());
		list.addAll(getFrequenciesData());
		list.addAll(getUserTypesData());
		list.addAll(getCTransactionTypesData());
		list.addAll(getMTransactionTypesData());
		list.addAll(getATransactionTypesData());
		list.addAll(getMerchantOwnerTypesData());
		list.addAll(getMerchantBusinessCategoriesData());
		list.addAll(getMerchantBusinessSubCategoriesData());
		list.addAll(getMerchantAvgTxAmountsData());
		list.addAll(getMerchantHighestMonthlyVolumesData());
		list.addAll(getMerchantPercentageAnualRevenuesData());
		list.addAll(getUserStatusesData());
		list.addAll(getHintQuestionsData());
		list.addAll(getRegionsData());
		list.addAll(getTitlesData());
		list.addAll(getMoneyAccountStatusData());
		list.addAll(getMoneyAccountDeleteStatusData());
		list.addAll(getBankAccountTypesData());
		list.addAll(getFeeTypesData());
		list.addAll(getFeeOperationTypesData());
		list.addAll(getFeeServiceTypesData());
		list.addAll(getFeePayingEntitiesData());
		list.addAll(getMasterWalletsData());
		list.addAll(getFeeTimeFrequencyData());
		list.addAll(getTrxTimePeriodData());
		list.addAll(getTransactionStatusData());
		list.addAll(getReceiveMoneyStatusData());
		list.addAll(getDestinationTypeData());
		list.addAll(getReportTypesData());
		list.addAll(getDisputeTypeData());
		list.addAll(getAccountClosureStatusData());
		list.addAll(getDisputeStatusData());
		list.addAll(getQueryOrFeedbackData());
		list.addAll(getPaymentMessagesData());
		list.addAll(getErrorCodes());
		list.addAll(getChannelTypeData());
		list.addAll(getPGCodes());
		return list;
	}
	
	/**
	 * @return list of default languages
	 * and languages for specific language 
	 */
	public static List<Object> getLanguageData() {

		List<Object> list = new ArrayList<Object>();

		//Default Languages
		list.add(new Language(1L, "EN", "English"));
		list.add(new Language(2L, "JP", "Japanees"));
		list.add(new Language(3L, "TH", "Thai"));

		//Languages in english
		list.add(new LanguageLocale(1L, 1L, 1L, "EN", "English - en"));
		list.add(new LanguageLocale(2L, 1L, 2L, "JP", "Japanees - en"));
		list.add(new LanguageLocale(3L, 1L, 3L, "TH", "Thai - en"));

		//Languages in japanies
		list.add(new LanguageLocale(4L, 2L, 1L, "JE", "English - jp"));
		list.add(new LanguageLocale(5L, 2L, 2L, "JJ", "Japanees - jp"));
		list.add(new LanguageLocale(6L, 2L, 3L, "JT", "Thai - jp"));

		//Languages in thai
		list.add(new LanguageLocale(7L, 3L, 1L, "TE", "English - th"));
		list.add(new LanguageLocale(8L, 3L, 2L, "TJ", "Japanees - th"));
		list.add(new LanguageLocale(9L, 3L, 3L, "TT", "Thai - th"));

		return list;
	}

	/**
	 * @return list of default menus
	 * and menus in specific language 
	 */
	/*public static List<Object> getMenuData() {

		List<Object> list = new ArrayList<Object>();

		// Default menus
		  // id, parentId, isParent, menuName
		  list.add(new Menu(1L, 0L, true, "Dashboard","Dashboard Page","/tarang/admin",false,0L));
		  list.add(new Menu(2L, 0L, true, "Management","Management Page","/tarang/admin#",false,0L));
		  list.add(new Menu(3L, 0L, true, "Transactions","Transactions Page","/tarang/admin#",false,0L));
		  list.add(new Menu(4L, 0L, true, "Transaction History","Transaction History Page","/tarang/admin#",false,0L));
		  list.add(new Menu(5L, 0L, true, "Profile","Profile Page","/tarang/admin#",false,0L));
		  
		  list.add(new Menu(6L, 2L, false, "Admin User Management","Admin User Management","/tarang/adminusermgmt",false,0L));
		  list.add(new Menu(7L, 2L, false, "Admin Role Management","Admin Role Management","/tarang/adminroles",false,0L));
		  list.add(new Menu(8L, 2L, false, "Merchant Management","Merchant Management","/tarang/merchantmgmt",false,0L));
		  list.add(new Menu(9L, 2L, false, "Customer Management","Merchant Management","/tarang/customermgmt",false,0L));
		  list.add(new Menu(10L, 2L, false, "Transaction History","Transaction History","/tarang/admin/underconstruction",false,0L));
		  list.add(new Menu(11L, 2L, false, "Fee Management","Fee Management","/tarang/admin/underconstruction",false,0L));

		  list.add(new Menu(12L, 3L, false, "Recover Money","Recover Money Page","/tarang/admin/underconstruction",false,0L));
		  list.add(new Menu(13L, 3L, false, "Refund","Refund Page","/tarang/admin/underconstruction",false,0L));

		  list.add(new Menu(14L, 5L, false, "Change Password","Change Password","/tarang/adminlogin/changepassword",false,0L));
		  list.add(new Menu(15L, 5L, false, "Close Account","Close Account Page","/tarang/admin/underconstruction",false,0L));
		  list.add(new Menu(16L, 5L, false, "Threshold","Threshold Page","/tarang/admin/underconstruction",false,0L));
		  list.add(new Menu(17L, 5L, false, "Preferences","Preferences Page","/tarang/admin/underconstruction",false,0L));
		

		// Menus in English
		// id, languageId, menuId, menuName
		list.add(new MenuLocale(1L, 1L, 1L, "Dashboard - en"));
		list.add(new MenuLocale(2L, 1L, 2L, "Management - en"));
		list.add(new MenuLocale(3L, 1L, 3L, "Transactions - en"));
		list.add(new MenuLocale(4L, 1L, 4L, "Transaction History - en"));
		list.add(new MenuLocale(5L, 1L, 5L, "Profile - en"));
		
		list.add(new MenuLocale(6L, 1L, 6L, "Admin User Management - en"));
		list.add(new MenuLocale(7L, 1L, 7L, "Admin Role Management - en"));
		list.add(new MenuLocale(8L, 1L, 8L, "Merchant Management - en"));
		list.add(new MenuLocale(9L, 1L, 9L, "Customer Management - en"));
		list.add(new MenuLocale(10L, 1L, 10L, "Transaction History - en"));
		list.add(new MenuLocale(11L, 1L, 11L, "Fee Management - en"));

		list.add(new MenuLocale(12L, 1L, 12L, "Recover Money - en"));
		list.add(new MenuLocale(13L, 1L, 13L, "Refund - en"));

		list.add(new MenuLocale(14L, 1L, 14L, "Profile - en"));
		list.add(new MenuLocale(15L, 1L, 15L, "Close Account - en"));
		list.add(new MenuLocale(16L, 1L, 16L, "Threshold - en"));
		list.add(new MenuLocale(17L, 1L, 17L, "Preferences - en"));

		// Menus in Japanies
		// id, languageId, menuId, menuName
		list.add(new MenuLocale(18L, 2L, 1L, "Dashboard - jp"));
		list.add(new MenuLocale(19L, 2L, 2L, "Management - jp"));
		list.add(new MenuLocale(20L, 2L, 3L, "Transactions - jp"));
		list.add(new MenuLocale(21L, 2L, 4L, "Transaction History - jp"));
		list.add(new MenuLocale(22L, 2L, 5L, "Profile - jp"));
		
		list.add(new MenuLocale(23L, 2L, 6L, "Admin User Management - jp"));
		list.add(new MenuLocale(24L, 2L, 7L, "Admin Role Management - jp"));
		list.add(new MenuLocale(25L, 2L, 8L, "Merchant Management - jp"));
		list.add(new MenuLocale(26L, 2L, 9L, "Customer Management - jp"));
		list.add(new MenuLocale(27L, 2L, 10L, "Transaction History - jp"));
		list.add(new MenuLocale(28L, 2L, 11L, "Fee Management - jp"));

		list.add(new MenuLocale(29L, 2L, 12L, "Recover Money - jp"));
		list.add(new MenuLocale(30L, 2L, 13L, "Refund - jp"));

		list.add(new MenuLocale(31L, 2L, 14L, "Profile - jp"));
		list.add(new MenuLocale(32L, 2L, 15L, "Close Account - jp"));
		list.add(new MenuLocale(33L, 2L, 16L, "Threshold - jp"));
		list.add(new MenuLocale(34L, 2L, 17L, "Preferences - jp"));

		// Menus in Japanies
		// id, languageId, menuId, menuName
		list.add(new MenuLocale(35L, 3L, 1L, "Dashboard - th"));
		list.add(new MenuLocale(36L, 3L, 2L, "Management - th"));
		list.add(new MenuLocale(37L, 3L, 3L, "Transactions - th"));
		list.add(new MenuLocale(38L, 3L, 4L, "Transaction History - th"));
		list.add(new MenuLocale(39L, 3L, 5L, "Profile - th"));
		
		list.add(new MenuLocale(40L, 3L, 6L, "Admin User Management - th"));
		list.add(new MenuLocale(41L, 3L, 7L, "Admin Role Management - th"));
		list.add(new MenuLocale(42L, 3L, 8L, "Merchant Management - th"));
		list.add(new MenuLocale(43L, 3L, 9L, "Customer Management - th"));
		list.add(new MenuLocale(44L, 3L, 10L, "Transaction History - th"));
		list.add(new MenuLocale(45L, 3L, 11L, "Fee Management - th"));

		list.add(new MenuLocale(46L, 3L, 12L, "Recover Money - th"));
		list.add(new MenuLocale(47L, 3L, 13L, "Refund - th"));

		list.add(new MenuLocale(48L, 3L, 14L, "Profile - th"));
		list.add(new MenuLocale(49L, 3L, 15L, "Close Account - th"));
		list.add(new MenuLocale(50L, 3L, 16L, "Threshold - th"));
		list.add(new MenuLocale(51L, 3L, 17L, "Preferences - th"));

		return list;
	}*/

	/**
	 * @return list of default functions
	 * and functions in specific language 
	 */
	public static List<Object> getCountriesData() {

		List<Object> list = new ArrayList<Object>();
		
		//Default Countries
		// country id, country code, country name, phone code, currency id
		list.add(new Country(CountryIds.USD_COUNTRY_ID, "US", "United States of America", "001", 1L));
		list.add(new Country(CountryIds.JPY_COUNTRY_ID, "JP", "Japan", "081", 2L));
		list.add(new Country(CountryIds.THAI_COUNTRY_ID, "TH", "Thailand", "066", 3L));

		//Countries in english
		list.add(new CountryLocale(1L, 1L, CountryIds.USD_COUNTRY_ID, "USe", "United States of America- en"));
		list.add(new CountryLocale(2L, 1L, CountryIds.JPY_COUNTRY_ID, "JPe", "Japan - en"));
		list.add(new CountryLocale(3L, 1L, CountryIds.THAI_COUNTRY_ID, "THe", "Thailand - en"));

		//Countries in japanies
		list.add(new CountryLocale(4L, 2L, CountryIds.USD_COUNTRY_ID, "USj", "United States of America - jp"));
		list.add(new CountryLocale(5L, 2L, CountryIds.JPY_COUNTRY_ID, "JPj", "Japan - jp"));
		list.add(new CountryLocale(6L, 2L, CountryIds.THAI_COUNTRY_ID, "THj", "Thailand - jp"));

		//Countries in japanies
		list.add(new CountryLocale(7L, 3L, CountryIds.USD_COUNTRY_ID, "USh", "United States of America - th"));
		list.add(new CountryLocale(8L, 3L, CountryIds.JPY_COUNTRY_ID, "JPh", "Japan - th"));
		list.add(new CountryLocale(9L, 3L, CountryIds.THAI_COUNTRY_ID, "THh", "Thailand - th"));

		return list;
	}

	/**
	 * @return list of default currencies
	 * and currencies in specific language 
	 */
	public static List<Object> getCurrenciesData() {

		List<Object> list = new ArrayList<Object>();

		//Default currencies
	    list.add(new Currency(CurrencyIds.USD, "USD", "Dollor"));
		list.add(new Currency(CurrencyIds.JPY, "JPY", "Yen"));
		list.add(new Currency(CurrencyIds.THB, "THB", "Baht"));

		// Currencies in English
		list.add(new CurrencyLocale(1L, 1L, CurrencyIds.USD, "Dollor - en"));
		list.add(new CurrencyLocale(2L, 1L, CurrencyIds.JPY, "Yen - en"));
		list.add(new CurrencyLocale(CurrencyIds.THB, 1L, 3L, "Baht - en"));

		// Currencies in Japanies
		list.add(new CurrencyLocale(4L, 2L, CurrencyIds.USD, "Dollor - jp"));
		list.add(new CurrencyLocale(5L, 2L, CurrencyIds.JPY, "Yen - jp"));
		list.add(new CurrencyLocale(6L, 2L, CurrencyIds.THB, "Baht - jp"));

		// Currencies in Thai
		list.add(new CurrencyLocale(7L, 3L, CurrencyIds.USD, "Dollor - th"));
		list.add(new CurrencyLocale(8L, 3L, CurrencyIds.JPY, "Yen - th"));
		list.add(new CurrencyLocale(9L, 3L, CurrencyIds.THB, "Baht - th"));

		return list;
	}

	/**
	 * @return list of default card types
	 * and card types in specific language 
	 */
	public static List<Object> getCardTypesData() {

		List<Object> list = new ArrayList<Object>();

		//Default card types
		list.add(new CardType(1L, "Visa"));
		list.add(new CardType(2L, "Master"));
		list.add(new CardType(3L, "Maestro"));
		list.add(new CardType(4L, "American Express"));

		//card types in Engilish
		list.add(new CardTypeLocale(1L, 1L, 1L, "Visa - en"));
		list.add(new CardTypeLocale(2L, 1L, 2L, "Master - en"));
		list.add(new CardTypeLocale(3L, 1L, 3L, "Maestro - en"));
		list.add(new CardTypeLocale(4L, 1L, 4L, "American Express - en"));

		//card types in Japan
		list.add(new CardTypeLocale(5L, 2L, 1L, "Visa - jp"));
		list.add(new CardTypeLocale(6L, 2L, 2L, "Master - jp"));
		list.add(new CardTypeLocale(7L, 2L, 3L, "Maestro - jp"));
		list.add(new CardTypeLocale(8L, 2L, 4L, "American Express - jp"));

		//card types in Thai
		list.add(new CardTypeLocale(9L, 3L, 1L, "Visa - th"));
		list.add(new CardTypeLocale(10L, 3L, 2L, "Master - th"));
		list.add(new CardTypeLocale(11L, 3L, 3L, "Maestro - th"));
		list.add(new CardTypeLocale(12L, 3L, 4L, "American Express - th"));

		return list;
	}

	/**
	 * @return list of default frequencies
	 * and frequencies in specific language 
	 */
	public static List<Object> getFrequenciesData() {

		List<Object> list = new ArrayList<Object>();

		//Default frequencies
		list.add(new Frequency(1L, "Daily"));
		list.add(new Frequency(2L, "Weekly"));
		list.add(new Frequency(3L, "By-Weekly"));
		list.add(new Frequency(4L, "Monthly"));

		//frequencies in English
		list.add(new FrequencyLocale(1L, 1L, 1L, "Daily - en"));
		list.add(new FrequencyLocale(2L, 1L, 2L, "Weekly - en"));
		list.add(new FrequencyLocale(3L, 1L, 3L, "By-Weekly - en"));
		list.add(new FrequencyLocale(4L, 1L, 4L, "Monthly - en"));
		
		//frequencies in Japanies
		list.add(new FrequencyLocale(5L, 2L, 1L, "Daily - jp"));
		list.add(new FrequencyLocale(6L, 2L, 2L, "Weekly - jp"));
		list.add(new FrequencyLocale(7L, 2L, 3L, "By-Weekly - jp"));
		list.add(new FrequencyLocale(8L, 2L, 4L, "Monthly - jp"));

		//frequencies in Japanies
		list.add(new FrequencyLocale( 9L, 3L, 1L, "Daily - th"));
		list.add(new FrequencyLocale(10L, 3L, 2L, "Weekly - th"));
		list.add(new FrequencyLocale(11L, 3L, 3L, "By-Weekly - th"));
		list.add(new FrequencyLocale(12L, 3L, 4L, "Monthly - th"));

		return list;
	}

	/**
	 * @return list of default user types
	 * and user types in specific language 
	 */
	public static List<Object> getUserTypesData() {

		List<Object> list = new ArrayList<Object>();

		//Default user types
		list.add(new UserType(1L, "Customer"));
		list.add(new UserType(2L, "Merchant"));
		list.add(new UserType(3L, "Admin"));
		list.add(new UserType(4L, "Customer_NonRegistered"));


		//user types in English
		list.add(new UserTypeLocale(1L, 1L, 1L, "Customer - en"));
		list.add(new UserTypeLocale(2L, 1L, 2L, "Merchant - en"));
		list.add(new UserTypeLocale(3L, 1L, 3L, "Admin - en"));
		list.add(new UserTypeLocale(4L, 1L, 4L, "Customer_NonRegistered - en"));

		//user types in Japanies
		list.add(new UserTypeLocale(5L, 2L, 1L, "Customer - jp"));
		list.add(new UserTypeLocale(6L, 2L, 2L, "Merchant - jp"));
		list.add(new UserTypeLocale(7L, 2L, 3L, "Admin - jp"));
		list.add(new UserTypeLocale(8L, 2L, 4L, "Customer_NonRegistered - jp"));
		
		//user types in Thai
		list.add(new UserTypeLocale(9L, 3L, 1L, "Customer - th"));
		list.add(new UserTypeLocale(10L, 3L, 2L, "Merchant - th"));
		list.add(new UserTypeLocale(11L, 3L, 3L, "Admin - th"));
		list.add(new UserTypeLocale(12L, 3L, 4L, "Customer_NonRegistered - th"));

		return list;
	}

	/**
	 * @return list of default customer transaction types
	 * and customer transaction types in specific language 
	 */
	public static List<Object> getCTransactionTypesData() {

		List<Object> list = new ArrayList<Object>();

		//Default Customer transaction types
		list.add(new CTransactionType(1L, "Refunds"));
		list.add(new CTransactionType(2L, "Payments Received"));
		list.add(new CTransactionType(3L, "Payments Sent"));

		//Customer Transaction types in English
		list.add(new CTransactionTypeLocale(1L, 1L, 1L, "Refunds - en"));
		list.add(new CTransactionTypeLocale(2L, 1L, 2L, "Payments Received - en"));
		list.add(new CTransactionTypeLocale(3L, 1L, 3L, "Payments Sent - en"));
		
		//Customer Transaction types in Japanies
		list.add(new CTransactionTypeLocale(4L, 2L, 1L, "Refunds - jp"));
		list.add(new CTransactionTypeLocale(5L, 2L, 2L, "Payments Received - jp"));
		list.add(new CTransactionTypeLocale(6L, 2L, 3L, "Payments Sent - jp"));

		//Customer Transaction types in Thai
		list.add(new CTransactionTypeLocale(7L, 3L, 1L, "Refunds - th"));
		list.add(new CTransactionTypeLocale(8L, 3L, 2L, "Payments Received - th"));
		list.add(new CTransactionTypeLocale(9L, 3L, 3L, "Payments Sent - th"));

		return list;
	}

	/**
	 * @return list of default Merchant transaction types
	 * and Merchant transaction types in specific language 
	 */
	public static List<Object> getMTransactionTypesData() {

		List<Object> list = new ArrayList<Object>();

		//Default Merchant transaction types
		list.add(new MTransactionType(1L, "Refunds"));
		list.add(new MTransactionType(2L, "Payments Received"));
		list.add(new MTransactionType(3L, "Payments Sent"));

		//Merchant Transaction types in English
		list.add(new MTransactionTypeLocale(1L, 1L, 1L, "Refunds - en"));
		list.add(new MTransactionTypeLocale(2L, 1L, 2L, "Payments Received - en"));
		list.add(new MTransactionTypeLocale(3L, 1L, 3L, "Payments Sent - en"));
		
		//Merchant Transaction types in Japanies
		list.add(new MTransactionTypeLocale(4L, 2L, 1L, "Refunds - jp"));
		list.add(new MTransactionTypeLocale(5L, 2L, 2L, "Payments Received - jp"));
		list.add(new MTransactionTypeLocale(6L, 2L, 3L, "Payments Sent - jp"));

		//Merchant Transaction types in Thai
		list.add(new MTransactionTypeLocale(7L, 3L, 1L, "Refunds - th"));
		list.add(new MTransactionTypeLocale(8L, 3L, 2L, "Payments Received - th"));
		list.add(new MTransactionTypeLocale(9L, 3L, 3L, "Payments Sent - th"));

		return list;
	}

	/**
	 * @return list of default Admin transaction types
	 * and Admin transaction types in specific language 
	 */
	public static List<Object> getATransactionTypesData() {

		List<Object> list = new ArrayList<Object>();

		//Default Admin transaction types
		list.add(new ATransactionType(1L, "Refunds"));
		list.add(new ATransactionType(2L, "Payments Received"));
		list.add(new ATransactionType(3L, "Payments Sent"));

		//Admin Transaction types in English
		list.add(new ATransactionTypeLocale(1L, 1L, 1L, "Refunds - en"));
		list.add(new ATransactionTypeLocale(2L, 1L, 2L, "Payments Received - en"));
		list.add(new ATransactionTypeLocale(3L, 1L, 3L, "Payments Sent - en"));
		
		//Admin Transaction types in Japanies
		list.add(new ATransactionTypeLocale(4L, 2L, 1L, "Refunds - jp"));
		list.add(new ATransactionTypeLocale(5L, 2L, 2L, "Payments Received - jp"));
		list.add(new ATransactionTypeLocale(6L, 2L, 3L, "Payments Sent - jp"));

		//Admin Transaction types in Thai
		list.add(new ATransactionTypeLocale(7L, 3L, 1L, "Refunds - th"));
		list.add(new ATransactionTypeLocale(8L, 3L, 2L, "Payments Received - th"));
		list.add(new ATransactionTypeLocale(9L, 3L, 3L, "Payments Sent - th"));

		return list;
	}

	/**
	 * @return list of default Merchant owner types
	 * and Merchant owner types in specific language 
	 */
	public static List<Object> getMerchantOwnerTypesData() {

		List<Object> list = new ArrayList<Object>();

		//Default Merchant Owner types
		list.add(new MerchantOwnerType(1L, "Sole Trader"));
		list.add(new MerchantOwnerType(2L, "Partnership"));
		list.add(new MerchantOwnerType(3L, "Corporation"));
		list.add(new MerchantOwnerType(4L, "Other Corporate Body"));
		list.add(new MerchantOwnerType(5L, "Government Entity"));
		list.add(new MerchantOwnerType(6L, "Non Profit Organization"));

		//Merchant Owner types in English
		list.add(new MerchantOwnerTypeLocale(1L, 1L, 1L, "Sole Trader - en"));
		list.add(new MerchantOwnerTypeLocale(2L, 1L, 2L, "Partnership - en"));
		list.add(new MerchantOwnerTypeLocale(3L, 1L, 3L, "Corporation - en"));
		list.add(new MerchantOwnerTypeLocale(4L, 1L, 4L, "Other Corporate Body - en"));
		list.add(new MerchantOwnerTypeLocale(5L, 1L, 5L, "Government Entity - en"));
		list.add(new MerchantOwnerTypeLocale(6L, 1L, 6L, "Non Profit Organization - en"));
		
		//Merchant Owner types in Japanies
		list.add(new MerchantOwnerTypeLocale(7L, 2L, 1L, "Sole Trader - jp"));
		list.add(new MerchantOwnerTypeLocale(8L, 2L, 2L, "Partnership - jp"));
		list.add(new MerchantOwnerTypeLocale(9L, 2L, 3L, "Corporation - jp"));
		list.add(new MerchantOwnerTypeLocale(10L, 2L, 4L, "Other Corporate Body - jp"));
		list.add(new MerchantOwnerTypeLocale(11L, 2L, 5L, "Government Entity - jp"));
		list.add(new MerchantOwnerTypeLocale(12L, 2L, 6L, "Non Profit Organization - jp"));

		//Merchant Owner types in Thai
		list.add(new MerchantOwnerTypeLocale(13L, 3L, 1L, "Sole Trader - th"));
		list.add(new MerchantOwnerTypeLocale(14L, 3L, 2L, "Partnership - th"));
		list.add(new MerchantOwnerTypeLocale(15L, 3L, 3L, "Corporation - th"));
		list.add(new MerchantOwnerTypeLocale(16L, 3L, 4L, "Other Corporate Body - th"));
		list.add(new MerchantOwnerTypeLocale(17L, 3L, 5L, "Government Entity - th"));
		list.add(new MerchantOwnerTypeLocale(18L, 3L, 6L, "Non Profit Organization - th"));

		return list;
	}

	/**
	 * @return list of default Merchant business categories 
	 * and Merchant business categories in specific language 
	 */
	public static List<Object> getMerchantBusinessCategoriesData() {

		List<Object> list = new ArrayList<Object>();

		//Default Merchant business categories
		list.add(new MerchantBusinessCategory(1L, "Arts and Craft"));
		list.add(new MerchantBusinessCategory(2L, "Baby"));
		list.add(new MerchantBusinessCategory(3L, "Government"));
		list.add(new MerchantBusinessCategory(4L, "Home and Garden"));
		list.add(new MerchantBusinessCategory(5L, "Travel"));

		//Merchant business categories in English
		list.add(new MerchantBusinessCategoryLocale(1L, 1L, 1L, "Arts and Craft - en"));
		list.add(new MerchantBusinessCategoryLocale(2L, 1L, 2L, "Baby - en"));
		list.add(new MerchantBusinessCategoryLocale(3L, 1L, 3L, "Government - en"));
		list.add(new MerchantBusinessCategoryLocale(4L, 1L, 4L, "Home and Garden - en"));
		list.add(new MerchantBusinessCategoryLocale(5L, 1L, 5L, "Travel - en"));
		
		//Merchant business categories in Japanies
		list.add(new MerchantBusinessCategoryLocale(6L, 2L, 1L, "Arts and Craft - jp"));
		list.add(new MerchantBusinessCategoryLocale(7L, 2L, 2L, "Baby - jp"));
		list.add(new MerchantBusinessCategoryLocale(8L, 2L, 3L, "Government - jp"));
		list.add(new MerchantBusinessCategoryLocale(9L, 2L, 4L, "Home and Garden - jp"));
		list.add(new MerchantBusinessCategoryLocale(10L, 2L, 5L, "Travel - jp"));

		//Merchant business categories in Thai
		list.add(new MerchantBusinessCategoryLocale(11L, 3L, 1L, "Arts and Craft - th"));
		list.add(new MerchantBusinessCategoryLocale(12L, 3L, 2L, "Baby - th"));
		list.add(new MerchantBusinessCategoryLocale(13L, 3L, 3L, "Government - th"));
		list.add(new MerchantBusinessCategoryLocale(14L, 3L, 4L, "Home and Garden - th"));
		list.add(new MerchantBusinessCategoryLocale(15L, 3L, 5L, "Travel - th"));

		return list;

	}

	/**
	 * @return list of default Merchant business sub categories 
	 * and Merchant business sub categories in specific language 
	 */
	public static List<Object> getMerchantBusinessSubCategoriesData() {

		List<Object> list = new ArrayList<Object>();

		//Default Merchant sub business categories
		list.add(new MerchantBusinessSubCategory(1L, 1L, "Arts"));
		list.add(new MerchantBusinessSubCategory(2L, 1L, "Craft"));
		list.add(new MerchantBusinessSubCategory(3L, 2L, "Baby"));
		list.add(new MerchantBusinessSubCategory(4L, 2L, "Born Baby"));
		list.add(new MerchantBusinessSubCategory(5L, 3L, "Central Government"));
		list.add(new MerchantBusinessSubCategory(6L, 3L, "State Government"));
		list.add(new MerchantBusinessSubCategory(7L, 4L, "Home"));
		list.add(new MerchantBusinessSubCategory(8L, 4L, "Garden"));
		list.add(new MerchantBusinessSubCategory(9L, 5L, "National - Travel"));
		list.add(new MerchantBusinessSubCategory(10L, 5L, "Internation - Travel"));

		//Merchant business categories in English
		// args --- id,  languageId,  mbcId,  mbscId,  subCategory
		list.add(new MerchantBusinessSubCategoryLocale(1L, 1L, 1L, 1L, "Arts - en"));
		list.add(new MerchantBusinessSubCategoryLocale(2L, 1L, 1L, 2L, "Craft - en"));
		list.add(new MerchantBusinessSubCategoryLocale(3L, 1L, 2L, 3L, "Baby - en"));
		list.add(new MerchantBusinessSubCategoryLocale(4L, 1L, 2L, 4L, "Born Baby - en"));
		list.add(new MerchantBusinessSubCategoryLocale(5L, 1L, 3L, 5L, "Central Government - en"));
		list.add(new MerchantBusinessSubCategoryLocale(6L, 1L, 3L, 6L, "State Government - en"));
		list.add(new MerchantBusinessSubCategoryLocale(7L, 1L, 4L, 7L, "Home - en"));
		list.add(new MerchantBusinessSubCategoryLocale(8L, 1L, 4L, 8L, "Garden - en"));
		list.add(new MerchantBusinessSubCategoryLocale(9L, 1L, 5L, 9L, "National - Travel - en"));
		list.add(new MerchantBusinessSubCategoryLocale(10L, 1L, 5L, 10L, "Internation - Travel - en"));
		
		//Merchant business categories in Japanies
		list.add(new MerchantBusinessSubCategoryLocale(11L, 2L, 1L, 1L, "Arts - jp"));
		list.add(new MerchantBusinessSubCategoryLocale(12L, 2L, 1L, 2L, "Craft - jp"));
		list.add(new MerchantBusinessSubCategoryLocale(13L, 2L, 2L, 3L, "Baby - jp"));
		list.add(new MerchantBusinessSubCategoryLocale(14L, 2L, 2L, 4L, "Born Baby - jp"));
		list.add(new MerchantBusinessSubCategoryLocale(15L, 2L, 3L, 5L, "Central Government - jp"));
		list.add(new MerchantBusinessSubCategoryLocale(16L, 2L, 3L, 6L, "State Government - jp"));
		list.add(new MerchantBusinessSubCategoryLocale(17L, 2L, 4L, 7L, "Home - jp"));
		list.add(new MerchantBusinessSubCategoryLocale(18L, 2L, 4L, 8L, "Garden - jp"));
		list.add(new MerchantBusinessSubCategoryLocale(19L, 2L, 5L, 9L, "National - Travel - jp"));
		list.add(new MerchantBusinessSubCategoryLocale(20L, 2L, 5L, 10L, "Internation - Travel - jp"));

		//Merchant business categories in Thai
		list.add(new MerchantBusinessSubCategoryLocale(21L, 3L, 1L, 1L, "Arts - th"));
		list.add(new MerchantBusinessSubCategoryLocale(22L, 3L, 1L, 2L, "Craft - th"));
		list.add(new MerchantBusinessSubCategoryLocale(23L, 3L, 2L, 3L, "Baby - th"));
		list.add(new MerchantBusinessSubCategoryLocale(24L, 3L, 2L, 4L, "Born Baby - th"));
		list.add(new MerchantBusinessSubCategoryLocale(25L, 3L, 3L, 5L, "Central Government - th"));
		list.add(new MerchantBusinessSubCategoryLocale(26L, 3L, 3L, 6L, "State Government - th"));
		list.add(new MerchantBusinessSubCategoryLocale(27L, 3L, 4L, 7L, "Home - th"));
		list.add(new MerchantBusinessSubCategoryLocale(28L, 3L, 4L, 8L, "Garden - th"));
		list.add(new MerchantBusinessSubCategoryLocale(29L, 3L, 5L, 9L, "National - Travel - th"));
		list.add(new MerchantBusinessSubCategoryLocale(30L, 3L, 5L, 10L, "Internation - Travel - th"));

		return list;

	}

	/**
	 * @return list of default Merchant average transaction amount
	 * and Merchant average transaction amount in specific language 
	 */
	public static List<Object> getMerchantAvgTxAmountsData() {

		List<Object> list = new ArrayList<Object>();

		//Default Merchant Average transaction amount
		list.add(new MerchantAvgTxAmount(1L, "1 - 10 dollers"));
		list.add(new MerchantAvgTxAmount(2L, "11 - 20 dollers"));
		list.add(new MerchantAvgTxAmount(3L, "21 - 50 dollers"));
		list.add(new MerchantAvgTxAmount(4L, "51 - 100 dollers"));
		list.add(new MerchantAvgTxAmount(5L, "above 100 dollers"));

		//Merchant Average transaction amount in English
		list.add(new MerchantAvgTxAmountLocale(1L, 1L, 1L, "1 - 10 dollers - en"));
		list.add(new MerchantAvgTxAmountLocale(2L, 1L, 2L, "11 - 20 dollers - en"));
		list.add(new MerchantAvgTxAmountLocale(3L, 1L, 3L, "21 - 50 dollers - en"));
		list.add(new MerchantAvgTxAmountLocale(4L, 1L, 4L, "51 - 100 dollers - en"));
		list.add(new MerchantAvgTxAmountLocale(5L, 1L, 5L, "above 100 dollers - en"));
		
		//Merchant Average transaction amount in Japanies
		list.add(new MerchantAvgTxAmountLocale(6L, 2L, 1L, "1 - 10 dollers - jp"));
		list.add(new MerchantAvgTxAmountLocale(7L, 2L, 2L, "11 - 20 dollers - jp"));
		list.add(new MerchantAvgTxAmountLocale(8L, 2L, 3L, "21 - 50 dollers - jp"));
		list.add(new MerchantAvgTxAmountLocale(9L, 2L, 4L, "51 - 100 dollers - jp"));
		list.add(new MerchantAvgTxAmountLocale(10L, 2L, 5L, "above 100 dollers - jp"));

		//Merchant Average transaction amount in Thai
		list.add(new MerchantAvgTxAmountLocale(11L, 3L, 1L, "1 - 10 dollers - th"));
		list.add(new MerchantAvgTxAmountLocale(12L, 3L, 2L, "11 - 20 dollers - th"));
		list.add(new MerchantAvgTxAmountLocale(13L, 3L, 3L, "21 - 50 dollers - th"));
		list.add(new MerchantAvgTxAmountLocale(14L, 3L, 4L, "51 - 100 dollers - th"));
		list.add(new MerchantAvgTxAmountLocale(15L, 3L, 5L, "above 100 dollers - th"));

		return list;
	}

	/**
	 * @return list of default Merchant highest monthly volume 
	 * and Merchant highest volume in specific language 
	 */
	public static List<Object> getMerchantHighestMonthlyVolumesData() {

		List<Object> list = new ArrayList<Object>();

		//Default Merchant Highest monthly volume
		list.add(new MerchantHighestMonthlyVolume(1L, "100 - 1000 dollers"));
		list.add(new MerchantHighestMonthlyVolume(2L, "1001 - 2000 dollers"));
		list.add(new MerchantHighestMonthlyVolume(3L, "2001 - 5000 dollers"));
		list.add(new MerchantHighestMonthlyVolume(4L, "5001 - 10000 dollers"));
		list.add(new MerchantHighestMonthlyVolume(5L, "above 10000 dollers"));

		//Default Merchant Highest monthly volume in English
		list.add(new MerchantHighestMonthlyVolumeLocale(1L, 1L, 1L, "100 - 1000 dollers - en"));
		list.add(new MerchantHighestMonthlyVolumeLocale(2L, 1L, 2L, "1001 - 2000 dollers - en"));
		list.add(new MerchantHighestMonthlyVolumeLocale(3L, 1L, 3L, "2001 - 5000 dollers - en"));
		list.add(new MerchantHighestMonthlyVolumeLocale(4L, 1L, 4L, "5001 - 10000 dollers - en"));
		list.add(new MerchantHighestMonthlyVolumeLocale(5L, 1L, 5L, "above 10000 dollers - en"));

		//Default Merchant Highest monthly volume in Japanies
		list.add(new MerchantHighestMonthlyVolumeLocale(6L, 2L, 1L, "100 - 1000 dollers - jp"));
		list.add(new MerchantHighestMonthlyVolumeLocale(7L, 2L, 2L, "1001 - 2000 dollers - jp"));
		list.add(new MerchantHighestMonthlyVolumeLocale(8L, 2L, 3L, "2001 - 5000 dollers - jp"));
		list.add(new MerchantHighestMonthlyVolumeLocale(9L, 2L, 4L, "5001 - 10000 dollers - jp"));
		list.add(new MerchantHighestMonthlyVolumeLocale(10L, 2L, 5L, "above 10000 dollers - jp"));

		//Default Merchant Highest monthly volume in Thai
		list.add(new MerchantHighestMonthlyVolumeLocale(11L, 3L, 1L, "100 - 1000 dollers - th"));
		list.add(new MerchantHighestMonthlyVolumeLocale(12L, 3L, 2L, "1001 - 2000 dollers - th"));
		list.add(new MerchantHighestMonthlyVolumeLocale(13L, 3L, 3L, "2001 - 5000 dollers - th"));
		list.add(new MerchantHighestMonthlyVolumeLocale(14L, 3L, 4L, "5001 - 10000 dollers - th"));
		list.add(new MerchantHighestMonthlyVolumeLocale(15L, 3L, 5L, "above 10000 dollers - th"));

		return list;
	}

	/**
	 * @return list of default Merchant percentage anual revenue
	 * and Merchant percentage anual in specific language 
	 */
	public static List<Object> getMerchantPercentageAnualRevenuesData() {

		List<Object> list = new ArrayList<Object>();

		//Default Merchant Percentage Anual Revenue volume
		list.add(new MerchantPercentageAnualRevenue(1L, "00 - 05 Percent"));
		list.add(new MerchantPercentageAnualRevenue(2L, "06 - 10 Percent"));
		list.add(new MerchantPercentageAnualRevenue(3L, "10 - 20 Percent"));
		list.add(new MerchantPercentageAnualRevenue(4L, "20 - 50 Percent"));
		list.add(new MerchantPercentageAnualRevenue(5L, "50 plus Percent"));

		//Default Merchant Percentage Anual Revenue volume in English
		list.add(new MerchantPercentageAnualRevenueLocale(1L, 1L, 1L, "00 - 05 Percent - en"));
		list.add(new MerchantPercentageAnualRevenueLocale(2L, 1L, 2L, "06 - 10 Percent - en"));
		list.add(new MerchantPercentageAnualRevenueLocale(3L, 1L, 3L, "10 - 20 Percent - en"));
		list.add(new MerchantPercentageAnualRevenueLocale(4L, 1L, 4L, "20 - 50 Percent - en"));
		list.add(new MerchantPercentageAnualRevenueLocale(5L, 1L, 5L, "50 plus Percent - en"));

		//Default Merchant Percentage Anual Revenue volume in Japnies
		list.add(new MerchantPercentageAnualRevenueLocale(6L, 2L, 1L, "00 - 05 Percent - jp"));
		list.add(new MerchantPercentageAnualRevenueLocale(7L, 2L, 2L, "06 - 10 Percent - jp"));
		list.add(new MerchantPercentageAnualRevenueLocale(8L, 2L, 3L, "10 - 20 Percent - jp"));
		list.add(new MerchantPercentageAnualRevenueLocale(9L, 2L, 4L, "20 - 50 Percent - jp"));
		list.add(new MerchantPercentageAnualRevenueLocale(10L, 2L, 5L, "50 plus Percent - jp"));

		//Default Merchant Percentage Anual Revenue volume in Thai
		list.add(new MerchantPercentageAnualRevenueLocale(11L, 3L, 1L, "00 - 05 Percent - th"));
		list.add(new MerchantPercentageAnualRevenueLocale(12L, 3L, 2L, "06 - 10 Percent - th"));
		list.add(new MerchantPercentageAnualRevenueLocale(13L, 3L, 3L, "10 - 20 Percent - th"));
		list.add(new MerchantPercentageAnualRevenueLocale(14L, 3L, 4L, "20 - 50 Percent - th"));
		list.add(new MerchantPercentageAnualRevenueLocale(15L, 3L, 5L, "50 plus Percent - th"));

		return list;
	}

	/**
	 * @return list of default User status
	 * and User types in specific language 
	 */
	public static List<Object> getUserStatusesData() {

		List<Object> list = new ArrayList<Object>();

		//Default User statuses
		list.add(new UserStatus(1L, "Approved"));
		list.add(new UserStatus(2L, "Rejected"));
		list.add(new UserStatus(3L, "Locked"));
		list.add(new UserStatus(4L, "Pending"));
		list.add(new UserStatus(5L, "Deleted"));

		// User statuses in English
		list.add(new UserStatusLocale(1L, 1L, 1L, "Approved - en"));
		list.add(new UserStatusLocale(2L, 1L, 2L, "Rejected - en"));
		list.add(new UserStatusLocale(3L, 1L, 3L, "Locked - en"));
		list.add(new UserStatusLocale(4L, 1L, 4L, "Pending - en"));
		list.add(new UserStatusLocale(5L, 1L, 5L, "Deleted - en"));

		// User statuses in Japanies
		list.add(new UserStatusLocale(6L, 2L, 1L, "Approved - jp"));
		list.add(new UserStatusLocale(7L, 2L, 2L, "Rejected - jp"));
		list.add(new UserStatusLocale(8L, 2L, 3L, "Locked - jp"));
		list.add(new UserStatusLocale(9L, 2L, 4L, "Pending - jp"));
		list.add(new UserStatusLocale(10L, 2L, 5L, "Deleted - jp"));

		// User statuses in Thai
		list.add(new UserStatusLocale(11L, 3L, 1L, "Approved - th"));
		list.add(new UserStatusLocale(12L, 3L, 2L, "Rejected - th"));
		list.add(new UserStatusLocale(13L, 3L, 3L, "Locked - th"));
		list.add(new UserStatusLocale(14L, 3L, 4L, "Pending - th"));
		list.add(new UserStatusLocale(15L, 3L, 5L, "Deleted - th"));

		return list;
	}
	
	/**
	 * @return list of default HintQuestions
	 * and Hint Questions in specific language 
	 */
	public static List<Object> getHintQuestionsData() {

		List<Object> list = new ArrayList<Object>();

		//Default Hint Questions
		list.add(new HintQuestion(1L, "What was the name of your first pet?"));
		list.add(new HintQuestion(2L, "What was the name of your first school?"));
		list.add(new HintQuestion(3L, "What is the name of your best friend?"));

		// Hint Questions in English
		list.add(new HintQuestionLocale(1L, 1L, 1L, "What was the name of your first pet? - en"));
		list.add(new HintQuestionLocale(2L, 1L, 2L, "What was the name of your first school? - en"));
		list.add(new HintQuestionLocale(3L, 1L, 3L, "What is the name of your best friend? - en"));

		// Hint Questions in Japanies
		list.add(new HintQuestionLocale(4L, 2L, 1L, "What was the name of your first pet? - jp"));
		list.add(new HintQuestionLocale(5L, 2L, 2L, "What was the name of your first school? - jp"));
		list.add(new HintQuestionLocale(6L, 2L, 3L, "What is the name of your best friend? - jp"));

		// Hint Questions in Thai
		list.add(new HintQuestionLocale(7L, 3L, 1L, "What was the name of your first pet? - th"));
		list.add(new HintQuestionLocale(8L, 3L, 2L, "What was the name of your first school? - th"));
		list.add(new HintQuestionLocale(9L, 3L, 3L, "What is the name of your best friend? - th"));

		return list;
	}

	/**
	 * @return list of default regions 
	 * and regions in specific language for specific country
	 */
	public static List<Object> getRegionsData() {

		List<Object> list = new ArrayList<Object>();
		
		//Default Regions
		// region id, country id, region
		list.add(new Region(1L, 1L, "Alabama"));
		list.add(new Region(2L, 1L, "Alaska"));
		list.add(new Region(3L, 1L, "Arizona"));
		list.add(new Region(4L, 1L, "Arkansas"));
		list.add(new Region(5L, 1L, "California"));
		list.add(new Region(6L, 1L, "Colorado"));
		list.add(new Region(7L, 1L, "Connecticut"));
		list.add(new Region(8L, 1L, "Delaware"));
		list.add(new Region(9L, 1L, "Florida"));
		list.add(new Region(10L, 1L, "Georgia"));
		list.add(new Region(11L, 1L, "Hawaii"));
		list.add(new Region(12L, 1L, "Idaho"));
		list.add(new Region(13L, 1L, "Illinois"));
		list.add(new Region(14L, 1L, "Indiana"));
		list.add(new Region(15L, 1L, "Iowa"));
		list.add(new Region(16L, 1L, "Kansas"));
		list.add(new Region(17L, 1L, "Kentucky"));
		list.add(new Region(18L, 1L, "Louisiana"));
		list.add(new Region(19L, 1L, "Maine"));
		list.add(new Region(20L, 1L, "Maryland"));
		list.add(new Region(21L, 1L, "Massachusetts"));
		list.add(new Region(22L, 1L, "Michigan"));
		list.add(new Region(23L, 1L, "Minnesota"));
		list.add(new Region(24L, 1L, "Mississippi"));
		list.add(new Region(25L, 1L, "Missouri"));
		list.add(new Region(26L, 1L, "Montana"));
		list.add(new Region(27L, 1L, "Nebraska"));
		list.add(new Region(28L, 1L, "Nevada"));
		list.add(new Region(29L, 1L, "New Hampshire"));
		list.add(new Region(30L, 1L, "New Jersey"));
		list.add(new Region(31L, 1L, "New Mexico"));
		list.add(new Region(32L, 1L, "New York"));
		list.add(new Region(33L, 1L, "North Carolina"));
		list.add(new Region(34L, 1L, "North Dakota"));
		list.add(new Region(35L, 1L, "Ohio"));
		list.add(new Region(36L, 1L, "Oklahoma"));
		list.add(new Region(37L, 1L, "Oregon"));
		list.add(new Region(38L, 1L, "Pennsylvania"));
		list.add(new Region(39L, 1L, "Rhode Island"));
		list.add(new Region(40L, 1L, "South Carolina"));
		list.add(new Region(41L, 1L, "South Dakota"));
		list.add(new Region(42L, 1L, "Tennessee"));
		list.add(new Region(43L, 1L, "Texas"));
		list.add(new Region(44L, 1L, "Utah"));
		list.add(new Region(45L, 1L, "Vermont"));
		list.add(new Region(46L, 1L, "Virginia"));
		list.add(new Region(47L, 1L, "Washington"));
		list.add(new Region(48L, 1L, "West Virginia"));
		list.add(new Region(49L, 1L, "Wisconsin"));
		list.add(new Region(50L, 1L, "Wyoming"));
		
		
		
		list.add(new Region(51L, 2L, "Aichi"));
		list.add(new Region(52L, 2L, "Akita"));
		list.add(new Region(53L, 2L, "Aomori"));
		list.add(new Region(54L, 2L, "Chiba"));
		list.add(new Region(55L, 2L, "Ehime"));
		list.add(new Region(56L, 2L, "Fukui"));
		list.add(new Region(57L, 2L, "Fukuoka"));
		list.add(new Region(58L, 2L, "Fukushima"));
		list.add(new Region(59L, 2L, "Gifu"));
		list.add(new Region(60L, 2L, "Gumma"));
		list.add(new Region(61L, 2L, "Hiroshima"));
		list.add(new Region(62L, 2L, "Hokkaido"));
		list.add(new Region(63L, 2L, "Hyogo"));
		list.add(new Region(64L, 2L, "Ibaragi"));
		list.add(new Region(65L, 2L, "Ishikawa"));
		list.add(new Region(66L, 2L, "Iwate"));
		list.add(new Region(67L, 2L, "Kagawa"));
		list.add(new Region(68L, 2L, "Kagoshima"));
		list.add(new Region(69L, 2L, "Kanagawa"));
		list.add(new Region(70L, 2L, "Kochi"));
		list.add(new Region(71L, 2L, "Kumamoto"));
		list.add(new Region(72L, 2L, "Kyoto"));
		list.add(new Region(73L, 2L, "Mie"));
		list.add(new Region(74L, 2L, "Miyagi"));
		list.add(new Region(75L, 2L, "Miyazaki"));
		list.add(new Region(76L, 2L, "Nagano"));
		list.add(new Region(77L, 2L, "Nagasaki"));
		list.add(new Region(78L, 2L, "Nara"));
		list.add(new Region(79L, 2L, "Niigata"));
		list.add(new Region(80L, 2L, "Oita"));
		list.add(new Region(81L, 2L, "Okayama"));
		list.add(new Region(82L, 2L, "Okinawa"));
		list.add(new Region(83L, 2L, "Osaka"));
		list.add(new Region(84L, 2L, "Saga"));
		list.add(new Region(85L, 2L, "Saitama"));
		list.add(new Region(86L, 2L, "Shiga"));
		list.add(new Region(87L, 2L, "Shimane"));
		list.add(new Region(88L, 2L, "Shizuoka"));
		list.add(new Region(89L, 2L, "Tochigi"));
		list.add(new Region(90L, 2L, "Tokushima"));
		list.add(new Region(91L, 2L, "Tokyo-to"));
		list.add(new Region(92L, 2L, "Tottori"));
		list.add(new Region(93L, 2L, "Toyama"));
		list.add(new Region(94L, 2L, "Wakayama"));
		list.add(new Region(95L, 2L, "Yamagata"));
		list.add(new Region(96L, 2L, "Yamaguchi"));
		list.add(new Region(97L, 2L, "Yamanashi"));

		
		list.add(new Region(98L, 3L, "Bangkok"));
		list.add(new Region(99L, 3L, "Chiang Mai"));
		list.add(new Region(100L, 3L, "Khon Kaen"));
		list.add(new Region(101L, 3L, "Nakhon Pathom"));
		list.add(new Region(102L, 3L, "Nakhon Ratchasima"));
		list.add(new Region(103L, 3L, "Nakhon Sawan"));
		list.add(new Region(104L, 3L, "Nonthaburi"));
		list.add(new Region(105L, 3L, "Songkhla"));
		list.add(new Region(106L, 3L, "Ubon Ratchathani"));
		list.add(new Region(107L, 3L, "Udon Thani"));
		 
		//Regions in english
		list.add(new RegionLocale(1L, 1L, 1L, 1L, "Alabama - en"));
		list.add(new RegionLocale(2L, 1L, 1L, 2L, "Alaska - en"));
		list.add(new RegionLocale(3L, 1L, 1L, 3L, "Arizona - en"));
		list.add(new RegionLocale(4L, 1L, 1L, 4L, "Arkansas - en"));
		list.add(new RegionLocale(5L, 1L, 1L, 5L, "California - en"));
		list.add(new RegionLocale(6L, 1L, 1L, 6L, "Colorado - en"));
		list.add(new RegionLocale(7L, 1L, 1L, 7L, "Connecticut - en"));
		list.add(new RegionLocale(8L, 1L, 1L, 8L, "Delaware - en"));
		list.add(new RegionLocale(9L, 1L, 1L, 9L, "Florida - en"));
		list.add(new RegionLocale(10L, 1L, 1L, 10L, "Georgia - en"));
		list.add(new RegionLocale(11L, 1L, 1L, 11L, "Hawaii - en"));
		list.add(new RegionLocale(12L, 1L, 1L, 12L, "Idaho - en"));
		list.add(new RegionLocale(13L, 1L, 1L, 13L, "Illinois - en"));
		list.add(new RegionLocale(14L, 1L, 1L, 14L, "Indiana - en"));
		list.add(new RegionLocale(15L, 1L, 1L, 15L, "Iowa - en"));
		list.add(new RegionLocale(16L, 1L, 1L, 16L, "Kansas - en"));
		list.add(new RegionLocale(17L, 1L, 1L, 17L, "Kentucky - en"));
		list.add(new RegionLocale(18L, 1L, 1L, 18L, "Louisiana - en"));
		list.add(new RegionLocale(19L, 1L, 1L, 19L, "Maine - en"));
		list.add(new RegionLocale(20L, 1L, 1L, 20L, "Maryland - en"));
		list.add(new RegionLocale(21L, 1L, 1L, 21L, "Massachusetts - en"));
		list.add(new RegionLocale(22L, 1L, 1L, 22L, "Michigan - en"));
		list.add(new RegionLocale(23L, 1L, 1L, 23L, "Minnesota - en"));
		list.add(new RegionLocale(24L, 1L, 1L, 24L, "Mississippi - en"));
		list.add(new RegionLocale(25L, 1L, 1L, 25L, "Missouri - en"));
		list.add(new RegionLocale(26L, 1L, 1L, 26L, "Montana - en"));
		list.add(new RegionLocale(27L, 1L, 1L, 27L, "Nebraska - en"));
		list.add(new RegionLocale(28L, 1L, 1L, 28L, "Nevada - en"));
		list.add(new RegionLocale(29L, 1L, 1L, 29L, "New Hampshire - en"));
		list.add(new RegionLocale(30L, 1L, 1L, 30L, "New Jersey - en"));
		list.add(new RegionLocale(31L, 1L, 1L, 31L, "New Mexico - en"));
		list.add(new RegionLocale(32L, 1L, 1L, 32L, "New York - en"));
		list.add(new RegionLocale(33L, 1L, 1L, 33L, "North Carolina - en"));
		list.add(new RegionLocale(34L, 1L, 1L, 34L, "North Dakota - en"));
		list.add(new RegionLocale(35L, 1L, 1L, 35L, "Ohio - en"));
		list.add(new RegionLocale(36L, 1L, 1L, 36L, "Oklahoma - en"));
		list.add(new RegionLocale(37L, 1L, 1L, 37L, "Oregon - en"));
		list.add(new RegionLocale(38L, 1L, 1L, 38L, "Pennsylvania - en"));
		list.add(new RegionLocale(39L, 1L, 1L, 39L, "Rhode Island - en"));
		list.add(new RegionLocale(40L, 1L, 1L, 40L, "South Carolina - en"));
		list.add(new RegionLocale(41L, 1L, 1L, 41L, "South Dakota - en"));
		list.add(new RegionLocale(42L, 1L, 1L, 42L, "Tennessee - en"));
		list.add(new RegionLocale(43L, 1L, 1L, 43L, "Texas - en"));
		list.add(new RegionLocale(44L, 1L, 1L, 44L, "Utah - en"));
		list.add(new RegionLocale(45L, 1L, 1L, 45L, "Vermont - en"));
		list.add(new RegionLocale(46L, 1L, 1L, 46L, "Virginia - en"));
		list.add(new RegionLocale(47L, 1L, 1L, 47L, "Washington - en"));
		list.add(new RegionLocale(48L, 1L, 1L, 48L, "West Virginia - en"));
		list.add(new RegionLocale(49L, 1L, 1L, 49L, "Wisconsin - en"));
		list.add(new RegionLocale(50L, 1L, 1L, 50L, "Wyoming - en"));
		
		list.add(new RegionLocale(51L, 1L, 2L, 51L, "Aichi - en"));
		list.add(new RegionLocale(52L, 1L, 2L, 52L, "Akita - en"));
		list.add(new RegionLocale(53L, 1L, 2L, 53L, "Aomori - en"));
		list.add(new RegionLocale(54L, 1L, 2L, 54L, "Chiba - en"));
		list.add(new RegionLocale(55L, 1L, 2L, 55L, "Ehime - en"));
		list.add(new RegionLocale(56L, 1L, 2L, 56L, "Fukui - en"));
		list.add(new RegionLocale(57L, 1L, 2L, 57L, "Fukuoka - en"));
		list.add(new RegionLocale(58L, 1L, 2L, 58L, "Fukushima - en"));
		list.add(new RegionLocale(59L, 1L, 2L, 59L, "Gifu - en"));
		list.add(new RegionLocale(60L, 1L, 2L, 60L, "Gumma - en"));
		list.add(new RegionLocale(61L, 1L, 2L, 61L, "Hiroshima - en"));
		list.add(new RegionLocale(62L, 1L, 2L, 62L, "Hokkaido - en"));
		list.add(new RegionLocale(63L, 1L, 2L, 63L, "Hyogo - en"));
		list.add(new RegionLocale(64L, 1L, 2L, 64L, "Ibaragi - en"));
		list.add(new RegionLocale(65L, 1L, 2L, 65L, "Ishikawa - en"));
		list.add(new RegionLocale(66L, 1L, 2L, 66L, "Iwate - en"));
		list.add(new RegionLocale(67L, 1L, 2L, 67L, "Kagawa - en"));
		list.add(new RegionLocale(68L, 1L, 2L, 68L, "Kagoshima - en"));
		list.add(new RegionLocale(69L, 1L, 2L, 69L, "Kanagawa - en"));
		list.add(new RegionLocale(70L, 1L, 2L, 70L, "Kochi - en"));
		list.add(new RegionLocale(71L, 1L, 2L, 71L, "Kumamoto - en"));
		list.add(new RegionLocale(72L, 1L, 2L, 72L, "Kyoto - en"));
		list.add(new RegionLocale(73L, 1L, 2L, 73L, "Mie - en"));
		list.add(new RegionLocale(74L, 1L, 2L, 74L, "Miyagi - en"));
		list.add(new RegionLocale(75L, 1L, 2L, 75L, "Miyazaki - en"));
		list.add(new RegionLocale(76L, 1L, 2L, 76L, "Nagano - en"));
		list.add(new RegionLocale(77L, 1L, 2L, 77L, "Nagasaki - en"));
		list.add(new RegionLocale(78L, 1L, 2L, 78L, "Nara - en"));
		list.add(new RegionLocale(79L, 1L, 2L, 79L, "Niigata - en"));
		list.add(new RegionLocale(80L, 1L, 2L, 80L, "Oita - en"));
		list.add(new RegionLocale(81L, 1L, 2L, 81L, "Okayama - en"));
		list.add(new RegionLocale(82L, 1L, 2L, 82L, "Okinawa - en"));
		list.add(new RegionLocale(83L, 1L, 2L, 83L, "Osaka - en"));
		list.add(new RegionLocale(84L, 1L, 2L, 84L, "Saga - en"));
		list.add(new RegionLocale(85L, 1L, 2L, 85L, "Saitama - en"));
		list.add(new RegionLocale(86L, 1L, 2L, 86L, "Shiga - en"));
		list.add(new RegionLocale(87L, 1L, 2L, 87L, "Shimane - en"));
		list.add(new RegionLocale(88L, 1L, 2L, 88L, "Shizuoka - en"));
		list.add(new RegionLocale(89L, 1L, 2L, 89L, "Tochigi - en"));
		list.add(new RegionLocale(90L, 1L, 2L, 90L, "Tokushima - en"));
		list.add(new RegionLocale(91L, 1L, 2L, 91L, "Tokyo-to - en"));
		list.add(new RegionLocale(92L, 1L, 2L, 92L, "Tottori - en"));
		list.add(new RegionLocale(93L, 1L, 2L, 93L, "Toyama - en"));
		list.add(new RegionLocale(94L, 1L, 2L, 94L, "Wakayama - en"));
		list.add(new RegionLocale(95L, 1L, 2L, 95L, "Yamagata - en"));
		list.add(new RegionLocale(96L, 1L, 2L, 96L, "Yamaguchi - en"));
		list.add(new RegionLocale(97L, 1L, 2L, 97L, "Yamanashi - en"));
		
		list.add(new RegionLocale(98L, 1L, 3L, 98L, "Bangkok - en"));
		list.add(new RegionLocale(99L, 1L, 3L, 99L, "Chiang Mai - en"));
		list.add(new RegionLocale(100L, 1L, 3L, 100L, "Khon Kaen - en"));
		list.add(new RegionLocale(101L, 1L, 3L, 101L, "Nakhon Pathom - en"));
		list.add(new RegionLocale(102L, 1L, 3L, 102L, "Nakhon Ratchasima - en"));
		list.add(new RegionLocale(103L, 1L, 3L, 103L, "Nakhon Sawan - en"));
		list.add(new RegionLocale(104L, 1L, 3L, 104L, "Nonthaburi - en"));
		list.add(new RegionLocale(105L, 1L, 3L, 105L, "Songkhla - en"));
		list.add(new RegionLocale(106L, 1L, 3L, 106L, "Ubon Ratchathani - en"));
		list.add(new RegionLocale(107L, 1L, 3L, 107L, "Udon Thani - en"));

		//Regions in japanies
		list.add(new RegionLocale(108L, 2L, 1L, 1L, "Alabama - jp"));
		list.add(new RegionLocale(109L, 2L, 1L, 2L, "Alaska - jp"));
		list.add(new RegionLocale(110L, 2L, 1L, 3L, "Arizona - jp"));
		list.add(new RegionLocale(111L, 2L, 1L, 4L, "Arkansas - jp"));
		list.add(new RegionLocale(112L, 2L, 1L, 5L, "California - jp"));
		list.add(new RegionLocale(113L, 2L, 1L, 6L, "Colorado - jp"));
		list.add(new RegionLocale(114L, 2L, 1L, 7L, "Connecticut - jp"));
		list.add(new RegionLocale(115L, 2L, 1L, 8L, "Delaware - jp"));
		list.add(new RegionLocale(116L, 2L, 1L, 9L, "Florida - jp"));
		list.add(new RegionLocale(117L, 2L, 1L, 10L, "Georgia - jp"));
		list.add(new RegionLocale(118L, 2L, 1L, 11L, "Hawaii - jp"));
		list.add(new RegionLocale(119L, 2L, 1L, 12L, "Idaho - jp"));
		list.add(new RegionLocale(120L, 2L, 1L, 13L, "Illinois - jp"));
		list.add(new RegionLocale(121L, 2L, 1L, 14L, "Indiana - jp"));
		list.add(new RegionLocale(122L, 2L, 1L, 15L, "Iowa - jp"));
		list.add(new RegionLocale(123L, 2L, 1L, 16L, "Kansas - jp"));
		list.add(new RegionLocale(124L, 2L, 1L, 17L, "Kentucky - jp"));
		list.add(new RegionLocale(125L, 2L, 1L, 18L, "Louisiana - jp"));
		list.add(new RegionLocale(126L, 2L, 1L, 19L, "Maine - jp"));
		list.add(new RegionLocale(127L, 2L, 1L, 20L, "Maryland - jp"));
		list.add(new RegionLocale(128L, 2L, 1L, 21L, "Massachusetts - jp"));
		list.add(new RegionLocale(129L, 2L, 1L, 22L, "Michigan - jp"));
		list.add(new RegionLocale(130L, 2L, 1L, 23L, "Minnesota - jp"));
		list.add(new RegionLocale(131L, 2L, 1L, 24L, "Mississippi - jp"));
		list.add(new RegionLocale(132L, 2L, 1L, 25L, "Missouri - jp"));
		list.add(new RegionLocale(133L, 2L, 1L, 26L, "Montana - jp"));
		list.add(new RegionLocale(134L, 2L, 1L, 27L, "Nebraska - jp"));
		list.add(new RegionLocale(135L, 2L, 1L, 28L, "Nevada - jp"));
		list.add(new RegionLocale(136L, 2L, 1L, 29L, "New Hampshire - jp"));
		list.add(new RegionLocale(137L, 2L, 1L, 30L, "New Jersey - jp"));
		list.add(new RegionLocale(138L, 2L, 1L, 31L, "New Mexico - jp"));
		list.add(new RegionLocale(139L, 2L, 1L, 32L, "New York - jp"));
		list.add(new RegionLocale(140L, 2L, 1L, 33L, "North Carolina - jp"));
		list.add(new RegionLocale(141L, 2L, 1L, 34L, "North Dakota - jp"));
		list.add(new RegionLocale(142L, 2L, 1L, 35L, "Ohio - jp"));
		list.add(new RegionLocale(143L, 2L, 1L, 36L, "Oklahoma - jp"));
		list.add(new RegionLocale(144L, 2L, 1L, 37L, "Oregon - jp"));
		list.add(new RegionLocale(145L, 2L, 1L, 38L, "Pennsylvania - jp"));
		list.add(new RegionLocale(146L, 2L, 1L, 39L, "Rhode Island - jp"));
		list.add(new RegionLocale(147L, 2L, 1L, 40L, "South Carolina - jp"));
		list.add(new RegionLocale(148L, 2L, 1L, 41L, "South Dakota - jp"));
		list.add(new RegionLocale(149L, 2L, 1L, 42L, "Tennessee - jp"));
		list.add(new RegionLocale(150L, 2L, 1L, 43L, "Texas - jp"));
		list.add(new RegionLocale(151L, 2L, 1L, 44L, "Utah - jp"));
		list.add(new RegionLocale(152L, 2L, 1L, 45L, "Vermont - jp"));
		list.add(new RegionLocale(153L, 2L, 1L, 46L, "Virginia - jp"));
		list.add(new RegionLocale(154L, 2L, 1L, 47L, "Washington - jp"));
		list.add(new RegionLocale(155L, 2L, 1L, 48L, "West Virginia - jp"));
		list.add(new RegionLocale(156L, 2L, 1L, 49L, "Wisconsin - jp"));
		list.add(new RegionLocale(157L, 2L, 1L, 50L, "Wyoming - jp"));
		
		list.add(new RegionLocale(158L, 2L, 2L, 51L, "Aichi - jp"));
		list.add(new RegionLocale(159L, 2L, 2L, 52L, "Akita - jp"));
		list.add(new RegionLocale(160L, 2L, 2L, 53L, "Aomori - jp"));
		list.add(new RegionLocale(161L, 2L, 2L, 54L, "Chiba - jp"));
		list.add(new RegionLocale(162L, 2L, 2L, 55L, "Ehime - jp"));
		list.add(new RegionLocale(163L, 2L, 2L, 56L, "Fukui - jp"));
		list.add(new RegionLocale(164L, 2L, 2L, 57L, "Fukuoka - jp"));
		list.add(new RegionLocale(165L, 2L, 2L, 58L, "Fukushima - jp"));
		list.add(new RegionLocale(166L, 2L, 2L, 59L, "Gifu - jp"));
		list.add(new RegionLocale(167L, 2L, 2L, 60L, "Gumma - jp"));
		list.add(new RegionLocale(168L, 2L, 2L, 61L, "Hiroshima - jp"));
		list.add(new RegionLocale(169L, 2L, 2L, 62L, "Hokkaido - jp"));
		list.add(new RegionLocale(170L, 2L, 2L, 63L, "Hyogo - jp"));
		list.add(new RegionLocale(171L, 2L, 2L, 64L, "Ibaragi - jp"));
		list.add(new RegionLocale(172L, 2L, 2L, 65L, "Ishikawa - jp"));
		list.add(new RegionLocale(173L, 2L, 2L, 66L, "Iwate - jp"));
		list.add(new RegionLocale(174L, 2L, 2L, 67L, "Kagawa - jp"));
		list.add(new RegionLocale(175L, 2L, 2L, 68L, "Kagoshima - jp"));
		list.add(new RegionLocale(176L, 2L, 2L, 69L, "Kanagawa - jp"));
		list.add(new RegionLocale(177L, 2L, 2L, 70L, "Kochi - jp"));
		list.add(new RegionLocale(178L, 2L, 2L, 71L, "Kumamoto - jp"));
		list.add(new RegionLocale(179L, 2L, 2L, 72L, "Kyoto - jp"));
		list.add(new RegionLocale(180L, 2L, 2L, 73L, "Mie - jp"));
		list.add(new RegionLocale(181L, 2L, 2L, 74L, "Miyagi - jp"));
		list.add(new RegionLocale(182L, 2L, 2L, 75L, "Miyazaki - jp"));
		list.add(new RegionLocale(183L, 2L, 2L, 76L, "Nagano - jp"));
		list.add(new RegionLocale(184L, 2L, 2L, 77L, "Nagasaki - jp"));
		list.add(new RegionLocale(185L, 2L, 2L, 78L, "Nara - jp"));
		list.add(new RegionLocale(186L, 2L, 2L, 79L, "Niigata - jp"));
		list.add(new RegionLocale(187L, 2L, 2L, 80L, "Oita - jp"));
		list.add(new RegionLocale(188L, 2L, 2L, 81L, "Okayama - jp"));
		list.add(new RegionLocale(189L, 2L, 2L, 82L, "Okinawa - jp"));
		list.add(new RegionLocale(190L, 2L, 2L, 83L, "Osaka - jp"));
		list.add(new RegionLocale(191L, 2L, 2L, 84L, "Saga - jp"));
		list.add(new RegionLocale(192L, 2L, 2L, 85L, "Saitama - jp"));
		list.add(new RegionLocale(193L, 2L, 2L, 86L, "Shiga - jp"));
		list.add(new RegionLocale(194L, 2L, 2L, 87L, "Shimane - jp"));
		list.add(new RegionLocale(195L, 2L, 2L, 88L, "Shizuoka - jp"));
		list.add(new RegionLocale(196L, 2L, 2L, 89L, "Tochigi - jp"));
		list.add(new RegionLocale(197L, 2L, 2L, 90L, "Tokushima - jp"));
		list.add(new RegionLocale(198L, 2L, 2L, 91L, "Tokyo-to - jp"));
		list.add(new RegionLocale(199L, 2L, 2L, 92L, "Tottori - jp"));
		list.add(new RegionLocale(200L, 2L, 2L, 93L, "Toyama - jp"));
		list.add(new RegionLocale(201L, 2L, 2L, 94L, "Wakayama - jp"));
		list.add(new RegionLocale(202L, 2L, 2L, 95L, "Yamagata - jp"));
		list.add(new RegionLocale(203L, 2L, 2L, 96L, "Yamaguchi - jp"));
		list.add(new RegionLocale(204L, 2L, 2L, 97L, "Yamanashi - jp"));
		
		list.add(new RegionLocale(205L, 2L, 3L, 98L, "Bangkok - jp"));
		list.add(new RegionLocale(206L, 2L, 3L, 99L, "Chiang Mai - jp"));
		list.add(new RegionLocale(207L, 2L, 3L, 100L, "Khon Kaen - jp"));
		list.add(new RegionLocale(208L, 2L, 3L, 101L, "Nakhon Pathom - jp"));
		list.add(new RegionLocale(209L, 2L, 3L, 102L, "Nakhon Ratchasima - jp"));
		list.add(new RegionLocale(210L, 2L, 3L, 103L, "Nakhon Sawan - jp"));
		list.add(new RegionLocale(211L, 2L, 3L, 104L, "Nonthaburi - jp"));
		list.add(new RegionLocale(212L, 2L, 3L, 105L, "Songkhla - jp"));
		list.add(new RegionLocale(213L, 2L, 3L, 106L, "Ubon Ratchathani - jp"));
		list.add(new RegionLocale(214L, 2L, 3L, 107L, "Udon Thani - jp"));

		//Regions in thai
		list.add(new RegionLocale(215L, 3L, 1L, 1L, "Alabama - th"));
		list.add(new RegionLocale(216L, 3L, 1L, 2L, "Alaska - th"));
		list.add(new RegionLocale(217L, 3L, 1L, 3L, "Arizona - th"));
		list.add(new RegionLocale(218L, 3L, 1L, 4L, "Arkansas - th"));
		list.add(new RegionLocale(219L, 3L, 1L, 5L, "California - th"));
		list.add(new RegionLocale(220L, 3L, 1L, 6L, "Colorado - th"));
		list.add(new RegionLocale(221L, 3L, 1L, 7L, "Connecticut - th"));
		list.add(new RegionLocale(222L, 3L, 1L, 8L, "Delaware - th"));
		list.add(new RegionLocale(223L, 3L, 1L, 9L, "Florida - th"));
		list.add(new RegionLocale(224L, 3L, 1L, 10L, "Georgia - th"));
		list.add(new RegionLocale(225L, 3L, 1L, 11L, "Hawaii - th"));
		list.add(new RegionLocale(226L, 3L, 1L, 12L, "Idaho - th"));
		list.add(new RegionLocale(227L, 3L, 1L, 13L, "Illinois - th"));
		list.add(new RegionLocale(228L, 3L, 1L, 14L, "Indiana - th"));
		list.add(new RegionLocale(229L, 3L, 1L, 15L, "Iowa - th"));
		list.add(new RegionLocale(230L, 3L, 1L, 16L, "Kansas - th"));
		list.add(new RegionLocale(231L, 3L, 1L, 17L, "Kentucky - th"));
		list.add(new RegionLocale(232L, 3L, 1L, 18L, "Louisiana - th"));
		list.add(new RegionLocale(233L, 3L, 1L, 19L, "Maine - th"));
		list.add(new RegionLocale(234L, 3L, 1L, 20L, "Maryland - th"));
		list.add(new RegionLocale(235L, 3L, 1L, 21L, "Massachusetts - th"));
		list.add(new RegionLocale(236L, 3L, 1L, 22L, "Michigan - th"));
		list.add(new RegionLocale(237L, 3L, 1L, 23L, "Minnesota - th"));
		list.add(new RegionLocale(238L, 3L, 1L, 24L, "Mississippi - th"));
		list.add(new RegionLocale(239L, 3L, 1L, 25L, "Missouri - th"));
		list.add(new RegionLocale(240L, 3L, 1L, 26L, "Montana - th"));
		list.add(new RegionLocale(241L, 3L, 1L, 27L, "Nebraska - th"));
		list.add(new RegionLocale(242L, 3L, 1L, 28L, "Nevada - th"));
		list.add(new RegionLocale(243L, 3L, 1L, 29L, "New Hampshire - th"));
		list.add(new RegionLocale(244L, 3L, 1L, 30L, "New Jersey - th"));
		list.add(new RegionLocale(245L, 3L, 1L, 31L, "New Mexico - th"));
		list.add(new RegionLocale(246L, 3L, 1L, 32L, "New York - th"));
		list.add(new RegionLocale(247L, 3L, 1L, 33L, "North Carolina - th"));
		list.add(new RegionLocale(248L, 3L, 1L, 34L, "North Dakota - th"));
		list.add(new RegionLocale(249L, 3L, 1L, 35L, "Ohio - th"));
		list.add(new RegionLocale(250L, 3L, 1L, 36L, "Oklahoma - th"));
		list.add(new RegionLocale(251L, 3L, 1L, 37L, "Oregon - th"));
		list.add(new RegionLocale(252L, 3L, 1L, 38L, "Pennsylvania - th"));
		list.add(new RegionLocale(253L, 3L, 1L, 39L, "Rhode Island - th"));
		list.add(new RegionLocale(254L, 3L, 1L, 40L, "South Carolina - th"));
		list.add(new RegionLocale(255L, 3L, 1L, 41L, "South Dakota - th"));
		list.add(new RegionLocale(256L, 3L, 1L, 42L, "Tennessee - th"));
		list.add(new RegionLocale(257L, 3L, 1L, 43L, "Texas - th"));
		list.add(new RegionLocale(258L, 3L, 1L, 44L, "Utah - th"));
		list.add(new RegionLocale(259L, 3L, 1L, 45L, "Vermont - th"));
		list.add(new RegionLocale(260L, 3L, 1L, 46L, "Virginia - th"));
		list.add(new RegionLocale(261L, 3L, 1L, 47L, "Washington - th"));
		list.add(new RegionLocale(262L, 3L, 1L, 48L, "West Virginia - th"));
		list.add(new RegionLocale(263L, 3L, 1L, 49L, "Wisconsin - th"));
		list.add(new RegionLocale(264L, 3L, 1L, 50L, "Wyoming - th"));
		
		list.add(new RegionLocale(265L, 3L, 2L, 51L, "Aichi - th"));
		list.add(new RegionLocale(266L, 3L, 2L, 52L, "Akita - th"));
		list.add(new RegionLocale(267L, 3L, 2L, 53L, "Aomori - th"));
		list.add(new RegionLocale(268L, 3L, 2L, 54L, "Chiba - th"));
		list.add(new RegionLocale(269L, 3L, 2L, 55L, "Ehime - th"));
		list.add(new RegionLocale(270L, 3L, 2L, 56L, "Fukui - th"));
		list.add(new RegionLocale(271L, 3L, 2L, 57L, "Fukuoka - th"));
		list.add(new RegionLocale(272L, 3L, 2L, 58L, "Fukushima - th"));
		list.add(new RegionLocale(273L, 3L, 2L, 59L, "Gifu - th"));
		list.add(new RegionLocale(274L, 3L, 2L, 60L, "Gumma - th"));
		list.add(new RegionLocale(275L, 3L, 2L, 61L, "Hiroshima - th"));
		list.add(new RegionLocale(276L, 3L, 2L, 62L, "Hokkaido - th"));
		list.add(new RegionLocale(277L, 3L, 2L, 63L, "Hyogo - th"));
		list.add(new RegionLocale(278L, 3L, 2L, 64L, "Ibaragi - th"));
		list.add(new RegionLocale(279L, 3L, 2L, 65L, "Ishikawa - th"));
		list.add(new RegionLocale(280L, 3L, 2L, 66L, "Iwate - th"));
		list.add(new RegionLocale(281L, 3L, 2L, 67L, "Kagawa - th"));
		list.add(new RegionLocale(282L, 3L, 2L, 68L, "Kagoshima - th"));
		list.add(new RegionLocale(283L, 3L, 2L, 69L, "Kanagawa - th"));
		list.add(new RegionLocale(284L, 3L, 2L, 70L, "Kochi - th"));
		list.add(new RegionLocale(285L, 3L, 2L, 71L, "Kumamoto - th"));
		list.add(new RegionLocale(286L, 3L, 2L, 72L, "Kyoto - th"));
		list.add(new RegionLocale(287L, 3L, 2L, 73L, "Mie - th"));
		list.add(new RegionLocale(288L, 3L, 2L, 74L, "Miyagi - th"));
		list.add(new RegionLocale(289L, 3L, 2L, 75L, "Miyazaki - th"));
		list.add(new RegionLocale(290L, 3L, 2L, 76L, "Nagano - th"));
		list.add(new RegionLocale(291L, 3L, 2L, 77L, "Nagasaki - th"));
		list.add(new RegionLocale(292L, 3L, 2L, 78L, "Nara - th"));
		list.add(new RegionLocale(293L, 3L, 2L, 79L, "Niigata - th"));
		list.add(new RegionLocale(294L, 3L, 2L, 80L, "Oita - th"));
		list.add(new RegionLocale(295L, 3L, 2L, 81L, "Okayama - th"));
		list.add(new RegionLocale(296L, 3L, 2L, 82L, "Okinawa - th"));
		list.add(new RegionLocale(297L, 3L, 2L, 83L, "Osaka - th"));
		list.add(new RegionLocale(298L, 3L, 2L, 84L, "Saga - th"));
		list.add(new RegionLocale(299L, 3L, 2L, 85L, "Saitama - th"));
		list.add(new RegionLocale(300L, 3L, 2L, 86L, "Shiga - th"));
		list.add(new RegionLocale(301L, 3L, 2L, 87L, "Shimane - th"));
		list.add(new RegionLocale(302L, 3L, 2L, 88L, "Shizuoka - th"));
		list.add(new RegionLocale(303L, 3L, 2L, 89L, "Tochigi - th"));
		list.add(new RegionLocale(304L, 3L, 2L, 90L, "Tokushima - th"));
		list.add(new RegionLocale(305L, 3L, 2L, 91L, "Tokyo-to - th"));
		list.add(new RegionLocale(306L, 3L, 2L, 92L, "Tottori - th"));
		list.add(new RegionLocale(307L, 3L, 2L, 93L, "Toyama - th"));
		list.add(new RegionLocale(308L, 3L, 2L, 94L, "Wakayama - th"));
		list.add(new RegionLocale(309L, 3L, 2L, 95L, "Yamagata - th"));
		list.add(new RegionLocale(310L, 3L, 2L, 96L, "Yamaguchi - th"));
		list.add(new RegionLocale(311L, 3L, 2L, 97L, "Yamanashi - th"));
	
		list.add(new RegionLocale(312L, 3L, 3L, 98L, "Bangkok - th"));
		list.add(new RegionLocale(313L, 3L, 3L, 99L, "Chiang Mai - th"));
		list.add(new RegionLocale(314L, 3L, 3L, 100L, "Khon Kaen - th"));
		list.add(new RegionLocale(315L, 3L, 3L, 101L, "Nakhon Pathom - th"));
		list.add(new RegionLocale(316L, 3L, 3L, 102L, "Nakhon Ratchasima - th"));
		list.add(new RegionLocale(317L, 3L, 3L, 103L, "Nakhon Sawan - th"));
		list.add(new RegionLocale(318L, 3L, 3L, 104L, "Nonthaburi - th"));
		list.add(new RegionLocale(319L, 3L, 3L, 105L, "Songkhla - th"));
		list.add(new RegionLocale(320L, 3L, 3L, 106L, "Ubon Ratchathani - th"));
		list.add(new RegionLocale(321L, 3L, 3L, 107L, "Udon Thani - th"));

		return list;
	}
	
	/**
	 * @return list of default titles
	 * and titles in specific language 
	 */
	public static List<Object> getTitlesData() {

		List<Object> list = new ArrayList<Object>();

		//Default titles
		list.add(new Title(1L, "Mr."));
		list.add(new Title(2L, "Mrs."));
		list.add(new Title(3L, "Miss"));
		list.add(new Title(4L, "Ms"));
		list.add(new Title(5L, "Dr"));

		// titles in English
		list.add(new TitleLocale(1L, 1L, 1L, "Mr. - en"));
		list.add(new TitleLocale(2L, 1L, 2L, "Mrs. - en"));
		list.add(new TitleLocale(3L, 1L, 3L, "Miss - en"));
		list.add(new TitleLocale(4L, 1L, 4L, "Ms - en"));
		list.add(new TitleLocale(5L, 1L, 5L, "Dr - en"));

		// titles in Japanies
		list.add(new TitleLocale(6L, 2L, 1L, "Mr. - jp"));
		list.add(new TitleLocale(7L, 2L, 2L, "Mrs. - jp"));
		list.add(new TitleLocale(8L, 2L, 3L, "Miss - jp"));
		list.add(new TitleLocale(9L, 2L, 4L, "Ms - jp"));
		list.add(new TitleLocale(10L, 2L, 5L, "Dr - jp"));

		// titles in Thai
		list.add(new TitleLocale(11L, 3L, 1L, "Mr. - th"));
		list.add(new TitleLocale(12L, 3L, 2L, "Mrs. - th"));
		list.add(new TitleLocale(13L, 3L, 3L, "Miss - th"));
		list.add(new TitleLocale(14L, 3L, 4L, "Ms - th"));
		list.add(new TitleLocale(15L, 3L, 5L, "Dr - th"));

		return list;
	}

	/**
	 * @return list of default money account status
	 * and money account status in specific language 
	 */
	public static List<Object> getMoneyAccountStatusData() {

		List<Object> list = new ArrayList<Object>();

		//Default money account status
		list.add(new MoneyAccountStatus(1L, "Not Verified"));
		list.add(new MoneyAccountStatus(2L, "Pending"));
		list.add(new MoneyAccountStatus(3L, "Rejected"));
		list.add(new MoneyAccountStatus(4L, "Verified"));

		//money account status in Engilish
		list.add(new MoneyAccountStatusLocale(1L, 1L, 1L, "Not Verified - en"));
		list.add(new MoneyAccountStatusLocale(2L, 1L, 2L, "Pending - en"));
		list.add(new MoneyAccountStatusLocale(3L, 1L, 3L, "Rejected - en"));
		list.add(new MoneyAccountStatusLocale(4L, 1L, 4L, "Verified - en"));

		//money account status in Japan
		list.add(new MoneyAccountStatusLocale(5L, 2L, 1L, "Not Verified - jp"));
		list.add(new MoneyAccountStatusLocale(6L, 2L, 2L, "Pending - jp"));
		list.add(new MoneyAccountStatusLocale(7L, 2L, 3L, "Rejected - jp"));
		list.add(new MoneyAccountStatusLocale(8L, 2L, 4L, "Verified - jp"));

		//money account status in Thai
		list.add(new MoneyAccountStatusLocale(9L, 3L, 1L, "Not Verified - th"));
		list.add(new MoneyAccountStatusLocale(10L, 3L, 2L, "Pending - th"));
		list.add(new MoneyAccountStatusLocale(11L, 3L, 3L, "Rejected - th"));
		list.add(new MoneyAccountStatusLocale(12L, 3L, 4L, "Verified - th"));

		return list;
	}
	
	/**
	 * @return list of default money account status
	 * and money account status in specific language 
	 */
	public static List<Object> getMoneyAccountDeleteStatusData() {

		List<Object> list = new ArrayList<Object>();

		//Default money account delete status
		list.add(new MoneyAccountDeleteStatus(1L, "Not Delete"));
		list.add(new MoneyAccountDeleteStatus(2L, "Soft Delete"));

		//money account deletestatus in Engilish
		list.add(new MoneyAccountDeleteStatusLocale(1L, 1L, 1L, "Not Delete - en"));
		list.add(new MoneyAccountDeleteStatusLocale(2L, 1L, 2L, "Soft Delete - en"));

		//money account delete status in Japan
		list.add(new MoneyAccountDeleteStatusLocale(3L, 2L, 1L, "Not Delete - jp"));
		list.add(new MoneyAccountDeleteStatusLocale(4L, 2L, 2L, "Soft Delete - jp"));

		//money account delete status in Thai
		list.add(new MoneyAccountDeleteStatusLocale(5L, 3L, 1L, "Not Delete - th"));
		list.add(new MoneyAccountDeleteStatusLocale(6L, 3L, 2L, "Soft Delete - th"));

		return list;
	}

	/**
	 * @return list of default bank account types 
	 * and bank account types in specific language for specific country
	 */
	public static List<Object> getBankAccountTypesData() {

		List<Object> list = new ArrayList<Object>();
		
		//Default bank account types
		// accounttype id, country id, type
		list.add(new BankAccountType(1L, 2L, "Futsuu"));
		list.add(new BankAccountType(2L, 2L, "Touza"));
		list.add(new BankAccountType(4L, 2L, "Chochiku"));
		list.add(new BankAccountType(9L, 2L, "Sonota"));
		list.add(new BankAccountType(11L, 1L, "Savings"));
		list.add(new BankAccountType(12L, 1L, "Checking"));
		list.add(new BankAccountType(13L, 3L, "Savings"));
		list.add(new BankAccountType(14L, 3L, "Current"));
		 
		//bank account types in english
		// id, language id, country id, accounttype id, type
		list.add(new BankAccountTypeLocale(1L, 1L, 2L, 1L, "Futsuu - en"));
		list.add(new BankAccountTypeLocale(2L, 1L, 2L, 2L, "Touza - en"));
		list.add(new BankAccountTypeLocale(3L, 1L, 2L, 4L, "Chochiku - en"));
		list.add(new BankAccountTypeLocale(4L, 1L, 2L, 9L, "Sonota - en"));
		list.add(new BankAccountTypeLocale(5L, 1L, 1L, 11L, "Savings - en"));
		list.add(new BankAccountTypeLocale(6L, 1L, 1L, 12L, "Checking - en"));
		list.add(new BankAccountTypeLocale(7L, 1L, 3L, 13L, "Savings - en"));
		list.add(new BankAccountTypeLocale(8L, 1L, 3L, 14L, "Current - en"));

		//bank account types in japanees
		// id, language id, country id, accounttype id, type
		list.add(new BankAccountTypeLocale(9L, 2L, 2L, 1L, "Futsuu - jp"));
		list.add(new BankAccountTypeLocale(10L, 2L, 2L, 2L, "Touza - jp"));
		list.add(new BankAccountTypeLocale(11L, 2L, 2L, 4L, "Chochiku - jp"));
		list.add(new BankAccountTypeLocale(12L, 2L, 2L, 9L, "Sonota - jp"));
		list.add(new BankAccountTypeLocale(13L, 2L, 1L, 11L, "Savings - jp"));
		list.add(new BankAccountTypeLocale(14L, 2L, 1L, 12L, "Checking - jp"));
		list.add(new BankAccountTypeLocale(15L, 2L, 3L, 13L, "Savings - jp"));
		list.add(new BankAccountTypeLocale(16L, 2L, 3L, 14L, "Current - jp"));

		//bank account types in thai
		// id, language id, country id, accounttype id, type
		list.add(new BankAccountTypeLocale(17L, 3L, 2L, 1L, "Futsuu - th"));
		list.add(new BankAccountTypeLocale(18L, 3L, 2L, 2L, "Touza - th"));
		list.add(new BankAccountTypeLocale(19L, 3L, 2L, 4L, "Chochiku - th"));
		list.add(new BankAccountTypeLocale(20L, 3L, 2L, 9L, "Sonota - th"));
		list.add(new BankAccountTypeLocale(21L, 3L, 1L, 11L, "Savings - th"));
		list.add(new BankAccountTypeLocale(22L, 3L, 1L, 12L, "Checking - th"));
		list.add(new BankAccountTypeLocale(23L, 3L, 3L, 13L, "Savings - th"));
		list.add(new BankAccountTypeLocale(24L, 3L, 3L, 14L, "Current - th"));

		return list;
	}
	
	/**
	 * @return list of default fee types 
	 * and fee types in specific language
	 */
	public static List<Object> getFeeTypesData() {

		List<Object> list = new ArrayList<Object>();
		
		//Default fee types
		//id, name
		list.add(new FeeType(1L, "Flat fee"));
		list.add(new FeeType(2L, "Percentage of the transaction amount"));
		list.add(new FeeType(3L, "Flat fee + percentage of the transaction amount"));
		 
		//fee types in english
		// id, language id, name
		list.add(new FeeTypeLocale(1L, 1L, 1L, "Flat fee - en"));
		list.add(new FeeTypeLocale(2L, 2L, 1L, "Percentage of the transaction amount - en"));
		list.add(new FeeTypeLocale(3L, 3L, 1L, "Flat fee + percentage of the transaction amount - en"));
		
		//fee types in japanees
		// id, language id, name
		list.add(new FeeTypeLocale(4L, 1L, 2L, "Flat fee - jp"));
		list.add(new FeeTypeLocale(5L, 2L, 2L, "Percentage of the transaction amount - jp"));
		list.add(new FeeTypeLocale(6L, 3L, 2L, "Flat fee + percentage of the transaction amount - jp"));
		
		//fee types in thai
		// id, language id, name
		list.add(new FeeTypeLocale(7L, 1L, 3L, "Flat fee - th"));
		list.add(new FeeTypeLocale(8L, 2L, 3L, "Percentage of the transaction amount - th"));
		list.add(new FeeTypeLocale(9L, 3L, 3L, "Flat fee + percentage of the transaction amount - th"));
		
		return list;
	}
	
	/**
	 * @return list of default fee types 
	 * and fee types in specific language
	 */
	public static List<Object> getFeeServiceTypesData() {

		List<Object> list = new ArrayList<Object>();
		
		//Default fee types
		//id, name
		list.add(new FeeService(1L, "Financial Services"));
		list.add(new FeeService(2L, "Non Financial Services"));
		list.add(new FeeService(3L, "Financial On Velocity"));
		 
		//fee types in english
		// id, stId, language id, name
		list.add(new FeeServiceLocale(1L, 1L, 1L, "Financial Services - en"));
		list.add(new FeeServiceLocale(2L, 2L, 1L, "Non Financial Services - en"));
		list.add(new FeeServiceLocale(3L, 3L, 1L, "Financial On Velocity - en"));
		
		//fee types in japanees
		// id, stId, language id, name
		list.add(new FeeServiceLocale(4L, 1L, 2L, "Financial Services - jp"));
		list.add(new FeeServiceLocale(5L, 2L, 2L, "Non Financial Services - jp"));
		list.add(new FeeServiceLocale(6L, 3L, 2L, "Financial On Velocity - jp"));
				
		//fee types in thai
		//id, stId, language id, name
		list.add(new FeeServiceLocale(7L, 1L, 3L, "Financial Services - th"));
		list.add(new FeeServiceLocale(8L, 2L, 3L, "Non Financial Services - th"));
		list.add(new FeeServiceLocale(9L, 3L, 3L, "Financial On Velocity - th"));
		
		return list;
	}
	
	/**
	 * @return list of default fee types 
	 * and fee types in specific language
	 */
	public static List<Object> getFeeOperationTypesData() {

		List<Object> list = new ArrayList<Object>();
		
		//Default fee types
		//id, fee type, user type(Customer), name
		
		//id, fee type(Financial), user type(Customer), name
		FeeOperationType fo_c1 = new FeeOperationType(1L, 1L, 1L, "P 2 P");
		list.add(fo_c1);
		
		FeeOperationType fo_c2 = new FeeOperationType(2L, 1L, 1L, "P 2 M");
		list.add(fo_c2);
		
		FeeOperationType fo_c3 = new FeeOperationType(3L, 1L, 1L, "P 2 NP(Non Register Person)");
		list.add(fo_c3);
		
		FeeOperationType fo_c4 = new FeeOperationType(4L, 1L, 1L, "Withdraw money to Card");
		list.add(fo_c4);
		
		FeeOperationType fo_c5 = new FeeOperationType(5L, 1L, 1L, "Withdraw money to Bank");
		list.add(fo_c5);
		
		FeeOperationType fo_c6 = new FeeOperationType(6L, 1L, 1L, "Reload money from Card");
		list.add(fo_c6);
		
		FeeOperationType fo_c7 = new FeeOperationType(7L, 1L, 1L, "Reload money from Bank");
		list.add(fo_c7);
		
		FeeOperationType fo_c8 = new FeeOperationType(8L, 1L, 1L, "Penny drop for Card");
		list.add(fo_c8);
		
		FeeOperationType fo_c9 = new FeeOperationType(9L, 1L, 1L, "Penny drop for Bank");
		list.add(fo_c9);
		
		FeeOperationType fo_c10 = new FeeOperationType(10L, 1L, 1L, "Recover from Card");
		list.add(fo_c10);
		
		FeeOperationType fo_c11 = new FeeOperationType(11L, 1L, 1L, "Recover from Bank");
		list.add(fo_c11);
		
		
		
		//id, fee type(non Financial), user type(Customer), name
		FeeOperationType nfo_c1 = new FeeOperationType(12L, 2L, 1L, "Customer annual fee");
		list.add(nfo_c1);
		FeeOperationType nfo_c2 = new FeeOperationType(13L, 2L, 1L, "Customer joining fee");
		list.add(nfo_c2);
		
		//id, fee type(Financial periodic), user type(Customer), name
		FeeOperationType fp_c1 = new FeeOperationType(14L, 3L, 1L, "Customer Quarterly fee");
		list.add(fp_c1);
		FeeOperationType fp_c2 = new FeeOperationType(15L, 3L, 1L, "Customer Monthly fee");
		list.add(fp_c2);

		//id, fee type(Financial), user type(Merchant), name
		FeeOperationType fo_m1 = new FeeOperationType(16L, 1L, 2L, "M 2 P");
		list.add(fo_m1);
		
		FeeOperationType fo_m2 = new FeeOperationType(17L, 1L, 2L, "M 2 M");
		list.add(fo_m2);

		FeeOperationType fo_m3 = new FeeOperationType(18L, 1L, 2L, "M 2 NP(Non Register Person)");
		list.add(fo_m3);

		FeeOperationType fo_m4 = new FeeOperationType(19L, 1L, 2L, "Withdraw money to Card");
		list.add(fo_m4);

		FeeOperationType fo_m5 = new FeeOperationType(20L, 1L, 2L, "Withdraw money to Bank");
		list.add(fo_m5);

		FeeOperationType fo_m6 = new FeeOperationType(21L, 1L, 2L, "Reload money from Card");
		list.add(fo_m6);
		
		FeeOperationType fo_m7 = new FeeOperationType(22L, 1L, 2L, "Reload money from Bank");
		list.add(fo_m7);
		
		FeeOperationType fo_m8 = new FeeOperationType(23L, 1L, 2L, "Penny drop from Card");
		list.add(fo_m8);
		
		FeeOperationType fo_m9 = new FeeOperationType(24L, 1L, 2L, "Penny drop from Bank");
		list.add(fo_m9);
		
		FeeOperationType fo_m10 = new FeeOperationType(25L, 1L, 2L, "Recover from Card");
		list.add(fo_m10);
		
		FeeOperationType fo_m11 = new FeeOperationType(26L, 1L, 2L, "Recover from Bank");
		list.add(fo_m11);

		// id, fee type(non Financial), user type(Merchant), name
		FeeOperationType nfo_m1 = new FeeOperationType(27L, 2L, 2L,"Merchant annual fee");
		list.add(nfo_m1);
		FeeOperationType nfo_m2 = new FeeOperationType(28L, 2L, 2L,"Merchant joining fee");
		list.add(nfo_m2);
		FeeOperationType nfo_m3 = new FeeOperationType(29L, 2L, 2L,"Merchant statement copy");
		list.add(nfo_m3);
		
		//id, fee type(Financial periodic), user type(Customer), name
		FeeOperationType fp_m1 = new FeeOperationType(30L, 3L, 2L, "Merchant Quarterly fee");
		list.add(fp_m1);
		
		FeeOperationType fp_m2 = new FeeOperationType(31L, 3L, 2L, "Merchant Monthly fee");
		list.add(fp_m2);
		
		//updating financial services P_TO_S = 32L;//financial P 2 S and M_TO_S = 33L;//financial M 2 S
		FeeOperationType fo_c32 = new FeeOperationType(32L, 1L, 1L, "P 2 S");
		list.add(fo_c32);
		
		FeeOperationType fo_m33 = new FeeOperationType(33L, 1L, 2L, "M 2 S");
		list.add(fo_m33);

		FeeOperationType fo_chb34 = new FeeOperationType(34L, 4L, 4L, "Charge back");
		list.add(fo_chb34);
		
		FeeOperationType fo_chb35 = new FeeOperationType(35L, 4L, 4L, "Refund");
		list.add(fo_chb35);
		
		
		
		//id, FeeOperationType obj, name, language
		list.add(new FeeOperationTypeLocale(1L, fo_c1, "P 2 P - en", 1L));
		list.add(new FeeOperationTypeLocale(2L, fo_c2, "P 2 M - en", 1L));
		list.add(new FeeOperationTypeLocale(3L, fo_c3, "P 2 NP (Non Register Person) - en", 1L));
		list.add(new FeeOperationTypeLocale(4L, fo_c4, "Withdraw money to Card - en", 1L));
		list.add(new FeeOperationTypeLocale(5L, fo_c5, "Withdraw money to Bank - en", 1L));
		list.add(new FeeOperationTypeLocale(6L, fo_c6, "Reload  money from Card - en", 1L));
		list.add(new FeeOperationTypeLocale(7L, fo_c7, "Reload  money from Bank - en", 1L));
		list.add(new FeeOperationTypeLocale(8L, fo_c8, "Penny drop for Card - en", 1L));
		list.add(new FeeOperationTypeLocale(9L, fo_c9, "Penny drop for Bank - en", 1L));
		list.add(new FeeOperationTypeLocale(10L, fo_c10, "Recover from Card - en", 1L));
		list.add(new FeeOperationTypeLocale(11L, fo_c11, "Recover from Bank - en", 1L));
		
		list.add(new FeeOperationTypeLocale(12L, nfo_c1, "Customer annual fee - en", 1L));
		list.add(new FeeOperationTypeLocale(13L, nfo_c2, "Customer joining fee - en", 1L));

		list.add(new FeeOperationTypeLocale(14L, fp_c1, "Customer Quarterly fee - en", 1L));
		list.add(new FeeOperationTypeLocale(15L, fp_c2, "Customer Monthly fee - en", 1L));
		
		list.add(new FeeOperationTypeLocale(16L, fo_m1, "M 2 P - en", 1L));
		list.add(new FeeOperationTypeLocale(17L, fo_m2, "M 2 M - en", 1L));
		list.add(new FeeOperationTypeLocale(18L, fo_m3, "M 2 NP - en", 1L));
		
		list.add(new FeeOperationTypeLocale(19L, fo_m4, "Withdraw money to Card - en", 1L));
		list.add(new FeeOperationTypeLocale(20L, fo_m5, "Withdraw money to Bank - en", 1L));
		list.add(new FeeOperationTypeLocale(21L, fo_m6, "Reload money from Card - en", 1L));
		list.add(new FeeOperationTypeLocale(22L, fo_m7, "Reload money from Bank - en", 1L));
		list.add(new FeeOperationTypeLocale(23L, fo_m8, "Penny drop for Card - en", 1L));
		list.add(new FeeOperationTypeLocale(24L, fo_m9, "Penny drop for Bank - en", 1L));
		list.add(new FeeOperationTypeLocale(25L, fo_m10, "Recover from Card - en", 1L));
		list.add(new FeeOperationTypeLocale(26L, fo_m11, "Recover from Bank - en", 1L));
		
		list.add(new FeeOperationTypeLocale(27L, nfo_m1, "Merchant annual fee - en", 1L));
		list.add(new FeeOperationTypeLocale(28L, nfo_m2, "Merchant joining fee - en", 1L));
		list.add(new FeeOperationTypeLocale(29L, nfo_m3, "Merchant statement copy - en", 1L));

		list.add(new FeeOperationTypeLocale(30L, fp_m1, "Merchant Quarterly fee - en", 1L));
		list.add(new FeeOperationTypeLocale(31L, fp_m2, "Merchant Monthly fee - en", 1L));
		
		list.add(new FeeOperationTypeLocale(32L, fo_c32, "P 2 S - en", 1L));
		list.add(new FeeOperationTypeLocale(33L, fo_m33, "M 2 S - en", 1L));

		list.add(new FeeOperationTypeLocale(34L, fo_chb34, "Charge back - en", 1L));
		list.add(new FeeOperationTypeLocale(35L, fo_chb35, "Refund - en", 1L));
		
		
		
		list.add(new FeeOperationTypeLocale(36L, fo_c1, "P 2 P - jp", 2L));
		list.add(new FeeOperationTypeLocale(37L, fo_c2, "P 2 M - jp", 2L));
		list.add(new FeeOperationTypeLocale(38L, fo_c3, "P 2 NP (Non Register Person) - jp", 2L));
		list.add(new FeeOperationTypeLocale(39L, fo_c4, "Withdraw money to Card - jp", 2L));
		list.add(new FeeOperationTypeLocale(40L, fo_c5, "Withdraw money to Bank - jp", 2L));
		list.add(new FeeOperationTypeLocale(41L, fo_c6, "Reload  money from Card - jp", 2L));
		list.add(new FeeOperationTypeLocale(42L, fo_c7, "Reload  money from Bank - jp", 2L));
		list.add(new FeeOperationTypeLocale(43L, fo_c8, "Penny drop for Card - jp", 2L));
		list.add(new FeeOperationTypeLocale(44L, fo_c9, "Penny drop for Bank - jp", 2L));
		list.add(new FeeOperationTypeLocale(45L, fo_c10, "Recover from Card - jp", 2L));
		list.add(new FeeOperationTypeLocale(46L, fo_c11, "Recover from Bank - jp", 2L));
		
		list.add(new FeeOperationTypeLocale(47L, nfo_c1, "Customer annual fee - jp", 2L));
		list.add(new FeeOperationTypeLocale(48L, nfo_c2, "Customer joining fee - jp", 2L));

		list.add(new FeeOperationTypeLocale(49L, fp_c1, "Customer Quarterly fee - jp", 2L));
		list.add(new FeeOperationTypeLocale(50L, fp_c2, "Customer Monthly fee - jp", 2L));
		
		list.add(new FeeOperationTypeLocale(51L, fo_m1, "M 2 P - jp", 2L));
		list.add(new FeeOperationTypeLocale(52L, fo_m2, "M 2 M - jp", 2L));
		list.add(new FeeOperationTypeLocale(53L, fo_m3, "M 2 NP - jp", 2L));
		
		list.add(new FeeOperationTypeLocale(54L, fo_m4, "Withdraw money to Card - jp", 2L));
		list.add(new FeeOperationTypeLocale(55L, fo_m5, "Withdraw money to Bank - jp", 2L));
		list.add(new FeeOperationTypeLocale(56L, fo_m6, "Reload money from Card - jp", 2L));
		list.add(new FeeOperationTypeLocale(57L, fo_m7, "Reload money from Bank - jp", 2L));
		list.add(new FeeOperationTypeLocale(58L, fo_m8, "Penny drop for Card - jp", 2L));
		list.add(new FeeOperationTypeLocale(59L, fo_m9, "Penny drop for Bank - jp", 2L));
		list.add(new FeeOperationTypeLocale(60L, fo_m10, "Recover from Card - jp", 2L));
		list.add(new FeeOperationTypeLocale(61L, fo_m11, "Recover from Bank - jp", 2L));
		
		list.add(new FeeOperationTypeLocale(62L, nfo_m1, "Merchant annual fee - jp", 2L));
		list.add(new FeeOperationTypeLocale(63L, nfo_m2, "Merchant joining fee - jp", 2L));
		list.add(new FeeOperationTypeLocale(64L, nfo_m3, "Merchant statemjpt copy - jp", 2L));

		list.add(new FeeOperationTypeLocale(65L, fp_m1, "Merchant Quarterly fee - jp", 2L));
		list.add(new FeeOperationTypeLocale(66L, fp_m2, "Merchant Monthly fee - jp", 2L));
		
		list.add(new FeeOperationTypeLocale(67L, fo_c32, "P 2 S - jp", 2L));
		list.add(new FeeOperationTypeLocale(68L, fo_m33, "M 2 S - jp", 2L));
		
		list.add(new FeeOperationTypeLocale(69L, fo_chb34, "Charge back - en", 2L));
		list.add(new FeeOperationTypeLocale(70L, fo_chb35, "Refund - en", 2L));

		
		list.add(new FeeOperationTypeLocale(71L, fo_c1, "P 2 P - th", 3L));
		list.add(new FeeOperationTypeLocale(72L, fo_c2, "P 2 M - th", 3L));
		list.add(new FeeOperationTypeLocale(73L, fo_c3, "P 2 NP (Non Register Person) - th", 3L));
		list.add(new FeeOperationTypeLocale(74L, fo_c4, "Withdraw money to Card - th", 3L));
		list.add(new FeeOperationTypeLocale(75L, fo_c5, "Withdraw money to Bank - th", 3L));
		list.add(new FeeOperationTypeLocale(76L, fo_c6, "Reload  money from Card - th", 3L));
		list.add(new FeeOperationTypeLocale(77L, fo_c7, "Reload  money from Bank - th", 3L));
		list.add(new FeeOperationTypeLocale(78L, fo_c8, "Penny drop for Card - th", 3L));
		list.add(new FeeOperationTypeLocale(79L, fo_c9, "Penny drop for Bank - th", 3L));
		list.add(new FeeOperationTypeLocale(80L, fo_c10, "Recover from Card - th", 3L));
		list.add(new FeeOperationTypeLocale(81L, fo_c11, "Recover from Bank - th", 3L));
		
		list.add(new FeeOperationTypeLocale(82L, nfo_c1, "Customer annual fee - th", 3L));
		list.add(new FeeOperationTypeLocale(83L, nfo_c2, "Customer joining fee - th", 3L));

		list.add(new FeeOperationTypeLocale(84L, fp_c1, "Customer Quarterly fee - th", 3L));
		list.add(new FeeOperationTypeLocale(85L, fp_c2, "Customer Monthly fee - th", 3L));
		
		list.add(new FeeOperationTypeLocale(86L, fo_m1, "M 2 P - th", 3L));
		list.add(new FeeOperationTypeLocale(87L, fo_m2, "M 2 M - th", 3L));
		list.add(new FeeOperationTypeLocale(88L, fo_m3, "M 2 NP - th", 3L));
		
		list.add(new FeeOperationTypeLocale(89L, fo_m4, "Withdraw money to Card - th", 3L));
		list.add(new FeeOperationTypeLocale(90L, fo_m5, "Withdraw money to Bank - th", 3L));
		list.add(new FeeOperationTypeLocale(91L, fo_m6, "Reload money from Card - th", 3L));
		list.add(new FeeOperationTypeLocale(92L, fo_m7, "Reload money from Bank - th", 3L));
		list.add(new FeeOperationTypeLocale(93L, fo_m8, "Penny drop for Card - th", 3L));
		list.add(new FeeOperationTypeLocale(94L, fo_m9, "Penny drop for Bank - th", 3L));
		list.add(new FeeOperationTypeLocale(95L, fo_m10, "Recover from Card - th", 3L));
		list.add(new FeeOperationTypeLocale(96L, fo_m11, "Recover from Bank - th", 3L));
		
		list.add(new FeeOperationTypeLocale(97L, nfo_m1, "Merchant annual fee - th", 3L));
		list.add(new FeeOperationTypeLocale(98L, nfo_m2, "Merchant joining fee - th", 3L));
		list.add(new FeeOperationTypeLocale(99L, nfo_m3, "Merchant statemtht copy - th", 3L));

		list.add(new FeeOperationTypeLocale(100L, fp_m1, "Merchant Quarterly fee - th", 3L));
		list.add(new FeeOperationTypeLocale(101L, fp_m2, "Merchant Monthly fee - th", 3L));

		list.add(new FeeOperationTypeLocale(102L, fo_c32, "P 2 S - th", 3L));
		list.add(new FeeOperationTypeLocale(103L, fo_m33, "M 2 S - th", 3L));
		
		list.add(new FeeOperationTypeLocale(104L, fo_chb34, "Charge back - en", 3L));
		list.add(new FeeOperationTypeLocale(105L, fo_chb35, "Refund - en", 3L));

		
		return list;
	}	
	
	
	/**
	 * @return list of default fee paying entities
	 * and fee paying entities in specific language 
	 */
	public static List<Object> getFeePayingEntitiesData() {

		List<Object> list = new ArrayList<Object>();

		//Default fee paying entities
		list.add(new FeePayingEntity(1L, "Sender"));
		list.add(new FeePayingEntity(2L, "Receiver"));
		list.add(new FeePayingEntity(3L, "Both"));

		//fee paying entities in Engilish
		list.add(new FeePayingEntityLocale(1L, 1L, 1L, "Sender - en"));
		list.add(new FeePayingEntityLocale(2L, 1L, 2L, "Receiver - en"));
		list.add(new FeePayingEntityLocale(3L, 1L, 3L, "Both - en"));

		//fee paying entities in Japan
		list.add(new FeePayingEntityLocale(4L, 2L, 1L, "Sender - jp"));
		list.add(new FeePayingEntityLocale(5L, 2L, 2L, "Receiver - jp"));
		list.add(new FeePayingEntityLocale(6L, 2L, 3L, "Both - jp"));

		//fee paying entities in Thai
		list.add(new FeePayingEntityLocale(7L, 3L, 1L, "Sender - th"));
		list.add(new FeePayingEntityLocale(8L, 3L, 2L, "Receiver - th"));
		list.add(new FeePayingEntityLocale(9L, 3L, 3L, "Both - th"));

		return list;
	}

	public static List<Object> getMasterWalletsData(){
		List<Object> list = new ArrayList<Object>();
		
		// MasterAmountWallet (id, currency, amount)
		list.add(new MasterAmountWallet(1L, 1L, 0.0));
		list.add(new MasterAmountWallet(2L, 2L, 0.0));
		list.add(new MasterAmountWallet(3L, 3L, 0.0));

		// MasterFeeWallet( id, currency, fee)
		list.add(new MasterFeeWallet(1L, 1L, 0.0));
		list.add(new MasterFeeWallet(2L, 2L, 0.0));
		list.add(new MasterFeeWallet(3L, 3L, 0.0));

		// MasterTaxWallet( id, currency, tax)
		list.add(new MasterTaxWallet(1L, 1L, 0.0));
		list.add(new MasterTaxWallet(2L, 2L, 0.0));
		list.add(new MasterTaxWallet(3L, 3L, 0.0));

		return list;
	}
	
	/**
	 * @return list of default fee time frequency entities
	 * and fee time frequency in specific language 
	 */
	public static List<Object> getFeeTimeFrequencyData() {

		List<Object> list = new ArrayList<Object>();

		//Default fee time frequency entities
		list.add(new FeeTimeFrequency(1L, "Weekly"));
		list.add(new FeeTimeFrequency(2L, "Monthly"));
		list.add(new FeeTimeFrequency(3L, "Quarterly"));
		list.add(new FeeTimeFrequency(4L, "Half yearly"));
		list.add(new FeeTimeFrequency(5L, "Annually"));

		//fee time frequency entities in Engilish
		list.add(new FeeTimeFrequencyLocale(1L, 1L, 1L, "Weekly - en"));
		list.add(new FeeTimeFrequencyLocale(2L, 1L, 2L, "Monthly - en"));
		list.add(new FeeTimeFrequencyLocale(3L, 1L, 3L, "Quarterly - en"));
		list.add(new FeeTimeFrequencyLocale(4L, 1L, 4L, "Half yearly - en"));
		list.add(new FeeTimeFrequencyLocale(5L, 1L, 5L, "Annually - en"));

		//fee time frequency entities in Japan
		list.add(new FeeTimeFrequencyLocale(6L, 2L, 1L, "Weekly - jp"));
		list.add(new FeeTimeFrequencyLocale(7L, 2L, 2L, "Monthly - jp"));
		list.add(new FeeTimeFrequencyLocale(8L, 2L, 3L, "Quarterly - jp"));
		list.add(new FeeTimeFrequencyLocale(9L, 2L, 4L, "Half yearly - jp"));
		list.add(new FeeTimeFrequencyLocale(10L, 2L, 5L, "Annually - jp"));

		//fee time frequency entities in Thai
		list.add(new FeeTimeFrequencyLocale(11L, 3L, 1L, "Weekly - th"));
		list.add(new FeeTimeFrequencyLocale(12L, 3L, 2L, "Monthly - th"));
		list.add(new FeeTimeFrequencyLocale(13L, 3L, 3L, "Quarterly - th"));
		list.add(new FeeTimeFrequencyLocale(14L, 3L, 4L, "Half yearly - th"));
		list.add(new FeeTimeFrequencyLocale(15L, 3L, 5L, "Annually - th"));

		return list;
	}
	
	/**
	 * @return list of default transaction time period entities
	 * and transaction time period entities in specific language 
	 */
	public static List<Object> getTrxTimePeriodData() {

		List<Object> list = new ArrayList<Object>();

		//Default transaction time period
		list.add(new TrxTimePeriod(1L, "Daily"));
		list.add(new TrxTimePeriod(2L, "Weekly"));
		list.add(new TrxTimePeriod(3L, "Monthly"));

		//transaction time period in Engilish
		list.add(new TrxTimePeriodLocale(1L, 1L, 1L, "Daily - en"));
		list.add(new TrxTimePeriodLocale(2L, 1L, 2L, "Weekly - en"));
		list.add(new TrxTimePeriodLocale(3L, 1L, 3L, "Monthly - en"));

		//transaction time period in Japan
		list.add(new TrxTimePeriodLocale(4L, 2L, 1L, "Daily - jp"));
		list.add(new TrxTimePeriodLocale(5L, 2L, 2L, "Weekly - jp"));
		list.add(new TrxTimePeriodLocale(6L, 2L, 3L, "Monthly - jp"));

		//transaction time period in Thai
		list.add(new TrxTimePeriodLocale(7L, 3L, 1L, "Daily - th"));
		list.add(new TrxTimePeriodLocale(8L, 3L, 2L, "Weekly - th"));
		list.add(new TrxTimePeriodLocale(9L, 3L, 3L, "Monthly - th"));

		return list;
	}
	
	/**
	 * @return list of default transaction status 
	 * and transaction status entities in specific language 
	 */
	public static List<Object> getTransactionStatusData() {

		List<Object> list = new ArrayList<Object>();

		//Default transaction status name
		list.add(new TransactionStatus(1L, "Pending"));
		list.add(new TransactionStatus(2L, "Cancel"));
		list.add(new TransactionStatus(3L, "Reject"));
		list.add(new TransactionStatus(4L, "Success"));
		list.add(new TransactionStatus(5L, "Scheduled"));
		list.add(new TransactionStatus(6L, "Failed"));
		list.add(new TransactionStatus(7L, "Expired"));
		list.add(new TransactionStatus(8L, "Fraud Check"));

		//transaction status  in Engilish
		list.add(new TransactionStatusLocale(1L, 1L, 1L, "Pending - en"));
		list.add(new TransactionStatusLocale(2L, 1L, 2L, "Cancel - en"));
		list.add(new TransactionStatusLocale(3L, 1L, 3L, "Reject - en"));
		list.add(new TransactionStatusLocale(4L, 1L, 4L, "Success - en"));
		list.add(new TransactionStatusLocale(5L, 1L, 5L, "Scheduled - en"));
		list.add(new TransactionStatusLocale(6L, 1L, 6L, "Failed - en"));
		list.add(new TransactionStatusLocale(7L, 1L, 7L, "Expired - en"));
		list.add(new TransactionStatusLocale(8L, 1L, 8L, "Fraud Check - en"));
		
		//transaction status  in Japan
		list.add(new TransactionStatusLocale(9L, 2L, 1L, "Pending - jp"));
		list.add(new TransactionStatusLocale(10L, 2L, 2L, "Cancel - jp"));
		list.add(new TransactionStatusLocale(11L, 2L, 3L, "Reject - jp"));
		list.add(new TransactionStatusLocale(12L, 2L, 4L, "Success - jp"));
		list.add(new TransactionStatusLocale(13L, 2L, 5L, "Scheduled - jp"));
		list.add(new TransactionStatusLocale(14L, 2L, 6L, "Failed - jp"));
		list.add(new TransactionStatusLocale(15L, 2L, 7L, "Expired - jp"));
		list.add(new TransactionStatusLocale(16L, 2L, 8L, "Fraud Check - jp"));
		
		//transaction status  in in Thai
		list.add(new TransactionStatusLocale(17L, 3L, 1L, "Pending - th"));
		list.add(new TransactionStatusLocale(18L, 3L, 2L, "Cancel - th"));
		list.add(new TransactionStatusLocale(19L, 3L, 3L, "Reject - th"));
		list.add(new TransactionStatusLocale(20L, 3L, 4L, "Success - th"));
		list.add(new TransactionStatusLocale(21L, 3L, 5L, "Scheduled - th"));
		list.add(new TransactionStatusLocale(22L, 3L, 6L, "Failed - th"));
		list.add(new TransactionStatusLocale(23L, 3L, 7L, "Expired - th"));
		list.add(new TransactionStatusLocale(24L, 3L, 8L, "Fraud Check - th"));
		
		return list;
	}
	
	/**
	 * @return list of default Receive Money status 
	 * and transaction status entities in specific language 
	 */
	public static List<Object> getReceiveMoneyStatusData() {

		List<Object> list = new ArrayList<Object>();

		//Default transaction status name
		list.add(new ReceiveMoneyStatus(1L, "Pending"));
		list.add(new ReceiveMoneyStatus(2L, "Cancel"));
		list.add(new ReceiveMoneyStatus(3L, "Reject"));
		list.add(new ReceiveMoneyStatus(4L, "Success"));
		list.add(new ReceiveMoneyStatus(5L, "Failed"));

		//receive money status  in Engilish
		list.add(new ReceiveMoneyStatusLocale(1L, 1L, 1L, "Pending - en"));
		list.add(new ReceiveMoneyStatusLocale(2L, 1L, 2L, "Cancel - en"));
		list.add(new ReceiveMoneyStatusLocale(3L, 1L, 3L, "Reject - en"));
		list.add(new ReceiveMoneyStatusLocale(4L, 1L, 4L, "Success - en"));
		list.add(new ReceiveMoneyStatusLocale(5L, 1L, 5L, "Failed - en"));

		//receive money status  in Japan
		list.add(new ReceiveMoneyStatusLocale(6L, 2L, 1L, "Pending - jp"));
		list.add(new ReceiveMoneyStatusLocale(7L, 2L, 2L, "Cancel - jp"));
		list.add(new ReceiveMoneyStatusLocale(8L, 2L, 3L, "Reject - jp"));
		list.add(new ReceiveMoneyStatusLocale(9L, 2L, 4L, "Success - jp"));
		list.add(new ReceiveMoneyStatusLocale(10L, 2L, 5L, "Failed - jp"));

		//receive money status  in Thai
		list.add(new ReceiveMoneyStatusLocale(11L, 3L, 1L, "Pending - th"));
		list.add(new ReceiveMoneyStatusLocale(12L, 3L, 2L, "Cancel - th"));
		list.add(new ReceiveMoneyStatusLocale(13L, 3L, 3L, "Reject - th"));
		list.add(new ReceiveMoneyStatusLocale(14L, 3L, 4L, "Success - th"));
		list.add(new ReceiveMoneyStatusLocale(15L, 3L, 5L, "Failed - th"));

		return list;
	}
	
	
	/**
	 * @return list of Destination type period entities
	 * and transaction time period entities in specific language 
	 */
	public static List<Object> getDestinationTypeData() {

		List<Object> list = new ArrayList<Object>();

		//Default Destination Type
		list.add(new DestinationType(1L, "Registered Member"));
		list.add(new DestinationType(4L, "Non Registered Member"));
		list.add(new DestinationType(2L, "Merchant"));

		//Default Destination Type in Engilish
		list.add(new DestinationTypeLocale(1L, 1L, 1L, "Registered Member - en"));
		list.add(new DestinationTypeLocale(2L, 1L, 4L, "Non Registered Member - en"));
		list.add(new DestinationTypeLocale(3L, 1L, 2L, "Merchant - en"));

		//Default Destination Type in Japan
		list.add(new DestinationTypeLocale(4L, 2L, 1L, "Registered Member - jp"));
		list.add(new DestinationTypeLocale(5L, 2L, 4L, "Non Registered Member - jp"));
		list.add(new DestinationTypeLocale(6L, 2L, 2L, "Merchant - jp"));

		//Default Destination Type in Thai
		list.add(new DestinationTypeLocale(7L, 3L, 1L, "Registered Member - th"));
		list.add(new DestinationTypeLocale(8L, 3L, 4L, "Non Registered Member - th"));
		list.add(new DestinationTypeLocale(9L, 3L, 2L, "Merchant - th"));

		return list;
	}
	
	/**
	 * @return list of default report types 
	 * and report types in specific language for 
	 */
	public static List<Object> getReportTypesData() {

		List<Object> list = new ArrayList<Object>();
		
		//Default report types for customer
		// id, userTypeId, name
		list.add(new ReportType(1L, 1L, "Last N Transactions"));
		list.add(new ReportType(2L, 1L, "Merchant wise transaction summary"));
		list.add(new ReportType(3L, 1L, "Receive Money Summary"));
		list.add(new ReportType(4L, 1L, "Send Money Summary"));
		list.add(new ReportType(5L, 1L, "Request fails"));
		list.add(new ReportType(6L, 1L, "Failed transaction"));

		//Default report types for merchant
		// id, userTypeId, name
		list.add(new ReportType(7L, 2L, "Last N transactions"));
		list.add(new ReportType(8L, 2L, "Receive money transaction"));
		list.add(new ReportType(9L, 2L, "Send money transaction"));
		list.add(new ReportType(10L, 2L, "Customer wise transaction"));
		list.add(new ReportType(11L, 2L, "Failed transaction"));
		list.add(new ReportType(25L, 2L, "Commission summary"));
		list.add(new ReportType(26L, 2L, "Disputed transaction"));

		//Default report types for admin
		// id, userTypeId, name
		list.add(new ReportType(12L, 3L, "Last N transactions"));
		list.add(new ReportType(13L, 3L, "Customer wise transaction"));
		list.add(new ReportType(14L, 3L, "Merchant wise transaction"));
		list.add(new ReportType(15L, 3L, "Commission summary"));
		list.add(new ReportType(16L, 3L, "Customer balances"));
		list.add(new ReportType(17L, 3L, "Merchant balances"));
		list.add(new ReportType(18L, 3L, "Customer having over limit"));
		list.add(new ReportType(19L, 3L, "Customer request fails"));
		list.add(new ReportType(20L, 3L, "Failed transaction"));
		list.add(new ReportType(21L, 3L, "Dormant accounts"));
		list.add(new ReportType(22L, 3L, "Disputed transaction"));
		list.add(new ReportType(23L, 3L, "List of account not used in x time"));
		list.add(new ReportType(24L, 3L, "Fraudulent transaction"));
		
		 
		//id,  language,ReportType id, usertypeid, name for customer
		list.add(new ReportTypeLocale(1L, 1L, 1L, 1L, "Last N Transactions - en"));
		list.add(new ReportTypeLocale(2L, 1L, 2L, 1L, "Merchant wise transaction summary - en"));
		list.add(new ReportTypeLocale(3L, 1L, 3L, 1L, "Receive Money Summary - en"));
		list.add(new ReportTypeLocale(4L, 1L, 4L, 1L, "Send Money Summary - en"));
		list.add(new ReportTypeLocale(5L, 1L, 5L, 1L, "Request fails - en"));
		list.add(new ReportTypeLocale(6L, 1L, 6L, 1L, "Failed transaction - en"));

		//id,  language,ReportType id, usertypeid, name for merchant

		list.add(new ReportTypeLocale(7L,  1L, 7L, 2L, "Last N transactions - en"));
		list.add(new ReportTypeLocale(8L,  1L, 8L, 2L, "Receive money transaction - en"));
		list.add(new ReportTypeLocale(9L,  1L, 9L, 2L, "Send money transaction - en"));
		list.add(new ReportTypeLocale(10L, 1L, 10L, 2L, "Customer wise transaction - en"));
		list.add(new ReportTypeLocale(11L, 1L, 11L, 2L, "Failed transaction - en"));
		list.add(new ReportTypeLocale(12L, 1L, 25L, 2L, "Commission summary - en"));
		list.add(new ReportTypeLocale(13L, 1L, 26L, 2L, "Disputed transaction - en"));

		//id,  language,ReportType id, usertypeid, name for admin
		list.add(new ReportTypeLocale(14L, 1L, 12L, 3L, "Last N transactions - en"));
		list.add(new ReportTypeLocale(15L, 1L, 13L, 3L, "Customer wise transaction - en"));
		list.add(new ReportTypeLocale(16L, 1L, 14L, 3L, "Merchant wise transaction - en"));
		list.add(new ReportTypeLocale(17L, 1L, 15L, 3L, "Commission summary - en"));
		list.add(new ReportTypeLocale(18L, 1L, 16L, 3L, "Customer balances - en"));
		list.add(new ReportTypeLocale(19L, 1L, 17L, 3L, "Merchant balances - en"));
		list.add(new ReportTypeLocale(20L, 1L, 18L, 3L, "Customer having over limit - en"));
		list.add(new ReportTypeLocale(21L, 1L, 19L, 3L, "Customer request fails - en"));
		list.add(new ReportTypeLocale(22L, 1L, 20L, 3L, "Failed transaction - en"));
		list.add(new ReportTypeLocale(23L, 1L, 21L, 3L, "Dormant accounts - en"));
		list.add(new ReportTypeLocale(24L, 1L, 22L, 3L, "Disputed transaction - en"));
		list.add(new ReportTypeLocale(25L, 1L, 23L, 3L, "List of account not used in x time - en"));
		list.add(new ReportTypeLocale(26L, 1L, 24L, 3L, "Fraudulent transaction - en"));

		
		//id,  language,ReportType id, usertypeid, name for customer
		list.add(new ReportTypeLocale(27L, 2L, 1L, 1L, "Last N Transactions - jp"));
		list.add(new ReportTypeLocale(28L, 2L, 2L, 1L, "Merchant wise transaction summary - jp"));
		list.add(new ReportTypeLocale(29L, 2L, 3L, 1L, "Receive Money Summary - jp"));
		list.add(new ReportTypeLocale(30L, 2L, 4L, 1L, "Send Money Summary - jp"));
		list.add(new ReportTypeLocale(31L, 2L, 5L, 1L, "Request fails - jp"));
		list.add(new ReportTypeLocale(32L, 2L, 6L, 1L, "Failed transaction - jp"));
		
		//id,  language,ReportType id, usertypeid, name for merchant
		
		list.add(new ReportTypeLocale(33L,  2L, 7L, 2L, "Last N transactions - jp"));
		list.add(new ReportTypeLocale(34L,  2L, 8L, 2L, "Receive money transaction - jp"));
		list.add(new ReportTypeLocale(35L,  2L, 9L, 2L, "Send money transaction - jp"));
		list.add(new ReportTypeLocale(36L, 2L, 10L, 2L, "Customer wise transaction - jp"));
		list.add(new ReportTypeLocale(37L, 2L, 11L, 2L, "Failed transaction - jp"));
		list.add(new ReportTypeLocale(38L, 2L, 25L, 2L, "Commission summary - jp"));
		list.add(new ReportTypeLocale(39L, 2L, 26L, 2L, "Disputed transaction - jp"));
		
		//id,  language,ReportType id, usertypeid, name for admin
		list.add(new ReportTypeLocale(40L, 2L, 12L, 3L, "Last N transactions - jp"));
		list.add(new ReportTypeLocale(41L, 2L, 13L, 3L, "Customer wise transaction - jp"));
		list.add(new ReportTypeLocale(42L, 2L, 14L, 3L, "Merchant wise transaction - jp"));
		list.add(new ReportTypeLocale(43L, 2L, 15L, 3L, "Commission summary - jp"));
		list.add(new ReportTypeLocale(44L, 2L, 16L, 3L, "Customer balances - jp"));
		list.add(new ReportTypeLocale(45L, 2L, 17L, 3L, "Merchant balances - jp"));
		list.add(new ReportTypeLocale(46L, 2L, 18L, 3L, "Customer having over limit - jp"));
		list.add(new ReportTypeLocale(47L, 2L, 19L, 3L, "Customer request fails - jp"));
		list.add(new ReportTypeLocale(48L, 2L, 20L, 3L, "Failed transaction - jp"));
		list.add(new ReportTypeLocale(49L, 2L, 21L, 3L, "Dormant accounts - jp"));
		list.add(new ReportTypeLocale(50L, 2L, 22L, 3L, "Disputed transaction - jp"));
		list.add(new ReportTypeLocale(51L, 2L, 23L, 3L, "List of account not used in x time - jp"));
		list.add(new ReportTypeLocale(52L, 2L, 24L, 3L, "Fraudulent transaction - jp"));

		//id,  language,ReportType id, usertypeid, name for customer
		list.add(new ReportTypeLocale(53L, 3L, 1L, 1L, "Last N Transactions - th"));
		list.add(new ReportTypeLocale(54L, 3L, 2L, 1L, "Merchant wise transaction summary - th"));
		list.add(new ReportTypeLocale(55L, 3L, 3L, 1L, "Receive Money Summary - th"));
		list.add(new ReportTypeLocale(56L, 3L, 4L, 1L, "Send Money Summary - th"));
		list.add(new ReportTypeLocale(57L, 3L, 5L, 1L, "Request fails - th"));
		list.add(new ReportTypeLocale(58L, 3L, 6L, 1L, "Failed transaction - th"));
		
		//id,  language,ReportType id, usertypeid, name for merchant
		
		list.add(new ReportTypeLocale(59L,  3L, 7L, 2L, "Last N transactions - th"));
		list.add(new ReportTypeLocale(60L,  3L, 8L, 2L, "Receive money transaction - th"));
		list.add(new ReportTypeLocale(61L,  3L, 9L, 2L, "Send money transaction - th"));
		list.add(new ReportTypeLocale(62L, 3L, 10L, 2L, "Customer wise transaction - th"));
		list.add(new ReportTypeLocale(63L, 3L, 11L, 2L, "Failed transaction - th"));
		list.add(new ReportTypeLocale(64L, 3L, 25L, 2L, "Commission summary - th"));
		list.add(new ReportTypeLocale(65L, 3L, 26L, 2L, "Disputed transaction - th"));
		
		
		//id,  language,ReportType id, usertypeid, name for admin
		list.add(new ReportTypeLocale(66L, 3L, 12L, 3L, "Last N transactions - th"));
		list.add(new ReportTypeLocale(67L, 3L, 13L, 3L, "Customer wise transaction - th"));
		list.add(new ReportTypeLocale(68L, 3L, 14L, 3L, "Merchant wise transaction - th"));
		list.add(new ReportTypeLocale(69L, 3L, 15L, 3L, "Commission summary - th"));
		list.add(new ReportTypeLocale(70L, 3L, 16L, 3L, "Customer balances - th"));
		list.add(new ReportTypeLocale(71L, 3L, 17L, 3L, "Merchant balances - th"));
		list.add(new ReportTypeLocale(72L, 3L, 18L, 3L, "Customer having over limit - th"));
		list.add(new ReportTypeLocale(73L, 3L, 19L, 3L, "Customer request fails - th"));
		list.add(new ReportTypeLocale(74L, 3L, 20L, 3L, "Failed transaction - th"));
		list.add(new ReportTypeLocale(75L, 3L, 21L, 3L, "Dormant accounts - th"));
		list.add(new ReportTypeLocale(76L, 3L, 22L, 3L, "Disputed transaction - th"));
		list.add(new ReportTypeLocale(77L, 3L, 23L, 3L, "List of account not used in x time - th"));
		list.add(new ReportTypeLocale(78L, 3L, 24L, 3L, "Fraudulent transaction - th"));
		
		return list;
		
		}
	
	/**
	 * @return list of Dispute Type entities
	 * and Dispute Type  entities in specific language 
	 */
	public static List<Object> getDisputeTypeData() {

		List<Object> list = new ArrayList<Object>();

		//Default Dispute Type
		list.add(new DisputeType(1L, "Refund"));
		list.add(new DisputeType(2L, "Chargeback"));

		//Dispute Type in Engilish
		list.add(new DisputeTypeLocale(1L, 1L, 1L, "Refund - en"));
		list.add(new DisputeTypeLocale(2L, 1L, 2L, "Chargeback - en"));

		//Dispute Type in Japan
		list.add(new DisputeTypeLocale(3L, 2L, 1L, "Refund - jp"));
		list.add(new DisputeTypeLocale(4L, 2L, 2L, "Chargeback - jp"));

		//Dispute Type in Thai
		list.add(new DisputeTypeLocale(5L, 3L, 1L, "Refund - th"));
		list.add(new DisputeTypeLocale(6L, 3L, 2L, "Chargeback - th"));

		return list;
	}

	/**
	 * @return list of Dispute Type entities
	 * and Dispute Type  entities in specific language 
	 */
	public static List<Object> getAccountClosureStatusData() {
		
		List<Object> list = new ArrayList<Object>();
		
		//Default Dispute Type
		list.add(new AccountClosureStatus(1L, "Pending"));
		list.add(new AccountClosureStatus(2L, "Approval"));
		list.add(new AccountClosureStatus(3L, "Reject"));
		list.add(new AccountClosureStatus(4L, "Closed"));
		
		//AccountClosureStatus in Engilish
		list.add(new AccountClosureStatusLocale(1L, 1L, 1L, "Pending - en"));
		list.add(new AccountClosureStatusLocale(2L, 1L, 2L, "Approval - en"));
		list.add(new AccountClosureStatusLocale(3L, 1L, 3L, "Reject - en"));
		list.add(new AccountClosureStatusLocale(4L, 1L, 4L, "Closed - en"));
		
		//AccountClosureStatus in Japan
		list.add(new AccountClosureStatusLocale(5L, 2L, 1L, "Pending - jp"));
		list.add(new AccountClosureStatusLocale(6L, 2L, 2L, "Approval - jp"));
		list.add(new AccountClosureStatusLocale(7L, 2L, 3L, "Reject - jp"));
		list.add(new AccountClosureStatusLocale(8L, 2L, 4L, "Closed - jp"));
		
		//AccountClosureStatus in Thai
		list.add(new AccountClosureStatusLocale(9L, 3L, 1L, "Pending - th"));
		list.add(new AccountClosureStatusLocale(10L, 3L, 2L, "Approval - th"));
		list.add(new AccountClosureStatusLocale(11L, 3L, 3L, "Reject - th"));
		list.add(new AccountClosureStatusLocale(12L, 3L, 4L, "Closed - th"));
		
		return list;
	}


	/**
	 * @return list of Dispute status entities
	 * and Dispute status  entities in specific language 
	 */
	public static List<Object> getDisputeStatusData() {
		
		List<Object> list = new ArrayList<Object>();
		
		//Default Dispute Status
		list.add(new DisputeStatus(1L, "Pending"));
		list.add(new DisputeStatus(2L, "Admin rejected"));
		list.add(new DisputeStatus(3L, "Merchant to pay"));
		list.add(new DisputeStatus(4L, "Approved"));
		list.add(new DisputeStatus(5L, "Merchant rejected"));
		list.add(new DisputeStatus(6L, "Force withdrawal"));
		
		//Dispute Status in Engilish
		list.add(new DisputeStatusLocale(1L, 1L, 1L, "Pending - en"));
		list.add(new DisputeStatusLocale(2L, 1L, 2L, "Admin rejected - en"));
		list.add(new DisputeStatusLocale(3L, 1L, 3L, "Merchant to pay - en"));
		list.add(new DisputeStatusLocale(4L, 1L, 4L, "Approved - en"));
		list.add(new DisputeStatusLocale(5L, 1L, 5L, "Merchant rejected - en"));
		list.add(new DisputeStatusLocale(6L, 1L, 6L, "Force withdrawal - en"));
		
		//Dispute Status in Japan
		list.add(new DisputeStatusLocale(7L, 2L, 1L, "Pending - jp"));
		list.add(new DisputeStatusLocale(8L, 2L, 2L, "Admin rejected - jp"));
		list.add(new DisputeStatusLocale(9L, 2L, 3L, "Merchant to pay - jp"));
		list.add(new DisputeStatusLocale(10L, 2L, 4L, "Approved - jp"));
		list.add(new DisputeStatusLocale(11L, 2L, 5L, "Merchant rejected - jp"));
		list.add(new DisputeStatusLocale(12L, 2L, 6L, "Force withdrawal - jp"));
		
		//Dispute Status in Thai
		list.add(new DisputeStatusLocale(13L, 3L, 1L, "Pending - th"));
		list.add(new DisputeStatusLocale(14L, 3L, 2L, "Admin rejected - th"));
		list.add(new DisputeStatusLocale(15L, 3L, 3L, "Merchant to pay - th"));
		list.add(new DisputeStatusLocale(16L, 3L, 4L, "Approved - th"));
		list.add(new DisputeStatusLocale(17L, 3L, 5L, "Merchant rejected - th"));
		list.add(new DisputeStatusLocale(18L, 3L, 6L, "Force withdrawal - th"));
		
		return list;
	}
	public static List<Object> getQueryOrFeedbackData(){
		
		List<Object> list = new ArrayList<Object>();
		
		//Default QueryOrFeedback
		list.add(new QueryOrFeedback(1L, "Query"));
		list.add(new QueryOrFeedback(2L, "Feedback"));
		
		//Default QueryOrFeedback in English
		list.add(new QueryOrFeedbackLocale(1L, 1L , 1L, "Query - en"));
		list.add(new QueryOrFeedbackLocale(2L, 1L , 2L, "Feedback - en"));
		
		//Default QueryOrFeedback in Japan
		list.add(new QueryOrFeedbackLocale(3L, 2L , 1L, "Query - jp"));
		list.add(new QueryOrFeedbackLocale(4L, 2L , 2L, "Feedback - jp"));
		
		//Default QueryOrFeedback in Thai
		list.add(new QueryOrFeedbackLocale(5L, 3L , 1L, "Query - th"));
		list.add(new QueryOrFeedbackLocale(6L, 3L , 2L, "Feedback - th"));
		
		return list;
	}

	
	/**
	 * @return list of default paymentmessages
	 * 
	 */
	public static List<Object> getPaymentMessagesData() {

		List<Object> list = new ArrayList<Object>();

		//Default transaction status name
		list.add(new PaymentMessage(1L, "E001", "authentication.failure"));
		list.add(new PaymentMessage(2L, "E002", "insufficiant.balance"));
		list.add(new PaymentMessage(3L, "E003", "merchant.blocked"));
		list.add(new PaymentMessage(4L, "E004", "customer.blocked"));
		list.add(new PaymentMessage(5L, "E005", "fraudcheck.failed"));
		list.add(new PaymentMessage(6L, "E006", "txn.failed"));
		list.add(new PaymentMessage(7L, "E007", "unknown.error"));
		list.add(new PaymentMessage(8L, "E008", "customer.not.found"));
		list.add(new PaymentMessage(9L, "E009", "merchant.not.found"));
		list.add(new PaymentMessage(10L, "E010", "invalid.inputs"));
		list.add(new PaymentMessage(11L, "E011", "invalid.input.data"));
		list.add(new PaymentMessage(12L, "E012", "currency.not.support"));
		list.add(new PaymentMessage(13L, "E013", "user.cancel.action"));
		list.add(new PaymentMessage(14L, "E014", "customer.locked"));
		list.add(new PaymentMessage(15L, "E015", "user.locked.exced.failure.limit"));
		list.add(new PaymentMessage(16L, "E016", "merchant.code.not.matching"));
		list.add(new PaymentMessage(17L, "E017", "merchant.hand.shake.not.matches"));
		list.add(new PaymentMessage(18L,"E018","merchant.account.rej.administrator" ));
		list.add(new PaymentMessage(19L,"E019", "merchant.locked"));
		list.add(new PaymentMessage(20L,"E020","merchant.account.deleted"));
		list.add(new PaymentMessage(21L,"E021","customer.account.pendind.state"));
		list.add(new PaymentMessage(22L,"E022","customer.account.rej.administrator"));
		list.add(new PaymentMessage(23L,"E023","customer.account.deleted"));
		list.add(new PaymentMessage(24L,"E024","customer.account.deactive"));
		list.add(new PaymentMessage(25L,"E025","invalid.amount.enter"));
		
	return list;

}
	public static List<Object> getErrorCodes(){
		List<Object> list = new ArrayList<Object>();

		//Default transaction status name
		list.add(new ErrorCode(1L, "M0001", "empty.data"));
		list.add(new ErrorCode(2L, "M0002", "user.does.not.exist"));
		list.add(new ErrorCode(3L, "M0003", "failed.to.logout.from.mobile.wallet"));
		list.add(new ErrorCode(4L, "M0004", "login.error.invalid.user"));
		list.add(new ErrorCode(5L, "M0005", "login.email.varification.not.done"));
		list.add(new ErrorCode(6L, "M0006", "login.user.blocked"));
		list.add(new ErrorCode(7L, "M0007", "login.account.deleted"));
		list.add(new ErrorCode(8L, "M0008", "login.password.not.matched"));
		list.add(new ErrorCode(9L, "M0009", "login.email.not.matched"));
		list.add(new ErrorCode(10L, "M0010", "login.account.rejected"));
		list.add(new ErrorCode(11L, "M0011", "account.allready.login"));
		list.add(new ErrorCode(12L, "M0012", "login.account.deactive"));
		list.add(new ErrorCode(13L, "M0013", "login.email.password.failed"));
		
		
		/*Add cards*/
		list.add(new ErrorCode(14L, "M0014", "joint.card.accounts.exceeded"));
		list.add(new ErrorCode(15L, "M0015", "joint.card.account.existwithauthid"));
		list.add(new ErrorCode(16L, "M0016", "duplicate.card.account.errmsg"));
		list.add(new ErrorCode(17L, "M0017", "card.fraud.check.failed"));
		list.add(new ErrorCode(18L, "M0018", "get.account.details.errmsg"));
		list.add(new ErrorCode(19L, "M0019", "pg.service.is.not.establish"));
		list.add(new ErrorCode(20L, "M0020", "communication.with.payment.system.timed.out"));
		list.add(new ErrorCode(21L, "M0021", "card.save.errmsg"));
		
		/*View wallet balance*/
		list.add(new ErrorCode(22L, "M0022", "unable.retrive.wallet.balance.list"));
		list.add(new ErrorCode(23L, "M0023", "no.trnasaction.record.found"));
		
		/*Login and logout*/
		list.add(new ErrorCode(24L, "M0024", "msisdn.number.is.empty"));
		list.add(new ErrorCode(25L, "M0025", "sim.number.is.empty"));
		list.add(new ErrorCode(26L, "M0026", "imei.number.is.empty"));
		list.add(new ErrorCode(27L, "M0027", "user.mobile.doesnot.matches.reg.number"));
		list.add(new ErrorCode(28L, "M0028", "unable.to.registered.your.mobile.wallet"));
		
		list.add(new ErrorCode(29L, "M0029", "successfully.logout"));
		
		/*edit and verify cards */
		list.add(new ErrorCode(30L, "M0030", "no.records.found"));
		list.add(new ErrorCode(31L, "M0031", "card.verification.faild"));
		list.add(new ErrorCode(32L, "M0032", "account.faild.to.retrive"));
		list.add(new ErrorCode(33L, "M0033", "erro.msg.wrong.card.info"));
		list.add(new ErrorCode(34L, "M0034", "edit.card.errmsg"));
				
		// Added for Transaction
		list.add(new ErrorCode(35L, "M0035", "self.transfer.failed"));
		list.add(new ErrorCode(36L, "M0036","userwallet.nothaving.enoughbalance"));
		list.add(new ErrorCode(37L, "M0037","error.msg.over.limit.threshold.amount"));
		list.add(new ErrorCode(38L, "M0038", "user.does.not.have.wallets"));
		list.add(new ErrorCode(39L, "M0039","not.a.authorized.user.to.transfer.money"));
		list.add(new ErrorCode(40L, "M0040","receivemoney.emailId.errmsg.exist"));
		list.add(new ErrorCode(41L, "M0041","sendmoney.customer.emailid.notregistered.errmsg"));
		list.add(new ErrorCode(42L, "M0042","sendmoney.merchant.emailid.notregisterd.errmsg"));
		list.add(new ErrorCode(43L, "M0043","self.transferred.transaction.success"));
		
		/*P2P Transaction*/
		list.add(new ErrorCode(44L, "M0044","error.occured.for.getting.wallets"));
		list.add(new ErrorCode(45L, "M0045", "unknown.error"));
		list.add(new ErrorCode(46L, "M0046", "user.deleted"));
		list.add(new ErrorCode(47L, "M0047", "user.locked"));
		list.add(new ErrorCode(48L, "M0048", "user.rejected"));
		list.add(new ErrorCode(49L, "M0049", "user.inactive"));
		list.add(new ErrorCode(50L, "M0050", "user.notapproved"));
		list.add(new ErrorCode(51L, "M0051","sendmoney.customer.emailid.notregistered.errmsg"));
		list.add(new ErrorCode(52L, "M0052","sendmoney.user.email.exists.errmsg"));
		list.add(new ErrorCode(53L, "M0053", "transaction.failed"));
		list.add(new ErrorCode(54L, "M0054", "transaction.success"));
		list.add(new ErrorCode(55L, "M0055","sendmoney.merchant.emailid.notregisterd.errmsg"));
		list.add(new ErrorCode(56L, "M0056", "email.merchant.notregistered.error"));
		list.add(new ErrorCode(57L, "M0057", "error.msg.already.default.account"));
		
		list.add(new ErrorCode(58L, "M0058", "error.msg.verified.account.required"));
		list.add(new ErrorCode(59L, "M0059", "account.setdefault.error.message"));
		list.add(new ErrorCode(60L, "M0060", "error.msg.unable.delete.default.account"));
		list.add(new ErrorCode(61L, "M0061", "error.msg.not.verified.and.verified.account.required"));
		list.add(new ErrorCode(62L, "M0062", "account.delete.error.message"));
		list.add(new ErrorCode(63L, "M0063", "card.register.success.msg"));
		list.add(new ErrorCode(64L, "M0064", "existing.account.added.again"));
		list.add(new ErrorCode(65L, "M0065", "card.updated.success.msg"));
		list.add(new ErrorCode(66L, "M0066", "account.delete.success.message"));
		list.add(new ErrorCode(67L, "M0067", "account.setdefault.success.message"));
		list.add(new ErrorCode(68L, "M0068", "accounts.size.exceeds.error.message"));
		list.add(new ErrorCode(69L, "M0069", "accounts.size.unknown.error.message"));
		list.add(new ErrorCode(70L, "M0070", "not.verified.and.pending.account.required"));
		list.add(new ErrorCode(71L, "M0071", "not.verified.and.verified.account.required"));
		list.add(new ErrorCode(72L, "M0072", "not.autherised.transaction.errmsg"));
		list.add(new ErrorCode(73L, "M0073", "card.verified.successfully"));

		list.add(new ErrorCode(74L, "M0074","failedto.retrieve.account.details.reload.money.errmsg"));
		list.add(new ErrorCode(75L, "M0075","threshold.doest.support.for.reload.fund"));
		list.add(new ErrorCode(76L, "M0076","money.reloaded.successfully"));
		list.add(new ErrorCode(77L, "M0077","reload.failed.message"));
		list.add(new ErrorCode(78L, "M0078","unknown.pg.response"));
		list.add(new ErrorCode(79L, "M0079","error.msg.unknown.pg.res"));
		list.add(new ErrorCode(80L, "M0080","invalid.account.id"));
		list.add(new ErrorCode(81L, "M0081","invalid.amount.format"));
		list.add(new ErrorCode(82L, "M0082","account.type.is.not.card"));
		list.add(new ErrorCode(83L, "M0083","account.is.not.varified"));
		list.add(new ErrorCode(84L, "M0084","invalid.cvv.formate"));
		
		list.add(new ErrorCode(85L, "M0085","user.not.yet.login"));
		list.add(new ErrorCode(86L, "M0086","error.msg.invalid.session"));
		list.add(new ErrorCode(87L, "M0087","error.msg.invalid.user.access"));
		list.add(new ErrorCode(88L, "M0088","user.not.register.as.mobile.wallet"));
		list.add(new ErrorCode(89L, "M0089","wrong.account.details"));
		list.add(new ErrorCode(90L, "M0090","error.msg.unable.edit.default.account"));
		list.add(new ErrorCode(91L, "M0091","account.has.already.deleted"));
		list.add(new ErrorCode(92L, "M0092","duplicate.mobile.registration"));
		
		list.add(new ErrorCode(93L, "M0093","email.should.not.be.empty"));		
		list.add(new ErrorCode(94L, "M0094","usertype.should.not.be.empty"));		
		list.add(new ErrorCode(95L, "M0095","password.should.not.be.empty"));
		list.add(new ErrorCode(96L, "M0096","from.towallet.shouldnotbe.empty"));
		list.add(new ErrorCode(97L, "M0097","from.towallet.shouldnotbe.emptystring"));
		list.add(new ErrorCode(98L, "M0098","from.towallet.shouldnotbe.same"));
		list.add(new ErrorCode(99L, "M0099","from.towallet.couldnot.support"));
		list.add(new ErrorCode(100L, "M00100","amount.shouldnotbe.empty"));
		list.add(new ErrorCode(101L, "M00101","not.a.valid.destination.type"));
		list.add(new ErrorCode(102L, "M00102","user.not.have.balance.errmsg"));
		list.add(new ErrorCode(103L, "M00103","unable.to.retrieve.listofcurrency"));
		list.add(new ErrorCode(104L, "M00104","unable.to.retrieve.destinationtypes"));
		list.add(new ErrorCode(105L, "M00105","unable.to.retrieve.typeofcards"));
		list.add(new ErrorCode(106L, "M00106","user.authentication.success"));
		list.add(new ErrorCode(107L, "M00107","records.found"));
		list.add(new ErrorCode(108L, "M00108","self.transfer.conformation.required"));
		list.add(new ErrorCode(109L, "M00109","transaction.conformation.required"));
		list.add(new ErrorCode(110L, "M00110","successfully.registered.with.device"));
		list.add(new ErrorCode(111L, "M00111","already.registor.as.mobile.wallet"));
		list.add(new ErrorCode(112L, "M00112","successfully.mpin.generated"));
		list.add(new ErrorCode(113L, "M00113","unable.to.generate.mpin"));
		list.add(new ErrorCode(114L, "M00114","invalid.mpin.format"));
		list.add(new ErrorCode(115L, "M00115","invalid.mpin"));
		list.add(new ErrorCode(116L, "M00116", "mobile.user.block"));
		list.add(new ErrorCode(117L, "M00117", "mpin.match.fail"));
		list.add(new ErrorCode(118L, "M00118", "successfully.mpin.changed"));
		list.add(new ErrorCode(119L, "M00119", "unable.to.deactivate.your.mobile.wallet"));
		list.add(new ErrorCode(120L, "M00120", "successfully.deactivated.with.device"));
		list.add(new ErrorCode(121L, "M00121", "unable.to.retrieve.mpin.hint.questions"));
		list.add(new ErrorCode(122L, "M00122", "user.requested.from.different.device.or.sim"));
		list.add(new ErrorCode(123L, "M00123", "forgot.mpin.fail"));
		list.add(new ErrorCode(124L, "M00124", "forgot.mpin.success.and.sent.to.email"));
		list.add(new ErrorCode(125L, "M00125", "hint.question.one.not.matching"));
		list.add(new ErrorCode(126L, "M00126", "hint.answ.one.not.matching"));
		list.add(new ErrorCode(127L, "M00127", "mobile.wallet.account.is.not.active"));
		list.add(new ErrorCode(128L, "M00128", "oldmpin.newmpin.shouldnot.be.same"));
		list.add(new ErrorCode(129L, "M00129", "mobilewallet.account.exists.generate.mpin"));
		list.add(new ErrorCode(130L, "M00130", "unable.to.process.msg"));
		list.add(new ErrorCode(131L, "M00131", "query.sent.success"));
		list.add(new ErrorCode(132L, "M00132", "query.sent.failed"));
		list.add(new ErrorCode(133L, "M00133", "query.type.not.support"));
		list.add(new ErrorCode(133L, "M00162", "old.mpin.incorrect"));
		
		return list;
	}

	public static List<Object> getChannelTypeData() {

		List<Object> list = new ArrayList<Object>();

		//Default Channel Type
		list.add(new ChannelType(1L, "Mobile"));
		list.add(new ChannelType(2L, "Web"));

		//Channel Type in Engilish
		list.add(new ChannelTypeLocale(1L, 1L, 1L, "Mobile - en"));
		list.add(new ChannelTypeLocale(2L, 1L, 2L, "Web - en"));

		//Channel Type in Japan
		list.add(new ChannelTypeLocale(3L, 2L, 1L, "Mobile - jp"));
		list.add(new ChannelTypeLocale(4L, 2L, 2L, "Web - jp"));

		//Channel Type in Thai
		list.add(new ChannelTypeLocale(5L, 3L, 1L, "Mobile - th"));
		list.add(new ChannelTypeLocale(6L, 3L, 2L, "Web - th"));

		return list;
	}
	
	public static List<Object> getPGCodes(){
		List<Object> list = new ArrayList<Object>();
		list.add(new PGCode(1L,"pgresponse.1001","There was a technical issue with your payment which is under investigation"));
		list.add(new PGCode(2L,"pgresponse.1002","Please try again later"));
		list.add(new PGCode(3L,"pgresponse.1003","There has been a problem with your payment, please try again"));
		list.add(new PGCode(4L,"pgresponse.1004","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(5L,"pgresponse.1005","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(6L,"pgresponse.1006","Your payment method is not permitted, please try another form of payment"));
		list.add(new PGCode(7L,"pgresponse.1007","There was a technical issue with your payment which is under investigation"));
		list.add(new PGCode(8L,"pgresponse.1009","Your payment was unsuccessful, please check your details"));
		list.add(new PGCode(9L,"pgresponse.1011","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(10L,"pgresponse.1012","Your card has expired, please try another form of payment or contact your bank"));
		list.add(new PGCode(11L,"pgresponse.1013","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(12L,"pgresponse.1014","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(13L,"pgresponse.1015","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(14L,"pgresponse.1016","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(15L,"pgresponse.1017","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(16L,"pgresponse.1018","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(17L,"pgresponse.1020","Your payment was unsuccessful, please check your details"));
		list.add(new PGCode(18L,"pgresponse.1021","Please try again later"));
		list.add(new PGCode(19L,"pgresponse.1022","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(20L,"pgresponse.1024","There was a technical issue with your payment which is under investigation"));
		list.add(new PGCode(21L,"pgresponse.1025","Request does not match original transaction"));
		list.add(new PGCode(22L,"pgresponse.1026","Your payment was unsuccessful, please try another form of payment or contact your bank"));
		list.add(new PGCode(23L,"pgresponse.1028","There was a technical issue with your payment which is under investigation"));
		list.add(new PGCode(24L,"pgresponse.1029","There was a technical issue with your payment which is under investigation"));
		list.add(new PGCode(25L,"pgresponse.1031","There was a technical issue with your payment which is under investigation"));
		list.add(new PGCode(26L,"pgresponse.1032","There was a technical issue with your payment which is under investigation"));
		list.add(new PGCode(27L,"pgresponse.1033","The amount you have tried is above the threshold, please select a lower amount"));
		list.add(new PGCode(28L,"pgresponse.1034","Your payment was unsuccessful, please check your details"));
		list.add(new PGCode(29L,"pgresponse.1035","Your payment was unsuccessful, please check your details"));
		list.add(new PGCode(30L,"pgresponse.1036","Your payment was unsuccessful, please check your details"));
		list.add(new PGCode(31L,"pgresponse.1037","Your payment was unsuccessful, please check your details"));
		list.add(new PGCode(32L,"pgresponse.1038","Your payment was unsuccessful, please check your details"));
		list.add(new PGCode(33L,"pgresponse.1040","Please try again later"));
		list.add(new PGCode(34L,"pgresponse.1043","Your payment was unsuccessful, please check your details"));
		list.add(new PGCode(35L,"pgresponse.1044","There was a technical issue with your payment which is under investigation"));
		list.add(new PGCode(36L,"pgresponse.1047","There was a technical issue with your payment which is under investigation"));
		list.add(new PGCode(37L,"pgresponse.1048","There was a technical issue with your payment which is under investigation"));
		list.add(new PGCode(38L,"pgresponse.1057","Your payment was unsuccessful, please check your details"));
		list.add(new PGCode(39L,"pgresponse.1058","Your payment was unsuccessful, please check your details"));
		return list;
	}

}


