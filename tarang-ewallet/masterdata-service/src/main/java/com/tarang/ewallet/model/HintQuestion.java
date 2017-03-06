/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Oct 22, 2012
 * @time    : 9:28:36 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class HintQuestion {

	private Long id;

	private String name;

	public HintQuestion() {
	}

	public HintQuestion(Long id, String name) {
		this.id = id;
		this.name = name;
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

}
