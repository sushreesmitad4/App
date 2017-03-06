<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.constants.Dispute,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type="text/javascript">

	function backForDispute(){
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customerdispute/transactions';
	}
	
	function backForUpdate(){
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customerdispute';
	}
	
	function continueAction() {
		submitFormData(document.raisedisp);
	}
</script>  

<% String disputetype = (String)request.getAttribute("disputetype"); %> 
		<% if(disputetype.equals(Dispute.REFUND)){ %>		
		<div class="pageheading"><spring:message code="raise.refund.dispute.lbl" /></div> 
		<%} %>
		<% if(disputetype.equals(Dispute.CHARGEBACK)){ %>		
		<div class="pageheading"><spring:message code="raise.chargeback.dispute" /></div> 
		<%} %>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="raisedisp" method="POST" commandName="disputeForm" action="raisedispute">
	<table class="form" style="width: 50%">
			<tr class="formtr">
				<td class="formtd">
					<form:label path="transactionId" cssClass="formlebel">
						<spring:message code="transaction.id.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.transactionId}</td>
			</tr>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="transactionDate" cssClass="formlebel">
						<spring:message code="transaction.date.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.transactionDate}</td>
			</tr>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="transactionAmount" cssClass="formlebel">
						<spring:message code="transaction.amount.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.transactionAmount}&nbsp;&nbsp;&nbsp;${disputeForm.transactionCurrency}</td>
			</tr>
			<% if(disputetype.equals(Dispute.REFUND)){ %>			
			<tr>
				<td></td>
				<td class="formerror"><form:errors path="requestAmount" cssClass="error" /></td>
			</tr>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="requestAmount" cssClass="formlebel">
						<spring:message code="requested.amount.lbl" /><span class="mfield">&nbsp;*</span>
					</form:label>
				</td>
				<td class="formtd"><form:input path="requestAmount"  />&nbsp;&nbsp;&nbsp;${disputeForm.dispRequestedCurrency}</td>	
			</tr>
			<% } if(disputetype.equals(Dispute.CHARGEBACK)){ %>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="requestAmount" cssClass="formlebel">
						<spring:message code="requested.amount.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.transactionAmount}&nbsp;&nbsp;&nbsp;${disputeForm.dispRequestedCurrency}</td>
			</tr>
			<tr><form:hidden path="requestAmount"/></tr>
			<%} %>
				 
			<tr>
				<td></td>
				<td class="formerror"><form:errors path="message" cssClass="error" /></td>
			</tr>				
			<tr class="formtr">
				<td class="formtd">
					<form:label path="message" cssClass="formlebel">
						<spring:message code="message.lbl" /><span class="mfield">&nbsp;*</span>
					</form:label>
				</td>
				<td class="formtd"><form:textarea path="message" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td colspan="2"> 
					<form:hidden path="disputeType"/>
					<form:hidden path="transactionId"/>
					<form:hidden path="distype"/>
					<form:hidden path="requestedCurrencyId"/>
					<form:hidden path="transactionDate"/>
					<form:hidden path="transactionAmount"/>
					<form:hidden path="transactionCurrency"/>
					<form:hidden path="dispRequestedCurrency"/>
					<form:hidden path="payeeEmailId"/>
				</td>
			</tr>
			<tr>
				<td colspan=2 align="center">
				   <div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="raisedispute.lbl" />' onclick="continueAction()" />
						<% if(disputetype.equals("update")){  %>
							<input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="backForUpdate()" />
						<% }else {  %>
							<input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="backForDispute()" />
						<% } %>
				  </div>
			   </td>
			</tr>
		</table>
</form:form>