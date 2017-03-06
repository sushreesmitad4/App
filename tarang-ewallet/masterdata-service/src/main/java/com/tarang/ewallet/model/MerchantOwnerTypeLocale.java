package com.tarang.ewallet.model;

/**
 * @author prasadj
 *
 */
public class MerchantOwnerTypeLocale {

	private Long id;

	private Long motId;

	private Long languageId;

	private String type;

	public MerchantOwnerTypeLocale() {
	}

	public MerchantOwnerTypeLocale(Long id, String type) {
		this.id = id;
		this.type = type;
	}

	public MerchantOwnerTypeLocale(Long id, Long languageId, Long motId, String type) {
		this.id = id;
		this.motId = motId;
		this.languageId = languageId;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMotId() {
		return motId;
	}

	public void setMotId(Long motId) {
		this.motId = motId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
