package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class MerchantAvgTxAmount {

	private Long id;

	private String avgAmount;

	public MerchantAvgTxAmount() {
	}

	public MerchantAvgTxAmount(Long id, String avgAmount) {
		this.id = id;
		this.avgAmount = avgAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAvgAmount() {
		return avgAmount;
	}

	public void setAvgAmount(String avgAmount) {
		this.avgAmount = avgAmount;
	}

}
