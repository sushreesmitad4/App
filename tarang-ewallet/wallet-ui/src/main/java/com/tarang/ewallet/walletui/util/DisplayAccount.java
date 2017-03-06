/**
 * 
 */
package com.tarang.ewallet.walletui.util;

import java.io.Serializable;

/**
 * @author  : prasadj
 * @date    : Dec 3, 2012
 * @time    : 8:56:50 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class DisplayAccount implements Serializable {

	private static final long serialVersionUID = 1L;


	// common attributes
	private Long id;
	
	// dto value
	private String atype;
	
	// display value of bank a/c type or card type
	private String bankOrCardType;

	// display value of bank a/c number or card number
	private String bankOrCardNumber;

	// display of bank name or card provider value
	private String bankOrCardName;
	
	//dto value of status
	private Long status;

	//display value of status
	private String statusName;
	
	//dto value
	private Boolean defaultAccount;
	
	// display value of defaultAccount
	private String defaultValue;

	public DisplayAccount(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public String getBankOrCardType() {
		return bankOrCardType;
	}

	public void setBankOrCardType(String bankOrCardType) {
		this.bankOrCardType = bankOrCardType;
	}

	public String getBankOrCardNumber() {
		return bankOrCardNumber;
	}

	public void setBankOrCardNumber(String bankOrCardNumber) {
		this.bankOrCardNumber = bankOrCardNumber;
	}

	public String getBankOrCardName() {
		return bankOrCardName;
	}

	public void setBankOrCardName(String bankOrCardName) {
		this.bankOrCardName = bankOrCardName;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Boolean getDefaultAccount() {
		return defaultAccount;
	}

	public void setDefaultAccount(Boolean defaultAccount) {
		this.defaultAccount = defaultAccount;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defultValue) {
		this.defaultValue = defultValue;
	}
	
}
