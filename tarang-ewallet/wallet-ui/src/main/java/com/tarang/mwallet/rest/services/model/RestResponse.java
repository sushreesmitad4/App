/**
 * 
 */
package com.tarang.mwallet.rest.services.model;

/**
 * @author kedarnathd
 *
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestResponse {
	private String responseId;
	private String responseMsg;

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
}

