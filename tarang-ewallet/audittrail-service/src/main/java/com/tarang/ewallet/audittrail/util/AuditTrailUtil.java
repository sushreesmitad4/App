package com.tarang.ewallet.audittrail.util;

import java.util.ArrayList;
import java.util.List;

import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.dto.AdminUserDto;
import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.PreferencesDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AuditField;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.model.Tax;
import com.tarang.ewallet.model.UserIP;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.WalletThreshold;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.FeeTaxConstants;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author vasanthar
 *
 */
public class AuditTrailUtil extends AuditTrailFieldConstrain{
	
	public static List<AuditField> updateAuditCustomer(CustomerDto oldCustomerDto, CustomerDto newCustomerDto)throws WalletException{
		
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		
		if(!oldCustomerDto.getFirstName().equalsIgnoreCase(newCustomerDto.getFirstName())){			
			auditFieldList.add(new AuditField(FIRST_NAME, oldCustomerDto.getFirstName(), newCustomerDto.getFirstName()));
		}
		if(!oldCustomerDto.getLastName().equalsIgnoreCase(newCustomerDto.getLastName())){
			auditFieldList.add(new AuditField(LAST_NAME, oldCustomerDto.getLastName(), newCustomerDto.getLastName()));
		}
		if(!oldCustomerDto.getTitle().equals(newCustomerDto.getTitle())){
			auditFieldList.add(new AuditField(TITLE, String.valueOf(oldCustomerDto.getTitle()), String.valueOf(newCustomerDto.getTitle())));
		}
		if(!oldCustomerDto.getCountry().equals(newCustomerDto.getCountry())){
			auditFieldList.add(new AuditField(COUNTRY, String.valueOf(oldCustomerDto.getCountry()), String.valueOf(newCustomerDto.getCountry())));
		}		
		if(!oldCustomerDto.getAddrOne().equalsIgnoreCase(newCustomerDto.getAddrOne())){
			auditFieldList.add(new AuditField(ADDRESS_ONE, oldCustomerDto.getAddrOne(), newCustomerDto.getAddrOne()));
		}		
		if(oldCustomerDto.getAddrTwo() != null && !oldCustomerDto.getAddrTwo().equalsIgnoreCase(newCustomerDto.getAddrTwo())){
			auditFieldList.add(new AuditField(ADDRESS_TWO, oldCustomerDto.getAddrTwo(), newCustomerDto.getAddrTwo()));
		}
		if(!DateConvertion.dateToString(oldCustomerDto.getDateOfBirth()).equals(DateConvertion.dateToString(newCustomerDto.getDateOfBirth()))){
			auditFieldList.add(new AuditField(DATE_OF_BIRTH, String.valueOf(oldCustomerDto.getDateOfBirth()), String.valueOf(newCustomerDto.getDateOfBirth())));
		}
		if(!oldCustomerDto.getState().equals(newCustomerDto.getState())){
			auditFieldList.add(new AuditField(STATE_OR_REGION, String.valueOf(oldCustomerDto.getState()), String.valueOf(newCustomerDto.getState())));
		}
		if(!oldCustomerDto.getCity().equalsIgnoreCase(newCustomerDto.getCity())){
			auditFieldList.add(new AuditField(CITY, oldCustomerDto.getCity(), newCustomerDto.getCity()));
		}
		if(oldCustomerDto.getPostelCode() != null && !oldCustomerDto.getPostelCode().equalsIgnoreCase(newCustomerDto.getPostelCode())){
			auditFieldList.add(new AuditField(POSTAL_CODE, oldCustomerDto.getPostelCode(), newCustomerDto.getPostelCode()));
		}
		if(!oldCustomerDto.getPhoneCode().equalsIgnoreCase(newCustomerDto.getPhoneCode())){
			auditFieldList.add(new AuditField(PHONE_CODE, oldCustomerDto.getPhoneCode(), newCustomerDto.getPhoneCode()));
		}
		if(!oldCustomerDto.getPhoneNo().equalsIgnoreCase(newCustomerDto.getPhoneNo())){
			auditFieldList.add(new AuditField(PHONE_NUMBER, oldCustomerDto.getPhoneNo(), newCustomerDto.getPhoneNo()));
		}
		if(!oldCustomerDto.getHintQuestion1().equals(newCustomerDto.getHintQuestion1())){
			auditFieldList.add(new AuditField(QUESTION, String.valueOf(oldCustomerDto.getHintQuestion1()), String.valueOf(newCustomerDto.getHintQuestion1())));
		}
		if(!oldCustomerDto.getAnswer1().equalsIgnoreCase(newCustomerDto.getAnswer1())){
			auditFieldList.add(new AuditField(ANSWER, oldCustomerDto.getAnswer1(), newCustomerDto.getAnswer1()));
		}
		if(!oldCustomerDto.getPassword().equalsIgnoreCase(newCustomerDto.getPassword())){
			auditFieldList.add(new AuditField(PASSWORD, oldCustomerDto.getPassword(), newCustomerDto.getPassword()));
		}
		
		return auditFieldList;
	}
	
