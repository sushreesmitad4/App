package com.tarang.ewallet.model;

public class ReceiveMoneyStatusLocale {
	
	private Long id;

	private Long receivemoneystatusId;

	private Long languageId;

	private String name;
	
	public ReceiveMoneyStatusLocale(){
		
	}
	
	public ReceiveMoneyStatusLocale(Long id, Long languageId, Long receivemoneystatusId, String name){
		this.id = id;
		this.languageId = languageId;
		this.receivemoneystatusId = receivemoneystatusId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReceivemoneystatusId() {
		return receivemoneystatusId;
	}

	public void setReceivemoneystatusId(Long receivemoneystatusId) {
		this.receivemoneystatusId = receivemoneystatusId;
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
