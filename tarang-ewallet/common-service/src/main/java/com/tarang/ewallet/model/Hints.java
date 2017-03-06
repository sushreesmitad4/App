package com.tarang.ewallet.model;

import java.io.Serializable;
/**
 * @author rajasekhar
 * 
 */
public class Hints implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long hintQuestion1;
	private String hintAnswer1;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the hintQuestion1
	 */
	public Long getHintQuestion1() {
		return hintQuestion1;
	}
	/**
	 * @param hintQuestion1 the hintQuestion1 to set
	 */
	public void setHintQuestion1(Long hintQuestion1) {
		this.hintQuestion1 = hintQuestion1;
	}
	/**
	 * @return the hintAnswer1
	 */
	public String getHintAnswer1() {
		return hintAnswer1;
	}
	/**
	 * @param hintAnswer1 the hintAnswer1 to set
	 */
	public void setHintAnswer1(String hintAnswer1) {
		this.hintAnswer1 = hintAnswer1;
	}
}
