/**
 * 
 */
package com.tarang.ewallet.dto;


/**
 * @author  : prasadj
 * @date    : Feb 22, 2013
 * @time    : 2:52:30 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class DisputeMessageDto{

	private String message;
	
	private String creationDate;
	
	private Long creator;
	
	public DisputeMessageDto(){
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}
}
