/**
 * 
 */
package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kedarnathd
 *
 */
public class SendMoneyRecurring implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long sendMoneyId;
	
	private String userJobName;

	private Date fromDate;
	
	private Date toDate;
	
	private Long frequency;
	
	private Integer totalOccurences;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSendMoneyId() {
		return sendMoneyId;
	}
	public void setSendMoneyId(Long sendMoneyId) {
		this.sendMoneyId = sendMoneyId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Long getFrequency() {
		return frequency;
	}
	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}
	public Integer getTotalOccurences() {
		return totalOccurences;
	}
	public void setTotalOccurences(Integer totalOccurences) {
		this.totalOccurences = totalOccurences;
	}
	public String getUserJobName() {
		return userJobName;
	}
	public void setUserJobName(String userJobName) {
		this.userJobName = userJobName;
	}
}
