/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Oct 22, 2012
 * @time    : 2:11:04 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class TitleLocale {

	private Long id;

	private Long titleId;

	private Long languageId;

	private String name;

	public TitleLocale() {
	}

	public TitleLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public TitleLocale(Long id, Long languageId, Long titleId, String name) {
		this.id = id;
		this.languageId = languageId;
		this.titleId = titleId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
