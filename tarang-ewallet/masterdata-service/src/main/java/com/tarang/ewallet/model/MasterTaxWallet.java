/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Jan 15, 2013
 * @time    : 1:28:47 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class MasterTaxWallet {

	private Long id;

	private Long currency;

	private Double tax;

	public MasterTaxWallet(){
	}

	public MasterTaxWallet(Long id, Long currency, Double tax) {
		this.id = id;
		this.currency = currency;
		this.tax = tax;
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

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public void addTax(Double addTax){
		tax += addTax;
	}
	
	public void deductTax(Double deductTax){
		tax -= deductTax;
	}

}
