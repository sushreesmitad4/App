/**
 * 
 */
package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

import com.tarang.ewallet.util.DateConvertion;


/**
 * @author  : prasadj
 * @date    : Jun 3, 2013
 * @time    : 5:10:37 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class SendMoneyJobSummaryModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long authId;
	
	private Long sendMoneyId;
	
	private Date updatedDate;
	
	private Date startDate;
	
	private Date endDate;
	
	private Date newStartDate;
	
	private Date newEndDate;
	
	private String sendMoneyTxnStatus;
	
    private Date creationDate;
	
	private String userJobName;
	
	private Date recentFiredTime; 
	
	private Long recentFiredStatus;
	
	private Date nextOccurrenceDate;
	
	public SendMoneyJobSummaryModel(){
	}

	public Date getNewStartDate() {
		return newStartDate;
	}

	public void setNewStartDate(Date newStartDate) {
		this.newStartDate = newStartDate;
	}

	public Date getNewEndDate() {
		return newEndDate;
	}

	public void setNewEndDate(Date newEndDate) {
		this.newEndDate = newEndDate;
	}

	public Date getNextOccurrenceDate() {
		return nextOccurrenceDate;
	}

	public void setNextOccurrenceDate(Date nextOccurrenceDate) {
		this.nextOccurrenceDate = nextOccurrenceDate;
	}

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public Long getSendMoneyId() {
		return sendMoneyId;
	}

	public void setSendMoneyId(Long sendMoneyId) {
		this.sendMoneyId = sendMoneyId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public String getStartDateStr() {
		return DateConvertion.dateToString(startDate);
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getEndDateStr() {
		return DateConvertion.dateToString(endDate);
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getCreationDateStr() {
		return DateConvertion.dateToString(creationDate);
	}

	public String getUserJobName() {
		return userJobName;
	}

	public void setUserJobName(String userJobName) {
		this.userJobName = userJobName;
	}

	public Date getRecentFiredTime() {
		return recentFiredTime;
	}

	public void setRecentFiredTime(Date recentFiredTime) {
		this.recentFiredTime = recentFiredTime;
	}
	
	public String getRecentFiredTimeStr() {
		return DateConvertion.dateToString(recentFiredTime);
	}

	public Long getRecentFiredStatus() {
		return recentFiredStatus;
	}

	public void setRecentFiredStatus(Long recentFiredStatus) {
		this.recentFiredStatus = recentFiredStatus;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getUpdatedDateStr() {
		return DateConvertion.dateToString(updatedDate);
	}

	public String getSendMoneyTxnStatus() {
		return sendMoneyTxnStatus;
	}

	public void setSendMoneyTxnStatus(String sendMoneyTxnStatus) {
		this.sendMoneyTxnStatus = sendMoneyTxnStatus;
	}

}
