/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author vasanthar
 *
 */
public class FeeSlab implements Cloneable {
	
	private Long id;
	
	private Long feeId;
	
	private Double lowerLimit;
	
	private Double upperLimit;
	
	private Double fixedChargeSender;
	
	private Double percentageOfSender;
	
	private Double fixedChargeReceiver;
	
	private Double percentageOfReceiver;
	
	private Fee fee;


	public FeeSlab(){
	}
	
	public FeeSlab(Double lowerLimit, Double upperLimit, Double fixedChargeSender,
			Double percentageOfSender, Double fixedChargeReceiver, Double percentageOfReceiver) {
		
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.fixedChargeSender = fixedChargeSender;
		this.percentageOfSender = percentageOfSender;
		this.fixedChargeReceiver = fixedChargeReceiver;
		this.percentageOfReceiver = percentageOfReceiver;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public Double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Double upperLimit) {
		this.upperLimit = upperLimit;
	}

	public Double getFixedChargeSender() {
		return fixedChargeSender;
	}

	public void setFixedChargeSender(Double fixedChargeSender) {
		this.fixedChargeSender = fixedChargeSender;
	}

	public Double getPercentageOfSender() {
		return percentageOfSender;
	}

	public void setPercentageOfSender(Double percentageOfSender) {
		this.percentageOfSender = percentageOfSender;
	}

	public Double getFixedChargeReceiver() {
		return fixedChargeReceiver;
	}

	public void setFixedChargeReceiver(Double fixedChargeReceiver) {
		this.fixedChargeReceiver = fixedChargeReceiver;
	}

	public Double getPercentageOfReceiver() {
		return percentageOfReceiver;
	}

	public void setPercentageOfReceiver(Double percentageOfReceiver) {
		this.percentageOfReceiver = percentageOfReceiver;
	}

	public Long getFeeId() {
		return feeId;
	}

	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}	
	
	public Fee getFee() {
		return fee;
	}

	public void setFee(Fee fee) {
		this.fee = fee;
	}

	public Object clone() throws CloneNotSupportedException {
		FeeSlab slab = new FeeSlab();
		slab.setId(id);
		slab.setFee(getFee());
		slab.setLowerLimit(lowerLimit);
		slab.setUpperLimit(upperLimit);
		slab.setFixedChargeSender(fixedChargeSender);
		slab.setPercentageOfSender(percentageOfSender);
		slab.setFixedChargeReceiver(fixedChargeReceiver);
		slab.setPercentageOfReceiver(percentageOfReceiver);
		return slab;		
	}

}
