/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Oct 24, 2012
 * @time    : 6:25:58 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class Menu {

	private Long id;
	
	private Long parentId;
	
	private Boolean isParent;
	
	private String menuName;
	
	private String pageUrl;
	
	private String pageName;
	
	private Boolean isDeleted;
	
	private Long displayOrder;

	public Menu(){
	}

	public Menu(Long id, Long parentId, Boolean isParent, String menuName){
		this.id = id;
		this.parentId = parentId;
		this.isParent = isParent;
		this.menuName = menuName;
	}
	
	public Menu(Long id, Long parentId, Boolean isParent, String menuName, 
			String pageName, String pageUrl, Boolean isDeleted, Long displayOrder){
		this.id = id;
		this.parentId = parentId;
		this.isParent = isParent;
		this.menuName = menuName;
		this.pageName = pageName;
		this.pageUrl = pageUrl;
		this.isDeleted = isDeleted;
		this.displayOrder = displayOrder;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}
}
