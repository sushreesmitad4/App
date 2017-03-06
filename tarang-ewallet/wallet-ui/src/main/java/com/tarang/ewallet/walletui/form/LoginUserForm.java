/**
 * 
 */
package com.tarang.ewallet.walletui.form;

import java.io.Serializable;

/**
 * @Author : Kedarnath
 * @Date : Oct 19, 2012
 * @Time : 12:43:09 PM
 * @Version : 1.0
 * @Comments:
 */
public class LoginUserForm implements Serializable {

  // Property key for serialVersionUID
  private static final long serialVersionUID = 1107657677269075418L;

  // Property key for email
  private String email;

  // Property key for password
  private String password;
  
  // Property key for userRole
  private String userType;
  
  //Property to display Merchant Name as a label in login page 
  private String merchantName;
  

  //Property to display Amount as a label in login page 
  private String amount;
  
  //Property to display Currency as a label in login page 
  private String currency;
  /**
   * Returns the email value
   * 
   * @return the email
   */
  
  private String code;
  /**
   * to display CheckBox at wallet payment login
   */
  private Boolean expCheckOut;
  
  /**
   * to display address at merchant client side
   */
  private Boolean onlinePayment;
  
  private String pathImage;


public String getEmail() {
    return email;
  }

  /**
   * Set the email value
   * 
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the password value
   * 
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set the password value
   * 
   * @param password
   *          the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
  /**
   * Returns the userRole value
   * 
   * @return the userRole
   */
  public String getUserType() {
    return userType;
  }

  /**
   * Set the userType value
   * 
   * @param userType
   *          the userType to set
   */
  public void setUserType(String userType) {
    this.userType = userType;
  }

	public String getMerchantName() {
		return merchantName;
	}
	
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	  
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Boolean getExpCheckOut() {
		return expCheckOut;
	}

	public void setExpCheckOut(Boolean expCheckOut) {
		this.expCheckOut = expCheckOut;
	}

	public Boolean getOnlinePayment() {
		return onlinePayment;
	}

	public void setOnlinePayment(Boolean onlinePayment) {
		this.onlinePayment = onlinePayment;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

}
