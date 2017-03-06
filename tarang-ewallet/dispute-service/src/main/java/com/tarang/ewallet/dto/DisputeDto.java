/**
 * 
 */
package com.tarang.ewallet.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.tarang.ewallet.model.Dispute;


/**
 * @author vasanthar
 *
 */
public class DisputeDto extends Dispute implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private BigDecimal txnid;
	
	private BigDecimal disputeid;
	
	private String payeremailid;
	
	private String payeeemailid;
	
	private double transactionamount;
	
	private String transactioncurrency;
	
	private String statusname;
	
	private BigDecimal statusid;

	private Date disputelogdate;
	
	private String disputetype;
	
	private BigDecimal disputetypeid;
	
	private Date transactiondate;
	
	private Double payeramount;
	
	private String payercurrency;

	private Double requestamount;
	
	private String requestcurrency;
	
	private Double approvedamount;
	
	private String approvedcurrency;
	
	private String message;
	
	private Date updationdate;
	
	private Date creationdate;
	
	private Date messagecreationdate;
	
	private BigDecimal creator;
	
	private String transationDateAsString;
	
	private Long languageId;
	
	private String payerName;
	
	private String payeeName;
	
	private String updatedby;
	
	/**
	 * currencyCode to hold currency name 
	 */
	private String currencyCode;
	
	
	private List<DisputeMessageDto> dtomessages;
	
	public DisputeDto(){
	}
	
	public BigDecimal getDisputeid() {
		return disputeid;
	}

	public void setDisputeid(BigDecimal disputeid) {
		this.disputeid = disputeid;
	}

	public String getPayeremailid() {
		return payeremailid;
	}

	public void setPayeremailid(String payeremailid) {
		this.payeremailid = payeremailid;
	}

	public String getPayeeemailid() {
		return payeeemailid;
	}

	public void setPayeeemailid(String payeeemailid) {
		this.payeeemailid = payeeemailid;
	}

	public double getTransactionamount() {
		return transactionamount;
	}

	public void setTransactionamount(double transactionamount) {
		this.transactionamount = transactionamount;
	}

	public String getTransactioncurrency() {
		return transactioncurrency;
	}

	public void setTransactioncurrency(String transactioncurrency) {
		this.transactioncurrency = transactioncurrency;
	}

	public Date getDisputelogdate() {
		return disputelogdate;
	}

	public void setDisputelogdate(Date disputelogdate) {
		this.disputelogdate = disputelogdate;
	}

	public String getDisputetype() {
		return disputetype;
	}

	public void setDisputetype(String disputetype) {
		this.disputetype = disputetype;
	}

	public Date getTransactiondate() {
		return transactiondate;
	}

	public void setTransactiondate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}

	public Double getRequestamount() {
		return requestamount;
	}

	public void setRequestamount(Double requestamount) {
		this.requestamount = requestamount;
	}

	public Double getApprovedamount() {
		return approvedamount;
	}

	public void setApprovedamount(Double approvedamount) {
		this.approvedamount = approvedamount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessagecreationdate() {
		return messagecreationdate;
	}

	public void setMessagecreationdate(Date messagecreationdate) {
		this.messagecreationdate = messagecreationdate;
	}
	
	public BigDecimal getCreator() {
		return creator;
	}

	public void setCreator(BigDecimal creator) {
		this.creator = creator;
	}

	public String getRequestcurrency() {
		return requestcurrency;
	}

	public void setRequestcurrency(String requestcurrency) {
		this.requestcurrency = requestcurrency;
	}

	public String getApprovedcurrency() {
		return approvedcurrency;
	}

	public void setApprovedcurrency(String approvedcurrency) {
		this.approvedcurrency = approvedcurrency;
	}
	
	public Date getUpdationdate() {
		return updationdate;
	}

	public void setUpdationdate(Date updationdate) {
		this.updationdate = updationdate;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Double getPayeramount() {
		return payeramount;
	}

	public void setPayeramount(Double payeramount) {
		this.payeramount = payeramount;
	}

	public String getPayercurrency() {
		return payercurrency;
	}

	public void setPayercurrency(String payercurrency) {
		this.payercurrency = payercurrency;
	}

	

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public List<DisputeMessageDto> getDtomessages() {
		return dtomessages;
	}

	public void setDtomessages(List<DisputeMessageDto> dtomessages) {
		this.dtomessages = dtomessages;
	}

	public BigDecimal getTxnid() {
		return txnid;
	}

	public void setTxnid(BigDecimal txnid) {
		this.txnid = txnid;
	}

	public String getTransationDateAsString() {
		return transationDateAsString;
	}

	public void setTransationDateAsString(String transationDateAsString) {
		this.transationDateAsString = transationDateAsString;
	}

	public Long getStatusid() {
		return statusid.longValue();
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public Long getDisputetypeid() {
		return disputetypeid.longValue();
	}

	public void setDisputetypeid(BigDecimal disputetypeid) {
		this.disputetypeid = disputetypeid;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	
	
	
}
