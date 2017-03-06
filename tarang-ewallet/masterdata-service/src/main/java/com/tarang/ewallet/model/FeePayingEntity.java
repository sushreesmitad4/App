/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Jan 2, 2013
 * @time    : 7:08:48 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class FeePayingEntity {

	private Long id;

	private String name;

	public FeePayingEntity() {
	}

	public FeePayingEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
