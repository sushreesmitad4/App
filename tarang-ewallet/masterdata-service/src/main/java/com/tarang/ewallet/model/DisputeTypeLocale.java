package com.tarang.ewallet.model;

public class DisputeTypeLocale {
	
	private Long id;

	private Long disputeTypeId;

	private Long languageId;

	private String name;
	
	public DisputeTypeLocale(){
	}
	
	public DisputeTypeLocale(Long id, Long languageId, Long disputeTypeId, String name) {
		this.id = id;
		this.disputeTypeId = disputeTypeId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDisputeTypeId() {
		return disputeTypeId;
	}

	public void setDisputeTypeId(Long disputeTypeId) {
		this.disputeTypeId = disputeTypeId;
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
