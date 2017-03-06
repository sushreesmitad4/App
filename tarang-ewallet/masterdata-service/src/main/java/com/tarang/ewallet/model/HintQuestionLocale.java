/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Oct 22, 2012
 * @time    : 9:30:14 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class HintQuestionLocale {

	private Long id;

	private Long hintId;

	private Long languageId;

	private String name;

	public HintQuestionLocale() {
	}

	public HintQuestionLocale(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public HintQuestionLocale(Long id, Long languageId, Long hintId, String name) {
		this.id = id;
		this.hintId = hintId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHintId() {
		return hintId;
	}

	public void setHintId(Long hintId) {
		this.hintId = hintId;
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
