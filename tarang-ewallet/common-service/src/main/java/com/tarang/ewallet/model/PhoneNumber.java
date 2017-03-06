package com.tarang.ewallet.model;

import java.io.Serializable;
/**
 * @author rajasekhar
 * 
 */
public class PhoneNumber implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String code;
	
	private String pnumber;
	
	public PhoneNumber(){
	}
	
	public PhoneNumber(String code, String pnumber){
		this.code = code;
		this.pnumber = pnumber;
	}
	
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the pnumber
	 */
	public String getPnumber() {
		return pnumber;
	}
	/**
	 * @param pnumber the pnumber to set
	 */
	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}

}
