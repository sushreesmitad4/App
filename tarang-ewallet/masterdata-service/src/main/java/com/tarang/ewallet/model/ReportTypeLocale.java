package com.tarang.ewallet.model;

public class ReportTypeLocale {
	
	private Long id;
	
	private Long reportTypeId;
	
	private Long userTypeId;

	private Long languageId;
	
	private String name;
	
	public ReportTypeLocale() {
	
	}

	public ReportTypeLocale(Long reportTypeId, Long userTypeId, String name) {
		this.reportTypeId = reportTypeId;
		this.userTypeId = userTypeId;
		this.name = name;
	}
	
	public ReportTypeLocale(Long id, Long languageId, Long reportTypeId, Long userTypeId, String name){
		this.id = id;
		this.languageId = languageId;
		this.reportTypeId = reportTypeId;
		this.userTypeId = userTypeId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReportTypeId() {
		return reportTypeId;
	}

	public void setReportTypeId(Long reportTypeId) {
		this.reportTypeId = reportTypeId;
	}

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
