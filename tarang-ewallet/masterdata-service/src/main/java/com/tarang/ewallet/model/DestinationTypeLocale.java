package com.tarang.ewallet.model;

public class DestinationTypeLocale {

	private Long id;

	private Long destinationtypeId;

	private Long languageId;

	private String name;
	
	public DestinationTypeLocale(){
		
	}
	
	public DestinationTypeLocale(Long id, Long languageId, Long destinationtypeId, String name) {
		this.id = id;
		this.destinationtypeId = destinationtypeId;
		this.languageId = languageId;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the destinationtypeId
	 */
	public Long getDestinationtypeId() {
		return destinationtypeId;
	}

	/**
	 * @param destinationtypeId the destinationtypeId to set
	 */
	public void setDestinationtypeId(Long destinationtypeId) {
		this.destinationtypeId = destinationtypeId;
	}

	/**
	 * @return the languageId
	 */
	public Long getLanguageId() {
		return languageId;
	}

	/**
	 * @param languageId the languageId to set
	 */
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
