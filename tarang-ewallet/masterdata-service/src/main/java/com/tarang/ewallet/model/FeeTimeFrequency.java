package com.tarang.ewallet.model;
/**
 * @author  : kedarnathd
 * @date    : Jan 16, 2013
 * @time    : 7:09:03 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class FeeTimeFrequency {
	private Long id;

	private String name;

	public FeeTimeFrequency() {
	}

	public FeeTimeFrequency(Long id, String name) {
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
