/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Nov 28, 2012
 * @time    : 10:22:16 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class BankAccountType {

	private Long id;

	private Long countryId;
	
	private String type;

	public BankAccountType(){
	}

	public BankAccountType(Long id, Long countryId, String region){
		this.id = id;
		this.countryId = countryId;
		this.type = region;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
