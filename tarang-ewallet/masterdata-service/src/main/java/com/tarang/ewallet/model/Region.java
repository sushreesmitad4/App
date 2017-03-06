/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Oct 22, 2012
 * @time    : 10:17:55 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class Region {

	private Long id;

	private Long countryId;
	
	private String region;

	public Region(){
	}

	public Region(Long id, Long countryId, String region){
		this.id = id;
		this.countryId = countryId;
		this.region = region;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
