package com.tarang.ewallet.model;

public class PGCode {
	private Long id;
	private String pgCode;
	private String pgMessage;

	public PGCode() {
	}

	public PGCode(Long id, String pgCode, String pgMessage) {
		this.id = id;
		this.pgCode = pgCode;
		this.pgMessage = pgMessage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPgCode() {
		return pgCode;
	}

	public void setPgCode(String pgCode) {
		this.pgCode = pgCode;
	}

	public String getPgMessage() {
		return pgMessage;
	}

	public void setPgMessage(String pgMessage) {
		this.pgMessage = pgMessage;
	}

}
