package com.tarang.ewallet.model;

public class TransactionStatusLocale {
	
	private Long id;

	private Long transactionstatusId;

	private Long languageId;

	private String name;
	
	public TransactionStatusLocale(){
		
	}
	
	public TransactionStatusLocale(Long id, Long languageId, Long transactionstatusId, String name){
		this.id = id;
		this.languageId = languageId;
		this.transactionstatusId = transactionstatusId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransactionstatusId() {
		return transactionstatusId;
	}

	public void setTransactionstatusId(Long transactionstatusId) {
		this.transactionstatusId = transactionstatusId;
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
