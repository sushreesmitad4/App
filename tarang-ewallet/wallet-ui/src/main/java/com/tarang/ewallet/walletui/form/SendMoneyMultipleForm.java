package com.tarang.ewallet.walletui.form;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SendMoneyMultipleForm implements Serializable{

	private static final long serialVersionUID = 1L;
	private String[] emailId;
	private String[] amount;
	private Long[] currency;
	private String[] message;
	private Long[] destinationType;
	private int slabsize;
	
	//for file upload
	private String file;
	private MultipartFile fileData;
	
	
	public SendMoneyMultipleForm(){
	}
	
	
	public String[] getEmailId() {
		return emailId;
	}
	public void setEmailId(String[] emailId) {
		if( null == emailId ){
			this.emailId = new String[0];
		} else {
			this.emailId = new String[emailId.length];
			for(int i = 0; i < emailId.length; i++){
				this.emailId[i] = emailId[i];
			}
		}
	}
	public String[] getAmount() {
		return amount;
	}
	public void setAmount(String[] amount) {
		if( null == amount ){
			this.amount = new String[0];
		} else {
			this.amount = new String[amount.length];
			for(int i = 0; i < amount.length; i++){
				this.amount[i] = amount[i];
			}
		}
	}
	public Long[] getCurrency() {
		return currency;
	}
	public void setCurrency(Long[] currency) {
		if( null == currency ){
			this.currency = new Long[0];
		} else {
			this.currency = new Long[currency.length];
			for(int i = 0; i < currency.length; i++){
				this.currency[i] = currency[i];
			}
		}
	}
	public String[] getMessage() {
		return message;
	}
	public void setMessage(String[] message) {
		if( null == message ){
			this.message = new String[0];
		} else {
			this.message = new String[message.length];
			for(int i = 0; i < message.length; i++){
				this.message[i] = message[i];
			}
		}
	}
	public Long[] getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(Long[] destinationType) {
		if(  null  == destinationType){
			this.destinationType = new Long[0];
		} else {
			this.destinationType = new Long[destinationType.length];
			for(int i = 0; i < destinationType.length; i++){
				this.destinationType[i] = destinationType[i];
			}
		}
	}
	public int getSlabsize() {
		return slabsize;
	}
	public void setSlabsize(int slabsize) {
		this.slabsize = slabsize;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public MultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
	
	
		
	/*
	
	*//**
	 * @return the emailId
	 *//*
	public String[] getEmailId() {
		return emailId;
	}
	
	*//**
	 * @param emailId the emailId to set
	 *//*
	public void setEmailId(String emailId[]) {
		if( null == emailId ){
			this.emailId = new String[0];
		} else {
			this.emailId = new String[emailId.length];
			for(int i = 0; i < emailId.length; i++){
				this.emailId[i] = emailId[i];
			}
		}
	}
	
	*//**
	 * @return the amount
	 *//*
	public String[] getAmount() {
		return amount;
	}
	
	*//**
	 * @param amount the amount to set
	 *//*
	public void setAmount(String[] amount) {
		if( null == amount ){
			this.amount = new String[0];
		} else {
			this.amount = new String[amount.length];
			for(int i = 0; i < amount.length; i++){
				this.amount[i] = amount[i];
			}
		}
	}
	
	*//**
	 * @return the currency
	 *//*
	public Long[] getCurrency() {
		return currency;
	}
	
	*//**
	 * @param currency the currency to set
	 *//*
	public void setCurrency(Long[] currency) {
		if( null == currency ){
			this.currency = new Long[0];
		} else {
			this.currency = new Long[currency.length];
			for(int i = 0; i < currency.length; i++){
				this.currency[i] = currency[i];
			}
		}
	}
	
	*//**
	 * @return the message
	 *//*
	public String[] getMessage() {
		return message;
	}
	
	*//**
	 * @param message the message to set
	 *//*
	public void setMessage(String[] message) {
		if( null == message ){
			this.message = new String[0];
		} else {
			this.message = new String[message.length];
			for(int i = 0; i < message.length; i++){
				this.message[i] = message[i];
			}
		}
	}
	
	*//**
	 * @return the destinationType
	 *//*

	*//**
	 * @return the slabsize
	 *//*
	public int getSlabsize() {
		return slabsize;
	}
	
	*//**
	 * @return the destinationType
	 *//*
	public Long[] getDestinationType() {
		return destinationType;
	}
	
	*//**
	 * @param destinationType the destinationType to set
	 *//*
	public void setDestinationType(Long[] destinationType) {
		if(  null  == destinationType){
			this.destinationType = new Long[0];
		} else {
			this.destinationType = new Long[destinationType.length];
			for(int i = 0; i < destinationType.length; i++){
				this.destinationType[i] = destinationType[i];
			}
		}
	}
	
	*//**
	 * @param slabsize the slabsize to set
	 *//*
	public void setSlabsize(int slabsize) {
		this.slabsize = slabsize;
	}
	
	*//**
	 * @return the file
	 *//*
	public String getFile() {
		return file;
	}
	
	*//**
	 * @param file the file to set
	 *//*
	public void setFile(String file) {
		this.file = file;
	}
	
	*//**
	 * @return the fileData
	 *//*
	public MultipartFile getFileData() {
		return fileData;
	}
	
	*//**
	 * @param fileData the fileData to set
	 *//*
	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
	
	 *//**
     * @param fileData
     *            the fileData to set
     *//*
    public void setFileData(CommonsMultipartFile fileData) {
	this.fileData = fileData;
    }*/
   
}