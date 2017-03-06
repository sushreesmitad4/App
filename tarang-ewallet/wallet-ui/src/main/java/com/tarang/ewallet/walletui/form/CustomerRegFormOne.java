/**
 * 
 */
package com.tarang.ewallet.walletui.form;

/**
 * @author  : prasadj
 * @date    : Oct 22, 2012
 * @time    : 11:28:17 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CustomerRegFormOne {

	private String emailId;
	
	private String cemailId;

	private String password;

	private String cpassword;
	
	private Long hintQuestion1;

	private String answer1;

	private String securityCode;
	
	
	//for back button
	private String mode;
	
	public CustomerRegFormOne(){
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCemailId() {
		return cemailId;
	}

	public void setCemailId(String cemailId) {
		this.cemailId = cemailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public Long getHintQuestion1() {
		return hintQuestion1;
	}

	public void setHintQuestion1(Long hintQuestion1) {
		this.hintQuestion1 = hintQuestion1;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	
}
