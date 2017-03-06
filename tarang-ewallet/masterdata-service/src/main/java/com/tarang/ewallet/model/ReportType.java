package com.tarang.ewallet.model;

public class ReportType {

	private Long id;
	
	private Long userTypeId;
	
	private String name;

	public ReportType() {
		
	}

	public ReportType(Long id, Long userTypeId, String name) {
		this.id = id;
		this.userTypeId = userTypeId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
