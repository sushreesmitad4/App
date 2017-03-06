package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class CardTypeLocale {

	private Long id;

	private Long cardTypeId;

	private Long languageId;

	private String name;

	public CardTypeLocale() {
	}

	public CardTypeLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public CardTypeLocale(Long id, Long languageId, Long cardTypeId, String name) {
		this.id = id;
		this.cardTypeId = cardTypeId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(Long cardTypeId) {
		this.cardTypeId = cardTypeId;
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
