/**
 * 
 */
package com.tarang.ewallet.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.tarang.ewallet.util.DateConvertion;


/**
 * @author  : prasadj
 * @date    : Jan 18, 2013
 * @time    : 2:48:50 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CustomerReportModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal transactionid;
	
	private String selfcurrency;
	
	private String emailid;
	
	private String otherscurrency;
	
	private String transactiontype;
	
	private String status;
	
	private Date updateddate;
	
	private Double amount;
	
	private Double deductions;

	/* These fields are for ADMIN Reports */
	private String payeremail;
	
	private String payertype;
	
	private Double payeramount;
	
	private String payercurrency;
	
	private Double payerbalance;
	
	private String payeeemail;
	
	private String payeetype;
	
	private Double payeeamount;
	
	private String payeecurrency;
	
	private Double payeebalance;
	
	private String usertype;
	
	private Double amountother;
	
	private Double amountself;
	
	private Double selfbalance;
	
	private String country;
	
	private Double usdcurrency;
	
	private Double yencurrency;
	
	private Double bahtcurrency;
	
	private BigDecimal sendmoneyid;
	
   /**
	* feeType to hold sender or receiver
	*/
	private String feetype;
	private Date paydate;
	private BigDecimal parentid;
	private Boolean reversal;
	
	
	/**
	 * these fields for merchantCommission.
	 */
	private Double feetax;
	
	private String istype;
	
	private Date disputedate;
	
	private String disputetype;
	
	private String disputestatus;
	
   /**
	* these fields for last x unused accounts.
	*/
	private BigDecimal authid;
	
	private String personororganizationname;
	
	private Date lasttransactiondate;
	
	private Date creationdate;
	
   /**
	* To hold balance status as DR or CR
	*/
	private String balancestatus;
	
   /**
	* these fields to hold string converged amount and deduction
	*/
	private String displayamount;
	private String displaydeduction;
	private String displayPayeeBalance;

	public BigDecimal getAuthid() {
		return authid;
	}

	public void setAuthid(BigDecimal authid) {
		this.authid = authid;
	}

	public String getPersonororganizationname() {
		return personororganizationname;
	}

	public void setPersonororganizationname(String personororganizationname) {
		this.personororganizationname = personororganizationname;
	}

	public Date getLasttransactiondate() {
		return lasttransactiondate;
	}

	public void setLasttransactiondate(Date lasttransactiondate) {
		this.lasttransactiondate = lasttransactiondate;
	}
	
	public String getGriddisplaylasttransactiondate() {
		return DateConvertion.dateTimeToString(lasttransactiondate);
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	
	public String getGriddisplaycreationdate() {
		return DateConvertion.dateTimeToString(creationdate);
	}

	public Date getDisputedate() {
		return disputedate;
	}

	public void setDisputedate(Date disputedate) {
		this.disputedate = disputedate;
	}
	
	public String getGriddisplaydisputedate() {
		return DateConvertion.dateTimeToString(disputedate);
	}
	
	public String getDisputetype() {
		return disputetype;
	}

	public void setDisputetype(String disputetype) {
		this.disputetype = disputetype;
	}

	public String getDisputestatus() {
		return disputestatus;
	}

	public void setDisputestatus(String disputestatus) {
		this.disputestatus = disputestatus;
	}

	public Double getFeetax() {
		return feetax;
	}

	public void setFeetax(Double feetax) {
		this.feetax = feetax;
	}

	public String getIstype() {
		return istype;
	}

	public void setIstype(String istype) {
		this.istype = istype;
	}

	public String getSelfcurrency() {
		return selfcurrency;
	}

	public void setSelfcurrency(String selfcurrency) {
		this.selfcurrency = selfcurrency;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getOtherscurrency() {
		return otherscurrency;
	}

	public void setOtherscurrency(String otherscurrency) {
		this.otherscurrency = otherscurrency;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}
	
	public String getGriddisplayupdateddate() {
		return DateConvertion.dateTimeToString(updateddate);
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getDeductions() {
		return deductions;
	}

	public void setDeductions(Double deductions) {
		this.deductions = deductions;
	}

	public BigDecimal getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(BigDecimal transactionid) {
		this.transactionid = transactionid;
	}

	public String getPayeremail() {
		return payeremail;
	}

	public void setPayeremail(String payeremail) {
		this.payeremail = payeremail;
	}

	public String getPayertype() {
		return payertype;
	}

	public void setPayertype(String payertype) {
		this.payertype = payertype;
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

	public Double getPayerbalance() {
		return payerbalance;
	}

	public void setPayerbalance(Double payerbalance) {
		this.payerbalance = payerbalance;
	}

	public String getPayeeemail() {
		return payeeemail;
	}

	public void setPayeeemail(String payeeemail) {
		this.payeeemail = payeeemail;
	}

	public String getPayeetype() {
		return payeetype;
	}

	public void setPayeetype(String payeetype) {
		this.payeetype = payeetype;
	}

	public Double getPayeeamount() {
		return payeeamount;
	}

	public void setPayeeamount(Double payeeamount) {
		this.payeeamount = payeeamount;
	}

	public String getPayeecurrency() {
		return payeecurrency;
	}

	public void setPayeecurrency(String payeecurrency) {
		this.payeecurrency = payeecurrency;
	}

	public Double getPayeebalance() {
		return payeebalance;
	}

	public void setPayeebalance(Double payeebalance) {
		this.payeebalance = payeebalance;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public Double getAmountother() {
		return amountother;
	}

	public void setAmountother(Double amountother) {
		this.amountother = amountother;
	}

	public Double getAmountself() {
		return amountself;
	}

	public void setAmountself(Double amountself) {
		this.amountself = amountself;
	}

	public Double getSelfbalance() {
		return selfbalance;
	}

	public void setSelfbalance(Double selfbalance) {
		this.selfbalance = selfbalance;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getUsdcurrency() {
		return usdcurrency;
	}

	public void setUsdcurrency(Double usdcurrency) {
		this.usdcurrency = usdcurrency;
	}

	public Double getYencurrency() {
		return yencurrency;
	}

	public void setYencurrency(Double yencurrency) {
		this.yencurrency = yencurrency;
	}

	public Double getBahtcurrency() {
		return bahtcurrency;
	}

	public void setBahtcurrency(Double bahtcurrency) {
		this.bahtcurrency = bahtcurrency;
	}
	
	public Date getPaydate() {
		return paydate;
	}

	public void setPaydate(Date paydate) {
		this.paydate = paydate;
	}
	
	public String getGriddisplaypaydate() {
		return DateConvertion.dateTimeToString(paydate);
	}

	public BigDecimal getParentid() {
		return parentid;
	}

	public void setParentid(BigDecimal parentid) {
		this.parentid = parentid;
	}
	
	public Boolean getReversal() {
		return reversal;
	}

	public void setReversal(Boolean reversal) {
		this.reversal = reversal;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}
	
	public BigDecimal getSendmoneyid() {
		return sendmoneyid;
	}

	public void setSendmoneyid(BigDecimal sendmoneyid) {
		this.sendmoneyid = sendmoneyid;
	}

	public String getDisplayamount() {
		return displayamount;
	}

	public void setDisplayamount(String displayamount) {
		this.displayamount = displayamount;
	}

	public String getDisplaydeduction() {
		return displaydeduction;
	}

	public void setDisplaydeduction(String displaydeduction) {
		this.displaydeduction = displaydeduction;
	}

	public String getDisplayPayeeBalance() {
		return displayPayeeBalance;
	}

	public void setDisplayPayeeBalance(String displayPayeeBalance) {
		this.displayPayeeBalance = displayPayeeBalance;
	}

	public String getBalancestatus() {
		return balancestatus;
	}

	public void setBalancestatus(String balancestatus) {
		this.balancestatus = balancestatus;
	}

}
