package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class FrequencyLocale {

	private Long id;

	private Long frequencyId;

	private Long languageId;

	private String name;

	public FrequencyLocale() {
	}

	public FrequencyLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public FrequencyLocale(Long id, Long languageId, Long frequencyId, String name) {
		this.id = id;
		this.frequencyId = frequencyId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Long frequencyId) {
		this.frequencyId = frequencyId;
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
