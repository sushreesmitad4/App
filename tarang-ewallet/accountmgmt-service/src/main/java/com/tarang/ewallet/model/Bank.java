/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:06:25 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class Bank extends Account{

	private static final long serialVersionUID = 1L;

	private Long country;
	
	private Long bankAccountType;
	
	private String sortCode;
	
	private String bankName;
	
	private String accountNumber;
	
	private String bankAddress;

	private Boolean holdBank;
	
	public Bank(){
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public Long getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(Long bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Boolean getHoldBank() {
		return holdBank;
	}

	public void setHoldBank(Boolean holdBank) {
		this.holdBank = holdBank;
	}

}
