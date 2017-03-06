package com.tarang.ewallet.walletui.form;

import java.io.Serializable;

/**
 * @Author : kedarnath
 * @Date : Oct 31, 2012
 * @Time : 3:27:50 PM
 * @Version : 1.0
 * @Comments: Change password dto class for change password module.
 */
public class ChangePasswordForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String emailId;
	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;
	private String userType;
	private String mode;
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	
}
