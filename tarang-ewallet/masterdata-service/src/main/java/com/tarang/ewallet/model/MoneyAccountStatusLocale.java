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
public class MoneyAccountStatusLocale {

	private Long id;

	private Long moneyAccountStatusId;

	private Long languageId;

	private String name;

	public MoneyAccountStatusLocale() {
	}

	public MoneyAccountStatusLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public MoneyAccountStatusLocale(Long id, Long languageId, Long moneyAccountStatusId, String name) {
		this.id = id;
		this.moneyAccountStatusId = moneyAccountStatusId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMoneyAccountStatusId() {
		return moneyAccountStatusId;
	}

	public void setMoneyAccountStatusId(Long moneyAccountStatusId) {
		this.moneyAccountStatusId = moneyAccountStatusId;
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