	public static List<AuditField> updateAuditMerchant(MerchantDto oldMerchantDto,MerchantDto newMerchantDto)throws WalletException{
		
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		
		if(!oldMerchantDto.getFirstName().equalsIgnoreCase(newMerchantDto.getFirstName())){			
			auditFieldList.add(new AuditField(FIRST_NAME, oldMerchantDto.getFirstName(), newMerchantDto.getFirstName()));
		}
		if(!oldMerchantDto.getLastName().equalsIgnoreCase(newMerchantDto.getLastName())){
			auditFieldList.add(new AuditField(LAST_NAME, oldMerchantDto.getLastName(), newMerchantDto.getLastName()));
		}
		if(!oldMerchantDto.getCountryBI().equals(newMerchantDto.getCountryBI())){
			auditFieldList.add(new AuditField(COUNTRY, String.valueOf(oldMerchantDto.getCountryBI()), String.valueOf(newMerchantDto.getCountryBI())));
		}
		if(!oldMerchantDto.getCountryBO().equals(newMerchantDto.getCountryBO())){
			auditFieldList.add(new AuditField(COUNTRY, String.valueOf(oldMerchantDto.getCountryBO()), String.valueOf(newMerchantDto.getCountryBO())));
		}
		if(!oldMerchantDto.getAddress1BI().equalsIgnoreCase(newMerchantDto.getAddress1BI())){
			auditFieldList.add(new AuditField(ADDRESS_ONE, oldMerchantDto.getAddress1BI(), newMerchantDto.getAddress1BI()));
		}		
		if(!oldMerchantDto.getAddress1BO().equalsIgnoreCase(newMerchantDto.getAddress1BO())){
			auditFieldList.add(new AuditField(ADDRESS_TWO, oldMerchantDto.getAddress1BO(), newMerchantDto.getAddress1BO()));
		}
		if(oldMerchantDto.getAddress2BI() != null && !oldMerchantDto.getAddress2BI().equalsIgnoreCase(newMerchantDto.getAddress2BI())){
			auditFieldList.add(new AuditField(ADDRESS_ONE, oldMerchantDto.getAddress2BI(), newMerchantDto.getAddress2BI()));
		}		
		if(oldMerchantDto.getAddress2BO() != null && !oldMerchantDto.getAddress2BO().equalsIgnoreCase(newMerchantDto.getAddress2BO())){
			auditFieldList.add(new AuditField(ADDRESS_TWO, oldMerchantDto.getAddress2BO(), newMerchantDto.getAddress2BO()));
		}
		if(!oldMerchantDto.getStateorRegionBI().equals(newMerchantDto.getStateorRegionBI())){
			auditFieldList.add(new AuditField(STATE_OR_REGION, String.valueOf(oldMerchantDto.getStateorRegionBI()), String.valueOf(newMerchantDto.getStateorRegionBI())));
		}
		if(!oldMerchantDto.getStateOrRegionBO().equals(newMerchantDto.getStateOrRegionBO())){
			auditFieldList.add(new AuditField(STATE_OR_REGION, String.valueOf(oldMerchantDto.getStateOrRegionBO()), String.valueOf(newMerchantDto.getStateOrRegionBO())));
		}
		if(!oldMerchantDto.getCityOrTownBI().equalsIgnoreCase(newMerchantDto.getCityOrTownBI())){
			auditFieldList.add(new AuditField(CITY, oldMerchantDto.getCityOrTownBI(), newMerchantDto.getCityOrTownBI()));
		}
		if(!oldMerchantDto.getCityOrTownBO().equalsIgnoreCase(newMerchantDto.getCityOrTownBO())){
			auditFieldList.add(new AuditField(CITY, oldMerchantDto.getCityOrTownBO(), newMerchantDto.getCityOrTownBO()));
		}
		if(oldMerchantDto.getPostalCodeBI() != null && !oldMerchantDto.getPostalCodeBI().equalsIgnoreCase(newMerchantDto.getPostalCodeBI())){
			auditFieldList.add(new AuditField(POSTAL_CODE, oldMerchantDto.getPostalCodeBI(), newMerchantDto.getPostalCodeBI()));
		}
		if(oldMerchantDto.getPostalCodeBO() != null && !oldMerchantDto.getPostalCodeBO().equalsIgnoreCase(newMerchantDto.getPostalCodeBO())){
			auditFieldList.add(new AuditField(POSTAL_CODE, oldMerchantDto.getPostalCodeBO(), newMerchantDto.getPostalCodeBO()));
		}
		if(oldMerchantDto.getPhoneCountryCode1() != null && !oldMerchantDto.getPhoneCountryCode1().equalsIgnoreCase(newMerchantDto.getPhoneCountryCode1())){
			auditFieldList.add(new AuditField(PHONE_CODE, oldMerchantDto.getPhoneCountryCode1(), newMerchantDto.getPhoneCountryCode1()));
		}
		if(oldMerchantDto.getPhone() != null && !oldMerchantDto.getPhone().equalsIgnoreCase(newMerchantDto.getPhone())){
			auditFieldList.add(new AuditField(PHONE_NUMBER, oldMerchantDto.getPhone(), newMerchantDto.getPhone()));
		}
		if(!oldMerchantDto.getPhoneNumber().equalsIgnoreCase(newMerchantDto.getPhoneNumber())){
			auditFieldList.add(new AuditField(PHONE_NUMBER, oldMerchantDto.getPhoneNumber(), newMerchantDto.getPhoneNumber()));
		}
		
		if(!oldMerchantDto.getQuestion1().equals(newMerchantDto.getQuestion1())){
			auditFieldList.add(new AuditField(QUESTION, String.valueOf(oldMerchantDto.getQuestion1()), String.valueOf(newMerchantDto.getQuestion1())));
		}
		if(!oldMerchantDto.getHint1().equalsIgnoreCase(newMerchantDto.getHint1())){
			auditFieldList.add(new AuditField(ANSWER, oldMerchantDto.getHint1(), newMerchantDto.getHint1()));
		}
		if(!oldMerchantDto.getBusinessCategory().equals(newMerchantDto.getBusinessCategory())){
			auditFieldList.add(new AuditField(CATEGORY, String.valueOf(oldMerchantDto.getBusinessCategory()), String.valueOf(newMerchantDto.getBusinessCategory())));
		}
		if(!oldMerchantDto.getBusinessEstablishedMonth().equalsIgnoreCase(newMerchantDto.getBusinessEstablishedMonth())){
			auditFieldList.add(new AuditField(ESTABLIISHED_MONTH, oldMerchantDto.getBusinessEstablishedMonth(), newMerchantDto.getBusinessEstablishedMonth()));
		}
		if(!oldMerchantDto.getBusinessEstablishedYear().equalsIgnoreCase(newMerchantDto.getBusinessEstablishedYear())){
			auditFieldList.add(new AuditField(ESTABLISHED_YEAR, oldMerchantDto.getBusinessEstablishedYear(), newMerchantDto.getBusinessEstablishedYear()));
		}
		if(!oldMerchantDto.getBusinessLegalname().equalsIgnoreCase(newMerchantDto.getBusinessLegalname())){
			auditFieldList.add(new AuditField(LEGAL_NAME, oldMerchantDto.getBusinessLegalname(), newMerchantDto.getBusinessLegalname()));
		}
		if(!oldMerchantDto.getCurrency().equals(newMerchantDto.getCurrency())){
			auditFieldList.add(new AuditField(CURRENCY, String.valueOf(oldMerchantDto.getCurrency()), String.valueOf(newMerchantDto.getCurrency())));
		}
		if(!oldMerchantDto.getHighestMonthlyVolume().equals(newMerchantDto.getHighestMonthlyVolume())){
			auditFieldList.add(new AuditField(HIGHEST_MONTHLY_VOLUME, String.valueOf(oldMerchantDto.getHighestMonthlyVolume()), String.valueOf(newMerchantDto.getHighestMonthlyVolume())));
		}
		if(!oldMerchantDto.getOwnerType().equals(newMerchantDto.getOwnerType())){
			auditFieldList.add(new AuditField(OWNER_TYPE, String.valueOf(oldMerchantDto.getOwnerType()), String.valueOf(newMerchantDto.getOwnerType())));
		}
		
		if(!oldMerchantDto.getPercentageOfAnnualRevenueFromOnlineSales().equals(newMerchantDto.getPercentageOfAnnualRevenueFromOnlineSales())){
			auditFieldList.add(new AuditField(PERSENTAGE_OF_ANNUAL_REVENUE, String.valueOf(oldMerchantDto.getPercentageOfAnnualRevenueFromOnlineSales()), String.valueOf(newMerchantDto.getPercentageOfAnnualRevenueFromOnlineSales())));
		}
		
		if(!oldMerchantDto.getSubCategory().equals(newMerchantDto.getSubCategory())){
			auditFieldList.add(new AuditField(SUB_CATEGORY, String.valueOf(oldMerchantDto.getSubCategory()), String.valueOf(newMerchantDto.getSubCategory())));
		}
		if(!oldMerchantDto.getWebsite().equalsIgnoreCase(newMerchantDto.getWebsite())){
			auditFieldList.add(new AuditField(WEBSITE, oldMerchantDto.getWebsite(), newMerchantDto.getWebsite()));
		}
		
		if(oldMerchantDto.getAverageTransactionAmount() == null){
			if (newMerchantDto.getAverageTransactionAmount() != null) {
				auditFieldList.add(new AuditField(AVERAGE_TRANSACTION_AMOUNT, String.valueOf(oldMerchantDto.getAverageTransactionAmount()), String.valueOf(newMerchantDto.getAverageTransactionAmount())));
			}
		} else if(!oldMerchantDto.getAverageTransactionAmount().equals(newMerchantDto.getAverageTransactionAmount())){
			auditFieldList.add(new AuditField(AVERAGE_TRANSACTION_AMOUNT, String.valueOf(oldMerchantDto.getAverageTransactionAmount()), String.valueOf(newMerchantDto.getAverageTransactionAmount())));
		} else if(!oldMerchantDto.getEmailCSI().equals(newMerchantDto.getEmailCSI())){
			auditFieldList.add(new AuditField(EMAIL_CSI, oldMerchantDto.getEmailCSI(), newMerchantDto.getEmailCSI()));
		} else if(oldMerchantDto.getCode() != null && !oldMerchantDto.getCode().equals(newMerchantDto.getCode())){
			auditFieldList.add(new AuditField(CODE, oldMerchantDto.getCode(), newMerchantDto.getCode()));
		}
		
		return auditFieldList;
	}
	
