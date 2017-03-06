package com.tarang.ewallet.walletui.form;

import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.dto.PrepaidCardDto;


public class AddBankAccountForm {
	
	private Long country;

	private String accountHolderName;
	
	private String bankName;
	
	private Long accountType;
	
	private String sortCode;

	private String accountNumber;
	
	private String reEnterAccountNumber;
	
	private String bankAddress;
	
	private String mode;
	
	private Boolean jointAccount;
	
	public AddBankAccountForm(){
	}
	
	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}
	
	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Long getAccountType() {
		return accountType;
	}

	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}
	
	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getReEnterAccountNumber() {
		return reEnterAccountNumber;
	}

	public void setReEnterAccountNumber(String reEnterAccountNumber) {
		this.reEnterAccountNumber = reEnterAccountNumber;
	}
	
	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public Boolean getJointAccount() {
		return jointAccount;
	}

	public void setJointAccount(Boolean jointAccount) {
		this.jointAccount = jointAccount;
	}

	public void convertDtoToBankForm(AccountDto accountDto) {
		 if(accountDto!=null) {
		setCountry(accountDto.getCountry());
		setAccountHolderName(accountDto.getAccountHolderName());
		setAccountType(accountDto.getBankAccountType());
		setBankName(accountDto.getBankName());
		setSortCode(accountDto.getSortCode());
		setAccountNumber(accountDto.getAccountNumber());
		setBankAddress(accountDto.getBankAddress());
		setJointAccount(accountDto.getJointAccount());
		 }
	  }

}
