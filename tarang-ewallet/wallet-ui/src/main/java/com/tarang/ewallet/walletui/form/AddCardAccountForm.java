package com.tarang.ewallet.walletui.form;

import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.walletui.controller.constants.Accounts;
import com.tarang.ewallet.walletui.util.ManageAccountUtil;


public class AddCardAccountForm {
	private String accountHolderName;
	private Long cardType;
	private String cardNumber;
	private Long expireDateYear;
	private Long expireDateMonth;
	private String mode;
	private String cvv;
	private Boolean isSameAsProfileAddress;
	private Long country;
	private String addrOne;
	private String addrTwo;
	private String city;
	private Long state;
	private String postalCode;
	private String profileAddress;
	private String countryName;
	private Boolean jointAccount;
	private String simNumber;
	private String imeiNumber;
		
	//for validation
	private Long status;

    public AddCardAccountForm(){
    	
    }
	
    
	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}


	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
	}


	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public Long getCardType() {
		return cardType;
	}
	public void setCardType(Long cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public Long getExpireDateYear() {
		return expireDateYear;
	}

	public void setExpireDateYear(Long expireDateYear) {
		this.expireDateYear = expireDateYear;
	}

	public Long getExpireDateMonth() {
		return expireDateMonth;
	}

	public void setExpireDateMonth(Long expireDateMonth) {
		this.expireDateMonth = expireDateMonth;
	}
	
	

	public Boolean getIsSameAsProfileAddress() {
		return isSameAsProfileAddress;
	}

	public void setIsSameAsProfileAddress(Boolean isSameAsProfileAddress) {
		this.isSameAsProfileAddress = isSameAsProfileAddress;
	}
	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}
	public String getAddrOne() {
		return addrOne;
	}
	public void setAddrOne(String addrOne) {
		this.addrOne = addrOne;
	}
	public String getAddrTwo() {
		return addrTwo;
	}
	public void setAddrTwo(String addrTwo) {
		this.addrTwo = addrTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getProfileAddress() {
		return profileAddress;
	}

	public void setProfileAddress(String profileAddress) {
		this.profileAddress = profileAddress;
	}
	
	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	

	public Boolean getJointAccount() {
		return jointAccount;
	}


	public void setJointAccount(Boolean jointAccount) {
		this.jointAccount = jointAccount;
	}
      

	public void convertDtoToCardForm(AccountDto accountDto, boolean exceptExpDate, boolean includeAddress) {
		if(accountDto != null) {
			setAccountHolderName(accountDto.getAccountHolderName());
			setCardType(accountDto.getCardType());
			setJointAccount(accountDto.getJointAccount());
			if(accountDto.getStatus().equals(Accounts.CARD_STATUS_VARIFIED)){
				setCardNumber(ManageAccountUtil.xNumber(accountDto.getCardNumber()));
			}else{
				setCardNumber(accountDto.getCardNumber());
			}
			
			if( !exceptExpDate ){
				String[] ced = accountDto.getCardExpairyDate().split("/");
				expireDateMonth = Long.valueOf(ced[0]);
				expireDateYear = Long.valueOf(ced[1]);
			}
			
			if(includeAddress){
				setCountry(accountDto.getCountry());
				setAddrOne(accountDto.getAddrOne());
				setAddrTwo(accountDto.getAddrTwo());
				setCity(accountDto.getCity());
				setState(accountDto.getState());
				setPostalCode(accountDto.getPostalCode());
				setIsSameAsProfileAddress(accountDto.getIsSameAsProfileAddress());
			}
		}
	}
	
	public void convertCardFormToDtoForAddress(AccountDto accountDto){
		if( accountDto != null){
			accountDto.setCountry(country);
			accountDto.setAddrOne(addrOne);
			accountDto.setAddrTwo(addrTwo);
			accountDto.setCity(city);
			accountDto.setState(state);
			accountDto.setPostalCode(postalCode);
			accountDto.setJointAccount(jointAccount);
		}
	}
	
	public void convertDtoToCardFormForAddress(AccountDto accountDto){
		if( accountDto != null){
			setCountry(accountDto.getCountry());
			setAddrOne(accountDto.getAddrOne());
			setAddrTwo(accountDto.getAddrTwo());
			setCity(accountDto.getCity());
			setState(accountDto.getState());
			setPostalCode(accountDto.getPostalCode());
		}
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	
}