	public static List<AuditField> updateAuditAdmin(AdminUserDto oldAdminUserDto,AdminUserDto newAdminUserDto)throws WalletException{
		
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		
		if(!oldAdminUserDto.getFirstName().equalsIgnoreCase(newAdminUserDto.getFirstName())){			
			auditFieldList.add(new AuditField(FIRST_NAME, oldAdminUserDto.getFirstName(), newAdminUserDto.getFirstName()));
		}
		if(!oldAdminUserDto.getLastName().equalsIgnoreCase(newAdminUserDto.getLastName())){
			auditFieldList.add(new AuditField(LAST_NAME, oldAdminUserDto.getLastName(), newAdminUserDto.getLastName()));
		}
		if(!oldAdminUserDto.getCountryId().equals(newAdminUserDto.getCountryId())){
			auditFieldList.add(new AuditField(COUNTRY, String.valueOf(oldAdminUserDto.getCountryId()), String.valueOf(newAdminUserDto.getCountryId())));
		}		
		if(!oldAdminUserDto.getAddressOne().equalsIgnoreCase(newAdminUserDto.getAddressOne())){
			auditFieldList.add(new AuditField(ADDRESS_ONE, oldAdminUserDto.getAddressOne(), newAdminUserDto.getAddressOne()));
		}	
		if(!oldAdminUserDto.getAddressTwo().equalsIgnoreCase(newAdminUserDto.getAddressTwo())){
			auditFieldList.add(new AuditField(ADDRESS_TWO, oldAdminUserDto.getAddressTwo(), newAdminUserDto.getAddressTwo()));
		}
		if(!oldAdminUserDto.getStateId().equals(newAdminUserDto.getStateId())){
			auditFieldList.add(new AuditField(STATE_OR_REGION, String.valueOf(oldAdminUserDto.getStateId()), String.valueOf(newAdminUserDto.getStateId())));
		}
		if(!oldAdminUserDto.getCity().equalsIgnoreCase(newAdminUserDto.getCity())){
			auditFieldList.add(new AuditField(CITY, oldAdminUserDto.getCity(), newAdminUserDto.getCity()));
		}
		if(!oldAdminUserDto.getZipcode().equalsIgnoreCase(newAdminUserDto.getZipcode())){
			auditFieldList.add(new AuditField(POSTAL_CODE, oldAdminUserDto.getZipcode(), newAdminUserDto.getZipcode()));
		}
		if(!oldAdminUserDto.getPhoneCode().equalsIgnoreCase(newAdminUserDto.getPhoneCode())){
			auditFieldList.add(new AuditField(PHONE_CODE, oldAdminUserDto.getPhoneCode(), newAdminUserDto.getPhoneCode()));
		}
		if(!oldAdminUserDto.getPhoneNo().equalsIgnoreCase(newAdminUserDto.getPhoneNo())){
			auditFieldList.add(new AuditField(PHONE_NUMBER, oldAdminUserDto.getPhoneNo(), newAdminUserDto.getPhoneNo()));
		}		
		if(!oldAdminUserDto.getRoleId().equals(newAdminUserDto.getRoleId())){
			auditFieldList.add(new AuditField(ROLE_ID, String.valueOf(oldAdminUserDto.getRoleId()), String.valueOf(newAdminUserDto.getRoleId())));
		}		
		if(!oldAdminUserDto.getRoleName().equalsIgnoreCase(newAdminUserDto.getRoleName())){
			auditFieldList.add(new AuditField(ROLE_NAME,oldAdminUserDto.getRoleName(),newAdminUserDto.getRoleName()));
		}
		return auditFieldList;
	}

