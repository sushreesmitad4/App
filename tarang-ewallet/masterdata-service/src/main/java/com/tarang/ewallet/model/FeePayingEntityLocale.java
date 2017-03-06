/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Jan 2, 2013
 * @time    : 7:09:03 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class FeePayingEntityLocale {

	private Long id;

	private Long feePayingEntityId;

	private Long languageId;

	private String name;
	
	public FeePayingEntityLocale(){
	}
	
	public FeePayingEntityLocale(Long id, Long languageId, Long feePayingEntityId, String name) {
		this.id = id;
		this.feePayingEntityId = feePayingEntityId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFeePayingEntityId() {
		return feePayingEntityId;
	}

	public void setFeePayingEntityId(Long feePayingEntityId) {
		this.feePayingEntityId = feePayingEntityId;
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
