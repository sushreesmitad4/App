package com.tarang.ewallet.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author : kedarnath
 * @Date : Dec 12, 2012
 * @Time : 4:51:52 PM
 * @Version : 1.0
 * @Comments: Model object to hold password history information
 */
public class PasswordHistory implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long authentication;
	private String password;
	private Date creationDate;
	
	public PasswordHistory(){
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAuthentication() {
		return authentication;
	}
	public void setAuthentication(Long authentication) {
		this.authentication = authentication;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
