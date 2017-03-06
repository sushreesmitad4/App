package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class MerchantHighestMonthlyVolume {

	private Long id;

	private String highestMonthlyVolume;

	public MerchantHighestMonthlyVolume() {
	}

	public MerchantHighestMonthlyVolume(Long id, String highestMonthlyVolume) {
		this.id = id;
		this.highestMonthlyVolume = highestMonthlyVolume;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHighestMonthlyVolume() {
		return highestMonthlyVolume;
	}

	public void setHighestMonthlyVolume(String highestMonthlyVolume) {
		this.highestMonthlyVolume = highestMonthlyVolume;
	}

}
