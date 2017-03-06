/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Nov 28, 2012
 * @time    : 9:14:57 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class MoneyAccountDeleteStatusLocale {

	private Long id;

	private Long moneyAccountDeleteStatusId;

	private Long languageId;

	private String name;

	public MoneyAccountDeleteStatusLocale() {
	}

	public MoneyAccountDeleteStatusLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public MoneyAccountDeleteStatusLocale(Long id, Long languageId, Long moneyAccountDeleteStatusId, String name) {
		this.id = id;
		this.moneyAccountDeleteStatusId = moneyAccountDeleteStatusId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMoneyAccountDeleteStatusId() {
		return moneyAccountDeleteStatusId;
	}

	public void setMoneyAccountDeleteStatusId(Long moneyAccountDeleteStatusId) {
		this.moneyAccountDeleteStatusId = moneyAccountDeleteStatusId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
