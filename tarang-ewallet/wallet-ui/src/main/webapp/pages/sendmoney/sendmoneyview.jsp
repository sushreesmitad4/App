<%@page import="com.tarang.ewallet.walletui.form.SendMoneyForm"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script>
	function forConfirm(){
		document.getElementById("mode").value ="confirm";
		forSubmitAction();
	}
	function forBack(){
		document.getElementById("mode").value ="back";
		forSubmitAction();
	}
	function forCancel() {
	
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
   	 					window.location.href='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/sendmoney/tosingleconfirmcancel';
    				};
	 	confirmationDialog(yesAction, null, null, message);		
	} 
	
	function forSubmitAction(){
		submitFormData(document.sendconf);;
	}
</script>
<div class="pageheading"><spring:message code="moneytrnsf.singledestination" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="sendconf" method="POST" commandName="sendMoneyForm" action="tosingleconfirm" >

			<table class="form tablefixed" style="width:40%">
				<tr class="formtr">
					<td></td>
					<td><form:hidden path="mode"></form:hidden></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="emailId" cssClass="formlebel">
							<spring:message code="emailid.lbl"/>
						</form:label>
					</td>
					<td class="formtd">${sendMoneyForm.emailId }</td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="requestedAmount" cssClass="formlebel">
							<spring:message code="requested.amount.lbl" />
						</form:label>
					</td>
					<td class="formtd">${sendMoneyForm.requestedAmount }</td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="requestedCurrency" cssClass="formlebel">
							<spring:message code="requested.currency.lbl" />
						</form:label>
					</td>
					<td class="formtd">${requestedCurrencyCode }</td>
				</tr>
				<!-- <tr>
					<td class="formtd">
						<form:label path="actualAmount" cssClass="formlebel">
							<spring:message code="actual.amount.lbl" />
						</form:label>
					</td>
					<td class="formtd">${sendMoneyForm.actualAmount }</td>
				</tr>
				<tr>
				<td class="formtd">
						<form:label path="actualCurrency" cssClass="formlebel">
							<spring:message code="actual.currency.lbl" />
						</form:label></td>
				<td class="formtd">${actualCurrencyCode} </td>
				</tr> -->
				<tr>
					<td class="formtd">
						<form:label path="actualAmount" cssClass="formlebel">
							<spring:message code="amount.fee.tax.lbl" />
						</form:label></td>
					<td class="formtd">${totalAmountFeeTax }</td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="message" cssClass="formlebel">
							<spring:message code="message.lbl" />
						</form:label>
					</td>
					<td>${sendMoneyForm.message }</td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="destinationType" cssClass="formlebel">
							<spring:message code="destination.type.lbl" />
						</form:label>
					</td>
					<td class="formtd">${destinationTypeName}</td>
				</tr>	
				<tr>
					<td class="formtd">
						<form:label path="recurring" cssClass="formlebel">
							<spring:message code="recurring.lbl" />
						</form:label>
					</td>
					<td class="formtd" align="left">${sendMoneyForm.recurring }</td>
				</tr>
				<%
					SendMoneyForm sendMoneyForm =(SendMoneyForm) request.getAttribute("sendMoneyForm");
				  	if(sendMoneyForm.getRecurring()){
				%>
				<tr>
					<td class="formtd">
						<form:label path="userJobName" cssClass="formlebel">
							<spring:message code="jobname.lbl" />
						</form:label>
					</td>
					<td class="formtd" align="left">${sendMoneyForm.userJobName }</td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="fromDate" cssClass="formlebel">
							<spring:message code="fromdate.lbl" />
						</form:label>
					</td>
					<td class="formtd" align="left">${sendMoneyForm.fromDate }</td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="toDate" cssClass="formlebel">
							<spring:message code="todate.lbl" />
						</form:label>
					</td>
					<td class="formtd" align="left">${sendMoneyForm.toDate }</td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="frequency" cssClass="formlebel">
							<spring:message code="frequency.lbl" />
						</form:label>
					</td>
					<td class="formtd" align="left">${freequencyName }</td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="totalOccurences" cssClass="formlebel">
							<spring:message code="total.occurences.lbl" />
							
						</form:label>
					</td>
					<td class="formtd" align="left">${sendMoneyForm.totalOccurences }</td>
				</tr>
				<%} %> 	
				<tr>
					<td colspan="2" align="center">
				      <div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="confirm.lbl" />' onclick="return forConfirm();" />
						<input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="forBack();" />
						<input type="button" class="styled-button" id="cancel" value='<spring:message code="cancel.lbl" />'  onclick="forCancel();" />
					   </div>
					</td>
				</tr>
			</table>
	       	<form:hidden path="emailId"></form:hidden>
	       	<form:hidden path="requestedAmount"></form:hidden>
	      	<form:hidden path="requestedCurrency"></form:hidden>
	       	<form:hidden path="actualAmount"></form:hidden>
	       	<form:hidden path="actualCurrency"></form:hidden>
	       	<form:hidden path="message"></form:hidden>
	       	<form:hidden path="destinationType"></form:hidden>
	       	<form:hidden path="recurring"></form:hidden>
	       	<form:hidden path="userJobName"></form:hidden>
	       	<form:hidden path="fromDate"></form:hidden>
	       	<form:hidden path="toDate"></form:hidden>
	       	<form:hidden path="frequency"></form:hidden>
	       	<form:hidden path="totalOccurences"></form:hidden>
</form:form>