package com.tarang.ewallet.model;

public class TransactionStatus {
	
	private Long id;

	private String name;
	
	public TransactionStatus(){
		
	}
	
	public TransactionStatus(Long id, String name){
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
