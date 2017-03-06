/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Dec 31, 2012
 * @time    : 9:26:05 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class FeeOperationType {

	private Long id;

	private Long feeServiceId;

	private Long userTypeId;
	
	private String name;

	public FeeOperationType() {
	}

	public FeeOperationType(Long id, Long feeServiceId, Long userTypeId, String name) {
		this.id = id;
		this.feeServiceId = feeServiceId;
		this.userTypeId = userTypeId;
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

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
