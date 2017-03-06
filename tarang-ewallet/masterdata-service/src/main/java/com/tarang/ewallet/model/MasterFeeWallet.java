/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Jan 15, 2013
 * @time    : 1:28:30 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class MasterFeeWallet {

	private Long id;

	private Long currency;

	private Double fee;
	
	public MasterFeeWallet(){
	}

	public MasterFeeWallet(Long id, Long currency, Double fee) {
		this.id = id;
		this.currency = currency;
		this.fee = fee;
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

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	public void addFee(Double addFee){
		fee += addFee;
	}
	
	public void deductFee(Double deductFee){
		fee -= deductFee;
	}

}
