/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Oct 17, 2012
 * @time    : 11:33:00 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class MerchantBusinessSubCategory {

	private Long id;

	private Long category;
	
	private String subCategory;

	public MerchantBusinessSubCategory(){
	}

	public MerchantBusinessSubCategory(Long id, Long category, String subCategory){
		this.id = id;
		this.category = category;
		this.subCategory = subCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	
	
}