	public static List<AuditField> updateAuditRole(Role oldRoleDto, Role newRoleDto)throws WalletException {

		List<AuditField> auditFieldList = new ArrayList<AuditField>();

		if (!oldRoleDto.getName().equalsIgnoreCase(newRoleDto.getName())) {
			auditFieldList.add(new AuditField(ROLE_NAME, oldRoleDto.getName(), newRoleDto.getName()));
		}
		if (!oldRoleDto.getDescription().equalsIgnoreCase(newRoleDto.getDescription())) {
			auditFieldList.add(new AuditField(DESCRIPTION, oldRoleDto.getDescription(), newRoleDto.getDescription()));
		}
		if (!oldRoleDto.getMenuDetails().equals(newRoleDto.getMenuDetails())) {
			auditFieldList.add(new AuditField(MENU_DETAILS, String.valueOf(oldRoleDto.getMenuDetails()), String.valueOf(newRoleDto.getMenuDetails())));
		}

		return auditFieldList;

	}
	
	public static List<AuditField> updateAuditAccount(AccountDto oldAccountDto, AccountDto newAccountDto)throws WalletException {

		List<AuditField> auditFieldList = new ArrayList<AuditField>();

		if (!oldAccountDto.getDefaultAccount().equals(newAccountDto.getDefaultAccount())) {
			auditFieldList.add(new AuditField(DEFAULT_ACCOUNT, String.valueOf(oldAccountDto.getDefaultAccount()), String.valueOf(newAccountDto.getDefaultAccount())));
		}
		if (!oldAccountDto.getAccountHolderName().equalsIgnoreCase(newAccountDto.getAccountHolderName())) {
			auditFieldList.add(new AuditField(ACCOUNT_HOLDER_NAME, oldAccountDto.getAccountHolderName(), newAccountDto.getAccountHolderName()));
		}
		
		if (oldAccountDto.getAccountNumber() == null) {
			if (newAccountDto.getAccountNumber() != null) {
				auditFieldList.add(new AuditField(ACCOUNT_NUMBER, oldAccountDto.getAccountNumber(), newAccountDto.getAccountNumber()));
			}
		} else if (!oldAccountDto.getAccountNumber().equalsIgnoreCase(newAccountDto.getAccountNumber())) {
			auditFieldList.add(new AuditField(ACCOUNT_NUMBER, oldAccountDto.getAccountNumber(), newAccountDto.getAccountNumber()));
		}
		
		if (!oldAccountDto.getAtype().equalsIgnoreCase(newAccountDto.getAtype())) {
			auditFieldList.add(new AuditField(ACCOUNT_TYPE, oldAccountDto.getAtype(), newAccountDto.getAtype()));
		}
		
		if (oldAccountDto.getBankAccountType() == null) {
			if (newAccountDto.getBankAccountType() != null) {
				auditFieldList.add(new AuditField(BANK_ACCOUNT_TYPE, String.valueOf(oldAccountDto.getBankAccountType()), String.valueOf(newAccountDto.getBankAccountType())));
			}
		} else if(!oldAccountDto.getBankAccountType().equals(newAccountDto.getBankAccountType())) {
			auditFieldList.add(new AuditField(BANK_ACCOUNT_TYPE, String.valueOf(oldAccountDto.getBankAccountType()), String.valueOf(newAccountDto.getBankAccountType())));
		}
		
		if(oldAccountDto.getBankAddress() == null){
				if (newAccountDto.getBankAddress() != null) {
					auditFieldList.add(new AuditField(BANK_ADDRESS, oldAccountDto.getBankAddress(), newAccountDto.getBankAddress()));
				}
		}else if (!oldAccountDto.getBankAddress().equalsIgnoreCase(newAccountDto.getBankAddress())) {
			auditFieldList.add(new AuditField(BANK_ADDRESS, oldAccountDto.getBankAddress(), newAccountDto.getBankAddress()));
		}
		
		if(oldAccountDto.getBankName() == null){
			if (newAccountDto.getBankName() != null) {
				auditFieldList.add(new AuditField(BANK_NAME, oldAccountDto.getBankName(), newAccountDto.getBankName()));
			}
		} else if (!oldAccountDto.getBankName().equalsIgnoreCase(newAccountDto.getBankName())) {
			auditFieldList.add(new AuditField(BANK_NAME, oldAccountDto.getBankName(), newAccountDto.getBankName()));
		}	
		
		if(oldAccountDto.getCardExpairyDate() == null){
			if (newAccountDto.getCardExpairyDate() != null) {
				auditFieldList.add(new AuditField(EXPAIRY_DATE, oldAccountDto.getCardExpairyDate(), newAccountDto.getCardExpairyDate()));
			}
		} else if (!oldAccountDto.getCardExpairyDate().equalsIgnoreCase(newAccountDto.getCardExpairyDate())) {
			auditFieldList.add(new AuditField(EXPAIRY_DATE, oldAccountDto.getCardExpairyDate(), newAccountDto.getCardExpairyDate()));
		}
		
		if(oldAccountDto.getCardType() == null){
			if (newAccountDto.getCardType() != null) {
				auditFieldList.add(new AuditField(CARD_TYPE, String.valueOf(oldAccountDto.getCardType()), String.valueOf(newAccountDto.getCardType())));
			}
		} else if (!oldAccountDto.getCardType().equals(newAccountDto.getCardType())) {
			auditFieldList.add(new AuditField(CARD_TYPE, String.valueOf(oldAccountDto.getCardType()), String.valueOf(newAccountDto.getCardType())));
		}
		
		if(oldAccountDto.getCardNumber() == null){
			if (newAccountDto.getCardNumber() != null) {
				auditFieldList.add(new AuditField(CARD_NUMBER, oldAccountDto.getCardNumber(), newAccountDto.getCardNumber()));
			}
		} else if (!oldAccountDto.getCardNumber().equalsIgnoreCase(newAccountDto.getCardNumber())) {
			auditFieldList.add(new AuditField(CARD_NUMBER, oldAccountDto.getCardNumber(), newAccountDto.getCardNumber()));
		}
		
		if(oldAccountDto.getCountry() == null){
			if (newAccountDto.getCountry() != null) {
				auditFieldList.add(new AuditField(COUNTRY, String.valueOf(oldAccountDto.getCountry()), String.valueOf(newAccountDto.getCountry())));
			}
		} else if (!oldAccountDto.getCountry().equals(newAccountDto.getCountry())) {
			auditFieldList.add(new AuditField(COUNTRY, String.valueOf(oldAccountDto.getCountry()), String.valueOf(newAccountDto.getCountry())));
		}
		
		if (!oldAccountDto.getDeletedStatus().equals(newAccountDto.getDeletedStatus())) {
			auditFieldList.add(new AuditField(DELETED_STATUS, String.valueOf(oldAccountDto.getDeletedStatus()), String.valueOf(newAccountDto.getDeletedStatus())));
		}
		
		if(oldAccountDto.getSortCode() == null){
			if (newAccountDto.getSortCode() != null) {
				auditFieldList.add(new AuditField(SORE_CODE, oldAccountDto.getSortCode(), newAccountDto.getSortCode()));
			}
		} else if (!oldAccountDto.getSortCode().equalsIgnoreCase(newAccountDto.getSortCode())) {
			auditFieldList.add(new AuditField(SORE_CODE, oldAccountDto.getSortCode(), newAccountDto.getSortCode()));
		}
		
		/*sim,imei number & Type of request audit fields*/
		if(oldAccountDto.getSimNumber() == null){
			if (newAccountDto.getSimNumber() != null) {
				auditFieldList.add(new AuditField(SIM_NUMBER, oldAccountDto.getSimNumber(), newAccountDto.getSimNumber()));
			}
		} else if (!oldAccountDto.getSimNumber().equalsIgnoreCase(newAccountDto.getSimNumber())) {
			auditFieldList.add(new AuditField(SIM_NUMBER, oldAccountDto.getSimNumber(), newAccountDto.getSimNumber()));
		}
		
		if(oldAccountDto.getImeiNumber() == null){
			if (newAccountDto.getImeiNumber() != null) {
				auditFieldList.add(new AuditField(IMEI_NUMBER, oldAccountDto.getImeiNumber(), newAccountDto.getImeiNumber()));
			}
		} else if (!oldAccountDto.getImeiNumber().equalsIgnoreCase(newAccountDto.getImeiNumber())) {
			auditFieldList.add(new AuditField(IMEI_NUMBER, oldAccountDto.getImeiNumber(), newAccountDto.getImeiNumber()));
		}
		
		if(oldAccountDto.getTypeOfRequest() == null){
			if (newAccountDto.getTypeOfRequest() != null) {
				auditFieldList.add(new AuditField(TYPE_OF_REQUEST, String.valueOf(oldAccountDto.getTypeOfRequest()), String.valueOf(newAccountDto.getTypeOfRequest())));
			}
		} else if (!oldAccountDto.getTypeOfRequest().equals(newAccountDto.getTypeOfRequest())) {
			auditFieldList.add(new AuditField(TYPE_OF_REQUEST, String.valueOf(oldAccountDto.getTypeOfRequest()), String.valueOf(newAccountDto.getTypeOfRequest())));
		}
		
		return auditFieldList;
	}
	
