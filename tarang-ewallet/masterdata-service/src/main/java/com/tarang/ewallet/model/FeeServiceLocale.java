/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Dec 31, 2012
 * @time    : 9:12:15 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class FeeServiceLocale {

	private Long id;

	private Long feeServiceId;

	private Long languageId;

	private String name;

	public FeeServiceLocale() {
	}

	public FeeServiceLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public FeeServiceLocale(Long id, Long feeServiceId, Long languageId, String name) {
		this.id = id;
		this.feeServiceId = feeServiceId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFeeServiceId() {
		return feeServiceId;
	}

	public void setFeeServiceId(Long feeServiceId) {
		this.feeServiceId = feeServiceId;
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
