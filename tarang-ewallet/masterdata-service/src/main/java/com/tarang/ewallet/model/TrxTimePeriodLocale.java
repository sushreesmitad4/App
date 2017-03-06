package com.tarang.ewallet.model;

public class TrxTimePeriodLocale {
	private Long id;

	private Long trxTimePeriodId;

	private Long languageId;

	private String name;
	
	public TrxTimePeriodLocale(){
	}
	
	public TrxTimePeriodLocale(Long id, Long languageId, Long trxTimePeriodId, String name) {
		this.id = id;
		this.trxTimePeriodId = trxTimePeriodId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTrxTimePeriodId() {
		return trxTimePeriodId;
	}

	public void setTrxTimePeriodId(Long trxTimePeriodId) {
		this.trxTimePeriodId = trxTimePeriodId;
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
