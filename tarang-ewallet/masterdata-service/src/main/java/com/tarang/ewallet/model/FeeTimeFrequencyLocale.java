/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : kedarnathd
 * @date    : Jan 16, 2013
 * @time    : 7:09:03 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class FeeTimeFrequencyLocale {

	private Long id;

	private Long feeTimeFrequencyId;

	private Long languageId;

	private String name;
	
	public FeeTimeFrequencyLocale(){
	}
	
	public FeeTimeFrequencyLocale(Long id, Long languageId, Long feeTimeFrequencyId, String name) {
		this.id = id;
		this.feeTimeFrequencyId = feeTimeFrequencyId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFeeTimeFrequencyId() {
		return feeTimeFrequencyId;
	}

	public void setFeeTimeFrequencyId(Long feeTimeFrequencyId) {
		this.feeTimeFrequencyId = feeTimeFrequencyId;
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
