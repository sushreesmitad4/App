package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class Currency {

	private Long id;

	private String name;

	private String code;
	
	public Currency() {
	}

	public Currency(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Currency(Long id, String code, String name) {
		this.id = id;
		this.code = code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
