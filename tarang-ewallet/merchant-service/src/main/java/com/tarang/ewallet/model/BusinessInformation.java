package com.tarang.ewallet.model;

import java.io.Serializable;
/**
 * @author rajasekhar
 * 
 */
public class BusinessInformation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long ownerType;
	private String legalName;
	private Long phone;
	private Long category;
	private Long subCategory;
	private Long address;
	private String establishedMonth;
	private String establishedYear;
	private Long currency;
	private Long averageTxnAmount;
	private Long heighestMonthlyVolume;
	private Long percentageOfAnnualRevenue;
	private String website;
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
	 * @return the ownerType
	 */
	public Long getOwnerType() {
		return ownerType;
	}
	/**
	 * @param ownerType the ownerType to set
	 */
	public void setOwnerType(Long ownerType) {
		this.ownerType = ownerType;
	}
	/**
	 * @return the legalName
	 */
	public String getLegalName() {
		return legalName;
	}
	/**
	 * @param legalName the legalName to set
	 */
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	/**
	 * @return the phone
	 */
	public Long getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	/**
	 * @return the category
	 */
	public Long getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Long category) {
		this.category = category;
	}
	/**
	 * @return the address
	 */
	public Long getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(Long address) {
		this.address = address;
	}
	/**
	 * @return the establishedMonth
	 */
	public String getEstablishedMonth() {
		return establishedMonth;
	}
	/**
	 * @param establishedMonth the establishedMonth to set
	 */
	public void setEstablishedMonth(String establishedMonth) {
		this.establishedMonth = establishedMonth;
	}
	/**
	 * @return the establishedYear
	 */
	public String getEstablishedYear() {
		return establishedYear;
	}
	/**
	 * @param establishedYear the establishedYear to set
	 */
	public void setEstablishedYear(String establishedYear) {
		this.establishedYear = establishedYear;
	}
	
	/**
	 * @return the averageTxnAmount
	 */
	public Long getAverageTxnAmount() {
		return averageTxnAmount;
	}
	/**
	 * @param averageTxnAmount the averageTxnAmount to set
	 */
	public void setAverageTxnAmount(Long averageTxnAmount) {
		this.averageTxnAmount = averageTxnAmount;
	}
	/**
	 * @return the heighestMonthlyVolume
	 */
	public Long getHeighestMonthlyVolume() {
		return heighestMonthlyVolume;
	}
	/**
	 * @param heighestMonthlyVolume the heighestMonthlyVolume to set
	 */
	public void setHeighestMonthlyVolume(Long heighestMonthlyVolume) {
		this.heighestMonthlyVolume = heighestMonthlyVolume;
	}
	/**
	 * @return the percentageOfAnnualRevenue
	 */
	public Long getPercentageOfAnnualRevenue() {
		return percentageOfAnnualRevenue;
	}
	/**
	 * @param percentageOfAnnualRevenue the percentageOfAnnualRevenue to set
	 */
	public void setPercentageOfAnnualRevenue(Long percentageOfAnnualRevenue) {
		this.percentageOfAnnualRevenue = percentageOfAnnualRevenue;
	}
	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @return the subcategory
	 */
	/**
	 * @return the subCategory
	 */
	public Long getSubCategory() {
		return subCategory;
	}
	/**
	 * @param subCategory the subCategory to set
	 */
	public void setSubCategory(Long subCategory) {
		this.subCategory = subCategory;
	}

	public Long getCurrency() {
		return currency;
	}
	public void setCurrency(Long currency) {
		this.currency = currency;
	}
	
}
