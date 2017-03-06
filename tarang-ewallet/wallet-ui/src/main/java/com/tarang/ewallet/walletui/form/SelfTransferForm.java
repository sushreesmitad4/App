package com.tarang.ewallet.walletui.form;

import java.io.Serializable;

public class SelfTransferForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long fromWallet;
	private String balance;
	private String requestedAmount;
	private Long towallet;
	private String walletBalances;
	private String actualAmount;
	private Long transactionType;
	private String mode;
	
	public SelfTransferForm(){
	}
	
	/**
	 * @return the fromWallet
	 */
	public Long getFromWallet() {
		return fromWallet;
	}

	/**
	 * @param fromWallet the fromWallet to set
	 */
	public void setFromWallet(Long fromWallet) {
		this.fromWallet = fromWallet;
	}

	/**
	 * @return the balance
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * @return the requestedAmount
	 */
	public String getRequestedAmount() {
		return requestedAmount;
	}

	/**
	 * @param requestedAmount the requestedAmount to set
	 */
	public void setRequestedAmount(String requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	/**
	 * @return the towallet
	 */
	public Long getTowallet() {
		return towallet;
	}

	/**
	 * @param towallet the towallet to set
	 */
	public void setTowallet(Long towallet) {
		this.towallet = towallet;
	}

	/**
	 * @return the walletBalances
	 */
	public String getWalletBalances() {
		return walletBalances;
	}

	/**
	 * @param walletBalances the walletBalances to set
	 */
	public void setWalletBalances(String walletBalances) {
		this.walletBalances = walletBalances;
	}

	/**
	 * @return the actualAmount
	 */
	public String getActualAmount() {
		return actualAmount;
	}

	/**
	 * @param actualAmount the actualAmount to set
	 */
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	/**
	 * @return the transactionType
	 */
	public Long getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(Long transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	

}
