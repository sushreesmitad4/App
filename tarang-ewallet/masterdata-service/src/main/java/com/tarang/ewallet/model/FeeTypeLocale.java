package com.tarang.ewallet.model;

public class FeeTypeLocale {
	
	private Long id;
	private Long feeId;
	private Long languageId;
	private String name;
	
	public FeeTypeLocale() {
	}
	
	public FeeTypeLocale(Long id, Long feeId, Long languageId, String name) {
		this.id = id;
		this.feeId = feeId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getFeeId() {
		return feeId;
	}

	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long langaugeId) {
		this.languageId = langaugeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
