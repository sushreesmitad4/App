package com.tarang.ewallet.walletui.form;

import com.tarang.ewallet.dto.PreferencesDto;

public class PereferencesForm {

	private Long id;
	private Long language;
	private Long currency;
	private String userType;
	
	
	public Long getLanguage() {
		return language;
	}
	public void setLanguage(Long language) {
		this.language = language;
	}
	public Long getCurrency() {
		return currency;
	}
	public void setCurrency(Long currency) {
		this.currency = currency;
	}
	
	
	public PreferencesDto getPreferencesDto(){
		PreferencesDto preferencesDto = new PreferencesDto();
		preferencesDto.setId(id);
		preferencesDto.setLanguage(language);
		preferencesDto.setCurrency(currency);
		return preferencesDto;
	}
	public void setPreferencesDto(PreferencesDto preferencesDto){
		id=preferencesDto.getId();
		language = preferencesDto.getLanguage();
		currency=preferencesDto.getCurrency();
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}