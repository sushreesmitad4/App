/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Nov 28, 2012
 * @time    : 10:24:09 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class BankAccountTypeLocale {

	private Long id;

	private Long countryId;
	
	private Long bankAccountTypeId;

	private Long languageId;

	private String type;
	
	public BankAccountTypeLocale(){
	}

	public BankAccountTypeLocale(Long countryId, Long bankAccountTypeId, String type){
		this.countryId = countryId;
		this.bankAccountTypeId = bankAccountTypeId;
		this.type = type;
	}

	public BankAccountTypeLocale(Long id, Long languageId, Long countryId, Long bankAccountTypeId, String type){
		this.id = id;
		this.languageId = languageId;
		this.countryId = countryId;
		this.bankAccountTypeId = bankAccountTypeId;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getBankAccountTypeId() {
		return bankAccountTypeId;
	}

	public void setBankAccountTypeId(Long bankAccountTypeId) {
		this.bankAccountTypeId = bankAccountTypeId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
