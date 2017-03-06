package com.tarang.ewallet.walletui.form;


public class ReloadMoneyForm {
	
	private String accountId;
	private String cvv;
	private String amount;
	private String mode;
	private String currecnyCode;
	private Long cardType;
	
	private String accountOrCardHolderName;

	// display value of bank a/c number or card number
	private String bankOrCardNumber;

	// display of bank name or card provider value
	private String bankOrCardName;
		
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getCvv() {
		return cvv;
	}
	
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
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

	public String getAccountOrCardHolderName() {
		return accountOrCardHolderName;
	}

	public void setAccountOrCardHolderName(String accountOrCardHolderName) {
		this.accountOrCardHolderName = accountOrCardHolderName;
	}

	/**
	 * @return the currecnyCode
	 */
	public String getCurrecnyCode() {
		return currecnyCode;
	}

	/**
	 * @param currecnyCode the currecnyCode to set
	 */
	public void setCurrecnyCode(String currecnyCode) {
		this.currecnyCode = currecnyCode;
	}

	public Long getCardType() {
		return cardType;
	}

	public void setCardType(Long cardType) {
		this.cardType = cardType;
	}
	
}
