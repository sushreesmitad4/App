/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Nov 20, 2012
 * @time    : 8:14:01 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class EmailTemplate {

	private Long languageId;
	
	private String name;
	
	private String subject;
	
	private String greeting;
	
	private String body;
	
	private String signature;
	
	private String disclaimer;
	
	public EmailTemplate(){
	}

	public Long getLanguage() {
		return languageId;
	}

	public void setLanguage(Long language) {
		this.languageId = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	
	public String constructMail(){
		String lineBreak = "<br>";
		String startHtml = "<html>";
		String endHtml = "</html>";
		return startHtml + greeting + lineBreak + body + lineBreak + lineBreak + signature + lineBreak + disclaimer + lineBreak + endHtml;
	}
	
}
