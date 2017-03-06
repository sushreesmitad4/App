package com.tarang.ewallet.model;

public class QueryOrFeedbackLocale {
	
	private Long id;

	private Long queryOrFeedbackId;

	private Long languageId;

	private String name;
	
	public QueryOrFeedbackLocale(){
	}
	
	public QueryOrFeedbackLocale(Long id, Long queryOrFeedbackId, Long languageId, String name) {
		this.id = id;
		this.queryOrFeedbackId = queryOrFeedbackId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getQueryOrFeedbackId() {
		return queryOrFeedbackId;
	}

	public void setQueryOrFeedbackId(Long queryOrFeedbackId) {
		this.queryOrFeedbackId = queryOrFeedbackId;
	}

}
