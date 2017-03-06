package com.tarang.ewallet.model;

public class DisputeStatusLocale {
	
	private Long id;

	private Long disputeStatusLocaleId;

	private Long languageId;

	private String name;
	
	public DisputeStatusLocale(){
		
	}
	
	public DisputeStatusLocale(Long id, Long languageId, Long disputeStatusLocaleId, String name){
		this.id = id;
		this.languageId = languageId;
		this.disputeStatusLocaleId = disputeStatusLocaleId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDisputeStatusLocaleId() {
		return disputeStatusLocaleId;
	}

	public void setDisputeStatusLocaleId(Long disputeStatusLocaleId) {
		this.disputeStatusLocaleId = disputeStatusLocaleId;
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
