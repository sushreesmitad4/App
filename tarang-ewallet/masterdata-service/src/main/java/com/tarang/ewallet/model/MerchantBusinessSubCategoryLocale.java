/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Oct 17, 2012
 * @time    : 11:43:43 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class MerchantBusinessSubCategoryLocale {

	private Long id;

	private Long mbcId;
	
	private Long mbscId;

	private Long languageId;

	private String subCategory;
	
	public MerchantBusinessSubCategoryLocale(){
	}

	public MerchantBusinessSubCategoryLocale(Long id, Long mbcId, String subCategory){
		this.id = id;
		this.mbcId = mbcId;
		this.subCategory = subCategory;
	}

	public MerchantBusinessSubCategoryLocale(Long id, Long languageId, Long mbcId, Long mbscId, String subCategory){
		this.id = id;
		this.languageId = languageId;
		this.mbcId = mbcId;
		this.mbscId = mbscId;
		this.subCategory = subCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMbcId() {
		return mbcId;
	}

	public void setMbcId(Long mbcId) {
		this.mbcId = mbcId;
	}

	public Long getMbscId() {
		return mbscId;
	}

	public void setMbscId(Long mbscId) {
		this.mbscId = mbscId;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

}
