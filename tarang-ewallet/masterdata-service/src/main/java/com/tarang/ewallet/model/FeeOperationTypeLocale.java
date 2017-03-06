/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Dec 31, 2012
 * @time    : 9:40:43 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class FeeOperationTypeLocale {

	private Long id;
	
	private FeeOperationType feeOperationType;
	
	private Long languageId;
	
	private String name;
	
	public FeeOperationTypeLocale(){
	}
	
	public FeeOperationTypeLocale(Long id, FeeOperationType feeOperationType, String name, Long languageId){
		this.id = id;
		this.feeOperationType = feeOperationType;
		this.name = name;
		this.languageId = languageId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FeeOperationType getFeeOperationType() {
		return feeOperationType;
	}

	public void setFeeOperationType(FeeOperationType feeOperationType) {
		this.feeOperationType = feeOperationType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	
}