	public static List<AuditField> updateAuditVelocityAndThreshold(VelocityAndThreshold oldVelocityAndThreshold, VelocityAndThreshold newVelocityAndThreshold){
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		if(!oldVelocityAndThreshold.getMinimumamount().equals(newVelocityAndThreshold.getMinimumamount())){
			auditFieldList.add(new AuditField(MINIMAM_AMOUNT, String.valueOf(oldVelocityAndThreshold.getMinimumamount()), String.valueOf(newVelocityAndThreshold.getMinimumamount())));
		}
		if(!oldVelocityAndThreshold.getMaximumamount().equals(newVelocityAndThreshold.getMaximumamount())){
			auditFieldList.add(new AuditField(MAXIMAM_AMOUNT, String.valueOf(oldVelocityAndThreshold.getMaximumamount()), String.valueOf(newVelocityAndThreshold.getMaximumamount())));
		}
		return auditFieldList;
	}
	
	public static List<AuditField> updateAuditWalletThreshold(WalletThreshold oldWalletThreshold, WalletThreshold newWalletThreshold){
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		if(!oldWalletThreshold.getMaximumamount().equals(newWalletThreshold.getMaximumamount())){
			auditFieldList.add(new AuditField(MAXIMAM_AMOUNT, String.valueOf(oldWalletThreshold.getMaximumamount()), String.valueOf(newWalletThreshold.getMaximumamount())));
		}
		return auditFieldList;
	}
	
