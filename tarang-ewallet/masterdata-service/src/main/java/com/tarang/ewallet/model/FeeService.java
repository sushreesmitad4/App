/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Dec 31, 2012
 * @time    : 9:08:34 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class FeeService {

	private Long id;

	private String name;

	public FeeService() {
	}

	public FeeService(Long id, String name) {
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
