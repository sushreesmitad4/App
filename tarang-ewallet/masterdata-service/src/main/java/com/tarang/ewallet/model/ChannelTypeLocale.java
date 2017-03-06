package com.tarang.ewallet.model;

public class ChannelTypeLocale {

	private Long id;

	private Long channelTypeId;

	private Long languageId;

	private String name;

	public ChannelTypeLocale() {
	}

	public ChannelTypeLocale(Long id, Long languageId, Long channelTypeId,
			String name) {
		this.id = id;
		this.channelTypeId = channelTypeId;
		this.languageId = languageId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChannelTypeId() {
		return channelTypeId;
	}

	public void setChannelTypeId(Long channelTypeId) {
		this.channelTypeId = channelTypeId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