	public static List<AuditField> updateAuditPrefereances(PreferencesDto oldPreferencesDto, PreferencesDto newPreferencesDto){
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		if(!oldPreferencesDto.getLanguage().equals(newPreferencesDto.getLanguage())){
			auditFieldList.add(new AuditField(LANGUAGE, String.valueOf(oldPreferencesDto.getLanguage()), String.valueOf(newPreferencesDto.getLanguage())));
		}
		return auditFieldList;
	}
	
	public static List<AuditField> updateAuditAuthentication(Authentication oldAuth, Authentication newAuth){
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		if(null == oldAuth.getId()){
			auditFieldList.add(new AuditField(EMAIL_VARIFICATION_STATUS, Boolean.FALSE.toString(), Boolean.TRUE.toString()));
			auditFieldList.add(new AuditField(ACTIVE_STATUS, Boolean.FALSE.toString(), Boolean.TRUE.toString()));	
		}
		return auditFieldList;
	}
	
	public static List<AuditField> updateAuditFeeManagement(FeeDto oldFeeDto, FeeDto newFeeDto){
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		
		if(oldFeeDto.getServices().equals(FeeTaxConstants.FEE_FINANTIAL_TYPE)){
			if(!oldFeeDto.getPayingentity().equals(newFeeDto.getPayingentity())){
				auditFieldList.add(new AuditField(PAYING_ENTITY, String.valueOf(oldFeeDto.getPayingentity()), String.valueOf(newFeeDto.getPayingentity())));
			}
			if(!oldFeeDto.getFeeType().equals(newFeeDto.getFeeType())){
				auditFieldList.add(new AuditField(FEE_TYPE, String.valueOf(oldFeeDto.getFeeType()), String.valueOf(newFeeDto.getFeeType())));
			}
			
			if(oldFeeDto.getFixCharSen() != null && newFeeDto.getFixCharSen() != null){
				if(!oldFeeDto.getFixCharSen().equals(newFeeDto.getFixCharSen())){
					auditFieldList.add(new AuditField(FLAT_FEE_SENDER, String.valueOf(oldFeeDto.getFixCharSen()), String.valueOf(newFeeDto.getFixCharSen())));
				}
			} else if(oldFeeDto.getFixCharSen() == null && newFeeDto.getFixCharSen() != null){
				auditFieldList.add(new AuditField(FLAT_FEE_SENDER, null, String.valueOf(newFeeDto.getFixCharSen())));
			} else if(oldFeeDto.getFixCharSen() != null && newFeeDto.getFixCharSen() == null){
				auditFieldList.add(new AuditField(FLAT_FEE_SENDER, String.valueOf(oldFeeDto.getFixCharSen()), null));
			}
			
			if(oldFeeDto.getFixCharRec() != null && newFeeDto.getFixCharRec() != null){
				if(!oldFeeDto.getFixCharRec().equals(newFeeDto.getFixCharRec())){
				auditFieldList.add(new AuditField(FLAT_FEE_RECEIVER, String.valueOf(oldFeeDto.getFixCharRec()), String.valueOf(newFeeDto.getFixCharRec())));
				}
			} else if(oldFeeDto.getFixCharRec() == null && newFeeDto.getFixCharRec() != null){
				auditFieldList.add(new AuditField(FLAT_FEE_RECEIVER, null, String.valueOf(newFeeDto.getFixCharRec())));
			} else if(oldFeeDto.getFixCharRec() != null && newFeeDto.getFixCharRec() == null){
				auditFieldList.add(new AuditField(FLAT_FEE_RECEIVER, String.valueOf(oldFeeDto.getFixCharRec()), null));
			}	
				
			if(oldFeeDto.getPercentageSen() != null && newFeeDto.getPercentageSen() != null){
				if(!oldFeeDto.getPercentageSen().equals(newFeeDto.getPercentageSen())){
					auditFieldList.add(new AuditField(PERCENTAGE_SENDER, String.valueOf(oldFeeDto.getPercentageSen()), String.valueOf(newFeeDto.getPercentageSen())));
				}
			} else if(oldFeeDto.getPercentageSen() == null && newFeeDto.getPercentageSen() != null){
				auditFieldList.add(new AuditField(PERCENTAGE_SENDER, null, String.valueOf(newFeeDto.getPercentageSen())));
			} else if(oldFeeDto.getPercentageSen() != null && newFeeDto.getPercentageSen() == null){
				auditFieldList.add(new AuditField(PERCENTAGE_SENDER, String.valueOf(oldFeeDto.getPercentageSen()), null));
			}	
			
			
			if(oldFeeDto.getPercentageRec() != null && newFeeDto.getPercentageRec() != null){
				if(!oldFeeDto.getPercentageRec().equals(newFeeDto.getPercentageRec())){
				auditFieldList.add(new AuditField(PERCENTAGE_RECEIVER, String.valueOf(oldFeeDto.getPercentageRec()), String.valueOf(newFeeDto.getPercentageRec())));
				}
			} else if(oldFeeDto.getPercentageRec() == null && newFeeDto.getPercentageRec() != null){
				auditFieldList.add(new AuditField(PERCENTAGE_RECEIVER, null, String.valueOf(newFeeDto.getPercentageRec())));
			} else if(oldFeeDto.getPercentageRec() != null && newFeeDto.getPercentageRec() == null){
				auditFieldList.add(new AuditField(PERCENTAGE_RECEIVER, String.valueOf(oldFeeDto.getPercentageRec()), null));
			}		
			updateAuditFeeSlabs(oldFeeDto.getFeeSlabs(), newFeeDto.getFeeSlabs(), auditFieldList, oldFeeDto.getServices());
		} else if(oldFeeDto.getServices().equals(FeeTaxConstants.FEE_NON_FINANTIAL_TYPE)){			
			if(!oldFeeDto.getFixCharSen().equals(newFeeDto.getFixCharSen())){
				auditFieldList.add(new AuditField(FLAT_FEE, String.valueOf(oldFeeDto.getFixCharSen()), String.valueOf(newFeeDto.getFixCharSen())));
			}
			if(!oldFeeDto.getTimeFreequency().equals(newFeeDto.getTimeFreequency())){
				auditFieldList.add(new AuditField(TIME_FREQUENCY, String.valueOf(oldFeeDto.getTimeFreequency()), String.valueOf(newFeeDto.getTimeFreequency())));
			}			
		} else {
			if(!oldFeeDto.getTimeFreequency().equals(newFeeDto.getTimeFreequency())){
				auditFieldList.add(new AuditField(TIME_FREQUENCY, String.valueOf(oldFeeDto.getTimeFreequency()), String.valueOf(newFeeDto.getTimeFreequency())));
			}	
			if(!oldFeeDto.getFeeType().equals(newFeeDto.getFeeType())){
				auditFieldList.add(new AuditField(FEE_TYPE, String.valueOf(oldFeeDto.getFeeType()), String.valueOf(newFeeDto.getFeeType())));
			}
			if(oldFeeDto.getFixCharRec() != null && !oldFeeDto.getFixCharRec().equals(newFeeDto.getFixCharRec())){
				auditFieldList.add(new AuditField(FLAT_FEE_RECEIVER, String.valueOf(oldFeeDto.getFixCharRec()), String.valueOf(newFeeDto.getFixCharRec())));
			}
			if(oldFeeDto.getPercentageRec() != null && !oldFeeDto.getPercentageRec().equals(newFeeDto.getPercentageRec())){
				auditFieldList.add(new AuditField(PERCENTAGE_RECEIVER, String.valueOf(oldFeeDto.getPercentageRec()), String.valueOf(newFeeDto.getPercentageRec())));
			}
			updateAuditFeeSlabs(oldFeeDto.getFeeSlabs(), newFeeDto.getFeeSlabs(), auditFieldList, oldFeeDto.getServices());
		}
		return auditFieldList;
	}
	
