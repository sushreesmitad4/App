package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class Country {

	private Long id;

	private String code;

	private String name;

	private String pcode;
	
	private Long currency;
	
	public Country() {
	}

	public Country(Long id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Country(Long id, String code, String name, String pcode, Long currency) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.pcode = pcode;
		this.currency = currency;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

}
