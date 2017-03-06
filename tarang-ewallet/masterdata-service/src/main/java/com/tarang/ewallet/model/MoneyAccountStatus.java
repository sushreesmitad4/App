/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Nov 28, 2012
 * @time    : 9:13:04 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class MoneyAccountStatus {

	private Long id;

	private String name;

	public MoneyAccountStatus() {
	}

	public MoneyAccountStatus(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