	public static void updateAuditFeeSlabs(List<FeeSlab> oldFeeSlabs, List<FeeSlab> newFeeSlabs, List<AuditField> auditFieldList, Long serviceType){
		FeeSlab oldFeeSlab = null;
		FeeSlab newFeeSlab = null;
		StringBuffer sbOne = new StringBuffer();
		StringBuffer sbTwo = new StringBuffer();
		for(int i = 0; i<(oldFeeSlabs.size() >= newFeeSlabs.size() ? oldFeeSlabs.size() : newFeeSlabs.size()); i++){
			if(i <= (oldFeeSlabs.size() - 1)){
				oldFeeSlab = oldFeeSlabs.get(i);
			} else{
				oldFeeSlab = null;
			}
			if(i <= (newFeeSlabs.size() - 1)){
				newFeeSlab = newFeeSlabs.get(i);
			} else{
				newFeeSlab = null;
			}
			if(oldFeeSlab != null){
				sbOne.append(LOWER_LIMIT)
					.append(GlobalLitterals.EQUAL_STRING)
					.append(oldFeeSlab.getLowerLimit())
					.append(GlobalLitterals.FILE_LINE_SPLIT)
					.append(UPPER_LIMIT)
					.append(GlobalLitterals.EQUAL_STRING)
					.append(oldFeeSlab.getUpperLimit())
					.append(GlobalLitterals.FILE_LINE_SPLIT);
				if(serviceType.equals(FeeTaxConstants.FEE_FINANTIAL_TYPE)){
					sbOne.append(FLATFEE_SENDER)
						.append(GlobalLitterals.EQUAL_STRING)
						.append(oldFeeSlab.getFixedChargeSender())
						.append(GlobalLitterals.FILE_LINE_SPLIT)
						.append(FEE_SENDER_PERCENTAGE)
						.append(GlobalLitterals.EQUAL_STRING)
						.append(oldFeeSlab.getPercentageOfSender())
						.append(GlobalLitterals.FILE_LINE_SPLIT);
				}
				sbOne.append(FLATFEE_RECEIVER)
					.append(GlobalLitterals.EQUAL_STRING)
					.append(oldFeeSlab.getFixedChargeReceiver())
					.append(GlobalLitterals.FILE_LINE_SPLIT)
					.append(FEE_RECEIVER_PERCENTAGE)
					.append(GlobalLitterals.EQUAL_STRING)
					.append(oldFeeSlab.getPercentageOfReceiver());			
			}
			if(newFeeSlab != null){
				sbTwo.append(LOWER_LIMIT)
					.append(GlobalLitterals.EQUAL_STRING)
					.append(newFeeSlab.getLowerLimit())
					.append(GlobalLitterals.FILE_LINE_SPLIT)
					.append(UPPER_LIMIT)
					.append(GlobalLitterals.EQUAL_STRING)
					.append(newFeeSlab.getUpperLimit())
					.append(GlobalLitterals.FILE_LINE_SPLIT);
				if(serviceType.equals(FeeTaxConstants.FEE_FINANTIAL_TYPE)){
					sbTwo.append(FLATFEE_SENDER)
						.append(GlobalLitterals.EQUAL_STRING)
						.append(newFeeSlab.getFixedChargeSender())
						.append(GlobalLitterals.FILE_LINE_SPLIT)
						.append(FEE_SENDER_PERCENTAGE)
						.append(GlobalLitterals.EQUAL_STRING)
						.append(newFeeSlab.getPercentageOfSender())
						.append(GlobalLitterals.FILE_LINE_SPLIT);
				}
				sbTwo.append(FLATFEE_RECEIVER)
					.append(GlobalLitterals.EQUAL_STRING)
					.append(newFeeSlab.getFixedChargeReceiver())
					.append(GlobalLitterals.FILE_LINE_SPLIT)
					.append(FEE_RECEIVER_PERCENTAGE)
					.append(GlobalLitterals.EQUAL_STRING)
					.append(newFeeSlab.getPercentageOfReceiver());
			}
			if(!sbOne.toString().equals(sbTwo.toString())){
				if(i == 0){
					auditFieldList.add(new AuditField(FEESLAB_ONE, sbOne.toString(), sbTwo.toString()));
				} else if(i == 1){
					auditFieldList.add(new AuditField(FEESLAB_TWO, sbOne.toString(), sbTwo.toString()));
				} else{
					auditFieldList.add(new AuditField(FEESLAB_THREE, sbOne.toString(), sbTwo.toString()));	
				}
			}
			sbOne = new StringBuffer();
			sbTwo = new StringBuffer();
		}
	}
	
