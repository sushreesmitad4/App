/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Oct 24, 2012
 * @time    : 6:38:25 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class MenuLocale {

	private Long id;

	private Long menuId;

	private Long languageId;

	private String menuName;

	public MenuLocale() {
	}

	public MenuLocale(Long id, String menuName) {
		this.id = id;
		this.menuName = menuName;
	}

	public MenuLocale(Long id, Long languageId, Long menuId, String menuName) {
		this.id = id;
		this.menuId = menuId;
		this.languageId = languageId;
		this.menuName = menuName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
}
