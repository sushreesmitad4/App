/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Jan 11, 2013
 * @time    : 4:10:14 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class MasterAmountWallet {

	private Long id;

	private Long currency;

	private Double walletAmount;

	public MasterAmountWallet(){
	}

	public MasterAmountWallet(Long id, Long currency, Double walletAmount) {
		this.id = id;
		this.currency = currency;
		this.walletAmount = walletAmount;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public Double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(Double walletAmount) {
		this.walletAmount = walletAmount;
	}

	public void addAmount(Double addAmount){
		walletAmount += addAmount;
	}
	
	public void deductAmount(Double deductAmount){
		walletAmount -= deductAmount;
	}
}
