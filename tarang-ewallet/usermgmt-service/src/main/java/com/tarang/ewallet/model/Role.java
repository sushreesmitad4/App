/**
 * 
 */
package com.tarang.ewallet.model;

import java.io.Serializable;

/**
 * @author prasadj
 *
 */
public class Role implements Serializable,Cloneable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String description;
	
	private String menuDetails;
	
	public Role(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMenuDetails() {
		return menuDetails;
	}

	public void setMenuDetails(String menuDetails) {
		this.menuDetails = menuDetails;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();		
	  }

}
