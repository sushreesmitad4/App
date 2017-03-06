/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Oct 22, 2012
 * @time    : 10:19:55 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class RegionLocale {

	private Long id;

	private Long countryId;
	
	private Long regionId;

	private Long languageId;

	private String region;
	
	public RegionLocale(){
	}

	public RegionLocale(Long regionId, Long countryId, String region){
		this.regionId = regionId;
		this.countryId = countryId;
		this.region = region;
	}

	public RegionLocale(Long id, Long languageId, Long countryId, Long regionId, String region){
		this.id = id;
		this.languageId = languageId;
		this.countryId = countryId;
		this.regionId = regionId;
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

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
}
