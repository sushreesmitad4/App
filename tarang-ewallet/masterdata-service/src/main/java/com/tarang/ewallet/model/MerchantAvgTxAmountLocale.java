package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class MerchantAvgTxAmountLocale {

	private Long id;

	private Long matId;

	private Long languageId;

	private String avgAmount;

	public MerchantAvgTxAmountLocale() {
	}

	public MerchantAvgTxAmountLocale(Long id, String avgAmount) {
		this.id = id;
		this.avgAmount = avgAmount;
	}

	public MerchantAvgTxAmountLocale(Long id, Long languageId, Long matId, String avgAmount) {
		this.id = id;
		this.matId = matId;
		this.languageId = languageId;
		this.avgAmount = avgAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMatId() {
		return matId;
	}

	public void setMatId(Long matId) {
		this.matId = matId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getAvgAmount() {
		return avgAmount;
	}

	public void setAvgAmount(String avgAmount) {
		this.avgAmount = avgAmount;
	}

}