	public static List<AuditField> updateAuditUserIP(UserIP oldUserIP, UserIP newUserIP){
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		auditFieldList.add(new AuditField(CODE, oldUserIP.getCode(), newUserIP.getCode()));			
		return auditFieldList;
	}
	
	public static List<AuditField> updateAuditChangePassword(ChangePasswordDto oldDto, ChangePasswordDto newDto){
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		auditFieldList.add(new AuditField(PASSWORD, oldDto.getOldPassword(), newDto.getNewPassword()));
		return auditFieldList;
	}
	
	public static List<AuditField> updateAuditTax(Tax oldTaxDto, Tax newTaxDto){
		List<AuditField> auditFieldList = new ArrayList<AuditField>();
		if(!oldTaxDto.getPercentage().equals(newTaxDto.getPercentage())){
			auditFieldList.add(new AuditField(PERCENTAGE, String.valueOf(oldTaxDto.getPercentage()), String.valueOf(newTaxDto.getPercentage())));
		}
		return auditFieldList;
	}
	
	public static List<AuditField> getFields(Long disputeStatus, Double disputeAmount, Long newDisputeStatus, Double newDisputeAmount) throws WalletException {		
		List<AuditField> list = new ArrayList<AuditField>();	
		if (!disputeStatus.equals(newDisputeStatus)) {
			list.add(new AuditField(DISPUTE_STATUS, disputeStatus.toString(), newDisputeStatus.toString()));
		}
		if (!disputeAmount.equals(newDisputeAmount)) {
			list.add(new AuditField(AMOUNT, disputeAmount.toString(), newDisputeAmount.toString()));
		}	
		return list;
	}

}
