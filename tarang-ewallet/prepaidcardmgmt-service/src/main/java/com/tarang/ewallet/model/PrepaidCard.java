/**
 * 
 */
package com.tarang.ewallet.model;

/**
 * @author  : prasadj
 * @date    : Nov 26, 2012
 * @time    : 4:06:35 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class PrepaidCard extends PrepaidCardAccount{

	private static final long serialVersionUID = 1L;

	private Long cardType;
	
	private String cardNumber;
	
	private String cardExpairyDate;
	
	private Long addressId;
	
	private Boolean sameAsProfileAddress;

	private String cardBin;
	
	private String cvv;
	
	public PrepaidCard(){
	}

	public Long getCardType() {
		return cardType;
	}

	public void setCardType(Long cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpairyDate() {
		return cardExpairyDate;
	}

	public void setCardExpairyDate(String cardExpairyDate) {
		this.cardExpairyDate = cardExpairyDate;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Boolean getSameAsProfileAddress() {
		return sameAsProfileAddress;
	}

	public void setSameAsProfileAddress(Boolean sameAsProfileAddress) {
		this.sameAsProfileAddress = sameAsProfileAddress;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
}
