package com.tarang.ewallet.model;

import java.io.Serializable;
/**
 * @author rajasekhar
 * 
 */
public class Merchant implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long businessInformation;
	private Long customerServicePhone;
	private String customerServiceEmail;
	private Long authenticationId;
	private Long businessOwnerInformation;
	private Long pageInfoId;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the businessInformation
	 */
	public Long getBusinessInformation() {
		return businessInformation;
	}
	/**
	 * @param businessInformation the businessInformation to set
	 */
	public void setBusinessInformation(Long businessInformation) {
		this.businessInformation = businessInformation;
	}
	/**
	 * @return the customerServicePhone
	 */
	public Long getCustomerServicePhone() {
		return customerServicePhone;
	}
	/**
	 * @param customerServicePhone the customerServicePhone to set
	 */
	public void setCustomerServicePhone(Long customerServicePhone) {
		this.customerServicePhone = customerServicePhone;
	}
	/**
	 * @return the customerServiceEmail
	 */
	public String getCustomerServiceEmail() {
		return customerServiceEmail;
	}
	/**
	 * @param customerServiceEmail the customerServiceEmail to set
	 */
	public void setCustomerServiceEmail(String customerServiceEmail) {
		this.customerServiceEmail = customerServiceEmail;
	}
	/**
	 * @return the authenticationId
	 */
	public Long getAuthenticationId() {
		return authenticationId;
	}
	/**
	 * @param authenticationId the authenticationId to set
	 */
	public void setAuthenticationId(Long authenticationId) {
		this.authenticationId = authenticationId;
	}
	/**
	 * @return the businessOwnerInformation
	 */
	public Long getBusinessOwnerInformation() {
		return businessOwnerInformation;
	}
	/**
	 * @param businessOwnerInformation the businessOwnerInformation to set
	 */
	public void setBusinessOwnerInformation(Long businessOwnerInformation) {
		this.businessOwnerInformation = businessOwnerInformation;
	}
	
	public Long getPageInfoId() {
		return pageInfoId;
	}
	
	public void setPageInfoId(Long pageInfoId) {
		this.pageInfoId = pageInfoId;
	}
	
	
}
