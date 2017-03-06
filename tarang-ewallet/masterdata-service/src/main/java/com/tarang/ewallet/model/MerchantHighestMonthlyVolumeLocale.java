package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class MerchantHighestMonthlyVolumeLocale {

	private Long id;

	private Long hmvId;

	private Long languageId;

	private String highestMonthlyVolume;

	public MerchantHighestMonthlyVolumeLocale() {
	}

	public MerchantHighestMonthlyVolumeLocale(Long id,
			String highestMonthlyVolume) {
		this.id = id;
		this.highestMonthlyVolume = highestMonthlyVolume;
	}

	public MerchantHighestMonthlyVolumeLocale(Long id, Long languageId, Long hmvId, String highestMonthlyVolume) {
		this.id = id;
		this.hmvId = hmvId;
		this.languageId = languageId;
		this.highestMonthlyVolume = highestMonthlyVolume;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHmvId() {
		return hmvId;
	}

	public void setHmvId(Long hmvId) {
		this.hmvId = hmvId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getHighestMonthlyVolume() {
		return highestMonthlyVolume;
	}

	public void setHighestMonthlyVolume(String highestMonthlyVolume) {
		this.highestMonthlyVolume = highestMonthlyVolume;
	}

}
