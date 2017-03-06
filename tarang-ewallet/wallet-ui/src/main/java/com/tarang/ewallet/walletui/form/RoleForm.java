package com.tarang.ewallet.walletui.form;



public class RoleForm {
	
	private Long id;
	
	private String name;
	
	private String description;

	private String menuDetails;
	
	private String nameHidden;
	
	private String decHidden;
	
	public RoleForm(){
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
	
	public void setNameHidden(String nameHidden){
		this.nameHidden=nameHidden;
	}
	
	public String  getNameHidden(){
		return nameHidden;
	}
	
	public void setDecHidden(String decHidden){
		this.decHidden=decHidden;
	}
	
	public String getDecHidden(){
		return decHidden;
	}
	
}